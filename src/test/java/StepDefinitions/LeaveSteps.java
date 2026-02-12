package StepDefinitions;

import com.microsoft.playwright.Page;
import Driver.PlaywrightFactory;
import Pages.DashboardPage;
import Pages.Leave.ApplyLeavePage;
import Pages.Leave.MyLeavePage;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeaveSteps {

    private static final Logger log =
            LoggerFactory.getLogger(LeaveSteps.class);

    private final Page page =
            PlaywrightFactory.getPage();

    private final DashboardPage dashboardPage =
            new DashboardPage(page);

    private final ApplyLeavePage applyLeavePage =
            new ApplyLeavePage(page);

    private final MyLeavePage myLeavePage =
            new MyLeavePage(page);

    // ================= APPLY LEAVE =================

    @When("user navigates to Leave module")
    public void user_navigates_to_leave_module() {
        log.info("Navigating to Leave → Apply Leave page");
        applyLeavePage.navigateToApplyLeavePage();
    }

    @And("user navigates to Apply Leave page")
    public void user_navigates_to_apply_leave_page() {
        log.info("Apply Leave page navigation completed");
        // Navigation handled inside navigateToApplyLeavePage()
    }

    @And("user fills leave application form from json")
    public void user_fills_leave_application_form_from_json() {
        log.info("Filling leave application form using JSON data");
        applyLeavePage.fillLeaveApplicationFromJson();
    }

    @And("user submits leave application")
    public void user_submits_leave_application() {
        log.info("Submitting leave application");
        applyLeavePage.submitLeaveApplication();
    }

    @Then("success message should be displayed for leave")
    public void success_message_should_be_displayed_for_leave() {
        log.info("Validating leave success message");
        Assert.assertTrue(
                "Leave success message was NOT displayed",
                applyLeavePage.isSuccessMessageDisplayed()
        );
        log.info("Leave applied successfully");
    }

    // ================= MY LEAVE =================

    @When("user navigates to My Leave page")
    public void user_navigates_to_my_leave_page() {
        log.info("Navigating to My Leave page");
        myLeavePage.navigateToMyLeavePage();
    }

    @Then("my leave page should be displayed")
    public void my_leave_page_should_be_displayed() {
        log.info("Validating My Leave page is displayed");
        Assert.assertTrue(
                "My Leave page is NOT displayed",
                myLeavePage.isLeaveRecordDisplayed()
        );
        log.info("My Leave page displayed successfully");
    }

    @When("user filters leave by date range from json")
    public void user_filters_leave_by_date_range() {
        log.info("Filtering leave records using date range from JSON");
        myLeavePage.filterLeaveByDateRangeFromJson();
    }

    @Then("applied leave should appear in leave list")
    public void applied_leave_should_appear_in_leave_list() {
        log.info("Validating applied leave appears in leave list");
        Assert.assertTrue(
                "Applied leave record NOT found in leave list",
                myLeavePage.isLeaveRecordDisplayed()
        );
        log.info("Applied leave record found");
    }

    @And("leave status should be {string}")
    public void leave_status_should_be(String expectedStatus) {
        log.info("Validating leave status. Expected: {}", expectedStatus);
        String actualStatus = myLeavePage.getLeaveStatus();

        Assert.assertEquals(
                "Leave status mismatch",
                expectedStatus,
                actualStatus
        );
        log.info("Leave status validated successfully: {}", actualStatus);
    }


}
