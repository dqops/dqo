/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.hana;

import com.dqops.connectors.ProviderType;
import com.dqops.metadata.sources.ConnectionSpec;
import org.testcontainers.containers.GenericContainer;

public class HanaConnectionSpecObjectMother {
    private static HanaContainer sharedContainer;

    /**
     * Creates a shared HANA container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started HANA instance.
     */
    public static HanaContainer<?> getSharedContainer() {
        if (sharedContainer == null) {
            sharedContainer = new HanaContainer<>();
            sharedContainer.start();
        }
        return sharedContainer;
    }

    /**
     * Creates a default connection spec to HANA database that is started by either test containers or manually.
     * Running the test container handled instance takes much time. For the development purpose running docker
     * manually is preferred. For that change the persistentContainer flag below.
     *
     * @return Connection spec to a test container instance.
     */
    public static ConnectionSpec create() {
        String persistentContainerEnvVar = System.getenv("PERSISTENT_DATABASE_CONTAINER");
        boolean persistentContainer = Boolean.parseBoolean(persistentContainerEnvVar);

        if (persistentContainer) {
            ConnectionSpec connectionSpec = new ConnectionSpec()
            {{
                setProviderType(ProviderType.hana);
                setHana(new HanaParametersSpec()
                {{
                    setHost("localhost");
                    setPort("39017");
                    setUser(HanaContainer.USERNAME);
                    setPassword(HanaContainer.PASSWORD);
                }});
            }};
            return connectionSpec;
        }

        GenericContainer testContainer = getSharedContainer();

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.hana);
            setHana(new HanaParametersSpec()
            {{
                setHost("localhost");
                setPort(testContainer.getMappedPort(HanaContainer.SYSTEM_PORT).toString());
                setUser(HanaContainer.USERNAME);
                setPassword(HanaContainer.PASSWORD);
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable HANA database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "SYSTEM";
    }
}