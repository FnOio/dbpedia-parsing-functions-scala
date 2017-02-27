package functions.connectors

import java.net.URLEncoder

/**
  * Created by wmaroy on 23.02.17.
  */
class LonConnector(
  val coordinate: String,
  val singleCoordinate: String,
  val degrees: String,
  val minutes: String,
  val seconds: String,
  val direction: String
) extends FunctionConnector {
  /**
    * Creates the uri for making the function execute request
    *
    * @return
    */
  override protected def createUri(): String = {

    val firstPart = FunctionConnectionConfig.hostAddress + "/functions/lonFunction?"

    val coordinateAdded = addParam("coordinate=" + encode(coordinate), "")
    val singleCoordinateAdded = addParam("singleCoordinate=" + encode(singleCoordinate), coordinateAdded)
    val degreesAdded = addParam("degrees=" + encode(degrees), singleCoordinateAdded)
    val minutesAdded = addParam("minutes=" + encode(minutes), degreesAdded)
    val secondsAdded = addParam("seconds=" + encode(seconds), minutesAdded)
    val directionAdded = addParam("direction=" + encode(direction), secondsAdded)

    val secondPart = directionAdded

    firstPart + secondPart

  }
}
