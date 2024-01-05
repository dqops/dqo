**column list changed** checks

**Description**
Table-level check that detects if the list of columns has changed since the last time the check was run.
 This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will not depend on the order of columns, only on the column names.
 A data quality issue will be detected if new columns were added or columns that existed during the previous test were dropped.

___

## **profile column list changed**


**Check description**
Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_column_list_changed|profiling| |Consistency|[column_list_unordered_hash](../../../../reference/sensors/table/schema-table-sensors.md#column-list-unordered-hash)|[value_changed](../../../../reference/rules/Comparison.md#value-changed)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=profile_column_list_changed
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=profile_column_list_changed
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=profile_column_list_changed
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_column_list_changed
```

**Check structure (YAML)**

```yaml
  profiling_checks:
    schema:
      profile_column_list_changed:
        warning: {}
        error: {}
        fatal: {}
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="11-16"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    schema:
      profile_column_list_changed:
        warning: {}
        error: {}
        fatal: {}
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_list_unordered_hash](../../../../reference/sensors/table/schema-table-sensors.md#column-list-unordered-hash)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___

## **daily column list changed**


**Check description**
Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_column_list_changed|monitoring|daily|Consistency|[column_list_unordered_hash](../../../../reference/sensors/table/schema-table-sensors.md#column-list-unordered-hash)|[value_changed](../../../../reference/rules/Comparison.md#value-changed)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=daily_column_list_changed
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=daily_column_list_changed
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=daily_column_list_changed
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_column_list_changed
```

**Check structure (YAML)**

```yaml
  monitoring_checks:
    daily:
      schema:
        daily_column_list_changed:
          warning: {}
          error: {}
          fatal: {}
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="11-17"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  monitoring_checks:
    daily:
      schema:
        daily_column_list_changed:
          warning: {}
          error: {}
          fatal: {}
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_list_unordered_hash](../../../../reference/sensors/table/schema-table-sensors.md#column-list-unordered-hash)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___

## **monthly column list changed**


**Check description**
Detects if new columns were added or existing columns were removed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_column_list_changed|monitoring|monthly|Consistency|[column_list_unordered_hash](../../../../reference/sensors/table/schema-table-sensors.md#column-list-unordered-hash)|[value_changed](../../../../reference/rules/Comparison.md#value-changed)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=monthly_column_list_changed
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=monthly_column_list_changed
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=monthly_column_list_changed
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_column_list_changed
```

**Check structure (YAML)**

```yaml
  monitoring_checks:
    monthly:
      schema:
        monthly_column_list_changed:
          warning: {}
          error: {}
          fatal: {}
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="11-17"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  monitoring_checks:
    monthly:
      schema:
        monthly_column_list_changed:
          warning: {}
          error: {}
          fatal: {}
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_list_unordered_hash](../../../../reference/sensors/table/schema-table-sensors.md#column-list-unordered-hash)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___
