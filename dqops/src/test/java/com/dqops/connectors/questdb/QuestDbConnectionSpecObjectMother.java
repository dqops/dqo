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
package com.dqops.connectors.questdb;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.postgresql.PostgresqlEngineType;
import com.dqops.connectors.postgresql.PostgresqlParametersSpec;
import com.dqops.connectors.testcontainers.TestContainersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.QuestDBContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Object mother for a testable timescale connection spec that provides access to a database started inside Testcontainers.
 */
public class QuestDbConnectionSpecObjectMother {
    private static QuestDBContainer sharedContainer;
    private static final int PORT = 8812;
    static final DockerImageName IMAGE_NAME = DockerImageName.parse("questdb/questdb");

    /**
     * Creates a shared Timescale container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started Timescale instance.
     */
    public static QuestDBContainer getSharedContainer() {
        if (sharedContainer == null) {
            //noinspection resource
            sharedContainer = new QuestDBContainer(IMAGE_NAME)
                    .withExposedPorts(PORT, 9000)
                    .withReuse(TestContainersObjectMother.shouldUseReusableTestContainers());
            sharedContainer.start();
        }

        return sharedContainer;
    }

    /**
     * Connection name to timescale.
     */
    public static final String CONNECTION_NAME = "questdb_connection";

    /**
     * Creates a default connection spec to postgresql database that should be started by test containers.
     * @return Connection spec to a test container instance.
     */
    public static ConnectionSpec create() {
        QuestDBContainer testContainer = getSharedContainer();

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.questdb);
            setQuestdb(new QuestDbParametersSpec()
            {{
                setHost("localhost");
                setPort(testContainer.getMappedPort(PORT).toString());
                setDatabase("qdb");
                setUser("admin");
                setPassword("quest");
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable postgresql database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "qdb";
    }
}
