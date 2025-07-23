/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
