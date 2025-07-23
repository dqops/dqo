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
import com.dqops.cli.commands.settings.instancename.SettingsInstanceNameRemoveCliCommand;
import com.dqops.cli.commands.settings.instancename.SettingsInstanceNameSetCliCommand;
import com.dqops.cli.commands.settings.instancename.SettingsInstanceNameShowCliCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli settings instance name base command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "instancename", description = "Set or show the DQOps instance name", subcommands = {
		SettingsInstanceNameSetCliCommand.class,
		SettingsInstanceNameRemoveCliCommand.class,
		SettingsInstanceNameShowCliCommand.class,
})
public class SettingsInstanceNameCliCommand extends BaseCommand {
}
