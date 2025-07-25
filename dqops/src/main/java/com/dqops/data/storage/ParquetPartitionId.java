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

import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Identifies a single partition for hive partitioned tables stored as parquet files.
 */
public class ParquetPartitionId implements Comparable<ParquetPartitionId> {
    @JsonPropertyDescription("Data domain name.")
    private String dataDomain;

    @JsonPropertyDescription("Table type.")
    private DqoRoot tableType;

    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Table name (schema.table).")
    private PhysicalTableName tableName;

    @JsonPropertyDescription("The date of the first day of the month that identifies a monthly partition.")
    private LocalDate month;

    /**
     * Creates a partition identifier for a single partition.
     * A partition is identified by a three level partitioning scheme: connection name, table name (schema.table)
     * and the month (the date of the first day of the month).
     * @param dataDomain Data domain name.
     * @param tableType Target table type.
     * @param connectionName Connection name.
     * @param tableName Table name.
     * @param month The date of the first day of the month.
     */
    public ParquetPartitionId(String dataDomain, DqoRoot tableType, String connectionName, PhysicalTableName tableName, LocalDate month) {
        assert month == null || month.getDayOfMonth() == 1;
        this.dataDomain = dataDomain;
        this.tableType = tableType;
        this.connectionName = connectionName;
        this.tableName = tableName;
        this.month = month;
    }

    /**
     * Returns the data domain name.
     * @return Data domain name.
     */
    public String getDataDomain() {
        return dataDomain;
    }

    /**
     * Returns the table type.
     * @return Table type.
     */
    public DqoRoot getTableType() {
        return tableType;
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

        if (!Objects.equals(dataDomain, that.dataDomain)) return false;
        if (tableType != that.tableType) return false;
        if (!Objects.equals(connectionName, that.connectionName)) return false;
        if (!Objects.equals(tableName, that.tableName)) return false;
        return month.equals(that.month);
    }

    @Override
    public int hashCode() {
        int result = tableType.hashCode();
        if (dataDomain != null) {
            result = 31 * result + dataDomain.hashCode();
        }
        if (connectionName != null) {
            result = 31 * result + connectionName.hashCode();
        }
        if (tableName != null) {
            result = 31 * result + tableName.hashCode();
        }
        result = 31 * result + month.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ParquetPartitionId{" +
                "dataDomain=" + dataDomain +
                ", tableType=" + tableType +
                ", connectionName='" + connectionName + '\'' +
                ", tableName=" + tableName +
                ", month=" + month +
                '}';
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(@NotNull ParquetPartitionId o) {
        int compareDataDomain = this.dataDomain != null ? this.dataDomain.compareTo(o.dataDomain != null ? o.dataDomain : "") : (o.dataDomain != null ? -1 : 0);
        if (compareDataDomain != 0) {
            return compareDataDomain;
        }

        int compareTableType = this.tableType.compareTo(o.tableType);
        if (compareTableType != 0) {
            return compareTableType;
        }

        int compareConnection = this.connectionName != null ? this.connectionName.compareTo(o.connectionName != null ? o.connectionName : "") :  (o.connectionName != null ? -1 : 0);
        if (compareConnection != 0) {
            return compareConnection;
        }

        int compareSchemaTable = this.tableName != null ? this.tableName.compareTo(o.tableName) : (o.tableName != null ? -1 : 0);
        if (compareSchemaTable != 0) {
            return compareSchemaTable;
        }

        return this.month != null ? this.month.compareTo(o.month) : (o.month != null ? -1 : 0);
    }
}
