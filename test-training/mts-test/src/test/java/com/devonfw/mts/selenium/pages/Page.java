package com.devonfw.mts.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Example solution for exercise 3 - Book Table Page and Common Page class */
public class Page {
	/** Global Contants */
	public static final String HOME = "http://localhost:8081/restaurant";
	public static final String MENU = "MENU";
	public static final String BOOKTABLE = "BOOKTABLE";
	
	/** The WebDrivers */
	static WebDriver sDriver = new ChromeDriver();	
	static WebDriverWait sDriverWait = new WebDriverWait(sDriver, 5);
	
	/** Shared / reused elements */
	private static final By sErrorPanel = By.cssSelector("simple-snack-bar");
	
	/** Public page access */
	public static void navigateTo(String url) {
		sDriver.get(url);
	}
	
	/** Nuclear option : avoid usage of this method and try to find a elements deterministically */
	public static void pause(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}			
	}
	
	/** Return the popup text return success or failure messages */
	public static String getPopupMessage() {
		return sDriverWait.until(f -> f.findElement(sErrorPanel)).getText();
	}
}	
