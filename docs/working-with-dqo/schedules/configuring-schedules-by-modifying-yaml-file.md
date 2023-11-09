# Configuring schedule by modifying the YAML file

In this section, we describe how you can customize when checks are run by setting schedules by modifying the YAML files.
Instructions on how to configure the schedule using the user interface can be found [here.](./index.md)

## Configuring a schedule at the connection level

To schedule a check on an entire connection, you first need to open the YAML file of that connection. To do this, simply
use the [connection edit](../../../command-line-interface/connection/#dqo-connection-edit) command, which will launch the
YAML file in Visual Studio Code. To make working with the YAML file even easier, we recommend installing the YAML
extension by RedHat and the Better Jinja extension by Samuel Colvin.

For example, to edit the connection named "testconnection" just run
```
connection edit -c=testconnection
```

To add a schedule to the YAML file, start by including the `schedules:` parameter at the end of the document. Then, specify the check type you want
to run (`profiling`, `monitoring_daily`, `monitoring_monthly`, `partitioned_daily`, or `partitioned_monthly`).
Next, define the frequency at which the check should be run using the `cron_expression:` and input the frequency in the [cron format](./cron-formatting.md).

For example, to schedule checks to run every day at 12:00 for all check types, the YAML file will look like the one provided below:

``` yaml hl_lines="10-20"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  provider_type: bigquery
  bigquery:
    source_project_id: bigquery-public-data
    authentication_mode: google_application_credentials
    jobs_create_project: create_jobs_in_default_project_from_credentials
  schedules:
    profiling:
      cron_expression: 0 12 1 * *
    monitoring_daily:
      cron_expression: 0 12 * * *
    monitoring_monthly:
      cron_expression: 0 12 * * *
    partitioned_daily:
      cron_expression: 0 12 * * *
    partitioned_monthly:
      cron_expression: 0 12 * * *
```


## Creating a schedule at the table level

To schedule a check on a table or check level you first need to open the YAML file of that table.

To do this, simply use the [table edit](../../../command-line-interface/table/#dqo-table-edit) command, which will launch the
YAML file in Visual Studio Code. To make working with the YAML file even easier, we recommend installing the YAML
extension by RedHat and the Better Jinja extension by Samuel Colvin.

For example, to edit the "crime" table that has been defined in the "austin_crime" schema within
"testconnection" data source, simply run the following command.

```
table edit -c=testconnection -t=austin_crime.crime
```

To set a schedule for an entire table in the YAML file, begin by adding the `schedules_override:` parameter before the 
column section. Then, specify the check type you want to run (`profiling`, `monitoring_daily`, `monitoring_monthly`, `partitioned_daily`, or `partitioned_monthly`).
Next, define the frequency at which the check should be run using the `cron_expression:` and input the frequency in the [cron format](./cron-formatting.md).


For example, to schedule checks to run every day at 10:00 for all profiling checks, the YAML file will look like the one provided below:

``` yaml hl_lines="8-10"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  schedules_override:
    profiling:
      cron_expression: 0 10 * * *
  columns:
    unique_key:
      type_snapshot:
        column_type: INT64
        nullable: true
    address:
      type_snapshot:
        column_type: STRING
        nullable: true
    census_tract:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
```

## Creating a schedule at the check level

If you want to modify the schedule for a specific check type, open the YAML file of the table as described in the previous section.
Then, simply add the `schedules_override:` under that check type's name. Next, define the frequency at which the check 
should be run using the `cron_expression:` and input the frequency in the [cron format](./cron-formatting.md).

For example, to schedule profiling on a table level row_count check to run every day at 09:00 or column level nulls_count check to run every day at 08:00
the YAML files will look like the ones provided below:


=== "Schedule on table level row_count check"
    ``` yaml hl_lines="11-12"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      profiling_checks:
        volume:
          row_count:
            schedule_override:
              cron_expression: 0 9 * * *
            error:
              min_count: 0
      columns:
        unique_key:
          type_snapshot:
            column_type: INT64
            nullable: true
        address:
          type_snapshot:
            column_type: STRING
            nullable: true
        census_tract:
          type_snapshot:
            column_type: FLOAT64
            nullable: true
    ```
=== "Schedule on column level nulls_count check"
    ```yaml hl_lines="16-17"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      columns:
        unique_key:
          type_snapshot:
            column_type: INT64
            nullable: true
          profiling_checks:
            nulls:
              nulls_count:
                schedule_override:
                  cron_expression: 0 8 * * *
                error:
                  max_count: 10
        address:
          type_snapshot:
            column_type: STRING
            nullable: true 
    ```