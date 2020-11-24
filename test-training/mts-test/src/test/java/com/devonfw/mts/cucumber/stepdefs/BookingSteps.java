package com.devonfw.mts.cucumber.stepdefs;

import com.devonfw.mts.cucumber.pages.BookingPage;
import com.devonfw.mts.cucumber.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertTrue;

public class BookingSteps {
    @Autowired
    private HomePage homePage;

    @Autowired
    private BookingPage bookingPage;

    @Given("^the booking section has been opened$")
    public void bookingHasBeenOpened() {
        this.homePage.openBookingSection();
    }

    @When("^I enter valid booking data$")
    public void enterValidBookingData() {
        enterValidBookingForPersons(2);
    }

    @When("^I do not accept the terms$")
    public void doNotAcceptTerms() {
        bookingPage.acceptTerms(false);
    }

    @When("^I accept the terms$")
    public void acceptTerms() {
        bookingPage.acceptTerms(true);
    }

    @Then("^Booking a table is not possible$")
    public void bookingNotPossible() {
        Assert.assertFalse(bookingPage.isBookingPossible());
    }

    @When("^I enter valid booking information for a table for (\\d+) persons$")
    public void enterValidBookingForPersons(int noOfPersons) {
        String dateTomorrow = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        bookingPage.enterTimeAndDate(dateTomorrow + ", 08:00 PM");
        bookingPage.enterEmail("donald@mytest.de");
        bookingPage.enterName("Donald D. Tester");
        bookingPage.enterGuests(noOfPersons);
    }

    @When("^I confirm the booking$")
    public void confirmBooking() {
        bookingPage.bookTableAndConfirm();
    }

    @Then("^The table is successfully booked$")
    public void tableSuccessfullyBooked() {
        assertTrue(bookingPage.isSuccessMessageShown());
    }


    @When("^I change (email|name|persons) to (.*)$")
    public void changeEmail(String attribute, String value) {
        switch (attribute) {
            case "email":
                bookingPage.enterEmail(value);
                break;
            case "name":
                bookingPage.enterName(value);
                break;
            case "persons":
                bookingPage.enterGuests(value);
                break;
            default:
                throw new IllegalArgumentException("Unknown attribute " + attribute);
        }
    }
}
