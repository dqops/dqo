/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.jdbc;

import com.dqops.connectors.AbstractSqlSourceConnection;
import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.RowCountLimitExceededException;
import com.dqops.core.jobqueue.JobCancellationListenerHandle;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.exceptions.RunSilently;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

/**
 * Base abstract class for JDBC based source connections.
 */
@Slf4j
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
     * Returns an open JDBC connection (if the {@link AbstractJdbcSourceConnection#open(SecretValueLookupContext)} ()} was called.
     * @return Jdbc connection object.
     */
    public Connection getJdbcConnection() {
        return jdbcConnection;
    }

    /**
     * Stores a reference to an opened JDBC connection. This method should be called only when we create the connection in a different way, for example - by duplicating a DuckDB in-memory connection.
     * @param jdbcConnection JDBC connection to store.
     */
    protected void setJdbcConnection(Connection jdbcConnection) {
        if (this.jdbcConnection != null) {
            throw new DqoRuntimeException("Cannot replace a JDBC connection, because a connection is already present.");
        }
        this.jdbcConnection = jdbcConnection;
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that can be used in the connection names.
     * @return Hikari config.
     */
    public abstract HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext);

    /**
     * Opens a connection before it can be used for executing any statements.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that can be used in the connection names.
     */
    @Override
    public void open(SecretValueLookupContext secretValueLookupContext) {
        if (this.jdbcConnection != null) {
            throw new JdbcConnectionFailedException("Cannot open a connection again");
        }

        try {
            HikariDataSource dataSource = this.jdbcConnectionPool.getDataSource(this.getConnectionSpec(), () -> createHikariConfig(secretValueLookupContext));
			this.jdbcConnection = dataSource.getConnection();
            this.jdbcConnection.setAutoCommit(true);
        }
        catch (Exception ex) {
            if (this.jdbcConnection != null) {
                try {
                    this.jdbcConnection.close();
                }
                catch (SQLException sqlException) {
                    log.error("Cannot close a connection during a failure, error: " + sqlException.getMessage(), sqlException);
                } finally {
                    this.jdbcConnection = null;
                }
            }

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
     * @param sqlQueryStatement       SQL statement that returns a row set.
     * @param jobCancellationToken    Job cancellation token, enables cancelling a running query.
     * @param maxRows                 Maximum rows limit.
     * @param failWhenMaxRowsExceeded Throws an exception if the maximum number of rows is exceeded.
     * @return Tabular result captured from the query.
     */
    @Override
    public Table executeQuery(String sqlQueryStatement, JobCancellationToken jobCancellationToken, Integer maxRows, boolean failWhenMaxRowsExceeded) {
        try {
            try (Statement statement = this.jdbcConnection.createStatement()) {
                boolean maxRowsSupported = true;

                if (maxRows != null) {
                    try {
                        statement.setMaxRows(failWhenMaxRowsExceeded ? maxRows + 1 : maxRows);
                    }
                    catch (Exception ex) {
                        maxRowsSupported = false;
                    }
                }

                try (JobCancellationListenerHandle cancellationListenerHandle =
                             jobCancellationToken.registerCancellationListener(
                                     cancellationToken -> RunSilently.run(statement::cancel))) {
                    try (ResultSet results = statement.executeQuery(sqlQueryStatement)) {
                        ResultSet rowCountLimitedResultSet = new MaxRowsLimitingResultSet(results,
                                maxRows != null ? maxRows + (failWhenMaxRowsExceeded ? 1 : 0) : Integer.MAX_VALUE);

                        Table resultTable = rawTableResultFromResultSet(rowCountLimitedResultSet, sqlQueryStatement);
                        if (maxRows != null && resultTable.rowCount() > maxRows) {
                            if (failWhenMaxRowsExceeded) {
                                throw new RowCountLimitExceededException(maxRows);
                            } else {
                                resultTable.dropRange(maxRows, resultTable.rowCount());
                            }
                        }

                        for (Column<?> column : resultTable.columns()) {
                            if (column.name() != null) {
                                column.setName(column.name().toLowerCase(Locale.ROOT));
                            }
                        }
                        return resultTable;
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

    /**
     * Creates the tablesaw's Table from the ResultSet for the query execution
     * @param results               ResultSet object that contains the data produced by a query
     * @param sqlQueryStatement     SQL statement that returns a row set.
     * @return Tabular result captured from the query.
     * @throws SQLException
     */
    protected Table rawTableResultFromResultSet(ResultSet results, String sqlQueryStatement) throws SQLException {
        Table resultTable = Table.read().db(results, sqlQueryStatement);
        return resultTable;
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
            try (Statement statement = this.jdbcConnection.createStatement()) {
                try (JobCancellationListenerHandle cancellationListenerHandle =
                             jobCancellationToken.registerCancellationListener(
                                     cancellationToken -> RunSilently.run(statement::cancel))) {
                    statement.execute(sqlStatement);
                    return statement.getLargeUpdateCount();
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
