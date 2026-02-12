package Pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import Utils.LocatorReader;
import Utils.WaitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            throw new AssertionError("candidateName is NULL or empty.");
        }

        log.info("Searching candidate: {}", candidateName);

        // Always navigate (SPA safe)
        page.click(LocatorReader.get("recruitmentPage.recruitmentMenu"));

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("recruitmentPage.candidatesHeader")
        );

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("searchPage.candidateNameInput")
        );

        String firstName = candidateName.split(" ")[0];

        page.fill(LocatorReader.get("searchPage.candidateNameInput"), "");
        page.fill(LocatorReader.get("searchPage.candidateNameInput"), firstName);

        WaitUtil.waitForVisibleLong(
                page,
                LocatorReader.get("searchPage.candidateSuggestions")
        );

        page.locator(LocatorReader.get("searchPage.candidateSuggestions"))
                .filter(new Locator.FilterOptions()
                        .setHasText(candidateName))
                .first()
                .click();

        page.click(LocatorReader.get("searchPage.searchButton"));

        WaitUtil.waitForResultsToLoad(page);

        page.mouse().wheel(0, 1000);

        log.info("Candidate search completed successfully");
    }

    // ================= RESULT =================
    public String getDisplayedCandidateName(String candidateName) {

        WaitUtil.waitForResultsToLoad(page);

        String rowLocator =
                LocatorReader.get("candidatesPage.rowByCandidateName")
                        .replace("{CANDIDATE_NAME}", candidateName);

        Locator row = page.locator("xpath=" + rowLocator);

        // First check count
        if (row.count() == 0) {
            throw new AssertionError("Candidate not found in table: " + candidateName);
        }

        row.first().scrollIntoViewIfNeeded();
        row.first().waitFor();

        log.info("Candidate found in table: {}", candidateName);

        return candidateName;
    }

    // ================= VIEW =================
    public void clickViewButtonForCandidate(String candidateName) {

        log.info("Clicking View button for candidate: {}", candidateName);

        WaitUtil.waitForResultsToLoad(page);

        String rowLocator =
                LocatorReader.get("candidatesPage.rowByCandidateName")
                        .replace("{CANDIDATE_NAME}", candidateName);

        Locator candidateRow = page.locator("xpath=" + rowLocator);

        if (candidateRow.count() == 0) {
            throw new AssertionError(
                    "Candidate '" + candidateName + "' not found in results table"
            );
        }

        candidateRow.first().scrollIntoViewIfNeeded();

        candidateRow.first()
                .locator("button.oxd-icon-button")
                .first()
                .click();

        // Proper wait for candidate details page
        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("candidateDetailsPage.candidateNameHeader")
        );

        log.info("Navigated to Candidate Details page successfully");
    }

    // ================= UTIL =================
    public boolean isCandidateInTable(String candidateName) {

        WaitUtil.waitForResultsToLoad(page);

        String rowLocator =
                LocatorReader.get("candidatesPage.rowByCandidateName")
                        .replace("{CANDIDATE_NAME}", candidateName);

        return page.locator("xpath=" + rowLocator).count() > 0;
    }
}
