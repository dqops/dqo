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
package com.dqops.connectors.postgresql;

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

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Postgresql source connection.
 */
@Component("postgresql-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PostgresqlSourceConnection extends AbstractJdbcSourceConnection {

    private final static Object driverRegisterLock = new Object();
    private static boolean driverRegistered = false;

    /**
     * Injection constructor for the postgresql connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public PostgresqlSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                      SecretValueProvider secretValueProvider,
                                      PostgresqlConnectionProvider postgresqlConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, postgresqlConnectionProvider);
    }

    /**
     * Manually registers the JDBC Driver allowing the control of the registration time.
     */
    private static void registerDriver(){
        if(driverRegistered){
            return;
        }
        try {
            synchronized (driverRegisterLock){
                Class.forName("org.postgresql.Driver");
                driverRegistered = true;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that can be used in the connection names.
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        registerDriver();

        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        PostgresqlParametersSpec postgresqlSpec = connectionSpec.getPostgresql();

        String host = this.getSecretValueProvider().expandValue(postgresqlSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:postgresql://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(postgresqlSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to PostgreSQL, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');
        String database = this.getSecretValueProvider().expandValue(postgresqlSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        dataSourceProperties.putIfAbsent("connectTimeout", 10);
        dataSourceProperties.putIfAbsent("loginTimeout", 10);

        if (postgresqlSpec.getSslmode() != null){
            dataSourceProperties.put("sslmode", postgresqlSpec.getSslmode().getValue());
        }

        if (postgresqlSpec.getProperties() != null) {
            dataSourceProperties.putAll(postgresqlSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        String userName = this.getSecretValueProvider().expandValue(postgresqlSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(postgresqlSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

        String options =  this.getSecretValueProvider().expandValue(postgresqlSpec.getOptions(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(options)) {
            dataSourceProperties.put("options", options);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }
}
