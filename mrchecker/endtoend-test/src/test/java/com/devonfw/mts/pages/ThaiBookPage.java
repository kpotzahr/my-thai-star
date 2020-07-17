package com.devonfw.mts.pages;

import com.capgemini.mrchecker.selenium.core.BasePage;
import com.capgemini.mrchecker.selenium.core.newDrivers.elementType.InputTextElement;
import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.devonfw.mts.common.data.Reservation;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class ThaiBookPage extends BasePage {

  /** Date field search criteria */
  private static final By dateSearch = By.cssSelector("input[formcontrolname='bookingDate']");

  /** Name input field search criteria */
  private static final By nameSearch = By.cssSelector("input[formcontrolname='name']");

  /** Email input field search criteria */
  private static final By emailSearch = By.cssSelector("input[formcontrolname='email']");

  /** Number of guests search criteria */
  private static final By guestsSearch = By.cssSelector("input[formcontrolname='assistants']");

  /** Check box search criteria */
  private static final By checkboxSearch = By.cssSelector("mat-checkbox[data-name='bookTableTerms']");

  /** Dialog search criteria */
  private static final By dialogSearch = By.className("bgc-green-600");

  private static final String TABLE_SUCCESFULLY_BOOKED = "Table succesfully booked";

  /**
   * {@inheritDoc}
   * */
  @Override
  public boolean isLoaded() {
    WebElement dateInput = getDriver().findElementDynamic(dateSearch);

    return dateInput.isDisplayed();
  }

  /**
   * {@inheritDoc}
   * */
  @Override
  public void load() {
    BFLogger.logError("MyThaiStar book page was not loaded.");
  }

  /**
   * {@inheritDoc}
   * */
  @Override
  public String pageTitle() {
    return "";
  }

    /**
     * @param Date for the reservation
     */
    public void enterTimeAndDate(String Date) {
        InputTextElement dateInput = getDriver().elementInputText(dateSearch);
        dateInput.clearInputText();
        dateInput.setInputText(Date);

    }

    /**
     * @param name used for the reservation.
     */
    public void enterName(String name) {
        InputTextElement nameInput = getDriver().elementInputText(nameSearch);
        nameInput.clearInputText();
        nameInput.setInputText(name);
    }

    /**
     * @param email Email for the reservation form
     */
    public void enterEmail(String email) {
        InputTextElement emailInput = getDriver().elementInputText(emailSearch);
        emailInput.clearInputText();
        emailInput.setInputText(email);
    }

    /**
     * @param amountOfGuests number oof guests for reservation form
     */
    public void enterGuests(String amountOfGuests) {
        InputTextElement guestsInput = getDriver().elementInputText(guestsSearch);
        guestsInput.clearInputText();
        guestsInput.setInputText(amountOfGuests);
    }

    public void enterGuests(int amountOfGuests) {
        enterGuests(String.valueOf(amountOfGuests));
    }

  /**
   * Accept terms and conditions of service
   */
  public void acceptTerms() {
      WebElement checkbox = getDriver().findElementDynamic(checkboxSearch);
      WebElement square = checkbox.findElement(By.className("mat-checkbox-inner-container"));
      JavascriptExecutor js = (JavascriptExecutor) getDriver();
      js.executeScript("arguments[0].click()", square);
  }

    public void acceptTerms(boolean accept) {
        WebElement checkbox = getDriver().findElementDynamic(checkboxSearch);
        String checked = checkbox.findElement(By.cssSelector(".mat-checkbox-input")).getAttribute("aria-checked");
        if (accept != Boolean.parseBoolean(checked)) {
            acceptTerms();
        }
    }


    /**
     * Submit data and book a table
     */
    public ThaiConfirmBookPage clickBookTable() {
        WebElement buttonbutton = getDriver().findElementDynamic(By.name("bookTableSubmit"));
        buttonbutton.click();
        return new ThaiConfirmBookPage();
    }

  /**
   * @param reservation object
   * @return Confirmation dialog.
   */
  public ThaiConfirmBookPage enterBookingData(Reservation reservation) {
    enterTimeAndDate(reservation.getDate());
    enterName(reservation.getName());
    enterEmail(reservation.getEmail());
    enterGuests(reservation.getGuests());
    acceptTerms();
    clickBookTable();

    return new ThaiConfirmBookPage();
  }

  /**
   * @return
   */
  public boolean checkConfirmationDialog() {
    WebElement greenConfirmationDialog = getDriver().findElementDynamic(dialogSearch);

    return greenConfirmationDialog.isDisplayed();
  }

    public boolean isBookingEnabled() {
        return getDriver().findElementDynamic(By.name("bookTableSubmit")).isEnabled();
    }

    public boolean isSuccessMessageShown() {
        String message = getSnackbarMessage();

        int retry = 0;
        while (!message.startsWith(TABLE_SUCCESFULLY_BOOKED) && retry < 10) {
            message = getSnackbarMessage();
            retry++;
        }

        return message.startsWith(TABLE_SUCCESFULLY_BOOKED);
    }

    private String getSnackbarMessage() {
        String message = getDriver().findElementDynamic(By.cssSelector(".mat-snack-bar-container"))
                .findElement(By.cssSelector(".mat-simple-snackbar")).getText();
        return null == message ? "" : message;
    }

}
