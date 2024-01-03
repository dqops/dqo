
## **expected strings in top values count**
**Full sensor name**
```
column/strings/expected_strings_in_top_values_count
```
**Description**  
Column level sensor that counts how many expected string values are among the TOP most popular values in the column.
 The sensor will first count the number of occurrences of each column&#x27;s value and will pick the TOP X most popular values (configurable by the &#x27;top&#x27; parameter).
 Then, it will compare the list of most popular values to the given list of expected values that should be most popular.
 This sensor will return the number of expected values that were found within the &#x27;top&#x27; most popular column values.
 This sensor is useful for analyzing string columns that have several very popular values, these could be the country codes of the countries with the most number of customers.
 The sensor can detect if any of the most popular value (an expected value) is no longer one of the top X most popular values.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|expected_values|List of expected string values that should be found in the tested column among the TOP most popular (highest distinct count) column values.|string_list| ||
|top|The number of the most popular values (with the highest distinct count) that are analyzed to find the expected values.|long| ||




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
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
=== "MySQL"
      
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
=== "Oracle"
      
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
=== "PostgreSQL"
      
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
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
                top_value,
                COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                FROM (
                    SELECT
                        original_table.*,
                        {{ lib.render_target_column('original_table') }} AS top_value
                        {{- lib.render_data_grouping_projections('original_table') }}
                        {{- lib.render_time_dimension_projection('original_table') }}
                    FROM {{ lib.render_target_table() }} original_table
                    {{- lib.render_where_clause(table_alias_prefix='original_table') }}
                ) analyzed_table
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
=== "Redshift"
      
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
=== "Snowflake"
      
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
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
=== "SQL Server"
      
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
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
                top_value,
                COUNT(*) AS total_values
                    {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                FROM (
                    SELECT
                        original_table.*,
                        {{ lib.render_target_column('original_table') }} AS top_value
                        {{- lib.render_data_grouping_projections('original_table') }}
                        {{- lib.render_time_dimension_projection('original_table') }}
                    FROM {{ lib.render_target_table() }} original_table
                    {{- lib.render_where_clause(table_alias_prefix='original_table') }}
                ) analyzed_table
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
___

## **expected strings in use count**
**Full sensor name**
```
column/strings/expected_strings_in_use_count
```
**Description**  
Column level sensor that counts how many expected string values are used in a tested column. Finds unique column values from the set of expected string values and counts them.
 This sensor is useful to analyze string columns that have a low number of unique values and it should be tested if all possible values from the list of expected values are used in any row.
 The typical type of columns analyzed using this sensor are currency, country, status or gender columns.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|expected_values|List of expected string values that should be found in the tested column.|string_list| ||




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{ lib.make_text_constant(i) }},
            {%- else -%}
                {{ lib.make_text_constant(i) }}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            NULL
        {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{ render_else() }}
        END AS actual_value,
        MAX(CAST({{ parameters.expected_values | length }} AS INT)) AS expected_value_alias
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
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
    {% endmacro -%}
    
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
            WHEN COUNT(*) = 0 THEN MAX(0)
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
=== "Oracle"
      
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
    {% endmacro -%}
    
    {%- macro render_else() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            MAX(NULL)
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
        MAX({{ parameters.expected_values | length }}) expected_value
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
=== "PostgreSQL"
      
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
    {% endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            NULL
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL
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
        CAST({{ actual_value() }} AS BIGINT) AS actual_value,
        MAX({{ parameters.expected_values | length }} ) AS expected_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
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
    {% endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            NULL
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
=== "Snowflake"
      
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
    {% endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            NULL
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{ lib.make_text_constant(i) }},
            {%- else -%}
                {{ lib.make_text_constant(i) }}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            NULL
        {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{ render_else() }}
        END AS actual_value,
        MAX(CAST({{ parameters.expected_values | length }} AS INT)) AS expected_value_alias
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
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
    {% endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
            NULL
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
            WHEN COUNT_BIG(*) = 0 THEN NULL
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        NULL
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
        CAST({{ actual_value() }} AS BIGINT) AS actual_value,
        MAX({{ parameters.expected_values | length }} ) AS expected_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string boolean placeholder percent**
**Full sensor name**
```
column/strings/string_boolean_placeholder_percent
```
**Description**  
Column level sensor that calculates the number of rows with a boolean placeholder string column value.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string empty count**
**Full sensor name**
```
column/strings/string_empty_count
```
**Description**  
Column level sensor that calculates the number of rows with an empty string.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ render_column_cast_to_string('analyzed_table')}} = ''
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_target_column('analyzed_table')}} = ''
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_target_column('analyzed_table')}} = ''
                    THEN 1
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
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_column_cast_to_string('analyzed_table')}} = ''
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LEN({{ lib.render_target_column('analyzed_table')}}) = 0
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_column_cast_to_string('analyzed_table')}} = ''
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string empty percent**
**Full sensor name**
```
column/strings/string_empty_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with an empty string.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ render_column_cast_to_string('analyzed_table')}} = ''
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_target_column('analyzed_table')}} = ''
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_target_column('analyzed_table')}} = ''
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_column_cast_to_string('analyzed_table')}} = ''
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LEN({{ lib.render_target_column('analyzed_table')}}) = 0
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_column_cast_to_string('analyzed_table')}} = ''
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string invalid email count**
**Full sensor name**
```
column/strings/string_invalid_email_count
```
**Description**  
Column level sensor that calculates the number of rows with an invalid emails value in a column.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$")
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$')
                    THEN 0
                ELSE 1
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
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{lib.render_target_column('analyzed_table')}}  !~ '^[a-zA-Z0-9.!#$%&''*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$'
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{lib.render_target_column('analyzed_table')}}  !~ '^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z]{2,6})$'
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%_@__%.__%' AND PATINDEX('%[^a-z,0-9,@,.,_]%', REPLACE({{ lib.render_target_column('analyzed_table') }}, '-', 'a')) = 0
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string invalid ip4 address count**
**Full sensor name**
```
column/strings/string_invalid_ip4_address_count
```
**Description**  
Column level sensor that calculates the number of rows with an invalid IP4 address value in a column.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$")
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} ~ '^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} ~ '^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table') }} LIKE '[0-9].[0-9].[0-9].[0-9]'
                 OR {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]'
                 OR {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9]')
                 AND (PATINDEX('%[^0-9.]%', {{ lib.render_target_column('analyzed_table') }}) = 0
                 OR PATINDEX('%[^0-9.]%', {{ lib.render_target_column('analyzed_table') }}) = 0
                 OR PATINDEX('%[^0-9.]%', {{ lib.render_target_column('analyzed_table') }}) = 0)
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string invalid ip6 address count**
**Full sensor name**
```
column/strings/string_invalid_ip6_address_count
```
**Description**  
Column level sensor that calculates the number of rows with an invalid IP6 address value in a column.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$")
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table') }} REGEXP '^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}')
                    OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} ~ '^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} ~ '^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table') }} REGEXP '^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table') }} REGEXP '^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}:[0-9a-fA-F]{1,4}'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string invalid uuid count**
**Full sensor name**
```
column/strings/string_invalid_uuid_count
```
**Description**  
Column level sensor that calculates the number of rows with an invalid uuid value in a column.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$")
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[0-9A-F]{8}([ -]?)[0-9A-F]{4}([ -]?)[0-9A-F]{4}\1[0-9A-F]{4}\1[0-9A-F]{12}$','i')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} ~ '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} ~ '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$'
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN TRY_CONVERT(UNIQUEIDENTIFIER,{{ lib.render_target_column('analyzed_table') }}) IS NULL
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$')
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string length above max length count**
**Full sensor name**
```
column/strings/string_length_above_max_length_count
```
**Description**  
Column level sensor that calculates the count of values that are longer than a given length in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer| ||




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) >= {{(parameters.max_length)}}
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) >= {{(parameters.max_length)}}
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) >= {{(parameters.max_length)}}
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) >= {{(parameters.max_length)}}
                    THEN 1
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
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) >= {{(parameters.max_length)}}
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) >= {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) >= {{(parameters.max_length)}}
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) >= {{(parameters.max_length)}}
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) >= {{(parameters.max_length)}}
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN LEN({{ lib.render_target_column('analyzed_table')}}) >= {{(parameters.max_length)}}
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) >= {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string length above max length percent**
**Full sensor name**
```
column/strings/string_length_above_max_length_percent
```
**Description**  
Column level sensor that calculates the percentage of values that are longer than a given length in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer| ||




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) >= {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) >= {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) >= {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) >= {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) >= {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) >= {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) >= {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) >= {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) >= {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LEN({{ lib.render_target_column('analyzed_table')}}) >= {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT_BIG(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) >= {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string length below min length count**
**Full sensor name**
```
column/strings/string_length_below_min_length_count
```
**Description**  
Column level sensor that calculates the count of values that are shorter than a given length in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|min_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer| ||




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) <= {{(parameters.min_length)}}
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) <= {{(parameters.min_length)}}
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) <= {{(parameters.min_length)}}
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) <= {{(parameters.min_length)}}
                    THEN 1
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
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) <= {{(parameters.min_length)}}
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) <= {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) <= {{(parameters.min_length)}}
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) <= {{(parameters.min_length)}}
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) <= {{(parameters.min_length)}}
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN LEN({{ lib.render_target_column('analyzed_table')}}) <= {{(parameters.min_length)}}
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) <= {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string length below min length percent**
**Full sensor name**
```
column/strings/string_length_below_min_length_percent
```
**Description**  
Column level sensor that calculates the percentage of values that are shorter than a given length in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|min_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer| ||




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) <= {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) <= {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) <= {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) <= {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) <= {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) <= {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) <= {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) <= {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) <= {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LEN({{ lib.render_target_column('analyzed_table')}}) <= {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT_BIG(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) <= {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string length in range percent**
**Full sensor name**
```
column/strings/string_length_in_range_percent
```
**Description**  
Column level sensor that calculates the percentage of strings with a length below the indicated length in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|min_length|Sets a minimal string length|integer| ||
|max_length|Sets a maximal string length.|integer| ||




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
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
=== "MySQL"
      
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
=== "Oracle"
      
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
=== "PostgreSQL"
      
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE CAST(100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
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
=== "Snowflake"
      
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
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
=== "SQL Server"
      
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE CAST(100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
                        ELSE 0
                    END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string match date regex percent**
**Full sensor name**
```
column/strings/string_match_date_regex_percent
```
**Description**  
Column level sensor that calculates the percentage of values that does fit a given date regex in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|date_formats|Desired date format. Sensor will try to parse the column records and cast the data using this format.|enum| |YYYY-MM-DD<br/>Month D, YYYY<br/>MM/DD/YYYY<br/>YYYY/MM/DD<br/>DD/MM/YYYY<br/>|




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            '%Y-%m-%d'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            '%m/%d/%Y'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            '%d/%m/%Y'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            '%Y/%m/%d'
        {%- elif date_formats == 'Month D, YYYY'-%}
            '%b %d, %Y'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE.PARSE_DATE({{render_date_formats(parameters.date_formats)}}, {{ lib.render_target_column('analyzed_table') }}) IS NOT NULL
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
       {%- if date_formats == 'YYYY-MM-DD'-%}
               'yyyy-mm-dd'
           {%- elif date_formats == 'MM/DD/YYYY' -%}
               'mm/dd/yyyy'
           {%- elif date_formats == 'DD/MM/YYYY' -%}
               'dd/mm/yyyy'
           {%- elif date_formats == 'YYYY/MM/DD'-%}
               'yyyy/mm/dd'
        {%- elif date_formats == 'Month D, YYYY'-%}
            'Month DD,YYYY'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN TRY_TO_TIMESTAMP ({{ lib.render_target_column('analyzed_table') }},{{render_date_formats(parameters.date_formats)}}) IS NOT NULL
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            '%Y-%m-%d'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            '%m/%d/%Y'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            '%d/%m/%Y'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            '%Y/%m/%d'
        {%- elif date_formats == 'Month D, YYYY'-%}
            '%b %d, %Y'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN STR_TO_DATE({{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}}) IS NOT NULL
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            'YYYY-MM-DD'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            'MM/DD/YYYY'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            'DD/MM/YYYY'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            'YYYY/MM/DD'
        {%- elif date_formats == 'Month D, YYYY'-%}
            'MON DD, YYYY'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN TO_DATE({{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}}) IS NOT NULL
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            '%YYYY-%MM-%DD'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            '%MM/%DD/%YYYY'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            '%DD/%MM/%YYYY'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            '%YYYY/%MM/%DD'
        {%- elif date_formats == 'Month D, YYYY'-%}
            '%Month %DD,%YYYY'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN TO_DATE({{ lib.render_target_column('analyzed_table') }}::VARCHAR, {{render_date_formats(parameters.date_formats)}}) IS NOT NULL
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            '%Y-%m-%d'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            '%m/%d/%Y'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            '%d/%m/%Y'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            '%Y/%m/%d'
        {%- elif date_formats == 'Month D, YYYY'-%}
            '%b %d, %Y'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN TRY(DATE_PARSE({{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}})) IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            '%YYYY-%MM-%DD'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            '%MM/%DD/%YYYY'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            '%DD/%MM/%YYYY'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            '%YYYY/%MM/%DD'
        {%- elif date_formats == 'Month D, YYYY'-%}
            '%Month %DD,%YYYY'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN TO_DATE({{ lib.render_target_column('analyzed_table') }}::VARCHAR, {{render_date_formats(parameters.date_formats)}}) IS NOT NULL
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            'YYYY-MM-DD'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            'MM/DD/YYYY'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            'DD/MM/YYYY'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            'YYYY/MM/DD'
        {%- elif date_formats == 'Month D, YYYY'-%}
            'Month DD,YYYY'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN TRY_TO_DATE ({{ lib.render_target_column('analyzed_table') }},{{render_date_formats(parameters.date_formats)}}) IS NOT NULL
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
       {%- if date_formats == 'YYYY-MM-DD'-%}
               'yyyy-mm-dd'
           {%- elif date_formats == 'MM/DD/YYYY' -%}
               'mm/dd/yyyy'
           {%- elif date_formats == 'DD/MM/YYYY' -%}
               'dd/mm/yyyy'
           {%- elif date_formats == 'YYYY/MM/DD'-%}
               'yyyy/mm/dd'
        {%- elif date_formats == 'Month D, YYYY'-%}
            'Month DD,YYYY'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN TO_DATE ({{ lib.render_target_column('analyzed_table') }},{{render_date_formats(parameters.date_formats)}}) IS NOT NULL
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            120
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            101
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            103
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            111
        {%- elif date_formats == 'Month D, YYYY'-%}
            107
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN TRY_CONVERT(DATETIME, {{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}}) IS NOT NULL
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            '%Y-%m-%d'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            '%m/%d/%Y'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            '%d/%m/%Y'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            '%Y/%m/%d'
        {%- elif date_formats == 'Month D, YYYY'-%}
            '%b %d, %Y'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN TRY(DATE_PARSE({{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}})) IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string match name regex percent**
**Full sensor name**
```
column/strings/string_match_name_regex_percent
```
**Description**  
Column level sensor that calculates the percentage of values that does fit a given name regex in a column.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([\s-'])|([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{1})([.])(\s?))([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([\s-'.]?([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([\s-'.]?)(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([.])?))?$")
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP "^(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([\\s-'])|([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{1})([.])(\\s?))([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([\\s-'.]?([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([\\s-'.]?)(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([.])?))?$"
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, ''^(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([\s-'])|([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{1})([.])(\s?))([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([\s-'.]?([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([\s-'.]?)(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([.])?))?$')
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[[:alpha:] .''-]{2,}(\s.[[:alpha:] .''-]{2,})+$')
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ '^[[:alpha:] .''-]+$'
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([''\s-])|([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{1})([.])(\s?))([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([''\s-.]?([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([''\s-.]?)(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([.])?))?$')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ '^[[:alpha:] .''-]+$'
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[[:alpha:] .''-]+$'
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP "^(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([\\s-'])|([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{1})([.])(\\s?))([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([\\s-'.]?([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([\\s-'.]?)(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([.])?))?$"
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%[^ -~]%'
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([''\s-])|([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{1})([.])(\s?))([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([''\s-.]?([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([''\s-.]?)(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([.])?))?$')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string match regex percent**
**Full sensor name**
```
column/strings/string_match_regex_percent
```
**Description**  
Column level sensor that calculates the percent of values that fit to a regex in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|regex|This field can be used to define custom regex. In order to define custom regex, user should write correct regex as a string. If regex is not defined by user then default regex is null|string| ||




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro -%}
    
    {%- macro render_regex(regex) -%}
         r{{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS({{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }})
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro -%}
    
    {%- macro render_regex(regex) -%}
        {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} RLIKE {{ render_regex(parameters.regex) }}
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro -%}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }})
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro -%}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }})
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ ''{{ render_regex(parameters.regex) }}'' IS TRUE
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro -%}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }})
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ {{ render_regex(parameters.regex) }}
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro -%}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP {{ render_regex(parameters.regex) }}
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro -%}
    
    {%- macro render_regex(regex) -%}
        {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} RLIKE {{ render_regex(parameters.regex) }}
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE {{ render_regex(parameters.regex) }}
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {%- macro render_regex(regex) -%}
         {{ lib.make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }})
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string max length**
**Full sensor name**
```
column/strings/string_max_length
```
**Description**  
Column level sensor that ensures that the length of string in a column does not exceed the maximum accepted length.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        MAX(
            LENGTH({{render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        MAX(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        MAX(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        MAX(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        MAX(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    SELECT
        MAX(
            LENGTH({{lib.render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        MAX(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        MAX(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        MAX(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        MAX(
            LEN({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    SELECT
        MAX(
            LENGTH({{lib.render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string mean length**
**Full sensor name**
```
column/strings/string_mean_length
```
**Description**  
Column level sensor that ensures that the length of string in a column does not exceed the mean accepted length.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        AVG(
            LENGTH({{render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        AVG(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        AVG(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        AVG(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        AVG(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        AVG(
            LENGTH({{render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        AVG(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        AVG(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        AVG(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        AVG(
            LEN({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        AVG(
            LENGTH({{render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string min length**
**Full sensor name**
```
column/strings/string_min_length
```
**Description**  
Column level sensor that ensures that the length of string in a column does not exceed the minimum accepted length.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        MIN(
            LENGTH({{render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        MIN(
            LENGTH({{lib.render_target_column('analyzed_table')}})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        MIN(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        MIN(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        MIN(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    SELECT
        MIN(
            LENGTH({{ lib.render_column_cast_to_string('analyzed_table') }})
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        MIN(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        MIN(
            LENGTH({{lib.render_target_column('analyzed_table')}})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        MIN(
            LENGTH({{lib.render_target_column('analyzed_table')}})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        MIN(
            LEN({{lib.render_target_column('analyzed_table')}})
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    SELECT
        MIN(
            LENGTH({{ lib.render_column_cast_to_string('analyzed_table') }})
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string not match date regex count**
**Full sensor name**
```
column/strings/string_not_match_date_regex_count
```
**Description**  
Column level sensor that calculates the number of values that does not fit to a date regex in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|date_formats|Desired date format. Sensor will try to parse the column records and cast the data using this format.|enum| |YYYY-MM-DD<br/>Month D, YYYY<br/>MM/DD/YYYY<br/>YYYY/MM/DD<br/>DD/MM/YYYY<br/>|




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            '%Y-%m-%d'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            '%m/%d/%Y'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            '%d/%m/%Y'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            '%Y/%m/%d'
        {%- elif date_formats == 'Month D, YYYY'-%}
            '%b %d, %Y'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN SAFE.PARSE_DATE({{render_date_formats(parameters.date_formats)}}, {{ lib.render_target_column('analyzed_table') }}) IS NULL
                        THEN 1
                    ELSE 0
                END
            )
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD' -%}
            'yyyy-MM-dd'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            'MM/dd/yyyy'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            'dd/MM/yyyy'
        {%- elif date_formats == 'YYYY/MM/DD' -%}
            'yyyy/MM/dd'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN TO_DATE({{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}}) IS NULL
                        THEN 1
                    ELSE 0
                END
            )
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            '%Y-%m-%d'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            '%m/%d/%Y'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            '%d/%m/%Y'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            '%Y/%m/%d'
        {%- elif date_formats == 'Month D, YYYY'-%}
            '%b %d, %Y'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN STR_TO_DATE({{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}}) IS NULL
                        THEN 1
                    ELSE 0
                END
            )
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            'YYYY-MM-DD'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            'MM/DD/YYYY'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            'DD/MM/YYYY'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            'YYYY/MM/DD'
        {%- elif date_formats == 'Month D, YYYY'-%}
            'MON DD, YYYY'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN TO_DATE({{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}}) IS NULL
                        THEN 1
                    ELSE 0
                END
            )
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            '%YYYY-%MM-%DD'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            '%MM/%DD/%YYYY'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            '%DD/%MM/%YYYY'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            '%YYYY/%MM/%DD'
        {%- elif date_formats == 'Month D, YYYY'-%}
            '%MM %DD,%YYYY'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN TO_DATE({{ lib.render_target_column('analyzed_table') }}::VARCHAR, {{render_date_formats(parameters.date_formats)}}) IS NULL
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            '%Y-%m-%d'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            '%m/%d/%Y'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            '%d/%m/%Y'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            '%Y/%m/%d'
        {%- elif date_formats == 'Month D, YYYY'-%}
            '%b %d, %Y'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN TRY(DATE_PARSE({{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}})) IS NULL
                        THEN 1
                    ELSE 0
                END
            )
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            '%YYYY-%MM-%DD'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            '%MM/%DD/%YYYY'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            '%DD/%MM/%YYYY'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            '%YYYY/%MM/%DD'
        {%- elif date_formats == 'Month D, YYYY'-%}
            '%MM %DD,%YYYY'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN TO_DATE({{ lib.render_target_column('analyzed_table') }}::VARCHAR, {{render_date_formats(parameters.date_formats)}}) IS NULL
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            '%Y-%m-%d'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            '%m/%d/%Y'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            '%d/%m/%Y'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            '%Y/%m/%d'
        {%- elif date_formats == 'Month D, YYYY'-%}
            '%b %d, %Y'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN TRY_TO_DATE({{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}}) IS NULL
                        THEN 1
                    ELSE 0
                END
            )
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD' -%}
            'yyyy-MM-dd'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            'MM/dd/yyyy'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            'dd/MM/yyyy'
        {%- elif date_formats == 'YYYY/MM/DD' -%}
            'yyyy/MM/dd'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN TO_DATE({{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}}) IS NULL
                        THEN 1
                    ELSE 0
                END
            )
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            120
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            101
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            103
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            111
        {%- elif date_formats == 'Month D, YYYY'-%}
            107
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN TRY_CONVERT(DATETIME, {{ lib.quote_identifier(column_name) }}, {{render_date_formats(parameters.date_formats)}}) IS NULL
                        THEN 1
                    ELSE 0
                END
            )
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'YYYY-MM-DD'-%}
            '%Y-%m-%d'
        {%- elif date_formats == 'MM/DD/YYYY' -%}
            '%m/%d/%Y'
        {%- elif date_formats == 'DD/MM/YYYY' -%}
            '%d/%m/%Y'
        {%- elif date_formats == 'YYYY/MM/DD'-%}
            '%Y/%m/%d'
        {%- elif date_formats == 'Month D, YYYY'-%}
            '%b %d, %Y'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN TRY(DATE_PARSE({{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}})) IS NULL
                        THEN 1
                    ELSE 0
                END
            )
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string not match regex count**
**Full sensor name**
```
column/strings/string_not_match_regex_count
```
**Description**  
Column level sensor that calculates the number of values that does not fit to a regex in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|regex|This field can be used to define custom regex. In order to define custom regex, user should write correct regex as a string. If regex is not defined by user then default regex is null|string| ||




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         r{{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN REGEXP_CONTAINS({{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }})
                        THEN 0
                    ELSE 1
                END
            )
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP {{ render_regex(parameters.regex) }}
                        THEN 0
                    ELSE 1
                END
            )
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }})
                        THEN 0
                    ELSE 1
                END
            )
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }})
                        THEN 0
                    ELSE 1
                END
            )
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ ''{{ render_regex(parameters.regex) }}'' IS TRUE
                        THEN 0
                    ELSE 1
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }})
                        THEN 0
                    ELSE 1
                END
            )
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ {{ render_regex(parameters.regex) }}
                        THEN 0
                    ELSE 1
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP {{ render_regex(parameters.regex) }}
                        THEN 0
                    ELSE 1
                END
            )
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP {{ render_regex(parameters.regex) }}
                        THEN 0
                    ELSE 1
                END
            )
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE {{ render_regex(parameters.regex) }}
                        THEN 0
                    ELSE 1
                END
            )
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {%- macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }})
                        THEN 0
                    ELSE 1
                END
            )
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string null placeholder count**
**Full sensor name**
```
column/strings/string_null_placeholder_count
```
**Description**  
Column level sensor that calculates the number of rows with a null placeholder string column value.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN LOWER({{ render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '''', '-', '')
                    THEN 1
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
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\''\''', '-', '')
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '''''', '-', '')
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '-', '')
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\\', '-', '')
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '''''', '-', '')
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string null placeholder percent**
**Full sensor name**
```
column/strings/string_null_placeholder_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a null placeholder string column value.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
            CASE
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '''', '-', '')
                    THEN 1
                ELSE 0
            END
            ) / COUNT(*)
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\''\''', '-', '')
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '''''', '-', '')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '-', '')
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\\', '-', '')
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '''''', '-', '')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string parsable to float percent**
**Full sensor name**
```
column/strings/string_parsable_to_float_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with parsable to float string column value.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS FLOAT64)
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                CAST({{ lib.render_target_column('analyzed_table') }} AS FLOAT)
            ) / COUNT(*)
        END AS actual_value
        {{ lib.render_data_grouping_projections('analyzed_table') }}
        {{ lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{ lib.render_where_clause() }}
    {{ lib.render_group_by() }}
    {{ lib.render_order_by() }}
    ```
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }}
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }}
            ) / COUNT(*)
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }} ~ '^([0-9]+\.?[0-9]*|\.[0-9]+)$'
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * COUNT(
                TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DOUBLE)
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }} ~ '^([0-9]+\.?[0-9]*|\.[0-9]+)$'
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                TRY_TO_NUMERIC({{ lib.render_target_column('analyzed_table') }})
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                CAST({{ lib.render_target_column('analyzed_table') }} AS FLOAT)
            ) / COUNT(*)
        END AS actual_value
        {{ lib.render_data_grouping_projections('analyzed_table') }}
        {{ lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{ lib.render_where_clause() }}
    {{ lib.render_group_by() }}
    {{ lib.render_order_by() }}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                TRY_CONVERT(FLOAT, {{ lib.render_target_column('analyzed_table') }})
            ) / COUNT_BIG(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * COUNT(
                TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DOUBLE)
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string parsable to integer percent**
**Full sensor name**
```
column/strings/string_parsable_to_integer_percent
```
**Description**  
Column level sensor that calculates the number of rows with parsable to integer string column value.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS INT64)
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                CAST({{ lib.render_target_column('analyzed_table') }} AS INTEGER)
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[0-9]$')
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[0-9]*$') THEN 1
                    ELSE NULL
                END
            ) / COUNT(*)
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }} ~ '^[0-9]$'
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * COUNT(
                TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS BIGINT)
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }} ~ '^[0-9]$'
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                TRY_TO_NUMBER({{ lib.render_target_column('analyzed_table') }})
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                CAST({{ lib.render_target_column('analyzed_table') }} AS INTEGER)
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                TRY_CONVERT(INT, {{ lib.render_target_column('analyzed_table') }})
            ) / COUNT_BIG(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * COUNT(
                TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS BIGINT)
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string surrounded by whitespace count**
**Full sensor name**
```
column/strings/string_surrounded_by_whitespace_count
```
**Description**  
Column level sensor that calculates the number of rows with string surrounded by whitespace column value.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                AND ({{ lib.render_target_column('analyzed_table') }}) <> TRIM({{ lib.render_target_column('analyzed_table') }})
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                AND ({{ lib.render_target_column('analyzed_table') }}) <> TRIM({{ lib.render_target_column('analyzed_table') }})
                    THEN 1
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
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string surrounded by whitespace percent**
**Full sensor name**
```
column/strings/string_surrounded_by_whitespace_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with string surrounded by whitespace column value.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                    AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table') }}) <> TRIM({{ lib.render_target_column('analyzed_table') }})
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table') }}) <> TRIM({{ lib.render_target_column('analyzed_table') }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                    AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                    AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string valid country code percent**
**Full sensor name**
```
column/strings/string_valid_country_code_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid country code string column value.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string valid currency code percent**
**Full sensor name**
```
column/strings/string_valid_currency_code_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid currency code string column value.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ render_column_cast_to_string('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table') }}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table') }}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN UPPER({{ render_column_cast_to_string('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN UPPER({{ render_column_cast_to_string('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string valid date percent**
**Full sensor name**
```
column/strings/string_valid_date_percent
```
**Description**  
Column level sensor that ensures that there is at least a minimum percentage of valid dates in a monitored column..




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST({{render_column_cast_to_string('analyzed_table')}} AS DATE) IS NOT NULL
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS DATE) IS NOT NULL
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN CONVERT({{ lib.render_target_column('analyzed_table') }}, DATE) IS NOT NULL
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN TO_DATE({{ lib.render_target_column('analyzed_table') }}, 'YYYY-MM-DD HH24:MI:SS') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} ~ '^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)(?:0?2|(?:Feb))\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$'
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN TRY_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE) IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{lib.render_target_column('analyzed_table')}} SIMILAR TO '\\d{4}-\\d{2}-\\d{2}'
                        OR {{lib.render_target_column('analyzed_table')}} SIMILAR TO '\\d{2}-\\d{2}-\\d{4}'
                        OR {{lib.render_target_column('analyzed_table')}} SIMILAR TO '\\d{2}/\\d{2}/\\d{4}'
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN TRY_TO_DATE({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS DATE) IS NOT NULL
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN TRY_CAST({{ lib.render_target_column('analyzed_table')}} AS DATE) IS NOT NULL
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN TRY_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE) IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string valid uuid percent**
**Full sensor name**
```
column/strings/string_valid_uuid_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid UUID value in a column.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$")
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$'
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}')
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[0-9A-F]{8}([ -]?)[0-9A-F]{4}([ -]?)[0-9A-F]{4}\1[0-9A-F]{4}\1[0-9A-F]{12}$','i')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$'
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$'
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$'
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$'
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN TRY_CONVERT(UNIQUEIDENTIFIER,{{ lib.render_target_column('analyzed_table') }}) IS NOT NULL
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string value in set percent**
**Full sensor name**
```
column/strings/string_value_in_set_percent
```
**Description**  
Column level sensor that calculates the percentage of rows for which the tested string (text) column contains a value from the list of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 This sensor is useful for testing that a string column with a low number of unique values (country, currency, state, gender, etc.) contains only values from a set of expected values.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|expected_values|A list of expected values that must be present in a string column, only values from this list are accepted and rows having these values in the tested column are counted as valid rows.|string_list| ||




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"
      
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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
=== "PostgreSQL"
      
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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        CAST(0.0 AS DOUBLE)
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"
      
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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        CAST(0.0 AS DOUBLE)
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string whitespace count**
**Full sensor name**
```
column/strings/string_whitespace_count
```
**Description**  
Column level sensor that calculates the number of rows with an whitespace string column value.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_target_column('analyzed_table') }} <> ''
                AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_target_column('analyzed_table') }} <> ''
                AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
                    THEN 1
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
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **string whitespace percent**
**Full sensor name**
```
column/strings/string_whitespace_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a whitespace string column value.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
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
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                    AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
=== "MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_target_column('analyzed_table') }} <> ''
                    AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_target_column('analyzed_table') }} <> ''
                    AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                    AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
=== "Trino"
      
    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                    AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___
