/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.spark;

import com.dqops.connectors.*;
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
import java.util.stream.Collectors;

/**
 * Spark source connection.
 */
@Component("spark-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SparkSourceConnection extends AbstractJdbcSourceConnection {

    private final static Object driverRegisterLock = new Object();
    private static boolean driverRegistered = false;

    /**
     * Injection constructor for the spark connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public SparkSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                 SecretValueProvider secretValueProvider,
                                 SparkConnectionProvider sparkConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, sparkConnectionProvider);
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
                Class.forName("org.apache.hive.jdbc.HiveDriver");
                driverRegistered = true;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        registerDriver();

        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        SparkParametersSpec sparkParametersSpec = connectionSpec.getSpark();

        String host = this.getSecretValueProvider().expandValue(sparkParametersSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:hive2://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(sparkParametersSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to Spark, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (sparkParametersSpec.getProperties() != null) {
            dataSourceProperties.putAll(sparkParametersSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        String userName = this.getSecretValueProvider().expandValue(sparkParametersSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(sparkParametersSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

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
        sqlBuilder.append("SHOW DATABASES");
        String listSchemataSql = sqlBuilder.toString();
        Table schemaRows = this.executeQuery(listSchemataSql, JobCancellationToken.createDummyJobCancellationToken(), null, false);

        List<SourceSchemaModel> results = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < schemaRows.rowCount(); rowIndex++) {
            String namespace = schemaRows.getString(rowIndex, "namespace");
            SourceSchemaModel schemaModel = new SourceSchemaModel(namespace);
            results.add(schemaModel);
        }

        return results;
    }

    /**
     * Lists tables inside a schema. Views are also returned.
     *
     * @param schemaName Schema name.
     * @param tableNameContains Optional filter for a table name substring.
     * @param limit The limit of tables to return.
     * @param secretValueLookupContext Secret lookup context.
     * @return List of tables in the given schema.
     */
    @Override
    public List<SourceTableModel> listTables(String schemaName, String tableNameContains, int limit, SecretValueLookupContext secretValueLookupContext) {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SHOW tables FROM ");
        sqlBuilder.append(schemaName);

        String listTablesSql = sqlBuilder.toString();
        Table tablesRows = this.executeQuery(listTablesSql, JobCancellationToken.createDummyJobCancellationToken(), null, false);

        List<SourceTableModel> results = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < tablesRows.rowCount() ; rowIndex++) {
            String tableName = tablesRows.getString(rowIndex, "tablename");
            if (!Strings.isNullOrEmpty(tableNameContains)) {
                if (!tableName.contains(tableNameContains)) {
                    continue;
                }
            }

            PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
            SourceTableModel schemaModel = new SourceTableModel(schemaName, physicalTableName);
            results.add(schemaModel);

            if (results.size() >= limit) {
                break;
            }
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
     * @param tableNameContains Optional filter to limit the number of returned tables.
     * @param limit Limit of returned tables.
     * @param tableNames Table names.
     * @param connectionWrapper Parent connection.
     * @param secretValueLookupContext Secret lookup.
     * @return List of table specifications with the column list.
     */
    @Override
    public List<TableSpec> retrieveTableMetadata(String schemaName,
                                                 String tableNameContains,
                                                 int limit,
                                                 List<String> tableNames,
                                                 ConnectionWrapper connectionWrapper,
                                                 SecretValueLookupContext secretValueLookupContext) {
        assert !Strings.isNullOrEmpty(schemaName);

        try {
            List<TableSpec> tableSpecs = new ArrayList<>();

            for (String tableName : tableNames) {
                if (!Strings.isNullOrEmpty(tableNameContains)) {
                    if (!tableName.contains(tableNameContains)) {
                        continue;
                    }
                }

                String sql = "DESCRIBE " + schemaName + "." + tableName;
                tech.tablesaw.api.Table tableResult = this.executeQuery(sql, JobCancellationToken.createDummyJobCancellationToken(), null, false);
                Column<?>[] columns = tableResult.columnArray();
                for (Column<?> column : columns) {
                    column.setName(column.name().toLowerCase(Locale.ROOT));
                }

                HashMap<String, TableSpec> tablesByTableName = new LinkedHashMap<>();
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

                if (tableSpecs.size() >= limit) {
                    break;
                }
            }

            return tableSpecs;
        }
        catch (Exception ex) {
            throw new ConnectionQueryException(ex);
        }
    }

}
