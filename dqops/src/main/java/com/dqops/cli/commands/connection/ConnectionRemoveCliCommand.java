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
package com.dqops.cli.commands.connection;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.connection.impl.ConnectionCliService;
import com.dqops.cli.completion.completers.ConnectionNameCompleter;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to add a new connection.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "remove", header = "Remove the connection(s) that match a given condition", description = "Removes the connection or connections that match the conditions specified in the options. It allows the user to remove any unwanted connections that are no longer needed.")
public class ConnectionRemoveCliCommand extends BaseCommand implements ICommand {
    private ConnectionCliService connectionCliService;
    private TerminalReader terminalReader;
    private TerminalWriter terminalWriter;

    public ConnectionRemoveCliCommand() {
    }

    @Autowired
    public ConnectionRemoveCliCommand(ConnectionCliService connectionCliService,
                                      TerminalReader terminalReader,
                                      TerminalWriter terminalWriter) {
        this.connectionCliService = connectionCliService;
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
    }

    @CommandLine.Option(names = {"-n", "--name"}, description = "Connection name", required = false,
            completionCandidates = ConnectionNameCompleter.class)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        if (Strings.isNullOrEmpty(this.name)) {
            name = "*";
        }

        CliOperationStatus cliOperationStatus = this.connectionCliService.removeConnection(this.name);
        this.terminalWriter.writeLine(cliOperationStatus.getMessage());
        return cliOperationStatus.isSuccess() ? 0 : -1;
    }
}
