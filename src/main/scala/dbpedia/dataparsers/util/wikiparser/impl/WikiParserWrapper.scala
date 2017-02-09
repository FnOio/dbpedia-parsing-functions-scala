package dbpedia.dataparsers.util.wikiparser.impl

import dbpedia.dataparsers.util.wikiparser.{PageNode, WikiParser}
import dbpedia.dataparsers.util.wikiparser.WikiPage
import dbpedia.dataparsers.util.wikiparser.impl.simple.SimpleWikiParser

import WikiParserWrapper._

import dbpedia.dataparsers.util.wikiparser.{PageNode, WikiPage, WikiParser}
import dbpedia.dataparsers.util.wikiparser.impl.simple.SimpleWikiParser

/**
 * Created with IntelliJ IDEA.
 * User: andread
 * Date: 08/04/13
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */

object WikiParserWrapper {

  private val simpleWikiParser = new SimpleWikiParser()

}

class WikiParserWrapper(wikiTextParserName: String) extends  WikiParser{

  def apply(page : WikiPage) : Option[PageNode]  =
  {
    page.format match {
      //case "application/json" => jsonParser(page)  //obslete now after core refactoring
      case _ =>
        if (wikiTextParserName == null || wikiTextParserName.equals("simple")){
          simpleWikiParser(page)
        } else None
    }
  }
}