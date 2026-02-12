package Pages;
import com.microsoft.playwright.Page;
import Utils.TestDataReader;
import Utils.LocatorReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {

    private final Page page;
    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(Page page) {
        this.page = page;
    }

    public void enterUsername() {
        try {
            String username = TestDataReader.get("login.username");
            log.info("Entering username: {}", username);
            page.fill(
                    LocatorReader.get("login.username"),
                    username
            );
            log.info("Username entered successfully");
        } catch (Exception e) {
            log.error("Error entering username: {}", e.getMessage());
            throw e;
        }
    }

    public void enterPassword() {
        try {
            String password = TestDataReader.get("login.password");
            log.info("Entering password");
            page.fill(
                    LocatorReader.get("login.password"),
                    password
            );
            log.info("Password entered successfully");
        } catch (Exception e) {
            log.error("Error entering password: {}", e.getMessage());
            throw e;
        }
    }

    public void clickLoginButton() {
        try {
            log.info("Clicking login button");
            page.click(LocatorReader.get("login.loginBtn"));
            log.info("Login button clicked successfully");
        } catch (Exception e) {
            log.error("Error clicking login button: {}", e.getMessage());
            throw e;
        }
    }

    @SuppressWarnings("unused")
    public boolean isDashboardDisplayed() {
        try {
            boolean isDisplayed = page.isVisible(LocatorReader.get("dashboardPage.dashboardHeader"));
            log.info("Dashboard displayed check: {}", isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            log.error("Error checking if dashboard is displayed: {}", e.getMessage());
            return false;
        }
    }
}
