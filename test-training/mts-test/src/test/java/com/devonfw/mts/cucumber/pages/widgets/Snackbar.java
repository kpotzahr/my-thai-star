package com.devonfw.mts.cucumber.pages.widgets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Snackbar extends Widget {
    public Snackbar(WebDriver searchContext) {
        super(By.cssSelector(".mat-snack-bar-container"), searchContext);
    }

    public boolean containsMessage(String messagePart) {
        int retry = 0;
        boolean containMessage = false;
        while (!containMessage && retry < 10) {
            retry++;
            containMessage = getMessage().contains(messagePart);
        }
        return containMessage;
    }

    private String getMessage() {
        String message = getWebElement().findElement(
                By.cssSelector(".mat-simple-snackbar")).getText();
        return null == message ? "" : message;
    }
}
