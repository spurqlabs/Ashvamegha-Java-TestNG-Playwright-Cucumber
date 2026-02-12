package Runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("Features")

// ================= GLUE & TAGS CONFIGURATION =================
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "StepDefinitions,Hooks,io.cucumber.spring"
)

@ConfigurationParameter(
        key = "cucumber.object-factory",
        value = "io.cucumber.picocontainer.PicoFactory"
)

@ConfigurationParameter(
        key = FILTER_TAGS_PROPERTY_NAME,
        value = "@candidate"
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
        value =  "false"
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

// ================= THREAD CONFIGURATION =================
@ConfigurationParameter(
        key = "junit.jupiter.execution.parallel.config.fixed.max-pool-size",
        value = "6"
)

// ================= BROWSER CONFIGURATION =================
@ConfigurationParameter(
        key = "browser",
        value = "chrome"
)

public class TestRunner {
    /*
     * PARALLEL EXECUTION CONFIGURATION:
     *
     * Cucumber Parallel Settings:
     * - PARALLEL_EXECUTION_ENABLED: true (enables parallel execution)
     * - PARALLEL_EXECUTION_MODE: CONCURRENT (runs scenarios concurrently)
     * - PARALLEL_EXECUTION_STRATEGY: SCENARIO (each scenario runs in parallel)
     *
     * JUnit Parallel Settings:
     * - junit.jupiter.execution.parallel.enabled: true
     * - junit.jupiter.execution.parallel.mode.default: concurrent
     * - junit.jupiter.execution.parallel.config.fixed.parallelism: 2
     * - junit.jupiter.execution.parallel.config.fixed.max-pool-size: 4
     *
     * Thread Pool Configuration:
     * - 2 threads for parallelism (initial)
     * - 4 threads max pool size
     *
     * To adjust parallel thread count, modify the values:
     * - Change "parallelism" value for initial thread count (currently 2)
     * - Change "max-pool-size" value for maximum threads (currently 4)
     *
     * To run all tests (not just @smoke):
     * - Remove or modify the FILTER_TAGS_PROPERTY_NAME parameter
     *
     * To run specific tag:
     * - Update the FILTER_TAGS_PROPERTY_NAME value (e.g., "@smoke and @login")
     */
}
