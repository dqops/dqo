/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.sqlserver;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.testcontainers.TestContainersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import org.testcontainers.containers.MSSQLServerContainer;

import java.util.LinkedHashMap;


public class SqlServerConnectionSpecObjectMother {

    private static MSSQLServerContainer<?> sharedContainer;
    private static final int PORT = 1433;
    private static final String SQLSERVERPASSWORD = "Te$t_sqlserver1";
    /**
     * Creates a shared MSSQL Server container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started MSSQL Server instance.
     */
    public static MSSQLServerContainer<?> getSharedContainer() {
        if (sharedContainer == null) {
            //noinspection resource
            sharedContainer = new MSSQLServerContainer<>(MSSQLServerContainer.IMAGE)
                    .withExposedPorts(PORT)
                    .withPassword(SQLSERVERPASSWORD)
                    .acceptLicense()
                    .withReuse(TestContainersObjectMother.shouldUseReusableTestContainers());
            sharedContainer.start();
        }

        return sharedContainer;
    }

    /**
     * Connection name to MSSQL Server.
     */
    public static final String CONNECTION_NAME = "sqlserver_connection";

    /**
     * Creates a default connection spec to sql server database that should be started by test containers.
     * @return Connection spec to a test container instance.
     */
    public static ConnectionSpec create() {
        MSSQLServerContainer<?> testContainer = getSharedContainer();

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.sqlserver);
            setSqlserver(new SqlServerParametersSpec()
            {{
                setHost("localhost");
                setPort(testContainer.getMappedPort(PORT).toString());
                setDatabase("master");
                setUser(testContainer.getUsername());
                setPassword(testContainer.getPassword());
                setDisableEncryption(false);
                setProperties(new LinkedHashMap<>() {{
                    put("trustServerCertificate", "true");
                }});
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable sql server database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "dbo";
    }
}
