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
package com.dqops.connectors.sqlserver;

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
import tech.tablesaw.api.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
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
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     *
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        SqlServerParametersSpec sqlserverSpec = connectionSpec.getSqlserver();

        String host = this.getSecretValueProvider().expandValue(sqlserverSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:sqlserver://;serverName=");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(sqlserverSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(";port="); // todo: isn't it a property?
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to SQLSERVER, the port number is invalid: " + port, nfe);
            }
        }
        String database = this.getSecretValueProvider().expandValue(sqlserverSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(";databaseName="); // todo: isn't it a property?
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();

        if(sqlserverSpec.getDisableEncryption() != null && sqlserverSpec.getDisableEncryption()){
            dataSourceProperties.put("encrypt", "false");
        }

        if (sqlserverSpec.getProperties() != null) {
            dataSourceProperties.putAll(sqlserverSpec.getProperties());
        }

        switch (sqlserverSpec.getAuthenticationMode()){
            case sql_password:
            case active_directory_password:
            case active_directory_managed_identity:
            case active_directory_service_principal:

                String userName = this.getSecretValueProvider().expandValue(sqlserverSpec.getUser(), secretValueLookupContext);
                hikariConfig.setUsername(userName);

                String password = this.getSecretValueProvider().expandValue(sqlserverSpec.getPassword(), secretValueLookupContext);
                hikariConfig.setPassword(password);

                break;
            case default_credential:
                    // todo:

                break;
            default:
                new RuntimeException("Given enum is not supported : " + sqlserverSpec.getAuthenticationMode());
        }

        String authenticationValue = getJdbcAuthenticationValue(sqlserverSpec.getAuthenticationMode());
        dataSourceProperties.put("authentication", authenticationValue);

        String options =  this.getSecretValueProvider().expandValue(sqlserverSpec.getOptions(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(options)) {
            dataSourceProperties.put("options", options);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

    /**
     * Returns the value for the key "authentication" for the jdbc connection string.
     * @param authenticationMode
     * @return
     */
    private String getJdbcAuthenticationValue(SqlServerAuthenticationMode authenticationMode){
        switch (authenticationMode){
            case sql_password:                          return "SqlPassword";
            case active_directory_password:             return "ActiveDirectoryPassword";
            case active_directory_managed_identity:     return "ActiveDirectoryManagedIdentity";
            case active_directory_service_principal:    return "ActiveDirectoryServicePrincipal";
            case default_credential:                    return null; // todo
            default: new RuntimeException("Given enum is not supported : " + authenticationMode);
        }
        return null;
    }

    /**
     * Creates the tablesaw's Table from the ResultSet for the query execution
     * @param results               ResultSet object that contains the data produced by a query
     * @param sqlQueryStatement     SQL statement that returns a row set.
     * @return Tabular result captured from the query.
     * @throws SQLException
     */
    @Override
    protected Table rawTableResultFromResultSet(ResultSet results, String sqlQueryStatement) throws SQLException {
        try (SqlServerResultSet sqlServerResultSet = new SqlServerResultSet(results)) {
            Table resultTable = Table.read().db(sqlServerResultSet, sqlQueryStatement);
            return resultTable;
        }
    }

}
