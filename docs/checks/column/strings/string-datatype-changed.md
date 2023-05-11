**string datatype changed** checks  

**Description**  
Table level check that scans all values in a string column and detects the data type of all values in a column. The actual_value returned from the sensor is one of: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.
 The check compares the data type detected during the current run to the last known data type detected during a previous run. For daily recurring checks, it will compare the value to yesterday&#x27;s value (or an earlier date).
 For partitioned checks, it will compare the current data type to the data type in the previous daily or monthly partition. The last partition with data is used for comparison.

___

## **string datatype changed**  
  
**Check description**  
Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|string_datatype_changed|profiling| |[string_datatype_detect](../../../../reference/sensors/column/strings-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=string_datatype_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=string_datatype_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=string_datatype_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=string_datatype_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=string_datatype_changed
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        strings:
          string_datatype_changed:
            warning: {}
            error: {}
            fatal: {}
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-18"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
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
        strings:
          string_datatype_changed:
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN(REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN(REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IS NULL OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(SAFE_CAST(analyzed_table.`target_column` AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value,
        TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
        TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        actual_value,
        time_period,
        time_period_utc
    FROM (
        SELECT
            CASE
                WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
    	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
    	        ELSE 0 END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM (
            SELECT
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
        ) AS data_type_counts
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
    ) AS result
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        actual_value,
        time_period,
        time_period_utc
    FROM (
        SELECT
            CASE
                WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
    	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
    	        ELSE 0 END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM (
            SELECT
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        ) AS data_type_counts
    GROUP BY time_period, time_period_utc) AS result
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 35-40"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          profiling_checks:
            strings:
              string_datatype_changed:
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
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            WHEN TRIM(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NULL OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            WHEN TRIM(SAFE_CAST(analyzed_table.`target_column` AS STRING)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
            TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                CASE
                    WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                    WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
        	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
        	        ELSE 0 END AS actual_value
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM (
                SELECT
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
            ) AS data_type_counts
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
        ) AS result
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                CASE
                    WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                    WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
        	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
        	        ELSE 0 END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM (
                SELECT
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ) AS data_type_counts
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc) AS result
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily string datatype changed**  
  
**Check description**  
Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_string_datatype_changed|recurring|daily|[string_datatype_detect](../../../../reference/sensors/column/strings-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_string_datatype_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_string_datatype_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_string_datatype_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_string_datatype_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_string_datatype_changed
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          strings:
            daily_string_datatype_changed:
              warning: {}
              error: {}
              fatal: {}
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-19"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
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
      recurring_checks:
        daily:
          strings:
            daily_string_datatype_changed:
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN(REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN(REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IS NULL OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(SAFE_CAST(analyzed_table.`target_column` AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value,
        CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
        TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        actual_value,
        time_period,
        time_period_utc
    FROM (
        SELECT
            CASE
                WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
    	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
    	        ELSE 0 END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM (
            SELECT
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
        ) AS data_type_counts
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
    ) AS result
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        actual_value,
        time_period,
        time_period_utc
    FROM (
        SELECT
            CASE
                WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
    	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
    	        ELSE 0 END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM (
            SELECT
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        ) AS data_type_counts
    GROUP BY time_period, time_period_utc) AS result
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 36-41"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          recurring_checks:
            daily:
              strings:
                daily_string_datatype_changed:
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
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            WHEN TRIM(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NULL OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            WHEN TRIM(SAFE_CAST(analyzed_table.`target_column` AS STRING)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
            TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                CASE
                    WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                    WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
        	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
        	        ELSE 0 END AS actual_value
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM (
                SELECT
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
            ) AS data_type_counts
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
        ) AS result
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                CASE
                    WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                    WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
        	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
        	        ELSE 0 END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM (
                SELECT
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ) AS data_type_counts
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc) AS result
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly string datatype changed**  
  
**Check description**  
Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_string_datatype_changed|recurring|monthly|[string_datatype_detect](../../../../reference/sensors/column/strings-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_string_datatype_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_string_datatype_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_string_datatype_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_string_datatype_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_string_datatype_changed
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          strings:
            monthly_string_datatype_changed:
              warning: {}
              error: {}
              fatal: {}
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-19"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
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
      recurring_checks:
        monthly:
          strings:
            monthly_string_datatype_changed:
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN(REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN(REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IS NULL OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(SAFE_CAST(analyzed_table.`target_column` AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        actual_value,
        time_period,
        time_period_utc
    FROM (
        SELECT
            CASE
                WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
    	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
    	        ELSE 0 END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM (
            SELECT
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
        ) AS data_type_counts
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
    ) AS result
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        actual_value,
        time_period,
        time_period_utc
    FROM (
        SELECT
            CASE
                WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
    	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
    	        ELSE 0 END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM (
            SELECT
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        ) AS data_type_counts
    GROUP BY time_period, time_period_utc) AS result
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 36-41"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          recurring_checks:
            monthly:
              strings:
                monthly_string_datatype_changed:
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
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            WHEN TRIM(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NULL OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            WHEN TRIM(SAFE_CAST(analyzed_table.`target_column` AS STRING)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                CASE
                    WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                    WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
        	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
        	        ELSE 0 END AS actual_value
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM (
                SELECT
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
            ) AS data_type_counts
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
        ) AS result
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                CASE
                    WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                    WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
        	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
        	        ELSE 0 END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM (
                SELECT
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ) AS data_type_counts
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc) AS result
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily partition string datatype changed**  
  
**Check description**  
Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_string_datatype_changed|partitioned|daily|[string_datatype_detect](../../../../reference/sensors/column/strings-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_partition_string_datatype_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_partition_string_datatype_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_partition_string_datatype_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_partition_string_datatype_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_string_datatype_changed
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          strings:
            daily_partition_string_datatype_changed:
              warning: {}
              error: {}
              fatal: {}
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-19"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
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
      partitioned_checks:
        daily:
          strings:
            daily_partition_string_datatype_changed:
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN(REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN(REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IS NULL OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(SAFE_CAST(analyzed_table.`target_column` AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        CAST(analyzed_table.`` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value,
        CAST(analyzed_table."" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        actual_value,
        time_period,
        time_period_utc
    FROM (
        SELECT
            CASE
                WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
    	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
    	        ELSE 0 END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM (
            SELECT
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
        ) AS data_type_counts
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
    ) AS result
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        actual_value,
        time_period,
        time_period_utc
    FROM (
        SELECT
            CASE
                WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
    	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
    	        ELSE 0 END AS actual_value,
        CAST(analyzed_table."" AS date) AS time_period,
        CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM (
            SELECT
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool,
        CAST(analyzed_table."" AS date) AS time_period,
        CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        ) AS data_type_counts
    GROUP BY time_period, time_period_utc) AS result
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value,
        CAST(analyzed_table."" AS date) AS time_period,
        CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 36-41"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          partitioned_checks:
            daily:
              strings:
                daily_partition_string_datatype_changed:
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
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            WHEN TRIM(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NULL OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            WHEN TRIM(SAFE_CAST(analyzed_table.`target_column` AS STRING)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                CASE
                    WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                    WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
        	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
        	        ELSE 0 END AS actual_value
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM (
                SELECT
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
            ) AS data_type_counts
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
        ) AS result
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                CASE
                    WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                    WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
        	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
        	        ELSE 0 END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM (
                SELECT
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ) AS data_type_counts
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc) AS result
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition string datatype changed**  
  
**Check description**  
Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_string_datatype_changed|partitioned|monthly|[string_datatype_detect](../../../../reference/sensors/column/strings-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_partition_string_datatype_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_partition_string_datatype_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_partition_string_datatype_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_partition_string_datatype_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_string_datatype_changed
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          strings:
            monthly_partition_string_datatype_changed:
              warning: {}
              error: {}
              fatal: {}
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-19"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
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
      partitioned_checks:
        monthly:
          strings:
            monthly_partition_string_datatype_changed:
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN(REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN(REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IS NULL OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(SAFE_CAST(analyzed_table.`target_column` AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 0
                            END
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        actual_value,
        time_period,
        time_period_utc
    FROM (
        SELECT
            CASE
                WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
    	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
    	        ELSE 0 END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM (
            SELECT
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
        ) AS data_type_counts
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
    ) AS result
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        actual_value,
        time_period,
        time_period_utc
    FROM (
        SELECT
            CASE
                WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
    	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
    	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
    	        ELSE 0 END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM (
            SELECT
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool,
        DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        ) AS data_type_counts
    GROUP BY time_period, time_period_utc) AS result
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 1
                            ELSE 0)
                        THEN 2
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 1
                            ELSE 0)
                        THEN 3
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 1
                            ELSE 0)
                        THEN 4
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 1
                            ELSE 0)
                        THEN 5
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                THEN 1
                            END
                            ELSE 0)
                        THEN 6
                    ELSE
                        ELSE 7
                    END
            )
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 36-41"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          partitioned_checks:
            monthly:
              strings:
                monthly_partition_string_datatype_changed:
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
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            WHEN TRIM(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NULL OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                                 REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            WHEN TRIM(SAFE_CAST(analyzed_table.`target_column` AS STRING)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[-+]?[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^[+-]?([0-9]*[.])[0-9]+$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 0
                                END
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                CASE
                    WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                    WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
        	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
        	        ELSE 0 END AS actual_value
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM (
                SELECT
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                    (SELECT COUNT(*) FROM regexp_matches(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
            ) AS data_type_counts
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
        ) AS result
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                CASE
                    WHEN COUNT(*) = SUM(matches_bool) AND COUNT(*) > 0 THEN 1
                    WHEN COUNT(*) = SUM(matches_date) AND COUNT(*) > 0 THEN 2
        	        WHEN COUNT(CASE WHEN matches_int > 0 AND matches_float = 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 3
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float > 0 AND matches_string = 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 4
        	        WHEN COUNT(CASE WHEN matches_int = 0 AND matches_float = 0 AND matches_string > 0 AND matches_date = 0 AND matches_bool = 0 THEN 1 END) = COUNT(*) THEN 5
        	        ELSE 0 END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM (
                SELECT
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+$', 'g')) AS matches_int,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[+-]?\d+\.\d+$', 'g')) AS matches_float,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^[^0-9]*$', 'g')) AS matches_string,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^((20|21|22)\d{2})([\/\-\.])((0?[1-9]|1[012])([\/\-\.])(0?[1-9]|[12][0-9]|3[01])|((0?[1-9]|[12][0-9]|3[01])([\/\-\.])(0?[1-9]|1[012])))(\s(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?)?$|(?:\d{4}[./-]\d{2}[./-]\d{2}(?: \d{2}:\d{2}:\d{2})?|\d{2}[./-]\d{2}[./-]\d{4}(?: \d{2}:\d{2}(?::\d{2})?)?)$|^(\d{4})[-./](\d{2})[-./](\d{2})([ T]\d{2}:\d{2}:\d{2})?$|^(\d{2})[-./](\d{2})[-./](\d{2})$', 'g')) AS matches_date,
                    (SELECT COUNT(*) FROM regexp_matches(CAST(analyzed_table."target_column" AS TEXT), '^(1|0|true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$', 'g')) AS matches_bool,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ) AS data_type_counts
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc) AS result
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[-+]?[0-9]+$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 1
                                ELSE 0)
                            THEN 2
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 1
                                ELSE 0)
                            THEN 3
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 1
                                ELSE 0)
                            THEN 4
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 1
                                ELSE 0)
                            THEN 5
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^[-+]?[0-9]+$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^[+-]?([0-9]*[.])[0-9]+$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS STRING) <> ''))
                                    THEN 1
                                END
                                ELSE 0)
                            THEN 6
                        ELSE
                            ELSE 7
                        END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___
