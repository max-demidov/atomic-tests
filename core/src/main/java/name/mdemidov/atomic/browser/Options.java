package name.mdemidov.atomic.browser;

import static org.openqa.selenium.PageLoadStrategy.EAGER;
import static org.openqa.selenium.PageLoadStrategy.NONE;
import static org.openqa.selenium.UnexpectedAlertBehaviour.DISMISS;
import static org.openqa.selenium.ie.InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING;
import static org.openqa.selenium.ie.InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION;
import static org.openqa.selenium.ie.InternetExplorerDriver.IGNORE_ZOOM_SETTING;
import static org.openqa.selenium.ie.InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS;
import static org.openqa.selenium.ie.InternetExplorerDriver.REQUIRE_WINDOW_FOCUS;
import static org.openqa.selenium.logging.LogType.PERFORMANCE;
import static org.openqa.selenium.remote.CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION;
import static org.openqa.selenium.remote.CapabilityType.HAS_NATIVE_EVENTS;
import static org.openqa.selenium.remote.CapabilityType.LOGGING_PREFS;
import static org.openqa.selenium.remote.CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR;

import lombok.val;
import name.mdemidov.atomic.param.Param;
import name.mdemidov.atomic.param.Params;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;
import java.util.logging.Level;

interface Options {

    static MutableCapabilities saucelabs() {
        val caps = new MutableCapabilities();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("version", "latest");
        caps.setCapability("idleTimeout", 300);
        caps.setCapability("screenResolution", Params.get(Param.BROWSER_SIZE));
        caps.setCapability("tunnelIdentifier", Params.get(Param.SAUCE_TUNNEL));
        return caps;
    }

    static ChromeOptions chrome() {
        val options = new ChromeOptions().addArguments(
            "silent",
            "test-type",
            "disable-extensions",
            "disable-infobars",
            "disable-plugins",
            "disable-print-preview");

        val logPrefs = new LoggingPreferences();
        logPrefs.enable(PERFORMANCE, Level.INFO);
        options.setCapability(LOGGING_PREFS, logPrefs);

        val device = Params.get(Param.BROWSER_DEVICE).replace("_", " ");
        if (!device.isEmpty()) {
            val mobileEmulation = new HashMap<String, String>();
            mobileEmulation.put("deviceName", device);
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
        }

        return options.merge(common());
    }

    static FirefoxOptions firefox() {
        val options = new FirefoxOptions();
        options.setCapability("marionette", true);
        options.setPageLoadStrategy(EAGER);
        return options.merge(common());
    }

    static EdgeOptions edge() {
        val options = new EdgeOptions();
        options.setCapability(IE_ENSURE_CLEAN_SESSION, true);
        options.setCapability(IGNORE_ZOOM_SETTING, true);
        options.setCapability(INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        options.setCapability(REQUIRE_WINDOW_FOCUS, true);
        options.setCapability(ENABLE_PERSISTENT_HOVERING, false);
        return options.merge(common());
    }

    static InternetExplorerOptions ie() {
        val options = new InternetExplorerOptions();
        options.setCapability(IE_ENSURE_CLEAN_SESSION, true);
        options.setCapability(IGNORE_ZOOM_SETTING, true);
        options.setCapability(INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        options.setCapability(REQUIRE_WINDOW_FOCUS, true);
        options.setCapability(ENABLE_PERSISTENT_HOVERING, false);
        options.setPageLoadStrategy(NONE);
        return options.merge(common());
    }

    static SafariOptions safari() {
        val options = new SafariOptions();
        options.setCapability("seleniumVersion", "3.14.0_safarilegacy");
        options.setCapability("platform", "macOS 10.13");
        options.setCapability("screenResolution", "1920x1440");
        return options.merge(common());
    }

    static DesiredCapabilities iphoneXsSafari() {
        val caps = DesiredCapabilities.iphone();
        caps.setCapability("appiumVersion", "1.9.1");
        caps.setCapability("deviceName", "iPhone XS Simulator");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("platformVersion", "12.0");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("browserName", "Safari");
        return caps.merge(common());
    }

    static DesiredCapabilities galaxyS9PlusChrome() {
        val caps = DesiredCapabilities.android();
        caps.setCapability("appiumVersion", "1.9.1");
        caps.setCapability("deviceName", "Samsung Galaxy S9 Plus WQHD GoogleAPI Emulator");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("platformVersion", "8.1");
        caps.setCapability("platformName", "Android");
        caps.setCapability("browserName", "Chrome");
        return caps.merge(common());
    }

    static MutableCapabilities common() {
        val caps = new MutableCapabilities();
        caps.setCapability(ENSURING_CLEAN_SESSION, true);
        caps.setCapability(HAS_NATIVE_EVENTS, false);
        caps.setCapability(UNEXPECTED_ALERT_BEHAVIOUR, DISMISS);
        return caps;
    }
}
