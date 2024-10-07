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
package com.dqops.connectors.db2;

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

import java.util.*;
import java.util.stream.Collectors;

/**
 * IBM DB2 source connection.
 */
@Component("db2-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Db2SourceConnection extends AbstractJdbcSourceConnection {

    private final static Object driverRegisterLock = new Object();
    private static boolean driverRegistered = false;
    private final Db2ConnectionProvider db2ConnectionProvider;

    /**
     * Injection constructor for the DB2 connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public Db2SourceConnection(JdbcConnectionPool jdbcConnectionPool,
                               SecretValueProvider secretValueProvider,
                               Db2ConnectionProvider db2ConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, db2ConnectionProvider);
        this.db2ConnectionProvider = db2ConnectionProvider;
    }

    /**
     * Manually registers the JDBC Driver allowing the control of the registration time.
     */
    private static void registerDriver() {
        if(driverRegistered){
            return;
        }
        try {
            synchronized (driverRegisterLock){
                Class.forName("com.ibm.db2.jcc.DB2Driver");
                driverRegistered = true;
            }
        } catch (ClassNotFoundException e) {
            throw new DqoRuntimeException("Please contact DQOps sales team to get access to a commercial version of DQOps. " +
                    "IBM DB2 drivers are not provided in an open-source version due to license limitations.");
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
        Db2ParametersSpec db2ParametersSpec = connectionSpec.getDb2();

        String host = this.getSecretValueProvider().expandValue(db2ParametersSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:db2://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(db2ParametersSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to DB2, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');
        jdbcConnectionBuilder.append(db2ParametersSpec.getDatabase());

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (db2ParametersSpec.getProperties() != null) {
            dataSourceProperties.putAll(db2ParametersSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        String userName = this.getSecretValueProvider().expandValue(db2ParametersSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(db2ParametersSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

    /**
     * Returns the schema name of the INFORMATION_SCHEMA equivalent.
     * @return Information schema name.
     */
    public String getInformationSchemaName() {
        return "SYSIBM";
    }

    /**
     * Returns a list of schemas from the source.
     *
     * @return List of schemas.
     */
    @Override
    public List<SourceSchemaModel> listSchemas() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT CREATOR AS schema_name FROM ");
        sqlBuilder.append(getInformationSchemaName());
        sqlBuilder.append(".systables ");
        sqlBuilder.append("WHERE CREATOR NOT IN ('SYSCAT', 'SYSIBM', 'SYSIBMADM', 'SYSPUBLIC', 'SYSSTAT', 'SYSTOOLS')" );
        sqlBuilder.append("GROUP BY CREATOR");

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
     * Generates an SQL statement that lists tables.
     * @param schemaName Schema name.
     * @param tableNameContains Optional filter with a text that must be present in the tables returned.
     * @param limit The limit of the number of tables to return.
     * @return SQL string for a query that lists tables.
     */
    @NotNull
    public String buildListTablesSql(String schemaName, String tableNameContains, int limit) {
        ConnectionProviderSpecificParameters providerSpecificConfiguration = this.getConnectionSpec().getProviderSpecificConfiguration();

//        where CREATOR = 'SCHEMA'
//        and name like '%CUR%'
//        and type = 'T';

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT CREATOR AS table_schema, NAME AS table_name FROM ");
        sqlBuilder.append(getInformationSchemaName());
        sqlBuilder.append(".systables\n");
        sqlBuilder.append("WHERE CREATOR='");
        sqlBuilder.append(schemaName.replace("'", "''"));
        sqlBuilder.append("'");

        if (!Strings.isNullOrEmpty(tableNameContains)) {
            sqlBuilder.append(" AND NAME LIKE '%");
            sqlBuilder.append(tableNameContains.replace("'", "''"));
            sqlBuilder.append("%'");
        }

        ProviderDialectSettings dialectSettings = this.db2ConnectionProvider.getDialectSettings(this.getConnectionSpec());
        if (dialectSettings.isSupportsLimitClause()) {
            sqlBuilder.append(" LIMIT ");
            sqlBuilder.append(limit);
        }

        String listTablesSql = sqlBuilder.toString();
        return listTablesSql;
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

                String columnName = colRow.getString("column_name");
                boolean isNullable = Objects.equals(colRow.getString("is_nullable"),"YES");
                String dataType = colRow.getString("data_type");

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
            sqlBuilder.append(" AND TABLE_CATALOG=UPPER('");
            sqlBuilder.append(databaseName.replace("'", "''"));
            sqlBuilder.append("')");
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

}
