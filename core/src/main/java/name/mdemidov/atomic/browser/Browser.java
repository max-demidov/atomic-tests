package name.mdemidov.atomic.browser;

import static org.openqa.selenium.logging.LogType.PERFORMANCE;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import name.mdemidov.atomic.param.Param;
import name.mdemidov.atomic.param.Params;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.logging.LogEntry;
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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Slf4j
public abstract class Browser {

    private static final int DEFAULT_WAIT_TIMEOUT = Integer.parseInt(Params.get(Param.DEFAULT_WAIT_TIMEOUT));
    private static final String SCREENSHOT_DIR = String.format("target%1$sscreenshot%1$s", File.separator);
    private static final DateFormat SCREENSHOT_FILENAME = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss.SSS");

    private static Name name;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    static {
        java.util.logging.Logger.getLogger("").setLevel(Level.OFF); // turns off logs of browser drivers
    }

    private Browser() {
    }

    public static Name name() {
        return name;
    }

    public static WebDriver driver() {
        return driver.get();
    }

    /**
     * Starts {@link Param#BROWSER_NAME} browser.
     *
     * @param testName is a name of test for Saucelabs
     */
    public static void start(String testName) {
        val browserName = Params.get(Param.BROWSER_NAME);
        log.info("Open {} browser for {}", browserName, testName);
        name = Name.valueOf(browserName);
        val webDriver = isRemote() ? startRemote(testName) : startLocal();
        driver.set(webDriver);
        updateTimeouts();
        if (!isMobile()) {
            resize(Params.get(Param.BROWSER_SIZE));
        }
    }

    /**
     * Quits browser if open.
     */
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

    public static void jsClick(WebElement element) {
        execute("arguments[0].click();", element);
    }

    public static void scrollTo(WebElement element) {
        execute("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Executes JavaScript with arguments in browser.
     *
     * @param js   - JavaScript code
     * @param args - Javascript arguments
     * @return Object that JavaScript returns
     */
    public static Object execute(String js, Object... args) {
        val executor = (JavascriptExecutor) driver();
        try {
            return executor.executeScript(js, args);
        } catch (Exception e) {
            log.error("Failed to execute JS", e);
            return null;
        }
    }

    public static WebDriverWait waiting() {
        return waiting(DEFAULT_WAIT_TIMEOUT);
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

    /**
     * Changes size of browser window.
     *
     * @param browserSize must match pattern <code>^\\d+x\\d+$</code>, e.g. 1920x1080
     */
    public static void resize(String browserSize) {
        log.debug("Change size of browser window to [{}]", browserSize);
        Param.BROWSER_SIZE.validate(browserSize);
        val widthHeight = browserSize.split("x");
        val width = Integer.parseInt(widthHeight[0]);
        val height = Integer.parseInt(widthHeight[1]);
        val dimension = new Dimension(width, height);
        driver().manage().window().setSize(dimension);
    }

    /**
     * Takes screenshot of a web page open in a browser.
     *
     * @return File of screenshot
     */
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

    public static void back() {
        log.info("Navigate back to the previous page");
        driver().navigate().back();
    }

    public static void switchToMainFrame() {
        log.trace("Switch to main frame");
        driver().switchTo().defaultContent();
    }

    private static WebDriver startLocal() {
        val msg = "Start local {} with {}";
        switch (name()) {
            case CHROME:
                val chromeOptions = Options.chrome();
                log.debug(msg, name() + printMobileDevice(), chromeOptions);
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

    private static RemoteWebDriver startRemote(String testName) {
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
        log.debug("Start remote {}{} with {}", name(), printMobileDevice(), caps);
        val url = buildSaucelabsUrl();
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

    private static void updateTimeouts() {
        if (name() == Name.FIREFOX) {
            return; // there is a bug with setting timeouts in FirefoxDriver
        }
        val timeout = Integer.parseInt(Params.get(Param.PAGE_LOAD_TIMEOUT));
        val timeouts = driver().manage().timeouts();
        timeouts.pageLoadTimeout(timeout, TimeUnit.SECONDS);
        timeouts.setScriptTimeout(timeout, TimeUnit.SECONDS);
        timeouts.implicitlyWait(0, TimeUnit.SECONDS);
    }

    /**
     * Provides performance logs of Chrome browser.
     *
     * @return List of log entries for Chrome or empty list for other browsers
     */
    public static List<LogEntry> getPerformanceLogs() {
        if (name() == Name.CHROME && driver() != null) {
            return driver().manage().logs().get(PERFORMANCE).getAll();
        } else {
            return Collections.emptyList();
        }
    }

    public static boolean isMobile() {
        val device = Params.get(Param.BROWSER_DEVICE);
        return (name() == Name.CHROME && !device.isEmpty());
    }

    private static String printMobileDevice() {
        val device = Params.get(Param.BROWSER_DEVICE).replace("_", " ");
        return isMobile() ? String.format(" (%s emulation)", device) : "";
    }
}
