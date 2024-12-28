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
import java.util.List;

public class SkillsPageTests {

    private static final String SKILL_SECTION_ID = "skills-row"; // ID used for skill rows
    private String baseUrl;

    @BeforeClass
    @Parameters("baseUrl")  // Receives the baseUrl from the testng.xml file
    public void setup(@Optional("http://localhost:8000") String baseUrl) {
        this.baseUrl = baseUrl; // Set the baseUrl to the value passed from testng.xml
        WebDriverSetup.getDriver(); // Initializes WebDriver (assuming the driver is set up here)
        Reporter.log("Starting Skills Page Tests...", true);
    }

    @Test
    public void testSkillsPage() {
        // Initialize WebDriver using WebDriverSetup class
        WebDriver driver = WebDriverSetup.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Navigate to Skills Page
            driver.get(baseUrl + "/skills");

            // Bypass loading animation
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));

            // Validate skill sections are displayed
            List<WebElement> skillSections = driver.findElements(By.id(SKILL_SECTION_ID));
            Assert.assertFalse(skillSections.isEmpty(), "No skill sections found on the Skills page!");
            Reporter.log("Skills Page contains " + skillSections.size() + " skill sections.", true);

            // Validate each section has images
            int count = 0;
            for (WebElement section : skillSections) {
                count += 1;
                String sectionName = section.findElement(By.tagName("h5")).getText();
                List<WebElement> images = section.findElements(By.tagName("img"));
                Assert.assertFalse(images.isEmpty(), "Skill section has no images!");
                Reporter.log("Section: " + sectionName + "\nSkill section contains " + images.size() + " images.", true);
            }

            // Validate navigation within the page using anchor links
            WebElement programmingLanguagesLink = driver.findElement(By.linkText("Programming Languages"));
            programmingLanguagesLink.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("progs")));
            Reporter.log("Navigated to Programming Languages section successfully.", true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void teardown() {
        WebDriverSetup.closeDriver();
    }
}
