/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.output;

import com.dqops.cli.terminal.FormattedTableDto;
import org.springframework.shell.table.TableModel;
import tech.tablesaw.api.Table;

public interface OutputFormatService {
	/**
	 * Renders a table model.
	 * @param tableData Table data.
	 * Returns csv string.
	 */
	String tableToCsv(FormattedTableDto<?> tableData);

	/**
	 * Writes a table dataset with a header that is extracted from the column names.
	 * @param table Table (dataset).
	 * Returns csv string.
	 */
	String tableToCsv(Table table);

	/**
	 * Renders a table using a given table model.
	 * @param tableModel Table model for the spring shell table.
	 * Returns csv string.
	 */
	String tableToCsv(TableModel tableModel);

	/**
	 * Renders a table model.
	 * @param tableData Table data.
	 * Returns json string.
	 */
	String tableToJson(FormattedTableDto<?> tableData);

	/**
	 * Writes a table dataset with a header that is extracted from the column names.
	 * @param table Table (dataset).
	 * Returns json string.
	 */
	String tableToJson(Table table);

	/**
	 * Renders a table using a given table model.
	 * @param tableModel Table model for the spring shell table.
	 * Returns json string.
	 */
	String tableToJson(TableModel tableModel);
}
