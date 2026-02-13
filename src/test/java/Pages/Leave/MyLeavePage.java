package Pages.Leave;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.TestDataReader;
import Utils.WaitUtil;

public class MyLeavePage {

    private final Page page;

    public MyLeavePage(Page page) {
        this.page = page;
    }

    // ================= NAVIGATION =================

    public void navigateToMyLeavePage() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("leavePage.myLeaveTab")
        );
    }

    public boolean isMyLeavePageDisplayed() {

        String header =
                LocatorReader.get("myLeavePage.myLeaveHeader");

        WaitUtil.waitForVisible(page, header);
        return page.locator(header).isVisible();
    }

    // ================= FILTER =================

    public void filterByDateRangeFromJson() {

        String fromDate =
                TestDataReader.get("leave.verify.fromDate");

        String toDate =
                TestDataReader.get("leave.verify.toDate");

        WaitUtil.fillWhenReady(
                page,
                LocatorReader.get("myLeavePage.fromDateInput"),
                fromDate
        );

        WaitUtil.fillWhenReady(
                page,
                LocatorReader.get("myLeavePage.toDateInput"),
                toDate
        );
    }

    public void clickSearch() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("myLeavePage.searchButton")
        );
    }

    public boolean isLeaveRecordDisplayed() {

        String rows =
                LocatorReader.get("myLeavePage.leaveTableRows");

        WaitUtil.waitForVisible(page, rows);

        return page.locator(rows).count() > 0;
    }
}
