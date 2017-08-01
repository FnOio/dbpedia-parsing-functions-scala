package functions.implementations.geocoordinate

import dbpedia.dataparsers.coordinate.Longitude
import dbpedia.dataparsers.util.wikiparser.PropertyNode

/**
  * Created by wmaroy on 07.02.17.
  */
class LonFunction(
  override val coordinate: String,
  override val singleCoordinate: String,
  override val degrees: String,
  override val minutes: String,
  override val seconds: String,
  override val direction: String,
  override val language: String)
  extends GeoFunction(coordinate, singleCoordinate, degrees, minutes, seconds, direction, language) {

  /**
    * Returns an array filled with a string that represents the latitude
    * Input is a property node with the coordinate value, latitude will be parsed from this
    *
    * @param coordinate
    * @return
    */
  protected def calculateCoordinate(coordinate: PropertyNode): Seq[String] = {
    val geoCoordinate = geoCoordinateParser.parse(coordinate)
    Array(geoCoordinate.get.longitude.toString)
  }


  /**
    * Return an array filled with a string that represents the latitude
    *
    * @param degrees
    * @param minutes
    * @param seconds
    * @param direction
    * @return
    */
  protected def calculateCoordinate(degrees: Double, minutes: Double, seconds: Double, direction: String): Seq[String] = {
    degrees match {

      //do not allow degrees that are 0.0, this means that the degree param has not been set
      case 0.0 => Array(null)

      case other =>
        val longitude = new Longitude(degrees, minutes, seconds, direction)
        Array(longitude.toDouble.toString)

    }
  }

  /**
    * Parses a direction string from a Property Node
    *
    * @param directionProperty
    * @return
    */
  protected def parseDirection(directionProperty : PropertyNode) : String = {
    if(directionProperty != null) {
      stringParser.parse(directionProperty).getOrElse("E")
    } else "E"
  }

}
