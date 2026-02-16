package StepDefinitions;

import Driver.PlaywrightFactory;
import Pages.Time.MyTimesheetsPage;
import Pages.Time.TimesheetPage;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimesheetSteps {

    private static final Logger log =
            LoggerFactory.getLogger(TimesheetSteps.class);

    private MyTimesheetsPage myTimesheetsPage;
    private TimesheetPage timesheetPage;

    // ======================================================
    // NAVIGATION
    // ======================================================

    @When("user navigates to Time module")
    public void user_navigates_to_time_module() {

        myTimesheetsPage =
                new MyTimesheetsPage(PlaywrightFactory.getPage());

        myTimesheetsPage.navigateToTimeModule();
    }

    @And("user navigates to My Timesheets page")
    public void user_navigates_to_my_timesheets_page() {

        myTimesheetsPage.openMyTimesheets();
    }

    @Then("my timesheet page should be displayed")
    public void my_timesheet_page_should_be_displayed() {

        Assert.assertTrue(
                "My Timesheet page not displayed",
                myTimesheetsPage.isMyTimesheetPageDisplayed()
        );
    }

    // ======================================================
    // EDIT / CREATE
    // ======================================================

    @When("user clicks on Edit button")
    public void user_clicks_on_edit_button() {

        myTimesheetsPage.clickEdit();

        timesheetPage =
                new TimesheetPage(PlaywrightFactory.getPage());
    }

    @And("user adds new timesheet entry from json")
    public void user_adds_new_timesheet_entry_from_json() {

        timesheetPage.addNewEntryFromJson();
    }

    @And("user updates existing timesheet entry from json")
    public void user_updates_existing_timesheet_entry_from_json() {

        // Same smart method handles update
        timesheetPage.addNewEntryFromJson();
    }

    @And("user saves the timesheet")
    public void user_saves_the_timesheet() {

        timesheetPage.saveTimesheet();
    }

    @Then("success message should be displayed for timesheet")
    public void success_message_should_be_displayed_for_timesheet() {

        Assert.assertTrue(
                "Success toast not visible",
                timesheetPage.isSuccessToastDisplayed()
        );
    }

    // ======================================================
    // TOTAL VALIDATION
    // ======================================================

    @And("total hours should be calculated correctly")
    public void total_hours_should_be_calculated_correctly() {

        int expected =
                timesheetPage.getExpectedTotalBasedOnRun();

        int actual =
                timesheetPage.getDisplayedTotalHours();

        Assert.assertEquals(
                "Total hours mismatch",
                expected,
                actual
        );

        log.info("Total validated successfully. Expected: {}, Actual: {}", expected, actual);
    }

    @And("updated hours should be displayed correctly")
    public void updated_hours_should_be_displayed_correctly() {

        // Wait a bit for page to settle after save
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int expected =
                timesheetPage.getExpectedTotalBasedOnRun();

        int actual =
                timesheetPage.getDisplayedTotalHours();

        Assert.assertEquals(
                "Updated hours mismatch",
                expected,
                actual
        );

        log.info("Updated hours validated successfully");
    }

    @And("total hours should be recalculated correctly")
    public void total_hours_should_be_recalculated_correctly() {

        int expected =
                timesheetPage.getExpectedTotalBasedOnRun();

        int actual =
                timesheetPage.getDisplayedTotalHours();

        Assert.assertEquals(
                "Recalculated total mismatch",
                expected,
                actual
        );

        log.info("Recalculated total validated successfully");
    }
}
