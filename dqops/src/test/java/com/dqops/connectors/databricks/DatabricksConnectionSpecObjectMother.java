/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.databricks;

import com.dqops.connectors.ProviderType;
import com.dqops.core.secrets.DevelopmentCredentialsSecretNames;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderImpl;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

import java.util.Map;

/**
 * Object mother for a testable Databricks connection spec that provides access to the sandbox database.
 */
public class DatabricksConnectionSpecObjectMother {
    /**
     * Connection name to databricks.
     */
    public static final String CONNECTION_NAME = "databricks_connection";

    private static final int DATABRICKS_PORT = 443;

    /**
     * Creates a default connection spec to a sandbox databricks database.
     * @return Connection spec to a sandbox environment.
     */
    public static ConnectionSpec create() {

        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        SecretValueProviderImpl secretValueProvider = beanFactory.getBean(SecretValueProviderImpl.class);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.databricks);

			setDatabricks(new DatabricksParametersSpec()
            {{
                setHost(DevelopmentCredentialsSecretNames.AZURE_DATABRICKS_HOST);
                setPort(String.valueOf(DATABRICKS_PORT));
                setProperties(Map.of(
                        "HttpPath", DevelopmentCredentialsSecretNames.AZURE_DATABRICKS_HTTP_PATH,
                        "PWD", DevelopmentCredentialsSecretNames.AZURE_DATABRICKS_ACCESS_TOKEN
                ));
                setInitializationSql("SET ANSI_MODE=FALSE");
            }});
        }};
        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable databricks database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "default";
    }
}
