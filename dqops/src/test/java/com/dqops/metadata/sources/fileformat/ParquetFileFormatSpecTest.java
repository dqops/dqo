package com.dqops.metadata.sources.fileformat;

import com.dqops.BaseTest;
import com.dqops.metadata.sources.TableSpec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ParquetFileFormatSpecTest extends BaseTest {

    @Test
    void buildSourceTableOptionsString_whenEachFieldsIsSet_fieldsArePresentInValidlyFormattedString() {
        ParquetFileFormatSpec sut = new ParquetFileFormatSpec(){{
            setBinaryAsString(true);
            setFilename(true);
            setFileRowNumber(true);
            setHivePartitioning(true);
            setUnionByName(true);
        }};

        String output = sut.buildSourceTableOptionsString(List.of("/dev/table.csv"), null);

        assertTrue(output.contains("binary_as_string = true"));
        assertTrue(output.contains("filename = true"));
        assertTrue(output.contains("file_row_number = true"));
        assertTrue(output.contains("hive_partitioning = true"));
        assertTrue(output.contains("union_by_name = true"));
    }

    @Test
    void buildSourceTableOptionsString_onParquetFile_useSuitableFunctionToReadParquet() {
        ParquetFileFormatSpec sut = new ParquetFileFormatSpec();
        String output = sut.buildSourceTableOptionsString(List.of("/dev/table.parquet"), new TableSpec());
        assertTrue(output.contains("read_parquet"));
    }
}