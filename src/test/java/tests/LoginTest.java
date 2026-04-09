package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(
            description = "Valid users should be able to log in and land on the inventory page.",
            groups = {"smoke", "regression", "happy-path"}
    )
    public void validLoginShouldNavigateToInventoryPage() {
        LoginPage loginPage = openLoginPage();
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page should be visible after login.");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"), "URL should point to the inventory page.");
    }
}
