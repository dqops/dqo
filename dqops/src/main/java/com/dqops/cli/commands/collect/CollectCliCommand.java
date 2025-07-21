/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.collect;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.check.CheckActivateCliCommand;
import com.dqops.cli.commands.check.CheckDeactivateCliCommand;
import com.dqops.cli.commands.check.CheckRunCliCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "collect" 1st level CLI command - a grouping command for performing actions that collect some information, for example - error samples, etc.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "collect", description = "Commands related to collecting statistics and samples", subcommands = {
        CollectErrorSamplesCliCommand.class,
})
public class CollectCliCommand extends BaseCommand {
}
