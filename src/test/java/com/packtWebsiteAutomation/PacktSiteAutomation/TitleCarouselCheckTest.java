package com.packtWebsiteAutomation.PacktSiteAutomation;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
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

public class TitleCarouselCheckTest{
	private WebDriver driver;
	private String homeURL = "https://subscription.packtpub.com/";
	private WebElement orderedList;

	@BeforeTest
	public void setUp() 
	{
		driver = new ChromeDriver();
		System.out.println("Browser started");
		driver.get(homeURL);
		driver.manage().window().maximize();
	}
	
	@Test
	public void TestToClickTitles() throws UnsupportedEncodingException, InterruptedException
	{
		System.out.println("TITLE CAROUSEL CHECK STARTED");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		orderedList = driver.findElement(By.tagName("ol"));
			List<WebElement> divisions = orderedList.findElements(By.className("w-100"));
			//System.out.println(divisions.size());
			for (int divisionIndex = 0; divisionIndex < divisions.size(); divisionIndex++) {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				divisions = orderedList.findElements(By.className("w-100"));
    			WebElement division = divisions.get(divisionIndex);
    			WebElement collapseElement = division.findElement(By.className("collapse"));
    			if(!checkCollapseStatus(collapseElement))
					division.findElement(By.className("fa")).click();
    			List<WebElement> sectionNameLinks = division.findElements(By.cssSelector(".section-names a"));
    			for (int sectionNameLinkIndex = 0; sectionNameLinkIndex < sectionNameLinks.size(); sectionNameLinkIndex++) 
    			{
					collapseElement = division.findElement(By.className("collapse"));
					if(!checkCollapseStatus(collapseElement))
						division.findElement(By.className("fa")).click();
					sectionNameLinks = division.findElements(By.cssSelector(".section-names a"));
    				//TestClickingOnSectionName(division);
    				System.out.println(sectionNameLinks.size());
					WebElement sectionNameLink = sectionNameLinks.get(sectionNameLinkIndex);
					String sectionNameLinkText = sectionNameLink.getAttribute("innerHTML");
		            //System.out.println("Link Text: " + sectionNameLinkText);
					TestClickingOnSectionName(sectionNameLink,sectionNameLinkText);
					driver.navigate().back();
					orderedList =driver.findElement(By.tagName("ol"));
					divisions = orderedList.findElements(By.className("w-100"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    			division = divisions.get(sectionNameLinkIndex);
	    			sectionNameLinks = division.findElements(By.cssSelector(".section-names a"));
					}
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		        }
			}
			

	public void TestClickingOnSectionName(WebElement sectionNameLink, String sectionNameLinkText) throws UnsupportedEncodingException
	{
			//System.out.println("Link Text: " + sectionNameLinkText);
            String expectedURL = sectionNameLink.getAttribute("href");
			expectedURL = URLDecoder.decode(expectedURL, "UTF-8");
			sectionNameLink.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
			WebElement loadedPageHeading = driver.findElement(By.tagName("h1"));
            String loadedPageText = loadedPageHeading.getText();
            //System.out.println("Page Text: "+loadedPageText);
            // Assertion: Check if the loaded page text contains the link text
            assert sectionNameLinkText.toString().contains(loadedPageText) : "Text mismatch for link: " + sectionNameLinkText;
            
	}
	
	public boolean checkCollapseStatus(WebElement collapseElement)
	{
		boolean collapseStatus = collapseElement.getAttribute("class").contains("show");
		//System.out.println(collapseStatus);
		return collapseStatus;
	}
	
	@AfterTest
	public void tearDown() {
		// Close the browser
		driver.quit();
	}
}
	
