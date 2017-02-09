package functions


import java.io.{File, FileOutputStream}
import java.util

import scala.collection.mutable.ListBuffer
import dbpedia.dataparsers._
import dbpedia.dataparsers.util.{Language, Redirects, XMLSource}
import dbpedia.dataparsers.util.wikiparser.impl.simple.SimpleWikiParser
import dbpedia.dataparsers.util.wikiparser.{Namespace, WikiTitle}

import scala.language.reflectiveCalls
import scala.collection.JavaConverters._
import dbpedia.dataparsers.ontology.datatypes._
import dbpedia.dataparsers.ontology.{Ontology, OntologyClass, OntologyDatatypes}
import dbpedia.dataparsers.ontology.io.OntologyReader
import dbpedia.dataparsers.util.wikiparser.impl.wikipedia.Redirect
import org.apache.commons.io.FileUtils


class SimplePropertyFunction(
  val templateProperty : String, // IntermediateNodeMapping and CreateMappingStats requires this to be public
  val select : String,  // rml mappings require this to be public (e.g. ModelMapper)
  val prefix : String,  // rml mappings require this to be public (e.g. ModelMapper)
  val suffix : String,  // rml mappings require this to be public (e.g. ModelMapper)
  val transform : String, // rml mappings require this to be public (e.g. ModelMapper)
  val factor : Double,  // rml mappings require this to be public (e.g. ModelMapper)
  val datatype : String,
  val unit :  String) extends Function {

  private val ontologyStream =  this.getClass.getClassLoader.getResourceAsStream("ontology.xml")
  private val tempFile : File = File.createTempFile("ontology_temp", ".xml")
  tempFile.deleteOnExit()
  FileUtils.copyInputStreamToFile(ontologyStream, tempFile)

  private val ontologySource = XMLSource.fromFile(tempFile, Language.English)
  private val ontologyObject = new OntologyReader().read(ontologySource)

  val dt = try {
      ontologyObject.datatypes(datatype)
    } catch {
      case _ : Exception => null
    }


  val ut = try {
    OntologyDatatypes.load().map(t => (t.name, t)).toMap.get(unit).get
  } catch {
    case _ : Exception => null
  }

    private val language = Language.English
    private val wikiparser = new SimpleWikiParser

    val context = new {
      val language : Language = Language.English
      val redirects : Redirects = new Redirects(Map())
      val ontology : Ontology = new Ontology(null,null,OntologyDatatypes.load().map(t => (t.name, t)).toMap,null,null,null)
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

    val languageResourceNamespace = language.resourceUri.namespace

    private val parser : DataParser = dt match
        {
      case null =>
        if (datatype == "foaf:homepage") {
          checkMultiplicationFactor("foaf:homepage")
          new LinkParser()
        }
        else {
          new ObjectParser(context)
        }
      case d : UnitDatatype      => new UnitValueParser(context, if(ut != null) ut else d , multiplicationFactor = factor)
      case d : DimensionDatatype => new UnitValueParser(context, if(ut != null) ut else d, multiplicationFactor = factor)
      case d : EnumerationDatatype =>
      {
        checkMultiplicationFactor("EnumerationDatatype")
        new EnumerationParser(d)
      }
      case dt : Datatype => dt.name match
      {
        case "owl:Thing" =>
          if (dt.name == "foaf:homepage") {
            checkMultiplicationFactor("foaf:homepage")
            new LinkParser()
          }
          else {
            new ObjectParser(context)
          }
        case "xsd:integer" => new IntegerParser(context, multiplicationFactor = factor)
        case "xsd:positiveInteger"    => new IntegerParser(context, multiplicationFactor = factor, validRange = (i => i > 0))
        case "xsd:nonNegativeInteger" => new IntegerParser(context, multiplicationFactor = factor, validRange = (i => i >=0))
        case "xsd:nonPositiveInteger" => new IntegerParser(context, multiplicationFactor = factor, validRange = (i => i <=0))
        case "xsd:negativeInteger"    => new IntegerParser(context, multiplicationFactor = factor, validRange = (i => i < 0))
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
        case "xsd:anyURI" =>
        {
          checkMultiplicationFactor("xsd:anyURI")
          new LinkParser(false)
        }
        case "xsd:date" =>
        {
          checkMultiplicationFactor("xsd:date")
          new DateTimeParser(context, dt)
        }
        case "xsd:gYear" =>
        {
          checkMultiplicationFactor("xsd:gYear")
          new DateTimeParser(context, dt)
        }
        case "xsd:gYearMonth" =>
        {
          checkMultiplicationFactor("xsd:gYearMonth")
          new DateTimeParser(context, dt)
        }
        case "xsd:gMonthDay" =>
        {
          checkMultiplicationFactor("xsd:gMonthDay")
          new DateTimeParser(context, dt)
        }
        case "xsd:boolean" =>
        {
          checkMultiplicationFactor("xsd:boolean")
          BooleanParser
        }
        case name => throw new IllegalArgumentException("Not implemented range " + name + " of property " + templateProperty)
      }
      case other => throw new IllegalArgumentException("Property " + templateProperty + " does have invalid range " + other)
        }


    private def checkMultiplicationFactor(datatypeName : String)
    {
        if(factor != 1)
        {
            throw new IllegalArgumentException("multiplication factor cannot be specified for " + datatypeName)
        }
    }

    def execute(): Array[String] = {
      val node = wikiparser.parseString(templateProperty)
      var result = new ListBuffer[String]
      val parseResults = parser.parsePropertyNode(node,true, transform, valueTransformer)
      for(parseResult <- selector(parseResults)) {
        val g = parseResult match
        {
          case (value : Double, unit : UnitDatatype) => {
            if(unit.isInstanceOf[InconvertibleUnitDatatype]) {
              value.toString
            } else {
              unit.toStandardUnit(value).toString
            }
          }
          case value => value.toString
        }
        result += g
      }

      result.toArray

    }

}
