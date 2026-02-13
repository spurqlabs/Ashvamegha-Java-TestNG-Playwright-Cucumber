package Pages;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.WaitUtil;

public class DashboardPage {

    private final Page page;

    // ================= CONSTRUCTOR =================

    public DashboardPage(Page page) {
        this.page = page;
    }

    // ================= LOGOUT =================
    public void logout() {

        String userDropdown =
                LocatorReader.get("dashboard.userDropdown");

        String logoutBtn =
                LocatorReader.get("dashboard.logoutButton");

        // Open user dropdown
        WaitUtil.waitForVisible(page, userDropdown);
        page.click(userDropdown);

        // Click logout
        WaitUtil.waitForVisible(page, logoutBtn);
        page.click(logoutBtn);

        // Wait for navigation back to login
        WaitUtil.waitForPageLoad(page);

    }
}
