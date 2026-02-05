package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.CandidateDataReader;
import utils.LocatorReader;
import utils.WaitUtil;

import java.nio.file.Paths;

public class AddCandidatePage {

    private final Page page;

    public AddCandidatePage(Page page) {
        this.page = page;
    }

    public void clickAddCandidate() {
        String addBtn = LocatorReader.get("addCandidatePage", "addButton");
        WaitUtil.waitForClickable(page, addBtn);
        page.click(addBtn);
    }

    public void enterFirstName() {
        String firstName = LocatorReader.get("addCandidatePage", "firstName");
        WaitUtil.waitForVisible(page, firstName);
        page.fill(firstName, CandidateDataReader.getFirstName());
    }

    public void enterLastName() {
        String lastName = LocatorReader.get("addCandidatePage", "lastName");
        WaitUtil.waitForVisible(page, lastName);
        page.fill(lastName, CandidateDataReader.getLastName());
    }

    public void enterEmail() {
        String email = LocatorReader.get("addCandidatePage", "email");
        WaitUtil.waitForVisible(page, email);
        page.fill(email, CandidateDataReader.getEmail());
    }

    public void enterPhone() {
        String phone = LocatorReader.get("addCandidatePage", "phone");
        WaitUtil.waitForVisible(page, phone);
        page.fill(phone, CandidateDataReader.getPhoneNumber());
    }

    public void selectVacancy() {
        String dropdown = LocatorReader.get("addCandidatePage", "vacancyDropdown");
        String optionTemplate = LocatorReader.get("addCandidatePage", "vacancyOption");

        page.click(dropdown);

        String option =
                optionTemplate.replace("{VACANCY}", CandidateDataReader.getVacancy());

        page.click(option);
    }

    public void uploadResume() {
        String uploadInput =
                LocatorReader.get("addCandidatePage", "resumeUpload");

        page.setInputFiles(
                uploadInput,
                Paths.get("C:/Users/Ashvamegha/Downloads/Demofile.pdf")
        );
    }

    public void enterKeywords() {
        String keywords =
                LocatorReader.get("addCandidatePage", "keywords");

        page.locator(keywords).scrollIntoViewIfNeeded();
        page.locator(keywords).click();
        page.keyboard().type(CandidateDataReader.getKeywords());
        page.keyboard().press("Enter");
    }

    public void saveCandidate() {

        // Wait for any loader to disappear
        page.waitForSelector(
                ".oxd-form-loader",
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.DETACHED)
                        .setTimeout(40000)
        );

        Locator saveButton = page.locator("button[type='submit']");

        saveButton.scrollIntoViewIfNeeded();
        saveButton.waitFor(
                new Locator.WaitForOptions().setTimeout(30000)
        );

        saveButton.click();
    }

    public void verifySuccessMessage() {

        Locator successToast = page.locator(".oxd-toast--success");

        successToast.waitFor(
                new Locator.WaitForOptions().setTimeout(30000)
        );

        String toastText = successToast.innerText().toLowerCase();

        if (!toastText.contains("success")) {
            throw new AssertionError(
                    "Expected success message not found. Actual text: " + toastText
            );
        }
    }
}
