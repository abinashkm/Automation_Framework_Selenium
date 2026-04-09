package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    private ScreenshotUtil() {
    }

    // Saves a screenshot with a timestamp so failures from parallel runs do not overwrite each other.
    public static void captureScreenshot(WebDriver driver, String testName) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File temp = ts.getScreenshotAs(OutputType.FILE);
        File directory = new File("screenshots");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File src = new File(directory, testName + "_" + timeStamp + ".png");
        try {
            FileHandler.copy(temp, src);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save screenshot for failed test: " + testName, e);
        }
    }
}
