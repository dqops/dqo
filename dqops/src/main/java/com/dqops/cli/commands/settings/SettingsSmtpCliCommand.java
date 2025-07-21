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
import com.dqops.cli.commands.settings.smtp.SettingsSmtpRemoveCliCommand;
import com.dqops.cli.commands.settings.smtp.SettingsSmtpSetCliCommand;
import com.dqops.cli.commands.settings.smtp.SettingsSmtpShowCliCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli settings SMTP server configuration base command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "smtp", description = "Set, show or remove SMTP server settings for incident notifications.", subcommands = {
		SettingsSmtpSetCliCommand.class,
		SettingsSmtpRemoveCliCommand.class,
		SettingsSmtpShowCliCommand.class,
})
public class SettingsSmtpCliCommand extends BaseCommand {
}
