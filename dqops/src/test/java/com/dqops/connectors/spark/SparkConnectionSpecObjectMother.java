/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.spark;

import com.dqops.connectors.ProviderType;
import com.dqops.metadata.sources.ConnectionSpec;
import org.testcontainers.containers.GenericContainer;

public class SparkConnectionSpecObjectMother {
    private static SparkContainer sharedContainer;

    /**
     * Creates a shared Spark container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started Spark instance.
     */
    public static SparkContainer<?> getSharedContainer() {
        if (sharedContainer == null) {
            sharedContainer = new SparkContainer<>();
            sharedContainer.start();
        }
        return sharedContainer;
    }

    /**
     * Creates a default connection spec to Spark database that should be started by test containers.
     * @return Connection spec to a test container instance.
     */
    public static ConnectionSpec create() {
        GenericContainer testContainer = getSharedContainer();

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.spark);
            setSpark(new SparkParametersSpec()
            {{
                setHost("localhost");
                setPort(testContainer.getMappedPort(SparkContainer.SPARK_SQL_THRIFTSERVER_PORT).toString());
                // no username and password is required
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable Spark database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return SparkContainer.getDefaultSchemaName();
    }
}