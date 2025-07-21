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
