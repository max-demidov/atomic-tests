package name.mdemidov.atomic.basetest;

import name.mdemidov.atomic.browser.Browser;
import name.mdemidov.atomic.listener.HttpListener;
import name.mdemidov.atomic.listener.SaucelabsListener;
import name.mdemidov.atomic.listener.ScreenshotListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;

@Listeners({ScreenshotListener.class, SaucelabsListener.class, HttpListener.class})
public abstract class MultiSessionBrowserTest extends AbstractTest {

    @BeforeMethod(description = "Open browser")
    protected void openBrowser(Method method) {
        Browser.start(this.getClass().getSimpleName() + "." + method.getName() + "()");
    }

    @AfterMethod(description = "Close browser",
        alwaysRun = true)
    protected void closeBrowser() {
        Browser.quit();
    }
}
