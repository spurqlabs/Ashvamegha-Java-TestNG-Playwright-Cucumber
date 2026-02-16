package Pages.Leave;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.WaitUtil;

public class EntitlementPage {

    private final Page page;

    // ================= CONSTRUCTOR =================
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
            // 🔥 IMPORTANT: wait for page load after tab navigation
            WaitUtil.waitForPageLoad(page);

            // Wait for tab content to be fully visible and stable (prevent collapse)
            try {
                Thread.sleep(2000);  // Extra wait for tab content to stabilize
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Entitlements tab", e);
        }
    }

    /**
     * Ensure the Entitlements tab is still expanded
     * If it collapsed, re-click it to expand
     */
    private void ensureTabStaysOpen() {
        try {
            String addEntitlementLocator = LocatorReader.get("leavePage.addEntitlementOption");

            // Check if the Add Entitlements button is visible (tab is open)
            if (!page.locator(addEntitlementLocator).isVisible()) {
                // Tab collapsed, re-click to expand
                WaitUtil.clickWhenReady(
                        page,
                        LocatorReader.get("leavePage.entitlementsTab")
                );
                WaitUtil.waitForPageLoad(page);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception ignored) {
            // Element not found yet, will be found in clickAddEntitlementsOption
        }
    }

    public void clickAddEntitlementsOption() {
        try {
            // 🔥 IMPORTANT: ensure page is fully loaded and tab content is rendered
            WaitUtil.waitForPageLoad(page);

            // Wait extra time for tab content to fully render and stabilize
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Ensure the tab is still open (didn't collapse)
            ensureTabStaysOpen();

            String addEntitlementLocator = LocatorReader.get("leavePage.addEntitlementOption");

            // Try to find and click the Add Entitlements button with retry logic
            try {
                // Use retry logic for flaky element
                WaitUtil.waitForElementWithRetry(page, addEntitlementLocator);

                // Wait a bit to ensure tab content is stable
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // Scroll element into view if needed
                page.locator(addEntitlementLocator).scrollIntoViewIfNeeded();

                // Wait to ensure tab doesn't collapse during scroll
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // Click the button
                page.locator(addEntitlementLocator).click();
            } catch (Exception e) {
                // Re-ensure tab is still open before fallback
                ensureTabStaysOpen();

                // Fallback: Try with a more generic locator
                String fallbackLocator = "a:has-text('Add Entitlements')";
                try {
                    WaitUtil.waitForElementWithRetry(page, fallbackLocator);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    page.locator(fallbackLocator).scrollIntoViewIfNeeded();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    page.locator(fallbackLocator).click();
                } catch (Exception fallbackError) {
                    // Last resort: throw original error
                    throw e;
                }
            }

            // 🔥 IMPORTANT: wait for page load after navigation
            WaitUtil.waitForPageLoad(page);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Add Entitlements option", e);
        }
    }

    public boolean isAddEntitlementPageDisplayed() {
        try {
            String header = LocatorReader.get("leavePage.addLeaveEntitlementHeader");
            WaitUtil.waitForVisible(page, header);
            return page.locator(header).isVisible();
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify Add Entitlement page display", e);
        }
    }

    // ================= GET LOGGED-IN USER (FROM HEADER) =================

    public String getLoggedInUsernameFromHeader() {
        try {
            String userDropdown = LocatorReader.get("dashboard.userDropdown");
            WaitUtil.waitForVisible(page, userDropdown);
            String fullText = page.locator(userDropdown).textContent().trim();

            // Sometimes header shows: "manda32 user", "John Smith", "Admin User"
            // We remove the word "user" if present (case-insensitive)
            if (fullText.toLowerCase().endsWith(" user")) {
                fullText = fullText.substring(0, fullText.length() - 5).trim();
            }

            return fullText;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get logged-in username from header", e);
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

            // Clear & enter employee name
            input.fill("");
            input.fill(employeeName);

            // Wait for suggestion dropdown to appear after input
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Wait for suggestion to appear with retry logic
            try {
                WaitUtil.waitForElementWithRetry(page, suggestion);
            } catch (Exception e) {
                // If suggestion doesn't appear, try clicking the input field to trigger dropdown
                input.click();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }

            // Wait for the filtered suggestion to appear
            try {
                page.locator(suggestion)
                        .filter(new Locator.FilterOptions().setHasText(employeeName))
                        .first()
                        .waitFor(new Locator.WaitForOptions().setTimeout(30000));
            } catch (Exception e) {
                // Fallback: Try with partial match by getting first suggestion
                page.locator(suggestion).first().waitFor(new Locator.WaitForOptions().setTimeout(30000));
            }

            // Click the suggestion that matches the employee name
            try {
                page.locator(suggestion)
                        .filter(new Locator.FilterOptions().setHasText(employeeName))
                        .first()
                        .click();
            } catch (Exception e) {
                // Fallback: Click first suggestion if text match fails
                page.locator(suggestion).first().click();
            }

            // Wait for the selection to be processed and form to fully load
            WaitUtil.waitForPageLoad(page);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to select logged-in employee from header", e);
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

            // Wait for page updates and entitlement field to be enabled
            WaitUtil.waitForPageLoad(page);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select leave type: " + leaveType, e);
        }
    }


    public void enterEntitlementDays(String days) {
        try {
            String locator = LocatorReader.get("leavePage.entitlementInput");

            // Wait until the entitlement input is visible and enabled
            WaitUtil.waitForVisible(page, locator);

            Locator input = page.locator(locator);

            // Click the field to activate it
            input.click();

            // Clear the input if necessary and then fill in the value
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
            throw new RuntimeException("Failed to verify update popup display", e);
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
            throw new RuntimeException("Failed to verify entitlement record display", e);
        }
    }
}
