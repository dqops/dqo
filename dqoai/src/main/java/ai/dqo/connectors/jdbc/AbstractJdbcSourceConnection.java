/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.connectors.jdbc;

import ai.dqo.connectors.AbstractSqlSourceConnection;
import ai.dqo.connectors.ConnectionProvider;
import ai.dqo.connectors.sqlserver.SqlServerResultSet;
import ai.dqo.core.secrets.SecretValueProvider;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.io.jdbc.SqlResultSetReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

/**
 * Base abstract class for JDBC based source connections.
 */
public abstract class AbstractJdbcSourceConnection extends AbstractSqlSourceConnection {
    private final JdbcConnectionPool jdbcConnectionPool;
    private Connection jdbcConnection;

    /**
     * Creates a jdbc connection source.
     * @param jdbcConnectionPool Dependency to the jdbc connection pool.
     * @param secretValueProvider Secret manager to expand parameters.
     * @param connectionProvider Connection provider that created the connection, used to get the dialect settings.
     */
    public AbstractJdbcSourceConnection(JdbcConnectionPool jdbcConnectionPool,
										SecretValueProvider secretValueProvider,
										ConnectionProvider connectionProvider) {
        super(secretValueProvider, connectionProvider);
        this.jdbcConnectionPool = jdbcConnectionPool;
    }

    /**
     * Returns an open JDBC connection (if the {@link AbstractJdbcSourceConnection#open()} was called.
     * @return Jdbc connection object.
     */
    public Connection getJdbcConnection() {
        return jdbcConnection;
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @return Hikari config.
     */
    public abstract HikariConfig createHikariConfig();

    /**
     * Opens a connection before it can be used for executing any statements.
     */
    @Override
    public void open() {
        if (this.jdbcConnection != null) {
            throw new JdbcConnectionFailedException("Cannot open a connection again");
        }

        try {
            HikariDataSource dataSource = this.jdbcConnectionPool.getDataSource(this.getConnectionSpec(), () -> createHikariConfig());
			this.jdbcConnection = dataSource.getConnection();
            this.jdbcConnection.setAutoCommit(true);
        }
        catch (Exception ex) {
            if (this.getConnectionSpec().getHierarchyId() != null) {
                String connectionName = this.getConnectionSpec().getConnectionName();
                throw new JdbcConnectionFailedException("Connection failed for source " + connectionName + ", error: " + ex.getMessage(), ex);
            }
            throw new JdbcConnectionFailedException("Connection failed, error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Closes a connection.
     */
    @Override
    public void close() {
        try {
            try {
				this.jdbcConnection.close();
            }
            catch (Exception ex) {
                throw new JdbcConnectionFailedException("Connection cannot be closed, error: " + ex.getMessage(), ex);
            }
        }
        finally {
			this.jdbcConnection = null;
        }
    }

    /**
     * Executes a provider specific SQL that returns a query. For example a SELECT statement or any other SQL text that also returns rows.
     *
     * @param sqlQueryStatement SQL statement that returns a row set.
     * @return Tabular result captured from the query.
     */
    @Override
    public Table executeQuery(String sqlQueryStatement) {
        try {
            try (Statement statement = this.jdbcConnection.createStatement()) {
                try (ResultSet results = statement.executeQuery(sqlQueryStatement)) {
                    Table resultTable = Table.read().db(results, "query_result");
                    for (Column<?> column : resultTable.columns()) {
                        if (column.name() != null) {
                            column.setName(column.name().toLowerCase(Locale.ROOT));
                        }
                    }
                    return resultTable;
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

    /**
     * Executes a provider specific SQL that runs a command DML/DDL command.
     *
     * @param sqlStatement SQL DDL or DML statement.
     */
    @Override
    public long executeCommand(String sqlStatement) {
        try {
            try (Statement statement = this.jdbcConnection.createStatement()) {
                statement.execute(sqlStatement);
                return statement.getLargeUpdateCount();
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
