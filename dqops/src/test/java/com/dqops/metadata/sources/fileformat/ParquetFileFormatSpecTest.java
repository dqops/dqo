package com.dqops.metadata.sources.fileformat;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParquetFileFormatSpecTest {

    @Test
    void buildSourceTableOptionsString_whenEachFieldsIsSet_fieldsArePresentInValidlyFormattedString() {
        ParquetFileFormatSpec sut = new ParquetFileFormatSpec(){{
            setBinaryAsString(true);
            setFilename(true);
            setFileRowNumber(true);
            setHivePartitioning(true);
        }};

        String output = sut.buildSourceTableOptionsString(List.of("/dev/table.csv"));

        assertTrue(output.contains("binary_as_string = true"));
        assertTrue(output.contains("filename = true"));
        assertTrue(output.contains("file_row_number = true"));
        assertTrue(output.contains("hive_partitioning = true"));
    }

    @Test
    void buildSourceTableOptionsString_onParquetFile_useSuitableFunctionToReadParquet() {
        ParquetFileFormatSpec sut = new ParquetFileFormatSpec();
        String output = sut.buildSourceTableOptionsString(List.of("/dev/table.parquet"));
        assertTrue(output.contains("read_parquet"));
    }
}