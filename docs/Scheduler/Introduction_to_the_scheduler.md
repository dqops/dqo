This guide is an introduction to what a scheduler is and how it works.

Scheduler is a tool that enables running a check at a specific time or at a defined frequency.

It can be both used on a certain table or on a whole connection.

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