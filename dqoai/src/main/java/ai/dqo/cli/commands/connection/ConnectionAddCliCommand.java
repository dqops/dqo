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
import ai.dqo.cli.completion.completers.ProviderTypeCompleter;
import ai.dqo.cli.terminal.TerminalFactory;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.connectors.ProviderType;
import ai.dqo.metadata.sources.ConnectionSpec;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.LinkedHashMap;

/**
 * Cli command to add a new connection.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "add", description = "Add connection with specified details")
public class ConnectionAddCliCommand extends BaseCommand implements ICommand {
    private ConnectionService connectionService;
    private TerminalFactory terminalFactory;

    public ConnectionAddCliCommand() {
    }

    @Autowired
    public ConnectionAddCliCommand(ConnectionService connectionService,
                                   TerminalFactory terminalFactory) {
        this.connectionService = connectionService;
        this.terminalFactory = terminalFactory;
    }

    /**
     * Connection name. Must be provided.
     */
    @CommandLine.Option(names = {"-n", "--name"}, description = "Connection name", required = false)
    private String name;

    @CommandLine.Option(names = {"-t", "--provider"}, description = "Connection provider type", required = false,
            completionCandidates = ProviderTypeCompleter.class)
    private ProviderType providerType;

    @CommandLine.Mixin
    private ConnectionSpec connection;

    /**
     * Returns the connection name that will be changed.
     * @return Connection name.
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
     * Returns the provider type.
     * @return Provider type.
     */
    public ProviderType getProviderType() {
        return providerType;
    }

    /**
     * Sets the provider type.
     * @param providerType Provider type.
     */
    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    /**
     * Returns a connection specification that is filled as a mixin.
     * @return Connection specification filled with cli command options.
     */
    public ConnectionSpec getConnection() {
        return connection;
    }

    /**
     * Sets a connection specification, filled with options.
     * @param connection
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
        if (Strings.isNullOrEmpty(this.name)) {
			throwRequiredParameterMissingIfHeadless("--name");
			this.name = this.terminalFactory.getReader().prompt("Connection name (--name)", null, false);
        }

        if (this.providerType == null) {
			throwRequiredParameterMissingIfHeadless("--provider");
			this.providerType = this.terminalFactory.getReader().promptEnum("Database provider type (--provider)", ProviderType.class, null, false);
        }

        ConnectionSpec connectionSpec = this.connection != null ? this.connection : new ConnectionSpec();
        connectionSpec.setProviderType(providerType);

		this.connectionService.promptForConnectionParameters(connectionSpec, this.isHeadless(), this.terminalFactory.getReader(), this.terminalFactory.getWriter());

        CliOperationStatus cliOperationStatus = this.connectionService.addConnection(this.name, connectionSpec);
        this.terminalFactory.getWriter().writeLine(cliOperationStatus.getMessage());
        return cliOperationStatus.isSuccess() ? 0 : -1;
    }
}
