# dqo

___
### **dqo **

Root command that permit control on CLI mode

**Description**

A root command that allows the user to access all the features and functionalities of the application from the command-line interface (CLI) level. It is the first command to be used before accessing any other commands of the application.


**Summary (CLI)**
```
$ dqo [root_level_parameter] [command]
```
**Example**
```
$ dqo --dqo.cloud.api-key=3242424324242 check run -c=connection_name
```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`--dqo.cli.terminal.width`<br/>|Width of the terminal when no terminal window is available, e.g. in one-shot running mode.<br/>This parameter could be also configured by setting DQO_CLI_TERMINAL_WIDTH  environment variable.| ||
|`--dqo.cloud.api-key`<br/>|DQO cloud api key. Log in to https://cloud.dqo.ai/ to get the key.<br/>This parameter could be also configured by setting DQO_CLOUD_API_KEY  environment variable.| ||
|`--dqo.cloud.parallel-file-downloads`<br/>|The number of files that are downloaded from DQO Cloud in parallel using HTTP/2 multiplexing.<br/>This parameter could be also configured by setting DQO_CLOUD_PARALLEL_FILE_DOWNLOADS  environment variable.| ||
|`--dqo.cloud.parallel-file-uploads`<br/>|The number of files that are uploaded to DQO Cloud in parallel using HTTP/2 multiplexing.<br/>This parameter could be also configured by setting DQO_CLOUD_PARALLEL_FILE_UPLOADS  environment variable.| ||
|`--dqo.core.lock-wait-timeout-seconds`<br/>|Sets the maximum wait timeout in seconds to obtain a lock to read or write files.<br/>This parameter could be also configured by setting DQO_CORE_LOCK_WAIT_TIMEOUT_SECONDS  environment variable.| ||
|`--dqo.core.print-stack-trace`<br/>|Prints a full stack trace for errors on the console.<br/>This parameter could be also configured by setting DQO_CORE_PRINT_STACK_TRACE  environment variable.| ||
|`--dqo.default-time-zone`<br/>|Default time zone name used to convert the server&#x27;s local dates to a local time in a time zone that is relevant for the user. Use official IANA time zone names. When the parameter is not configured, DQO uses the local time zone of the host running the application. The time zone could be reconfigured at a user settings level.<br/>This parameter could be also configured by setting DQO_DEFAULT_TIME_ZONE  environment variable.| ||
|`--dqo.docker.userhome.allow-unmounted`<br/>|When running DQO in a docker container, allow DQO user home folder to be initialized inside the container&#x27;s filesystem if the folder hasn&#x27;t been mounted to an external volume.<br/>This parameter could be also configured by setting DQO_DOCKER_USERHOME_ALLOW_UNMOUNTED  environment variable.| ||
|`--dqo.home`<br/>|Overrides the path to the DQO system home (DQO_HOME). The default DQO_HOME contains the definition of built-in data quality sensors, rules and libraries.<br/>This parameter could be also configured by setting DQO_HOME  environment variable.| ||
|`--dqo.incidents.check-histogram-size`<br/>|The size of the data quality check histogram that is generated for a preview of a data quality incident.<br/>This parameter could be also configured by setting DQO_INCIDENTS_CHECK_HISTOGRAM_SIZE  environment variable.| ||
|`--dqo.incidents.column-histogram-size`<br/>|The size of the column histogram that is generated for a preview of a data quality incident.<br/>This parameter could be also configured by setting DQO_INCIDENTS_COLUMN_HISTOGRAM_SIZE  environment variable.| ||
|`--dqo.incidents.count-open-incidents-days`<br/>|The number of days since today that are scanned for open incidents first seen in since this number of days.<br/>This parameter could be also configured by setting DQO_INCIDENTS_COUNT_OPEN_INCIDENTS_DAYS  environment variable.| ||
|`--dqo.jdbc.expire-after-access-seconds`<br/>|Sets the number of seconds when a connection in a JDBC pool is expired after the last access.<br/>This parameter could be also configured by setting DQO_JDBC_EXPIRE_AFTER_ACCESS_SECONDS  environment variable.| ||
|`--dqo.jdbc.max-connection-in-pool`<br/>|Sets the maximum number of connections in the JDBC connection pool, shared across all data sources using JDBC drivers.<br/>This parameter could be also configured by setting DQO_JDBC_MAX_CONNECTION_IN_POOL  environment variable.| ||
|`--dqo.logging.enable-user-home-logging`<br/>|Enables file logging inside the DQO User Home&#x27;s .logs folder.<br/>This parameter could be also configured by setting DQO_LOGGING_ENABLE_USER_HOME_LOGGING  environment variable.| ||
|`--dqo.logging.max-history`<br/>|Sets the maximum number of log files that could be stored (archived) in the .logs folder.<br/>This parameter could be also configured by setting DQO_LOGGING_MAX_HISTORY  environment variable.| ||
|`--dqo.logging.pattern`<br/>|Log entry pattern for logback used for writing log entries.<br/>This parameter could be also configured by setting DQO_LOGGING_PATTERN  environment variable.| ||
|`--dqo.logging.total-size-cap`<br/>|Total log file size cap.<br/>This parameter could be also configured by setting DQO_LOGGING_TOTAL_SIZE_CAP  environment variable.| ||
|`--dqo.python.interpreter`<br/>|Python interpreter command line name, like &#x27;python&#x27; or &#x27;python3&#x27;.<br/>This parameter could be also configured by setting DQO_PYTHON_INTERPRETER  environment variable.| ||
|`--dqo.python.python-script-timeout-seconds`<br/>|Python script execution time limit in seconds for running jinja2 and rule evaluation scripts.<br/>This parameter could be also configured by setting DQO_PYTHON_PYTHON_SCRIPT_TIMEOUT_SECONDS  environment variable.| ||
|`--dqo.queue.max-concurrent-jobs`<br/>|Sets the maximum number of concurrent jobs that the job queue can process at once (running data quality checks, importing metadata, etc.). The maximum number of threads is also limited by the DQO license.<br/>This parameter could be also configured by setting DQO_QUEUE_MAX_CONCURRENT_JOBS  environment variable.| ||
|`--dqo.queue.wait-timeouts.default-wait-timeout`<br/>|Sets the default wait timeout (in seconds) for waiting for a job when the &quot;waitTimeout&quot; parameter is not given to the call to the &quot;waitForJob&quot; operation from the DQO client..<br/>This parameter could be also configured by setting DQO_QUEUE_WAIT_TIMEOUTS_DEFAULT_WAIT_TIMEOUT  environment variable.| ||
|`--dqo.queue.wait-timeouts.run-checks`<br/>|Sets the default timeout (in seconds) for the &quot;run checks&quot; rest api operation called from the DQO client when the &quot;wait&quot; parameter is true and the timeout is not provided by the client.<br/>This parameter could be also configured by setting DQO_QUEUE_WAIT_TIMEOUTS_RUN_CHECKS  environment variable.| ||
|`--dqo.scheduler.check-run-mode`<br/>|Configures the console logging mode for the &#x27;&quot;check run&quot; jobs performed by the job scheduler in the background.<br/>This parameter could be also configured by setting DQO_SCHEDULER_CHECK_RUN_MODE  environment variable.| |silent<br/>summary<br/>info<br/>debug<br/>|
|`--dqo.scheduler.enable-cloud-sync`<br/>|Enable synchronization of metadata and results with DQO Cloud in the job scheduler.<br/>This parameter could be also configured by setting DQO_SCHEDULER_ENABLE_CLOUD_SYNC  environment variable.| ||
|`--dqo.scheduler.start`<br/>|Starts the job scheduler on startup (true) or disables the job scheduler (false).<br/>This parameter could be also configured by setting DQO_SCHEDULER_START  environment variable.| ||
|`--dqo.scheduler.synchronization-mode`<br/>|Configures the console logging mode for the &#x27;&quot;cloud sync all&quot; operations performed by the job scheduler in the background.<br/>This parameter could be also configured by setting DQO_SCHEDULER_SYNCHRONIZATION_MODE  environment variable.| |silent<br/>summary<br/>debug<br/>|
|`--dqo.scheduler.synchronize-cron-schedule`<br/>|Unix cron expression to configure how often the scheduler will synchronize the local copy of the metadata with DQO Cloud and detect new cron schedules. Synchronization with DQO cloud could be disabled by setting --dqo.scheduler.enable-cloud-sync&#x3D;false.<br/>This parameter could be also configured by setting DQO_SCHEDULER_SYNCHRONIZE_CRON_SCHEDULE  environment variable.| ||
|`--dqo.scheduler.synchronized-folders`<br/>|Configures which folders from the DQO user home folder are synchronized to DQO Cloud during a recurring synchronization (triggered by a cron schedule configured by --dqo.scheduler.synchronize-cron-schedule). By default, DQO synchronizes (pushes) only changes from folders that have local changes.<br/>This parameter could be also configured by setting DQO_SCHEDULER_SYNCHRONIZED_FOLDERS  environment variable.| |all<br/>locally_changed<br/>|
|`--dqo.secrets.enable-gcp-secret-manager`<br/>|Enables GCP secret manager to resolve parameters like null in the yaml files.<br/>This parameter could be also configured by setting DQO_SECRETS_ENABLE_GCP_SECRET_MANAGER  environment variable.| ||
|`--dqo.secrets.gcp-project-id`<br/>|GCP project name with a GCP secret manager enabled to pull the secrets.<br/>This parameter could be also configured by setting DQO_SECRETS_GCP_PROJECT_ID  environment variable.| ||
|`--dqo.user.home`<br/>|Overrides the path to the DQO user home. The default user home is created in the current folder (.).<br/>This parameter could be also configured by setting DQO_USER_HOME  environment variable.| ||
|`--dqo.user.initialize-user-home`<br/>|Initializes an empty DQO user home (identified by the DQO_USER_HOME environment variable) without asking the user for confirmation.<br/>This parameter could be also configured by setting DQO_USER_INITIALIZE_USER_HOME  environment variable.| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file<br/>This parameter could be also configured by setting _FWFILE_WRITE  environment variable.| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode<br/>This parameter could be also configured by setting _HLHEADLESS  environment variable.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters<br/>This parameter could be also configured by setting _HHELP  environment variable.| ||
|`--logging.level.com.dqops`<br/>|Default logging level for the DQO runtime.<br/>This parameter could be also configured by setting LOGGING_LEVEL_COM_DQOPS  environment variable.| |ERROR<br/>WARN<br/>INFO<br/>DEBUG<br/>TRACE<br/>|
|`--logging.level.root`<br/>|Default logging level at the root level of the logging hierarchy.<br/>This parameter could be also configured by setting LOGGING_LEVEL_ROOT  environment variable.| |ERROR<br/>WARN<br/>INFO<br/>DEBUG<br/>TRACE<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses<br/>This parameter could be also configured by setting _OFOUTPUT_FORMAT  environment variable.| |TABLE<br/>CSV<br/>JSON<br/>|
|`--server.port`<br/>|Sets the web server port to host the DQO local web UI.<br/>This parameter could be also configured by setting SERVER_PORT  environment variable.| ||
|`--spring.config.location`<br/>|Sets a path to the folder that has the spring configuration files (application.properties or application.yml) or directly to an application.properties or application.yml file. The format of this value is: --spring.config.location&#x3D;file:./foldername/,file:./alternativeapplication.yml<br/>This parameter could be also configured by setting SPRING_CONFIG_LOCATION  environment variable.| ||



