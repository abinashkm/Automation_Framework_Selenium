package tests;

import base.BaseTest;
import data.TestDataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutInformationPage;
import pages.InventoryPage;
import pages.LoginPage;

public class NegativeFlowTest extends BaseTest {

    @Test(
            dataProvider = "invalidLoginUsers",
            dataProviderClass = TestDataProviders.class,
            description = "Invalid login combinations should show the correct error message.",
            groups = {"regression", "negative"}
    )
    public void invalidLoginShouldShowHelpfulError(String username, String password, String expectedMessage) {
        LoginPage loginPage = openLoginPage();
        loginPage.attemptLogin(username, password);

        Assert.assertEquals(loginPage.getErrorMessage(), expectedMessage,
                "Login error message should match the rejected scenario.");
    }

    @Test(
            dataProvider = "invalidCheckoutData",
            dataProviderClass = TestDataProviders.class,
            description = "Checkout form should block continuation when a required field is missing.",
            groups = {"regression", "negative"}
    )
    public void checkoutShouldValidateRequiredFields(
            String firstName,
            String lastName,
            String postalCode,
            String expectedMessage
    ) {
        LoginPage loginPage = openLoginPage();
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        CartPage cartPage = inventoryPage.openCart();
        CheckoutInformationPage checkoutInformationPage = cartPage.clickCheckout();
        checkoutInformationPage.submitCustomerInformation(firstName, lastName, postalCode);

        Assert.assertEquals(checkoutInformationPage.getErrorMessage(), expectedMessage,
                "Checkout validation should explain which field is missing.");
    }
}
