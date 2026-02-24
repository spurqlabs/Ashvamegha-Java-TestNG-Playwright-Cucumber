package Pages;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.TestDataReader;
import Utils.WaitUtil;

import java.nio.file.Paths;

public class AddCandidatePage {

    private final Page page;

    public AddCandidatePage(Page page) {
        this.page = page;
    }

    public void clickAddCandidate() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("addCandidatePage.addButton")
        );
    }

    public void enterCandidateDetailsFromJson() {

        page.fill(
                LocatorReader.get("addCandidatePage.firstName"),
                TestDataReader.get("candidate.firstName")
        );

        page.fill(
                LocatorReader.get("addCandidatePage.lastName"),
                TestDataReader.get("candidate.lastName")
        );

        page.fill(
                LocatorReader.get("addCandidatePage.email"),
                TestDataReader.get("candidate.email")
        );

        page.fill(
                LocatorReader.get("addCandidatePage.phone"),
                TestDataReader.get("candidate.phone")
        );

        selectVacancy();

        page.fill(
                LocatorReader.get("addCandidatePage.keywords"),
                TestDataReader.get("candidate.keywords")
        );
    }

    public void selectVacancy() {

        String dropdown =
                LocatorReader.get("addCandidatePage.vacancyDropdown");

        String optionTemplate =
                LocatorReader.get("addCandidatePage.vacancyOption");

        page.click(dropdown);

        String option =
                optionTemplate.replace(
                        "{VACANCY}",
                        TestDataReader.get("candidate.vacancy")
                );

        page.click(option);
    }

    public void uploadResume() {

        page.setInputFiles(
                LocatorReader.get("addCandidatePage.resumeUpload"),
                Paths.get(TestDataReader.get("candidate.resumePath"))
        );
    }

    public void saveCandidate() {

        WaitUtil.clickWhenReady(
                page,
                LocatorReader.get("addCandidatePage.saveButton")
        );
    }

    public boolean isCandidateSaved() {

        String toast =
                LocatorReader.get("addCandidatePage.successToast");

        WaitUtil.waitForVisible(page, toast);

        return page.locator(toast).isVisible();
    }
}
