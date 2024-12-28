package main;

import main.java.utils.TestListener;
import org.testng.TestNG;
import java.util.Collections;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Ayushmaan's Flask Portfolio Tests...");

        Logger logger = Logger.getLogger("TestLog");
        Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.SEVERE);
        Logger.getLogger("org.openqa.selenium.chromium.ChromiumDriver").setLevel(Level.SEVERE);
        try {
            FileHandler fileHandler = new FileHandler("test-results.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);

            logger.info("Initializing tests...");
        } catch (Exception e) {
            System.err.println("Failed to set up logging: " + e.getMessage());
        }

        TestNG testng = new TestNG();
        testng.setTestSuites(Collections.singletonList("testng.xml"));

        testng.addListener(new TestListener(logger));

        testng.run();

        logger.info("All tests executed successfully.");
        System.out.println("All tests executed successfully.");
    }
}
