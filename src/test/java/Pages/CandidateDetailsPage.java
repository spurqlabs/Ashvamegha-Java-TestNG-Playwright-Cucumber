package Pages;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.TestDataReader;
import Utils.WaitUtil;

public class CandidateDetailsPage {

    private final Page page;

    // ================= CONSTRUCTOR =================
    public CandidateDetailsPage(Page page) {
        this.page = page;
    }

    // ================= PAGE VALIDATION =================

    public boolean isCandidateDetailsPageDisplayed() {
        String header =
                LocatorReader.get("candidateDetailsPage.candidateNameHeader");
        WaitUtil.waitForVisible(page, header);
        return page.locator(header).isVisible();
    }

    public String getCandidateStatus() {
        String status =
                LocatorReader.get("candidateDetailsPage.candidateStatus");
        WaitUtil.waitForVisible(page, status);
        return page.locator(status).textContent().trim();
    }

    // ================= SHORTLIST ACTIONS =================

    public void clickShortlistButton() {
        String shortlistBtn =
                LocatorReader.get("candidateDetailsPage.shortlistButton");
        WaitUtil.clickWhenReady(page, shortlistBtn);
    }

    public void enterShortlistDetailsFromJson() {
        String notesLocator =
                LocatorReader.get("shortlistPage.shortlistNotes");

        WaitUtil.waitForVisible(page, notesLocator);
        String notes = TestDataReader.get("shortlist.notes");
        page.locator(notesLocator).fill(notes);
    }

    public void saveShortlist() {
        String saveBtn =
                LocatorReader.get("shortlistPage.saveButton");

        WaitUtil.clickWhenReady(page, saveBtn);
        WaitUtil.waitForPageLoad(page);
        WaitUtil.waitForToast(
                page,
                LocatorReader.get("addCandidatePage.successToast")
        );
    }

    // ================= SCHEDULE INTERVIEW ACTIONS =================

    public void clickScheduleInterviewButton() {
        String scheduleBtn =
                LocatorReader.get("candidateDetailsPage.scheduleInterviewButton");
        WaitUtil.clickWhenReady(page, scheduleBtn);
    }

    public void enterInterviewDetailsFromJson() {

        // Interview Title
        String titleInput =
                LocatorReader.get("scheduleInterviewPage.interviewTitle");
        WaitUtil.waitForVisible(page, titleInput);
        page.locator(titleInput)
                .fill(TestDataReader.get("interview.title"));

        // Interviewer (auto-suggestion)
        String interviewerInput =
                LocatorReader.get("scheduleInterviewPage.interviewerInput");
        WaitUtil.waitForVisible(page, interviewerInput);
        page.locator(interviewerInput)
                .fill(TestDataReader.get("interview.interviewer"));

        String interviewerOption =
                LocatorReader.get("scheduleInterviewPage.interviewerSuggestion");
        WaitUtil.waitForVisible(page, interviewerOption);
        page.locator(interviewerOption).first().click();

        // Interview Date
        String dateInput =
                LocatorReader.get("scheduleInterviewPage.interviewDate");
        WaitUtil.waitForVisible(page, dateInput);
        page.locator(dateInput)
                .fill(TestDataReader.get("interview.date"));

        // Interview Time
        String timeInput =
                LocatorReader.get("scheduleInterviewPage.interviewTime");
        WaitUtil.waitForVisible(page, timeInput);
        page.locator(timeInput)
                .fill(TestDataReader.get("interview.time"));

        // Notes
        String notesInput =
                LocatorReader.get("scheduleInterviewPage.interviewNotes");
        WaitUtil.waitForVisible(page, notesInput);
        page.locator(notesInput)
                .fill(TestDataReader.get("interview.notes"));
    }

    public void saveInterview() {
        String saveBtn =
                LocatorReader.get("scheduleInterviewPage.saveButton");

        WaitUtil.clickWhenReady(page, saveBtn);
        WaitUtil.waitForPageLoad(page);
        WaitUtil.waitForToast(
                page,
                LocatorReader.get("addCandidatePage.successToast")
        );
    }

    // ================= SUCCESS VALIDATION =================

    public boolean isSuccessToastDisplayed() {
        String toast =
                LocatorReader.get("addCandidatePage.successToast");
        WaitUtil.waitForToast(page, toast);
        return page.locator(toast).isVisible();
    }
}
