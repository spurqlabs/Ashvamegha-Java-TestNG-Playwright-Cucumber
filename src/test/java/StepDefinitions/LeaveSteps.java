package StepDefinitions;

import Driver.PlaywrightFactory;
import Pages.Leave.ApplyLeavePage;
import Pages.Leave.EntitlementPage;
import Pages.Leave.MyLeavePage;
import Utils.TestDataReader;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class LeaveSteps {

    private final EntitlementPage entitlementPage =
            new EntitlementPage(PlaywrightFactory.getPage());

    private final ApplyLeavePage applyLeavePage =
            new ApplyLeavePage(PlaywrightFactory.getPage());

    private final MyLeavePage myLeavePage =
            new MyLeavePage(PlaywrightFactory.getPage());

    // ===================== NAVIGATION =====================

    @When("user navigates to Leave module")
    public void user_navigates_to_leave_module() {
        entitlementPage.navigateToLeaveModule();
    }

    // ===================== ENTITLEMENT =====================

    @When("user clicks on Entitlements tab")
    public void user_clicks_on_entitlements_tab() {
        entitlementPage.clickEntitlementsTab();
    }

    @When("user selects Add Entitlements option")
    public void user_selects_add_entitlements_option() {
        entitlementPage.clickAddEntitlementsOption();
    }

    @Then("Add Leave Entitlement page should be displayed")
    public void add_leave_entitlement_page_should_be_displayed() {
        assertTrue(
                entitlementPage.isAddEntitlementPageDisplayed(),
                "Add Leave Entitlement page not displayed"
        );
    }

    @When("user searches and selects logged-in employee name")
    public void user_searches_and_selects_logged_in_employee_name() {
        entitlementPage.selectLoggedInEmployeeFromHeader();
    }

    @When("user selects leave type {string}")
    public void user_selects_leave_type(String leaveType) {
        entitlementPage.selectLeaveType(leaveType);
    }

    @When("user enters entitlement days {string}")
    public void user_enters_entitlement_days(String days) {
        entitlementPage.enterEntitlementDays(days);
    }

    @When("user clicks on Save entitlement button")
    public void user_clicks_on_save_entitlement_button() {
        entitlementPage.clickSaveEntitlement();
    }

    @Then("Updating Entitlement popup should be displayed")
    public void updating_entitlement_popup_should_be_displayed() {
        assertTrue(
                entitlementPage.isUpdatePopupDisplayed(),
                "Update Entitlement popup not displayed"
        );
    }

    @When("user confirms entitlement update")
    public void user_confirms_entitlement_update() {
        entitlementPage.confirmUpdate();
    }

    @Then("entitlement record should be displayed in records")
    public void entitlement_record_should_be_displayed_in_records() {
        assertTrue(
                entitlementPage.isEntitlementRecordDisplayed(),
                "Entitlement record not displayed"
        );
    }

    // ===================== APPLY LEAVE =====================

    @When("user navigates to Apply Leave page")
    public void user_navigates_to_apply_leave_page() {
        applyLeavePage.navigateToApplyPage();
    }

    @Then("Apply Leave page should be displayed")
    public void apply_leave_page_should_be_displayed() {
        assertTrue(
                applyLeavePage.isApplyPageDisplayed(),
                "Apply Leave page not displayed"
        );
    }

    @When("user selects leave type from json")
    public void user_selects_leave_type_from_json() {
        String leaveType = TestDataReader.get("leave.apply.leaveType");
        applyLeavePage.selectLeaveType(leaveType);
    }

    @When("user selects from date and to date from json")
    public void user_selects_from_date_and_to_date_from_json() {
        String fromDate = TestDataReader.get("leave.apply.fromDate");
        String toDate = TestDataReader.get("leave.apply.toDate");
        applyLeavePage.selectDates(fromDate, toDate);
    }

    @When("user clicks on Apply button")
    public void user_clicks_on_apply_button() {
        applyLeavePage.clickApply();
    }

    @Then("leave application should be submitted successfully")
    public void leave_application_should_be_submitted_successfully() {
        assertTrue(
                applyLeavePage.isLeaveAppliedSuccessfully(),
                "Leave application not submitted successfully"
        );
    }

    // ===================== MY LEAVE =====================

    @When("user navigates to My Leave page")
    public void user_navigates_to_my_leave_page() {
        myLeavePage.navigateToMyLeavePage();
    }

    @When("user filters leave by date range from json")
    public void user_filters_leave_by_date_range_from_json() {
        String fromDate = TestDataReader.get("leave.verify.fromDate");
        String toDate = TestDataReader.get("leave.verify.toDate");
        myLeavePage.filterByDateRange(fromDate, toDate);
    }

    @When("user clicks on Search button")
    public void user_clicks_on_search_button() {
        myLeavePage.clickSearch();
    }

    @Then("applied leave record should be displayed")
    public void applied_leave_record_should_be_displayed() {
        assertTrue(
                myLeavePage.isLeaveRecordDisplayed(),
                "Applied leave record not found"
        );
    }
}