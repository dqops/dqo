/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
