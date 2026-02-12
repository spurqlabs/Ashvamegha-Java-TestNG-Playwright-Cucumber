package Pages.Time;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.WaitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTimesheetsPage {

    private final Page page;
    private static final Logger log =
            LoggerFactory.getLogger(MyTimesheetsPage.class);

    public MyTimesheetsPage(Page page) {
        this.page = page;
    }

    // ================= NAVIGATION =================

    public void navigateToTimeModule() {
        log.info("Navigating to Time module");

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("timePage.timeMenu")
        );

        WaitUtil.waitForPageLoad(page);
    }

    public void openMyTimesheets() {
        log.info("Opening Timesheets dropdown");

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("timePage.timesheetsDropdown")
        );

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("timePage.myTimesheetsOption")
        );

        WaitUtil.waitForPageLoad(page);
        log.info("My Timesheets page opened");
    }

    // ================= ACTIONS =================

    public void clickEdit() {
        log.info("Clicking Edit button on My Timesheets page");

        String editBtn =
                LocatorReader.get("myTimesheetPage.editButton");

        WaitUtil.clickWhenReady(page, editBtn);

        // 🔥 IMPORTANT FIX
        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("timesheetPage.mondayInput")
        );

        log.info("Edit form loaded successfully");
    }


    // ================= VALIDATIONS =================

    public boolean isMyTimesheetPageDisplayed() {
        String header =
                LocatorReader.get("myTimesheetPage.pageHeader");

        WaitUtil.waitForVisible(page, header);
        return page.locator(header).isVisible();
    }

    public boolean isTimesheetEntryPresent() {

        String rows = LocatorReader.get("myTimesheetPage.tableRows");

        page.waitForLoadState();

        int rowCount = page.locator(rows).count();

        return rowCount > 0;
    }


    public boolean isUpdatedEntryDisplayed() {
        return isTimesheetEntryPresent();
    }

    public int getDisplayedTotalHours() {
        String totalCell =
                LocatorReader.get("timesheetPage.totalCell");

        WaitUtil.waitForVisible(page, totalCell);

        String text =
                page.locator(totalCell).textContent().trim();

        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }
}
