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
package com.dqops.connectors.teradata;

import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.connectors.jdbc.JdbcQueryFailedException;
import com.dqops.core.jobqueue.JobCancellationListenerHandle;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.utils.exceptions.RunSilently;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.sql.Statement;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Teradata source connection.
 */
@Component("teradata-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TeradataSourceConnection extends AbstractJdbcSourceConnection {

    private final static Object driverRegisterLock = new Object();
    private static boolean driverRegistered = false;
    private static ConnectionProvider connectionProvider;

    /**
     * Injection constructor for the teradata connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public TeradataSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                  SecretValueProvider secretValueProvider,
                                  TeradataConnectionProvider teradataConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, teradataConnectionProvider);
        this.connectionProvider = teradataConnectionProvider;
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
                Class.forName("com.teradata.jdbc.TeraDriver");
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
        TeradataParametersSpec teradataSpec = connectionSpec.getTeradata();

        String host = this.getSecretValueProvider().expandValue(teradataSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:teradata://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(teradataSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to Teradata, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');
        String database = this.getSecretValueProvider().expandValue(teradataSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        String userName = this.getSecretValueProvider().expandValue(teradataSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(teradataSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

        Properties dataSourceProperties = new Properties();
        if (teradataSpec.getProperties() != null) {
            dataSourceProperties.putAll(teradataSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }
        hikariConfig.setDataSourceProperties(dataSourceProperties);

        return hikariConfig;
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
        sqlBuilder.append("SELECT TOP ");
        sqlBuilder.append(limit);
        sqlBuilder.append(" DatabaseName AS table_schema, TableName AS table_name FROM dbc.tables ");
        sqlBuilder.append("WHERE table_schema='");
        sqlBuilder.append(schemaName.replace("'", "''"));
        sqlBuilder.append("'");

        if (!Strings.isNullOrEmpty(tableNameContains)) {
            sqlBuilder.append(" AND table_name LIKE '%");
            sqlBuilder.append(tableNameContains.replace("'", "''"));
            sqlBuilder.append("%'");
        }

        ProviderDialectSettings dialectSettings = this.connectionProvider.getDialectSettings(this.getConnectionSpec());
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
        try {
            this.executeCommand(createTableSql, JobCancellationToken.createDummyJobCancellationToken());
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
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

        for (int rowIndex = 0; rowIndex < data.rowCount() ; rowIndex++) {
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
            sqlBuilder.append(" VALUES ");

            sqlBuilder.append('(');
            for (int colIndex = 0; colIndex < data.columnCount() ; colIndex++) {
                if (colIndex > 0) {
                    sqlBuilder.append(", ");
                }

                Column<?> column = data.column(colIndex);
                Object cellValue = column.isMissing(rowIndex) ? null : data.get(rowIndex, colIndex);
                ColumnSpec columnSpec = tableSpec.getColumns().get(column.name());

                String formattedConstant = this.connectionProvider.formatConstant(cellValue, columnSpec.getTypeSnapshot());
                sqlBuilder.append(formattedConstant);
            }

            sqlBuilder.append(");\n");

            String insertValueSql = sqlBuilder.toString();
            this.executeCommand(insertValueSql, JobCancellationToken.createDummyJobCancellationToken());
        }
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

}
