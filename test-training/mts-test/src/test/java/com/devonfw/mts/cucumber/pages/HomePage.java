package com.devonfw.mts.cucumber.pages;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HomePage {
    private static final By LOGIN_BUTTON_SELECTOR = By.name("login");
    private static final By BOOK_TABLE_SELECTOR = By.xpath("//a[@routerlink='/bookTable']");

    @Autowired
    private WidgetHelper helper;

    @Autowired
    private BrowserAccess browserAccess;

    @Value("${mythaistar.url:http://localhost:8081}")
    private String homepageUrl;


    public void open() {
        browserAccess.webDriver().navigate().to(homepageUrl);

    }

    public void openBookingSection() {
        helper.widget(BOOK_TABLE_SELECTOR).click();
    }

    public void openLogin() {
        helper.widget(LOGIN_BUTTON_SELECTOR).click();
    }

    public void waitForDisplayedLoginName(String expectedName) {
        browserAccess.webDriver().findElement(
                By.xpath("//div//span[@class ='forDesktop'][contains(text(), '" + expectedName + "')]"));
    }
}
