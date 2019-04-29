package name.mdemidov.atomic.example.basetest;

import name.mdemidov.atomic.basetest.MultiSessionBrowserTest;
import name.mdemidov.atomic.browser.Browser;
import name.mdemidov.atomic.example.env.Env;
import name.mdemidov.atomic.param.Param;
import name.mdemidov.atomic.param.Params;
import org.testng.annotations.BeforeMethod;

public class ExampleMultiSessionBrowserTest extends MultiSessionBrowserTest {

    protected static final Env ENV = Env.valueOf(Params.get(Param.ENV));

    @BeforeMethod(description = "Open start page",
        dependsOnMethods = "openBrowser")
    protected void openStartPage() {
        Browser.open(ENV.getStartUrl());
    }
}
