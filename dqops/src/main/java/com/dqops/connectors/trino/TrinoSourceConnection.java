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
package com.dqops.connectors.trino;

import com.dqops.connectors.ConnectorOperationFailedException;
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

import java.util.Properties;

/**
 * Trino source connection.
 */
@Component("trino-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TrinoSourceConnection extends AbstractJdbcSourceConnection {
    /**
     * Injection constructor for the trino connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public TrinoSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                  SecretValueProvider secretValueProvider,
                                  TrinoConnectionProvider trinoConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, trinoConnectionProvider);
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        TrinoParametersSpec trinoSpec = connectionSpec.getTrino();

        String host = this.getSecretValueProvider().expandValue(trinoSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:trino://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(trinoSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to Trino, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');
        String database = this.getSecretValueProvider().expandValue(trinoSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        String userName = this.getSecretValueProvider().expandValue(trinoSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        //String password = this.getSecretValueProvider().expandValue(trinoSpec.getPassword(), secretValueLookupContext);
        //hikariConfig.setPassword(password);

        Properties dataSourceProperties = new Properties();
        if (trinoSpec.getProperties() != null) {
            dataSourceProperties.putAll(trinoSpec.getProperties());
        }
        hikariConfig.setDataSourceProperties(dataSourceProperties);

        return hikariConfig;
    }
}
