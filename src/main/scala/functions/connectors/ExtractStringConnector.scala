package functions.connectors

/**
  * Created by wmaroy on 22.04.17.
  */
class ExtractStringConnector(val property : String) extends FunctionConnector {

  /**
    * Creates the uri for making the function execute request
    *
    * @return
    */
  override protected def createUri(): String = {

    val firstPart = FunctionConnectionConfig.hostAddress + "/functions/extract-string?"

    val propertyAdded = addParam("property=" + encode(property), "")

    val secondPart = propertyAdded
    val uri = firstPart + secondPart

    uri
  }

}