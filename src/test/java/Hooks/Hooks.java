package Hooks;

import Driver.PlaywrightFactory;
import Utils.ConfigReader;
import Utils.ScenarioContext;
import io.cucumber.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.microsoft.playwright.Page;

public class Hooks {

    private static final Logger log =
            LoggerFactory.getLogger(Hooks.class);

    // ================= BEFORE =================

    @Before
    public void setUp(Scenario scenario) {

        log.info("===== Starting Scenario: {} | Thread: {} =====",
                scenario.getName(),
                Thread.currentThread().getName());

        ScenarioContext.setScenario(scenario);

        // IMPORTANT: Each thread gets its own browser
        String browser = ConfigReader.get("browser");
        PlaywrightFactory.initBrowser(browser);
    }

    // ================= AFTER STEP (Screenshot only if failed) =================

    @AfterStep
    public void captureStepScreenshot(Scenario scenario) {

        if (!scenario.isFailed()) {
            return;
        }

        try {
            Page page = PlaywrightFactory.getPage();

            if (page != null && !page.isClosed()) {

                byte[] screenshot = page.screenshot(
                        new Page.ScreenshotOptions()
                                .setFullPage(true)
                );

                scenario.attach(
                        screenshot,
                        "image/png",
                        "Failed Step Screenshot"
                );
            }

        } catch (Exception e) {
            log.warn("Step screenshot failed: {}", e.getMessage());
        }
    }

    // ================= AFTER =================

    @After
    public void tearDown(Scenario scenario) {

        log.info("===== Finished Scenario: {} | Status: {} | Thread: {} =====",
                scenario.getName(),
                scenario.getStatus(),
                Thread.currentThread().getName());

        try {
            Page page = PlaywrightFactory.getPage();

            if (page != null && !page.isClosed()) {

                byte[] screenshot = page.screenshot(
                        new Page.ScreenshotOptions()
                                .setFullPage(true)
                );

                scenario.attach(
                        screenshot,
                        "image/png",
                        "Final Screenshot"
                );
            }

        } catch (Exception e) {
            log.warn("Final screenshot failed: {}", e.getMessage());
        } finally {

            // IMPORTANT: Close only this thread's browser
            PlaywrightFactory.closeBrowser();
            ScenarioContext.clear();
        }
    }
}