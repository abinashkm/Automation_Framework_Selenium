package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

import java.util.List;

public class InventoryTest extends BaseTest {

    @Test(
            description = "Users should be able to sort products from low to high price.",
            groups = {"regression", "happy-path"}
    )
    public void userCanSortProductsByPriceLowToHigh() {
        LoginPage loginPage = openLoginPage();
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        inventoryPage.sortProductsBy("Price (low to high)");
        List<String> productNames = inventoryPage.getVisibleProductNames();

        Assert.assertEquals(productNames.getFirst(), "Sauce Labs Onesie",
                "The lowest priced product should appear first after sorting.");
    }

    @Test(
            description = "Users should be able to add multiple products and see the cart badge update.",
            groups = {"smoke", "regression", "happy-path"}
    )
    public void userCanAddMultipleProductsToCart() {
        LoginPage loginPage = openLoginPage();
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.addProductToCart("Sauce Labs Bike Light");
        inventoryPage.addProductToCart("Sauce Labs Fleece Jacket");

        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 3,
                "Cart badge should match the number of added products.");
    }

    @Test(
            description = "Users should be able to open the cart and verify the selected products.",
            groups = {"regression", "happy-path"}
    )
    public void userCanReviewProductsInCart() {
        LoginPage loginPage = openLoginPage();
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.addProductToCart("Sauce Labs Bike Light");
        CartPage cartPage = inventoryPage.openCart();
        List<String> cartItems = cartPage.getCartItemNames();

        Assert.assertTrue(cartItems.contains("Sauce Labs Backpack"),
                "Backpack should be present in the cart.");
        Assert.assertTrue(cartItems.contains("Sauce Labs Bike Light"),
                "Bike Light should be present in the cart.");
    }

    @Test(
            description = "Users should be able to remove a product from the cart and keep the remaining item.",
            groups = {"regression", "happy-path"}
    )
    public void userCanRemoveProductFromCart() {
        LoginPage loginPage = openLoginPage();
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.addProductToCart("Sauce Labs Bike Light");
        CartPage cartPage = inventoryPage.openCart();
        cartPage.removeProduct("Sauce Labs Bike Light");
        List<String> cartItems = cartPage.getCartItemNames();

        Assert.assertEquals(cartItems.size(), 1, "Only one product should remain after removal.");
        Assert.assertTrue(cartItems.contains("Sauce Labs Backpack"),
                "Backpack should remain in the cart after removing the other item.");
    }
}
