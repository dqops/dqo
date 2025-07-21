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
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.connection.impl.ConnectionCliService;
import com.dqops.cli.completion.completers.ConnectionNameCompleter;
import com.dqops.cli.terminal.TerminalReader;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to edit a table.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "edit", header = "Edit connection that matches a given condition", description = "Edit the connection or connections that match the filter conditions specified in the options. It allows the user to modify the details of an existing connection in the application.")
public class ConnectionEditCliCommand extends BaseCommand implements ICommand {
	private TerminalReader terminalReader;
	private ConnectionCliService connectionCliService;

	public ConnectionEditCliCommand() {
	}

	/**
	 * Default injection constructor.
	 * @param terminalReader Terminal reader.
	 * @param connectionCliService Connection service.
	 */
	@Autowired
	public ConnectionEditCliCommand(TerminalReader terminalReader,
								ConnectionCliService connectionCliService) {
		this.terminalReader = terminalReader;
		this.connectionCliService = connectionCliService;
	}

	@CommandLine.Option(names = {"-c", "--connection"}, description = "Connection Name", completionCandidates = ConnectionNameCompleter.class)
	private String connection;

	/**
	 * Returns the connection name.
	 * @return Connection name.
	 */
	public String getConnection() {
		return connection;
	}

	/**
	 * Sets the connection name.
	 * @param connection Connection name.
	 */
	public void setConnection(String connection) {
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
		if (Strings.isNullOrEmpty(this.connection)) {
			throwRequiredParameterMissingIfHeadless("--connection");
			this.connection = this.terminalReader.prompt("Connection name (--connection)", null, false);
		}

		return this.connectionCliService.launchEditorForConnection(this.connection);
	}
}
