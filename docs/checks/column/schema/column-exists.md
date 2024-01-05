**column exists** checks

**Description**
Column level check that reads the metadata of the monitored table and verifies that the column still exists in the data source.
 The data quality sensor returns 1.0 when the column was found or 0.0 when the column was not found.

___

## **profile column exists**


**Check description**
Checks the metadata of the monitored table and verifies if the column exists.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_column_exists|profiling| |Completeness|[column_exists](../../../../reference/sensors/column/schema-column-sensors.md#column-exists)|[equals_1](../../../../reference/rules/Comparison.md#equals-1)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=profile_column_exists
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=profile_column_exists
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=profile_column_exists
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_column_exists
```

**Check structure (YAML)**

```yaml
      profiling_checks:
        schema:
          profile_column_exists:
            warning: {}
            error: {}
            fatal: {}
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="13-18"
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
  columns:
    target_column:
      profiling_checks:
        schema:
          profile_column_exists:
            warning: {}
            error: {}
            fatal: {}
      labels:
      - This is the column that is analyzed for data quality issues
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_exists](../../../../reference/sensors/column/schema-column-sensors.md#column-exists)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___

## **daily column exists**


**Check description**
Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_column_exists|monitoring|daily|Completeness|[column_exists](../../../../reference/sensors/column/schema-column-sensors.md#column-exists)|[equals_1](../../../../reference/rules/Comparison.md#equals-1)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=daily_column_exists
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=daily_column_exists
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=daily_column_exists
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_column_exists
```

**Check structure (YAML)**

```yaml
      monitoring_checks:
        daily:
          schema:
            daily_column_exists:
              warning: {}
              error: {}
              fatal: {}
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="13-19"
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
  columns:
    target_column:
      monitoring_checks:
        daily:
          schema:
            daily_column_exists:
              warning: {}
              error: {}
              fatal: {}
      labels:
      - This is the column that is analyzed for data quality issues
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_exists](../../../../reference/sensors/column/schema-column-sensors.md#column-exists)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___

## **monthly column exists**


**Check description**
Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_column_exists|monitoring|monthly|Completeness|[column_exists](../../../../reference/sensors/column/schema-column-sensors.md#column-exists)|[equals_1](../../../../reference/rules/Comparison.md#equals-1)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=monthly_column_exists
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=monthly_column_exists
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=monthly_column_exists
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_column_exists
```

**Check structure (YAML)**

```yaml
      monitoring_checks:
        monthly:
          schema:
            monthly_column_exists:
              warning: {}
              error: {}
              fatal: {}
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="13-19"
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
  columns:
    target_column:
      monitoring_checks:
        monthly:
          schema:
            monthly_column_exists:
              warning: {}
              error: {}
              fatal: {}
      labels:
      - This is the column that is analyzed for data quality issues
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_exists](../../../../reference/sensors/column/schema-column-sensors.md#column-exists)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___
