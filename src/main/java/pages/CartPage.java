package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {
    private final By cartItemNames = By.cssSelector(".cart_item .inventory_item_name");
    private final By checkoutButton = By.id("checkout");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By cartContentsContainer = By.id("cart_contents_container");
    private final By emptyCartState = By.cssSelector(".cart_item");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Collects the names currently shown in the cart so the tests can assert cart contents clearly.
    public List<String> getCartItemNames() {
        return findVisibleElements(cartItemNames)
                .stream()
                .map(element -> element.getText().trim())
                .collect(Collectors.toList());
    }

    // Removes a specific item from the cart using the same product slug strategy as the app ids.
    public void removeProduct(String productName) {
        click(By.id("remove-" + toInventorySlug(productName)));
    }

    // Moves from the cart into the checkout information step.
    public CheckoutInformationPage clickCheckout() {
        click(checkoutButton);
        CheckoutInformationPage checkoutInformationPage = new CheckoutInformationPage(driver);
        checkoutInformationPage.waitUntilLoaded();
        return checkoutInformationPage;
    }

    // Returns to the inventory page when the user chooses to keep shopping.
    public InventoryPage clickContinueShopping() {
        click(continueShoppingButton);
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.isLoaded();
        return inventoryPage;
    }

    // Tells whether the cart contains any item rows.
    public boolean hasItemsInCart() {
        return !driver.findElements(emptyCartState).isEmpty();
    }

    // Confirms the cart page is ready before the next action tries to use it.
    public void waitUntilLoaded() {
        waitForUrlToContain("cart");
        waitForPageAnchor(checkoutButton);
        findVisible(cartContentsContainer);
    }

    private String toInventorySlug(String productName) {
        return productName.toLowerCase()
                .replace(".", "")
                .replace("(", "")
                .replace(")", "")
                .replace(" ", "-");
    }
}
