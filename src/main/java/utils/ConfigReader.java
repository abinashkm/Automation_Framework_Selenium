package utils;

public final class ConfigReader {
    private static final String DEFAULT_BASE_URL = "https://www.saucedemo.com/";
    private static final String DEFAULT_BROWSER = "chrome";
    private static final String DEFAULT_TIMEOUT = "10";

    private ConfigReader() {
    }

    // Reads the base URL from system properties so the suite can point to another environment later.
    public static String getBaseUrl() {
        return System.getProperty("base.url", DEFAULT_BASE_URL);
    }

    // Reads the requested browser and falls back to Chrome because it is the safest default here.
    public static String getBrowser() {
        return System.getProperty("browser", DEFAULT_BROWSER).toLowerCase();
    }

    // Central timeout value keeps the waits consistent across page objects.
    public static int getExplicitWaitSeconds() {
        return Integer.parseInt(System.getProperty("explicit.wait.seconds", DEFAULT_TIMEOUT));
    }
}
