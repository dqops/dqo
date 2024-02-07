package com.dqops.metadata.sources.fileformat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class FileFormatSpecTest {

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