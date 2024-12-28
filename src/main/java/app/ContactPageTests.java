package main.java.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import main.java.utils.WebDriverSetup;

import java.time.Duration;

public class ContactPageTests {

    private String baseUrl;

    @BeforeClass
    @Parameters("baseUrl")  // Receives the baseUrl from the testng.xml file
    public void setup(@Optional("http://localhost:8000") String baseUrl) {
        this.baseUrl = baseUrl; // Set the baseUrl to the value passed from testng.xml
        WebDriverSetup.getDriver(); // Initializes WebDriver (assuming the driver is set up here)
        Reporter.log("Starting Contact Page Tests...", true);
    }

    @Test
    public void testContactPage() {
        // Initialize WebDriver using WebDriverSetup class
        WebDriver driver = WebDriverSetup.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Navigate to Contact Page
            driver.get(baseUrl + "/contact");

            // Bypass loading animation
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));

            // Validate social links are present
            Assert.assertTrue(driver.findElement(By.linkText("GitHub")).isDisplayed(), "GitHub link is missing!");
            Assert.assertTrue(driver.findElement(By.linkText("LinkedIn")).isDisplayed(), "LinkedIn link is missing!");
            Assert.assertTrue(driver.findElement(By.linkText("Email")).isDisplayed(), "Email link is missing!");
            Assert.assertTrue(driver.findElement(By.linkText("Phone")).isDisplayed(), "Phone link is missing!");
            Reporter.log("Social links are displayed successfully.", true);

            // Validate presence of contact form
            WebElement contactForm = driver.findElement(By.tagName("form"));
            Assert.assertNotNull(contactForm, "Contact form is not displayed!");
            Reporter.log("Contact form is displayed successfully.", true);

            // Fill in the contact form
            driver.findElement(By.id("name")).sendKeys("Test User");
            driver.findElement(By.id("email")).sendKeys("testuser@example.com");
            driver.findElement(By.id("phone")).sendKeys("1234567890");
            driver.findElement(By.id("organization")).sendKeys("Test Organization");
            driver.findElement(By.id("position")).sendKeys("Tester");
            driver.findElement(By.id("website")).sendKeys("https://example.com");
            driver.findElement(By.id("linkedin")).sendKeys("https://linkedin.com/in/testuser");
            driver.findElement(By.id("message")).sendKeys("This is a test message.");

            // Submit the form
            WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
            Assert.assertNotNull(submitButton, "Submit button is not displayed!");
            submitButton.click();

            // Wait for confirmation (assuming a confirmation page or message appears)
            wait.until(ExpectedConditions.urlContains("/"));
            Reporter.log("Form submitted successfully, navigated to Home.", true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void teardown() {
        WebDriverSetup.closeDriver();
    }
}
