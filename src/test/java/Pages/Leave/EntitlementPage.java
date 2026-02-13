package Pages.Leave;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.WaitUtil;


public class EntitlementPage {

    private final Page page;

    public EntitlementPage(Page page) {
        this.page = page;
    }

    // ================= NAVIGATION =================

    public void clickEntitlementsTab() {
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("leavePage.entitlementsTab")
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Entitlements Tab", e);
        }
    }

    public void clickAddEntitlementsOption() {
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("leavePage.addEntitlementOption")
            );
            WaitUtil.waitForPageLoad(page);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Add Entitlement option", e);
        }
    }

    public boolean isAddEntitlementPageDisplayed() {
        try {
            String header = LocatorReader.get("leavePage.addLeaveEntitlementHeader");
            WaitUtil.waitForVisible(page, header);
            return page.locator(header).isVisible();
        } catch (Exception e) {
            throw new RuntimeException("Add Entitlement page not displayed", e);
        }
    }

    // ================= GET LOGGED-IN USER =================

    public String getLoggedInUsernameFromHeader() {
        try {
            String userDropdown = LocatorReader.get("dashboard.userDropdown");
            WaitUtil.waitForVisible(page, userDropdown);
            String fullText = page.locator(userDropdown).textContent().trim();

            if (fullText.toLowerCase().endsWith(" user")) {
                fullText = fullText.substring(0, fullText.length() - 5).trim();
            }

            return fullText;

        } catch (Exception e) {
            throw new RuntimeException("Unable to fetch logged-in username from header", e);
        }
    }

    // ================= FORM =================

    public void selectLoggedInEmployeeFromHeader() {
        try {
            String employeeName = getLoggedInUsernameFromHeader();
            String employeeInput = LocatorReader.get("leavePage.employeeNameInput");
            String suggestion = LocatorReader.get("leavePage.employeeSuggestion");

            WaitUtil.waitForVisible(page, employeeInput);
            Locator input = page.locator(employeeInput);

            input.fill("");
            input.fill(employeeName);

            page.locator(suggestion)
                    .filter(new Locator.FilterOptions().setHasText(employeeName))
                    .first()
                    .waitFor();

            page.locator(suggestion)
                    .filter(new Locator.FilterOptions().setHasText(employeeName))
                    .first()
                    .click();

            WaitUtil.waitForPageLoad(page);

        } catch (Exception e) {
            throw new RuntimeException("Failed to select logged-in employee: " + e.getMessage(), e);
        }
    }

    public void selectLeaveType(String leaveType) {
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("leavePage.leaveTypeDropdown")
            );

            page.click(
                    LocatorReader.get("leavePage.leaveTypeOption")
                            .replace("{LEAVE_TYPE}", leaveType)
            );

            WaitUtil.waitForPageLoad(page);

        } catch (Exception e) {
            throw new RuntimeException("Failed to select Leave Type: " + leaveType, e);
        }
    }

    public void enterEntitlementDays(String days) {
        try {
            String locator = LocatorReader.get("leavePage.entitlementInput");
            WaitUtil.waitForVisible(page, locator);

            Locator input = page.locator(locator);
            input.click();
            input.fill("");
            input.fill(days);

        } catch (Exception e) {
            throw new RuntimeException("Failed to enter entitlement days: " + days, e);
        }
    }

    public void clickSaveEntitlement() {
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("leavePage.saveEntitlementButton")
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Save Entitlement button", e);
        }
    }

    // ================= POPUP =================

    public boolean isUpdatePopupDisplayed() {
        try {
            String popup = LocatorReader.get("leavePage.updatePopup");
            WaitUtil.waitForVisible(page, popup);
            return page.locator(popup).isVisible();
        } catch (Exception e) {
            throw new RuntimeException("Update popup not displayed", e);
        }
    }

    public void confirmUpdate() {
        try {
            WaitUtil.clickWhenReady(
                    page,
                    LocatorReader.get("leavePage.confirmButton")
            );
            WaitUtil.waitForPageLoad(page);
        } catch (Exception e) {
            throw new RuntimeException("Failed to confirm update", e);
        }
    }

    // ================= RECORD VALIDATION =================

    public boolean isEntitlementRecordDisplayed() {
        try {
            String rows = LocatorReader.get("leavePage.recordTableRows");
            WaitUtil.waitForVisible(page, rows);
            return page.locator(rows).count() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Entitlement record not displayed", e);
        }
    }
}
