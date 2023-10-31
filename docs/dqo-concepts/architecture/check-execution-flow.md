# Data quality check execution flow
DQOps runs data quality checks in several steps. The first step matches target tables and target
checks. Table and check targeting is described in the [running checks](../running-checks/running-checks.md) article.

The detailed process of running data quality checks and all DQOps engine internal components involved in the process
are described below.


## Check execution data flow
When the data quality checks execution is started, for example by running
the [check run](../../command-line-interface/check.md#dqo-check-run) command line command as shown
in [running checks](../running-checks/running-checks.md) article, DQOps queues a `run checks` job
on an internal job queue. 

The `run checks` job is executed as a sequence of steps, involving [sensors](../sensors/sensors.md),
[rules](../rules/rules.md), [checks](../checks/index.md), running SQL queries on the data sources using JDBC drivers
and finally storing the results in the local data lake in the *$DQO_USER_HOME/.data* folder.

The sequence of steps for running data quality checks is shown on the diagram below.

![DQOps simple data flow](https://dqops.com/docs/images/architecture/DQOPs-simple-data-flow-diagram-min.png)

The steps performed by the DQOps engine are:

1.   DQOps `run checks` job is started and picked from the execution queue.

     The possible methods for starting
     a 'run checks' job are: 

     -   triggering the check by a user from the CLI using
         the [check run](../../command-line-interface/check.md#dqo-check-run) command
     
     -   triggering the checks from the [user interface](../../working-with-dqo/run-data-quality-checks/run-data-quality-checks.md)
     
     -   submitting a 'run checks' job to the queue using a REST API client
         by calling the [run checks](../../client/operations/jobs.md#run_checks) operation.
     
     -   the checks can be also scheduled for regular execution and triggered by
         an [internal CRON scheduler](../../working-with-dqo/schedules/index.md). 

2.   All parameters passed to the `run checks` job are used to identify the target data source,
     the target table and match the target checks by name, type, time scale or a column name.
     DQOps accesses the *DQOps user home* folder, reads all YAML files and finds the list of checks that are selected.

     Please read the reference of the [CheckSearchFilters](../../client/models/#checksearchfilters)
     parameter that is passed to the [run checks](../../client/operations/jobs.md#run_checks) operation
     when using the DQOps Python client or running data quality checks using a REST API.

3.   DQOps renders SQL queries that will be executed on the data sources.





![DQOps data quality checks and incidents workflow](https://dqops.com/docs/images/architecture/DQOps-data-quality-incident-flow-diagram-min.png)



![DQOps engine components](https://dqops.com/docs/images/architecture/DQOps-engine-components-min.png)


1.   DQOps `run checks` job is started and picked from the execution queue. The possible methods for starting
     a 'run checks' job are: triggering the check by a user from the CLI using
     the [check run](../../command-line-interface/check.md#dqo-check-run) command, triggering the checks
     from the [user interface](../../working-with-dqo/run-data-quality-checks/run-data-quality-checks.md) or
     starting a check using a REST API client by submitting a [run checks](../../client/operations/jobs.md#run_checks) job
     to the queue. The checks can be also scheduled for regular execution and triggered by
     an [internal CRON scheduler](../../working-with-dqo/schedules/index.md).

     1.1.   Step 1.1

     1.2.   Step 1.2

2.   Step 2


