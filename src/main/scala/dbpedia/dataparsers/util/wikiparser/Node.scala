package dbpedia.dataparsers.util.wikiparser

import java.lang.StringBuilder

import scala.collection.mutable.HashMap

/**
 * Base class of all nodes in the abstract syntax tree.
 * 
 * This class is NOT thread-safe.
 */
abstract class Node(val children : List[Node], val line : Int)
{
    /**
     * CAUTION: code outside this class should change the parent only under very rare circumstances.
     */
    var parent: Node = null
    
    //Set the parent of all children
    for(child <- children) child.parent = this

    /**
     * Convert back to original (or equivalent) wiki markup string.
     */
    def toWikiText: String

    /**
     * Get plain text content of this node and all child nodes, without markup. Since templates
     * are not expanded, this will not work well for templates.
     */
    def toPlainText: String

    /**
     *  Retrieves the root node of this AST.
     *
     * @return The root Node
     */
    lazy val root : PageNode =
    {
        var node = this
        
        while(node.parent != null)
        {
            node = node.parent;
        }
        
        node.asInstanceOf[PageNode];
    }
   

    /**
     * Retrieves some text from this node. Only works on a TextNode or a Node that has 
     * a single TextNode child. Returns None iff this node is not a TextNode and contains 
     * child nodes other than a single TextNode.
     * 
     * TODO: the behavior of this method is weird, but I don't dare to change it because existing
     * code may rely on its current behavior. New code should probably use toPlainText.
     */
    final def retrieveText: Option[String] = retrieveText(true)

    protected def retrieveText(recurse: Boolean): Option[String] = {
      if (recurse && children.length == 1) children(0).retrieveText(false) else None
    }


}

object Node {

}
