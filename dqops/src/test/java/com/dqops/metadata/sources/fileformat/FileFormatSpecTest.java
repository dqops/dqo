package com.dqops.metadata.sources.fileformat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileFormatSpecTest {

    @Test
    void buildTableOptionsString() {
        // todo: to test creation of all parameters for csv
        // todo: to test creation of all parameters for json
        // todo: to test creation of all parameters for parquet

    }

    @Test
    void formatFilePaths_forOneFile_formatsIt() {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("csv_file",
                List.of("file_one.csv"));

        String output = tableOptionsFormatter.toString();

        Assertions.assertEquals("""
                csv_file(
                  'file_one.csv'
                )""",
                output);
    }

}