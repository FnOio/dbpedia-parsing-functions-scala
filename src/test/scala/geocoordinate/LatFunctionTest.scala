package geocoordinate

import dbpedia.dataparsers.ontology.OntologySingleton
import functions.implementations.geocoordinate.LatFunction
import org.scalatest.{FlatSpec, Matchers}

/**
  * Tests for the LatFunction
  */
class LatFunctionTest extends FlatSpec with Matchers {

  //TODO more cases?

  OntologySingleton.load

  "A latitude" should "be parsed correctly" in {

    val coordinate = null
    val latitude = null
    val degrees = "latd=33"
    val minutes = "latm=51"
    val seconds = "lats=54"
    val direction = null
    val language = "en"

    val fn = new LatFunction(null, latitude, degrees, minutes, seconds, direction, language)
    val result = fn.execute()

    result.head should be ("33.865")

  }

  "A latitude" should "be parsed correctly again" in {

    val coordinate = null
    val latitude = null
    val degrees = null
    val minutes = "latm=51"
    val seconds = "lats=54"
    val direction = null
    val language = "en"

    val fn = new LatFunction(null, latitude, degrees, minutes, seconds, direction, language)
    val result = fn.execute()

    result.head should be (null)

  }

}
