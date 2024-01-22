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