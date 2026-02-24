package Runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("Features")

// ================= GLUE =================
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "StepDefinitions,Hooks"
)

// ================= REPORTING =================
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-report.html, json:target/cucumber.json"
)
//----------------------------TAG Filtering-----------
@ConfigurationParameter(
        key = FILTER_TAGS_PROPERTY_NAME,
        value = "@timesheet"
)

// ================= CUCUMBER PARALLEL =================
@ConfigurationParameter(
        key = "cucumber.execution.parallel.enabled",
        value = "false"
)

@ConfigurationParameter(
        key = "cucumber.execution.parallel.config.strategy",
        value = "fixed"
)

@ConfigurationParameter(
        key = "cucumber.execution.parallel.config.fixed.parallelism",
        value = "2"
)

public class TestRunner {
}