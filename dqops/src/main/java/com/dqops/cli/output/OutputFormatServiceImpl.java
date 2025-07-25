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
import com.dqops.cli.terminal.TablesawDatasetTableModel;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

@Component
public class OutputFormatServiceImpl implements OutputFormatService {
	/**
	 * Renders a csv string from table model.
	 * @param tableData Table data.
	 * Returns csv string.
	 */
	public String tableToCsv(FormattedTableDto<?> tableData) {
		return this.tableToCsv(new BeanListTableModel<>(tableData.getRows(), tableData.getHeaders()));
	}

	/**
	 * Renders a csv string from table.
	 * @param table Table (dataset).
	 * Returns csv string.
	 */
	public String tableToCsv(Table table) {
		return this.tableToCsv(new TablesawDatasetTableModel(table));
	}

	/**
	 * Renders a csv string from table table model.
	 * @param tableModel Table model for the spring shell table.
	 * Returns csv string.
	 */
	public String tableToCsv(TableModel tableModel) {
		int columnNumber = tableModel.getColumnCount();
		int rowNumber = tableModel.getRowCount();
		String csvContent = "";
		for (int y = 0; y < rowNumber; y++) {
			for (int x = 0; x < columnNumber; x++) {
				if (y == 0) {
					csvContent += tableModel.getValue(y, x) == null ? "" : tableModel.getValue(y, x).toString()
							.toLowerCase().replace(' ', '_');
				}
				else {
					csvContent += tableModel.getValue(y, x) == null ? "" : tableModel.getValue(y, x).toString();
				}
				csvContent += x == columnNumber - 1 ? "\n" : ';';
			}
		}
		return csvContent;
	}

	/**
	 * Renders a table model.
	 * @param tableData Table data.
	 * Returns json string.
	 */
	public String tableToJson(FormattedTableDto<?> tableData) {
		return this.tableToJson(new BeanListTableModel<>(tableData.getRows(), tableData.getHeaders()));
	}

	/**
	 * Writes a table dataset with a header that is extracted from the column names.
	 * @param table Table (dataset).
	 * Returns json string.
	 */
	public String tableToJson(Table table) {
		return this.tableToJson(new TablesawDatasetTableModel(table));
	}

	/**
	 * Renders a table using a given table model.
	 * @param tableModel Table model for the spring shell table.
	 * Returns json string.
	 */
	public String tableToJson(TableModel tableModel) {
		int columnNumber = tableModel.getColumnCount();
		int rowNumber = tableModel.getRowCount();
		String jsonContent = "{\n\t\"table\":[";
		for (int y = 1; y < rowNumber; y++) {
			jsonContent += "\n\t\t{\n";
			for (int x = 0; x < columnNumber; x++) {
				jsonContent += "\t\t\t\"";
				jsonContent += tableModel.getValue(0, x).toString().toLowerCase().replace(' ', '_');
				jsonContent += "\":\"";
				jsonContent += tableModel.getValue(y, x) == null ? "" : tableModel.getValue(y, x).toString();
				jsonContent += "\",\n";
			}
			jsonContent += "\t\t},\n";
		}
		jsonContent += "\t]\n}\n";
		return jsonContent;
	}
}
