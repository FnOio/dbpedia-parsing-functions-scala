package functions.connectors

/**
  * Created by wmaroy on 23.02.17.
  */
class IsSetConnector(val property : String) extends FunctionConnector {
  /**
    * Creates the uri for making the function execute request
    *
    * @return
    */
  override protected def createUri(): String = {

    val firstPart = FunctionConnectionConfig.hostAddress + "/functions/isSetFunction?"

    val propertyAdded = addParam("property=" + encode(property), "")
    val secondPart = propertyAdded

    val uri = firstPart + secondPart
    uri
  }
}
