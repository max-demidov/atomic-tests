package com.icefoxman.atomic.page.google;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultsPage extends StartPage {

    @FindBy(css = "#res h3")
    private List<WebElement> searchResultHeaders;

    public List<String> getSearchResultHeaders() {
        return searchResultHeaders.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
