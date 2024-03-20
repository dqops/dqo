/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.connectors.snowflake;

import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ConnectionSpec;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Snowflake source connection.
 */
@Component("snowflake-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SnowflakeSourceConnection extends AbstractJdbcSourceConnection {
    /**
     * Injection constructor for the snowflake connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public SnowflakeSourceConnection(JdbcConnectionPool jdbcConnectionPool,
									 SecretValueProvider secretValueProvider,
									 SnowflakeConnectionProvider snowflakeConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, snowflakeConnectionProvider);
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that can be used in the connection names.
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        SnowflakeParametersSpec snowflakeSpec = connectionSpec.getSnowflake();

        String snowflakeAccount = this.getSecretValueProvider().expandValue(snowflakeSpec.getAccount(), secretValueLookupContext);
        hikariConfig.setJdbcUrl("jdbc:snowflake://" + snowflakeAccount + ".snowflakecomputing.com/");

        Properties dataSourceProperties = new Properties();
        if (snowflakeSpec.getProperties() != null) {
            dataSourceProperties.putAll(snowflakeSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        String warehouse = this.getSecretValueProvider().expandValue(snowflakeSpec.getWarehouse(), secretValueLookupContext);
        dataSourceProperties.put("warehouse", warehouse);

        String databaseName = this.getSecretValueProvider().expandValue(snowflakeSpec.getDatabase(), secretValueLookupContext);
        dataSourceProperties.put("db", databaseName);

        String role = this.getSecretValueProvider().expandValue(snowflakeSpec.getRole(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(role)) {
            dataSourceProperties.put("role", role);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);

        String userName = this.getSecretValueProvider().expandValue(snowflakeSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(snowflakeSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

        return hikariConfig;
    }
}
