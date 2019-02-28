package com.icefoxman.atomic.page;

import com.icefoxman.atomic.browser.Browser;
import com.icefoxman.atomic.param.Param;
import com.icefoxman.atomic.param.Params;
import lombok.val;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Supplier;

public abstract class AbstractPage {

    private static final int DEFAULT_WAIT_TIMEOUT = Integer.parseInt(Params.get(Param.DEFAULT_WAIT_TIMEOUT));

    protected void initElements() {
        val factory = new AjaxElementLocatorFactory(Browser.driver(), 0);
        PageFactory.initElements(factory, this);
    }

    protected void waitUntilReadyStateToBeComplete() {
        Browser.switchToMainFrame();
        val timeout = Integer.parseInt(Params.get(Param.PAGE_LOAD_TIMEOUT));
        val js = "return document.readyState";
        Supplier<String> msg = () ->
            String.format("Page [%s] got stuck in loading state", Browser.driver().getCurrentUrl());
        Browser.waiting(timeout).withMessage(msg)
            .until((ExpectedCondition<Boolean>) c -> "complete".equals(Browser.execute(js)));
    }

    protected WebDriverWait waiting() {
        return waiting(DEFAULT_WAIT_TIMEOUT);
    }

    protected WebDriverWait waiting(int sec) {
        return Browser.waiting(sec);
    }
}
