package Pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.ElementHandle;
import Utils.LocatorReader;
import Utils.WaitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CandidatesPage {

    private final Page page;
    private static final Logger log =
            LoggerFactory.getLogger(CandidatesPage.class);

    public CandidatesPage(Page page) {
        this.page = page;
    }

    // ================= SEARCH =================
    public void searchCandidate(String candidateName) {

        if (candidateName == null || candidateName.isBlank()) {
            throw new AssertionError(
                    "candidateName is NULL or empty. Check ScenarioContext setup."
            );
        }

        log.info("Searching candidate: {}", candidateName);

        // 🔴 FORCE NAVIGATION TO CANDIDATES PAGE
        if (!page.url().contains("/recruitment/viewCandidates")) {
            log.info("Navigating to Candidates page before search");
            page.navigate(
                    page.url().split("/web/")[0]
                            + "/web/index.php/recruitment/viewCandidates"
            );

            WaitUtil.waitForPageLoad(page);


            WaitUtil.waitForVisible(
                    page,
                    LocatorReader.get("recruitmentPage.candidatesHeader")
            );
        }

        // Now search safely
        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("searchPage.candidateNameInput")
        );

        page.fill(
                LocatorReader.get("searchPage.candidateNameInput"),
                candidateName
        );

        // Auto-suggestion (optional)
        try {
            WaitUtil.waitForVisibleLong(
                    page,
                    LocatorReader.get("searchPage.candidateSuggestions")
            );

            for (ElementHandle option :
                    page.querySelectorAll(
                            LocatorReader.get("searchPage.candidateSuggestions")
                    )) {

                if (option.innerText().trim()
                        .equalsIgnoreCase(candidateName)) {
                    option.click();
                    break;
                }
            }
        } catch (Exception e) {
            log.info("Suggestion dropdown not visible, continuing");
        }

        page.click(
                LocatorReader.get("searchPage.searchButton")
        );

        WaitUtil.waitForResultsToLoad(page);
    }
    // ================= RESULT =================
    public String getDisplayedCandidateName() {

        WaitUtil.waitForResultsToLoad(page);

        WaitUtil.waitForVisibleLong(
                page,
                LocatorReader.get("searchPage.resultCandidateName")
        );

        String name = page.textContent(
                LocatorReader.get("searchPage.resultCandidateName")
        );

        return name != null ? name.trim() : "";
    }

    // ================= VIEW =================
    public void clickViewButtonForCandidate(String candidateName) {

        log.info("Clicking View button for candidate: {}", candidateName);

        WaitUtil.waitForResultsToLoad(page);

        String rowLocator =
                LocatorReader.get("candidatesPage.rowByCandidateName")
                        .replace("{CANDIDATE_NAME}", candidateName);

        Locator candidateRow = page.locator(rowLocator);

        if (candidateRow.count() == 0) {
            throw new AssertionError(
                    "Candidate '" + candidateName + "' not found in table"
            );
        }

        candidateRow.first()
                .locator(
                        LocatorReader.get("candidatesPage.viewButtonInRow")
                )
                .first()
                .click();
    }

    // ================= UTIL =================
    public boolean isCandidateInTable(String candidateName) {

        WaitUtil.waitForResultsToLoad(page);

        String rowLocator =
                LocatorReader.get("candidatesPage.rowByCandidateName")
                        .replace("{CANDIDATE_NAME}", candidateName);

        return page.locator(rowLocator).count() > 0;
    }
}
