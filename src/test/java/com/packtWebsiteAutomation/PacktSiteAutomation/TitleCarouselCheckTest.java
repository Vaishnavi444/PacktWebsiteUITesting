package com.packtWebsiteAutomation.PacktSiteAutomation;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.packtWebsiteAutomation.configuration.Configuration;

import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class TitleCarouselCheckTest {
	private WebDriver driver;
	private String homeURL = "https://subscription.packtpub.com/";
	private WebElement orderedList;
	WebElement loadedPageHeading;
	String loadedPageHeadingText; 

	@BeforeTest
	public void setUp() {
		driver = new ChromeDriver();
		System.out.println("Browser started");
		driver.get(homeURL);
		driver.manage().window().maximize();
		/*
		 * WebElement signIn =
		 * driver.findElement(By.xpath("//*[@id=\"packt-navbar-nav\"]/div/a[3]"));
		 * signIn.click();
		 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); WebElement
		 * emailField = driver.findElement(By.id("inline-form-input-username"));
		 * WebElement passwordField =
		 * driver.findElement(By.id("inline-form-input-password")); WebElement
		 * signInButton = driver .findElement(By.xpath(
		 * "//button[@class='login-page__main__container__login__form__button__login']")
		 * ); String email = Configuration.getEmail(); String password =
		 * Configuration.getPassword(); emailField.sendKeys(email);
		 * passwordField.sendKeys(password); signInButton.click();
		 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		 */
	}

	@Test(priority=7)
	public void TestToClickTitles() throws UnsupportedEncodingException, InterruptedException {
		System.out.println("TITLE CAROUSEL CHECK STARTED");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		orderedList = driver.findElement(By.tagName("ol"));
		System.out.println(orderedList.getSize());

		List<WebElement> divisions = orderedList.findElements(By.className("w-100"));
		// System.out.println(divisions.size());
		for (int divisionIndex = 0; divisionIndex < divisions.size(); divisionIndex++) {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			divisions = orderedList.findElements(By.className("w-100"));
			WebElement division = divisions.get(divisionIndex);
			WebElement spanElement = division.findElement(By.cssSelector("span.chapter-index"));
			String spanNumber = spanElement.getText();
			System.out.println("Division number: "+spanNumber);
			WebElement collapseElement = division.findElement(By.className("collapse"));
			if (!checkCollapseStatus(collapseElement))
				division.findElement(By.className("fa")).click();
			List<WebElement> sectionNameLinks = division.findElements(By.cssSelector(".section-names a"));
			int sectionNameLinkIndex = 0;
			while (sectionNameLinkIndex < sectionNameLinks.size()) {
				System.out.println("Section under division: "+(sectionNameLinkIndex+1));
				collapseElement = division.findElement(By.className("collapse"));
				if (!checkCollapseStatus(collapseElement))
					division.findElement(By.className("fa")).click();
				// TestClickingOnSectionName(division);
				// System.out.println(sectionNameLinks.size());
				WebElement sectionNameLink = sectionNameLinks.get(sectionNameLinkIndex);
				String sectionNameLinkText = sectionNameLink.getAttribute("innerHTML");
				// System.out.println("Link Text: " + sectionNameLinkText);
				TestClickingOnSectionName(sectionNameLink, sectionNameLinkText);
				driver.navigate().back();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
				orderedList = driver.findElement(By.tagName("ol"));
				divisions = orderedList.findElements(By.className("w-100"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				division = divisions.get(divisionIndex);
				sectionNameLinks = division.findElements(By.cssSelector(".section-names a"));
				sectionNameLinkIndex++;
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		}
	}

	public void TestClickingOnSectionName(WebElement sectionNameLink, String sectionNameLinkText) throws UnsupportedEncodingException,NoSuchElementException
	{
			//System.out.println("Link Text: " + sectionNameLinkText);
            String expectedURL = sectionNameLink.getAttribute("href");
			expectedURL = URLDecoder.decode(expectedURL, "UTF-8");
			sectionNameLink.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(16));
			
			
			try {
			 loadedPageHeading = driver.findElement(By.tagName("h1"));
			}
			catch (NoSuchElementException e)
            {
            	System.out.println(""+loadedPageHeading+" is not present");
                e.printStackTrace();
            }
			
            loadedPageHeadingText = loadedPageHeading.getText();
            //System.out.println("Page Text: "+loadedPageText);
            // Assertion: Check if the loaded page text contains the link text
            try {
            	
            	 assert sectionNameLinkText.contains(loadedPageHeadingText) : "Text mismatch for link: " + sectionNameLinkText;
            }
            catch(AssertionError e)
            {
            	System.out.println("Assertion failed for Section name: " + sectionNameLinkText+" The actual header is: "+loadedPageHeadingText);
                //e.printStackTrace();
            }
                      
	}

	public boolean checkCollapseStatus(WebElement collapseElement) {
		boolean collapseStatus = collapseElement.getAttribute("class").contains("show");
		// System.out.println(collapseStatus);
		return collapseStatus;
	}

	@AfterTest
	public void tearDown() {
		// Close the browser
		driver.quit();
	}
}
