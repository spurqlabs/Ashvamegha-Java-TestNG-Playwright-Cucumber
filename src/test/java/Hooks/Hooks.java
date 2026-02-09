package Hooks;

import Driver.PlaywrightFactory;
import Utils.ScreenshotUtil;
import Utils.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Hooks {

    private static final Logger log = LoggerFactory.getLogger(Hooks.class);
    private static boolean isLoggedIn = false;

    // Public zero-argument constructor required by Cucumber
    public Hooks() {
    }

    @Before
    public void setUp(Scenario scenario) {
        log.info("========== Starting Scenario: {} ==========", scenario.getName());

        // Store scenario in context for use in step definitions
        ScenarioContext.setScenario(scenario);

        // Initialize browser only once, not for every scenario
        if (!isLoggedIn) {
            PlaywrightFactory.initBrowser();
            log.info("Browser initialized");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        log.info("========== Finishing Scenario: {} | Status: {} ==========",
                scenario.getName(),
                scenario.getStatus());

        // Capture screenshot for all scenarios (both pass and fail)
        try {
            if (PlaywrightFactory.getPage() != null) {
                String screenshotPath = null;

                if (scenario.isFailed()) {
                    // Capture failure screenshot
                    screenshotPath = ScreenshotUtil.captureFailureScreenshot(
                            PlaywrightFactory.getPage(),
                            scenario.getName()
                    );
                    log.error("Scenario FAILED - Screenshot captured: {}", scenario.getName());
                } else {
                    // Capture success screenshot
                    screenshotPath = ScreenshotUtil.captureSuccessScreenshot(
                            PlaywrightFactory.getPage(),
                            "Final State - " + scenario.getName()
                    );
                    log.info("Scenario PASSED - Final screenshot captured: {}", scenario.getName());
                }

                // Embed screenshot in Cucumber HTML Report
                if (screenshotPath != null && !screenshotPath.isEmpty()) {
                    try {
                        Path path = Paths.get(screenshotPath);
                        byte[] imageBytes = Files.readAllBytes(path);
                        scenario.attach(
                                imageBytes,
                                "image/png",
                                scenario.getStatus() + "_screenshot"
                        );
                        log.info("Screenshot embedded in Cucumber report");
                    } catch (Exception e) {
                        log.error("Error embedding screenshot in report: {}", e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error capturing screenshot in tearDown: {}", e.getMessage());
        }

        // Mark as logged in after first successful login
        isLoggedIn = true;

        // Clear scenario context
        ScenarioContext.clear();

        // Close browser only after all scenarios complete
        // For now, keep it open - will be closed after test suite ends
    }
}




