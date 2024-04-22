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

package com.dqops.metadata.labels;

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
