/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.snowflake;

import com.dqops.connectors.ProviderType;
import com.dqops.core.secrets.DevelopmentCredentialsSecretNames;
import com.dqops.metadata.sources.ConnectionSpec;

/**
 * Object mother for a testable snowflake connection spec that provides access to the sandbox database.
 */
public class SnowflakeConnectionSpecObjectMother {
    /**
     * Connection name to snowflake.
     */
    public static final String CONNECTION_NAME = "snowflake_connection";

    /**
     * Creates a default connection spec to a sandbox snowflake database.
     * @return Connection spec to a sandbox environment.
     */
    public static ConnectionSpec create() {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.snowflake);

			setSnowflake(new SnowflakeParametersSpec()
            {{
				setAccount(DevelopmentCredentialsSecretNames.SNOWFLAKE_ACCOUNT);
				setWarehouse(DevelopmentCredentialsSecretNames.SNOWFLAKE_WAREHOUSE);
                setDatabase(DevelopmentCredentialsSecretNames.SNOWFLAKE_DATABASE);
                setUser(DevelopmentCredentialsSecretNames.SNOWFLAKE_USER);
                setPassword(DevelopmentCredentialsSecretNames.SNOWFLAKE_PASSWORD);
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
