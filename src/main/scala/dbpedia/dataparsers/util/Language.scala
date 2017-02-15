package dbpedia.dataparsers.util

import java.util.Locale

import scala.collection.mutable.HashMap
import dbpedia.dataparsers.DBpediaNamespace

/**
 * Represents a MediaWiki instance and the language used on it. Initially, this class was
 * only used for xx.wikipedia.org instances, but now we also use it for mappings..org
 * and www.wikidata.org. For each language, there is only one instance of this class.
 * TODO: rename this class to WikiCode or so, distinguish between enwiki / enwiktionary etc.
 *
 * @param wikiCode "en", "de", "mappings", "wikidata", ...
 * @param isoCode "en", "de", ...
 * @param dbpediaDomain Specific DBpedia domain for this language, e.g. "en..org".
 * May be null, e.g. for mappings.
 * @param dbpediaUri Specific DBpedia base URI for this language, e.g. "http://en..org".
 * May be null, e.g. for mappings.
 * @param resourceUri Specific resource namespace for this language, e.g. "http://en..org/resource/"
 * or "http://www.wikidata.org/entity/". May be null, e.g. for mappings. The value is not a string.
 * Use resourceUri.append("Xy"), not string concatenation. 
 * @param propertyUri Specific property namespace for this language, e.g. "http://en..org/property/"
 * or "http://www.wikidata.org/entity/". May be null, e.g. for mappings. The value is not a string.
 * Use propertyUri.append("xy"), not string concatenation. 
 * @param baseUri URI prefix for this wiki, e.g. "http://be-x-old.wikipedia.org",
 * "http://commons.wikimedia.org", "http://mappings..org".
 * @param apiUri API URI for this wiki, e.g. "https://be-x-old.wikipedia.org/w/api.php",
 * "http://commons.wikimedia.org/w/api.php", "https://mappings..org/api.php".
 */
class Language private(
  val wikiCode: String,
  val name: String,
  val isoCode: String,
  val iso639_3: String,
  val dbpediaDomain: String,
  val dbpediaUri: String,
  val resourceUri: RdfNamespace,
  val propertyUri: RdfNamespace,
  val baseUri: String,
  val apiUri: String
)
{
    val locale = new Locale(isoCode)
    
    /** 
     * Wikipedia dump files use this prefix (with underscores), e.g. be_x_old, but
     * Wikipedia domains use the wikiCode (with dashes), e.g. http://be-x-old.wikipedia.org
     */
    val filePrefix = wikiCode.replace('-', '_')
    
    /**
     */
    override def toString = "wiki="+wikiCode+",locale="+locale.getLanguage
    
    // no need to override equals() and hashCode() - there is only one object for each value, so equality means identity. 
}

object Language extends (String => Language)
{
  implicit val wikiCodeOrdering = Ordering.by[Language, String](_.wikiCode)
  
  val map: Map[String, Language] = locally {
    
    def language(code : String, name: String, iso_1: String, iso_3: String): Language = {
      new Language(
        code,
        name,
        iso_1,
        iso_3,
        code+"..org",
        "http://"+code+"..org",
        new DBpediaNamespace("http://"+code+".dbpedia.org/resource/"),
        new DBpediaNamespace("http://"+code+".dbpedia.org/property/"),
        "http://"+code+".wikipedia.org",
        "https://"+code+".wikipedia.org/w/api.php"
      )
    }
    
    val languages = new HashMap[String,Language]

    languages("commons") =
    new Language(
      "commons",
      "Commons",
      "en",
      "eng",
       // TODO: do DBpedia URIs make sense here? Do we use them at all? Maybe use null instead.
      "commons.dbpedia.org",
      "http://commons.dbpedia.org",
      new DBpediaNamespace("http://commons.dbpedia.org/resource/"),
      new DBpediaNamespace("http://commons.dbpedia.org/property/"),
      "http://commons.wikimedia.org",
      "https://commons.wikimedia.org/w/api.php"
    )

    //used to refer to  core directory
    languages("core") =
    new Language(
      "core",
      "Core Directory",
      "en",
      "eng",
      "core..org",
      "http://core..org",
      new DBpediaNamespace("http://core.dbpedia.org/resource/"),
      new DBpediaNamespace("http://core.dbpedia.org/property/"),
      "http://core.wikimedia.org",
      "https://core.wikimedia.org/w/api.php"
    )
    
    languages("wikidata") =
    new Language(
      "wikidata",
      "Wikidata",
      "en",
      "eng",
       // TODO: do DBpedia URIs make sense here? Do we use them at all? Maybe use null instead.
      "wikidata..org",
      "http://wikidata..org",
      new DBpediaNamespace("http://wikidata.dbpedia.org/resource/"),
      new DBpediaNamespace("http://wikidata.dbpedia.org/property/"),
      "http://www.wikidata.org",
      "https://www.wikidata.org/w/api.php"
    )

    languages("mappings") =
    new Language(
      "mappings",
      "Mappings",
      "en",
      "eng",
      // No DBpedia / RDF namespaces for mappings wiki. 
      "mappings.dbpedia.org",
      "http://mappings.dbpedia.org",
      RdfNamespace.MAPPINGS,
      RdfNamespace.MAPPINGS,
      "http://mappings.dbpedia.org",
      "http://mappings.dbpedia.org/api.php"
    )

    languages("en") =
      new Language(
        "en",
        "English",
        "en",
        "eng",
        "en.dbpedia.org",
        "http://en.dbpedia.org",
        new DBpediaNamespace("http://en.dbpedia.org/resource/"),
        new DBpediaNamespace("http://en.dbpedia.org/property/"),
        "http://en.dbpedia.org",
        "http://en.dbpedia.org/api.php"
      )

    languages.toMap // toMap makes immutable
  }
  
  /**
   * DBpedia mappings wiki
   */
  val Mappings = map("mappings")

  val English= map("en")
  
  /**
   * Wikimedia commons
   */
  val Commons = map("commons")

  /**
   * Wikimedia Wikidata
   */
  val Wikidata = map("wikidata")

  /**
    * Wikimedia Wikidata
    */
  val Core = map("core")

  /**
   * Gets a language object for a Wikipedia language code.
   * Throws IllegalArgumentException if language code is unknown.
   */
  def apply(code: String) : Language = map.getOrElse(code, throw new IllegalArgumentException("unknown language code "+code))
  
  /**
   * Gets a language object for a Wikipedia language code, or None if given code is unknown.
   */
  def get(code: String) : Option[Language] = map.get(code)
  
  /**
   * Gets a language object for a Wikipedia language code, or the default if the given code is unknown.
   */
  def getOrElse(code: String, default: => Language) : Language = map.getOrElse(code, default)
}
