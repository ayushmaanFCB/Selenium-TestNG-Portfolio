package main.java.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

public class BlogPageTests {

    private String baseUrl;

    @BeforeClass
    @Parameters("baseUrl")  // Receives the baseUrl from the testng.xml file
    public void setup(@Optional("http://localhost:8000") String baseUrl) {
        this.baseUrl = baseUrl; // Set the baseUrl to the value passed from testng.xml
        WebDriverSetup.getDriver(); // Initializes WebDriver (assuming the driver is set up here)
        Reporter.log("Starting Blog Page Tests...", true);
    }

    @Test
    public void testBlogPage() {
        // Initialize WebDriver using WebDriverSetup class
        WebDriver driver = WebDriverSetup.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Navigate to Blog Page
            driver.get(baseUrl + "/blog");

            // Bypass loading animation
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));

            // Validate presence of the blog section
            List<WebElement> blogCards = driver.findElements(By.className("card"));
            Assert.assertFalse(blogCards.isEmpty(), "No blogs are displayed on the Blog page!");
            Reporter.log("Blog page contains " + blogCards.size() + " blogs.", true);

            // Apply "Sort by Date" filter (newest first)
            Select sortDropdown = new Select(driver.findElement(By.id("sort")));
            sortDropdown.selectByValue("date_desc");
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            // Wait for the page to reload with sorted blogs
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card")));

            // Refetch blog cards after filter application
            List<WebElement> updatedBlogCards = driver.findElements(By.className("card"));
            Assert.assertFalse(updatedBlogCards.isEmpty(), "No blogs displayed after applying 'Sort by Date' filter!");
            Reporter.log("Filter applied: Sorted by Date (Newest First).", true);

            // Apply "Blog Type" filter (e.g., Projects)
            Select typeDropdown = new Select(driver.findElement(By.id("type")));
            typeDropdown.selectByValue("project");
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            // Wait for the page to reload with filtered blogs
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card")));

            // Validate filtered blogs
            updatedBlogCards = driver.findElements(By.className("card"));
            Assert.assertFalse(updatedBlogCards.isEmpty(), "No blogs displayed after applying 'Blog Type' filter!");
            Reporter.log("Filter applied: Blog Type - Project. " + updatedBlogCards.size() + " blogs displayed.", true);

            // Apply "Date Range" filter
            WebElement startDate = driver.findElement(By.id("date_range_start"));
            WebElement endDate = driver.findElement(By.id("date_range_end"));
            startDate.clear();
            startDate.sendKeys("2023-01-01");
            endDate.clear();
            endDate.sendKeys("2023-12-31");
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            // Wait for the page to reload with filtered blogs
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card")));

            updatedBlogCards = driver.findElements(By.className("card"));
            Assert.assertFalse(updatedBlogCards.isEmpty(), "No blogs displayed after applying 'Date Range' filter!");
            Reporter.log("Filter applied: Date Range from 2023-01-01 to 2023-12-31.", true);

            // Validate first blog details (title and date)
            WebElement firstBlog = updatedBlogCards.get(1);

            WebElement blogDate = firstBlog.findElement(By.className("card-subtitle"));
            Assert.assertNotNull(blogDate.getText(), "First blog date is not displayed!");
            Reporter.log("First blog date is displayed correctly after filters.", true);

            // Validate upvote functionality for the first blog
            WebElement upvoteButton = firstBlog.findElement(By.cssSelector(".btn-outline-success"));
            String upvoteTextBefore = upvoteButton.getText();
            upvoteButton.click();

            // Wait for upvote action to reflect (simple wait for demonstration)
            Thread.sleep(5000); // Avoid thread.sleep in production; use explicit waits if possible.
            String upvoteTextAfter = upvoteButton.getText();
            Assert.assertNotEquals(upvoteTextBefore, upvoteTextAfter, "Upvote did not update as expected!");
            Reporter.log("Upvote functionality validated for the first blog.", true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void teardown() {
        WebDriverSetup.closeDriver();
    }
}
