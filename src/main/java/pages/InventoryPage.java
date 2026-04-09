package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {
    private final By inventoryContainer = By.id("inventory_container");
    private final By sortDropdown = By.className("product_sort_container");
    private final By itemNames = By.cssSelector(".inventory_item_name");
    private final By shoppingCartLink = By.className("shopping_cart_link");
    private final By shoppingCartBadge = By.className("shopping_cart_badge");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    // Confirms the inventory screen is present after login.
    public boolean isLoaded() {
        return isDisplayed(inventoryContainer);
    }

    // Returns the names shown on screen so sorting behavior can be asserted clearly.
    public List<String> getVisibleProductNames() {
        return findVisibleElements(itemNames)
                .stream()
                .map(element -> element.getText().trim())
                .collect(Collectors.toList());
    }

    // Sorts the products by the same labels the user sees in the UI dropdown.
    public void sortProductsBy(String visibleText) {
        selectByVisibleText(sortDropdown, visibleText);
    }

    // Adds the requested product by building the button id from the product name.
    public void addProductToCart(String productName) {
        click(By.id("add-to-cart-" + toInventorySlug(productName)));
    }

    // Removes the requested product from the inventory page cart state.
    public void removeProductFromCart(String productName) {
        click(By.id("remove-" + toInventorySlug(productName)));
    }

    // Opens the shopping cart from the header.
    public CartPage openCart() {
        click(shoppingCartLink);
        CartPage cartPage = new CartPage(driver);
        cartPage.waitUntilLoaded();
        return cartPage;
    }

    // Returns the badge value and defaults to zero when the badge is not visible.
    public int getCartBadgeCount() {
        if (driver.findElements(shoppingCartBadge).isEmpty()) {
            return 0;
        }
        return Integer.parseInt(getText(shoppingCartBadge));
    }

    // Opens the side menu so account actions like logout can be used.
    public void openMenu() {
        click(menuButton);
    }

    // Logs the user out and returns the login page.
    public LoginPage logout() {
        openMenu();
        click(logoutLink);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.isLoaded();
        return loginPage;
    }

    private String toInventorySlug(String productName) {
        return productName.toLowerCase()
                .replace(".", "")
                .replace("(", "")
                .replace(")", "")
                .replace(" ", "-");
    }
}
