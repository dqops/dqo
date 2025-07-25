/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Microsoft SQL Server source connection.
 */
@Component("sqlserver-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SqlServerSourceConnection extends AbstractJdbcSourceConnection {

    private final static Object driverRegisterLock = new Object();
    private static boolean driverRegistered = false;

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
     * Manually registers the JDBC Driver allowing the control of the registration time.
     */
    private static void registerDriver(){
        if(driverRegistered){
            return;
        }
        try {
            synchronized (driverRegisterLock){
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                driverRegistered = true;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that can be used in the connection names.
     *
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        registerDriver();

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
            dataSourceProperties.putAll(sqlserverSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        SqlServerAuthenticationMode authenticationMode = sqlserverSpec.getAuthenticationMode() == null ?
                SqlServerAuthenticationMode.sql_password : sqlserverSpec.getAuthenticationMode();
        switch (authenticationMode){
            case sql_password:
            case active_directory_password:
                String userName = this.getSecretValueProvider().expandValue(sqlserverSpec.getUser(), secretValueLookupContext);
                hikariConfig.setUsername(userName);

                String password = this.getSecretValueProvider().expandValue(sqlserverSpec.getPassword(), secretValueLookupContext);
                hikariConfig.setPassword(password);

                String authenticationValue = getJdbcAuthenticationValue(authenticationMode);
                dataSourceProperties.put("authentication", authenticationValue);

                break;
            case active_directory_service_principal:
                // the below code use the user password instead of service principal tenant id, etc...
//                Optional<AzureCredential> azureCredential = azureCredentialsProvider.provideCredentials(secretValueLookupContext);
//                if(azureCredential.isPresent()
//                        && !azureCredential.get().getUser().isEmpty()
//                        && !azureCredential.get().getPassword().isEmpty()
//                        && !azureCredential.get().getAuthentication().isEmpty()
//                ){
//                    String defaultUserName = this.getSecretValueProvider().expandValue(azureCredential.get().getUser(), secretValueLookupContext);
//                    hikariConfig.setUsername(defaultUserName);
//
//                    String defaultPassword = this.getSecretValueProvider().expandValue(azureCredential.get().getPassword(), secretValueLookupContext);
//                    hikariConfig.setPassword(defaultPassword);
//
//                } else {
//                    // In case of authentication from local .azure folder (azure cli is installed, executed: az login and az account get-access-token), it got two issues:
//                    // 1. Token based login to Azure SQL end up with the error: Login failed for user '<token-identified principal>'. Incorrect or invalid token.
//                    // 2. Token generation has to be done out of the createHikariConfig method due to block method
//
//                    // TokenRequestContext tokenRequestContext = new TokenRequestContext().addScopes("https://management.azure.com/.default");
//                    // TokenCredential dacWithUserAssignedManagedIdentity = new DefaultAzureCredentialBuilder().build();
//                    // Mono<AccessToken> accessTokenMono = dacWithUserAssignedManagedIdentity.getToken(tokenRequestContext);
//                    // dataSourceProperties.put("accessToken", accessTokenMono.block().getToken());
//
//                    new RuntimeException("Can't use default credentials set in " + DefaultCloudCredentialFileNames.AZURE_DEFAULT_CREDENTIALS_NAME + " file.");
//                }
                throw new RuntimeException("active_directory_service_principal is not supported");
            case active_directory_default:
                String authenticationValueString = getJdbcAuthenticationValue(authenticationMode);
                dataSourceProperties.put("authentication", authenticationValueString);
                break;
            default:
                throw new RuntimeException("Given enum is not supported : " + authenticationMode);
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
            case active_directory_service_principal:    return "ActiveDirectoryServicePrincipal";
            case active_directory_default:              return "ActiveDirectoryDefault";
            default: throw new RuntimeException("Given enum is not supported : " + authenticationMode);
        }
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

    /**
     * Generates an SQL statement that lists tables.
     *
     * @param schemaName        Schema name.
     * @param tableNameContains Optional filter with a text that must be present in the tables returned.
     * @param limit             The limit of the number of tables to return.
     * @return SQL string for a query that lists tables.
     */
    @Override
    public @NotNull String buildListTablesSql(String schemaName, String tableNameContains, int limit) {
        String sql = super.buildListTablesSql(schemaName, tableNameContains, limit);
        String sqlWithTop = sql.replace("SELECT", "SELECT TOP " + limit);
        return sqlWithTop;
    }
}
