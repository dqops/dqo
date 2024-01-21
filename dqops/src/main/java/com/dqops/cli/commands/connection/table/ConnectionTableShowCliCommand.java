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
package com.dqops.cli.commands.connection.table;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.TabularOutputFormat;
import com.dqops.cli.commands.connection.impl.ConnectionCliService;
import com.dqops.cli.completion.completedcommands.IConnectionNameCommand;
import com.dqops.cli.completion.completers.ConnectionNameCompleter;
import com.dqops.cli.completion.completers.TableNameCompleter;
import com.dqops.cli.terminal.*;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "connection table show" 3nd level cli command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "show", header = "Show table for connection", description = "Show the details of the specified table in the database for the specified connection. It allows the user to view the details of a specific table in the database.")
public class ConnectionTableShowCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand {
	private ConnectionCliService connectionCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;
	private TerminalTableWritter terminalTableWritter;
	private FileWriter fileWriter;

	public ConnectionTableShowCliCommand() {
	}

	@Autowired
	public ConnectionTableShowCliCommand(ConnectionCliService connectionCliService,
                                         TerminalReader terminalReader,
                                         TerminalWriter terminalWriter,
                                         TerminalTableWritter terminalTableWritter,
                                         FileWriter fileWriter) {
		this.connectionCliService = connectionCliService;
		this.terminalWriter = terminalWriter;
		this.terminalReader = terminalReader;
		this.terminalTableWritter = terminalTableWritter;
		this.fileWriter = fileWriter;
	}

	@CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name",
			required = false, completionCandidates = ConnectionNameCompleter.class)
	private String connection;

	@CommandLine.Option(names = {"-t", "--table", "--full-table-name"}, description = "Full table name (schema.table), supports wildcard patterns 'sch*.tab*'",
			required = false, completionCandidates = TableNameCompleter.class)
	private String table;

	/**
	 * Returns the connection name.
	 * @return Connection name.
	 */
	public String getConnection() {
		return connection;
	}

	/**
	 * Sets the connection name.
	 * @param connectionName Connection name.
	 */
	public void setConnection(String connectionName) {
		this.connection = connectionName;
	}

	/**
	 * Returns the table name filter.
	 * @return Table name filter.
	 */
	public String getTableName() {
		return table;
	}

	/**
	 * Sets the table name filter.
	 * @param tableName Table name filter.
	 */
	public void setTableName(String tableName) {
		this.table = tableName;
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	public Integer call() throws Exception {
		if (Strings.isNullOrEmpty(this.connection)) {
			throwRequiredParameterMissingIfHeadless("--connection");
			this.connection = this.terminalReader.prompt("Connection name (--connection)", null, false);
		}

		if (Strings.isNullOrEmpty(this.table)) {
			throwRequiredParameterMissingIfHeadless("--table");
			this.table = this.terminalReader.prompt("Full table name (schema.table), supports wildcard patterns 'sch*.tab*'", null, false);
		}

		CliOperationStatus cliOperationStatus = this.connectionCliService.showTableForConnection(connection, table, this.getOutputFormat());
		if (cliOperationStatus.isSuccess()) {
			if (this.getOutputFormat() == TabularOutputFormat.TABLE) {
				if (this.isWriteToFile()) {
					TableBuilder tableBuilder = new TableBuilder(new TablesawDatasetTableModel(cliOperationStatus.getTable()));
					tableBuilder.addInnerBorder(BorderStyle.oldschool);
					tableBuilder.addHeaderBorder(BorderStyle.oldschool);
					String renderedTable = tableBuilder.build().render(this.terminalWriter.getTerminalWidth() - 1);
					CliOperationStatus cliOperationStatus2 = this.fileWriter.writeStringToFile(renderedTable);
					this.terminalWriter.writeLine(cliOperationStatus2.getMessage());
				} else {
					this.terminalTableWritter.writeTable(cliOperationStatus.getTable(), true);
				}
			} else {
				if (this.isWriteToFile()) {
					CliOperationStatus cliOperationStatus2 = this.fileWriter.writeStringToFile(cliOperationStatus.getMessage());
					this.terminalWriter.writeLine(cliOperationStatus2.getMessage());
				}
				else {
					this.terminalWriter.write(cliOperationStatus.getMessage());
				}
			}
			return 0;
		} else {
			this.terminalWriter.writeLine(cliOperationStatus.getMessage());
			return -1;
		}
	}
}
