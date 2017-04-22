import functions.*;
import functions.connectors.*;
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
            SimplePropertyConnector cn = new SimplePropertyConnector(property, select, prefix, suffix, transform, dFactor,dataType,unit);
            return new ArrayList<String>(scala.collection.JavaConversions.seqAsJavaList(cn.execute()));
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
            LatConnector cn = new LatConnector(coordinate, latitude, degrees, minutes, seconds, direction);
            return new ArrayList<String>(scala.collection.JavaConversions.seqAsJavaList(cn.execute()));
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
            LatConnector cn = new LatConnector(coordinate, longitude, degrees, minutes, seconds, direction);
            return new ArrayList<String>(scala.collection.JavaConversions.seqAsJavaList(cn.execute()));
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
            EqualsConnector cn = new EqualsConnector(property, value);
            return Boolean.valueOf(cn.execute().head());
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
            IsSetConnector cn = new IsSetConnector(property);
            return Boolean.valueOf(cn.execute().head());
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
            ContainsConnector cn = new ContainsConnector(property, value);
            Seq<String> result = cn.execute();
            System.out.println(result.head());
            System.out.println(Boolean.valueOf(result.head()));
            return Boolean.valueOf(result.head());
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
            StartDateConnector cn = new StartDateConnector(property, ontologyProperty);
            System.out.println("Parameters: " + property + ", " + ontologyProperty);
            String result = cn.execute().head();
            System.out.println("Result:" + result);
            return result;
        } catch(Exception e) {
            e.printStackTrace();
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
            EndDateConnector cn = new EndDateConnector(property, ontologyProperty);
            System.out.println("Parameters: " + property + ", " + ontologyProperty);
            String result = cn.execute().head();
            return result;
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
            ExtractDateConnector cn = new ExtractDateConnector(property, dateDatatype);
            return cn.execute().head();
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
            ExtractEntityConnector cn = new ExtractEntityConnector(property);
            return new ArrayList<String>(scala.collection.JavaConversions.seqAsJavaList(cn.execute()));
        } catch(Exception e) {
        }
        return null;
    }



}