package Utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class WaitUtil {

    public static void waitForVisible(Page page, String locator) {
        page.waitForSelector(
                locator,
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(15000)
        );
    }

    public static void waitForClickable(Page page, String locator) {
        page.waitForSelector(
                locator,
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(15000)
        );
    }
}
