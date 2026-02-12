package StepDefinitions;

import Driver.PlaywrightFactory;
import Pages.*;
import Utils.*;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CommonStep {

    private static final Logger log =
            LoggerFactory.getLogger(CommonStep.class);

    private LoginPage loginPage;
    private AddCandidatePage addCandidatePage;
    private CandidatesPage candidatesPage;
    private CandidateDetailsPage candidateDetailsPage;

    private final JSONObject candidateData;

    // ================= CONSTRUCTOR =================
    public CommonStep() {
        try {
            String json = new String(
                    Files.readAllBytes(
                            Paths.get("src/test/resources/TestData/candidateData.json")
                    )
            );
            candidateData = new JSONObject(json).getJSONObject("candidate");
        } catch (Exception e) {
            throw new RuntimeException("Unable to load candidateData.json", e);
        }
    }

    // ================= LOGIN =================
    @Given("user opens the OrangeHRM application")
    public void user_opens_the_application() {

        Page page = PlaywrightFactory.getPage();

        if (!page.url().contains("/dashboard")) {
            page.navigate(ConfigReader.get("baseUrl"));
        }

        WaitUtil.waitForVisible(page, LocatorReader.get("login.username"));
        loginPage = new LoginPage(page);
    }

    @When("user enters valid username and password")
    public void user_enters_credentials() {
        loginPage.enterUsername();
        loginPage.enterPassword();
    }

    @And("user clicks on the login button")
    public void user_clicks_login() {
        loginPage.clickLoginButton();
    }

    @Then("dashboard page should be displayed")
    public void dashboard_page_should_be_displayed() {

        Page page = PlaywrightFactory.getPage();

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("dashboardPage.dashboardHeader")
        );

        System.out.println("Dashboard loaded successfully");
    }



    // ================= NAVIGATION =================
    @When("user navigates to Recruitment Candidates page")
    public void navigate_to_candidates_page() {

        Page page = PlaywrightFactory.getPage();
        page.click(LocatorReader.get("recruitmentPage.recruitmentMenu"));
        page.waitForURL("**/recruitment/viewCandidates");

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("recruitmentPage.candidatesHeader")
        );
    }

    @Then("candidates page header should be displayed")
    public void candidates_page_header_should_be_displayed() {

        Assert.assertTrue(
                PlaywrightFactory.getPage().isVisible(
                        LocatorReader.get("recruitmentPage.candidatesHeader")
                )
        );
    }

    // ================= ADD CANDIDATE =================
    @When("user clicks on Add Candidate button")
    public void click_add_candidate() {
        addCandidatePage = new AddCandidatePage(PlaywrightFactory.getPage());
        addCandidatePage.clickAddCandidate();
    }

    @And("user enters candidate details from json")
    public void enter_candidate_details(DataTable dataTable) {

        List<String> keys = dataTable.asList();
        String firstName = null;
        String lastName = null;

        for (String key : keys) {
            String value = candidateData.getString(key);

            switch (key) {
                case "firstName":
                    firstName = value;
                    addCandidatePage.enterFirstName(value);
                    break;
                case "lastName":
                    lastName = value;
                    addCandidatePage.enterLastName(value);
                    break;
                case "email":
                    addCandidatePage.enterEmail(value);
                    break;
                case "phone":
                    addCandidatePage.enterPhone(value);
                    break;
                case "vacancy":
                    addCandidatePage.selectVacancy(value);
                    break;
                case "keywords":
                    addCandidatePage.enterKeywords(value);
                    break;
            }
        }

        // ✅ Store candidate name ONCE
        ScenarioContext.set(
                "expectedCandidateName",
                firstName + " " + lastName
        );
    }

    @And("user uploads resume file")
    public void upload_resume() {
        addCandidatePage.uploadResume(
                candidateData.getString("resumePath")
        );
    }

    @And("user saves the candidate")
    public void save_candidate() {
        addCandidatePage.clickSave();
    }

    @Then("candidate should be saved successfully")
    public void candidate_saved_successfully() {

        Page page = PlaywrightFactory.getPage();

        page.waitForSelector(
                LocatorReader.get("addCandidatePage.successToast"),
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
        );

        Assert.assertTrue(
                "Success toast not displayed",
                page.textContent(
                        LocatorReader.get("addCandidatePage.successToast")
                ).contains("Successfully")
        );
    }

    // ================= SEARCH (FIXED) =================
    @When("user searches candidate")
    public void user_searches_candidate() {

        if (candidatesPage == null) {
            candidatesPage = new CandidatesPage(PlaywrightFactory.getPage());
        }

        String candidateName =
                ScenarioContext.get("expectedCandidateName");

        // 🔥 Fallback for second scenario
        if (candidateName == null || candidateName.isBlank()) {
            candidateName =
                    candidateData.getString("firstName") + " " +
                            candidateData.getString("lastName");

            ScenarioContext.set("expectedCandidateName", candidateName);
            log.info("Candidate name restored from test data: {}", candidateName);
        }

        candidatesPage.searchCandidate(candidateName);
    }

    @Then("candidate record should be displayed")
    public void candidate_record_should_be_displayed() {

        String expected =
                ScenarioContext.get("expectedCandidateName");

        String actual =
                candidatesPage.getDisplayedCandidateName();

        Assert.assertNotNull(
                "Candidate name in results is NULL",
                actual
        );

        Assert.assertFalse(
                "Candidate name in results is EMPTY",
                actual.isBlank()
        );

        Assert.assertEquals(
                "Candidate name mismatch in search results",
                expected,
                actual
        );
    }

    // ================= VIEW =================
    @When("user clicks on View button for the candidate")
    @When("user clicks on View button for selected candidate")
    public void user_clicks_on_view_button() {

        if (candidatesPage == null) {
            candidatesPage = new CandidatesPage(PlaywrightFactory.getPage());
        }

        candidatesPage.clickViewButtonForCandidate(
                ScenarioContext.get("expectedCandidateName")
        );

        candidateDetailsPage =
                new CandidateDetailsPage(PlaywrightFactory.getPage());
    }

    @Then("candidate details page should be displayed")
    public void candidate_details_page_should_be_displayed() {

        Assert.assertTrue(
                "Candidate details page not displayed",
                candidateDetailsPage.isCandidateDetailsPageDisplayed()
        );
    }

    // ================= SHORTLIST =================
    @When("user clicks on Shortlist button")
    public void click_shortlist() {
        candidateDetailsPage.clickShortlistButton();
    }

    @And("user enters shortlist details from json")
    public void enter_shortlist_details() {
        candidateDetailsPage.enterShortlistDetailsFromJson();
    }

    @And("user clicks on Save button")
    public void click_save_button() {
        candidateDetailsPage.saveShortlist();
    }

    @Then("candidate status should be updated to {string}")
    public void candidate_status_updated(String status) {

        Assert.assertEquals(
                status,
                candidateDetailsPage.getCandidateStatus()
        );
    }

    // ================= INTERVIEW =================
    @When("user clicks on Schedule Interview button")
    public void click_schedule_interview() {
        candidateDetailsPage.clickScheduleInterviewButton();
    }

    @And("user enters interview details from json")
    public void enter_interview_details() {
        candidateDetailsPage.enterInterviewDetailsFromJson();
    }

    @Then("interview should be scheduled successfully")
    public void interview_scheduled_successfully() {

        Assert.assertTrue(
                "Interview success toast not displayed",
                candidateDetailsPage.isSuccessToastDisplayed()
        );
    }
    // ================= LOGOUT =================

    @When("user logs out from the application")
    public void user_logs_out_from_the_application() {

        log.info("Logging out from OrangeHRM application");

        Page page = PlaywrightFactory.getPage();
        DashboardPage dashboardPage = new DashboardPage(page);

        dashboardPage.logout();

        log.info("Logout completed successfully");
    }

    @Then("login page should be displayed")
    public void login_page_should_be_displayed() {

        log.info("Validating login page is displayed after logout");

        Page page = PlaywrightFactory.getPage();

        Assert.assertTrue(
                "Login page not displayed after logout",
                page.isVisible(LocatorReader.get("login.username"))
        );
    }

    @And("user session should be terminated")
    public void user_session_should_be_terminated() {

        log.info("Validating user session termination");

        Page page = PlaywrightFactory.getPage();

        Assert.assertTrue(
                "User session not terminated properly",
                page.url().contains("/auth/login")
        );
    }

}
