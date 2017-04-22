package functions.connectors

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 28.02.17.
  */
class ContainsConnectorTest extends FlatSpec with Matchers {

  "A ContainsConnector " should "return true if a property contains the value" in {

    val property = "{{ marriage|[[Melinda Gates]]|January 1, 1994 }}\n"
    val value = "Melinda Gates"

    val fn = new ContainsConnector(property, value)
    fn.execute().head should be ("true")

  }

  "A ContainsConnector " should "return true if a property contains the value 2" in {

    val property = "boards={{Ubl|[[Microsoft|Microsoft]] |[[Berkshire Hathaway|Berkshire Hathaway]]}} \n"
    val value = "Microsoft"

    val fn = new ContainsConnector(property, value)
    fn.execute().head should be ("true")

  }

  "A ContainsConnector " should "return false if a property does not contain the value" in {

    val property= "boards={{Ubl|[[Microsoft|Microsoft]] |[[Berkshire Hathaway|Berkshire Hathaway]] }} \n"
    val value = "test"

    val fn = new ContainsConnector(property, value)
    fn.execute().head should be ("false")

  }

  "A ContainsConnector " should "return false if a property is null" in {

    val property = null
    val value = "Microsoft"

    val fn = new ContainsConnector(property, value)
    fn.execute().head should be ("false")

  }

}
