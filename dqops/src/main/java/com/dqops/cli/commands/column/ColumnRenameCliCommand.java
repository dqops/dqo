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
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to rename a table.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "rename", header = "Rename the column filtered by the given conditions", description = "Rename one or more columns in a table based on a specified condition.")
public class ColumnRenameCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand, ITableNameCommand {
	private ColumnCliService columnCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;

	public ColumnRenameCliCommand() {
	}

	@Autowired
	public ColumnRenameCliCommand(TerminalReader terminalReader,
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

	@CommandLine.Option(names = {"-n", "--newColumn"}, description = "New column name", required = false)
	private String newColumnName;


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
	 * Sets the column name.
	 * @param columnName Column name.
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * Returns the new column name.
	 * @return New column name.
	 */
	public String getNewColumnName() {
		return newColumnName;
	}

	/**
	 * Sets the new column name.
	 * @param newColumnName Column name.
	 */
	public void setNewColumnName(String newColumnName) {
		this.newColumnName = newColumnName;
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	public Integer call() throws Exception {

		if (Strings.isNullOrEmpty(this.columnName)) {
			throwRequiredParameterMissingIfHeadless("--column");
			this.columnName = this.terminalReader.prompt("Column name (--column)", null, false);
		}

		if (Strings.isNullOrEmpty(this.newColumnName)) {
			throwRequiredParameterMissingIfHeadless("--newColumn");
			this.columnName = this.terminalReader.prompt("New column name (--newColumn)", null, false);
		}

		CliOperationStatus cliOperationStatus = columnCliService.renameColumn(connectionName, fullTableName, columnName, newColumnName);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;

	}
}
