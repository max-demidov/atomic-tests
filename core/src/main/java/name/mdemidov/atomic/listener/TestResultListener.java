package name.mdemidov.atomic.listener;

import static org.testng.ITestResult.FAILURE;
import static org.testng.ITestResult.SKIP;
import static org.testng.ITestResult.SUCCESS;

import lombok.extern.slf4j.Slf4j;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

@Slf4j
public class TestResultListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // do nothing
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod()) {
            return;
        }

        if (testResult.getStatus() == SUCCESS) {
            log.info("TEST PASSED");
        } else if (testResult.getStatus() == FAILURE) {
            log.info("TEST FAILED");
        } else if (testResult.getStatus() == SKIP) {
            log.info("TEST SKIPPED");
        }
    }
}
