package com.devonfw.mts.cucumber.pages.widgets;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Widget {
    private final WebDriver searchContext;
    private final By selector;

    public Widget(By selector, WebDriver searchContext)  {
        this.searchContext = searchContext;
        this.selector = selector;
    }

    public void click() {
        (new WebDriverWait(searchContext, 10)).
                until(ExpectedConditions.elementToBeClickable(getWebElement()));
        getWebElement().click();
    }

    protected void clickViaJavascript(WebElement element) {
        scrollTo(element);
        (new WebDriverWait(searchContext, 10)).
                until(ExpectedConditions.elementToBeClickable(element));
        JavascriptExecutor executor = (JavascriptExecutor) searchContext;
        executor.executeScript("arguments[0].click();", element);
    }

    protected void scrollTo(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) searchContext;
        executor.executeScript("arguments[0].scrollIntoView();", element);
    }

    public boolean isEnabled() {
        return getWebElement().isEnabled();
    }

    public WebElement getWebElement() {
        return searchContext.findElement(selector);
    }
}
