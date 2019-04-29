package name.mdemidov.atomic.example.page.google;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import lombok.extern.slf4j.Slf4j;
import name.mdemidov.atomic.page.AbstractPage;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.TextInput;

@Slf4j
public class StartPage extends AbstractPage {

    @FindBy(name = "q")
    private TextInput searchInput;

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
