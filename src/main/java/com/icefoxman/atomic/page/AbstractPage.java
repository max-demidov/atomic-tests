package com.icefoxman.atomic.page;

import com.icefoxman.atomic.browser.Browser;
import com.icefoxman.atomic.param.Param;
import com.icefoxman.atomic.param.Params;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Supplier;

public abstract class AbstractPage {

    private final static int PAGE_LOAD_TIMEOUT = Integer.parseInt(Params.get(Param.PAGE_LOAD_TIMEOUT));
    protected final WebDriverWait wait5s = Browser.waiting(5);

    public AbstractPage() {
        initElements();
    }

    public void initElements() {
        waitUntilReadyStateToBeComplete();
        ElementLocatorFactory factory = new AjaxElementLocatorFactory(Browser.driver(), PAGE_LOAD_TIMEOUT);
        PageFactory.initElements(factory, this);
    }

    protected void waitUntilReadyStateToBeComplete() {
        final String js = "return document.readyState";
        Supplier<String> msg = () ->
                String.format("Page [%s] got stuck in loading state", Browser.driver().getCurrentUrl());
        Browser.waiting(PAGE_LOAD_TIMEOUT).withMessage(msg)
                .until((ExpectedCondition<Boolean>) c -> "complete".equals(Browser.execute(js)));
    }
}
