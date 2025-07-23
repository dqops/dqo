/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sampledata.files;

import com.dqops.BaseTest;
import com.dqops.sampledata.SampleCsvFileNames;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;

/**
 * Unit tests for the {@link TestDataSampleFilesObjectMother}
 */
@SpringBootTest
public class CsvSampleFilesTests extends BaseTest {
    @Test
    void loadTableCsv_whenLoaded_continuous_days_one_row_per_day_thenFileParsed() {
        TestDataSampleFileContent csvSampleFileContent = TestDataSampleFilesObjectMother.loadTableCsv(SampleCsvFileNames.continuous_days_one_row_per_day);
        Table table = csvSampleFileContent.getTable();
        Assertions.assertEquals("continuous_days_one_row_per_day", table.name());
        Assertions.assertEquals(24, table.rowCount());

        Assertions.assertEquals("id", table.column(0).name());
        Assertions.assertEquals(ColumnType.INTEGER, table.column(0).type());

        Assertions.assertEquals("date", table.column(1).name());
        Assertions.assertEquals(ColumnType.LOCAL_DATE, table.column(1).type());
    }

    @Test
    void getSampleTable_whenCalled_thenReturnsTableHashed() {
        SampleTableFromTestDataFile sampleTable = TestDataSampleFilesObjectMother.getSampleTableFromCsv(SampleCsvFileNames.continuous_days_one_row_per_day);
        Assertions.assertNotNull(sampleTable);
        Assertions.assertEquals(24, sampleTable.getTable().rowCount());
        Assertions.assertEquals("continuous_days_one_row_per_day_" + sampleTable.getHash(), sampleTable.getHashedTableName());
    }

    @Test
    void getSampleTable_whenCalledTwice_thenReturnsTheSameCachedTable() {
        SampleTableFromTestDataFile sampleTable = TestDataSampleFilesObjectMother.getSampleTableFromCsv(SampleCsvFileNames.continuous_days_one_row_per_day);
        Assertions.assertNotNull(sampleTable);
        Assertions.assertSame(sampleTable, TestDataSampleFilesObjectMother.getSampleTableFromCsv(SampleCsvFileNames.continuous_days_one_row_per_day));
    }
}
