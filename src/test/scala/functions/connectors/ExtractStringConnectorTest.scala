package functions.connectors

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 22.04.17.
  */
class ExtractStringConnectorTest extends FlatSpec with Matchers {

  "A correct query" should "be executed correctly" in {

    val connector = new ExtractStringConnector("[[Melinda Gates|Melinda Gates]]")
    val response = connector.execute()

  }

  "A correct query" should "return a result" in {

    val connector = new ExtractStringConnector("[[Melinda Gates|Melinda Gates]]")
    val response = connector.execute()

    response.size should not be 0
    response should not be null
    response.head should not be "null"

  }

  "An empty query " should "return an empty ListBuffer" in {

    val connector = new ExtractStringConnector(null)
    val response = connector.execute()

    response.size should be (0)
    response should not be null

  }

}