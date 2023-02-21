# dqo

___
### **dqo **

**Description**

Root command that permit control on CLI mode.


**Summary (CLI)**
```
bash prompt> dqo [root_level_parameter] [command]
```
**Example**
```
bash prompt> dqo --dqo.cloud.api-key=3242424324242 check run -c=connection_name
```

**Parameters**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
|`--dqo.cloud.api-key`<br/>|DQO cloud api key. Log in to https://cloud.dqo.ai/ to get the key. This parameter is effective only in CLI mode.| ||
|`--dqo.core.lock-wait-timeout-seconds`<br/>|Sets the maximum wait timeout in seconds to obtain a lock to read or write files. This parameter is effective only in CLI mode.| ||
|`--dqo.core.print-stack-trace`<br/>|Prints a full stack trace for errors on the console. This parameter is effective only in CLI mode.| ||
|`--dqo.default-time-zone`<br/>|Default time zone name used to convert the server&#x27;s local dates to a local time in a time zone that is relevant for the user. Use official IANA time zone names. When the parameter is not configured, DQO uses the local time zone of the host running the application. The time zone could be reconfigured at a user settings level.| ||
|`--dqo.home`<br/>|Overrides the path to the DQO system home (DQO_HOME). The default DQO_HOME contains the definition of built-in data quality sensors, rules and libraries.| ||
|`--dqo.logging.enable-user-home-logging`<br/>|Enables file logging inside the DQO User Home&#x27;s .logs folder.| ||
|`--dqo.logging.max-history`<br/>|Sets the maximum number of log files that could be stored (archived) in the .logs folder.| ||
|`--dqo.logging.pattern`<br/>|Log entry pattern for logback used for writing log entries.| ||
|`--dqo.logging.total-size-cap`<br/>|Total log file size cap.| ||
|`--dqo.python.interpreter`<br/>|Python interpreter command line name, like &#x27;python&#x27; or &#x27;python3&#x27;. This parameter is effective only in CLI mode.| ||
|`--dqo.python.python-script-timeout-seconds`<br/>|Python script execution time limit in seconds for running jinja2 and rule evaluation scripts. This parameter is effective only in CLI mode.| ||
|`--dqo.queue.threads`<br/>|Sets the number of threads that the job queue creates for processing jobs (running data quality checks, importing metadata, etc.). | ||
|`--dqo.scheduler.check-run-mode`<br/>|Configures the console logging mode for the &#x27;&quot;check run&quot; jobs performed by the job scheduler in the background.| |silent<br/>summary<br/>info<br/>debug<br/>|
|`--dqo.scheduler.enable-cloud-sync`<br/>|Enable synchronization of metadata and results with DQO Cloud in the job scheduler. This parameter is effective only in CLI mode.| ||
|`--dqo.scheduler.scan-metadata-cron-schedule`<br/>|Unix cron expression to configure how often the scheduler will synchronize the local copy of the metadata with DQO Cloud and detect new schedules. This parameter is effective only in CLI mode.| ||
|`--dqo.scheduler.start`<br/>|Starts the job scheduler on startup (true) or disables the job scheduler (false).| ||
|`--dqo.scheduler.synchronization-mode`<br/>|Configures the console logging mode for the &#x27;&quot;cloud sync all&quot; operations performed by the job scheduler in the background.| |silent<br/>summary<br/>debug<br/>|
|`--dqo.secrets.enable-gcp-secret-manager`<br/>|Enables GCP secret manager to resolve parameters like null in the yaml files. This parameter is effective only in CLI mode.| ||
|`--dqo.secrets.gcp-project-id`<br/>|GCP project name with a GCP secret manager enabled to pull the secrets. This parameter is effective only in CLI mode.| ||
|`--dqo.user.home`<br/>|Overrides the path to the DQO user home. The default user home is created in the current folder (.). This parameter is effective only in CLI mode.| ||
|`--dqo.user.initialize-user-home`<br/>|Initializes an empty DQO user home (identified by the DQO_USER_HOME environment variable) without asking the user for confirmation.| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`--logging.level.ai.dqo`<br/>|Default logging level for the DQO runtime. This parameter is effective only in CLI mode.| |ERROR<br/>WARN<br/>INFO<br/>DEBUG<br/>TRACE<br/>|
|`--logging.level.root`<br/>|Default logging level at the root level of the logging hierarchy. This parameter is effective only in CLI mode.| |ERROR<br/>WARN<br/>INFO<br/>DEBUG<br/>TRACE<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`--server.port`<br/>|Sets the web server port to host the DQO local web UI. This parameter is effective only in CLI mode.| ||
|`--spring.config.location`<br/>|Sets a path to the folder that has the spring configuration files (application.properties or application.yml) or directly to an application.properties or application.yml file. The format of this value is: --spring.config.location&#x3D;file:./foldername/,file:./alternativeapplication.yml| ||



