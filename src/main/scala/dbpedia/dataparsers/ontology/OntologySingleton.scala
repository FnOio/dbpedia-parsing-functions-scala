package dbpedia.dataparsers.ontology

import java.io.File

import dbpedia.dataparsers._
import dbpedia.dataparsers.ontology.datatypes.{Datatype, DimensionDatatype, EnumerationDatatype, UnitDatatype}
import dbpedia.dataparsers.ontology.io.OntologyReader
import dbpedia.dataparsers.util.wikiparser.WikiParser
import dbpedia.dataparsers.util.{Language, Redirects, XMLSource}
import org.apache.commons.io.FileUtils

/**
  * Contains all functionality that is desired by the DBpedia Functions
  */
object OntologySingleton {


  private var ontologyObject : Ontology = null
  private var ontologyDatatypes : Map[String, Datatype] = null

  def getDatatypes : Map[String, Datatype] = ontologyDatatypes

  def getOntology : Ontology = ontologyObject

  def load = {

    val ontologyStream =  this.getClass.getClassLoader.getResourceAsStream("ontology.xml")
    val tempFile : File = File.createTempFile("ontology_temp", ".xml")
    tempFile.deleteOnExit()
    FileUtils.copyInputStreamToFile(ontologyStream, tempFile)

    val ontologySource = XMLSource.fromFile(tempFile, Language.Mappings)
    val values = ontologySource.map(WikiParser.getInstance()).flatten.map(page => (page.title, page)).toMap
    ontologyObject = new OntologyReader().read(values.values)
    println("Datatypes loaded.")
    ontologyDatatypes = OntologyDatatypes.load().map(t => (t.name, t)).toMap
  }

  /**
    * Returns the size of the properties map    *
    *
    * @return
    */
  def getPropertiesSize: Int = {
    ontologyObject.properties.size
  }

  /**
    * Returns an ontology property
    *
    * @param key
    * @return
    */
  def getOntologyProperty(key : String) : String = {
    try {
      OntologySingleton.getOntologyProperty(key)
    } catch {
      case ex : Exception => null
    }
  }

  /**
    * (Public) Returns the correct parser for a simple property based on ontology property, ontology datatype, language, ...
    *
    * @param propertyKey
    * @param unitKey
    * @param factor
    * @return
    */
  def getSimplePropertyDataParser(propertyKey: String, unitKey: String, factor: String) : String = {

    val ontologyProperty = try {
      ontologyObject.properties(propertyKey)
    } catch {
      case _ : Exception => null
    }

    val unit = try {
      OntologyDatatypes.load().map(t => (t.name, t)).toMap.get(unitKey).get
    } catch {
      case _ : Exception => null
    }

    val context = createContext()
    val factorDouble = if(factor != null) factor.toDouble else 0

    val fullClassName = getSimplePropertyDataParser(ontologyProperty, unit, context, factorDouble).getClass.toString
    parseDataParserFromClassName(fullClassName)

  }

  /**
    * Parse the data parser type from the full class name
    *
    * @param className
    * @return
    */
  private def parseDataParserFromClassName(className : String) = {
    val pattern = "[A-Z]\\w+".r

    val matched = pattern findFirstIn className

    matched.orNull
  }

  /**
    * (Private) Returns the correct parser for a simple property based on ontology property, ontology datatype, language, ...
    *
    * @param ontologyProperty
    * @param context
    * @param factor
    * @return
    */
  private def getSimplePropertyDataParser(ontologyProperty: OntologyProperty,
                                  ontologyDatatype: Datatype,
                                  context : {
                                    val language : Language
                                    val redirects : Redirects
                                    val ontology : Ontology
                                  },
                                  factor : Double
                                 ) : DataParser = {
    val parser : DataParser =
    if(ontologyProperty != null) {
      ontologyProperty.range match {
        case c: OntologyClass =>
          if (ontologyProperty.name == "foaf:homepage") {
            checkMultiplicationFactor("foaf:homepage", factor)
            new LinkParser()
          }
          else {
            new ObjectParser(context)
          }
        case d: UnitDatatype => new UnitValueParser(context, if (ontologyDatatype != null) ontologyDatatype else d, multiplicationFactor = factor)
        case d: DimensionDatatype => new UnitValueParser(context, if (ontologyDatatype != null) ontologyDatatype else d, multiplicationFactor = factor)
        case d: EnumerationDatatype => {
          checkMultiplicationFactor("EnumerationDatatype", factor)
          new EnumerationParser(d)
        }
        case dt: Datatype => dt.name match {
          case "owl:Thing" =>
            if (dt.name == "foaf:homepage") {
              checkMultiplicationFactor("foaf:homepage", factor)
              new LinkParser()
            }
            else {
              new ObjectParser(context)
            }
          case "xsd:integer" => new IntegerParser(context, multiplicationFactor = factor)
          case "xsd:positiveInteger" => new IntegerParser(context, multiplicationFactor = factor, validRange = (i => i > 0))
          case "xsd:nonNegativeInteger" => new IntegerParser(context, multiplicationFactor = factor, validRange = (i => i >= 0))
          case "xsd:nonPositiveInteger" => new IntegerParser(context, multiplicationFactor = factor, validRange = (i => i <= 0))
          case "xsd:negativeInteger" => new IntegerParser(context, multiplicationFactor = factor, validRange = (i => i < 0))
          case "xsd:double" => new DoubleParser(context, multiplicationFactor = factor)
          case "xsd:float" => new DoubleParser(context, multiplicationFactor = factor)
          case "xsd:string" => // strings with no language tags
          {
            checkMultiplicationFactor("xsd:string", factor)
            StringParser
          }
          case "rdf:langString" => // strings with language tags
          {
            checkMultiplicationFactor("rdf:langString", factor)
            StringParser
          }
          case "xsd:anyURI" => {
            checkMultiplicationFactor("xsd:anyURI", factor)
            new LinkParser(false)
          }
          case "xsd:date" => {
            checkMultiplicationFactor("xsd:date", factor)
            new DateTimeParser(context, dt)
          }
          case "xsd:gYear" => {
            checkMultiplicationFactor("xsd:gYear", factor)
            new DateTimeParser(context, dt)
          }
          case "xsd:gYearMonth" => {
            checkMultiplicationFactor("xsd:gYearMonth", factor)
            new DateTimeParser(context, dt)
          }
          case "xsd:gMonthDay" => {
            checkMultiplicationFactor("xsd:gMonthDay",factor)
            new DateTimeParser(context, dt)
          }
          case "xsd:boolean" => {
            checkMultiplicationFactor("xsd:boolean", factor)
            BooleanParser
          }
          case name => null
        }
        case other => null
      }
    } else {
      null
    }

    parser
  }

  /**
    * Utility method for creating a context that will be passed to parsers
    *
    * @return
    */
  private def createContext() : {
    val language : Language
    val redirects : Redirects
    val ontology : Ontology
  } = {

    val context = new {
      val language = Language.English
      val redirects = new Redirects(Map())
      val ontology = ontologyObject
    }

    context
  }

  /**
    * Check the multiplication factor if it is equal to 1 or not
    *
    * @param datatypeName
    * @param factor
    */
  private def checkMultiplicationFactor(datatypeName : String, factor: Double)
  {
    if(factor != 1)
    {
      throw new IllegalArgumentException("multiplication factor cannot be specified for " + datatypeName)
    }
  }

}
