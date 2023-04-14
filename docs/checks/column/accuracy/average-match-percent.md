**average match percent** checks  

**Description**  
Column level check that ensures that there are no more than a maximum percentage of difference of average of a table column and of an average of another table column.

___

## **average match percent**  
  
**Check description**  
Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|average_match_percent|profiling| |[average_match_percent](../../../../reference/sensors/column/accuracy-column-sensors/#average-match-percent)|[diff_percent](../../../../reference/rules/comparison/#diff-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=average_match_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=average_match_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=average_match_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=average_match_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=average_match_percent
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        accuracy:
          average_match_percent:
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
          average_match_percent:
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
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        (SELECT
            AVG(referenced_table.`customer_id`)
        FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
        ) AS expected_value,
        AVG(analyzed_table.`target_column`) AS actual_value
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
    {%- endmacro %}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    
    
    SELECT
        (SELECT
            AVG(referenced_table."customer_id")
        FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        AVG(analyzed_table."target_column") AS actual_value
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
        {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
    {%- endmacro %}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    
    
    SELECT
        (SELECT
            AVG(referenced_table."customer_id")
        FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        AVG(analyzed_table."target_column") AS actual_value
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
        {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
    {%- endmacro %}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    
    
    SELECT
        (SELECT
            AVG(referenced_table."customer_id")
        FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        AVG(analyzed_table."target_column") AS actual_value
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
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
              average_match_percent:
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
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
            {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            (SELECT
                AVG(referenced_table.`customer_id`)
            FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
            ) AS expected_value,
            AVG(analyzed_table.`target_column`) AS actual_value
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
            {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
        {%- endmacro %}
        
        SELECT
            (SELECT
                AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        
        
        SELECT
            (SELECT
                AVG(referenced_table."customer_id")
            FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            AVG(analyzed_table."target_column") AS actual_value
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
            {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
        {%- endmacro %}
        
        SELECT
            (SELECT
                AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        
        
        SELECT
            (SELECT
                AVG(referenced_table."customer_id")
            FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            AVG(analyzed_table."target_column") AS actual_value
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
            {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
        {%- endmacro %}
        
        SELECT
            (SELECT
                AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        
        
        SELECT
            (SELECT
                AVG(referenced_table."customer_id")
            FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            AVG(analyzed_table."target_column") AS actual_value
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    





___

## **daily average match percent**  
  
**Check description**  
Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_average_match_percent|recurring|daily|[average_match_percent](../../../../reference/sensors/column/accuracy-column-sensors/#average-match-percent)|[diff_percent](../../../../reference/rules/comparison/#diff-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_average_match_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_average_match_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_average_match_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_average_match_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_average_match_percent
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          accuracy:
            daily_average_match_percent:
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
            daily_average_match_percent:
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
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        (SELECT
            AVG(referenced_table.`customer_id`)
        FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
        ) AS expected_value,
        AVG(analyzed_table.`target_column`) AS actual_value
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
    {%- endmacro %}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    
    
    SELECT
        (SELECT
            AVG(referenced_table."customer_id")
        FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        AVG(analyzed_table."target_column") AS actual_value
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
        {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
    {%- endmacro %}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    
    
    SELECT
        (SELECT
            AVG(referenced_table."customer_id")
        FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        AVG(analyzed_table."target_column") AS actual_value
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
        {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
    {%- endmacro %}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    
    
    SELECT
        (SELECT
            AVG(referenced_table."customer_id")
        FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        AVG(analyzed_table."target_column") AS actual_value
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
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
                daily_average_match_percent:
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
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
            {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            (SELECT
                AVG(referenced_table.`customer_id`)
            FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
            ) AS expected_value,
            AVG(analyzed_table.`target_column`) AS actual_value
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
            {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
        {%- endmacro %}
        
        SELECT
            (SELECT
                AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        
        
        SELECT
            (SELECT
                AVG(referenced_table."customer_id")
            FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            AVG(analyzed_table."target_column") AS actual_value
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
            {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
        {%- endmacro %}
        
        SELECT
            (SELECT
                AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        
        
        SELECT
            (SELECT
                AVG(referenced_table."customer_id")
            FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            AVG(analyzed_table."target_column") AS actual_value
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
            {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
        {%- endmacro %}
        
        SELECT
            (SELECT
                AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        
        
        SELECT
            (SELECT
                AVG(referenced_table."customer_id")
            FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            AVG(analyzed_table."target_column") AS actual_value
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    





___

## **monthly average match percent**  
  
**Check description**  
Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_average_match_percent|recurring|monthly|[average_match_percent](../../../../reference/sensors/column/accuracy-column-sensors/#average-match-percent)|[diff_percent](../../../../reference/rules/comparison/#diff-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_average_match_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_average_match_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_average_match_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_average_match_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_average_match_percent
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          accuracy:
            monthly_average_match_percent:
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
            monthly_average_match_percent:
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
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        (SELECT
            AVG(referenced_table.`customer_id`)
        FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
        ) AS expected_value,
        AVG(analyzed_table.`target_column`) AS actual_value
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
    {%- endmacro %}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    
    
    SELECT
        (SELECT
            AVG(referenced_table."customer_id")
        FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        AVG(analyzed_table."target_column") AS actual_value
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
        {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
    {%- endmacro %}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    
    
    SELECT
        (SELECT
            AVG(referenced_table."customer_id")
        FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        AVG(analyzed_table."target_column") AS actual_value
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
        {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
    {%- endmacro %}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    
    
    SELECT
        (SELECT
            AVG(referenced_table."customer_id")
        FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
        ) AS expected_value,
        AVG(analyzed_table."target_column") AS actual_value
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
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
                monthly_average_match_percent:
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
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
            {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
        {%- endmacro -%}
        
        SELECT
            (SELECT
                AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            (SELECT
                AVG(referenced_table.`customer_id`)
            FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
            ) AS expected_value,
            AVG(analyzed_table.`target_column`) AS actual_value
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
            {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
        {%- endmacro %}
        
        SELECT
            (SELECT
                AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        
        
        SELECT
            (SELECT
                AVG(referenced_table."customer_id")
            FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            AVG(analyzed_table."target_column") AS actual_value
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
            {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
        {%- endmacro %}
        
        SELECT
            (SELECT
                AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        
        
        SELECT
            (SELECT
                AVG(referenced_table."customer_id")
            FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            AVG(analyzed_table."target_column") AS actual_value
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro render_referenced_table(referenced_table) -%}
            {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(target_table.schema_name) }}.{{ lib.quote_identifier(referenced_table) }}
        {%- endmacro %}
        
        SELECT
            (SELECT
                AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
            FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
            ) AS expected_value,
            AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        
        
        SELECT
            (SELECT
                AVG(referenced_table."customer_id")
            FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
            ) AS expected_value,
            AVG(analyzed_table."target_column") AS actual_value
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        ```
    





___
