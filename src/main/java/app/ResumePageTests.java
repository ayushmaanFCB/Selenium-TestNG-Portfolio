package main.java.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import main.java.utils.WebDriverSetup;

public class ResumePageTests {
    public static void testResumePageContent(String baseUrl) {
        WebDriverSetup.getDriver().get(baseUrl + "/resume");
        WebElement downloadButton = WebDriverSetup.getDriver().findElement(By.id("download-resume"));
        Assert.assertTrue(downloadButton.isDisplayed(), "Resume Download button is missing!");

        String downloadLink = downloadButton.getAttribute("href");
        Assert.assertTrue(downloadLink.endsWith(".pdf"), "Resume download link is not a PDF file!");
        Reporter.log("Resume Page validated successfully. PDF download link: " + downloadLink, true);
    }
}

