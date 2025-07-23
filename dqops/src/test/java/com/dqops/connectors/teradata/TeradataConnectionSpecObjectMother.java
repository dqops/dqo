/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
