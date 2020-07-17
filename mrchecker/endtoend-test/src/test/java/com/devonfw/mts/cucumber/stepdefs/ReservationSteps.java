package com.devonfw.mts.cucumber.stepdefs;

import com.devonfw.mts.cucumber.api.BookingManagementService;
import com.devonfw.mts.pages.ThaiReservationsPage;
import com.devonfw.mts.pages.ThaiWaiterPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertFalse;

public class ReservationSteps {
    @Autowired
    private BookingManagementService bookingManagementService;

    final private ThaiWaiterPage waiterPage = new ThaiWaiterPage();

    @Then("^I can see a reservation for \"([^\"]*)\"$")
    public void reservationExistsFor(String emailAddress)  {
        ThaiReservationsPage thaiReservationsPage = this.waiterPage.switchToReservations();
        assertFalse(thaiReservationsPage.getReservationsForEmail(emailAddress).isEmpty());
    }

    @Given("There is a reservation for {string}")
    public void thereIsAReservationFor(String email) {
        if (!bookingManagementService.hasBookingForEmail(email)) {
            bookingManagementService.createBookingForEmail(email);
        }
    }
}
