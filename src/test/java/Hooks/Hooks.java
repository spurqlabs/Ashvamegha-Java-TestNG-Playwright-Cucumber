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

    private static final Logger log =
            LoggerFactory.getLogger(Hooks.class);

    // Required zero-arg constructor
    public Hooks() {
    }

    @Before
    public void setUp(Scenario scenario) {

        log.info("========== Starting Scenario: {} ==========",
                scenario.getName());

        // Thread-local scenario binding
        ScenarioContext.setScenario(scenario);

        // One browser per scenario (parallel safe)
        PlaywrightFactory.initBrowser();
        log.info("Browser initialized for scenario");
    }

    @After
    public void tearDown(Scenario scenario) {

        log.info("========== Finishing Scenario: {} | Status: {} ==========",
                scenario.getName(),
                scenario.getStatus());

        try {
            if (PlaywrightFactory.getPage() != null && scenario != null) {

                String screenshotPath = null;

                if (scenario.isFailed()) {
                    screenshotPath =
                            ScreenshotUtil.captureFailureScreenshot(
                                    PlaywrightFactory.getPage(),
                                    scenario.getName()
                            );
                    log.error("Scenario FAILED - Screenshot captured");
                } else {
                    screenshotPath =
                            ScreenshotUtil.captureSuccessScreenshot(
                                    PlaywrightFactory.getPage(),
                                    scenario.getName()
                            );
                    log.info("Scenario PASSED - Screenshot captured");
                }

                // Attach only if file exists
                if (screenshotPath != null && Files.exists(Paths.get(screenshotPath))) {
                    Path path = Paths.get(screenshotPath);
                    byte[] imageBytes = Files.readAllBytes(path);
                    scenario.attach(
                            imageBytes,
                            "image/png",
                            "Screenshot"
                    );
                }
            }
        } catch (Exception e) {
            log.warn("Screenshot attachment skipped: {}", e.getMessage());
        } finally {
            // ✅ Close browser FIRST
            PlaywrightFactory.closeBrowser();

            // ✅ Then clear scenario data
            ScenarioContext.clear();
        }
    }
}
