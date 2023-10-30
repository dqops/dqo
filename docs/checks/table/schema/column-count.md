**column count** checks  

**Description**  
Table-level check that retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns.

___

## **profile column count**  
  
**Check description**  
Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_column_count|profiling| |Validity|[column_count](../../../../reference/sensors/table/schema-table-sensors/#column-count)|[equals_integer](../../../../reference/rules/Comparison/#equals-integer)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=profile_column_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=profile_column_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=profile_column_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_column_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_column_count
```
**Check structure (YAML)**
```yaml
  profiling_checks:
    schema:
      profile_column_count:
        warning:
          expected_value: 10
        error:
          expected_value: 10
        fatal:
          expected_value: 10
```
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="11-19"
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
      profile_column_count:
        warning:
          expected_value: 10
        error:
          expected_value: 10
        fatal:
          expected_value: 10
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_count](../../../../reference/sensors/table/schema-table-sensors/#column-count)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___

## **daily column count**  
  
**Check description**  
Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_column_count|monitoring|daily|Validity|[column_count](../../../../reference/sensors/table/schema-table-sensors/#column-count)|[equals_integer](../../../../reference/rules/Comparison/#equals-integer)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_column_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_column_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_column_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_column_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_column_count
```
**Check structure (YAML)**
```yaml
  monitoring_checks:
    daily:
      schema:
        daily_column_count:
          warning:
            expected_value: 10
          error:
            expected_value: 10
          fatal:
            expected_value: 10
```
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="11-20"
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
        daily_column_count:
          warning:
            expected_value: 10
          error:
            expected_value: 10
          fatal:
            expected_value: 10
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_count](../../../../reference/sensors/table/schema-table-sensors/#column-count)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___

## **monthly column count**  
  
**Check description**  
Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_column_count|monitoring|monthly|Validity|[column_count](../../../../reference/sensors/table/schema-table-sensors/#column-count)|[equals_integer](../../../../reference/rules/Comparison/#equals-integer)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_column_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_column_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_column_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_column_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_column_count
```
**Check structure (YAML)**
```yaml
  monitoring_checks:
    monthly:
      schema:
        monthly_column_count:
          warning:
            expected_value: 10
          error:
            expected_value: 10
          fatal:
            expected_value: 10
```
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="11-20"
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
        monthly_column_count:
          warning:
            expected_value: 10
          error:
            expected_value: 10
          fatal:
            expected_value: 10
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_count](../../../../reference/sensors/table/schema-table-sensors/#column-count)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___
