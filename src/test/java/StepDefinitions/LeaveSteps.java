package StepDefinitions;

import Driver.PlaywrightFactory;
import Pages.Leave.ApplyLeavePage;
import Pages.Leave.EntitlementPage;
import Pages.Leave.MyLeavePage;
import Utils.LocatorReader;
import Utils.WaitUtil;
import io.cucumber.java.en.*;
import org.junit.Assert;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeaveSteps {

    private final Page page = PlaywrightFactory.getPage();

    private EntitlementPage entitlementPage = new EntitlementPage(page);
    private ApplyLeavePage applyLeavePage = new ApplyLeavePage(page);
    private MyLeavePage myLeavePage = new MyLeavePage(page);

    // Create a logger instance
    private static final Logger logger = LoggerFactory.getLogger(LeaveSteps.class);

    // ================= NAVIGATION =================

    @When("user navigates to Leave module")
    public void user_navigates_to_leave_module() {
        logger.info("Navigating to Leave module");

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("dashboardPage.dashboardHeader")
        );

        logger.info("Clicking on Leave menu");
        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("leavePage.leaveMenu")
        );

        WaitUtil.waitForPageLoad(page);
        logger.info("Page load completed, Leave module opened");
    }

    // ================= ENTITLEMENT =================

    @And("user clicks on Entitlements tab")
    public void user_clicks_on_entitlements_tab() {
        logger.info("Clicking on Entitlements tab");
        entitlementPage.clickEntitlementsTab();
    }

    @And("user selects Add Entitlements option")
    public void user_selects_add_entitlements_option() {
        logger.info("Selecting Add Entitlements option");
        entitlementPage.clickAddEntitlementsOption();
    }

    @Then("Add Leave Entitlement page should be displayed")
    public void add_leave_entitlement_page_should_be_displayed() {
        logger.info("Verifying Add Leave Entitlement page display");
        Assert.assertTrue(entitlementPage.isAddEntitlementPageDisplayed());
        logger.info("Add Leave Entitlement page displayed successfully");
    }

    @When("user searches and selects logged-in employee name")
    public void user_searches_and_selects_logged_in_employee_name() {
        logger.info("Selecting logged-in employee from header");
        entitlementPage.selectLoggedInEmployeeFromHeader();
    }

    @And("user selects leave type {string}")
    public void user_selects_leave_type(String leaveType) {
        logger.info("Selecting leave type: {}", leaveType);
        entitlementPage.selectLeaveType(leaveType);
        // Wait for entitlement field to appear after leave type selection
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @And("user enters entitlement days {string}")
    public void user_enters_entitlement_days(String days) {
        logger.info("Entering entitlement days: {}", days);
        entitlementPage.enterEntitlementDays(days);
    }


    @And("user clicks on Save entitlement button")
    public void user_clicks_on_save_entitlement_button() {
        logger.info("Clicking on Save Entitlement button");
        entitlementPage.clickSaveEntitlement();
    }

    @Then("Updating Entitlement popup should be displayed")
    public void updating_entitlement_popup_should_be_displayed() {
        logger.info("Verifying Updating Entitlement popup display");
        Assert.assertTrue(entitlementPage.isUpdatePopupDisplayed());
        logger.info("Update Entitlement popup displayed successfully");
    }

    @When("user confirms entitlement update")
    public void user_confirms_entitlement_update() {
        logger.info("Confirming entitlement update");
        entitlementPage.confirmUpdate();
    }

    @Then("entitlement record should be displayed in records")
    public void entitlement_record_should_be_displayed_in_records() {
        logger.info("Verifying entitlement record display in records");
        Assert.assertTrue(entitlementPage.isEntitlementRecordDisplayed());
        logger.info("Entitlement record displayed successfully");
    }

    // ================= APPLY LEAVE =================

    @When("user navigates to Apply Leave page")
    public void user_navigates_to_apply_leave_page() {
        logger.info("Navigating to Apply Leave page");
        applyLeavePage.navigateToApplyPage();
    }

    @Then("Apply Leave page should be displayed")
    public void apply_leave_page_should_be_displayed() {
        logger.info("Verifying Apply Leave page display");
        Assert.assertTrue(applyLeavePage.isApplyPageDisplayed());
        logger.info("Apply Leave page displayed successfully");
    }

    @And("user selects leave type from json")
    public void user_selects_leave_type_from_json() {
        logger.info("Selecting leave type from JSON");
        applyLeavePage.selectLeaveTypeFromJson();
    }

    @And("user selects from date and to date from json")
    public void user_selects_from_date_and_to_date_from_json() {
        logger.info("Selecting from date and to date from JSON");
        applyLeavePage.selectDatesFromJson();
    }

    @And("user clicks on Apply button")
    public void user_clicks_on_apply_button() {
        logger.info("Clicking on Apply button");
        applyLeavePage.clickApply();
    }

    @Then("leave application should be submitted successfully")
    public void leave_application_should_be_submitted_successfully() {
        logger.info("Verifying if leave application is successfully submitted");
        Assert.assertTrue(applyLeavePage.isLeaveAppliedSuccessfully());
        logger.info("Leave application submitted successfully");
    }

    // ================= MY LEAVE =================

    @When("user navigates to My Leave page")
    public void user_navigates_to_my_leave_page() {
        logger.info("Navigating to My Leave page");
        myLeavePage.navigateToMyLeavePage();
    }

    @And("user filters leave by date range from json")
    public void user_filters_leave_by_date_range_from_json() {
        logger.info("Filtering leaves by date range from JSON");
        myLeavePage.filterByDateRangeFromJson();
    }

    @And("user clicks on Search button")
    public void user_clicks_on_search_button() {
        logger.info("Clicking on Search button");
        myLeavePage.clickSearch();
    }

    @Then("applied leave record should be displayed")
    public void applied_leave_record_should_be_displayed() {
        logger.info("Verifying if applied leave record is displayed");
        Assert.assertTrue(myLeavePage.isLeaveRecordDisplayed());
        logger.info("Applied leave record displayed successfully");
    }
}
