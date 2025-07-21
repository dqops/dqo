/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.sources;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.metadata.id.HierarchyId;

/**
 * Test object mother for creating a sample connection spec.
 */
public class ConnectionSpecObjectMother {
    /**
     * Sample connection name.
     */
    public static final String CONNECTION_NAME = "connection";

    /**
     * Creates a sample connection specification for DuckDB.
     * @param connectionName Connection name;
     * @return Sample connection specification.
     */
    public static ConnectionSpec createSampleConnectionSpec(String connectionName) {
        ConnectionSpec connectionSpec = new ConnectionSpec();
        connectionSpec.setHierarchyId(HierarchyId.makeHierarchyIdForConnection(connectionName));
        connectionSpec.setProviderType(ProviderType.duckdb);
        connectionSpec.setDuckdb(new DuckdbParametersSpec());
        return connectionSpec;
    }

    /**
     * Creates a sample connection specification for DuckDB.
     * @return Sample connection specification.
     */
    public static ConnectionSpec createSampleConnectionSpec() {
        return createSampleConnectionSpec(CONNECTION_NAME);
    }
}
