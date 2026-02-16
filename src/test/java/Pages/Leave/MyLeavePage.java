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
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("leavePage.myLeaveTab")
            );

            WaitUtil.waitForPageLoad(page);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to My Leave page", e);
        }
    }

    public boolean isMyLeavePageDisplayed() {
        try {
            String header = LocatorReader.get("myLeavePage.myLeaveHeader");

            WaitUtil.waitForVisible(page, header);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return page.locator(header).isVisible();
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify My Leave page display", e);
        }
    }

    // ================= FILTER =================

    public void filterByDateRangeFromJson() {
        try {
            String fromDate = TestDataReader.get("leave.verify.fromDate");
            String toDate = TestDataReader.get("leave.verify.toDate");

            String fromDateLocator = LocatorReader.get("myLeavePage.fromDateInput");
            String toDateLocator = LocatorReader.get("myLeavePage.toDateInput");

            WaitUtil.fillWhenReady(page, fromDateLocator, fromDate);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            WaitUtil.fillWhenReady(page, toDateLocator, toDate);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to filter by date range from JSON", e);
        }
    }

    public void clickSearch() {
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("myLeavePage.searchButton")
            );

            WaitUtil.waitForPageLoad(page);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Search button", e);
        }
    }

    public boolean isLeaveRecordDisplayed() {
        try {
            String rows = LocatorReader.get("myLeavePage.leaveTableRows");

            WaitUtil.waitForVisible(page, rows);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return page.locator(rows).count() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify leave record display", e);
        }
    }
}
