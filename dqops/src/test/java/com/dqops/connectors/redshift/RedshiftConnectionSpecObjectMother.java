/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.redshift;

import com.dqops.connectors.ProviderType;
import com.dqops.core.secrets.DevelopmentCredentialsSecretNames;
import com.dqops.metadata.sources.ConnectionSpec;

/**
 * Object mother for a testable redshift connection spec that provides access to the sandbox database.
 */
public class RedshiftConnectionSpecObjectMother {

    /**
     * Connection name to Redshift.
     */
    public static final String CONNECTION_NAME = "redshift_connection";

    /**
     * Creates a default connection spec to a sandbox redshift database.
     * @return Connection spec to a sandbox environment.
     */
    public static ConnectionSpec create() {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.redshift);
            setRedshift(new RedshiftParametersSpec()
            {{
                setHost(DevelopmentCredentialsSecretNames.REDSHIFT_HOST);
                setPort(DevelopmentCredentialsSecretNames.REDSHIFT_PORT);
                setDatabase(DevelopmentCredentialsSecretNames.REDSHIFT_DATABASE);
                setUser(DevelopmentCredentialsSecretNames.REDSHIFT_USERNAME);
                setPassword(DevelopmentCredentialsSecretNames.REDSHIFT_PASSWORD);
            }});
        }};
        return connectionSpec;
    }
    /**
     * Returns the default schema used for a testable redshift database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "public";
    }

}
