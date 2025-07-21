/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
