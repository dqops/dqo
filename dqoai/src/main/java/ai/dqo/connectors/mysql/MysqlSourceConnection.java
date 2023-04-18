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
package ai.dqo.connectors.mysql;

import ai.dqo.connectors.ConnectorOperationFailedException;
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
 * MySQL source connection.
 */
@Component("mysql-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MysqlSourceConnection extends AbstractJdbcSourceConnection {
    /**
     * Injection constructor for the MySQL connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public MysqlSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                 SecretValueProvider secretValueProvider,
                                 MysqlConnectionProvider mysqlConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, mysqlConnectionProvider);
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
        MysqlParametersSpec mysqlParametersSpec = connectionSpec.getMysql();

        String host = this.getSecretValueProvider().expandValue(mysqlParametersSpec.getHost());
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:mysql://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(mysqlParametersSpec.getPort());
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to MySQL, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');
        String database = this.getSecretValueProvider().expandValue(mysqlParametersSpec.getDatabase());
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (mysqlParametersSpec.getProperties() != null) {
            dataSourceProperties.putAll(mysqlParametersSpec.getProperties());
        }

        String userName = this.getSecretValueProvider().expandValue(mysqlParametersSpec.getUser());
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(mysqlParametersSpec.getPassword());
        hikariConfig.setPassword(password);

        String options =  this.getSecretValueProvider().expandValue(mysqlParametersSpec.getOptions());
        if (!Strings.isNullOrEmpty(options)) {
            dataSourceProperties.put("options", options);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }
}
