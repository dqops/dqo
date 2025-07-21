/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.cloud.sync;

import com.dqops.cli.commands.BaseCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "cloud sync" 2nd level cli command to connect and synchronize with DQOps Cloud
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "sync", description = "Synchronize local data with DQOps Cloud", subcommands = {
        CloudSyncEnableCliCommand.class,
        CloudSyncDisableCliCommand.class,
        CloudSyncDataCliCommand.class,
        CloudSyncSourcesCliCommand.class,
        CloudSyncSensorsCliCommand.class,
        CloudSyncRulesCliCommand.class,
        CloudSyncChecksCliCommand.class,
        CloudSyncSettingsCliCommand.class,
        CloudSyncCredentialsCliCommand.class,
        CloudSyncDictionariesCliCommand.class,
        CloudSyncPatternsCliCommand.class,
        CloudSyncAllCliCommand.class
})
public class CloudSyncCliCommand extends BaseCommand {
    /**
     * Message shown by CLI operations when the DQOps Cloud api key is invalid.
     */
    public static final String API_KEY_INVALID_MESSAGE = "Invalid DQOps Cloud credentials, probably your trial period has expired or a new version of DQOps was released. " +
            "Please run \"cloud login\" from the command-line to get a new DQOps Cloud API Key.";
}
