/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.column;

import com.dqops.cli.commands.BaseCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "column" 1st level cli command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "column", description = "Modify and list columns",subcommands = {
		ColumnAddCliCommand.class,
		ColumnRemoveCliCommand.class,
		ColumnUpdateCliCommand.class,
		ColumnListCliCommand.class,
		ColumnEnableCliCommand.class,
		ColumnDisableCliCommand.class,
		ColumnRenameCliCommand.class,
})
public class ColumnCliCommand extends BaseCommand {
}
