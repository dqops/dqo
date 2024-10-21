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
package com.dqops.connectors.questdb;

import com.dqops.connectors.*;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.*;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.util.*;
import java.util.stream.Collectors;

/**
 * QuestDB source connection.
 */
@Component("questdb-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QuestDbSourceConnection extends AbstractJdbcSourceConnection {

    private final static Object driverRegisterLock = new Object();
    private static boolean driverRegistered = false;
    private QuestDbConnectionProvider questDbConnectionProvider;

    /**
     * Injection constructor for the QuestDb connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public QuestDbSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                      SecretValueProvider secretValueProvider,
                                   QuestDbConnectionProvider questDbConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, questDbConnectionProvider);
        this.questDbConnectionProvider = questDbConnectionProvider;
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
                Class.forName("org.postgresql.Driver");
                driverRegistered = true;
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
        QuestDbParametersSpec questDbParametersSpec = connectionSpec.getQuestdb();

        String host = this.getSecretValueProvider().expandValue(questDbParametersSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:postgresql://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(questDbParametersSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to QuestDB, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');
        String database = this.getSecretValueProvider().expandValue(questDbParametersSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();

        if (questDbParametersSpec.getProperties() != null) {
            dataSourceProperties.putAll(questDbParametersSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        String userName = this.getSecretValueProvider().expandValue(questDbParametersSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(questDbParametersSpec.getPassword(), secretValueLookupContext);
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
        sqlBuilder.append("SELECT CATALOG_NAME AS catalog_name, SCHEMA_NAME as schema_name FROM ");
        sqlBuilder.append(getInformationSchemaName());
        sqlBuilder.append(".SCHEMATA WHERE SCHEMA_NAME NOT IN ('information_schema', 'timescaledb_information', '_timescaledb_config', '_timescaledb_internal', '_timescaledb_catalog', '_timescaledb_cache', 'pg_catalog', 'pg_toast_temp_1', 'pg_temp_1', 'pg_toast')");
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
     * @param tableNameContains Optional filter for a table name substring.
     * @param limit The limit of tables to return.
     * @param secretValueLookupContext Secret lookup context.
     * @return List of tables in the given schema.
     */
    @Override
    public List<SourceTableModel> listTables(String schemaName, String tableNameContains, int limit, SecretValueLookupContext secretValueLookupContext) {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM TABLES();");

        String listTablesSql = sqlBuilder.toString();
        Table tablesRows = this.executeQuery(listTablesSql, JobCancellationToken.createDummyJobCancellationToken(), null, false);

        List<SourceTableModel> results = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < tablesRows.rowCount() ; rowIndex++) {
            String tableName = tablesRows.getString(rowIndex, "table_name");
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
     * Creates a target table following the table specification.
     *
     * @param tableSpec Table specification with the physical table name, column names and physical column data types.
     */
    @Override
    public void createTable(TableSpec tableSpec) {

        ProviderDialectSettings dialectSettings = this.questDbConnectionProvider.getDialectSettings(this.getConnectionSpec());
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
            if (Objects.equals(typeSnapshot.getColumnType(), "integer")) {
                sqlBuilder.append("int");
            } else {
                sqlBuilder.append(typeSnapshot.getColumnType());
            }
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

    @Override
    protected String makeFullyQualifiedTableName(TableSpec tableSpec){
        ProviderDialectSettings dialectSettings = this.questDbConnectionProvider.getDialectSettings(this.getConnectionSpec());

        StringBuilder tableNameBuilder = new StringBuilder();
        tableNameBuilder.append(dialectSettings.quoteIdentifier(tableSpec.getPhysicalTableName().getTableName()));
        return tableNameBuilder.toString();
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
        try {
            List<TableSpec> tableSpecs = new ArrayList<>();

            HashMap<String, TableSpec> tablesByTableName = new LinkedHashMap<>();

            for (String tableName : tableNames) {
                String sql = "SHOW COLUMNS FROM " + tableName;

                tech.tablesaw.api.Table tableResult = this.executeQuery(sql,
                        JobCancellationToken.createDummyJobCancellationToken(), null, false);
                Column<?>[] columns = tableResult.columnArray();
                for (Column<?> column : columns) {
                    column.setName(column.name().toLowerCase(Locale.ROOT));
                }

                HashMap<String, HashSet<String>> tableColumnMap = new HashMap<>();
                try {
                    String keyColumnUsageSql = buildKeyColumnUsageSql(schemaName, tableNames);
                    tech.tablesaw.api.Table keyColumnUsageResult = this.executeQuery(keyColumnUsageSql,
                            JobCancellationToken.createDummyJobCancellationToken(), null, false);
                    for (Row row : keyColumnUsageResult) {
                        String columnName = row.getString("column");
                        tableColumnMap.computeIfAbsent(tableName, k -> new HashSet<>()).add(columnName);
                    }
                } catch (Exception ex) {
                    // exception is swallowed
                }

                for (Row colRow : tableResult) {

                    if (!Strings.isNullOrEmpty(tableNameContains)) {
                        if (!tableName.contains(tableNameContains)) {
                            continue;
                        }
                    }

                    String columnName = colRow.getString("column");
                    String dataType = colRow.getString("type");
                    boolean isNullable = !(dataType.equals("BOOLEAN") || dataType.equals("BYTE") || dataType.equals("SHORT"));

                    TableSpec tableSpec = tablesByTableName.get(tableName);
                    if (tableSpec == null) {
                        if (tableSpecs.size() >= limit) {
                            break;
                        }

                        tableSpec = new TableSpec();
                        tableSpec.setPhysicalTableName(new PhysicalTableName(schemaName, tableName));
                        tablesByTableName.put(tableName, tableSpec);
                        tableSpecs.add(tableSpec);
                    }

                    ColumnSpec columnSpec = new ColumnSpec();
                    ColumnTypeSnapshotSpec columnType = ColumnTypeSnapshotSpec.fromType(dataType);

                    columnType.setNullable(isNullable);
                    columnSpec.setTypeSnapshot(columnType);
                    tableSpec.getColumns().put(columnName, columnSpec);

                    if(tableColumnMap.containsKey(tableName) && tableColumnMap.get(tableName).contains(columnName)){
                        columnSpec.setId(true);
                    }
                }

            }

            return tableSpecs;
        }
        catch (Exception ex) {
            throw new ConnectionQueryException(ex);
        }
    }

}
