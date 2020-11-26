package com.devonfw.mts.cucumber.stepdefs;

import com.devonfw.mts.cucumber.config.CucumberConfiguration;
import com.devonfw.mts.cucumber.data.ScenarioVariables;
import com.devonfw.mts.cucumber.pages.BrowserAccess;
import com.devonfw.mts.cucumber.api.MailMockServer;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = {CucumberConfiguration.class})
public class CommonHooks {
    private static boolean shutdownHookAdded = false;

    @Autowired
    private BrowserAccess browserAccess;

    @Autowired
    private ScenarioVariables scenarioVariables;

    @Autowired
    private MailMockServer mailMockServer;

    @Before
    public void resetVariables() {
        scenarioVariables.reset();
    }

    @Before
    public void mockEmailSuccess() {
        mailMockServer.reset();
        mailMockServer.mockEmailSuccess();
    }


    @Before(value = "@ui", order = 100)
    public void setupUiTest() {
        if (!shutdownHookAdded) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> browserAccess.closeBrowser()));
            shutdownHookAdded = true;
        }
    }

    @After("@ui")
    public void afterScenarioUI(Scenario scenario) {
        if (scenario.isFailed() && browserAccess.isBrowserOpen()) {
            scenario.attach(browserAccess.takeScreenshot(), "image/png", scenario.getName());
            browserAccess.closeBrowser();
        }
    }
}
