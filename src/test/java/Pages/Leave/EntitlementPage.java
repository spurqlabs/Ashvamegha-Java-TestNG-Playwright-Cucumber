package Pages.Leave;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.WaitUtil;

public class EntitlementPage {

    private Page page;   // ✅ ADD THIS

    // ================= CONSTRUCTOR =================
    public EntitlementPage(Page page) {
        this.page = page;   // ✅ FIXED
    }

    // ================= NAVIGATION =================

    public void navigateToLeaveModule() {

        // ❌ ensureSidebarExpanded();  (removed)

        page.click(
                LocatorReader.get("leavePage.leaveMenu")
        );

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("leavePage.applyTab")
        );
    }

    public void clickEntitlementsTab() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("leavePage.entitlementsTab")
        );

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("leavePage.addEntitlementOption")
        );
    }

    public void clickAddEntitlementsOption() {

        String locator =
                LocatorReader.get("leavePage.addEntitlementOption");

        WaitUtil.waitForVisible(page, locator);

        page.locator(locator).scrollIntoViewIfNeeded();
        page.locator(locator).click();

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("leavePage.addLeaveEntitlementHeader")
        );
    }

    public boolean isAddEntitlementPageDisplayed() {

        String header =
                LocatorReader.get("leavePage.addLeaveEntitlementHeader");

        WaitUtil.waitForVisible(page, header);

        return page.locator(header).isVisible();
    }

    // ================= HEADER USER =================

    public String getLoggedInUsernameFromHeader() {

        String userDropdown =
                LocatorReader.get("logout.userDropdown");

        WaitUtil.waitForVisible(page, userDropdown);

        String fullText =
                page.locator(userDropdown).textContent().trim();

        if (fullText.toLowerCase().endsWith(" user")) {
            fullText = fullText.substring(0,
                    fullText.length() - 5).trim();
        }

        return fullText;
    }

    // ================= FORM =================

    public void selectLoggedInEmployeeFromHeader() {

        String employeeName =
                getLoggedInUsernameFromHeader();

        String inputLocator =
                LocatorReader.get("leavePage.employeeNameInput");

        String suggestionLocator =
                LocatorReader.get("leavePage.employeeSuggestion");

        WaitUtil.waitForVisible(page, inputLocator);

        Locator input = page.locator(inputLocator);
        input.fill(employeeName);

        page.locator(suggestionLocator)
                .filter(new Locator.FilterOptions()
                        .setHasText(employeeName))
                .first()
                .waitFor();

        page.locator(suggestionLocator)
                .filter(new Locator.FilterOptions()
                        .setHasText(employeeName))
                .first()
                .click();

        WaitUtil.waitForPageLoad(page);
    }

    public void selectLeaveType(String leaveType) {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("leavePage.leaveTypeDropdown")
        );

        page.click(
                LocatorReader.get("leavePage.leaveTypeOption")
                        .replace("{LEAVE_TYPE}", leaveType)
        );

        WaitUtil.waitForPageLoad(page);
    }

    public void enterEntitlementDays(String days) {

        String locator =
                LocatorReader.get("leavePage.entitlementInput");

        WaitUtil.waitForVisible(page, locator);

        Locator input = page.locator(locator);
        input.fill("");
        input.fill(days);
    }

    public void clickSaveEntitlement() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("leavePage.saveEntitlementButton")
        );
    }

    // ================= POPUP =================

    public boolean isUpdatePopupDisplayed() {

        String popup =
                LocatorReader.get("leavePage.updatePopup");

        WaitUtil.waitForVisible(page, popup);

        return page.locator(popup).isVisible();
    }

    public void confirmUpdate() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("leavePage.confirmButton")
        );

        WaitUtil.waitForPageLoad(page);
    }

    // ================= RECORD VALIDATION =================

    public boolean isEntitlementRecordDisplayed() {

        String rows =
                LocatorReader.get("leavePage.recordTableRows");

        WaitUtil.waitForVisible(page, rows);

        return page.locator(rows).count() > 0;
    }
}
