package Driver;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import Utils.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaywrightFactory {

    private static final Logger log = LoggerFactory.getLogger(PlaywrightFactory.class);

    private static final ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> tlContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> tlPage = new ThreadLocal<>();
    private static final ThreadLocal<String> tlBrowserName = new ThreadLocal<>();

    public static void initBrowser() {

        // Create Playwright instance
        Playwright playwright = Playwright.create();
        tlPlaywright.set(playwright);

        // Read browser from system property (Maven profile) or fallback to config.json
        String browserName = System.getProperty("browser", ConfigReader.get("browser"));
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", ConfigReader.get("headless")));

        // Store browser name for logging
        tlBrowserName.set(browserName);

        // Log browser launch
        log.info("========================================");
        log.info("🚀 LAUNCHING BROWSER: {}", browserName.toUpperCase());
        log.info("Headless Mode: {}", headless);
        log.info("Thread: {}", Thread.currentThread().getName());
        log.info("========================================");

        BrowserType browserType;

        browserType = switch (browserName.toLowerCase()) {
            case "firefox" -> {
                log.info("✓ Firefox browser type selected");
                yield playwright.firefox();
            }
            case "webkit" -> {
                log.info("✓ WebKit browser type selected");
                yield playwright.webkit();
            }
            case "edge" -> {
                log.info("✓ Edge (Chromium) browser type selected");
                yield playwright.chromium();
            }
            case "chrome" -> {
                log.info("✓ Chrome (Chromium) browser type selected");
                yield playwright.chromium();
            }
            default -> {
                log.info("✓ Chrome (Chromium) browser type selected (default)");
                yield playwright.chromium();
            }
        };

        log.info("Launching {} browser instance...", browserName);

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless);

        // Only set channel for Chromium-based browsers (Chrome, Edge)
        if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("edge")) {
            launchOptions.setChannel(getChannel(browserName));
            log.info("Channel set to: {}", getChannel(browserName));
        }

        Browser browser = browserType.launch(launchOptions);

        tlBrowser.set(browser);
        log.info(" {} Browser launched successfully on thread: {}",
                 browserName.toUpperCase(), Thread.currentThread().getName());

        // Create isolated context per scenario (parallel safe)
        BrowserContext context = browser.newContext();
        tlContext.set(context);
        log.info("✓ Browser context created");

        // Create page
        Page page = context.newPage();
        tlPage.set(page);
        log.info("✓ Page created");

        // Navigate to base URL with proper timeout and wait state
        try {
            page.navigate(
                    ConfigReader.get("baseUrl"),
                    new Page.NavigateOptions()
                            .setWaitUntil(WaitUntilState.LOAD)
                            .setTimeout(90000)
            );
            log.info("✓ Navigated to: {}", ConfigReader.get("baseUrl"));
        } catch (PlaywrightException e) {
            log.warn("Navigation timeout, but continuing. Page URL: {}", page.url());
            // Continue even if page takes time to load
        }
        log.info("========================================");
    }

    public static Page getPage() {
        return tlPage.get();
    }

    @SuppressWarnings("unused")
    public static String getBrowserName() {
        return tlBrowserName.get();
    }

    public static void closeBrowser() {

        String browserName = tlBrowserName.get();
        log.info("========================================");
        log.info(" CLOSING BROWSER: {}", browserName != null ? browserName.toUpperCase() : "UNKNOWN");
        log.info("Thread: {}", Thread.currentThread().getName());

        if (tlPage.get() != null) {
            tlPage.get().close();
            log.info("✓ Page closed");
        }

        if (tlContext.get() != null) {
            tlContext.get().close();
            log.info("✓ Browser context closed");
        }

        if (tlBrowser.get() != null) {
            tlBrowser.get().close();
            log.info(" {} Browser closed successfully", browserName != null ? browserName.toUpperCase() : "Browser");
        }

        if (tlPlaywright.get() != null) {
            tlPlaywright.get().close();
            log.info("✓ Playwright closed");
        }

        log.info("========================================");

        //  VERY IMPORTANT for parallel execution
        tlPage.remove();
        tlContext.remove();
        tlBrowser.remove();
        tlPlaywright.remove();
        tlBrowserName.remove();
    }

    private static String getChannel(String browserName) {
        return switch (browserName.toLowerCase()) {
            case "chrome" -> "chrome";
            case "edge" -> "msedge";
            case "firefox" -> "firefox";
            case "webkit" -> null;
            default -> null;
        };
    }
}
