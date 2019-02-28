package com.icefoxman.atomic;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.everyItem;

import com.icefoxman.atomic.basetest.SingleSessionBrowserTest;
import com.icefoxman.atomic.page.google.StartPage;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.testng.annotations.Test;

import java.util.stream.Collectors;

@Slf4j
public class DemoTest extends SingleSessionBrowserTest {

    @Test(description = "Check that Google shows relevant search results")
    public void checkThatGoogleShowsRelevantSearchResults() {
        val request = "icefox";
        val startPage = new StartPage();
        val resultsPage = startPage.search(request);
        log.info("Check that every item in search results contains search request [{}]", request);
        resultsPage.printSearchResultHeaders();
        val results = resultsPage.getSearchResultHeaders()
            .stream().map(String::toLowerCase).collect(Collectors.toList());
        assertThat("Search results are irrelevant:", results, everyItem(containsString(request)));
    }
}
