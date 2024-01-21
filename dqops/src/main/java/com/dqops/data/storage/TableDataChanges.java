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
