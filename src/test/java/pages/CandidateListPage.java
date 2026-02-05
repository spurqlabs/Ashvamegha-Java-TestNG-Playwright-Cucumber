package pages;

import com.microsoft.playwright.Page;
import utils.CandidateDataReader;
import utils.LocatorReader;
import utils.WaitUtil;

public class CandidateListPage {

    private Page page;

    public CandidateListPage(Page page) {
        this.page = page;
    }

    // Search candidate by name or email
    public void searchCandidate() {

        String searchInput =
                LocatorReader.get("candidateListPage", "candidateSearchInput");

        String searchButton =
                LocatorReader.get("candidateListPage", "searchButton");

        // Use email (most reliable)
        page.fill(searchInput, CandidateDataReader.getEmail());

        WaitUtil.waitForClickable(page, searchButton);
        page.click(searchButton);
    }

    // Validate search results
    public boolean isSearchResultDisplayed() {

        String resultRow =
                "//div[@class='oxd-table-body']//div[contains(text(),'" +
                        CandidateDataReader.getEmail() + "')]";

        WaitUtil.waitForVisible(page, resultRow);
        return page.isVisible(resultRow);
    }
}
