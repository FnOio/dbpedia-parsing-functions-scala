package functions;

import dbpedia.dataparsers.ontology.OntologySingleton;
import functions.*;
import functions.implementations.SimplePropertyFunction;
import functions.implementations.conditions.ContainsFunction;
import functions.implementations.conditions.EqualsFunction;
import functions.implementations.conditions.IsSetFunction;
import functions.implementations.core.ExtractDateFunction;
import functions.implementations.core.ExtractEntityFunction;
import functions.implementations.core.ExtractStringFunction;
import functions.implementations.date.EndDateFunction;
import functions.implementations.date.StartDateFunction;
import functions.implementations.geocoordinate.LatFunction;
import functions.implementations.geocoordinate.LonFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.collection.Seq;

import javax.sql.rowset.serial.SerialRef;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Contains the functions that are defined by the DBpedia Functions Ontology
 */
public class DBpediaFunctions {

    private static Logger logger = LoggerFactory.getLogger(DBpediaFunctions.class);

    /**
     * Executes SimpleProperty Function
     * @param property
     * @param factor
     * @param transform
     * @param select
     * @param prefix
     * @param suffix
     * @param unit
     * @param dataType
     * @return
     */
    public static ArrayList<String> simplePropertyFunction(String property, String factor, String transform, String select, String prefix, String suffix, String unit, String dataType, String language) {
        Double dFactor;
        if(factor == null) {
            dFactor = 1.0;
        } else {
            dFactor = Double.parseDouble(factor);
        }
        try {
            loadOntologyMaybe();
            String localDatatype = dataType;
            if(localDatatype != null) {
                localDatatype = getLocalName(dataType);
            }
            String localUnit = unit;
            if(localUnit != null) {
                localUnit = getLocalName(unit);
            }
            SimplePropertyFunction fn = new SimplePropertyFunction(property, select, prefix, suffix, transform, dFactor, localDatatype, localUnit, language);
            ArrayList<String> list = new ArrayList<String>(scala.collection.JavaConversions.seqAsJavaList(fn.execute()));
            return list ;
        } catch(Exception e) {

        }

        return null;

    }

    /**
     * Executes the latitude function
     * @param latitude
     * @param degrees
     * @param minutes
     * @param seconds
     * @param direction
     * @return
     */
    public static ArrayList<String> latFunction(String coordinate, String latitude, String degrees, String minutes, String seconds, String direction, String language) {
        try {
            loadOntologyMaybe();
            LatFunction fn = new LatFunction(coordinate, latitude, degrees, minutes, seconds, direction, language);
            ArrayList<String> list = new ArrayList<String>(scala.collection.JavaConversions.seqAsJavaList(fn.execute()));
            return list;
        } catch(Exception e) {
        }
        return new ArrayList<String>();
    }

    /**
     * Executes the longitude function
     * @param longitude
     * @param degrees
     * @param minutes
     * @param seconds
     * @param direction
     * @return
     */
    public static ArrayList<String> lonFunction(String coordinate, String longitude, String degrees, String minutes, String seconds, String direction, String language) {
        try {
            loadOntologyMaybe();
            LonFunction cn = new LonFunction(coordinate, longitude, degrees, minutes, seconds, direction, language);
            ArrayList<String> list = new ArrayList<String>(scala.collection.JavaConversions.seqAsJavaList(cn.execute()));
            return list;
        } catch(Exception e) {
        }
        return new ArrayList<String>();
    }

    /**
     * Executes the equals function
     * @param property
     * @param value
     * @return
     */
    public static Boolean equals(String property, String value) {
        try {
            loadOntologyMaybe();
            EqualsFunction fn = new EqualsFunction(property, value);
            return fn.execute();
        } catch(Exception e) {
        }
        return false;
    }

    /**
     * Executes the isSet function
     * @param property
     * @return
     */
    public static Boolean isSet(String property) {
        try {
            loadOntologyMaybe();
            IsSetFunction fn = new IsSetFunction(property);
            return fn.execute();
        } catch(Exception e) {
        }
        return false;
    }

    /**
     * Executes the contains function
     * @param property
     * @param value
     * @return
     */
    public static Boolean contains(String property, String value) {
        try {
            loadOntologyMaybe();
            ContainsFunction fn = new ContainsFunction(property, value);
            return fn.execute();
        } catch(Exception e) {
        }
        return false;
    }

    /**
     * Executes the start date function
     * @param property
     * @param ontologyProperty
     * @return
     */
    public static String startDateFunction(String property, String ontologyProperty, String language) {
        try {
            //StartDateConnector cn = new StartDateConnector(property, ontologyProperty);
            //String result = cn.execute().head();
            //return result;
            loadOntologyMaybe();
            String localOntologyProperty = ontologyProperty;
            if(localOntologyProperty != null) {
                localOntologyProperty = getLocalName(ontologyProperty);
            }
            StartDateFunction fn = new StartDateFunction(property, localOntologyProperty, language);
            return fn.execute();
        } catch(Exception e) {
        }
        return null;
    }

    /**
     * Executes the end date function
     * @param property
     * @param ontologyProperty
     * @return
     */
    public static String endDateFunction(String property, String ontologyProperty, String language) {
        try {
            loadOntologyMaybe();
            String localOntologyProperty = ontologyProperty;
            if(localOntologyProperty != null) {
                localOntologyProperty = getLocalName(ontologyProperty);
            }
            EndDateFunction cn = new EndDateFunction(property, localOntologyProperty, language);
            return cn.execute();
        } catch(Exception e) {
        }
        return null;
    }

    /**
     * Executes extract date function
     * @param property
     * @param dateDatatype
     * @return
     */
    public static String extractDate(String property, String dateDatatype) {
        try {
            loadOntologyMaybe();
            ExtractDateFunction fn = new ExtractDateFunction();
            return fn.execute(property, dateDatatype).head();
        } catch(Exception e) {
        }
        return null;
    }

    /**
     * Executes extract entity function
     * @param property
     * @return
     */
    public static ArrayList<String> extractEntity(String property) {
        try {
            loadOntologyMaybe();
            ExtractEntityFunction fn = new ExtractEntityFunction();
            return new ArrayList<String>(scala.collection.JavaConversions.seqAsJavaList(fn.execute(property)));
        } catch(Exception e) {
        }
        return null;
    }

    /**
     * Executes extract String function
     * @param property
     * @return
     */
    public static String extractString(String property) {
        try {
            loadOntologyMaybe();
            ExtractStringFunction fn = new ExtractStringFunction();
            return fn.execute(property).head();
        } catch(Exception e) {
        }
        return null;
    }

    private static void loadOntologyMaybe() {
        if(!ontologyLoaded()) {
            OntologySingleton.load();
            System.out.println("Ontology loaded.");
        }
    }

    private static Boolean ontologyLoaded() {
        return OntologySingleton.getOntology() != null;
    }

    public static String getLocalName(String uri) {
        int lastDashIndex = uri.lastIndexOf("/");
        return uri.substring(lastDashIndex + 1, uri.length());
    }



}