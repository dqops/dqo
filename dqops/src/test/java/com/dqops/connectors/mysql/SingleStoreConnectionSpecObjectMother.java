/*
 * Copyright © 2023 DQOps (support@dqops.com)
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
package com.dqops.connectors.mysql;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.mysql.singlestore.SingleStoreLoadBalancingMode;
import com.dqops.connectors.mysql.singlestore.SingleStoreParametersSpec;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderImpl;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

import java.util.List;
import java.util.Map;

/**
 * Object mother for a testable Single Store connection spec that provides access to the sandbox database.
 */
public class SingleStoreConnectionSpecObjectMother {

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

            SingleStoreParametersSpec singleStoreParametersSpec = new SingleStoreParametersSpec(){{
                setHostDescriptions(List.of(secretValueProvider.expandValue("${SINGLE_STORE_HOST_DESCRIPTIONS}", secretValueLookupContext)));
                setSingleStoreLoadBalancingMode(SingleStoreLoadBalancingMode.none);
            }};

			setMysql(new MysqlParametersSpec()
            {{
                setMysqlEngineType(MysqlEngineType.singlestore);
                setSingleStoreParametersSpec(singleStoreParametersSpec);
                setDatabase(secretValueProvider.expandValue("${SINGLE_STORE_DATABASE}", secretValueLookupContext));
                setUser(secretValueProvider.expandValue("${SINGLE_STORE_USERNAME}", secretValueLookupContext));
                setPassword(secretValueProvider.expandValue("${SINGLE_STORE_PASSWORD}", secretValueLookupContext));
                setProperties(Map.of("useSsl", "true"));    // todo: to it as a field
            }});
        }};

        return connectionSpec;
    }


    // todo: check below
    /**
     * Returns the default schema used for a testable athena database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "default";
    }
}
