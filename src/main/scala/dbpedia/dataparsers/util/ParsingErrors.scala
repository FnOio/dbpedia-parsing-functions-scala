package dbpedia.dataparsers.util

import scala.collection.mutable.ArrayBuffer

/**
 * Used to collect parsing errors.
 * Not thread-safe.
 */
private class ParsingErrors
{
    private val errors = new ArrayBuffer[String]()

    def add(msg : String) = errors += msg

    override def toString = if(errors.isEmpty) "Error not logged" else "Errors:\n" + errors.mkString("\t- ", "\n\t- ", "")
}
