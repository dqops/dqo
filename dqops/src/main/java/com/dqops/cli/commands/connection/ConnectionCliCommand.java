/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.connection;

import com.dqops.cli.commands.BaseCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "connection" 1st level cli command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "connection", description = "Modify or list connections", subcommands = {
        ConnectionListCliCommand.class,
        ConnectionAddCliCommand.class,
        ConnectionRemoveCliCommand.class,
        ConnectionUpdateCliCommand.class,
		ConnectionSchemaCliCommand.class,
		ConnectionTableCliCommand.class,
		ConnectionEditCliCommand.class
})
public class ConnectionCliCommand extends BaseCommand {
}
