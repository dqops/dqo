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
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.core.configuration.DqoSchedulerConfigurationProperties;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationReportingMode;
import ai.dqo.core.scheduler.JobSchedulerService;
import ai.dqo.execution.checks.progress.CheckRunReportingMode;
import ai.dqo.utils.datetime.DurationParseUtility;
import ai.dqo.utils.datetime.InvalidDurationFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.time.Duration;

/**
 * "run" 1st level CLI command - starts DQO in a server mode.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "run", description = "Starts DQO in a server mode, continuously running a job scheduler that runs the data quality checks.")
public class RunCliCommand extends BaseCommand implements ICommand {
    public static final String POST_STARTUP_MESSAGE = "DQO was started in a server mode.";

    private JobSchedulerService jobSchedulerService;
    private TerminalReader terminalReader;
    private TerminalWriter terminalWriter;
    private DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties;

    public RunCliCommand() {
    }

    /**
     * Creates a cli command given the dependencies.
     *
     * @param jobSchedulerService                 Job scheduler dependency.
     * @param terminalReader                      Terminal reader - used to wait for an exit signal.
     * @param terminalWriter                      Terminal writer.
     * @param dqoSchedulerConfigurationProperties DQO job scheduler configuration - used to check if the scheduler is not disabled.
     */
    @Autowired
    public RunCliCommand(JobSchedulerService jobSchedulerService,
                         TerminalReader terminalReader,
                         TerminalWriter terminalWriter,
                         DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties) {
        this.jobSchedulerService = jobSchedulerService;
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
        this.dqoSchedulerConfigurationProperties = dqoSchedulerConfigurationProperties;
    }

    @CommandLine.Option(names = {"-s", "--synchronization-mode"}, description = "Reporting mode for the DQO cloud synchronization (silent, summary, debug)", defaultValue = "summary")
    private FileSystemSynchronizationReportingMode synchronizationMode = FileSystemSynchronizationReportingMode.summary;

    @CommandLine.Option(names = {"-m", "--mode"}, description = "Check execution reporting mode (silent, summary, info, debug)", defaultValue = "summary")
    private CheckRunReportingMode checkRunMode = CheckRunReportingMode.summary;

    @CommandLine.Option(names = {"-t", "--time-limit"}, description = "Optional execution time limit. DQO will run for the given duration and gracefully shut down. " +
            "Supported values are in the following format: 300s (300 seconds), 10m (10 minutes), 2h (run for up to 2 hours) or just a number that is the time limit in seconds.")
    private String timeLimit;

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
        Duration runDuration = null;

        try {
            runDuration = DurationParseUtility.parseSimpleDuration(this.timeLimit);
        }
        catch (InvalidDurationFormatException ex) {
            this.terminalWriter.writeLine("Invalid duration: " + this.timeLimit);
            return -1;
        }

        if (this.dqoSchedulerConfigurationProperties.getStart() == null ||
                this.dqoSchedulerConfigurationProperties.getStart()) {
            // even if the job scheduler is started, we just change the logging modes to the parameters from the "run" command parameters
            this.jobSchedulerService.start(this.synchronizationMode, this.checkRunMode);
        }
        if (this.dqoSchedulerConfigurationProperties.getStart() == null) {
            // the scheduler was not configured before
            this.jobSchedulerService.triggerMetadataSynchronization();
        }

        if (runDuration == null) {
            this.terminalReader.waitForExit(POST_STARTUP_MESSAGE);
        }
        else {
            this.terminalReader.waitForExitWithTimeLimit(POST_STARTUP_MESSAGE +
                    " DQO will shutdown automatically after " + this.timeLimit + ".", runDuration);
        }

        this.jobSchedulerService.shutdown();
        return 0;
    }
}
