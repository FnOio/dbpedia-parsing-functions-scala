package dbpedia.dataparsers

import dbpedia.dataparsers.util.Language
import dbpedia.dataparsers.util.wikiparser._
import scala.language.reflectiveCalls

/**
 * Parses links to other instances.
 */

class ObjectParser(context : { def language : Language }, val strict : Boolean = false) extends DataParser
{

    //TODO wmaroy: add page node or not? see original ObjectParser

    private val flagTemplateParser = new FlagTemplateParser(context)

    private val language = context.language.wikiCode

    override val splitPropertyNodeRegex = if (DataParserConfig.splitPropertyNodeRegexObject.contains(language)) DataParserConfig.splitPropertyNodeRegexObject.get(language).get
                                          else DataParserConfig.splitPropertyNodeRegexObject.get("en").get
    // the Template {{·}} would also be nice, but is not that easy as the regex splits

    override def parsePropertyNode( propertyNode : PropertyNode, split : Boolean, transformCmd : String = null, transformFunc : String => String = identity ) =
    {
        if(split)
        {
            NodeUtil.splitPropertyNode(propertyNode, splitPropertyNodeRegex, trimResults = true, transformCmd = transformCmd, transformFunc = transformFunc).flatMap( node => parse(node).toList )
        }
        else
        {
            parse(propertyNode).toList
        }
    }

    override def parse(node : Node) : Option[String] =
    {

        if (! strict)
        {
            for (child <- node :: node.children) child match
            {
                //ordinary links
                case InternalLinkNode(destination, _, _, _) if destination.namespace == Namespace.Main =>
                {
                    return Some(getUri(destination))
                }

                case ExternalLinkNode(destination, _, _, _) =>
                {
                    return Some(destination.toString)
                }

                //resolve templates to create links
                case templateNode : TemplateNode if(node.children.length == 1) => resolveTemplate(templateNode) match
                {
                    case Some(destination) =>  // should always be Main namespace
                    {
                        return Some(context.language.resourceUri.append(destination.decodedWithNamespace))
                    }
                    case None =>
                }

                case _ =>
            }
        }
        else
        {
            node match
            {
                case InternalLinkNode(destination, _, _, _) => return Some(getUri(destination))
                case _ =>
                {
                    node.children match
                    {
                        case InternalLinkNode(destination, _, _, _) :: Nil => return Some(getUri(destination))
                        case InternalLinkNode(destination, _, _, _) :: TextNode(text, _) :: Nil if text.trim.isEmpty => return Some(getUri(destination))
                        case TextNode(text, _) :: InternalLinkNode(destination, _, _, _) :: Nil if text.trim.isEmpty => return Some(getUri(destination))
                        case TextNode(text1, _) ::InternalLinkNode(destination, _, _, _) :: TextNode(text2, _) :: Nil if (text1.trim.isEmpty && text2.trim.isEmpty) => return Some(getUri(destination))
                        case _ => return None
                    }
                }
            }
        }
        None
    }

    /**
     * Searches on the wiki page for a link with the same name as surfaceForm and returns the destination if one is found.
     */
    private def getAdditionalWikiTitle(surfaceForm : String, pageNode : PageNode) : Option[WikiTitle] =
    {
        surfaceForm.trim.toLowerCase(context.language.locale) match
        {
            case "" => None
            case sf: String => getTitleForSurfaceForm(sf, pageNode)
        }
    }

    private def getTitleForSurfaceForm(surfaceForm : String, node : Node) : Option[WikiTitle] =
    {
        node match
        {
            case linkNode : InternalLinkNode =>
            {
                // TODO: Here we match the link label. Should we also match the link target?
                val linkText = linkNode.children.collect{case TextNode(text, _) => text}.mkString("")
                if(linkText.toLowerCase(context.language.locale) == surfaceForm)
                {
                    return Some(linkNode.destination)
                }
            }
            case _ =>
        }

        for(child <- node.children)
        {
            getTitleForSurfaceForm(surfaceForm, child) match
            {
                case Some(destination) => return Some(destination)
                case None =>
            }
        }

        None
    }

    private def resolveTemplate(templateNode : TemplateNode) : Option[WikiTitle] =
    {
        flagTemplateParser.parse(templateNode).foreach(destination => return Some(destination))
        None
    }

    private def getUri(destination : WikiTitle) : String =
    {
        // prepend page title to the URI if the destination links to a subsection on this page, e.g. starring = #Cast
        // FIXME: how do we want to treat fragment URIs? Currently, "#" is percent-encoded as "%23"
        val uriSuffix = destination.decodedWithNamespace
        // TODO this is not the final solution:
        // parsing the list of items in the subsection would be better, but the signature of parse would have to change to return a List

        destination.language.resourceUri.append(uriSuffix)
    }
}
