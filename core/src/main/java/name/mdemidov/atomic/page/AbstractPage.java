package name.mdemidov.atomic.page;

import lombok.val;
import name.mdemidov.atomic.browser.Browser;
import name.mdemidov.atomic.param.Param;
import name.mdemidov.atomic.param.Params;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

import java.util.function.Supplier;

public abstract class AbstractPage {

    protected AbstractPage() {
        waitUntilReadyStateToBeComplete();
        initElements();
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

    protected void initElements() {
        HtmlElementLoader.populatePageObject(this, Browser.driver());
    }

    protected WebDriverWait waiting() {
        return Browser.waiting();
    }

    protected WebDriverWait waiting(int sec) {
        return Browser.waiting(sec);
    }
}
