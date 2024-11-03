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
package com.dqops.connectors.clickhouse;

import com.dqops.connectors.*;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.*;
import com.dqops.utils.conversion.NumericTypeConverter;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.jetbrains.annotations.NotNull;
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
import java.util.stream.Collectors;

/**
 * ClickHouse source connection.
 */
@Component("clickhouse-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClickHouseSourceConnection extends AbstractJdbcSourceConnection {

    private final static Object driverRegisterLock = new Object();
    private static boolean driverRegistered = false;
    private final ClickHouseConnectionProvider clickHouseConnectionProvider;

    /**
     * Injection constructor for the ClickHouse connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public ClickHouseSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                      SecretValueProvider secretValueProvider,
                                      ClickHouseConnectionProvider clickHouseConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, clickHouseConnectionProvider);
        this.clickHouseConnectionProvider = clickHouseConnectionProvider;
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
                Class.forName("com.clickhouse.jdbc.ClickHouseDriver");
                driverRegistered = true;
            }
        } catch (ClassNotFoundException e) {
            throw new DqoRuntimeException("Please contact DQOps sales team to get access to a commercial version of DQOps. " +
                    "ClickHouse drivers are not provided in an open-source version due to license limitations.");
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
        ClickHouseParametersSpec clickHouseParametersSpec = connectionSpec.getClickhouse();

        String host = this.getSecretValueProvider().expandValue(clickHouseParametersSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:clickhouse://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(clickHouseParametersSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to ClickHouse, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (clickHouseParametersSpec.getProperties() != null) {
            dataSourceProperties.putAll(clickHouseParametersSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        String userName = this.getSecretValueProvider().expandValue(clickHouseParametersSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(clickHouseParametersSpec.getPassword(), secretValueLookupContext);
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
        try (ClickHouseResultSet clickHouseResultSet = new ClickHouseResultSet(results)) {
            Table resultTable = Table.read().db(clickHouseResultSet, sqlQueryStatement);
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
        ConnectionProviderSpecificParameters providerSpecificConfiguration = this.getConnectionSpec().getProviderSpecificConfiguration();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, ORDINAL_POSITION, COLUMN_DEFAULT, IS_NULLABLE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION, NUMERIC_PRECISION_RADIX, NUMERIC_SCALE, DATETIME_PRECISION, CHARACTER_SET_CATALOG, CHARACTER_SET_SCHEMA, CHARACTER_SET_NAME, COLLATION_CATALOG, COLLATION_SCHEMA, COLLATION_NAME, DOMAIN_CATALOG, DOMAIN_SCHEMA, DOMAIN_NAME, EXTRA, COLUMN_COMMENT, COLUMN_TYPE ");
        sqlBuilder.append("FROM ");
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
     * Generates an SQL statement that lists tables.
     * @param schemaName Schema name.
     * @param tableNameContains Optional filter with a text that must be present in the tables returned.
     * @param limit The limit of the number of tables to return.
     * @return SQL string for a query that lists tables.
     */
    @NotNull
    public String buildListTablesSql(String schemaName, String tableNameContains, int limit) {
        ConnectionProviderSpecificParameters providerSpecificConfiguration = this.getConnectionSpec().getProviderSpecificConfiguration();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT table_schema AS table_schema, TABLE_NAME AS table_name FROM ");
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

        if (!Strings.isNullOrEmpty(tableNameContains)) {
            sqlBuilder.append(" AND table_name LIKE '%");
            sqlBuilder.append(tableNameContains.replace("'", "''"));
            sqlBuilder.append("%'");
        }

        ProviderDialectSettings dialectSettings = this.clickHouseConnectionProvider.getDialectSettings(this.getConnectionSpec());
        if (dialectSettings.isSupportsLimitClause()) {
            sqlBuilder.append(" LIMIT ");
            sqlBuilder.append(limit);
        }

        String listTablesSql = sqlBuilder.toString();
        return listTablesSql;
    }

    /**
     * Creates a target table following the table specification.
     *
     * @param tableSpec Table specification with the physical table name, column names and physical column data types.
     */
    @Override
    public void createTable(TableSpec tableSpec) {

        ProviderDialectSettings dialectSettings = this.clickHouseConnectionProvider.getDialectSettings(this.getConnectionSpec());
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

            sqlBuilder.append(" NULL");
        }

        sqlBuilder.append("\n)");
        sqlBuilder.append("ENGINE = TinyLog");

        String createTableSql = sqlBuilder.toString();
        this.executeCommand(createTableSql, JobCancellationToken.createDummyJobCancellationToken());
    }

    /**
     * Retrieves the metadata (column information) for a given list of tables from a given schema.
     *
     * @param schemaName Schema name.
     * @param tableNameContains Optional filter with a substring that must be present in the table names.
     * @param limit The limit of tables to return.
     * @param secretValueLookupContext Secret value lookup context.
     * @param tableNames Table names.
     * @param connectionWrapper Parent connection wrapper.
     * @param secretValueLookupContext Secret value lookup context.
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
            String sql = buildListColumnsSql(schemaName, tableNames);
            tech.tablesaw.api.Table tableResult = this.executeQuery(sql, JobCancellationToken.createDummyJobCancellationToken(), null, false);
            Column<?>[] columns = tableResult.columnArray();
            for (Column<?> column : columns) {
                column.setName(column.name().toLowerCase(Locale.ROOT));
            }

            HashMap<String, HashSet<String>> tableColumnMap = new HashMap<>();
            try {
                String keyColumnUsageSql = buildKeyColumnUsageSql(schemaName, tableNames);
                tech.tablesaw.api.Table keyColumnUsageResult = this.executeQuery(keyColumnUsageSql, JobCancellationToken.createDummyJobCancellationToken(), null, false);
                for (Row row : keyColumnUsageResult) {
                    String tableName = row.getString("table_name");
                    String columnName = row.getString("column_name");
                    tableColumnMap.computeIfAbsent(tableName, k -> new HashSet<>()).add(columnName);
                }
            } catch (Exception ex) {
                // exception is swallowed
            }

            HashMap<String, TableSpec> tablesByTableName = new LinkedHashMap<>();

            for (Row colRow : tableResult) {
                String physicalTableName = colRow.getString("table_name");

                if (!Strings.isNullOrEmpty(tableNameContains)) {
                    if (!physicalTableName.contains(tableNameContains)) {
                        continue;
                    }
                }

                String columnName = colRow.getString("COLUMN_NAME");
                boolean isNullable = Objects.equals(colRow.getString("IS_NULLABLE"),"1");
                String dataType = colRow.getString("DATA_TYPE");

                TableSpec tableSpec = tablesByTableName.get(physicalTableName);
                if (tableSpec == null) {
                    if (tableSpecs.size() >= limit) {
                        break;
                    }

                    tableSpec = new TableSpec();
                    tableSpec.setPhysicalTableName(new PhysicalTableName(schemaName, physicalTableName));
                    tablesByTableName.put(physicalTableName, tableSpec);
                    tableSpecs.add(tableSpec);
                }

                ColumnSpec columnSpec = new ColumnSpec();
                ColumnTypeSnapshotSpec columnType = ClickHouseColumnTypeSnapshotReader.fromType(dataType);

                if (tableResult.containsColumn("NUMERIC_SCALE") &&
                        !colRow.isMissing("NUMERIC_SCALE")) {
                    columnType.setPrecision(NumericTypeConverter.toInt(colRow.getObject("NUMERIC_SCALE")));
                }

                columnType.setNullable(isNullable);
                columnSpec.setTypeSnapshot(columnType);
                tableSpec.getColumns().put(columnName, columnSpec);

                if(tableColumnMap.containsKey(physicalTableName) && tableColumnMap.get(physicalTableName).contains(columnName)){
                    columnSpec.setId(true);
                }
            }

            return tableSpecs;
        }
        catch (Exception ex) {
            throw new ConnectionQueryException(ex);
        }
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
        sqlBuilder.append(".SCHEMATA WHERE SCHEMA_NAME NOT IN ('INFORMATION_SCHEMA', 'information_schema', 'system')");
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

}
