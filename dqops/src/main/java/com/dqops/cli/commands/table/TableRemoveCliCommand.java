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
import com.dqops.cli.completion.completers.FullTableNameCompleter;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to remove a connection.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "remove", header = "Remove tables that match a given condition", description = "Remove one or more tables that match a given condition. It allows user to use various filters, such as table names to narrow down the set of tables to remove.")
public class TableRemoveCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand {
    private TableCliService tableImportService;
    private TerminalReader terminalReader;
    private TerminalWriter terminalWriter;

    public TableRemoveCliCommand() {
    }

    @Autowired
    public TableRemoveCliCommand(TerminalReader terminalReader,
								 TerminalWriter terminalWriter,
								 TableCliService tableImportService) {
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
        this.tableImportService = tableImportService;
    }

    @CommandLine.Option(names = {"-t", "--table", "--full-table-name"}, description = "Full table name filter in the form \"schema.table\", but also supporting patterns: public.*, *.customers, landing*.customer*.", required = false,
            completionCandidates = FullTableNameCompleter.class)
    private String fullTableName;

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
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {

        CliOperationStatus cliOperationStatus = tableImportService.removeTable(connectionName, fullTableName);
        this.terminalWriter.writeLine(cliOperationStatus.getMessage());
        return cliOperationStatus.isSuccess() ? 0 : -1;
    }
}
