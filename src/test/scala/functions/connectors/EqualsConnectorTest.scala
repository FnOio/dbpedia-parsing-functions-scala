package functions.connectors

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 28.02.17.
  */
class EqualsConnectorTest extends FlatSpec with Matchers {

  "An EqualsFunction" should "return true if the value is equal to the property" in {

    val property = "name=Bill Gates\n"
    val value="Bill Gates"

    val fn = new EqualsConnector(property, value)
    fn.execute().head should be ("true")

  }

}
