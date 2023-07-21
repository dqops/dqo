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
import com.dqops.cli.commands.TabularOutputFormat;
import com.dqops.cli.commands.column.impl.ColumnCliService;
import com.dqops.cli.completion.completedcommands.IConnectionNameCommand;
import com.dqops.cli.completion.completedcommands.ITableNameCommand;
import com.dqops.cli.completion.completers.ColumnNameCompleter;
import com.dqops.cli.completion.completers.ConnectionNameCompleter;
import com.dqops.cli.completion.completers.FullTableNameCompleter;
import com.dqops.cli.terminal.FileWritter;
import com.dqops.cli.terminal.TablesawDatasetTableModel;
import com.dqops.cli.terminal.TerminalTableWritter;
import com.dqops.cli.terminal.TerminalWriter;
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
	private ColumnCliService columnCliService;
	private TerminalWriter terminalWriter;
	private TerminalTableWritter terminalTableWritter;
	private FileWritter fileWritter;

	public ColumnListCliCommand() {
	}

	@Autowired
	public ColumnListCliCommand(TerminalWriter terminalWriter,
							   ColumnCliService columnCliService,
								TerminalTableWritter terminalTableWritter,
								FileWritter fileWritter) {
		this.terminalWriter = terminalWriter;
		this.columnCliService = columnCliService;
		this.terminalTableWritter = terminalTableWritter;
		this.fileWritter = fileWritter;
	}

	@CommandLine.Option(names = {"-t", "--table"}, description = "Table name filter", required = false,
			completionCandidates = FullTableNameCompleter.class)
	private String fullTableName = "*";

	@CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name filter", required = false,
			completionCandidates = ConnectionNameCompleter.class)
	private String connectionName = "*";

	@CommandLine.Option(names = {"-C", "--column"}, description = "Connection name filter", required = false,
			completionCandidates = ColumnNameCompleter.class)
	private String columnName = "*";

	@CommandLine.Option(names = {"-tg", "--tags"}, description = "Data stream tag filter",
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
