package com.icefoxman.atomic.browser;

import com.icefoxman.atomic.param.Param;
import com.icefoxman.atomic.param.Params;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariOptions;

import static org.openqa.selenium.PageLoadStrategy.EAGER;
import static org.openqa.selenium.UnexpectedAlertBehaviour.DISMISS;
import static org.openqa.selenium.ie.InternetExplorerDriver.*;
import static org.openqa.selenium.remote.CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION;
import static org.openqa.selenium.remote.CapabilityType.HAS_NATIVE_EVENTS;
import static org.openqa.selenium.remote.CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR;

interface Options {

    static MutableCapabilities saucelabs() {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("version", "latest");
        caps.setCapability("idleTimeout", 300);
        caps.setCapability("screenResolution", Params.get(Param.BROWSER_SIZE));
        caps.setCapability("tunnelIdentifier", Params.get(Param.SAUCE_TUNNEL));
        return caps;
    }

    static ChromeOptions chrome() {
        ChromeOptions options = new ChromeOptions().addArguments(
                "silent",
                "test-type",
                "disable-extensions",
                "disable-infobars",
                "disable-plugins",
                "disable-print-preview");
        return options.merge(common());
    }

    static FirefoxOptions firefox() {
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("marionette", true);
        options.setPageLoadStrategy(EAGER);
        return options.merge(common());
    }

    static EdgeOptions edge() {
        EdgeOptions options = new EdgeOptions();
        options.setCapability(IE_ENSURE_CLEAN_SESSION, true);
        options.setCapability(IGNORE_ZOOM_SETTING, true);
        options.setCapability(INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        options.setCapability(REQUIRE_WINDOW_FOCUS, true);
        options.setCapability(ENABLE_PERSISTENT_HOVERING, false);
        return options.merge(common());
    }

    static InternetExplorerOptions ie() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability(IE_ENSURE_CLEAN_SESSION, true);
        options.setCapability(IGNORE_ZOOM_SETTING, true);
        options.setCapability(INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        options.setCapability(REQUIRE_WINDOW_FOCUS, true);
        options.setCapability(ENABLE_PERSISTENT_HOVERING, false);
        return options.merge(common());
    }

    static SafariOptions safari() {
        SafariOptions options = new SafariOptions();
        options.setCapability("seleniumVersion", "3.14.0_safarilegacy");
        options.setCapability("platform", "macOS 10.13");
        options.setCapability("screenResolution", "1920x1440");
        return options.merge(common());
    }

    static MutableCapabilities common() {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability(ENSURING_CLEAN_SESSION, true);
        caps.setCapability(HAS_NATIVE_EVENTS, false);
        caps.setCapability(UNEXPECTED_ALERT_BEHAVIOUR, DISMISS);
        return caps;
    }
}
