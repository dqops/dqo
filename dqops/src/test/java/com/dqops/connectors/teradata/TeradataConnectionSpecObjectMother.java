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
package com.dqops.connectors.teradata;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.snowflake.SnowflakeParametersSpec;
import com.dqops.core.secrets.DevelopmentCredentialsSecretNames;
import com.dqops.metadata.sources.ConnectionSpec;

/**
 * Object mother for a testable teradata connection spec that provides access to the sandbox database.
 */
public class TeradataConnectionSpecObjectMother {
    /**
     * Connection name to teradata.
     */
    public static final String CONNECTION_NAME = "teradata_connection";

    /**
     * Creates a default connection spec to a sandbox teradata database.
     * @return Connection spec to a sandbox environment.
     */
    public static ConnectionSpec create() {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.teradata);

			setTeradata(new TeradataParametersSpec()
            {{
                setHost("");
                setUser("");
                setPassword("");
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable snowflake database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "demo_user";
    }
}
