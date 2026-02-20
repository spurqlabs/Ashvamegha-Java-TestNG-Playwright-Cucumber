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
        String header = LocatorReader.get("applyLeavePage.applyHeader");
        WaitUtil.waitForVisible(page, header);
        return page.locator(header).isVisible();
    }

    // ================= FORM =================

    public void selectLeaveType(String leaveType) {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("applyLeavePage.leaveTypeDropdown")
        );

        String optionLocator = LocatorReader
                .get("applyLeavePage.leaveTypeOption")
                .replace("{LEAVE_TYPE}", leaveType);

        WaitUtil.waitForVisible(page, optionLocator);
        page.click(optionLocator);
    }

    public void selectLeaveTypeFromJson() {
        String leaveType = TestDataReader.get("leave.apply.leaveType");
        selectLeaveType(leaveType);
    }

    public void selectDates(String fromDate, String toDate) {

        String fromDateLocator =
                LocatorReader.get("applyLeavePage.fromDateInput");

        String toDateLocator =
                LocatorReader.get("applyLeavePage.toDateInput");

        // Fill From Date
        page.fill(fromDateLocator, fromDate);

        // Clear and Fill To Date (Playwright auto waits)
        page.fill(toDateLocator, "");
        page.fill(toDateLocator, toDate);
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

        // Wait for form submission to process on server
        page.waitForTimeout(3000);
    }

    public boolean isLeaveAppliedSuccessfully() {

        String toast =
                LocatorReader.get("applyLeavePage.successToast");

        // Use extra-long timeout for success toast to appear after server processing (can take up to 45+ seconds)
        page.waitForSelector(
                toast,
                new com.microsoft.playwright.Page.WaitForSelectorOptions()
                        .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                        .setTimeout(90000)
        );

        return page.locator(toast).isVisible();
    }
}
