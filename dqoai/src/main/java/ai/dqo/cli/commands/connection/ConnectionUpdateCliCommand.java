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
package ai.dqo.cli.commands.connection;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.connection.impl.ConnectionService;
import ai.dqo.cli.completion.completers.ConnectionNameCompleter;
import ai.dqo.cli.completion.completers.ProviderTypeCompleter;
import ai.dqo.cli.terminal.TerminalFactory;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.connectors.ProviderType;
import ai.dqo.connectors.postgresql.PostgresqlParametersSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
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
@CommandLine.Command(name = "update", header = "Update the connection(s) that match a given condition", description = "Update the connection or connections that match the conditions specified in the options with new details. It allows the user to modify existing connections in the application.")
public class ConnectionUpdateCliCommand extends BaseCommand implements ICommand {
    private ConnectionService connectionService;
    private TerminalFactory terminalFactory;

    public ConnectionUpdateCliCommand() {
    }

    @Autowired
    public ConnectionUpdateCliCommand(ConnectionService connectionService,
                                      TerminalFactory terminalFactory) {
        this.connectionService = connectionService;
        this.terminalFactory = terminalFactory;
    }

    @CommandLine.Option(names = {"-n", "--name"}, description = "Connection name, supports wildcards for changing multiple connections at once, i.e. \"conn*\"", required = false,
            completionCandidates = ConnectionNameCompleter.class)
    private String name;

    @CommandLine.Mixin
    private ConnectionSpec connection;

    /**
     * Returns a connection name or a wildcard to modify multiple connections.
     * @return Connection wildcard.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the connection name.
     * @param name Connection name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a connection object filled with options provided in the cli.
     * @return Connection object, filled with new parameters.
     */
    public ConnectionSpec getConnection() {
        return connection;
    }

    /**
     * Sets a connection object that will have new values.
     * @param connection Connection options.
     */
    public void setConnection(ConnectionSpec connection) {
        this.connection = connection;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        ConnectionSpec connectionSpec = this.connection == null ? new ConnectionSpec() : this.connection;

        CliOperationStatus cliOperationStatus = this.connectionService.updateConnection(this.name, connectionSpec);
        this.terminalFactory.getWriter().writeLine(cliOperationStatus.getMessage());
        return cliOperationStatus.isSuccess() ? 0 : -1;
    }
}
