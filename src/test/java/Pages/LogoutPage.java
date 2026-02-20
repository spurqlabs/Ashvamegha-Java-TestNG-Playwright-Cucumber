package Pages;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.WaitUtil;

public class LogoutPage {

    private final Page page;

    public LogoutPage(Page page) {
        this.page = page;
    }

    public void logout() {

        String userDropdown =
                LocatorReader.get("logout.userDropdown");

        String logoutBtn =
                LocatorReader.get("logout.logoutButton");

        // Open user dropdown
        WaitUtil.clickWhenReady(page, userDropdown);

        // Click logout
        WaitUtil.clickWhenReady(page, logoutBtn);

        // Wait for login page to appear
        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("login.username")
        );
    }
}
