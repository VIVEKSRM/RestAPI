package org.example.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExtentManager {

    private static ExtentReports extent;
    private static final String REPORT_DIR = "target/extent-report";
    private static final String REPORT_FILE = REPORT_DIR + "/ExtentReport.html";

    public synchronized static ExtentReports getInstance() {
        if (extent == null) {
            createInstance(REPORT_FILE);
        }
        return extent;
    }

    public synchronized static ExtentReports createInstance(String filePath) {
        try {
            Path reportDir = Paths.get(REPORT_DIR);
            if (!Files.exists(reportDir)) {
                Files.createDirectories(reportDir);
            }
        } catch (Exception e) {
            // ignore - will attempt to write later and fail noisily
            System.err.println("Could not create extent report directory: " + e.getMessage());
        }

        ExtentSparkReporter spark = new ExtentSparkReporter(filePath);
        spark.config().setDocumentTitle("Automation Test Report");
        spark.config().setReportName("API Test Results");

        extent = new ExtentReports();
        extent.attachReporter(spark);

        // add system info
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java", System.getProperty("java.version"));

        return extent;
    }

    public synchronized static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
