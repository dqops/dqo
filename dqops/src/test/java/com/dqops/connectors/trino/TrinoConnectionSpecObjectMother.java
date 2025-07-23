/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.trino;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.testcontainers.TestContainersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import org.testcontainers.containers.TrinoContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Object mother for a testable Trino connection spec that provides access to the sandbox database.
 */
public class TrinoConnectionSpecObjectMother {
    private static final DockerImageName DOCKER_IMAGE_NAME = DockerImageName.parse("trinodb/trino");
    private static final int TRINO_PORT = 8080;

    private static TrinoContainer sharedContainer;

    /**
     * Connection name to Trino.
     */
    public static final String CONNECTION_NAME = "trino_connection";

    /**
     * Creates a shared Trino container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started Trino instance.
     */
    public static TrinoContainer getSharedContainer() {

        if (sharedContainer == null) {
            //noinspection resource
            sharedContainer = new TrinoContainer(DOCKER_IMAGE_NAME)
                    .withDatabaseName("memory")
                    .withUsername("USER_TEST")
                    .withReuse(TestContainersObjectMother.shouldUseReusableTestContainers());
            sharedContainer.start();
        }

        return sharedContainer;
    }
    
    /**
     * Creates a default connection spec to a sandbox trino database.
     * @return Connection spec to a sandbox environment.
     */
    public static ConnectionSpec create() {
        TrinoContainer testContainer = getSharedContainer();

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.trino);

			setTrino(new TrinoParametersSpec()
            {{
                setHost("localhost");
                setPort(testContainer.getMappedPort(TRINO_PORT).toString());
                setCatalog(testContainer.getDatabaseName());
                setUser(testContainer.getUsername());
                setTrinoEngineType(TrinoEngineType.trino);
            }});
        }};

        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable trino database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "default";
    }
}
