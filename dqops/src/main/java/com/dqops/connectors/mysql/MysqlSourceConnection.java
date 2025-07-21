/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.mysql;

import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.SourceSchemaModel;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.connectors.mysql.singlestore.SingleStoreDbSourceConnection;
import com.dqops.core.jobqueue.JobCancellationToken;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * MySQL source connection.
 */
@Component("mysql-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MysqlSourceConnection extends AbstractJdbcSourceConnection {

    private final static Object driverRegisterLock = new Object();
    private static boolean mysqlDriverRegistered = false;
    private static boolean singleStoreDbDriverRegistered = false;

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
     * Generates an SQL statement that lists tables.
     *
     * @param schemaName        Schema name.
     * @param tableNameContains Optional filter with a text that must be present in the tables returned.
     * @param limit             The limit of the number of tables to return.
     * @return SQL string for a query that lists tables.
     */
    @Override
    public @NotNull String buildListTablesSql(String schemaName, String tableNameContains, int limit) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT TABLE_CATALOG AS table_catalog, TABLE_SCHEMA AS table_schema, TABLE_NAME AS table_name FROM ");
        sqlBuilder.append(getInformationSchemaName());
        sqlBuilder.append(".TABLES ");
        sqlBuilder.append("WHERE TABLE_SCHEMA='");
        sqlBuilder.append(schemaName.replace("'", "''"));
        sqlBuilder.append("'");

        if (!Strings.isNullOrEmpty(tableNameContains)) {
            sqlBuilder.append(" AND table_name LIKE '%");
            sqlBuilder.append(tableNameContains.replace("'", "''"));
            sqlBuilder.append("%'");
        }

        sqlBuilder.append(" LIMIT ");
        sqlBuilder.append(limit);

        String listTablesSql = sqlBuilder.toString();
        return listTablesSql;
    }

    /**
     * Returns a list of schemas from the source.
     *
     * @return List of schemas.
     */
    @Override
    public List<SourceSchemaModel> listSchemas() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT CATALOG_NAME AS catalog_name, SCHEMA_NAME as schema_name FROM ");
        sqlBuilder.append(getInformationSchemaName());
        sqlBuilder.append(".SCHEMATA WHERE SCHEMA_NAME <> 'information_schema' and SCHEMA_NAME <> 'performance_schema'");
        String listSchemataSql = sqlBuilder.toString();
        Table schemaRows = this.executeQuery(listSchemataSql, JobCancellationToken.createDummyJobCancellationToken(), null, false);

        List<SourceSchemaModel> results = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < schemaRows.rowCount() ; rowIndex++) {
            String schemaName = schemaRows.getString(rowIndex, "schema_name");
            SourceSchemaModel schemaModel = new SourceSchemaModel(schemaName);
            results.add(schemaModel);
        }

        return results;
    }

    /**
     * Manually registers the JDBC Driver allowing the control of the registration time.
     */
    private static void registerDriver(MysqlEngineType mysqlEngineType){
        if(mysqlEngineType.equals(MysqlEngineType.mysql)){
            if(mysqlDriverRegistered){
                return;
            }
            try {
                synchronized (driverRegisterLock){
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    mysqlDriverRegistered = true;
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if(mysqlEngineType.equals(MysqlEngineType.singlestoredb)){
            if(singleStoreDbDriverRegistered){
                return;
            }
            try {
                synchronized (driverRegisterLock){
                    Class.forName("com.singlestore.jdbc.Driver");
                    singleStoreDbDriverRegistered = true;
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that can be used in the connection names.
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        MysqlParametersSpec mysqlParametersSpec = this.getConnectionSpec().getMysql();

        registerDriver(mysqlParametersSpec.getMysqlEngineType());

        if (mysqlParametersSpec.getMysqlEngineType() == MysqlEngineType.singlestoredb) {
            return SingleStoreDbSourceConnection.createHikariConfig(
                    secretValueLookupContext,
                    mysqlParametersSpec,
                    this.getSecretValueProvider());
        } else {
            return createHikariConfigForMysql(secretValueLookupContext);
        }
    }

    /**
     * Creates a hikari connection pool config for the connection specification for mysql.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that can be used in the connection names.
     * @return Hikari config.
     */
    private HikariConfig createHikariConfigForMysql(SecretValueLookupContext secretValueLookupContext) {

        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        MysqlParametersSpec mysqlParametersSpec = connectionSpec.getMysql();

        String host = this.getSecretValueProvider().expandValue(mysqlParametersSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:mysql://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(mysqlParametersSpec.getPort(), secretValueLookupContext);
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
        String database = this.getSecretValueProvider().expandValue(mysqlParametersSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        } else {
            throw new ConnectorOperationFailedException("Cannot create a connection to MySQL, the database name is not provided");
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (mysqlParametersSpec.getSslmode() != null){
            dataSourceProperties.put("sslmode", mysqlParametersSpec.getSslmode().toString());
        }

        if (mysqlParametersSpec.getProperties() != null) {
            dataSourceProperties.putAll(mysqlParametersSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        String userName = this.getSecretValueProvider().expandValue(mysqlParametersSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(mysqlParametersSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
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
        try (MysqlResultSet mysqlResultSet = new MysqlResultSet(results)) {
            Table resultTable = Table.read().db(mysqlResultSet, sqlQueryStatement);
            return resultTable;
        }
    }

    /**
     * Creates an SQL for listing columns in the given tables.
     * @param schemaName Schema name (bigquery dataset name).
     * @param tableNames Table names to list.
     * @return SQL of the INFORMATION_SCHEMA query.
     */
    public String buildListColumnsSql(String schemaName, List<String> tableNames) {
        MysqlParametersSpec mysqlParametersSpec = getConnectionSpec().getMysql();
        if (mysqlParametersSpec.getMysqlEngineType() == MysqlEngineType.singlestoredb) {
            return SingleStoreDbSourceConnection.buildListColumnsSql(getConnectionSpec(), schemaName, tableNames, this.getInformationSchemaName());
        } else {
            return this.buildListColumnsSqlForMySql(schemaName, tableNames);
        }
    }

    /**
     * Creates an SQL for listing columns in the given tables.
     * @param schemaName Schema name (bigquery dataset name).
     * @param tableNames Table names to list.
     * @return SQL of the INFORMATION_SCHEMA query.
     */
    public String buildListColumnsSqlForMySql(String schemaName, List<String> tableNames) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM ");

        sqlBuilder.append(getInformationSchemaName());
        sqlBuilder.append(".COLUMNS ");
        sqlBuilder.append("WHERE TABLE_SCHEMA='");
        sqlBuilder.append(schemaName.replace("'", "''"));
        sqlBuilder.append("'");

        if (tableNames != null && tableNames.size() > 0) {
            sqlBuilder.append(" AND TABLE_NAME IN (");
            for (int ti = 0; ti < tableNames.size(); ti++) {
                String tableName = tableNames.get(ti);
                if (ti > 0) {
                    sqlBuilder.append(",");
                }
                sqlBuilder.append('\'');
                sqlBuilder.append(tableName.replace("'", "''"));
                sqlBuilder.append('\'');
            }
            sqlBuilder.append(") ");
        }
        sqlBuilder.append("ORDER BY TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, ORDINAL_POSITION");
        String sql = sqlBuilder.toString();
        return sql;
    }
}
