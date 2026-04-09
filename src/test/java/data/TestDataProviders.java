package data;

import org.testng.annotations.DataProvider;

public class TestDataProviders {

    // Covers the common invalid login combinations the application should reject.
    @DataProvider(name = "invalidLoginUsers", parallel = true)
    public Object[][] invalidLoginUsers() {
        return new Object[][]{
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."},
                {"invalid_user", "secret_sauce", "Epic sadface: Username and password do not match any user in this service"},
                {"standard_user", "wrong_password", "Epic sadface: Username and password do not match any user in this service"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"}
        };
    }

    // Covers the required field validations for the checkout information form.
    @DataProvider(name = "invalidCheckoutData", parallel = true)
    public Object[][] invalidCheckoutData() {
        return new Object[][]{
                {"", "Patel", "751001", "Error: First Name is required"},
                {"Abinash", "", "751001", "Error: Last Name is required"},
                {"Abinash", "Patel", "", "Error: Postal Code is required"}
        };
    }
}
