package Driver;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import Utils.ConfigReader;   //

public class PlaywrightFactory {

    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage = new ThreadLocal<>();

    public static void initBrowser() {

        // Create Playwright
        Playwright playwright = Playwright.create();
        tlPlaywright.set(playwright);

        // Launch REAL Google Chrome (visible)
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setChannel("chrome")
                        .setHeadless(false)
                        .setSlowMo(800)
        );
        tlBrowser.set(browser);

        // Create browser context
        BrowserContext context = browser.newContext();
        tlContext.set(context);

        // Create page
        Page page = context.newPage();
        tlPage.set(page);

        // Navigate to base URL
        page.navigate(
                ConfigReader.get("baseUrl"),
                new Page.NavigateOptions()
                        .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                        .setTimeout(60000)
        );
    }

    public static Page getPage() {
        return tlPage.get();
    }

    public static void closeBrowser() {

        try {
            Thread.sleep(5000); //  keeps browser open so you SEE dashboard
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (tlPage.get() != null) tlPage.get().close();
        if (tlContext.get() != null) tlContext.get().close();
        if (tlBrowser.get() != null) tlBrowser.get().close();
        if (tlPlaywright.get() != null) tlPlaywright.get().close();
    }
}
