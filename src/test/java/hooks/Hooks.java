package hooks;

import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.PlaywrightFactory;
import utils.ScreenshotUtil;

public class Hooks {

    private Page page;

    @Before
    public void setUp() {
        PlaywrightFactory.initBrowser();
        page = PlaywrightFactory.getPage();
    }

    @After
    public void tearDown(Scenario scenario) {

        //  TAKE SCREENSHOT ONLY IF SCENARIO FAILED
        if (scenario.isFailed()) {
            ScreenshotUtil.takeScreenshot(
                    page,
                    scenario.getName()
            );
        }

        PlaywrightFactory.closeBrowser();
    }
}
