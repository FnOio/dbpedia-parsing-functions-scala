package functions

import dbpedia.dataparsers.ontology.OntologyProperty
import dbpedia.dataparsers.ontology.datatypes.Datatype
import dbpedia.dataparsers.{ContextLoader, DataParserConfig, DateTimeParser}
import dbpedia.config.mapping.DateIntervalMappingConfig._
import dbpedia.dataparsers.util.wikiparser.{NodeUtil, PropertyNode}
import dbpedia.dataparsers.util.wikiparser.impl.simple.SimpleWikiParser
import dbpedia.destinations.DBpediaDatasets


/**
  * Created by wmaroy on 08.02.17.
  */
class StartDateFunction(property : String, ontologyPropertyString: String) extends Function {

  private val context = ContextLoader.loadContext()
  private val ontologyProperty = context.ontology.properties(ontologyPropertyString)
  private val wikiparser = new SimpleWikiParser
  private val startDateParser = new DateTimeParser(context, rangeType(ontologyProperty))
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
  def execute() : String = {
    val node = wikiparser.parseProperty(property)
    NodeUtil.splitPropertyNode(node, splitPropertyNodeRegex)
      .map( node => extractStartFromInterval(node))
      .dropWhile(e => e.isEmpty).headOption.orNull
  }

  private def extractStartFromInterval(node : PropertyNode) : String = {

    //Split the node. Note that even if some of these hyphens are looking similar, they represent different Unicode numbers.
    val splitNodes = splitIntervalNode(node)

    //Can only map exactly two values onto an interval
    if(splitNodes.size > 2 || splitNodes.size  <= 0)
    {
      null
    }

    //Parse start; return if no start year has been found
    val start = startDateParser.parse(splitNodes(0)).orNull.toString
    start
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
