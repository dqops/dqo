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
package com.dqops.connectors.databricks;

import com.dqops.connectors.ConnectionQueryException;
import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.SourceSchemaModel;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.connectors.jdbc.JdbcQueryFailedException;
import com.dqops.core.jobqueue.JobCancellationListenerHandle;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.*;
import com.dqops.utils.exceptions.RunSilently;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.sql.Statement;
import java.util.*;

/**
 * Databricks source connection.
 */
@Component("databricks-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DatabricksSourceConnection extends AbstractJdbcSourceConnection {
    /**
     * Injection constructor for the databricks connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public DatabricksSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                      SecretValueProvider secretValueProvider,
                                      DatabricksConnectionProvider databricksConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, databricksConnectionProvider);
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
        DatabricksParametersSpec databricksParametersSpec = connectionSpec.getDatabricks();

        String host = this.getSecretValueProvider().expandValue(databricksParametersSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:databricks://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(databricksParametersSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to Databricks, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (databricksParametersSpec.getProperties() != null) {
            dataSourceProperties.putAll(databricksParametersSpec.getProperties());
        }

        String catalog = this.getSecretValueProvider().expandValue(databricksParametersSpec.getCatalog(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(catalog)){
            dataSourceProperties.put("ConnCatalog", catalog);
        }

        String httpPath = this.getSecretValueProvider().expandValue(databricksParametersSpec.getHttpPath(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(httpPath)) {
            dataSourceProperties.put("HttpPath", httpPath);
        }

        String accessToken = this.getSecretValueProvider().expandValue(databricksParametersSpec.getAccessToken(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(accessToken)) {
            dataSourceProperties.put("PWD", accessToken);
        }

        String userName = this.getSecretValueProvider().expandValue(databricksParametersSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(databricksParametersSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

        String options = this.getSecretValueProvider().expandValue(databricksParametersSpec.getOptions(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(options)) {
            dataSourceProperties.put("options", options);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

    /**
     * Returns a list of schemas from the source.
     *
     * @return List of schemas.
     */
    @Override
    public List<SourceSchemaModel> listSchemas() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SHOW SCHEMAS");
        String listSchemataSql = sqlBuilder.toString();
        Table schemaRows = this.executeQuery(listSchemataSql, JobCancellationToken.createDummyJobCancellationToken(), null, false);

        List<SourceSchemaModel> results = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < schemaRows.rowCount(); rowIndex++) {
            String namespace = schemaRows.getString(rowIndex, "databaseName");
            SourceSchemaModel schemaModel = new SourceSchemaModel(namespace);
            results.add(schemaModel);
        }

        return results;
    }

    /**
     * Lists tables inside a schema. Views are also returned.
     *
     * @param schemaName Schema name.
     * @return List of tables in the given schema.
     */
    @Override
    public List<SourceTableModel> listTables(String schemaName) {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SHOW tables FROM ");
        sqlBuilder.append(schemaName);

        String listTablesSql = sqlBuilder.toString();
        Table tablesRows = this.executeQuery(listTablesSql, JobCancellationToken.createDummyJobCancellationToken(), null, false);

        List<SourceTableModel> results = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < tablesRows.rowCount() ; rowIndex++) {
            String tableName = tablesRows.getString(rowIndex, "tableName");
            PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
            SourceTableModel schemaModel = new SourceTableModel(schemaName, physicalTableName);
            results.add(schemaModel);
        }

        return results;
    }

    /**
     * Executes a provider specific SQL that runs a command DML/DDL command.
     *
     * @param sqlStatement SQL DDL or DML statement.
     * @param jobCancellationToken Job cancellation token, enables cancelling a running query.
     */
    @Override
    public long executeCommand(String sqlStatement, JobCancellationToken jobCancellationToken) {
        try {
            try (Statement statement = this.getJdbcConnection().createStatement()) {
                try (JobCancellationListenerHandle cancellationListenerHandle =
                             jobCancellationToken.registerCancellationListener(
                                     cancellationToken -> RunSilently.run(statement::cancel))) {
                    statement.execute(sqlStatement);
                    return 0;
                }
                finally {
                    jobCancellationToken.throwIfCancelled();
                }
            }
        }
        catch (Exception ex) {
            String connectionName = this.getConnectionSpec().getConnectionName();
            throw new JdbcQueryFailedException(
                    String.format("SQL statement failed: %s, connection: %s, SQL: %s", ex.getMessage(), connectionName, sqlStatement),
                    ex, sqlStatement, connectionName);
        }
    }

    /**
     * Retrieves the metadata (column information) for a given list of tables from a given schema.
     *
     * @param schemaName Schema name.
     * @param tableNames Table names.
     * @return List of table specifications with the column list.
     */
    @Override
    public List<TableSpec> retrieveTableMetadata(String schemaName, List<String> tableNames) {
        assert !Strings.isNullOrEmpty(schemaName);

        try {
            List<TableSpec> tableSpecs = new ArrayList<>();

            for (String tableName : tableNames) {
                String sql = String.format("DESCRIBE %s.%s", schemaName, tableName);
                Table tableResult = this.executeQuery(sql, JobCancellationToken.createDummyJobCancellationToken(), null, false);
                Column<?>[] columns = tableResult.columnArray();
                for (Column<?> column : columns) {
                    column.setName(column.name().toLowerCase(Locale.ROOT));
                }

                HashMap<String, TableSpec> tablesByTableName = new HashMap<>();
                TableSpec tableSpec = tablesByTableName.get(tableName);
                if (tableSpec == null) {
                    tableSpec = new TableSpec();
                    tableSpec.setPhysicalTableName(new PhysicalTableName(schemaName, tableName));
                    tablesByTableName.put(tableName, tableSpec);
                    tableSpecs.add(tableSpec);
                }

                for (Row colRow : tableResult) {
                    String columnName = colRow.getString("col_name");
                    String dataType = colRow.getString("data_type");
                    boolean isNullable = true; //  todo: the following statement returns this information, but it has to be parsed: SHOW TABLE EXTENDED like 'table_name_here'

                    ColumnSpec columnSpec = new ColumnSpec();
                    ColumnTypeSnapshotSpec columnType = ColumnTypeSnapshotSpec.fromType(dataType);

                    // todo: lengths and precisions if applicable
//                if (tableResult.containsColumn("character_maximum_length") &&
//                        !colRow.isMissing("character_maximum_length")) {
//                    columnType.setLength(NumericTypeConverter.toInt(colRow.getObject("character_maximum_length")));
//                }
//                else if (tableResult.containsColumn("character_octet_length") &&
//                        !colRow.isMissing("character_octet_length")) {
//                    columnType.setLength(NumericTypeConverter.toInt(colRow.getObject("character_octet_length")));
//                }
//
//                if (tableResult.containsColumn("numeric_precision") &&
//                        !colRow.isMissing("numeric_precision")) {
//                    columnType.setPrecision(NumericTypeConverter.toInt(colRow.getObject("numeric_precision")));
//                }
//
//                if (tableResult.containsColumn("numeric_scale") &&
//                        !colRow.isMissing("numeric_scale")) {
//                    columnType.setPrecision(NumericTypeConverter.toInt(colRow.getObject("numeric_scale")));
//                }
//
//                if (tableResult.containsColumn("datetime_precision") &&
//                        !colRow.isMissing("datetime_precision")) {
//                    columnType.setPrecision(NumericTypeConverter.toInt(colRow.getObject("datetime_precision")));
//                }
//
//                if (tableResult.containsColumn("interval_precision") &&
//                        !colRow.isMissing("interval_precision")) {
//                    columnType.setPrecision(NumericTypeConverter.toInt(colRow.getObject("interval_precision")));
//                }

                    columnType.setNullable(isNullable);
                    columnSpec.setTypeSnapshot(columnType);
                    tableSpec.getColumns().put(columnName, columnSpec);
                }

            }

            return tableSpecs;
        }
        catch (Exception ex) {
            throw new ConnectionQueryException(ex);
        }
    }

}
