package Driver;

import com.microsoft.playwright.*;

public class PlaywrightFactory {

    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    // ================= INIT =================

    public static void initBrowser(String browserName) {

        playwright.set(Playwright.create());

        BrowserType browserType;

        switch (browserName.toLowerCase()) {
            case "firefox":
                browserType = playwright.get().firefox();
                break;
            case "webkit":
                browserType = playwright.get().webkit();
                break;
            default:
                browserType = playwright.get().chromium();
        }

        browser.set(browserType.launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
        ));

        context.set(browser.get().newContext());
        page.set(context.get().newPage());

        System.out.println("Browser launched successfully in thread: "
                + Thread.currentThread().getName());
    }

    // ================= GET PAGE =================

    public static Page getPage() {
        return page.get();
    }

    // ================= CLOSE =================

    public static void closeBrowser() {

        try {
            if (page.get() != null) page.get().close();
            if (context.get() != null) context.get().close();
            if (browser.get() != null) browser.get().close();
            if (playwright.get() != null) playwright.get().close();
        } catch (Exception ignored) {}

        page.remove();
        context.remove();
        browser.remove();
        playwright.remove();

        System.out.println("Browser closed in thread: "
                + Thread.currentThread().getName());
    }
}