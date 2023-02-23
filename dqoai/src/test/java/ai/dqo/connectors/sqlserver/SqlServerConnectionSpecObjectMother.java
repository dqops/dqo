/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.connectors.sqlserver;

import ai.dqo.connectors.ProviderType;
import ai.dqo.connectors.testcontainers.TestContainersObjectMother;
import ai.dqo.metadata.sources.ConnectionSpec;
import org.testcontainers.containers.MSSQLServerContainer;

import java.util.HashMap;
import java.util.Map;

public class SqlServerConnectionSpecObjectMother {
    private static MSSQLServerContainer<?> sharedContainer;
    private static final int PORT = 1433;

    /**
     * Creates a shared PostgreSQL container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started postgresql instance.
     */
    public static MSSQLServerContainer<?> getSharedContainer() {
        if (sharedContainer == null) {
            //noinspection resource
            sharedContainer = new MSSQLServerContainer<>(MSSQLServerContainer.IMAGE)
                    .withExposedPorts(PORT)
                    .withPassword("Te$t_sqlserver1")
                    .withUrlParam("trustServerCertificate", "true")
                    .acceptLicense()
                    .withReuse(TestContainersObjectMother.shouldUseReusableTestContainers());
            sharedContainer.start();
        }

        return sharedContainer;
    }

    /**
     * Connection name to big query.
     */
    public static final String CONNECTION_NAME = "sqlserver_connection";

    /**
     * Creates a default connection spec to postgresql database that should be started by test containers.
     * @return Connection spec to a test container instance.
     */
    public static ConnectionSpec create() {
        MSSQLServerContainer<?> testContainer = getSharedContainer();
        HashMap<String,String> properitesTest = new HashMap<String, String>();
        properitesTest.put("trustServerCertificate", "true");

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.sqlserver);
            setSqlserver(new SqlServerParametersSpec()
            {{
                setHost("localhost");
                setPort(testContainer.getMappedPort(PORT).toString());
                setUser(testContainer.getUsername());
                setPassword(testContainer.getPassword());
                setProperties(properitesTest);
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable postgresql database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "dbo";
    }
}
