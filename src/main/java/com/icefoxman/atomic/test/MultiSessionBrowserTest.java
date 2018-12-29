package com.icefoxman.atomic.test;

import com.icefoxman.atomic.browser.Browser;
import com.icefoxman.atomic.listener.ScreenshotListener;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;

@Slf4j
@Listeners(ScreenshotListener.class)
public abstract class MultiSessionBrowserTest extends AbstractTest {

    @BeforeMethod(description = "Open browser")
    protected void openBrowser(Method method) {
        Browser.start(this.getClass().getSimpleName() + "." + method.getName() + "()");
    }

    @BeforeMethod(description = "Open start page", dependsOnMethods = "openBrowser")
    protected void openStartPage() {
        Browser.open(ENV.url());
    }

    @AfterMethod(description = "Close browser", alwaysRun = true)
    protected void closeBrowser() {
        Browser.quit();
    }
}
