package dbpedia.dataparsers

import java.lang.StringBuilder

import dbpedia.dataparsers.util.{RdfNamespace, StringUtils}

import dbpedia.dataparsers.util.StringUtils.{escape, replacements}



class DBpediaNamespace(namespace: String) extends RdfNamespace(null, namespace, true) {
  
  override protected def append(sb: StringBuilder, suffix: String): Unit = {
    escape(sb, suffix, DBpediaNamespace.iriEscapes)
  }
}

object DBpediaNamespace {
  
  // for this list of characters, see RFC 3987 and https://sourceforge.net/mailarchive/message.php?msg_id=28982391
  private val iriEscapes = {
    val chars = ('\u0000' to '\u001F').mkString + "\"#%<>?[\\]^`{|}" + ('\u007F' to '\u009F').mkString
    val replace = replacements('%', chars)
    // don't escape space, replace it by underscore
    replace(' ') = "_"
    replace
  }

  private def ns(namespace: String): DBpediaNamespace = {
    new DBpediaNamespace(namespace)
  }
  
  val ONTOLOGY = ns("http://.org/ontology/")
  val DATATYPE = ns("http://.org/datatype/")

}
