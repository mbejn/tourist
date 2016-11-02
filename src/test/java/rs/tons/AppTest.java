package rs.tons;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;

import static rs.tons.App.TONS_HOME;

public class AppTest {

    @BeforeClass
    public static void setUp(){
        if(TONS_HOME == null) throw new Error("Please setup TONS_HOME environment.");
    }

    @Test
    public void testMe() throws FileNotFoundException {
        final String inputFilePath = TONS_HOME + "/survey-input.json";
        final String outputFilePath = TONS_HOME + "/test.json";

        App.main(new String[]{inputFilePath, outputFilePath});
    }

    @Test
    public void testMainWithoutArguments() throws FileNotFoundException {
        App.main(new String[0]);
    }

    @Test
    public void testMainWithRouteLegsNotBeingNumber() throws FileNotFoundException {

        final String inputFilePath = TONS_HOME + "/survey-input.json";
        final String outputFilePath = TONS_HOME + "/survey-output.json";
        final String numberOfRouteLegs = "number";

        App.main(new String[]{inputFilePath, outputFilePath, numberOfRouteLegs});
    }
}
