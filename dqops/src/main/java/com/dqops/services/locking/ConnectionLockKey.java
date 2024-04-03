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
