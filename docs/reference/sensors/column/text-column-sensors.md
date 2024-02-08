# Data quality text sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **text** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## text length above max length count
Column level sensor that calculates the count of text values that are longer than a given length in a column.

**Sensor summary**

The text length above max length count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_length_above_max_length_count`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_length`</span>|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|*integer*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
                WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) > {{(parameters.max_length)}}
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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
                WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) > {{(parameters.max_length)}}
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
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) > {{(parameters.max_length)}}
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
                WHEN LEN({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
                WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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



## text length above max length percent
Column level sensor that calculates the percentage of text values that are longer than a given length in a column.

**Sensor summary**

The text length above max length percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_length_above_max_length_percent`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_length`</span>|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|*integer*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LEN({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
                        THEN 1
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



## text length below min length count
Column level sensor that calculates the count of text values that are shorter than a given length in a column.

**Sensor summary**

The text length below min length count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_length_below_min_length_count`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_length`</span>|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|*integer*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
                WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) < {{(parameters.min_length)}}
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
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) < {{(parameters.min_length)}}
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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
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
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
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
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
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
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
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
                WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) < {{(parameters.min_length)}}
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
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
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
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) < {{(parameters.min_length)}}
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
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) < {{(parameters.min_length)}}
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
                WHEN LEN({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
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
                WHEN LENGTH({{ lib.render_column_cast_to_string('analyzed_table')}}) < {{(parameters.min_length)}}
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



## text length below min length percent
Column level sensor that calculates the percentage of text values that are shorter than a given length in a column.

**Sensor summary**

The text length below min length percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_length_below_min_length_percent`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_length`</span>|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|*integer*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) < {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) < {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) < {{(parameters.min_length)}}
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) < {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) < {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LEN({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_column_cast_to_string('analyzed_table')}}) < {{(parameters.min_length)}}
                        THEN 1
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



## text length in range percent
Column level sensor that calculates the percentage of text values with a length below the indicated length in a column.

**Sensor summary**

The text length in range percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_length_in_range_percent`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_length`</span>|Sets a minimum text length|*integer*|:material-check-bold:||
|<span class="no-wrap-code">`max_length`</span>|Sets a maximum text length|*integer*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
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



## text max length
Column level sensor that ensures that the length of text values in a column does not exceed the maximum accepted length.

**Sensor summary**

The text max length sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_max_length`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
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



## text mean length
Column level sensor that ensures that the length of text values in a column does not exceed the mean accepted length.

**Sensor summary**

The text mean length sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_mean_length`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
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



## text min length
Column level sensor that ensures that the length of text values in a column does not exceed the minimum accepted length.

**Sensor summary**

The text min length sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_min_length`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
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



## text parsable to boolean percent
Column level sensor that calculates the number of rows with a text value in a text column that is parsable to a boolean type or is a well-known boolean value placeholder: yes, no, true, false, t, f, y, n, 1, 0.

**Sensor summary**

The text parsable to boolean percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_parsable_to_boolean_percent`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
                        THEN 1
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



## text parsable to date percent
Column level sensor that ensures that there is at least a minimum percentage of rows with a text value that is parsable to a date in an analyzed column.

**Sensor summary**

The text parsable to date percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_parsable_to_date_percent`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST({{render_column_cast_to_string('analyzed_table')}} AS DATE) IS NOT NULL
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS DATE) IS NOT NULL
                        THEN 1
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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{lib.render_target_column('analyzed_table')}} SIMILAR TO '\d{4}-\d{2}-\d{2}'
                         OR {{lib.render_target_column('analyzed_table')}} SIMILAR TO '\d{2}-\d{2}-\d{4}'
                         OR {{lib.render_target_column('analyzed_table')}} SIMILAR TO '\d{2}/\d{2}/\d{4}'
                         OR {{lib.render_target_column('analyzed_table')}} SIMILAR TO '\d{2}.\d{2}.\d{4}'
                         OR {{lib.render_target_column('analyzed_table')}} SIMILAR TO '\d{4}.\d{2}.\d{2}'
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN CONVERT({{ lib.render_target_column('analyzed_table') }}, DATE) IS NOT NULL
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN TO_DATE(
                            {{ lib.render_target_column('analyzed_table') }},
                            (select value from v$nls_parameters where parameter = 'NLS_DATE_FORMAT')
                        ) IS NOT NULL THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} ~ '^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)(?:0?2|(?:Feb))\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$'
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN TRY_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE) IS NOT NULL
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{lib.render_target_column('analyzed_table')}} SIMILAR TO '\\d{4}-\\d{2}-\\d{2}'
                        OR {{lib.render_target_column('analyzed_table')}} SIMILAR TO '\\d{2}-\\d{2}-\\d{4}'
                        OR {{lib.render_target_column('analyzed_table')}} SIMILAR TO '\\d{2}/\\d{2}/\\d{4}'
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN TRY_TO_DATE({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS DATE) IS NOT NULL
                        THEN 1
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN TRY_CAST({{ lib.render_target_column('analyzed_table')}} AS DATE) IS NOT NULL
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN TRY_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE) IS NOT NULL
                        THEN 1
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



## text parsable to float percent
Column level sensor that calculates the percentage of rows with text values in an analyzed column that are parsable to a float (numeric) value.

**Sensor summary**

The text parsable to float percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_parsable_to_float_percent`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS FLOAT64)
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                CAST({{ lib.render_target_column('analyzed_table') }} AS FLOAT)
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{ lib.render_data_grouping_projections('analyzed_table') }}
        {{ lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{ lib.render_where_clause() }}
    {{ lib.render_group_by() }}
    {{ lib.render_order_by() }}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }} ~ '^([0-9]+\.?[0-9]*|\.[0-9]+)$'
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
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }}
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
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }}
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }} ~ '^([0-9]+\.?[0-9]*|\.[0-9]+)$'
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
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * COUNT(
                TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DOUBLE)
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }} ~ '^([0-9]+\.?[0-9]*|\.[0-9]+)$'
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
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                TRY_TO_NUMERIC({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                CAST({{ lib.render_target_column('analyzed_table') }} AS FLOAT)
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                TRY_CONVERT(FLOAT, {{ lib.render_target_column('analyzed_table') }})
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
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * COUNT(
                TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DOUBLE)
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



## text parsable to integer percent
Column level sensor that calculates the percentage of rows with text values in an analyzed column that are parsable to an integer value.

**Sensor summary**

The text parsable to integer percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_parsable_to_integer_percent`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS INT64)
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                CAST({{ lib.render_target_column('analyzed_table') }} AS INTEGER)
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }} ~ '^[0-9]$'
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
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^[0-9]$') }}
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
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[0-9]*$') THEN 1
                    ELSE NULL
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }} ~ '^[0-9]$'
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
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * COUNT(
                TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS BIGINT)
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                {{ lib.render_target_column('analyzed_table') }} ~ '^[0-9]$'
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
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                TRY_TO_NUMBER({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                CAST({{ lib.render_target_column('analyzed_table') }} AS INTEGER)
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                TRY_CONVERT(INT, {{ lib.render_target_column('analyzed_table') }})
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
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * COUNT(
                TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS BIGINT)
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



## text surrounded by whitespace count
Column level sensor that calculates the number of rows with text values that are surrounded by whitespace characters in an analyzed column.

**Sensor summary**

The text surrounded by whitespace count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_surrounded_by_whitespace_count`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
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



## text surrounded by whitespace percent
Column level sensor that calculates the percentage of rows with text values that are surrounded by whitespace characters in an analyzed column.

**Sensor summary**

The text surrounded by whitespace percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_surrounded_by_whitespace_percent`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                    AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
                        THEN 1
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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table') }}) <> TRIM({{ lib.render_target_column('analyzed_table') }})
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table') }}) <> TRIM({{ lib.render_target_column('analyzed_table') }})
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                    AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
                        THEN 1
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                    AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
                        THEN 1
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



## text valid country code percent
Column level sensor that calculates the percentage of rows with text values with a valid country codes in an analyzed column.

**Sensor summary**

The text valid country code percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_valid_country_code_percent`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
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



## text valid currency code percent
Column level sensor that calculates the percentage of rows with text values that are valid currency codes in an analyzed column.

**Sensor summary**

The text valid currency code percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_valid_currency_code_percent`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ render_column_cast_to_string('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table') }}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table') }}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN UPPER({{ render_column_cast_to_string('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN UPPER({{ render_column_cast_to_string('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
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




## What's next
- Learn how the [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) are defined in DQOps and what is the definition of all Jinja2 macros used in the templates
- Understand how DQOps [runs data quality checks](../../../dqo-concepts/architecture/data-quality-check-execution-flow.md), rendering templates to SQL queries
