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
