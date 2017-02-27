package functions.connectors

import java.net.URLEncoder

import play.api.libs.json.{JsArray, JsValue, Json}

/**
  * Created by wmaroy on 21.02.17.
  */
class SimplePropertyConnector(
   val property : String,
   val select : String,
   val prefix : String,
   val suffix : String,
   val transform : String,
   val factor : Double,
   val datatype : String,
   val unit : String) extends FunctionConnector {

    protected def createUri(): String = {

      val firstPart = FunctionConnectionConfig.hostAddress + "/functions/simpleProperty?"

      var secondPart = ""

      if(property != null) {

        secondPart = "property=" + URLEncoder.encode(property, "UTF-8")

        if(select != null) {
          secondPart += "&select=" + URLEncoder.encode(select, "UTF-8")
        }

        if(prefix != null) {
          secondPart += "&prefix=" + URLEncoder.encode(prefix, "UTF-8")
        }

        if(suffix != null) {
          secondPart += "&suffix=" + URLEncoder.encode(suffix, "UTF-8")
        }

        if(transform != null) {
          secondPart += "&transform=" + URLEncoder.encode(transform, "UTF-8")
        }

        secondPart += "&factor=" + 1

        if(datatype != null) {
          secondPart += "&datatype=" + URLEncoder.encode(datatype,"UTF-8")
        }

        if(unit != null) {
          secondPart += "&unit=" + URLEncoder.encode(unit, "UTF-8")
        }

      }

      firstPart + secondPart

    }


}
