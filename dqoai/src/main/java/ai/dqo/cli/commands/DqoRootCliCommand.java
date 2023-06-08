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
package ai.dqo.cli.commands;

import ai.dqo.cli.commands.check.CheckCliCommand;
import ai.dqo.cli.commands.cloud.CloudCliCommand;
import ai.dqo.cli.commands.column.ColumnCliCommand;
import ai.dqo.cli.commands.connection.ConnectionCliCommand;
import ai.dqo.cli.commands.data.DataCliCommand;
import ai.dqo.cli.commands.impl.DqoShellRunnerService;
import ai.dqo.cli.commands.rule.RuleCliCommand;
import ai.dqo.cli.commands.run.RunCliCommand;
import ai.dqo.cli.commands.scheduler.SchedulerCliCommand;
import ai.dqo.cli.commands.sensor.SensorCliCommand;
import ai.dqo.cli.commands.settings.SettingsCliCommand;
import ai.dqo.cli.commands.table.TableCliCommand;
import ai.dqo.cli.commands.utility.ClearScreenCliCommand;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.core.configuration.DqoLoggingConfigurationProperties;
import ai.dqo.core.scheduler.JobSchedulerService;
import ai.dqo.core.scheduler.synchronize.ScheduledSynchronizationFolderSelectionMode;
import ai.dqo.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import ai.dqo.execution.checks.progress.CheckRunReportingMode;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;

/**
 * DQO root CLI command.
 */
@Component
@CommandLine.Command(
        name = "",
        description = {
                "DQO Interactive Shell",
                "Hit @|yellow <TAB>|@ to see available commands.",
                "Hit @|yellow ALT-S|@ to toggle tailtips.",
                ""},
        footer = {"", "Press Ctrl-D to exit."},
        subcommands = {
            ClearScreenCliCommand.class,
            ConnectionCliCommand.class,
            TableCliCommand.class,
            CheckCliCommand.class,
            ColumnCliCommand.class,
            SettingsCliCommand.class,
            CloudCliCommand.class,
            SensorCliCommand.class,
            SchedulerCliCommand.class,
            DataCliCommand.class,
            RunCliCommand.class,
            RuleCliCommand.class
        }
)
public class DqoRootCliCommand extends BaseCommand implements ICommand {
    private BeanFactory beanFactory;
    private JobSchedulerService jobSchedulerService;
    private TerminalWriter terminalWriter;

    public DqoRootCliCommand() {
    }

    /**
     * Creates a default root CLI command.
     * @param beanFactory Bean factory - used to delay the creation of the shell runner.
     * @param jobSchedulerService Job scheduler - stops the scheduler on exit.
     * @param terminalWriter Terminal writer.
     */
    @Autowired
    public DqoRootCliCommand(BeanFactory beanFactory,
                             JobSchedulerService jobSchedulerService,
                             TerminalWriter terminalWriter) {
        this.beanFactory = beanFactory;
        this.jobSchedulerService = jobSchedulerService;
        this.terminalWriter = terminalWriter;
    }

    /**
     * Add fake parameters here to support overriding configuration parameters from application.yaml.
     * When user starts the application and passes a parameter that starts with "--" followed by a dot separated configuration property name,
     * Spring Boot will override the given parameter. However, we want to show the list of these parameters when the user runs "dqo --help" and we also don't want
     * picocli to fail because a parameter is unknown, so we make all these overridable parameters known to picocli.
     */

    @CommandLine.Option(names = {"--dqo.cloud.api-key"},
            description = "DQO cloud api key. Log in to https://cloud.dqo.ai/ to get the key.")
    private String dqoCloudApiKey;

    @CommandLine.Option(names = {"--server.port"},
            description = "Sets the web server port to host the DQO local web UI.", defaultValue = "8888")
    private Integer serverPort;

    @CommandLine.Option(names = {"--dqo.user.initialize-user-home"},
            description = "Initializes an empty DQO user home (identified by the DQO_USER_HOME environment variable) without asking the user for confirmation.", defaultValue = "false")
    private Boolean dqoUserInitializeUserHome;

    @CommandLine.Option(names = {"--logging.level.root"},
            description = "Default logging level at the root level of the logging hierarchy.", defaultValue = "WARN")
    private org.slf4j.event.Level loggingLevelRoot;

    @CommandLine.Option(names = {"--logging.level.ai.dqo"},
            description = "Default logging level for the DQO runtime.", defaultValue = "WARN")
    private org.slf4j.event.Level loggingLevelAiDqo;

    @CommandLine.Option(names = {"--dqo.logging.enable-user-home-logging"},
            description = "Enables file logging inside the DQO User Home's .logs folder.", defaultValue = "true")
    private boolean dqoLoggingEnableUserHomeLogging;

    @CommandLine.Option(names = {"--dqo.logging.max-history"},
            description = "Sets the maximum number of log files that could be stored (archived) in the .logs folder.", defaultValue = "7")
    private Integer maxHistory;

    @CommandLine.Option(names = {"--dqo.logging.pattern"},
            description = "Log entry pattern for logback used for writing log entries.", defaultValue = DqoLoggingConfigurationProperties.DEFAULT_PATTERN)
    private String dqoLoggingPattern;

    @CommandLine.Option(names = {"--dqo.logging.total-size-cap"},
            description = "Total log file size cap.", defaultValue = DqoLoggingConfigurationProperties.DEFAULT_TOTAL_SIZE_CAP)
    private String dqoLoggingTotalSizeCap;

    @CommandLine.Option(names = {"--dqo.python.python-script-timeout-seconds"},
            description = "Python script execution time limit in seconds for running jinja2 and rule evaluation scripts.", defaultValue = "120")
    private Integer dqoPythonPythonScriptTimeoutSeconds;

    @CommandLine.Option(names = {"--dqo.python.interpreter"},
            description = "Python interpreter command line name, like 'python' or 'python3'.", defaultValue = "python3")
    private String dqoPythonInterpreter;

    @CommandLine.Option(names = {"--dqo.user.home"},
            description = "Overrides the path to the DQO user home. The default user home is created in the current folder (.).", defaultValue = ".")
    private String dqoUserHome;

    @CommandLine.Option(names = {"--dqo.home"},
            description = "Overrides the path to the DQO system home (DQO_HOME). The default DQO_HOME contains the definition of built-in data quality sensors, rules and libraries.")
    private String dqoHome;

    @CommandLine.Option(names = {"--dqo.default-time-zone"},
            description = "Default time zone name used to convert the server's local dates to a local time in a time zone that is relevant for the user. Use official IANA time zone names. " +
                    "When the parameter is not configured, DQO uses the local time zone of the host running the application. The time zone could be reconfigured at a user settings level.")
    private String dqoDefaultTimeZone;

    @CommandLine.Option(names = {"--dqo.incidents.count-open-incidents-days"},
            description = "The number of days since today that are scanned for open incidents first seen in since this number of days.", defaultValue = "15")
    private Integer dqoIncidentsCountOpenIncidentsDays;

    @CommandLine.Option(names = {"--dqo.incidents.column-histogram-size"},
            description = "The size of the column histogram that is generated for a preview of a data quality incident.", defaultValue = "10")
    private Integer dqoIncidentsColumnHistogramSize;

    @CommandLine.Option(names = {"--dqo.incidents.check-histogram-size"},
            description = "The size of the data quality check histogram that is generated for a preview of a data quality incident.", defaultValue = "10")
    private Integer dqoIncidentsCheckHistogramSize;

    @CommandLine.Option(names = {"--dqo.jdbc.max-connection-in-pool"},
            description = "Sets the maximum number of connections in the JDBC connection pool, shared across all data sources using JDBC drivers.", defaultValue = "1000")
    private Integer dqoJdbcMaxConnectionInPool;

    @CommandLine.Option(names = {"--dqo.jdbc.expire-after-access-seconds"},
            description = "Sets the number of seconds when a connection in a JDBC pool is expired after the last access.", defaultValue = "1800")
    private Integer dqoJdbcExpireAfterAccessSeconds;

    @CommandLine.Option(names = {"--dqo.secrets.enable-gcp-secret-manager"},
            description = "Enables GCP secret manager to resolve parameters like ${sm:secret-name} in the yaml files.", defaultValue = "true")
    private Boolean dqoSecretsEnableGcpSecretManager;

    @CommandLine.Option(names = {"--dqo.secrets.gcp-project-id"},
            description = "GCP project name with a GCP secret manager enabled to pull the secrets.", defaultValue = "true")
    private Boolean dqoSecretsGcpProjectId;

    @CommandLine.Option(names = {"--dqo.cli.terminal.width"},
            description = "Width of the terminal when no terminal window is available, e.g. in one-shot running mode.", defaultValue = "100")
    private Integer dqoCliTerminalWidth;

    @CommandLine.Option(names = {"--dqo.core.print-stack-trace"},
            description = "Prints a full stack trace for errors on the console.", defaultValue = "true")
    private Boolean dqoCorePrintStackTrace;

    @CommandLine.Option(names = {"--dqo.core.lock-wait-timeout-seconds"},
            description = "Sets the maximum wait timeout in seconds to obtain a lock to read or write files.", defaultValue = "900")
    private Long dqoCoreLockWaitTimeoutSeconds;

    @CommandLine.Option(names = {"--dqo.cloud.parallel-file-uploads"},
            description = "The number of files that are uploaded to DQO Cloud in parallel using HTTP/2 multiplexing.", defaultValue = "500")
    private Integer dqoCloudParallelFileUploads;

    @CommandLine.Option(names = {"--dqo.cloud.parallel-file-downloads"},
            description = "The number of files that are downloaded from DQO Cloud in parallel using HTTP/2 multiplexing.", defaultValue = "500")
    private Integer dqoCloudParallelFileDownloads;

    @CommandLine.Option(names = {"--dqo.queue.max-concurrent-jobs"},
            description = "Sets the maximum number of concurrent jobs that the job queue can process at once (running data quality checks, importing metadata, etc.). The maximum number of threads is also limited by the DQO license.")
    private Long dqoQueueMaxConcurrentJobs;

    @CommandLine.Option(names = {"--dqo.queue.wait-timeouts.run-checks"},
            description = "Sets the default timeout (in seconds) for the \"run checks\" rest api operation called from the DQO client when the \"wait\" parameter is true and the timeout is not provided by the client.", defaultValue = "120")
    private Long dqoQueueWaitTimeoutsRunChecks;

    @CommandLine.Option(names = {"--dqo.scheduler.start"},
            description = "Starts the job scheduler on startup (true) or disables the job scheduler (false).")
    private Boolean dqoSchedulerStart;

    @CommandLine.Option(names = {"--dqo.scheduler.enable-cloud-sync"},
            description = "Enable synchronization of metadata and results with DQO Cloud in the job scheduler.", defaultValue = "true")
    private Boolean dqoSchedulerEnableCloudSync;

    @CommandLine.Option(names = {"--dqo.scheduler.synchronize-cron-schedule"},
            description = "Unix cron expression to configure how often the scheduler will synchronize the local copy of the metadata with DQO Cloud and detect new cron schedules. Synchronization with DQO cloud could be disabled by setting --dqo.scheduler.enable-cloud-sync=false.", defaultValue = "*/10 * * * *")
    private String dqoSchedulerSynchronizeCronSchedule;

    @CommandLine.Option(names = {"--dqo.scheduler.synchronized-folders"},
            description = "Configures which folders from the DQO user home folder are synchronized to DQO Cloud during a recurring synchronization (triggered by a cron schedule configured by --dqo.scheduler.synchronize-cron-schedule). By default, DQO synchronizes (pushes) only changes from folders that have local changes.", defaultValue = "locally_changed")
    private ScheduledSynchronizationFolderSelectionMode dqoSchedulerSynchronizedFolders = ScheduledSynchronizationFolderSelectionMode.locally_changed;

    @CommandLine.Option(names = {"--dqo.scheduler.synchronization-mode"},
            description = "Configures the console logging mode for the '\"cloud sync all\" operations performed by the job scheduler in the background.", defaultValue = "silent")
    private FileSystemSynchronizationReportingMode dqoSchedulerSynchronizationMode;

    @CommandLine.Option(names = {"--dqo.scheduler.check-run-mode"},
            description = "Configures the console logging mode for the '\"check run\" jobs performed by the job scheduler in the background.", defaultValue = "silent")
    private CheckRunReportingMode dqoSchedulerCheckRunMode;

    @CommandLine.Option(names = {"--dqo.docker.userhome.allow-unmounted"},
            description = "When running DQO in a docker container, allow DQO user home folder to be initialized inside the container's filesystem if the folder hasn't been mounted to an external volume.", defaultValue = "false")
    private Boolean dqoDockerUserhomeAllowUnmounted;

    @CommandLine.Option(names = {"--spring.config.location"},
            description = "Sets a path to the folder that has the spring configuration files (application.properties or application.yml) or directly to an application.properties or application.yml file. " +
                    "The format of this value is: --spring.config.location=file:./foldername/,file:./alternativeapplication.yml")
    private String springConfigLocation;

    /**
     * This field will capture all remaining parameters that could be also in the form "--name" and should be captured by Spring to update the configuration parameters.
     */
    @CommandLine.Unmatched
    private List<String> remainingUnmatchedArguments;

    /**
     * Analyses the argument list and detects those that do not look like "--paramname", so they will not be picked up by Spring Boot Externalized configuration
     * to update the application configuration.
     * @param arguments List of arguments.
     * @return null when all arguments are valid, or the first invalid argument that was found
     */
    public String findFirstInvalidNonConfigurationArgument(List<String> arguments) {
        if (arguments == null || arguments.size() == 0) {
            return null;
        }

        for (String argument : arguments) {
            if (!argument.startsWith("--") || argument.length() < 8) {
                return argument;
            }
        }

        return null;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        DqoShellRunnerService shellRunnerService = this.beanFactory.getBean(DqoShellRunnerService.class);

        String firstInvalidNonConfigurationArgument = findFirstInvalidNonConfigurationArgument(this.remainingUnmatchedArguments);
        if (firstInvalidNonConfigurationArgument != null) {
            this.terminalWriter.writeLine("Invalid argument found: " + firstInvalidNonConfigurationArgument);
            return -1;
        }

        try {
            return shellRunnerService.call();
        }
        finally {
            this.jobSchedulerService.shutdown(); // shutdown the job scheduler in case that it was running
        }
    }
}
