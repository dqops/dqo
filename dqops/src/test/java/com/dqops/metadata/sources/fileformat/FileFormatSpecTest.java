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
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.config.DuckdbFilesFormatType;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.fileformat.csv.CsvFileFormatSpec;
import com.dqops.metadata.sources.fileformat.json.JsonFileFormatSpec;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FileFormatSpecTest extends BaseTest {

    private FileFormatSpec sut;

    @BeforeEach
    void setUp() {
        this.sut = new FileFormatSpec();
    }

    @Test
    void buildTableOptionsString_whenSourceFilesTypeIsNull_throwsException() {
        DuckdbParametersSpec duckdb = new DuckdbParametersSpec();

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(()->{
                    sut.buildTableOptionsString(duckdb, null);
                });
    }

    @Test
    void buildTableOptionsString_whenSourceFilesTypeIsCsv_callsCsvDuckdbReadMethod() {
        this.sut.setCsv(new CsvFileFormatSpec());
        this.sut.getFilePaths().add("/a/file/path");
        DuckdbParametersSpec duckdb = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv).getDuckdb();
        TableSpec tableSpec = new TableSpec();
        tableSpec.getColumns().put("col1", new ColumnSpec(){{
            setTypeSnapshot(new ColumnTypeSnapshotSpec("STRING"));
        }});

        String output = this.sut.buildTableOptionsString(duckdb, tableSpec);

        assertTrue(output.contains("read_csv"));
    }

    @Test
    void buildTableOptionsString_whenSourceFilesTypeIsJson_callsJsonDuckdbReadMethod() {
        this.sut.setJson(new JsonFileFormatSpec());
        this.sut.getFilePaths().add("/a/file/path");
        DuckdbParametersSpec duckdb = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.json).getDuckdb();
        TableSpec tableSpec = new TableSpec();
        tableSpec.getColumns().put("col1", new ColumnSpec(){{
            setTypeSnapshot(new ColumnTypeSnapshotSpec("STRING"));
        }});

        String output = this.sut.buildTableOptionsString(duckdb, tableSpec);

        assertTrue(output.contains("read_json"));
    }

    @Test
    void buildTableOptionsString_whenSourceFilesTypeIsParquet_callsParquetDuckdbReadMethod() {
        this.sut.setParquet(new ParquetFileFormatSpec());
        this.sut.getFilePaths().add("/a/file/path");
        DuckdbParametersSpec duckdb = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.parquet).getDuckdb();
        TableSpec tableSpec = new TableSpec();
        tableSpec.getColumns().put("col1", new ColumnSpec(){{
            setTypeSnapshot(new ColumnTypeSnapshotSpec("STRING"));
        }});

        String output = this.sut.buildTableOptionsString(duckdb, tableSpec);

        assertTrue(output.contains("read_parquet"));
    }

    @Test
    void isSetHivePartitioning_hivePartitioningIsNull_returnsFalse() {
        this.sut.setCsv(new CsvFileFormatSpec());
        assert this.sut.isFormatSetForType(DuckdbFilesFormatType.csv);

        assertFalse(this.sut.isSetHivePartitioning(DuckdbFilesFormatType.csv));
    }
}