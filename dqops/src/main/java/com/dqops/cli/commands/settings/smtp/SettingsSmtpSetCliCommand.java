/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.settings.smtp;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.settings.impl.SettingsCliService;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to set an SMTP server configuration for incident notifications.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "set", header = "Set SMTP config", description = "Set SMTP server configuration for incident notifications.")
public class SettingsSmtpSetCliCommand extends BaseCommand implements ICommand {
	private SettingsCliService settingsCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;

	public SettingsSmtpSetCliCommand() {
	}

	@Autowired
	public SettingsSmtpSetCliCommand(SettingsCliService settingsCliService,
									 TerminalReader terminalReader,
									 TerminalWriter terminalWriter) {
		this.settingsCliService = settingsCliService;
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
	}

	/**
	 * SMTP server host
	 *
	 * @param host Sets the SMTP server host
	 * @return The SMTP server host
	 */
	@Getter
	@Setter
	@CommandLine.Option(names = {"-ho", "--host"}, description = "SMTP server host", required = true)
	private String host;

	/**
	 * SMTP server port
	 *
	 * @param host Sets the SMTP server port
	 * @return The SMTP server port
	 */
	@Getter
	@Setter
	@CommandLine.Option(names = {"-p", "--port"}, description = "SMTP server port", required = true)
	String port;

	/**
	 * SMTP server use SSL
	 *
	 * @param host Sets the SMTP to use SSL
	 * @return Whether the SMTP server uses SSL
	 */
	@Getter
	@Setter
	@CommandLine.Option(names = {"-s", "--use-ssl"}, description = "SMTP server use SSL", required = true)
	Boolean useSSL;

	/**
	 * SMTP server username
	 *
	 * @param host Sets the SMTP server username
	 * @return The SMTP server username
	 */
	@Getter
	@Setter
	@CommandLine.Option(names = {"-u", "--username"}, description = "SMTP server user name", required = true)
	String username;

	/**
	 * SMTP server password
	 *
	 * @param host Sets the SMTP server password
	 * @return The SMTP server password
	 */
	@Getter
	@Setter
	@CommandLine.Option(names = {"-ps", "--password"}, description = "SMTP server password", required = true)
	String password;

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 */
	@Override
	public Integer call() throws Exception {
		if (Strings.isNullOrEmpty(this.host)) {
			throwRequiredParameterMissingIfHeadless("--host");
			this.host = this.terminalReader.prompt("Host (--host)", null, false);
		}

		if (Strings.isNullOrEmpty(this.port)) {
			throwRequiredParameterMissingIfHeadless("--port");
			this.port = this.terminalReader.prompt("Port (--port)", null, false);
		}

		if (Strings.isNullOrEmpty(this.host)) {
			throwRequiredParameterMissingIfHeadless("--host");
			this.host = this.terminalReader.prompt("Host (--host)", null, false);
		}

		if (Strings.isNullOrEmpty(this.host)) {
			throwRequiredParameterMissingIfHeadless("--host");
			this.host = this.terminalReader.prompt("Host (--host)", null, false);
		}

		if (Strings.isNullOrEmpty(this.host)) {
			throwRequiredParameterMissingIfHeadless("--host");
			this.host = this.terminalReader.prompt("Host (--host)", null, false);
		}

		CliOperationStatus cliOperationStatus = this.settingsCliService.setSmtpServerConfiguration(
				host, port, useSSL, username, password);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
