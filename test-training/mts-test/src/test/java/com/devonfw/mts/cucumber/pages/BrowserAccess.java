package com.devonfw.mts.cucumber.pages;

import com.devonfw.mts.shared.TestConfiguration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BrowserAccess {
    private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";

    private WebDriver webDriver;

    public WebDriver webDriver() {
        if (null == webDriver) {
            openBrowser();
        }

        return webDriver;
    }

    public void openBrowser() {
        if (null != webDriver) {
            return;
        }
        System.setProperty(CHROME_DRIVER_PROPERTY, TestConfiguration.getChromedriverPath());
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isBrowserOpen() {
        return null != webDriver;
    }

    public void closeBrowser() {
        if (null != webDriver) {
            try {
                webDriver.quit();
                webDriver.close();
            } finally {
                webDriver = null;
            }
        }
    }

    public byte[] takeScreenshot() {
        if (null != webDriver) {
            return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
        } else {
            return null;
        }
    }
}
