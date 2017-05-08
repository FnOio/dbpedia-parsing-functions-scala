package functions.connectors

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 22.04.17.
  */
class ExtractEntityConnectorTest extends FlatSpec with Matchers {

  "A correct query" should "be executed correctly" in {

    val connector = new ExtractEntityConnector("<br />[[Melinda Gates|Melinda Gates]]<br />January 1, 1994<br />")
    val response = connector.execute()

  }

  "A correct query" should "return a result" in {

    val connector = new ExtractEntityConnector("<br />[[Melinda Gates|Melinda Gates]]<br />January 1, 1994<br />")
    val response = connector.execute()

    response.size should not be 0
    response should not be null
    response.head should not be "null"



  }

  "An empty query " should "return an empty ListBuffer" in {

    val connector = new ExtractEntityConnector(null)
    val response = connector.execute()

    response.size should be (0)
    response should not be null

  }

  "A place " should "be executed correctly" in {

    val connector = new ExtractEntityConnector("[[Seattle, Washington|Seattle, Washington]], U.S")
    val response = connector.execute()

    response.size should not be 0
    response should not be null
    response.head should not be "null"

  }

  "An enumartion of entities " should "be executed correctly" in {

    val property = "{{Ubl|[[Technical advisor|Technology Advisor]] of [[Microsoft|Microsoft]] |[[Chairman|Co-Chairman]] of the [[Bill & Melinda Gates Foundation|Bill & Melinda Gates Foundation]] |[[CEO|CEO]] of [[Cascade Investment|Cascade Investment]] |[[Chairman|Chairman]] of [[Branded Entertainment Network|Branded Entertainment Network]] |[[Chairman|Chairman]] of the [[TerraPower|TerraPower]] }}"

    val connector = new ExtractEntityConnector(property)
    val response = connector.execute()

    response.size should not be 0
    response should not be null
    response.head should not be "null"
  }

}
