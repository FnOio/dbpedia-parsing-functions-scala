package dataparsers.util

import java.net.URI

import scala.collection.mutable.ArrayBuffer
import dataparsers.util.wikiparser._

/**
 * Template transformations.
 *
 * Could be useful to analyse
 *
 * http://en.wikipedia.org/wiki/Wikipedia:Template_messages
 *
 * and collect transformations for the commonly used templates
 */
object TemplateTransformConfig {

  private def extractTextFromPropertyNode(node: Option[PropertyNode], prefix : String = "", suffix : String = "") : String = {
    node match {
      case Some(p) =>
        val propertyText = p.children.collect { case TextNode(t, _) => t }.mkString.trim
        if (propertyText.nonEmpty) prefix + propertyText + suffix else ""
      case None => ""
    }
  }

  // General functions
  private def textNode(text: String)(node: TemplateNode, lang:Language) : List[TextNode] = List(TextNode(text, node.line))

  /**
   * Extracts all the children of the PropertyNode's in the given TemplateNode
   */
  private def extractChildren(filter: PropertyNode => Boolean, split : Boolean = true)(node: TemplateNode, lang:Language) : List[Node] = {
    // We have to reverse because flatMap prepends to the final list
    // while we want to keep the original order
    val children : List[Node] = node.children.filter(filter).flatMap(_.children).reverse

    val splitChildren = new ArrayBuffer[Node]()
    val splitTxt = if (split) "<br />" else " "
    for ( c <- children) {
      splitChildren += new TextNode(splitTxt, c.line)
      splitChildren += c
    }
    if (splitChildren.nonEmpty) {
      splitChildren += new TextNode(splitTxt, 0)
    }
    splitChildren.toList

  }

  private def identity(node: TemplateNode, lang:Language) : List[Node] = List(node)

  private def externalLinkNode(node: TemplateNode, lang:Language) : List[Node] = {

    try {

      def defaultLinkTitle(node: Node) : PropertyNode = {
        PropertyNode("link-title", List(TextNode("", node.line)), node.line)
      }

      // Check if this uri has a scheme. If it does not, add a default http:// scheme
      // From https://en.wikipedia.org/wiki/Template:URL:
      // The first parameter is parsed to see if it takes the form of a complete URL.
      // If it doesn't start with a URI scheme (such as "http:", "https:", or "ftp:"),
      // an "http://" prefix will be prepended to the specified generated target URL of the link.
      val uri = new URI(extractTextFromPropertyNode(node.property("1")))
      val uriWithScheme = if (uri.getScheme() == null) new URI("http://" + uri.toString)
                          else uri

      List(
        ExternalLinkNode(
          uriWithScheme,
          node.property("2").getOrElse(defaultLinkTitle(node)).children,
          node.line
        )
      )
    } catch {
      // In case there are problems with the URL/URI just bail and return the original node
      case _ : Throwable => List(node)
    }
  }

  private val transformMap : Map[String, Map[String, (TemplateNode, Language) => List[Node]]] = Map(

    "en" -> Map(
      "Dash" -> textNode(" - ") _ ,
      "Spaced ndash" -> textNode(" - ") _ ,
      "Ndash" -> textNode("-") _ ,
      "Mdash" -> textNode(" - ") _ ,
      "Marriage" -> extractChildren { p : PropertyNode => p.key != "end" && p.key != "()" }  _,
      "Emdash" -> textNode(" - ") _ ,
      "-" -> textNode("<br />") _ ,
      "Clr" -> textNode("<br />") _ ,
      "Nowrap" -> extractChildren { p => true }  _,
      "Nobr" -> extractChildren { p => true }  _,
      "Flatlist" -> extractChildren { p : PropertyNode => !(Set("class", "style", "indent").contains(p.key)) }  _,
      "Plainlist" -> extractChildren { p : PropertyNode => !(Set("class", "style", "indent").contains(p.key)) } _ ,
      "Hlist" ->  extractChildren { p : PropertyNode => !(Set("class", "style", "ul_style", "li_style", "indent").contains(p.key)) } _ ,
      "Unbulleted list" -> extractChildren { p : PropertyNode => !(Set("class", "style", "ul_style", "li_style", "indent").contains(p.key)) } _ ,
      "Lang" -> ((node: TemplateNode, lang:Language) =>
        List(new TextNode("<br />" + extractTextFromPropertyNode(node.property("3")) + "<br />", node.line))), //TODO: we need to set the language in the TextNode


      "URL" -> externalLinkNode _ ,
      "Official website" -> externalLinkNode _ ,

      // http://en.wikipedia.org/wiki/Template:ICD10
      // See https://github.com/dbpedia/extraction-framework/issues/40
      "ICD10" ->
        ((node: TemplateNode, lang:Language) =>
          List(
            new TextNode(extractTextFromPropertyNode(node.property("1")) +
                         extractTextFromPropertyNode(node.property("2")) +
                         extractTextFromPropertyNode(node.property("3"), "."), node.line)
          )
        )
      ,
      // http://en.wikipedia.org/wiki/Template:ICD9
      // See https://github.com/dbpedia/extraction-framework/issues/40
      "ICD9" -> extractChildren { p : PropertyNode => p.key == "1" } _ /*,

      // see https://www.mediawiki.org/wiki/Help:Magic_words#Variables
      // For now all these remain commented becase the SimpleWikiPArser cannot handle well nested templates and results in many errors
      "CURRENTDAY" ->  textNode(" " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " ") _ ,
      "CURRENTDAY2" -> textNode(" " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " ") _ ,
      "CURRENTDOW" -> textNode(" " + Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + " ") _ ,
      "CURRENTDAYNAME" -> textNode(" " + Calendar.getInstance.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH) + " ") _ ,
      "CURRENTHOUR" -> textNode(" " + Calendar.getInstance().get(Calendar.HOUR) + " ") _ ,
      "CURRENTMONTH" ->  textNode(" " + Calendar.getInstance().get(Calendar.MONTH) + " ") _ ,
      "CURRENTMONTHNAME" ->  textNode(" " + Calendar.getInstance.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " ") _ ,
      "CURRENTTIME" ->  textNode(" " + new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime) + " ") _ ,
      "CURRENTTIMESTAMP" -> textNode(" " + new SimpleDateFormat("YYYYMMDDHHmmss").format(Calendar.getInstance().getTime) + " ") _ ,
      "CURRENTWEEK" -> textNode(" " + Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) + " ") _ ,
      "CURRENTYEAR" ->  textNode(" " + Calendar.getInstance().get(Calendar.YEAR) + " ") _ ,

      "NAMESPACE" -> ((node: TemplateNode, lang:Language) => List( new TextNode(" " + node.root.title.namespace.name + " ", node.line))),
      "NAMESPACEE" -> ((node: TemplateNode, lang:Language) => List( new TextNode(" " + node.root.title.namespace.name + " ", node.line))),
      "NAMESPACENUMBER" -> ((node: TemplateNode, lang:Language) => List( new TextNode(" " + node.root.title.namespace.code + " ", node.line))),
      "REVISIONID" -> ((node: TemplateNode, lang:Language) => List( new TextNode(" " + node.root.revision + " ", node.line))),
      "REVISIONTIMESTAMP" -> ((node: TemplateNode, lang:Language) => List( new TextNode(" " + node.root.timestamp + " ", node.line))),
      "REVISIONUSER" -> ((node: TemplateNode, lang:Language) => List( new TextNode(" " + node.root.contributorName + " ", node.line))),
      //"REVISIONDAY2" -> textNode(" " + ((node: TemplateNode, lang:Language) => node.root.title.namespace.code) + " ") _ ,
      //"REVISIONMONTH" -> textNode(" " + ((node: TemplateNode, lang:Language) => node.root.timestamp).distinct.mkString(" ")) + " ") _ ,
      //"REVISIONYEAR" -> textNode(" " + ((node: TemplateNode, lang:Language) => node.root.title.namespace.code) + " ") _
      //"TALKPAGENAME" -> textNode(" " + Calendar.getInstance().get(Calendar.YEAR) + " ") _ ,
      "PAGEID" -> ((node: TemplateNode, lang:Language) => List( new TextNode(" " + node.root.id + " ", node.line))),
      "FULLPAGENAME" -> ((node: TemplateNode, lang:Language) => List( new TextNode(" " + node.root.title.decodedWithNamespace + " ", node.line))),
      "FULLPAGENAMEE" -> ((node: TemplateNode, lang:Language) => List( new TextNode(" " + node.root.title.decodedWithNamespace + " ", node.line))),
      "PAGENAME" -> ((node: TemplateNode, lang:Language) => List( new TextNode(" " + node.root.title.decoded + " ", node.line))) ,
      "PAGENAMEE" -> ((node: TemplateNode, lang:Language) => List( new TextNode(" " + node.root.title.decoded + " ", node.line)))  */

    ),

    "commons" -> Map(
      "Self" -> unwrapTemplates { p: PropertyNode => !(Set("author", "attribution", "migration").contains(p.key)) } _,
      "PD-Art" -> unwrapTemplates { p: PropertyNode => Set("1").contains(p.key) } _,
      "PD-Art-two" -> unwrapTemplates { p: PropertyNode => !(Set("deathyear").contains(p.key)) } _,
      "Licensed-PD-Art" -> unwrapTemplates { p: PropertyNode => Set("1", "2").contains(p.key) } _,
      "Licensed-PD-Art-two" -> unwrapTemplates { p: PropertyNode => Set("1", "2", "3").contains(p.key) } _,
      "Licensed-FOP" -> unwrapTemplates { p: PropertyNode => Set("1", "2").contains(p.key) } _,
      "Copyright information" -> unwrapTemplates { p: PropertyNode => !(Set("13").contains(p.key)) } _,
      "PD-scan" -> unwrapTemplates { p: PropertyNode => Set("1").contains(p.key) } _
    )

  )

  /*
   * Unwraps templates that contain other templates as arguments. Please ensure
   * that the filter removes ALL children that may not be templates.
   *
   * Since we unwrap the template, the original template node is kept at the head
   * of the resulting list.
   *
   * Examples of such templates include:
   *    - https://commons.wikimedia.org/wiki/Template:Self
   *    - https://commons.wikimedia.org/wiki/Template:PD-art
   */
  private def unwrapTemplates(filter: PropertyNode => Boolean)(node: TemplateNode, lang:Language):List[Node] =
      node :: toTemplateNodes(extractChildren(filter)(node, lang), lang) 

  /**
   * Stores the Template namespace to avoid querying Namespace.template in a loop.
   */
  private val templateNamespace = Namespace.Template
  
  /**
   * Converts TextNodes in a List[Node] to TemplateNodes. Used by unwrapTemplates.
   * Note that there is no way to test whether this template exists at this
   * stage: EVERY TextNode will be converted into a TemplateNode, which may or
   * may not point to an actual template.
   *
   * TODO: Support entire templates embedded in others, such as this example
   * from https://commons.wikimedia.org/wiki/Template:Licensed-FOP
   *    - {{Licensed-FOP|Spain| {{self|cc-by-sa-3.0|GFDL}} }} 
   *
   * @param nodes The nodes to convert
   * @param lang The language in which the TemplateNode should be created.
   * @return A List of every node in nodes, with TextNodes changed to TemplateNodes.
   */
  private def toTemplateNodes(nodes: List[Node], lang: Language): List[Node] =
      nodes.flatMap({
        case TextNode(text, line) => List(TemplateNode(
                new WikiTitle(text.capitalize, templateNamespace, lang), 
                List.empty, line
            ))
        case node:Node => List(node)
      })

  def apply(node: TemplateNode, lang: Language) : List[Node] = {

     val mapKey = if (transformMap.contains(lang.wikiCode)) lang.wikiCode else "en"

     transformMap(mapKey).get(node.title.decoded).getOrElse(identity _)(node, lang)
  }
}
