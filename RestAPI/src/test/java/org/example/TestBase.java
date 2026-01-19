package org.example;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class TestBase {

    protected final Config config = Config.getInstance();

    @BeforeClass(alwaysRun = true)
    public void setup() {
        // Allow overriding baseURI via system property (e.g., -Dbase.uri.demoqa)
        String demoqa = System.getProperty("base.uri.demoqa", config.get("base.uri.demoqa"));
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        // Do not set a global baseURI here; tests should set appropriate base per test
        // but provide a convenience default if needed via config
        if (demoqa != null && !demoqa.isEmpty()) {
            // no global assignment to avoid interfering with tests that hit different hosts
            RestAssured.baseURI = demoqa;
        }
    }
}
