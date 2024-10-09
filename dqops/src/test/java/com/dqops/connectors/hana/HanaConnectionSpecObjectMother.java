/*
 * Copyright © 2021 DQOps (support@dqops.com)
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