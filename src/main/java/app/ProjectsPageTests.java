package main.java.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import main.java.utils.WebDriverSetup;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ProjectsPageTests {

    private static final String PROJECT_CARD_CLASS = "project-card";
    private String baseUrl;

    @BeforeClass
    @Parameters("baseUrl")  // Receives the baseUrl from the testng.xml file
    public void setup(@Optional("http://localhost:8000") String baseUrl) {
        this.baseUrl = baseUrl; // Set the baseUrl to the value passed from testng.xml
        WebDriverSetup.getDriver(); // Initializes WebDriver (assuming the driver is set up here)
        Reporter.log("Starting Projects Page Tests...", true);
    }

    @Test
    public void testProjectsPage() {
        // Initialize WebDriver using WebDriverSetup class
        WebDriver driver = WebDriverSetup.getDriver();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Navigate to Projects Page
            driver.get(baseUrl + "/projects");

            // Bypass loading animation
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));

            // Validate all project cards are displayed
            List<WebElement> projectCards = driver.findElements(By.className(PROJECT_CARD_CLASS));
            Assert.assertFalse(projectCards.isEmpty(), "No project cards found on the Projects page!");
            Reporter.log("Projects Page contains " + projectCards.size() + " project cards.", true);

            // Randomly click on 2-3 project links to validate
            Random random = new Random();
            int randomIndex = random.nextInt(projectCards.size());
            WebElement projectCard = projectCards.get(randomIndex);

            // Wait for the element to be clickable
            WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(projectCard));

            String projectLink = clickableElement.getAttribute("href");
            if (projectLink != null) {
                driver.navigate().to(projectLink);
            } else {
                System.err.println("Failed to retrieve project GitHub link");
            }
            wait.until(ExpectedConditions.urlToBe(projectLink));
            Reporter.log("Successfully navigated to: " + projectLink, true);

            // Navigate back to Projects page
            driver.navigate().back();
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(PROJECT_CARD_CLASS)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void teardown() {
        WebDriverSetup.closeDriver();
    }
}
