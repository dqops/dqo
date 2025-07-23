/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.cloud;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.cloud.sync.CloudSyncCliCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "cloud" 1st level cli command to connect and synchronize with DQOps Cloud
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "cloud", description = "Manage the DQOps Cloud account. This group of commands supports connecting this DQOps instance to a DQOps Cloud account, " +
        "changing the password and synchronizing local data with the Data Quality Data Warehouse hosted by DQOps Cloud",
        subcommands = {
            CloudLoginCliCommand.class,
            CloudPasswordCliCommand.class,
            CloudSyncCliCommand.class
})
public class CloudCliCommand extends BaseCommand {
}
