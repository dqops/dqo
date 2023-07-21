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
package com.dqops.connectors.bigquery;

import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;

/**
 * User home context for in-memory context that knows bigquery testable objects (tables) that we can use for testing.
 */
@Deprecated
public class BigQueryUserHomeContextObjectMother {
    /**
     * Creates an in-memory user home context with testable bigquery tables already registered.
     * @return User home context with testable connections, tables, columns.
     */
    public static UserHomeContext createWithSampleTablesInMemory() {
        UserHomeContext homeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        ConnectionWrapper connectionWrapper = addDefaultConnection(homeContext);
		addTableToDefaultConnection(homeContext, BigQueryTableSpecObjectMother.create_bq_data_types_test());
		addTableToDefaultConnection(homeContext, BigQueryTableSpecObjectMother.create_numerical_datetime_average_week());

        return homeContext;
    }

    /**
     * Adds a default connection to a testable big query instance.
     * @param homeContext Target home context.
     * @return Connection wrapper that was added.
     */
    public static ConnectionWrapper addDefaultConnection(UserHomeContext homeContext) {
        return addConnection(homeContext, BigQueryConnectionSpecObjectMother.CONNECTION_NAME, BigQueryConnectionSpecObjectMother.createLegacy());
    }

    /**
     * Adds a connection to the user home context.
     * @param homeContext Home context.
     * @param connectionName Connection name.
     * @param connectionSpec Connection spec.
     * @return Connection wrapper.
     */
    public static ConnectionWrapper addConnection(UserHomeContext homeContext, String connectionName, ConnectionSpec connectionSpec) {
        ConnectionWrapper connectionWrapper = homeContext.getUserHome().getConnections().createAndAddNew(connectionName);
        connectionWrapper.setSpec(connectionSpec);
        return connectionWrapper;
    }

    /**
     * Extracts the default connection from the user home. It should be already added.
     * @param homeContext Home context.
     * @return Connection wrapper for the default connection that we use for testing.
     */
    public static ConnectionWrapper getDefaultConnection(UserHomeContext homeContext) {
        ConnectionWrapper connectionWrapper = homeContext.getUserHome().getConnections().getByObjectName(BigQueryConnectionSpecObjectMother.CONNECTION_NAME, false);
        assert  connectionWrapper != null;
        return connectionWrapper;
    }

    /**
     * Adds a table to the default connection.
     * @param homeContext Target user context.
     * @param tableSpec Table specification.
     * @return Table wrapper that stores the table specification.
     */
    public static TableWrapper addTableToDefaultConnection(UserHomeContext homeContext, TableSpec tableSpec) {
        ConnectionWrapper defaultConnection = getDefaultConnection(homeContext);
        TableWrapper tableWrapper = defaultConnection.getTables().createAndAddNew(tableSpec.getPhysicalTableName());
        tableWrapper.setSpec(tableSpec);
        return tableWrapper;
    }
}
