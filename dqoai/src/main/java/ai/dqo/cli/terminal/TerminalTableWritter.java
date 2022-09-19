package ai.dqo.cli.terminal;

import ai.dqo.cli.commands.CliOperationStatus;
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
	 * Renders a table with paging using a given table model.
	 * @param tableModel Table model for the spring shell table.
	 * @param addBorder Adds a border to the table. When false, the table is rendered without any borders.
	 */
	void writeTable(TableModel tableModel, boolean addBorder);

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
