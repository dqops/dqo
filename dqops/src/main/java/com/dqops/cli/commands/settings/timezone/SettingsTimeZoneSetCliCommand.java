/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.settings.timezone;

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
 * Cli command to set a time zone in settings.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "set", header = "Set the default time zone", description = "Set the default time zone used by the DQOps.")
public class SettingsTimeZoneSetCliCommand extends BaseCommand implements ICommand {
	private SettingsCliService settingsCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;
	private DqoCloudApiKeyProvider apiKeyProvider;

	public SettingsTimeZoneSetCliCommand() {
	}

	@Autowired
	public SettingsTimeZoneSetCliCommand(SettingsCliService settingsCliService,
                                         TerminalReader terminalReader,
                                         TerminalWriter terminalWriter,
                                         DqoCloudApiKeyProvider apiKeyProvider) {
		this.settingsCliService = settingsCliService;
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
		this.apiKeyProvider = apiKeyProvider;
	}

	@CommandLine.Parameters(index = "0", description = "IANA time zone name")
	private String timeZone;

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 */
	@Override
	public Integer call() throws Exception {
		if (Strings.isNullOrEmpty(this.timeZone)) {
			throwRequiredParameterMissingIfHeadless("<time zone>");
			this.timeZone = this.terminalReader.prompt("Time zone", null, false);
		}

		CliOperationStatus cliOperationStatus = this.settingsCliService.setTimeZone(timeZone);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
