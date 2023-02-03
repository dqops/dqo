# table

## **standard** table sensors
___

### **row count**
**Full sensor name**
```
table/standard/row_count
```
**Description**  
Tabular sensor that executes a row count query on a table.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "postgresql"
      
    ```
    {% raw %}
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___


## **timeliness** table sensors
___

### **days since most recent event**
**Full sensor name**
```
table/timeliness/days_since_most_recent_event
```
**Description**  
Tabular sensor that runs a query calculating maximum days since the most recent event.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'STRING' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type is not defined -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        <INVALID OR NOT MATCHING DATA TYPE>
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'STRING' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type is not defined -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        <INVALID OR NOT MATCHING DATA TYPE>
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **partition reload lag**
**Full sensor name**
```
table/timeliness/partition_reload_lag
```
**Description**  
Tabular sensor that runs a query calculating maximum difference in days between ingestion timestamp and event timestamp rows.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        MAX(
            TIMESTAMP_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        MAX(
            DATE_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                DAY
            )
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        MAX(
            DATETIME_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'STRING' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'STRING' -%}
        MAX(
            TIMESTAMP_DIFF(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type is not defined or
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type is not defined -%}
        MAX(
            TIMESTAMP_DIFF(
                CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        <INVALID OR NOT MATCHING DATA TYPE>
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        MAX(
            TIMESTAMP_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        MAX(
            DATE_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                DAY
            )
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        MAX(
            DATETIME_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'STRING' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'STRING' -%}
        MAX(
            TIMESTAMP_DIFF(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type is not defined or
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type is not defined -%}
        MAX(
            TIMESTAMP_DIFF(
                CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        <INVALID OR NOT MATCHING DATA TYPE>
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **days since most recent ingestion**
**Full sensor name**
```
table/timeliness/days_since_most_recent_ingestion
```
**Description**  
Tabular sensor that runs a query calculating the time difference in days between the current date and most recent data loading timestamp (staleness).


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'STRING' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type is not defined -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        <INVALID OR NOT MATCHING DATA TYPE>
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'STRING' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type is not defined -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        <INVALID OR NOT MATCHING DATA TYPE>
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **data ingestion delay**
**Full sensor name**
```
table/timeliness/data_ingestion_delay
```
**Description**  
Tabular sensor that runs a query calculating the time difference in days between the most recent transaction timestamp and the most recent data loading timestamp.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        )
        / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        )
        / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'STRING' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'STRING' -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        )
        / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type is not defined or
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type is not defined -%}
        TIMESTAMP_DIFF(
            MAX(
                CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        )
        / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        <INVALID OR NOT MATCHING DATA TYPE>
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }}
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'STRING' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'STRING' -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type is not defined or
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type is not defined -%}
        TIMESTAMP_DIFF(
            MAX(
                CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        <INVALID OR NOT MATCHING DATA TYPE>
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___


## **availability** table sensors
___

### **table availability**
**Full sensor name**
```
table/availability/table_availability
```
**Description**  
Tabular sensor that executes a row count query on a table.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    {% endraw %}
    ```
=== "postgresql"
      
    ```
    {% raw %}
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    {% endraw %}
    ```
___


## **sql** table sensors
___

### **sql condition passed percent**
**Full sensor name**
```
table/sql/sql_condition_passed_percent
```
**Description**  
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string| ||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **sql condition failed count**
**Full sensor name**
```
table/sql/sql_condition_failed_count
```
**Description**  
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string| ||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **sql aggregated expression**
**Full sensor name**
```
table/sql/sql_aggregated_expression
```
**Description**  
Table level sensor that executes a given SQL expression on a table.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_expression|SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or withing a GROUP BY clause for daily partitions and/or data streams. The expression can use {table} placeholder that is replaced with a full table name.|string| ||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **sql condition passed count**
**Full sensor name**
```
table/sql/sql_condition_passed_count
```
**Description**  
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string| ||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **sql condition failed percent**
**Full sensor name**
```
table/sql/sql_condition_failed_percent
```
**Description**  
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string| ||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

