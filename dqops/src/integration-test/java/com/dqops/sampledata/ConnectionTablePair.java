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
package com.dqops.sampledata;

import com.dqops.metadata.sources.ConnectionSpec;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Connection specification and a table name (csv file name) pair to use as a hashtable key.
 */
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class ConnectionTablePair {
    private final ConnectionSpec connection;
    private final String hashedTableName;

    /**
     * Creates a connection and schema pair.
     * @param connection Connection specification.
     * @param hashedTableName Physical schema name.
     */
    public ConnectionTablePair(ConnectionSpec connection, String hashedTableName) {
        this.connection = connection;
        this.hashedTableName = hashedTableName;
    }

    /**
     * Connection specification.
     * @return Connection specification.
     */
    public ConnectionSpec getConnection() {
        return connection;
    }

    /**
     * Physical table name (without the schema).
     * @return Physical table name.
     */
    public String getHashedTableName() {
        return hashedTableName;
    }
}
