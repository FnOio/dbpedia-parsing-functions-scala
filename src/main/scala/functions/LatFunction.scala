package functions

import dbpedia.dataparsers.coordinate.Latitude
import dbpedia.dataparsers._
import dbpedia.dataparsers.util.wikiparser.PropertyNode
import dbpedia.dataparsers.util.wikiparser.impl.simple.SimpleWikiParser

/**
  * Created by wmaroy on 07.12.16.
  */
class LatFunction(
  val coordinate: String,
  val latitude: String,
  val degrees: String,
  val minutes: String,
  val seconds: String,
  val direction: String) extends Function {

  private val context = ContextLoader.loadContext()
  private val wikiparser = new SimpleWikiParser
  private val doubleParser = new DoubleParser(context)
  private val stringParser = StringParser
  private val wikiCode = context.language.wikiCode
  private val singleGeoCoordinateParser = new SingleGeoCoordinateParser(context)
  private val geoCoordinateParser = new GeoCoordinateParser(context)

  def execute : Array[String] = {

    val coordinateProperty = parsePropertyString(coordinate)
    val latitudeProperty = parsePropertyString(latitude)
    val degreesProperty = parsePropertyString(degrees)
    val minutesProperty = parsePropertyString(minutes)
    val secondsProperty = parsePropertyString(seconds)
    val directionProperty = parsePropertyString(direction)

    if(coordinateProperty != null) {

      // coordinate is given as a parameter
      calculateLatitudeFromCoordinate(coordinateProperty)

    } else if(latitudeProperty != null) {

      // latitude is given as a parameter
      calculateLatitude(latitudeProperty)

    } else {

      //Latitude is given as separate properties

      val degreesValue =  parseDouble(degreesProperty)

      val minutesValue = parseDouble(minutesProperty)

      val secondsValue = parseDouble(secondsProperty)

      val directionValue = parseDirection(directionProperty)

      calculateLatitude(degreesValue, minutesValue, secondsValue, directionValue)

    }

  }

  /**
    * Parses a double from a Property Node
    * @param property
    * @return
    */
  def parseDouble(property : PropertyNode) : Double = {
    if(property != null) {
      doubleParser.parse(property).getOrElse(0.0)
    } else 0.0
  }

  /**
    * Parses a direction string from a Property Node
    * @param directionProperty
    * @return
    */
  def parseDirection(directionProperty : PropertyNode) : String = {
    if(directionProperty != null) {
      stringParser.parse(directionProperty).getOrElse("N")
    } else "N"
  }


  /**
    * Returns an array filled with a string that represents the latitude
    * Input is a property node with the coordinate value, latitude will be parsed from this
    * @param coordinate
    * @return
    */
  def calculateLatitudeFromCoordinate(coordinate : PropertyNode) : Array[String] = {
    val geoCoordinate = geoCoordinateParser.parse(coordinate)
    Array(geoCoordinate.get.latitude.toString)
  }

  /**
    * Returns an array filled with a string that represent the latitude
    * Input is a property node with the latitude value
    * @param latitude
    * @return
    */
  def calculateLatitude(latitude : PropertyNode) : Array[String] = {
    val lat = getSingleCoordinate(latitude, -90.0, 90.0, wikiCode)
    Array(lat.get.toString)
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
  def calculateLatitude(degrees : Double, minutes : Double, seconds : Double, direction : String) : Array[String] = {
    degrees match {

      //do not allow degrees that are 0.0, this means that the degree param has not been set
      case 0.0 => Array(null)

      case other =>
        val latitude = new Latitude(degrees, minutes, seconds, direction)
        Array(latitude.toDouble.toString)

    }
  }

  /**
    * Parses a string to a Property Node
    * Returns null of the string is null
 *
    * @param property
    * @return
    */
  private def parsePropertyString(property : String) : PropertyNode = {
      if(property!=null) {
        wikiparser.parseProperty(property)
      } else null
    }


  /**
    * Parses a single coordinate (longitude or latitude)
 *
    * @param coordinateProperty
    * @param rangeMin
    * @param rangeMax
    * @param wikiCode
    * @return
    */
  private def getSingleCoordinate(coordinateProperty: PropertyNode, rangeMin: Double, rangeMax: Double, wikiCode: String ): Option[Double] = {
    singleGeoCoordinateParser.parse(coordinateProperty).map(_.toDouble) orElse doubleParser.parse(coordinateProperty) match {
      case Some(coordinateValue) =>
        //Check if the coordinate is in the correct range
        if (rangeMin <= coordinateValue && coordinateValue <= rangeMax) {
          Some(coordinateValue)
        } else if (!wikiCode.equals("en"))  {
          // Sometimes coordinates are written with the English locale (. instead of ,)
          doubleParser.parse(coordinateProperty) match { // TODO: originally this was done with the english double parser
            case Some(enCoordinateValue) =>
              if (rangeMin <= enCoordinateValue && enCoordinateValue <= rangeMax) {
                // do not return invalid coordinates either way
                Some(enCoordinateValue)
              } else None
            case None => None
          }
        } else None
      case None => None
    }
  }


}
