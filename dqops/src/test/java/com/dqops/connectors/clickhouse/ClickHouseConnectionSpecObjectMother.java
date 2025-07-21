/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.clickhouse;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.testcontainers.TestContainersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import org.testcontainers.containers.ClickHouseContainer;

public class ClickHouseConnectionSpecObjectMother {
    private static ClickHouseContainer sharedContainer;
    private static final int PORT = 8123;
    private static final String IMAGE = "clickhouse/clickhouse-server:24.8";

    /**
     * Creates a shared ClickHouse container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started ClickHouse instance.
     */
    public static ClickHouseContainer getSharedContainer() {
        if (sharedContainer == null) {
            //noinspection resource
            sharedContainer = new ClickHouseContainer(IMAGE)
                    .withExposedPorts(PORT)
                    .withReuse(TestContainersObjectMother.shouldUseReusableTestContainers());
            sharedContainer.start();
        }

        return sharedContainer;
    }

    /**
     * Connection name to ClickHouse.
     */
    public static final String CONNECTION_NAME = "clickhouse_connection";

    /**
     * Creates a default connection spec to ClickHouse database that should be started by test containers.
     * @return Connection spec to a test container instance.
     */
    public static ConnectionSpec create() {
        ClickHouseContainer testContainer = getSharedContainer();

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.clickhouse);
            setClickhouse(new ClickHouseParametersSpec()
            {{
                setHost("localhost");
                setPort(testContainer.getMappedPort(PORT).toString());
                setUser(testContainer.getUsername());
                setPassword(testContainer.getPassword());
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable ClickHouse database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "default";
    }
}