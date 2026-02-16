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
        try {
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
        } catch (Exception e) {
            logger.error("Failed to navigate to Leave module", e);
            throw new RuntimeException("Failed to navigate to Leave module", e);
        }
    }

    // ================= ENTITLEMENT =================

    @And("user clicks on Entitlements tab")
    public void user_clicks_on_entitlements_tab() {
        try {
            logger.info("Clicking on Entitlements tab");
            entitlementPage.clickEntitlementsTab();
        } catch (Exception e) {
            logger.error("Failed to click Entitlements tab", e);
            throw new RuntimeException("Failed to click Entitlements tab", e);
        }
    }

    @And("user selects Add Entitlements option")
    public void user_selects_add_entitlements_option() {
        try {
            logger.info("Selecting Add Entitlements option");
            entitlementPage.clickAddEntitlementsOption();
        } catch (Exception e) {
            logger.error("Failed to select Add Entitlements option", e);
            throw new RuntimeException("Failed to select Add Entitlements option", e);
        }
    }

    @Then("Add Leave Entitlement page should be displayed")
    public void add_leave_entitlement_page_should_be_displayed() {
        try {
            logger.info("Verifying Add Leave Entitlement page display");
            Assert.assertTrue(entitlementPage.isAddEntitlementPageDisplayed());
            logger.info("Add Leave Entitlement page displayed successfully");
        } catch (Exception e) {
            logger.error("Failed to verify Add Leave Entitlement page display", e);
            throw new RuntimeException("Failed to verify Add Leave Entitlement page display", e);
        }
    }

    @When("user searches and selects logged-in employee name")
    public void user_searches_and_selects_logged_in_employee_name() {
        try {
            logger.info("Selecting logged-in employee from header");
            entitlementPage.selectLoggedInEmployeeFromHeader();
            logger.info("Successfully selected logged-in employee from header");
        } catch (Exception e) {
            logger.error("Failed to select logged-in employee from header. This may be due to suggestion dropdown not appearing or employee name not matching.", e);
            throw new RuntimeException("Failed to select logged-in employee from header", e);
        }
    }

    @And("user selects leave type {string}")
    public void user_selects_leave_type(String leaveType) {
        try {
            logger.info("Selecting leave type: {}", leaveType);
            entitlementPage.selectLeaveType(leaveType);
            // Wait for entitlement field to appear after leave type selection
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            logger.error("Failed to select leave type: " + leaveType, e);
            throw new RuntimeException("Failed to select leave type: " + leaveType, e);
        }
    }

    @And("user enters entitlement days {string}")
    public void user_enters_entitlement_days(String days) {
        try {
            logger.info("Entering entitlement days: {}", days);
            entitlementPage.enterEntitlementDays(days);
        } catch (Exception e) {
            logger.error("Failed to enter entitlement days: " + days, e);
            throw new RuntimeException("Failed to enter entitlement days: " + days, e);
        }
    }


    @And("user clicks on Save entitlement button")
    public void user_clicks_on_save_entitlement_button() {
        try {
            logger.info("Clicking on Save Entitlement button");
            entitlementPage.clickSaveEntitlement();
        } catch (Exception e) {
            logger.error("Failed to click Save Entitlement button", e);
            throw new RuntimeException("Failed to click Save Entitlement button", e);
        }
    }

    @Then("Updating Entitlement popup should be displayed")
    public void updating_entitlement_popup_should_be_displayed() {
        try {
            logger.info("Verifying Updating Entitlement popup display");
            Assert.assertTrue(entitlementPage.isUpdatePopupDisplayed());
            logger.info("Update Entitlement popup displayed successfully");
        } catch (Exception e) {
            logger.error("Failed to verify Update Entitlement popup display", e);
            throw new RuntimeException("Failed to verify Update Entitlement popup display", e);
        }
    }

    @When("user confirms entitlement update")
    public void user_confirms_entitlement_update() {
        try {
            logger.info("Confirming entitlement update");
            entitlementPage.confirmUpdate();
        } catch (Exception e) {
            logger.error("Failed to confirm entitlement update", e);
            throw new RuntimeException("Failed to confirm entitlement update", e);
        }
    }

    @Then("entitlement record should be displayed in records")
    public void entitlement_record_should_be_displayed_in_records() {
        try {
            logger.info("Verifying entitlement record display in records");
            Assert.assertTrue(entitlementPage.isEntitlementRecordDisplayed());
            logger.info("Entitlement record displayed successfully");
        } catch (Exception e) {
            logger.error("Failed to verify entitlement record display", e);
            throw new RuntimeException("Failed to verify entitlement record display", e);
        }
    }

    // ================= APPLY LEAVE =================

    @When("user navigates to Apply Leave page")
    public void user_navigates_to_apply_leave_page() {
        try {
            logger.info("Navigating to Apply Leave page");
            applyLeavePage.navigateToApplyPage();
        } catch (Exception e) {
            logger.error("Failed to navigate to Apply Leave page", e);
            throw new RuntimeException("Failed to navigate to Apply Leave page", e);
        }
    }

    @Then("Apply Leave page should be displayed")
    public void apply_leave_page_should_be_displayed() {
        try {
            logger.info("Verifying Apply Leave page display");
            Assert.assertTrue(applyLeavePage.isApplyPageDisplayed());
            logger.info("Apply Leave page displayed successfully");
        } catch (Exception e) {
            logger.error("Failed to verify Apply Leave page display", e);
            throw new RuntimeException("Failed to verify Apply Leave page display", e);
        }
    }

    @And("user selects leave type from json")
    public void user_selects_leave_type_from_json() {
        try {
            logger.info("Selecting leave type from JSON");
            applyLeavePage.selectLeaveTypeFromJson();
        } catch (Exception e) {
            logger.error("Failed to select leave type from JSON", e);
            throw new RuntimeException("Failed to select leave type from JSON", e);
        }
    }

    @And("user selects from date and to date from json")
    public void user_selects_from_date_and_to_date_from_json() {
        try {
            logger.info("Selecting from date and to date from JSON");
            applyLeavePage.selectDatesFromJson();
        } catch (Exception e) {
            logger.error("Failed to select from date and to date from JSON", e);
            throw new RuntimeException("Failed to select from date and to date from JSON", e);
        }
    }

    @And("user clicks on Apply button")
    public void user_clicks_on_apply_button() {
        try {
            logger.info("Clicking on Apply button");
            applyLeavePage.clickApply();
            logger.info("Apply button clicked successfully");

            // Additional wait for response
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            logger.error("Failed to click Apply button", e);
            throw new RuntimeException("Failed to click Apply button", e);
        }
    }

    @Then("leave application should be submitted successfully")
    public void leave_application_should_be_submitted_successfully() {
        try {
            logger.info("Verifying if leave application is successfully submitted");
            boolean isApplied = applyLeavePage.isLeaveAppliedSuccessfully();
            logger.info("Leave application status verified: {}", isApplied);
            Assert.assertTrue(isApplied);
            logger.info("Leave application submitted successfully");
        } catch (Exception e) {
            logger.error("Failed to verify leave application submission. Check if success toast appeared or if page navigated correctly.", e);
            throw new RuntimeException("Failed to verify leave application submission", e);
        }
    }

    // ================= MY LEAVE =================

    @When("user navigates to My Leave page")
    public void user_navigates_to_my_leave_page() {
        try {
            logger.info("Navigating to My Leave page");
            myLeavePage.navigateToMyLeavePage();
        } catch (Exception e) {
            logger.error("Failed to navigate to My Leave page", e);
            throw new RuntimeException("Failed to navigate to My Leave page", e);
        }
    }

    @And("user filters leave by date range from json")
    public void user_filters_leave_by_date_range_from_json() {
        try {
            logger.info("Filtering leaves by date range from JSON");
            myLeavePage.filterByDateRangeFromJson();
        } catch (Exception e) {
            logger.error("Failed to filter leaves by date range from JSON", e);
            throw new RuntimeException("Failed to filter leaves by date range from JSON", e);
        }
    }

    @And("user clicks on Search button")
    public void user_clicks_on_search_button() {
        try {
            logger.info("Clicking on Search button");
            myLeavePage.clickSearch();
        } catch (Exception e) {
            logger.error("Failed to click Search button", e);
            throw new RuntimeException("Failed to click Search button", e);
        }
    }

    @Then("applied leave record should be displayed")
    public void applied_leave_record_should_be_displayed() {
        try {
            logger.info("Verifying if applied leave record is displayed");
            Assert.assertTrue(myLeavePage.isLeaveRecordDisplayed());
            logger.info("Applied leave record displayed successfully");
        } catch (Exception e) {
            logger.error("Failed to verify applied leave record display", e);
            throw new RuntimeException("Failed to verify applied leave record display", e);
        }
    }
}
