package functions.connectors

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 28.02.17.
  */
class EndDateConnectorTest extends FlatSpec with Matchers {

  "An end date connector " should "not be created with null parameters" in {

    val property = null
    val ontologyPropertyString = null

    val cn = new EndDateConnector(property, ontologyPropertyString)
    cn.execute() should be (Seq())

  }

  "An end date connector" should "return null if the present parameter is given" in {

    val property = "1975-present"
    val ontologyPropertyString = "activeYearsEndYear"

    val fn = new EndDateConnector(property, ontologyPropertyString)
    fn.execute() should be (Seq())

  }

  "An end date function" should "return the end date if a correct parameter is given" in {

    val property = "1975-1992"
    val ontologyPropertyString = "activeYearsEndYear"

    val fn = new EndDateConnector(property, ontologyPropertyString)
    fn.execute() should be (Seq("1992"))

  }

}
