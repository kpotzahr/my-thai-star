package com.devonfw.mts.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/** Example solution for exercise 3 and 4 - Book Table Page and Common Page class */
public final class BookTablePage extends Page {
	public static final String BOOK_TABLE = "http://localhost:8081/bookTable";
	
	private static By submitBookingDateButton = By.xpath("/html/body/div[3]/div[2]/div/owl-dialog-container/owl-date-time-container/div[2]/div/button[2]");
	private static By dateInput = By.xpath("/html/body/public-main/div/div/div/mat-sidenav-container/mat-sidenav-content/public-book-table/mat-card/mat-tab-group/div/mat-tab-body[1]/div/div/div[2]/form/div[2]/div[1]/mat-form-field/div/div[1]/div/input");
	private static By nameInput = By.xpath("/html/body/public-main/div/div/div/mat-sidenav-container/mat-sidenav-content/public-book-table/mat-card/mat-tab-group/div/mat-tab-body[1]/div/div/div[2]/form/div[2]/div[2]/mat-form-field/div/div[1]/div/input");
	private static By emailInput = By.xpath("/html/body/public-main/div/div/div/mat-sidenav-container/mat-sidenav-content/public-book-table/mat-card/mat-tab-group/div/mat-tab-body[1]/div/div/div[2]/form/div[2]/div[3]/mat-form-field/div/div[1]/div/input");
	private static By guestInput = By.xpath("/html/body/public-main/div/div/div/mat-sidenav-container/mat-sidenav-content/public-book-table/mat-card/mat-tab-group/div/mat-tab-body[1]/div/div/div[2]/form/div[2]/div[4]/mat-form-field/div/div[1]/div/input");
	private static By acceptTerms = By.xpath("/html/body/public-main/div/div/div/mat-sidenav-container/mat-sidenav-content/public-book-table/mat-card/mat-tab-group/div/mat-tab-body[1]/div/div/div[2]/form/div[2]/div[5]/mat-checkbox/label/div");
	private static By bookTableButton = By.name("bookTableSubmit");
	private static By bookTableConfirm = By.name("bookTableConfirm");
	
	private static WebElement getSubmitBookingDateButton() {
		return sDriver.findElement(submitBookingDateButton);
	}
	
	private static WebElement getDateField() {
		return sDriver.findElement(dateInput);
	}
	
	private static WebElement getNameInput() {
		return sDriver.findElement(nameInput);
	}
	
	private static WebElement getEMailInput() {
		return sDriver.findElement(emailInput);
	}
	
	private static WebElement getNoOfGuestInput() {
		return sDriver.findElement(guestInput);
	}
	
	private static WebElement getAcceptTermsCheckbox() {
		return sDriver.findElement(acceptTerms);
	}
	
	private static WebElement getBookTableButton() {
		return sDriverWait.until(driver -> driver.findElement(bookTableButton));
	}
	
	private static WebElement getBookTableConfirm() {
		return sDriverWait.until(driver -> driver.findElement(bookTableConfirm));
	}
	
	private static void clickBookingDate() {
		getDateField().click();
	}
		
	private static void clickSubmitBookingDate() {
		getSubmitBookingDateButton().click();
	}
		
	private static void setName(String name) {
		getNameInput().sendKeys(name);
	}
	
	private static void setEMail(String mail) {
		getEMailInput().sendKeys(mail);
	}
	
	private static void setNoOfGuests(String noOfGuests) {
		getNoOfGuestInput().sendKeys(noOfGuests);
	}
	
	private static void setAccptTerms(boolean accept) {
		getAcceptTermsCheckbox().click();
	}
	
	private static void submitBookTable() {
		getBookTableButton().click();
	}
	
	private static void confirmBookTable() {
		getBookTableConfirm().click();
	}
	
	public static void bookTable(String name, String eMail, String noOfGuests, boolean acceptTerms) {
		clickBookingDate();
		pause(500);		
		clickSubmitBookingDate();
		setName(name);
		setEMail(eMail);
		setNoOfGuests(noOfGuests);
		setAccptTerms(acceptTerms);
		submitBookTable();
		confirmBookTable();
	}
}
