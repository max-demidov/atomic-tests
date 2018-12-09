package com.icefoxman.atomic;

import com.icefoxman.atomic.page.google.SearchResultsPage;
import com.icefoxman.atomic.page.google.StartPage;
import com.icefoxman.atomic.test.UiTest;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.everyItem;

@Slf4j
public class DemoTest extends UiTest {

    @Test(description = "Check that Google shows relevant search results")
    public void checkThatGoogleShowsRelevantSearchResults() {
        final String request = "icefox";
        StartPage startPage = new StartPage();
        SearchResultsPage resultsPage = startPage.search(request);
        log.info("Check that every item in search results contains search request [{}]", request);
        resultsPage.printSearchResultHeaders();
        List<String> results = resultsPage.getSearchResultHeaders()
                .stream().map(String::toLowerCase).collect(Collectors.toList());
        assertThat("Search results are irrelevant:", results, everyItem(containsString(request)));
    }
}
