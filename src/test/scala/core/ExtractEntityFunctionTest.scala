package core

import functions.implementations.core.ExtractEntityFunction
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 22.04.17.
  */
class ExtractEntityFunctionTest extends FlatSpec with Matchers {

  "An entity" should "be extracted correctly" in {

    val property = "<br />[[Melinda Gates|Melinda Gates]]<br />January 1, 1994<br />"
    val fn = new ExtractEntityFunction()
    val result = fn.execute(property)

    result.head should be("http://en.dbpedia.org/resource/Melinda_Gates")

  }

  "A list of entities " should "be extracted correctly" in {

    val property = "{{Ubl|[[Technical advisor|Technology Advisor]] of [[Microsoft|Microsoft]] |[[Chairman|Co-Chairman]] of the [[Bill & Melinda Gates Foundation|Bill & Melinda Gates Foundation]] |[[CEO|CEO]] of [[Cascade Investment|Cascade Investment]] |[[Chairman|Chairman]] of [[Branded Entertainment Network|Branded Entertainment Network]] |[[Chairman|Chairman]] of the [[TerraPower|TerraPower]] }}"

    val fn = new ExtractEntityFunction()
    val result = fn.execute(property)

    println(result)

  }

}