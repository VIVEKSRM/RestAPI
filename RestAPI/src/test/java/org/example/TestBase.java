package org.example;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

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

    // Capture a simple textual snapshot to attach to the report when no WebDriver is present.
    // Returns a relative path (from project root) to the saved snapshot file, or null on failure.
    public String captureSnapshot(String name, Throwable t) {
        try {
            String dir = "target/extent-report/screenshots";
            Path d = Paths.get(dir);
            if (!Files.exists(d)) {
                Files.createDirectories(d);
            }
            String ts = Instant.now().toString().replaceAll(":", "-");
            String fileName = ts + "_" + name + ".txt";
            Path file = d.resolve(fileName);

            StringBuilder sb = new StringBuilder();
            sb.append("Test: ").append(name).append(System.lineSeparator());
            sb.append("Timestamp: ").append(Instant.now().toString()).append(System.lineSeparator());
            if (t != null) {
                sb.append("Throwable: ").append(t.toString()).append(System.lineSeparator());
                for (StackTraceElement ste : t.getStackTrace()) {
                    sb.append("    at ").append(ste.toString()).append(System.lineSeparator());
                }
            } else {
                sb.append("No throwable provided").append(System.lineSeparator());
            }

            Files.write(file, sb.toString().getBytes(StandardCharsets.UTF_8));
            return file.toString().replace("\\", "/");
        } catch (IOException e) {
            System.err.println("Failed to write snapshot: " + e.getMessage());
            return null;
        }
    }
}
