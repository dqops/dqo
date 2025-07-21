/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.checkresults.factory;

import com.dqops.BaseTest;
import com.dqops.data.readouts.factory.SensorReadoutsTableFactoryImpl;
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
public class CheckResultsTableFactoryImplTests extends BaseTest {
    private CheckResultsTableFactoryImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = new CheckResultsTableFactoryImpl(new SensorReadoutsTableFactoryImpl());
    }

    @Test
    void createEmptyRuleResultsTable_whenCalled_thenCreatesTableWithRuleRelatedColumns() {
        Table table = this.sut.createEmptyCheckResultsTable("tab");
        Assertions.assertNotNull(table);
        Assertions.assertEquals(59, table.columnCount());
    }

    // This is a special test, it produces an empty rule results parquet file in the target/parquet-samples/rule-results-empty.parquet file
    @Test
    void createEmptyRuleResultsTable_whenEmptyTableWrittenAsParquet_thenWritesParquetFile() throws IOException {
        Table table = this.sut.createEmptyCheckResultsTable("empty");
        String mavenTargetFolderPath = System.getenv("DQO_TEST_TEMPORARY_FOLDER");
        Path parquetSamplesFolder = Path.of(mavenTargetFolderPath).resolve("parquet-samples");
        if( !parquetSamplesFolder.toFile().exists()) {
            Files.createDirectories(parquetSamplesFolder);
        }

        File targetParquetFile = parquetSamplesFolder.resolve("check-results-empty.parquet").toFile();

        TablesawParquetWriteOptions writeOptions = TablesawParquetWriteOptions
                .builder(targetParquetFile)
                .withOverwrite(true)
                .withCompressionCode(TablesawParquetWriteOptions.CompressionCodec.UNCOMPRESSED)
                .build();

        new TablesawParquetWriter().write(table, writeOptions);
    }
}
