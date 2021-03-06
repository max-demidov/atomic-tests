package name.mdemidov.atomic.param;

import lombok.val;
import name.mdemidov.atomic.browser.Name;

public enum Param {

    BROWSER_NAME("browser.name", "CHROME", Name.pattern()),
    BROWSER_SIZE("browser.size", "1920x1080", "^\\d+x\\d+$"),
    BROWSER_DEVICE("browser.device", "", "^.*$"),
    DEFAULT_WAIT_TIMEOUT("defaultWaitTimeout", "30", "^\\d+$"),
    ENV("env", "", "^.*$"),
    LOG_LEVEL("log.level", "TRACE", "^(OFF|FATAL|ERROR|WARN|INFO|DEBUG|TRACE|ALL)$"),
    PAGE_LOAD_TIMEOUT("pageLoadTimeout", "60", "^\\d+$"),
    SAUCE_CREDS("sauceCreds", null, "^.+:.+S"),
    SAUCE_TUNNEL("sauceTunnel", "", "^.+$");

    private final String type;
    private final String defaultValue;
    private final String pattern;

    Param(String type, String defaultValue, String pattern) {
        this.type = type;
        this.defaultValue = defaultValue;
        this.pattern = pattern;
    }

    public String type() {
        return type;
    }

    public String defaultValue() {
        return defaultValue;
    }

    public void validate(String value) {
        if (!matches(value)) {
            val msg = String.format("Value of param [%s=%s] must match pattern [%s]", type, value, pattern);
            throw new IllegalArgumentException(msg);
        }
    }

    public boolean matches(String value) {
        return (value != null && value.matches(pattern));
    }
}
