/*
 * Copyright © 2022 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.core.jobqueue.jobs.table;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Concurrency target (connection name, schema name, table names) that is used to limit the number of concurrent operations.
 */
public class ImportTablesQueueJobConcurrencyTarget {
    private String connectionName;
    private String schemaName;
    private Set<String> tableNames;

    public ImportTablesQueueJobConcurrencyTarget() {
    }

    /**
     * Creates a concurrency object used to limit concurrent table import operations to a single table at a time (DOP: 1)
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param tableNames Table names.
     */
    public ImportTablesQueueJobConcurrencyTarget(String connectionName, String schemaName, Set<String> tableNames) {
        this.connectionName = connectionName;
        this.schemaName = schemaName;
        this.tableNames = tableNames;
    }

    /**
     * Returns the connection name.
     * @return Connection name.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Sets the connection name.
     * @param connectionName Connection name.
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Returns the source schema name.
     * @return Source schema name.
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * Sets the source schema name.
     * @param schemaName Schema name.
     */
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    /**
     * Returns the table names that are imported.
     * @return Table names.
     */
    public Set<String> getTableNames() {
        return tableNames;
    }

    /**
     * Sets the table names to import.
     * @param tableNames Table names.
     */
    public void setTableNames(Set<String> tableNames) {
        this.tableNames = tableNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImportTablesQueueJobConcurrencyTarget that = (ImportTablesQueueJobConcurrencyTarget) o;

        if (!connectionName.equals(that.connectionName)) return false;
        if (!schemaName.equals(that.schemaName)) return false;
        return Collections.disjoint(tableNames, that.tableNames);
    }

    @Override
    public int hashCode() {
        int result = connectionName.hashCode();
        result = 31 * result + schemaName.hashCode();
        result = 31 * result + tableNames.hashCode();
        return result;
    }
}
