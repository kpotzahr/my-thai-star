package com.devonfw.mts.cucumber.pages;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WaiterPage {
    private static final By ORDERS_SELECTOR = By.xpath("//a[@routerlink='/orders']");
    private static final By RESERVATIONS_SELECTOR = By.xpath("//a[@routerlink='/reservations']");

    @Autowired
    private WidgetHelper helper;

    public void openOrdersSection() {
        helper.widget(ORDERS_SELECTOR).click();
    }

    public void openReservationSection() {
        helper.widget(RESERVATIONS_SELECTOR).click();
    }


}
