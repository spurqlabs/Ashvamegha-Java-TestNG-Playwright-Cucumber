package Runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("Features")

// ================= GLUE CONFIGURATION =================
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "StepDefinitions,Hooks,io.cucumber.spring"
)


// ================= OBJECT FACTORY =================
@ConfigurationParameter(
        key = "cucumber.object-factory",
        value = "io.cucumber.picocontainer.PicoFactory"
)

// ================= TAG FILTER =================
@ConfigurationParameter(
        key = FILTER_TAGS_PROPERTY_NAME,
        value = "@leave"
)

// ================= CUCUMBER PARALLEL EXECUTION =================
@ConfigurationParameter(
        key = "cucumber.execution.parallel.enabled",
        value = "false"
)

@ConfigurationParameter(
        key = "cucumber.execution.parallel.mode",
        value = "CONCURRENT"
)

@ConfigurationParameter(
        key = "cucumber.execution.parallel.strategy",
        value = "SCENARIO"
)

// ================= JUNIT PARALLEL EXECUTION =================
@ConfigurationParameter(
        key = "junit.jupiter.execution.parallel.enabled",
        value = "false"
)

@ConfigurationParameter(
        key = "junit.jupiter.execution.parallel.mode.default",
        value = "concurrent"
)

@ConfigurationParameter(
        key = "junit.jupiter.execution.parallel.mode.classes.default",
        value = "concurrent"
)

@ConfigurationParameter(
        key = "junit.jupiter.execution.parallel.config.strategy",
        value = "fixed"
)

@ConfigurationParameter(
        key = "junit.jupiter.execution.parallel.config.fixed.parallelism",
        value = "2"
)

@ConfigurationParameter(
        key = "junit.jupiter.execution.parallel.config.fixed.max-pool-size",
        value = "6"
)

// ================= CUSTOM BROWSER CONFIG =================
@ConfigurationParameter(
        key = "browser",
        value = "chrome"
)

public class TestRunner {
    // No code required here.
}
