package com.icefoxman.atomic.basetest;

import com.icefoxman.atomic.env.Env;
import com.icefoxman.atomic.listener.TestResultListener;
import com.icefoxman.atomic.param.Param;
import com.icefoxman.atomic.param.Params;
import lombok.val;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Listeners;

@Listeners({TestResultListener.class/*, ReportPortalListener.class*/})
abstract class AbstractTest extends MatcherAssert {

    protected static final Env ENV = Env.valueOf(Params.get(Param.ENV));

    static {
        val param = Params.get(Param.LOG_LEVEL);
        Logger.getRootLogger().setLevel(Level.toLevel(param));
    }
}
