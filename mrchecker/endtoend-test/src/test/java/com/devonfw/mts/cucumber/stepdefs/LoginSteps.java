package com.devonfw.mts.cucumber.stepdefs;

import com.devonfw.mts.common.data.User;
import com.devonfw.mts.pages.ThaiHomePage;
import com.devonfw.mts.pages.ThaiLoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {
    private final ThaiHomePage myThaiStarHome = new ThaiHomePage();

    private String loggedInUser;


    @Given("^The My Thai start page has been opened$")
    public void startPageIsOpen() {
        this.myThaiStarHome.load();
    }

    @When("^I login as Willy Waiter$")
    public void loginAsWillyWaiter() {
        ThaiLoginPage loginPage = this.myThaiStarHome.clickLogInButton();
        User waiter = User.waiterUser();
        loggedInUser = waiter.getUsername();
        loginPage.enterCredentials(loggedInUser, waiter.getPassword());
    }

    @Then("^My login name is shown$")
    public void loginNameIsShown() {
        this.myThaiStarHome.isUserLogged(loggedInUser);
    }

}
