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
package com.dqops.cli.commands;

import com.dqops.cli.commands.check.CheckCliCommand;
import com.dqops.cli.commands.cloud.CloudCliCommand;
import com.dqops.cli.commands.column.ColumnCliCommand;
import com.dqops.cli.commands.connection.ConnectionCliCommand;
import com.dqops.cli.commands.data.DataCliCommand;
import com.dqops.cli.commands.impl.DqoShellRunnerService;
import com.dqops.cli.commands.rule.RuleCliCommand;
import com.dqops.cli.commands.run.RunCliCommand;
import com.dqops.cli.commands.scheduler.SchedulerCliCommand;
import com.dqops.cli.commands.sensor.SensorCliCommand;
import com.dqops.cli.commands.settings.SettingsCliCommand;
import com.dqops.cli.commands.table.TableCliCommand;
import com.dqops.cli.commands.utility.ClearScreenCliCommand;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.utils.logging.DqoConsoleLoggingMode;
import com.dqops.core.configuration.DqoLoggingConfigurationProperties;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.core.scheduler.synchronize.ScheduledSynchronizationFolderSelectionMode;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import com.dqops.execution.checks.progress.CheckRunReportingMode;
import org.slf4j.event.Level;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;

/**
 * DQOps root CLI command.
 */
@Component
@CommandLine.Command(
        name = "",
        description = {
                "DQOps Data Quality Operations Center Interactive Shell",
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


    @CommandLine.Option(names = {"--DQO_JAVA_OPTS"},
            description = "Configures additional JVM (Java Virtual Machine) options such as the memory limit. " +
                    "The default value for both the 'dqops' python package and for the dqops/dqo Docker image is -XX:MaxRAMPercentage=80.0 which " +
                    "sets the upper memory limit for 80%% of the available RAM at the moment when the container starts. " +
                    "This parameter is not supported as a command line parameter, it is only supported as an environment variable. " +
                    "Set (and export) the environment variable DQO_JAVA_OPTS before starting DQOps.", defaultValue = "-XX:MaxRAMPercentage=80.0")
    private String DQO_JAVA_OPTS;

    @CommandLine.Option(names = {"--silent"},
            description = "Starts DQOps in a silent mode, without showing the banner and any other information.", defaultValue = "false")
    private boolean silent;

    @CommandLine.Option(names = {"--dqo.cloud.api-key"},
            description = "DQOps Cloud api key. Log in to https://cloud.dqops.com/ to get the key.")
    private String dqoCloudApiKey;

    @CommandLine.Option(names = {"--server.port"},
            description = "Sets the web server port to host the DQOps local web UI.", defaultValue = "8888")
    private Integer serverPort;

    @CommandLine.Option(names = {"--dqo.user.initialize-user-home"},
            description = "Initializes an empty DQOps user home (identified by the DQO_USER_HOME environment variable) without asking the user for confirmation.", defaultValue = "false")
    private Boolean dqoUserInitializeUserHome;

    @CommandLine.Option(names = {"--logging.level.root"},
            description = "Default logging level at the root level of the logging hierarchy.", defaultValue = "WARN")
    private org.slf4j.event.Level loggingLevelRoot;

    @CommandLine.Option(names = {"--logging.level.com.dqops"},
            description = "Default logging level for the DQOps runtime.", defaultValue = "WARN")
    private org.slf4j.event.Level loggingLevelComDqops;

    @CommandLine.Option(names = {"--dqo.logging.console"},
            description = "Enables logging to console, selecting the correct format. " +
                    "The default configuration 'OFF' disables console logging, allowing to use the DQOps shell without being distracted by log entries. " +
                    "Set the 'PATTERN' mode to send formatted entries to the console in a format similar to Apache logs. " +
                    "When running DQOps in as a docker container on a Kubernetes engine that is configured to capture DQOps container logs, use 'JSON' mode to publish " +
                    "structured Json log entries that can be parsed by fluentd or other similar log engines. JSON formatted messages use a Logstash compatible format.", defaultValue = "OFF")
    private DqoConsoleLoggingMode dqoLoggingConsole;

    @CommandLine.Option(names = {"--dqo.logging.console-immediate-flush"},
            description = "When the console logging is enabled with --dqo.logging.console=PATTERN or --dqo.logging.console=JSON, turns on (for 'true') or turns of (for 'false') " +
                          "immediate console flushing after each log entry was written. Immediate console flushing is desirable when DQOps is started as a docker container " +
                          "and docker logs from DQOps should be forwarded to Kubernetes for centralized logging.", defaultValue = "false")
    private Boolean dqoLoggingConsoleImmediateFlush;

    @CommandLine.Option(names = {"--dqo.logging.pattern"},
            description = "Log entry pattern for logback used for writing log entries.", defaultValue = DqoLoggingConfigurationProperties.DEFAULT_PATTERN)
    private String dqoLoggingPattern;

    @CommandLine.Option(names = {"--dqo.logging.enable-user-home-logging"},
            description = "Enables file logging inside the DQOps User Home's .logs folder.", defaultValue = "true")
    private boolean dqoLoggingEnableUserHomeLogging;

    @CommandLine.Option(names = {"--dqo.logging.max-history"},
            description = "Sets the maximum number of log files that can be stored (archived) in the .logs folder.", defaultValue = "7")
    private Integer maxHistory;

    @CommandLine.Option(names = {"--dqo.logging.total-size-cap"},
            description = "Total log file size cap of log files generated in the DQOps User Home's .logs folder. Supported suffixes are: kb, mb, gb. For example: 10mb, 2gb.",
            defaultValue = DqoLoggingConfigurationProperties.DEFAULT_TOTAL_SIZE_CAP)
    private String dqoLoggingTotalSizeCap;

    @CommandLine.Option(names = {"--dqo.logging.user-errors.sensors-log-level"},
            description = "The logging level at which any errors captured during the data quality sensor (query) execution are reported. " +
                    "The logging level for the whole application must be equal or higher to this level for effective logging. " +
                    "Sensor logs are logged under the com.dqops.user-errors.sensors log.", defaultValue = "WARN")
    private Level dqologgingUserErrorsSensorsLogLevel;

    @CommandLine.Option(names = {"--dqo.logging.user-errors.rules-log-level"},
            description = "The logging level at which any errors captured during the data quality rule (python function) evaluation are reported. " +
                    "The logging level for the whole application must be equal or higher to this level for effective logging. " +
                    "Rule logs are logged under the com.dqops.user-errors.rules log.", defaultValue = "WARN")
    private Level dqologgingUserErrorsRulesLogLevel;

    @CommandLine.Option(names = {"--dqo.logging.user-errors.checks-log-level"},
            description = "The logging level at which any errors captured during the data quality check evaluation are reported. " +
                    "When a data quality check is executed and the error is related to a sensor (query) or a rule (python) function, they are reported as sensor or rules issues." +
                    "Only data quality check configuration issues that prevent running a data quality check are reported as check issues. " +
                    "The logging level for the whole application must be equal or higher to this level for effective logging. " +
                    "Check logs are logged under the com.dqops.user-errors.checks log.", defaultValue = "WARN")
    private Level dqologgingUserErrorsChecksLogLevel;

    @CommandLine.Option(names = {"--dqo.logging.user-errors.statistics-log-level"},
            description = "The logging level at which any errors captured during the statistics collection are reported. " +
                    "The logging level for the whole application must be equal or higher to this level for effective logging. " +
            "Statistics logs are logged under the com.dqops.user-errors.statistics log.", defaultValue = "WARN")
    private Level dqologgingUserErrorsStatisticsLogLevel;

    @CommandLine.Option(names = {"--dqo.logging.user-errors.yaml-log-level"},
            description = "The logging level at which any errors captured during YAML file parsing are reported. " +
                    "The logging level for the whole application must be equal or higher to this level for effective logging. " +
                    "Statistics logs are logged under the com.dqops.user-errors.yaml log.", defaultValue = "WARN")
    private Level dqologgingUserErrorsYamlLogLevel;

    @CommandLine.Option(names = {"--dqo.python.python-script-timeout-seconds"},
            description = "Python script execution time limit in seconds for running jinja2 and rule evaluation scripts.", defaultValue = "120")
    private Integer dqoPythonPythonScriptTimeoutSeconds;

    @CommandLine.Option(names = {"--dqo.python.interpreter-name"},
            description = "A list of python interpreter executable names, separated by a comma, containing possible python interpreter names such as 'python', 'python3', 'python3.exe' " +
                    "or an absolute path to the python interpreter. DQOps will try to find the first python interpreter executable in directories " +
                    "defined in the PATH when a list of python interpreter names (not an absolute path) is used.", defaultValue = "python3")
    private String dqoPythonInterpreter;

    @CommandLine.Option(names = {"--dqo.python.use-host-python"},
            description = "Disable creating a python virtual environment by DQOps on startup. Instead, use the system python interpreter. " +
                    "DQOps will not install any required python packages on startup and use packages from the user's python installation.", defaultValue = "false")
    private String dqoPythonUseHostPython;

    @CommandLine.Option(names = {"--dqo.user.home"},
            description = "Overrides the path to the DQOps user home. The default user home is created in the current folder (.).", defaultValue = ".")
    private String dqoUserHome;

    @CommandLine.Option(names = {"--dqo.home"},
            description = "Overrides the path to the DQOps system home (DQO_HOME). The default DQO_HOME contains the definition of built-in data quality sensors, rules and libraries.")
    private String dqoHome;

    @CommandLine.Option(names = {"--dqo.default-time-zone"},
            description = "Default time zone name used to convert the server's local dates to a local time in a time zone that is relevant for the user. Use official IANA time zone names. " +
                    "When the parameter is not configured, DQOps uses the local time zone of the host running the application. The time zone can be reconfigured at a user settings level.")
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

    @CommandLine.Option(names = {"--dqo.incidents.partitioned-checks-time-window-days"},
            description = "The time window for the maximum age of a daily or monthly partition whose data quality issues are included in new data quality incidents when an issue is detected. " +
                    "Data quality issues on older partitions will not trigger creating a new incident.", defaultValue = "45")
    private Integer dqoIncidentsPartitionedChecksTimeWindowDays;

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
            description = "The number of files that are uploaded to DQOps Cloud in parallel using HTTP/2 multiplexing.", defaultValue = "500")
    private Integer dqoCloudParallelFileUploads;

    @CommandLine.Option(names = {"--dqo.cloud.parallel-file-downloads"},
            description = "The number of files that are downloaded from DQOps Cloud in parallel using HTTP/2 multiplexing.", defaultValue = "500")
    private Integer dqoCloudParallelFileDownloads;

    @CommandLine.Option(names = {"--dqo.cloud.start-without-api-key"},
            description = "Allow starting DQOps without a DQOps Cloud API Key and without prompting to log in to DQOps Cloud.", defaultValue = "false")
    private Boolean dqoCloudStartWithoutApiKey;

    @CommandLine.Option(names = {"--dqo.cloud.authenticate-with-dqo-cloud"},
            description = "Turns on user authentication by using DQOps Cloud credentials. Users will be redirected to the DQOps Cloud login screen " +
                    "to login and will be returned back to the local DQOps instance.", defaultValue = "false")
    private Boolean dqoCloudAuthenticateWithDqoCloud;

    @CommandLine.Option(names = {"--dqo.instance.return-base-url"},
            description = "Base url of this instance that is used as a return url when authentication with DQOps Cloud credentials is forwarded and " +
                    "the user must be forwarded back to the current instance from the https://cloud.dqops.com login screen. " +
                    "When this parameter is not provided, DQOps will use the url from the \"Host\" HTTP header.")
    private String dqoInstanceReturnBaseUrl;

    @CommandLine.Option(names = {"--dqo.instance.signature-key"},
            description = "DQOps local instance signature key that is used to issue and verify digital signatures on API keys. It is a base64 encoded byte array (32 bytes). " +
                    "When not configured, DQOps will generate a secure random key and store it in the .localsettings.dqosettings.yaml file.")
    private String dqoInstanceSignatureKey;

    @CommandLine.Option(names = {"--dqo.queue.max-concurrent-jobs"},
            description = "Sets the maximum number of concurrent jobs that the job queue can process at once (running data quality checks, importing metadata, etc.). " +
                    "The maximum number of threads is also limited by the DQOps license.")
    private Long dqoQueueMaxConcurrentJobs;

    @CommandLine.Option(names = {"--dqo.queue.wait-timeouts.run-checks"},
            description = "Sets the default timeout (in seconds) for the \"run checks\" rest api operation called from the DQOps client when the \"wait\" parameter " +
                    "is true and the timeout is not provided by the client.", defaultValue = "120")
    private Long dqoQueueWaitTimeoutsRunChecks;

    @CommandLine.Option(names = {"--dqo.queue.wait-timeouts.collect-statistics"},
            description = "Sets the default timeout (in seconds) for the \"collect statistics\" REST API operation called from the DQOps client when the \"wait\" parameter " +
                    "is true and the timeout is not provided by the client.", defaultValue = "120")
    private Long dqoQueueWaitTimeoutsCollectStatistics;

    @CommandLine.Option(names = {"--dqo.queue.wait-timeouts.import-tables"},
            description = "Sets the default timeout (in seconds) for the \"import tables\" rest api operation called from the DQOps client when the \"wait\" parameter " +
                    "is true and the timeout is not provided by the client.", defaultValue = "120")
    private Long dqoQueueWaitTimeoutsImportTables;

    @CommandLine.Option(names = {"--dqo.queue.wait-timeouts.delete-stored-data"},
            description = "Sets the default timeout (in seconds) for the \"delete stored data\" rest api operation called from the DQOps client when the \"wait\" parameter " +
                    "is true and the timeout is not provided by the client.", defaultValue = "120")
    private Long dqoQueueWaitTimeoutsDeleteStoredData;

    @CommandLine.Option(names = {"--dqo.queue.wait-timeouts.synchronize-multiple-folders"},
            description = "Sets the default timeout (in seconds) for the \"synchronize multiple folders\" rest api operation called from the DQOps client when the \"wait\" parameter " +
                    "is true and the timeout is not provided by the client.", defaultValue = "120")
    private Long dqoQueueWaitTimeoutsSynchronizeMultipleFolders;

    @CommandLine.Option(names = {"--dqo.queue.wait-timeouts.default-wait-timeout"},
            description = "Sets the default wait timeout (in seconds) for waiting for a job when the \"waitTimeout\" parameter is not given to the " +
                    "call to the \"waitForJob\" operation from the DQOps client..", defaultValue = "120")
    private Long dqoQueueWaitTimeoutsDefaultWaitTimeout;

    @CommandLine.Option(names = {"--dqo.scheduler.start"},
            description = "Starts the job scheduler on startup (true) or disables the job scheduler (false).", defaultValue = "true")
    private Boolean dqoSchedulerStart;

    @CommandLine.Option(names = {"--dqo.scheduler.enable-cloud-sync"},
            description = "Enable synchronization of metadata and results with DQOps Cloud in the job scheduler.", defaultValue = "true")
    private Boolean dqoSchedulerEnableCloudSync;

    @CommandLine.Option(names = {"--dqo.scheduler.synchronize-cron-schedule"},
            description = "Unix cron expression to configure how often the scheduler will synchronize the local copy of the metadata with DQOps Cloud and detect new cron schedules. " +
                    "The default schedule will synchronize local files with DQOps Cloud and refresh the data quality data warehouse 5 minutes past each hour. " +
                    "A DQOps instance that uses a FREE or a trial PERSONAL license will ignore this setting and synchronize files once an hour, on a random time. " +
                    "Synchronization with DQOps cloud can be disabled by setting --dqo.scheduler.enable-cloud-sync=false.", defaultValue = "5 * * * *")
    private String dqoSchedulerSynchronizeCronSchedule;

    @CommandLine.Option(names = {"--dqo.scheduler.synchronized-folders"},
            description = "Configures which folders from the DQOps user home folder are synchronized to DQOps Cloud during a monitoring synchronization " +
                    "(triggered by a cron schedule configured by --dqo.scheduler.synchronize-cron-schedule). " +
                    "By default, DQOps synchronizes (pushes) only changes from folders that have local changes.", defaultValue = "locally_changed")
    private ScheduledSynchronizationFolderSelectionMode dqoSchedulerSynchronizedFolders = ScheduledSynchronizationFolderSelectionMode.locally_changed;

    @CommandLine.Option(names = {"--dqo.scheduler.synchronization-mode"},
            description = "Configures the console logging mode for the '\"cloud sync all\" operations performed by the job scheduler in the background.", defaultValue = "silent")
    private FileSystemSynchronizationReportingMode dqoSchedulerSynchronizationMode;

    @CommandLine.Option(names = {"--dqo.scheduler.check-run-mode"},
            description = "Configures the console logging mode for the '\"check run\" jobs performed by the job scheduler in the background.", defaultValue = "silent")
    private CheckRunReportingMode dqoSchedulerCheckRunMode;

    @CommandLine.Option(names = {"--dqo.scheduler.default-schedules.profiling"},
            description = "Sets the default schedule for running profiling checks that is copied to the configuration of new data source connections that are registered in DQOps. " +
                    "The default schedule runs profiling checks once a month, on the first day of the month at 12 PM (noon). " +
                    "This parameter is used only once, during the first initialization of DQOps user home. " +
                    "The value is copied to the settings/defaultschedules.dqoschedules.yaml file.", defaultValue = "0 12 1 * *")
    private String dqoSchedulerDefaultSchedulesProfiling;

    @CommandLine.Option(names = {"--dqo.scheduler.default-schedules.monitoring-daily"},
            description = "Sets the default schedule for running daily monitoring checks that is copied to the configuration of new data source connections that are registered in DQOps. " +
                    "The default schedule runs checks once a day at 12 PM (noon). " +
                    "This parameter is used only once, during the first initialization of DQOps user home. " +
                    "The value is copied to the settings/defaultschedules.dqoschedules.yaml file.", defaultValue = "0 12 * * *")
    private String dqoSchedulerDefaultSchedulesMonitoringDaily;

    @CommandLine.Option(names = {"--dqo.scheduler.default-schedules.monitoring-monthly"},
            description = "Sets the default schedule for running monthly monitoring checks that is copied to the configuration of new data source connections that are registered in DQOps. " +
                    "The default schedule runs checks once a day at 12 PM (noon). " +
                    "This parameter is used only once, during the first initialization of DQOps user home. " +
                    "The value is copied to the settings/defaultschedules.dqoschedules.yaml file.", defaultValue = "0 12 * * *")
    private String dqoSchedulerDefaultSchedulesMonitoringMonthly;

    @CommandLine.Option(names = {"--dqo.scheduler.default-schedules.partitioned-daily"},
            description = "Sets the default schedule for running daily partitioned checks that is copied to the configuration of new data source connections that are registered in DQOps. " +
                    "The default schedule runs checks once a day at 12 PM (noon). " +
                    "This parameter is used only once, during the first initialization of DQOps user home. " +
                    "The value is copied to the settings/defaultschedules.dqoschedules.yaml file.", defaultValue = "0 12 * * *")
    private String dqoSchedulerDefaultSchedulesDailyPartitioned;

    @CommandLine.Option(names = {"--dqo.scheduler.default-schedules.partitioned-monthly"},
            description = "Sets the default schedule for running monthly partitioned checks that is copied to the configuration of new data source connections that are registered in DQOps. " +
                    "The default schedule runs checks once a day at 12 PM (noon). " +
                    "This parameter is used only once, during the first initialization of DQOps user home. " +
                    "The value is copied to the .settings/defaultschedules.dqoschedules.yaml file.", defaultValue = "0 12 * * *")
    private String dqoSchedulerDefaultSchedulesMonthlyPartitioned;

    @CommandLine.Option(names = {"--dqo.docker.user-home.allow-unmounted"},
            description = "When running DQOps in a docker container, allow DQOps user home folder to be initialized inside the container's filesystem " +
                    "if the folder hasn't been mounted to an external volume.", defaultValue = "false")
    private Boolean dqoDockerUserHomeAllowUnmounted;

    @CommandLine.Option(names = {"--dqo.sensor.limit.sensor-readout-limit"},
            description = "Default row count limit retrieved by a data quality sensor from the results of an SQL query for non-partitioned checks (profiling and monitoring). " +
                    "This is the row count limit applied when querying the data source. When the data grouping configuration sets up a GROUP BY too many columns " +
                    "or columns with too many distinct values, the data source will return too many results to store them as data quality check results and sensor readouts. " +
                    "DQOps will discard additional values returned from the data source or raise an error.", defaultValue = "1000")
    private int dqoSensorLimitSensorReadoutLimit;

    @CommandLine.Option(names = {"--dqo.sensor.limit.sensor-readout-limit-partitioned"},
            description = "Default row count limit retrieved by a data quality sensor from the results of an SQL query for partitioned checks. " +
                    "This is the row count limit applied when querying the data source. When the data grouping configuration sets up a GROUP BY too many columns " +
                    "or columns with too many distinct values, the data source will return too many results to store them as data quality check results and sensor readouts. " +
                    "DQOps will discard additional values returned from the data source or return an error. " +
                    "The default value is 7x bigger than the sensor-readout-limit to allow analysing the last 7 daily partitions.", defaultValue = "7000")
    private int dqoSensorLimitSensorReadoutLimitPartitioned;

    @CommandLine.Option(names = {"--dqo.sensor.limit.fail-on-sensor-readout-limit-exceeded"},
            description = "Configures the behavior when the number of rows returned from a data quality sensor exceeds the limit configured in the 'sensor-readout-limit' parameter. " +
                    "When true, the whole check execution is failed. When false, only results up to the limit are analyzed. The default value is true.", defaultValue = "true")
    private boolean dqoSensorLimitFailOnSensorReadoutLimitExceeded;

    @CommandLine.Option(names = {"--dqo.sensor.limit.max-merged-queries"},
            description = "The maximum number of queries that are merged into a bigger query, to calculate multiple sensors on the same table and " +
                    "to analyze multiple columns from the same table.", defaultValue = "100")
    private int dqoSensorLimitMaxMergedQueries;

    @CommandLine.Option(names = {"--dqo.statistics.truncated-strings-length"},
            description = "The length of samples captured from text columns (varchar, string, text, etc.) that are stored as samples. " +
                    "DQOps truncates longer column values and stores only the first few characters, up to the character count limit defined by this parameter.", defaultValue = "50")
    private int dqoStatisticsTruncatedStringsLength;

    @CommandLine.Option(names = {"--dqo.statistics.viewed-statistics-age-months"},
            description = "The maximum age (in months) of the basic statistics that are shown on the basic statistics screen. " +
                    "Statistics values captured earlier are still stored, but are not shown in the DQOps UI.", defaultValue = "3")
    private int dqoStatisticsViewedStatisticsAgeMonths;

    @CommandLine.Option(names = {"--dqo.cache.expire-after-seconds"},
            description = "The time in seconds to expire the cache entries since they were added to the cache.", defaultValue = "86400")
    private long dqoCacheExpireAfterSeconds = 86400;  // 24h

    @CommandLine.Option(names = {"--dqo.cache.yaml-files-limit"},
            description = "The maximum number of specification files to cache.", defaultValue = "1000000")
    private long dqoCacheYamlFilesLimit = 1000000;

    @CommandLine.Option(names = {"--dqo.cache.file-lists-limit"},
            description = "The maximum number of folders for which the list of files are cached to avoid listing the files.", defaultValue = "1000000")
    private long dqoCacheFileListsLimit = 1000000;

    @CommandLine.Option(names = {"--dqo.cache.parquet-cache-memory-fraction"},
            description = "The maximum fraction of the JVM heap memory (configured using the -Xmx java parameter) that is used to cache parquet files in memory. " +
                    "The default value 0.6 means that up to 50%% of the JVM heap memory can be used for caching files. " +
                    "The value of the reserved-heap-memory-bytes is subtracted from the total memory size (JVM's -Xmx or -XX:MaxRAMPercentage=80.0 parameter values) " +
                    "before the memory fraction is calculated. The value can be increased to 0.8 for for systems when JVM is given more than 8 GB RAM. ", defaultValue = "0.6")
    private double dqoCacheParquetCacheMemoryFraction = 0.6;

    @CommandLine.Option(names = {"--dqo.cache.reserved-heap-memory-bytes"},
            description = "The memory size (in bytes) that is not subtracted from the total JVM heap memory before the memory fraction dedicated for the parquet cache is calculated. " +
                    "The default value is 200mb.", defaultValue = "200000000")
    private long dqoCacheReservedHeapMemoryBytes = 200L * 1000 * 1000;

    @CommandLine.Option(names = {"--dqo.cache.enabled"},
            description = "Enables or disables the in-memory cache for parsed YAML files and Parquet data files.", defaultValue = "true")
    private boolean dqoCacheEnable = true;

    @CommandLine.Option(names = {"--dqo.cache.watch-file-system-changes"},
            description = "Use a file watcher to detect file system changes and invalidate the in-memory file cache." +
                    "When a file watches is enabled, all changes made to YAML files directly on the file system " +
                    "(i.e. by editing a file in Visual Studio Code) are instantly detected by DQOps.", defaultValue = "true")
    private boolean dqoCacheWatchFileSystemChanges = true;

    /**
     * The delay in milliseconds between processing file changes that would invalidate the cache.
     */
    @CommandLine.Option(names = {"--dqo.cache.process-file-changes-delay-millis"},
            description = "The delay in milliseconds between processing file changes that would invalidate the cache.", defaultValue = "100")
    private long dqoCacheProcessFileChangesDelayMillis = 100;

    @CommandLine.Option(names = {"--spring.config.location"},
            description = "Sets a path to the folder that has the spring configuration files (application.properties or application.yml) or " +
                    "directly to an application.properties or application.yml file. " +
                    "The format of this value is: --spring.config.location=file:./foldername/,file:./alternativeapplication.yml")
    private String springConfigLocation;

    @CommandLine.Option(names = {"--dqo.duckdb.memory-limit"},
            description = "The maximum memory of the system (e.g., 1GB). When not set, DuckDB use the 80%% of RAM.")
    private String dqoDuckdbMemoryLimit = "";

    @CommandLine.Option(names = {"--dqo.duckdb.threads"},
            description = "The number of total threads used by the system. The default value is 1000",
            defaultValue = "1000")
    private long dqoDuckdbThreads = 1000;

    /**
     * This field will capture all remaining parameters that can be also in the form "--name" and should be captured by Spring to update the configuration parameters.
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
