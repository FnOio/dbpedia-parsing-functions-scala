import functions.SimplePropertyFunction;

import java.util.ArrayList;
import java.util.Arrays;

public class DBpediaFunctions {

    public static ArrayList<String> latFunction(String lat, String latDegrees, String latMinutes, String latSeconds, String latDirection){
        ArrayList<String> result = new ArrayList<String>();
        result.add("50.833332");
        return result;
    }

    public static ArrayList<String> lonFunction(String lat, String latDegrees, String latMinutes, String latSeconds, String latDirection){
        ArrayList<String> result = new ArrayList<String>();
        result.add("4.000000");
        return result;
    }

    public static ArrayList<String> simplePropertyFunction(String property, String factor, String transform, String select, String prefix, String suffix, String unit, String dataType) {
        Double dFactor;
        if(factor == null) {
            dFactor = 1.0;
        } else {
            dFactor = Double.parseDouble(factor);
        }
        try {
            SimplePropertyFunction fn = new SimplePropertyFunction(property, select, prefix, suffix, transform, dFactor,dataType,unit);
            return new ArrayList<String>(Arrays.asList(fn.execute()));
        } catch(Exception e) {
            System.err.println("-------------------------------------------");
            System.err.println("\nSimplePropertyFunction exception: " + e);
            System.err.println("Property: " + property);
            System.err.println("Select: " + select);
            System.err.println("Preficx: " + prefix);
            System.err.println("Suffix: " + suffix);
            System.err.println("Transform: " + transform);
            System.err.println("Factor: " + dFactor);
            System.err.println("Datatype: " + dataType);
            System.err.println("Stacktrace:");
            e.printStackTrace();
        }

        return null;

    }

}