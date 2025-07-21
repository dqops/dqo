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

import java.util.Objects;

/**
 * Lock target object for a connection.
 */
public class ConnectionLockKey {
    private final String connectionName;

    public ConnectionLockKey(String connectionName) {
        this.connectionName = connectionName;
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

        ConnectionLockKey that = (ConnectionLockKey) o;

        return Objects.equals(connectionName, that.connectionName);
    }

    @Override
    public int hashCode() {
        return connectionName != null ? connectionName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ConnectionLockKey{" +
                "connectionName='" + connectionName + '\'' +
                '}';
    }
}
