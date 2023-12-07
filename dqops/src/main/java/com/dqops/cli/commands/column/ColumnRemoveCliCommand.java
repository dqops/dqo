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
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to remove a column.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "remove", header = "Remove the column(s) that match a given condition", description = "Remove one or more columns from a table that match a specified condition. Users can filter the column.")
public class ColumnRemoveCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand, ITableNameCommand {
	private ColumnCliService columnCliService;
	private DqoUserPrincipalProvider principalProvider;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;

	public ColumnRemoveCliCommand() {
	}

	@Autowired
	public ColumnRemoveCliCommand(TerminalReader terminalReader,
								  TerminalWriter terminalWriter,
								  ColumnCliService columnCliService,
								  DqoUserPrincipalProvider principalProvider) {
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
		this.columnCliService = columnCliService;
		this.principalProvider = principalProvider;
	}

	@CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name", required = false,
			completionCandidates = ConnectionNameCompleter.class)
	private String connectionName;

	@CommandLine.Option(names = {"-t", "--table", "--full-table-name"},
			description = "Full table name filter in the form \"schema.table\", but also supporting patterns: public.*, *.customers, landing*.customer*.", required = false,
			completionCandidates = FullTableNameCompleter.class)
	private String fullTableName;

	@CommandLine.Option(names = {"-C", "--column"}, description = "Column name, supports patterns: c_*, *_id, prefix*suffix.", required = false,
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
		DqoUserPrincipal principal = this.principalProvider.createUserPrincipalForAdministrator();
		CliOperationStatus cliOperationStatus = columnCliService.removeColumn(connectionName, fullTableName, columnName, principal);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
