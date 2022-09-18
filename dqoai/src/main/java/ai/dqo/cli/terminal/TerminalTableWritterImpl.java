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
					int response = this.terminalReader.promptChar("Show next page? [Y]es / [n]o / [a]ll / [s]ave to file: ", ' ', false);
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
