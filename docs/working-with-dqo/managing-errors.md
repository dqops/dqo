# Managing errors 
Read this guide to learn how to manage log errors and data quality check execution errors in DQOps and where they are stored.

## Error logs

### **Location of the logging folder**
Errors logs are stored locally in the *.logs* folder located in the **DQOps User Home** folder.
The files in the *.logs* folder are rotated to save space. If an error occurs while running DQOps, 
please send the content of the folder to the DQOps support. 

Follow the link, to [learn more about DQOps user home folder](../dqo-concepts/dqops-user-home-folder.md). 


### **Changing logging configuration**
You can change the configuration of logging using the *--dqo.logging.\** and *--logging.\** parameters. These parameters
should be passed to DQOps as the [entry point parameters](../command-line-interface/dqo.md).

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp;                                                                   | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               | Required | Accepted values                                            |
|-----------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:--------:|------------------------------------------------------------|
| <div id="--dqo.logging.console">`--dqo.logging.console`</div>                                                   | Enables logging to console, selecting the correct format. The default configuration &#x27;OFF&#x27; disables console logging, allowing to use the DQOps shell without being distracted by log entries. Set the &#x27;PATTERN&#x27; mode to send formatted entries to the console in a format similar to Apache logs. When running DQOps in as a docker container on a Kubernetes engine that is configured to capture DQOps container logs, use &#x27;JSON&#x27; mode to publish structured Json log entries that can be parsed by fluentd or other similar log engines. JSON formatted messages use a Logstash compatible format.<br/>This parameter can also be configured by setting the *DQO_LOGGING_CONSOLE* environment variable. |          | *OFF*<br/>*JSON*<br/>*PATTERN*<br/>                        |
| <div id="--dqo.logging.console-immediate-flush">`--dqo.logging.console-immediate-flush`</div>                   | When the console logging is enabled with --dqo.logging.console&#x3D;PATTERN or --dqo.logging.console&#x3D;JSON, turns on (for &#x27;true&#x27;) or turns of (for &#x27;false&#x27;) immediate console flushing after each log entry was written. Immediate console flushing is desirable when DQOps is started as a docker container and docker logs from DQOps should be forwarded to Kubernetes for centralized logging.<br/>This parameter can also be configured by setting the *DQO_LOGGING_CONSOLE_IMMEDIATE_FLUSH* environment variable.                                                                                                                                                                                         |          |                                                            |
| <div id="--dqo.logging.enable-user-home-logging">`--dqo.logging.enable-user-home-logging`</div>                 | Enables file logging inside the DQOps User Home&#x27;s .logs folder.<br/>This parameter can also be configured by setting the *DQO_LOGGING_ENABLE_USER_HOME_LOGGING* environment variable.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |          |                                                            |
| <div id="--dqo.logging.max-history">`--dqo.logging.max-history`</div>                                           | Sets the maximum number of log files that can be stored (archived) in the .logs folder.<br/>This parameter can also be configured by setting the *DQO_LOGGING_MAX_HISTORY* environment variable.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |          |                                                            |
| <div id="--dqo.logging.pattern">`--dqo.logging.pattern`</div>                                                   | Log entry pattern for logback used for writing log entries.<br/>This parameter can also be configured by setting the *DQO_LOGGING_PATTERN* environment variable.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |          |                                                            |
| <div id="--dqo.logging.total-size-cap">`--dqo.logging.total-size-cap`</div>                                     | Total log file size cap of log files generated in the DQOps User Home&#x27;s .logs folder. Supported suffixes are: kb, mb, gb. For example: 10mb, 2gb.<br/>This parameter can also be configured by setting the *DQO_LOGGING_TOTAL_SIZE_CAP* environment variable.                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |          |                                                            |
| <div id="--dqo.logging.user-errors.checks-log-level">`--dqo.logging.user-errors.checks-log-level`</div>         | The logging level at which any errors captured during the data quality check evaluation are reported. When a data quality check is executed and the error is related to a sensor (query) or a rule (python) function, they are reported as sensor or rules issues.Only data quality check configuration issues that prevent running a data quality check are reported as check issues. The logging level for the whole application must be equal or higher to this level for effective logging. Check logs are logged under the com.dqops.user-errors.checks log.<br/>This parameter can also be configured by setting the *DQO_LOGGING_USER_ERRORS_CHECKS_LOG_LEVEL* environment variable.                                             |          | *ERROR*<br/>*WARN*<br/>*INFO*<br/>*DEBUG*<br/>*TRACE*<br/> |
| <div id="--dqo.logging.user-errors.rules-log-level">`--dqo.logging.user-errors.rules-log-level`</div>           | The logging level at which any errors captured during the data quality rule (python function) evaluation are reported. The logging level for the whole application must be equal or higher to this level for effective logging. Rule logs are logged under the com.dqops.user-errors.rules log.<br/>This parameter can also be configured by setting the *DQO_LOGGING_USER_ERRORS_RULES_LOG_LEVEL* environment variable.                                                                                                                                                                                                                                                                                                                |          | *ERROR*<br/>*WARN*<br/>*INFO*<br/>*DEBUG*<br/>*TRACE*<br/> |
| <div id="--dqo.logging.user-errors.sensors-log-level">`--dqo.logging.user-errors.sensors-log-level`</div>       | The logging level at which any errors captured during the data quality sensor (query) execution are reported. The logging level for the whole application must be equal or higher to this level for effective logging. Sensor logs are logged under the com.dqops.user-errors.sensors log.<br/>This parameter can also be configured by setting the *DQO_LOGGING_USER_ERRORS_SENSORS_LOG_LEVEL* environment variable.                                                                                                                                                                                                                                                                                                                   |          | *ERROR*<br/>*WARN*<br/>*INFO*<br/>*DEBUG*<br/>*TRACE*<br/> |
| <div id="--dqo.logging.user-errors.statistics-log-level">`--dqo.logging.user-errors.statistics-log-level`</div> | The logging level at which any errors captured during the statistics collection are reported. The logging level for the whole application must be equal or higher to this level for effective logging. Statistics logs are logged under the com.dqops.user-errors.statistics log.<br/>This parameter can also be configured by setting the *DQO_LOGGING_USER_ERRORS_STATISTICS_LOG_LEVEL* environment variable.                                                                                                                                                                                                                                                                                                                         |          | *ERROR*<br/>*WARN*<br/>*INFO*<br/>*DEBUG*<br/>*TRACE*<br/> |
| <div id="--dqo.logging.user-errors.yaml-log-level">`--dqo.logging.user-errors.yaml-log-level`</div>             | The logging level at which any errors captured during YAML file parsing are reported. The logging level for the whole application must be equal or higher to this level for effective logging. Statistics logs are logged under the com.dqops.user-errors.yaml log.<br/>This parameter can also be configured by setting the *DQO_LOGGING_USER_ERRORS_YAML_LOG_LEVEL* environment variable.                                                                                                                                                                                                                                                                                                                                             |          | *ERROR*<br/>*WARN*<br/>*INFO*<br/>*DEBUG*<br/>*TRACE*<br/> |
| <div id="--logging.level.com.dqops">`--logging.level.com.dqops`</div>                                           | Default logging level for the DQOps runtime.<br/>This parameter can also be configured by setting the *LOGGING_LEVEL_COM_DQOPS* environment variable.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |          | *ERROR*<br/>*WARN*<br/>*INFO*<br/>*DEBUG*<br/>*TRACE*<br/> |
| <div id="--logging.level.root">`--logging.level.root`</div>                                                     | Default logging level at the root level of the logging hierarchy.<br/>This parameter can also be configured by setting the *LOGGING_LEVEL_ROOT* environment variable.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |          | *ERROR*<br/>*WARN*<br/>*INFO*<br/>*DEBUG*<br/>*TRACE*<br/> |


## Data quality execution errors
The data quality execution errors can be divided into **sensor** and **rule** errors.

 - The **sensor execution errors** are error messages received from the data source when the tested table does not exist or the sensor's SQL query is invalid.
 - The **rule execution errors** are exceptions raised during the Python rule evaluation.


### **Location of the execution errors**
The data quality execution errors are stored in the uncompressed parquet files located in the *[$DQO_USER_HOME](../dqo-concepts/dqops-user-home-folder.md)/.data/errors* folder.

For a full [reference of execution errors parquet files schema](../reference/parquetfiles/errors.md), please follow the link.


### **Review details of the execution error in the UI**
If an execution error occurs while [running data quality checks](run-data-quality-checks.md) in the check editor,
you will see a black square next to the check name.

![Execution error on the check editor](https://dqops.com/docs/images/working-with-dqo/managing-errors/execution-error-on-check-editor.png)


To review the error details, click on the **Results** icon and select the **Execution errors** tab.
The list of errors will display the date, source (sensor or rule), unique readout ID, and a detailed error message.
You can filter the list by data group and month.

For example, when we execute a numeric check on the string column we get a sensor error as the one shown below.

![Execution error on the check editor](https://dqops.com/docs/images/working-with-dqo/managing-errors/execution-error-details.png)


### **Delete the execution error**
To delete check execution errors, go to the Error details screen and click on the delete icon in the upper right corner.

![Delete execution error](https://dqops.com/docs/images/working-with-dqo/managing-errors/delete-execution-errors.png)

Then on the dialog box, you can select only the Execution errors using the checkbox and specify the time range.

![Delete execution error - dialog screen](https://dqops.com/docs/images/working-with-dqo/managing-errors/delete-execution-errors-dialog-screen2.png)

For more information about [deleting results](delete-data-quality-results.md), please follow the link. 

### **Reviewing check execution errors on dashboards**
You can review the summary list and details of all the execution errors on the dashboards.

To view the execution errors dashboards, simply go to the **Data Quality Dashboard** section. On the tree view on the
left open the **DQOPs usage**, and then the **Execution errors** folder.

![Navigating to execution errors dashboards](https://dqops.com/docs/images/working-with-dqo/managing-errors/navigating-to-execution-errors-dashboards.png)

DQOps has two built-in dashboards for reviewing Execution errors: **History of execution errors** and **Details of execution errors** described below.


The **Details of execution errors** dashboard displays a summary of errors per connection, schema, table, column, dimension,
check type, check category, or check name. It also displays the daily distribution of errors, time when the error occurred, and detailed error messages. 
It also displays the daily distribution of errors and full error details including the error message. 

![Details of execution errors](https://dqops.com/docs/images/working-with-dqo/managing-errors/details-of-execution-errors-dashboard.png)


The **History of execution errors** dashboard displays the summary of errors per connection, schema, table, column, dimension,
check type, check category, or check name for the current or previous month. It also shows the distribution of errors 
per day of the month.

![History of execution errors](https://dqops.com/docs/images/working-with-dqo/managing-errors/history-of-execution-errors-dashboard.png)

You can modify the built-in dashboards. Follow the link for [more information about creating custom data quality dashboards](../integrations/looker-studio/creating-custom-data-quality-dashboards.md).

## Running checks from the DQOps Shell in the debug mode
When running checks from the DQOPs Shell and an error occurs, you can examine its details by executing the check in the debug mode.
Simply add the `--mode=debug` parameter to the `check run` command

`check run --mode=debug`

Follow the link, for more information about [working with DQOps Shell](working-with-dqo-shell.md).

## Reporting errors directly to the console when starting dqops/dqo docker image
If you want to report errors directly to the console when starting dqops/dqo docker image use, the following parameters for docker run:

```
-e DQO_LOGGING_CONSOLE=JSON \
-e DQO_LOGGING_CONSOLE_IMMEDIATE_FLUSH=true \
-e DQO_LOGGING_LOG_ERRORS_TO_STDERR=false \
-e LOGGING_LEVEL_COM_DQOPS=error \
```

A full description of [starting DQOps as a Docker container can be found here](../dqops-installation/run-dqops-as-docker-container.md)

## What's next
- Check the detailed manual of [how to run data quality checks using the check editor](run-data-quality-checks.md).
- Learn how to [review the data quality results on dashboards](review-the-data-quality-results-on-dashboards.md) to identify all tables and columns affected by issues. 