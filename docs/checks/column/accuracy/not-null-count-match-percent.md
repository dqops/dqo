**not null count match percent** checks  

**Description**  
Column level check that ensures that there are no more than a maximum percentage of difference of the row count of a tested table&#x27;s column (counting the not null values) and of an row count of another (reference) table, also counting all rows with not null values.

___

## **not null count match percent**  
  
**Check description**  
Verifies that the percentage of difference in row count of a column in a table and row count of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|not_null_count_match_percent|profiling| |[not_null_count_match_percent](../../../../reference/sensors/column/accuracy-column-sensors/#not-null-count-match-percent)|[diff_percent](../../../../reference/rules/comparison/#diff-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=not_null_count_match_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=not_null_count_match_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=not_null_count_match_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=not_null_count_match_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=not_null_count_match_percent
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        accuracy:
          not_null_count_match_percent:
            parameters:
              referenced_table: dim_customer
              referenced_column: customer_id
            warning:
              max_diff_percent: 1.0
            error:
              max_diff_percent: 2.0
            fatal:
              max_diff_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-24"
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
        accuracy:
          not_null_count_match_percent:
            parameters:
              referenced_table: dim_customer
              referenced_column: customer_id
            warning:
              max_diff_percent: 1.0
            error:
              max_diff_percent: 2.0
            fatal:
              max_diff_percent: 5.0
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
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table.`customer_id`)
        FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table.`target_column`) AS actual_value
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table."customer_id")
        FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table."target_column") AS actual_value
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table."customer_id")
        FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table."target_column") AS actual_value
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table."customer_id")
        FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table."target_column") AS actual_value
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table.[customer_id])
        FROM [your_sql_server_database].[<target_schema>].[dim_customer] AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table.[target_column]) AS actual_value
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table.`customer_id`)
        FROM `dim_customer` AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table.`target_column`) AS actual_value
    FROM `<target_table>` AS analyzed_table
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 41-46"
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
            accuracy:
              not_null_count_match_percent:
                parameters:
                  referenced_table: dim_customer
                  referenced_column: customer_id
                warning:
                  max_diff_percent: 1.0
                error:
                  max_diff_percent: 2.0
                fatal:
                  max_diff_percent: 5.0
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
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table.`customer_id`)
            FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table.`target_column`) AS actual_value
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table."customer_id")
            FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table."target_column") AS actual_value
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table."customer_id")
            FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table."target_column") AS actual_value
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table."customer_id")
            FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table."target_column") AS actual_value
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table.[customer_id])
            FROM [your_sql_server_database].[<target_schema>].[dim_customer] AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table.[target_column]) AS actual_value
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table.`customer_id`)
            FROM `dim_customer` AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table.`target_column`) AS actual_value
        FROM `<target_table>` AS analyzed_table
        ```
    





___

## **daily not null count match percent**  
  
**Check description**  
Verifies that the percentage of difference in row count of a column in a table and row count of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_not_null_count_match_percent|recurring|daily|[not_null_count_match_percent](../../../../reference/sensors/column/accuracy-column-sensors/#not-null-count-match-percent)|[diff_percent](../../../../reference/rules/comparison/#diff-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_not_null_count_match_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_not_null_count_match_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_not_null_count_match_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_not_null_count_match_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_not_null_count_match_percent
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          accuracy:
            daily_not_null_count_match_percent:
              parameters:
                referenced_table: dim_customer
                referenced_column: customer_id
              warning:
                max_diff_percent: 1.0
              error:
                max_diff_percent: 2.0
              fatal:
                max_diff_percent: 5.0
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
      recurring_checks:
        daily:
          accuracy:
            daily_not_null_count_match_percent:
              parameters:
                referenced_table: dim_customer
                referenced_column: customer_id
              warning:
                max_diff_percent: 1.0
              error:
                max_diff_percent: 2.0
              fatal:
                max_diff_percent: 5.0
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
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table.`customer_id`)
        FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table.`target_column`) AS actual_value
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table."customer_id")
        FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table."target_column") AS actual_value
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table."customer_id")
        FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table."target_column") AS actual_value
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table."customer_id")
        FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table."target_column") AS actual_value
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table.[customer_id])
        FROM [your_sql_server_database].[<target_schema>].[dim_customer] AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table.[target_column]) AS actual_value
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table.`customer_id`)
        FROM `dim_customer` AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table.`target_column`) AS actual_value
    FROM `<target_table>` AS analyzed_table
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 42-47"
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
              accuracy:
                daily_not_null_count_match_percent:
                  parameters:
                    referenced_table: dim_customer
                    referenced_column: customer_id
                  warning:
                    max_diff_percent: 1.0
                  error:
                    max_diff_percent: 2.0
                  fatal:
                    max_diff_percent: 5.0
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
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table.`customer_id`)
            FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table.`target_column`) AS actual_value
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table."customer_id")
            FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table."target_column") AS actual_value
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table."customer_id")
            FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table."target_column") AS actual_value
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table."customer_id")
            FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table."target_column") AS actual_value
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table.[customer_id])
            FROM [your_sql_server_database].[<target_schema>].[dim_customer] AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table.[target_column]) AS actual_value
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table.`customer_id`)
            FROM `dim_customer` AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table.`target_column`) AS actual_value
        FROM `<target_table>` AS analyzed_table
        ```
    





___

## **monthly not null count match percent**  
  
**Check description**  
Verifies that the percentage of difference in row count of a column in a table and row count of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_not_null_count_match_percent|recurring|monthly|[not_null_count_match_percent](../../../../reference/sensors/column/accuracy-column-sensors/#not-null-count-match-percent)|[diff_percent](../../../../reference/rules/comparison/#diff-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_not_null_count_match_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_not_null_count_match_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_not_null_count_match_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_not_null_count_match_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_not_null_count_match_percent
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          accuracy:
            monthly_not_null_count_match_percent:
              parameters:
                referenced_table: dim_customer
                referenced_column: customer_id
              warning:
                max_diff_percent: 1.0
              error:
                max_diff_percent: 2.0
              fatal:
                max_diff_percent: 5.0
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
      recurring_checks:
        monthly:
          accuracy:
            monthly_not_null_count_match_percent:
              parameters:
                referenced_table: dim_customer
                referenced_column: customer_id
              warning:
                max_diff_percent: 1.0
              error:
                max_diff_percent: 2.0
              fatal:
                max_diff_percent: 5.0
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
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table.`customer_id`)
        FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table.`target_column`) AS actual_value
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table."customer_id")
        FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table."target_column") AS actual_value
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table."customer_id")
        FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table."target_column") AS actual_value
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table."customer_id")
        FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table."target_column") AS actual_value
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table.[customer_id])
        FROM [your_sql_server_database].[<target_schema>].[dim_customer] AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table.[target_column]) AS actual_value
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        (SELECT
            COUNT(referenced_table.`customer_id`)
        FROM `dim_customer` AS referenced_table
        ) AS expected_value,
        COUNT(analyzed_table.`target_column`) AS actual_value
    FROM `<target_table>` AS analyzed_table
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 42-47"
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
              accuracy:
                monthly_not_null_count_match_percent:
                  parameters:
                    referenced_table: dim_customer
                    referenced_column: customer_id
                  warning:
                    max_diff_percent: 1.0
                  error:
                    max_diff_percent: 2.0
                  fatal:
                    max_diff_percent: 5.0
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
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table.`customer_id`)
            FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table.`target_column`) AS actual_value
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table."customer_id")
            FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table."target_column") AS actual_value
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table."customer_id")
            FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table."target_column") AS actual_value
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table."customer_id")
            FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table."target_column") AS actual_value
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table.[customer_id])
            FROM [your_sql_server_database].[<target_schema>].[dim_customer] AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table.[target_column]) AS actual_value
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
        {%- if referenced_table.find(".") < 0 -%}
           {{- lib.quote_identifier(referenced_table) -}}
        {%- else -%}
           {{ referenced_table }}
        {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            (SELECT
                COUNT(referenced_table.`customer_id`)
            FROM `dim_customer` AS referenced_table
            ) AS expected_value,
            COUNT(analyzed_table.`target_column`) AS actual_value
        FROM `<target_table>` AS analyzed_table
        ```
    





___
