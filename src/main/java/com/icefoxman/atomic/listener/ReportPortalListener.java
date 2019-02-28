package com.icefoxman.atomic.listener;

import com.epam.reportportal.testng.BaseTestNGListener;
import com.epam.reportportal.testng.ITestNGService;
import com.epam.reportportal.testng.TestNGService;
import lombok.val;
import org.testng.ITestResult;
import org.testng.internal.ConstructorOrMethod;
import rp.com.google.common.base.Supplier;
import rp.com.google.common.base.Suppliers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReportPortalListener extends BaseTestNGListener {

    private static final Supplier<ITestNGService> SERVICE = Suppliers.memoize(getService());
    private Map<ConstructorOrMethod, String> descriptions = new HashMap<>();

    public ReportPortalListener() {
        super(SERVICE.get());
    }

    private static Supplier<ITestNGService> getService() {
        return TestNGService::new;
    }

    @Override
    public void onTestStart(ITestResult testResult) {
        if (testResult.getParameters().length > 0) {
            addParametersToDescription(testResult);
        }
        super.onTestStart(testResult);
    }

    private synchronized void addParametersToDescription(ITestResult testResult) {
        val method = testResult.getMethod().getConstructorOrMethod();
        if (!descriptions.containsKey(method)) {
            descriptions.put(method, testResult.getMethod().getDescription());
        }
        val description = String.format("%s%n%s",
                                        Optional.ofNullable(descriptions.get(method)).orElse(""),
                                        Arrays.asList(testResult.getParameters()));
        testResult.getMethod().setDescription(description);
    }
}
