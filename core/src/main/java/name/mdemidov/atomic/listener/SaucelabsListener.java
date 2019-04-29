package name.mdemidov.atomic.listener;

import lombok.extern.slf4j.Slf4j;
import name.mdemidov.atomic.browser.Browser;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

@Slf4j
public class SaucelabsListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // do nothing
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE && Browser.isRemote() && Browser.sessionId() != null) {
            log.debug("Saucelabs session: https://saucelabs.com/jobs/{}", Browser.sessionId());
        }
    }
}
