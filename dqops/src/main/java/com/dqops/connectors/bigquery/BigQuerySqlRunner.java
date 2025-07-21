/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.bigquery;

import com.google.cloud.bigquery.Schema;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.util.List;

/**
 * Support class that is responsible for executing SQL queries on a given connection object.
 */
public interface BigQuerySqlRunner {
    /**
     * Executes a query and returns a data frame with the results.
     * @param connection Connection object.
     * @param sql SQL string to execute.
     * @param maxRows Maximum rows limit.
     * @param failWhenMaxRowsExceeded Throws an exception if the maximum number of rows is exceeded.
     * @return Table object.
     */
    Table executeQuery(BigQuerySourceConnection connection, String sql, Integer maxRows, boolean failWhenMaxRowsExceeded);

    /**
     * Executes an SQL statement that does not return results (DML or DDL).
     * @param connection Connection object.
     * @param sql SQL string to execute.
     * @return Number of rows affected.
     */
    long executeStatement(BigQuerySourceConnection connection, String sql);

    /**
     * Creates a list of columns for a bigquery result schema.
     * @param tableSchema Table schema.
     * @return List of columns.
     */
    List<Column<?>> createColumnsFromBigQuerySchema(Schema tableSchema);
}
