package com.devonfw.mts.cucumber.pages;

import com.devonfw.mts.cucumber.data.CukesBookingData;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ReservationPage {
    private static final By TABLE_ROWS_SELECTOR = By.xpath("//tbody/tr");
    private static final By FILTER_SELECTOR = By.cssSelector("#mat-expansion-panel-header-1");
    private static final By EMAIL_INPUT_SELECTOR = By.name("email");
    private static final By SUBMIT_SELECTOR = By.xpath("//button[@type='submit']");

    private static final By COLUMN_BOOKING_DATE_SEARCH = By.className("cdk-column-bookingDate");
    private static final By COLUMN_EMAIL_SEARCH = By.className("cdk-column-email");
    private static final By COLUMN_BOOKING_TOKEN_SEARCH = By.className("cdk-column-bookingToken");
    private static final String DATE_FORMAT_UI = "MMM d, yyyy h:mm a";

    @Autowired
    private WidgetHelper helper;

    @Autowired
    private BrowserAccess browserAccess;

    public List<CukesBookingData> getReservations(String email) {
        filterByEmail(email);
        return getReservationsFromTable();
    }

    private void filterByEmail(String email) {
        helper.widget(FILTER_SELECTOR).click();
        List<WebElement> inputs = browserAccess.webDriver().findElements(EMAIL_INPUT_SELECTOR);
        inputs.get(1).sendKeys(email);
        helper.widget(SUBMIT_SELECTOR).click();
    }

    private List<CukesBookingData> getReservationsFromTable() {
        try {
            return getReservationsFromTableInternal();
        } catch (StaleElementReferenceException exc) {
            return getReservationsFromTableInternal();
        }
    }

    private List<CukesBookingData> getReservationsFromTableInternal() {
        List<CukesBookingData> reservations = new ArrayList<>();
        List<WebElement> reservationsLines = browserAccess.webDriver().findElements(TABLE_ROWS_SELECTOR);

        for (WebElement reservationLine : reservationsLines) {
            Instant date = parseDateTime(reservationLine.findElement(COLUMN_BOOKING_DATE_SEARCH).getText());
            String email = reservationLine.findElement(COLUMN_EMAIL_SEARCH).getText();
            String id = reservationLine.findElement(COLUMN_BOOKING_TOKEN_SEARCH).getText();
            reservations.add(new CukesBookingData(date, email, id));
        }
        return reservations;
    }

    private Instant parseDateTime(String dateFromTable) {
        DateTimeFormatter readingFormat = DateTimeFormatter.ofPattern(DATE_FORMAT_UI)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault());

        return readingFormat.parse(dateFromTable, Instant::from);
    }

}
