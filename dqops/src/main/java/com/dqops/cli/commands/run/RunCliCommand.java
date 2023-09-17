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
package com.dqops.cli.commands.run;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.cli.terminal.logging.RootConfigurationProperties;
import com.dqops.core.configuration.DqoSchedulerConfigurationProperties;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import com.dqops.execution.checks.progress.CheckRunReportingMode;
import com.dqops.utils.datetime.DurationParseUtility;
import com.dqops.utils.datetime.InvalidDurationFormatException;
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
@CommandLine.Command(name = "run", header = "Starts DQO in a server mode, continuously running a job scheduler that runs the data quality checks", description = "This command is useful when you want to continuously monitor the quality of your data in real-time. The job scheduler runs in the background, allowing you to perform other tasks while the DQO is running.")
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
     * @param rootConfigurationProperties         Root configuration properties - to detect the "--silent" parameter.
     */
    @Autowired
    public RunCliCommand(JobSchedulerService jobSchedulerService,
                         TerminalReader terminalReader,
                         TerminalWriter terminalWriter,
                         DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties,
                         RootConfigurationProperties rootConfigurationProperties) {
        this.jobSchedulerService = jobSchedulerService;
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
        this.dqoSchedulerConfigurationProperties = dqoSchedulerConfigurationProperties;
        this.synchronizationMode = dqoSchedulerConfigurationProperties.getSynchronizationMode();
        this.checkRunMode = dqoSchedulerConfigurationProperties.getCheckRunMode();
        this.rootConfigurationProperties = rootConfigurationProperties;
    }

    @CommandLine.Option(names = {"-s", "--synchronization-mode"}, description = "Reporting mode for the DQO cloud synchronization (silent, summary, debug)", defaultValue = "silent")
    private FileSystemSynchronizationReportingMode synchronizationMode = FileSystemSynchronizationReportingMode.silent;

    @CommandLine.Option(names = {"-m", "--mode"}, description = "Check execution reporting mode (silent, summary, info, debug)", defaultValue = "silent")
    private CheckRunReportingMode checkRunMode = CheckRunReportingMode.silent;
    private RootConfigurationProperties rootConfigurationProperties;

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
            this.terminalReader.waitForExit(this.rootConfigurationProperties.getSilent() ? null : POST_STARTUP_MESSAGE);
        }
        else {
            String startupMessage = POST_STARTUP_MESSAGE + " DQO will shutdown automatically after " + this.timeLimit + ".";
            this.terminalReader.waitForExitWithTimeLimit(this.rootConfigurationProperties.getSilent() ? null : startupMessage, runDuration);
        }

        this.jobSchedulerService.shutdown();
        return 0;
    }
}
