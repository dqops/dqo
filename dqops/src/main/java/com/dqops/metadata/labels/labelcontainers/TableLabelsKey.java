/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.labels.labelcontainers;

import com.dqops.metadata.sources.PhysicalTableName;

/**
 * A key object used in the dictionary of labels to reference tables.
 */
public class TableLabelsKey {
    private String connection;
    private PhysicalTableName table;

    public TableLabelsKey(String connection, PhysicalTableName table) {
        this.connection = connection;
        this.table = table;
    }

    /**
     * Returns the connection name.
     * @return Connection name.
     */
    public String getConnection() {
        return connection;
    }

    /**
     * Returns the table name as a physical table name object.
     * @return Physical table name.
     */
    public PhysicalTableName getTable() {
        return table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableLabelsKey that = (TableLabelsKey) o;

        if (!connection.equals(that.connection)) return false;
        return table.equals(that.table);
    }

    @Override
    public int hashCode() {
        int result = connection.hashCode();
        result = 31 * result + table.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TableLabelsKey{" +
                "connection='" + connection + '\'' +
                ", table=" + table +
                '}';
    }
}
