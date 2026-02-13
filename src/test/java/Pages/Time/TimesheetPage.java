package Pages.Time;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.TestDataReader;
import Utils.WaitUtil;

import java.util.Map;

public class TimesheetPage {

    private final Page page;

    public TimesheetPage(Page page) {
        this.page = page;
    }

    // ================= ADD ENTRY =================

    public void addNewEntryFromJson() {
        selectProjectFromJson();
        selectActivityFromJson();
        activateTimesheetRow();
        fillHoursFromJson("timesheet.hours");
    }

    // ================= UPDATE ENTRY =================

    public void updateExistingEntryFromJson() {
        activateTimesheetRow();
        fillHoursFromJson("timesheet.updatedHours");
    }

    // ================= PROJECT =================

    private void selectProjectFromJson() {

        String fullProject =
                TestDataReader.get("timesheet.project");

        String partialProject =
                fullProject.split(" ")[0];

        WaitUtil.fillWhenReady(
                page,
                LocatorReader.get("timesheetPage.projectInput"),
                partialProject
        );

        page.locator(
                        LocatorReader.get("timesheetPage.projectSuggestion")
                )
                .filter(new Locator.FilterOptions().setHasText(fullProject))
                .first()
                .click();
    }

    // ================= ACTIVITY =================

    private void selectActivityFromJson() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("timesheetPage.activityDropdown")
        );

        String activity =
                TestDataReader.get("timesheet.activity");

        page.click(
                LocatorReader.get("timesheetPage.activityOption")
                        .replace("{ACTIVITY}", activity)
        );
    }

    // ================= ROW ACTIVATION =================

    private void activateTimesheetRow() {

        page.locator(
                LocatorReader.get("timesheetPage.mondayInput")
        ).click();
    }

    // ================= HOURS =================

    public void fillHoursFromJson(String jsonPath) {

        Map<String, String> hours =
                TestDataReader.getMap(jsonPath);

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("timesheetPage.mondayInput")
        );

        page.locator(LocatorReader.get("timesheetPage.mondayInput")).fill(hours.get("mon"));
        page.locator(LocatorReader.get("timesheetPage.tuesdayInput")).fill(hours.get("tue"));
        page.locator(LocatorReader.get("timesheetPage.wednesdayInput")).fill(hours.get("wed"));
        page.locator(LocatorReader.get("timesheetPage.thursdayInput")).fill(hours.get("thu"));
        page.locator(LocatorReader.get("timesheetPage.fridayInput")).fill(hours.get("fri"));
        page.locator(LocatorReader.get("timesheetPage.saturdayInput")).fill(hours.get("sat"));
        page.locator(LocatorReader.get("timesheetPage.sundayInput")).fill(hours.get("sun"));
    }

    // ================= SAVE =================

    public void saveTimesheet() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("timesheetPage.saveButton")
        );

        WaitUtil.waitForToast(
                page,
                LocatorReader.get("timesheetPage.successToast")
        );
    }

    public boolean isSuccessToastDisplayed() {
        return page
                .locator(LocatorReader.get("timesheetPage.successToast"))
                .isVisible();
    }

    // ================= FIXED TOTAL VALIDATION =================

    public int getDisplayedTotalHours() {

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("timesheetPage.totalCell")
        );

        String text =
                page.locator(
                        LocatorReader.get("timesheetPage.totalCell")
                ).textContent().trim();

        // 🔥 FIX: Handle decimal correctly (40.00 → 40)
        double total = Double.parseDouble(text.replaceAll("[^0-9.]", ""));

        return (int) total;

    }

    public boolean isUpdatedHoursDisplayed() {

        String expected =
                TestDataReader.get("timesheet.updatedHours.mon");

        String actual =
                page.locator(
                        LocatorReader.get("timesheetPage.mondayInput")
                ).inputValue();

        return actual.equals(expected);
    }

    public int getExpectedTotalHoursFromJson() {
        return Integer.parseInt(
                TestDataReader.get("timesheet.expectedTotal")
        );
    }

    public int getUpdatedTotalHoursFromJson() {
        return Integer.parseInt(
                TestDataReader.get("timesheet.updatedTotal")
        );
    }
}
