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
package com.dqops.cli.terminal;

import com.dqops.cli.commands.CliOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TextColumn;


/**
 * File wrapper. Provides access to the file services.
 */
@Component
public class TerminalTableWriterImpl implements TerminalTableWritter {
	private final FileWriter fileWriter;
	private final TerminalReader terminalReader;
	private final TerminalWriter terminalWriter;

	@Autowired
	TerminalTableWriterImpl(TerminalFactory terminalFactory,
							FileWriter fileWriter) {
		this.fileWriter = fileWriter;
		this.terminalReader = terminalFactory.getReader();
		this.terminalWriter = terminalFactory.getWriter();
	}

	TerminalTableWriterImpl(TerminalWriter terminalWriter,
							TerminalReader terminalReader,
							FileWriter fileWriter) {
		this.fileWriter = fileWriter;
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
	}

	/**
	 * Shows a tablesaw table and asks the user to pick one row.
	 * @param question Question that is shown before the table.
	 * @param table Table (tablesaw) data.
	 * @return 0-based row index that was selected or the default value (may be null).
	 */
	public Integer pickTableRowWithPaging(String question, Table table) {
		RowSelectionTableModel tableModel = new RowSelectionTableModel(table);

		terminalWriter.writeLine(question);
		this.writeTable(tableModel, false, true);

		while (true) {
			String line = terminalReader.prompt("Please enter one of the [] values: ", "", false);

			int rowCount = table.rowCount();
			try {
				int pickedNumber = Integer.parseInt(line.trim());
				if (pickedNumber <= 0 || pickedNumber > rowCount) {
					terminalWriter.write(String.format("Please enter a number between 1 .. %d", rowCount));
				}
				else {
					return pickedNumber - 1;  // WATCH OUT: the user picks a 1-based row index, so the user picks [1] to get the very first row, but rows are 0-based indexed and we will return 0 as the selected row index
				}
			} catch (NumberFormatException nfe) {
				terminalWriter.write(String.format("Please enter a number between 1 .. %d", rowCount));
			}
		}
	}

	@Override
	public CliOperationStatus writeTableToFile(FormattedTableDto<?> tableData) {
		TableModel model = new BeanListTableModel<>(tableData.getRows(), tableData.getHeaders());
		String renderedTable = renderTable(model, true, 180);
		return this.fileWriter.writeStringToFile(renderedTable);
	}

	@Override
	public CliOperationStatus writeTableToFile(Table table) {
		TablesawDatasetTableModel tableModel = new TablesawDatasetTableModel(table);
		String renderedTable = renderTable(tableModel, true, 180);
		return this.fileWriter.writeStringToFile(renderedTable);
	}

	@Override
	public CliOperationStatus writeTableToFile(TableModel tableModel) {
		String renderedTable = renderTable(tableModel, true, 180);
		return this.fileWriter.writeStringToFile(renderedTable);
	}

	/**
	 * Renders a table model with paging.
	 * @param tableData Table data.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	@Override
	public void writeTable(FormattedTableDto<?> tableData, boolean addBorder) {
		final int pageHeight = getPageHeight(addBorder);
		final int lastTableIndex = tableData.getRows().size();
		int rowsLeft = tableData.getRows().size();
		int pageIndex = 0;

		while (rowsLeft > 0) {
			int pageStartTableIndex = pageIndex * pageHeight;
			if (pageStartTableIndex >= lastTableIndex) {
				break;
			}
			int pageEndTableIndex = Math.min(pageStartTableIndex + pageHeight, lastTableIndex);

			TableModel model = new BeanListTableModel<>(tableData.getRows().subList(pageStartTableIndex, pageEndTableIndex), tableData.getHeaders());
			String renderedTable = renderTable(model, addBorder);
			terminalWriter.write(renderedTable);

			if (rowsLeft >= pageHeight) {
				boolean shouldPrintNextPage = pagingPrompt(tableData, addBorder);
				if(!shouldPrintNextPage){
					return;
				}
				rowsLeft -= pageHeight;
				pageIndex++;
			}
			else {
				return;
			}
		}
	}

	/**
	 * Returns the page height of terminal.
	 * @param addBorder Whether to add a border.
	 * @return Page heights in lines.
	 */
	private int getPageHeight(boolean addBorder){
		int pageHeight = (addBorder ? terminalWriter.getTerminalHeight() / 3 : terminalWriter.getTerminalHeight() - 2);
		if( pageHeight == 0) {
			pageHeight = 1;
		}
		return pageHeight;
	}

	/**
	 * Writes a table dataset with paging with a header that is extracted from the column names.
	 * @param table Table (dataset).
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	@Override
	public void writeTable(Table table, boolean addBorder) {
		writeTable(table, addBorder, false);
	}

	/**
	 * Writes a table dataset with paging with a header that is extracted from the column names.
	 * @param table Table (dataset).
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 * @param noHeader Whether the header should be rendered.
	 */
	@Override
	public void writeTable(Table table, boolean addBorder, boolean noHeader) {
		final int pageHeight = getPageHeight(addBorder);
		final int lastTableIndex = table.rowCount();
		int rowsLeft = table.rowCount();
		int pageIndex = 0;

		while (rowsLeft > 0) {
			int pageStartTableIndex = pageIndex * pageHeight;
			if (pageStartTableIndex >= lastTableIndex) {
				break;
			}
			int pageEndTableIndex = Math.min(pageStartTableIndex + pageHeight, lastTableIndex);

			TablesawDatasetTableModel tableModel = new TablesawDatasetTableModel(table.inRange(pageStartTableIndex, pageEndTableIndex));
			String renderedTable = renderTable(tableModel, addBorder);
			if(noHeader){
				int secondLineIndex = renderedTable.indexOf("\n") + 1;
				renderedTable = renderedTable.substring(secondLineIndex);
			}
			terminalWriter.write(renderedTable);

			if (rowsLeft >= pageHeight) {
				boolean shouldPrintNextPage = pagingPrompt(table, addBorder);
				if(!shouldPrintNextPage){
					return;
				}
				rowsLeft -= pageHeight;
				pageIndex++;
			}
			else {
				return;
			}
		}
	}

	/**
	 * Renders a table with paging using a given table model.
	 * @param tableModel Table model for the spring shell table.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 * @param noHeader Whether to push the first line as a row or only as a header.
	 *                 If set to true, the first row is pushed as a header providing a valid number of columns.
	 *                 Then the header values should be treated as placeholders.
	 */
	@Override
	public void writeTable(TableModel tableModel, boolean addBorder, boolean noHeader) {
		int tableStartIndex = noHeader ? 0 : 1;
		TextColumn[] headers = retrieveHeaders(tableModel);
		Table resultTable = Table.create().addColumns(headers);

		for(int y = tableStartIndex; y < tableModel.getRowCount(); y++) {
			Row row = resultTable.appendRow();
			for (int x = 0; x < tableModel.getColumnCount(); x++) {
				String rowString = tableModel.getValue(y, x).toString();
				row.setString(x, rowString);
			}
		}

		writeTable(resultTable, addBorder, noHeader);
	}

	/**
	 * Renders a table with paging using a given table model.
	 * @param tableModel Table model for the spring shell table.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	@Override
	public void writeTable(TableModel tableModel, boolean addBorder) {
		writeTable(tableModel, addBorder, false);
	}

	/**
	 * Renders a table model.
	 * @param tableData Table data.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	@Override
	public void writeWholeTable(FormattedTableDto<?> tableData, boolean addBorder) {
		TableModel model = new BeanListTableModel<>(tableData.getRows(), tableData.getHeaders());
		String renderedTable = renderTable(model, addBorder);
		terminalWriter.write(renderedTable);
	}

	/**
	 * Writes a table dataset with a header that is extracted from the column names.
	 * @param table Table (dataset).
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	@Override
	public void writeWholeTable(Table table, boolean addBorder) {
		TablesawDatasetTableModel tableModel = new TablesawDatasetTableModel(table);
		String renderedTable = renderTable(tableModel, addBorder);
		terminalWriter.write(renderedTable);
	}

	/**
	 * Renders a table using a given table model.
	 * @param tableModel Table model for the spring shell table.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	@Override
	public void writeWholeTable(TableModel tableModel, boolean addBorder) {
		String renderedTable = renderTable(tableModel, addBorder);
        terminalWriter.write(renderedTable);
	}

	/**
	 * Paging prompt.
	 * @param tableData The table with data
	 * @param addBorder Whether to render a border.
	 * @return Whether to continue rendering of the next page.
	 */
	private boolean pagingPrompt(FormattedTableDto<?> tableData, boolean addBorder){
		try {
			int response = terminalReader.promptChar("Show next page? [Y]es / [n]o / [a]ll / [s]ave to file: ", 'y', false);
			if (response == 'N' || response == 'n') {
				return false;
			}
			else if (response == 'a' || response == 'A') {
				writeWholeTable(tableData, addBorder);
				return false;
			}
			else if (response == 's' || response == 'S') {
				CliOperationStatus cliOperationStatus = this.writeTableToFile(tableData);
				terminalWriter.writeLine(cliOperationStatus.getMessage());
				return false;
			}
			else if (response == 'y' || response == 'Y') {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * Paging prompt.
	 * @param table The table with data
	 * @param addBorder Whether to render a border.
	 * @return Whether to continue rendering of the next page.
	 */
	private boolean pagingPrompt(Table table, boolean addBorder){
		try {
			int response = terminalReader.promptChar("Show next page? [Y]es / [n]o / [a]ll / [s]ave to file: ", 'y', false);
			if (response == 'N' || response == 'n') {
				return false;
			}
			else if (response == 'a' || response == 'A') {
				writeWholeTable(table, addBorder);
				return false;
			}
			else if (response == 's' || response == 'S') {
				CliOperationStatus cliOperationStatus = this.writeTableToFile(table);
				terminalWriter.writeLine(cliOperationStatus.getMessage());
				return false;
			}
			else if (response == 'y' || response == 'Y') {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	private TextColumn[] retrieveHeaders(TableModel tableModel) {
		int columnCount = tableModel.getColumnCount();
		TextColumn[] result = new TextColumn[columnCount];

		for (int i = 0; i < columnCount; i++) {
			result[i] = TextColumn.create(tableModel.getValue(0, i).toString());
		}

		return result;
	}

	/**
	 * Renders table to a string.
	 * @param tableModel Table with data.
	 * @param addBorder Whether to render a border.
	 * @param totalAvailableWidth totalAvailableWidth
	 * @return Rendered table.
	 */
	private String renderTable(TableModel tableModel, boolean addBorder, int totalAvailableWidth){
		TableBuilder tableBuilder = new TableBuilder(tableModel);
		if (addBorder) {
			tableBuilder.addInnerBorder(BorderStyle.oldschool);
			tableBuilder.addHeaderBorder(BorderStyle.oldschool);
		}
		String renderedTable = tableBuilder.build().render(totalAvailableWidth);
		return renderedTable;
	}

	/**
	 * Renders table to a string. The width of the table will fit to the terminal.
	 * @param tableModel Table with data.
	 * @param addBorder Whether to render a border.
	 * @return Rendered table.
	 */
	private String renderTable(TableModel tableModel, boolean addBorder){
		return renderTable(tableModel, addBorder, terminalWriter.getTerminalWidth() - 1);
	}

}
