package main.java.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverSetup {
    private static WebDriver driver;
    private static WebDriverWait wait;

    static {
        System.setProperty("webdriver.edge.driver", "C:\\Selenium_Webdriver\\msedgedriver.exe");
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            driver = new EdgeDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        }
        return wait;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
        }
    }
}
