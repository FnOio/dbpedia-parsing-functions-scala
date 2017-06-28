package dbpedia.dataparsers

import java.io.File

import dbpedia.dataparsers.ontology.io.OntologyReader
import dbpedia.dataparsers.ontology.{Ontology, OntologyDatatypes, OntologySingleton}
import dbpedia.dataparsers.util.{Language, Redirects, XMLSource}
import org.apache.commons.io.FileUtils

/**
  * Created by wmaroy on 07.02.17.
  *
  * TODO: make this object more flexible
  *
  */
object ContextLoader {

  def loadContext() : {val language : Language
                        val redirects : Redirects
                        val ontology: Ontology} = {


    val context = new {
      val language : Language = Language.English
      val redirects : Redirects = new Redirects(Map())
      val ontology : Ontology = OntologySingleton.getOntology
    }

    context

  }

}
