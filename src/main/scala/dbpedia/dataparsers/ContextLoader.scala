package dbpedia.dataparsers

import java.io.File

import dbpedia.dataparsers.ontology.{Ontology, OntologyDatatypes,OntologyProperty}
import dbpedia.dataparsers.ontology.io.OntologyReader
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

    val ontologyStream =  this.getClass.getClassLoader.getResourceAsStream("ontology.xml")
    val tempFile : File = File.createTempFile("ontology_temp", ".xml")
    tempFile.deleteOnExit()
    FileUtils.copyInputStreamToFile(ontologyStream, tempFile)

    val ontologySource = XMLSource.fromFile(tempFile, Language.English)
    val ontologyObject = new OntologyReader().read(ontologySource)


    val context = new {
      val language : Language = Language.English
      val redirects : Redirects = new Redirects(Map())
      val ontology : Ontology = new Ontology(null,ontologyObject.properties,OntologyDatatypes.load().map(t => (t.name, t)).toMap,null,null,null)
    }

    context

  }



}
