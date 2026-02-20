package Pages;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.TestDataReader;
import Utils.WaitUtil;

public class CandidateListPage {

    private final Page page;

    public CandidateListPage(Page page) {
        this.page = page;
    }

    // ================= SEARCH =================

    public void searchCandidate() {

        String nameInput = LocatorReader.get("searchPage.candidateNameInput");
        String suggestions = LocatorReader.get("searchPage.candidateSuggestions");
        String searchBtn = LocatorReader.get("searchPage.searchButton");

        String candidateName = "Auto"; // replace with dynamic name if stored

        page.locator(nameInput).fill(candidateName);
        page.waitForTimeout(1000);

        if (page.locator(suggestions).first().isVisible()) {
            page.locator(suggestions).first().click();
        }

        page.locator(searchBtn).click();
    }

    public boolean isCandidateDisplayed() {

        String tableRows = LocatorReader.get("candidatesPage.tableRows");

        return page.locator(tableRows).count() > 0;
    }

    // ================= VIEW =================

    public void clickViewButton() {

        String rows = LocatorReader.get("candidatesPage.tableRows");

        page.locator(rows).first().locator("button").first().click();
    }

    public boolean isCandidateDetailsPageDisplayed() {

        String header =
                LocatorReader.get("candidateDetailsPage.candidateNameHeader");

        WaitUtil.waitForVisible(page, header);

        return page.locator(header).isVisible();
    }

    // ================= SHORTLIST =================

    public void clickShortlist() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("candidateDetailsPage.shortlistButton")
        );
    }

    public void enterShortlistDetails() {

        page.fill(
                LocatorReader.get("shortlistPage.shortlistNotes"),
                TestDataReader.get("candidate.shortlist.notes")
        );
    }

    public void clickSaveShortlist() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("shortlistPage.saveButton")
        );
    }

    // ================= INTERVIEW =================

    public void clickScheduleInterview() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("candidateDetailsPage.scheduleInterviewButton")
        );
    }

    public void enterInterviewDetails() {

        page.fill(
                LocatorReader.get("scheduleInterviewPage.interviewTitle"),
                TestDataReader.get("candidate.interview.title")
        );

        page.fill(
                LocatorReader.get("scheduleInterviewPage.interviewerInput"),
                TestDataReader.get("candidate.interview.interviewer")
        );

        page.fill(
                LocatorReader.get("scheduleInterviewPage.interviewDate"),
                TestDataReader.get("candidate.interview.date")
        );
    }

    public void clickSaveInterview() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("scheduleInterviewPage.saveButton")
        );
    }

    // ================= STATUS =================

    public String getCandidateStatus() {

        return page.locator(
                LocatorReader.get("candidateDetailsPage.candidateStatus")
        ).innerText();
    }
}