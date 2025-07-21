/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.settings;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.settings.catalog.SettingsCatalogAddCliCommand;
import com.dqops.cli.commands.settings.catalog.SettingsCatalogListCliCommand;
import com.dqops.cli.commands.settings.catalog.SettingsCatalogRemoveCliCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli settings data catalog integration base command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "catalog", description = "Lists, adds and removes URLs to the data catalog REST API endpoints that receive updates of data quality of tables.", subcommands = {
		SettingsCatalogListCliCommand.class,
		SettingsCatalogAddCliCommand.class,
		SettingsCatalogRemoveCliCommand.class,
})
public class SettingsCatalogCliCommand extends BaseCommand {
}
