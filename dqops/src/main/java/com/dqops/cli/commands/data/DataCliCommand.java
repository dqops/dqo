/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.data;

import com.dqops.cli.commands.BaseCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "data" 1st level CLI command - a grouping command for performing actions on the local data, like displaying the check results, deleting results, etc.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "data", description = "Commands related to the data",
        subcommands = {
                DataDeleteCliCommand.class,
                DataRepairCliCommand.class,
//                DataStoragePerformanceCliCommand.class
})
public class DataCliCommand extends BaseCommand {
}
