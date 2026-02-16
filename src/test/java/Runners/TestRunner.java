package Runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Features",
        glue = {"StepDefinitions", "Hooks"},
        plugin = {
                "pretty",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true
)
public class TestRunner {
}
