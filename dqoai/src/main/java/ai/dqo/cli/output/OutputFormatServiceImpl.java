/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.cli.output;

import ai.dqo.cli.terminal.FormattedTableDto;
import ai.dqo.cli.terminal.TablesawDatasetTableModel;
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
