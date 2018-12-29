package com.icefoxman.atomic.listener;

import com.icefoxman.atomic.browser.Browser;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

@Slf4j
public class ScreenshotListener implements IInvokedMethodListener {

    private static final String SCREENSHOT_MESSAGE = "Screenshot has been taken";

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult result) {
        // do nothing
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        if (result.getStatus() != ITestResult.FAILURE) {
            return;
        }
        if (Browser.isRemote() && Browser.sessionId() != null) {
            log.debug("Saucelabs session: https://saucelabs.com/jobs/{}", Browser.sessionId());
        }
        val screenshotFile = Browser.takeScreenshot();
        if (screenshotFile != null) {
            log.info("RP_MESSAGE#FILE#{}#{}", screenshotFile, SCREENSHOT_MESSAGE);
        }
    }
}
