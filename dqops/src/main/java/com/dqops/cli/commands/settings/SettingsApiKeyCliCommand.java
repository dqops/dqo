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
import com.dqops.cli.commands.settings.apikey.SettingsApiKeyRemoveCliCommand;
import com.dqops.cli.commands.settings.apikey.SettingsApiKeySetCliCommand;
import com.dqops.cli.commands.settings.apikey.SettingsApiKeyShowCliCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli settings Api key base command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "apikey", description = "Set or show the DQOps Cloud (SaaS) API Key", subcommands = {
		SettingsApiKeySetCliCommand.class,
		SettingsApiKeyRemoveCliCommand.class,
		SettingsApiKeyShowCliCommand.class,
})
public class SettingsApiKeyCliCommand extends BaseCommand {
}
