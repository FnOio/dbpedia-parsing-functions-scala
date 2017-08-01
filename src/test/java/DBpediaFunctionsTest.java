import dbpedia.dataparsers.ontology.Ontology;
import dbpedia.dataparsers.ontology.OntologySingleton;
import functions.DBpediaFunctions;

/**
 * Created by wmaroy on 28.02.17.
 */
public class DBpediaFunctionsTest {

    @org.junit.Test
    public void simplePropertyFunctionTest() throws Exception {

        String property = "birth_date={{Birth date and age|1955|10|28}}";
        String select = null;
        String prefix = null;
        String suffix = null;
        String transform = null;
        Double factor = 1.0;
        String ontologyProperty = "birthDate";
        String unit = null;
        String language = "en";

        // check if ontology is loaded only once
        System.out.println(DBpediaFunctions.simplePropertyFunction(property, factor.toString(), transform, select, prefix, suffix, unit, ontologyProperty, language));
        System.out.println(OntologySingleton.getOntology().datatypes().size());
        System.out.println(OntologySingleton.getOntology().properties().size());
        System.out.println(OntologySingleton.getOntology().classes().size());
        System.out.println(DBpediaFunctions.simplePropertyFunction(property, factor.toString(), transform, select, prefix, suffix, unit, ontologyProperty, language));
        System.out.println(DBpediaFunctions.simplePropertyFunction(property, factor.toString(), transform, select, prefix, suffix, unit, ontologyProperty, language));



    }

    @org.junit.Test
    public void getLocalNameTest() throws Exception {

        String uri = "http://dbpedia.org/ontology/name";
        String localName = DBpediaFunctions.getLocalName(uri);

        assert localName.equals("name");

    }

}