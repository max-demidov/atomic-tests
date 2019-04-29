package name.mdemidov.atomic.basetest;

import lombok.val;
import name.mdemidov.atomic.listener.TestResultListener;
import name.mdemidov.atomic.param.Param;
import name.mdemidov.atomic.param.Params;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Listeners;

@Listeners({TestResultListener.class/*, ReportPortalListener.class*/})
public abstract class AbstractTest extends MatcherAssert {

    static {
        val param = Params.get(Param.LOG_LEVEL);
        Logger.getRootLogger().setLevel(Level.toLevel(param));
        val implicitlyWaitTimeout = System.getProperty("webdriver.timeouts.implicitlywait", "0");
        System.setProperty("webdriver.timeouts.implicitlywait", implicitlyWaitTimeout);
    }
}
