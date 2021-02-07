package com.devonfw.mts.cucumber.pages;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class BookingPage {
    private static final By DATE_SELECTOR = By.cssSelector("input[formcontrolname='bookingDate']");
    private static final By NAME_SELECTOR = By.cssSelector("input[formcontrolname='name']");
    private static final By EMAIL_SELECTOR = By.cssSelector("input[formcontrolname='email']");
    private static final By GUESTS_SELECTOR = By.cssSelector("input[formcontrolname='assistants']");
    private static final By ACCEPT_SELECTOR = By.cssSelector("mat-checkbox[data-name='bookTableTerms']");
    private static final By BOOK_TABLE_SELECTOR = By.name("bookTableSubmit");
    private static final By CONFIRM_SELECTOR = By.name("bookTableConfirm");

    private static final String TABLE_SUCCESFULLY_BOOKED = "Table succesfully booked";


    @Autowired
    private WidgetHelper helper;

    public void acceptTerms(boolean accept) {
        helper.checkbox(ACCEPT_SELECTOR).select(accept);
    }

    public boolean isBookingPossible() {
        return helper.button(BOOK_TABLE_SELECTOR).isEnabled();
    }

    public void enterTimeAndDate(String dateTime) {
        helper.inputfield(DATE_SELECTOR).enter(dateTime);
    }

    public void enterTimeAndDate(Instant dateTime) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(dateTime, ZoneId.systemDefault());
        enterTimeAndDate(DateTimeFormatter.ofPattern("MM/dd/yyyy, hh:mm a")
                .format(localDateTime));
    }

    public void enterEmail(String email) {
        helper.inputfield(EMAIL_SELECTOR).enter(email);
    }

    public void enterName(String name) {
        helper.inputfield(NAME_SELECTOR).enter(name);
    }

    public void enterGuests(String numberOfGuests) {
        helper.inputfield(GUESTS_SELECTOR).enter(numberOfGuests);
    }

    public void enterGuests(int numberOfGuests) {
        enterGuests(String.valueOf(numberOfGuests));
    }

    public void bookTableAndConfirm() {
        bookTable();
        helper.button(CONFIRM_SELECTOR).click();
    }

    public void bookTable() {
        helper.button(BOOK_TABLE_SELECTOR).click();
    }

    public boolean isSuccessMessageShown() {
        return helper.snackbar().containsMessage(TABLE_SUCCESFULLY_BOOKED);
    }
}
