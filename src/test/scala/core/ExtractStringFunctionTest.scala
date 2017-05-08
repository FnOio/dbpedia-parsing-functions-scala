package core

import functions.implementations.core.ExtractStringFunction
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 22.04.17.
  */
class ExtractStringFunctionTest extends FlatSpec with Matchers {

  "A string" should "be extracted correctly" in {

    val property = "<br />[[Melinda Gates|Melinda Gates]]<br /><br />"
    val fn = new ExtractStringFunction()
    val result = fn.execute(property)

    result.head should be("Melinda Gates")

  }

  "A list of strings " should "be extracted correctly" in {

    val property = "{{Ubl|[[Technical advisor|Technology Advisor]] of [[Microsoft|Microsoft]] |[[Chairman|Co-Chairman]] of the [[Bill & Melinda Gates Foundation|Bill & Melinda Gates Foundation]] |[[CEO|CEO]] of [[Cascade Investment|Cascade Investment]] |[[Chairman|Chairman]] of [[Branded Entertainment Network|Branded Entertainment Network]] |[[Chairman|Chairman]] of the [[TerraPower|TerraPower]] }}"

    val fn = new ExtractStringFunction()
    val result = fn.execute(property)

    println(result)

  }

}
