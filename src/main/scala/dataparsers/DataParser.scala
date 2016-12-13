package dataparsers

import dataparsers.util.wikiparser.{Node, NodeUtil, PropertyNode}


/**
 * Extracts data from a node in the abstract syntax tree.
 * The type of the data which is extracted depends on the specific parser e.g. The IntegerParser extracts integers.
 */
abstract class DataParser
{

    def parse( node : Node ) : Option[Any]

    /**
      * Parser dependent splitting of nodes. Default is overridden by some parsers.
      */
    val splitPropertyNodeRegex = DataParserConfig.splitPropertyNodeRegex.get("en").get

    /**
      * (Split node and) return parse result.
      */
    def parsePropertyNode( propertyNode : PropertyNode, split : Boolean, transformCmd : String = null , transformFunc : String => String = identity ) : List[Any] =
    {
        if(split)
        {
            NodeUtil.splitPropertyNode(propertyNode, splitPropertyNodeRegex, transformCmd = transformCmd, transformFunc = transformFunc).flatMap( node => parse(node).toList )
        }
        else
        {
            parse(propertyNode).toList
        }
    }

}
