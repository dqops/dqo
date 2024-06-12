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
package com.dqops.utils.tables;

import com.dqops.BaseTest;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.readouts.factory.SensorReadoutTableFactoryObjectMother;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.storage.FileStorageSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

@SpringBootTest
public class TableMergeUtilityTests extends BaseTest {
    private Table currentTable;
    private Table newTable;
    private String[] joinColumnNames;

    @BeforeEach
    void setUp() {
		this.currentTable = SensorReadoutTableFactoryObjectMother.createEmptyNormalizedTable("current");
		this.newTable = SensorReadoutTableFactoryObjectMother.createEmptyNormalizedTable("new");
		this.joinColumnNames = new String[] {
                SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME,
                SensorReadoutsColumnNames.DATA_GROUP_HASH_COLUMN_NAME
        };
    }

    void addRows(Table targetTable, long checkId, long dimensionId, double actualValue) {
        Row row = targetTable.appendRow();
        row.setDouble(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, actualValue);
        row.setLong(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME, checkId);
        row.setLong(SensorReadoutsColumnNames.DATA_GROUP_HASH_COLUMN_NAME, dimensionId);
    }

    @Test
    void mergeNewResults_whenCurrentEmptyAndNewHasRow_thenRowAppended() {
		addRows(newTable, 10L, 20L, 15.0);

        Table merged = TableMergeUtility.mergeNewResults(this.currentTable, this.newTable, this.joinColumnNames,
                UserDomainIdentity.SYSTEM_USER_NAME, FileStorageSettings.DEFAULT_COPIED_COLUMN_NAMES);

        Assertions.assertEquals(1, merged.rowCount());
        Assertions.assertEquals("10", merged.getString(0, SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME));
        Assertions.assertEquals("15", merged.getString(0, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME));
    }

    @Test
    void mergeNewResults_whenCurrentHasRowAndNewHasNonMatchingRow_thenRowAppendedAndCurrentRetained() {
		addRows(currentTable, 11L, 21L, 16.0);
		addRows(newTable, 10L, 20L, 15.0);

        Table merged = TableMergeUtility.mergeNewResults(this.currentTable, this.newTable, this.joinColumnNames,
                UserDomainIdentity.SYSTEM_USER_NAME, FileStorageSettings.DEFAULT_COPIED_COLUMN_NAMES);

        Assertions.assertEquals(2, merged.rowCount());
        Assertions.assertEquals("11", merged.getString(0, SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME));
        Assertions.assertEquals("16", merged.getString(0, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME));

        Assertions.assertEquals("10", merged.getString(1, SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME));
        Assertions.assertEquals("15", merged.getString(1, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME));
    }

    @Test
    void mergeNewResults_whenCurrentHasRowAndNewHasNonMatchingRowBecauseOneJoinColumnDiffers_thenRowAppendedAndCurrentRetained() {
		addRows(currentTable, 10L, 21L, 16.0);
		addRows(newTable, 10L, 20L, 15.0);

        Table merged = TableMergeUtility.mergeNewResults(this.currentTable, this.newTable, this.joinColumnNames,
                UserDomainIdentity.SYSTEM_USER_NAME, FileStorageSettings.DEFAULT_COPIED_COLUMN_NAMES);

        Assertions.assertEquals(2, merged.rowCount());
        Assertions.assertEquals("10", merged.getString(0, SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME));
        Assertions.assertEquals("16", merged.getString(0, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME));

        Assertions.assertEquals("10", merged.getString(1, SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME));
        Assertions.assertEquals("15", merged.getString(1, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME));
    }

    @Test
    void mergeNewResults_whenCurrentHasRowAndNewHasRowThatOverrides_thenRowReplaced() {
		addRows(currentTable, 10L, 20L, 16.0);
		addRows(newTable, 10L, 20L, 15.0);

        Table merged = TableMergeUtility.mergeNewResults(this.currentTable, this.newTable, this.joinColumnNames,
                UserDomainIdentity.SYSTEM_USER_NAME, FileStorageSettings.DEFAULT_COPIED_COLUMN_NAMES);

        Assertions.assertEquals(1, merged.rowCount());
        Assertions.assertEquals("10", merged.getString(0, SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME));
        Assertions.assertEquals("15", merged.getString(0, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME));
    }
}
