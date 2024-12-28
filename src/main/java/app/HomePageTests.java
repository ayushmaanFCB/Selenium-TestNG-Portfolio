package main.java.app;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import main.java.utils.WebDriverSetup;

public class HomePageTests {

    private String baseUrl;

    @BeforeClass
    @Parameters("baseUrl")  // Receives the baseUrl from the testng.xml file
    public void setup(@Optional("http://localhost:8000") String baseUrl) {
        this.baseUrl = baseUrl; // Set the baseUrl to the value passed from testng.xml
        WebDriverSetup.getDriver();
        Reporter.log("Starting Home Page Tests...", true);
    }

    @Test
    public void testHomePageContent() {
        WebDriverSetup.getDriver().get(baseUrl + "/home");
        String pageTitle = WebDriverSetup.getDriver().getTitle();
        Reporter.log("Testing Home Page - Title: " + pageTitle, true);

        // Validate page title
        if (pageTitle != null) {
            Assert.assertTrue(pageTitle.contains("AyushMaan's Portfolio"), "Home Page title mismatch!");
        } else {
            Reporter.log("<font color='red'>Page Title not found</font>", true);
        }

        // Validate main content
        try {
            WebElement mainContent = WebDriverSetup.getDriver().findElement(By.id("main-content"));
            Assert.assertTrue(mainContent.isDisplayed(), "Main Content is not displayed!");
            Reporter.log("Main Content is rendered successfully.", true);
        } catch (NoSuchElementException e) {
            Reporter.log("<font color='red'>Main Content not found</font>", true);
        }

        // Validate navbar
        try {
            WebElement navbar = WebDriverSetup.getDriver().findElement(By.className("navbar"));
            Assert.assertTrue(navbar.isDisplayed(), "Navbar is not displayed!");
            Reporter.log("Navbar is rendered successfully.", true);
        } catch (NoSuchElementException e) {
            Reporter.log("<font color='red'>Navbar not found</font>", true);
        }

        // Validate footer
        try {
            WebElement footer = WebDriverSetup.getDriver().findElement(By.tagName("footer"));
            Assert.assertTrue(footer.isDisplayed(), "Footer is not displayed!");
            Reporter.log("The Footer is rendered successfully.", true);
        } catch (NoSuchElementException e) {
            Reporter.log("<font color='red'>Footer not found</font>", true);
        }

        Reporter.log("Home Page validation completed.", true);
    }

    @AfterClass
    public void teardown() {
        WebDriverSetup.closeDriver();
    }
}
