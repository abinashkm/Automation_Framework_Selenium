package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.DriverFactory;
import utils.ScreenshotUtil;

public class TestListener implements ITestListener {

    // Captures a screenshot only when a test fails so debugging has useful evidence without extra noise.
    @Override
    public void onTestFailure(ITestResult result) {
        if (DriverFactory.getDriver() != null) {
            ScreenshotUtil.captureScreenshot(DriverFactory.getDriver(), result.getMethod().getMethodName());
        }
    }
}
