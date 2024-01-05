# Data quality check execution flow

## Overview

DQOps runs data quality checks in several steps. The first step matches target tables and target
checks. Table and check targeting is described in the [running checks](../../running-checks/running-checks.md) article.

The detailed process of running data quality checks and all DQOps engine internal components involved in the process
are described below.


## Check execution data flow
When the data quality checks execution is started, for example by running
the [check run](../../../command-line-interface/check.md#dqo-check-run) command line command as shown
in [running checks](../../running-checks/running-checks.md) article, DQOps queues a `run checks` job
on an internal job queue. 

The `run checks` job is executed as a sequence of steps, involving [sensors](../../sensors/sensors.md),
[rules](../../rules/rules.md), [checks](../../checks/index.md), running SQL queries on the data sources using JDBC drivers
and finally storing the results in the local data lake in the *$DQO_USER_HOME/.data* folder.

The sequence of steps for running data quality checks is shown on the diagram below.

![DQOps simple data flow](https://dqops.com/docs/images/architecture/DQOPs-simple-data-flow-diagram-min.png)

The following steps are performed by the DQOps engine to run a data quality check.

1.   DQOps `run checks` job is started and picked from the execution queue.

     The possible methods for starting
     a 'run checks' job are: 

     -   triggering the check by a user from the CLI using
         the [check run](../../../command-line-interface/check.md#dqo-check-run) command
     
     -   triggering the checks from the [user interface](../../../working-with-dqo/run-data-quality-checks/run-data-quality-checks.md)
     
     -   submitting a 'run checks' job to the queue using a REST API client
         by calling the [run checks](../../../client/operations/jobs.md#run_checks) operation.
     
     -   the checks can be also scheduled for regular execution and triggered by
         an [internal CRON scheduler](../../../working-with-dqo/schedules/index.md). 

2.   All parameters passed to the `run checks` job are used to identify the target data source,
     the target table and match the target checks by name, type, time scale or a column name.
     DQOps accesses the *DQOps user home* folder, reads all YAML files and finds the list of checks that are selected.

     Please read the reference of the [CheckSearchFilters](../../../client/models/Common.md#checksearchfilters)
     parameter that is passed to the [run checks](../../../client/operations/jobs.md#run_checks) operation
     when using the DQOps Python client or running data quality checks using a REST API.

3.   DQOps renders SQL queries that will be executed on the data sources.
     The SQL templates are defined as Jinja2 templates and are called [sensors](../../sensors/sensors.md) in DQOps.
     The sensor rendering engine fills the template with the table name, column name and additional parameters.

4.   The [sensor](../../sensors/sensors.md) SQL queries are executed on the monitored data sources.
     Each sensor query must return an *actual_value* result column from the sensor's query which is a metric that
     was captured from the monitored data source. For example, a [daily_row_count](../../../reference/sensors/table/volume-table-sensors.md#row-count)
     counts the number of rows in the table.
     The sensor metrics that are captured by DQOps are called *sensor readouts*.

     The *sensor readouts* are saved to the [sensor_readouts](../../../reference/parquetfiles/sensor_readouts.md) local parquet table
     in the *$DQO_USER_HOME/.data/sensor_readouts* folder. DQOps stores only one result for each time series 
     (each data quality check on one data group) for each time period. The time period depends on the type of the data quality check.
     [Profiling](../../checks/profiling-checks/profiling-checks.md) are capturing one *sensor readout* per month, which means that
     when a profiling check is executed again during the same month, the previous *sensor readout* is replaced in the parquet table. 

5.   Anomaly detection and change detection data quality checks require historical values of *sensor readouts*.
     
     All *sensor readouts* captured over a period of time for the same data quality check are a time series.
     Before reading historical *sensor readouts*, DQOps loads the [rule](../../rules/rules.md) definition
     [.dqorule.yaml](../../../reference/yaml/RuleDefinitionYaml.md) file to decide if the rule requires historical data
     to evaluate the *sensor readout*. The configuration fields in
     the [RuleDefinitionSpec](../../../reference/yaml/RuleDefinitionYaml.md#ruledefinitionspec) are:

     -   *mode* - decides if the rule uses historical values or only evaluates the current *sensor readout*
     
     -   *time_window* - configures the size of the time window for rules that use historical values

6.   Data quality [rules](../../rules/rules.md) are called to evaluate every captured *sensor readout*.
     The rule that is configured to use historical values (the *mode* is *previous_readouts*) will also receive
     an array of all previous historical *sensor readouts*.

     DQOps calls the data quality rule up to 3 times for each *sensor readout*, separately for each configured severity level.
     First, the rule for the *fatal* severity level is called. If the rule fails, the check result is a failure and
     is scored at a *fatal* severity level and no further rules (*error* or *warning* severity level rules) are evaluated.

     If the *fatal* severity rule is not activated or has passed (accepting the *sensor readout* as a valid result), DQOps continues
     the rule evaluation by evaluating the *error* severity rule. If the *error* severity rule also passes, the *warning* severity
     rule is evaluated. Failed data quality checks are called *data quality issues* or just `issues` on the 
     [data quality dashboards](../../data-quality-dashboards/data-quality-dashboards.md).

     When all rules at all severity levels pass or no rules are enabled on the check, the data quality check
     result is *valid* (success).

     The check results after validation by the data quality rules are saved to the [check_results](../../../reference/parquetfiles/check_results.md)
     local parquet table in the *$DQO_USER_HOME/.data/check_results* folder. DQOps will append new daily results for
     [daily monitoring](../../checks/monitoring-checks/monitoring-checks.md) checks or replace the current result for the current month
     for profiling or monthly monitoring checks. Also, the results for partitioned checks are replaced for each time period (the partition day or month).

7.   Detect new incidents or update active incidents.

     DQOps groups similar data quality `issues` as a single data quality [incident](../../../working-with-dqo/incidents-and-notifications/incidents.md)
     that can be assigned for resolution. Data quality issue grouping is configured for each data source
     on the [Incidents and Notifications](../../../working-with-dqo/incidents-and-notifications/incidents.md) tab. 
     Grouping data quality issues into incidents avoids raising the same data quality issue on multiple days until the issue is resolved.

     The incidents are saved to the [incidents](../../../reference/parquetfiles/incidents.md) local parquet table
     in the *$DQO_USER_HOME/.data/incidents* folder. DQOps inserts a new incident when a just detected data quality issue
     does not match any active (not in the *RESOLVED* status) incident. If an issue matches an *OPEN*, *ACKNOWLEDGED* or *MUTED* status,
     only the issue count and the last seen dates are updated in an existing incident.

8.   Send notifications about new incidents

     When new data quality incidents are detected and the notification channels are configured, DQOps will send a notification
     with a description of the new incident. DQOps supports calling webhooks, 
     such as the [Slack](../../../integrations/slack/configuring-slack-notifications.md).

     The webhooks URLs are defined on the *Default webhooks* screen in the *Configuration* section of the user interface.
     The webhook URLs can be also overwritten for each data source, allowing to forward the incidents to a different group
     or a different Slack channel. 

     The JSON object schema that is posted to a webhook endpoint using a *POST* action is described in the reference section
     as the [IncidentNotificationMessage](../../../reference/yaml/IncidentNotificationMessage.md) object.


## Custom checks and errors flow
DQOps supports both defining custom data quality checks and customizing the definition of built-in checks.
The data quality [checks](../../checks/index.md) are defined as a pair of a [sensor](../../sensors/sensors.md) that will capture
a metric and a [rule](../../rules/rules.md) that verifies the metric, raising a data quality issue if the rule fails.

The definitions of custom checks, sensors and rules are stored in the *DQOps user home* folder. The check execution engine
will look up custom definitions when the data quality checks are executed. If a built-in sensor or rule is shadowed by
a custom definition in the *DQOps user home* folder, the custom definition is used.

Using untested custom sensors and rules may result in execution errors in the Jinja2 template rendering (generating a filled SQL query)
or in the rule written in Python. An additional source of execution errors are issues with connectivity to the data source,
missing tables or columns, or incorrect column's data types. For example, a numeric check
[sum_in_range](../../../checks/column/numeric/sum-in-range.md) uses a SUM aggregate function on a column, which supports only numeric columns.

Check execution errors that are captured during the data quality check execution are stored in the
[errors](../../../reference/parquetfiles/errors.md) parquet table that is stored in the *$DQO_USER_HOME/.data/errors* folder.

The following diagram shows a full check execution flow, including usage of custom definitions and error reporting.
This diagram shows the check execution process for running just one data quality check, allowing to stop the `run checks` job
after reaching an execution error. The *$D.U.H* shortcut on the diagram is the path to the *DQOps user home* folder.
Click the image to zoom it out.

![DQOps data quality checks and incidents workflow](https://dqops.com/docs/images/architecture/DQOps-data-quality-incident-flow-diagram-min.png)

The steps are described below.

1.   `Start` is the moment of starting the `run checks` job.

2.   `Find matching checks` performs the metadata search to find matching data sources, tables, columns and checks to execute.

3.   `Render SQL with Jinja2` runs a Python script that renders the sensor's Jinja2 template into a filled SQL query.
     Both built-in and custom sensor definitions (from the *DQOps user home*) are used, preferring custom definitions.

4.   `Append error` after the *Render SQL with Jinja2* steps is performed conditionally when the Jinja2 template failed to render.
     The error result with a stack trace and a line/column error location in the Jinja2 is written to the
     [errors](../../../reference/parquetfiles/errors.md) parquet table.

5.   `Run sensors (SQL queries)` uses JDBC drivers to run queries on the data sources. For selected data sources, DQOps may use
     other types of database connectors, such as Google BigQuery Java native libraries.

     It is worth mentioning that DQOps engine is able to merge multiple sensor queries that are about to be executed
     as separate queries on the same table into one big SQL SELECT statement. Such a query has only one *FROM* clause
     and performs a single-pass table scan of the tested table.

6.   `Append error` after the *Run sensors (SQL queries)* step is performed conditionally if the data source returns
     an error during the sensor (SQL query) execution. The query execution errors are also written to the
     [errors](../../../reference/parquetfiles/errors.md) parquet table.

     Error management for merged SQL queries requires another step that is not shown on the diagram.
     In case that two SQL queries from two different sensors were merged together and one of the Jinja2 template
     contains invalid SQL constructs, the merged SQL query will be rejected by the monitored data source.

     In that case, DQOps cannot discard executing the first check that uses a valid Jinja2 SQL template.
     DQOps will rewrite the combined SQL queries by splitting it. If a combined SQL query for multiple checks merges
     four checks together, splitting the query will generate two SQL queries with two checks each.
     DQOps retries the query execution, splitting invalid merged SQL queries until the invalid query (based on a wrong Jinja2 template)
     is isolated.

7.   `Append/replace sensor readouts` writes the captured *sensor readouts* to the
     [sensor_readouts](../../../reference/parquetfiles/sensor_readouts.md) local parquet table.

8.   `Read previous sensor readout` is a conditional step performed only to evaluate data quality
     [rules](../../rules/rules.md) that require historical values to detect anomalies. 
     The previous historical value can be used to detect changes from the last known value, such as
     detecting schema changes such as the [daily_column_count_changed](../../../checks/table/schema/column-count-changed.md#daily-column-count-changed)
     check that detects if the column count has changed.
     
9.   `Evaluate sensor readouts` step calls the Python rule functions, passing the current *sensor readout*
     that was captured by the SQL query and optionally an array of historical *sensor readouts* for the same time series.

     Both built-in and custom rule definitions (from the *DQOps user home*) are used, preferring custom rules.

10.  `Append error` after the *Evaluate sensor readouts* is run when the Python function fails to execute.
     This step is not called when the [rule](../../rules/rules.md) Python functions finishes successfully, but returning
     a failure (not passed) status of the evaluated *sensor readout*.
     
     The rule execution errors are written to the [errors](../../../reference/parquetfiles/errors.md) parquet table,
     including a full Python stack trace.

11.  `Append/replace check result` step writes a check execution result to the
     [check_results](../../../reference/parquetfiles/check_results.md) parquet table.

12.  `Find matching incidents` step is executed only when any data quality rule (*warning*, *error* or *fatal* severity rules)
     decided that the *sensor readout* is invalid, raising a data quality issue. 

     DQOps finds a matching active data quality [incidents](../../../reference/parquetfiles/incidents.md)
     by calculating an incident hash code and searching for an incident that has the same hash code.
     The incident hash code is calculated from the values of the *check result* columns selected on the
     [Incidents and Notifications](../../../working-with-dqo/incidents-and-notifications/incidents.md) screen.

     The widest supported issue grouping level is by a table, combining all data quality issues identified
     on a table into a single data quality incident. The default incident's issue grouping level is by the tested table,
     data quality dimension and a category (group) of data quality checks.

13.  `Update incident` updates an existing, active data quality incident that is not yet *RESOLVED*. No notifications are sent
     when an existing incident is updated by increasing the number of data quality issues and the last (issue) seen timestamp.

     The call to the [run checks](../../../client/operations/jobs.md#run_checks) Python client operation that started the
     *run checks* job will return the severity level (*success*, *warning*, *error* or *fatal*) of the highest
     severity data quality issue that was identified during the run.

     The highest severity is found in the [RunChecksQueueJobResult](../../../client/models/jobs.md#runchecksqueuejobresult) object,
     in the *result.highest_severity* field.

14.  `Create incident` step is called when no active incident was found that matches the incident's hash code.
     A new incident is written to the [incidents](../../../reference/parquetfiles/incidents.md) local parquet table.

15.  `Send notification` calls the *A new incident was opened (detected)* webhook url, POSTing the  
     [IncidentNotificationMessage](../../../reference/yaml/IncidentNotificationMessage.md) notification.


## DQOps engine internal components
The diagrams above showed the check execution data flows.
The following diagram shows DQOps internal components that are used during the data quality check execution.
DQOps core engine is a Java Spring Boot application. Please notice how the DQOps core engine communicates
with spawned Python processes that run the Jinja2 templating engine, and the Python data quality rules.

![DQOps engine components](https://dqops.com/docs/images/architecture/DQOps-engine-components-min.png)

The following list describes the role of each internal component.

1.   All three `1` steps are different entry points for queueing a `run checks` job. They were already described before. 

2.   `Job queue (job runner)` is an internal component that manages a job queue. The job queue uses constraints to limit
     parallel execution of SQL queries on the same data source or avoiding two parallel *DQOps Cloud* synchronization jobs
     (uploading the files from the *DQOps user home* to the DQOps Cloud Data Lake).

     DQOps internally supports splitting a single `run checks` job that spans multiple tables into multiple
     `run checks on table` child jobs. Both the parent `run checks` job and a list of nested `run checks on table` child jobs
     are shown in the DQOps user interface in the notification section that is in the top right corner of the screen.

     Splitting the `run checks` job at a table boundary supports parallel execution of multiple queries on the same data source
     which is necessary to monitor big databases and data lakes with hundreds or thousands of tables.
     Beyond that, the job queue will intervene `run checks on table` from different `run checks` jobs,
     mixing both batch (scheduled) and interactive check execution jobs. Mixing job shortens the time to see the check
     execution result by a user who triggered a `run checks` job from the user interface.
 
     The parallel queries (jobs) limit is defined for each data source in the [connection.dqoconnection.yaml](../../../reference/yaml/ConnectionYaml.md) file
     in the *spec.parallel_jobs_limit* field.

     The jobs can be in one of the following states:
   
     - `queued` is a new job that was just submitted and was not yet started due to the parallel jobs limit of 
       the DQOps instance. The parallel jobs limit depends on the DQOps license type. A *FREE* (community) instance
       is limited to run 1 job at a time.
     
     - `running` is a job that is currently running. The job can be cancelled by the user, switching the status to `cancel_requested`.
     
     - `waiting` is a job that was about to be executed, but the capacity constraints of parallel queries on the data source
       (the *spec.parallel_jobs_limit* field value) prevented from starting the job. The job will be executed as soon as
       any other `run checks on table` job on the same data source finishes. If a job is put aside in a `waiting` status,
       DQOps may try to run another `run checks on table` job on a different data source that is not yet capacity limited.
     
     - `succeeded` is a status for a job that has finished without any DQOps engine bugs. Even if all data quality checks failed
       or all data quality checks cannot be executed due to execution errors in invalid Jinja2 templates or exceptions raised
       by the Python rules, DQOps will still return a `succeeded` status.
       The only correct way to detect data quality issues identified during a data quality check execution queued by the
       [run checks](../../../client/operations/jobs.md#run_checks) operation is to verify the highest severity status
       found in the [RunChecksQueueJobResult](../../../client/models/jobs.md#runchecksqueuejobresult) object,
       in the *result.highest_severity* field.
     
     - `failed` is a status for a job that has failed due to some serious DQOps engine issues. The error details will be written
       to a local logging folder *$DQO_USER_HOME/.logs*.
       
     - `cancel_requested` is a status of a job that was requested to cancel, but was `running` at the time of cancellation.
       Jobs can be cancelled by using the [cancel_job](../../../client/operations/jobs.md#cancel_job) operation in the Python client
       or calling a REST API directly. Only jobs in the `queued` and `waiting` statuses are cancelled instantly.
       Jobs in the `running` status are requested to stop gracefully. DQOps will try to cancel long-running SQL queries
       by calling the *Statement.cancel()* method on a JDBC connection which requests the database to stop the query execution.

     - `cancelled` is a status of a job that was successfully cancelled.

3.   `Run data quality checks job` runs the `run checks` and its `run checks on table` jobs, orchestrating the process
     between other components.

4.   `YAML metadata search` step references the YAML in-memory cache and finds target checks that are selected by filters
     provided as the `run checks` job parameters.

5.   `YAML metadata in-memory cache` stores parsed YAML files in-memory, allowing instant access to all definitions.
     The in-memory cache is continuously refreshed by a file system change watcher. DQOps engine depends on the file system
     events raised when any [connection.dqoconnection.yaml](../../../reference/yaml/ConnectionYaml.md), 
     a [*.dqotable.yaml](../../../reference/yaml/TableYaml.md) or any other file in the *DQOps user home* is added, deleted
     or modified not in the DQOps user interface.
     
     The DQOps data source and check configuration files can be edited directly in Visual Studio Code or any other editor
     when a bulk change to multiple data quality checks is required and editing a YAML file directly is a faster way.

     Also, the automatic file change notification allows integrating a running DQOps instance with out-of-process Continuous Integration
     or GitFlow Workflows that are pushing or pulling the content of the *DQOps user home* folder to a Git repository.
     
6.   When the files are not found in the `YAML metadata in-memory cache`, they are read from the *DQOps user home* and cached. 
     
     6.1.   Custom check definitions are read from the *$DQO_USER_HOME/checks* folder.

     6.2.   The data source configuration files [connection.dqoconnection.yaml](../../../reference/yaml/ConnectionYaml.md)
            and the [*.dqotable.yaml](../../../reference/yaml/TableYaml.md) are read from the *$DQO_USER_HOME/sources/\[source_name\]* folder.

     6.3.   Additional shared credentials (passwords, keys) referenced in the data source connection file are read from
            the files in the *$DQO_USER_HOME/.credentials* folder. The name of the shared secret is the file name inside the
            *$DQO_USER_HOME/.credentials* folder, including the file name extension.

7.   `Sensor SQL renderer` runs the *$DQO_HOME/lib/evaluate_templates.py* script in a separate Python process.
     Because DQOps engine is a Java process, it must communicate with a Python process. DQOps uses regular standard input
     and standard output pipes to send the details of a Jinja2 template and the parameters (the table name to fill the template).
     The Python Jinja2 rendering process loads the Jinja2 template, parses it, caches a parsed template and renders the template.
     A rendered SQL query (filled with the table name, column name and other parameters) is returned to the DQOps engine.
     The *evaluate_templates.py* will block on reading the standard input to receive another template to render.

     At first, DQOps engine starts one Python rule rendering process, but under heavy load when running multiple parallel
     `run checks` jobs, DQOps may spawn additional Python processes to spread the load. When the required memory limit
     for a docker container and JVM garbage collector parameters are picked, about 50 MB should be reserved for each Python process.
     That is because the Python processes are started inside the DQOps docker container, sharing the memory limit with the core engine.

8.   Jinja2 sensor templates are read from two locations, preferring the custom definitions in the *DQOps user home* folder.

     8.1.   Built-in sensor definitions are read from the *$DQO_HOME/sensor* folder that is a part of a DQOps installation.

     8.2.   Custom sensor definitions are read from the *$DQO_USER_HOME/sensors* folder.

9.   `JDBC connectors` are called which are wrappers over the JDBC drivers, implemented in the DQOps engine.
     The connectors run rendered queries and return the *sensor readouts*.

10.  `Data quality rule runner (Python)` works in a similar way as the `Sensor SQL renderer` Python process.
     It also runs as a separate Python process that runs the *$DQO_HOME/lib/evaluate_rules.py* module, sending 
     sensor readouts for evaluation by the rule and receiving the results back.

11.  Python rule modules are loaded by the `Data quality rule runner (Python)` from two locations.

     11.1.  Built-in rule definitions are read from the *$DQO_HOME/rules* folder that is a part of a DQOps installation.

     11.2.  Custom rule definitions are read from the *$DQO_USER_HOME/rules* folder.

12.  `Parquet data in-memory cache` module is responsible for storing all Parquet files in-memory.
     The Parquet cache shortens the response time to preview the results in the user interface and also allows
     quick lookups to verify the data quality status of a table using the 
     [get_table_data_quality_status](../../../client/operations/check_results.md#gettabledataqualitystatus) Python client
     or a REST API operation.

     The Parquet in-memory cache is the biggest memory usage contributor. In order to avoid any out-of-memory issues,
     the cache computes the exact (1-byte precision) object sizes in memory, including the object headers and memory padding.
     The JVM heap memory size reserved for the in-memory Parquet cache is configured using the *--dqo.cache.parquet-cache-memory-fraction*
     parameter that is passed to DQOps when starting the engine. 
     See the [dqo](../../../command-line-interface/dqo.md) root command for the details.
     The default configuration is *--dqo.cache.parquet-cache-memory-fraction=0.6* which uses up to 60% of the JVM heap memory
     for caching. 

     The Java JVM memory limit is configured by setting the *DQO_JAVA_OPTS=-XX:MaxRAMPercentage=60.0* environment
     variable before starting DQOps as a Python module or passing a *-e DQO_JAVA_OPTS=-XX:MaxRAMPercentage=80.0* parameter
     when starting DQOps as a docker container. The default Java JVM memory size is 60% of the total RAM for DQOps started
     as a Python module. The default Java JVM memory size for DQOps started as a docker container is 80% of the
     container's memory limit. For example, to run *dqops/dqo* container with a limit of 8 GB, use *docker run -m 8g dqops/dqo run*.

13.  `Local data quality lake` is a local copy of the Parquet files that are managed by the Parquet in-memory cache.
     The list of tables and their partitioning formats is described in the [data storage](../../data-storage/data-storage.md) article.

14.  `Notification queue` is an internal component that calls [notifications](../../../integrations/webhooks/index.md)
     webhooks, sending new data quality incidents and incident's status changes.

