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
