/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.cli.commands.column;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.column.impl.ColumnCliService;
import ai.dqo.cli.completion.completedcommands.IConnectionNameCommand;
import ai.dqo.cli.completion.completers.ConnectionNameCompleter;
import ai.dqo.cli.completion.completers.FullTableNameCompleter;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.ColumnTypeSnapshotSpec;
import ai.dqo.metadata.sources.PhysicalTableName;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to add a new column.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "add", header = "Add a column with specified details", description = "Add a new column to a table with specific details. The new column is added to the YAML configuration file.")
public class ColumnAddCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand {
	private ColumnCliService columnCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;

	public ColumnAddCliCommand() {
	}

	@Autowired
	public ColumnAddCliCommand(TerminalReader terminalReader,
							  TerminalWriter terminalWriter,
							  ColumnCliService columnCliService) {
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
		this.columnCliService = columnCliService;
	}

	@CommandLine.Option(names = {"-t", "--table"}, description = "Table name", required = false,
			completionCandidates = FullTableNameCompleter.class)
	private String fullTableName;

	@CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name", required = false,
			completionCandidates = ConnectionNameCompleter.class)
	private String connectionName;

	@CommandLine.Option(names = {"-C", "--column"}, description = "Column name", required = false)
	private String columnName;

	@CommandLine.Option(names = {"-d", "--dataType"}, description = "Data type", required = false)
	private String dataType;


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
	public void setConnectionName(String connectionName) {
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
		if (Strings.isNullOrEmpty(this.connectionName)) {
			throwRequiredParameterMissingIfHeadless("--connection");
			this.connectionName = this.terminalReader.prompt("Connection name (--connection)", null, false);
		}

		if (Strings.isNullOrEmpty(this.fullTableName)) {
			throwRequiredParameterMissingIfHeadless("--table");
			this.fullTableName = this.terminalReader.prompt("Table name (--table)", null, false);
		}

		if (Strings.isNullOrEmpty(this.columnName)) {
			throwRequiredParameterMissingIfHeadless("--column");
			this.columnName = this.terminalReader.prompt("Column name (--column)", null, false);
		}

		if (Strings.isNullOrEmpty(this.dataType)) {
			throwRequiredParameterMissingIfHeadless("--dataType");
			this.dataType = this.terminalReader.prompt("Data type (--dataType)", null, false);
		}

		PhysicalTableName schemaTableName = PhysicalTableName.fromSchemaTableFilter(fullTableName);
		boolean isSchemaEmpty = schemaTableName.getSchemaName().equals("*");
		while(isSchemaEmpty) {
			this.terminalWriter.writeLine(String.format("Table name should fit <schemaName>.<tableName>", this.fullTableName));
			this.fullTableName = this.terminalReader.prompt("Table name (--table)", null, false);
			schemaTableName = PhysicalTableName.fromSchemaTableFilter(fullTableName);
			if(!schemaTableName.getSchemaName().equals("*")) {
				isSchemaEmpty = false;
			}
		}

		ColumnTypeSnapshotSpec columnTypeSnapshotSpec = new ColumnTypeSnapshotSpec(dataType);
		ColumnSpec columnSpec = new ColumnSpec(columnTypeSnapshotSpec);
		columnSpec.setDisabled(false);

		CliOperationStatus cliOperationStatus = columnCliService.addColumn(connectionName, fullTableName, columnName, columnSpec);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
