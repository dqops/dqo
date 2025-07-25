/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.terminal;

import com.dqops.cli.commands.CliOperationStatus;
import org.springframework.shell.table.TableModel;
import tech.tablesaw.api.Table;

public interface TerminalTableWritter {
	CliOperationStatus writeTableToFile(FormattedTableDto<?> tableData);

	CliOperationStatus writeTableToFile(Table table);

	CliOperationStatus writeTableToFile(TableModel tableModel);

	/**
	 * Shows a tablesaw table and asks the user to pick one row.
	 * @param question Question that is shown before the table.
	 * @param table Table (tablesaw) data.
	 * @return 0-based row index that was selected or the default value (may be null).
	 */
	Integer pickTableRowWithPaging(String question, Table table);

		/**
		 * Renders a table model with paging.
		 * @param tableData Table data.
		 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
		 */
	void writeTable(FormattedTableDto<?> tableData, boolean addBorder);

	/**
	 * Writes a table dataset with paging with a header that is extracted from the column names.
	 * @param table Table (dataset).
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	void writeTable(Table table, boolean addBorder);

	/**
	 * Writes a table dataset with paging with a header that is extracted from the column names.
	 * @param table Table (dataset).
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 * @param noHeader Whether the header should be rendered.
	 */
	void writeTable(Table table, boolean addBorder, boolean noHeader);

	/**
	 * Renders a table with paging using a given table model.
	 * @param tableModel Table model for the spring shell table.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	void writeTable(TableModel tableModel, boolean addBorder);

	/**
	 * Renders a table with paging using a given table model.
	 * @param tableModel Table model for the spring shell table.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 * @param noHeader Whether to push the first line as a row or only as a header.
	 *                 If set to true, the first row is pushed as a header providing a valid number of columns.
	 *                 Then the header values should be treated as placeholders.
	 */
	void writeTable(TableModel tableModel, boolean addBorder, boolean noHeader);

	/**
	 * Renders a table model.
	 * @param tableData Table data.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	void writeWholeTable(FormattedTableDto<?> tableData, boolean addBorder);

	/**
	 * Writes a table dataset with a header that is extracted from the column names.
	 * @param table Table (dataset).
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	void writeWholeTable(Table table, boolean addBorder);

	/**
	 * Renders a table using a given table model.
	 * @param tableModel Table model for the spring shell table.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	void writeWholeTable(TableModel tableModel, boolean addBorder);
}
