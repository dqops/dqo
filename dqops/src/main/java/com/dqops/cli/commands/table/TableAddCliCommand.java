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
package com.dqops.cli.commands.table;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.table.impl.TableCliService;
import com.dqops.cli.completion.completedcommands.IConnectionNameCommand;
import com.dqops.cli.completion.completers.ConnectionNameCompleter;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.metadata.sources.PhysicalTableName;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to add a new table.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "add", header = "Add table with specified name", description = "Add a new table with the specified name to the database. It allows the user to create a new table in the application for performing various operations.")
public class TableAddCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand {
    private TableCliService tableImportService;
    private TerminalReader terminalReader;
    private TerminalWriter terminalWriter;

    public TableAddCliCommand() {
    }

    @Autowired
    public TableAddCliCommand(TerminalReader terminalReader,
							  TerminalWriter terminalWriter,
							  TableCliService tableImportService) {
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
        this.tableImportService = tableImportService;
    }

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection Name", required = false,
            completionCandidates = ConnectionNameCompleter.class)
    private String connectionName;

    @CommandLine.Option(names = {"-t", "--table"}, description = "Table name", required = false)
    private String fullTableName;

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

        CliOperationStatus cliOperationStatus = this.tableImportService.addTable(connectionName, schemaTableName.getSchemaName(), schemaTableName.getTableName());
        this.terminalWriter.writeLine(cliOperationStatus.getMessage());
        return cliOperationStatus.isSuccess() ? 0 : -1;
    }
}
