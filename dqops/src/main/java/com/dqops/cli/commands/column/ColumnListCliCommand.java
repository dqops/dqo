/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.column;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.TabularOutputFormat;
import com.dqops.cli.commands.column.impl.ColumnCliService;
import com.dqops.cli.completion.completedcommands.IConnectionNameCommand;
import com.dqops.cli.completion.completedcommands.ITableNameCommand;
import com.dqops.cli.completion.completers.ColumnNameCompleter;
import com.dqops.cli.completion.completers.ConnectionNameCompleter;
import com.dqops.cli.completion.completers.FullTableNameCompleter;
import com.dqops.cli.terminal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to list columns.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "list", header = "List the columns that match a given condition", description = "List all the columns in a table or filter them based on a specified condition.")
public class ColumnListCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand, ITableNameCommand {
	private TerminalFactory terminalFactory;
	private ColumnCliService columnCliService;
	private TerminalTableWritter terminalTableWritter;
	private FileWriter fileWriter;

	public ColumnListCliCommand() {
	}

	@Autowired
	public ColumnListCliCommand(TerminalFactory terminalFactory,
								ColumnCliService columnCliService,
								TerminalTableWritter terminalTableWritter,
								FileWriter fileWriter) {
		this.terminalFactory = terminalFactory;
		this.columnCliService = columnCliService;
		this.terminalTableWritter = terminalTableWritter;
		this.fileWriter = fileWriter;
	}

	@CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name filter", required = false,
			completionCandidates = ConnectionNameCompleter.class)
	private String connectionName = "*";

	@CommandLine.Option(names = {"-t", "--table", "--full-table-name"},
			description = "Full table name filter in the form \"schema.table\", but also supporting patterns: public.*, *.customers, landing*.customer*.", required = false,
			completionCandidates = FullTableNameCompleter.class)
	private String fullTableName = "*.*";

	@CommandLine.Option(names = {"-C", "--column"}, description = "Connection name filter", required = false,
			completionCandidates = ColumnNameCompleter.class)
	private String columnName = "*";

	@CommandLine.Option(names = {"-tg", "--tags"}, description = "Data grouping static tag filter",
			required = false)
	private String[] tags;

	@CommandLine.Option(names = {"-l", "--label"}, description = "Label filter", required = false)
	private String[] labels;

	/**
	 * Returns the table name.
	 * @return Table name.
	 */
	public String getTable() {
		return this.fullTableName;
	}

	/**
	 * Sets the table name.
	 * @param name Table name.
	 */
	public void setTable(String name) {
		this.fullTableName = name;
	}

	/**
	 * Returns the connection name.
	 * @return Connection name.
	 */
	public String getConnection() {
		return connectionName;
	}

	/**
	 * Sets the connection name.
	 * @param connectionName Connection name.
	 */
	public void setConnection(String connectionName) {
		this.connectionName = connectionName;
	}

	/**
	 * Returns the dimensions filter.
	 * @return Dimensions filter.
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * Sets the dimensions filter.
	 * @param tags Dimensions filter.
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}

	/**
	 * Returns the label filters.
	 * @return Label filters.
	 */
	public String[] getLabels() {
		return labels;
	}

	/**
	 * Sets the label filters.
	 * @param labels Label filters.
	 */
	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	public Integer call() throws Exception {

		CliOperationStatus cliOperationStatus = this.columnCliService.loadColumns(connectionName, fullTableName, columnName, this.getOutputFormat(), tags, labels);

		if (cliOperationStatus.isSuccess()) {
			if (this.getOutputFormat() == TabularOutputFormat.TABLE) {
				if (this.isWriteToFile()) {
					TableBuilder tableBuilder = new TableBuilder(new TablesawDatasetTableModel(cliOperationStatus.getTable()));
					tableBuilder.addInnerBorder(BorderStyle.oldschool);
					tableBuilder.addHeaderBorder(BorderStyle.oldschool);
					String renderedTable = tableBuilder.build().render(this.terminalFactory.getWriter().getTerminalWidth() - 1);
					CliOperationStatus cliOperationStatus2 = this.fileWriter.writeStringToFile(renderedTable);
					this.terminalFactory.getWriter().writeLine(cliOperationStatus2.getMessage());
				} else {
					this.terminalTableWritter.writeTable(cliOperationStatus.getTable(), true);
				}
			} else {
				if (this.isWriteToFile()) {
					CliOperationStatus cliOperationStatus2 = this.fileWriter.writeStringToFile(cliOperationStatus.getMessage());
					this.terminalFactory.getWriter().writeLine(cliOperationStatus2.getMessage());
				}
				else {
					this.terminalFactory.getWriter().write(cliOperationStatus.getMessage());
				}
			}
			return 0;
		} else {
			this.terminalFactory.getWriter().writeLine(cliOperationStatus.getMessage());
			return -1;
		}
	}
}
