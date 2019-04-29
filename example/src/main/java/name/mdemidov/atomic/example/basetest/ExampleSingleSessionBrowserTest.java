package name.mdemidov.atomic.example.basetest;

import name.mdemidov.atomic.basetest.SingleSessionBrowserTest;
import name.mdemidov.atomic.browser.Browser;
import name.mdemidov.atomic.example.env.Env;
import name.mdemidov.atomic.example.page.google.StartPage;
import name.mdemidov.atomic.param.Param;
import name.mdemidov.atomic.param.Params;
import org.testng.annotations.BeforeClass;

public class ExampleSingleSessionBrowserTest extends SingleSessionBrowserTest {

    protected static final Env ENV = Env.valueOf(Params.get(Param.ENV));

    protected StartPage startPage;

    @BeforeClass(description = "Open start page",
        dependsOnMethods = "openBrowser")
    protected void openStartPage() {
        Browser.open(ENV.getStartUrl());
        startPage = new StartPage();
    }
}
