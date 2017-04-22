import static org.junit.Assert.*;

/**
 * Created by wmaroy on 28.02.17.
 */
public class DBpediaFunctionsTest {

    @org.junit.Test
    public void startDateFunction() throws Exception {

        String property = "years_active=427 BC – 386 BC";
        String ontologyPropertyString = "activeYearsStartYear";

        DBpediaFunctions.startDateFunction(property, ontologyPropertyString);

    }

}