package Pages;
import com.microsoft.playwright.Page;
import Utils.CandidateDataReader;
import Utils.LocatorReader;

public class LoginPage {

    private Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void enterUsername() {
        page.fill(
                LocatorReader.get("login.username"),
                CandidateDataReader.get("login.username")
        );
    }

    public void enterPassword() {
        page.fill(
                LocatorReader.get("login.password"),
                CandidateDataReader.get("login.password")
        );
    }

    public void clickLoginButton() {
        page.click(LocatorReader.get("login.loginBtn"));
    }

    public boolean isDashboardDisplayed() {
        return page.isVisible(LocatorReader.get("dashboardPage.dashboardHeader"));
    }
}
