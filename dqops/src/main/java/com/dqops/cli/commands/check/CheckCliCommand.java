/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.check;

import com.dqops.cli.commands.BaseCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "check" 1st level CLI command - a grouping command for performing actions on checks like executing checks, listing checks, etc.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "check", description = "Commands related to checks and rules", subcommands = {
        CheckRunCliCommand.class,
        CheckActivateCliCommand.class,
        CheckDeactivateCliCommand.class,
})
public class CheckCliCommand extends BaseCommand {
}
