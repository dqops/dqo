/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors;

import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;
import tech.tablesaw.api.Table;

import java.io.Closeable;
import java.util.List;

/**
 * Source connection.
 */
public interface SourceConnection extends Closeable {
    /**
     * Returns a connection specification.
     * @return Connection specification.
     */
    ConnectionSpec getConnectionSpec();

    /**
     * Opens a connection before it can be used for executing any statements.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that can be used in the connection names.
     */
    void open(SecretValueLookupContext secretValueLookupContext);

    /**
     * Closes a connection.
     */
    void close();

    /**
     * Returns a list of schemas from the source.
     * @return List of schemas.
     */
    List<SourceSchemaModel> listSchemas();

    /**
     * Lists tables inside a schema. Views are also returned.
     * @param schemaName Schema name.
     * @param tableNameContains Optional filter with a substring that must be present in the table names.
     * @param limit The limit of tables to return.
     * @param secretValueLookupContext Secret value lookup context.
     * @return List of tables in the given schema.
     */
    List<SourceTableModel> listTables(String schemaName, String tableNameContains, int limit, SecretValueLookupContext secretValueLookupContext);

    /**
     * Retrieves the metadata (column information) for a given list of tables from a given schema.
     * @param schemaName Schema name.
     * @param tableNameContains Optional filter with a substring that must be present in the table names.
     * @param limit The limit of tables to return.
     * @param tableNames Table names.
     * @param connectionWrapper Parent connection wrapper with a list of existing tables.
     * @param secretValueLookupContext Secret value lookup context.
     * @return List of table specifications with the column list which pass the filters.
     */
    List<TableSpec> retrieveTableMetadata(String schemaName, String tableNameContains, int limit,
                                          List<String> tableNames, ConnectionWrapper connectionWrapper, SecretValueLookupContext secretValueLookupContext);

    /**
     * Executes a provider specific SQL that returns a query. For example a SELECT statement or any other SQL text that also returns rows.
     *
     * @param sqlQueryStatement SQL statement that returns a row set.
     * @param jobCancellationToken Job cancellation token, enables cancelling a running query.
     * @param maxRows Maximum rows limit.
     * @param failWhenMaxRowsExceeded Throws an exception if the maximum number of rows is exceeded.
     * @return Tabular result captured from the query.
     */
    Table executeQuery(String sqlQueryStatement,
                       JobCancellationToken jobCancellationToken,
                       Integer maxRows,
                       boolean failWhenMaxRowsExceeded);

    /**
     * Executes a provider specific SQL that runs a command DML/DDL command.
     *
     * @param sqlStatement SQL DDL or DML statement.
     * @param jobCancellationToken Job cancellation token, enables cancelling a running query.
     */
    long executeCommand(String sqlStatement, JobCancellationToken jobCancellationToken);

    /**
     * Creates a target table following the table specification.
     * @param tableSpec Table specification with the physical table name, column names and physical column data types.
     */
    void createTable(TableSpec tableSpec);

    /**
     * Loads data into a table <code>tableSpec</code>.
     * @param tableSpec Target table specification.
     * @param data Dataset with the expected data.
     */
    void loadData(TableSpec tableSpec, Table data);

    /**
     * Drops a target table following the table specification.
     * @param tableSpec Table specification with the physical table name, column names and physical column data types.
     */
    void dropTable(TableSpec tableSpec);
}
