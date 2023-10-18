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
package com.dqops.connectors.sqlserver;

import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.RowCountLimitExceededException;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.connectors.jdbc.JdbcQueryFailedException;
import com.dqops.core.jobqueue.JobCancellationListenerHandle;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ConnectionSpec;
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
import java.util.Properties;

/**
 * Microsoft SQL Server source connection.
 */
@Component("sqlserver-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SqlServerSourceConnection extends AbstractJdbcSourceConnection {

    /**
     * Injection constructor for the MS SQL Server connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public SqlServerSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                      SecretValueProvider secretValueProvider,
                                      SqlServerConnectionProvider sqlServerConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, sqlServerConnectionProvider);
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     *
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        SqlServerParametersSpec sqlserverSpec = connectionSpec.getSqlserver();

        String host = this.getSecretValueProvider().expandValue(sqlserverSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:sqlserver://;serverName=");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(sqlserverSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(";port=");
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to SQLSERVER, the port number is invalid: " + port, nfe);
            }
        }
        String database = this.getSecretValueProvider().expandValue(sqlserverSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(";databaseName=");
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();

        if(sqlserverSpec.getDisableEncryption() != null && sqlserverSpec.getDisableEncryption()){
            dataSourceProperties.put("encrypt", "false");
        }

        if (sqlserverSpec.getProperties() != null) {
            dataSourceProperties.putAll(sqlserverSpec.getProperties());
        }

        String userName = this.getSecretValueProvider().expandValue(sqlserverSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(sqlserverSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

        String options =  this.getSecretValueProvider().expandValue(sqlserverSpec.getOptions(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(options)) {
            dataSourceProperties.put("options", options);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

    /**
     * Executes a provider specific SQL that returns a query. For example a SELECT statement or any other SQL text that also returns rows.
     *
     * @param sqlQueryStatement       SQL statement that returns a row set.
     * @param jobCancellationToken    Job cancellation token, enables cancelling a running query.
     * @param maxRows                 Maximum rows limit.
     * @param failWhenMaxRowsExceeded Throws an exception if the maximum number of rows is exceeded.
     * @return Tabular result captured from the query.
     */
    @Override
    public Table executeQuery(String sqlQueryStatement, JobCancellationToken jobCancellationToken, Integer maxRows, boolean failWhenMaxRowsExceeded) {
        try {
            try (Statement statement = this.getJdbcConnection().createStatement()) {
                if (maxRows != null) {
                    statement.setMaxRows(failWhenMaxRowsExceeded ? maxRows + 1 : maxRows);
                }

                try (JobCancellationListenerHandle cancellationListenerHandle =
                             jobCancellationToken.registerCancellationListener(
                                     cancellationToken -> RunSilently.run(statement::cancel))) {
                    try (ResultSet results = statement.executeQuery(sqlQueryStatement)) {
                        try (SqlServerResultSet sqlServerResultSet = new SqlServerResultSet(results)) {
                            Table resultTable = Table.read().db(sqlServerResultSet, sqlQueryStatement);
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
