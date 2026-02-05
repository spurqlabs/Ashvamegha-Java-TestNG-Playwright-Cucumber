package utils;

import com.microsoft.playwright.Page;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "screenshots";

    public static void takeScreenshot(Page page, String scenarioName) {
        try {
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));

            String timestamp =
                    LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String fileName =
                    scenarioName.replaceAll("[^a-zA-Z0-9]", "_")
                            + "_" + timestamp + ".png";

            Path path = Paths.get(SCREENSHOT_DIR, fileName);

            page.screenshot(
                    new Page.ScreenshotOptions()
                            .setPath(path)
                            .setFullPage(true)
            );

            System.out.println(" Screenshot saved: " + path.toAbsolutePath());

        } catch (Exception e) {
            System.out.println(" Failed to take screenshot: " + e.getMessage());
        }
    }
}
