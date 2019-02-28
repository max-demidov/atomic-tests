package com.icefoxman.atomic.page.google;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import com.icefoxman.atomic.page.AbstractPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class StartPage extends AbstractPage {

    @FindBy(name = "q")
    private WebElement searchInput;

    public StartPage() {
        waitUntilReadyStateToBeComplete();
        initElements();
    }

    public SearchResultsPage search(String request) {
        log.info("Search [{}] @ {}", request, this.getClass().getSimpleName());
        waiting().until(elementToBeClickable(searchInput));
        searchInput.sendKeys(request);
        searchInput.submit();
        return new SearchResultsPage();
    }
}
