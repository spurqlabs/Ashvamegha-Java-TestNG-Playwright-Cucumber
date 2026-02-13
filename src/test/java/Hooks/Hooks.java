package Hooks;

import Driver.PlaywrightFactory;
import Utils.ScenarioContext;
import io.cucumber.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.microsoft.playwright.Page;


public class Hooks {

    private static final Logger log =
            LoggerFactory.getLogger(Hooks.class);

    public Hooks() {
    }

    // ================= BEFORE =================
    @Before
    public void setUp(Scenario scenario) {

        log.info("===== Starting Scenario: {} =====",
                scenario.getName());

        ScenarioContext.setScenario(scenario);
        PlaywrightFactory.initBrowser();
    }

    // ================= STEP SCREENSHOT =================
    @AfterStep
    public void captureStepScreenshot(Scenario scenario) {

        try {
            if (PlaywrightFactory.getPage() != null) {

                byte[] screenshot =
                        PlaywrightFactory.getPage()
                                .screenshot(new Page.ScreenshotOptions()
                                        .setFullPage(true));

                scenario.attach(
                        screenshot,
                        "image/png",
                        "Step Screenshot"
                );
            }

        } catch (Exception e) {
            log.warn("Step screenshot failed: {}", e.getMessage());
        }
    }

    // ================= AFTER =================
    @After
    public void tearDown(Scenario scenario) {

        log.info("===== Finished Scenario: {} | Status: {} =====",
                scenario.getName(),
                scenario.getStatus());

        try {
            if (PlaywrightFactory.getPage() != null) {

                byte[] screenshot =
                        PlaywrightFactory.getPage()
                                .screenshot(new Page.ScreenshotOptions()
                                        .setFullPage(true));

                scenario.attach(
                        screenshot,
                        "image/png",
                        "Final Screenshot"
                );
            }

        } catch (Exception e) {
            log.warn("Final screenshot failed: {}", e.getMessage());
        } finally {

            PlaywrightFactory.closeBrowser();
            ScenarioContext.clear();
        }
    }
}
