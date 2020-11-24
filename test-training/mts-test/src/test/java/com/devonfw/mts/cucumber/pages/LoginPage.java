package com.devonfw.mts.cucumber.pages;

import com.devonfw.mts.cucumber.data.CukesUser;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginPage {
    private static final By USERNAME_SELECTOR = By.name("username");
    private static final By PASSWORD_SELECTOR = By.name("password");
    private static final By LOGIN_SELECTOR = By.name("submitLogin");

    @Autowired
    private WidgetHelper helper;

    public void login(CukesUser user) {
        helper.inputfield(USERNAME_SELECTOR).enter(user.getUsername());
        helper.inputfield(PASSWORD_SELECTOR).enter(user.getPassword());
        helper.widget(LOGIN_SELECTOR).click();
    }
}
