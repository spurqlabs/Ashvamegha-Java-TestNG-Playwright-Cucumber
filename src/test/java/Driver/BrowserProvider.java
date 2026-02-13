package Driver;

import java.util.stream.Stream;

/**
 * Provides browser parameters for parallel cross-browser testing
 * This allows the same test to run on multiple browsers simultaneously
 */
@SuppressWarnings("unused")
public class BrowserProvider {


    /**
     * Returns available browsers for testing
     * @return Stream of browser names
     */
    public static Stream<String> provideBrowsers() {
        return Stream.of("chrome", "firefox", "edge");
    }

    /**
     * Returns available browsers with full names
     * @return Stream of browser display names
     */
    public static Stream<String> provideBrowsersWithNames() {
        return Stream.of(
                "chrome",
                "firefox",
                "edge"
        );
    }

    /**
     * Get all configured browsers
     * @return Array of browser names
     */
    public static String[] getAllBrowsers() {
        return new String[]{"chrome", "firefox", "edge"};
    }
}
