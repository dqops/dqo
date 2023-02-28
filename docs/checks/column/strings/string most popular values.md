**string most popular values** checks  

**Description**  
Column level check that ensures that there are no more than a maximum number of empty strings in a monitored column.

___

## **string most popular values**  
  
**Check description**  
Verifies that the number of top values from a set in a column does not exceed the minimum accepted count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|string_most_popular_values|adhoc| |[string_most_popular_values](../../../../reference/sensors/column/strings%20column%20sensors/#string-most-popular-values)|[min_count](../../../../reference/rules/comparison/#min-count)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=string_most_popular_values
```
**Check structure (Yaml)**
```yaml
      checks:
        strings:
          string_most_popular_values:
            parameters:
              expected_values:
              - USD
              - GBP
              - EUR
            error:
              min_count: 5
            warning:
              min_count: 5
            fatal:
              min_count: 5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-27"
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
    partitioned_checks_timestamp_source: event_timestamp
  columns:
    target_column:
      checks:
        strings:
          string_most_popular_values:
            parameters:
              expected_values:
              - USD
              - GBP
              - EUR
            error:
              min_count: 5
            warning:
              min_count: 5
            fatal:
              min_count: 5
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
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            analyzed_table.`target_column` AS top_values,
            COUNT(*) AS total_values,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
            FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            analyzed_table."target_column" AS top_values,
            COUNT(*) AS total_values,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
            FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) AS top_col_values
    ) AS  top_values
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            "target_column" AS top_values,
            COUNT(*) AS total_values,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS  top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {% macro render_data_stream(table_alias_prefix = '') %}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {% endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
               SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
               FROM {{ lib.render_target_table() }} AS analyzed_table
               {{- lib.render_where_clause() }}
               {{- lib.render_group_by() }}, top_values
               {{- lib.render_order_by() }}, total_values
             ) AS top_col_values
        ) AS  top_values
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
               SELECT
                "target_column" AS top_values,
                COUNT(*) AS total_values,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
               FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
             ) AS top_col_values
        ) AS  top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-19 44-49"
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
        partitioned_checks_timestamp_source: event_timestamp
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
            strings:
              string_most_popular_values:
                parameters:
                  expected_values:
                  - USD
                  - GBP
                  - EUR
                error:
                  min_count: 5
                warning:
                  min_count: 5
                fatal:
                  min_count: 5
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
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                analyzed_table.`target_column` AS top_values,
                COUNT(*) AS total_values,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
                FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                analyzed_table."target_column" AS top_values,
                COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
                FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) AS top_col_values
        ) AS  top_values
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                "target_column" AS top_values,
                COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS  top_values
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {% macro render_data_stream(table_alias_prefix = '') %}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {% endmacro %}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {% endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                   SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_values,
                    COUNT(*) AS total_values
                    {{- lib.render_data_stream_projections('analyzed_table') }}
                    {{- lib.render_time_dimension_projection('analyzed_table') }}
                   FROM {{ lib.render_target_table() }} AS analyzed_table
                   {{- lib.render_where_clause() }}
                   {{- lib.render_group_by() }}, top_values
                   {{- lib.render_order_by() }}, total_values
                 ) AS top_col_values
            ) AS  top_values
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                   SELECT
                    "target_column" AS top_values,
                    COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                   FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
                 ) AS top_col_values
            ) AS  top_values
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily checkpoint string most popular values**  
  
**Check description**  
Verifies that the number of top values from a set in a column does not exceed the minimum accepted count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_string_most_popular_values|checkpoint|daily|[string_most_popular_values](../../../../reference/sensors/column/strings%20column%20sensors/#string-most-popular-values)|[min_count](../../../../reference/rules/comparison/#min-count)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_string_most_popular_values
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        daily:
          strings:
            daily_checkpoint_string_most_popular_values:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
    partitioned_checks_timestamp_source: event_timestamp
  columns:
    target_column:
      checkpoints:
        daily:
          strings:
            daily_checkpoint_string_most_popular_values:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
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
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            analyzed_table.`target_column` AS top_values,
            COUNT(*) AS total_values,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            analyzed_table."target_column" AS top_values,
            COUNT(*) AS total_values,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
            FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) AS top_col_values
    ) AS  top_values
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            "target_column" AS top_values,
            COUNT(*) AS total_values,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS  top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {% macro render_data_stream(table_alias_prefix = '') %}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {% endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
               SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
               FROM {{ lib.render_target_table() }} AS analyzed_table
               {{- lib.render_where_clause() }}
               {{- lib.render_group_by() }}, top_values
               {{- lib.render_order_by() }}, total_values
             ) AS top_col_values
        ) AS  top_values
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
               SELECT
                "target_column" AS top_values,
                COUNT(*) AS total_values,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
               FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
             ) AS top_col_values
        ) AS  top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-19 45-50"
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
        partitioned_checks_timestamp_source: event_timestamp
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
              strings:
                daily_checkpoint_string_most_popular_values:
                  parameters:
                    expected_values:
                    - USD
                    - GBP
                    - EUR
                  error:
                    min_count: 5
                  warning:
                    min_count: 5
                  fatal:
                    min_count: 5
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
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                analyzed_table.`target_column` AS top_values,
                COUNT(*) AS total_values,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
                FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                analyzed_table."target_column" AS top_values,
                COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
            TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
                FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) AS top_col_values
        ) AS  top_values
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                "target_column" AS top_values,
                COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS  top_values
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {% macro render_data_stream(table_alias_prefix = '') %}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {% endmacro %}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {% endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                   SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_values,
                    COUNT(*) AS total_values
                    {{- lib.render_data_stream_projections('analyzed_table') }}
                    {{- lib.render_time_dimension_projection('analyzed_table') }}
                   FROM {{ lib.render_target_table() }} AS analyzed_table
                   {{- lib.render_where_clause() }}
                   {{- lib.render_group_by() }}, top_values
                   {{- lib.render_order_by() }}, total_values
                 ) AS top_col_values
            ) AS  top_values
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                   SELECT
                    "target_column" AS top_values,
                    COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                   FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
                 ) AS top_col_values
            ) AS  top_values
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly checkpoint string most popular values**  
  
**Check description**  
Verifies that the number of top values from a set in a column does not exceed the minimum accepted count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_string_most_popular_values|checkpoint|monthly|[string_most_popular_values](../../../../reference/sensors/column/strings%20column%20sensors/#string-most-popular-values)|[min_count](../../../../reference/rules/comparison/#min-count)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_string_most_popular_values
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        monthly:
          strings:
            monthly_checkpoint_string_most_popular_values:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
    partitioned_checks_timestamp_source: event_timestamp
  columns:
    target_column:
      checkpoints:
        monthly:
          strings:
            monthly_checkpoint_string_most_popular_values:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
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
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            analyzed_table.`target_column` AS top_values,
            COUNT(*) AS total_values,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            analyzed_table."target_column" AS top_values,
            COUNT(*) AS total_values,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
            FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) AS top_col_values
    ) AS  top_values
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            "target_column" AS top_values,
            COUNT(*) AS total_values,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS  top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {% macro render_data_stream(table_alias_prefix = '') %}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {% endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
               SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
               FROM {{ lib.render_target_table() }} AS analyzed_table
               {{- lib.render_where_clause() }}
               {{- lib.render_group_by() }}, top_values
               {{- lib.render_order_by() }}, total_values
             ) AS top_col_values
        ) AS  top_values
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
               SELECT
                "target_column" AS top_values,
                COUNT(*) AS total_values,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
               FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
             ) AS top_col_values
        ) AS  top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-19 45-50"
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
        partitioned_checks_timestamp_source: event_timestamp
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
              strings:
                monthly_checkpoint_string_most_popular_values:
                  parameters:
                    expected_values:
                    - USD
                    - GBP
                    - EUR
                  error:
                    min_count: 5
                  warning:
                    min_count: 5
                  fatal:
                    min_count: 5
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
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                analyzed_table.`target_column` AS top_values,
                COUNT(*) AS total_values,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                analyzed_table."target_column" AS top_values,
                COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
                FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) AS top_col_values
        ) AS  top_values
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                "target_column" AS top_values,
                COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS  top_values
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {% macro render_data_stream(table_alias_prefix = '') %}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {% endmacro %}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {% endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                   SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_values,
                    COUNT(*) AS total_values
                    {{- lib.render_data_stream_projections('analyzed_table') }}
                    {{- lib.render_time_dimension_projection('analyzed_table') }}
                   FROM {{ lib.render_target_table() }} AS analyzed_table
                   {{- lib.render_where_clause() }}
                   {{- lib.render_group_by() }}, top_values
                   {{- lib.render_order_by() }}, total_values
                 ) AS top_col_values
            ) AS  top_values
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                   SELECT
                    "target_column" AS top_values,
                    COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                   FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
                 ) AS top_col_values
            ) AS  top_values
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily partition string most popular values**  
  
**Check description**  
Verifies that the number of top values from a set in a column does not exceed the minimum accepted count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_string_most_popular_values|partitioned|daily|[string_most_popular_values](../../../../reference/sensors/column/strings%20column%20sensors/#string-most-popular-values)|[min_count](../../../../reference/rules/comparison/#min-count)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_string_most_popular_values
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          strings:
            daily_partition_string_most_popular_values:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
    partitioned_checks_timestamp_source: event_timestamp
  columns:
    target_column:
      partitioned_checks:
        daily:
          strings:
            daily_partition_string_most_popular_values:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
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
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            analyzed_table.`target_column` AS top_values,
            COUNT(*) AS total_values,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            analyzed_table."target_column" AS top_values,
            COUNT(*) AS total_values,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
            FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) AS top_col_values
    ) AS  top_values
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            "target_column" AS top_values,
            COUNT(*) AS total_values,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS  top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {% macro render_data_stream(table_alias_prefix = '') %}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {% endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
               SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
               FROM {{ lib.render_target_table() }} AS analyzed_table
               {{- lib.render_where_clause() }}
               {{- lib.render_group_by() }}, top_values
               {{- lib.render_order_by() }}, total_values
             ) AS top_col_values
        ) AS  top_values
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
               SELECT
                "target_column" AS top_values,
                COUNT(*) AS total_values,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
               FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
             ) AS top_col_values
        ) AS  top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-19 45-50"
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
        partitioned_checks_timestamp_source: event_timestamp
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
                daily_partition_string_most_popular_values:
                  parameters:
                    expected_values:
                    - USD
                    - GBP
                    - EUR
                  error:
                    min_count: 5
                  warning:
                    min_count: 5
                  fatal:
                    min_count: 5
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
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                analyzed_table.`target_column` AS top_values,
                COUNT(*) AS total_values,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
                FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                analyzed_table."target_column" AS top_values,
                COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
                FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) AS top_col_values
        ) AS  top_values
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                "target_column" AS top_values,
                COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS  top_values
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {% macro render_data_stream(table_alias_prefix = '') %}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {% endmacro %}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {% endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                   SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_values,
                    COUNT(*) AS total_values
                    {{- lib.render_data_stream_projections('analyzed_table') }}
                    {{- lib.render_time_dimension_projection('analyzed_table') }}
                   FROM {{ lib.render_target_table() }} AS analyzed_table
                   {{- lib.render_where_clause() }}
                   {{- lib.render_group_by() }}, top_values
                   {{- lib.render_order_by() }}, total_values
                 ) AS top_col_values
            ) AS  top_values
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                   SELECT
                    "target_column" AS top_values,
                    COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                   FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
                 ) AS top_col_values
            ) AS  top_values
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition string most popular values**  
  
**Check description**  
Verifies that the number of top values from a set in a column does not exceed the minimum accepted count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_string_most_popular_values|partitioned|monthly|[string_most_popular_values](../../../../reference/sensors/column/strings%20column%20sensors/#string-most-popular-values)|[min_count](../../../../reference/rules/comparison/#min-count)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_string_most_popular_values
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          strings:
            monthly_partition_string_most_popular_values:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
    partitioned_checks_timestamp_source: event_timestamp
  columns:
    target_column:
      partitioned_checks:
        monthly:
          strings:
            monthly_partition_string_most_popular_values:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
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
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            analyzed_table.`target_column` AS top_values,
            COUNT(*) AS total_values,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            analyzed_table."target_column" AS top_values,
            COUNT(*) AS total_values,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
            FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    )
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
            SELECT
            {{ lib.render_target_column('analyzed_table') }} AS top_values,
            COUNT(*) AS total_values
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() }}
            {{- lib.render_group_by() }}, top_values
            {{- lib.render_order_by() }}, total_values
        ) AS top_col_values
    ) AS  top_values
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    {%- macro render_data_stream(table_alias_prefix = '') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period, time_period_utc,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
            SELECT
            "target_column" AS top_values,
            COUNT(*) AS total_values,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS  top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {% macro render_data_stream(table_alias_prefix = '') %}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {{ ', ' }}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ table_alias_prefix }}.stream_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro top_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL AS actual_value,
        {{parameters.expected_values|length}}
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else -%}
        SUM(
            CASE
                WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    {{ top_values_column() }}
        {%- endif -%}
    {% endmacro -%}
    
    {%- macro top_values_column() -%}
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period,
            RANK() OVER(partition by top_col_values.time_period
            {{- render_data_stream('top_col_values') }}
            ORDER BY top_col_values.total_values) as top_values_rank
            {{- render_data_stream('top_col_values') }}
        FROM (
               SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
               FROM {{ lib.render_target_table() }} AS analyzed_table
               {{- lib.render_where_clause() }}
               {{- lib.render_group_by() }}, top_values
               {{- lib.render_order_by() }}, total_values
             ) AS top_col_values
        ) AS  top_values
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                ELSE 0
            END
        ) AS actual_value,
        time_period
    FROM(
        SELECT
            top_col_values.top_values as top_values,
            top_col_values.time_period as time_period,
            RANK() OVER(partition by top_col_values.time_period
            ORDER BY top_col_values.total_values) as top_values_rank
        FROM (
               SELECT
                "target_column" AS top_values,
                COUNT(*) AS total_values,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
               FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc, top_values
    ORDER BY time_period, time_period_utc, total_values
             ) AS top_col_values
        ) AS  top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-19 45-50"
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
        partitioned_checks_timestamp_source: event_timestamp
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
                monthly_partition_string_most_popular_values:
                  parameters:
                    expected_values:
                    - USD
                    - GBP
                    - EUR
                  error:
                    min_count: 5
                  warning:
                    min_count: 5
                  fatal:
                    min_count: 5
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
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                analyzed_table.`target_column` AS top_values,
                COUNT(*) AS total_values,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
                FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                analyzed_table."target_column" AS top_values,
                COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
                FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        )
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_values,
                COUNT(*) AS total_values
                {{- lib.render_data_stream_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause() }}
                {{- lib.render_group_by() }}, top_values
                {{- lib.render_order_by() }}, total_values
            ) AS top_col_values
        ) AS  top_values
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        {%- macro render_data_stream(table_alias_prefix = '') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period, time_period_utc,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                SELECT
                "target_column" AS top_values,
                COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS  top_values
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {% macro render_data_stream(table_alias_prefix = '') %}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {{ ', ' }}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ table_alias_prefix }}.stream_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {% endmacro %}
        
        {%- macro top_value() -%}
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            NULL AS actual_value,
            {{parameters.expected_values|length}}
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else -%}
            SUM(
                CASE
                    WHEN top_values IN ({{ extract_in_list(parameters['expected_values']) }}) THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        {{ top_values_column() }}
            {%- endif -%}
        {% endmacro -%}
        
        {%- macro top_values_column() -%}
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period,
                RANK() OVER(partition by top_col_values.time_period
                {{- render_data_stream('top_col_values') }}
                ORDER BY top_col_values.total_values) as top_values_rank
                {{- render_data_stream('top_col_values') }}
            FROM (
                   SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_values,
                    COUNT(*) AS total_values
                    {{- lib.render_data_stream_projections('analyzed_table') }}
                    {{- lib.render_time_dimension_projection('analyzed_table') }}
                   FROM {{ lib.render_target_table() }} AS analyzed_table
                   {{- lib.render_where_clause() }}
                   {{- lib.render_group_by() }}, top_values
                   {{- lib.render_order_by() }}, total_values
                 ) AS top_col_values
            ) AS  top_values
        WHERE top_values_rank <= {{ parameters.top_values }}
        {%- endmacro -%}
        
        SELECT
            {{ top_value() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            SUM(
                CASE
                    WHEN top_values IN ('USD', 'GBP', 'EUR') THEN 1
                    ELSE 0
                END
            ) AS actual_value,
            time_period
        FROM(
            SELECT
                top_col_values.top_values as top_values,
                top_col_values.time_period as time_period,
                RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2
                ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2
            FROM (
                   SELECT
                    "target_column" AS top_values,
                    COUNT(*) AS total_values,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                   FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc, top_values
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc, total_values
                 ) AS top_col_values
            ) AS  top_values
        WHERE top_values_rank <= 
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___
