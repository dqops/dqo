**expected strings in top values count** checks  

**Description**  
Column-level check that counts how many expected string values are among the TOP most popular values in the column.
 The check will first count the number of occurrences of each column&#x27;s value and will pick the TOP X most popular values (configurable by the &#x27;top&#x27; parameter).
 Then, it will compare the list of most popular values to the given list of expected values that should be most popular.
 This check will verify how many supposed most popular values (provided in the &#x27;expected_values&#x27; list) were not found in the top X most popular values in the column.
 This check is useful for analyzing string columns that have several very popular values, these could be the country codes of the countries with the most number of customers.

___

## **profile expected strings in top values count**  
  
**Check description**  
Verifies that the top X most popular column values contain all values from a list of expected values.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|profile_expected_strings_in_top_values_count|profiling| |[expected_strings_in_top_values_count](../../../../reference/sensors/Column/strings-column-sensors/#expected-strings-in-top-values-count)|[max_missing](../../../../reference/rules/Comparison/#max-missing)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=profile_expected_strings_in_top_values_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=profile_expected_strings_in_top_values_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=profile_expected_strings_in_top_values_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=profile_expected_strings_in_top_values_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=profile_expected_strings_in_top_values_count
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        strings:
          profile_expected_strings_in_top_values_count:
            parameters:
              expected_values:
              - USD
              - GBP
              - EUR
            warning:
              max_missing: 1
            error:
              max_missing: 1
            fatal:
              max_missing: 2
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-26"
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
          profile_expected_strings_in_top_values_count:
            parameters:
              expected_values:
              - USD
              - GBP
              - EUR
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
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.`target_column` AS top_value,
                COUNT(*) AS total_values,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM
                `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.`target_column` AS top_value,
                COUNT(*) AS total_values,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM
                `<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value top_value,
            top_col_values.time_period time_period,
            top_col_values.time_period_utc time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} top_value,
                COUNT(*) total_values
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM
            (
                SELECT
                additional_table.*,
                {{ lib.render_target_column('additional_table') }} top_value
                {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                FROM {{ lib.render_target_table() }} additional_table) analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) top_col_values
    ) top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL actual_value,
        MAX(0) expected_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) actual_value,
        MAX({{ parameters.expected_values | length }})  expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) actual_value,
        MAX(3)  expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value top_value,
            top_col_values.time_period time_period,
            top_col_values.time_period_utc time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" top_value,
                COUNT(*) total_values,
        time_period,
        time_period_utc
            FROM
            (
                SELECT
                additional_table.*,
                additional_table."target_column" top_value,
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    ) top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
            FROM
                "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT_BIG(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
            {%- else %}
            GROUP BY {{ lib.render_target_column('analyzed_table') }}
            {%- endif %}
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT_BIG(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
            {%- for attribute in lib.data_groupings -%}
                top_values.grouping_{{ attribute }}{{ ', ' }}
            {%- endfor -%}
        {%- endif -%}
        top_values.time_period,
        top_values.time_period_utc
    {{ render_from_subquery() }}
    {%- endif %}
    GROUP BY time_period, time_period_utc
    {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
        {%- for attribute in lib.data_groupings -%}
            {{ ', ' }}top_values.grouping_{{ attribute }}
        {%- endfor -%}
    {%- endif -%}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        COUNT_BIG(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.[target_column] AS top_value,
                COUNT_BIG(*) AS total_values,
                DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM
                [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[target_column]
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 44-49"
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
              profile_expected_strings_in_top_values_count:
                parameters:
                  expected_values:
                  - USD
                  - GBP
                  - EUR
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
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.`target_column` AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                FROM
                    `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.`target_column` AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                    FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
                FROM
                    `<target_table>` AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value top_value,
                top_col_values.time_period time_period,
                top_col_values.time_period_utc time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} top_value,
                    COUNT(*) total_values
                    {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                FROM
                (
                    SELECT
                    additional_table.*,
                    {{ lib.render_target_column('additional_table') }} top_value
                    {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                    FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) top_col_values
        ) top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL actual_value,
            MAX(0) expected_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) actual_value,
            MAX({{ parameters.expected_values | length }})  expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) actual_value,
            MAX(3)  expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value top_value,
                top_col_values.time_period time_period,
                top_col_values.time_period_utc time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" top_value,
                    COUNT(*) total_values,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
                FROM
                (
                    SELECT
                    additional_table.*,
                    additional_table."target_column" top_value,
                    additional_table."country" AS grouping_level_1,
                    additional_table."state" AS grouping_level_2,
                    TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                    CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        ) top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                    CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM
                    "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                    CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM
                    "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                    TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
                FROM
                    "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT_BIG(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                    {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                {%- else %}
                GROUP BY {{ lib.render_target_column('analyzed_table') }}
                {%- endif %}
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT_BIG(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    top_values.grouping_{{ attribute }}{{ ', ' }}
                {%- endfor -%}
            {%- endif -%}
            top_values.time_period,
            top_values.time_period_utc
        {{ render_from_subquery() }}
        {%- endif %}
        GROUP BY time_period, time_period_utc
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ', ' }}top_values.grouping_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            COUNT_BIG(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,top_values.grouping_level_1, top_values.grouping_level_2, top_values.time_period,
            top_values.time_period_utc
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.[target_column] AS top_value,
                    COUNT_BIG(*) AS total_values,
                    analyzed_table.[country] AS grouping_level_1,
                    analyzed_table.[state] AS grouping_level_2,
                    DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                    CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
                FROM
                    [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                GROUP BY analyzed_table.[country], analyzed_table.[state], analyzed_table.[target_column]
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY time_period, time_period_utc, top_values.grouping_level_1, top_values.grouping_level_2
        ```
    






___

## **daily expected strings in top values count**  
  
**Check description**  
Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_expected_strings_in_top_values_count|monitoring|daily|[expected_strings_in_top_values_count](../../../../reference/sensors/Column/strings-column-sensors/#expected-strings-in-top-values-count)|[max_missing](../../../../reference/rules/Comparison/#max-missing)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_expected_strings_in_top_values_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_expected_strings_in_top_values_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_expected_strings_in_top_values_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_expected_strings_in_top_values_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_expected_strings_in_top_values_count
```
**Check structure (Yaml)**
```yaml
      monitoring_checks:
        daily:
          strings:
            daily_expected_strings_in_top_values_count:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
              warning:
                max_missing: 1
              error:
                max_missing: 1
              fatal:
                max_missing: 2
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-27"
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
            daily_expected_strings_in_top_values_count:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
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
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.`target_column` AS top_value,
                COUNT(*) AS total_values,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM
                `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.`target_column` AS top_value,
                COUNT(*) AS total_values,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
            FROM
                `<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value top_value,
            top_col_values.time_period time_period,
            top_col_values.time_period_utc time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} top_value,
                COUNT(*) total_values
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM
            (
                SELECT
                additional_table.*,
                {{ lib.render_target_column('additional_table') }} top_value
                {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                FROM {{ lib.render_target_table() }} additional_table) analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) top_col_values
    ) top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL actual_value,
        MAX(0) expected_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) actual_value,
        MAX({{ parameters.expected_values | length }})  expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) actual_value,
        MAX(3)  expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value top_value,
            top_col_values.time_period time_period,
            top_col_values.time_period_utc time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" top_value,
                COUNT(*) total_values,
        time_period,
        time_period_utc
            FROM
            (
                SELECT
                additional_table.*,
                additional_table."target_column" top_value,
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    ) top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
                TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
            FROM
                "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT_BIG(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
            {%- else %}
            GROUP BY {{ lib.render_target_column('analyzed_table') }}
            {%- endif %}
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT_BIG(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
            {%- for attribute in lib.data_groupings -%}
                top_values.grouping_{{ attribute }}{{ ', ' }}
            {%- endfor -%}
        {%- endif -%}
        top_values.time_period,
        top_values.time_period_utc
    {{ render_from_subquery() }}
    {%- endif %}
    GROUP BY time_period, time_period_utc
    {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
        {%- for attribute in lib.data_groupings -%}
            {{ ', ' }}top_values.grouping_{{ attribute }}
        {%- endfor -%}
    {%- endif -%}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        COUNT_BIG(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.[target_column] AS top_value,
                COUNT_BIG(*) AS total_values,
                CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
                CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
            FROM
                [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[target_column]
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 45-50"
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
                daily_expected_strings_in_top_values_count:
                  parameters:
                    expected_values:
                    - USD
                    - GBP
                    - EUR
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
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.`target_column` AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                    TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
                FROM
                    `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.`target_column` AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
                    FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
                FROM
                    `<target_table>` AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value top_value,
                top_col_values.time_period time_period,
                top_col_values.time_period_utc time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} top_value,
                    COUNT(*) total_values
                    {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                FROM
                (
                    SELECT
                    additional_table.*,
                    {{ lib.render_target_column('additional_table') }} top_value
                    {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                    FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) top_col_values
        ) top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL actual_value,
            MAX(0) expected_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) actual_value,
            MAX({{ parameters.expected_values | length }})  expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) actual_value,
            MAX(3)  expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value top_value,
                top_col_values.time_period time_period,
                top_col_values.time_period_utc time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" top_value,
                    COUNT(*) total_values,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
                FROM
                (
                    SELECT
                    additional_table.*,
                    additional_table."target_column" top_value,
                    additional_table."country" AS grouping_level_1,
                    additional_table."state" AS grouping_level_2,
                    TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
                    CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        ) top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    CAST(LOCALTIMESTAMP AS date) AS time_period,
                    CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM
                    "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    CAST(LOCALTIMESTAMP AS date) AS time_period,
                    CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM
                    "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
                    TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
                FROM
                    "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT_BIG(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                    {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                {%- else %}
                GROUP BY {{ lib.render_target_column('analyzed_table') }}
                {%- endif %}
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT_BIG(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    top_values.grouping_{{ attribute }}{{ ', ' }}
                {%- endfor -%}
            {%- endif -%}
            top_values.time_period,
            top_values.time_period_utc
        {{ render_from_subquery() }}
        {%- endif %}
        GROUP BY time_period, time_period_utc
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ', ' }}top_values.grouping_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            COUNT_BIG(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,top_values.grouping_level_1, top_values.grouping_level_2, top_values.time_period,
            top_values.time_period_utc
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.[target_column] AS top_value,
                    COUNT_BIG(*) AS total_values,
                    analyzed_table.[country] AS grouping_level_1,
                    analyzed_table.[state] AS grouping_level_2,
                    CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
                    CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
                FROM
                    [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                GROUP BY analyzed_table.[country], analyzed_table.[state], analyzed_table.[target_column]
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY time_period, time_period_utc, top_values.grouping_level_1, top_values.grouping_level_2
        ```
    






___

## **monthly expected strings in top values count**  
  
**Check description**  
Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_expected_strings_in_top_values_count|monitoring|monthly|[expected_strings_in_top_values_count](../../../../reference/sensors/Column/strings-column-sensors/#expected-strings-in-top-values-count)|[max_missing](../../../../reference/rules/Comparison/#max-missing)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_expected_strings_in_top_values_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_expected_strings_in_top_values_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_expected_strings_in_top_values_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_expected_strings_in_top_values_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_expected_strings_in_top_values_count
```
**Check structure (Yaml)**
```yaml
      monitoring_checks:
        monthly:
          strings:
            monthly_expected_strings_in_top_values_count:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
              warning:
                max_missing: 1
              error:
                max_missing: 1
              fatal:
                max_missing: 2
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-27"
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
            monthly_expected_strings_in_top_values_count:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
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
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.`target_column` AS top_value,
                COUNT(*) AS total_values,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM
                `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.`target_column` AS top_value,
                COUNT(*) AS total_values,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM
                `<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value top_value,
            top_col_values.time_period time_period,
            top_col_values.time_period_utc time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} top_value,
                COUNT(*) total_values
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM
            (
                SELECT
                additional_table.*,
                {{ lib.render_target_column('additional_table') }} top_value
                {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                FROM {{ lib.render_target_table() }} additional_table) analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) top_col_values
    ) top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL actual_value,
        MAX(0) expected_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) actual_value,
        MAX({{ parameters.expected_values | length }})  expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) actual_value,
        MAX(3)  expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value top_value,
            top_col_values.time_period time_period,
            top_col_values.time_period_utc time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" top_value,
                COUNT(*) total_values,
        time_period,
        time_period_utc
            FROM
            (
                SELECT
                additional_table.*,
                additional_table."target_column" top_value,
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    ) top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
            FROM
                "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT_BIG(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
            {%- else %}
            GROUP BY {{ lib.render_target_column('analyzed_table') }}
            {%- endif %}
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT_BIG(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
            {%- for attribute in lib.data_groupings -%}
                top_values.grouping_{{ attribute }}{{ ', ' }}
            {%- endfor -%}
        {%- endif -%}
        top_values.time_period,
        top_values.time_period_utc
    {{ render_from_subquery() }}
    {%- endif %}
    GROUP BY time_period, time_period_utc
    {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
        {%- for attribute in lib.data_groupings -%}
            {{ ', ' }}top_values.grouping_{{ attribute }}
        {%- endfor -%}
    {%- endif -%}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        COUNT_BIG(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.[target_column] AS top_value,
                COUNT_BIG(*) AS total_values,
                DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM
                [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[target_column]
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 45-50"
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
                monthly_expected_strings_in_top_values_count:
                  parameters:
                    expected_values:
                    - USD
                    - GBP
                    - EUR
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
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.`target_column` AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                FROM
                    `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.`target_column` AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                    FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
                FROM
                    `<target_table>` AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value top_value,
                top_col_values.time_period time_period,
                top_col_values.time_period_utc time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} top_value,
                    COUNT(*) total_values
                    {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                FROM
                (
                    SELECT
                    additional_table.*,
                    {{ lib.render_target_column('additional_table') }} top_value
                    {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                    FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) top_col_values
        ) top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL actual_value,
            MAX(0) expected_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) actual_value,
            MAX({{ parameters.expected_values | length }})  expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) actual_value,
            MAX(3)  expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value top_value,
                top_col_values.time_period time_period,
                top_col_values.time_period_utc time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" top_value,
                    COUNT(*) total_values,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
                FROM
                (
                    SELECT
                    additional_table.*,
                    additional_table."target_column" top_value,
                    additional_table."country" AS grouping_level_1,
                    additional_table."state" AS grouping_level_2,
                    TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                    CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        ) top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                    CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM
                    "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                    CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM
                    "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                    TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
                FROM
                    "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT_BIG(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                    {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                {%- else %}
                GROUP BY {{ lib.render_target_column('analyzed_table') }}
                {%- endif %}
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT_BIG(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    top_values.grouping_{{ attribute }}{{ ', ' }}
                {%- endfor -%}
            {%- endif -%}
            top_values.time_period,
            top_values.time_period_utc
        {{ render_from_subquery() }}
        {%- endif %}
        GROUP BY time_period, time_period_utc
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ', ' }}top_values.grouping_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            COUNT_BIG(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,top_values.grouping_level_1, top_values.grouping_level_2, top_values.time_period,
            top_values.time_period_utc
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.[target_column] AS top_value,
                    COUNT_BIG(*) AS total_values,
                    analyzed_table.[country] AS grouping_level_1,
                    analyzed_table.[state] AS grouping_level_2,
                    DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                    CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
                FROM
                    [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                GROUP BY analyzed_table.[country], analyzed_table.[state], analyzed_table.[target_column]
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY time_period, time_period_utc, top_values.grouping_level_1, top_values.grouping_level_2
        ```
    






___

## **daily partition expected strings in top values count**  
  
**Check description**  
Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_expected_strings_in_top_values_count|partitioned|daily|[expected_strings_in_top_values_count](../../../../reference/sensors/Column/strings-column-sensors/#expected-strings-in-top-values-count)|[max_missing](../../../../reference/rules/Comparison/#max-missing)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_partition_expected_strings_in_top_values_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_partition_expected_strings_in_top_values_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_partition_expected_strings_in_top_values_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_partition_expected_strings_in_top_values_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_expected_strings_in_top_values_count
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          strings:
            daily_partition_expected_strings_in_top_values_count:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
              warning:
                max_missing: 1
              error:
                max_missing: 1
              fatal:
                max_missing: 2
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
            daily_partition_expected_strings_in_top_values_count:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
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
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.`target_column` AS top_value,
                COUNT(*) AS total_values,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM
                `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.`target_column` AS top_value,
                COUNT(*) AS total_values,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
            FROM
                `<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value top_value,
            top_col_values.time_period time_period,
            top_col_values.time_period_utc time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} top_value,
                COUNT(*) total_values
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM
            (
                SELECT
                additional_table.*,
                {{ lib.render_target_column('additional_table') }} top_value
                {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                FROM {{ lib.render_target_table() }} additional_table) analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) top_col_values
    ) top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL actual_value,
        MAX(0) expected_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) actual_value,
        MAX({{ parameters.expected_values | length }})  expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) actual_value,
        MAX(3)  expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value top_value,
            top_col_values.time_period time_period,
            top_col_values.time_period_utc time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" top_value,
                COUNT(*) total_values,
        time_period,
        time_period_utc
            FROM
            (
                SELECT
                additional_table.*,
                additional_table."target_column" top_value,
                TRUNC(CAST(additional_table."date_column" AS DATE)) AS time_period,
                CAST(TRUNC(CAST(additional_table."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    ) top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
            FROM
                "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT_BIG(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
            {%- else %}
            GROUP BY {{ lib.render_target_column('analyzed_table') }}
            {%- endif %}
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT_BIG(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
            {%- for attribute in lib.data_groupings -%}
                top_values.grouping_{{ attribute }}{{ ', ' }}
            {%- endfor -%}
        {%- endif -%}
        top_values.time_period,
        top_values.time_period_utc
    {{ render_from_subquery() }}
    {%- endif %}
    GROUP BY time_period, time_period_utc
    {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
        {%- for attribute in lib.data_groupings -%}
            {{ ', ' }}top_values.grouping_{{ attribute }}
        {%- endfor -%}
    {%- endif -%}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        COUNT_BIG(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.[target_column] AS top_value,
                COUNT_BIG(*) AS total_values,
                CAST(analyzed_table.[date_column] AS date) AS time_period,
                CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
            FROM
                [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date), analyzed_table.[target_column]
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-22 51-56"
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
                daily_partition_expected_strings_in_top_values_count:
                  parameters:
                    expected_values:
                    - USD
                    - GBP
                    - EUR
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
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.`target_column` AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                FROM
                    `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.`target_column` AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                    FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
                FROM
                    `<target_table>` AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value top_value,
                top_col_values.time_period time_period,
                top_col_values.time_period_utc time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} top_value,
                    COUNT(*) total_values
                    {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                FROM
                (
                    SELECT
                    additional_table.*,
                    {{ lib.render_target_column('additional_table') }} top_value
                    {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                    FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) top_col_values
        ) top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL actual_value,
            MAX(0) expected_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) actual_value,
            MAX({{ parameters.expected_values | length }})  expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) actual_value,
            MAX(3)  expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value top_value,
                top_col_values.time_period time_period,
                top_col_values.time_period_utc time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" top_value,
                    COUNT(*) total_values,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
                FROM
                (
                    SELECT
                    additional_table.*,
                    additional_table."target_column" top_value,
                    additional_table."country" AS grouping_level_1,
                    additional_table."state" AS grouping_level_2,
                    TRUNC(CAST(additional_table."date_column" AS DATE)) AS time_period,
                    CAST(TRUNC(CAST(additional_table."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        ) top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM
                    "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM
                    "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
                FROM
                    "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT_BIG(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                    {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                {%- else %}
                GROUP BY {{ lib.render_target_column('analyzed_table') }}
                {%- endif %}
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT_BIG(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    top_values.grouping_{{ attribute }}{{ ', ' }}
                {%- endfor -%}
            {%- endif -%}
            top_values.time_period,
            top_values.time_period_utc
        {{ render_from_subquery() }}
        {%- endif %}
        GROUP BY time_period, time_period_utc
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ', ' }}top_values.grouping_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            COUNT_BIG(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,top_values.grouping_level_1, top_values.grouping_level_2, top_values.time_period,
            top_values.time_period_utc
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.[target_column] AS top_value,
                    COUNT_BIG(*) AS total_values,
                    analyzed_table.[country] AS grouping_level_1,
                    analyzed_table.[state] AS grouping_level_2,
                    CAST(analyzed_table.[date_column] AS date) AS time_period,
                    CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
                FROM
                    [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                GROUP BY analyzed_table.[country], analyzed_table.[state], CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date), analyzed_table.[target_column]
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY time_period, time_period_utc, top_values.grouping_level_1, top_values.grouping_level_2
        ```
    






___

## **monthly partition expected strings in top values count**  
  
**Check description**  
Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_expected_strings_in_top_values_count|partitioned|monthly|[expected_strings_in_top_values_count](../../../../reference/sensors/Column/strings-column-sensors/#expected-strings-in-top-values-count)|[max_missing](../../../../reference/rules/Comparison/#max-missing)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_partition_expected_strings_in_top_values_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_partition_expected_strings_in_top_values_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_partition_expected_strings_in_top_values_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_partition_expected_strings_in_top_values_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_expected_strings_in_top_values_count
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          strings:
            monthly_partition_expected_strings_in_top_values_count:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
              warning:
                max_missing: 1
              error:
                max_missing: 1
              fatal:
                max_missing: 2
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
            monthly_partition_expected_strings_in_top_values_count:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
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
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.`target_column` AS top_value,
                COUNT(*) AS total_values,
                DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
            FROM
                `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.`target_column` AS top_value,
                COUNT(*) AS total_values,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM
                `<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value top_value,
            top_col_values.time_period time_period,
            top_col_values.time_period_utc time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} top_value,
                COUNT(*) total_values
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM
            (
                SELECT
                additional_table.*,
                {{ lib.render_target_column('additional_table') }} top_value
                {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                FROM {{ lib.render_target_table() }} additional_table) analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) top_col_values
    ) top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL actual_value,
        MAX(0) expected_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) actual_value,
        MAX({{ parameters.expected_values | length }})  expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) actual_value,
        MAX(3)  expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value top_value,
            top_col_values.time_period time_period,
            top_col_values.time_period_utc time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" top_value,
                COUNT(*) total_values,
        time_period,
        time_period_utc
            FROM
            (
                SELECT
                additional_table.*,
                additional_table."target_column" top_value,
                TRUNC(CAST(additional_table."date_column" AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(additional_table."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) top_col_values
    ) top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {{- lib.render_group_by(indentation = '        ') }}, top_value
            {{- lib.render_order_by(indentation = '        ') }}, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        COUNT(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,
        top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table."target_column" AS top_value,
                COUNT(*) AS total_values,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
            FROM
                "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc, top_value
            ORDER BY time_period, time_period_utc, total_values
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_from_subquery() -%}
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS top_value,
                COUNT_BIG(*) AS total_values
                {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '        ') }}
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
            {%- else %}
            GROUP BY {{ lib.render_target_column('analyzed_table') }}
            {%- endif %}
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ',' }}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                    {%- endif -%}
                {%- endwith %}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
        NULL AS actual_value,
        MAX(0) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
        {%- else %}
        COUNT_BIG(DISTINCT
            CASE
                WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value,
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
            {%- for attribute in lib.data_groupings -%}
                top_values.grouping_{{ attribute }}{{ ', ' }}
            {%- endfor -%}
        {%- endif -%}
        top_values.time_period,
        top_values.time_period_utc
    {{ render_from_subquery() }}
    {%- endif %}
    GROUP BY time_period, time_period_utc
    {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
        {%- for attribute in lib.data_groupings -%}
            {{ ', ' }}top_values.grouping_{{ attribute }}
        {%- endfor -%}
    {%- endif -%}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        COUNT_BIG(DISTINCT
            CASE
                WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                ELSE NULL
            END
        ) AS actual_value,
        MAX(3) AS expected_value,top_values.time_period,
        top_values.time_period_utc
    FROM
    (
        SELECT
            top_col_values.top_value as top_value,
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            RANK() OVER(PARTITION BY top_col_values.time_period
                ORDER BY top_col_values.total_values) as top_values_rank
        FROM
        (
            SELECT
                analyzed_table.[target_column] AS top_value,
                COUNT_BIG(*) AS total_values,
                DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM
                [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0), analyzed_table.[target_column]
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= 
    GROUP BY time_period, time_period_utc
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-22 51-56"
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
                monthly_partition_expected_strings_in_top_values_count:
                  parameters:
                    expected_values:
                    - USD
                    - GBP
                    - EUR
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
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.`target_column` AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
                    TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
                FROM
                    `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.`target_column` AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                    FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
                FROM
                    `<target_table>` AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value top_value,
                top_col_values.time_period time_period,
                top_col_values.time_period_utc time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} top_value,
                    COUNT(*) total_values
                    {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                FROM
                (
                    SELECT
                    additional_table.*,
                    {{ lib.render_target_column('additional_table') }} top_value
                    {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                    FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) top_col_values
        ) top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL actual_value,
            MAX(0) expected_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) actual_value,
            MAX({{ parameters.expected_values | length }})  expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) actual_value,
            MAX(3)  expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value top_value,
                top_col_values.time_period time_period,
                top_col_values.time_period_utc time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" top_value,
                    COUNT(*) total_values,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
                FROM
                (
                    SELECT
                    additional_table.*,
                    additional_table."target_column" top_value,
                    additional_table."country" AS grouping_level_1,
                    additional_table."state" AS grouping_level_2,
                    TRUNC(CAST(additional_table."date_column" AS DATE), 'MONTH') AS time_period,
                    CAST(TRUNC(CAST(additional_table."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) top_col_values
        ) top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                    CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM
                    "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                    CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM
                    "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {{- lib.render_group_by(indentation = '        ') }}, top_value
                {{- lib.render_order_by(indentation = '        ') }}, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc
            {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
        {{ render_from_subquery() }}
        {%- endif -%}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            top_values.time_period,
            top_values.time_period_utc,
            top_values.grouping_level_1,
            top_values.grouping_level_2
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table."target_column" AS top_value,
                    COUNT(*) AS total_values,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                    TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
                FROM
                    "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {%- endmacro -%}
        
        {%- macro render_from_subquery() -%}
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                    ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
            FROM
            (
                SELECT
                    {{ lib.render_target_column('analyzed_table') }} AS top_value,
                    COUNT_BIG(*) AS total_values
                    {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                FROM
                    {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '        ') }}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                    {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                {%- else %}
                GROUP BY {{ lib.render_target_column('analyzed_table') }}
                {%- endif %}
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= {{ parameters.top }}
        {%- endmacro -%}
        
        {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ',' }}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                        {%- endif -%}
                    {%- endwith %}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
            NULL AS actual_value,
            MAX(0) AS expected_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
            {%- else %}
            COUNT_BIG(DISTINCT
                CASE
                    WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX({{ parameters.expected_values | length }}) AS expected_value,
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    top_values.grouping_{{ attribute }}{{ ', ' }}
                {%- endfor -%}
            {%- endif -%}
            top_values.time_period,
            top_values.time_period_utc
        {{ render_from_subquery() }}
        {%- endif %}
        GROUP BY time_period, time_period_utc
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
            {%- for attribute in lib.data_groupings -%}
                {{ ', ' }}top_values.grouping_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            COUNT_BIG(DISTINCT
                CASE
                    WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,top_values.grouping_level_1, top_values.grouping_level_2, top_values.time_period,
            top_values.time_period_utc
        FROM
        (
            SELECT
                top_col_values.top_value as top_value,
                top_col_values.time_period as time_period,
                top_col_values.time_period_utc as time_period_utc,
                RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                    ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
            FROM
            (
                SELECT
                    analyzed_table.[target_column] AS top_value,
                    COUNT_BIG(*) AS total_values,
                    analyzed_table.[country] AS grouping_level_1,
                    analyzed_table.[state] AS grouping_level_2,
                    DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                    CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
                FROM
                    [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                GROUP BY analyzed_table.[country], analyzed_table.[state], DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0), analyzed_table.[target_column]
            ) AS top_col_values
        ) AS top_values
        WHERE top_values_rank <= 
        GROUP BY time_period, time_period_utc, top_values.grouping_level_1, top_values.grouping_level_2
        ```
    






___
