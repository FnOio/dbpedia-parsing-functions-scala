package functions

import dbpedia.dataparsers.coordinate.{Latitude, Longitude}
import dbpedia.dataparsers.util.wikiparser.PropertyNode
import dbpedia.dataparsers._
import dbpedia.dataparsers.util.wikiparser.impl.simple.SimpleWikiParser

/**
  * Created by wmaroy on 07.02.17.
  */
class LonFunction(
  val coordinate: String,
  val longitude: String,
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
    val longitudeProperty = parsePropertyString(longitude)
    val degreesProperty = parsePropertyString(degrees)
    val minutesProperty = parsePropertyString(minutes)
    val secondsProperty = parsePropertyString(seconds)
    val directionProperty = parsePropertyString(direction)


    if(coordinate != null) {    // coordinate is given as a parameter

      val geoCoordinate = geoCoordinateParser.parse(coordinateProperty)
      Array(geoCoordinate.get.longitude.toString)

    } else if(longitudeProperty != null) { //Latitude is given as one property

      val lon = getSingleCoordinate(longitudeProperty, -180.0, 180.0, wikiCode)

      Array(lon.get.toString)

    } else {

      //Latitude is given as separate properties
      val degreesValue = if(degreesProperty != null) {
        doubleParser.parse(degreesProperty).getOrElse(0.0)
      } else 0.0

      val minutesValue = if(minutesProperty != null) {
        doubleParser.parse(minutesProperty).getOrElse(0.0)
      } else 0.0

      val secondsValue = if(secondsProperty != null) {
        doubleParser.parse(secondsProperty).getOrElse(0.0)
      } else 0.0

      val directionValue = if(directionProperty != null) {
        stringParser.parse(directionProperty).getOrElse("E")
      } else "E"

      val longitude = new Longitude(degreesValue, minutesValue, secondsValue, directionValue)

      Array(longitude.toDouble.toString)

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
      wikiparser.parseString(property)
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
