A schedule can be switched on or off by providing a parameter:

- `true` to disable a schedule
- `false` to enable a schedule 
 
to `disabled:` function in a schedule definition.

### Enabling/disabling a check on a table/column/check

In an example below, a `distinct_count_percent` check schedule has been disabled by providing the `true` parameter.

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
              disabled: true
    complaint_description:
      type_snapshot:
        column_type: STRING
        nullable: true
```

### Enabling/disabling a schedule on a connection

When a schedule is configured on a connection, it will run all the checks configured in all the tables within the connection. 

If there is no schedule configured or none of the schedule is enabled in any of the tables within the connection while a schedule is enabled on the connection - a schedule will run, but there will be no checks.

In an example below, a schedule on a connection has been disabled by providing the `true` parameter.

```
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  provider_type: bigquery
  bigquery:
    source_project_id: bigquery-public-data
    billing_project_id: dqo-ai-testing
    authentication_mode: google_application_credentials
    quota_project_id: dqo-ai-testing
  time_zone: UTC
  schedule:
    cron_expression: "*/1 * * * *"
    disabled: false
```