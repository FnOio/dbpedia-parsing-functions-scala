package conditions

import functions.implementations.conditions.ContainsFunction
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 24.02.17.
  */
class ContainsFunctionTest extends FlatSpec with Matchers {

  "A ContainsFunction " should "return true if a property contains the value" in {

    val property = "{{ marriage|[[Melinda Gates]]|January 1, 1994 }}\n"
    val value = "Melinda Gates"

    val fn = new ContainsFunction(property, value)
    fn.execute() should be (true)

  }

  "A ContainsFunction " should "return true if a property contains the value 2" in {

    val property = "boards={{Ubl|[[Microsoft|Microsoft]] |[[Berkshire Hathaway|Berkshire Hathaway]]}} \n"
    val value = "Microsoft"

    val fn = new ContainsFunction(property, value)
    fn.execute() should be (true)

  }

  "A ContainsFunction " should "return false if a property does not contain the value" in {

    val property= "boards={{Ubl|[[Microsoft|Microsoft]] |[[Berkshire Hathaway|Berkshire Hathaway]] }} \n"
    val value = "test"

    val fn = new ContainsFunction(property, value)
    fn.execute() should be (false)

  }

  "A ContainsFunction " should "return false if a property is null" in {

    val property = null
    val value = "Microsoft"

    val fn = new ContainsFunction(property, value)
    fn.execute() should be (false)

  }

}
