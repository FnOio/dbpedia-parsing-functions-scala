/**
  * Created by ali on 12/20/14.
  * singled out and extended by Chile on 9/19/2016.
  */

package dbpedia.dataparsers.util

import java.io._
import java.net.URL

import com.fasterxml.jackson.annotation.JsonFormat.Value
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.node.JsonNodeType
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper, ObjectReader}
import dbpedia.dataparsers.ontology.{OntologyClass, OntologyProperty}
import dbpedia.mappings.{WikidataCommandReceiver, WikidataOneToManyCommand, WikidataOneToOneCommand, WikidataTransformationCommands}

import scala.collection.JavaConversions._
import scala.language.postfixOps

class JsonConfig(fileUrl:URL) {
  var configMap: Map[String, JsonNode] = JsonConfig.load(fileUrl.openStream())

  def getMap(property: String): Map[String, JsonNode] = {
    configMap.get(property) match{
      case Some(o) => o.getNodeType match{
        case JsonNodeType.OBJECT => {
          JsonConfig.getObjectMap(o)
        }
        case JsonNodeType.ARRAY => {
          JsonConfig.getObjectMap(o)
      }
        case _ => null
      }
      case None => null
    }
  }

  def getCommand(property: String, value: Value, equivClassSet: Set[OntologyClass], equivPropertySet: Set[OntologyProperty], receiver: WikidataCommandReceiver): WikidataTransformationCommands = {
    var command = new WikidataTransformationCommands {
      override def execute(): Unit = print("")
    }

      if (getMap(property)!=null) {
        if (getMap(property).size >= 1) {
          receiver.setParameters(property, value, equivClassSet, equivPropertySet, getMap(property).map(x => x._1 -> x._2.asText()))
          val oneToManyCommand = new WikidataOneToManyCommand(receiver)
          command = oneToManyCommand
        }
        else {
          receiver.setParameters(property, value, equivClassSet, equivPropertySet, getMap(property).map(x => x._1 -> x._2.asText()))
          val oneToOneCommand = new WikidataOneToOneCommand(receiver)
          command = oneToOneCommand
        }
      }
    command
  }

  def keys(): Iterable[String] = configMap.keys

  def size(): Int = configMap.size

  def get(key:String): Option[JsonNode] = configMap.get(key)

  def put(key:String, value:JsonNode): JsonNode = configMap.put(key, value)


}

object JsonConfig{
  def load(inStream: InputStream): Map[String, JsonNode] = {
    val factory = new JsonFactory()
    val objectMapper = new ObjectMapper(factory)
    val objectReader: ObjectReader = objectMapper.reader()
    val json = objectReader.readTree(inStream)
    getObjectMap(json)
  }

  def getObjectMap(node: JsonNode): Map[String, JsonNode] ={
    var ret = Map[String, JsonNode]()
    node.getNodeType match{
      case JsonNodeType.OBJECT =>     for ( key <- node.fieldNames()) {
        if(ret.keys.contains(key))
          throw new Exception("JsonObjectMap was loaded with non unique key: " + key)
        val jsonNode = node.get(key)
        ret += (key -> jsonNode)
      }

      case JsonNodeType.ARRAY=> {
        node.foreach {
          eachMapping => {
            for ( key <- eachMapping.fieldNames()) {
              if(ret.keys.contains(key))
                throw new Exception("JsonObjectMap was loaded with non unique key: " + key)
              val jsonNode = eachMapping.get(key)
              ret += (key -> jsonNode)
            }
          }
        }
      }
      case _ => throw new Exception("JsonObjectMap was loaded with non ObjectNode type.")
    }
    ret
  }
}