**contains usa zipcode percent** checks  

**Description**  
Column check that calculates the percentage of rows that contains USA zip code values in a column.

___

## **contains usa zipcode percent**  
  
**Check description**  
Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|contains_usa_zipcode_percent|profiling| |[contains_usa_zipcode_percent](../../../../reference/sensors/Column/pii-column-sensors/#contains-usa-zipcode-percent)|[max_percent](../../../../reference/rules/Comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=contains_usa_zipcode_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=contains_usa_zipcode_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=contains_usa_zipcode_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=contains_usa_zipcode_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=contains_usa_zipcode_percent
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        pii:
          contains_usa_zipcode_percent:
            warning:
              max_percent: 1.0
            error:
              max_percent: 2.0
            fatal:
              max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-21"
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
        pii:
          contains_usa_zipcode_percent:
            warning:
              max_percent: 1.0
            error:
              max_percent: 2.0
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"[0-9]{5}(?:-[0-9]{4})?"
                    ) THEN 1
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST(analyzed_table.`target_column` AS STRING),
                        r"[0-9]{5}(?:-[0-9]{4})?"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[0-9]{5}(?:-[0-9]{4})?')
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(analyzed_table.`target_column`, '[0-9]{5}(?:-[0-9]{4})?')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        LOCALTIMESTAMP AS time_period_utc
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING(analyzed_table."target_column" from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
                            THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
        TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]%'
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
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.[target_column] LIKE '[0-9][0-9][0-9][0-9][0-9]%'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value,
        SYSDATETIMEOFFSET() AS time_period,
        CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 39-44"
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
              contains_usa_zipcode_percent:
                warning:
                  max_percent: 1.0
                error:
                  max_percent: 2.0
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"[0-9]{5}(?:-[0-9]{4})?"
                        ) THEN 1
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST(analyzed_table.`target_column` AS STRING),
                            r"[0-9]{5}(?:-[0-9]{4})?"
                        ) THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[0-9]{5}(?:-[0-9]{4})?')
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '[0-9]{5}(?:-[0-9]{4})?')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            LOCALTIMESTAMP AS time_period,
            LOCALTIMESTAMP AS time_period_utc
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING(analyzed_table."target_column" from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
                                THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
            TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]%'
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
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.[target_column] LIKE '[0-9][0-9][0-9][0-9][0-9]%'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            SYSDATETIMEOFFSET() AS time_period,
            CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state]
        ORDER BY level_1, level_2
                , 
            
        
            
        ```
    






___

## **daily contains usa zipcode percent**  
  
**Check description**  
Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_contains_usa_zipcode_percent|recurring|daily|[contains_usa_zipcode_percent](../../../../reference/sensors/Column/pii-column-sensors/#contains-usa-zipcode-percent)|[max_percent](../../../../reference/rules/Comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_contains_usa_zipcode_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_contains_usa_zipcode_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_contains_usa_zipcode_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_contains_usa_zipcode_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_contains_usa_zipcode_percent
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          pii:
            daily_contains_usa_zipcode_percent:
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-22"
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
          pii:
            daily_contains_usa_zipcode_percent:
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"[0-9]{5}(?:-[0-9]{4})?"
                    ) THEN 1
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST(analyzed_table.`target_column` AS STRING),
                        r"[0-9]{5}(?:-[0-9]{4})?"
                    ) THEN 1
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
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[0-9]{5}(?:-[0-9]{4})?')
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(analyzed_table.`target_column`, '[0-9]{5}(?:-[0-9]{4})?')
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
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING(analyzed_table."target_column" from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]%'
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
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.[target_column] LIKE '[0-9][0-9][0-9][0-9][0-9]%'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value,
        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 40-45"
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
          recurring_checks:
            daily:
              pii:
                daily_contains_usa_zipcode_percent:
                  warning:
                    max_percent: 1.0
                  error:
                    max_percent: 2.0
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"[0-9]{5}(?:-[0-9]{4})?"
                        ) THEN 1
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST(analyzed_table.`target_column` AS STRING),
                            r"[0-9]{5}(?:-[0-9]{4})?"
                        ) THEN 1
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
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[0-9]{5}(?:-[0-9]{4})?')
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '[0-9]{5}(?:-[0-9]{4})?')
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
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING(analyzed_table."target_column" from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]%'
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
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.[target_column] LIKE '[0-9][0-9][0-9][0-9][0-9]%'
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

## **monthly contains usa zipcode percent**  
  
**Check description**  
Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_contains_usa_zipcode_percent|recurring|monthly|[contains_usa_zipcode_percent](../../../../reference/sensors/Column/pii-column-sensors/#contains-usa-zipcode-percent)|[max_percent](../../../../reference/rules/Comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_contains_usa_zipcode_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_contains_usa_zipcode_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_contains_usa_zipcode_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_contains_usa_zipcode_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_contains_usa_zipcode_percent
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          pii:
            monthly_contains_usa_zipcode_percent:
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-22"
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
          pii:
            monthly_contains_usa_zipcode_percent:
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"[0-9]{5}(?:-[0-9]{4})?"
                    ) THEN 1
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST(analyzed_table.`target_column` AS STRING),
                        r"[0-9]{5}(?:-[0-9]{4})?"
                    ) THEN 1
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
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[0-9]{5}(?:-[0-9]{4})?')
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(analyzed_table.`target_column`, '[0-9]{5}(?:-[0-9]{4})?')
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
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING(analyzed_table."target_column" from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]%'
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
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.[target_column] LIKE '[0-9][0-9][0-9][0-9][0-9]%'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 40-45"
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
          recurring_checks:
            monthly:
              pii:
                monthly_contains_usa_zipcode_percent:
                  warning:
                    max_percent: 1.0
                  error:
                    max_percent: 2.0
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"[0-9]{5}(?:-[0-9]{4})?"
                        ) THEN 1
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST(analyzed_table.`target_column` AS STRING),
                            r"[0-9]{5}(?:-[0-9]{4})?"
                        ) THEN 1
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
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[0-9]{5}(?:-[0-9]{4})?')
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '[0-9]{5}(?:-[0-9]{4})?')
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
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING(analyzed_table."target_column" from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]%'
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
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.[target_column] LIKE '[0-9][0-9][0-9][0-9][0-9]%'
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

## **daily partition contains usa zipcode percent**  
  
**Check description**  
Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_contains_usa_zipcode_percent|partitioned|daily|[contains_usa_zipcode_percent](../../../../reference/sensors/Column/pii-column-sensors/#contains-usa-zipcode-percent)|[max_percent](../../../../reference/rules/Comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_partition_contains_usa_zipcode_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_partition_contains_usa_zipcode_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_partition_contains_usa_zipcode_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_partition_contains_usa_zipcode_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_contains_usa_zipcode_percent
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          pii:
            daily_partition_contains_usa_zipcode_percent:
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-22"
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
          pii:
            daily_partition_contains_usa_zipcode_percent:
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"[0-9]{5}(?:-[0-9]{4})?"
                    ) THEN 1
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST(analyzed_table.`target_column` AS STRING),
                        r"[0-9]{5}(?:-[0-9]{4})?"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table.`` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`` AS DATE)) AS time_period_utc
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[0-9]{5}(?:-[0-9]{4})?')
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(analyzed_table.`target_column`, '[0-9]{5}(?:-[0-9]{4})?')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_FORMAT(analyzed_table.``, '%Y-%m-%d 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.``, '%Y-%m-%d 00:00:00'))) AS time_period_utc
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING(analyzed_table."target_column" from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."" AS date) AS time_period,
        CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."" AS date) AS time_period,
        CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
                            THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]%'
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
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.[target_column] LIKE '[0-9][0-9][0-9][0-9][0-9]%'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value,
        CAST(analyzed_table.[] AS date) AS time_period,
        CAST((CAST(analyzed_table.[] AS date)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY CAST(analyzed_table.[] AS date), CAST(analyzed_table.[] AS date)
    ORDER BY CAST(analyzed_table.[] AS date)
    
        
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 40-45"
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
                daily_partition_contains_usa_zipcode_percent:
                  warning:
                    max_percent: 1.0
                  error:
                    max_percent: 2.0
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"[0-9]{5}(?:-[0-9]{4})?"
                        ) THEN 1
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST(analyzed_table.`target_column` AS STRING),
                            r"[0-9]{5}(?:-[0-9]{4})?"
                        ) THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            CAST(analyzed_table.`` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`` AS DATE)) AS time_period_utc
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[0-9]{5}(?:-[0-9]{4})?')
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '[0-9]{5}(?:-[0-9]{4})?')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(analyzed_table.``, '%Y-%m-%d 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.``, '%Y-%m-%d 00:00:00'))) AS time_period_utc
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING(analyzed_table."target_column" from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
                                THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]%'
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
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.[target_column] LIKE '[0-9][0-9][0-9][0-9][0-9]%'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            CAST(analyzed_table.[] AS date) AS time_period,
            CAST((CAST(analyzed_table.[] AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state], CAST(analyzed_table.[] AS date), CAST(analyzed_table.[] AS date)
        ORDER BY level_1, level_2CAST(analyzed_table.[] AS date)
        
            
        ```
    






___

## **monthly partition contains usa zipcode percent**  
  
**Check description**  
Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_contains_usa_zipcode_percent|partitioned|monthly|[contains_usa_zipcode_percent](../../../../reference/sensors/Column/pii-column-sensors/#contains-usa-zipcode-percent)|[max_percent](../../../../reference/rules/Comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_partition_contains_usa_zipcode_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_partition_contains_usa_zipcode_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_partition_contains_usa_zipcode_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_partition_contains_usa_zipcode_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_contains_usa_zipcode_percent
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          pii:
            monthly_partition_contains_usa_zipcode_percent:
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-22"
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
          pii:
            monthly_partition_contains_usa_zipcode_percent:
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"[0-9]{5}(?:-[0-9]{4})?"
                    ) THEN 1
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST(analyzed_table.`target_column` AS STRING),
                        r"[0-9]{5}(?:-[0-9]{4})?"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH)) AS time_period_utc
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[0-9]{5}(?:-[0-9]{4})?')
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(analyzed_table.`target_column`, '[0-9]{5}(?:-[0-9]{4})?')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_FORMAT(analyzed_table.``, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.``, '%Y-%m-01 00:00:00'))) AS time_period_utc
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING(analyzed_table."target_column" from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
                            THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]%'
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
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.[target_column] LIKE '[0-9][0-9][0-9][0-9][0-9]%'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value,
        DATEFROMPARTS(YEAR(CAST(analyzed_table.[] AS date)), MONTH(CAST(analyzed_table.[] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[] AS date)), MONTH(CAST(analyzed_table.[] AS date)), 1)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[] AS date)), MONTH(CAST(analyzed_table.[] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[]), 0)
    ORDER BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[] AS date)), MONTH(CAST(analyzed_table.[] AS date)), 1)
    
        
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 40-45"
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
                monthly_partition_contains_usa_zipcode_percent:
                  warning:
                    max_percent: 1.0
                  error:
                    max_percent: 2.0
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"[0-9]{5}(?:-[0-9]{4})?"
                        ) THEN 1
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(
                            CAST(analyzed_table.`target_column` AS STRING),
                            r"[0-9]{5}(?:-[0-9]{4})?"
                        ) THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH)) AS time_period_utc
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[0-9]{5}(?:-[0-9]{4})?')
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(analyzed_table.`target_column`, '[0-9]{5}(?:-[0-9]{4})?')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(analyzed_table.``, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.``, '%Y-%m-01 00:00:00'))) AS time_period_utc
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN SUBSTRING(analyzed_table."target_column" from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
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
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
                                THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]%'
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
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.[target_column] LIKE '[0-9][0-9][0-9][0-9][0-9]%'
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            DATEFROMPARTS(YEAR(CAST(analyzed_table.[] AS date)), MONTH(CAST(analyzed_table.[] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[] AS date)), MONTH(CAST(analyzed_table.[] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state], DATEFROMPARTS(YEAR(CAST(analyzed_table.[] AS date)), MONTH(CAST(analyzed_table.[] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[]), 0)
        ORDER BY level_1, level_2DATEFROMPARTS(YEAR(CAST(analyzed_table.[] AS date)), MONTH(CAST(analyzed_table.[] AS date)), 1)
        
            
        ```
    






___
