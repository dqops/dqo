/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.locking;

import com.dqops.metadata.sources.PhysicalTableName;

import java.util.Objects;

/**
 * Lock target object for a connection.
 */
public class ConnectionTableLockKey {
    private final String connectionName;
    private final PhysicalTableName physicalTableName;

    public ConnectionTableLockKey(String connectionName, PhysicalTableName physicalTableName) {
        this.connectionName = connectionName;
        this.physicalTableName = physicalTableName;
    }

    /**
     * Returns the connection name.
     * @return Connection name.
     */
    public String getConnectionName() {
        return connectionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConnectionTableLockKey that = (ConnectionTableLockKey) o;

        if (!Objects.equals(connectionName, that.connectionName))
            return false;
        return Objects.equals(physicalTableName, that.physicalTableName);
    }

    @Override
    public int hashCode() {
        int result = connectionName != null ? connectionName.hashCode() : 0;
        result = 31 * result + (physicalTableName != null ? physicalTableName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConnectionTableLockKey{" +
                "connectionName='" + connectionName + '\'' +
                ", physicalTableName=" + physicalTableName +
                '}';
    }
}
