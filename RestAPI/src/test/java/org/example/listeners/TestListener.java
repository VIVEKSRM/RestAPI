package org.example.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.example.TestBase;
import org.example.reports.ExtentManager;
import org.example.reports.ExtentTestManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getInstance(); // initialize
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getMethod().getMethodName();
        ExtentTest test = ExtentManager.getInstance().createTest(name);
        ExtentTestManager.startTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().pass("Test passed");
        ExtentTestManager.endTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable throwable = result.getThrowable();
        String method = result.getMethod().getMethodName();
        String details = throwable == null ? "No throwable provided" : throwable.toString();

        String path = null;
        Object instance = result.getInstance();
        if (instance instanceof TestBase) {
            try {
                path = ((TestBase) instance).captureSnapshot(method, throwable);
            } catch (Exception e) {
                System.err.println("Failed to capture snapshot: " + e.getMessage());
            }
        }

        try {
            if (path != null && (path.endsWith(".png") || path.endsWith(".jpg"))) {
                ExtentTestManager.getTest().fail(details,
                        MediaEntityBuilder.createScreenCaptureFromPath(path).build());
            } else {
                ExtentTestManager.getTest().fail(details);
                if (path != null) {
                    ExtentTestManager.getTest().info("Snapshot saved at: " + path);
                }
            }
        } catch (Exception ex) {
            ExtentTestManager.getTest().fail(details + " (failed to attach media: " + ex.getMessage() + ")");
        }

        ExtentTestManager.endTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String reason = result.getThrowable() == null ? "" : result.getThrowable().toString();
        ExtentTestManager.getTest().skip("Test skipped: " + reason);
        ExtentTestManager.endTest();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // not used
    }
}
