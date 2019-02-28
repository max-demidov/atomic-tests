package com.icefoxman.atomic.basetest;

import com.icefoxman.atomic.browser.Browser;
import com.icefoxman.atomic.listener.HttpListener;
import com.icefoxman.atomic.listener.SaucelabsListener;
import com.icefoxman.atomic.listener.ScreenshotListener;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Slf4j
@Listeners({ScreenshotListener.class, SaucelabsListener.class, HttpListener.class})
public abstract class SingleSessionBrowserTest extends AbstractTest {

    @BeforeClass(description = "Open browser")
    protected void openBrowser() {
        Browser.start(this.getClass().getSimpleName());
    }

    @BeforeClass(description = "Open start page",
        dependsOnMethods = "openBrowser")
    protected void openStartPage() {
        Browser.open(ENV.getStartUrl());
    }

    @AfterClass(description = "Close browser",
        alwaysRun = true)
    protected void closeBrowser() {
        Browser.quit();
    }
}
