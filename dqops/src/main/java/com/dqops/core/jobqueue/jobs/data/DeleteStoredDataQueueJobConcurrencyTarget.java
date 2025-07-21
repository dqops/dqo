/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.jobs.data;

import java.util.Objects;

/**
 * Concurrency target (connection name) that is used to limit the number of concurrent operations.
 */
public class DeleteStoredDataQueueJobConcurrencyTarget {
    private String connectionName;

    public DeleteStoredDataQueueJobConcurrencyTarget() {
    }

    /**
     * Creates a concurrency object used to limit concurrent operations to a single connection at a time (DOP: 1)
     * @param connectionName Connection name.
     */
    public DeleteStoredDataQueueJobConcurrencyTarget(String connectionName) {
        this.connectionName = connectionName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeleteStoredDataQueueJobConcurrencyTarget that = (DeleteStoredDataQueueJobConcurrencyTarget) o;

        return Objects.equals(connectionName, that.connectionName);
    }

    @Override
    public int hashCode() {
        return connectionName != null ? connectionName.hashCode() : 0;
    }
}
