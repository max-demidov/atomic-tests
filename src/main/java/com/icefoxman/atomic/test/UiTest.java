package com.icefoxman.atomic.test;

import com.icefoxman.atomic.browser.Browser;
import com.icefoxman.atomic.env.Env;
import com.icefoxman.atomic.listener.ScreenshotListener;
import com.icefoxman.atomic.param.Param;
import com.icefoxman.atomic.param.Params;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Slf4j
@Listeners(ScreenshotListener.class)
public abstract class UiTest extends AbstractTest {

    @BeforeClass(description = "Open browser")
    protected void openBrowser() {
        Browser.start(this.getClass().getSimpleName());
    }

    @BeforeClass(description = "Open start page", dependsOnMethods = "openBrowser")
    protected void openStartPage() {
        String sEnv = Params.get(Param.ENV);
        log.info("Open start page of {} environment", sEnv);
        Env env = Env.valueOf(sEnv);
        Browser.open(env.url());
    }

    @AfterClass(description = "Close browser", alwaysRun = true)
    protected void closeBrowser() {
        Browser.quit();
    }
}
