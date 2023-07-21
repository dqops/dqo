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
