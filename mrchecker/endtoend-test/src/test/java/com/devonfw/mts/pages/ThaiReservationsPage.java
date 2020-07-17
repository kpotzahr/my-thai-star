package com.devonfw.mts.pages;

import com.capgemini.mrchecker.selenium.core.BasePage;
import com.capgemini.mrchecker.selenium.core.newDrivers.elementType.Button;
import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.devonfw.mts.common.data.Reservation;
import com.devonfw.mts.common.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class ThaiReservationsPage extends BasePage {

  /** Reservations table search criteria */
  private static final By reservationsTableSearch = By.xpath("//tbody/tr");

  /** Search bar search criteria*/
  private static final By searchBarFilter = By.cssSelector("#mat-expansion-panel-header-1");

  /** Email input search criteria*/
  private static final By emailInputSearch = By.xpath("//input[@name=\"email\"]");

  /** submit data button search criteria */
  private static final By submitButtonSearch = By.xpath("//button[@type='submit']");

  private static final By COLUMN_BOOKING_DATE_SEARCH = By.className("cdk-column-bookingDate");
  private static final By COLUMN_EMAIL_SEARCH = By.className("cdk-column-email");
  private static final By COLUMN_BOOKING_TOKEN_SEARCH = By.className("cdk-column-bookingToken");
  private static final String DATE_FORMAT_UI = "MMM dd, yyyy hh:mm a";
  private static final String DATE_FORMAT_INTERNAL = "MM/dd/yyyy hh:mm a";

  /** Map to store email/reservation id data */
  private Map<String, List<String>> tableData;

  /**
   * {@inheritDoc}
   * */
  @Override
  public boolean isLoaded() {
    getDriver().waitForPageLoaded();

    return getDriver().getCurrentUrl().contains("reservations");
  }

  /**
   * {@inheritDoc}
   * */
  @Override
  public void load() {
    BFLogger.logError("MyThaiStar reservation page was not loaded.");
  }

  /**
   * {@inheritDoc}
   * */
  @Override
  public String pageTitle() {
    return "";
  }

  /**
   * Method to get the reservations for a given email
   *
   * @return List<String> a list of booking ids for that email
   */
  public List<String> findReservationsIdByEmail(String email) {

    return this.tableData.getOrDefault(email, new LinkedList<String>());
  }

  /**
   * Retrieves the list of reservations for a given email
   *
   * @param email
   * @return List of reservations by email
   */
  public HashMap<String, List<Reservation>> searchDatesByEmail(String email) {

    WebElement searchBar = getDriver().findElementDynamic(searchBarFilter);

    JavascriptExecutor js = (JavascriptExecutor) getDriver();
    js.executeScript("arguments[0].click()", searchBar);

    try {
      getDriver().findElementDynamic(By.xpath("adasd"), 2);
    } catch (Exception e) {

    }

    int index = 1;

    Utils.sendKeysWithCheck(email, emailInputSearch, getDriver(), getWebDriverWait(), index);
    WebElement button = getDriver().findElementDynamic(submitButtonSearch);
    button.click();

    try {
      getDriver().findElementDynamics(By.cssSelector("this-selector-doesnt-exist"), 2);
    } catch (Exception e) {

    }

    HashMap<String, List<Reservation>> idReservations = new HashMap<>();
    return getReservationsShownByDate(idReservations);
  }

  /**
   * List of reservations by date
   *
   * @param List of reservations
   * @return List of reservations by date
   */
  private HashMap<String, List<Reservation>> getReservationsShownByDate(
      HashMap<String, List<Reservation>> idReservations) {

    List<WebElement> reservations;
    List<Reservation> reservationsByDate;
    String date, id, email;

    reservations = getDriver().findElementDynamics(reservationsTableSearch);

    for (int i = 1; i <= reservations.size(); i++) {

      date = getDriver().findElementDynamic(findDataCell(i, 1)).getText();
      email = getDriver().findElementDynamic(findDataCell(i, 2)).getText();
      id = getDriver().findElementDynamic(findDataCell(i, 3)).getText();
      try {
        date = Utils.changeDateFormat(date, "MMM dd, yyyy hh:mm a", "MM/dd/yyyy hh:mm a");
      } catch (ParseException e) {
        System.err.println("Date not formated properly at getReservationsShownByDate in ThaiReservationsPage");
        e.printStackTrace();
      }

      reservationsByDate = idReservations.getOrDefault(date, new LinkedList<Reservation>());
      reservationsByDate.add(new Reservation(date, email, id));

      idReservations.put(date, reservationsByDate);
    }

    return idReservations;
  }

  /** Return the search criteria by xpath for a cell
   *
   * @param indexRow.
   * @param indexCol
   *
   * @return search criteria
   * */
  public By findDataCell(int indexRow, int indexCol) {
    return By.xpath("//tbody[@class='td-data-table-body']/tr[" + indexRow + "]/td[" + indexCol + "]//span");
  }

  /**
   * Method to get the reservations for a given email
   *
   * @return List<String> a list of booking ids for that email
   */
  public List<Reservation> getReservationsForEmail(String email)  {
    Button searchBar = getDriver().elementButton(searchBarFilter);
    getWebDriverWait().until(ExpectedConditions.elementToBeClickable(searchBar.getElement()));
    searchBar.click();
    Utils.sendKeysWithCheck(email, emailInputSearch, getDriver(), getWebDriverWait(), 1);
    getDriver().findElementDynamic(submitButtonSearch).click();

    return getReservations().stream().filter(r -> r.getEmail().equals(email)).collect(Collectors.toList());
  }

  private List<Reservation> getReservations() {
    try {
      return getReservationsInternal();
    } catch (StaleElementReferenceException exc) {
      return getReservationsInternal();
    }
  }


  private List<Reservation> getReservationsInternal() {
    List<Reservation> reservations = new ArrayList<>();

    List<WebElement> reservationsLines = getDriver().findElementDynamics(reservationsTableSearch);

    for (WebElement reservationLine : reservationsLines) {
      String date = reformatReservationDate(reservationLine.findElement(COLUMN_BOOKING_DATE_SEARCH).getText());
      String email = reservationLine.findElement(COLUMN_EMAIL_SEARCH).getText();
      String id = reservationLine.findElement(COLUMN_BOOKING_TOKEN_SEARCH).getText();
      reservations.add(new Reservation(date, email, id));

    }
    return reservations;
  }

  private String reformatReservationDate(String dateFromTable) {
    try {
      return Utils.changeDateFormat(dateFromTable, DATE_FORMAT_UI, DATE_FORMAT_INTERNAL);
    } catch (ParseException exc) {
      throw new IllegalArgumentException(
              "Date" + dateFromTable + " not formated properly at getReservationsShownByDate in ThaiReservationsPage", exc);
    }
  }

}
