package Utils;


import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {
    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");

    /**
     * Capture screenshot for step execution and attach to Allure report
     *
     * @param page - Playwright Page object
     * @param stepName - Name of the step executing
     * @return - Path to captured screenshot
     */
    public static String captureStepScreenshot(Page page, String stepName) {
        try {
            String timestamp = LocalDateTime.now().format(dateTimeFormatter);
            String sanitizedStepName = stepName.replaceAll("[^a-zA-Z0-9_]", "_");
            String fileName = sanitizedStepName + "_" + timestamp + ".png";

            Path screenshotDir = Paths.get("screenshots/steps");

            // Create directory if it does not exist
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
                log.info("Created screenshot directory: {}", screenshotDir);
            }

            Path screenshotPath = screenshotDir.resolve(fileName);

            // Capture screenshot
            try {
                page.screenshot(
                        new Page.ScreenshotOptions()
                                .setPath(screenshotPath)
                                .setFullPage(true)
                                .setTimeout(10000)  // 10 second timeout
                );

                // Attach to Allure Report
                try (FileInputStream is = new FileInputStream(screenshotPath.toFile())) {
                    Allure.addAttachment(
                            "Step Screenshot: " + stepName,
                            "image/png",
                            is,
                            ".png"
                    );
                }

                log.info("Screenshot captured for step: {} -> {}", stepName, screenshotPath);
                return screenshotPath.toString();

            } catch (Exception e) {
                log.warn("Screenshot capture timed out or failed for step: {}", stepName);
                return null;
            }

        } catch (FileNotFoundException e) {
            log.error("Screenshot file not found after capture: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Failed to capture step screenshot: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Capture screenshot on test failure
     *
     * @param page - Playwright Page object
     * @param scenarioName - Name of the failing scenario
     * @return - Path to captured screenshot
     */
    public static String captureFailureScreenshot(Page page, String scenarioName) {
        try {
            String timestamp = LocalDateTime.now().format(dateTimeFormatter);
            String sanitizedScenarioName = scenarioName.replaceAll("[^a-zA-Z0-9_]", "_");
            String fileName = sanitizedScenarioName + "_FAILURE_" + timestamp + ".png";

            Path screenshotDir = Paths.get("screenshots/failures");

            // Create directory if it does not exist
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
                log.info("Created failure screenshot directory: {}", screenshotDir);
            }

            Path screenshotPath = screenshotDir.resolve(fileName);

            // Capture screenshot with timeout
            try {
                page.screenshot(
                        new Page.ScreenshotOptions()
                                .setPath(screenshotPath)
                                .setFullPage(true)
                                .setTimeout(10000)  // 10 second timeout for screenshot
                );

                // Attach to Allure Report as failure evidence
                try (FileInputStream is = new FileInputStream(screenshotPath.toFile())) {
                    Allure.addAttachment(
                            "Failure Screenshot: " + scenarioName,
                            "image/png",
                            is,
                            ".png"
                    );
                }

                log.info("Failure screenshot captured for scenario: {} -> {}", scenarioName, screenshotPath);
                return screenshotPath.toString();

            } catch (Exception screenshotException) {
                log.warn("Screenshot capture timed out or failed for scenario: {}, continuing without screenshot", scenarioName);
                return null;
            }

        } catch (Exception e) {
            log.error("Failed to capture failure screenshot: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Capture screenshot for successful step completion
     *
     * @param page - Playwright Page object
     * @param stepName - Name of the step
     * @return - Path to captured screenshot
     */
    public static String captureSuccessScreenshot(Page page, String stepName) {
        return captureStepScreenshot(page, "✓ " + stepName);
    }

    /**
     * Capture screenshot on error and save to screenshots/errors folder
     *
     * @param page - Playwright Page object
     * @param stepName - Name of the step
     * @param errorMessage - Error message
     */
    @SuppressWarnings("unused")
    public static void captureErrorScreenshot(Page page, String stepName, String errorMessage) {
        try {
            String timestamp = LocalDateTime.now().format(dateTimeFormatter);
            String sanitizedStepName = stepName.replaceAll("[^a-zA-Z0-9_]", "_");
            String fileName = sanitizedStepName + "_ERROR_" + timestamp + ".png";

            Path screenshotDir = Paths.get("screenshots/errors");

            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            Path screenshotPath = screenshotDir.resolve(fileName);

            page.screenshot(
                    new Page.ScreenshotOptions()
                            .setPath(screenshotPath)
                            .setFullPage(true)
            );

            try (FileInputStream is = new FileInputStream(screenshotPath.toFile())) {
                Allure.addAttachment(
                        "Step Error: " + stepName + " - " + errorMessage,
                        "image/png",
                        is,
                        ".png"
                );
            }

            log.error("Error screenshot for step: {}, Error: {}", stepName, errorMessage);

        } catch (Exception e) {
            log.error("Failed to capture error screenshot: {}", e.getMessage());
        }
    }
}
