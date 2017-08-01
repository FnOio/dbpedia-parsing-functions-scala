package functions.implementations.date

import dbpedia.config.mapping.DateIntervalMappingConfig._
import dbpedia.dataparsers.ontology.OntologyProperty
import dbpedia.dataparsers.ontology.datatypes.Datatype
import dbpedia.dataparsers.util.Language
import dbpedia.dataparsers.util.wikiparser.impl.simple.SimpleWikiParser
import dbpedia.dataparsers.util.wikiparser.{NodeUtil, PropertyNode}
import dbpedia.dataparsers.{ContextLoader, DataParserConfig, DateTimeParser, StringParser}
import dbpedia.destinations.DBpediaDatasets
import functions.Function

/**
  * Created by wmaroy on 08.02.17.
  */
class EndDateFunction(property : String, ontologyPropertyString: String, language: String) extends Function {

  private val context = ContextLoader.loadContext(Language(language))
  private val ontologyProperty = context.ontology.properties(ontologyPropertyString)
  private val wikiparser = new SimpleWikiParser
  private val endDateParser = new DateTimeParser(context, rangeType(ontologyProperty))
  private val splitPropertyNodeRegex = splitPropertyNodeMap.getOrElse(context.language.wikiCode, splitPropertyNodeMap("en"))
  private val presentStrings : Set[String] = presentMap.getOrElse(context.language.wikiCode, presentMap("en"))
  private val sinceString = sinceMap.getOrElse(context.language.wikiCode, sinceMap("en"))
  private val onwardString = onwardMap.getOrElse(context.language.wikiCode, onwardMap("en"))
  private val splitString = splitMap.getOrElse(context.language.wikiCode, splitMap("en"))

  // TODO: the parser should resolve HTML entities
  private val intervalSplitRegex = "(?iu)(" + DataParserConfig.dashVariationsRegex + "|&mdash;|&ndash;" + ( if (splitString.isEmpty) "" else "|" + splitString ) + ")"

  private val datasets = Set(DBpediaDatasets.OntologyPropertiesLiterals)

  /**
    * Executes the function
    */
  def execute(): String = {
    val node = wikiparser.parseProperty(property)
    NodeUtil.splitPropertyNode(node, splitPropertyNodeRegex)
      .map( node => extractEndFromInterval(node))
      .dropWhile(e => e.isEmpty).headOption.orNull
  }

  private def extractEndFromInterval(node : PropertyNode) : String = {

    //Split the node. Note that even if some of these hyphens are looking similar, they represent different Unicode numbers.
    val splitNodes = splitIntervalNode(node)

    //Can only map exactly two values onto an interval
    if (splitNodes.size > 2 || splitNodes.size <= 0) {
      null
    } else {

      //Parse end
      val endDateOpt = splitNodes match {
        //if there were two elements found
        case List(start, end) => end.retrieveText match {
          //if until "present" is specified through one of the special words, don't write end triple
          case Some(text: String) if presentStrings.contains(text.trim.toLowerCase) => None

          //normal case of specified end date
          case _ => endDateParser.parse(end)
        }

        //if there was only one element found
        case List(start) => StringParser.parse(start) match {
          //if in a "since xxx" construct, don't write end triple
          case Some(text: String) if (text.trim.toLowerCase.startsWith(sinceString)
            || text.trim.toLowerCase.endsWith(onwardString)) => None

        }

        case _ => throw new IllegalStateException("size of split nodes must be 0 < l < 3; is " + splitNodes.size)
      }
      val endDate = endDateOpt.orNull
      if(endDate != null) endDate.toString else ""
    }
  }

  private def splitIntervalNode(propertyNode : PropertyNode) : List[PropertyNode] =
  {
    //Split the node. Note that even if some of these hyphens are looking similar, they represent different Unicode numbers.
    val splitNodes = NodeUtil.splitPropertyNode(propertyNode, intervalSplitRegex)

    //Did we split a date? e.g. 2009-10-13
    if(splitNodes.size > 2)
    {
      NodeUtil.splitPropertyNode(propertyNode, "\\s" + intervalSplitRegex + "\\s")
    }
    else
    {
      splitNodes
    }
  }

  private def rangeType(property: OntologyProperty) : Datatype =
  {
    if (! property.range.isInstanceOf[Datatype]) throw new IllegalArgumentException("property "+property+" has range "+property.range+", which is not a datatype")
    property.range.asInstanceOf[Datatype]
  }

}
