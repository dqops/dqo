/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.presto;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.testcontainers.TestContainersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;

/**
 * Object mother for a testable presto connection spec that provides access to the sandbox database.
 */
public class PrestoConnectionSpecObjectMother {
    private static PrestoTestContainer sharedContainer;
    
    /**
     * Connection name to presto.
     */
    public static final String CONNECTION_NAME = "presto_connection";

    /**
     * Creates a shared Presto container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started Presto instance.
     */
    public static PrestoTestContainer getSharedContainer() {

        if (sharedContainer == null) {
            //noinspection resource
            sharedContainer = new PrestoTestContainer()
                    .withDatabaseName("memory")
                    .withUsername("USER_TEST")
                    .withPassword("PASSWD_TEST")
                    .withReuse(TestContainersObjectMother.shouldUseReusableTestContainers());
            sharedContainer.start();
        }

        return sharedContainer;
    }
    
    /**
     * Creates a default connection spec to a sandbox presto database.
     * @return Connection spec to a sandbox environment.
     */
    public static ConnectionSpec create() {
        PrestoTestContainer testContainer = getSharedContainer();

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.presto);

			setPresto(new PrestoParametersSpec()
            {{
                setHost("localhost");
                setPort(testContainer.getMappedPort(PrestoTestContainer.PRESTO_PORT).toString());
                setDatabase(testContainer.getDatabaseName());
                setUser(testContainer.getUsername());
                setPassword(testContainer.getPassword());
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable presto database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "default";
    }
}
