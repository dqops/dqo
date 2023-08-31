**expected numbers in use count** checks  

**Description**  
Column level check that counts unique values in a numeric column and counts how many values out of a list of expected numeric values were found in the column.
 The check raises a data quality issue when the threshold of maximum number of missing values was exceeded (too many expected values were not found in the column).
 This check is useful for analysing columns with a low number of unique values, such as status codes, to detect that all status codes are in use in any row.

___

## **profile expected numbers in use count**  
  
**Check description**  
Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|profile_expected_numbers_in_use_count|profiling| |[expected_numbers_in_use_count](../../../../reference/sensors/Column/numeric-column-sensors/#expected-numbers-in-use-count)|[max_missing](../../../../reference/rules/Comparison/#max-missing)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=profile_expected_numbers_in_use_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=profile_expected_numbers_in_use_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=profile_expected_numbers_in_use_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=profile_expected_numbers_in_use_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=profile_expected_numbers_in_use_count
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        numeric:
          profile_expected_numbers_in_use_count:
            parameters:
              expected_values:
              - 2
              - 3
            warning:
              max_missing: 1
            error:
              max_missing: 1
            fatal:
              max_missing: 2
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-25"
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
        numeric:
          profile_expected_numbers_in_use_count:
            parameters:
              expected_values:
              - 2
              - 3
            warning:
              max_missing: 1
            error:
              max_missing: 1
            fatal:
              max_missing: 2
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
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
        COUNT(DISTINCT
            CASE
                WHEN analyzed_table.`target_column` IN (2, 3)
                    THEN analyzed_table.`target_column`
                ELSE NULL
            END
        ) AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table.`target_column` IN (2, 3
    )
                    THEN analyzed_table.`target_column`
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
        COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3)
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        ) AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT_BIG(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN MAX(0)
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT_BIG(*) = 0 THEN MAX(0)
            ELSE COUNT_BIG(DISTINCT
            CASE
                WHEN analyzed_table.[target_column] IN (2, 3
    )
                    THEN analyzed_table.[target_column]
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 43-48"
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
            numeric:
              profile_expected_numbers_in_use_count:
                parameters:
                  expected_values:
                  - 2
                  - 3
                warning:
                  max_missing: 1
                error:
                  max_missing: 1
                fatal:
                  max_missing: 2
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
        
        {%- macro extract_in_list(values_list) -%}
            {{ values_list|join(', ') -}}
        {% endmacro %}
        
        {%- macro actual_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
            0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN (2, 3)
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN (2, 3
        )
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{ values_list|join(', ') -}}
        {% endmacro %}
        
        {%- macro actual_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
            0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3)
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(2) AS expected_value,
        
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT_BIG(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN MAX(0)
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT_BIG(*) = 0 THEN MAX(0)
                ELSE COUNT_BIG(DISTINCT
                CASE
                    WHEN analyzed_table.[target_column] IN (2, 3
        )
                        THEN analyzed_table.[target_column]
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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

## **daily expected numbers in use count**  
  
**Check description**  
Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_expected_numbers_in_use_count|monitoring|daily|[expected_numbers_in_use_count](../../../../reference/sensors/Column/numeric-column-sensors/#expected-numbers-in-use-count)|[max_missing](../../../../reference/rules/Comparison/#max-missing)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_expected_numbers_in_use_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_expected_numbers_in_use_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_expected_numbers_in_use_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_expected_numbers_in_use_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_expected_numbers_in_use_count
```
**Check structure (Yaml)**
```yaml
      monitoring_checks:
        daily:
          numeric:
            daily_expected_numbers_in_use_count:
              parameters:
                expected_values:
                - 2
                - 3
              warning:
                max_missing: 1
              error:
                max_missing: 1
              fatal:
                max_missing: 2
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-26"
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
      monitoring_checks:
        daily:
          numeric:
            daily_expected_numbers_in_use_count:
              parameters:
                expected_values:
                - 2
                - 3
              warning:
                max_missing: 1
              error:
                max_missing: 1
              fatal:
                max_missing: 2
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
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
        COUNT(DISTINCT
            CASE
                WHEN analyzed_table.`target_column` IN (2, 3)
                    THEN analyzed_table.`target_column`
                ELSE NULL
            END
        ) AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table.`target_column` IN (2, 3
    )
                    THEN analyzed_table.`target_column`
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
        COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3)
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        ) AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT_BIG(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN MAX(0)
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT_BIG(*) = 0 THEN MAX(0)
            ELSE COUNT_BIG(DISTINCT
            CASE
                WHEN analyzed_table.[target_column] IN (2, 3
    )
                    THEN analyzed_table.[target_column]
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 44-49"
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
          monitoring_checks:
            daily:
              numeric:
                daily_expected_numbers_in_use_count:
                  parameters:
                    expected_values:
                    - 2
                    - 3
                  warning:
                    max_missing: 1
                  error:
                    max_missing: 1
                  fatal:
                    max_missing: 2
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
        
        {%- macro extract_in_list(values_list) -%}
            {{ values_list|join(', ') -}}
        {% endmacro %}
        
        {%- macro actual_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
            0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN (2, 3)
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN (2, 3
        )
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{ values_list|join(', ') -}}
        {% endmacro %}
        
        {%- macro actual_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
            0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3)
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(2) AS expected_value,
        
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT_BIG(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN MAX(0)
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT_BIG(*) = 0 THEN MAX(0)
                ELSE COUNT_BIG(DISTINCT
                CASE
                    WHEN analyzed_table.[target_column] IN (2, 3
        )
                        THEN analyzed_table.[target_column]
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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

## **monthly expected numbers in use count**  
  
**Check description**  
Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_expected_numbers_in_use_count|monitoring|monthly|[expected_numbers_in_use_count](../../../../reference/sensors/Column/numeric-column-sensors/#expected-numbers-in-use-count)|[max_missing](../../../../reference/rules/Comparison/#max-missing)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_expected_numbers_in_use_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_expected_numbers_in_use_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_expected_numbers_in_use_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_expected_numbers_in_use_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_expected_numbers_in_use_count
```
**Check structure (Yaml)**
```yaml
      monitoring_checks:
        monthly:
          numeric:
            monthly_expected_numbers_in_use_count:
              parameters:
                expected_values:
                - 2
                - 3
              warning:
                max_missing: 1
              error:
                max_missing: 1
              fatal:
                max_missing: 2
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-26"
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
      monitoring_checks:
        monthly:
          numeric:
            monthly_expected_numbers_in_use_count:
              parameters:
                expected_values:
                - 2
                - 3
              warning:
                max_missing: 1
              error:
                max_missing: 1
              fatal:
                max_missing: 2
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
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
        COUNT(DISTINCT
            CASE
                WHEN analyzed_table.`target_column` IN (2, 3)
                    THEN analyzed_table.`target_column`
                ELSE NULL
            END
        ) AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table.`target_column` IN (2, 3
    )
                    THEN analyzed_table.`target_column`
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
        COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3)
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        ) AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT_BIG(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN MAX(0)
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT_BIG(*) = 0 THEN MAX(0)
            ELSE COUNT_BIG(DISTINCT
            CASE
                WHEN analyzed_table.[target_column] IN (2, 3
    )
                    THEN analyzed_table.[target_column]
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 44-49"
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
          monitoring_checks:
            monthly:
              numeric:
                monthly_expected_numbers_in_use_count:
                  parameters:
                    expected_values:
                    - 2
                    - 3
                  warning:
                    max_missing: 1
                  error:
                    max_missing: 1
                  fatal:
                    max_missing: 2
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
        
        {%- macro extract_in_list(values_list) -%}
            {{ values_list|join(', ') -}}
        {% endmacro %}
        
        {%- macro actual_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
            0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN (2, 3)
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN (2, 3
        )
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{ values_list|join(', ') -}}
        {% endmacro %}
        
        {%- macro actual_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
            0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3)
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(2) AS expected_value,
        
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT_BIG(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN MAX(0)
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT_BIG(*) = 0 THEN MAX(0)
                ELSE COUNT_BIG(DISTINCT
                CASE
                    WHEN analyzed_table.[target_column] IN (2, 3
        )
                        THEN analyzed_table.[target_column]
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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

## **daily partition expected numbers in use count**  
  
**Check description**  
Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_expected_numbers_in_use_count|partitioned|daily|[expected_numbers_in_use_count](../../../../reference/sensors/Column/numeric-column-sensors/#expected-numbers-in-use-count)|[max_missing](../../../../reference/rules/Comparison/#max-missing)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_partition_expected_numbers_in_use_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_partition_expected_numbers_in_use_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_partition_expected_numbers_in_use_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_partition_expected_numbers_in_use_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_expected_numbers_in_use_count
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_expected_numbers_in_use_count:
              parameters:
                expected_values:
                - 2
                - 3
              warning:
                max_missing: 1
              error:
                max_missing: 1
              fatal:
                max_missing: 2
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-27"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
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
            daily_partition_expected_numbers_in_use_count:
              parameters:
                expected_values:
                - 2
                - 3
              warning:
                max_missing: 1
              error:
                max_missing: 1
              fatal:
                max_missing: 2
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
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
        COUNT(DISTINCT
            CASE
                WHEN analyzed_table.`target_column` IN (2, 3)
                    THEN analyzed_table.`target_column`
                ELSE NULL
            END
        ) AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table.`target_column` IN (2, 3
    )
                    THEN analyzed_table.`target_column`
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
        COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3)
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        ) AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT_BIG(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN MAX(0)
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT_BIG(*) = 0 THEN MAX(0)
            ELSE COUNT_BIG(DISTINCT
            CASE
                WHEN analyzed_table.[target_column] IN (2, 3
    )
                    THEN analyzed_table.[target_column]
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
        CAST(analyzed_table.[date_column] AS date) AS time_period,
        CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
    ORDER BY CAST(analyzed_table.[date_column] AS date)
    
        
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-22 50-55"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
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
                daily_partition_expected_numbers_in_use_count:
                  parameters:
                    expected_values:
                    - 2
                    - 3
                  warning:
                    max_missing: 1
                  error:
                    max_missing: 1
                  fatal:
                    max_missing: 2
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
        
        {%- macro extract_in_list(values_list) -%}
            {{ values_list|join(', ') -}}
        {% endmacro %}
        
        {%- macro actual_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
            0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN (2, 3)
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN (2, 3
        )
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{ values_list|join(', ') -}}
        {% endmacro %}
        
        {%- macro actual_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
            0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3)
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(2) AS expected_value,
        
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT_BIG(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN MAX(0)
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT_BIG(*) = 0 THEN MAX(0)
                ELSE COUNT_BIG(DISTINCT
                CASE
                    WHEN analyzed_table.[target_column] IN (2, 3
        )
                        THEN analyzed_table.[target_column]
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            CAST(analyzed_table.[date_column] AS date) AS time_period,
            CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state], CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
        ORDER BY level_1, level_2CAST(analyzed_table.[date_column] AS date)
        
            
        ```
    






___

## **monthly partition expected numbers in use count**  
  
**Check description**  
Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_expected_numbers_in_use_count|partitioned|monthly|[expected_numbers_in_use_count](../../../../reference/sensors/Column/numeric-column-sensors/#expected-numbers-in-use-count)|[max_missing](../../../../reference/rules/Comparison/#max-missing)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_partition_expected_numbers_in_use_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_partition_expected_numbers_in_use_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_partition_expected_numbers_in_use_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_partition_expected_numbers_in_use_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_expected_numbers_in_use_count
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_expected_numbers_in_use_count:
              parameters:
                expected_values:
                - 2
                - 3
              warning:
                max_missing: 1
              error:
                max_missing: 1
              fatal:
                max_missing: 2
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-27"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
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
            monthly_partition_expected_numbers_in_use_count:
              parameters:
                expected_values:
                - 2
                - 3
              warning:
                max_missing: 1
              error:
                max_missing: 1
              fatal:
                max_missing: 2
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
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
        COUNT(DISTINCT
            CASE
                WHEN analyzed_table.`target_column` IN (2, 3)
                    THEN analyzed_table.`target_column`
                ELSE NULL
            END
        ) AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table.`target_column` IN (2, 3
    )
                    THEN analyzed_table.`target_column`
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
        COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3)
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        ) AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT(*) = 0 THEN NULL
            ELSE COUNT(DISTINCT
            CASE
                WHEN analyzed_table."target_column" IN (2, 3
    )
                    THEN analyzed_table."target_column"
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
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
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            0
        {%- else -%}
        COUNT_BIG(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN MAX(0)
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
            WHEN COUNT_BIG(*) = 0 THEN MAX(0)
            ELSE COUNT_BIG(DISTINCT
            CASE
                WHEN analyzed_table.[target_column] IN (2, 3
    )
                    THEN analyzed_table.[target_column]
                ELSE NULL
            END
        )
        END AS actual_value,
        MAX(2) AS expected_value,
        DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
    ORDER BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
    
        
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-22 50-55"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
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
                monthly_partition_expected_numbers_in_use_count:
                  parameters:
                    expected_values:
                    - 2
                    - 3
                  warning:
                    max_missing: 1
                  error:
                    max_missing: 1
                  fatal:
                    max_missing: 2
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
        
        {%- macro extract_in_list(values_list) -%}
            {{ values_list|join(', ') -}}
        {% endmacro %}
        
        {%- macro actual_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
            0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN (2, 3)
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN (2, 3
        )
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{ values_list|join(', ') -}}
        {% endmacro %}
        
        {%- macro actual_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
            0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3)
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(2) AS expected_value,
        
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN (2, 3
        )
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
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
        
        {%- macro extract_in_list(values_list) -%}
            {{values_list|join(', ')}}
        {% endmacro %}
        
        {%- macro render_else() -%}
            {%- if parameters.expected_values|length == 0 -%}
                0
            {%- else -%}
            COUNT_BIG(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN MAX(0)
                ELSE {{render_else()}}
            END AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value
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
                WHEN COUNT_BIG(*) = 0 THEN MAX(0)
                ELSE COUNT_BIG(DISTINCT
                CASE
                    WHEN analyzed_table.[target_column] IN (2, 3
        )
                        THEN analyzed_table.[target_column]
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(2) AS expected_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state], DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
        ORDER BY level_1, level_2DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
        
            
        ```
    






___
