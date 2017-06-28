package functions.connectors

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 28.02.17.
  */
class IsSetConnectorTest extends FlatSpec with Matchers {

    "IsSetFunction " should "return false if not set" in {

      val property = "null"

      val fn = new IsSetConnector(property)
      fn.execute().head should be ("false")

    }

    "IsSetFunction " should "return true if set" in {

      val property = "test"

      val fn = new IsSetConnector(property)
      fn.execute().head should be ("true")

    }


}
