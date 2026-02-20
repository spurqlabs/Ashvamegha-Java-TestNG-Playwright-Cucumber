package Pages;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.WaitUtil;

public class RecruitmentPage {

    private final Page page;

    public RecruitmentPage(Page page) {
        this.page = page;
    }

    public void navigateToCandidatesPage() {

        String recruitmentMenu =
                LocatorReader.get("recruitmentPage.recruitmentMenu");

        WaitUtil.clickWhenReady(page, recruitmentMenu);
    }

    public boolean isCandidatesHeaderVisible() {

        String header =
                LocatorReader.get("recruitmentPage.candidatesHeader");

        WaitUtil.waitForVisible(page, header);

        return page.locator(header).isVisible();
    }
}
