package com.devonfw.mts.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/cucumber",
        tags = "not @ignored",
        glue={"com.devonfw.mts.cucumber"},
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-report.html", "json:target/cucumber.json"}
)
public class RunCucumberTests {
}
