/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.mysql;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.testcontainers.TestContainersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import org.testcontainers.containers.MySQLContainer;

public class MysqlConnectionSpecObjectMother {
    private static MySQLContainer <?> sharedContainer;
    private static final int PORT = 3306;

    /**
     * Creates a shared MySQL container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started MySQL instance.
     */
    public static MySQLContainer<?> getSharedContainer() {
        if (sharedContainer == null) {
            //noinspection resource
            sharedContainer = new MySQLContainer<>(MySQLContainer.IMAGE)
                    .withExposedPorts(PORT)
                    .withDatabaseName("public")
                    .withUsername("test")
                    .withPassword("test")
                    .withReuse(TestContainersObjectMother.shouldUseReusableTestContainers());
            sharedContainer.start();
        }

        return sharedContainer;
    }

    /**
     * Connection name to MySQL.
     */
    public static final String CONNECTION_NAME = "mysql_connection";

    /**
     * Creates a default connection spec to MySQL database that should be started by test containers.
     * @return Connection spec to a test container instance.
     */
    public static ConnectionSpec create() {
        MySQLContainer<?> testContainer = getSharedContainer();

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.mysql);
            setMysql(new MysqlParametersSpec()
            {{
                setHost("localhost");
                setPort(testContainer.getMappedPort(PORT).toString());
                setDatabase(testContainer.getDatabaseName());
                setUser(testContainer.getUsername());
                setPassword(testContainer.getPassword());
                setMysqlEngineType(MysqlEngineType.mysql);
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable MySQL database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "public";
    }
}