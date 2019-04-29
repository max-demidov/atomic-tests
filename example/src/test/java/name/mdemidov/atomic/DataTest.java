package name.mdemidov.atomic;

import lombok.extern.slf4j.Slf4j;
import name.mdemidov.atomic.basetest.AbstractTest;
import name.mdemidov.atomic.data.csv.CsvReader;
import name.mdemidov.atomic.data.excel.ExcelReader;
import name.mdemidov.atomic.data.json.JsonReader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
public class DataTest extends AbstractTest {

    private static final CsvReader CSV_READER = new CsvReader("src/test/resources/data/data.csv");
    private static final ExcelReader EXCEL_READER = new ExcelReader("src/test/resources/data/data.xlsx", "Test data");
    private static final JsonReader JSON_READER = new JsonReader("src/test/resources/data/data.json");

    @DataProvider(parallel = true)
    private static Iterator<Object[]> csvData() {
        return CSV_READER.data().stream().map(d -> new Object[]{d}).iterator();
    }

    @DataProvider(parallel = true)
    private static Iterator<Object[]> excelData() {
        return EXCEL_READER.data().stream()
            .filter(d -> d.values().stream().noneMatch(String::isEmpty)) // ignore inconsistent data
            .map(d -> new Object[]{d.get("Question"), d.get("Expected Answer")})
            .iterator();
    }

    @DataProvider(parallel = true)
    private static Iterator<Object[]> jsonData() {
        Map<String, List<String>> data = JSON_READER.getData();
        return data.entrySet().stream().map(d -> new Object[]{d.getKey(), d.getValue().toArray()}).iterator();
    }

    @Test(description = "Check something with vary data",
        dataProvider = "csvData",
        enabled = false)
    public void checkSomethingWithData(Map<String, String> data) {
        log.info("Test something with data {}", data);
    }

    @Test(description = "Check something with vary data",
        dataProvider = "excelData",
        enabled = false)
    public void checkSomethingWithData(String question, String answer) {
        log.info("Test something with Question [{}] and Answer [{}]", question, answer);
    }

    @Test(description = "Check something with vary data",
        dataProvider = "jsonData",
        enabled = false)
    public void checkSomethingWithData(String key, String[] values) {
        log.info("Test something with {}{}", key, Arrays.toString(values));
    }
}
