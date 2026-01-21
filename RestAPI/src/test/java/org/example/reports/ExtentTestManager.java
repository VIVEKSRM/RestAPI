package org.example.reports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static synchronized void startTest(ExtentTest test) {
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void endTest() {
        extentTest.remove();
    }
}
