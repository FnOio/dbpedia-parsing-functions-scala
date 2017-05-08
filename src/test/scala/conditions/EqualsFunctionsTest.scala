package conditions

import functions.implementations.conditions.EqualsFunction
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 24.02.17.
  */
class EqualsFunctionsTest extends FlatSpec with Matchers {

  "An EqualsFunction" should "return true if the value is equal to the property" in {

    val property = "name=Bill Gates\n"
    val value="Bill Gates"

    val fn = new EqualsFunction(property, value)
    fn.execute() should be (true)

  }

}
