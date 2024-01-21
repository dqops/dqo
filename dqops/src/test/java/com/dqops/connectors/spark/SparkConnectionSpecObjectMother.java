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

package com.dqops.connectors.spark;

import com.dqops.connectors.ProviderType;
import com.dqops.metadata.sources.ConnectionSpec;
import org.testcontainers.containers.GenericContainer;

public class SparkConnectionSpecObjectMother {
    private static SparkContainer sharedContainer;

    /**
     * Creates a shared Spark container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started Spark instance.
     */
    public static SparkContainer<?> getSharedContainer() {
        if (sharedContainer == null) {
            sharedContainer = new SparkContainer<>();
            sharedContainer.start();
        }
        return sharedContainer;
    }

    /**
     * Creates a default connection spec to Spark database that should be started by test containers.
     * @return Connection spec to a test container instance.
     */
    public static ConnectionSpec create() {
        GenericContainer testContainer = getSharedContainer();

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.spark);
            setSpark(new SparkParametersSpec()
            {{
                setHost("localhost");
                setPort(testContainer.getMappedPort(SparkContainer.SPARK_SQL_THRIFTSERVER_PORT).toString());
                // no username and password is required
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable Spark database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return SparkContainer.getDefaultSchemaName();
    }
}