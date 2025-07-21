/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.oracle;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.testcontainers.TestContainersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import org.testcontainers.containers.OracleContainer;

public class OracleConnectionSpecObjectMother {
    private static OracleContainer sharedContainer;

    private static final String ORACLE_IMAGE = "gvenzl/oracle-xe:21-slim-faststart";
    private static final int PORT = 1521;

    /**
     * Connection name to Oracle.
     */
    public static final String CONNECTION_NAME = "oracle_connection";

    /**
     * Creates a shared Oracle container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started Oracle instance.
     */
    public static OracleContainer getSharedContainer() {

        if (sharedContainer == null) {
            //noinspection resource
            sharedContainer = new OracleContainer(ORACLE_IMAGE)
                    .withExposedPorts(PORT)
                    .withDatabaseName("DB_TEST")
                    .withUsername(getSchemaName())
                    .withPassword("PASSWD_TEST")
                    .withReuse(TestContainersObjectMother.shouldUseReusableTestContainers());
            sharedContainer.start();
        }

        return sharedContainer;
    }

    /**
     * Creates a default connection spec to Oracle database that should be started by test containers.
     * @return Connection spec to a test container instance.
     */
    public static ConnectionSpec create() {
        OracleContainer testContainer = getSharedContainer();

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.oracle);
            setOracle(new OracleParametersSpec()
            {{
                setHost("localhost");
                setPort(testContainer.getMappedPort(PORT).toString());
                setDatabase(testContainer.getDatabaseName());
                setUser(testContainer.getUsername());
                setPassword(testContainer.getPassword());
                setInitializationSql("ALTER SESSION SET NLS_DATE_FORMAT='YYYY-MM-DD\"T\"HH24:MI:SS' NLS_TIMESTAMP_FORMAT='YYYY-MM-DD\"T\"HH24:MI:SS.FF'");
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable Oracle database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "USER_TEST";
    }
}