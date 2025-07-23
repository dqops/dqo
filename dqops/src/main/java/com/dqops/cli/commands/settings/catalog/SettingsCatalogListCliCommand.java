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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to show the list of data catalog wrappers that write data quality health statues to the data catalog.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "list", header = "List the URLs of data catalog forwarding services", description = "Shows a list of urls of REST API endpoints that receive updates of data quality health for tables having new results.")
public class SettingsCatalogListCliCommand extends BaseCommand implements ICommand {
	private SettingsCliService settingsCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;

	public SettingsCatalogListCliCommand() {
	}

	@Autowired
	public SettingsCatalogListCliCommand(SettingsCliService settingsCliService,
                                         TerminalReader terminalReader,
                                         TerminalWriter terminalWriter) {
		this.settingsCliService = settingsCliService;
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 */
	@Override
	public Integer call() throws Exception {
		CliOperationStatus cliOperationStatus = this.settingsCliService.showDataCatalogUrls();
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
