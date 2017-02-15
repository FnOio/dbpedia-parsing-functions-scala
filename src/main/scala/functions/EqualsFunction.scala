package functions

import dbpedia.dataparsers.StringParser
import dbpedia.dataparsers.util.wikiparser.PropertyNode
import dbpedia.dataparsers.util.wikiparser.impl.simple.SimpleWikiParser

/**
  * Function to test equality of an Infobox property and a given value
  */
class EqualsFunction(property : String, value : String) extends Function {

  private val wikiparser = new SimpleWikiParser
  val node = wikiparser.parseString(property)

  def execute() : Boolean = {

    val propertyText = StringParser.parse(node.head.asInstanceOf[PropertyNode])
    propertyText.get.equals(value)

  }

}
