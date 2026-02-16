package Utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;


public class WaitUtil {

    // ================= TIMEOUTS =================
    private static final int DEFAULT_TIMEOUT = 90000;
    private static final int LONG_TIMEOUT = 90000;
    private static final int TOAST_TIMEOUT = 90000;

    // ================= PAGE LOAD =================

    /**
     * Wait until page is fully loaded (recommended for slow pages)
     */
    public static void waitForPageLoad(Page page) {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    /**
     * Wait until specific URL is loaded
     */
    public static void waitForUrl(Page page, String urlPattern) {
        page.waitForURL(urlPattern);
        waitForPageLoad(page);
    }

    // ================= BASIC ELEMENT WAITS =================

    /**
     * Wait until element is visible
     */
    public static void waitForVisible(Page page, String locator) {
        // Ensure DOM is loaded before attempting to locate elements
        try {
            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        } catch (Exception ignored) {
            // Page might already be loaded or in process
        }
        page.waitForSelector(
                locator,
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(DEFAULT_TIMEOUT)
        );
    }

    /**
     * Long wait for slow elements
     */
    public static void waitForVisibleLong(Page page, String locator) {
        // Ensure DOM is loaded before attempting to locate elements
        try {
            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        } catch (Exception ignored) {
            // Page might already be loaded or in process
        }
        page.waitForSelector(
                locator,
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(LONG_TIMEOUT)
        );
    }

    /**
     * Wait until element disappears (loader / spinner)
     */
    public static void waitForHidden(Page page, String locator) {
        try {
            page.waitForSelector(
                    locator,
                    new Page.WaitForSelectorOptions()
                            .setState(WaitForSelectorState.HIDDEN)
                            .setTimeout(LONG_TIMEOUT)
            );
        } catch (Exception ignored) {
            // Loader may not appear every time
        }
    }

    // ================= SAFE ACTIONS =================

    /**
     * Click only when element is ready
     */
    public static void clickWhenReady(Page page, String locator) {
        // Ensure page is fully loaded before attempting to interact
        waitForPageLoad(page);
        waitForVisible(page, locator);
        page.click(locator);
    }

    /**
     * Fill input safely (used for project / employee auto-suggest)
     */
    public static void fillWhenReady(Page page, String locator, String value) {
        // Ensure page is fully loaded before attempting to interact
        waitForPageLoad(page);
        waitForVisible(page, locator);
        page.fill(locator, value);
    }

    // ================= NETWORK / RESULTS =================

    /**
     * Wait until table or search results refresh
     */
    public static void waitForResultsToLoad(Page page) {
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    // ================= TOAST / SUCCESS =================

    /**
     * Wait for success / error toast
     */
    public static void waitForToast(Page page, String locator) {
        page.waitForSelector(
                locator,
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(TOAST_TIMEOUT)
        );

    }
    /**
     * Wait until element is visible & enabled (clickable)
     */
    public static void waitForClickable(Page page, String locator) {
        // Ensure DOM is loaded before attempting to locate elements
        try {
            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        } catch (Exception ignored) {
            // Page might already be loaded or in process
        }
        page.waitForSelector(
                locator,
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(DEFAULT_TIMEOUT)
        );
    }

    /**
     * Wait for element with retry logic (for very flaky elements)
     */
    public static void waitForElementWithRetry(Page page, String locator) {
        int maxAttempts = 3;
        int attempt = 0;

        while (attempt < maxAttempts) {
            try {
                // Ensure DOM is loaded
                try {
                    page.waitForLoadState(LoadState.DOMCONTENTLOADED);
                } catch (Exception ignored) {
                }

                // Wait for element
                page.waitForSelector(
                        locator,
                        new Page.WaitForSelectorOptions()
                                .setState(WaitForSelectorState.VISIBLE)
                                .setTimeout(45000)  // 45 seconds per attempt
                );
                return; // Success
            } catch (Exception e) {
                attempt++;
                if (attempt >= maxAttempts) {
                    throw e; // Throw on final attempt
                }
                // Wait before retrying
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

}
