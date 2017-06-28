package conditions

import functions.implementations.conditions.IsSetFunction
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 24.02.17.
  */
class IsSetFunctionTest extends FlatSpec with Matchers {

  "IsSetFunction " should "return false if not set" in {

    val property = "null"

    val fn = new IsSetFunction(property)
    fn.execute() should be (false)

  }

  "IsSetFunction " should "return true if set" in {

    val property = "test"

    val fn = new IsSetFunction(property)
    fn.execute() should be (true)

  }


}
