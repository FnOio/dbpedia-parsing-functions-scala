package dbpedia.dataparsers.util

import dbpedia.dataparsers.ontology.datatypes.Datatype
import dbpedia.dataparsers.ontology.{Ontology, OntologyClass, OntologyProperty}

import scala.language.reflectiveCalls

/**
  * Loading ontology data from given ontology class instance
  */
object RMLOntologyUtil {

  // "class" is the rml value
  private final val mapToClass: String = "class"


  def loadOntologyClass(ontologyClassName : String, ontology: Ontology): OntologyClass = {
      try {
        ontology.classes(ontologyClassName)
      } catch {
        case _: NoSuchElementException => throw new IllegalArgumentException("Ontology class not found: " + ontologyClassName)
      }
  }

  def loadOntologyProperty(ontologyPropertyName: String, ontology: Ontology): OntologyProperty = {
      try {
        ontology.properties(ontologyPropertyName)
      } catch {
        case _ : NoSuchElementException => throw new IllegalArgumentException("Ontology property not found: " + ontologyPropertyName)
      }
  }

  def loadOntologyDataType(ontologyDataTypeName: String, ontology: Ontology): Datatype = {
    try {
      ontology.datatypes(ontologyDataTypeName)
    } catch {
      case _ : NoSuchElementException => throw new IllegalArgumentException("Ontology datatype not found: " + ontologyDataTypeName)
    }
  }

  def loadOntologyPropertyFromIRI(ontologyIRI : String, ontology : Ontology): OntologyProperty = {

      try {
      val pattern = "(.*[/#])([^/#]+)".r
      val pattern(namespace, localname) = ontologyIRI
      val prefix = getPrefix(namespace)
      val localOntologyPropertyName = if(prefix != "dbo") {
        prefix + ":" + localname
      } else localname
        loadOntologyProperty(localOntologyPropertyName, ontology)
      } catch {
        case e : IllegalArgumentException => println("Skipping Ontology Property"); null
        case e : Exception => null
      }
  }

  def loadOntologyClassFromIRI(ontologyIRI : String, ontology: Ontology): OntologyClass = {
    val localOntologyClassName = ontologyIRI.replaceAll(".*/","")
    try {
      loadOntologyClass(localOntologyClassName, ontology)
    } catch {
      case _ : IllegalArgumentException => println("Skipping Ontology Property: " + localOntologyClassName); null
    }
  }

  def loadOntologyDataTypeFromIRI(ontologyIRI : String, ontology : Ontology) : Datatype = {
      val localOntologyDataTypeName = ontologyIRI.replaceAll(".*/","")
      loadOntologyDataType(localOntologyDataTypeName, ontology)
  }


  //get prefix from namespace string
  private def getPrefix(namespace: String) : String = {
    for(key <- RdfNamespace.prefixMap.keySet) {
      if(RdfNamespace.prefixMap(key).namespace == namespace) {
        return key
      }
    }
    null
  }


}
