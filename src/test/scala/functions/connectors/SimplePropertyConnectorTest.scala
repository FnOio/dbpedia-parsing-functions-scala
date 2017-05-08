package functions.connectors

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wmaroy on 23.02.17.
  */
class SimplePropertyConnectorTest extends FlatSpec with Matchers {


  "A connector" should "survive a stress test" in {
    val property = "birth_date={{Birth date and age|1955|10|28}}"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val ontologyProperty = "birthDate"
    val unit = null

    for(i <- 1 to 50) {
      val cn = new SimplePropertyConnector(property, select, prefix, suffix, transform, factor, ontologyProperty, unit)
      val result = cn.execute()
    }
  }

  "A date" should "be parsed correctly" in {

    val property = "birth_date={{Birth date and age|1955|10|28}}"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val ontologyProperty = "birthDate"
    val unit = null

    val cn = new SimplePropertyConnector(property, select, prefix, suffix,transform , factor, ontologyProperty, unit)
    val result = cn.execute()


    result.head should be ("1955-10-28")

  }



  "An areacode" should "be parsed correctly" in {

    val property = "area_code = 0241 / 02405 / 02407 / 02408"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "areaCode"
    val unit = null

    val cn = new SimplePropertyConnector(property, select, prefix, suffix,transform , factor, datatype, unit)
    val result = cn.execute()

    result.head should be ("0241 / 02405 / 02407 / 02408")

  }

  "A person" should "be parsed correctly" in {

    val property = "spouse=<br />[[Melinda Gates|Melinda Gates]]<br />January 1, 1994<br />"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "spouse"
    val unit = null

    val cn = new SimplePropertyConnector(property, select, prefix, suffix,transform , factor, datatype, unit)
    val result = cn.execute()

    result.head should be ("http://en.dbpedia.org/resource/Melinda_Gates")

  }

  "A currency" should "be parsed correctly" in {

    val property = "net_worth=US$85.2 billion (January 28th, 2017)"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "networth"
    val unit = null

    val cn = new SimplePropertyConnector(property, select, prefix, suffix,transform , factor, datatype, unit)
    val result = cn.execute()

    result.head should be ("8.52E10")

  }

  "A currency" should "be parsed correctly again" in {

    val property = "[[{{#property:p38}}]] ($)"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "Currency"
    val unit = null

    val cn = new SimplePropertyConnector(property, select, prefix, suffix,transform , factor, datatype, unit)
    val result = cn.execute()

    result.length should be (0)
  }

  "A string" should "be parsed correctly" in {

    val property = "William Henry Gates III"
    val select = null
    val prefix = null
    val suffix = null
    val transform = null
    val factor = 1.0
    val datatype =  "birthName"
    val unit = null

    val cn = new SimplePropertyConnector(property, select, prefix, suffix,transform , factor, datatype, unit)
    val result = cn.execute()

    result.head should be ("William Henry Gates III")

  }

}
