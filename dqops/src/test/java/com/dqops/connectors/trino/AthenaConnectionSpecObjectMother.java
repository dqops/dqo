/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.trino;

import com.dqops.connectors.ProviderType;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderImpl;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for a testable Athena connection spec that provides access to the sandbox database.
 */
public class AthenaConnectionSpecObjectMother {

    /**
     * Creates a default connection spec to athena database.
     * @return Connection spec to a cloud environment.
     */
    public static ConnectionSpec create() {

        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        SecretValueProviderImpl secretValueProvider = beanFactory.getBean(SecretValueProviderImpl.class);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.trino);

			setTrino(new TrinoParametersSpec()
            {{
                setAthenaRegion("eu-central-1");
                setCatalog("awsdatacatalog");
                setAthenaWorkGroup("primary");
                setAthenaOutputLocation("s3://dqops-athena-test/results/");
//                setProperties(Map.of("CredentialsProvider","DefaultChain"));    // can only by used in the local environment
                setTrinoEngineType(TrinoEngineType.athena);
            }});
        }};

        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable athena database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "default";
    }
}
