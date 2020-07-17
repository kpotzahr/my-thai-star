package com.devonfw.mts.cucumber.stepdefs;

import com.capgemini.mrchecker.selenium.core.BasePage;
import com.devonfw.mts.cucumber.config.CucumberConfiguration;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = CucumberConfiguration.class)
public class CommonHooks {
    @After("@ui")
    public void afterScenarioUI(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) BasePage.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
    }
}
