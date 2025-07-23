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
 * 3st level CLI command "cloud sync data" to synchronize the "data" folder with sensor readouts and rule results in the DQOps user home.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "data", header = "Synchronize local \"data\" folder with sensor readouts and rule results with DQOps Cloud",
        description = "Uploads any local changes to the cloud and downloads any changes made to the cloud version of the \"data\" folder.")
public class CloudSyncDataCliCommand extends BaseCommand implements ICommand {
    private CloudSynchronizationCliService cloudSynchronizationCliService;
    private TerminalFactory terminalFactory;
    private DqoUserPrincipalProvider principalProvider;

    public CloudSyncDataCliCommand() {
    }

    @Autowired
    public CloudSyncDataCliCommand(CloudSynchronizationCliService cloudSynchronizationCliService,
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

    @CommandLine.Option(names = {"-r", "--refresh-data-warehouse"}, description = "Force refresh a whole table in the data quality data warehouse", defaultValue = "false")
    private boolean forceRefreshNativeTable;

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
     * Returns true when the native table should be refreshed fully.
     * @return True when the native table must be fully refreshed.
     */
    public boolean isForceRefreshNativeTable() {
        return forceRefreshNativeTable;
    }

    /**
     * Sets the flag to fully refresh a native table.
     * @param forceRefreshNativeTable True when the native table should be fully refreshed.
     */
    public void setForceRefreshNativeTable(boolean forceRefreshNativeTable) {
        this.forceRefreshNativeTable = forceRefreshNativeTable;
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

            int synchronizeReadoutsResult = this.cloudSynchronizationCliService.synchronizeRoot(
                    DqoRoot.data_sensor_readouts, this.mode, this.direction, this.forceRefreshNativeTable, this.isHeadless(), true, principal);
            if (synchronizeReadoutsResult < 0) {
                return synchronizeReadoutsResult;
            }
            int synchronizeRuleResultsResult = this.cloudSynchronizationCliService.synchronizeRoot(
                    DqoRoot.data_check_results, this.mode, this.direction, this.forceRefreshNativeTable, this.isHeadless(), true, principal);
            if (synchronizeRuleResultsResult < 0) {
                return synchronizeReadoutsResult;
            }

            int synchronizeErrorsResult = this.cloudSynchronizationCliService.synchronizeRoot(
                    DqoRoot.data_errors, this.mode, this.direction, this.forceRefreshNativeTable, this.isHeadless(), true, principal);
            if (synchronizeErrorsResult < 0) {
                return synchronizeErrorsResult;
            }

            int synchronizeStatisticsResult = this.cloudSynchronizationCliService.synchronizeRoot(
                    DqoRoot.data_statistics, this.mode, this.direction, this.forceRefreshNativeTable, this.isHeadless(), true, principal);
            if (synchronizeStatisticsResult < 0) {
                return synchronizeStatisticsResult;
            }

            int synchronizeIncidentsResult = this.cloudSynchronizationCliService.synchronizeRoot(
                    DqoRoot.data_incidents, this.mode, this.direction, this.forceRefreshNativeTable, this.isHeadless(), true, principal);

            return synchronizeIncidentsResult;
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
