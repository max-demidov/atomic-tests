package com.icefoxman.atomic.data.csv;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.apache.commons.collections.CollectionUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
public class CsvReader {
    private static final char SEPARATOR = '\t';
    private static final CSVParser PARSER = new CSVParserBuilder().withSeparator(SEPARATOR).build();

    private String filename;
    private String[] header;
    private List<String[]> rows = new ArrayList<>();

    public CsvReader(String filename) {
        this.filename = filename;
    }

    public List<Map<String, String>> data() {
        log.debug("Read data from [{}] file", filename);
        String[] header;
        List<String[]> rows;

        try {
            header = header();
            rows = rows();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read data from CSV file " + filename, e);
        }

        if (CollectionUtils.isEmpty(rows)) {
            return Collections.emptyList();
        }

        if (rows.get(0).length != header.length) {
            val msg = String.format("Number of headers [%d] != [%d] Number of data columns",
                    header.length, rows.get(0).length);
            throw new IllegalArgumentException(msg);
        }

        val data = new ArrayList<Map<String, String>>();

        for (String[] row : rows) {
            val rowData = new HashMap<String, String>();
            for (var i = 0; i < header.length; i++) {
                rowData.put(header[i], row[i]);
            }
            data.add(rowData);
        }

        return data;
    }

    private String[] header() throws IOException {
        if (header == null || header.length < 1) {
            header = readHeader();
        }
        return header;
    }

    private List<String[]> rows() throws IOException {
        if (rows.isEmpty()) {
            rows = readRows();
        }
        return rows;
    }

    private String[] readHeader() throws IOException {
        @Cleanup
        val reader = new CSVReaderBuilder(new FileReader(filename))
                .withCSVParser(PARSER)
                .build();
        return reader.readNext();
    }

    private List<String[]> readRows() throws IOException {
        @Cleanup
        val reader = new CSVReaderBuilder(new FileReader(filename))
                .withCSVParser(PARSER)
                .withSkipLines(1)
                .build();
        return reader.readAll();
    }
}
