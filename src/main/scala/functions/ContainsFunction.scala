package functions

import dbpedia.dataparsers.StringParser
import dbpedia.dataparsers.util.wikiparser.impl.simple.SimpleWikiParser

/**
  * Checks if an Infobox property contains a given value.
  */
class ContainsFunction(property : String, value : String) extends Function {

  val wikiparser = new SimpleWikiParser

  def execute(): Boolean = {
    val propertyNode = wikiparser.parseString(property)
    val parsedNode = StringParser.parse(propertyNode)
    parsedNode.contains(value)
  }

}
