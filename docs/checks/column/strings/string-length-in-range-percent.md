**string length in range percent** checks  

**Description**  
Column check that calculates the percentage of strings with a length below the indicated by the user length in a column.

___

## **profile string length in range percent**  
  
**Check description**  
The check counts the percentage of those strings with length in the range provided by the user in the column.   
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_string_length_in_range_percent|profiling| |Reasonableness|[string_length_in_range_percent](../../../../reference/sensors/column/strings-column-sensors/#string-length-in-range-percent)|[min_percent](../../../../reference/rules/Comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=profile_string_length_in_range_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=profile_string_length_in_range_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=profile_string_length_in_range_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=profile_string_length_in_range_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=profile_string_length_in_range_percent
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        strings:
          profile_string_length_in_range_percent:
            parameters:
              min_length: 5
              max_length: 10
            warning:
              min_percent: 100.0
            error:
              min_percent: 99.0
            fatal:
              min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-24"
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
        strings:
          profile_string_length_in_range_percent:
            parameters:
              min_length: 5
              max_length: 10
            warning:
              min_percent: 100.0
            error:
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
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table.`target_column`)
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table.`target_column`)
        END AS actual_value,
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
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        time_period,
        time_period_utc
    FROM(
        SELECT
            original_table.*,
        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.[target_column] ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT_BIG(analyzed_table.[target_column])
        END AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 42-47"
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
            strings:
              profile_string_length_in_range_percent:
                parameters:
                  min_length: 5
                  max_length: 10
                warning:
                  min_percent: 100.0
                error:
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
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
            {%- if (lib.target_column_data_type == 'STRING') -%}
                {{ lib.render_target_column(analyzed_table_to_render) }}
            {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INT64') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- else -%}
                {{ lib.render_target_column(analyzed_table_to_render) }}
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table.`target_column`)
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table.`target_column`)
            END AS actual_value,
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
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
            END AS actual_value,
        
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
            FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.[target_column] ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT_BIG(analyzed_table.[target_column])
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

## **daily string length in range percent**  
  
**Check description**  
The check counts the percentage of those strings with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_string_length_in_range_percent|monitoring|daily|Reasonableness|[string_length_in_range_percent](../../../../reference/sensors/column/strings-column-sensors/#string-length-in-range-percent)|[min_percent](../../../../reference/rules/Comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_string_length_in_range_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_string_length_in_range_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_string_length_in_range_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_string_length_in_range_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_string_length_in_range_percent
```
**Check structure (Yaml)**
```yaml
      monitoring_checks:
        daily:
          strings:
            daily_string_length_in_range_percent:
              parameters:
                min_length: 5
                max_length: 10
              warning:
                min_percent: 100.0
              error:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-25"
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
          strings:
            daily_string_length_in_range_percent:
              parameters:
                min_length: 5
                max_length: 10
              warning:
                min_percent: 100.0
              error:
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
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table.`target_column`)
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table.`target_column`)
        END AS actual_value,
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
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        time_period,
        time_period_utc
    FROM(
        SELECT
            original_table.*,
        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.[target_column] ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT_BIG(analyzed_table.[target_column])
        END AS actual_value,
        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 43-48"
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
              strings:
                daily_string_length_in_range_percent:
                  parameters:
                    min_length: 5
                    max_length: 10
                  warning:
                    min_percent: 100.0
                  error:
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
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
            {%- if (lib.target_column_data_type == 'STRING') -%}
                {{ lib.render_target_column(analyzed_table_to_render) }}
            {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INT64') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- else -%}
                {{ lib.render_target_column(analyzed_table_to_render) }}
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table.`target_column`)
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table.`target_column`)
            END AS actual_value,
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
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
            END AS actual_value,
        
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
            FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.[target_column] ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT_BIG(analyzed_table.[target_column])
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

## **monthly string length in range percent**  
  
**Check description**  
The check counts percentage of those strings with length in the range provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_string_length_in_range_percent|monitoring|monthly|Reasonableness|[string_length_in_range_percent](../../../../reference/sensors/column/strings-column-sensors/#string-length-in-range-percent)|[min_percent](../../../../reference/rules/Comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_string_length_in_range_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_string_length_in_range_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_string_length_in_range_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_string_length_in_range_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_string_length_in_range_percent
```
**Check structure (Yaml)**
```yaml
      monitoring_checks:
        monthly:
          strings:
            monthly_string_length_in_range_percent:
              parameters:
                min_length: 5
                max_length: 10
              warning:
                min_percent: 100.0
              error:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-25"
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
          strings:
            monthly_string_length_in_range_percent:
              parameters:
                min_length: 5
                max_length: 10
              warning:
                min_percent: 100.0
              error:
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
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table.`target_column`)
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table.`target_column`)
        END AS actual_value,
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
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        time_period,
        time_period_utc
    FROM(
        SELECT
            original_table.*,
        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.[target_column] ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT_BIG(analyzed_table.[target_column])
        END AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 43-48"
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
              strings:
                monthly_string_length_in_range_percent:
                  parameters:
                    min_length: 5
                    max_length: 10
                  warning:
                    min_percent: 100.0
                  error:
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
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
            {%- if (lib.target_column_data_type == 'STRING') -%}
                {{ lib.render_target_column(analyzed_table_to_render) }}
            {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INT64') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- else -%}
                {{ lib.render_target_column(analyzed_table_to_render) }}
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table.`target_column`)
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table.`target_column`)
            END AS actual_value,
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
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
            END AS actual_value,
        
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
            FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.[target_column] ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT_BIG(analyzed_table.[target_column])
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

## **daily partition string length in range percent**  
  
**Check description**  
The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_partition_string_length_in_range_percent|partitioned|daily|Reasonableness|[string_length_in_range_percent](../../../../reference/sensors/column/strings-column-sensors/#string-length-in-range-percent)|[min_percent](../../../../reference/rules/Comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_partition_string_length_in_range_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_partition_string_length_in_range_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_partition_string_length_in_range_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_partition_string_length_in_range_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_string_length_in_range_percent
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          strings:
            daily_partition_string_length_in_range_percent:
              parameters:
                min_length: 5
                max_length: 10
              warning:
                min_percent: 100.0
              error:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-26"
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
          strings:
            daily_partition_string_length_in_range_percent:
              parameters:
                min_length: 5
                max_length: 10
              warning:
                min_percent: 100.0
              error:
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
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table.`target_column`)
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table.`target_column`)
        END AS actual_value,
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
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        time_period,
        time_period_utc
    FROM(
        SELECT
            original_table.*,
        TRUNC(CAST(original_table."date_column" AS DATE)) AS time_period,
        CAST(TRUNC(CAST(original_table."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.[target_column] ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT_BIG(analyzed_table.[target_column])
        END AS actual_value,
        CAST(analyzed_table.[date_column] AS date) AS time_period,
        CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
    ORDER BY CAST(analyzed_table.[date_column] AS date)
    
        
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-22 49-54"
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
              strings:
                daily_partition_string_length_in_range_percent:
                  parameters:
                    min_length: 5
                    max_length: 10
                  warning:
                    min_percent: 100.0
                  error:
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
        
        {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
            {%- if (lib.target_column_data_type == 'STRING') -%}
                {{ lib.render_target_column(analyzed_table_to_render) }}
            {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INT64') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- else -%}
                {{ lib.render_target_column(analyzed_table_to_render) }}
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table.`target_column`)
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table.`target_column`)
            END AS actual_value,
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
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
            END AS actual_value,
        
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
            FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.[target_column] ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT_BIG(analyzed_table.[target_column])
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

## **monthly partition string length in range percent**  
  
**Check description**  
The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_partition_string_length_in_range_percent|partitioned|monthly|Reasonableness|[string_length_in_range_percent](../../../../reference/sensors/column/strings-column-sensors/#string-length-in-range-percent)|[min_percent](../../../../reference/rules/Comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_partition_string_length_in_range_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_partition_string_length_in_range_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_partition_string_length_in_range_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_partition_string_length_in_range_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_string_length_in_range_percent
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          strings:
            monthly_partition_string_length_in_range_percent:
              parameters:
                min_length: 5
                max_length: 10
              warning:
                min_percent: 100.0
              error:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-26"
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
          strings:
            monthly_partition_string_length_in_range_percent:
              parameters:
                min_length: 5
                max_length: 10
              warning:
                min_percent: 100.0
              error:
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
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table.`target_column`)
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table.`target_column`)
        END AS actual_value,
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
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        time_period,
        time_period_utc
    FROM(
        SELECT
            original_table.*,
        TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS time_period,
        CAST(TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT(analyzed_table."target_column")
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
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( analyzed_table.[target_column] ) BETWEEN 5 AND 10 THEN 1
                        ELSE 0
                    END
            ) / COUNT_BIG(analyzed_table.[target_column])
        END AS actual_value,
        DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
    ORDER BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
    
        
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-22 49-54"
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
              strings:
                monthly_partition_string_length_in_range_percent:
                  parameters:
                    min_length: 5
                    max_length: 10
                  warning:
                    min_percent: 100.0
                  error:
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
        
        {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
            {%- if (lib.target_column_data_type == 'STRING') -%}
                {{ lib.render_target_column(analyzed_table_to_render) }}
            {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INT64') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                        SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
            {%- else -%}
                {{ lib.render_target_column(analyzed_table_to_render) }}
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table.`target_column`)
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.`target_column` ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table.`target_column`)
            END AS actual_value,
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
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            CASE
                WHEN COUNT(analyzed_table."target_column") = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
            END AS actual_value,
        
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
            FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table."target_column" ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT(analyzed_table."target_column")
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
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                            ELSE 0
                        END
                ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
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
                ELSE
                    100.0 * SUM(
                        CASE
                            WHEN LENGTH( analyzed_table.[target_column] ) BETWEEN 5 AND 10 THEN 1
                            ELSE 0
                        END
                ) / COUNT_BIG(analyzed_table.[target_column])
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
