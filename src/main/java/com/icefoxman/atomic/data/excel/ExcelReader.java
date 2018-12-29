package com.icefoxman.atomic.data.excel;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.util.*;

@Slf4j
public class ExcelReader {

    private static final DataFormatter FORMATTER = new DataFormatter();

    private Sheet sheet;
    private List<String> header = new ArrayList<>();
    private List<List<String>> rows = new ArrayList<>();

    public ExcelReader(String fileName, String sheetName) {
        log.debug("Read data from [{}] sheet of file [{}]", sheetName, fileName);
        Workbook workbook;
        try {
            val file = new File(fileName);
            workbook = WorkbookFactory.create(file);
            workbook.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to read file " + fileName, e);
        }

        sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            throw new IllegalArgumentException(sheetName + " sheet not found in file " + fileName);
        }
    }

    public List<Map<String, String>> data() {
        val header = header();
        val rows = rows();

        if (CollectionUtils.isEmpty(rows)) {
            return Collections.emptyList();
        }

        val data = new ArrayList<Map<String, String>>();

        for (val row : rows) {
            val rowData = new HashMap<String, String>();
            for (var i = 0; i < header.size(); i++) {
                rowData.put(header.get(i), row.get(i));
            }
            data.add(rowData);
        }

        return data;
    }

    private List<String> header() {
        if (header.isEmpty()) {
            header = readHeader();
        }
        return header;
    }

    private List<List<String>> rows() {
        if (rows.isEmpty()) {
            rows = readRows();
        }
        return rows;
    }

    private List<String> readHeader() {
        val header = new ArrayList<String>();
        val headerRow = sheet.getRow(0);
        headerRow.forEach(cell -> header.add(FORMATTER.formatCellValue(cell)));
        return header;
    }

    private List<List<String>> readRows() {
        val rows = new ArrayList<List<String>>();
        sheet.forEach(row -> {
            val rowData = new ArrayList<String>();
            row.forEach(cell -> rowData.add(FORMATTER.formatCellValue(cell)));
            rows.add(rowData);
        });
        rows.remove(0); // header row
        return rows;
    }
}
