package com.devonfw.mts.cucumber.pages.widgets;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Inputfield extends Widget {
    public Inputfield(By selector, WebDriver searchContext) {
        super(selector, searchContext);
    }

    public void enter(String text) {
        WebElement element = getWebElement();
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(text);
        // retry if text could not be set
        if (!getDisplayedText().equals(text)) {
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.DELETE);
            element.sendKeys(text);
        }
    }

    public String getDisplayedText() {
        WebElement element = getWebElement();
        String value = element.getAttribute("value");
        return (null == value || value.isEmpty()) ? element.getText() : value;
    }
}
