package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutOverviewPage extends BasePage {
    private final By finishButton = By.id("finish");
    private final By summaryContainer = By.id("checkout_summary_container");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    // Confirms that the overview step is ready before the next checkout action happens.
    public void waitUntilLoaded() {
        waitForUrlToContain("checkout-step-two");
        waitForPageAnchor(finishButton);
        findVisible(summaryContainer);
    }

    // Confirms that the overview step is visible before the test continues.
    public boolean isLoaded() {
        waitUntilLoaded();
        return isDisplayed(summaryContainer);
    }

    // Completes the order and returns the confirmation page object.
    public CheckoutCompletePage finishCheckout() {
        click(finishButton);
        CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage(driver);
        checkoutCompletePage.waitUntilLoaded();
        return checkoutCompletePage;
    }
}
