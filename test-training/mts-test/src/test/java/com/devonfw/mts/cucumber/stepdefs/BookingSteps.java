package com.devonfw.mts.cucumber.stepdefs;

import com.devonfw.mts.cucumber.api.BookingManagementService;
import com.devonfw.mts.cucumber.api.MailMockServer;
import com.devonfw.mts.cucumber.data.CreateBookingResponse;
import com.devonfw.mts.cucumber.data.CukesBookingData;
import com.devonfw.mts.cucumber.data.MailInfo;
import com.devonfw.mts.cucumber.data.ScenarioVariables;
import com.devonfw.mts.cucumber.pages.BookingPage;
import com.devonfw.mts.cucumber.pages.ConfirmationPage;
import com.devonfw.mts.cucumber.pages.HomePage;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class BookingSteps {
    @Autowired
    private HomePage homePage;

    @Autowired
    private BookingPage bookingPage;

    @Autowired
    private ConfirmationPage confirmationPage;

    @Autowired
    private BookingManagementService bookingManagementService;

    @Autowired
    private ScenarioVariables scenarioVariables;

    @Autowired
    private MailMockServer mailMockServer;

    private CreateBookingResponse bookingResponse;

    @DataTableType
    public CukesBookingData bookingData(Map<String, String> entry) {
        CukesBookingData bookingData = new CukesBookingData();
        bookingData.setAssistants(Integer.parseInt(entry.get("persons")));
        bookingData.setEmail(entry.get("email"));
        bookingData.setName(entry.get("name"));

        String date = entry.get("date");
        String time = entry.get("time");
        switch (date) {
            case "<today>":
                date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now());
                break;
            case "<tomorrow>":
                date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(1));
                break;
            default:
                break;
        }

        LocalDateTime bookingDate = LocalDateTime.parse(date + " " + time,
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                        .withZone(ZoneId.systemDefault()));

        bookingData.setBookingDate(bookingDate.atZone(ZoneId.systemDefault()).toInstant());
        return bookingData;
    }


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
        String email = "donald@mytest.de";
        bookingPage.enterEmail(email);
        scenarioVariables.setEmail(email);
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
                scenarioVariables.setEmail(value);
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

    @When("I book a table with the following booking data:")
    public void iBookATableWithTheFollowingBookingData(CukesBookingData bookingData) {
        scenarioVariables.setEmail(bookingData.getEmail());
        scenarioVariables.setBookingData(bookingData);
        bookingResponse = bookingManagementService.createBooking(bookingData);
    }

    @Then("I get a confirmation email")
    public void iGetAConfirmationEmail() throws IOException {
        List<MailInfo> mailInfoList = mailMockServer.getEmails(scenarioVariables.getEmail());
        assertThat(mailInfoList).hasSize(1);
    }

    @When("I enter and accept the following booking data:")
    public void iEnterAndAcceptTheFollowingBookingData(CukesBookingData bookingData) {
        bookingPage.enterTimeAndDate(bookingData.getBookingDate());
        bookingPage.enterEmail(bookingData.getEmail());
        scenarioVariables.setEmail(bookingData.getEmail());
        bookingPage.enterName(bookingData.getName());
        bookingPage.enterGuests(bookingData.getAssistants());
        bookingPage.acceptTerms(true);
        scenarioVariables.setBookingData(bookingData);
        bookingPage.bookTable();
    }

    @Then("I see all the entered details in the confirmation dialog")
    public void iSeeAllTheEnteredDetailsInTheConfirmationDialog() {
        CukesBookingData bookingData = scenarioVariables.getBookingData();
        assertThat(confirmationPage.getName()).isEqualTo(bookingData.getName());
        assertThat(confirmationPage.getEmail()).isEqualTo(bookingData.getEmail());
        assertThat(confirmationPage.hasNumberOfGuests(bookingData.getAssistants())).isTrue();
        assertThat(confirmationPage.hasBookingDateTime(bookingData.getBookingDate())).isTrue();
    }

    @Then("I all the entered details are saved")
    public void iAllTheEnteredDetailsAreAccepted() {
        assertThat(bookingResponse.getData()).isEqualToComparingOnlyGivenFields(scenarioVariables.getBookingData(),
                "name", "email", "bookingDate", "assistants");
    }

    @Then("The confirmation email contains the booking token")
    public void theConfirmationEmailContainsABookingId() throws IOException {
        List<MailInfo> mailInfoList = mailMockServer.getEmails(scenarioVariables.getEmail());
        assertThat(mailInfoList.get(0).getText()).contains(bookingResponse.getData().getBookingToken());

    }

    @Given("The mail service has an internal problem")
    public void theMailServiceHasAnInternalProblem() {
        mailMockServer.mockEmailFailure();
    }

    @Then("The booking is not accepted")
    public void theBookingIsNotAccepted() {
        assertThat(bookingResponse.getHttpStatus()).isEqualTo(500);
    }
}
