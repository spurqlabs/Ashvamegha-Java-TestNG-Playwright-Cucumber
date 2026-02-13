package Pages;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.TestDataReader;
import Utils.WaitUtil;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;

public class CandidateDetailsPage {

    private final Page page;

    public CandidateDetailsPage(Page page) {
        this.page = page;
    }

    // ================= PAGE VALIDATION =================

    public boolean isCandidateDetailsPageDisplayed() {

        String header =
                "h6.orangehrm-main-title:has-text('Application Stage')";

        WaitUtil.waitForVisible(page, header);

        return page.locator(header).first().isVisible();
    }

    public String getCandidateStatus() {

        WaitUtil.waitForVisible(
                page,
                "h6:has-text('Application Stage')"
        );

        String statusLocator =
                LocatorReader.get("candidateDetailsPage.candidateStatus");

        WaitUtil.waitForVisible(page, statusLocator);

        String fullText =
                page.locator(statusLocator).textContent().trim();

        return fullText.replace("Status:", "").trim();
    }


    // ================= SHORTLIST =================

    public void clickShortlistButton() {

        String shortlistBtn =
                LocatorReader.get("candidateDetailsPage.shortlistButton");

        WaitUtil.clickWhenReady(page, shortlistBtn);
    }


    public void enterShortlistDetailsFromJson() {

        String notesLocator =
                LocatorReader.get("shortlistPage.shortlistNotes");

        WaitUtil.waitForVisible(page, notesLocator);

        String notes =
                TestDataReader.get("candidate.shortlist.notes");

        page.locator(notesLocator).fill(notes);
    }

    public void saveShortlist() {

        String saveBtn =
                LocatorReader.get("shortlistPage.saveButton");

        WaitUtil.clickWhenReady(page, saveBtn);

        // Wait until back to Application Stage page
        WaitUtil.waitForVisible(
                page,
                "h6.orangehrm-main-title:has-text('Application Stage')"
        );

        // Wait until Schedule Interview button becomes visible
        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("candidateDetailsPage.scheduleInterviewButton")
        );
    }

    // ================= SCHEDULE INTERVIEW =================

    public void clickScheduleInterviewButton() {

        String scheduleBtn =
                LocatorReader.get("candidateDetailsPage.scheduleInterviewButton");

        WaitUtil.clickWhenReady(page, scheduleBtn);

        // Wait for Schedule Interview page header
        WaitUtil.waitForVisible(
                page,
                "h6.orangehrm-main-title:has-text('Schedule Interview')"
        );
    }

    public void enterInterviewDetailsFromJson() {

        String title = TestDataReader.get("candidate.interview.title");
        String interviewer = TestDataReader.get("candidate.interview.interviewer");
        String date = TestDataReader.get("candidate.interview.date");

        // ================= TITLE =================
        Locator titleField = page.locator(
                LocatorReader.get("scheduleInterviewPage.interviewTitle"));
        titleField.waitFor();
        titleField.fill(title);

        // ================= INTERVIEWER =================
        String interviewerInputLocator =
                LocatorReader.get("scheduleInterviewPage.interviewerInput");

        String suggestionLocator =
                LocatorReader.get("scheduleInterviewPage.interviewerSuggestion");

        Locator interviewerInput = page.locator(interviewerInputLocator);

        interviewerInput.click();
        interviewerInput.fill("");
        interviewerInput.fill(interviewer);

// Wait for suggestions to appear
        Locator suggestions = page.locator(suggestionLocator);
        suggestions.first().waitFor();

// Click exact match
        Locator exactMatch = suggestions
                .filter(new Locator.FilterOptions().setHasText(interviewer))
                .first();

        exactMatch.waitFor();
        exactMatch.click();

// 🔥 WAIT until dropdown disappears (IMPORTANT)
        suggestions.first().waitFor(
                new Locator.WaitForOptions()
                        .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN)
        );


        // ================= DATE =================
        Locator dateField = page.locator(
                LocatorReader.get("scheduleInterviewPage.interviewDate"));
        dateField.fill(date);
        page.keyboard().press("Tab");
    }


    public void saveInterview() {

        String saveBtn =
                LocatorReader.get("scheduleInterviewPage.saveButton");

        WaitUtil.clickWhenReady(page, saveBtn);

        // Wait until back to Application Stage page
        WaitUtil.waitForVisible(
                page,
                "h6.orangehrm-main-title:has-text('Application Stage')"
        );
    }


}
