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
package com.dqops.core.jobqueue.jobs.schema;

/**
 * Concurrency target (connection name + schema name) that is used to limit the number of concurrent operations.
 */
public class ImportSchemaQueueJobConcurrencyTarget {
    private String connectionName;
    private String schemaName;

    public ImportSchemaQueueJobConcurrencyTarget() {
    }

    /**
     * Creates a concurrency object used to limit concurrent schema import operations to a single schema at a time (DOP: 1)
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     */
    public ImportSchemaQueueJobConcurrencyTarget(String connectionName, String schemaName) {
        this.connectionName = connectionName;
        this.schemaName = schemaName;
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
     * Returns the source schema name that is imported.
     * @return Source schema name.
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * Sets the source schema name to import.
     * @param schemaName Schema name.
     */
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImportSchemaQueueJobConcurrencyTarget that = (ImportSchemaQueueJobConcurrencyTarget) o;

        if (!connectionName.equals(that.connectionName)) return false;
        return schemaName.equals(that.schemaName);
    }

    @Override
    public int hashCode() {
        int result = connectionName.hashCode();
        result = 31 * result + schemaName.hashCode();
        return result;
    }
}
