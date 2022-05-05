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
package ai.dqo.connectors.snowflake;

import ai.dqo.connectors.ProviderType;
import ai.dqo.core.secrets.DevelopmentCredentialsSecretNames;
import ai.dqo.metadata.sources.ConnectionSpec;

/**
 * Object mother for a testable snowflake connection spec that provides access to the sandbox database.
 */
public class SnowflakeConnectionSpecObjectMother {
    /**
     * Connection name to big query.
     */
    public static final String CONNECTION_NAME = "snowflake_connection";

    /**
     * Creates a default connection spec to a sandbox bigquery database.
     * @return Connection spec to a sandbox environment.
     */
    public static ConnectionSpec create() {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.snowflake);
			setDatabaseName(DevelopmentCredentialsSecretNames.SNOWFLAKE_DATABASE);
			setUser(DevelopmentCredentialsSecretNames.SNOWFLAKE_USER);
			setPassword(DevelopmentCredentialsSecretNames.SNOWFLAKE_PASSWORD);

			setSnowflake(new SnowflakeParametersSpec()
            {{
				setAccount(DevelopmentCredentialsSecretNames.SNOWFLAKE_ACCOUNT);
				setWarehouse(DevelopmentCredentialsSecretNames.SNOWFLAKE_WAREHOUSE);
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable snowflake database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "PUBLIC";
    }
}
