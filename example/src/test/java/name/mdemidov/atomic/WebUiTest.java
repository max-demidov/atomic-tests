package name.mdemidov.atomic;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.everyItem;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import name.mdemidov.atomic.example.basetest.ExampleSingleSessionBrowserTest;
import org.testng.annotations.Test;

import java.util.stream.Collectors;

@Slf4j
public class WebUiTest extends ExampleSingleSessionBrowserTest {

    @Test(description = "Check that Google shows relevant search results")
    public void checkThatGoogleShowsRelevantSearchResults() {
        val request = "icefox";
        val resultsPage = startPage.search(request);
        log.info("Check that every item in search results contains search request [{}]", request);
        resultsPage.printSearchResultHeaders();
        val results = resultsPage.getSearchResultHeaders()
            .stream().map(String::toLowerCase).collect(Collectors.toList());
        assertThat("Search results are irrelevant:", results, everyItem(containsString(request)));
    }
}
