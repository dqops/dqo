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
package com.dqops.connectors;

import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.sources.ConnectionSpec;
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
     * @return List of tables in the given schema.
     */
    List<SourceTableModel> listTables(String schemaName);

    /**
     * Retrieves the metadata (column information) for a given list of tables from a given schema.
     * @param schemaName Schema name.
     * @param tableNames Table names.
     * @return List of table specifications with the column list which pass the filters.
     */
    List<TableSpec> retrieveTableMetadata(String schemaName, List<String> tableNames);

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
