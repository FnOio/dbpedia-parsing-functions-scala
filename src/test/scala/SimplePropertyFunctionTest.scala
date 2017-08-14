import dbpedia.dataparsers.ontology.OntologySingleton
import functions.implementations.SimplePropertyFunction
import org.scalatest.{FlatSpec, Matchers}


/**
  * Tests for the SimplePropertyFunction
  */
class SimplePropertyFunctionTest extends FlatSpec with Matchers {

  //TODO more cases?

  OntologySingleton.load

  "A date" should "be parsed correctly" in {

    val property = "birth_date={{Birth date and age|1955|10|28}}"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val ontologyProperty = "birthDate"
    val unit = null
    val language = "en"

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, ontologyProperty, unit, language)
    val result = fn.execute()

    result.head should be ("1955-10-28")

  }

  "A person" should "be parsed correctly" in {

    val property = "spouse=<br />[[Melinda Gates|Melinda Gates]]<br />January 1, 1994<br />"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "spouse"
    val unit = null
    val language = "en"

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit, language)
    val result = fn.execute()

    result.head should be ("http://en.dbpedia.org/resource/Melinda_Gates")

  }

  "A currency" should "be parsed correctly" in {

    val property = "net_worth=US$85.2 billion (January 28th, 2017)"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "networth"
    val unit = null
    val language = "en"

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit, language)
    val result = fn.execute()

    result.head should be ("8.52E10")

  }

  "A currency" should "be parsed correctly again" in {

    val property = "[[{{#property:p38}}]] ($)"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "Currency"
    val unit = null
    val language = "en"

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit, language)
    val result = fn.execute()

    result.length should be (0)
  }

  "A string" should "be parsed correctly" in {

    val property = "William Henry Gates III"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "birthName"
    val unit = null
    val language = "en"

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit, language)
    val result = fn.execute()

    result.head should be ("William Henry Gates III")

  }

  "Programming languages" should "be parsed correctly" in {
    val property = "prog_language=[[Java (programming language)|Java]], [[C (programming language)|C]], [[C++]], [[COBOL]], [[PL/I]], [[HLASM]], [[FORTRAN]], [[REXX]], and many others"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "programmingLanguage"
    val unit = null
    val language = "en"

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit, language)
    val result = fn.execute()

    result.head should be ("http://en.dbpedia.org/resource/Java_(programming_language)")

  }

  "Programming languages" should "be parsed correctly in another language" in {
    val property = "prog_language=[[Java (programming language)|Java]], [[C (programming language)|C]], [[C++]], [[COBOL]], [[PL/I]], [[HLASM]], [[FORTRAN]], [[REXX]], and many others"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "programmingLanguage"
    val unit = null
    val language = "nl"

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit, language)
    val result = fn.execute()

    result.head should be ("http://nl.dbpedia.org/resource/Java_(programming_language)")

  }

  "Titles " should "be extracted" in {

    val property = "{{Ubl|[[Technical advisor|Technology Advisor]] of [[Microsoft|Microsoft]]|[[Chairman|Co-Chairman]] of the [[Bill & Melinda Gates Foundation|Bill & Melinda Gates Foundation]]|[[CEO|CEO]] of [[Cascade Investment|Cascade Investment]]|[[Chairman|Chairman]] of [[Branded Entertainment Network|Branded Entertainment Network]]|[[Chairman|Chairman]] of [[TerraPower|TerraPower]]}}"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "title"
    val unit = null
    val language = "en"

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit, language)
    val result = fn.execute()

    result.size should be > 0

  }

  "Parents " should "be extracted" in {

    val property = "{{Ubl|[[William H. Gates Sr.|William H. Gates Sr.]]|[[Mary Maxwell Gates|Mary Maxwell Gates]]}}"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "parent"
    val unit = null
    val language = "en"

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit, language)
    val result = fn.execute()

    result.size should be > 0

  }

  "Netto value" should "be extracted as date" in {

    val property = "[[US$|US$]]89.9 billion (27 July 2017)"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "birthDate"
    val unit = null
    val language = "en"

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit, language)
    val result = fn.execute()
    result.length should be (1)


  }

  "Homepage " should "be extracted" in {

    val property = "[http://gatesnotes.com ]"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "http://xmlns.com/foaf/0.1/homepage"
    val unit = null
    val language = "en"

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit, language)
    val result = fn.execute()
    result.length should be (1)

  }

  "A state" should "be parsed correctly" in {
    val property = "state1=Sichuan"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "state"
    val unit = null
    val language = "en"

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit, language)
    val result = fn.execute()

    result.length should be (0) // fix this!

  }



}
