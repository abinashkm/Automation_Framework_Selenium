package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    // Builds one driver per thread so parallel TestNG runs do not step on each other.
    public static void initializeDriver(String browserName) {
        WebDriver driver = switch (browserName.toLowerCase()) {
            case "firefox" -> new FirefoxDriver(createFirefoxOptions());
            case "edge" -> new EdgeDriver(createEdgeOptions());
            default -> new ChromeDriver(createChromeOptions());
        };

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        DRIVER.set(driver);
    }

    // Gives the current test thread back its own browser instance.
    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    // Cleans up the current browser session without affecting other running tests.
    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }

    private static ChromeOptions createChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        return options;
    }

    private static FirefoxOptions createFirefoxOptions() {
        return new FirefoxOptions();
    }

    private static EdgeOptions createEdgeOptions() {
        return new EdgeOptions();
    }
}
