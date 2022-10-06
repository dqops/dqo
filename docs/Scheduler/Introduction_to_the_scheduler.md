This guide is an introduction to what a scheduler is and how it works.

Scheduler is a tool that enables running a check at a specific time or at a defined frequency and then synchronize the results with the cloud.

It can be used on a certain table, check or a column within a table or on a whole connection.

Synchronization can be done on a:

* metadata - it detects changes to the connections and synchronizes with the DQO Cloud at a defined frequency

* data (check run operations)
    * it synchronizes the parquet files from the DQO Cloud
    * it runs data quality checks
    * it pushes (synchornizes) parquet files back to DQO Cloud

### Synchronizing metadata

First let's start with synchronizing metadata. Metadata is the data that describes other data. They are stored as YAML files in the catalogue of a connection in `userhome/sources`.
There are two methods to synchronize metadata: CLI mode and server mode.

#### CLI mode

Open the CLI and start the DQO.

In order to define a frequency in [cron format](Cron_formatting.md) of a synchronization of a connection use following command:

```
--dqo.scheduler.scan-metadata-cron-schedule=<frequency>
```

Please use quotation marks when defining a frequency.

In order to enable/disable synchronization with the cloud use following command:

```
--dqo.scheduler.enable-cloud-sync=<true/false>
```

To enable type `true`, to disable type `false`.

#### Server mode

Open the CLI, do not start DQO.

In order to define a frequency in [cron format](Cron_formatting.md) of a synchronization of a connection use following command:

```
set DQO_SCHEDULER_SCANMETADATACRONSCHEDULE=<frequency>
```

Please use quotation marks when defining a frequency.

Having run the above command, now run:

```
dqo run
```

In order to enable/disable synchronization with the cloud use following command:

```
set DQO_SCHEDULER_ENABLECLOUDSYNC=<true/false>
```

To enable type `true`, to disable type `false`.

Having run the above command, now run:

```
dqo run
```

### Synchronizing data(check run operations)

#### Schedule a check on a check

In order to schedule a check on a certain check, open a YAML file of the table.

Type `schedule_override` below the definition of the check. In this example check is named `distinct_count_percent`.

Define how often a check should be run by typing `cron_expression:` and frequency in [cron format](Cron_formatting.md). Please use quotation marks when defining a frequency.

The `disable:` option allows to switch on or off a scheduler. In order to enable a scheduler type `false`. To disable type `true`. More on enabling and disabling a schedule [here](Enabling_and_disabling_schedules.md).

```
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: austin_311
    table_name: 311_service_requests
  time_series:
    mode: current_time
    time_gradient: day
  checks: {}
  columns:
    unique_key:
      type_snapshot:
        column_type: STRING
        nullable: true
      checks:
        uniqueness:
          distinct_count_percent:
            rules:
              min_count:
                low:
                  min_value: 98.0
                medium:
                  min_value: 95.0
                high:
                  min_value: 90.0
            schedule_override:
              cron_expression: "*/1 * * * *"
              disable: false
    complaint_description:
      type_snapshot:
        column_type: STRING
        nullable: true
    source:
      type_snapshot:
        column_type: STRING
        nullable: true
```

To run a scheduler use `scheduler start` command, to stop use `scheduler stop` command.

#### Schedule a check on a column

In order to schedule a check on a certain column, open a YAML file of the table.

Type `schedule_override` between the checked column and the definition of the check. In this example the checked column is named `unique key`.

Define how often a check should be run by typing `cron_expression:` and frequency in [cron format](Cron_formatting.md). Please use quotation marks when defining a frequency.

The `disable:` option allows to switch on or off a scheduler. In order to enable a scheduler type `false`. To disable type `true`. More on enabling and disabling a schedule [here](Enabling_and_disabling_schedules.md).

```
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: austin_311
    table_name: 311_service_requests
  time_series:
    mode: current_time
    time_gradient: day
  columns:
    unique_key:
      type_snapshot:
        column_type: STRING
        nullable: true
      schedule_override:
        cron_expression: "*/1 * * * *"
        disable: false
      checks:
        uniqueness:
          distinct_count_percent:
            rules:
              min_count:
                low:
                  min_value: 98.0
                medium:
                  min_value: 95.0
                high:
                  min_value: 90.0
    complaint_description:
      type_snapshot:
        column_type: STRING
        nullable: true
    source:
      type_snapshot:
        column_type: STRING
        nullable: true
```

To run a scheduler use `scheduler start` command, to stop use `scheduler stop` command.

#### Schedule a check on a table

In order to schedule a check on a certain table, open a YAML file of a table.

Type `schedule_override` between `checks: {}` and `columns:`. 

Define how often a check should be run by typing `cron_expression:` and frequency in [cron format](Cron_formatting.md). Please use quotation marks when defining a frequency.

The `disable:` option allows to switch on or off a scheduler. In order to enable a scheduler type `false`. To disable type `true`. More on enabling and disabling a schedule [here](Enabling_and_disabling_schedules.md).

```
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: austin_311
    table_name: 311_service_requests
  time_series:
    mode: current_time
    time_gradient: day
  checks: {}
  schedule_override:
    cron_expression: "*/1 * * * *"
    disable: false
```

To run a scheduler use `scheduler start` command, to stop use `scheduler stop` command.

#### Schedule a check on a connection

In order to schedule a check on a whole connection, open a YAML file of a connection.

Type `schedule` at the end, right after `time_zone`. 

Define how often a check should be run by typing `cron_expression:` and frequency in [cron format](Cron_formatting.md). Please use quotation marks when defining a frequency.

The `disable:` option allows to switch on or off a scheduler. In order to enable a scheduler type `false`. To disable type `true`. More on enabling and disabling a schedule [here](Enabling_and_disabling_schedules.md).

```
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  provider_type: bigquery
  bigquery:
    source_project_id: bigquery-public-data
    authentication_mode: google_application_credentials
  time_zone: UTC
  schedule:
    cron_expression: "*/1 * * * *"
    disable: false
```

To run a scheduler use `scheduler start` command, to stop use `scheduler stop` command.