package utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class WaitUtil {

    // Wait until element is visible
    public static void waitForVisible(Page page, String locator) {
        page.waitForSelector(
                locator,
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000)
        );
    }

    // Wait until element is clickable (visible + enabled)
    public static void waitForClickable(Page page, String locator) {
        page.waitForSelector(
                locator,
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000)
        );
    }
}
