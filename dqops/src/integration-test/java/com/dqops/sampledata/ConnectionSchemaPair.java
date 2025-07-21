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
 * Connection specification and schema pair where testable tables are created.
 */
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class ConnectionSchemaPair {
    private final ConnectionSpec connection;
    private final String schema;

    /**
     * Creates a connection and schema pair.
     * @param connection Connection specification.
     * @param schema Physical schema name.
     */
    public ConnectionSchemaPair(ConnectionSpec connection, String schema) {
        this.connection = connection;
        this.schema = schema;
    }

    /**
     * Connection specification.
     * @return Connection specification.
     */
    public ConnectionSpec getConnection() {
        return connection;
    }

    /**
     * Physical schema name.
     * @return Schema name.
     */
    public String getSchema() {
        return schema;
    }
}
