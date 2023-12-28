**column count match** checks  

**Description**  
Table level comparison check compares the column count of the current (parent) table to the column count in the reference table.

___

## **profile column count match**  
  
**Check description**  
Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_column_count_match|profiling| |Accuracy|[column_count](../../../../reference/sensors/table/schema-table-sensors/#column-count)|[diff_percent](../../../../reference/rules/Comparison/#diff-percent)|
  
**Activate check (Shell)**  
Activate this data quality using the [check activate](../../../../command-line-interface/check/#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=profile_column_count_match
```

**Run check (Shell)**  
Run this data quality check using the [check run](../../../../command-line-interface/check/#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=profile_column_count_match
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=profile_column_count_match
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_column_count_match
```

**Check structure (YAML)**
```yaml
  profiling_checks:
    comparisons:
      compare_to_source_of_truth_table:
        profile_column_count_match:
          warning:
            max_diff_percent: 0.0
          error:
            max_diff_percent: 1.0
          fatal:
            max_diff_percent: 5.0
```
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="22-31"
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
  table_comparisons:
    compare_to_source_of_truth_table:
      reference_table_connection_name: <source_of_truth_connection_name>
      reference_table_schema_name: <source_of_truth_schema_name>
      reference_table_name: <source_of_truth_table_name>
      check_type: profiling
      grouping_columns:
      - compared_table_column_name: country
        reference_table_column_name: country_column_name_on_reference_table
      - compared_table_column_name: state
        reference_table_column_name: state_column_name_on_reference_table
  profiling_checks:
    comparisons:
      compare_to_source_of_truth_table:
        profile_column_count_match:
          warning:
            max_diff_percent: 0.0
          error:
            max_diff_percent: 1.0
          fatal:
            max_diff_percent: 5.0
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested
    country:
      labels:
      - column used as the first grouping key for calculating aggregated values used
        for the table comparison
    state:
      labels:
      - column used as the first grouping key for calculating aggregated values used
        for the table comparison

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_count](../../../../reference/sensors/table/schema-table-sensors/#column-count)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___

## **daily column count match**  
  
**Check description**  
Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_column_count_match|monitoring|daily|Accuracy|[column_count](../../../../reference/sensors/table/schema-table-sensors/#column-count)|[diff_percent](../../../../reference/rules/Comparison/#diff-percent)|
  
**Activate check (Shell)**  
Activate this data quality using the [check activate](../../../../command-line-interface/check/#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=daily_column_count_match
```

**Run check (Shell)**  
Run this data quality check using the [check run](../../../../command-line-interface/check/#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=daily_column_count_match
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=daily_column_count_match
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_column_count_match
```

**Check structure (YAML)**
```yaml
  monitoring_checks:
    daily:
      comparisons:
        compare_to_source_of_truth_table:
          daily_column_count_match:
            warning:
              max_diff_percent: 0.0
            error:
              max_diff_percent: 1.0
            fatal:
              max_diff_percent: 5.0
```
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="22-32"
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
  table_comparisons:
    compare_to_source_of_truth_table:
      reference_table_connection_name: <source_of_truth_connection_name>
      reference_table_schema_name: <source_of_truth_schema_name>
      reference_table_name: <source_of_truth_table_name>
      check_type: profiling
      grouping_columns:
      - compared_table_column_name: country
        reference_table_column_name: country_column_name_on_reference_table
      - compared_table_column_name: state
        reference_table_column_name: state_column_name_on_reference_table
  monitoring_checks:
    daily:
      comparisons:
        compare_to_source_of_truth_table:
          daily_column_count_match:
            warning:
              max_diff_percent: 0.0
            error:
              max_diff_percent: 1.0
            fatal:
              max_diff_percent: 5.0
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested
    country:
      labels:
      - column used as the first grouping key for calculating aggregated values used
        for the table comparison
    state:
      labels:
      - column used as the first grouping key for calculating aggregated values used
        for the table comparison

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_count](../../../../reference/sensors/table/schema-table-sensors/#column-count)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___

## **monthly column count match**  
  
**Check description**  
Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_column_count_match|monitoring|monthly|Accuracy|[column_count](../../../../reference/sensors/table/schema-table-sensors/#column-count)|[diff_percent](../../../../reference/rules/Comparison/#diff-percent)|
  
**Activate check (Shell)**  
Activate this data quality using the [check activate](../../../../command-line-interface/check/#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=monthly_column_count_match
```

**Run check (Shell)**  
Run this data quality check using the [check run](../../../../command-line-interface/check/#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=monthly_column_count_match
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=monthly_column_count_match
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_column_count_match
```

**Check structure (YAML)**
```yaml
  monitoring_checks:
    monthly:
      comparisons:
        compare_to_source_of_truth_table:
          monthly_column_count_match:
            warning:
              max_diff_percent: 0.0
            error:
              max_diff_percent: 1.0
            fatal:
              max_diff_percent: 5.0
```
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="22-32"
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
  table_comparisons:
    compare_to_source_of_truth_table:
      reference_table_connection_name: <source_of_truth_connection_name>
      reference_table_schema_name: <source_of_truth_schema_name>
      reference_table_name: <source_of_truth_table_name>
      check_type: profiling
      grouping_columns:
      - compared_table_column_name: country
        reference_table_column_name: country_column_name_on_reference_table
      - compared_table_column_name: state
        reference_table_column_name: state_column_name_on_reference_table
  monitoring_checks:
    monthly:
      comparisons:
        compare_to_source_of_truth_table:
          monthly_column_count_match:
            warning:
              max_diff_percent: 0.0
            error:
              max_diff_percent: 1.0
            fatal:
              max_diff_percent: 5.0
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested
    country:
      labels:
      - column used as the first grouping key for calculating aggregated values used
        for the table comparison
    state:
      labels:
      - column used as the first grouping key for calculating aggregated values used
        for the table comparison

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_count](../../../../reference/sensors/table/schema-table-sensors/#column-count)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___
