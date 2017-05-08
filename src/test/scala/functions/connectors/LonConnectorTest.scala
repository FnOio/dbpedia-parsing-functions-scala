package functions.connectors

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 23.02.17.
  */
class LonConnectorTest extends FlatSpec with Matchers {

  val coordinate = null
  val latitude = null
  val degrees = "latd=33"
  val minutes = "latm=51"
  val seconds = "lats=54"
  val direction = null

  "A correct query" should "be executed correctly" in {

    val connector = new LonConnector(coordinate, latitude, degrees, minutes, seconds, direction)
    val response = connector.execute()

  }

  "A correct query" should "return a result" in {

    val connector = new LonConnector(coordinate, latitude, degrees, minutes, seconds, direction)
    val response = connector.execute()

    response.size should not be 0
    response should not be null
    response.head should not be "null"

  }

  "An empty query " should "return an empty ListBuffer" in {

    val connector = new LonConnector(null,null,null,null,null,null)
    val response = connector.execute()

    response.size should be (0)
    response should not be null

  }

}
