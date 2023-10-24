**string datatype changed** checks  

**Description**  
Table-level check that scans all values in a string column and detects the data type of all values in a column. The actual_value returned from the sensor is one of: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.
 The check compares the data type detected during the current run to the last known data type detected during a previous run. For daily monitoring checks, it will compare the value to yesterday&#x27;s value (or an earlier date).
 For partitioned checks, it will compare the current data type to the data type in the previous daily or monthly partition. The last partition with data is used for comparison.

___

## **profile string datatype changed**  
  
**Check description**  
Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|profile_string_datatype_changed|profiling| |[string_datatype_detect](../../../../reference/sensors/column/datatype-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/Comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=profile_string_datatype_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=profile_string_datatype_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=profile_string_datatype_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=profile_string_datatype_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=profile_string_datatype_changed
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        datatype:
          profile_string_datatype_changed:
            warning: {}
            error: {}
            fatal: {}
```
**Sample configuration (Yaml)**  
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
        datatype:
          profile_string_datatype_changed:
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
      
    ```sql+jinja
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
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
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
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\\d+$') IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\d+$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 0
                        WHEN TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\\d+$') IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IS NULL OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\d+$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 0
                        WHEN TRIM(analyzed_table.`target_column`) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN NULL
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN analyzed_table.[target_column] IS NULL OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table.[target_column] AS NVARCHAR(MAX))) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM [your_postgresql_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 36-41"
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
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          profiling_checks:
            datatype:
              profile_string_datatype_changed:
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
        ```sql+jinja
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
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
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
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\\d+$') IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\d+$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 0
                            WHEN TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\\d+$') IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NULL OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\d+$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 0
                            WHEN TRIM(analyzed_table.`target_column`) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        {% macro make_text_constant(string) -%}
            {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
        {%- endmacro %}
        
        {%- macro render_regex(regex) -%}
             {{ make_text_constant(regex) }}
        {%- endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN NULL
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NULL OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table.[target_column] AS NVARCHAR(MAX))) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM [your_postgresql_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    






___

## **daily string datatype changed**  
  
**Check description**  
Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_string_datatype_changed|monitoring|daily|[string_datatype_detect](../../../../reference/sensors/column/datatype-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/Comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_string_datatype_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_string_datatype_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_string_datatype_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_string_datatype_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_string_datatype_changed
```
**Check structure (Yaml)**
```yaml
      monitoring_checks:
        daily:
          datatype:
            daily_string_datatype_changed:
              warning: {}
              error: {}
              fatal: {}
```
**Sample configuration (Yaml)**  
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
          datatype:
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
      
    ```sql+jinja
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
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
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
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\\d+$') IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\d+$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 0
                        WHEN TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\\d+$') IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IS NULL OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\d+$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 0
                        WHEN TRIM(analyzed_table.`target_column`) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
        TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN NULL
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN analyzed_table.[target_column] IS NULL OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table.[target_column] AS NVARCHAR(MAX))) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM [your_postgresql_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 37-42"
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
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          monitoring_checks:
            daily:
              datatype:
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
        ```sql+jinja
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
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
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
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\\d+$') IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\d+$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 0
                            WHEN TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\\d+$') IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NULL OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\d+$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 0
                            WHEN TRIM(analyzed_table.`target_column`) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
            TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        {% macro make_text_constant(string) -%}
            {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
        {%- endmacro %}
        
        {%- macro render_regex(regex) -%}
             {{ make_text_constant(regex) }}
        {%- endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN NULL
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NULL OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table.[target_column] AS NVARCHAR(MAX))) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM [your_postgresql_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    






___

## **monthly string datatype changed**  
  
**Check description**  
Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_string_datatype_changed|monitoring|monthly|[string_datatype_detect](../../../../reference/sensors/column/datatype-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/Comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_string_datatype_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_string_datatype_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_string_datatype_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_string_datatype_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_string_datatype_changed
```
**Check structure (Yaml)**
```yaml
      monitoring_checks:
        monthly:
          datatype:
            monthly_string_datatype_changed:
              warning: {}
              error: {}
              fatal: {}
```
**Sample configuration (Yaml)**  
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
          datatype:
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
      
    ```sql+jinja
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
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
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
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\\d+$') IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\d+$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 0
                        WHEN TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\\d+$') IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IS NULL OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\d+$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 0
                        WHEN TRIM(analyzed_table.`target_column`) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN NULL
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN analyzed_table.[target_column] IS NULL OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table.[target_column] AS NVARCHAR(MAX))) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM [your_postgresql_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 37-42"
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
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          monitoring_checks:
            monthly:
              datatype:
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
        ```sql+jinja
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
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
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
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\\d+$') IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\d+$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 0
                            WHEN TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\\d+$') IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NULL OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\d+$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 0
                            WHEN TRIM(analyzed_table.`target_column`) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        {% macro make_text_constant(string) -%}
            {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
        {%- endmacro %}
        
        {%- macro render_regex(regex) -%}
             {{ make_text_constant(regex) }}
        {%- endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN NULL
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NULL OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table.[target_column] AS NVARCHAR(MAX))) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM [your_postgresql_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    






___

## **daily partition string datatype changed**  
  
**Check description**  
Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_string_datatype_changed|partitioned|daily|[string_datatype_detect](../../../../reference/sensors/column/datatype-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/Comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_partition_string_datatype_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_partition_string_datatype_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_partition_string_datatype_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_partition_string_datatype_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_string_datatype_changed
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          datatype:
            daily_partition_string_datatype_changed:
              warning: {}
              error: {}
              fatal: {}
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-20"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partition_by_column: date_column
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      partitioned_checks:
        daily:
          datatype:
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
    date_column:
      labels:
      - "date or datetime column used as a daily or monthly partitioning key, dates\
        \ (and times) are truncated to a day or a month by the sensor's query for\
        \ partitioned checks"

```
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
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
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
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
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
        CAST(analyzed_table.`date_column` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\\d+$') IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\d+$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 0
                        WHEN TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\\d+$') IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IS NULL OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\d+$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 0
                        WHEN TRIM(analyzed_table.`target_column`) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        CAST(analyzed_table."date_column" AS date) AS time_period,
        CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        CAST(analyzed_table."date_column" AS date) AS time_period,
        CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        CAST(analyzed_table."date_column" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN NULL
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN analyzed_table.[target_column] IS NULL OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table.[target_column] AS NVARCHAR(MAX))) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        CAST(analyzed_table.[date_column] AS date) AS time_period,
        CAST((CAST(analyzed_table.[date_column] AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM [your_postgresql_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-22 43-48"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
        partition_by_column: date_column
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
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
              datatype:
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
        date_column:
          labels:
          - "date or datetime column used as a daily or monthly partitioning key, dates\
            \ (and times) are truncated to a day or a month by the sensor's query for\
            \ partitioned checks"
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
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
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
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
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            CAST(analyzed_table.`date_column` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\\d+$') IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\d+$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 0
                            WHEN TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\\d+$') IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NULL OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\d+$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 0
                            WHEN TRIM(analyzed_table.`target_column`) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(analyzed_table."date_column" AS date) AS time_period,
            CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(analyzed_table."date_column" AS date) AS time_period,
            CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(analyzed_table."date_column" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        {% macro make_text_constant(string) -%}
            {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
        {%- endmacro %}
        
        {%- macro render_regex(regex) -%}
             {{ make_text_constant(regex) }}
        {%- endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN NULL
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NULL OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table.[target_column] AS NVARCHAR(MAX))) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            CAST(analyzed_table.[date_column] AS date) AS time_period,
            CAST((CAST(analyzed_table.[date_column] AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM [your_postgresql_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    






___

## **monthly partition string datatype changed**  
  
**Check description**  
Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_string_datatype_changed|partitioned|monthly|[string_datatype_detect](../../../../reference/sensors/column/datatype-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/Comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_partition_string_datatype_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_partition_string_datatype_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_partition_string_datatype_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_partition_string_datatype_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_string_datatype_changed
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          datatype:
            monthly_partition_string_datatype_changed:
              warning: {}
              error: {}
              fatal: {}
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-20"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partition_by_column: date_column
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      partitioned_checks:
        monthly:
          datatype:
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
    date_column:
      labels:
      - "date or datetime column used as a daily or monthly partitioning key, dates\
        \ (and times) are truncated to a day or a month by the sensor's query for\
        \ partitioned checks"

```
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
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
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
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
                        WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
        DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\\d+$') IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\d+$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 0
                        WHEN TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\\d+$') IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table.`target_column`) =
                SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IS NULL OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\d+$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                             REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 0
                        WHEN TRIM(analyzed_table.`target_column`) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT(analyzed_table."target_column") =
                SUM(
                    CASE
                        WHEN analyzed_table."target_column" IS NULL OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN NULL
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT_BIG(analyzed_table.[target_column]) =
                SUM(
                    CASE
                        WHEN analyzed_table.[target_column] IS NULL OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST(analyzed_table.[target_column] AS NVARCHAR(MAX))) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table.[date_column] AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table.[date_column] AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM [your_postgresql_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-22 43-48"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
        partition_by_column: date_column
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
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
              datatype:
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
        date_column:
          labels:
          - "date or datetime column used as a daily or monthly partitioning key, dates\
            \ (and times) are truncated to a day or a month by the sensor's query for\
            \ partitioned checks"
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
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
                            WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
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
                            WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
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
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\\d+$') IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\d+$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 0
                            WHEN TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\\d+$') IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table.`target_column`) =
                    SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NULL OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^[-+]?\d+$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                                 REGEXP_LIKE(analyzed_table.`target_column`, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                                THEN 0
                            WHEN TRIM(analyzed_table.`target_column`) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT(analyzed_table."target_column") =
                    SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NULL OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table."target_column" AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table."target_column" AS TEXT)) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        {% macro make_text_constant(string) -%}
            {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
        {%- endmacro %}
        
        {%- macro render_regex(regex) -%}
             {{ make_text_constant(regex) }}
        {%- endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                    SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE OR
                                 CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                                THEN 0
                            WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN NULL
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 1
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 2
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 3
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 4
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 1
                            ELSE 0
                            END
                    )
                    THEN 5
                WHEN COUNT_BIG(analyzed_table.[target_column]) =
                    SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NULL OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[-+]?\d+$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                                 CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                                THEN 0
                            WHEN TRIM(CAST(analyzed_table.[target_column] AS NVARCHAR(MAX))) <> ''
                                THEN 1
                            ELSE 0
                        END
                    )
                    THEN 6
                ELSE 7
            END AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table.[date_column] AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table.[date_column] AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM [your_postgresql_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    






___
