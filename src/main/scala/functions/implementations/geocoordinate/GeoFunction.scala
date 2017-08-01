package functions.implementations.geocoordinate

import dbpedia.dataparsers._
import dbpedia.dataparsers.util.Language
import dbpedia.dataparsers.util.wikiparser.PropertyNode
import dbpedia.dataparsers.util.wikiparser.impl.simple.SimpleWikiParser
import functions.Function

/**
  * Created by wmaroy on 17.02.17.
  */
abstract class GeoFunction(
  val coordinate: String,
  val singleCoordinate: String,
  val degrees: String,
  val minutes: String,
  val seconds: String,
  val direction: String,
  val language: String) extends Function {

  private val context = ContextLoader.loadContext(Language(language))
  private val wikiparser = new SimpleWikiParser
  private val doubleParser = new DoubleParser(context)
  private val singleGeoCoordinateParser = new SingleGeoCoordinateParser(context)

  protected val stringParser = StringParser
  protected val wikiCode = context.language.wikiCode
  protected val geoCoordinateParser = new GeoCoordinateParser(context)

  def execute : Seq[String] = {

    val coordinateProperty = parsePropertyString(coordinate)
    val latitudeProperty = parsePropertyString(singleCoordinate)
    val degreesProperty = parsePropertyString(degrees)
    val minutesProperty = parsePropertyString(minutes)
    val secondsProperty = parsePropertyString(seconds)
    val directionProperty = parsePropertyString(direction)

    if(coordinateProperty != null) {

      // full coordinate is given as a parameter
      calculateCoordinate(coordinateProperty)

    } else if(latitudeProperty != null) {

      // latitude or longitude is given as a parameter
      calculateSingleCoordinate(latitudeProperty)

    } else {

      //Latitude or longitude is given as separate properties
      val degreesValue =  parseDouble(degreesProperty)
      val minutesValue = parseDouble(minutesProperty)
      val secondsValue = parseDouble(secondsProperty)
      val directionValue = parseDirection(directionProperty)

      calculateCoordinate(degreesValue, minutesValue, secondsValue, directionValue)

    }

  }

  /**
    * Parses a double from a Property Node
    *
    * @param property
    * @return
    */
  protected def parseDouble(property : PropertyNode) : Double = {
    if(property != null) {
      doubleParser.parse(property).getOrElse(0.0)
    } else 0.0
  }

  /**
    * Parses a direction string from a Property Node
    *
    * @param directionProperty
    * @return
    */
  protected def parseDirection(directionProperty : PropertyNode) : String


  /**
    * Returns an array filled with a string that represents the latitude
    * Input is a property node with the coordinate value, latitude will be parsed from this
    *
    * @param coordinate
    * @return
    */
  protected def calculateCoordinate(coordinate : PropertyNode) : Seq[String]

  /**
    * Returns an array filled with a string that represent the latitude
    * Input is a property node with the latitude value
    *
    * @param latitude
    * @return
    */
  protected def calculateSingleCoordinate(latitude: PropertyNode): Seq[String] = {
    val lat = getSingleCoordinate(latitude, -90.0, 90.0, wikiCode)
    Seq(lat.get.toString)
  }

  /**
    * Return an array filled with a string that represents the the type of coordinate
    *
    * @param degrees
    * @param minutes
    * @param seconds
    * @param direction
    * @return
    */
  protected def calculateCoordinate(degrees : Double, minutes : Double, seconds : Double, direction : String) : Seq[String]

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
  protected def getSingleCoordinate(coordinateProperty: PropertyNode, rangeMin: Double, rangeMax: Double, wikiCode: String ): Option[Double] = {
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
