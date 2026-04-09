package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {
    private final By completionHeader = By.cssSelector("[data-test='complete-header']");
    private final By backHomeButton = By.id("back-to-products");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    // Confirms the order confirmation page is fully visible before assertions run.
    public void waitUntilLoaded() {
        waitForUrlToContain("checkout-complete");
        waitForPageAnchor(backHomeButton);
        findVisible(completionHeader);
    }

    // Returns the final order confirmation heading shown after a successful purchase.
    public String getConfirmationMessage() {
        waitUntilLoaded();
        return getText(completionHeader);
    }

    // Navigates back to inventory after order completion.
    public InventoryPage clickBackHome() {
        click(backHomeButton);
        return new InventoryPage(driver);
    }
}
