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
package ai.dqo.sampledata.files;

import ai.dqo.BaseTest;
import ai.dqo.sampledata.SampleCsvFileNames;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;

/**
 * Unit tests for the {@link CsvSampleFilesObjectMother}
 */
@SpringBootTest
public class CsvSampleFilesTests extends BaseTest {
    @Test
    void loadTableCsv_whenLoaded_continuous_days_one_row_per_day_thenFileParsed() {
        Table table = CsvSampleFilesObjectMother.loadTableCsv(SampleCsvFileNames.continuous_days_one_row_per_day);
        Assertions.assertEquals("continuous_days_one_row_per_day", table.name());
        Assertions.assertEquals(24, table.rowCount());

        Assertions.assertEquals("id", table.column(0).name());
        Assertions.assertEquals(ColumnType.INTEGER, table.column(0).type());

        Assertions.assertEquals("date", table.column(1).name());
        Assertions.assertEquals(ColumnType.LOCAL_DATE, table.column(1).type());
    }

    @Test
    void getSampleTable_whenCalled_thenReturnsTableHashed() {
        SampleTableFromCsv sampleTable = CsvSampleFilesObjectMother.getSampleTable(SampleCsvFileNames.continuous_days_one_row_per_day);
        Assertions.assertNotNull(sampleTable);
        Assertions.assertEquals(24, sampleTable.getTable().rowCount());
        Assertions.assertEquals("continuous_days_one_row_per_day_" + sampleTable.getHash(), sampleTable.getHashedTableName());
    }

    @Test
    void getSampleTable_whenCalledTwice_thenReturnsTheSameCachedTable() {
        SampleTableFromCsv sampleTable = CsvSampleFilesObjectMother.getSampleTable(SampleCsvFileNames.continuous_days_one_row_per_day);
        Assertions.assertNotNull(sampleTable);
        Assertions.assertSame(sampleTable, CsvSampleFilesObjectMother.getSampleTable(SampleCsvFileNames.continuous_days_one_row_per_day));
    }
}
