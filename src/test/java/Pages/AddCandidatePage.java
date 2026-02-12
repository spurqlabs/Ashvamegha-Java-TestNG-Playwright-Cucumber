package Pages;

import com.microsoft.playwright.Page;
import Utils.LocatorReader;
import Utils.WaitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddCandidatePage {

    private final Page page;
    private static final Logger log =
            LoggerFactory.getLogger(AddCandidatePage.class);

    public AddCandidatePage(Page page) {
        this.page = page;
    }

    public void clickAddCandidate() {
        log.info("Clicking Add Candidate button");
        page.click(LocatorReader.get("recruitmentPage.addCandidateBtn"));

        WaitUtil.waitForVisible(
                page,
                LocatorReader.get("addCandidatePage.firstName")
        );

        log.info("Add Candidate form opened");
    }

    public void enterFirstName(String firstName) {
        page.fill(
                LocatorReader.get("addCandidatePage.firstName"),
                firstName
        );
        log.info("Entered first name: {}", firstName);
    }

    public void enterLastName(String lastName) {
        page.fill(
                LocatorReader.get("addCandidatePage.lastName"),
                lastName
        );
        log.info("Entered last name: {}", lastName);
    }

    public void enterEmail(String email) {
        page.fill(
                LocatorReader.get("addCandidatePage.email"),
                email
        );
        log.info("Entered email: {}", email);
    }

    public void enterPhone(String phone) {
        page.fill(
                LocatorReader.get("addCandidatePage.phone"),
                phone
        );
        log.info("Entered phone: {}", phone);
    }

    public void selectVacancy(String vacancy) {
        log.info("Opening vacancy dropdown");

        try {
            page.click(LocatorReader.get("addCandidatePage.vacancyDropdown"));
            WaitUtil.waitForPageLoad(page);

            String optionTemplate =
                    LocatorReader.get("addCandidatePage.vacancyOption");
            String optionLocator =
                    optionTemplate.replace("{VACANCY}", vacancy);

            log.info("Trying primary vacancy locator: {}", optionLocator);

            try {
                WaitUtil.waitForVisible(page, optionLocator);
                page.click(optionLocator);
                log.info("Vacancy selected using primary locator: {}", vacancy);
                return;

            } catch (Exception primaryFailure) {
                log.warn("Primary locator failed, trying alternatives");

                String alternativeLocator = "div[role='option']";
                int optionCount = page.locator(alternativeLocator).count();

                log.info("Found {} dropdown options", optionCount);

                for (int i = 0; i < optionCount; i++) {
                    String optionText =
                            page.locator(alternativeLocator).nth(i).textContent();

                    if (optionText != null && optionText.contains(vacancy)) {
                        page.locator(alternativeLocator).nth(i).click();
                        log.info("Vacancy selected using role option: {}", vacancy);
                        return;
                    }
                }

                String xpathLocator =
                        "//div[contains(@class,'oxd-select-option')]//span[contains(text(),'" + vacancy + "')]";

                page.locator(xpathLocator).click();
                log.info("Vacancy selected using XPath: {}", vacancy);
            }

        } catch (Exception e) {
            log.error("Failed to select vacancy '{}'", vacancy, e);
            throw new AssertionError("Could not select vacancy: " + vacancy, e);
        }
    }

    public void enterKeywords(String keywords) {
        log.info("Entering keywords: {}", keywords);

        String locator =
                LocatorReader.get("addCandidatePage.keywords");

        WaitUtil.waitForVisible(page, locator);
        page.locator(locator).first().fill(keywords);

        log.info("Keywords entered successfully");
    }

    public void uploadResume(String resumePath) {
        log.info("Uploading resume from: {}", resumePath);

        page.setInputFiles(
                LocatorReader.get("addCandidatePage.resumeUpload"),
                java.nio.file.Paths.get(resumePath)
        );

        log.info("Resume uploaded successfully");
    }

    public void clickSave() {
        log.info("Clicking Save button");

        page.click(LocatorReader.get("addCandidatePage.saveButton"));
        WaitUtil.waitForPageLoad(page);

        try {
            WaitUtil.waitForToast(
                    page,
                    LocatorReader.get("addCandidatePage.successToast")
            );
            log.info("Success toast displayed");
        } catch (Exception e) {
            log.warn("Success toast not visible within timeout");
        }
    }
}
