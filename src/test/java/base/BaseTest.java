package base;

import listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.ConfigReader;
import utils.DriverFactory;

@Listeners(TestListener.class)
public class BaseTest {
    protected WebDriver driver;
    protected String baseUrl;

    // Creates a clean browser session for each test method so tests stay isolated and parallel-safe.
    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "baseUrl"})
    public void setup(@Optional String browser, @Optional String baseUrl) {
        String resolvedBrowser = browser != null ? browser : ConfigReader.getBrowser();
        String resolvedBaseUrl = baseUrl != null ? baseUrl : ConfigReader.getBaseUrl();

        DriverFactory.initializeDriver(resolvedBrowser);
        driver = DriverFactory.getDriver();
        this.baseUrl = resolvedBaseUrl;
    }

    // Closes only the current thread's driver after the test finishes.
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        DriverFactory.quitDriver();
    }

    // Gives child test classes access to the current thread's driver without sharing page object state.
    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    // Creates a fresh login page for the current test thread and opens the application.
    protected pages.LoginPage openLoginPage() {
        return new pages.LoginPage(getDriver()).open(baseUrl);
    }
}
