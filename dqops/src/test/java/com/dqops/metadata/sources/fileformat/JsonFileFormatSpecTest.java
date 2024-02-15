package com.dqops.metadata.sources.fileformat;

import com.dqops.BaseTest;
import com.dqops.metadata.sources.fileformat.json.JsonFormatType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JsonFileFormatSpecTest extends BaseTest {

    @Test
    void buildSourceTableOptionsString_whenEachFieldsIsSet_fieldsArePresentInValidlyFormattedString() {
        JsonFileFormatSpec sut = new JsonFileFormatSpec(){{
            setAutoDetect(true);
            setColumns(Map.of("col1", "type1", "col2", "type2"));
            setCompression(CompressionType.gzip);
            setConvertStringsToIntegers(true);
            setDateformat("%m/%d/%Y");
            setFilename(true);
            setFormat(JsonFormatType.array);
            setHivePartitioning(true);
            setIgnoreErrors(true);
            setMaximumDepth(BigInteger.TEN);
            setMaximumObjectSize(200L);
            setRecords("auto");
            setTimestampformat("%A, %-d %B %Y - %I:%M:%S %p");
        }};

        String output = sut.buildSourceTableOptionsString(List.of("/dev/table.json"));

        assertTrue(output.contains("auto_detect = true"));
        assertTrue(output.contains("columns"));
        assertTrue(output.contains("compression = 'gzip'"));
        assertTrue(output.contains("convert_strings_to_integers = true"));
        assertTrue(output.contains("dateformat = '%m/%d/%Y'"));
        assertTrue(output.contains("filename = true"));
        assertTrue(output.contains("format = 'array'"));
        assertTrue(output.contains("hive_partitioning = true"));
        assertTrue(output.contains("ignore_errors = true"));
        assertTrue(output.contains("maximum_depth = 10"));
        assertTrue(output.contains("maximum_object_size = 200"));
        assertTrue(output.contains("records = 'auto'"));
        assertTrue(output.contains("timestampformat = '%A, %-d %B %Y - %I:%M:%S %p'"));
    }

    @Test
    void buildSourceTableOptionsString_onJsonFile_useSuitableFunctionToReadJson() {
        JsonFileFormatSpec sut = new JsonFileFormatSpec();
        String output = sut.buildSourceTableOptionsString(List.of("/dev/table.json"));
        assertTrue(output.contains("read_json"));
    }

}