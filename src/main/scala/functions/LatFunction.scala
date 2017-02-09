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

    if(coordinate != null) {    // coordinate is given as a parameter

      val geoCoordinate = geoCoordinateParser.parse(coordinateProperty)
      Array(geoCoordinate.get.latitude.toString)

    } else if(latitudeProperty != null) {   // latitude is given as a parameter

      val lat = getSingleCoordinate(latitudeProperty, -90.0, 90.0, wikiCode);

      Array(lat.get.toString)

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
        stringParser.parse(directionProperty).getOrElse("N")
      } else "N"

      val latitude = new Latitude(degreesValue, minutesValue, secondsValue, directionValue)

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
