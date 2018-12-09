package com.icefoxman.atomic.page.google;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SearchResultsPage extends StartPage {

    @FindBy(css = "a > h3")
    private List<WebElement> searchResultHeaders;

    public List<String> getSearchResultHeaders() {
        return searchResultHeaders.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void printSearchResultHeaders() {
        log.info("Search results headers:");
        getSearchResultHeaders().forEach(log::info);
    }
}
