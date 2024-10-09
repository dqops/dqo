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
package com.dqops.connectors.mariadb;

import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.SourceSchemaModel;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * MariaDB source connection.
 */
@Component("mariadb-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MariaDbSourceConnection extends AbstractJdbcSourceConnection {

    private final static Object driverRegisterLock = new Object();
    private static boolean mariadbDriverRegistered = false;

    /**
     * Injection constructor for the MariaDB connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public MariaDbSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                   SecretValueProvider secretValueProvider,
                                   MariaDbConnectionProvider mariaDbConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, mariaDbConnectionProvider);
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
    private static void registerDriver(){
        if(mariadbDriverRegistered){
            return;
        }
        try {
            synchronized (driverRegisterLock){
                Class.forName("org.mariadb.jdbc.Driver");
                mariadbDriverRegistered = true;
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
        MariaDbParametersSpec mariaDbParametersSpec = connectionSpec.getMariadb();

        String host = this.getSecretValueProvider().expandValue(mariaDbParametersSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:mariadb://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(mariaDbParametersSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to MariaDB, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');
        String database = this.getSecretValueProvider().expandValue(mariaDbParametersSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        } else {
            throw new ConnectorOperationFailedException("Cannot create a connection to MariaDB, the database name is not provided");
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();

        if (mariaDbParametersSpec.getProperties() != null) {
            dataSourceProperties.putAll(mariaDbParametersSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        String userName = this.getSecretValueProvider().expandValue(mariaDbParametersSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(mariaDbParametersSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

//    /**
//     * Creates the tablesaw's Table from the ResultSet for the query execution
//     * @param results               ResultSet object that contains the data produced by a query
//     * @param sqlQueryStatement     SQL statement that returns a row set.
//     * @return Tabular result captured from the query.
//     * @throws SQLException
//     */
//    @Override
//    protected Table rawTableResultFromResultSet(ResultSet results, String sqlQueryStatement) throws SQLException {
//        try (MariaDbResultSet mysqlResultSet = new MariaDbResultSet(results)) {
//            Table resultTable = Table.read().db(mysqlResultSet, sqlQueryStatement);
//            return resultTable;
//        }
//    }

//    /**
//     * Creates an SQL for listing columns in the given tables.
//     * @param schemaName Schema name (bigquery dataset name).
//     * @param tableNames Table names to list.
//     * @return SQL of the INFORMATION_SCHEMA query.
//     */
//    public String buildListColumnsSql(String schemaName, List<String> tableNames) {
//        StringBuilder sqlBuilder = new StringBuilder();
//        sqlBuilder.append("SELECT * FROM ");
//
//        sqlBuilder.append(getInformationSchemaName());
//        sqlBuilder.append(".COLUMNS ");
//        sqlBuilder.append("WHERE TABLE_SCHEMA='");
//        sqlBuilder.append(schemaName.replace("'", "''"));
//        sqlBuilder.append("'");
//
//        if (tableNames != null && tableNames.size() > 0) {
//            sqlBuilder.append(" AND TABLE_NAME IN (");
//            for (int ti = 0; ti < tableNames.size(); ti++) {
//                String tableName = tableNames.get(ti);
//                if (ti > 0) {
//                    sqlBuilder.append(",");
//                }
//                sqlBuilder.append('\'');
//                sqlBuilder.append(tableName.replace("'", "''"));
//                sqlBuilder.append('\'');
//            }
//            sqlBuilder.append(") ");
//        }
//        sqlBuilder.append("ORDER BY TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, ORDINAL_POSITION");
//        String sql = sqlBuilder.toString();
//        return sql;
//    }
}
