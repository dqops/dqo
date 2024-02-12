package com.dqops.metadata.sources.fileformat;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvFileFormatSpecTest {

    @Test
    void buildSourceTableOptionsString_whenEachFieldsIsSet_fieldsArePresentInValidlyFormattedString() {
        CsvFileFormatSpec sut = new CsvFileFormatSpec(){{
            setAllVarchar(true);
            setAllowQuotedNulls(true);
            setAutoDetect(true);
            setColumns(Map.of("col1", "type1", "col2", "type2"));
            setCompression("gzip");
            setDateformat("%m/%d/%Y");
            setDecimalSeparator(".");
            setDelim(",");
            setEscape("'");
            setFilename(true);
            setHeader(true);
            setHivePartitioning(true);
            setIgnoreErrors(true);
            setNewLine("\n");
            setQuote("\"");
            setSkip(1L);
            setTimestampformat("%A, %-d %B %Y - %I:%M:%S %p");
        }};

        String output = sut.buildSourceTableOptionsString(List.of("/dev/table.parquet"));

        assertTrue(output.contains("all_varchar = true"));
        assertTrue(output.contains("allow_quoted_nulls = true"));
        assertTrue(output.contains("auto_detect = true"));
        assertTrue(output.contains("columns"));
        assertTrue(output.contains("compression = 'gzip'"));
        assertTrue(output.contains("dateformat = '%m/%d/%Y'"));
        assertTrue(output.contains("decimal_separator = '.'"));
        assertTrue(output.contains("delim = ','"));
        assertTrue(output.contains("escape = '''"));
        assertTrue(output.contains("filename = true"));
        assertTrue(output.contains("header = true"));
        assertTrue(output.contains("hive_partitioning = true"));
        assertTrue(output.contains("ignore_errors = true"));
        assertTrue(output.contains("new_line = '\n'"));
        assertTrue(output.contains("quote = '\"'"));
        assertTrue(output.contains("skip = 1"));
        assertTrue(output.contains("timestampformat = '%A, %-d %B %Y - %I:%M:%S %p'"));
    }

    @Test
    void buildSourceTableOptionsString_onCsvFile_useSuitableFunctionToReadCsv() {
        CsvFileFormatSpec sut = new CsvFileFormatSpec();
        String output = sut.buildSourceTableOptionsString(List.of("/dev/table.parquet"));
        assertTrue(output.contains("read_csv"));
    }

}