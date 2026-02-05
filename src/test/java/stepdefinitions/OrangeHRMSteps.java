package stepdefinitions;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.*;
import pages.LoginPage;
import pages.RecruitmentPage;
import pages.AddCandidatePage;
import pages.CandidateListPage;
import utils.PlaywrightFactory;

import static org.junit.Assert.assertTrue;

import utils.CandidateDataReader;
import utils.LocatorReader;
import utils.WaitUtil;

public class OrangeHRMSteps {

    // get Page
    private Page page = PlaywrightFactory.getPage();

    private LoginPage loginPage = new LoginPage(page);
    private RecruitmentPage recruitmentPage = new RecruitmentPage(page);
    private AddCandidatePage addCandidatePage = new AddCandidatePage(page);
    private CandidateListPage candidateListPage = new CandidateListPage(page);

    @Given("user launches OrangeHRM application")
    public void userLaunchesOrangeHRMApplication() {
        // intentionally empty
    }

    @And("user logs in with valid credentials")
    public void userLogsInWithValidCredentials() {
        loginPage.login(
                CandidateDataReader.getUsername(),
                CandidateDataReader.getPassword()
        );
    }

    @Then("dashboard should be displayed")
    public void dashboardShouldBeDisplayed() {

        String dashboardHeader =
                LocatorReader.get("dashboardPage", "dashboardHeader");

        WaitUtil.waitForVisible(page, dashboardHeader);
        assertTrue(page.isVisible(dashboardHeader));
    }

    @When("user navigates to Recruitment Candidates page")
    public void userNavigatesToRecruitmentCandidatesPage() {
        recruitmentPage.navigateToCandidatesPage();
    }

    @Then("candidates page should be displayed")
    public void candidatesPageShouldBeDisplayed() {
        assertTrue(recruitmentPage.isCandidatesPageDisplayed());
    }

    @Given("user is on Recruitment Candidates page")
    public void userIsOnRecruitmentCandidatesPage() {
        recruitmentPage.navigateToCandidatesPage();
    }

    @When("user clicks on Add Candidate")
    public void userClicksOnAddCandidate() {
        addCandidatePage.clickAddCandidate();
    }

    @And("user enters candidate first name")
    public void userEntersCandidateFirstName() {
        addCandidatePage.enterFirstName();
    }

    @And("user enters candidate last name")
    public void userEntersCandidateLastName() {
        addCandidatePage.enterLastName();
    }

    @And("user enters candidate email")
    public void userEntersCandidateEmail() {
        addCandidatePage.enterEmail();
    }

    @And("user enters candidate phone number")
    public void userEntersCandidatePhoneNumber() {
        addCandidatePage.enterPhone();
    }

    @And("user selects vacancy from dropdown")
    public void userSelectsVacancyFromDropdown() {
        addCandidatePage.selectVacancy();
    }

    @And("user uploads candidate resume")
    public void userUploadsCandidateResume() {
        addCandidatePage.uploadResume();
    }

    @And("user enters Keywords")
    public void userEntersKeywords() {
        addCandidatePage.enterKeywords();
    }

    @And("user saves the candidate")
    public void userSavesTheCandidate() {
        addCandidatePage.saveCandidate();
    }

    @Then("success message should be displayed")
    public void successMessageShouldBeDisplayed() {
        addCandidatePage.verifySuccessMessage();
    }

    @When("user searches candidate by name or email")
    public void userSearchesCandidateByNameOrEmail() {
        candidateListPage.searchCandidateByName();
    }

    @Then("candidate search results should be displayed")
    public void candidateSearchResultsShouldBeDisplayed() {
        assertTrue(
                "Candidate search result not displayed by name",
                candidateListPage.isSearchResultDisplayedByName()
        );
    }

    @And("user selects a candidate from candidate list")
    public void userSelectsACandidateFromCandidateList() {
        candidateListPage.selectCandidateFromList();
    }

    @When("user opens candidate details")
    public void userOpensCandidateDetails() {
        candidateListPage.openCandidateDetails();
    }

    @Then("candidate detail page should be displayed")
    public void candidateDetailPageShouldBeDisplayed() {

        String candidateHeader =
                LocatorReader.get("candidateDetailsPage", "candidateHeader");

        WaitUtil.waitForVisible(page, candidateHeader);

        assertTrue(
                "Candidate detail page not displayed",
                page.isVisible(candidateHeader)
        );
    }
}
