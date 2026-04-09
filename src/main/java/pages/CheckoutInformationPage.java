package pages;

import models.CheckoutData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutInformationPage extends BasePage {
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public CheckoutInformationPage(WebDriver driver) {
        super(driver);
    }

    // Confirms the checkout information step is fully ready for typing.
    public void waitUntilLoaded() {
        waitForUrlToContain("checkout-step-one");
        waitForPageAnchor(firstNameField);
    }

    // Fills the customer details form and advances to the overview page.
    public CheckoutOverviewPage continueCheckout(CheckoutData checkoutData) {
        waitUntilLoaded();
        type(firstNameField, checkoutData.firstName());
        type(lastNameField, checkoutData.lastName());
        type(postalCodeField, checkoutData.postalCode());
        click(continueButton);
        CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage(driver);
        checkoutOverviewPage.waitUntilLoaded();
        return checkoutOverviewPage;
    }

    // Submits whatever values are present so the tests can validate form errors.
    public void submitCustomerInformation(String firstName, String lastName, String postalCode) {
        waitUntilLoaded();
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(postalCodeField, postalCode);
        click(continueButton);
    }

    // Reads the inline validation message shown by the form.
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    // Lets the user back out of checkout to the cart page.
    public CartPage clickCancel() {
        click(cancelButton);
        return new CartPage(driver);
    }
}
