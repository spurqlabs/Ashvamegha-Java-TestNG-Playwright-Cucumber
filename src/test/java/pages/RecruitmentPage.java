package pages;

import com.microsoft.playwright.Page;
import utils.LocatorReader;
import utils.WaitUtil;

public class RecruitmentPage {

    private Page page;

    public RecruitmentPage(Page page) {
        this.page = page;
    }

    public void navigateToCandidatesPage() {

        String recruitmentMenu =
                LocatorReader.get("recruitmentPage", "recruitmentMenu");

        String candidatesHeader =
                LocatorReader.get("recruitmentPage", "candidatesHeader");

        // Click Recruitment menu
        WaitUtil.waitForClickable(page, recruitmentMenu);
        page.click(recruitmentMenu);

        // Wait for Candidates page to load
        WaitUtil.waitForVisible(page, candidatesHeader);
    }

    public boolean isCandidatesPageDisplayed() {
        String candidatesHeader =
                LocatorReader.get("recruitmentPage", "candidatesHeader");
        return page.isVisible(candidatesHeader);
    }
}
