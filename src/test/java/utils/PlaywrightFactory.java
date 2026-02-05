package utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;


public class PlaywrightFactory {

    private static ThreadLocal<Page> page = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();

    public static void initBrowser() {
        playwright.set(Playwright.create());

        browser.set(
                playwright.get().chromium().launch(
                        new BrowserType.LaunchOptions().setHeadless(false)
                )
        );

        page.set(browser.get().newPage());

        // BASE URL USED HERE
        try {
            page.get().navigate(
                    ConfigReader.get("baseUrl"),
                    new Page.NavigateOptions()
                            .setWaitUntil(WaitUntilState.NETWORKIDLE)
                            .setTimeout(120000)
            );


        } catch (Exception e) {
            System.out.println("First navigation attempt timed out, retrying: " + e.getMessage());
            try {
                page.get().navigate(
                        ConfigReader.get("baseUrl"),
                        new Page.NavigateOptions().setTimeout(120000)
                );
            } catch (Exception e2) {
                System.out.println("Navigation failed after retry: " + e2.getMessage());
            }
        }
    }

    public static Page getPage() {
        return page.get();
    }

    public static void closeBrowser() {
        browser.get().close();
        playwright.get().close();
    }
}
