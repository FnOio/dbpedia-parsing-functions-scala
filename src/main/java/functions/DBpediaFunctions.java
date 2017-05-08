package functions;

import dbpedia.dataparsers.ontology.OntologySingleton;
import functions.*;
import functions.connectors.*;
import functions.implementations.SimplePropertyFunction;
import functions.implementations.conditions.ContainsFunction;
import functions.implementations.conditions.EqualsFunction;
import functions.implementations.core.ExtractDateFunction;
import functions.implementations.core.ExtractEntityFunction;
import functions.implementations.core.ExtractStringFunction;
import functions.implementations.date.StartDateFunction;
import functions.implementations.geocoordinate.LatFunction;
import functions.implementations.geocoordinate.LonFunction;
import scala.collection.Seq;

import javax.sql.rowset.serial.SerialRef;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Contains the functions that are defined by the DBpedia Functions Ontology
 */
public class DBpediaFunctions {



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
    public static ArrayList<String> simplePropertyFunction(String property, String factor, String transform, String select, String prefix, String suffix, String unit, String dataType) {
        Double dFactor;
        if(factor == null) {
            dFactor = 1.0;
        } else {
            dFactor = Double.parseDouble(factor);
        }
        try {
            loadOntologyMaybe();
            //SimplePropertyConnector cn = new SimplePropertyConnector(property, select, prefix, suffix, transform, dFactor,dataType,unit);
            //ArrayList<String> list = new ArrayList<String>(scala.collection.JavaConversions.seqAsJavaList(cn.execute()));
            SimplePropertyFunction fn = new SimplePropertyFunction(property, select, prefix, suffix, transform, dFactor, dataType, unit);
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
    public static ArrayList<String> latFunction(String coordinate, String latitude, String degrees, String minutes, String seconds, String direction) {
        try {
            //LatConnector cn = new LatConnector(coordinate, latitude, degrees, minutes, seconds, direction);
            //ArrayList<String> list = new ArrayList<String>(scala.collection.JavaConversions.seqAsJavaList(cn.execute()));
            //return list;
            loadOntologyMaybe();
            LatFunction fn = new LatFunction(coordinate, latitude, degrees, minutes, seconds, direction);
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
    public static ArrayList<String> lonFunction(String coordinate, String longitude, String degrees, String minutes, String seconds, String direction) {
        try {
            /*
            LatConnector cn = new LatConnector(coordinate, longitude, degrees, minutes, seconds, direction);
            ArrayList<String> list = new ArrayList<String>(scala.collection.JavaConversions.seqAsJavaList(cn.execute()));
            return list;
            */
            loadOntologyMaybe();
            LonFunction cn = new LonFunction(coordinate, longitude, degrees, minutes, seconds, direction);
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
            //EqualsConnector cn = new EqualsConnector(property, value);
            //return Boolean.valueOf(cn.execute().head());
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
            //IsSetConnector cn = new IsSetConnector(property);
            //return Boolean.valueOf(cn.execute().head());
            loadOntologyMaybe();
            IsSetConnector fn = new IsSetConnector(property);
            return Boolean.valueOf(fn.execute().head());
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
            //ContainsConnector cn = new ContainsConnector(property, value);
            //Seq<String> result = cn.execute();
            //return Boolean.valueOf(result.head());
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
    public static String startDateFunction(String property, String ontologyProperty) {
        try {
            //StartDateConnector cn = new StartDateConnector(property, ontologyProperty);
            //String result = cn.execute().head();
            //return result;
            loadOntologyMaybe();
            StartDateFunction fn = new StartDateFunction(property, ontologyProperty);
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
    public static String endDateFunction(String property, String ontologyProperty) {
        try {
            //EndDateConnector cn = new EndDateConnector(property, ontologyProperty);
            //String result = cn.execute().head();
            //return result;
            loadOntologyMaybe();
            EndDateConnector cn = new EndDateConnector(property, ontologyProperty);
            return cn.execute().head();
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
            //ExtractDateConnector cn = new ExtractDateConnector(property, dateDatatype);
            //return cn.execute().head();
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
            //ExtractEntityConnector cn = new ExtractEntityConnector(property);
            //return new ArrayList<String>(scala.collection.JavaConversions.seqAsJavaList(cn.execute()));
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
            //ExtractStringConnector cn = new ExtractStringConnector(property);
            //return cn.execute().head();
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



}