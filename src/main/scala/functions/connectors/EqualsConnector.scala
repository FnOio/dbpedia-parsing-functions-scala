package functions.connectors

/**
  * Created by wmaroy on 28.02.17.
  */
class EqualsConnector(val property : String, val value : String) extends FunctionConnector {


  /**
    * Creates the uri for making the function execute request
    *
    * @return
    */
  override protected def createUri(): String = {

    val firstPart = FunctionConnectionConfig.hostAddress + "/functions/equalsFunction?"

    val propertyAdded = addParam("property=" + encode(property), "")
    val valueAdded = addParam("value=" + encode(value), propertyAdded)

    val secondPart = valueAdded
    val uri = firstPart+secondPart
    uri

  }

}
