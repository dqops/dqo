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
import com.dqops.cli.commands.column.impl.ColumnCliService;
import com.dqops.cli.completion.completedcommands.IConnectionNameCommand;
import com.dqops.cli.completion.completedcommands.ITableNameCommand;
import com.dqops.cli.completion.completers.ColumnNameCompleter;
import com.dqops.cli.completion.completers.ConnectionNameCompleter;
import com.dqops.cli.completion.completers.FullTableNameCompleter;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to disable a column.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "disable", header = "Disable the column(s)filtered by the given conditions", description = "Disable one or more columns in a table based on a specified condition. Disabling a column will prevent it from being queried or updated until it is enabled again.")
public class ColumnDisableCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand, ITableNameCommand {
	private ColumnCliService columnCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;

	public ColumnDisableCliCommand() {
	}

	@Autowired
	public ColumnDisableCliCommand(TerminalReader terminalReader,
							   TerminalWriter terminalWriter,
							   ColumnCliService columnCliService) {
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
		this.columnCliService = columnCliService;
	}

	@CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name", required = false,
			completionCandidates = ConnectionNameCompleter.class)
	private String connectionName;

	@CommandLine.Option(names = {"-t", "--table", "--full-table-name"}, description = "Full table name in the form \"schema.table\".", required = false,
			completionCandidates = FullTableNameCompleter.class)
	private String fullTableName;

	@CommandLine.Option(names = {"-C", "--column"}, description = "Column name", required = false,
			completionCandidates = ColumnNameCompleter.class)
	private String columnName;

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
	 * Returns the column name.
	 * @return Column name.
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * Sets the connection name.
	 * @param columnName Column name.
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	public Integer call() throws Exception {

		CliOperationStatus cliOperationStatus = columnCliService.setDisableTo(connectionName, fullTableName, columnName, true);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
