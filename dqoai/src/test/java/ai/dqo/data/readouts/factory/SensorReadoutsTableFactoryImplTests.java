/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.data.readouts.factory;

import ai.dqo.BaseTest;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
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

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
		this.sut = new SensorReadoutsTableFactoryImpl();
    }

    @Test
    void createEmptySensorReadoutsTable_whenCalled_thenCreatesTableWithSchema() {
        Table table = this.sut.createEmptySensorReadoutsTable("empty");
        Assertions.assertEquals(35, table.columnCount());

        int columnIndex = 0;
        Assertions.assertEquals(SensorReadoutsNormalizedResult.ID_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.EXPECTED_VALUE_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.TIME_GRADIENT_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "1", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "2", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "3", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "4", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "5", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "6", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "7", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "8", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "9", table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.DATA_STREAM_HASH_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.DATA_STREAM_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.CONNECTION_HASH_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.CONNECTION_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.PROVIDER_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.TABLE_HASH_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.SCHEMA_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.TABLE_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.TABLE_STAGE_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.COLUMN_HASH_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.COLUMN_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.CHECK_HASH_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.CHECK_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.CHECK_DISPLAY_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.CHECK_TYPE_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.CHECK_CATEGORY_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.QUALITY_DIMENSION_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.SENSOR_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.TIME_SERIES_ID_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.EXECUTED_AT_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(SensorReadoutsNormalizedResult.DURATION_MS_COLUMN_NAME, table.column(columnIndex++).name());
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
