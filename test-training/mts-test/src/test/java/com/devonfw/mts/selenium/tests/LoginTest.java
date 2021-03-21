package com.devonfw.mts.selenium.tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.devonfw.mts.selenium.pages.LoginPage;

public final class LoginTest {
	
	/** Public constants */
	public static final String LANDING_PAGE = "http://localhost:8081/";

	/** Private constants */
	private static final String SUCCESSFUL_LOGIN = "Login successful\nOK";
	
	private static final String USER = "waiter";
	
	private static final String PASSWORD = "waiter";

	/** 
	 * This is a bad example of how to write a Selenium test. Please do not copy.
	 * Simply think of what could have been done better and compare to the good example.
	 */
	@Test
	public void testSuccessfulLoginWithUserWaiterBadExample() {
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8081/");
		
		driver.findElement(By.name("login")).click();
		driver.findElement(By.name("username")).sendKeys("waiter");
		driver.findElement(By.name("password")).sendKeys("waiter");
		driver.findElement(By.name("submitLogin")).click();
		
		WebElement element = new WebDriverWait(driver, 3)
				.until(f -> f.findElement(By.cssSelector("simple-snack-bar")));
		
		assertEquals("Login successful\nOK", element.getText());
	}
	
	/**
	 * A good example of how to write a Selenium tests. Implementation details are hidden in the page objects.
	 */
	@Test
	public void testSuccessfulLoginWithUserWaiterGoodExample() {
		LoginPage.navigateTo(LANDING_PAGE);
		LoginPage.login(USER, PASSWORD);
		assertEquals(SUCCESSFUL_LOGIN, LoginPage.getLoginMessage());
	}
	
	@Test
	public void testUserCancelsLogin() {
		LoginPage.navigateTo(LANDING_PAGE);

		// to be implemented
	}
}
