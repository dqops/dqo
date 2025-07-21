/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errorsamples.factory;

import com.dqops.BaseTest;
import com.dqops.data.checkresults.factory.CheckResultsTableFactoryImpl;
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
public class ErrorSamplesTableFactoryImplTests extends BaseTest {
    private ErrorSamplesTableFactoryImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = new ErrorSamplesTableFactoryImpl();
    }

    @Test
    void createEmptyErrorSamplesTable_whenCalled_thenCreatesTableWithErrorSampleRelatedColumns() {
        Table table = this.sut.createEmptyErrorSamplesTable("tab");
        Assertions.assertNotNull(table);
        Assertions.assertEquals(57, table.columnCount());
    }

    // This is a special test, it produces an empty rule results parquet file in the target/parquet-samples/rule-results-empty.parquet file
    @Test
    void createEmptyErrorSamplesTable_whenEmptyTableWrittenAsParquet_thenWritesParquetFile() throws IOException {
        Table table = this.sut.createEmptyErrorSamplesTable("empty");
        String mavenTargetFolderPath = System.getenv("DQO_TEST_TEMPORARY_FOLDER");
        Path parquetSamplesFolder = Path.of(mavenTargetFolderPath).resolve("parquet-samples");
        if( !parquetSamplesFolder.toFile().exists()) {
            Files.createDirectories(parquetSamplesFolder);
        }

        File targetParquetFile = parquetSamplesFolder.resolve("error-samples-empty.parquet").toFile();

        TablesawParquetWriteOptions writeOptions = TablesawParquetWriteOptions
                .builder(targetParquetFile)
                .withOverwrite(true)
                .withCompressionCode(TablesawParquetWriteOptions.CompressionCodec.UNCOMPRESSED)
                .build();

        new TablesawParquetWriter().write(table, writeOptions);
    }
}
