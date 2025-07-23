/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.settings.catalog;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.settings.impl.SettingsCliService;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to remove a url to a data catalog wrapper that will receive data quality health updates.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "remove", header = "Remove a url to a data catalog wrapper", description = "Removes a url to a data catalog wrapper that receives updates of the table's health.")
public class SettingsCatalogRemoveCliCommand extends BaseCommand implements ICommand {
	private SettingsCliService settingsCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;

	public SettingsCatalogRemoveCliCommand() {
	}

	@Autowired
	public SettingsCatalogRemoveCliCommand(SettingsCliService settingsCliService,
                                           TerminalReader terminalReader,
                                           TerminalWriter terminalWriter) {
		this.settingsCliService = settingsCliService;
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
	}

	/**
	 * Data catalog wrapper url.
	 *
	 * @param host Sets the url to a data catalog wrapper.
	 * @return The data catalog wrapper url.
	 */
	@Getter
	@Setter
	@CommandLine.Parameters(index = "0", description = "Url to a data catalog wrapper endpoint")
	private String url;

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 */
	@Override
	public Integer call() throws Exception {
		if (Strings.isNullOrEmpty(this.url)) {
			throwRequiredParameterMissingIfHeadless("url");
			this.url = this.terminalReader.prompt("Data catalog wrapper URL", null, false);
		}

		CliOperationStatus cliOperationStatus = this.settingsCliService.removeDataCatalogUrl(url);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
