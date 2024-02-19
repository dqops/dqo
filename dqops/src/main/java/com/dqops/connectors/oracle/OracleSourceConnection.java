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
package com.dqops.connectors.oracle;

import com.dqops.connectors.*;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.*;
import com.dqops.utils.conversion.NumericTypeConverter;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Oracle source connection.
 */
@Component("oracle-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OracleSourceConnection extends AbstractJdbcSourceConnection {
    /**
     * Injection constructor for the oracle connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public OracleSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                  SecretValueProvider secretValueProvider,
                                  OracleConnectionProvider oracleConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, oracleConnectionProvider);
    }

    /**
     * Opens a connection before it can be used for executing any statements.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     */
    @Override
    public void open(SecretValueLookupContext secretValueLookupContext) {
        super.open(secretValueLookupContext);

        OracleParametersSpec oracleParametersSpec = this.getConnectionSpec().getOracle();
        if (!Strings.isNullOrEmpty(oracleParametersSpec.getInitializationSql())) {
            this.executeCommand(oracleParametersSpec.getInitializationSql(), JobCancellationToken.createDummyJobCancellationToken());
        }
    }

    /**
     * Returns the schema name of the INFORMATION_SCHEMA. Derived classes may return a databasename.INFORMATION_SCHEMA
     * if the information schema must be retrieved at a database level.
     * @return Information schema name.
     */
    @Override
    public String getInformationSchemaName() {
        return "ALL_TABLES";
    }

    /**
     * Returns a list of schemas from the source.
     *
     * @return List of schemas.
     */
    @Override
    public List<SourceSchemaModel> listSchemas() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT OWNER AS schema_name FROM ");
        sqlBuilder.append(getInformationSchemaName());
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
     * @param connectionWrapper Connection wrapper with a list of existing tables.
     * @return List of tables in the given schema.
     */
    @Override
    public List<SourceTableModel> listTables(String schemaName, ConnectionWrapper connectionWrapper) {
        ConnectionProviderSpecificParameters providerSpecificConfiguration = this.getConnectionSpec().getProviderSpecificConfiguration();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT OWNER AS table_schema, TABLE_NAME AS table_name FROM ");
        sqlBuilder.append(getInformationSchemaName());
        sqlBuilder.append("\n");
        sqlBuilder.append("WHERE OWNER='");
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
     * Retrieves the metadata (column information) for a given list of tables from a given schema.
     *
     * @param schemaName Schema name.
     * @param tableNames Table names.
     * @return List of table specifications with the column list.
     */
    @Override
    public List<TableSpec> retrieveTableMetadata(String schemaName, List<String> tableNames, ConnectionWrapper connectionWrapper) {
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
                boolean isNullable = !Objects.equals(colRow.getString("nullable"),"N");
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

                if (tableResult.containsColumn("char_length") &&
                        !colRow.isMissing("char_length")) {
                    columnType.setLength(NumericTypeConverter.toInt(colRow.getObject("char_length")));
                }
                else if (tableResult.containsColumn("data_length") &&
                        !colRow.isMissing("data_length")) {
                    columnType.setLength(NumericTypeConverter.toInt(colRow.getObject("data_length")));
                }

                if (tableResult.containsColumn("data_precision") &&
                        !colRow.isMissing("data_precision")) {
                    columnType.setPrecision(NumericTypeConverter.toInt(colRow.getObject("data_precision")));
                }

                if (tableResult.containsColumn("data_scale") &&
                        !colRow.isMissing("data_scale")) {
                    columnType.setPrecision(NumericTypeConverter.toInt(colRow.getObject("data_scale")));
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
    @Override
    public String buildListColumnsSql(String schemaName, List<String> tableNames) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM ALL_TAB_COLUMNS WHERE OWNER='");
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
        sqlBuilder.append("ORDER BY OWNER, TABLE_NAME, COLUMN_ID");
        String sql = sqlBuilder.toString();
        return sql;
    }

    /**
     * Creates a target table following the table specification.
     *
     * @param tableSpec Table specification with the physical table name, column names and physical column data types.
     */
    @Override
    public void createTable(TableSpec tableSpec) {
        ConnectionProviderSpecificParameters providerSpecificConfiguration = this.getConnectionSpec().getProviderSpecificConfiguration();

        ProviderDialectSettings dialectSettings = this.getDialectSettings();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE ");
        sqlBuilder.append(dialectSettings.quoteIdentifier(tableSpec.getPhysicalTableName().getSchemaName()));
        sqlBuilder.append(".");
        sqlBuilder.append(dialectSettings.quoteIdentifier(tableSpec.getPhysicalTableName().getTableName()));
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

        ConnectionProviderSpecificParameters providerSpecificConfiguration = this.getConnectionSpec().getProviderSpecificConfiguration();
        ProviderDialectSettings dialectSettings = this.getDialectSettings();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT ALL\n");

        for (int rowIndex = 0; rowIndex < data.rowCount(); rowIndex++) {
            sqlBuilder.append("  INTO ");
            sqlBuilder.append(dialectSettings.quoteIdentifier(tableSpec.getPhysicalTableName().getSchemaName()));
            sqlBuilder.append(".");
            sqlBuilder.append(dialectSettings.quoteIdentifier(tableSpec.getPhysicalTableName().getTableName()));
            sqlBuilder.append(" (");

            for (int i = 0; i < data.columnCount(); i++) {
                if (i > 0) {
                    sqlBuilder.append(", ");
                }
                sqlBuilder.append(dialectSettings.quoteIdentifier(data.column(i).name()));
            }
            sqlBuilder.append(") VALUES (");
            for (int colIndex = 0; colIndex < data.columnCount() ; colIndex++) {
                if (colIndex > 0) {
                    sqlBuilder.append(", ");
                }

                Column<?> column = data.column(colIndex);
                Object cellValue = column.isMissing(rowIndex) ? null : data.get(rowIndex, colIndex);
                ColumnSpec columnSpec = tableSpec.getColumns().get(column.name());

                String formattedConstant = this.getConnectionProvider().formatConstant(cellValue, columnSpec.getTypeSnapshot());
                sqlBuilder.append(formattedConstant);
            }

            sqlBuilder.append(")\n");
        }
        sqlBuilder.append("SELECT * FROM dual");

        String insertValueSql = sqlBuilder.toString();
        this.executeCommand(insertValueSql, JobCancellationToken.createDummyJobCancellationToken());
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that can be used in the connection names.
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        OracleParametersSpec oracleParametersSpec = connectionSpec.getOracle();

        String host = this.getSecretValueProvider().expandValue(oracleParametersSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:oracle:thin:@");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(oracleParametersSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to Oracle, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');
        String database = this.getSecretValueProvider().expandValue(oracleParametersSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (oracleParametersSpec.getProperties() != null) {
            dataSourceProperties.putAll(oracleParametersSpec.getProperties());
        }

        String userName = this.getSecretValueProvider().expandValue(oracleParametersSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(oracleParametersSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

        String options =  this.getSecretValueProvider().expandValue(oracleParametersSpec.getOptions(), secretValueLookupContext);
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
        try (OracleResultSet oracleResultSet = new OracleResultSet(results)) {
            Table resultTable = Table.read().db(oracleResultSet, sqlQueryStatement);
            return resultTable;
        }
    }

}
