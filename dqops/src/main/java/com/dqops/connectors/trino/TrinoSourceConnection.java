/*
 * Copyright © 2023 DQOps (support@dqops.com)
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
package com.dqops.connectors.trino;

import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.RowCountLimitExceededException;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.connectors.jdbc.JdbcQueryFailedException;
import com.dqops.core.jobqueue.JobCancellationListenerHandle;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.utils.exceptions.RunSilently;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Trino source connection.
 */
@Component("trino-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TrinoSourceConnection extends AbstractJdbcSourceConnection {
    /**
     * Injection constructor for the trino connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public TrinoSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                  SecretValueProvider secretValueProvider,
                                  TrinoConnectionProvider trinoConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, trinoConnectionProvider);
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        TrinoParametersSpec trinoSpec = this.getConnectionSpec().getTrino();
        switch (trinoSpec.getTrinoEngineType()){
            case trino: return makeHikariConfigForTrino(trinoSpec, secretValueLookupContext);
            case athena: return makeHikariConfigForAthena(trinoSpec, secretValueLookupContext);
            default: throw new RuntimeException("Cannot create hikari config. Unsupported enum: " + trinoSpec.getTrinoEngineType());
        }
    }

    private HikariConfig makeHikariConfigForTrino(TrinoParametersSpec trinoSpec, SecretValueLookupContext secretValueLookupContext){
        HikariConfig hikariConfig = new HikariConfig();
        String host = this.getSecretValueProvider().expandValue(trinoSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:trino://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(trinoSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to Trino, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');
        String database = this.getSecretValueProvider().expandValue(trinoSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        String userName = this.getSecretValueProvider().expandValue(trinoSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        //String password = this.getSecretValueProvider().expandValue(trinoSpec.getPassword(), secretValueLookupContext);
        //hikariConfig.setPassword(password);

        Properties dataSourceProperties = new Properties();
        if (trinoSpec.getProperties() != null) {
            dataSourceProperties.putAll(trinoSpec.getProperties());
        }
        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

    private HikariConfig makeHikariConfigForAthena(TrinoParametersSpec trinoSpec, SecretValueLookupContext secretValueLookupContext){
        try {
            Class.forName("com.amazon.athena.jdbc.AthenaDriver");   // todo: lazy registering
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:athena://");

        Properties dataSourceProperties = new Properties();
        if (trinoSpec.getAthenaProperties() != null) {
            dataSourceProperties.putAll(trinoSpec.getAthenaProperties());
        }

        String region = this.getSecretValueProvider().expandValue(trinoSpec.getAthenaRegion(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(region)){
            dataSourceProperties.put("Region", region);
        }

        String catalog = this.getSecretValueProvider().expandValue(trinoSpec.getCatalog(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(catalog)){
            dataSourceProperties.put("Catalog", catalog);
        }

        String outputLocation = this.getSecretValueProvider().expandValue(trinoSpec.getAthenaOutputLocation(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(outputLocation)){
            dataSourceProperties.put("OutputLocation", outputLocation);
        }

        String workgroup = this.getSecretValueProvider().expandValue(trinoSpec.getAthenaWorkGroup(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(workgroup)){
            dataSourceProperties.put("Workgroup", workgroup);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);

        return hikariConfig;
    }

    /**
     * Creates a target table following the table specification.
     *
     * @param tableSpec Table specification with the physical table name, column names and physical column data types.
     */
    @Override
    public void createTable(TableSpec tableSpec) {

        TrinoEngineType trinoEngineType = this.getConnectionSpec().getTrino().getTrinoEngineType();

        switch (trinoEngineType){
            case trino -> super.createTable(tableSpec);
            case athena -> {
                String createTableSql = generateCreateTableSqlStatementForAthena(tableSpec);
                this.executeCommand(createTableSql, JobCancellationToken.createDummyJobCancellationToken());
            }
        }

    }

    String generateCreateTableSqlStatementForAthena(TableSpec tableSpec){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE EXTERNAL TABLE IF NOT EXISTS ");
        sqlBuilder.append(tableSpec.getPhysicalTableName().getSchemaName());
        sqlBuilder.append(".");
        sqlBuilder.append(tableSpec.getPhysicalTableName().getTableName());
        sqlBuilder.append(" (\n");

        Map.Entry<String, ColumnSpec> [] columnKeyPairs = tableSpec.getColumns().entrySet().toArray(Map.Entry[]::new);
        for(int i = 0; i < columnKeyPairs.length ; i++) {
            Map.Entry<String, ColumnSpec> columnKeyPair = columnKeyPairs[i];

            if (i != 0) { // not the first iteration
                sqlBuilder.append(",\n");
            }

            sqlBuilder.append("    ");
            sqlBuilder.append(columnKeyPair.getKey());
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

        }
        sqlBuilder.append("\n)");
        sqlBuilder.append("\n");

        String tableName = tableSpec.getPhysicalTableName().getTableName();
        sqlBuilder.append("ROW FORMAT DELIMITED\n");
        sqlBuilder.append("FIELDS TERMINATED BY ','\n");
        sqlBuilder.append("STORED AS TEXTFILE\n");
//        sqlBuilder.append("LOCATION 's3://dqops-athena-test/" + tableName + "/" + tableName + ".csv'\n");
        sqlBuilder.append("LOCATION 's3://dqops-athena-test/" + tableName + "'\n");
        sqlBuilder.append("TBLPROPERTIES ('skip.header.line.count'='1');\n");

        String createTableSql = sqlBuilder.toString();
        return createTableSql;
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

    @Override
    public Table executeQuery(String sqlQueryStatement, JobCancellationToken jobCancellationToken, Integer maxRows, boolean failWhenMaxRowsExceeded) {
        if(this.getConnectionSpec().getTrino().getTrinoEngineType().equals(TrinoEngineType.trino)){
            return super.executeQuery(sqlQueryStatement, jobCancellationToken, maxRows, failWhenMaxRowsExceeded);
        } else {
            return executeQueryForAthena(sqlQueryStatement, jobCancellationToken, maxRows, failWhenMaxRowsExceeded);
        }
    }

    private Table executeQueryForAthena(String sqlQueryStatement, JobCancellationToken jobCancellationToken, Integer maxRows, boolean failWhenMaxRowsExceeded) {
        try {
            try (Statement statement = this.getJdbcConnection().createStatement()) {
                if (maxRows != null) {
                    statement.setMaxRows(failWhenMaxRowsExceeded ? maxRows + 1 : maxRows);
                }

                try (JobCancellationListenerHandle cancellationListenerHandle =
                             jobCancellationToken.registerCancellationListener(
                                     cancellationToken -> RunSilently.run(statement::cancel))) {
                    try (ResultSet results = statement.executeQuery(sqlQueryStatement)) {
                        try(AthenaResultSetWrapper trinoResultSet = new AthenaResultSetWrapper(results)){
                            Table resultTable = Table.read().db(trinoResultSet, sqlQueryStatement);
                            if (maxRows != null && resultTable.rowCount() > maxRows) {
                                throw new RowCountLimitExceededException(maxRows);
                            }

                            for (Column<?> column : resultTable.columns()) {
                                if (column.name() != null) {
                                    column.setName(column.name().toLowerCase(Locale.ROOT));
                                }
                            }
                            return resultTable;
                        }
                    }
                }
                finally {
                    jobCancellationToken.throwIfCancelled();
                }
            }
        }
        catch (Exception ex) {
            String connectionName = this.getConnectionSpec().getConnectionName();
            throw new JdbcQueryFailedException(
                    String.format("SQL query failed: %s, connection: %s, SQL: %s", ex.getMessage(), connectionName, sqlQueryStatement),
                    ex, sqlQueryStatement, connectionName);
        }
    }

}
