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
