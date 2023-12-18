package com.packtWebsiteAutomation.PacktSiteAutomation;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.packtWebsiteAutomation.configuration.Configuration;

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
		WebElement signIn = driver.findElement(By.xpath("//*[@id=\"packt-navbar-nav\"]/div/a[3]"));
		signIn.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement emailField = driver.findElement(By.id("inline-form-input-username"));
		WebElement passwordField = driver.findElement(By.id("inline-form-input-password"));
		WebElement signInButton = driver
				.findElement(By.xpath("//button[@class='login-page__main__container__login__form__button__login']"));
		String email = Configuration.getEmail();
		String password = Configuration.getPassword();
		emailField.sendKeys(email);
		passwordField.sendKeys(password);
		signInButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

	}

	@Test(priority=5)
	public void testNavigation() throws UnsupportedEncodingException, InterruptedException, IndexOutOfBoundsException {
		System.out.println("NAVIGATION BAR TEST STARTED");
		// Check logo
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement packtLogo = driver.findElement(By.className("navbar-brand"));
		String logoURL = packtLogo.getAttribute("href");
		packtLogo.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
		System.out.println("LOGO " + driver.getCurrentUrl());
		assertPageNavigation(homeURL);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Check search bar
		WebElement searchBar = driver.findElement(By.tagName("input"));
		WebElement searchButton = driver.findElement(By.className("fa-search"));
		searchBar.sendKeys(searchInputValue);
		searchButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		System.out.println("SEARCH BAR TEST " + driver.getCurrentUrl());
		assertPageNavigation("https://subscription.packtpub.com/search?query=" + searchInputValue);
		driver.navigate().to(homeURL);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// CheckCart
		WebElement cartButton = driver.findElement(By.id("cart-btn__BV_toggle_"));
		cartButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		System.out.println("CART " + driver.getCurrentUrl());
		assertPageNavigation("https://www.packtpub.com/checkout");
		driver.navigate().to(homeURL);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// dropdowns
		List<WebElement> dropdownElements = driver
				.findElements(By.xpath("//div[@class='dropdown nav-item']/a[@class='dropdown-toggle nav-link']"));
		int dropdownElementsSize = dropdownElements.size();
		int dropdownElementIndex = 0;
		while(dropdownElementIndex<dropdownElementsSize) {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			dropdownElements = driver
					.findElements(By.xpath("//div[@class='dropdown nav-item']/a[@class='dropdown-toggle nav-link']"));
			WebElement dropdownElement = dropdownElements.get(dropdownElementIndex);
			// String expanded = dropdownElement.getAttribute("aria-expanded");
			if (!checkCollapseStatus(dropdownElement))
				dropdownElement.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			WebElement dropdownMenu = driver.findElement(By.className("dropdown-menu"));
			List<WebElement> dropdownItems = dropdownMenu.findElements(By.tagName("a"));
			int dropdownItemIndex=0;
			while(dropdownItemIndex<dropdownItems.size()){
				WebElement dropdownItem=dropdownItems.get(dropdownItemIndex);
				dropdownElement = dropdownElements.get(dropdownElementIndex);
				if (!checkCollapseStatus(dropdownElement))
					dropdownElement.click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				String dropdownText = dropdownItem.getText().trim();
				String dropdownItemURL = dropdownItem.getAttribute("href");
				dropdownItemURL = URLDecoder.decode(dropdownItemURL, "UTF-8");
				// Click the dropdown item
				dropdownItem.click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
				System.out.println(dropdownText);
				assertPageNavigation(dropdownItemURL);
				driver.navigate().to(homeURL);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				dropdownElements = driver.findElements(
						By.xpath("//div[@class='dropdown nav-item']/a[@class='dropdown-toggle nav-link']"));
				dropdownElementsSize = dropdownElements.size();
				dropdownElement = dropdownElements.get(dropdownElementIndex);
				if (!checkCollapseStatus(dropdownElement))
					dropdownElement.click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				dropdownMenu = driver.findElement(By.className("dropdown-menu"));
				dropdownItems = dropdownMenu.findElements(By.tagName("a"));
				dropdownItemIndex++;
				
			}
			dropdownElements = driver.findElements(
					By.xpath("//div[@class='dropdown nav-item']/a[@class='dropdown-toggle nav-link']"));
			dropdownElementsSize = dropdownElements.size();
			dropdownElement = dropdownElements.get(dropdownElementIndex);
			dropdownElementIndex++;

		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

		//Other nav elements
		List<WebElement> navElements = driver.findElements(By.cssSelector("a.nav-link"));
		int navElementsSize = navElements.size();

		for (int navElementIndex = 0; navElementIndex < navElementsSize; navElementIndex++) {
			// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
			// System.out.println(navElementsSize);
			WebElement navElement = navElements.get(navElementIndex);
			// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			if (navElement.isDisplayed()) {
				String navText = navElement.getText().trim();
				// System.out.println(navElement.getText().trim());
				// Get the text and href attribute of the navigation element
				if (!isFormElement(navElement) && !isInputElement(navElement) && !isDropdownElement(navElement)) {
					String expectedURL = navElement.getAttribute("href");
					expectedURL = URLDecoder.decode(expectedURL, "UTF-8");

					// Click on the navigation element
					try {
						// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
						if (!(navText.equalsIgnoreCase("Sign Out"))) {
							navElement.click();

							// Wait for a fixed time
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

							System.out.println(navText);

							// Verify that the page has navigated to the correct destination
							assertPageNavigation(expectedURL);
						}

					} catch (Exception e) {

						e.printStackTrace();
					} finally {
						// Navigate back to the home URL for the next iteration
						driver.navigate().to(homeURL);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
					}
				}
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
			navElements = driver.findElements(By.cssSelector("a.nav-link"));
		}

		WebElement signOut = driver.findElement(By.xpath("//*[@id=\"packt-navbar\"]/div[4]/a[3]"));
		signOut.click();

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

	private boolean isDropdownElement(WebElement navElement) {
		String className = navElement.getAttribute("class");
		return className.contains("dropdown-toggle");

	}

	public boolean checkCollapseStatus(WebElement collapseElement) {
		boolean collapseStatus = collapseElement.getAttribute("aria-expanded").contains("true");
		// System.out.println(collapseStatus);
		return collapseStatus;
	}

	private void assertPageNavigation(String expectedPageURL)
			throws InterruptedException, UnsupportedEncodingException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		String actualPageURL = URLDecoder.decode(driver.getCurrentUrl(), "UTF-8");
		// Verify that the actual page URL contains the expected page URL
		assert actualPageURL.contains(expectedPageURL)
				: "Navigation to " + expectedPageURL + " failed. Actual URL is " + actualPageURL;
	}

	@AfterTest
	public void tearDown() {
		// Close the browser
		driver.quit();
	}
}