package Pages.Time;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.WaitUtil;

public class MyTimesheetsPage {

    private final Page page;

    public MyTimesheetsPage(Page page) {
        this.page = page;
    }

    // ================= NAVIGATE TO TIME MODULE =================

    public void navigateToTimeModule() {
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("timePage.timeMenu")
            );

            WaitUtil.waitForPageLoad(page);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to navigate to Time module", e);
        }
    }

    // ================= OPEN MY TIMESHEETS =================

    public void openMyTimesheets() {
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("timePage.timesheetsDropdown")
            );

            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("timePage.myTimesheetsOption")
            );

            WaitUtil.waitForPageLoad(page);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to open My Timesheets page", e);
        }
    }

    // ================= CLICK EDIT =================

    public void clickEdit() {
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("myTimesheetPage.editButton")
            );

            WaitUtil.waitForPageLoad(page);

            WaitUtil.waitForVisible(
                    page,
                    LocatorReader.get("timesheetPage.projectInput")
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to click Edit button on My Timesheets page", e);
        }
    }

    // ================= VERIFY PAGE DISPLAY =================

    public boolean isMyTimesheetPageDisplayed() {
        try {
            String header =
                    LocatorReader.get("myTimesheetPage.pageHeader");

            WaitUtil.waitForVisible(page, header);

            return page.locator(header).isVisible();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to verify My Timesheet page display", e);
        }
    }
}
