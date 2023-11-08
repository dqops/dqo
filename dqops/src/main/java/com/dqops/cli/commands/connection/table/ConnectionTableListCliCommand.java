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
import com.dqops.cli.completion.completers.ConnectionNameCompleter;
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
 * "connection table list" 3nd level cli command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "list", header = "List tables for the specified connection and schema name.", description = "List all the tables available in the database for the specified connection and schema. It allows the user to view all the tables in the database.")
public class ConnectionTableListCliCommand extends BaseCommand implements ICommand {
	private ConnectionCliService connectionCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;
	private TerminalTableWritter terminalTableWritter;
	private FileWritter fileWritter;

	public ConnectionTableListCliCommand() {
	}

	@Autowired
	public ConnectionTableListCliCommand(ConnectionCliService connectionCliService,
                                         TerminalReader terminalReader,
                                         TerminalWriter terminalWriter,
                                         TerminalTableWritter terminalTableWritter,
                                         FileWritter fileWritter) {
		this.connectionCliService = connectionCliService;
		this.terminalWriter = terminalWriter;
		this.terminalReader = terminalReader;
		this.terminalTableWritter = terminalTableWritter;
		this.fileWritter = fileWritter;
	}

	@CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name", required = false, completionCandidates = ConnectionNameCompleter.class)
	private String connection;

	@CommandLine.Option(names = {"-s", "--schema"}, description = "Schema name", required = false)
	private String schema;

	@CommandLine.Option(names = {"-t", "--table"}, description = "Table name, without the schema name.", required = false)
	private String table;

	@CommandLine.Option(names = {"-d", "--dimension"}, description = "Dimension filter", required = false)
	private String[] dimensions;

	@CommandLine.Option(names = {"-l", "--label"}, description = "Label filter", required = false)
	private String[] labels;

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

		if (Strings.isNullOrEmpty(this.schema)) {
			throwRequiredParameterMissingIfHeadless("--schema");
			this.schema = this.terminalReader.prompt("Schema name (--schema)", null, false);
		}

		CliOperationStatus cliOperationStatus = this.connectionCliService.loadTableList(connection, schema, table, this.getOutputFormat(), dimensions, labels);
		if (cliOperationStatus.isSuccess()) {
			if (this.getOutputFormat() == TabularOutputFormat.TABLE) {
				if (this.isWriteToFile()) {
					TableBuilder tableBuilder = new TableBuilder(new TablesawDatasetTableModel(cliOperationStatus.getTable()));
					tableBuilder.addInnerBorder(BorderStyle.oldschool);
					tableBuilder.addHeaderBorder(BorderStyle.oldschool);
					String renderedTable = tableBuilder.build().render(this.terminalWriter.getTerminalWidth() - 1);
					CliOperationStatus cliOperationStatus2 = this.fileWritter.writeStringToFile(renderedTable);
					this.terminalWriter.writeLine(cliOperationStatus2.getMessage());
				} else {
					this.terminalTableWritter.writeTable(cliOperationStatus.getTable(), true);
				}
			} else {
				if (this.isWriteToFile()) {
					CliOperationStatus cliOperationStatus2 = this.fileWritter.writeStringToFile(cliOperationStatus.getMessage());
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
