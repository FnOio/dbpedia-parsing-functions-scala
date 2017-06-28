package date

import dbpedia.dataparsers.ontology.OntologySingleton
import functions.implementations.date.EndDateFunction
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 24.02.17.
  */
class EndDateFunctionTest extends FlatSpec with Matchers {

  OntologySingleton.load

  "An end date function " should "not be created with null parameters" in {

    val property = null
    val ontologyPropertyString = null

    an [NoSuchElementException] should be thrownBy new EndDateFunction(property, ontologyPropertyString)

  }

  "An end date function" should "return null if the present parameter is given" in {

    val property = "1975-present"
    val ontologyPropertyString = "activeYearsEndYear"

    val fn = new EndDateFunction(property, ontologyPropertyString)
    fn.execute() should be (null)

  }

  "An end date function" should "return the end date if a correct parameter is given" in {

    val property = "1975-1992"
    val ontologyPropertyString = "activeYearsEndYear"

    val fn = new EndDateFunction(property, ontologyPropertyString)
    fn.execute() should be ("1992")

  }

}
