package ai.dqo.data.storage;

import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.metadata.sources.PhysicalTableName;

import java.time.LocalDate;

/**
 * Identifies a single partition for hive partitioned tables stored as parquet files.
 */
public class ParquetPartitionId {
    private DqoRoot tableType;
    private String connectionName;
    private PhysicalTableName tableName;
    private LocalDate month;

    /**
     * Creates a partition identifier for a single partition.
     * A partition is identified by a three level partitioning scheme: connection name, table name (schema.table)
     * and the month (the date of the first day of the month).
     * @param tableType Target table type.
     * @param connectionName Connection name.
     * @param tableName Table name.
     * @param month The date of the first day of the month.
     */
    public ParquetPartitionId(DqoRoot tableType, String connectionName, PhysicalTableName tableName, LocalDate month) {
        assert month.getDayOfMonth() == 1;

        this.connectionName = connectionName;
        this.tableName = tableName;
        this.month = month;
    }

    /**
     * Returns the connection name.
     * @return Connection name.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Returns the physical table name, identified as a schema.table.
     * @return Physical table name.
     */
    public PhysicalTableName getTableName() {
        return tableName;
    }

    /**
     * Returns the date of the month that is covered by a monthly partition. It is the date of the first day of the month.
     * @return The date of the first day of the month for a monthly partition.
     */
    public LocalDate getMonth() {
        return month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParquetPartitionId that = (ParquetPartitionId) o;

        if (tableType != that.tableType) return false;
        if (!connectionName.equals(that.connectionName)) return false;
        if (!tableName.equals(that.tableName)) return false;
        return month.equals(that.month);
    }

    @Override
    public int hashCode() {
        int result = tableType.hashCode();
        result = 31 * result + connectionName.hashCode();
        result = 31 * result + tableName.hashCode();
        result = 31 * result + month.hashCode();
        return result;
    }
}
