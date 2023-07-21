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
