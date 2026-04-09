package tests;

import base.BaseTest;
import models.CheckoutData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutCompletePage;
import pages.CheckoutInformationPage;
import pages.CheckoutOverviewPage;
import pages.InventoryPage;
import pages.LoginPage;

public class CheckoutAndMenuTest extends BaseTest {

    @Test(
            description = "Users should be able to complete checkout for selected products.",
            groups = {"smoke", "regression", "happy-path"}
    )
    public void userCanCompleteCheckoutSuccessfully() {
        LoginPage loginPage = openLoginPage();
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.addProductToCart("Sauce Labs Bike Light");

        CartPage cartPage = inventoryPage.openCart();
        CheckoutInformationPage checkoutInformationPage = cartPage.clickCheckout();
        CheckoutOverviewPage checkoutOverviewPage = checkoutInformationPage.continueCheckout(
                new CheckoutData("Abinash", "Mohanty", "751001")
        );
        Assert.assertTrue(checkoutOverviewPage.isLoaded(), "Checkout overview should be visible before finishing.");

        CheckoutCompletePage checkoutCompletePage = checkoutOverviewPage.finishCheckout();
        Assert.assertEquals(checkoutCompletePage.getConfirmationMessage(), "Thank you for your order!",
                "Successful checkout should show a confirmation message.");
    }

    @Test(
            description = "Logout should end the session and return the user to the login page.",
            groups = {"smoke", "regression", "happy-path"}
    )
    public void userCanLogoutAndReturnToLoginPage() {
        LoginPage loginPage = openLoginPage();
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        LoginPage returnedLoginPage = inventoryPage.logout();

        Assert.assertTrue(returnedLoginPage.isLoaded(), "Login page should be visible after logout.");
        Assert.assertFalse(getDriver().getCurrentUrl().contains("inventory"),
                "User should no longer remain on the inventory page after logout.");
    }
}
