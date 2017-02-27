package functions.connectors

import java.net.URLEncoder

import play.api.libs.json.{JsArray, JsValue, Json}

/**
  * A FunctionConnector connects with an external server to execute a specific function that returns a JSON string.
  */
trait FunctionConnector {

  /**
    * Makes a connection with the configured external server and executes a function. This returns a JSON string.
    *
    * @return
    */
  def execute() : Seq[String] = {
    val uri = createUri()
    val result = scala.io.Source.fromURL(uri).mkString
    val parsedJson : JsValue = Json.parse(result)
    val list = parsedJson.asInstanceOf[JsArray].value
    val seq = list.map(_.toString().replaceAll("\"",""))
    val seqWithoutNull = seq.filterNot(_.equals("null"))
    seqWithoutNull
  }

  /**
    * Creates the uri for making the function execute request
    *
    * @return
    */
  protected def createUri() : String

  /**
    * Returns a query with the parameter added/not added
    *
    * @param param
    * @param query
    * @return
    */
  protected def addParam(param : String, query : String) : String = {
    val ampersand = if(query.length > 0) "&" else ""
    query + ampersand + param
  }

  protected def encode(s : String) : String = {
    if(s != null) {
      URLEncoder.encode(s, "UTF-8")
    } else "null"
  }

}
