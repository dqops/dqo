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

public class HanaConnectionSpecObjectMother {
    private static HanaContainer sharedContainer;

    /**
     * Creates a shared HANA container using Testcontainers. The container will be stopped when the unit/integration session will finish.
     * @return Shared container with a started HANA instance.
     */
//    public static HanaContainer<?> getSharedContainer() {
////        if (sharedContainer == null) {
////            sharedContainer = new HanaContainer<>();
////            sharedContainer.start();
////        }
////        return sharedContainer;
//    }

    /**
     * Creates a default connection spec to HANA database that should be started by test containers.
     * @return Connection spec to a test container instance.
     */
    public static ConnectionSpec create() {
//        GenericContainer testContainer = getSharedContainer();

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.hana);
            setHana(new HanaParametersSpec()
            {{
                setHost("localhost");
//                setPort(testContainer.getMappedPort(HanaContainer.SYSTEM_PORT).toString());
                setPort("39017");
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