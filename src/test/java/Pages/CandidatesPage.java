package Pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import Utils.LocatorReader;
import Utils.WaitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CandidatesPage {

    private Page page;
    private static final Logger log = LoggerFactory.getLogger(CandidatesPage.class);

    public CandidatesPage(Page page) {
        this.page = page;
    }

    public void enterCandidateName(String firstName, String fullName) {
        try {
            log.info("Typing candidate first name: {}", firstName);

            // Wait for page to load
            page.waitForLoadState(LoadState.NETWORKIDLE);

            // 1️⃣ Type first name in search field
            page.fill(
                    LocatorReader.get("recruitmentPage.searchName"),
                    firstName
            );
            log.info("Typed first name: {}", firstName);

            // 2️⃣ Wait for suggestion dropdown to appear
            page.waitForTimeout(500);

            // 3️⃣ Build dynamic locator for suggestion
            String suggestionLocator =
                    LocatorReader.get("recruitmentPage.candidateSuggestion")
                            .replace("{NAME}", fullName);

            log.info("Waiting for suggestion: {}", fullName);
            WaitUtil.waitForVisible(page, suggestionLocator);

            // 4️⃣ Click the suggestion
            page.click(suggestionLocator);
            log.info("✓ Selected candidate from suggestion: {}", fullName);

        } catch (Exception e) {
            log.error("Failed to select candidate suggestion: {}", e.getMessage());
            throw e;
        }
    }


    public void clickSearch() {
        try {
            log.info("=== SEARCH: Clicking Search button ===");

            // Wait for page to be fully loaded before clicking
            page.waitForLoadState(LoadState.NETWORKIDLE);
            log.info("Page ready - clicking search");

            page.click(LocatorReader.get("recruitmentPage.searchButton"));

            // Wait for search to process
            log.info("Search clicked - waiting for results to load");
            page.waitForTimeout(500);

            // Wait for table results to appear
            log.info("Waiting for table rows to appear");
            page.waitForLoadState(LoadState.NETWORKIDLE);
            log.info("Page idle - results should be loaded");

            // Verify table rows are visible
            WaitUtil.waitForVisible(
                    page,
                    LocatorReader.get("recruitmentPage.tableRow")
            );

            // Additional wait to ensure all data is rendered
            page.waitForTimeout(1000);

            log.info("✓ Search results fully loaded");

        } catch (Exception e) {
            log.error("Error clicking search: {}", e.getMessage());
            throw e;
        }
    }

    // ✅ REQUIRED by step definition
    public boolean isCandidateDisplayed(String fullName) {
        try {
            log.info("=== VERIFY: Searching for candidate: {} ===", fullName);

            // Wait for page to settle
            page.waitForLoadState(LoadState.NETWORKIDLE);
            log.info("Page idle - starting search verification");

            // Wait for table to be fully rendered
            WaitUtil.waitForVisible(page, LocatorReader.get("recruitmentPage.tableRow"));

            // Additional wait for DOM to settle
            page.waitForTimeout(1000);

            // Get all rows
            int rowCount = page.locator(
                    LocatorReader.get("recruitmentPage.tableRow")
            ).count();

            log.info("Total table rows found: {}", rowCount);

            if (rowCount == 0) {
                log.error("No table rows found - search may have returned no results");
                return false;
            }

            // Loop through each row
            for (int i = 0; i < rowCount; i++) {
                String rowText = page.locator(
                        LocatorReader.get("recruitmentPage.tableRow")
                ).nth(i).textContent();

                log.info("Row {}: {}", i,
                    rowText != null ? rowText.substring(0, Math.min(100, rowText.length())) + "..." : "NULL");

                if (rowText != null && rowText.contains(fullName)) {
                    log.info("✓✓✓ CANDIDATE FOUND in row {}: {}", i, fullName);
                    return true;
                }
            }

            log.error("✗✗✗ Candidate NOT found in any row: {}", fullName);
            return false;

        } catch (Exception e) {
            log.error("Error verifying candidate in table: {}", e.getMessage(), e);
            return false;
        }
    }
}
