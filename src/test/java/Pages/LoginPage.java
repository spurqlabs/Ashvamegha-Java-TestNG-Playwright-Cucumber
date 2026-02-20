package Pages;

import com.microsoft.playwright.Page;
import Utils.TestDataReader;
import Utils.LocatorReader;
import Utils.WaitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {

    private final Page page;
    private static final Logger log =
            LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(Page page) {
        this.page = page;
    }

    // ================= ENTER USERNAME =================

    public void enterUsername() {

        String username =
                TestDataReader.get("login.username");

        log.info("Entering username");

        WaitUtil.fillWhenReady(
                page,
                LocatorReader.get("login.username"),
                username
        );
    }

    // ================= ENTER PASSWORD =================

    public void enterPassword() {

        String password =
                TestDataReader.get("login.password");

        log.info("Entering password");

        WaitUtil.fillWhenReady(
                page,
                LocatorReader.get("login.password"),
                password
        );
    }

    // ================= CLICK LOGIN =================

    public void clickLoginButton() {

        log.info("Clicking login button");

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("login.loginBtn")
        );
    }

    // ================= COMBINED LOGIN METHOD =================
    // Use this if you want single-call login

    public void login(String username, String password) {

        log.info("Performing login with provided credentials");

        WaitUtil.fillWhenReady(
                page,
                LocatorReader.get("login.username"),
                username
        );

        WaitUtil.fillWhenReady(
                page,
                LocatorReader.get("login.password"),
                password
        );

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("login.loginBtn")
        );
    }

    // ================= DASHBOARD VALIDATION =================

    public boolean isDashboardDisplayed() {

        String dashboardHeader =
                LocatorReader.get("dashboardPage.dashboardHeader");

        WaitUtil.waitForVisible(page, dashboardHeader);

        return page.locator(dashboardHeader).isVisible();
    }
}
