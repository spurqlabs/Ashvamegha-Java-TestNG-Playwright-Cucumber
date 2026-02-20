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

    // ADD OR UPDATE ENTRY (SMART + FINAL LOGIC)

    public void addNewEntryFromJson() {
        try {
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

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to add or update timesheet entry from JSON", e);
        }
    }

    // ================= GET CURRENT RUN STATUS =================

    public boolean getIsFirstRun() {
        return isFirstRun;
    }

    public void setIsFirstRun(boolean firstRun) {
        isFirstRun = firstRun;
    }

    // PROJECT

    private void selectProjectFromJson() {
        try {
            String fullProject =
                    TestDataReader.get("timesheet.project");

            String partialProject =
                    fullProject.split(" ")[0];

            WaitUtil.fillWhenReady(
                    page,
                    LocatorReader.get("timesheetPage.projectInput"),
                    partialProject
            );

            WaitUtil.waitForVisible(
                    page,
                    LocatorReader.get("timesheetPage.projectSuggestion")
            );

            page.locator(
                            LocatorReader.get("timesheetPage.projectSuggestion")
                    ).filter(new com.microsoft.playwright.Locator.FilterOptions()
                            .setHasText(fullProject))
                    .first()
                    .click();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to select project from JSON", e);
        }
    }

    // ACTIVITY

    private void selectActivityFromJson() {
        try {
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

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to select activity from JSON", e);
        }
    }


    // HOURS

    private void fillHoursFromJson(String jsonPath) {
        try {
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

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to fill timesheet hours from JSON path: " + jsonPath, e);
        }
    }

    // SAVE

    public void saveTimesheet() {
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("timesheetPage.saveButton")
            );

            WaitUtil.waitForToast(
                    page,
                    LocatorReader.get("timesheetPage.successToast")
            );

            WaitUtil.waitForPageLoad(page);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to save timesheet", e);
        }
    }

    public boolean isSuccessToastDisplayed() {
        try {
            return page
                    .locator(LocatorReader.get("timesheetPage.successToast"))
                    .isVisible();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to verify success toast display", e);
        }
    }

    // TOTAL VALIDATION (Handles 30:00 → 30)


    public int getDisplayedTotalHours() {
        try {
            String totalLocator =
                    LocatorReader.get("timesheetPage.totalCell");

            WaitUtil.waitForVisible(page, totalLocator);

            String text =
                    page.locator(totalLocator)
                            .innerText()
                            .trim();

            // Handle HH:MM format
            if (text.contains(":")) {
                String[] parts = text.split(":");
                if (parts.length >= 1) {
                    return Integer.parseInt(parts[0].trim());
                }
            }

            // Handle plain number format
            return Integer.parseInt(text);

        } catch (NumberFormatException e) {
            throw new RuntimeException(
                    "Failed to parse total hours value", e);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to retrieve displayed total hours", e);
        }
    }


    public int getExpectedTotalBasedOnRun() {
        try {
            if (isFirstRun) {
                return Integer.parseInt(
                        TestDataReader.get("timesheet.expectedTotal")
                );
            } else {
                return Integer.parseInt(
                        TestDataReader.get("timesheet.updatedTotal")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to retrieve expected total from JSON", e);
        }
    }
}
