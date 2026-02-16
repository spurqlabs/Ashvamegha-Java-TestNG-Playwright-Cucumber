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

    public void navigateToTimeModule() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("timePage.timeMenu")
        );

        WaitUtil.waitForPageLoad(page);
    }

    public void openMyTimesheets() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("timePage.timesheetsDropdown")
        );

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("timePage.myTimesheetsOption")
        );

        WaitUtil.waitForPageLoad(page);
    }

    public void clickEdit() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("myTimesheetPage.editButton")
        );

        WaitUtil.waitForPageLoad(page);

        // Wait for the table to load and be interactive
        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("timesheetPage.projectInput")
        );

        // Additional wait to ensure form is fully loaded
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isMyTimesheetPageDisplayed() {

        String header =
                LocatorReader.get("myTimesheetPage.pageHeader");

        WaitUtil.waitForVisible(page, header);

        return page.locator(header).isVisible();
    }
}



