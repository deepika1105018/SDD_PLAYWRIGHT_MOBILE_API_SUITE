package ae.sharjah.sdd.web.steps;

import ae.sharjah.sdd.web.pages.JQueryPage;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

import static org.testng.Assert.assertTrue;

public class WebSteps {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private JQueryPage jqueryPage;

    private boolean actionSuccessful;

    // =========================================================
    // SETUP
    // Runs before every @web scenario
    // =========================================================

    @Before("@web")
    public void setUpWeb() {

        playwright = Playwright.create();

        boolean isCI = System.getenv("CI") != null;

        BrowserType.LaunchOptions options =
                new BrowserType.LaunchOptions()
                        .setHeadless(isCI);

        // Local machine -> installed Chrome
        // GitHub Actions -> Playwright Chromium
        if (!isCI) {
            options.setChannel("chrome");
        }

        browser = playwright.chromium().launch(options);

        page = browser.newPage();

        jqueryPage = new JQueryPage(page);

        actionSuccessful = false;
    }

    // =========================================================
    // CASE 1 - DROPPABLE
    // =========================================================

    @When("I drag the element to the droppable target")
    public void dragElementToDroppableTarget() {

        jqueryPage.verifyDroppable();

        actionSuccessful = true;
    }

    // =========================================================
    // CASE 2 - SELECTABLE
    // =========================================================

    @When("I select items 1 3 and 7")
    public void selectItems() {

        jqueryPage.verifySelectable();

        actionSuccessful = true;
    }

    // =========================================================
    // CASE 3 - CONTROLGROUP
    // =========================================================

    @When("I choose Automatic Insurance and Book Now")
    public void useControlGroup() {

        jqueryPage.verifyControlGroup();

        actionSuccessful = true;
    }

    // =========================================================
    // CASE 4 - DATEPICKER
    // =========================================================

    @When("I select and validate the current date")
    public void selectCurrentDate() {

        jqueryPage.verifyDatePicker();

        actionSuccessful = true;
    }

    // =========================================================
    // CASE 5 - RESIZABLE
    // =========================================================

    @When("I resize the resizable box")
    public void resizeBox() {

        jqueryPage.verifyResizable();

        actionSuccessful = true;
    }

    // =========================================================
    // CASE 6 - SORTABLE
    // =========================================================

    @When("I reverse the sortable items")
    public void reverseSortableItems() {

        jqueryPage.verifySortable();

        actionSuccessful = true;
    }

    // =========================================================
    // COMMON VALIDATION
    // =========================================================

    @Then("the jQuery UI action should be successful")
    public void validateAction() {

        assertTrue(
                actionSuccessful,
                "jQuery UI operation was not completed successfully"
        );
    }

    // =========================================================
    // SCREENSHOT + TEARDOWN
    // =========================================================

    @After("@web")
    public void tearDownWeb(Scenario scenario) {

        try {

            if (page != null) {

                byte[] screenshot = page.screenshot(
                        new Page.ScreenshotOptions()
                                .setFullPage(true)
                );

                /*
                 * Attach screenshot to Cucumber report
                 */
                scenario.attach(
                        screenshot,
                        "image/png",
                        scenario.getName()
                );

                /*
                 * Attach to Allure only when scenario fails.
                 */
                if (scenario.isFailed()) {

                    Allure.addAttachment(
                            "Failure Screenshot - "
                                    + scenario.getName(),
                            new ByteArrayInputStream(screenshot)
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Screenshot could not be captured: "
                            + e.getMessage()
            );

        } finally {

            if (browser != null) {
                browser.close();
                browser = null;
            }

            if (playwright != null) {
                playwright.close();
                playwright = null;
            }

            page = null;
            jqueryPage = null;
        }
    }
}