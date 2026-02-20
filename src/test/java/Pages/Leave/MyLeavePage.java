package Pages.Leave;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import Utils.LocatorReader;
import Utils.WaitUtil;

public class MyLeavePage {

    private final Page page;

    // ================= CONSTRUCTOR =================

    public MyLeavePage(Page page) {
        this.page = page;
    }

    // ================= NAVIGATION =================

    public void navigateToMyLeavePage() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("leavePage.myLeaveTab")
        );

        page.waitForLoadState(LoadState.NETWORKIDLE);

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("myLeavePage.myLeaveHeader")
        );
    }

    public boolean isMyLeavePageDisplayed() {

        String header =
                LocatorReader.get("myLeavePage.myLeaveHeader");

        WaitUtil.waitForVisible(page, header);

        return page.locator(header).isVisible();
    }

    // ================= FILTER =================

    public void filterByDateRange(String fromDate, String toDate) {

        String fromDateLocator =
                LocatorReader.get("myLeavePage.fromDateInput");

        String toDateLocator =
                LocatorReader.get("myLeavePage.toDateInput");

        WaitUtil.fillWhenReady(page, fromDateLocator, fromDate);
        WaitUtil.fillWhenReady(page, toDateLocator, toDate);
    }

    public void clickSearch() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("myLeavePage.searchButton")
        );

        page.waitForLoadState(LoadState.NETWORKIDLE);

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("myLeavePage.leaveTableRows")
        );
    }

    public boolean isLeaveRecordDisplayed() {

        String rows =
                LocatorReader.get("myLeavePage.leaveTableRows");

        WaitUtil.waitForVisible(page, rows);

        return page.locator(rows).count() > 0;
    }
}
