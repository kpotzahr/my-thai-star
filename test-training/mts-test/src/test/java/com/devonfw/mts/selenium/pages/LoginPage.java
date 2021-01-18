package com.devonfw.mts.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	/** The WebDrivers */
	private static WebDriver sDriver = new ChromeDriver();	
	private static WebDriverWait sDriverWait = new WebDriverWait(sDriver, 5);

	/** Private page related static constants */
	private static final By sLoginButton = By.name("login");
	private static final By sUsernameField = By.name("username");
	private static final By sPasswordField = By.name("password");
	private static final By sSubmitButton = By.name("submitLogin");
	private static final By errorPanel = By.cssSelector("simple-snack-bar");
	
	/** Public page access */
	
	public static void navigateTo(String url) {
		sDriver.get(url);
	}
	
	/**
	 * Clicks the login button, enters username, password and clicks login. 
	 * 
	 * @param user user id 
	 * @param password password
	 */
	public static void login(String user, String password) {	
		loginButton().click();

		fillUsername(user);
		fillPassword(password);
		
		submitLoginButton().click();
	}
	
	public static String getLoginMessage() {
		return sDriverWait.until(f -> f.findElement(errorPanel)).getText();
	}
	
	/** Private page access */
	private static WebElement loginButton() {
		return sDriver.findElement(sLoginButton);
	}
	
	private static void fillUsername(String name) {
		WebElement usernameField = sDriver.findElement(sUsernameField);
		usernameField.sendKeys(name);
	}
	
	private static void fillPassword(String password) {
		WebElement passwordField = sDriver.findElement(sPasswordField);
		passwordField.sendKeys(password);
	}	
	
	private static WebElement submitLoginButton() {
		return sDriver.findElement(sSubmitButton);
	}
}
