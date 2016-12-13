package dataparsers.util.wikiparser.impl.simple


import dataparsers.util.wikiparser.WikiParserException

/**
 * Thrown if the parser encounters too many errors.
 */
private final class TooManyErrorsException(line: Int, text: String) extends WikiParserException("Too many errors", line, text)
