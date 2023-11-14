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

public class NavigationTest {

	private WebDriver driver;
	private String homeURL = "https://subscription.packtpub.com/";
	private String searchInputValue = "Java";
	
	@BeforeTest
	public void setUp() {
		driver = new ChromeDriver();
		System.out.println("Browser started");
		driver.get(homeURL);
		driver.manage().window().maximize();
	}

	@Test
	public void testNavigation() throws UnsupportedEncodingException, InterruptedException, IndexOutOfBoundsException {
				System.out.println("NAVIGATION BAR TEST STARTED");
				//Check logo
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
				WebElement packtLogo = driver.findElement(By.className("navbar-brand"));
				String logoURL = packtLogo.getAttribute("href");
				packtLogo.click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
				System.out.println("LOGO "+driver.getCurrentUrl());
				assertPageNavigation(homeURL);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				
				//Check search bar
				WebElement searchBar = driver.findElement(By.tagName("input"));
				WebElement searchButton = driver.findElement(By.className("fa-search"));
				searchBar.sendKeys(searchInputValue);
				searchButton.click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				System.out.println("SEARCH BAR TEST "+driver.getCurrentUrl());
				assertPageNavigation("https://subscription.packtpub.com/search?query="+searchInputValue);
				driver.navigate().to(homeURL);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				
				//CheckCart
				WebElement cartButton = driver.findElement(By.id("cart-btn__BV_toggle_"));
				cartButton.click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				System.out.println("CART "+driver.getCurrentUrl());
				driver.navigate().to(homeURL);
				//assertPageNavigation();
				
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		List<WebElement> navElements = driver.findElements(By.cssSelector(".navbar-nav a.nav-link"));
		int navElementsSize = navElements.size();

		for (int i = 0; i < navElementsSize; i++) {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
			System.out.println(navElementsSize);
			WebElement navElement = navElements.get(i);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			if(navElement.isDisplayed())
			{
				String navText = navElement.getText().trim();
				System.out.println(navElement.getText().trim());
				// Get the text and href attribute of the navigation element
				if (!isFormElement(navElement) && !isInputElement(navElement)) {
					String expectedURL = navElement.getAttribute("href");
					expectedURL = URLDecoder.decode(expectedURL, "UTF-8");

					// Click on the navigation element
					try {
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
						navElement.click();

						// Wait for a fixed time
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
						// Thread.sleep(5000); // Wait for 5 seconds

						// Print the current URL
						System.out.println(driver.getCurrentUrl());

						// Verify that the page has navigated to the correct destination
						assertPageNavigation(expectedURL);

					} catch (Exception e) {
						
						e.printStackTrace();
					}
					finally {
						// Navigate back to the home URL for the next iteration
						if (navText.equals("Sign In"))
						{
							driver.navigate().back();
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
						}
						else
						{
							driver.navigate().to(homeURL);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
						}
							

						/*
						 * WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
						 * wait.until(ExpectedConditions.urlContains(homeURL));
						 */
					}
				}
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
			navElements = driver.findElements(By.cssSelector(".navbar-nav a.nav-link"));

		}
		
		
		
		
	}

	private boolean isInputElement(WebElement navElement) {
		String tagName = navElement.getTagName();
		return tagName.equalsIgnoreCase("input");
	}

	private boolean isFormElement(WebElement element) {
		// Check if the element is a form element based on its tag name
		String tagName = element.getTagName();
		return tagName.equalsIgnoreCase("form");
	}

	private void assertPageNavigation(String expectedPageURL)
			throws InterruptedException, UnsupportedEncodingException {
		// Verify that the actual page URL contains the expected page URL
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		String actualPageURL = URLDecoder.decode(driver.getCurrentUrl(), "UTF-8");
		assert actualPageURL.contains(expectedPageURL)
				: "Navigation to " + expectedPageURL + " failed. Actual URL is " + actualPageURL;
	}

	@AfterTest
	public void tearDown() {
		// Close the browser
		driver.quit();
	}
}
