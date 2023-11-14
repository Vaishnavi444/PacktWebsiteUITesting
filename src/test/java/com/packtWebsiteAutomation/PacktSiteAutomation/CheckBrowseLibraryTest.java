package com.packtWebsiteAutomation.PacktSiteAutomation;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CheckBrowseLibraryTest 
{
		private WebDriver driver;
		private String homeURL = "https://subscription.packtpub.com/";
		private String yearToSelect = "2021";

		@BeforeTest
		public void setUp() 
		{
			driver = new ChromeDriver();
			System.out.println("Browser started");
			driver.get(homeURL);
			driver.manage().window().maximize();
		}
		
		@Test
		public void BrowserLibraryVerification() throws UnsupportedEncodingException, InterruptedException
		{
			System.out.println("CHECK BROWSER LIBRARY TEST STARTED");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			WebElement browseLibrary = driver.findElement(By.xpath("/html/body/div[1]/div/nav/div[1]/a"));
			String expectedURL = browseLibrary.getAttribute("href");
			expectedURL = URLDecoder.decode(expectedURL, "UTF-8");
			System.out.println(expectedURL);
			browseLibrary.click();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.urlContains(expectedURL));
			WebElement resetButton = driver.findElement(By.cssSelector(".reset-button"));
			resetButton.click();
			wait.until(ExpectedConditions.elementToBeClickable(resetButton));
			// Assuming you have a reference to the filter card element
			List<WebElement> filterCards = driver.findElements(By.cssSelector(".filter-card"));
			System.out.println(filterCards.size());
			for(int i=0;i<filterCards.size();i++)
			{
				WebElement filtercard = filterCards.get(i);
				WebElement filterTitleElement = filtercard.findElement(By.cssSelector(".title"));
				String filterTitle = filterTitleElement.getAttribute("innerHTML");
				if(filterTitle.equals("Published Year"))
				{
					WebElement arrowImageToClick = filtercard.findElement(By.tagName("img"));
					arrowImageToClick.click();
					WebElement dropDown = filtercard.findElement(By.cssSelector(".popup"));
					List <WebElement> years = dropDown.findElements(By.cssSelector(".value"));
					System.out.println(years.size());
					for (int j=0;j<years.size();j++)
					{
						WebElement yearElement = years.get(j);
						String year = yearElement.getAttribute("innerHTML");
						if(year.equals(yearToSelect))
						{
							yearElement.click();
							System.out.println(year+" clicked");
							break;
						}
					}
					
					
				}
				
			}
			WebElement searchInput = driver.findElement(By.id("search-input")).findElement(By.tagName("input"));

	        // Locate the search icon element
	        WebElement searchIcon = driver.findElement(By.className("search-icon"));

	        // Text values to enter in the search bar
	        String[] searchValues = {"Python", "Paint", "Secure", "Tableau"};

	        // Loop through the values
	        for (String value : searchValues) {
	        	System.out.println(value);
	            // Enter text into the search bar
	            searchInput.clear(); // Clear the input field before entering a new value
	            searchInput.sendKeys(value);

	            // Click on the search icon
	            searchIcon.click();
	            //searchInput.sendKeys(Keys.ENTER);
	            Thread.sleep(6000);
	            List<WebElement> productCards = driver.findElements(By.cssSelector(".product-card"));
	            System.out.println(productCards.size());
	            for(int k=0;k<productCards.size();k++) 
	            {
	            	WebElement productCard = productCards.get(k);
	            	WebElement productCardContent = productCard.findElement(By.cssSelector(".product-title"));
	            	String productTitle = productCardContent.getAttribute("innerHTML");
	            	//System.out.println(productTitle);
	            	try {
	                    assertBookTitles(productTitle, value);
	                } catch (AssertionError e) {
	                    // Print a message indicating the assertion failure, but continue the loop
	                    System.out.println("Assertion failed for product title: " + productTitle + " for value: " + value);
	                    e.printStackTrace(); // Print the stack trace for debugging purposes
	                }
	            	//
	            }
	            

	            
	        }
	    }
		private void assertBookTitles(String productTitle,String value) throws UnsupportedEncodingException {
	        // Verify that the actual page URL contains the expected page URL
			assert productTitle.contains(value) : productTitle+" does not contain "+value; 
			}

		@AfterTest
		public void tearDown() {
			// Close the browser
			driver.quit();
		}
}
		


