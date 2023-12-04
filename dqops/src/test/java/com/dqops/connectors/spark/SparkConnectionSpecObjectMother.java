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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public class SparkConnectionSpecObjectMother {
    private static SparkContainer sharedContainer;

    private static final String SPARK_IMAGE_DOCKERFILE = "src/test/resources/dockerfiles/spark.Dockerfile";
    private static final int PORT = SparkContainer.SPARK_SQL_THRIFTSERVER_PORT;

    /**
     * Connection name to Spark.
     */
    public static final String CONNECTION_NAME = "spark_connection";

    @Autowired
    private ResourceLoader resourceLoader;


    /**
     * Creates a shared Spark container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started Spark instance.
     */
    public static SparkContainer<?> getSharedContainer() {

        if (sharedContainer == null) {

            Path dockerfilePath = null;
            try {
                dockerfilePath = ResourceUtils.getFile(SPARK_IMAGE_DOCKERFILE).getAbsoluteFile().toPath();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            sharedContainer = new SparkContainer<>(dockerfilePath)
                    .withExposedPorts(10000)
//                    .withStartupCheckStrategy()
                    .withStartupTimeoutSeconds(30)
                    .waitingFor(Wait.forLogMessage(".*HiveThriftServer2 started.*\\s", 100))
                    ;

//
//
//            sharedContainer = new GenericContainer<>(
//                    new ImageFromDockerfile()
//                            .withDockerfile(dockerfilePath));

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
                setPort(testContainer.getMappedPort(PORT).toString());

//                setDatabase(testContainer.getDatabaseName());
//                setUser(testContainer.getUsername());
//                setPassword(testContainer.getPassword());
////                setInitializationSql("alter session set NLS_DATE_FORMAT='YYYY-MM-DD HH24:MI:SS'");
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable Spark database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "default";
    }
}