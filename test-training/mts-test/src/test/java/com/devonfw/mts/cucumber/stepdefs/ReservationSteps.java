package com.devonfw.mts.cucumber.stepdefs;

import com.devonfw.mts.cucumber.api.BookingManagementService;
import io.cucumber.core.exception.CucumberException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class ReservationSteps {
    @Autowired
    private BookingManagementService bookingManagementService;


    @Given("There is a reservation for {string}")
    public void thereIsAReservationFor(String email) {
        if (!bookingManagementService.hasBookingForEmail(email)) {
            bookingManagementService.createBookingForEmail(email);
        }
    }

    @Then("I can find the reservation for the email")
    public void iCanFindTheReservationForTheEmail() {
        throw new CucumberException("not yet implemented");
    }

}
