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
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.connectors.ProviderType;
import ai.dqo.metadata.sources.ConnectionSpec;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.LinkedHashMap;

/**
 * Cli command to add a new connection.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "add", description = "Add connection with specified details")
public class ConnectionAddCliCommand extends BaseCommand implements ICommand {
    private final ConnectionService connectionService;
    private final TerminalReader terminalReader;
    private final TerminalWriter terminalWriter;

    @Autowired
    public ConnectionAddCliCommand(ConnectionService connectionService,
								   TerminalReader terminalReader,
								   TerminalWriter terminalWriter) {
        this.connectionService = connectionService;
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
    }

    /**
     * Connection name. Must be provided.
     */
    @CommandLine.Option(names = {"-n", "--name"}, description = "Connection name", required = false)
    private String name;

    @CommandLine.Option(names = {"-t", "--provider"}, description = "Connection provider type", required = false,
            completionCandidates = ProviderTypeCompleter.class)
    private ProviderType providerType;

    @CommandLine.Option(names = {"-d", "--database"}, description = "Database name", required = false)
    private String database;

    @CommandLine.Option(names = {"-j", "--jdbc"}, description = "JDBC connection url", required = false)
    private String url;

    @CommandLine.Option(names = {"-u", "--user"}, description = "Username", required = false)
    private String user;

    @CommandLine.Option(names = {"-p", "--password"}, description = "Password", required = false)
    private String password;

    @CommandLine.Option(names = {"-P", "--properties"}, description = "Provider specific parameters", required = false)
    private LinkedHashMap<String, String> properties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LinkedHashMap<String, String> getProperties() {
        return properties;
    }

    public void setProperties(LinkedHashMap<String, String> properties) {
        this.properties = properties;
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
			this.name = this.terminalReader.prompt("Connection name (--name)", null, false);
        }

        if (this.providerType == null) {
			throwRequiredParameterMissingIfHeadless("--provider");
			this.providerType = this.terminalReader.promptEnum("Database provider type (--provider)", ProviderType.class, null, false);
        }

        ConnectionSpec connectionSpec = new ConnectionSpec();
        connectionSpec.setProviderType(providerType);
        connectionSpec.setUrl(url);
        connectionSpec.setUser(user);
        connectionSpec.setPassword(password);
        connectionSpec.setDatabaseName(this.database);
        if (this.properties != null) {
            connectionSpec.setProperties(this.properties);
        }

		this.connectionService.promptForConnectionParameters(connectionSpec, this.isHeadless(), terminalReader, terminalWriter);

        CliOperationStatus cliOperationStatus = this.connectionService.addConnection(this.name, connectionSpec);
        this.terminalWriter.writeLine(cliOperationStatus.getMessage());
        return cliOperationStatus.isSuccess() ? 0 : -1;
    }
}
