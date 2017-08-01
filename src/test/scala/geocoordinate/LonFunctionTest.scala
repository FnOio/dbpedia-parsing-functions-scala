package geocoordinate

import dbpedia.dataparsers.ontology.OntologySingleton
import functions.implementations.geocoordinate.LonFunction
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 17.02.17.
  */
class LonFunctionTest extends FlatSpec with Matchers {

  //TODO more cases?

  OntologySingleton.load

  "A longitude" should "be parsed correctly" in {

    val coordinate = null
    val longitude = null
    val degrees = "lond=33"
    val minutes = "lonm=51"
    val seconds = "lons=54"
    val direction = null
    val language = "en"

    val fn = new LonFunction(null, longitude, degrees, minutes, seconds, direction, language)
    val result = fn.execute()

    result.head should be("33.865")

  }

  "A longitude" should "be parsed correctly again" in {

    val coordinate = null
    val longitude = null
    val degrees = null
    val minutes = "lonm=51"
    val seconds = "lons=54"
    val direction = null
    val language = "en"

    val fn = new LonFunction(null, longitude, degrees, minutes, seconds, direction, language)
    val result = fn.execute()

    result.head should be(null)

  }

}