package com.devonfw.mts.cucumber.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Component
public class ConfirmationPage {
    private static final By TOP_LEVEL_SELECTOR = By.className("mat-dialog-content");
    private static final By DATA_ELEMENT_SELECTOR = By.tagName("span");
    private static final By NAME_SELECTOR = By.className("nameValue");
    private static final By EMAIL_SELECTOR = By.className("emailValue");

    @Autowired
    private WidgetHelper helper;

    @Autowired
    private BrowserAccess browserAccess;

    public String getEmail() {
        return helper.widget(EMAIL_SELECTOR).getText();
    }

    public String getName() {
        return helper.widget(NAME_SELECTOR).getText();
    }

    public boolean hasBookingDateTime(Instant expectedBookingDateTime) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(expectedBookingDateTime, ZoneId.systemDefault());
        String expectedBookingDateTimeString = DateTimeFormatter.ofPattern(
                "MMMM d, yyyy h:mm a", Locale.getDefault()).format(localDateTime);
        return hasElementWithText(expectedBookingDateTimeString);
    }

    public boolean hasNumberOfGuests(int expectedNumberOfGuests) {
        return hasElementWithText(String.valueOf(expectedNumberOfGuests));
    }

    private boolean hasElementWithText(String expectedText) {
        List<WebElement> elements = helper.widget(TOP_LEVEL_SELECTOR).getWebElement().findElements(DATA_ELEMENT_SELECTOR);

        for (WebElement element : elements) {
            if (expectedText.equals(element.getText())) {
                return true;
            }
        }
        return false;
    }


}
