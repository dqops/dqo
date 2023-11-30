/*
 * Copyright © 2023 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        return "DEFAULT";
    }
}
