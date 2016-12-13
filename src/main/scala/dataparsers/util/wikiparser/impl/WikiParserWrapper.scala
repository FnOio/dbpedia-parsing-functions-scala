package dataparsers.util.wikiparser.impl

import dataparsers.util.wikiparser.{PageNode, WikiParser}
import dataparsers.util.wikiparser.WikiPage
import dataparsers.util.wikiparser.impl.simple.SimpleWikiParser

import WikiParserWrapper._

import dataparsers.util.wikiparser.{PageNode, WikiPage, WikiParser}
import dataparsers.util.wikiparser.impl.simple.SimpleWikiParser

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