package functions

import org.scalatest.{FlatSpec, Matchers}


/**
  * Tests for the SimplePropertyFunction
  */
class SimplePropertyFunctionTest extends FlatSpec with Matchers {

  "A date" should "be parsed correctly" in {

    val property = "birth_date={{Birth date and age|1955|10|28}}"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val ontologyProperty = "birthDate"
    val unit = null

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, ontologyProperty, unit)
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

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit)
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

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit)
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

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit)
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

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit)
    val result = fn.execute()

    result.head should be ("William Henry Gates III")

  }



}
