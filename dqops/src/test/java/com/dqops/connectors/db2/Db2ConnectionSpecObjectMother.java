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
package com.dqops.connectors.db2;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.testcontainers.TestContainersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import org.testcontainers.containers.Db2Container;

/**
 * Object mother for a testable DB2 connection spec that provides access to a database started inside Testcontainers.
 */
public class Db2ConnectionSpecObjectMother {
    private static Db2Container sharedContainer;
    private static final int PORT = 50000;
    private static final String IMAGE_NAME = "ibmcom/db2:latest";
    private static final String USER_NAME = "DB2INST1"; // db2inst1

    /**
     * Creates a shared DB2 container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started DB2 instance.
     */
    public static Db2Container getSharedContainer() {

        String licenseAcceptedEnvVar = System.getenv("I_HAVE_ACCEPTED_IBM_LICENSE");
        Boolean licenseAccepted = Boolean.parseBoolean(licenseAcceptedEnvVar);
        if (!licenseAccepted){
            throw new RuntimeException("The IBM license has not been accepted.");
        }

        if (sharedContainer == null) {

            sharedContainer = new Db2Container(IMAGE_NAME)
                    .acceptLicense()
                    .withReuse(TestContainersObjectMother.shouldUseReusableTestContainers());

            sharedContainer.start();
        }

        System.out.println(sharedContainer.getJdbcUrl());

        return sharedContainer;
    }

    /**
     * Connection name to DB2.
     */
    public static final String CONNECTION_NAME = "db2_connection";

    /**
     * Creates a default connection spec to DB2 database that should be started by test containers.
     * @return Connection spec to a test container instance.
     */
    public static ConnectionSpec create() {
        Db2Container testContainer = getSharedContainer();

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.db2);
            setDb2(new Db2ParametersSpec()
            {{
                setHost("localhost");
                setPort(testContainer.getMappedPort(PORT).toString());
//                setDatabase(testContainer.getDatabaseName());
//                setUser(testContainer.getUsername());
//                setPassword(testContainer.getPassword());
                setDatabase("test");
                setUser(USER_NAME);
                setPassword("foobar1234");
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable HANA database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return USER_NAME;
    }

}
