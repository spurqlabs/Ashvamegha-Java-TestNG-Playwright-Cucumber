package Pages;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.WaitUtil;
import com.microsoft.playwright.options.LoadState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AddCandidatePage {

    private Page page;
    private static final Logger log = LoggerFactory.getLogger(AddCandidatePage.class);

    public AddCandidatePage(Page page) {
        this.page = page;
    }

    public void clickAddCandidate() {
        log.info("Clicking Add Candidate button");
        page.click(LocatorReader.get("recruitmentPage.addCandidateBtn"));
        WaitUtil.waitForVisible(page, LocatorReader.get("addCandidatePage.firstName"));
        log.info("Add Candidate form opened");
    }

    public void enterFirstName(String firstName) {
        page.fill(LocatorReader.get("addCandidatePage.firstName"), firstName);
        log.info("Entered first name: {}", firstName);
    }

    public void enterLastName(String lastName) {
        page.fill(LocatorReader.get("addCandidatePage.lastName"), lastName);
        log.info("Entered last name: {}", lastName);
    }

    public void enterEmail(String email) {
        page.fill(LocatorReader.get("addCandidatePage.email"), email);
        log.info("Entered email: {}", email);
    }

    public void enterPhone(String phone) {
        page.fill(LocatorReader.get("addCandidatePage.phone"), phone);
        log.info("Entered phone: {}", phone);
    }

    public void selectVacancy(String vacancy) {

        //  Click dropdown
        log.info("Opening vacancy dropdown");
        page.click(LocatorReader.get("addCandidatePage.vacancyDropdown"));

        // Small wait for animation (important!)
        page.waitForTimeout(500);

        //  Build dynamic locator
        String optionTemplate = LocatorReader.get("addCandidatePage.vacancyOption");
        String optionLocator = optionTemplate.replace("{VACANCY}", vacancy);

        //  Wait for option
        WaitUtil.waitForVisible(page, optionLocator);

        // Click option
        log.info("Selecting vacancy: {}", vacancy);
        page.click(optionLocator);
        log.info("Vacancy selected: {}", vacancy);
    }


    public void enterKeywords(String keywords) {
        page.fill(LocatorReader.get("addCandidatePage.keywords"), keywords);
        log.info("Entered keywords: {}", keywords);
    }

    public void uploadResume(String resumePath) {
        log.info("Uploading resume from: {}", resumePath);
        page.setInputFiles(
                LocatorReader.get("addCandidatePage.resumeUpload"),
                java.nio.file.Paths.get(resumePath)
        );
        log.info("Resume file uploaded");
    }

    public void clickSave() {
        log.info("=== SAVE STEP: Clicking Save button ===");
        page.click(LocatorReader.get("addCandidatePage.saveButton"));

        // Wait for the page to become idle (loader to disappear)
        log.info("Waiting for candidate to be saved...");
        page.waitForLoadState(LoadState.NETWORKIDLE);
        log.info("✓ Candidate save operation completed");

    }
}
