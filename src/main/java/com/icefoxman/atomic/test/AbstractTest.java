package com.icefoxman.atomic.test;

import com.icefoxman.atomic.env.Env;
import com.icefoxman.atomic.param.Param;
import com.icefoxman.atomic.param.Params;
import org.hamcrest.MatcherAssert;

//@Listeners(ReportPortalListener.class)
abstract class AbstractTest extends MatcherAssert {

    protected static final Env ENV = Env.valueOf(Params.get(Param.ENV));
}
