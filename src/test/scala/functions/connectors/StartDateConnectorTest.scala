package functions.connectors

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 28.02.17.
  */
class StartDateConnectorTest extends FlatSpec with Matchers {

  "A start date function " should "not be created with null parameters" in {

    val property = null
    val ontologyPropertyString = null

    val cn = new StartDateConnector(property, ontologyPropertyString)
    cn.execute() should be (Seq())

  }

  "A start date function" should "be parsed correctly with correct parameters" in {

    val property = "1975-present"
    val ontologyPropertyString = "activeYearsStartYear"

    val fn = new StartDateConnector(property, ontologyPropertyString)
    fn.execute() should be (Seq("1975"))

  }

  "A start date function" should "be parsed correctly with correct parameters 3" in {

    val property = "years_active=427 BC – 386 BC"
    val ontologyPropertyString = "activeYearsStartYear"

    val fn = new StartDateConnector(property, ontologyPropertyString)
    println(fn.execute().head)


  }

}
