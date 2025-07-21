/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.storage;

import tech.tablesaw.api.Table;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Identifies changes (new rows, updated rows, deleted rows) that should be stored to a partitioned table.
 * The changes must be related to the same connection and table, but may span multiple monthly partitions.
 */
public class TableDataChanges {
    private Table newOrChangedRows;
    private Set<String> deletedIds = new LinkedHashSet<>();

    /**
     * Creates a partition delta.
     * @param newOrChangedRows New or changed rows to be added or replaced in the table. The data must be related to the same connection and schema.table, but may span across multiple monthly partitions.
     */
    public TableDataChanges(Table newOrChangedRows) {
        this.newOrChangedRows = newOrChangedRows;
    }

    /**
     * New or modified rows in a tablesaw table.
     * @return New or modified rows to be replaced.
     */
    public Table getNewOrChangedRows() {
        return newOrChangedRows;
    }

    /**
     * Changes the table with new rows.
     * @param newOrChangedRows New or changed rows.
     */
    public void setNewOrChangedRows(Table newOrChangedRows) {
        this.newOrChangedRows = newOrChangedRows;
    }

    /**
     * Optional collection of primary keys to be deleted.
     * @return Primary keys to be deleted.
     */
    public Set<String> getDeletedIds() {
        return deletedIds;
    }

    /**
     * Checks if the data changes contains any changes that needs to be persisted (new rows, updated rows, rows to delete).
     * @return True when there are any changes, false when no changes are identified.
     */
    public boolean hasChanges() {
        return this.newOrChangedRows.rowCount() > 0 || this.deletedIds.size() > 0;
    }
}
