/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.data.readouts.factory;

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
public class SensorReadoutsTableFactoryImplTests extends BaseTest {
    private SensorReadoutsTableFactoryImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = new SensorReadoutsTableFactoryImpl();
    }

    @Test
    void createEmptySensorReadoutsTable_whenCalled_thenCreatesTableWithSchema() {
        Table table = this.sut.createEmptySensorReadoutsTable("empty");
        Assertions.assertEquals(45, table.columnCount());

        int columnIndex = 0;
        Assertions.assertEquals(SensorReadoutsColumnNames.ID_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "1", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "2", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "3", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "4", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "5", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "6", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "7", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "8", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "9", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.DATA_GROUP_HASH_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.DATA_GROUPING_CONFIGURATION_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.CONNECTION_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.PROVIDER_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.SCHEMA_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.TABLE_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.TABLE_NAME_PATTERN_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.TABLE_STAGE_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.TABLE_PRIORITY_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.COLUMN_HASH_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.COLUMN_NAME_PATTERN_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.QUALITY_DIMENSION_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.SENSOR_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.TIME_SERIES_ID_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsColumnNames.DURATION_MS_COLUMN_NAME, table.column(columnIndex++).name());
    }

    // This is a special test, it produces an empty sensor readouts parquet file in the target/parquet-samples/sensor-readouts-empty.parquet file
    @Test
    void createEmptySensorReadoutsTable_whenEmptyTableWrittenAsParquet_thenWritesParquetFile() throws IOException {
        Table table = this.sut.createEmptySensorReadoutsTable("empty");
        String mavenTargetFolderPath = System.getenv("DQO_TEST_TEMPORARY_FOLDER");
        Path parquetSamplesFolder = Path.of(mavenTargetFolderPath).resolve("parquet-samples");
        if( !parquetSamplesFolder.toFile().exists()) {
            Files.createDirectories(parquetSamplesFolder);
        }

        File targetParquetFile = parquetSamplesFolder.resolve("sensor-readouts-empty.parquet").toFile();

        TablesawParquetWriteOptions writeOptions = TablesawParquetWriteOptions
                .builder(targetParquetFile)
                .withOverwrite(true)
                .withCompressionCode(TablesawParquetWriteOptions.CompressionCodec.UNCOMPRESSED)
                .build();

        new TablesawParquetWriter().write(table, writeOptions);
    }
}
