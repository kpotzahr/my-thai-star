package com.devonfw.mts.cucumber.pages.widgets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Checkbox extends Widget {

    public static final By SQUARE_SELECTOR = By.className("mat-checkbox-inner-container");

    public Checkbox(By selector, WebDriver searchContext) {
        super(selector, searchContext);
    }

    @Override
    public void click() {
        WebElement square = getWebElement().findElement(SQUARE_SELECTOR);
        clickViaJavascript(square);
    }

    public void select(boolean selected) {
        String checked = getWebElement().findElement(By.cssSelector(".mat-checkbox-input")).getAttribute("aria-checked");
        if (selected != Boolean.parseBoolean(checked)) {
            click();
        }
    }
}
