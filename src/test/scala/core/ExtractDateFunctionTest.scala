package core

import functions.implementations.core.ExtractDateFunction
import org.scalatest.{FlatSpec, Matchers}


/**
  * Created by wmaroy on 22.04.17.
  */
class ExtractDateFunctionTest extends FlatSpec with Matchers {

  "A date" should "be extracted correctly" in {

    val property = "birth_date={{Birth date and age|1955|10|28}}"
    val fn = new ExtractDateFunction()
    val result = fn.execute(property, "xsd:date")

    result.head should be ("1955-10-28")

  }

}
