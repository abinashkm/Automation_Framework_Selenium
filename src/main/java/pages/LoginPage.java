package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By userName = By.id("user-name");
    private final By passWord = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By realError = By.cssSelector("h3[data-test='error']");
    private final By loginContainer = By.className("login_container");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Opens the application landing page so every test starts from the same point.
    public LoginPage open(String baseUrl) {
        driver.get(baseUrl);
        waitForPageAnchor(userName);
        return this;
    }

    // Performs a successful login flow and returns the inventory page.
    public InventoryPage login(String username, String password) {
        type(userName, username);
        type(passWord, password);
        click(loginButton);
        return new InventoryPage(driver);
    }

    // Attempts login without assuming the outcome, which is useful for negative test cases.
    public void attemptLogin(String username, String password) {
        type(userName, username);
        type(passWord, password);
        click(loginButton);
    }

    // Returns the login error shown under the form.
    public String getErrorMessage() {
        return getText(realError);
    }

    // Checks whether the login form is visible after logout or at app start.
    public boolean isLoaded() {
        waitForUrlToContain("saucedemo.com");
        return isDisplayed(loginContainer) && isDisplayed(userName);
    }
}
