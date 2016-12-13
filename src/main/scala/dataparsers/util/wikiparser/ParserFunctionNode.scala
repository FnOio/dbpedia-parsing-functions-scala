package dataparsers.util.wikiparser

/**
 * Represents a parser function.
 *
 * @param title The title of the page, where this parser function is defined
 * @param children The properties of this parser function
 * @param line The source line number of this parser function
 */
case class ParserFunctionNode(title : String, override val children : List[Node], override val line : Int) extends Node(children, line)
{
    // TODO: check that separating children by pipe is correct
    def toWikiText = "{{" + title + ":" + children.map(_.toWikiText).mkString("|") + "}}"
    
    // parser functions are skipped for plain text
    def toPlainText = ""
}
