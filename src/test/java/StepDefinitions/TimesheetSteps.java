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

    // ================= NAVIGATION =================

    @When("user navigates to Time module")
    public void user_navigates_to_time_module() {
        log.info("Navigating to Time module");

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
                "My Timesheet page is NOT displayed",
                myTimesheetsPage.isMyTimesheetPageDisplayed()
        );
    }

    // ================= ADD TIMESHEET =================

    @When("user clicks on Edit button")
    public void user_clicks_on_edit_button() {
        log.info("Clicking Edit button");

        myTimesheetsPage.clickEdit();

        // ✅ CRITICAL FIX — initialize after navigation
        timesheetPage =
                new TimesheetPage(PlaywrightFactory.getPage());

        Assert.assertTrue(
                "Not navigated to Edit Timesheet page",
                PlaywrightFactory.getPage().url().contains("editTimesheet")
        );
    }

    @And("user adds new timesheet entry from json")
    public void user_adds_new_timesheet_entry_from_json() {
        timesheetPage.addNewEntryFromJson();
    }

    @And("user saves the timesheet")
    public void user_saves_the_timesheet() {
        timesheetPage.saveTimesheet();
    }

    @Then("success message should be displayed for timesheet")
    public void success_message_should_be_displayed_for_timesheet() {
        Assert.assertTrue(
                "Timesheet success toast is NOT displayed",
                timesheetPage.isSuccessToastDisplayed()
        );
    }

    @And("timesheet entry should appear in table")
    public void timesheet_entry_should_appear_in_table() {
        Assert.assertTrue(
                "Timesheet entry not found",
                myTimesheetsPage.isTimesheetEntryPresent()
        );
    }

    @And("total hours should be calculated correctly")
    public void total_hours_should_be_calculated_correctly() {

        int expected =
                timesheetPage.getExpectedTotalHoursFromJson();

        int actual =
                myTimesheetsPage.getDisplayedTotalHours();

        Assert.assertEquals(
                "Total hours mismatch",
                expected,
                actual
        );
    }

    // ================= EDIT TIMESHEET =================

    @And("user updates existing timesheet entry from json")
    public void user_updates_existing_timesheet_entry_from_json() {
        timesheetPage.updateExistingEntryFromJson();
    }

    @And("updated hours should be displayed in table")
    public void updated_hours_should_be_displayed_in_table() {
        Assert.assertTrue(
                myTimesheetsPage.isUpdatedEntryDisplayed()
        );
    }

    @And("total hours should be recalculated correctly")
    public void total_hours_should_be_recalculated_correctly() {

        int expected =
                timesheetPage.getUpdatedTotalHoursFromJson();

        int actual =
                myTimesheetsPage.getDisplayedTotalHours();

        Assert.assertEquals(
                "Updated total hours mismatch",
                expected,
                actual
        );
    }
}
