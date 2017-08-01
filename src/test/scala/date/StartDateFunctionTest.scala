package date

import dbpedia.dataparsers.ontology.OntologySingleton
import functions.implementations.date.StartDateFunction
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 24.02.17.
  */
class StartDateFunctionTest extends FlatSpec with Matchers {

  //TODO more cases?

  OntologySingleton.load

  "A start date function " should "not be created with null parameters" in {

    val property = null
    val ontologyPropertyString = null
    val language = "en"

    an [NoSuchElementException] should be thrownBy new StartDateFunction(property, ontologyPropertyString, language)

  }

  "A start date function" should "be parsed correctly with correct parameters" in {

    val property = "1975-present"
    val ontologyPropertyString = "activeYearsStartYear"
    val language = "en"

    val fn = new StartDateFunction(property, ontologyPropertyString, language)
    fn.execute() should be ("1975")
  }

  "A start date function" should "be parsed correctly with correct parameters 2" in {

    val property = "3 months<br />{{small|(April 21, 1832 – July 10, 1832)}}"
    val ontologyPropertyString = "serviceStartYear"
    val language = "en"

    val fn = new StartDateFunction(property, ontologyPropertyString, language)
    fn.execute() should be ("0003")

  }

  "A start date function" should "be parsed correctly with correct parameters 3" in {

    val property = "years_active=427 BC – 386 BC"
    val ontologyPropertyString = "activeYearsStartYear"
    val language = "en"

    val fn = new StartDateFunction(property, ontologyPropertyString, language)
    println(fn.execute())

  }

}
