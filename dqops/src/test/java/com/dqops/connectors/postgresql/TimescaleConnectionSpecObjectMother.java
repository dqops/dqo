/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.postgresql;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.testcontainers.TestContainersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Object mother for a testable timescale connection spec that provides access to a database started inside Testcontainers.
 */
public class TimescaleConnectionSpecObjectMother {
    private static PostgreSQLContainer<?> sharedContainer;
    private static final int PORT = 5432;

    /**
     * Creates a shared Timescale container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started Timescale instance.
     */
    public static PostgreSQLContainer<?> getSharedContainer() {
        if (sharedContainer == null) {
            DockerImageName image = DockerImageName.parse("timescale/timescaledb:2.1.0-pg11")
                    .asCompatibleSubstituteFor("postgres");
            //noinspection resource
            sharedContainer = new PostgreSQLContainer<>(image)
                    .withExposedPorts(PORT)
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test")
                    .withReuse(TestContainersObjectMother.shouldUseReusableTestContainers());
            sharedContainer.start();
        }

        return sharedContainer;
    }

    /**
     * Connection name to timescale.
     */
    public static final String CONNECTION_NAME = "postgresql_connection";

    /**
     * Creates a default connection spec to postgresql database that should be started by test containers.
     * @return Connection spec to a test container instance.
     */
    public static ConnectionSpec create() {
        PostgreSQLContainer<?> testContainer = getSharedContainer();

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.postgresql);
            setPostgresql(new PostgresqlParametersSpec()
            {{
                setHost("localhost");
                setPort(testContainer.getMappedPort(PORT).toString());
                setDatabase(testContainer.getDatabaseName());
                setUser(testContainer.getUsername());
                setPassword(testContainer.getPassword());
                setPostgresqlEngineType(PostgresqlEngineType.timescale);
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable postgresql database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "public";
    }
}
