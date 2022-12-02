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
package ai.dqo.cli.commands.run;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.core.configuration.DqoSchedulerConfigurationProperties;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationReportingMode;
import ai.dqo.core.scheduler.JobSchedulerService;
import ai.dqo.execution.checks.progress.CheckRunReportingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "run" 1st level CLI command - starts DQO in a server mode.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "run", description = "Starts DQO in a server mode, continuously running a job scheduler that runs the data quality checks.")
public class RunCliCommand extends BaseCommand implements ICommand {
    private JobSchedulerService jobSchedulerService;
    private TerminalReader terminalReader;
    private DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties;

    /**
     * Creates a cli command given the dependencies.
     *
     * @param jobSchedulerService                 Job scheduler dependency.
     * @param terminalReader                      Terminal reader - used to wait for an exit signal.
     * @param dqoSchedulerConfigurationProperties DQO job scheduler configuration - used to check if the scheduler is not disabled.
     */
    @Autowired
    public RunCliCommand(JobSchedulerService jobSchedulerService,
                         TerminalReader terminalReader,
                         DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties) {
        this.jobSchedulerService = jobSchedulerService;
        this.terminalReader = terminalReader;
        this.dqoSchedulerConfigurationProperties = dqoSchedulerConfigurationProperties;
    }

    @CommandLine.Option(names = {"-s", "--synchronization-mode"}, description = "Reporting mode for the DQO cloud synchronization (silent, summary, debug)", defaultValue = "summary")
    private FileSystemSynchronizationReportingMode synchronizationMode = FileSystemSynchronizationReportingMode.summary;

    @CommandLine.Option(names = {"-m", "--mode"}, description = "Check execution reporting mode (silent, summary, info, debug)", defaultValue = "summary")
    private CheckRunReportingMode checkRunMode = CheckRunReportingMode.summary;

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
        if (this.dqoSchedulerConfigurationProperties.getStart() == null ||
                this.dqoSchedulerConfigurationProperties.getStart()) {
            // even if the job scheduler is started, we just change the logging modes to the parameters from the "run" command parameters
            this.jobSchedulerService.start(this.synchronizationMode, this.checkRunMode);
        }
        if (this.dqoSchedulerConfigurationProperties.getStart() == null) {
            // the scheduler was not configured before
            this.jobSchedulerService.triggerMetadataSynchronization();
        }
        this.terminalReader.waitForExit("DQO was started in a server mode.");
        this.jobSchedulerService.shutdown();
        return 0;
    }
}
