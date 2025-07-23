/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.settings.apikey;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.settings.impl.SettingsCliService;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to set a api key in settings.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "set", header = "Set API key", description = "Set the API key used for accessing external services. This key is used to authenticate requests to the service.")
public class SettingsApiKeySetCliCommand extends BaseCommand implements ICommand {
	private SettingsCliService settingsCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;
	private DqoCloudApiKeyProvider apiKeyProvider;

	public SettingsApiKeySetCliCommand() {
	}

	@Autowired
	public SettingsApiKeySetCliCommand(SettingsCliService settingsCliService,
                                       TerminalReader terminalReader,
                                       TerminalWriter terminalWriter,
                                       DqoCloudApiKeyProvider apiKeyProvider) {
		this.settingsCliService = settingsCliService;
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
		this.apiKeyProvider = apiKeyProvider;
	}

	@CommandLine.Parameters(index = "0", description = "DQOps Cloud Api key")
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 */
	@Override
	public Integer call() throws Exception {
		if (Strings.isNullOrEmpty(this.key)) {
			throwRequiredParameterMissingIfHeadless("key");
			this.key = this.terminalReader.prompt("DQOps Cloud Api key", null, false);
		}

		try {
			this.apiKeyProvider.decodeApiKey(this.key);
		}
		catch (Exception ex) {
			this.terminalWriter.writeLine("Invalid DQOps Cloud API key: " + ex.getMessage());
			return -1;
		}

		CliOperationStatus cliOperationStatus = this.settingsCliService.setApiKey(key);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
