package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWaitSeconds()));
    }

    // Waits for an element to be visible before returning it to the caller.
    protected WebElement findVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Waits for an element to become clickable before interacting with it.
    protected WebElement findClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Sends text after clearing any existing value to keep field state predictable.
    protected void type(By locator, String value) {
        WebElement element = findClickable(locator);
        element.clear();
        element.sendKeys(value);
    }

    // Click helper keeps all click interactions consistent across the framework.
    protected void click(By locator) {
        findClickable(locator).click();
    }

    // Reads visible text in one place so page objects stay concise.
    protected String getText(By locator) {
        return findVisible(locator).getText().trim();
    }

    // Exposes a safe visibility check for assertions that do not need to fail early.
    protected boolean isDisplayed(By locator) {
        try {
            return findVisible(locator).isDisplayed();
        } catch (Exception exception) {
            return false;
        }
    }

    // Selects a dropdown option by visible label because that matches the user-facing behavior.
    protected void selectByVisibleText(By locator, String visibleText) {
        new Select(findVisible(locator)).selectByVisibleText(visibleText);
    }

    // Returns all matching visible elements when we need to inspect collections.
    protected List<WebElement> findVisibleElements(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElements(locator);
    }

    // Waits for the browser URL to contain the expected path before the next action starts.
    protected void waitForUrlToContain(String partialUrl) {
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    // Waits for one visible anchor element that tells us the destination page is ready.
    protected void waitForPageAnchor(By locator) {
        findClickable(locator);
    }
}
