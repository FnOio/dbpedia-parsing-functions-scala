package functions.implementations.conditions

import dbpedia.dataparsers.StringParser
import dbpedia.dataparsers.util.wikiparser.impl.simple.SimpleWikiParser
import functions.Function

/**
  * Function to test equality of an Infobox property and a given value
  */
class EqualsFunction(property : String, value : String) extends Function {

  private val wikiparser = new SimpleWikiParser
  val node = wikiparser.parseProperty(property)

  def execute() : Boolean = {

    val propertyText = StringParser.parse(node)
    propertyText.get.equals(value)
  }



}
