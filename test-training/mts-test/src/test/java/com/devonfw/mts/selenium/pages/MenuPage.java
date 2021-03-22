package com.devonfw.mts.selenium.pages;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** Example solution for exercise 5 - Filtering by slider */
public class MenuPage extends Page {
	public static final String MENU = "http://localhost:8081/menu";
	
	private static By sFilterPanelExpandButton = By.cssSelector("body > public-main > div > div > div > mat-sidenav-container > mat-sidenav-content > public-menu > own-menu-filters > form > mat-expansion-panel");
	private static By sPriceSlider = By.cssSelector("#cdk-accordion-child-0 > div > div > div > div:nth-child(1) > mat-slider");	
	private static By sSliderXPath = By.xpath("//*[@id=\"cdk-accordion-child-0\"]/div/div/div/div[1]/mat-slider");
	private static By sApplyFilter = By.cssSelector("body > public-main > div > div > div > mat-sidenav-container > mat-sidenav-content > public-menu > own-menu-filters > form > div.filter-actions > button.mat-focus-indicator.apply-filter.mat-button.mat-button-base.mat-accent");
	private static By allPriceTags = By.xpath("//span[@class='price']");
	private static By filterChilds = By.xpath("/html/body/public-main/div/div/div/mat-sidenav-container/mat-sidenav-content/public-menu/own-menu-filters/form/div[2]/button[2]/div[1]/descendant::*");
	
	
	private static WebElement getSlider() {		
		return sDriver.findElement(sPriceSlider);
	}
	
	private static WebElement getApplyFilterButton() {
		return sDriver.findElement(sApplyFilter);
	}
	
	private static List<WebElement> getPriceTags() {
		return sDriver.findElements(allPriceTags);
	}
	
	private static void applyFilters() {
		getApplyFilterButton().click();

		// Wait for finish filtering.
		sDriverWait.until(ExpectedConditions.invisibilityOf(sDriver.findElement(filterChilds)));
	}
	
	private static void setPriceSlider(int price) {
		WebElement slider = getSlider();
		
		double maxValue = Double.parseDouble(slider.getAttribute("aria-valuemax"));
		int sliderW = slider.getSize().width;
		
		Actions action = new Actions(sDriver);

		// INFO: When using the W3C Action commands, offsets are from the center of element
		int test = (int) (price*sliderW / maxValue) - sliderW / 2;	
		action.moveToElement(slider, test, slider.getSize().height / 2).click().build().perform();
		
		String value = sDriverWait.until(x -> x.findElement(sSliderXPath)).getAttribute("aria-valuenow");
		assertEquals(String.valueOf(price), value);
	}
	
	private static void showFilters() {
		sDriver.findElement(sFilterPanelExpandButton).click();
		sDriverWait.until(x -> x.findElement(sPriceSlider).isDisplayed());
	}
	
	public static void navigateTo(String url) {
		sDriver.get(url);
		sDriverWait.until(ExpectedConditions.invisibilityOf(sDriver.findElement(sPriceSlider)));
	}
	
	public static void setPriceFilter(int price) throws Throwable {
		showFilters();
		setPriceSlider(price);
		applyFilters();
	}
	
	public static boolean checkAllPricesAreBelowOrEqual(double maxPrice) {
		List<WebElement> priceTags = getPriceTags();
		boolean isLowerOrEqual = true;
		
		if(priceTags.isEmpty()) {
			return false;
		}
		else {
			for(WebElement priceTag : priceTags) {
				String priceWithoutCurrency = priceTag.getText().replace("€", "").trim();
				double price = Double.parseDouble(priceWithoutCurrency);
				if (price >= maxPrice) {
					isLowerOrEqual = false;
					break;
				}
			}
		}
		return isLowerOrEqual;
	}
}
