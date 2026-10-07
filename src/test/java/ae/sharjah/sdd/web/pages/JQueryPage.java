package ae.sharjah.sdd.web.pages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class JQueryPage {

    private final Page page;

    private static final String BASE_URL = "https://jqueryui.com/";
    private static final String DEMO_FRAME = "iframe.demo-frame";

    public JQueryPage(Page page) {
        this.page = page;
    }

    /*
     * Common navigation
     * Every scenario starts from jQuery UI home page.
     */
    public void openHomePage() {
        page.navigate(BASE_URL);
        page.waitForLoadState();

        if (!page.url().contains("jqueryui.com")) {
            throw new AssertionError("jQuery UI home page was not opened");
        }
    }

    /*
     * Click demo link from left-side menu.
     */
    private void openDemo(String demoName) {

        Locator demoLink = page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions()
                        .setName(demoName)
                        .setExact(true)
        );

        demoLink.click();

        page.waitForLoadState();

        page.locator(DEMO_FRAME).waitFor();
    }

    private FrameLocator getDemoFrame() {
        return page.frameLocator(DEMO_FRAME);
    }

    // =========================================================
    // CASE 1 - DROPPABLE
    // =========================================================

    public void verifyDroppable() {

        openHomePage();
        openDemo("Droppable");

        FrameLocator frame = getDemoFrame();

        Locator draggable = frame.locator("#draggable");
        Locator droppable = frame.locator("#droppable");

        draggable.waitFor();
        droppable.waitFor();

        draggable.dragTo(droppable);

        String result = droppable.innerText();

        if (!result.contains("Dropped!")) {
            throw new AssertionError(
                    "Droppable failed. Expected 'Dropped!' but found: "
                            + result
            );
        }
    }

    // =========================================================
    // CASE 2 - SELECTABLE
    // Select Item 1, Item 3 and Item 7
    // =========================================================

    public void verifySelectable() {

        openHomePage();
        openDemo("Selectable");

        FrameLocator frame = getDemoFrame();

        Locator items = frame.locator("#selectable li");

        if (items.count() < 7) {
            throw new AssertionError(
                    "Expected 7 selectable items but found: "
                            + items.count()
            );
        }

        // Item 1
        items.nth(0).click();

        // Multi-select Item 3 and Item 7
        page.keyboard().down("Control");

        try {
            items.nth(2).click();
            items.nth(6).click();
        } finally {
            page.keyboard().up("Control");
        }

        validateSelected(items.nth(0), "Item 1");
        validateSelected(items.nth(2), "Item 3");
        validateSelected(items.nth(6), "Item 7");
    }

    private void validateSelected(
            Locator item,
            String itemName
    ) {

        String cssClass = item.getAttribute("class");

        if (cssClass == null ||
                !cssClass.contains("ui-selected")) {

            throw new AssertionError(
                    itemName + " was not selected"
            );
        }
    }

    // =========================================================
    // CASE 3 - CONTROLGROUP
    // Automatic -> Insurance -> Book Now
    // =========================================================

    public void verifyControlGroup() {

        openHomePage();
        openDemo("Controlgroup");

        FrameLocator frame = getDemoFrame();

        /*
         * The page contains two control groups.
         * We work with the first Car Rental control group.
         */

        Locator automaticLabel =
                frame.locator(
                        "label[for='transmission-automatic']"
                ).first();

        automaticLabel.click();

        Locator insuranceLabel =
                frame.locator(
                        "label[for='insurance']"
                ).first();

        insuranceLabel.click();

        Locator automatic =
                frame.locator(
                        "#transmission-automatic"
                ).first();

        Locator insurance =
                frame.locator(
                        "#insurance"
                ).first();

        if (!automatic.isChecked()) {
            throw new AssertionError(
                    "Automatic transmission was not selected"
            );
        }

        if (!insurance.isChecked()) {
            throw new AssertionError(
                    "Insurance was not selected"
            );
        }

        /*
         * jQuery demo has two Book Now! buttons.
         * Use the first one from the horizontal Car Rental group.
         */
        Locator bookNow = frame.getByRole(
                AriaRole.BUTTON,
                new FrameLocator.GetByRoleOptions()
                        .setName("Book Now!")
                        .setExact(true)
        ).first();

        bookNow.click();
    }

    // =========================================================
    // CASE 4 - DATEPICKER
    // Select current date and validate
    // =========================================================

    public void verifyDatePicker() {

        openHomePage();
        openDemo("Datepicker");

        FrameLocator frame = getDemoFrame();

        Locator dateInput =
                frame.locator("#datepicker");

        dateInput.click();

        /*
         * ui-datepicker-today identifies current date.
         */
        Locator currentDate =
                frame.locator(
                        "#ui-datepicker-div .ui-datepicker-today a"
                );

        currentDate.waitFor();
        currentDate.click();

        String actualDate = dateInput.inputValue();

        String expectedDate =
                LocalDate.now().format(
                        DateTimeFormatter.ofPattern("MM/dd/yyyy")
                );

        if (!expectedDate.equals(actualDate)) {

            throw new AssertionError(
                    "Date validation failed. Expected: "
                            + expectedDate
                            + " but found: "
                            + actualDate
            );
        }
    }

    // =========================================================
    // CASE 5 - RESIZABLE
    // Resize box and validate dimensions
    // =========================================================

    public void verifyResizable() {

        openHomePage();
        openDemo("Resizable");

        FrameLocator frame = getDemoFrame();

        Locator resizable =
                frame.locator("#resizable");

        Locator resizeHandle =
                frame.locator(
                        "#resizable .ui-resizable-se"
                );

        resizable.waitFor();
        resizeHandle.waitFor();

        BoundingBox before =
                resizable.boundingBox();

        BoundingBox handle =
                resizeHandle.boundingBox();

        if (before == null) {
            throw new AssertionError(
                    "Unable to get initial resizable dimensions"
            );
        }

        if (handle == null) {
            throw new AssertionError(
                    "Unable to locate resize handle"
            );
        }

        double startX =
                handle.x + (handle.width / 2);

        double startY =
                handle.y + (handle.height / 2);

        page.mouse().move(startX, startY);

        page.mouse().down();

        page.mouse().move(
                startX + 100,
                startY + 80
        );

        page.mouse().up();

        BoundingBox after =
                resizable.boundingBox();

        if (after == null) {
            throw new AssertionError(
                    "Unable to get resized dimensions"
            );
        }

        if (after.width <= before.width) {

            throw new AssertionError(
                    "Resizable width did not increase"
            );
        }

        if (after.height <= before.height) {

            throw new AssertionError(
                    "Resizable height did not increase"
            );
        }
    }

    // =========================================================
    // CASE 6 - SORTABLE
    // ASC -> DESC
    // =========================================================

    public void verifySortable() {

        openHomePage();
        openDemo("Sortable");

        FrameLocator frame = getDemoFrame();

        Locator items =
                frame.locator("#sortable li");

        if (items.count() != 7) {

            throw new AssertionError(
                    "Expected 7 sortable items but found: "
                            + items.count()
            );
        }

        /*
         * Initial:
         * 1 2 3 4 5 6 7
         *
         * Move each next item to first position:
         *
         * 2 1 3 4 5 6 7
         * 3 2 1 4 5 6 7
         * ...
         * 7 6 5 4 3 2 1
         */

        for (int number = 2; number <= 7; number++) {

            Locator source =
                    frame.getByText(
                            "Item " + number,
                            new FrameLocator.GetByTextOptions()
                                    .setExact(true)
                    );

            Locator firstItem =
                    frame.locator(
                            "#sortable li"
                    ).first();

            source.dragTo(firstItem);
        }

        List<String> expectedOrder =
                Arrays.asList(
                        "Item 7",
                        "Item 6",
                        "Item 5",
                        "Item 4",
                        "Item 3",
                        "Item 2",
                        "Item 1"
                );

        Locator sortedItems =
                frame.locator("#sortable li");

        for (int i = 0;
             i < expectedOrder.size();
             i++) {

            String actual =
                    sortedItems.nth(i)
                            .innerText()
                            .trim();

            String expected =
                    expectedOrder.get(i);

            if (!expected.equals(actual)) {

                throw new AssertionError(
                        "Sortable validation failed at position "
                                + (i + 1)
                                + ". Expected: "
                                + expected
                                + " but found: "
                                + actual
                );
            }
        }
    }
}