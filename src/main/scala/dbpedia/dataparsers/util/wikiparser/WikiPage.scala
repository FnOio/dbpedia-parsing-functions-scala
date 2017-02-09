package dbpedia.dataparsers.util.wikiparser

import dbpedia.dataparsers.util.StringUtils._
import dbpedia.dataparsers.util.wikiparser.WikiPage._

/**
 * Represents a wiki page
 *
 * TODO: use redirect id to check redirect extractor. Or get rid of redirect extractor.
 * 
 * @param title The title of this page
 * @param id The page ID
 * @param revision The revision of this page
 * @param timestamp The timestamp of the revision, in milliseconds since 1970-01-01 00:00:00 UTC
 * @param contributorID The ID of the latest contributor
 * @param contributorName The name of the latest contributor
 * @param source The WikiText source of this page
 * @param format e.g. "text/x-wiki"
 */
class WikiPage(val title: WikiTitle, val redirect: WikiTitle, val id: Long, val revision: Long, val timestamp: Long,
               val contributorID: Long, val contributorName: String, val source : String, val format: String)
{

    def this(title: WikiTitle, redirect: WikiTitle, id: String, revision: String, timestamp: String, contributorID: String, contributorName: String, source : String, format: String) =
      this(title, redirect, parseLong(id), parseLong(revision), parseTimestamp(timestamp), parseLong(contributorID), contributorName, source, format)

    def this(title: WikiTitle, source : String) =
      this(title, null, -1, -1, -1, 0, "", source, "")
    
     def this(title: WikiTitle, redirect: WikiTitle, id: Long, revision: Long, timestamp: Long, source: String) = 
      this(title, redirect, id, revision, timestamp, 0, "", source, "")
      
    override def toString = "WikiPage(" + title + "," + id + "," + revision + "," + contributorID + "," + contributorName + "," + source + "," + format + ")"
    
    /**
     * Serializes this page to XML using the MediaWiki export format.
     * The MediaWiki export format is specified at http://www.mediawiki.org/xml/export-0.8.
     */

    def sourceUri : String = title.pageIri + (if (revision >= 0) "?oldid=" + revision else "")
}

object WikiPage {
  
  def parseInt(str: String): Int = {
    if (str == null || str.isEmpty) -1
    else str.toInt
  }
  
  def formatInt(id: Int): String = {
    if (id < 0) ""
    else id.toString
  }
  
  def parseLong(str: String): Long = {
    if (str == null || str.isEmpty) -1
    else str.toLong
  }
  
  def formatLong(id: Long): String = {
    if (id < 0) ""
    else id.toString
  }

}
