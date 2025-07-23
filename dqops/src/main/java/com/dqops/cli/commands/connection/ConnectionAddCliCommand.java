/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.connection;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.connection.impl.ConnectionCliService;
import com.dqops.cli.completion.completers.ProviderTypeCompleter;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.connectors.ProviderType;
import com.dqops.metadata.sources.ConnectionSpec;
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
@CommandLine.Command(name = "add", header = "Add a connection with specified details", description = "Creates a new connection to the database with the specified details such as connection name, database type, hostname, username, and password. It allows the user to connect to the database from the application to perform various operations on the database.")
public class ConnectionAddCliCommand extends BaseCommand implements ICommand {
    private ConnectionCliService connectionCliService;
    private TerminalFactory terminalFactory;

    public ConnectionAddCliCommand() {
    }

    @Autowired
    public ConnectionAddCliCommand(ConnectionCliService connectionCliService,
                                   TerminalFactory terminalFactory) {
        this.connectionCliService = connectionCliService;
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

		this.connectionCliService.promptForConnectionParameters(connectionSpec, this.isHeadless(), this.terminalFactory.getReader(), this.terminalFactory.getWriter());

        CliOperationStatus cliOperationStatus = this.connectionCliService.addConnection(this.name, connectionSpec);
        this.terminalFactory.getWriter().writeLine(cliOperationStatus.getMessage());
        return cliOperationStatus.isSuccess() ? 0 : -1;
    }
}
