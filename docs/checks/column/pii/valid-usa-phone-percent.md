**valid usa phone percent** checks  

**Description**  
Column check that calculates percent of valid USA phone values in a column.

___

## **valid usa phone percent**  
  
**Check description**  
Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|valid_usa_phone_percent|profiling| |[valid_usa_phone_percent](../../../../reference/sensors/column/pii-column-sensors/#valid-usa-phone-percent)|[min_percent](../../../../reference/rules/comparison/#min-percent)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=valid_usa_phone_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=valid_usa_phone_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=valid_usa_phone_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=valid_usa_phone_percent
```
**Check structure (Yaml)**
```yaml
      checks:
        pii:
          valid_usa_phone_percent:
            error:
              min_percent: 98.0
            warning:
              min_percent: 99.0
            fatal:
              min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="16-24"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: target_schema
    table_name: target_table
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      checks:
        pii:
          valid_usa_phone_percent:
            error:
              min_percent: 98.0
            warning:
              min_percent: 99.0
            fatal:
              min_percent: 95.0
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST(analyzed_table.`target_column` AS STRING),
                        r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(1\)\d{10,11}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{10,11}$$ THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\(1\)\d{10,11}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\d{10,11}$$ THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
        TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING(analyzed_table."target_column" from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN replace(replace(replace(replace({{ lib.render_target_column('analyzed_table') }}, '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN replace(replace(replace(replace(analyzed_table."target_column", '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="14-21 41-46"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      target:
        schema_name: target_schema
        table_name: target_table
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
          checks:
            pii:
              valid_usa_phone_percent:
                error:
                  min_percent: 98.0
                warning:
                  min_percent: 99.0
                fatal:
                  min_percent: 95.0
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                        ) THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST(analyzed_table.`target_column` AS STRING),
                            r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                        ) THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(1\)\d{10,11}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{10,11}$$ THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\(1\)\d{10,11}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\d{10,11}$$ THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
            TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
        FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING(analyzed_table."target_column" from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN replace(replace(replace(replace({{ lib.render_target_column('analyzed_table') }}, '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN replace(replace(replace(replace(analyzed_table."target_column", '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily checkpoint valid usa phone percent**  
  
**Check description**  
Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_valid_usa_phone_percent|checkpoint|daily|[valid_usa_phone_percent](../../../../reference/sensors/column/pii-column-sensors/#valid-usa-phone-percent)|[min_percent](../../../../reference/rules/comparison/#min-percent)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_checkpoint_valid_usa_phone_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_checkpoint_valid_usa_phone_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_checkpoint_valid_usa_phone_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_valid_usa_phone_percent
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        daily:
          pii:
            daily_checkpoint_valid_usa_phone_percent:
              error:
                min_percent: 98.0
              warning:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="16-25"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: target_schema
    table_name: target_table
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      checkpoints:
        daily:
          pii:
            daily_checkpoint_valid_usa_phone_percent:
              error:
                min_percent: 98.0
              warning:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST(analyzed_table.`target_column` AS STRING),
                        r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(1\)\d{10,11}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{10,11}$$ THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\(1\)\d{10,11}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\d{10,11}$$ THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
        TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING(analyzed_table."target_column" from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN replace(replace(replace(replace({{ lib.render_target_column('analyzed_table') }}, '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN replace(replace(replace(replace(analyzed_table."target_column", '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="14-21 42-47"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      target:
        schema_name: target_schema
        table_name: target_table
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
          checkpoints:
            daily:
              pii:
                daily_checkpoint_valid_usa_phone_percent:
                  error:
                    min_percent: 98.0
                  warning:
                    min_percent: 99.0
                  fatal:
                    min_percent: 95.0
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                        ) THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST(analyzed_table.`target_column` AS STRING),
                            r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                        ) THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(1\)\d{10,11}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{10,11}$$ THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\(1\)\d{10,11}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\d{10,11}$$ THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
            TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
        FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING(analyzed_table."target_column" from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN replace(replace(replace(replace({{ lib.render_target_column('analyzed_table') }}, '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN replace(replace(replace(replace(analyzed_table."target_column", '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly checkpoint valid usa phone percent**  
  
**Check description**  
Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_valid_usa_phone_percent|checkpoint|monthly|[valid_usa_phone_percent](../../../../reference/sensors/column/pii-column-sensors/#valid-usa-phone-percent)|[min_percent](../../../../reference/rules/comparison/#min-percent)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_checkpoint_valid_usa_phone_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_checkpoint_valid_usa_phone_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_checkpoint_valid_usa_phone_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_valid_usa_phone_percent
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        monthly:
          pii:
            monthly_checkpoint_valid_usa_phone_percent:
              error:
                min_percent: 98.0
              warning:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="16-25"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: target_schema
    table_name: target_table
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      checkpoints:
        monthly:
          pii:
            monthly_checkpoint_valid_usa_phone_percent:
              error:
                min_percent: 98.0
              warning:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST(analyzed_table.`target_column` AS STRING),
                        r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(1\)\d{10,11}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{10,11}$$ THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\(1\)\d{10,11}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\d{10,11}$$ THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING(analyzed_table."target_column" from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN replace(replace(replace(replace({{ lib.render_target_column('analyzed_table') }}, '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN replace(replace(replace(replace(analyzed_table."target_column", '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="14-21 42-47"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      target:
        schema_name: target_schema
        table_name: target_table
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
          checkpoints:
            monthly:
              pii:
                monthly_checkpoint_valid_usa_phone_percent:
                  error:
                    min_percent: 98.0
                  warning:
                    min_percent: 99.0
                  fatal:
                    min_percent: 95.0
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                        ) THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST(analyzed_table.`target_column` AS STRING),
                            r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                        ) THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(1\)\d{10,11}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{10,11}$$ THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\(1\)\d{10,11}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\d{10,11}$$ THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING(analyzed_table."target_column" from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN replace(replace(replace(replace({{ lib.render_target_column('analyzed_table') }}, '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN replace(replace(replace(replace(analyzed_table."target_column", '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily partition valid usa phone percent**  
  
**Check description**  
Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_valid_usa_phone_percent|partitioned|daily|[valid_usa_phone_percent](../../../../reference/sensors/column/pii-column-sensors/#valid-usa-phone-percent)|[min_percent](../../../../reference/rules/comparison/#min-percent)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_partition_valid_usa_phone_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_partition_valid_usa_phone_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_partition_valid_usa_phone_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_valid_usa_phone_percent
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          pii:
            daily_partition_valid_usa_phone_percent:
              error:
                min_percent: 98.0
              warning:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="16-25"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: target_schema
    table_name: target_table
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
          pii:
            daily_partition_valid_usa_phone_percent:
              error:
                min_percent: 98.0
              warning:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST(analyzed_table.`target_column` AS STRING),
                        r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(1\)\d{10,11}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{10,11}$$ THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\(1\)\d{10,11}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\d{10,11}$$ THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING(analyzed_table."target_column" from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN replace(replace(replace(replace({{ lib.render_target_column('analyzed_table') }}, '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN replace(replace(replace(replace(analyzed_table."target_column", '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="14-21 42-47"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      target:
        schema_name: target_schema
        table_name: target_table
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
              pii:
                daily_partition_valid_usa_phone_percent:
                  error:
                    min_percent: 98.0
                  warning:
                    min_percent: 99.0
                  fatal:
                    min_percent: 95.0
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                        ) THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST(analyzed_table.`target_column` AS STRING),
                            r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                        ) THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(1\)\d{10,11}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{10,11}$$ THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\(1\)\d{10,11}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\d{10,11}$$ THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING(analyzed_table."target_column" from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN replace(replace(replace(replace({{ lib.render_target_column('analyzed_table') }}, '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN replace(replace(replace(replace(analyzed_table."target_column", '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition valid usa phone percent**  
  
**Check description**  
Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_valid_usa_phone_percent|partitioned|monthly|[valid_usa_phone_percent](../../../../reference/sensors/column/pii-column-sensors/#valid-usa-phone-percent)|[min_percent](../../../../reference/rules/comparison/#min-percent)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_partition_valid_usa_phone_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_partition_valid_usa_phone_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_partition_valid_usa_phone_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_valid_usa_phone_percent
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          pii:
            monthly_partition_valid_usa_phone_percent:
              error:
                min_percent: 98.0
              warning:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="16-25"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: target_schema
    table_name: target_table
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
          pii:
            monthly_partition_valid_usa_phone_percent:
              error:
                min_percent: 98.0
              warning:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST(analyzed_table.`target_column` AS STRING),
                        r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(1\)\d{10,11}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{10,11}$$ THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\(1\)\d{10,11}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                    WHEN analyzed_table."target_column" REGEXP $$\d{10,11}$$ THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING(analyzed_table."target_column" from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN replace(replace(replace(replace({{ lib.render_target_column('analyzed_table') }}, '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN replace(replace(replace(replace(analyzed_table."target_column", '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="14-21 42-47"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      target:
        schema_name: target_schema
        table_name: target_table
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
              pii:
                monthly_partition_valid_usa_phone_percent:
                  error:
                    min_percent: 98.0
                  warning:
                    min_percent: 99.0
                  fatal:
                    min_percent: 95.0
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                        ) THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST(analyzed_table.`target_column` AS STRING),
                            r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$"
                        ) THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\(1\)\d{10,11}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP $$\d{10,11}$$ THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\d{4} \d{4} \d{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$[0-9]{3}-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\([0-9]{3}\)[0-9]{7}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\(\+1\)\d{10,11}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\(1\)\d{10,11}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\+1\([0-9]{3}\)[0-9]{7}}$$ THEN 1
                        WHEN analyzed_table."target_column" REGEXP $$\d{10,11}$$ THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
        FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING(analyzed_table."target_column" from '^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$') IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN replace(replace(replace(replace({{ lib.render_target_column('analyzed_table') }}, '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN replace(replace(replace(replace(analyzed_table."target_column", '+', ''), '(', ''), ')', ''), '-', '')  ~ '^\\d{10}\\d?$'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___
