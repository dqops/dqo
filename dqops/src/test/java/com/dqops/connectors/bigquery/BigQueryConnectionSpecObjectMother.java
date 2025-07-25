/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.bigquery;

import com.dqops.connectors.ProviderType;
import com.dqops.core.secrets.DevelopmentCredentialsSecretNames;
import com.dqops.core.secrets.SecretValueProviderObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;

/**
 * Object mother for a testable bigquery connection spec that provides access to the sandbox database.
 */
public class BigQueryConnectionSpecObjectMother {
    /**
     * Connection name to big query.
     */
    public static final String CONNECTION_NAME = "bigquery_connection";

    /**
     * GCP project id that is used for testing.
     */
    @Deprecated  // get the name from BigQueryConnectionSpecObjectMother.getGcpProjectId()
    public static final String GCP_PROJECT_ID = "dqo-ai-testing";

    /**
     * Creates a default connection spec to a sandbox bigquery database.
     * @return Connection spec to a sandbox environment.
     */
    @Deprecated // creates an old type of test data, we will use test tables that were auto created
    public static ConnectionSpec createLegacy() {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.bigquery);
			setBigquery(new BigQueryParametersSpec()
            {{
                setAuthenticationMode(BigQueryAuthenticationMode.google_application_credentials);
                setSourceProjectId(GCP_PROJECT_ID);
                setBillingProjectId(GCP_PROJECT_ID);
            }});
        }};
        return connectionSpec;
    }

    /**
     * Creates a default connection spec to a sandbox bigquery database.
     * @return Connection spec to a sandbox environment.
     */
    public static ConnectionSpec create() {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.bigquery);
			setBigquery(new BigQueryParametersSpec()
            {{
                setAuthenticationMode(BigQueryAuthenticationMode.google_application_credentials);
                setSourceProjectId(getGcpProjectId());
                setBillingProjectId(getGcpProjectId());
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable snowflake database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getGcpProjectId() {
        return SecretValueProviderObjectMother.resolveProperty(DevelopmentCredentialsSecretNames.BIGQUERY_PROJECT);
    }

    /**
     * Returns the default schema used for a testable snowflake database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getDatasetName() {
        return SecretValueProviderObjectMother.resolveProperty(DevelopmentCredentialsSecretNames.BIGQUERY_DATASET);
    }
}
