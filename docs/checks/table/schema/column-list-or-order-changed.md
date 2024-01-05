**column list or order changed** checks

**Description**
Table-level check that detects if the list of columns and the order of columns have changed since the last time the check was run.
 This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will depend on the order of columns.
 A data quality issue will be detected if new columns were added, columns that existed during the previous test were dropped or the columns were reordered.

___

## **profile column list or order changed**


**Check description**
Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_column_list_or_order_changed|profiling| |Consistency|[column_list_ordered_hash](../../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)|[value_changed](../../../../reference/rules/Comparison.md#value-changed)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=profile_column_list_or_order_changed
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=profile_column_list_or_order_changed
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=profile_column_list_or_order_changed
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_column_list_or_order_changed
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="8-13"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    schema:
      profile_column_list_or_order_changed:
        warning: {}
        error: {}
        fatal: {}
  columns: {}

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_list_ordered_hash](../../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___

## **daily column list or order changed**


**Check description**
Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_column_list_or_order_changed|monitoring|daily|Consistency|[column_list_ordered_hash](../../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)|[value_changed](../../../../reference/rules/Comparison.md#value-changed)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=daily_column_list_or_order_changed
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=daily_column_list_or_order_changed
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=daily_column_list_or_order_changed
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_column_list_or_order_changed
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="8-14"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  monitoring_checks:
    daily:
      schema:
        daily_column_list_or_order_changed:
          warning: {}
          error: {}
          fatal: {}
  columns: {}

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_list_ordered_hash](../../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___

## **monthly column list or order changed**


**Check description**
Detects if new columns were added, existing columns were removed or the columns were reordered since the last month. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_column_list_or_order_changed|monitoring|monthly|Consistency|[column_list_ordered_hash](../../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)|[value_changed](../../../../reference/rules/Comparison.md#value-changed)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=monthly_column_list_or_order_changed
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=monthly_column_list_or_order_changed
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=monthly_column_list_or_order_changed
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_column_list_or_order_changed
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="8-14"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  monitoring_checks:
    monthly:
      schema:
        monthly_column_list_or_order_changed:
          warning: {}
          error: {}
          fatal: {}
  columns: {}

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[column_list_ordered_hash](../../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)
[sensor](../../../dqo-concepts/sensors/sensors.md).








___
