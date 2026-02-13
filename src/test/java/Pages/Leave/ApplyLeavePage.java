package Pages.Leave;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.TestDataReader;
import Utils.WaitUtil;

public class ApplyLeavePage {

    private final Page page;

    public ApplyLeavePage(Page page) {
        this.page = page;
    }

    // ================= NAVIGATION =================

    public void navigateToApplyPage() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("leavePage.applyTab")
        );
    }

    public boolean isApplyPageDisplayed() {

        String header =
                LocatorReader.get("applyLeavePage.applyHeader");

        WaitUtil.waitForVisible(page, header);
        return page.locator(header).isVisible();
    }

    // ================= FORM =================

    public void selectLeaveType(String leaveType) {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("applyLeavePage.leaveTypeDropdown")
        );

        page.click(
                LocatorReader.get("applyLeavePage.leaveTypeOption")
                        .replace("{LEAVE_TYPE}", leaveType)
        );
    }

    public void selectLeaveTypeFromJson() {
        String leaveType = TestDataReader.get("leave.apply.leaveType");
        selectLeaveType(leaveType);
    }

    public void selectDates(String fromDate, String toDate) {

        String fromDateLocator = LocatorReader.get("applyLeavePage.fromDateInput");
        String toDateLocator   = LocatorReader.get("applyLeavePage.toDateInput");

        //  Fill From Date
        WaitUtil.fillWhenReady(page, fromDateLocator, fromDate);

        //  Click To Date field

        WaitUtil.clickWhenReady(page, toDateLocator);

        //  Clear existing value properly
        page.locator(toDateLocator).press("Control+A");   // Select all
        page.locator(toDateLocator).press("Delete");      // Delete

        //  Now fill new To Date
        WaitUtil.fillWhenReady(page, toDateLocator, toDate);
    }


    public void selectDatesFromJson() {
        String fromDate = TestDataReader.get("leave.apply.fromDate");
        String toDate = TestDataReader.get("leave.apply.toDate");
        selectDates(fromDate, toDate);
    }

    public void clickApply() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("applyLeavePage.applyButton")
        );
    }

    public boolean isLeaveAppliedSuccessfully() {

        String toast =
                LocatorReader.get("applyLeavePage.successToast");

        WaitUtil.waitForToast(page, toast);
        return page.locator(toast).isVisible();
    }
}
