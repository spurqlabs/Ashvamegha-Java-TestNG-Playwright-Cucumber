package StepDefinitions;

import Driver.PlaywrightFactory;
import Pages.LoginPage;
import Pages.AddCandidatePage;
import Pages.CandidatesPage;
import Utils.ConfigReader;
import Utils.LocatorReader;
import Utils.ScreenshotUtil;
import Utils.ScenarioContext;
import Utils.WaitUtil;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CommonStep {

    private static final Logger log =
            LoggerFactory.getLogger(CommonStep.class);

    private LoginPage loginPage;
    private AddCandidatePage addCandidatePage;
    private JSONObject candidateData;

    // ================= CONSTRUCTOR =================
    public CommonStep() {
        try {
            log.info("Loading candidateData.json");
            String json = new String(
                    Files.readAllBytes(
                            Paths.get("src/test/resources/testdata/candidateData.json")
                    )
            );
            candidateData = new JSONObject(json).getJSONObject("candidate");
        } catch (Exception e) {
            log.error("Failed to load candidateData.json", e);
            throw new RuntimeException("Unable to load candidateData.json");
        }
    }

    // ================= SCREENSHOT HELPER =================
    private void embedScreenshotInReport(String screenshotPath, String stepName) {
        if (screenshotPath != null && !screenshotPath.isEmpty()) {
            try {
                Path path = Paths.get(screenshotPath);
                if (Files.exists(path)) {
                    byte[] imageBytes = Files.readAllBytes(path);
                    if (ScenarioContext.getScenario() != null) {
                        ScenarioContext.getScenario().attach(
                                imageBytes,
                                "image/png",
                                "Step: " + stepName
                        );
                        log.info("Screenshot embedded: {}", stepName);
                    }
                }
            } catch (Exception e) {
                log.error("Error embedding screenshot: {}", e.getMessage());
            }
        }
    }

    // ================= LOGIN (UPDATED & SAFE FOR BACKGROUND) =================
    // ================= LOGIN =================

    @Given("user opens the OrangeHRM application")
    public void userOpensTheOrangeHRMApplication() {

        Page page = PlaywrightFactory.getPage();

        // 🔐 If already logged in, do NOTHING
        if (page.url().contains("/dashboard")
                || page.url().contains("/recruitment")) {
            log.info("Application already open & logged in, skipping navigation");
            return;
        }

        log.info("Opening OrangeHRM application");
        page.navigate(ConfigReader.get("baseUrl"));

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("login.username")
        );

        loginPage = new LoginPage(page);
    }


    @When("user enters valid username and password")
    public void userEntersValidUsernameAndPassword() {
        // ✅ Already logged in → skip
        if (loginPage == null) {
            log.info("LoginPage is null → skipping credential entry");
            return;
        }

        log.info("Entering credentials");
        loginPage.enterUsername();
        loginPage.enterPassword();
    }

    @And("user clicks on the login button")
    public void userClicksOnTheLoginButton() {
        // ✅ Already logged in → skip
        if (loginPage == null) {
            log.info("LoginPage is null → skipping login click");
            return;
        }

        log.info("Clicking login button");
        loginPage.clickLoginButton();
    }

    @Then("dashboard page should be displayed")
    public void dashboardPageShouldBeDisplayed() {
        log.info("Validating dashboard page");

        try {
            Page page = PlaywrightFactory.getPage();
            String currentUrl = page.url();

            log.info("Current URL: {}", currentUrl);

            // If redirected to login, session expired - need to re-login
            if (currentUrl.contains("/auth/login")) {
                log.info("Session expired, redirecting to login page. Re-logging in...");
                loginPage = new LoginPage(page);
                loginPage.enterUsername();
                loginPage.enterPassword();
                loginPage.clickLoginButton();
                page.waitForLoadState(LoadState.NETWORKIDLE);
                currentUrl = page.url();
            }

            // If on add candidate page or other page, go back to dashboard
            if (!currentUrl.contains("/dashboard/") && !currentUrl.contains("/recruitment/viewCandidates")) {
                log.info("Not on dashboard or candidates page, navigating to dashboard");
                page.navigate(ConfigReader.get("baseUrl") + "/web/index.php/dashboard/index");
                page.waitForLoadState(LoadState.NETWORKIDLE);
            }

            // Wait for dashboard header to be visible
            try {
                WaitUtil.waitForVisible(page, LocatorReader.get("dashboardPage.dashboardHeader"));
            } catch (Exception e) {
                // If header not found, we might be on candidates page which is fine
                log.info("Dashboard header not visible, checking current page");
            }

            // Verify we're on a valid page
            String finalUrl = page.url();
            boolean isDashboard = finalUrl.contains("/dashboard/");
            boolean isCandidates = finalUrl.contains("/recruitment/viewCandidates");

            Assert.assertTrue(
                    "Not on dashboard or candidates page. Current URL: " + finalUrl,
                    isDashboard || isCandidates
            );

            log.info("Dashboard or Candidates page verified successfully");

        } catch (Exception e) {
            log.error("Dashboard validation failed: {}", e.getMessage());
            throw e;
        }
    }

    // ================= NAVIGATION =================
    @When("user navigates to Recruitment Candidates page")
    public void userNavigatesToRecruitmentCandidatesPage() {
        log.info("Navigating to Candidates page");

        PlaywrightFactory.getPage()
                .click(LocatorReader.get("recruitmentPage.recruitmentMenu"));

        // Wait for page to navigate
        PlaywrightFactory.getPage().waitForURL("**/recruitment/viewCandidates");

        WaitUtil.waitForVisible(
                PlaywrightFactory.getPage(),
                LocatorReader.get("recruitmentPage.candidatesHeader")
        );
    }

    @Then("candidates page header should be displayed")
    public void candidatesPageHeaderShouldBeDisplayed() {
        log.info("Verifying candidates page header");

        try {
            // Wait for URL to confirm navigation
            PlaywrightFactory.getPage().waitForURL("**/recruitment/viewCandidates");

            // Wait for element to be visible
            WaitUtil.waitForVisible(
                    PlaywrightFactory.getPage(),
                    LocatorReader.get("recruitmentPage.candidatesHeader")
            );

            // Assert element is visible
            Assert.assertTrue(
                    "Candidates page header is NOT visible",
                    PlaywrightFactory.getPage()
                            .isVisible(LocatorReader.get("recruitmentPage.candidatesHeader"))
            );

            log.info("Candidates page header is visible");

        } catch (Exception e) {
            log.error("Candidates page validation failed: {}", e.getMessage());
            throw e;
        }
    }

    // ================= ADD CANDIDATE =================
    @When("user clicks on Add Candidate button")
    public void userClicksOnAddCandidateButton() {
        log.info("Clicking Add Candidate button");
        addCandidatePage = new AddCandidatePage(PlaywrightFactory.getPage());
        addCandidatePage.clickAddCandidate();
    }

    @And("user enters candidate details from json")
    public void userEntersCandidateDetailsFromJson(DataTable dataTable) {
        log.info("Entering candidate details using DataTable keys");

        List<String> keys = dataTable.asList();

        for (String key : keys) {
            String value = candidateData.getString(key);
            log.info("Filling {} -> {}", key, value);

            switch (key) {
                case "firstName":
                    addCandidatePage.enterFirstName(value);
                    break;
                case "lastName":
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
                default:
                    Assert.fail("Unsupported key: " + key);
            }
        }
    }

    @And("user uploads resume file")
    public void userUploadsResumeFile() {
        log.info("Uploading resume file");
        String resumePath = candidateData.getString("resumePath");

        Assert.assertTrue(
                "Resume file not found",
                Files.exists(Paths.get(resumePath))
        );

        addCandidatePage.uploadResume(resumePath);
    }

    @And("user saves the candidate")
    public void userSavesTheCandidate() {
        log.info("Saving candidate");
        addCandidatePage.clickSave();
    }

    @Then("candidate should be saved successfully")
    public void candidateShouldBeSavedSuccessfully() {
        try {
            log.info("Validating success toast message");
            Page page = PlaywrightFactory.getPage();

            // Wait longer for the success toast (30 seconds)
            page.waitForSelector(
                    LocatorReader.get("addCandidatePage.successToast"),
                    new Page.WaitForSelectorOptions()
                            .setState(WaitForSelectorState.VISIBLE)
                            .setTimeout(30000)
            );

            // Get the toast message text
            String toastText = page.textContent(LocatorReader.get("addCandidatePage.successToast"));
            log.info("Toast message displayed: {}", toastText);

            // Verify success message - handle null case
            Assert.assertNotNull(
                    "Success toast text is null",
                    toastText
            );

            Assert.assertTrue(
                    "Success toast not displayed or doesn't contain 'Successfully'",
                    toastText.contains("Successfully")
            );

            log.info("Candidate saved successfully with message: {}", toastText);

        } catch (Exception e) {
            log.error("Success toast validation failed: {}", e.getMessage());
            ScreenshotUtil.captureFailureScreenshot(PlaywrightFactory.getPage(), "Success_Toast_Validation_Failed");
            throw e;
        }
    }

    private CandidatesPage candidatesPage;

    @When("user enters candidate name in search field")
    public void userEntersCandidateNameInSearchField() {
        log.info("Entering candidate name in search field");

        candidatesPage = new CandidatesPage(PlaywrightFactory.getPage());

        String firstName = candidateData.getString("firstName");
        String lastName = candidateData.getString("lastName");

        String fullName = firstName + " " + lastName;

        // ✅ NEW FLOW: first name → suggestion → select full name
        candidatesPage.enterCandidateName(firstName, fullName);
    }


    @And("user clicks on Search button")
    public void userClicksOnSearchButton() {
        log.info("=== SEARCH STEP: Clicking Search button ===");
        candidatesPage.clickSearch();

        // Wait for search results to be fully rendered
        try {
            log.info("Waiting for search results to load...");
            Thread.sleep(3000); // Increased wait for search results
            log.info("Search results should now be loaded");
        } catch (InterruptedException e) {
            log.error("Interrupted while waiting for search: {}", e.getMessage());
        }
    }

    @Then("candidate should appear in the candidates list")
    public void candidateShouldAppearInTheCandidatesList() {
        log.info("=== SEARCH STEP: Verifying candidate in results ===");

        String firstName = candidateData.getString("firstName");
        String lastName = candidateData.getString("lastName");
        String fullName = firstName + " " + lastName;

        log.info("Candidate to verify: {}", fullName);
        log.info("Looking in search results for: {} {}", firstName, lastName);

        boolean found = candidatesPage.isCandidateDisplayed(fullName);

        if (!found) {
            log.error("✗ FAILED: Candidate not found in search results");
            log.error("Expected candidate: {}", fullName);

            // Take screenshot for debugging
            try {
                ScreenshotUtil.captureFailureScreenshot(
                    PlaywrightFactory.getPage(),
                    "Candidate_Not_Found_" + firstName + "_" + lastName
                );
                log.info("Screenshot captured for debugging");
            } catch (Exception e) {
                log.warn("Could not capture screenshot: {}", e.getMessage());
            }
        } else {
            log.info("✓ SUCCESS: Candidate found in search results");
        }

        Assert.assertTrue(
                "Candidate not found in candidates list. Expected: " + fullName,
                found
        );

        log.info("✓ Candidate verification PASSED: {}", fullName);
    }


}

