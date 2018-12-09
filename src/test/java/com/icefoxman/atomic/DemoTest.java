package com.icefoxman.atomic;

import com.icefoxman.atomic.page.google.SearchResultsPage;
import com.icefoxman.atomic.page.google.StartPage;
import com.icefoxman.atomic.test.UiTest;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;

@Slf4j
public class DemoTest extends UiTest {

    @Test(description = "Check that Google shows relevant search results")
    public void checkThatGoogleShowsRelevantSearchResults() {
        final String request = "icefox";
        StartPage startPage = new StartPage();
        SearchResultsPage resultsPage = startPage.search(request);
        List<String> results = resultsPage.getSearchResultHeaders();
        assertThat("Search results are irrelevant:", results, everyItem(equalTo(request)));
    }
}
