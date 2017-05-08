package functions.implementations.core

import dbpedia.dataparsers.StringParser
import dbpedia.dataparsers.util.wikiparser.impl.simple.SimpleWikiParser

/**
  * Created by wmaroy on 22.04.17.
  */
class ExtractStringFunction {

  val wikiparser = new SimpleWikiParser

  def execute(property : String): Seq[String] = {

    val propertyNode = wikiparser.parseProperty(property)
    Seq(StringParser.parse(propertyNode).get)

  }

}
