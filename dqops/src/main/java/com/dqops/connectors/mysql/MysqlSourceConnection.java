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
package com.dqops.connectors.mysql;

import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.SourceSchemaModel;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.connectors.mysql.singlestore.SingleStoreSourceConnection;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
     * Lists tables inside a schema. Views are also returned.
     *
     * @param schemaName Schema name.
     * @return List of tables in the given schema.
     */
    @Override
    public List<SourceTableModel> listTables(String schemaName) {
        ConnectionProviderSpecificParameters providerSpecificConfiguration = this.getConnectionSpec().getProviderSpecificConfiguration();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT TABLE_CATALOG AS table_catalog, TABLE_SCHEMA AS table_schema, TABLE_NAME AS table_name FROM ");
        sqlBuilder.append(getInformationSchemaName());
        sqlBuilder.append(".TABLES ");
        sqlBuilder.append("WHERE TABLE_SCHEMA='");
        sqlBuilder.append(schemaName.replace("'", "''"));
        sqlBuilder.append("'");

        String listTablesSql = sqlBuilder.toString();
        Table tablesRows = this.executeQuery(listTablesSql, JobCancellationToken.createDummyJobCancellationToken(), null, false);

        List<SourceTableModel> results = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < tablesRows.rowCount() ; rowIndex++) {
            String tableName = tablesRows.getString(rowIndex, "table_name");
            PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
            SourceTableModel schemaModel = new SourceTableModel(schemaName, physicalTableName);
            results.add(schemaModel);
        }

        return results;
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
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        MysqlParametersSpec mysqlParametersSpec = this.getConnectionSpec().getMysql();

        switch(mysqlParametersSpec.getMysqlEngineType()){
            case singlestore:
                return SingleStoreSourceConnection.createHikariConfig(
                        secretValueLookupContext,
                        mysqlParametersSpec,
                        this.getSecretValueProvider());
            case mysql:
                return createHikariConfigForMysql(secretValueLookupContext);
            default:
                throw new RuntimeException("Given enum is not supported : " + mysqlParametersSpec.getMysqlEngineType());
        }
    }

    /**
     * Creates a hikari connection pool config for the connection specification for mysql.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
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
            dataSourceProperties.putAll(mysqlParametersSpec.getProperties());
        }

        String userName = this.getSecretValueProvider().expandValue(mysqlParametersSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(mysqlParametersSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

        String options =  this.getSecretValueProvider().expandValue(mysqlParametersSpec.getOptions(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(options)) {
            dataSourceProperties.put("options", options);
        }

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

}
