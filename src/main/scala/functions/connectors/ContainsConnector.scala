package functions.connectors

/**
  * Created by wmaroy on 23.02.17.
  */
class ContainsConnector(val property: String, val value : String) extends FunctionConnector {
  /**
    * Creates the uri for making the function execute request
    *
    * @return
    */
  override protected def createUri(): String = {

    val firstPart = FunctionConnectionConfig.hostAddress + "/functions/containsFunction?"

    val propertyAdded = addParam("property=" + encode(property), "")
    val valueAdded = addParam("value=" + encode(value), propertyAdded)

    val secondPart = valueAdded
    val uri = firstPart + secondPart

    uri
  }
}
