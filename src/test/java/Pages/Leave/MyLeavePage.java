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

    // ===== Navigation =====
    public void navigateToMyLeavePage() {

        String leaveMenu =
                LocatorReader.get("leaveMenuPage.leaveMenu");
        String myLeaveTab =
                LocatorReader.get("leaveMenuPage.myLeaveTab");

        WaitUtil.waitForVisible(page, leaveMenu);
        page.click(leaveMenu);

        WaitUtil.waitForVisible(page, myLeaveTab);
        page.click(myLeaveTab);
    }

    // ===== Actions =====
    public void filterLeaveByDateRangeFromJson() {

        String fromDate =
                TestDataReader.get("leave.dateFilter.fromDate");
        String toDate =
                TestDataReader.get("leave.dateFilter.toDate");

        page.fill(
                LocatorReader.get("myLeavePage.fromDateFilter"),
                fromDate
        );
        page.fill(
                LocatorReader.get("myLeavePage.toDateFilter"),
                toDate
        );

        String searchBtn =
                LocatorReader.get("myLeavePage.searchButton");

        WaitUtil.waitForVisible(page, searchBtn);
        page.click(searchBtn);
    }

    public boolean isLeaveRecordDisplayed() {

        String leaveRow =
                LocatorReader.get("myLeavePage.leaveTableRow");

        // Wait first
        WaitUtil.waitForVisible(page, leaveRow);

        // Then verify visibility
        return page.isVisible(leaveRow);
    }
    public String getLeaveStatus() {

        String statusCell =
                LocatorReader.get("myLeavePage.leaveStatusCell");

        WaitUtil.waitForVisible(page, statusCell);
        return page.textContent(statusCell).trim();
    }


}
