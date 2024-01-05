**expected strings in use count** checks

**Description**
Column-level check that counts unique values in a string column and counts how many values out of a list of expected string values were found in the column.
 The check raises a data quality issue when the threshold of maximum number of missing values was exceeded (too many expected values were not found in the column).
 This check is useful for analysing columns with a low number of unique values, such as status codes, to detect that all status codes are in use in any row.

___

## **profile expected strings in use count**


**Check description**
Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_expected_strings_in_use_count|profiling| |Reasonableness|[expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)|[max_missing](../../../../reference/rules/Comparison.md#max-missing)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=profile_expected_strings_in_use_count
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=profile_expected_strings_in_use_count
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=profile_expected_strings_in_use_count
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_expected_strings_in_use_count
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="10-23"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      profiling_checks:
        strings:
          profile_expected_strings_in_use_count:
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

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

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
    === "Rendered SQL for BigQuery"

        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Databricks"

    === "Sensor template for Databricks"

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
    === "Rendered SQL for Databricks"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                        THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
            END AS actual_value,
            MAX(CAST(3 AS INT)) AS expected_value_alias,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "MySQL"

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
    === "Rendered SQL for MySQL"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN MAX(0)
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Oracle"

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
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) expected_value,
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
??? example "PostgreSQL"

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
    === "Rendered SQL for PostgreSQL"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Presto"

    === "Sensor template for Presto"

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
    === "Rendered SQL for Presto"

        ```sql
        SELECT
            CAST(COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS BIGINT) AS actual_value,
            MAX(3 ) AS expected_value,
            time_period,
            time_period_utc
            FROM (
                SELECT
                    original_table.*,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
            CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

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
    === "Rendered SQL for Redshift"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

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
    === "Rendered SQL for Snowflake"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                        THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
            END AS actual_value,
            MAX(CAST(3 AS INT)) AS expected_value_alias,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

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
    === "Rendered SQL for SQL Server"

        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN NULL
                ELSE COUNT_BIG(DISTINCT
                CASE
                    WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.[target_column]
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        ```
??? example "Trino"

    === "Sensor template for Trino"

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
    === "Rendered SQL for Trino"

        ```sql
        SELECT
            CAST(COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS BIGINT) AS actual_value,
            MAX(3 ) AS expected_value,
            time_period,
            time_period_utc
            FROM (
                SELECT
                    original_table.*,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
            CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```


Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="8-18 35-40"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
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
              profile_expected_strings_in_use_count:
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
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                        CASE
                            WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                            THEN analyzed_table.`target_column`
                            ELSE NULL
                        END
                    )
                END AS actual_value,
                MAX(CAST(3 AS INT)) AS expected_value_alias,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN MAX(0)
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) expected_value,
            
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
    ??? example "PostgreSQL"

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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CAST(COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                ) AS BIGINT) AS actual_value,
                MAX(3 ) AS expected_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT
                        original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" original_table
                ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                        CASE
                            WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                            THEN analyzed_table.`target_column`
                            ELSE NULL
                        END
                    )
                END AS actual_value,
                MAX(CAST(3 AS INT)) AS expected_value_alias,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN NULL
                    ELSE COUNT_BIG(DISTINCT
                    CASE
                        WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.[target_column]
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CAST(COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                ) AS BIGINT) AS actual_value,
                MAX(3 ) AS expected_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT
                        original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" original_table
                ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___

## **daily expected strings in use count**


**Check description**
Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_expected_strings_in_use_count|monitoring|daily|Reasonableness|[expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)|[max_missing](../../../../reference/rules/Comparison.md#max-missing)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=daily_expected_strings_in_use_count
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=daily_expected_strings_in_use_count
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=daily_expected_strings_in_use_count
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_expected_strings_in_use_count
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="10-24"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      monitoring_checks:
        daily:
          strings:
            daily_expected_strings_in_use_count:
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

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

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
    === "Rendered SQL for BigQuery"

        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Databricks"

    === "Sensor template for Databricks"

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
    === "Rendered SQL for Databricks"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                        THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
            END AS actual_value,
            MAX(CAST(3 AS INT)) AS expected_value_alias,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "MySQL"

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
    === "Rendered SQL for MySQL"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN MAX(0)
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Oracle"

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
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) expected_value,
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
??? example "PostgreSQL"

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
    === "Rendered SQL for PostgreSQL"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Presto"

    === "Sensor template for Presto"

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
    === "Rendered SQL for Presto"

        ```sql
        SELECT
            CAST(COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS BIGINT) AS actual_value,
            MAX(3 ) AS expected_value,
            time_period,
            time_period_utc
            FROM (
                SELECT
                    original_table.*,
            CAST(CURRENT_TIMESTAMP AS date) AS time_period,
            CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

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
    === "Rendered SQL for Redshift"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

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
    === "Rendered SQL for Snowflake"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
            TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                        THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
            END AS actual_value,
            MAX(CAST(3 AS INT)) AS expected_value_alias,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

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
    === "Rendered SQL for SQL Server"

        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN NULL
                ELSE COUNT_BIG(DISTINCT
                CASE
                    WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.[target_column]
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        ```
??? example "Trino"

    === "Sensor template for Trino"

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
    === "Rendered SQL for Trino"

        ```sql
        SELECT
            CAST(COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS BIGINT) AS actual_value,
            MAX(3 ) AS expected_value,
            time_period,
            time_period_utc
            FROM (
                SELECT
                    original_table.*,
            CAST(CURRENT_TIMESTAMP AS date) AS time_period,
            CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```


Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="8-18 36-41"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
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
                daily_expected_strings_in_use_count:
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
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                        CASE
                            WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                            THEN analyzed_table.`target_column`
                            ELSE NULL
                        END
                    )
                END AS actual_value,
                MAX(CAST(3 AS INT)) AS expected_value_alias,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN MAX(0)
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) expected_value,
            
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
    ??? example "PostgreSQL"

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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CAST(COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                ) AS BIGINT) AS actual_value,
                MAX(3 ) AS expected_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT
                        original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" original_table
                ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
                TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                        CASE
                            WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                            THEN analyzed_table.`target_column`
                            ELSE NULL
                        END
                    )
                END AS actual_value,
                MAX(CAST(3 AS INT)) AS expected_value_alias,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN NULL
                    ELSE COUNT_BIG(DISTINCT
                    CASE
                        WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.[target_column]
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
                CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CAST(COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                ) AS BIGINT) AS actual_value,
                MAX(3 ) AS expected_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT
                        original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" original_table
                ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___

## **monthly expected strings in use count**


**Check description**
Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_expected_strings_in_use_count|monitoring|monthly|Reasonableness|[expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)|[max_missing](../../../../reference/rules/Comparison.md#max-missing)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=monthly_expected_strings_in_use_count
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=monthly_expected_strings_in_use_count
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=monthly_expected_strings_in_use_count
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_expected_strings_in_use_count
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="10-24"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      monitoring_checks:
        monthly:
          strings:
            monthly_expected_strings_in_use_count:
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

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

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
    === "Rendered SQL for BigQuery"

        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Databricks"

    === "Sensor template for Databricks"

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
    === "Rendered SQL for Databricks"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                        THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
            END AS actual_value,
            MAX(CAST(3 AS INT)) AS expected_value_alias,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "MySQL"

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
    === "Rendered SQL for MySQL"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN MAX(0)
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Oracle"

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
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) expected_value,
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
??? example "PostgreSQL"

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
    === "Rendered SQL for PostgreSQL"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Presto"

    === "Sensor template for Presto"

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
    === "Rendered SQL for Presto"

        ```sql
        SELECT
            CAST(COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS BIGINT) AS actual_value,
            MAX(3 ) AS expected_value,
            time_period,
            time_period_utc
            FROM (
                SELECT
                    original_table.*,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
            CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

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
    === "Rendered SQL for Redshift"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

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
    === "Rendered SQL for Snowflake"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                        THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
            END AS actual_value,
            MAX(CAST(3 AS INT)) AS expected_value_alias,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

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
    === "Rendered SQL for SQL Server"

        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN NULL
                ELSE COUNT_BIG(DISTINCT
                CASE
                    WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.[target_column]
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        ```
??? example "Trino"

    === "Sensor template for Trino"

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
    === "Rendered SQL for Trino"

        ```sql
        SELECT
            CAST(COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS BIGINT) AS actual_value,
            MAX(3 ) AS expected_value,
            time_period,
            time_period_utc
            FROM (
                SELECT
                    original_table.*,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
            CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```


Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="8-18 36-41"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
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
                monthly_expected_strings_in_use_count:
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
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                        CASE
                            WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                            THEN analyzed_table.`target_column`
                            ELSE NULL
                        END
                    )
                END AS actual_value,
                MAX(CAST(3 AS INT)) AS expected_value_alias,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN MAX(0)
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) expected_value,
            
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
    ??? example "PostgreSQL"

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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CAST(COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                ) AS BIGINT) AS actual_value,
                MAX(3 ) AS expected_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT
                        original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" original_table
                ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                        CASE
                            WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                            THEN analyzed_table.`target_column`
                            ELSE NULL
                        END
                    )
                END AS actual_value,
                MAX(CAST(3 AS INT)) AS expected_value_alias,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN NULL
                    ELSE COUNT_BIG(DISTINCT
                    CASE
                        WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.[target_column]
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CAST(COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                ) AS BIGINT) AS actual_value,
                MAX(3 ) AS expected_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT
                        original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" original_table
                ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___

## **daily partition expected strings in use count**


**Check description**
Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_partition_expected_strings_in_use_count|partitioned|daily|Reasonableness|[expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)|[max_missing](../../../../reference/rules/Comparison.md#max-missing)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=daily_partition_expected_strings_in_use_count
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=daily_partition_expected_strings_in_use_count
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=daily_partition_expected_strings_in_use_count
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_expected_strings_in_use_count
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="12-26"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    partition_by_column: date_column
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      partitioned_checks:
        daily:
          strings:
            daily_partition_expected_strings_in_use_count:
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
    date_column:
      labels:
      - "date or datetime column used as a daily or monthly partitioning key, dates\
        \ (and times) are truncated to a day or a month by the sensor's query for\
        \ partitioned checks"

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

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
    === "Rendered SQL for BigQuery"

        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            CAST(analyzed_table.`date_column` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Databricks"

    === "Sensor template for Databricks"

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
    === "Rendered SQL for Databricks"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                        THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
            END AS actual_value,
            MAX(CAST(3 AS INT)) AS expected_value_alias,
            CAST(analyzed_table.`date_column` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "MySQL"

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
    === "Rendered SQL for MySQL"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN MAX(0)
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Oracle"

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
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) expected_value,
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
??? example "PostgreSQL"

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
    === "Rendered SQL for PostgreSQL"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            CAST(analyzed_table."date_column" AS date) AS time_period,
            CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Presto"

    === "Sensor template for Presto"

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
    === "Rendered SQL for Presto"

        ```sql
        SELECT
            CAST(COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS BIGINT) AS actual_value,
            MAX(3 ) AS expected_value,
            time_period,
            time_period_utc
            FROM (
                SELECT
                    original_table.*,
            CAST(original_table."date_column" AS date) AS time_period,
            CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

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
    === "Rendered SQL for Redshift"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            CAST(analyzed_table."date_column" AS date) AS time_period,
            CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

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
    === "Rendered SQL for Snowflake"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            CAST(analyzed_table."date_column" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                        THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
            END AS actual_value,
            MAX(CAST(3 AS INT)) AS expected_value_alias,
            CAST(analyzed_table.`date_column` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

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
    === "Rendered SQL for SQL Server"

        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN NULL
                ELSE COUNT_BIG(DISTINCT
                CASE
                    WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.[target_column]
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            CAST(analyzed_table.[date_column] AS date) AS time_period,
            CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
        ORDER BY CAST(analyzed_table.[date_column] AS date)
        
            
        ```
??? example "Trino"

    === "Sensor template for Trino"

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
    === "Rendered SQL for Trino"

        ```sql
        SELECT
            CAST(COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS BIGINT) AS actual_value,
            MAX(3 ) AS expected_value,
            time_period,
            time_period_utc
            FROM (
                SELECT
                    original_table.*,
            CAST(original_table."date_column" AS date) AS time_period,
            CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```


Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="10-20 43-48"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
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
                daily_partition_expected_strings_in_use_count:
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

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                        CASE
                            WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                            THEN analyzed_table.`target_column`
                            ELSE NULL
                        END
                    )
                END AS actual_value,
                MAX(CAST(3 AS INT)) AS expected_value_alias,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN MAX(0)
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) expected_value,
            
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
    ??? example "PostgreSQL"

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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CAST(COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                ) AS BIGINT) AS actual_value,
                MAX(3 ) AS expected_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT
                        original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(original_table."date_column" AS date) AS time_period,
                CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" original_table
                ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                        CASE
                            WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                            THEN analyzed_table.`target_column`
                            ELSE NULL
                        END
                    )
                END AS actual_value,
                MAX(CAST(3 AS INT)) AS expected_value_alias,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN NULL
                    ELSE COUNT_BIG(DISTINCT
                    CASE
                        WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.[target_column]
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                CAST(analyzed_table.[date_column] AS date) AS time_period,
                CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state], CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
            ORDER BY level_1, level_2CAST(analyzed_table.[date_column] AS date)
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CAST(COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                ) AS BIGINT) AS actual_value,
                MAX(3 ) AS expected_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT
                        original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(original_table."date_column" AS date) AS time_period,
                CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" original_table
                ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___

## **monthly partition expected strings in use count**


**Check description**
Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_partition_expected_strings_in_use_count|partitioned|monthly|Reasonableness|[expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)|[max_missing](../../../../reference/rules/Comparison.md#max-missing)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=monthly_partition_expected_strings_in_use_count
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=monthly_partition_expected_strings_in_use_count
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=monthly_partition_expected_strings_in_use_count
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_partition_expected_strings_in_use_count
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="12-26"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    partition_by_column: date_column
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      partitioned_checks:
        monthly:
          strings:
            monthly_partition_expected_strings_in_use_count:
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
    date_column:
      labels:
      - "date or datetime column used as a daily or monthly partitioning key, dates\
        \ (and times) are truncated to a day or a month by the sensor's query for\
        \ partitioned checks"

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

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
    === "Rendered SQL for BigQuery"

        ```sql
        SELECT
            COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            ) AS actual_value,
            MAX(3) AS expected_value,
            DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Databricks"

    === "Sensor template for Databricks"

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
    === "Rendered SQL for Databricks"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                        THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
            END AS actual_value,
            MAX(CAST(3 AS INT)) AS expected_value_alias,
            DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "MySQL"

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
    === "Rendered SQL for MySQL"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN MAX(0)
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.`target_column`
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Oracle"

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
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) expected_value,
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
??? example "PostgreSQL"

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
    === "Rendered SQL for PostgreSQL"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Presto"

    === "Sensor template for Presto"

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
    === "Rendered SQL for Presto"

        ```sql
        SELECT
            CAST(COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS BIGINT) AS actual_value,
            MAX(3 ) AS expected_value,
            time_period,
            time_period_utc
            FROM (
                SELECT
                    original_table.*,
            DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
            CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

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
    === "Rendered SQL for Redshift"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

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
    === "Rendered SQL for Snowflake"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN NULL
                ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                        THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
            END AS actual_value,
            MAX(CAST(3 AS INT)) AS expected_value_alias,
            DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
        FROM `<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

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
    === "Rendered SQL for SQL Server"

        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN NULL
                ELSE COUNT_BIG(DISTINCT
                CASE
                    WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table.[target_column]
                    ELSE NULL
                END
            )
            END AS actual_value,
            MAX(3) AS expected_value,
            DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
        ORDER BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
        
            
        ```
??? example "Trino"

    === "Sensor template for Trino"

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
    === "Rendered SQL for Trino"

        ```sql
        SELECT
            CAST(COUNT(DISTINCT
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN analyzed_table."target_column"
                    ELSE NULL
                END
            ) AS BIGINT) AS actual_value,
            MAX(3 ) AS expected_value,
            time_period,
            time_period_utc
            FROM (
                SELECT
                    original_table.*,
            DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
            CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```


Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="10-20 43-48"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
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
                monthly_partition_expected_strings_in_use_count:
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

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [expected_strings_in_use_count](../../../../reference/sensors/column/strings-column-sensors.md#expected-strings-in-use-count)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                        CASE
                            WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                            THEN analyzed_table.`target_column`
                            ELSE NULL
                        END
                    )
                END AS actual_value,
                MAX(CAST(3 AS INT)) AS expected_value_alias,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN MAX(0)
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.`target_column`
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) expected_value,
            
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
    ??? example "PostgreSQL"

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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CAST(COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                ) AS BIGINT) AS actual_value,
                MAX(3 ) AS expected_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT
                        original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" original_table
                ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE COUNT(DISTINCT
                        CASE
                            WHEN analyzed_table.`target_column` IN ('USD','GBP','EUR')
                            THEN analyzed_table.`target_column`
                            ELSE NULL
                        END
                    )
                END AS actual_value,
                MAX(CAST(3 AS INT)) AS expected_value_alias,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN NULL
                    ELSE COUNT_BIG(DISTINCT
                    CASE
                        WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table.[target_column]
                        ELSE NULL
                    END
                )
                END AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state], DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
            ORDER BY level_1, level_2DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CAST(COUNT(DISTINCT
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN analyzed_table."target_column"
                        ELSE NULL
                    END
                ) AS BIGINT) AS actual_value,
                MAX(3 ) AS expected_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT
                        original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" original_table
                ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___
