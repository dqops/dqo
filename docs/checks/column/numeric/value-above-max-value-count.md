**value above max value count** checks  

**Description**  
Column level check that ensures that the number of values in the monitored column with a value above the value defined by the user as a parameter does not exceed set thresholds.

___

## **profile value above max value count**  
  
**Check description**  
The check counts the number of values in the column that is above the value defined by the user as a parameter.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|profile_value_above_max_value_count|profiling| |[value_above_max_value_count](../../../../reference/sensors/column/numeric-column-sensors/#value-above-max-value-count)|[max_count](../../../../reference/rules/Comparison/#max-count)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=profile_value_above_max_value_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=profile_value_above_max_value_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=profile_value_above_max_value_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=profile_value_above_max_value_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=profile_value_above_max_value_count
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        numeric:
          profile_value_above_max_value_count:
            warning:
              max_count: 0
            error:
              max_count: 10
            fatal:
              max_count: 15
```
**Sample configuration (Yaml)**  
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
        numeric:
          profile_value_above_max_value_count:
            warning:
              max_count: 0
            error:
              max_count: 10
            fatal:
              max_count: 15
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
      {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
              {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
      FROM(
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period,
        time_period_utc
      FROM(
          SELECT
                     original_table.*,
        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                     FROM "<target_schema>"."<target_table>" original_table
           ) analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.[target_column] > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
            numeric:
              profile_value_above_max_value_count:
                warning:
                  max_count: 0
                error:
                  max_count: 10
                fatal:
                  max_count: 15
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.`target_column` > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.`target_column` > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
          {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                  {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
          FROM(
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
          FROM(
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
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.[target_column] > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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

## **daily value above max value count**  
  
**Check description**  
The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_value_above_max_value_count|monitoring|daily|[value_above_max_value_count](../../../../reference/sensors/column/numeric-column-sensors/#value-above-max-value-count)|[max_count](../../../../reference/rules/Comparison/#max-count)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_value_above_max_value_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_value_above_max_value_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_value_above_max_value_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_value_above_max_value_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_value_above_max_value_count
```
**Check structure (Yaml)**
```yaml
      monitoring_checks:
        daily:
          numeric:
            daily_value_above_max_value_count:
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 15
```
**Sample configuration (Yaml)**  
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
          numeric:
            daily_value_above_max_value_count:
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 15
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
      {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
              {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
      FROM(
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period,
        time_period_utc
      FROM(
          SELECT
                     original_table.*,
        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                     FROM "<target_schema>"."<target_table>" original_table
           ) analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
        TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.[target_column] > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_value_above_max_value_count:
                  warning:
                    max_count: 0
                  error:
                    max_count: 10
                  fatal:
                    max_count: 15
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.`target_column` > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.`target_column` > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
          {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                  {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
          FROM(
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
          FROM(
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
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.[target_column] > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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

## **monthly value above max value count**  
  
**Check description**  
The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_value_above_max_value_count|monitoring|monthly|[value_above_max_value_count](../../../../reference/sensors/column/numeric-column-sensors/#value-above-max-value-count)|[max_count](../../../../reference/rules/Comparison/#max-count)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_value_above_max_value_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_value_above_max_value_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_value_above_max_value_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_value_above_max_value_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_value_above_max_value_count
```
**Check structure (Yaml)**
```yaml
      monitoring_checks:
        monthly:
          numeric:
            monthly_value_above_max_value_count:
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 15
```
**Sample configuration (Yaml)**  
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
          numeric:
            monthly_value_above_max_value_count:
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 15
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
      {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
              {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
      FROM(
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period,
        time_period_utc
      FROM(
          SELECT
                     original_table.*,
        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                     FROM "<target_schema>"."<target_table>" original_table
           ) analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.[target_column] > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_value_above_max_value_count:
                  warning:
                    max_count: 0
                  error:
                    max_count: 10
                  fatal:
                    max_count: 15
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.`target_column` > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.`target_column` > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
          {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                  {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
          FROM(
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
          FROM(
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
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.[target_column] > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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

## **daily partition value above max value count**  
  
**Check description**  
The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_value_above_max_value_count|partitioned|daily|[value_above_max_value_count](../../../../reference/sensors/column/numeric-column-sensors/#value-above-max-value-count)|[max_count](../../../../reference/rules/Comparison/#max-count)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_partition_value_above_max_value_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_partition_value_above_max_value_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_partition_value_above_max_value_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_partition_value_above_max_value_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_value_above_max_value_count
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_value_above_max_value_count:
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 15
```
**Sample configuration (Yaml)**  
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
          numeric:
            daily_partition_value_above_max_value_count:
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 15
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
      {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
              {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
      FROM(
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period,
        time_period_utc
      FROM(
          SELECT
                     original_table.*,
        TRUNC(CAST(original_table."date_column" AS DATE)) AS time_period,
        CAST(TRUNC(CAST(original_table."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                     FROM "<target_schema>"."<target_table>" original_table
           ) analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(analyzed_table."date_column" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.[target_column] > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(analyzed_table.[date_column] AS date) AS time_period,
        CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
    ORDER BY CAST(analyzed_table.[date_column] AS date)
    
        
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_partition_value_above_max_value_count:
                  warning:
                    max_count: 0
                  error:
                    max_count: 10
                  fatal:
                    max_count: 15
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.`target_column` > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.`target_column` > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
          {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                  {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
          FROM(
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
          FROM(
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
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.[target_column] > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            CAST(analyzed_table.[date_column] AS date) AS time_period,
            CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state], CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
        ORDER BY level_1, level_2CAST(analyzed_table.[date_column] AS date)
        
            
        ```
    






___

## **monthly partition value above max value count**  
  
**Check description**  
The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_value_above_max_value_count|partitioned|monthly|[value_above_max_value_count](../../../../reference/sensors/column/numeric-column-sensors/#value-above-max-value-count)|[max_count](../../../../reference/rules/Comparison/#max-count)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_partition_value_above_max_value_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_partition_value_above_max_value_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_partition_value_above_max_value_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_partition_value_above_max_value_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_value_above_max_value_count
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_value_above_max_value_count:
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 15
```
**Sample configuration (Yaml)**  
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
          numeric:
            monthly_partition_value_above_max_value_count:
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 15
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
      {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
              {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
      FROM(
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period,
        time_period_utc
      FROM(
          SELECT
                     original_table.*,
        TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS time_period,
        CAST(TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                     FROM "<target_schema>"."<target_table>" original_table
           ) analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.[target_column] > 0 THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
    ORDER BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
    
        
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_partition_value_above_max_value_count:
                  warning:
                    max_count: 0
                  error:
                    max_count: 10
                  fatal:
                    max_count: 15
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.`target_column` > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.`target_column` > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
          {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                  {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
          FROM(
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
          FROM(
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
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0
                        THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table."target_column" > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
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
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
                    ELSE 0
                END
            ) AS actual_value
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
            SUM(
                CASE
                    WHEN analyzed_table.[target_column] > 0 THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state], DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
        ORDER BY level_1, level_2DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
        
            
        ```
    






___
