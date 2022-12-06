package ai.dqo.data.storage;

import tech.tablesaw.api.Table;

import java.util.HashSet;
import java.util.Set;

/**
 * Identifies changes (new rows, updated rows, deleted rows) that should be stored to a partitioned table.
 * The changes must be related to the same connection and table, but may span multiple monthly partitions.
 */
public class TableDataChanges {
    private Table newOrChangedRows;
    private HashSet<String> deletedIds = new HashSet<>();

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
