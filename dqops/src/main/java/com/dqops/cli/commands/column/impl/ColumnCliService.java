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
package com.dqops.cli.commands.column.impl;

import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.TabularOutputFormat;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.sources.ColumnSpec;

/**
 * Service that performs column operations.
 */
public interface ColumnCliService {
	/**
	 * Loads a list of columns from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @param tabularOutputFormat Tabular output format.
	 * @param dimensions Dimensions filter.
	 * @param labels Labels filter.
	 * @return Table with the results.
	 */
	CliOperationStatus loadColumns(String connectionName, String tableName, String columnName, TabularOutputFormat tabularOutputFormat, String[] dimensions, String[] labels);

	/**
	 * Add a column from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @param columnSpec Column spec.
	 * @return Cli operation status.
	 */
	CliOperationStatus addColumn(String connectionName, String tableName, String columnName, ColumnSpec columnSpec);

	/**
	 * Remove a column from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @param principal Principal that will be used to run the job.
	 * @return Cli operation status.
	 */
	CliOperationStatus removeColumn(String connectionName, String tableName, String columnName, DqoUserPrincipal principal);

	/**
	 * Update a column from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @param columnSpec Column spec.
	 * @return Cli operation status.
	 */
	CliOperationStatus updateColumn(String connectionName, String tableName, String columnName, ColumnSpec columnSpec);

	/**
	 * Update a column from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @param newColumnName New column name.
	 * @return Cli operation status.
	 */
	CliOperationStatus renameColumn(String connectionName, String tableName, String columnName, String newColumnName);

	/**
	 * Disable or enable a column from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @param disable logic value determines if we turn on or off check.
	 * @return Cli operation status.
	 */
	CliOperationStatus setDisableTo(String connectionName, String tableName, String columnName, boolean disable);
}
