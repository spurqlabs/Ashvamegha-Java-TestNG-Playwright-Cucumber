package Pages.Time;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.TestDataReader;
import Utils.WaitUtil;

import java.util.Map;

public class TimesheetPage {

    private final Page page;
    private boolean isFirstRun = false;

    public TimesheetPage(Page page) {
        this.page = page;
    }

    // ======================================================
    // ADD OR UPDATE ENTRY (SMART + FINAL LOGIC)
    // ======================================================

    public void addNewEntryFromJson() {

        String projectInput =
                LocatorReader.get("timesheetPage.projectInput");

        WaitUtil.waitForVisible(page, projectInput);

        String currentProjectValue =
                page.locator(projectInput).inputValue().trim();

        // ================= FIRST RUN =================
        if (currentProjectValue.isEmpty()) {

            isFirstRun = true;

            selectProjectFromJson();
            selectActivityFromJson();
            fillHoursFromJson("timesheet.hours");
        }

        // ================= SECOND RUN =================
        else {

            isFirstRun = false;

            fillHoursFromJson("timesheet.updatedHours");
        }
    }

    // ================= GET CURRENT RUN STATUS =================
    public boolean getIsFirstRun() {
        return isFirstRun;
    }

    public void setIsFirstRun(boolean firstRun) {
        isFirstRun = firstRun;
    }

    // ======================================================
    // PROJECT
    // ======================================================

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
                ).filter(new com.microsoft.playwright.Locator.FilterOptions()
                        .setHasText(fullProject))
                .first()
                .click();
    }

    // ======================================================
    // ACTIVITY
    // ======================================================

    private void selectActivityFromJson() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("timesheetPage.activityDropdown")
        );

        String activity =
                TestDataReader.get("timesheet.activity");

        String activityOption =
                LocatorReader.get("timesheetPage.activityOption")
                        .replace("{ACTIVITY}", activity);

        WaitUtil.waitForVisible(page, activityOption);

        page.locator(activityOption).first().click();
    }

    // ======================================================
    // HOURS
    // ======================================================

    private void fillHoursFromJson(String jsonPath) {

        Map<String, String> hours =
                TestDataReader.getMap(jsonPath);

        page.locator(LocatorReader.get("timesheetPage.mondayInput"))
                .fill(hours.get("mon"));

        page.locator(LocatorReader.get("timesheetPage.tuesdayInput"))
                .fill(hours.get("tue"));

        page.locator(LocatorReader.get("timesheetPage.wednesdayInput"))
                .fill(hours.get("wed"));

        page.locator(LocatorReader.get("timesheetPage.thursdayInput"))
                .fill(hours.get("thu"));

        page.locator(LocatorReader.get("timesheetPage.fridayInput"))
                .fill(hours.get("fri"));

        page.locator(LocatorReader.get("timesheetPage.saturdayInput"))
                .fill(hours.get("sat"));

        page.locator(LocatorReader.get("timesheetPage.sundayInput"))
                .fill(hours.get("sun"));
    }

    // ======================================================
    // SAVE
    // ======================================================

    public void saveTimesheet() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("timesheetPage.saveButton")
        );

        WaitUtil.waitForToast(
                page,
                LocatorReader.get("timesheetPage.successToast")
        );

        // Wait for page to stabilize after save
        WaitUtil.waitForPageLoad(page);

        // Additional wait to ensure total is calculated
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isSuccessToastDisplayed() {

        return page
                .locator(LocatorReader.get("timesheetPage.successToast"))
                .isVisible();
    }

    // ======================================================
    // TOTAL VALIDATION (Handles 30:00 → 30)
    // ======================================================

    public int getDisplayedTotalHours() {

        String totalLocator =
                LocatorReader.get("timesheetPage.totalCell");

        WaitUtil.waitForVisible(page, totalLocator);

        String text =
                page.locator(totalLocator)
                        .innerText()
                        .trim();   // Example: 30:00

        // Handle HH:MM format
        if (text.contains(":")) {
            String[] parts = text.split(":");
            if (parts.length >= 1) {
                try {
                    return Integer.parseInt(parts[0].trim());
                } catch (NumberFormatException e) {
                    throw new RuntimeException(
                            "Failed to parse total hours from format: " + text, e
                    );
                }
            }
        }

        // Handle plain number format
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new RuntimeException(
                    "Failed to parse total hours: " + text, e
            );
        }
    }

    // ======================================================
    // DYNAMIC EXPECTED TOTAL
    // ======================================================

    public int getExpectedTotalBasedOnRun() {

        if (isFirstRun) {
            return Integer.parseInt(
                    TestDataReader.get("timesheet.expectedTotal")
            );
        } else {
            return Integer.parseInt(
                    TestDataReader.get("timesheet.updatedTotal")
            );
        }
    }
}
