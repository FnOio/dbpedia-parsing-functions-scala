import functions.*;

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
            SimplePropertyFunction fn = new SimplePropertyFunction(property, select, prefix, suffix, transform, dFactor,dataType,unit);
            System.out.println("-----------------PARAMETERS--------------------------");
            System.out.println("\nSimplePropertyFunction");
            System.out.println("Property: " + property);
            System.out.println("Select: " + select);
            System.out.println("Preficx: " + prefix);
            System.out.println("Suffix: " + suffix);
            System.out.println("Transform: " + transform);
            System.out.println("Factor: " + dFactor);
            System.out.println("Datatype: " + dataType);
            System.out.println("Unit: " + unit);
            return new ArrayList<String>(Arrays.asList(fn.execute()));
        } catch(Exception e) {
            System.out.println("-------------------------------------------");
            System.err.println("\nSimplePropertyFunction exception: " + e);
            System.err.println("Property: " + property);
            System.err.println("Select: " + select);
            System.err.println("Preficx: " + prefix);
            System.err.println("Suffix: " + suffix);
            System.err.println("Transform: " + transform);
            System.err.println("Factor: " + dFactor);
            System.err.println("Datatype: " + dataType);
            System.err.println("Unit: " + unit);
            System.err.println("Stacktrace:");
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
            LatFunction fn = new LatFunction(coordinate, latitude, degrees, minutes, seconds, direction);
            System.out.println("-------------------------------------------");
            System.out.println("LatFunction exception: ");
            System.out.println("Latitude: " + latitude);
            System.out.println("Degrees: " + degrees);
            System.out.println("Minutes: " + minutes);
            System.out.println("Seconds: " + seconds);
            System.out.println("Direction: " + direction);
            return new ArrayList<String>(Arrays.asList(fn.execute()));
        } catch(Exception e) {
            System.err.println("-------------------------------------------");
            System.err.println("LatFunction exception: " + e);
            System.err.println("Latitude: " + latitude);
            System.err.println("Degrees: " + degrees);
            System.err.println("Minutes: " + minutes);
            System.err.println("Seconds: " + seconds);
            System.err.println("Direction: " + direction);
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
            LonFunction fn = new LonFunction(coordinate, longitude, degrees, minutes, seconds, direction);
            return new ArrayList<String>(Arrays.asList(fn.execute()));
        } catch(Exception e) {
            System.err.println("-------------------------------------------");
            System.err.println("LonFunction exception: " + e);
            System.err.println("Latitude: " + longitude);
            System.err.println("Degrees: " + degrees);
            System.err.println("Minutes: " + minutes);
            System.err.println("Seconds: " + seconds);
            System.err.println("Direction: " + direction);
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
            System.err.println("-------------------------------------------");
            System.err.println("Equals exception: " + e);
            System.err.println("Property: " + property);
            System.err.println("Value: " + value);
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
            System.err.println("-------------------------------------------");
            System.err.println("IsSet exception: " + e);
            System.err.println("Property: " + property);
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
            System.err.println("-------------------------------------------");
            System.err.println("Contains exception: " + e);
            System.err.println("Property: " + property);
            System.err.println("Value: " + value);
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
            System.err.println("-------------------------------------------");
            System.err.println("Start Data function exception: " + e);
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.err.println("Property: " + property);
            System.err.println("OntologyProperty: " + ontologyProperty);
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
            System.err.println("-------------------------------------------");
            System.err.println("Contains exception: " + e);
            System.err.println("Property: " + property);
            System.err.println("OntologyProperty: " + ontologyProperty);
        }
        return null;
    }



}