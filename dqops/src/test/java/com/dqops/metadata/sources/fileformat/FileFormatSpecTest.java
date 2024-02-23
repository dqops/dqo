package com.dqops.metadata.sources.fileformat;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FileFormatSpecTest extends BaseTest {

    @Test
    void formatFilePaths_forOneFile_formatsIt() {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("csv_file",
                List.of("file_one.csv"));

        String output = tableOptionsFormatter.build();

        Assertions.assertEquals("""
                csv_file(
                  'file_one.csv'
                )""",
                output);
    }

}