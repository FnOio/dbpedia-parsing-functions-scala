package functions.connectors

/**
  * Created by wmaroy on 22.04.17.
  */
class ExtractDateConnector(val property : String, val dataDatatype : String) extends FunctionConnector{

  /**
    * Creates the uri for making the function execute request
    *
    * @return
    */
  override protected def createUri(): String = {

    val firstPart = FunctionConnectionConfig.hostAddress + "/functions/extract-date?"

    val propertyAdded = addParam("property=" + encode(property), "")
    val valueAdded = addParam("datedatatype=" + encode(dataDatatype), propertyAdded)

    val secondPart = valueAdded
    val uri = firstPart + secondPart

    uri
  }

}
