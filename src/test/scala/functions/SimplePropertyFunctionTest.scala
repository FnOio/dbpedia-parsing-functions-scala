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
    val datatype =  "xsd:date"
    val unit = null

    val fn = new SimplePropertyFunction(property, select, prefix, suffix,transform , factor, datatype, unit)
    val result = fn.execute()

    result.head should be ("1955-10-28")

  }

}
