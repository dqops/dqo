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
import com.dqops.cli.completion.completers.ConnectionNameCompleter;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.metadata.sources.ConnectionSpec;
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
    private ConnectionCliService connectionCliService;
    private TerminalFactory terminalFactory;

    public ConnectionUpdateCliCommand() {
    }

    @Autowired
    public ConnectionUpdateCliCommand(ConnectionCliService connectionCliService,
                                      TerminalFactory terminalFactory) {
        this.connectionCliService = connectionCliService;
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

        CliOperationStatus cliOperationStatus = this.connectionCliService.updateConnection(this.name, connectionSpec);
        this.terminalFactory.getWriter().writeLine(cliOperationStatus.getMessage());
        return cliOperationStatus.isSuccess() ? 0 : -1;
    }
}
