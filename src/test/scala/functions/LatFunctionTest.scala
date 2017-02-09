package functions

import org.scalatest.{FlatSpec, Matchers}

/**
  * Tests for the LatFunction
  */
class LatFunctionTest extends FlatSpec with Matchers {

  "A latitude" should "be parsed correctly" in {

    val coordinate = null
    val latitude = null
    val degrees = "latd=33"
    val minutes = "latm=51"
    val seconds = "lats=54"
    val direction = null

    val fn = new LatFunction(null, latitude, degrees, minutes, seconds, direction)
    val result = fn.execute()

    result.head should be ("33.865")

  }

}
