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
package com.dqops.connectors;

import com.dqops.connectors.mysql.MysqlEngineType;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.*;
import com.dqops.utils.conversion.NumericTypeConverter;
import org.apache.parquet.Strings;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.util.*;

/**
 * Base class for source connections that are using SQL. The derived providers can reuse the logic for querying the metadata using the INFORMATION_SCHEMA management views.
 */
public abstract class AbstractSqlSourceConnection implements SourceConnection {
    private ConnectionSpec connectionSpec;
    private final SecretValueProvider secretValueProvider;
    private final ConnectionProvider connectionProvider;

    /**
     * Creates an sql  connection source.
     * @param secretValueProvider Secret manager to expand parameters.
     * @param connectionProvider Connection provider that created the connection, used to get the dialect settings.
     */
    public AbstractSqlSourceConnection(SecretValueProvider secretValueProvider,
									   ConnectionProvider connectionProvider) {
        this.secretValueProvider = secretValueProvider;
        this.connectionProvider = connectionProvider;
    }

    /**
     * Sets the connection spec.
     * @param connectionSpec Connection spec.
     */
    public void setConnectionSpec(ConnectionSpec connectionSpec) {
        this.connectionSpec = connectionSpec;
    }

    /**
     * Returns a connection specification.
     *
     * @return Connection specification.
     */
    @Override
    public ConnectionSpec getConnectionSpec() {
        return this.connectionSpec;
    }

    /**
     * Secret manager dependency to expand secret values.
     * @return Secret manager.
     */
    public SecretValueProvider getSecretValueProvider() {
        return secretValueProvider;
    }

    /**
     * Parent connection provider that created this source connection.
     * @return Connection provider to access the dialect settings.
     */
    public ConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    /**
     * Returns the dialect settings for the current connection.
     * @return Dialect settings.
     */
    public ProviderDialectSettings getDialectSettings() {
        return this.connectionProvider.getDialectSettings(this.connectionSpec);
    }

    /**
     * Opens a connection before it can be used for executing any statements.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     */
    @Override
    public abstract void open(SecretValueLookupContext secretValueLookupContext);

    /**
     * Closes a connection.
     */
    @Override
    public abstract void close();

    /**
     * Returns the schema name of the INFORMATION_SCHEMA. Derived classes may return a databasename.INFORMATION_SCHEMA
     * if the information schema must be retrieved at a database level.
     * @return Information schema name.
     */
    public String getInformationSchemaName() {
        ConnectionProviderSpecificParameters providerSpecificConfiguration = this.getConnectionSpec().getProviderSpecificConfiguration();

        if (this.getDialectSettings().isTableNameIncludesDatabaseName() && !Strings.isNullOrEmpty(providerSpecificConfiguration.getDatabase())) {
            return this.getDialectSettings().quoteIdentifier(providerSpecificConfiguration.getDatabase()) + ".INFORMATION_SCHEMA";
        }
        return "INFORMATION_SCHEMA";
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
        sqlBuilder.append(".SCHEMATA WHERE SCHEMA_NAME <> 'INFORMATION_SCHEMA'");
        String listSchemataSql = sqlBuilder.toString();
        Table schemaRows = this.executeQuery(listSchemataSql, JobCancellationToken.createDummyJobCancellationToken(), null, false);

        List<SourceSchemaModel> results = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < schemaRows.rowCount(); rowIndex++) {
            String schemaName = schemaRows.getString(rowIndex, "schema_name");
            SourceSchemaModel schemaModel = new SourceSchemaModel(schemaName);
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
        ConnectionProviderSpecificParameters providerSpecificConfiguration = this.getConnectionSpec().getProviderSpecificConfiguration();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT TABLE_CATALOG AS table_catalog, TABLE_SCHEMA AS table_schema, TABLE_NAME AS table_name FROM ");
        sqlBuilder.append(getInformationSchemaName());
        sqlBuilder.append(".TABLES\n");
        sqlBuilder.append("WHERE table_schema='");
        sqlBuilder.append(schemaName.replace("'", "''"));
        sqlBuilder.append("'");
        String databaseName = providerSpecificConfiguration.getDatabase();
        if (!Strings.isNullOrEmpty(databaseName)) {
            sqlBuilder.append(" AND table_catalog='");
            sqlBuilder.append(databaseName.replace("'", "''"));
            sqlBuilder.append("'");
        }

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
            String sql = buildListColumnsSql(schemaName, tableNames);
            tech.tablesaw.api.Table tableResult = this.executeQuery(sql, JobCancellationToken.createDummyJobCancellationToken(), null, false);
            Column<?>[] columns = tableResult.columnArray();
            for (Column<?> column : columns) {
                column.setName(column.name().toLowerCase(Locale.ROOT));
            }

            HashMap<String, TableSpec> tablesByTableName = new LinkedHashMap<>();

            for (Row colRow : tableResult) {
                String physicalTableName = colRow.getString("table_name");
                String columnName = colRow.getString("column_name");
                boolean isNullable = Objects.equals(colRow.getString("is_nullable"),"YES");
                String dataType = colRow.getString("data_type");

                TableSpec tableSpec = tablesByTableName.get(physicalTableName);
                if (tableSpec == null) {
                    tableSpec = new TableSpec();
                    tableSpec.setPhysicalTableName(new PhysicalTableName(schemaName, physicalTableName));
                    tablesByTableName.put(physicalTableName, tableSpec);
                    tableSpecs.add(tableSpec);
                }

                ColumnSpec columnSpec = new ColumnSpec();
                ColumnTypeSnapshotSpec columnType = ColumnTypeSnapshotSpec.fromType(dataType);

                if (tableResult.containsColumn("character_maximum_length") &&
                        !colRow.isMissing("character_maximum_length")) {
                    columnType.setLength(NumericTypeConverter.toInt(colRow.getObject("character_maximum_length")));
                }
                else if (tableResult.containsColumn("character_octet_length") &&
                        !colRow.isMissing("character_octet_length")) {
                    columnType.setLength(NumericTypeConverter.toInt(colRow.getObject("character_octet_length")));
                }

                if (tableResult.containsColumn("numeric_precision") &&
                        !colRow.isMissing("numeric_precision")) {
                    columnType.setPrecision(NumericTypeConverter.toInt(colRow.getObject("numeric_precision")));
                }

                if (tableResult.containsColumn("numeric_scale") &&
                        !colRow.isMissing("numeric_scale")) {
                    columnType.setPrecision(NumericTypeConverter.toInt(colRow.getObject("numeric_scale")));
                }

                if (tableResult.containsColumn("datetime_precision") &&
                        !colRow.isMissing("datetime_precision")) {
                    columnType.setPrecision(NumericTypeConverter.toInt(colRow.getObject("datetime_precision")));
                }

                if (tableResult.containsColumn("interval_precision") &&
                        !colRow.isMissing("interval_precision")) {
                    columnType.setPrecision(NumericTypeConverter.toInt(colRow.getObject("interval_precision")));
                }

                columnType.setNullable(isNullable);
                columnSpec.setTypeSnapshot(columnType);
                tableSpec.getColumns().put(columnName, columnSpec);
            }

            return tableSpecs;
        }
        catch (Exception ex) {
            throw new ConnectionQueryException(ex);
        }
    }

    /**
     * Creates an SQL for listing columns in the given tables.
     * @param schemaName Schema name (bigquery dataset name).
     * @param tableNames Table names to list.
     * @return SQL of the INFORMATION_SCHEMA query.
     */
    public String buildListColumnsSql(String schemaName, List<String> tableNames) {
        ConnectionProviderSpecificParameters providerSpecificConfiguration = this.getConnectionSpec().getProviderSpecificConfiguration();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM ");

        String databaseName = providerSpecificConfiguration.getDatabase();
        sqlBuilder.append(getInformationSchemaName());
        sqlBuilder.append(".COLUMNS ");
        sqlBuilder.append("WHERE TABLE_SCHEMA='");
        sqlBuilder.append(schemaName.replace("'", "''"));
        sqlBuilder.append("'");

        if (!Strings.isNullOrEmpty(databaseName)) {
            sqlBuilder.append(" AND TABLE_CATALOG='");
            sqlBuilder.append(databaseName.replace("'", "''"));
            sqlBuilder.append("'");
        }

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

    /**
     * Executes a provider specific SQL that returns a query. For example a SELECT statement or any other SQL text that also returns rows.
     *
     * @param sqlQueryStatement SQL statement that returns a row set.
     * @param jobCancellationToken Job cancellation token, enables cancelling a running query.
     * @param maxRows Maximum rows limit.
     * @param failWhenMaxRowsExceeded Throws an exception if the maximum number of rows is exceeded.
     * @return Tabular result captured from the query.
     */
    @Override
    public abstract Table executeQuery(String sqlQueryStatement,
                                       JobCancellationToken jobCancellationToken,
                                       Integer maxRows,
                                       boolean failWhenMaxRowsExceeded);

    /**
     * Executes a provider specific SQL that runs a command DML/DDL command.
     *
     * @param sqlStatement SQL DDL or DML statement.
     * @param jobCancellationToken Job cancellation token, enables cancelling a running query.
     */
    @Override
    public abstract long executeCommand(String sqlStatement, JobCancellationToken jobCancellationToken);

    /**
     * Creates a target table following the table specification.
     *
     * @param tableSpec Table specification with the physical table name, column names and physical column data types.
     */
    @Override
    public void createTable(TableSpec tableSpec) {

        ProviderDialectSettings dialectSettings = this.connectionProvider.getDialectSettings(this.getConnectionSpec());
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE ");
        sqlBuilder.append(makeFullyQualifiedTableName(tableSpec));
        sqlBuilder.append(" (\n");

        Map.Entry<String, ColumnSpec> [] columnKeyPairs = tableSpec.getColumns().entrySet().toArray(Map.Entry[]::new);
        for(int i = 0; i < columnKeyPairs.length ; i++) {
            Map.Entry<String, ColumnSpec> columnKeyPair = columnKeyPairs[i];

            if (i != 0) { // not the first iteration
                sqlBuilder.append(",\n");
            }

            sqlBuilder.append("    ");
            sqlBuilder.append(dialectSettings.quoteIdentifier(columnKeyPair.getKey()));
            sqlBuilder.append(" ");
            ColumnTypeSnapshotSpec typeSnapshot = columnKeyPair.getValue().getTypeSnapshot();
            sqlBuilder.append(typeSnapshot.getColumnType());
            if (typeSnapshot.getLength() != null) {
                sqlBuilder.append("(");
                sqlBuilder.append(typeSnapshot.getLength());
                sqlBuilder.append(")");
            }
            else if (typeSnapshot.getPrecision() != null && typeSnapshot.getScale() != null) {
                sqlBuilder.append("(");
                sqlBuilder.append(typeSnapshot.getPrecision());
                sqlBuilder.append(",");
                sqlBuilder.append(typeSnapshot.getScale());
                sqlBuilder.append(")");
            }

            if (typeSnapshot.getNullable() != null) {
                boolean isNullable = typeSnapshot.getNullable();
                if (isNullable) {
                    sqlBuilder.append(" NULL");
                }
                else {
                    sqlBuilder.append(" NOT NULL");
                }
            }
        }

        sqlBuilder.append("\n)");

        String createTableSql = sqlBuilder.toString();
		this.executeCommand(createTableSql, JobCancellationToken.createDummyJobCancellationToken());
    }

    /**
     * Loads data into a table <code>tableSpec</code>.
     *
     * @param tableSpec Target table specification.
     * @param data      Dataset with the expected data.
     */
    @Override
    public void loadData(TableSpec tableSpec, Table data) {
        if (data.rowCount() == 0) {
            return;
        }

        ProviderDialectSettings dialectSettings = this.connectionProvider.getDialectSettings(this.getConnectionSpec());
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO ");
        sqlBuilder.append(makeFullyQualifiedTableName(tableSpec));
        sqlBuilder.append("(");
        for (int i = 0; i < data.columnCount() ; i++) {
            if (i > 0) {
                sqlBuilder.append(",");
            }
            sqlBuilder.append(dialectSettings.quoteIdentifier(data.column(i).name()));
        }
        sqlBuilder.append(")");
        sqlBuilder.append(" VALUES\n");

        for (int rowIndex = 0; rowIndex < data.rowCount() ; rowIndex++) {
            if (rowIndex > 0) {
                sqlBuilder.append(",\n");
            }

            sqlBuilder.append('(');
            for (int colIndex = 0; colIndex < data.columnCount() ; colIndex++) {
                if (colIndex > 0) {
                    sqlBuilder.append(",\n");
                }

                Column<?> column = data.column(colIndex);
                Object cellValue = column.isMissing(rowIndex) ? null : data.get(rowIndex, colIndex);
                ColumnSpec columnSpec = tableSpec.getColumns().get(column.name());

                String formattedConstant = this.connectionProvider.formatConstant(cellValue, columnSpec.getTypeSnapshot());
                sqlBuilder.append(formattedConstant);
            }

            sqlBuilder.append(')');
        }

        String insertValueSql = sqlBuilder.toString();
		this.executeCommand(insertValueSql, JobCancellationToken.createDummyJobCancellationToken());
    }

    public void dropTable(TableSpec tableSpec){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("DROP TABLE ");
        sqlBuilder.append(makeFullyQualifiedTableName(tableSpec));

        String insertValueSql = sqlBuilder.toString();
        this.executeCommand(insertValueSql, JobCancellationToken.createDummyJobCancellationToken());
    }

    private String makeFullyQualifiedTableName(TableSpec tableSpec){
        ConnectionProviderSpecificParameters providerSpecificConfiguration = this.getConnectionSpec().getProviderSpecificConfiguration();
        ProviderDialectSettings dialectSettings = this.connectionProvider.getDialectSettings(this.getConnectionSpec());

        StringBuilder tableNameBuilder = new StringBuilder();
        if (dialectSettings.isTableNameIncludesDatabaseName() && !Strings.isNullOrEmpty(providerSpecificConfiguration.getDatabase())) {
            tableNameBuilder.append(dialectSettings.quoteIdentifier(providerSpecificConfiguration.getDatabase()));
            tableNameBuilder.append(".");
        }
        tableNameBuilder.append(dialectSettings.quoteIdentifier(tableSpec.getPhysicalTableName().getSchemaName()));
        tableNameBuilder.append(".");
        tableNameBuilder.append(dialectSettings.quoteIdentifier(tableSpec.getPhysicalTableName().getTableName()));
        return tableNameBuilder.toString();
    }

}
