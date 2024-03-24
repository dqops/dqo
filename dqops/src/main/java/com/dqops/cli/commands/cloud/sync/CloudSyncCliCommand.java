/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
