package com.devonfw.mts.cucumber.stepdefs;

import com.devonfw.mts.cucumber.api.LoginService;
import com.devonfw.mts.cucumber.data.CukesUser;
import com.devonfw.mts.cucumber.pages.HomePage;
import com.devonfw.mts.cucumber.pages.LoginPage;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginSteps {
    @Autowired
    private HomePage homePage;

    @Autowired
    private LoginPage loginPage;




    @ParameterType(".*")
    public CukesUser user(String username) {
        return new CukesUser(username, username);
    }

    @Given("^The My Thai start page has been opened$")
    public void startPageIsOpen() {
        this.homePage.open();
    }

    @When("I login as {user}")
    public void iLoginAs(CukesUser user) {
        this.homePage.openLogin();
        this.loginPage.login(user);
    }

    @When("I login with invalid credentials")
    public void iLoginWithInvalidCredentials() {
        loginPage.login(CukesUser.invalidUser());
    }

}
