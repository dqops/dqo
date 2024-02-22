package com.dqops.metadata.sources.fileformat;

import com.dqops.BaseTest;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnSpecMap;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.fileformat.json.JsonFormatType;
import com.dqops.metadata.sources.fileformat.json.JsonRecordsType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JsonFileFormatSpecTest extends BaseTest {

    @Test
    void buildSourceTableOptionsString_whenEachFieldsIsSet_fieldsArePresentInValidlyFormattedString() {
        JsonFileFormatSpec sut = new JsonFileFormatSpec(){{
            setAutoDetect(true);
            setCompression(CompressionType.gzip);
            setConvertStringsToIntegers(true);
            setDateformat("%m/%d/%Y");
            setFilename(true);
            setFormat(JsonFormatType.array);
            setHivePartitioning(true);
            setIgnoreErrors(true);
            setMaximumDepth(10L);
            setMaximumObjectSize(200L);
            setRecords(JsonRecordsType.auto);
            setTimestampformat("%A, %-d %B %Y - %I:%M:%S %p");
        }};

        ColumnSpec columnSpec = new ColumnSpec(){{
            setTypeSnapshot(ColumnTypeSnapshotSpec.fromType("INT"));
        }};
        ColumnSpec columnSpec2 = new ColumnSpec(){{
            setTypeSnapshot(ColumnTypeSnapshotSpec.fromType("STRING"));
        }};
        ColumnSpecMap columnSpecMap = new ColumnSpecMap(){{
            put("col1", columnSpec);
            put("col2", columnSpec2);
        }};
        TableSpec tableSpec = new TableSpec();
        tableSpec.setColumns(columnSpecMap);

        String output = sut.buildSourceTableOptionsString(List.of("/dev/table.json"), tableSpec);

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
        String output = sut.buildSourceTableOptionsString(List.of("/dev/table.json"), null);
        assertTrue(output.contains("read_json"));
    }

    @Test
    void deepClone_onInstanceWithAllFieldsSet_clones(){
        JsonFileFormatSpec sut = new JsonFileFormatSpec(){{
            setAutoDetect(true);
            setCompression(CompressionType.gzip);
            setConvertStringsToIntegers(true);
            setDateformat("%m/%d/%Y");
            setFilename(true);
            setFormat(JsonFormatType.array);
            setHivePartitioning(true);
            setIgnoreErrors(true);
            setMaximumDepth(10L);
            setMaximumObjectSize(200L);
            setRecords(JsonRecordsType.auto);
            setTimestampformat("%A, %-d %B %Y - %I:%M:%S %p");
        }};

        JsonFileFormatSpec sutCloned = sut.deepClone();
        assertNotNull(sutCloned.getAutoDetect());
        assertNotNull(sutCloned.getCompression());
        assertNotNull(sutCloned.getConvertStringsToIntegers());
        assertNotNull(sutCloned.getDateformat());
        assertNotNull(sutCloned.getFilename());
        assertNotNull(sutCloned.getFormat());
        assertNotNull(sutCloned.getHivePartitioning());
        assertNotNull(sutCloned.getIgnoreErrors());
        assertNotNull(sutCloned.getMaximumDepth());
        assertNotNull(sutCloned.getMaximumObjectSize());
        assertNotNull(sutCloned.getRecords());
        assertNotNull(sutCloned.getTimestampformat());
    }

}