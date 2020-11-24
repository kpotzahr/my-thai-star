package com.devonfw.mts.cucumber.stepdefs;

import com.devonfw.mts.cucumber.config.CucumberConfiguration;
import com.devonfw.mts.cucumber.pages.BrowserAccess;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = {CucumberConfiguration.class})
public class CommonHooks {
    @Autowired
    private BrowserAccess browserAccess;

    @After("@ui")
    public void afterScenarioUI(Scenario scenario) {
        if (scenario.isFailed() && browserAccess.isBrowserOpen()) {
            scenario.attach(browserAccess.takeScreenshot(), "image/png", scenario.getName());
            browserAccess.closeBrowser();
        }
    }
}
