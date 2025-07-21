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
import com.dqops.cli.commands.cloud.sync.impl.CloudSynchronizationCliService;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.core.dqocloud.accesskey.DqoCloudCredentialsException;
import com.dqops.core.jobqueue.exceptions.DqoQueueJobExecutionException;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.fileexchange.FileSynchronizationDirection;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * 3st level CLI command "cloud sync sensors" to synchronize the "sensors" folder with custom rules in the DQOps user home.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "sensors", header = "Synchronize local \"sensors\" folder with custom sensor definitions with DQOps Cloud",
        description = "Uploads any local changes to the cloud and downloads any changes made to the cloud version of the \"sensors\" folder.")
public class CloudSyncSensorsCliCommand extends BaseCommand implements ICommand {
    private CloudSynchronizationCliService cloudSynchronizationCliService;
    private TerminalFactory terminalFactory;
    private DqoUserPrincipalProvider principalProvider;

    public CloudSyncSensorsCliCommand() {
    }

    @Autowired
    public CloudSyncSensorsCliCommand(CloudSynchronizationCliService cloudSynchronizationCliService,
                                      TerminalFactory terminalFactory,
                                      DqoUserPrincipalProvider principalProvider) {
        this.cloudSynchronizationCliService = cloudSynchronizationCliService;
        this.terminalFactory = terminalFactory;
        this.principalProvider = principalProvider;
    }

    @CommandLine.Option(names = {"-m", "--mode"}, description = "Reporting mode (silent, summary, debug)", defaultValue = "summary")
    private FileSystemSynchronizationReportingMode mode = FileSystemSynchronizationReportingMode.summary;

    @CommandLine.Option(names = {"-d", "--direction"}, description = "File synchronization direction", defaultValue = "full")
    private FileSynchronizationDirection direction = FileSynchronizationDirection.full;

    /**
     * Returns the synchronization logging mode.
     * @return Logging mode.
     */
    public FileSystemSynchronizationReportingMode getMode() {
        return mode;
    }

    /**
     * Sets the reporting (logging) mode.
     * @param mode Reporting mode.
     */
    public void setMode(FileSystemSynchronizationReportingMode mode) {
        this.mode = mode;
    }

    /**
     * Returns the file synchronization direction.
     * @return File synchronization direction.
     */
    public FileSynchronizationDirection getDirection() {
        return direction;
    }

    /**
     * Sets the file synchronization direction.
     * @param direction File synchronization direction.
     */
    public void setDirection(FileSynchronizationDirection direction) {
        this.direction = direction;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        try {
            DqoUserPrincipal principal = this.principalProvider.getLocalUserPrincipal();
            return this.cloudSynchronizationCliService.synchronizeRoot(
                    DqoRoot.sensors, this.mode, this.direction, false, this.isHeadless(), true, principal);
        }
        catch (DqoQueueJobExecutionException cex) {
            if (cex.getRealCause() instanceof DqoCloudCredentialsException) {
                TerminalWriter terminalWriter = this.terminalFactory.getWriter();
                terminalWriter.writeLine(CloudSyncCliCommand.API_KEY_INVALID_MESSAGE);
                return -1;
            }

            throw cex;
        }
    }
}
