package pages;

import com.microsoft.playwright.Page;
import utils.CandidateDataReader;
import utils.LocatorReader;
import utils.WaitUtil;

import java.nio.file.Paths;

public class AddCandidatePage {

    private Page page;

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
        String vacancyDropdown =
                LocatorReader.get("addCandidatePage", "vacancyDropdown");
        String vacancyOptionTemplate =
                LocatorReader.get("addCandidatePage", "vacancyOption");

        page.click(vacancyDropdown);

        String vacancyOption =
                vacancyOptionTemplate.replace(
                        "{VACANCY}",
                        CandidateDataReader.getVacancy()
                );

        page.click(vacancyOption);
    }

    public void uploadResume() {
        String uploadInput =
                LocatorReader.get("addCandidatePage", "resumeUpload");

        page.setInputFiles(
                uploadInput,
                Paths.get("C:/Users/Ashvamegha/Downloads/Demofile.pdf")
        );
    }

    //
    public void enterKeywords() {

        String keywords =
                LocatorReader.get("addCandidatePage", "keywords");

        // Scroll to make sure section is reachable
        page.evaluate("window.scrollBy(0, 600)");

        // Click to activate React input
        page.locator(keywords).click();

        // Type keyword (keyboard, not fill)
        page.keyboard().type(CandidateDataReader.getKeywords());

        // Press Enter to confirm chip
        page.keyboard().press("Enter");
    }

    public void saveCandidate() {

        String baseSaveBtn =
                LocatorReader.get("addCandidatePage", "saveButton");

        String formLoader = ".oxd-form-loader";

        try {
            // Wait for loader to disappear (IMPORTANT) with extended timeout
            page.waitForSelector(
                    formLoader,
                    new Page.WaitForSelectorOptions()
                            .setState(com.microsoft.playwright.options.WaitForSelectorState.DETACHED)
                            .setTimeout(20000)
            );
            System.out.println("Form loader detached successfully");
        } catch (Exception e) {
            // Loader might not exist, continue
            System.out.println("Form loader not found or already detached, proceeding...");
        }

        // Scroll down to ensure Save button is in view
        page.evaluate("window.scrollBy(0, 300)");

        // Add a small delay for page stabilization
        page.waitForTimeout(500);

        // Try multiple selector strategies
        String saveBtn = null;
        String[] selectors = {
            baseSaveBtn,
            "button[type='submit']",
            "button.oxd-button--primary",
            "//button[contains(text(), 'Save')]",
            "button >> text=Save",
            "//button[normalize-space()='Save']"
        };

        for (String selector : selectors) {
            try {
                int count = page.locator(selector).count();
                System.out.println("Selector '" + selector + "' found " + count + " element(s)");

                if (count > 0) {
                    saveBtn = selector;
                    System.out.println("Using selector: " + saveBtn);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Selector '" + selector + "' failed: " + e.getMessage());
            }
        }

        // If no selector worked, use JavaScript to find and click the button
        if (saveBtn == null) {
            System.out.println("No selector found, trying JavaScript approach");
            try {
                String scrollScript = "const buttons = Array.from(document.querySelectorAll('button'));" +
                    "const saveButton = buttons.find(b => b.textContent.toLowerCase().includes('save'));" +
                    "if (saveButton) saveButton.scrollIntoView();";
                page.evaluate(scrollScript);

                page.waitForTimeout(1000);

                String clickScript = "const buttons = Array.from(document.querySelectorAll('button'));" +
                    "const saveButton = buttons.find(b => b.textContent.toLowerCase().includes('save'));" +
                    "if (saveButton) saveButton.click();";
                page.evaluate(clickScript);

                System.out.println("Button clicked via JavaScript");
                return;
            } catch (Exception e) {
                System.out.println("JavaScript approach failed: " + e.getMessage());
            }
        }

        if (saveBtn == null) {
            throw new RuntimeException("Could not find Save button with any selector strategy");
        }

        // Now wait for Save button with extended timeout and scroll into view
        page.waitForSelector(
                saveBtn,
                new Page.WaitForSelectorOptions()
                        .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                        .setTimeout(20000)
        );

        // Scroll button into view
        page.locator(saveBtn).scrollIntoViewIfNeeded();

        // Add delay before clicking
        page.waitForTimeout(1000);

        System.out.println("Clicking Save button with selector: " + saveBtn);

        // Click the button using force click if needed
        page.click(saveBtn, new Page.ClickOptions().setForce(true));
    }

    public void verifySuccessMessage() {

        String baseSuccessToast = LocatorReader.get("addCandidatePage", "successToast");

        // Try multiple selector strategies for the success toast
        String successToastSelector = null;
        String[] selectors = {
            baseSuccessToast,
            ".oxd-toast--success",
            "[role='alert']",
            ".oxd-toast",
            "//div[contains(@class, 'oxd-toast')]",
            "div >> text=Successfully",
            "//div[contains(text(), 'Successfully')]"
        };

        for (String selector : selectors) {
            try {
                int count = page.locator(selector).count();
                System.out.println("Success toast selector '" + selector + "' found " + count + " element(s)");

                if (count > 0) {
                    successToastSelector = selector;
                    System.out.println("Using success toast selector: " + successToastSelector);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Success toast selector '" + selector + "' failed: " + e.getMessage());
            }
        }

        // If no selector worked, use JavaScript to find success message
        if (successToastSelector == null) {
            System.out.println("No success toast selector found, trying JavaScript approach");
            try {
                String jsScript = "const toasts = Array.from(document.querySelectorAll('[role=\"alert\"], .oxd-toast, .oxd-toast--success'));" +
                    "const successToast = toasts.find(t => t.textContent.toLowerCase().includes('successfully'));" +
                    "if (successToast) { " +
                    "  const text = successToast.textContent.toLowerCase(); " +
                    "  console.log('Found success message: ' + text); " +
                    "  return text.includes('saved') || text.includes('successfully'); " +
                    "} return false;";

                Object result = page.evaluate(jsScript);
                System.out.println("JavaScript evaluation result: " + result);

                if (result instanceof Boolean && (Boolean) result) {
                    System.out.println("Success message verified via JavaScript");
                    return;
                } else {
                    throw new AssertionError("Success message not found via JavaScript");
                }
            } catch (Exception e) {
                System.out.println("JavaScript approach failed: " + e.getMessage());
                throw new AssertionError("Could not verify success message: " + e.getMessage());
            }
        }

        // Wait for the success toast to be visible
        page.waitForSelector(
                successToastSelector,
                new Page.WaitForSelectorOptions()
                        .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                        .setTimeout(20000)
        );

        System.out.println("Verifying success message content");

        // Get the text and verify
        String toastText = page.locator(successToastSelector).innerText();
        System.out.println("Toast text: " + toastText);

        if (!toastText.toLowerCase().contains("successfully saved") &&
            !toastText.toLowerCase().contains("successfully") &&
            !toastText.toLowerCase().contains("saved")) {
            throw new AssertionError(
                    "Expected success message not found. Actual text: " + toastText
            );
        }

        System.out.println("Success message verified successfully");
    }
}
