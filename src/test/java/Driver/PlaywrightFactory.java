package Driver;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import Utils.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaywrightFactory {

    private static final Logger log =
            LoggerFactory.getLogger(PlaywrightFactory.class);

    private static final ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> tlContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> tlPage = new ThreadLocal<>();

    public static void initBrowser() {

        Playwright playwright = Playwright.create();
        tlPlaywright.set(playwright);

        String browserName = System.getProperty(
                "browser",
                ConfigReader.get("browser")
        );

        boolean headless = Boolean.parseBoolean(
                System.getProperty(
                        "headless",
                        ConfigReader.get("headless")
                )
        );

        BrowserType browserType;

        switch (browserName.toLowerCase()) {
            case "firefox":
                browserType = playwright.firefox();
                break;
            case "webkit":
                browserType = playwright.webkit();
                break;
            case "edge":
                browserType = playwright.chromium();
                break;
            default:
                browserType = playwright.chromium();
        }

        BrowserType.LaunchOptions options =
                new BrowserType.LaunchOptions()
                        .setHeadless(headless);

        if (browserName.equalsIgnoreCase("chrome")) {
            options.setChannel("chrome");
        } else if (browserName.equalsIgnoreCase("edge")) {
            options.setChannel("msedge");
        }

        Browser browser = browserType.launch(options);
        tlBrowser.set(browser);

        BrowserContext context = browser.newContext();
        tlContext.set(context);

        Page page = context.newPage();
        tlPage.set(page);

        page.navigate(
                ConfigReader.get("baseUrl"),
                new Page.NavigateOptions()
                        .setWaitUntil(WaitUntilState.LOAD)
                        .setTimeout(90000)
        );

        log.info("Browser launched successfully in thread: {}",
                Thread.currentThread().getName());
    }

    public static Page getPage() {
        return tlPage.get();
    }

    public static void closeBrowser() {

        if (tlPage.get() != null) {
            tlPage.get().close();
        }

        if (tlContext.get() != null) {
            tlContext.get().close();
        }

        if (tlBrowser.get() != null) {
            tlBrowser.get().close();
        }

        if (tlPlaywright.get() != null) {
            tlPlaywright.get().close();
        }

        tlPage.remove();
        tlContext.remove();
        tlBrowser.remove();
        tlPlaywright.remove();
    }
}
