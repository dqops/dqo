/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
