package StepDefinitions;

import Driver.PlaywrightFactory;
import Pages.*;
import Utils.LocatorReader;
import Utils.WaitUtil;
import Utils.ConfigReader;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

import com.microsoft.playwright.Page;

public class OrangeHRMSteps {

    //  DO NOT STORE PAGE HERE
    private Page getPage() {
        return PlaywrightFactory.getPage();
    }

    // ====================== LOGIN =========================

    @Given("user opens the OrangeHRM application")
    public void userOpensTheOrangeHRMApplication() {
        getPage().navigate(ConfigReader.get("baseUrl"));
    }

    @When("user enters valid username and password")
    public void userEntersValidUsernameAndPassword() {
        new LoginPage(getPage()).enterUsername();
        new LoginPage(getPage()).enterPassword();
    }

    @And("user clicks on the login button")
    public void userClicksOnTheLoginButton() {
        new LoginPage(getPage()).clickLoginButton();
    }

    @Then("dashboard page should be displayed")
    public void dashboardPageShouldBeDisplayed() {
        assertTrue(new LoginPage(getPage()).isDashboardDisplayed(),
                "Dashboard not displayed");
    }

    // ================= RECRUITMENT =======================

    @When("user navigates to Recruitment Candidates page")
    public void navigateToRecruitment() {
        new RecruitmentPage(getPage()).navigateToCandidatesPage();
    }

    @Then("candidates page header should be displayed")
    public void validateCandidatesPage() {
        assertTrue(new RecruitmentPage(getPage()).isCandidatesHeaderVisible(),
                "Candidates page not displayed");
    }

    // ================= ADD CANDIDATE =====================

    @When("user clicks on Add Candidate button")
    public void clickAddCandidate() {
        new AddCandidatePage(getPage()).clickAddCandidate();
    }

    @And("user enters candidate details from json")
    public void enterCandidateDetails() {
        new AddCandidatePage(getPage()).enterCandidateDetailsFromJson();
    }

    @And("user uploads resume file")
    public void uploadResume() {
        new AddCandidatePage(getPage()).uploadResume();
    }

    @And("user saves the candidate")
    public void saveCandidate() {
        new AddCandidatePage(getPage()).saveCandidate();
    }

    @Then("candidate should be saved successfully")
    public void validateCandidateSaved() {
        assertTrue(new AddCandidatePage(getPage()).isCandidateSaved(),
                "Candidate not saved successfully");
    }

    // ================= SEARCH ==================

    @When("user searches candidate")
    public void searchCandidate() {
        new CandidateListPage(getPage()).searchCandidate();
    }

    @Then("candidate record should be displayed")
    public void validateCandidateDisplayed() {
        assertTrue(new CandidateListPage(getPage()).isCandidateDisplayed(),
                "Candidate record not found");
    }

    // ================= DETAILS =================

    @When("user clicks on View button for selected candidate")
    public void clickView() {
        new CandidateListPage(getPage()).clickViewButton();
    }

    @Then("candidate details page should be displayed")
    public void validateDetailsPage() {
        assertTrue(new CandidateListPage(getPage()).isCandidateDetailsPageDisplayed(),
                "Candidate details page not displayed");
    }

    // ================= SHORTLIST =================

    @When("user clicks on Shortlist button")
    public void clickShortlist() {
        new CandidateListPage(getPage()).clickShortlist();
    }

    @And("user enters shortlist details from json")
    public void enterShortlistDetails() {
        new CandidateListPage(getPage()).enterShortlistDetails();
    }

    @And("user clicks on Save button")
    public void saveShortlist() {
        new CandidateListPage(getPage()).clickSaveShortlist();
    }

    // ================= INTERVIEW =================

    @When("user clicks on Schedule Interview button")
    public void clickScheduleInterview() {
        new CandidateListPage(getPage()).clickScheduleInterview();
    }

    @And("user enters interview details from json")
    public void enterInterviewDetails() {
        new CandidateListPage(getPage()).enterInterviewDetails();
    }

    @And("user clicks on Interview Save button")
    public void saveInterview() {
        new CandidateListPage(getPage()).clickSaveInterview();
    }

    @Then("candidate status should be updated to {string}")
    public void validateStatus(String expected) {
        assertEquals(expected,
                new CandidateListPage(getPage()).getCandidateStatus(),
                "Candidate status mismatch");
    }

    // ================= LOGOUT =================

    @When("user logs out from the application")
    public void userLogsOutFromTheApplication() {
        new LogoutPage(getPage()).logout();
    }

    @Then("login page should be displayed")
    public void loginPageShouldBeDisplayed() {

        String usernameField = LocatorReader.get("login.username");

        WaitUtil.waitForVisible(getPage(), usernameField);

        assertTrue(getPage().locator(usernameField).isVisible(),
                "Login page not displayed after logout");
    }

    @And("user session should be terminated")
    public void userSessionShouldBeTerminated() {
        assertTrue(getPage().url().contains("/auth/login"),
                "Session not terminated properly");
    }
}