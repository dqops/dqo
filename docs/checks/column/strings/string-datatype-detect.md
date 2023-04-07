**string datatype detect** checks  

**Description**  
Table level check that returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype.

___

## **string datatype detect**  
  
**Check description**  
Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|string_datatype_detect|profiling| |[string_datatype_detect](../../../../reference/sensors/column/strings-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/comparison/#value-changed)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=string_datatype_detect
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=string_datatype_detect
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=string_datatype_detect
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=string_datatype_detect
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        strings:
          string_datatype_detect:
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
          string_datatype_detect:
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
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            END
                            WHEN ({{ lib.render_target_column('analyzed_table') }} AS STRING) IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 6
                            END
                    ELSE 7)
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
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            END
                            WHEN (analyzed_table.`target_column` AS STRING) IS NOT NULL AND (TRIM(CAST(analyzed_table.`target_column` AS STRING) <> ''))
                                THEN 6
                            END
                    ELSE 7)
                END
            )
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                $') IS NOT NULL
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                $') IS NOT NULL
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR) <> ''))
                                THEN 6
                            END
                        END
                    ELSE 7)
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
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                $') IS NOT NULL
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                $') IS NOT NULL
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS VARCHAR) <> ''))
                                THEN 6
                            END
                        END
                    ELSE 7)
                END
            )
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
              string_datatype_detect:
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
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN(REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 0
                                END
                                WHEN ({{ lib.render_target_column('analyzed_table') }} AS STRING) IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 6
                                END
                        ELSE 7)
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
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN(REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 0
                                END
                                WHEN (analyzed_table.`target_column` AS STRING) IS NOT NULL AND (TRIM(CAST(analyzed_table.`target_column` AS STRING) <> ''))
                                    THEN 6
                                END
                        ELSE 7)
                    END
                )
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                    $') IS NOT NULL
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                    $') IS NOT NULL
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR) <> ''))
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                    $') IS NOT NULL
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                    $') IS NOT NULL
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS VARCHAR) <> ''))
                                    THEN 6
                                END
                            END
                        ELSE 7)
                    END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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

## **daily string datatype detect**  
  
**Check description**  
Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_string_datatype_detect|recurring|daily|[string_datatype_detect](../../../../reference/sensors/column/strings-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/comparison/#value-changed)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_string_datatype_detect
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_string_datatype_detect
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_string_datatype_detect
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_string_datatype_detect
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          strings:
            daily_string_datatype_detect:
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
            daily_string_datatype_detect:
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
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            END
                            WHEN ({{ lib.render_target_column('analyzed_table') }} AS STRING) IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 6
                            END
                    ELSE 7)
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
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            END
                            WHEN (analyzed_table.`target_column` AS STRING) IS NOT NULL AND (TRIM(CAST(analyzed_table.`target_column` AS STRING) <> ''))
                                THEN 6
                            END
                    ELSE 7)
                END
            )
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                $') IS NOT NULL
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                $') IS NOT NULL
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR) <> ''))
                                THEN 6
                            END
                        END
                    ELSE 7)
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
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                $') IS NOT NULL
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                $') IS NOT NULL
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS VARCHAR) <> ''))
                                THEN 6
                            END
                        END
                    ELSE 7)
                END
            )
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
                daily_string_datatype_detect:
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
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN(REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 0
                                END
                                WHEN ({{ lib.render_target_column('analyzed_table') }} AS STRING) IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 6
                                END
                        ELSE 7)
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
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN(REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 0
                                END
                                WHEN (analyzed_table.`target_column` AS STRING) IS NOT NULL AND (TRIM(CAST(analyzed_table.`target_column` AS STRING) <> ''))
                                    THEN 6
                                END
                        ELSE 7)
                    END
                )
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                    $') IS NOT NULL
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                    $') IS NOT NULL
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR) <> ''))
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                    $') IS NOT NULL
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                    $') IS NOT NULL
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS VARCHAR) <> ''))
                                    THEN 6
                                END
                            END
                        ELSE 7)
                    END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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

## **monthly string datatype detect**  
  
**Check description**  
Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_string_datatype_detect|recurring|monthly|[string_datatype_detect](../../../../reference/sensors/column/strings-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/comparison/#value-changed)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_string_datatype_detect
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_string_datatype_detect
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_string_datatype_detect
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_string_datatype_detect
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          strings:
            monthly_string_datatype_detect:
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
            monthly_string_datatype_detect:
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
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            END
                            WHEN ({{ lib.render_target_column('analyzed_table') }} AS STRING) IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 6
                            END
                    ELSE 7)
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
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            END
                            WHEN (analyzed_table.`target_column` AS STRING) IS NOT NULL AND (TRIM(CAST(analyzed_table.`target_column` AS STRING) <> ''))
                                THEN 6
                            END
                    ELSE 7)
                END
            )
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                $') IS NOT NULL
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                $') IS NOT NULL
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR) <> ''))
                                THEN 6
                            END
                        END
                    ELSE 7)
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
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                $') IS NOT NULL
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                $') IS NOT NULL
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS VARCHAR) <> ''))
                                THEN 6
                            END
                        END
                    ELSE 7)
                END
            )
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
                monthly_string_datatype_detect:
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
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN(REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 0
                                END
                                WHEN ({{ lib.render_target_column('analyzed_table') }} AS STRING) IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 6
                                END
                        ELSE 7)
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
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN(REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 0
                                END
                                WHEN (analyzed_table.`target_column` AS STRING) IS NOT NULL AND (TRIM(CAST(analyzed_table.`target_column` AS STRING) <> ''))
                                    THEN 6
                                END
                        ELSE 7)
                    END
                )
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                    $') IS NOT NULL
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                    $') IS NOT NULL
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR) <> ''))
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                    $') IS NOT NULL
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                    $') IS NOT NULL
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS VARCHAR) <> ''))
                                    THEN 6
                                END
                            END
                        ELSE 7)
                    END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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

## **daily partition string datatype detect**  
  
**Check description**  
Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_string_datatype_detect|partitioned|daily|[string_datatype_detect](../../../../reference/sensors/column/strings-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/comparison/#value-changed)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_partition_string_datatype_detect
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_partition_string_datatype_detect
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_partition_string_datatype_detect
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_string_datatype_detect
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          strings:
            daily_partition_string_datatype_detect:
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
            daily_partition_string_datatype_detect:
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
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            END
                            WHEN ({{ lib.render_target_column('analyzed_table') }} AS STRING) IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 6
                            END
                    ELSE 7)
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
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            END
                            WHEN (analyzed_table.`target_column` AS STRING) IS NOT NULL AND (TRIM(CAST(analyzed_table.`target_column` AS STRING) <> ''))
                                THEN 6
                            END
                    ELSE 7)
                END
            )
        END AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
                END
            )
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                $') IS NOT NULL
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                $') IS NOT NULL
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR) <> ''))
                                THEN 6
                            END
                        END
                    ELSE 7)
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
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                $') IS NOT NULL
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                $') IS NOT NULL
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS VARCHAR) <> ''))
                                THEN 6
                            END
                        END
                    ELSE 7)
                END
            )
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
                END
            )
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                daily_partition_string_datatype_detect:
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
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN(REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 0
                                END
                                WHEN ({{ lib.render_target_column('analyzed_table') }} AS STRING) IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 6
                                END
                        ELSE 7)
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
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN(REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 0
                                END
                                WHEN (analyzed_table.`target_column` AS STRING) IS NOT NULL AND (TRIM(CAST(analyzed_table.`target_column` AS STRING) <> ''))
                                    THEN 6
                                END
                        ELSE 7)
                    END
                )
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
                    END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                    $') IS NOT NULL
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                    $') IS NOT NULL
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR) <> ''))
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                    $') IS NOT NULL
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                    $') IS NOT NULL
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS VARCHAR) <> ''))
                                    THEN 6
                                END
                            END
                        ELSE 7)
                    END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
                    END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition string datatype detect**  
  
**Check description**  
Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_string_datatype_detect|partitioned|monthly|[string_datatype_detect](../../../../reference/sensors/column/strings-column-sensors/#string-datatype-detect)|[value_changed](../../../../reference/rules/comparison/#value-changed)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_partition_string_datatype_detect
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_partition_string_datatype_detect
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_partition_string_datatype_detect
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_string_datatype_detect
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          strings:
            monthly_partition_string_datatype_detect:
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
            monthly_partition_string_datatype_detect:
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
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            END
                            WHEN ({{ lib.render_target_column('analyzed_table') }} AS STRING) IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                THEN 6
                            END
                    ELSE 7)
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
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN(REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table.`target_column`) = SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                THEN 0
                            END
                            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                THEN 0
                            END
                            WHEN (analyzed_table.`target_column` AS STRING) IS NOT NULL AND (TRIM(CAST(analyzed_table.`target_column` AS STRING) <> ''))
                                THEN 6
                            END
                    ELSE 7)
                END
            )
        END AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
                END
            )
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                $') IS NOT NULL
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                $') IS NOT NULL
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 0
                            END
                            WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR) <> ''))
                                THEN 6
                            END
                        END
                    ELSE 7)
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
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE(
                CASE
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 1
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                $') IS NOT NULL
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                $') IS NOT NULL
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 5
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                IS NOT NULL
                                THEN 0
                            END
                            WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                THEN 0
                            END
                            WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS VARCHAR) <> ''))
                                THEN 6
                            END
                        END
                    ELSE 7)
                END
            )
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
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
                                THEN 2
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                THEN 3
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^
                                ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                THEN 4
                            ELSE 0)
                        END
                    WHEN COUNT(analyzed_table."target_column") = SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                THEN 5
                            ELSE 0)
                        END
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
                                THEN 6
                            END
                        END
                    ELSE 7)
                END
            )
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                monthly_partition_string_datatype_detect:
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
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN(REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 0
                                END
                                WHEN ({{ lib.render_target_column('analyzed_table') }} AS STRING) IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING) <> ''))
                                    THEN 6
                                END
                        ELSE 7)
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
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table.`target_column`) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN(REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$")) IS TRUE
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table.`target_column`) = SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[-+]?\d+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$| ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                                    THEN 0
                                END
                                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                                    THEN 0
                                END
                                WHEN (analyzed_table.`target_column` AS STRING) IS NOT NULL AND (TRIM(CAST(analyzed_table.`target_column` AS STRING) <> ''))
                                    THEN 6
                                END
                        ELSE 7)
                    END
                )
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN REGEXP_LIKE(analyzed_table."target_column", '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
                    END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                    $') IS NOT NULL
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                    $') IS NOT NULL
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN {{lib.render_target_column('analyzed_table')}} IS NOT NULL AND (TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR) <> ''))
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE(
                    CASE
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 1
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))
                                    $') IS NOT NULL
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)
                                    $') IS NOT NULL
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 5
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[-+]?\d+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^[+-]?([0-9]*[.])[0-9]+$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                                    IS NOT NULL
                                    THEN 0
                                END
                                WHEN SUBSTRING(CAST(analyzed_table."target_column" AS VARCHAR) FROM '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$') IS NOT NULL
                                    THEN 0
                                END
                                WHEN analyzed_table."target_column" IS NOT NULL AND (TRIM(CAST(analyzed_table."target_column" AS VARCHAR) <> ''))
                                    THEN 6
                                END
                            END
                        ELSE 7)
                    END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = SUM(
                            CASE
                                WHEN {{lib.render_target_column('analyzed_table')}} ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
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
                                    THEN 2
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                                    THEN 3
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^
                                    ((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|
                                    ^(([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$'
                                    THEN 4
                                ELSE 0)
                            END
                        WHEN COUNT(analyzed_table."target_column") = SUM(
                            CASE
                                WHEN analyzed_table."target_column" ~ '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$'
                                    THEN 5
                                ELSE 0)
                            END
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
                                    THEN 6
                                END
                            END
                        ELSE 7)
                    END
                )
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___
