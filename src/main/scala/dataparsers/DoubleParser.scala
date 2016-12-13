package dataparsers

import java.text.ParseException
import java.util.logging.{Level, Logger}

import dataparsers.util.wikiparser.Node
import dataparsers.util.{Language, ParserUtils}
import scala.language.reflectiveCalls

/**
 * Parses double-precision floating-point numbers.
 */
//TODO a lot of copied code from IntegerParser!
class DoubleParser(context : { def language : Language },
                   strict : Boolean = false,
                   multiplicationFactor : Double = 1.0) extends DataParser
{
    private val parserUtils = new ParserUtils(context.language)

    private val logger = Logger.getLogger(getClass.getName)

    private val language = context.language.wikiCode

    override val splitPropertyNodeRegex = if (DataParserConfig.splitPropertyNodeRegexDouble.contains(language))
                                            DataParserConfig.splitPropertyNodeRegexDouble.get(language).get
                                          else DataParserConfig.splitPropertyNodeRegexDouble.get("en").get

    // we allow digits, minus, comma, dot and space in numbers
    private val DoubleRegex  = """\D*?(-?[0-9-,. ]+).*""".r

    override def parse(node : Node) : Option[Double] =
    {
        for( text <- StringParser.parse(node);
             convertedText = parserUtils.convertLargeNumbers(text);
             value <- parseFloatValue(convertedText) )
        {
            return Some(value * multiplicationFactor)
        }

        None
    }

    def parseFloatValue(input : String) : Option[Double] =                                                                   //needed for RMLProcessRunner
    {
        val numberStr = if(strict) input.trim else DoubleRegex.findFirstMatchIn(input.trim) match
        {
            case Some(s) => s.toString()
            case None =>
            {
                logger.log(Level.FINE, "Cannot convert '" + input + "' to a floating point number, DoubleRegex did not match")
                return None
            }
        }

        try
        {
            val result = parserUtils.parse(numberStr).doubleValue
            val hasMinusSign = (!input.equals(numberStr) && DataParserConfig.dashVariations.contains(input.trim.charAt(0)))
            val negatize = if (result>=0 && hasMinusSign) -1 else 1
            Some(negatize * result)
        }
        catch
        {
            case ex : ParseException =>
            {
                logger.log(Level.FINE, "Cannot convert '" + numberStr + "' to a floating point number", ex)
                None
            }
            case ex : NumberFormatException =>
            {
                logger.log(Level.FINE, "Cannot convert '" + numberStr + "' to a floating point number", ex)
                None
            }
            case ex : ArrayIndexOutOfBoundsException =>
            {
                logger.log(Level.FINE, "Cannot convert '" + numberStr + "' to an integer", ex)
                None
            }
        }
    }
}