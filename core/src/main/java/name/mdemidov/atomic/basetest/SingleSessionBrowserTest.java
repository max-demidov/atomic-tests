package name.mdemidov.atomic.basetest;

import name.mdemidov.atomic.browser.Browser;
import name.mdemidov.atomic.listener.HttpListener;
import name.mdemidov.atomic.listener.SaucelabsListener;
import name.mdemidov.atomic.listener.ScreenshotListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners({ScreenshotListener.class, SaucelabsListener.class, HttpListener.class})
public abstract class SingleSessionBrowserTest extends AbstractTest {

    @BeforeClass(description = "Open browser")
    protected void openBrowser() {
        Browser.start(this.getClass().getSimpleName());
    }

    @AfterClass(description = "Close browser",
        alwaysRun = true)
    protected void closeBrowser() {
        Browser.quit();
    }
}
