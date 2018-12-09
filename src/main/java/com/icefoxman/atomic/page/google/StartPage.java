package com.icefoxman.atomic.page.google;

import com.icefoxman.atomic.page.AbstractPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

@Slf4j
public class StartPage extends AbstractPage {

    @FindBy(name = "q")
    private WebElement searchInput;

    public SearchResultsPage search(String request) {
        log.info("Search [{}] @ {}", request, this.getClass().getSimpleName());
        wait5s.until(elementToBeClickable(searchInput));
        searchInput.sendKeys(request);
        searchInput.submit();
        return new SearchResultsPage();
    }
}
