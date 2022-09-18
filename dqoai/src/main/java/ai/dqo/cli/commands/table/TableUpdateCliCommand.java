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
package ai.dqo.cli.commands.table;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.commands.table.impl.TableService;
import ai.dqo.cli.completion.completedcommands.IConnectionNameCommand;
import ai.dqo.cli.completion.completers.ConnectionNameCompleter;
import ai.dqo.cli.completion.completers.FullTableNameCompleter;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.metadata.sources.PhysicalTableName;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to update table.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "update", description = "Update tables which match filters")
public class TableUpdateCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand {
    private final TableService tableImportService;
    private final TerminalReader terminalReader;
    private final TerminalWriter terminalWriter;

    @Autowired
    public TableUpdateCliCommand(TerminalReader terminalReader,
								 TerminalWriter terminalWriter,
								 TableService tableImportService) {
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
        this.tableImportService = tableImportService;
    }

    @CommandLine.Option(names = {"-t", "--table"}, description = "Table", required = false,
            completionCandidates = FullTableNameCompleter.class)
    private String fullTableName;

    @CommandLine.Option(names = {"-n", "--newTable"}, description = "New table name", required = false)
    private String newTableName;

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection Name",
            required = false, completionCandidates = ConnectionNameCompleter.class)
    private String connectionName;

    /**
     * Returns the table name.
     * @return Table name.
     */
    public String getFullTableName() {
        return this.fullTableName;
    }

    /**
     * Sets the table name.
     * @param name Table name.
     */
    public void setFullTableName(String name) {
		this.fullTableName = name;
    }

    /**
     * Returns the new table name.
     * @return New table name.
     */
    public String getNewTableName() {
        return this.newTableName;
    }

    /**
     * Sets the new table name.
     * @param name New table name.
     */
    public void setNewTableName(String name) {
		this.newTableName = name;
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

        if (Strings.isNullOrEmpty(this.newTableName)) {
			throwRequiredParameterMissingIfHeadless("--newTable");
			this.newTableName = this.terminalReader.prompt("New table name (--newTable)", null, false);
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

        PhysicalTableName newSchemaTableName = PhysicalTableName.fromSchemaTableFilter(newTableName);
        isSchemaEmpty = newSchemaTableName.getSchemaName().equals("*");
        while(isSchemaEmpty) {
            this.terminalWriter.writeLine(String.format("Table name should fit <schemaName>.<tableName>", this.fullTableName));
            this.fullTableName = this.terminalReader.prompt("Table name (--table)", null, false);
            newSchemaTableName = PhysicalTableName.fromSchemaTableFilter(newTableName);
            if(!newSchemaTableName.getSchemaName().equals("*")) {
                isSchemaEmpty = false;
            }
        }

        CliOperationStatus cliOperationStatus = tableImportService.updateTable(connectionName, schemaTableName.getSchemaName(), schemaTableName.getTableName(), newTableName);
        this.terminalWriter.writeLine(cliOperationStatus.getMessage());
        return cliOperationStatus.isSuccess() ? 0 : -1;
    }
}
