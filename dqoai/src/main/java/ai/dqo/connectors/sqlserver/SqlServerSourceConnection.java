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
package ai.dqo.connectors.sqlserver;

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
 * Microsoft SQL Server source connection.
 */
@Component("sqlserver-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SqlServerSourceConnection extends AbstractJdbcSourceConnection {

    /**
     * Injection constructor for the MS SQL Server connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public SqlServerSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                      SecretValueProvider secretValueProvider,
                                      SqlServerConnectionProvider sqlServerConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, sqlServerConnectionProvider);
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
        SqlServerParametersSpec sqlserverSpec = connectionSpec.getSqlserver();

        String host = this.getSecretValueProvider().expandValue(sqlserverSpec.getHost());
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:sqlserver://;serverName=");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(sqlserverSpec.getPort());
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(";port=");
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to SQLSERVER, the port number is invalid: " + port, nfe);
            }
        }
        String database = this.getSecretValueProvider().expandValue(sqlserverSpec.getDatabase());
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(";databaseName=");
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (sqlserverSpec.getProperties() != null) {
            dataSourceProperties.putAll(sqlserverSpec.getProperties());
        }

        String userName = this.getSecretValueProvider().expandValue(sqlserverSpec.getUser());
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(sqlserverSpec.getPassword());
        hikariConfig.setPassword(password);

        String options =  this.getSecretValueProvider().expandValue(sqlserverSpec.getOptions());
        if (!Strings.isNullOrEmpty(options)) {
            dataSourceProperties.put("options", options);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }
}
