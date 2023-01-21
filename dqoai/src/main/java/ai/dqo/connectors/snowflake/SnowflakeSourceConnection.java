/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.connectors.snowflake;

import ai.dqo.connectors.jdbc.AbstractJdbcSourceConnection;
import ai.dqo.connectors.jdbc.JdbcConnectionPool;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.sources.ConnectionSpec;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Properties;

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
     *
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        SnowflakeParametersSpec snowflakeSpec = connectionSpec.getSnowflake();

        String snowflakeAccount = this.getSecretValueProvider().expandValue(snowflakeSpec.getAccount());
        hikariConfig.setJdbcUrl("jdbc:snowflake://" + snowflakeAccount + ".snowflakecomputing.com/");

        Properties dataSourceProperties = new Properties();
        if (snowflakeSpec.getProperties() != null) {
            dataSourceProperties.putAll(snowflakeSpec.getProperties());
        }
        hikariConfig.setDataSourceProperties(dataSourceProperties);

        String warehouse = this.getSecretValueProvider().expandValue(snowflakeSpec.getWarehouse());
        dataSourceProperties.put("warehouse", warehouse);

        String databaseName = this.getSecretValueProvider().expandValue(snowflakeSpec.getDatabase());
        dataSourceProperties.put("db", databaseName);

        String role = this.getSecretValueProvider().expandValue(snowflakeSpec.getRole());
        if (!Strings.isNullOrEmpty(role)) {
            dataSourceProperties.put("role", role);
        }

        String userName = this.getSecretValueProvider().expandValue(snowflakeSpec.getUser());
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(snowflakeSpec.getPassword());
        hikariConfig.setPassword(password);

        return hikariConfig;
    }
}
