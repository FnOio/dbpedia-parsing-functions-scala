package functions.connectors

/**
  * Created by wmaroy on 23.02.17.
  */
class StartDateConnector(val property : String, val ontologyProperty : String) extends FunctionConnector {
  /**
    * Creates the uri for making the function execute request
    *
    * @return
    */
  override protected def createUri(): String = {

    val firstPart = FunctionConnectionConfig.hostAddress + "/functions/startDateFunction?"

    val propertyAdded = addParam("property=" + encode(property), "")
    val ontologyPropertyAdded = addParam("ontologyProperty=" + encode(ontologyProperty), propertyAdded)

    val secondPart = ontologyPropertyAdded

    val uri = firstPart + secondPart
    uri
  }
}
