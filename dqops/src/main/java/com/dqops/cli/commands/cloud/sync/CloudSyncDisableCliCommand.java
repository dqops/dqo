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
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.cloud.impl.CloudLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * 3st level CLI command "cloud sync disable" to disable synchronization with DQOps cloud.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "disable", header = "Disable synchronization with DQOps Cloud, allowing to work offline, but without access to the data quality dashboards")
public class CloudSyncDisableCliCommand extends BaseCommand implements ICommand {
    private CloudLoginService cloudLoginService;

    public CloudSyncDisableCliCommand() {
    }

    @Autowired
    public CloudSyncDisableCliCommand(CloudLoginService cloudLoginService) {
        this.cloudLoginService = cloudLoginService;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        this.cloudLoginService.disableCloudSync();
        return 0;
    }
}
