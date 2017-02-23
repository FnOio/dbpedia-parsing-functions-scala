import functions.*;
import functions.connectors.SimplePropertyConnector;

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
            //SimplePropertyConnector cn = new SimplePropertyConnector(property, select, prefix, suffix, transform, dFactor,dataType,unit);
            //SimplePropertyFunction fn = new SimplePropertyFunction(property, select, prefix, suffix, transform, dFactor,dataType,unit);
            //return new ArrayList<String>(Arrays.asList(fn.execute()));
            //return new ArrayList<String>(scala.collection.JavaConversions.seqAsJavaList(cn.execute()));
        } catch(Exception e) {
            System.err.println("\nSimplePropertyFunction exception: " + e);
            e.printStackTrace();
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
            //LatFunction fn = new LatFunction(coordinate, latitude, degrees, minutes, seconds, direction);
            //return new ArrayList<String>(Arrays.asList(fn.execute()));
            return new ArrayList<String>();
        } catch(Exception e) {
            System.err.println("LatFunction exception: " + e);
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
            //LonFunction fn = new LonFunction(coordinate, longitude, degrees, minutes, seconds, direction);
            //return new ArrayList<String>(Arrays.asList(fn.execute()));
            return new ArrayList<String>();
        } catch(Exception e) {
            System.err.println("LonFunction exception: " + e);
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
            EqualsFunction fn = new EqualsFunction(property, value);
            return fn.execute();
        } catch(Exception e) {
            System.err.println("Equals exception: " + e);
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
            IsSetFunction fn = new IsSetFunction(property);
            return fn.execute();
        } catch(Exception e) {
            System.err.println("IsSet exception: " + e);
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
            ContainsFunction fn = new ContainsFunction(property, value);
            return fn.execute();
        } catch(Exception e) {
            System.err.println("Contains exception: " + e);
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
            StartDateFunction fn = new StartDateFunction(property, ontologyProperty);
            return fn.execute();
        } catch(Exception e) {
            System.err.println("Start Data function exception: " + e);
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

        } catch(Exception e) {
            System.err.println("Contains exception: " + e);
        }
        return null;
    }



}