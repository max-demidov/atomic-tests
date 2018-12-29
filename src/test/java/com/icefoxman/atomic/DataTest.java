package com.icefoxman.atomic;

import com.icefoxman.atomic.data.csv.CsvReader;
import com.icefoxman.atomic.data.excel.ExcelReader;
import com.icefoxman.atomic.test.MultiSessionBrowserTest;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class DataTest extends MultiSessionBrowserTest {

    private static final CsvReader CSV_READER = new CsvReader("src/test/resources/data/data.csv");
    private static final ExcelReader EXCEL_READER = new ExcelReader("src/test/resources/data/data.xlsx",
            "Test data");

    @DataProvider(parallel = true)
    private static Iterator<Object[]> dataMap() {
        return CSV_READER.data().stream()
                .map(d -> new Object[]{d})
                .collect(Collectors.toList())
                .iterator();
    }

    @DataProvider(parallel = true)
    private static Iterator<Object[]> dataArgs() {
        return EXCEL_READER.data().stream()
                .filter(d -> d.values().stream().noneMatch(String::isEmpty)) // ignore inconsistent data
                .map(d -> new Object[]{d.get("Question"), d.get("Expected Answer")})
                .collect(Collectors.toList())
                .iterator();
    }

    @Test(description = "Check something with vary data", dataProvider = "dataMap", enabled = false)
    public void checkSomethingWithData(Map<String, String> data) {
        log.info("Test something with data {}", data);
    }

    @Test(description = "Check something with vary data", dataProvider = "dataArgs", enabled = false)
    public void checkSomethingWithData(String question, String answer) {
        log.info("Test something with Question [{}] and Answer [{}]", question, answer);
    }
}
