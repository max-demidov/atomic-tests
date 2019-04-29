package name.mdemidov.atomic.listener;

import static org.testng.ITestResult.FAILURE;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import name.mdemidov.atomic.browser.Browser;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

@Slf4j
public class ScreenshotListener implements IInvokedMethodListener {

    private static final String SCREENSHOT_MESSAGE = "Screenshot has been taken";

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // do nothing
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (testResult.getStatus() != FAILURE) {
            return;
        }
        val screenshotFile = Browser.takeScreenshot();
        if (screenshotFile != null) {
            log.info("RP_MESSAGE#FILE#{}#{}", screenshotFile, SCREENSHOT_MESSAGE);
        }
    }
}
