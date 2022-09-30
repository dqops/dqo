This guide is an introduction to what a scheduler is and how it works.

Scheduler is a tool that enables running a check at a specific time or at a defined frequency.

It can be used on a certain table or a column within a table or on a whole connection.

### Schedule a check on a column

In order to schedule a check on a certain column, open a YAML file of the table.

Type `schedule_override` between the checked column and the definition of the check(see the highlighted lines below). In this example the checked column is named `unique key`.

Define how often a check should be run by typing `cron_expression:` and frequency in [cron format](Cron_formatting.md). Please use quotation marks when defining a frequency.

The `disable:` option allows to switch on or off a scheduler. In order to enable a scheduler type `false`. To disable type `true`.

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

### Schedule a check on a table

In order to schedule a check on a certain table, open a YAML file of a table.

Type `schedule_override` between `checks: {}` and `columns:`. 

Define how often a check should be run by typing `cron_expression:` and frequency in [cron format](Cron_formatting.md). Please use quotation marks when defining a frequency.

The `disable:` option allows to switch on or off a scheduler. In order to enable a scheduler type `false`. To disable type `true`.

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
    cron_expression: "*/5 * * * *"
    disable: false
```

To run a scheduler use `scheduler start` command, to stop use `scheduler stop` command.

### Schedule a check on a connection

In order to schedule a check on a whole connection, open a YAML file of a connection.

Type `schedule` at the end, right after `time_zone`. 

Define how often a check should be run by typing `cron_expression:` and frequency in [cron format](Cron_formatting.md). Please use quotation marks when defining a frequency.

The `disable:` option allows to switch on or off a scheduler. In order to enable a scheduler type `false`. To disable type `true`.

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
    cron_expression: "*/5 * * * *"
    disable: false
```

To run a scheduler use `scheduler start` command, to stop use `scheduler stop` command.