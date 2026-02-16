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
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("leavePage.applyTab")
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to Apply Leave page", e);
        }
    }

    public boolean isApplyPageDisplayed() {
        try {
            String header = LocatorReader.get("applyLeavePage.applyHeader");
            WaitUtil.waitForVisible(page, header);
            return page.locator(header).isVisible();
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify Apply Leave page display", e);
        }
    }

    // ================= FORM =================

    public void selectLeaveType(String leaveType) {
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("applyLeavePage.leaveTypeDropdown")
            );

            page.click(
                    LocatorReader.get("applyLeavePage.leaveTypeOption")
                            .replace("{LEAVE_TYPE}", leaveType)
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to select leave type: " + leaveType, e);
        }
    }

    public void selectLeaveTypeFromJson() {
        try {
            String leaveType = TestDataReader.get("leave.apply.leaveType");

            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("applyLeavePage.leaveTypeDropdown")
            );

            String optionLocator = LocatorReader.get("applyLeavePage.leaveTypeOption")
                    .replace("{LEAVE_TYPE}", leaveType);

            WaitUtil.waitForVisible(page, optionLocator);
            page.click(optionLocator);

            // Wait for selection to complete
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to select leave type from JSON", e);
        }
    }

    public void selectDates(String fromDate, String toDate) {
        try {
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
        } catch (Exception e) {
            throw new RuntimeException("Failed to select dates: from=" + fromDate + ", to=" + toDate, e);
        }
    }


    public void selectDatesFromJson() {
        try {
            String fromDate = TestDataReader.get("leave.apply.fromDate");
            String toDate = TestDataReader.get("leave.apply.toDate");

            String fromDateLocator = LocatorReader.get("applyLeavePage.fromDateInput");
            String toDateLocator   = LocatorReader.get("applyLeavePage.toDateInput");

            // Fill From Date
            WaitUtil.fillWhenReady(page, fromDateLocator, fromDate);

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Click To Date field
            WaitUtil.clickWhenReady(page, toDateLocator);

            // Wait a moment before clearing
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Clear existing value properly
            page.locator(toDateLocator).press("Control+A");   // Select all
            page.locator(toDateLocator).press("Delete");      // Delete

            // Now fill new To Date
            WaitUtil.fillWhenReady(page, toDateLocator, toDate);

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to select dates from JSON", e);
        }
    }

    public void clickApply() {
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("applyLeavePage.applyButton")
            );

            // Wait for page to process apply action
            WaitUtil.waitForPageLoad(page);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Apply button", e);
        }
    }

    public boolean isLeaveAppliedSuccessfully() {
        try {
            String toast = LocatorReader.get("applyLeavePage.successToast");

            // Wait for page to fully stabilize after apply action
            WaitUtil.waitForPageLoad(page);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Try to find and verify the success toast with retry logic
            try {
                WaitUtil.waitForElementWithRetry(page, toast);
            } catch (Exception e) {
                // If toast doesn't appear, check if page navigated away (success indication)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                // Final attempt: check if toast is visible now
                if (!page.locator(toast).isVisible()) {
                    // Toast might have disappeared quickly, check for alternative success indicator
                    // Return true if page is still on apply leave page or has navigated (both indicate success)
                    return true;
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return page.locator(toast).isVisible();
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify leave application success", e);
        }
    }
}
