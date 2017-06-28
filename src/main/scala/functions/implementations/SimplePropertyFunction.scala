package functions.implementations

import dbpedia.dataparsers._
import dbpedia.dataparsers.ontology.datatypes._
import dbpedia.dataparsers.ontology.{Ontology, OntologyClass, OntologyDatatypes, OntologySingleton}
import dbpedia.dataparsers.util.wikiparser.impl.simple.SimpleWikiParser
import dbpedia.dataparsers.util.{Language, Redirects}
import functions.Function

import scala.collection.mutable.ListBuffer
import scala.language.reflectiveCalls

//TODO change datatype parameter here and in mapping files
class SimplePropertyFunction(
  val templateProperty : String, // IntermediateNodeMapping and CreateMappingStats requires this to be public
  val select : String,  // rml mappings require this to be public (e.g. ModelMapper)
  val prefix : String,  // rml mappings require this to be public (e.g. ModelMapper)
  val suffix : String,  // rml mappings require this to be public (e.g. ModelMapper)
  val transform : String, // rml mappings require this to be public (e.g. ModelMapper)
  val factor : Double,  // rml mappings require this to be public (e.g. ModelMapper)
  val datatype : String,
  val unit :  String) extends Function {


  private val ontologyObject = OntologySingleton.getOntology
  private val datatypes = OntologySingleton.getDatatypes


  private val ontologyProperty = try {
      val result = ontologyObject.properties(datatype)
      result
    } catch {
      case _ : Exception => null
    }


  val ut = try {
    datatypes.get(unit).get
  } catch {
    case _ : Exception => null
  }

    private val language = Language.English
    private val wikiparser = new SimpleWikiParser

    val context = new {
      val language : Language = Language.English
      val redirects : Redirects = new Redirects(Map())
      val ontology : Ontology = new Ontology(null,null,datatypes,null,null,null)
    }

    val selector: List[_] => List[_] =
        select match {
            case "first" => _.take(1)
            case "last" => _.reverse.take(1)
            case null => identity
            case _ => throw new IllegalArgumentException("Only 'first' or 'last' are allowed in property 'select'")
        }


  /**
   * Transforms a text value appending/prepending a suffix/prefix.
   * Note that the value will be trimmed iff the function needs to apply suffix/prefix.
   * Otherwise the value will be left untouched.
   *
   * The suffix/prefix will never be trimmed.
   *
   * @param value The text value to transform
   * @return  Transformed text (after applying prefix/suffix)
   */
    private def valueTransformer(value : String) = {

        val p = prefix match {
            case _ : String => prefix
            case _ => ""
        }

        val s = suffix match {
            case _ : String => suffix
            case _ => ""
        }

        p + value.trim + s
    }

    private val languageResourceNamespace = language.resourceUri.namespace

    private val parser : DataParser = if(ontologyProperty != null) {
    ontologyProperty.range match {
      case c: OntologyClass =>
        if (ontologyProperty.name == "foaf:homepage") {
          checkMultiplicationFactor("foaf:homepage")
          new LinkParser()
        }
        else {
          new ObjectParser(context)
        }
      case d: UnitDatatype => new UnitValueParser(context, if (ut != null) ut else d, multiplicationFactor = factor)
      case d: DimensionDatatype => new UnitValueParser(context, if (ut != null) ut else d, multiplicationFactor = factor)
      case d: EnumerationDatatype => {
        checkMultiplicationFactor("EnumerationDatatype")
        new EnumerationParser(d)
      }
      case dt: Datatype => dt.name match {
        case "owl:Thing" =>
          if (dt.name == "foaf:homepage") {
            checkMultiplicationFactor("foaf:homepage")
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
          checkMultiplicationFactor("xsd:string")
          StringParser
        }
        case "rdf:langString" => // strings with language tags
        {
          checkMultiplicationFactor("rdf:langString")
          StringParser
        }
        case "xsd:anyURI" => {
          checkMultiplicationFactor("xsd:anyURI")
          new LinkParser(false)
        }
        case "xsd:date" => {
          checkMultiplicationFactor("xsd:date")
          new DateTimeParser(context, dt)
        }
        case "xsd:gYear" => {
          checkMultiplicationFactor("xsd:gYear")
          new DateTimeParser(context, dt)
        }
        case "xsd:gYearMonth" => {
          checkMultiplicationFactor("xsd:gYearMonth")
          new DateTimeParser(context, dt)
        }
        case "xsd:gMonthDay" => {
          checkMultiplicationFactor("xsd:gMonthDay")
          new DateTimeParser(context, dt)
        }
        case "xsd:boolean" => {
          checkMultiplicationFactor("xsd:boolean")
          BooleanParser
        }
        case name => null
      }
      case other => null
    }
  } else {
    null
  }

    private def createDataParserUri() : String = {

      var uri : String = "http://localhost:8080/functions/dataparser?"

      if(ontologyProperty != null) uri += "property=" + datatype
      if(unit != null) uri += "&unit=" + unit
      uri += "&factor=" + factor

      uri
    }

    private def executeUri(uri : String) : String = {
      scala.io.Source.fromURL(uri).mkString
    }


    private def checkMultiplicationFactor(datatypeName : String)
    {
        if(factor != 1)
        {
            throw new IllegalArgumentException("multiplication factor cannot be specified for " + datatypeName)
        }
    }

    def execute(): Seq[String] = {
      val node = wikiparser.parseProperty(templateProperty)
      var result = new ListBuffer[String]
      if(parser != null) {
        val parseResults = parser.parsePropertyNode(node, true, transform, valueTransformer)
        for (parseResult <- selector(parseResults)) {
          val g = parseResult match {
            case (value: Double, unit: UnitDatatype) => {
              if (unit.isInstanceOf[InconvertibleUnitDatatype]) {
                value.toString
              } else {
                unit.toStandardUnit(value).toString
              }
            }
            case value => value.toString
          }
          result += g
        }

      }
      result.toSeq

    }

}
