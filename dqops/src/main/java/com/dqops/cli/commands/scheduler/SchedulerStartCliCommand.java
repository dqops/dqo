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
package com.dqops.cli.commands.scheduler;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.ICommand;
import com.dqops.core.configuration.DqoSchedulerConfigurationProperties;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import com.dqops.execution.checks.progress.CheckRunReportingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "scheduler start" 2nd level CLI command - starts a scheduler that will run checks.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "start", header = "Starts a background job scheduler",
        description = "This operation should be called only from the shell mode. When the DQOps is started as 'dqo scheduler start' from the operating system, it will stop immediately.")
public class SchedulerStartCliCommand extends BaseCommand implements ICommand {
    private JobSchedulerService jobSchedulerService;
    private DqoSchedulerConfigurationProperties schedulerConfigurationProperties;

    public SchedulerStartCliCommand() {
    }

    /**
     * Creates a cli command given the dependencies.
     * @param jobSchedulerService Job scheduler dependency.
     * @param schedulerConfigurationProperties DQOps Scheduler configuration - used to get the default values.
     */
    @Autowired
    public SchedulerStartCliCommand(JobSchedulerService jobSchedulerService,
                                    DqoSchedulerConfigurationProperties schedulerConfigurationProperties) {
        this.jobSchedulerService = jobSchedulerService;
        this.schedulerConfigurationProperties = schedulerConfigurationProperties;
        this.synchronizationMode = schedulerConfigurationProperties.getSynchronizationMode();
        this.checkRunMode = schedulerConfigurationProperties.getCheckRunMode();
    }

    @CommandLine.Option(names = {"-sm", "--synchronization-mode"}, description = "Reporting mode for the DQOps cloud synchronization (silent, summary, debug)")
    private FileSystemSynchronizationReportingMode synchronizationMode = FileSystemSynchronizationReportingMode.silent;

    @CommandLine.Option(names = {"-crm", "--check-run-mode"}, description = "Check execution reporting mode (silent, summary, info, debug)")
    private CheckRunReportingMode checkRunMode = CheckRunReportingMode.silent;

    /**
     * Returns the synchronization logging mode.
     * @return Logging mode.
     */
    public FileSystemSynchronizationReportingMode getSynchronizationMode() {
        return synchronizationMode;
    }

    /**
     * Sets the reporting (logging) mode.
     * @param synchronizationMode Reporting mode.
     */
    public void setSynchronizationMode(FileSystemSynchronizationReportingMode synchronizationMode) {
        this.synchronizationMode = synchronizationMode;
    }

    /**
     * Returns the check execution reporting mode.
     * @return Check execution reporting mode.
     */
    public CheckRunReportingMode getCheckRunMode() {
        return checkRunMode;
    }

    /**
     * Sets the check execution reporting mode.
     * @param checkRunMode Check execution reporting mode.
     */
    public void setCheckRunMode(CheckRunReportingMode checkRunMode) {
        this.checkRunMode = checkRunMode;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        if (!this.jobSchedulerService.isStarted()) {
            this.jobSchedulerService.start(this.synchronizationMode, this.checkRunMode);
            this.jobSchedulerService.triggerMetadataSynchronization();

            return 0;
        }

        return -1; // scheduler already running
    }
}
