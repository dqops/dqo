/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.cli.commands.cloud.sync;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.cloud.sync.impl.CloudSynchronizationService;
import ai.dqo.cli.terminal.TerminalFactory;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.core.dqocloud.accesskey.DqoCloudCredentialsException;
import ai.dqo.core.jobqueue.DqoQueueJobExecutionException;
import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.core.synchronization.fileexchange.FileSynchronizationDirection;
import ai.dqo.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * 3st level CLI command "cloud sync all" to synchronize all the files the DQO user home.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "all", header = "Synchronize local files with DQO Cloud (sources, table rules, custom rules, custom sensors and data - sensor readouts and rule results)", description = "Uploads any local changes to the cloud and downloads any changes made to the cloud versions of the folders.")
public class CloudSyncAllCliCommand extends BaseCommand implements ICommand {
    private CloudSynchronizationService cloudSynchronizationService;
    private TerminalFactory terminalFactory;

    public CloudSyncAllCliCommand() {
    }

    @Autowired
    public CloudSyncAllCliCommand(CloudSynchronizationService cloudSynchronizationService,
                                  TerminalFactory terminalFactory) {
        this.cloudSynchronizationService = cloudSynchronizationService;
        this.terminalFactory = terminalFactory;
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
            int synchronizeSourcesResult = this.cloudSynchronizationService.synchronizeRoot(
                    DqoRoot.sources, this.mode, this.direction, this.forceRefreshNativeTable, this.isHeadless(), true);
            if (synchronizeSourcesResult < 0) {
                return synchronizeSourcesResult;
            }

            int synchronizeSensorsResult = this.cloudSynchronizationService.synchronizeRoot(
                    DqoRoot.sensors, this.mode, this.direction, this.forceRefreshNativeTable, this.isHeadless(), true);
            if (synchronizeSensorsResult < 0) {
                return synchronizeSensorsResult;
            }

            int synchronizeRulesResult = this.cloudSynchronizationService.synchronizeRoot(
                    DqoRoot.rules, this.mode, this.direction, this.forceRefreshNativeTable, this.isHeadless(), true);
            if (synchronizeRulesResult < 0) {
                return synchronizeRulesResult;
            }

            int synchronizeChecksResult = this.cloudSynchronizationService.synchronizeRoot(
                    DqoRoot.checks, this.mode, this.direction, this.forceRefreshNativeTable, this.isHeadless(), true);
            if (synchronizeChecksResult < 0) {
                return synchronizeChecksResult;
            }

            int synchronizeReadoutsResult = this.cloudSynchronizationService.synchronizeRoot(
                    DqoRoot.data_sensor_readouts, this.mode, this.direction, this.forceRefreshNativeTable, this.isHeadless(), true);
            if (synchronizeReadoutsResult < 0) {
                return synchronizeReadoutsResult;
            }

            int synchronizeRuleResultsResult = this.cloudSynchronizationService.synchronizeRoot(
                    DqoRoot.data_check_results, this.mode, this.direction, this.forceRefreshNativeTable, this.isHeadless(), true);
            if (synchronizeRuleResultsResult < 0) {
                return synchronizeRuleResultsResult;
            }

            int synchronizeErrorsResult = this.cloudSynchronizationService.synchronizeRoot(
                    DqoRoot.data_errors, this.mode, this.direction, this.forceRefreshNativeTable, this.isHeadless(), true);
            if (synchronizeErrorsResult < 0) {
                return synchronizeErrorsResult;
            }

            int synchronizeStatisticsResult = this.cloudSynchronizationService.synchronizeRoot(
                    DqoRoot.data_statistics, this.mode, this.direction, this.forceRefreshNativeTable, this.isHeadless(), true);
            return synchronizeStatisticsResult;
        }
        catch (DqoQueueJobExecutionException cex) {
            if (cex.getRealCause() instanceof DqoCloudCredentialsException) {
                TerminalWriter terminalWriter = this.terminalFactory.getWriter();
                terminalWriter.writeLine("Invalid DQO Cloud credentials, please run \"cloud login\" to get a new DQO Cloud API Key.");
                return -1;
            }

            throw cex;
        }
    }
}
