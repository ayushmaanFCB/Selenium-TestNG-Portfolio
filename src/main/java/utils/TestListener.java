package main.java.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.logging.Logger;

public class TestListener implements ITestListener {

    private final Logger logger;

    public TestListener(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onTestStart(ITestResult result) {
        String message = "Starting test: " + result.getMethod().getMethodName();
        System.out.println(message);
        logger.info(message);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String message = "Test passed: " + result.getMethod().getMethodName();
        System.out.println(message);
        logger.info(message);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String message = "Test failed: " + result.getMethod().getMethodName();
        System.err.println(message);
        logger.severe(message);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String message = "Test skipped: " + result.getMethod().getMethodName();
        System.out.println(message);
        logger.warning(message);
    }

    @Override
    public void onStart(ITestContext context) {
        String message = "Starting test suite: " + context.getName();
        System.out.println(message);
        logger.info(message);
    }

    @Override
    public void onFinish(ITestContext context) {
        String message = "Finished test suite: " + context.getName();
        System.out.println(message);
        logger.info(message);
    }
}
