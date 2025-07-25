/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.sources.fileformat;

import com.dqops.BaseTest;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnSpecMap;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.fileformat.csv.CsvFileFormatSpec;
import com.dqops.metadata.sources.fileformat.csv.NewLineCharacterType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CsvFileFormatSpecTest extends BaseTest {

    @Test
    void buildSourceTableOptionsString_whenEachFieldsIsSet_fieldsArePresentInValidlyFormattedString() {
        CsvFileFormatSpec sut = new CsvFileFormatSpec(){{
            setAllVarchar(true);
            setAllowQuotedNulls(true);
            setAutoDetect(true);
            setCompression(CompressionType.gzip);
            setDateformat("%m/%d/%Y");
            setDecimalSeparator(".");
            setDelim(",");
            setEscape("'");
            setFilename(true);
            setHeader(true);
            setHivePartitioning(true);
            setIgnoreErrors(true);
            setNewLine(NewLineCharacterType.lf);
            setQuote("\"");
            setSkip(1L);
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

        String output = sut.buildSourceTableOptionsString(List.of("/dev/table.parquet"), tableSpec);

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
        assertTrue(output.contains("new_line = '\\n'"));
        assertTrue(output.contains("quote = '\"'"));
        assertTrue(output.contains("skip = 1"));
        assertTrue(output.contains("timestampformat = '%A, %-d %B %Y - %I:%M:%S %p'"));
    }

    @Test
    void buildSourceTableOptionsString_onCsvFile_useSuitableFunctionToReadCsv() {
        CsvFileFormatSpec sut = new CsvFileFormatSpec();
        String output = sut.buildSourceTableOptionsString(List.of("/dev/table.parquet"), new TableSpec());
        assertTrue(output.contains("read_csv"));
    }

}