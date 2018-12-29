package com.icefoxman.atomic.browser;

import com.icefoxman.atomic.param.Param;
import com.icefoxman.atomic.param.Params;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Slf4j
public abstract class Browser {

    private static final String SCREENSHOT_DIR = String.format("target%1$sscreenshot%1$s", File.separator);
    private static final DateFormat SCREENSHOT_FILENAME = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss.SSS");

    private static Name name;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    static {
        java.util.logging.Logger.getLogger("").setLevel(Level.OFF); // turns off logs of browser drivers
    }

    public static Name name() {
        return name;
    }

    public static WebDriver driver() {
        return driver.get();
    }

    public static void start(String testName) {
        val browserName = Params.get(Param.BROWSER_NAME);
        log.info("Open {} browser for {}", browserName, testName);
        name = Name.valueOf(browserName);
        val webDriver = isRemote() ? startRemote(testName) : startLocal();

        if (name != Name.FIREFOX) { // there is a bug in FirefoxDriver
            val timeout = Integer.parseInt(Params.get(Param.PAGE_LOAD_TIMEOUT));
            webDriver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
        }

        driver.set(webDriver);
        resize(Params.get(Param.BROWSER_SIZE));
    }

    public static void quit() {
        if (driver() == null) {
            return;
        }
        log.info("Close {} browser", name());
        driver().quit();
        driver.set(null);
    }

    public static void open(String url) {
        log.info("Open URL {}", url);
        driver().get(url);
    }

    public static Object execute(String js, Object... args) {
        val executor = (JavascriptExecutor) driver();
        try {
            return executor.executeScript(js, args);
        } catch (Exception e) {
            log.error("Failed to execute JS:\n{}", e.getMessage());
            return null;
        }
    }

    public static WebDriverWait waiting(int sec) {
        return new WebDriverWait(driver(), sec);
    }

    public static Actions actions() {
        return new Actions(driver());
    }

    public static boolean isRemote() {
        return (Params.get(Param.SAUCE_CREDS) != null);
    }

    public static void resize(String browserSize) {
        log.debug("Change size of browser window to [{}]", browserSize);
        Param.BROWSER_SIZE.validate(browserSize);
        val widthHeight = browserSize.split("x");
        val width = Integer.parseInt(widthHeight[0]);
        val height = Integer.parseInt(widthHeight[1]);
        val dimension = new Dimension(width, height);
        driver().manage().window().setSize(dimension);
    }

    public static File takeScreenshot() {
        if (driver() == null) {
            log.debug("Unable to take a screenshot when browser is not open");
            return null;
        }
        val date = Calendar.getInstance().getTime();
        val timestamp = SCREENSHOT_FILENAME.format(date);
        try {
            val dir = new File(SCREENSHOT_DIR);
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
            val screenshotFile = new File(String.format("%s%s.png", SCREENSHOT_DIR, timestamp));
            log.trace("Take screenshot {}", screenshotFile.getAbsolutePath());
            val srcFile = ((TakesScreenshot) driver()).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(srcFile, screenshotFile);
            return screenshotFile;
        } catch (Exception e) {
            log.warn("Failed to take a screenshot:\n{}", e.getMessage());
            return null;
        }
    }

    public static SessionId sessionId() {
        return (driver() == null ? null : ((RemoteWebDriver) driver()).getSessionId());
    }

    private static WebDriver startLocal() {
        val msg = "Start local {} with {}";
        switch (name()) {
            case CHROME:
                val chromeOptions = Options.chrome();
                log.debug(msg, name(), chromeOptions);
                return new ChromeDriver(chromeOptions);
            case FIREFOX:
                val firefoxOptions = Options.firefox();
                log.debug(msg, name(), firefoxOptions);
                return new FirefoxDriver(firefoxOptions);
            case EDGE:
                val edgeOptions = Options.edge();
                log.debug(msg, name(), edgeOptions);
                return new EdgeDriver(edgeOptions);
            case IE:
                val ieOptions = Options.ie();
                log.debug(msg, name(), ieOptions);
                return new InternetExplorerDriver(ieOptions);
            case SAFARI:
                val safariOptions = Options.safari();
                log.debug(msg, name(), safariOptions);
                return new SafariDriver(safariOptions);
            default:
                throw new IllegalArgumentException("Unsupported browser " + name());
        }
    }

    public static RemoteWebDriver startRemote(String testName) {
        val url = buildSaucelabsUrl();
        var caps = Options.saucelabs();
        switch (name()) {
            case CHROME:
                caps = caps.merge(Options.chrome());
                break;
            case FIREFOX:
                caps = caps.merge(Options.firefox());
                break;
            case EDGE:
                caps = caps.merge(Options.edge());
                break;
            case IE:
                caps = caps.merge(Options.ie());
                break;
            case SAFARI:
                caps = caps.merge(Options.safari());
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser " + name());

        }
        if (testName != null) {
            caps.setCapability("name", testName);
        }
        log.debug("Start remote {} with {}", name(), caps);
        val remote = new RemoteWebDriver(url, caps);
        log.debug("Saucelabs session: https://saucelabs.com/jobs/{}", remote.getSessionId());
        return remote;
    }

    private static URL buildSaucelabsUrl() {
        val sauceCreds = Params.get(Param.SAUCE_CREDS);
        val url = String.format("https://%s@ondemand.saucelabs.com:443/wd/hub/", sauceCreds);
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid Saucelabs URL " + url, e);
        }
    }
}
