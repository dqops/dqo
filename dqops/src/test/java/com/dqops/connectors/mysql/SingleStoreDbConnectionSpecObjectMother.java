/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.mysql;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.mysql.singlestore.SingleStoreDbLoadBalancingMode;
import com.dqops.connectors.mysql.singlestore.SingleStoreDbParametersSpec;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderImpl;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

import java.util.List;

/**
 * Object mother for a testable Single Store DB connection spec that provides access to the sandbox database.
 */
public class SingleStoreDbConnectionSpecObjectMother {

    /**
     * Creates a default connection spec to single store database.
     * @return Connection spec to a cloud single store environment.
     */
    public static ConnectionSpec create() {

        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        SecretValueProviderImpl secretValueProvider = beanFactory.getBean(SecretValueProviderImpl.class);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);

        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.mysql);

            SingleStoreDbParametersSpec singleStoreDbParametersSpec = new SingleStoreDbParametersSpec(){{
                setHostDescriptions(List.of(secretValueProvider.expandValue("${SINGLE_STORE_HOST_DESCRIPTIONS}", secretValueLookupContext)));
                setLoadBalancingMode(SingleStoreDbLoadBalancingMode.none);
                setSchema(secretValueProvider.expandValue("${SINGLE_STORE_DATABASE}", secretValueLookupContext));
                setUseSsl(true);
            }};

			setMysql(new MysqlParametersSpec()
            {{
                setMysqlEngineType(MysqlEngineType.singlestoredb);
                setSingleStoreDbParametersSpec(singleStoreDbParametersSpec);
                setUser(secretValueProvider.expandValue("${SINGLE_STORE_USERNAME}", secretValueLookupContext));
                setPassword(secretValueProvider.expandValue("${SINGLE_STORE_PASSWORD}", secretValueLookupContext));
            }});
        }};

        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable Single Store DB database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        SecretValueProviderImpl secretValueProvider = beanFactory.getBean(SecretValueProviderImpl.class);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);
        return secretValueProvider.expandValue("${SINGLE_STORE_DATABASE}", secretValueLookupContext);
    }
}
