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
package com.dqops.connectors.duckdb;

import com.dqops.connectors.ProviderType;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderImpl;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for a testable DuckDB connection spec that provides access to the database.
 */
public class DuckdbConnectionSpecObjectMother {

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
			setProviderType(ProviderType.duckdb);

			setDuckdb(new DuckdbParametersSpec()
            {{
                setInMemory(true);
//                setDatabase();
            }});
        }};

        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable DuckDB database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "main";
    }

}