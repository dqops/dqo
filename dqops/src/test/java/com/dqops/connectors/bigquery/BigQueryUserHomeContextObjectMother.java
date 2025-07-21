/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
