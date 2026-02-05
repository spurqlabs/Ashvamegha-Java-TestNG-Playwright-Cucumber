package pages;

import com.microsoft.playwright.Page;
import utils.LocatorReader;
import utils.WaitUtil;

public class LoginPage {

    private Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void login(String username, String password) {

        String userField = LocatorReader.get("loginPage", "username");
        String passField = LocatorReader.get("loginPage", "password");
        String loginBtn  = LocatorReader.get("loginPage", "loginButton");

        // Explicit waits
        WaitUtil.waitForVisible(page, userField);
        page.fill(userField, username);

        WaitUtil.waitForVisible(page, passField);
        page.fill(passField, password);

        WaitUtil.waitForClickable(page, loginBtn);
        page.click(loginBtn);
    }
}
