package name.mdemidov.atomic.example.page.google;

import lombok.extern.slf4j.Slf4j;
import name.mdemidov.atomic.example.page.google.component.SearchResult;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SearchResultsPage extends StartPage {

    @FindBy(className = "rc")
    private List<SearchResult> searchResults;

    public List<String> getSearchResultHeaders() {
        waitUntilSearchResultsToBeDisplayed();
        return searchResults.stream().map(SearchResult::getHeaderText).collect(Collectors.toList());
    }

    public void printSearchResultHeaders() {
        log.info("Search results headers:");
        getSearchResultHeaders().forEach(log::info);
    }

    private void waitUntilSearchResultsToBeDisplayed() {
        waiting().withMessage("No search results found @ " + this.getClass().getSimpleName())
            .until((ExpectedCondition<Boolean>) c -> !searchResults.isEmpty());
    }
}
