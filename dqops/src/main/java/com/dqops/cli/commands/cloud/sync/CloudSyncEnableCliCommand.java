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
import com.dqops.cli.commands.cloud.sync.impl.CloudSynchronizationCliService;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.core.dqocloud.accesskey.DqoCloudCredentialsException;
import com.dqops.core.jobqueue.exceptions.DqoQueueJobExecutionException;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.fileexchange.FileSynchronizationDirection;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * 3st level CLI command "cloud sync enable" to enable synchronization with DQOps cloud if it was disabled before.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "enable", header = "Enables synchronization with DQOps Cloud if it was disabled before")
public class CloudSyncEnableCliCommand extends BaseCommand implements ICommand {
    private CloudLoginService cloudLoginService;

    public CloudSyncEnableCliCommand() {
    }

    @Autowired
    public CloudSyncEnableCliCommand(CloudLoginService cloudLoginService) {
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
        this.cloudLoginService.enableCloudSync();
        return 0;
    }
}
