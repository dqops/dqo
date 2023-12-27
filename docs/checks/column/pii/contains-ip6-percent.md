**contains ip6 percent** checks  

**Description**  
Column check that calculates the percentage of rows that contains valid IP6 address values in a column.

___

## **profile contains ip6 percent**  
  
**Check description**  
Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_contains_ip6_percent|profiling| |Validity|[contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)|[max_percent](../../../../reference/rules/Comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=profile_contains_ip6_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=profile_contains_ip6_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=profile_contains_ip6_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_contains_ip6_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_contains_ip6_percent
```
**Check structure (YAML)**
```yaml
      profiling_checks:
        pii:
          profile_contains_ip6_percent:
            warning:
              max_percent: 0.0
            error:
              max_percent: 1.0
            fatal:
              max_percent: 5.0
```
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="13-21"
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
        pii:
          profile_contains_ip6_percent:
            warning:
              max_percent: 0.0
            error:
              max_percent: 1.0
            fatal:
              max_percent: 5.0
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
[contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

    === "Sensor template for BigQuery"

        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "MySQL"

    === "Sensor template for MySQL"

        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE(analyzed_table.`target_column`,
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE(analyzed_table.`target_column`,
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Oracle"

    === "Sensor template for Oracle"

        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM (
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE(analyzed_table."target_column",
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE(analyzed_table."target_column",
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                original_table.*,
            TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
            CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table
        ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "PostgreSQL"

    === "Sensor template for PostgreSQL"

        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                          '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                             THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table."target_column",
                                         '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                             REGEXP_LIKE(analyzed_table."target_column",
                                          '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                             THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

    === "Sensor template for Redshift"

        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{lib.render_target_column('analyzed_table')}} ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                            OR {{lib.render_target_column('analyzed_table')}} ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                            OR analyzed_table."target_column" ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

    === "Sensor template for Snowflake"

        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                        THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table."target_column", '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                            OR REGEXP_LIKE(analyzed_table."target_column", '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                        THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

        ```sql+jinja
        {% import '/dialects/spark.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

    === "Sensor template for SQL Server"

        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {% set byte = "[0-9A-Fa-f]" %}
        {% set qbyte = byte + byte + byte + byte %}
        {% set negbyte = "[^g-z]" %}
        {% set tested_column = "CAST(" ~ lib.render_target_column('analyzed_table') ~ " AS NVARCHAR(MAX))" %}
        {% set colons_count = "len(" ~ lib.render_target_column('analyzed_table') ~ ") - len(replace(" ~  lib.render_target_column('analyzed_table') ~ ", ':', ''))" %}
        
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ tested_column }} LIKE '%{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}%'
                            OR
                            {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                AND {{ tested_column }} LIKE '%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%'
                            OR -- 6x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 7
                            OR -- 5x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 6
                            OR -- 4x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 5
                            OR -- 3x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                               ) AND {{ colons_count }} = 4
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
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
                WHEN COUNT_BIG(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]%'
                            OR
                            CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                AND CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%'
                            OR -- 6x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 7
                            OR -- 5x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 6
                            OR -- 4x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 5
                            OR -- 3x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%'
                               ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 4
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        ```

  
Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"
      
    **Sample configuration with data grouping enabled (YAML)**  
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="11-21 39-44"
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
            pii:
              profile_contains_ip6_percent:
                warning:
                  max_percent: 0.0
                error:
                  max_percent: 1.0
                fatal:
                  max_percent: 5.0
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

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                    r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                    r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE(analyzed_table.`target_column`,
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE(analyzed_table.`target_column`,
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE(analyzed_table."target_column",
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE(analyzed_table."target_column",
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                              '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column",
                                             '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                 REGEXP_LIKE(analyzed_table."target_column",
                                              '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                                OR {{lib.render_target_column('analyzed_table')}} ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                                OR analyzed_table."target_column" ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                                OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                            THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                                OR REGEXP_LIKE(analyzed_table."target_column", '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                            THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                    "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                    "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {% set byte = "[0-9A-Fa-f]" %}
            {% set qbyte = byte + byte + byte + byte %}
            {% set negbyte = "[^g-z]" %}
            {% set tested_column = "CAST(" ~ lib.render_target_column('analyzed_table') ~ " AS NVARCHAR(MAX))" %}
            {% set colons_count = "len(" ~ lib.render_target_column('analyzed_table') ~ ") - len(replace(" ~  lib.render_target_column('analyzed_table') ~ ", ':', ''))" %}
            
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ tested_column }} LIKE '%{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}%'
                                OR
                                {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    AND {{ tested_column }} LIKE '%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%'
                                OR -- 6x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 7
                                OR -- 5x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 6
                                OR -- 4x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 5
                                OR -- 3x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                   ) AND {{ colons_count }} = 4
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
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
                    WHEN COUNT_BIG(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]%'
                                OR
                                CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    AND CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%'
                                OR -- 6x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 7
                                OR -- 5x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 6
                                OR -- 4x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 5
                                OR -- 3x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%'
                                   ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 4
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    






___

## **daily contains ip6 percent**  
  
**Check description**  
Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_contains_ip6_percent|monitoring|daily|Validity|[contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)|[max_percent](../../../../reference/rules/Comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_contains_ip6_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_contains_ip6_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_contains_ip6_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_contains_ip6_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_contains_ip6_percent
```
**Check structure (YAML)**
```yaml
      monitoring_checks:
        daily:
          pii:
            daily_contains_ip6_percent:
              warning:
                max_percent: 0.0
              error:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="13-22"
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
          pii:
            daily_contains_ip6_percent:
              warning:
                max_percent: 0.0
              error:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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
[contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

    === "Sensor template for BigQuery"

        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "MySQL"

    === "Sensor template for MySQL"

        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE(analyzed_table.`target_column`,
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE(analyzed_table.`target_column`,
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Oracle"

    === "Sensor template for Oracle"

        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM (
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE(analyzed_table."target_column",
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE(analyzed_table."target_column",
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                original_table.*,
            TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
            CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table
        ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "PostgreSQL"

    === "Sensor template for PostgreSQL"

        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                          '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                             THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table."target_column",
                                         '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                             REGEXP_LIKE(analyzed_table."target_column",
                                          '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                             THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

    === "Sensor template for Redshift"

        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{lib.render_target_column('analyzed_table')}} ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                            OR {{lib.render_target_column('analyzed_table')}} ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                            OR analyzed_table."target_column" ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

    === "Sensor template for Snowflake"

        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                        THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table."target_column", '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                            OR REGEXP_LIKE(analyzed_table."target_column", '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                        THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
            TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

        ```sql+jinja
        {% import '/dialects/spark.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

    === "Sensor template for SQL Server"

        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {% set byte = "[0-9A-Fa-f]" %}
        {% set qbyte = byte + byte + byte + byte %}
        {% set negbyte = "[^g-z]" %}
        {% set tested_column = "CAST(" ~ lib.render_target_column('analyzed_table') ~ " AS NVARCHAR(MAX))" %}
        {% set colons_count = "len(" ~ lib.render_target_column('analyzed_table') ~ ") - len(replace(" ~  lib.render_target_column('analyzed_table') ~ ", ':', ''))" %}
        
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ tested_column }} LIKE '%{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}%'
                            OR
                            {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                AND {{ tested_column }} LIKE '%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%'
                            OR -- 6x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 7
                            OR -- 5x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 6
                            OR -- 4x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 5
                            OR -- 3x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                               ) AND {{ colons_count }} = 4
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
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
                WHEN COUNT_BIG(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]%'
                            OR
                            CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                AND CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%'
                            OR -- 6x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 7
                            OR -- 5x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 6
                            OR -- 4x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 5
                            OR -- 3x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%'
                               ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 4
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value,
            CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        ```

  
Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"
      
    **Sample configuration with data grouping enabled (YAML)**  
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="11-21 40-45"
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
              pii:
                daily_contains_ip6_percent:
                  warning:
                    max_percent: 0.0
                  error:
                    max_percent: 1.0
                  fatal:
                    max_percent: 5.0
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

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                    r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                    r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE(analyzed_table.`target_column`,
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE(analyzed_table.`target_column`,
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE(analyzed_table."target_column",
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE(analyzed_table."target_column",
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                              '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column",
                                             '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                 REGEXP_LIKE(analyzed_table."target_column",
                                              '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                                OR {{lib.render_target_column('analyzed_table')}} ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                                OR analyzed_table."target_column" ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                                OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                            THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                                OR REGEXP_LIKE(analyzed_table."target_column", '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                            THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
                TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                    "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                    "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {% set byte = "[0-9A-Fa-f]" %}
            {% set qbyte = byte + byte + byte + byte %}
            {% set negbyte = "[^g-z]" %}
            {% set tested_column = "CAST(" ~ lib.render_target_column('analyzed_table') ~ " AS NVARCHAR(MAX))" %}
            {% set colons_count = "len(" ~ lib.render_target_column('analyzed_table') ~ ") - len(replace(" ~  lib.render_target_column('analyzed_table') ~ ", ':', ''))" %}
            
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ tested_column }} LIKE '%{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}%'
                                OR
                                {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    AND {{ tested_column }} LIKE '%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%'
                                OR -- 6x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 7
                                OR -- 5x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 6
                                OR -- 4x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 5
                                OR -- 3x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                   ) AND {{ colons_count }} = 4
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
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
                    WHEN COUNT_BIG(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]%'
                                OR
                                CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    AND CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%'
                                OR -- 6x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 7
                                OR -- 5x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 6
                                OR -- 4x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 5
                                OR -- 3x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%'
                                   ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 4
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
                CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    






___

## **monthly contains ip6 percent**  
  
**Check description**  
Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_contains_ip6_percent|monitoring|monthly|Validity|[contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)|[max_percent](../../../../reference/rules/Comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_contains_ip6_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_contains_ip6_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_contains_ip6_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_contains_ip6_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_contains_ip6_percent
```
**Check structure (YAML)**
```yaml
      monitoring_checks:
        monthly:
          pii:
            monthly_contains_ip6_percent:
              warning:
                max_percent: 0.0
              error:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="13-22"
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
          pii:
            monthly_contains_ip6_percent:
              warning:
                max_percent: 0.0
              error:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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
[contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

    === "Sensor template for BigQuery"

        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "MySQL"

    === "Sensor template for MySQL"

        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE(analyzed_table.`target_column`,
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE(analyzed_table.`target_column`,
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Oracle"

    === "Sensor template for Oracle"

        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM (
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE(analyzed_table."target_column",
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE(analyzed_table."target_column",
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                original_table.*,
            TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
            CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table
        ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "PostgreSQL"

    === "Sensor template for PostgreSQL"

        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                          '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                             THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table."target_column",
                                         '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                             REGEXP_LIKE(analyzed_table."target_column",
                                          '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                             THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

    === "Sensor template for Redshift"

        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{lib.render_target_column('analyzed_table')}} ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                            OR {{lib.render_target_column('analyzed_table')}} ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                            OR analyzed_table."target_column" ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

    === "Sensor template for Snowflake"

        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                        THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table."target_column", '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                            OR REGEXP_LIKE(analyzed_table."target_column", '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                        THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

        ```sql+jinja
        {% import '/dialects/spark.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

    === "Sensor template for SQL Server"

        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {% set byte = "[0-9A-Fa-f]" %}
        {% set qbyte = byte + byte + byte + byte %}
        {% set negbyte = "[^g-z]" %}
        {% set tested_column = "CAST(" ~ lib.render_target_column('analyzed_table') ~ " AS NVARCHAR(MAX))" %}
        {% set colons_count = "len(" ~ lib.render_target_column('analyzed_table') ~ ") - len(replace(" ~  lib.render_target_column('analyzed_table') ~ ", ':', ''))" %}
        
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ tested_column }} LIKE '%{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}%'
                            OR
                            {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                AND {{ tested_column }} LIKE '%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%'
                            OR -- 6x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 7
                            OR -- 5x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 6
                            OR -- 4x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 5
                            OR -- 3x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                               ) AND {{ colons_count }} = 4
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
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
                WHEN COUNT_BIG(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]%'
                            OR
                            CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                AND CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%'
                            OR -- 6x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 7
                            OR -- 5x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 6
                            OR -- 4x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 5
                            OR -- 3x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%'
                               ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 4
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        ```

  
Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"
      
    **Sample configuration with data grouping enabled (YAML)**  
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="11-21 40-45"
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
              pii:
                monthly_contains_ip6_percent:
                  warning:
                    max_percent: 0.0
                  error:
                    max_percent: 1.0
                  fatal:
                    max_percent: 5.0
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

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                    r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                    r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE(analyzed_table.`target_column`,
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE(analyzed_table.`target_column`,
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE(analyzed_table."target_column",
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE(analyzed_table."target_column",
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                              '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column",
                                             '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                 REGEXP_LIKE(analyzed_table."target_column",
                                              '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                                OR {{lib.render_target_column('analyzed_table')}} ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                                OR analyzed_table."target_column" ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                                OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                            THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                                OR REGEXP_LIKE(analyzed_table."target_column", '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                            THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                    "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                    "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {% set byte = "[0-9A-Fa-f]" %}
            {% set qbyte = byte + byte + byte + byte %}
            {% set negbyte = "[^g-z]" %}
            {% set tested_column = "CAST(" ~ lib.render_target_column('analyzed_table') ~ " AS NVARCHAR(MAX))" %}
            {% set colons_count = "len(" ~ lib.render_target_column('analyzed_table') ~ ") - len(replace(" ~  lib.render_target_column('analyzed_table') ~ ", ':', ''))" %}
            
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ tested_column }} LIKE '%{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}%'
                                OR
                                {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    AND {{ tested_column }} LIKE '%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%'
                                OR -- 6x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 7
                                OR -- 5x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 6
                                OR -- 4x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 5
                                OR -- 3x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                   ) AND {{ colons_count }} = 4
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
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
                    WHEN COUNT_BIG(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]%'
                                OR
                                CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    AND CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%'
                                OR -- 6x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 7
                                OR -- 5x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 6
                                OR -- 4x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 5
                                OR -- 3x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%'
                                   ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 4
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    






___

## **daily partition contains ip6 percent**  
  
**Check description**  
Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_partition_contains_ip6_percent|partitioned|daily|Validity|[contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)|[max_percent](../../../../reference/rules/Comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_partition_contains_ip6_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_partition_contains_ip6_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_partition_contains_ip6_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_contains_ip6_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_partition_contains_ip6_percent
```
**Check structure (YAML)**
```yaml
      partitioned_checks:
        daily:
          pii:
            daily_partition_contains_ip6_percent:
              warning:
                max_percent: 0.0
              error:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="14-23"
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
          pii:
            daily_partition_contains_ip6_percent:
              warning:
                max_percent: 0.0
              error:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

    === "Sensor template for BigQuery"

        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            CAST(analyzed_table.`date_column` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "MySQL"

    === "Sensor template for MySQL"

        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE(analyzed_table.`target_column`,
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE(analyzed_table.`target_column`,
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Oracle"

    === "Sensor template for Oracle"

        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM (
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE(analyzed_table."target_column",
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE(analyzed_table."target_column",
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                original_table.*,
            TRUNC(CAST(original_table."date_column" AS DATE)) AS time_period,
            CAST(TRUNC(CAST(original_table."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table
        ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "PostgreSQL"

    === "Sensor template for PostgreSQL"

        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                          '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                             THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table."target_column",
                                         '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                             REGEXP_LIKE(analyzed_table."target_column",
                                          '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                             THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            CAST(analyzed_table."date_column" AS date) AS time_period,
            CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

    === "Sensor template for Redshift"

        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{lib.render_target_column('analyzed_table')}} ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                            OR {{lib.render_target_column('analyzed_table')}} ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                            OR analyzed_table."target_column" ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            CAST(analyzed_table."date_column" AS date) AS time_period,
            CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

    === "Sensor template for Snowflake"

        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                        THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table."target_column", '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                            OR REGEXP_LIKE(analyzed_table."target_column", '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                        THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            CAST(analyzed_table."date_column" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

        ```sql+jinja
        {% import '/dialects/spark.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            CAST(analyzed_table.`date_column` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

    === "Sensor template for SQL Server"

        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {% set byte = "[0-9A-Fa-f]" %}
        {% set qbyte = byte + byte + byte + byte %}
        {% set negbyte = "[^g-z]" %}
        {% set tested_column = "CAST(" ~ lib.render_target_column('analyzed_table') ~ " AS NVARCHAR(MAX))" %}
        {% set colons_count = "len(" ~ lib.render_target_column('analyzed_table') ~ ") - len(replace(" ~  lib.render_target_column('analyzed_table') ~ ", ':', ''))" %}
        
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ tested_column }} LIKE '%{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}%'
                            OR
                            {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                AND {{ tested_column }} LIKE '%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%'
                            OR -- 6x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 7
                            OR -- 5x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 6
                            OR -- 4x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 5
                            OR -- 3x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                               ) AND {{ colons_count }} = 4
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
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
                WHEN COUNT_BIG(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]%'
                            OR
                            CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                AND CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%'
                            OR -- 6x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 7
                            OR -- 5x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 6
                            OR -- 4x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 5
                            OR -- 3x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%'
                               ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 4
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value,
            CAST(analyzed_table.[date_column] AS date) AS time_period,
            CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
        ORDER BY CAST(analyzed_table.[date_column] AS date)
        
            
        ```

  
Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"
      
    **Sample configuration with data grouping enabled (YAML)**  
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="12-22 46-51"
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
              pii:
                daily_partition_contains_ip6_percent:
                  warning:
                    max_percent: 0.0
                  error:
                    max_percent: 1.0
                  fatal:
                    max_percent: 5.0
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

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                    r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                    r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE(analyzed_table.`target_column`,
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE(analyzed_table.`target_column`,
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE(analyzed_table."target_column",
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE(analyzed_table."target_column",
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                TRUNC(CAST(original_table."date_column" AS DATE)) AS time_period,
                CAST(TRUNC(CAST(original_table."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                              '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column",
                                             '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                 REGEXP_LIKE(analyzed_table."target_column",
                                              '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                                OR {{lib.render_target_column('analyzed_table')}} ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                                OR analyzed_table."target_column" ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                                OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                            THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                                OR REGEXP_LIKE(analyzed_table."target_column", '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                            THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                    "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                    "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {% set byte = "[0-9A-Fa-f]" %}
            {% set qbyte = byte + byte + byte + byte %}
            {% set negbyte = "[^g-z]" %}
            {% set tested_column = "CAST(" ~ lib.render_target_column('analyzed_table') ~ " AS NVARCHAR(MAX))" %}
            {% set colons_count = "len(" ~ lib.render_target_column('analyzed_table') ~ ") - len(replace(" ~  lib.render_target_column('analyzed_table') ~ ", ':', ''))" %}
            
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ tested_column }} LIKE '%{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}%'
                                OR
                                {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    AND {{ tested_column }} LIKE '%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%'
                                OR -- 6x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 7
                                OR -- 5x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 6
                                OR -- 4x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 5
                                OR -- 3x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                   ) AND {{ colons_count }} = 4
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
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
                    WHEN COUNT_BIG(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]%'
                                OR
                                CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    AND CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%'
                                OR -- 6x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 7
                                OR -- 5x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 6
                                OR -- 4x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 5
                                OR -- 3x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%'
                                   ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 4
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                CAST(analyzed_table.[date_column] AS date) AS time_period,
                CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state], CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
            ORDER BY level_1, level_2CAST(analyzed_table.[date_column] AS date)
            
                
            ```
    






___

## **monthly partition contains ip6 percent**  
  
**Check description**  
Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_partition_contains_ip6_percent|partitioned|monthly|Validity|[contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)|[max_percent](../../../../reference/rules/Comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_partition_contains_ip6_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_partition_contains_ip6_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_partition_contains_ip6_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_partition_contains_ip6_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_partition_contains_ip6_percent
```
**Check structure (YAML)**
```yaml
      partitioned_checks:
        monthly:
          pii:
            monthly_partition_contains_ip6_percent:
              warning:
                max_percent: 0.0
              error:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="14-23"
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
          pii:
            monthly_partition_contains_ip6_percent:
              warning:
                max_percent: 0.0
              error:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

    === "Sensor template for BigQuery"

        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "MySQL"

    === "Sensor template for MySQL"

        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE(analyzed_table.`target_column`,
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE(analyzed_table.`target_column`,
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Oracle"

    === "Sensor template for Oracle"

        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM (
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP_LIKE(analyzed_table."target_column",
                                        '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                            REGEXP_LIKE(analyzed_table."target_column",
                                         '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            time_period,
            time_period_utc
        FROM (
            SELECT
                original_table.*,
            TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS time_period,
            CAST(TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table
        ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "PostgreSQL"

    === "Sensor template for PostgreSQL"

        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                         '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                          '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                             THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table."target_column",
                                         '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                             REGEXP_LIKE(analyzed_table."target_column",
                                          '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                             THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

    === "Sensor template for Redshift"

        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{lib.render_target_column('analyzed_table')}} ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                            OR {{lib.render_target_column('analyzed_table')}} ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                            OR analyzed_table."target_column" ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

    === "Sensor template for Snowflake"

        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                        THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table."target_column", '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                            OR REGEXP_LIKE(analyzed_table."target_column", '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                        THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

        ```sql+jinja
        {% import '/dialects/spark.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN
                            REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                            REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

    === "Sensor template for SQL Server"

        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {% set byte = "[0-9A-Fa-f]" %}
        {% set qbyte = byte + byte + byte + byte %}
        {% set negbyte = "[^g-z]" %}
        {% set tested_column = "CAST(" ~ lib.render_target_column('analyzed_table') ~ " AS NVARCHAR(MAX))" %}
        {% set colons_count = "len(" ~ lib.render_target_column('analyzed_table') ~ ") - len(replace(" ~  lib.render_target_column('analyzed_table') ~ ", ':', ''))" %}
        
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ tested_column }} LIKE '%{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}%'
                            OR
                            {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                AND {{ tested_column }} LIKE '%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%'
                            OR -- 6x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 7
                            OR -- 5x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 6
                            OR -- 4x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            ) AND {{ colons_count }} = 5
                            OR -- 3x bytes
                            ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                               ) AND {{ colons_count }} = 4
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
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
                WHEN COUNT_BIG(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]%'
                            OR
                            CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                AND CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%'
                            OR -- 6x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 7
                            OR -- 5x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 6
                            OR -- 4x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                            ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 5
                            OR -- 3x bytes
                            (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%'
                                OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%'
                               ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 4
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value,
            DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
        ORDER BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
        
            
        ```

  
Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"
      
    **Sample configuration with data grouping enabled (YAML)**  
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="12-22 46-51"
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
              pii:
                monthly_partition_contains_ip6_percent:
                  warning:
                    max_percent: 0.0
                  error:
                    max_percent: 1.0
                  fatal:
                    max_percent: 5.0
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

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [contains_ip6_percent](../../../../reference/sensors/column/pii-column-sensors/#contains-ip6-percent)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                    r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP_CONTAINS(CAST(analyzed_table.`target_column` AS STRING),
                                    r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE(analyzed_table.`target_column`,
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE(analyzed_table.`target_column`,
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP_LIKE(analyzed_table."target_column",
                                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                REGEXP_LIKE(analyzed_table."target_column",
                                             '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                             '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                 REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                              '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column",
                                             '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                                 REGEXP_LIKE(analyzed_table."target_column",
                                              '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                                 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{lib.render_target_column('analyzed_table')}} ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                                OR {{lib.render_target_column('analyzed_table')}} ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                                OR analyzed_table."target_column" ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                                OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                            THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_LIKE(analyzed_table."target_column", '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                                OR REGEXP_LIKE(analyzed_table."target_column", '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                            THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                                    "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN
                                REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                    "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                                REGEXP(CAST(analyzed_table.`target_column` AS STRING),
                                    "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {% set byte = "[0-9A-Fa-f]" %}
            {% set qbyte = byte + byte + byte + byte %}
            {% set negbyte = "[^g-z]" %}
            {% set tested_column = "CAST(" ~ lib.render_target_column('analyzed_table') ~ " AS NVARCHAR(MAX))" %}
            {% set colons_count = "len(" ~ lib.render_target_column('analyzed_table') ~ ") - len(replace(" ~  lib.render_target_column('analyzed_table') ~ ", ':', ''))" %}
            
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ tested_column }} LIKE '%{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}%'
                                OR
                                {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    AND {{ tested_column }} LIKE '%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%'
                                OR -- 6x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 7
                                OR -- 5x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 6
                                OR -- 4x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                                ) AND {{ colons_count }} = 5
                                OR -- 3x bytes
                                ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                                    OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                                   ) AND {{ colons_count }} = 4
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
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
                    WHEN COUNT_BIG(*) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]:[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]%'
                                OR
                                CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    AND CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%:[^g-z]%'
                                OR -- 6x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 7
                                OR -- 5x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%::%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 6
                                OR -- 4x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]:%[^g-z]::%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%[^g-z]:%'
                                ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 5
                                OR -- 3x bytes
                                (CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]:%[^g-z]::%[^g-z]:%'
                                    OR CAST(analyzed_table.[target_column] AS NVARCHAR(MAX)) LIKE '%[^g-z]::%[^g-z]:%[^g-z]:%'
                                   ) AND len(analyzed_table.[target_column]) - len(replace(analyzed_table.[target_column], ':', '')) = 4
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state], DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
            ORDER BY level_1, level_2DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
            
                
            ```
    






___
