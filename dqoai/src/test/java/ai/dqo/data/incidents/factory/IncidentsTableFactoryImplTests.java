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
package ai.dqo.data.incidents.factory;

import ai.dqo.BaseTest;
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
        Assertions.assertEquals(20, table.columnCount());

        int columnIndex = 0;
        Assertions.assertEquals(IncidentsColumnNames.ID_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.DATA_STREAM_NAME_COLUMN_NAME, table.column(columnIndex++).name());
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
        Assertions.assertEquals(IncidentsColumnNames.CREATED_BY_COLUMN_NAME, table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.RESOLVED_BY_COLUMN_NAME , table.column(columnIndex++).name());
        Assertions.assertEquals(IncidentsColumnNames.STATUS_COLUMN_NAME, table.column(columnIndex++).name());
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
