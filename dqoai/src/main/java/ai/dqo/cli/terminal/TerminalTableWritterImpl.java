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
package ai.dqo.cli.terminal;

import ai.dqo.cli.commands.CliOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;


/**
 * File wrapper. Provides access to the file services.
 */
@Component
public class TerminalTableWritterImpl implements TerminalTableWritter {
	private final TerminalReader terminalReader;
	private final TerminalWriter terminalWriter;
	private final FileWritter fileWritter;

	@Autowired
	TerminalTableWritterImpl(TerminalReader terminalReader,
							 TerminalWriter terminalWriter,
							 FileWritter fileWritter) {
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
		this.fileWritter = fileWritter;
	}

	/**
	 * Shows a tablesaw table and asks the user to pick one row.
	 * @param question Question that is shown before the table.
	 * @param table Table (tablesaw) data.
	 * @return 0-based row index that was selected or the default value (may be null).
	 */
	public Integer pickTableRowWithPaging(String question, Table table) {
		RowSelectionTableModel tableModel = new RowSelectionTableModel(table);

		this.terminalWriter.writeLine(question);
		this.writeTable(tableModel, false);

		while (true) {
			String line = this.terminalReader.prompt("Please enter one of the [] values: ", "", false);

			int rowCount = table.rowCount();
			try {
				int pickedNumber = Integer.parseInt(line.trim());
				if (pickedNumber <= 0 || pickedNumber > rowCount) {
					this.terminalWriter.write(String.format("Please enter a number between 1 .. %d", rowCount));
				}
				else {
					return pickedNumber - 1;  // WATCH OUT: the user picks a 1-based row index, so the user picks [1] to get the very first row, but rows are 0-based indexed and we will return 0 as the selected row index
				}
			} catch (NumberFormatException nfe) {
				this.terminalWriter.write(String.format("Please enter a number between 1 .. %d", rowCount));
			}
		}
	}

	@Override
	public CliOperationStatus writeTableToFile(FormattedTableDto<?> tableData) {
		TableModel model = new BeanListTableModel<>(tableData.getRows(), tableData.getHeaders());

		TableBuilder tableBuilder = new TableBuilder(model);
		tableBuilder.addInnerBorder(BorderStyle.oldschool);
		tableBuilder.addHeaderBorder(BorderStyle.oldschool);

		String renderedTable = tableBuilder.build().render(180);

		return this.fileWritter.writeStringToFile(renderedTable);
	}

	@Override
	public CliOperationStatus writeTableToFile(Table table) {
		TablesawDatasetTableModel tableModel = new TablesawDatasetTableModel(table);

		TableBuilder tableBuilder = new TableBuilder(tableModel);
		tableBuilder.addInnerBorder(BorderStyle.oldschool);
		tableBuilder.addHeaderBorder(BorderStyle.oldschool);

		String renderedTable = tableBuilder.build().render(180);

		return this.fileWritter.writeStringToFile(renderedTable);
	}

	@Override
	public CliOperationStatus writeTableToFile(TableModel tableModel) {
		TableBuilder tableBuilder = new TableBuilder(tableModel);

		tableBuilder.addInnerBorder(BorderStyle.oldschool);
		tableBuilder.addHeaderBorder(BorderStyle.oldschool);

		String renderedTable = tableBuilder.build().render(180);

		return this.fileWritter.writeStringToFile(renderedTable);
	}

	/**
	 * Renders a table model with paging.
	 * @param tableData Table data.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	@Override
	public void writeTable(FormattedTableDto<?> tableData, boolean addBorder) {
		int height = addBorder ? this.terminalWriter.getTerminalHeight() / 3 : this.terminalWriter.getTerminalHeight() - 2;
		if( height == 0) {
			height = 1;
		}
		int rowsLeft = tableData.getRows().size();
		int index = 0;

		while (rowsLeft > 0) {
			int start = index * height;
			int maxEnd = tableData.getRows().size();
			if (start >= maxEnd) {
				break;
			}
			int end = Math.min(start + height, maxEnd);
			TableModel model = new BeanListTableModel<>(tableData.getRows().subList(start, end), tableData.getHeaders());

			TableBuilder tableBuilder = new TableBuilder(model);
			if (addBorder) {
				tableBuilder.addInnerBorder(BorderStyle.oldschool);
				tableBuilder.addHeaderBorder(BorderStyle.oldschool);
			}
			String renderedTable = tableBuilder.build().render(this.terminalWriter.getTerminalWidth() - 1);

			this.terminalWriter.write(renderedTable);

			if (rowsLeft >= height) {
				try {
					int response = this.terminalReader.promptChar("Show next page? [Y]es / [n]o / [a]ll / [s]ave to file: ", 'y', false);
					if (response == 'N' || response == 'n') {
						return;
					}
					else if (response == 'a' || response == 'A') {
						writeWholeTable(tableData, addBorder);
						return;
					}
					else if (response == 's' || response == 'S') {
						CliOperationStatus cliOperationStatus = this.writeTableToFile(tableData);
						this.terminalWriter.writeLine(cliOperationStatus.getMessage());
						return;
					}
					else if (response == 'y' || response == 'Y') {
						rowsLeft -= height;
						index++;
					}
				} catch (Exception e) {
					return;
				}
			}
			else {
				return;
			}
		}
	}

	/**
	 * Writes a table dataset with paging with a header that is extracted from the column names.
	 * @param table Table (dataset).
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	@Override
	public void writeTable(Table table, boolean addBorder) {
		int height = addBorder ? this.terminalWriter.getTerminalHeight() / 3 : this.terminalWriter.getTerminalHeight() - 2;
		if( height == 0) {
			height = 1;
		}
		int rowsLeft = table.rowCount();
		int index = 0;

		while (rowsLeft > 0) {
			int start = index * height;
			int maxEnd = table.rowCount();
			if (start >= maxEnd) {
				break;
			}
			int end = Math.min(start + height, maxEnd);
			TablesawDatasetTableModel tableModel = new TablesawDatasetTableModel(table.inRange(start, end));

			TableBuilder tableBuilder = new TableBuilder(tableModel);
			if (addBorder) {
				tableBuilder.addInnerBorder(BorderStyle.oldschool);
				tableBuilder.addHeaderBorder(BorderStyle.oldschool);
			}
			String renderedTable = tableBuilder.build().render(this.terminalWriter.getTerminalWidth() - 1);

			this.terminalWriter.write(renderedTable);

			if (rowsLeft >= height) {
				try {
					int response = this.terminalReader.promptChar("Show next page? [Y]es / [n]o / [a]ll / [s]ave to file: ", 'y', false);
					if (response == 'N' || response == 'n') {
						return;
					}
					else if (response == 'a' || response == 'A') {
						writeWholeTable(table, addBorder);
						return;
					}
					else if (response == 's' || response == 'S') {
						CliOperationStatus cliOperationStatus = this.writeTableToFile(table);
						this.terminalWriter.writeLine(cliOperationStatus.getMessage());
						return;
					}
					else if (response == 'y' || response == 'Y') {
						rowsLeft -= height;
						index++;
					}
				} catch (Exception e) {
					return;
				}
			}
			else {
				return;
			}
		}
	}

	private StringColumn[] retrieveHeaders(TableModel tableModel) {
		int columnCount = tableModel.getColumnCount();
		StringColumn[] result = new StringColumn[columnCount];

		for (int i = 0; i < columnCount; i++) {
			result[i] = StringColumn.create(tableModel.getValue(0, i).toString());
		}

		return result;
	}

	/**
	 * Renders a table with paging using a given table model.
	 * @param tableModel Table model for the spring shell table.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	@Override
	public void writeTable(TableModel tableModel, boolean addBorder) {
		StringColumn[] headers = retrieveHeaders(tableModel);
		Table resultTable = Table.create().addColumns(headers);

		for(int y = 1; y < tableModel.getRowCount(); y++) {
			Row row = resultTable.appendRow();
			for (int x = 0; x < tableModel.getColumnCount(); x++) {
				row.setString(x, tableModel.getValue(y, x).toString());
			}
		}
		writeTable(resultTable, addBorder);
	}

	/**
	 * Renders a table model.
	 * @param tableData Table data.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	@Override
	public void writeWholeTable(FormattedTableDto<?> tableData, boolean addBorder) {
		TableModel model = new BeanListTableModel<>(tableData.getRows(), tableData.getHeaders());

		TableBuilder tableBuilder = new TableBuilder(model);
		if (addBorder) {
			tableBuilder.addInnerBorder(BorderStyle.oldschool);
			tableBuilder.addHeaderBorder(BorderStyle.oldschool);
		}
		String renderedTable = tableBuilder.build().render(this.terminalWriter.getTerminalWidth() - 1);
		this.terminalWriter.write(renderedTable);
	}

	/**
	 * Writes a table dataset with a header that is extracted from the column names.
	 * @param table Table (dataset).
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	@Override
	public void writeWholeTable(Table table, boolean addBorder) {
		TablesawDatasetTableModel tableModel = new TablesawDatasetTableModel(table);
		TableBuilder tableBuilder = new TableBuilder(tableModel);
		if (addBorder) {
			tableBuilder.addInnerBorder(BorderStyle.oldschool);
			tableBuilder.addHeaderBorder(BorderStyle.oldschool);
		}
		String renderedTable = tableBuilder.build().render(this.terminalWriter.getTerminalWidth() - 1);
		this.terminalWriter.write(renderedTable);
	}

	/**
	 * Renders a table using a given table model.
	 * @param tableModel Table model for the spring shell table.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	@Override
	public void writeWholeTable(TableModel tableModel, boolean addBorder) {
		TableBuilder tableBuilder = new TableBuilder(tableModel);
		if (addBorder) {
			tableBuilder.addInnerBorder(BorderStyle.oldschool);
			tableBuilder.addHeaderBorder(BorderStyle.oldschool);
		}
		String renderedTable = tableBuilder.build().render(this.terminalWriter.getTerminalWidth() - 1);
		this.terminalWriter.write(renderedTable);
	}
}
