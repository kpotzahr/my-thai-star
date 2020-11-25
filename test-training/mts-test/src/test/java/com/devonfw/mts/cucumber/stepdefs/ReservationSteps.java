package com.devonfw.mts.cucumber.stepdefs;

import com.devonfw.mts.cucumber.api.BookingManagementService;
import com.devonfw.mts.cucumber.data.ScenarioVariables;
import com.devonfw.mts.cucumber.pages.ReservationPage;
import com.devonfw.mts.cucumber.pages.WaiterPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationSteps {
    @Autowired
    private BookingManagementService bookingManagementService;

    @Autowired
    private WaiterPage waiterPage;

    @Autowired
    private ReservationPage reservationPage;

    @Autowired
    private ScenarioVariables scenarioVariables;

    @Given("There is a reservation for {string}")
    public void thereIsAReservationFor(String email) {
        scenarioVariables.setEmail(email);
        if (!bookingManagementService.hasBookingForEmail(email)) {
            bookingManagementService.createBookingForEmail(email);
        }
    }

    @Then("I can find the reservation for the email")
    public void iCanFindTheReservationForTheEmail() {
        waiterPage.openReservationSection();
        assertThat(reservationPage.getReservations(scenarioVariables.getEmail())).isNotEmpty();
    }

}
