/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.incidents.factory;

import com.dqops.BaseTest;
import net.tlabs.tablesaw.parquet.TablesawParquetWriteOptions;
import net.tlabs.tablesaw.parquet.TablesawParquetWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
public class IncidentsTableFactoryImplTests extends BaseTest {
    private IncidentsTableFactoryImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = new IncidentsTableFactoryImpl();
    }

    @Test
    void createEmptySensorReadoutsTable_whenCalled_thenCreatesTableWithSchema() {
        Table table = this.sut.createEmptyIncidentsTable("empty");
        Assertions.assertEquals(23, table.columnCount());

        int columnIndex = 0;
        Assertions.assertEquals(IncidentsColumnNames.ID_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.MINIMUM_SEVERITY_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.RESOLVED_BY_COLUMN_NAME , table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.STATUS_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.CREATED_AT_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.UPDATED_AT_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.CREATED_BY_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.UPDATED_BY_COLUMN_NAME, table.column(columnIndex++).name());
    }

    // This is a special test, it produces an empty incidents parquet file in the target/parquet-samples/incidents-empty.parquet file
    @Test
    void createEmptyIncidentsTable_whenEmptyTableWrittenAsParquet_thenWritesParquetFile() throws IOException {
        Table table = this.sut.createEmptyIncidentsTable("empty");
        String mavenTargetFolderPath = System.getenv("DQO_TEST_TEMPORARY_FOLDER");
        Path parquetSamplesFolder = Path.of(mavenTargetFolderPath).resolve("parquet-samples");
        if( !parquetSamplesFolder.toFile().exists()) {
            Files.createDirectories(parquetSamplesFolder);
        }

        File targetParquetFile = parquetSamplesFolder.resolve("incidents-empty.parquet").toFile();

        TablesawParquetWriteOptions writeOptions = TablesawParquetWriteOptions
                .builder(targetParquetFile)
                .withOverwrite(true)
                .withCompressionCode(TablesawParquetWriteOptions.CompressionCodec.UNCOMPRESSED)
                .build();

        new TablesawParquetWriter().write(table, writeOptions);
    }
}
