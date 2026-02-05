package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.CandidateDataReader;
import utils.LocatorReader;

public class CandidateListPage {

    private final Page page;

    public CandidateListPage(Page page) {
        this.page = page;
    }

    // ================= EXISTING CODE (UNCHANGED) =================

    // Search candidate by FULL NAME
    public void searchCandidateByName() {

        String searchInput =
                LocatorReader.get("candidateListPage", "candidateSearchInput");

        String searchButton =
                LocatorReader.get("candidateListPage", "searchButton");

        String fullName =
                CandidateDataReader.getFirstName() + " " +
                        CandidateDataReader.getLastName();

        page.fill(searchInput, fullName);
        page.click(searchButton);
    }

    // Verify candidate is displayed by FULL NAME
    public boolean isSearchResultDisplayedByName() {

        String fullName =
                CandidateDataReader.getFirstName() + " " +
                        CandidateDataReader.getLastName();

        Locator result =
                page.locator(".oxd-table-body")
                        .locator("text=" + fullName);

        result.waitFor(
                new Locator.WaitForOptions().setTimeout(15000)
        );

        return result.isVisible();
    }

    // ================= NEW CODE FOR VIEW DETAILS =================

    // Select ANY candidate from candidate list (different user)
    public void selectCandidateFromList() {

        String candidateNameCells =
                LocatorReader.get("candidateListPage", "candidateNameCells");

        Locator candidates = page.locator(candidateNameCells);

        candidates.first().waitFor(
                new Locator.WaitForOptions().setTimeout(15000)
        );

        candidates.last().click();
    }

    // Click View (eye icon)
    public void openCandidateDetails() {

        String viewIcon =
                LocatorReader.get("candidateListPage", "viewIcon");

        Locator viewButton = page.locator(viewIcon).first();

        viewButton.waitFor(
                new Locator.WaitForOptions().setTimeout(10000)
        );

        viewButton.click();
    }
}
