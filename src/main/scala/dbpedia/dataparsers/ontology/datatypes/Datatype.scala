package dbpedia.dataparsers.ontology.datatypes

import dbpedia.dataparsers.DBpediaNamespace
import dbpedia.dataparsers.ontology.OntologyType
import dbpedia.dataparsers.util.{Language, RdfNamespace}

/**
 * Base class of all data types.
 *
 * @param name The name of this datatype e.g. xsd:float
 * @param labels The labels of this datatype. Map: LanguageCode -> Label
 * @param comments Comments describing this datatype. Map: LanguageCode -> Comment
 */
class Datatype(name : String, labels : Map[Language, String], comments : Map[Language, String]) extends OntologyType(name, labels, comments)
{
    /**
     * Constructs a datatype when no label is explicitly defined.
     *
     * @param name The name of this datatype
     */
    def this(name : String) = this(name, Map(Language.English -> name), Map())

    /**
     * The URI of this datatype
     */
    override val uri = RdfNamespace.fullUri(DBpediaNamespace.DATATYPE, name)

    val isExternalProperty = ! uri.startsWith(DBpediaNamespace.DATATYPE.namespace)
}
