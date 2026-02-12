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

    // ===== Navigation =====
    public void navigateToApplyLeavePage() {

        String leaveMenu =
                LocatorReader.get("leaveMenuPage.leaveMenu");

        String leaveListTab =
                LocatorReader.get("leaveMenuPage.leaveListTab");

        String applyLeaveTab =
                LocatorReader.get("leaveMenuPage.applyLeaveTab");

        // Step 1: Click Leave from sidebar
        WaitUtil.waitForVisible(page, leaveMenu);
        page.click(leaveMenu);

        // Step 2: Wait until Leave List tab appears (page loaded)
        WaitUtil.waitForVisible(page, leaveListTab);

        // Step 3: Click Apply tab
        WaitUtil.waitForVisible(page, applyLeaveTab);
        page.click(applyLeaveTab);
    }

    // ===== Actions =====
    public void fillLeaveApplicationFromJson() {

        String leaveType =
                TestDataReader.get("leave.validLeaveApplication.leaveType");
        String fromDate =
                TestDataReader.get("leave.validLeaveApplication.fromDate");
        String toDate =
                TestDataReader.get("leave.validLeaveApplication.toDate");
        String comment =
                TestDataReader.get("leave.validLeaveApplication.comment");

        String leaveTypeDropdown =
                LocatorReader.get("applyLeavePage.leaveTypeDropdown");

        String leaveTypeOption =
                LocatorReader.get("applyLeavePage.leaveTypeOption")
                        .replace("{LEAVE_TYPE}", leaveType);

        // Select Leave Type
        WaitUtil.waitForVisible(page, leaveTypeDropdown);
        page.click(leaveTypeDropdown);

        WaitUtil.waitForVisible(page, leaveTypeOption);
        page.click(leaveTypeOption);

        // Fill dates and comment
        page.fill(
                LocatorReader.get("applyLeavePage.fromDate"),
                fromDate
        );
        page.fill(
                LocatorReader.get("applyLeavePage.toDate"),
                toDate
        );
        page.fill(
                LocatorReader.get("applyLeavePage.comment"),
                comment
        );
    }

    public void submitLeaveApplication() {

        String applyBtn =
                LocatorReader.get("applyLeavePage.applyButton");

        WaitUtil.waitForVisible(page, applyBtn);
        page.click(applyBtn);
    }

    public boolean isSuccessMessageDisplayed() {

        String successToast =
                LocatorReader.get("applyLeavePage.successToast");

        WaitUtil.waitForVisible(page, successToast);
        return page.isVisible(successToast);
    }
}
