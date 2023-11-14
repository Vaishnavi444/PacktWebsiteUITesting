package com.packtWebsiteAutomation.PacktSiteAutomation;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class WebPageElementTest {

    private WebDriver driver;


	@BeforeTest
    public void setUp() {
        // Set the path of the ChromeDriver executable
    	driver = new ChromeDriver();
		System.out.println("Browser started");
        // Navigate to the webpage
		String url = "https://subscription.packtpub.com/";
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
    }

	/*
	 * @Test public void testHeaderElement() { // Example: Test the header text
	 * WebElement headerElement =
	 * driver.findElement(By.xpath("/html/body/div[1]/div/nav"));
	 * Assert.assertEquals(headerElement.getText(), "Expected Header Text"); }
	 */
    
    
    @Test
    public void testLogo() {
    	System.out.println("WEB ELEMENTS TEST STARTED -- LOGO");
    	WebElement logo = driver.findElement(By.cssSelector(".logo"));
        AssertJUnit.assertTrue(logo.isDisplayed());
    }
    
    @Test
    public void testButtonColor() {
    	System.out.println("WEB ELEMENTS TEST STARTED -- BUTTON COLOR");
        WebElement buttonElement = driver.findElement(By.cssSelector(".button--more-info"));
        String buttonColor = buttonElement.getCssValue("color");
        AssertJUnit.assertEquals(buttonColor, "rgba(236, 102, 17, 1)");// color: orange
    }
    
    @Test
    public void testSearchPosition() {
    	System.out.println("WEB ELEMENTS TEST STARTED -- SEARCH BAR POSITION");
    	WebElement searchButton = driver.findElement(By.xpath("/html/body/div[1]/div/nav/form/input"));

        // Verify the positioning 
    	int actualX = searchButton.getLocation().getX();
    	int actualY = searchButton.getLocation().getY();
    	System.out.println("Positions: X = "+actualX+", Y = "+actualY);
        AssertJUnit.assertTrue(actualX > 0);
        AssertJUnit.assertTrue(actualY > 0);
    }
    
    @Test
    public void testText() {
    	System.out.println("WEB ELEMENTS TEST STARTED -- TEXT VERIFICATION");
    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    	WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div/nav/div[2]/a/div/button"));
    	String textValue = button.getText();
    	AssertJUnit.assertEquals(textValue, "Advanced Search");
    }

    @AfterTest
    public void tearDown() {
        // Close the browser after all tests are executed
        {
            driver.quit();
        }
    }
}
