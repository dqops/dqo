---
title: duplicate record count data quality checks
---
# duplicate record count data quality checks

This check counts duplicate records values. It raises a data quality issue when the number of duplicates is above a minimum accepted value.
 The default configuration detects duplicate rows by enforcing that the *min_count* of duplicates is zero.


___
The **duplicate record count** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile duplicate record count


**Check description**

Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_duplicate_record_count`</span>|Maximum count of duplicate records|[uniqueness](../../../categories-of-data-quality-checks/how-to-detect-data-uniqueness-issues-and-duplicates.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |[Uniqueness](../../../dqo-concepts/data-quality-dimensions.md#data-uniqueness)|[*duplicate_record_count*](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)|[*max_count*](../../../reference/rules/Comparison.md#max-count)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile duplicate record count data quality check.

??? example "Managing profile duplicate record count check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_duplicate_record_count --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_duplicate_record_count --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_duplicate_record_count --enable-warning
                            -Wmax_count=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_duplicate_record_count --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_duplicate_record_count --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_duplicate_record_count --enable-error
                            -Emax_count=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_duplicate_record_count* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_duplicate_record_count
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_duplicate_record_count
        ```

        You can also run this check on all tables  on which the *profile_duplicate_record_count* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_duplicate_record_count
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-8"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  profiling_checks:
    uniqueness:
      profile_duplicate_record_count:
        parameters:
          columns:
          - id
          - created_at
        warning:
          max_count: 0
        error:
          max_count: 10
        fatal:
          max_count: 100
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [duplicate_record_count](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST( ', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM  AS analyzed_table
                WHERE (COALESCE(CAST( "id" AS VARCHAR), CAST( "created_at" AS VARCHAR)) IS NOT NULL)
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MariaDB"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE(CAST("id" AS STRING), CAST("created_at" AS STRING)) IS NOT NULL)
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            {% macro render_group_by(table_alias_prefix = 'grouping_table', indentation = '    ') %}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
                GROUP BY
                {%- endif -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{- ',' if not loop.first -}}{{- lib.eol() }}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute -}}
                    {%- endfor -%}
                {%- endif -%}
                {%- if lib.time_series is not none -%}
                    {{ ',' if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}{{- lib.eol() -}}
                    {{ indentation }}time_period,{{ lib.eol() -}}
                    {{ indentation }}time_period_utc
                {%- endif -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{ render_group_by('grouping_table') }}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                WHERE (COALESCE(CAST([id] AS VARCHAR), CAST([created_at] AS VARCHAR)) IS NOT NULL)
                GROUP BY [id], [created_at]
            ) grouping_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 28-33"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      profiling_checks:
        uniqueness:
          profile_duplicate_record_count:
            parameters:
              columns:
              - id
              - created_at
            warning:
              max_count: 0
            error:
              max_count: 10
            fatal:
              max_count: 100
      columns:
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [duplicate_record_count](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                grouping_table.grouping_level_1,
                grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                grouping_table.grouping_level_1,
                grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                    analyzed_table_nested.grouping_level_1,
            
                                    analyzed_table_nested.grouping_level_2
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST( ', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2
                FROM  AS analyzed_table
                WHERE (COALESCE(CAST( "id" AS VARCHAR), CAST( "created_at" AS VARCHAR)) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"
            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MariaDB"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                    analyzed_table_nested.grouping_level_1,
            
                                    analyzed_table_nested.grouping_level_2
            
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2
            
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2
                FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2
                FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE(CAST("id" AS STRING), CAST("created_at" AS STRING)) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            {% macro render_group_by(table_alias_prefix = 'grouping_table', indentation = '    ') %}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
                GROUP BY
                {%- endif -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{- ',' if not loop.first -}}{{- lib.eol() }}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute -}}
                    {%- endfor -%}
                {%- endif -%}
                {%- if lib.time_series is not none -%}
                    {{ ',' if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}{{- lib.eol() -}}
                    {{ indentation }}time_period,{{ lib.eol() -}}
                    {{ indentation }}time_period_utc
                {%- endif -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{ render_group_by('grouping_table') }}
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.[country] AS grouping_level_1,
                    analyzed_table.[state] AS grouping_level_2
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                WHERE (COALESCE(CAST([id] AS VARCHAR), CAST([created_at] AS VARCHAR)) IS NOT NULL)
                GROUP BY [id], [created_at], analyzed_table.[country], analyzed_table.[state]
            ) grouping_table
            GROUP BY
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2
            
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    
___


## daily duplicate record count


**Check description**

Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_duplicate_record_count`</span>|Maximum count of duplicate records|[uniqueness](../../../categories-of-data-quality-checks/how-to-detect-data-uniqueness-issues-and-duplicates.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|[Uniqueness](../../../dqo-concepts/data-quality-dimensions.md#data-uniqueness)|[*duplicate_record_count*](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)|[*max_count*](../../../reference/rules/Comparison.md#max-count)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily duplicate record count data quality check.

??? example "Managing daily duplicate record count check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_duplicate_record_count --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_duplicate_record_count --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_duplicate_record_count --enable-warning
                            -Wmax_count=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_duplicate_record_count --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_duplicate_record_count --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_duplicate_record_count --enable-error
                            -Emax_count=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_duplicate_record_count* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_duplicate_record_count
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_duplicate_record_count
        ```

        You can also run this check on all tables  on which the *daily_duplicate_record_count* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_duplicate_record_count
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-9"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    daily:
      uniqueness:
        daily_duplicate_record_count:
          parameters:
            columns:
            - id
            - created_at
          warning:
            max_count: 0
          error:
            max_count: 10
          fatal:
            max_count: 100
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [duplicate_record_count](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST( ', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM  AS analyzed_table
                WHERE (COALESCE(CAST( "id" AS VARCHAR), CAST( "created_at" AS VARCHAR)) IS NOT NULL)
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MariaDB"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE(CAST("id" AS STRING), CAST("created_at" AS STRING)) IS NOT NULL)
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            {% macro render_group_by(table_alias_prefix = 'grouping_table', indentation = '    ') %}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
                GROUP BY
                {%- endif -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{- ',' if not loop.first -}}{{- lib.eol() }}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute -}}
                    {%- endfor -%}
                {%- endif -%}
                {%- if lib.time_series is not none -%}
                    {{ ',' if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}{{- lib.eol() -}}
                    {{ indentation }}time_period,{{ lib.eol() -}}
                    {{ indentation }}time_period_utc
                {%- endif -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{ render_group_by('grouping_table') }}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                WHERE (COALESCE(CAST([id] AS VARCHAR), CAST([created_at] AS VARCHAR)) IS NOT NULL)
                GROUP BY [id], [created_at]
            ) grouping_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 29-34"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      monitoring_checks:
        daily:
          uniqueness:
            daily_duplicate_record_count:
              parameters:
                columns:
                - id
                - created_at
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 100
      columns:
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [duplicate_record_count](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                grouping_table.grouping_level_1,
                grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                grouping_table.grouping_level_1,
                grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                    analyzed_table_nested.grouping_level_1,
            
                                    analyzed_table_nested.grouping_level_2
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST( ', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2
                FROM  AS analyzed_table
                WHERE (COALESCE(CAST( "id" AS VARCHAR), CAST( "created_at" AS VARCHAR)) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"
            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MariaDB"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                    analyzed_table_nested.grouping_level_1,
            
                                    analyzed_table_nested.grouping_level_2
            
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2
            
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2
                FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2
                FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE(CAST("id" AS STRING), CAST("created_at" AS STRING)) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            {% macro render_group_by(table_alias_prefix = 'grouping_table', indentation = '    ') %}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
                GROUP BY
                {%- endif -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{- ',' if not loop.first -}}{{- lib.eol() }}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute -}}
                    {%- endfor -%}
                {%- endif -%}
                {%- if lib.time_series is not none -%}
                    {{ ',' if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}{{- lib.eol() -}}
                    {{ indentation }}time_period,{{ lib.eol() -}}
                    {{ indentation }}time_period_utc
                {%- endif -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{ render_group_by('grouping_table') }}
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.[country] AS grouping_level_1,
                    analyzed_table.[state] AS grouping_level_2
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                WHERE (COALESCE(CAST([id] AS VARCHAR), CAST([created_at] AS VARCHAR)) IS NOT NULL)
                GROUP BY [id], [created_at], analyzed_table.[country], analyzed_table.[state]
            ) grouping_table
            GROUP BY
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2
            
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    
___


## monthly duplicate record count


**Check description**

Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_duplicate_record_count`</span>|Maximum count of duplicate records|[uniqueness](../../../categories-of-data-quality-checks/how-to-detect-data-uniqueness-issues-and-duplicates.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|monthly|[Uniqueness](../../../dqo-concepts/data-quality-dimensions.md#data-uniqueness)|[*duplicate_record_count*](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)|[*max_count*](../../../reference/rules/Comparison.md#max-count)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly duplicate record count data quality check.

??? example "Managing monthly duplicate record count check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_duplicate_record_count --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_duplicate_record_count --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_duplicate_record_count --enable-warning
                            -Wmax_count=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_duplicate_record_count --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_duplicate_record_count --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_duplicate_record_count --enable-error
                            -Emax_count=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_duplicate_record_count* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_duplicate_record_count
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_duplicate_record_count
        ```

        You can also run this check on all tables  on which the *monthly_duplicate_record_count* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_duplicate_record_count
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-9"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    monthly:
      uniqueness:
        monthly_duplicate_record_count:
          parameters:
            columns:
            - id
            - created_at
          warning:
            max_count: 0
          error:
            max_count: 10
          fatal:
            max_count: 100
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [duplicate_record_count](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST( ', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM  AS analyzed_table
                WHERE (COALESCE(CAST( "id" AS VARCHAR), CAST( "created_at" AS VARCHAR)) IS NOT NULL)
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MariaDB"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE(CAST("id" AS STRING), CAST("created_at" AS STRING)) IS NOT NULL)
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`
            ) grouping_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            {% macro render_group_by(table_alias_prefix = 'grouping_table', indentation = '    ') %}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
                GROUP BY
                {%- endif -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{- ',' if not loop.first -}}{{- lib.eol() }}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute -}}
                    {%- endfor -%}
                {%- endif -%}
                {%- if lib.time_series is not none -%}
                    {{ ',' if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}{{- lib.eol() -}}
                    {{ indentation }}time_period,{{ lib.eol() -}}
                    {{ indentation }}time_period_utc
                {%- endif -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{ render_group_by('grouping_table') }}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
            FROM (
                SELECT COUNT(*) AS duplicated_count
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                WHERE (COALESCE(CAST([id] AS VARCHAR), CAST([created_at] AS VARCHAR)) IS NOT NULL)
                GROUP BY [id], [created_at]
            ) grouping_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    FROM (
                        SELECT
                            "id", "created_at"
                        FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at"
            ) grouping_table
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 29-34"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      monitoring_checks:
        monthly:
          uniqueness:
            monthly_duplicate_record_count:
              parameters:
                columns:
                - id
                - created_at
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 100
      columns:
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [duplicate_record_count](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                grouping_table.grouping_level_1,
                grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                grouping_table.grouping_level_1,
                grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                    analyzed_table_nested.grouping_level_1,
            
                                    analyzed_table_nested.grouping_level_2
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST( ', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2
                FROM  AS analyzed_table
                WHERE (COALESCE(CAST( "id" AS VARCHAR), CAST( "created_at" AS VARCHAR)) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"
            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MariaDB"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                    analyzed_table_nested.grouping_level_1,
            
                                    analyzed_table_nested.grouping_level_2
            
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2
            
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2
                FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2
                FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE(CAST("id" AS STRING), CAST("created_at" AS STRING)) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            {% macro render_group_by(table_alias_prefix = 'grouping_table', indentation = '    ') %}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
                GROUP BY
                {%- endif -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{- ',' if not loop.first -}}{{- lib.eol() }}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute -}}
                    {%- endfor -%}
                {%- endif -%}
                {%- if lib.time_series is not none -%}
                    {{ ',' if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}{{- lib.eol() -}}
                    {{ indentation }}time_period,{{ lib.eol() -}}
                    {{ indentation }}time_period_utc
                {%- endif -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{ render_group_by('grouping_table') }}
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.[country] AS grouping_level_1,
                    analyzed_table.[state] AS grouping_level_2
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                WHERE (COALESCE(CAST([id] AS VARCHAR), CAST([created_at] AS VARCHAR)) IS NOT NULL)
                GROUP BY [id], [created_at], analyzed_table.[country], analyzed_table.[state]
            ) grouping_table
            GROUP BY
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2
            
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2
                        FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    
___


## daily partition duplicate record count


**Check description**

Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_partition_duplicate_record_count`</span>|Maximum count of duplicate records|[uniqueness](../../../categories-of-data-quality-checks/how-to-detect-data-uniqueness-issues-and-duplicates.md)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|daily|[Uniqueness](../../../dqo-concepts/data-quality-dimensions.md#data-uniqueness)|[*duplicate_record_count*](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)|[*max_count*](../../../reference/rules/Comparison.md#max-count)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily partition duplicate record count data quality check.

??? example "Managing daily partition duplicate record count check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_partition_duplicate_record_count --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_partition_duplicate_record_count --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_partition_duplicate_record_count --enable-warning
                            -Wmax_count=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_partition_duplicate_record_count --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_partition_duplicate_record_count --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_partition_duplicate_record_count --enable-error
                            -Emax_count=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_partition_duplicate_record_count* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_partition_duplicate_record_count
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_duplicate_record_count
        ```

        You can also run this check on all tables  on which the *daily_partition_duplicate_record_count* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_partition_duplicate_record_count
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="10-14"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    partition_by_column: date_column
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  partitioned_checks:
    daily:
      uniqueness:
        daily_partition_duplicate_record_count:
          parameters:
            columns:
            - id
            - created_at
          warning:
            max_count: 0
          error:
            max_count: 10
          fatal:
            max_count: 100
  columns:
    date_column:
      labels:
      - "date or datetime column used as a daily or monthly partitioning key, dates\
        \ (and times) are truncated to a day or a month by the sensor's query for\
        \ partitioned checks"

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [duplicate_record_count](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
                        time_period,
                        time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        CAST(analyzed_table_nested."date_column" AS DATE) AS time_period,
                        TIMESTAMP(CAST(analyzed_table_nested."date_column" AS DATE)) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST( ', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM  AS analyzed_table
                WHERE (COALESCE(CAST( "id" AS VARCHAR), CAST( "created_at" AS VARCHAR)) IS NOT NULL)
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
                    time_period,
                    time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        CAST(analyzed_table_nested."date_column" AS DATE) AS time_period,
                        TO_TIMESTAMP(CAST(analyzed_table_nested."date_column" AS DATE)) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MariaDB"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                    FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                    FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
                        time_period,
                        time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        TRUNC(CAST(analyzed_table_nested."date_column" AS DATE)) AS time_period,
                        CAST(TRUNC(CAST(analyzed_table_nested."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
                    time_period,
                    time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        CAST(analyzed_table_nested."date_column" AS date) AS time_period,
                        CAST(CAST(analyzed_table_nested."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                        FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
                FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE(CAST("id" AS STRING), CAST("created_at" AS STRING)) IS NOT NULL)
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            {% macro render_group_by(table_alias_prefix = 'grouping_table', indentation = '    ') %}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
                GROUP BY
                {%- endif -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{- ',' if not loop.first -}}{{- lib.eol() }}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute -}}
                    {%- endfor -%}
                {%- endif -%}
                {%- if lib.time_series is not none -%}
                    {{ ',' if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}{{- lib.eol() -}}
                    {{ indentation }}time_period,{{ lib.eol() -}}
                    {{ indentation }}time_period_utc
                {%- endif -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{ render_group_by('grouping_table') }}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    CAST(analyzed_table.[date_column] AS date) AS time_period,
                    CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                WHERE (COALESCE(CAST([id] AS VARCHAR), CAST([created_at] AS VARCHAR)) IS NOT NULL)
                GROUP BY [id], [created_at], CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
            ) grouping_table
            GROUP BY
                time_period,
                time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
                    time_period,
                    time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        CAST(analyzed_table_nested."date_column" AS date) AS time_period,
                        CAST(CAST(analyzed_table_nested."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                        FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="10-4 39-44"
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
      partitioned_checks:
        daily:
          uniqueness:
            daily_partition_duplicate_record_count:
              parameters:
                columns:
                - id
                - created_at
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 100
      columns:
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
    [duplicate_record_count](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                grouping_table.grouping_level_1,
                grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                grouping_table.grouping_level_1,
                grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                    analyzed_table_nested.grouping_level_1,
            
                                    analyzed_table_nested.grouping_level_2,
                        time_period,
                        time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2,
                        CAST(analyzed_table_nested."date_column" AS DATE) AS time_period,
                        TIMESTAMP(CAST(analyzed_table_nested."date_column" AS DATE)) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST( ', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM  AS analyzed_table
                WHERE (COALESCE(CAST( "id" AS VARCHAR), CAST( "created_at" AS VARCHAR)) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2,
                    time_period,
                    time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2,
                        CAST(analyzed_table_nested."date_column" AS DATE) AS time_period,
                        TO_TIMESTAMP(CAST(analyzed_table_nested."date_column" AS DATE)) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"
            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MariaDB"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                    FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                    FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                    analyzed_table_nested.grouping_level_1,
            
                                    analyzed_table_nested.grouping_level_2
            ,
                        time_period,
                        time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2,
                        TRUNC(CAST(analyzed_table_nested."date_column" AS DATE)) AS time_period,
                        CAST(TRUNC(CAST(analyzed_table_nested."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2
            ,
                    time_period,
                    time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2,
                        CAST(analyzed_table_nested."date_column" AS date) AS time_period,
                        CAST(CAST(analyzed_table_nested."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                        FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
                FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE(CAST("id" AS STRING), CAST("created_at" AS STRING)) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            {% macro render_group_by(table_alias_prefix = 'grouping_table', indentation = '    ') %}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
                GROUP BY
                {%- endif -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{- ',' if not loop.first -}}{{- lib.eol() }}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute -}}
                    {%- endfor -%}
                {%- endif -%}
                {%- if lib.time_series is not none -%}
                    {{ ',' if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}{{- lib.eol() -}}
                    {{ indentation }}time_period,{{ lib.eol() -}}
                    {{ indentation }}time_period_utc
                {%- endif -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{ render_group_by('grouping_table') }}
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.[country] AS grouping_level_1,
                    analyzed_table.[state] AS grouping_level_2,
                    CAST(analyzed_table.[date_column] AS date) AS time_period,
                    CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                WHERE (COALESCE(CAST([id] AS VARCHAR), CAST([created_at] AS VARCHAR)) IS NOT NULL)
                GROUP BY [id], [created_at], analyzed_table.[country], analyzed_table.[state], CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
            ) grouping_table
            GROUP BY
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2
            ,
                    time_period,
                    time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2,
                        CAST(analyzed_table_nested."date_column" AS date) AS time_period,
                        CAST(CAST(analyzed_table_nested."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                        FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___


## monthly partition duplicate record count


**Check description**

Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_partition_duplicate_record_count`</span>|Maximum count of duplicate records|[uniqueness](../../../categories-of-data-quality-checks/how-to-detect-data-uniqueness-issues-and-duplicates.md)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|monthly|[Uniqueness](../../../dqo-concepts/data-quality-dimensions.md#data-uniqueness)|[*duplicate_record_count*](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)|[*max_count*](../../../reference/rules/Comparison.md#max-count)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly partition duplicate record count data quality check.

??? example "Managing monthly partition duplicate record count check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_partition_duplicate_record_count --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_partition_duplicate_record_count --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_partition_duplicate_record_count --enable-warning
                            -Wmax_count=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_partition_duplicate_record_count --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_partition_duplicate_record_count --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_partition_duplicate_record_count --enable-error
                            -Emax_count=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_partition_duplicate_record_count* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_partition_duplicate_record_count
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_partition_duplicate_record_count
        ```

        You can also run this check on all tables  on which the *monthly_partition_duplicate_record_count* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_partition_duplicate_record_count
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="10-14"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    partition_by_column: date_column
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  partitioned_checks:
    monthly:
      uniqueness:
        monthly_partition_duplicate_record_count:
          parameters:
            columns:
            - id
            - created_at
          warning:
            max_count: 0
          error:
            max_count: 10
          fatal:
            max_count: 100
  columns:
    date_column:
      labels:
      - "date or datetime column used as a daily or monthly partitioning key, dates\
        \ (and times) are truncated to a day or a month by the sensor's query for\
        \ partitioned checks"

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [duplicate_record_count](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
                    TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                    TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
                        time_period,
                        time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        DATE_TRUNC('MONTH', CAST(analyzed_table_nested."date_column" AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table_nested."date_column" AS DATE))) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST( ', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                    CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM  AS analyzed_table
                WHERE (COALESCE(CAST( "id" AS VARCHAR), CAST( "created_at" AS VARCHAR)) IS NOT NULL)
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
                    time_period,
                    time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        SERIES_ROUND(CAST(analyzed_table_nested."date_column" AS DATE), 'INTERVAL 1 MONTH', ROUND_DOWN) AS time_period,
                        TO_TIMESTAMP(SERIES_ROUND(CAST(analyzed_table_nested."date_column" AS DATE), 'INTERVAL 1 MONTH', ROUND_DOWN)) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MariaDB"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                    FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                    FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
                        time_period,
                        time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        TRUNC(CAST(analyzed_table_nested."date_column" AS DATE), 'MONTH') AS time_period,
                        CAST(TRUNC(CAST(analyzed_table_nested."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                    CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
                    time_period,
                    time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        DATE_TRUNC('MONTH', CAST(analyzed_table_nested."date_column" AS date)) AS time_period,
                        CAST(DATE_TRUNC('MONTH', CAST(analyzed_table_nested."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                        FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                    CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                    TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
                FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE(CAST("id" AS STRING), CAST("created_at" AS STRING)) IS NOT NULL)
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                    TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            {% macro render_group_by(table_alias_prefix = 'grouping_table', indentation = '    ') %}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
                GROUP BY
                {%- endif -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{- ',' if not loop.first -}}{{- lib.eol() }}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute -}}
                    {%- endfor -%}
                {%- endif -%}
                {%- if lib.time_series is not none -%}
                    {{ ',' if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}{{- lib.eol() -}}
                    {{ indentation }}time_period,{{ lib.eol() -}}
                    {{ indentation }}time_period_utc
                {%- endif -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{ render_group_by('grouping_table') }}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                    CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                WHERE (COALESCE(CAST([id] AS VARCHAR), CAST([created_at] AS VARCHAR)) IS NOT NULL)
                GROUP BY [id], [created_at], DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
            ) grouping_table
            GROUP BY
                time_period,
                time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
                    time_period,
                    time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        DATE_TRUNC('MONTH', CAST(analyzed_table_nested."date_column" AS date)) AS time_period,
                        CAST(DATE_TRUNC('MONTH', CAST(analyzed_table_nested."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                        FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", time_period, time_period_utc
            ) grouping_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="10-4 39-44"
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
      partitioned_checks:
        monthly:
          uniqueness:
            monthly_partition_duplicate_record_count:
              parameters:
                columns:
                - id
                - created_at
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 100
      columns:
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
    [duplicate_record_count](../../../reference/sensors/table/uniqueness-table-sensors.md#duplicate-record-count)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                grouping_table.grouping_level_1,
                grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
                    TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
                grouping_table.grouping_level_1,
                grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                    TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                    analyzed_table_nested.grouping_level_1,
            
                                    analyzed_table_nested.grouping_level_2,
                        time_period,
                        time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(analyzed_table_nested."date_column" AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table_nested."date_column" AS DATE))) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST( ', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                    CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM  AS analyzed_table
                WHERE (COALESCE(CAST( "id" AS VARCHAR), CAST( "created_at" AS VARCHAR)) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2,
                    time_period,
                    time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2,
                        SERIES_ROUND(CAST(analyzed_table_nested."date_column" AS DATE), 'INTERVAL 1 MONTH', ROUND_DOWN) AS time_period,
                        TO_TIMESTAMP(SERIES_ROUND(CAST(analyzed_table_nested."date_column" AS DATE), 'INTERVAL 1 MONTH', ROUND_DOWN)) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"
            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MariaDB"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                    FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                    FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
                FROM `<target_table>` AS analyzed_table
                WHERE (COALESCE(`id`, `created_at`) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
                ) analyzed_table
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                    analyzed_table_nested.grouping_level_1,
            
                                    analyzed_table_nested.grouping_level_2
            ,
                        time_period,
                        time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2,
                        TRUNC(CAST(analyzed_table_nested."date_column" AS DATE), 'MONTH') AS time_period,
                        CAST(TRUNC(CAST(analyzed_table_nested."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" analyzed_table_nested
                        WHERE (COALESCE(CAST("id" AS VARCHAR(4000)), CAST("created_at" AS VARCHAR(4000))) IS NOT NULL)
                ) analyzed_table
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                    CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2
            ,
                    time_period,
                    time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(analyzed_table_nested."date_column" AS date)) AS time_period,
                        CAST(DATE_TRUNC('MONTH', CAST(analyzed_table_nested."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                        FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                    CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE("id"::VARCHAR, "created_at"::VARCHAR) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table."country" AS grouping_level_1,
                    analyzed_table."state" AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                    TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
                FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                WHERE (COALESCE(CAST("id" AS STRING), CAST("created_at" AS STRING)) IS NOT NULL)
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.`country` AS grouping_level_1,
                    analyzed_table.`state` AS grouping_level_2,
                    DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                    TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table
                WHERE (COALESCE(CAST(`id` AS STRING), CAST(`created_at` AS STRING)) IS NOT NULL)
                GROUP BY `id`, `created_at`, grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro -%}
            
            {% macro render_group_by(table_alias_prefix = 'grouping_table', indentation = '    ') %}
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
                GROUP BY
                {%- endif -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{- ',' if not loop.first -}}{{- lib.eol() }}
                        {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute -}}
                    {%- endfor -%}
                {%- endif -%}
                {%- if lib.time_series is not none -%}
                    {{ ',' if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}{{- lib.eol() -}}
                    {{ indentation }}time_period,{{ lib.eol() -}}
                    {{ indentation }}time_period_utc
                {%- endif -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
            FROM (
                SELECT COUNT(*) AS duplicated_count
                {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
                {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{ render_group_by('grouping_table') }}
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT COUNT(*) AS duplicated_count,
                    analyzed_table.[country] AS grouping_level_1,
                    analyzed_table.[state] AS grouping_level_2,
                    DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                    CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                WHERE (COALESCE(CAST([id] AS VARCHAR), CAST([created_at] AS VARCHAR)) IS NOT NULL)
                GROUP BY [id], [created_at], analyzed_table.[country], analyzed_table.[state], DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
            ) grouping_table
            GROUP BY
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2,
                time_period,
                time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
                {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
                {%- for item in column_names -%}
                    {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
                {%- endfor -%}
            {% endmacro %}
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value
                {{- lib.render_data_grouping_projections_reference('grouping_table') }}
                {{- lib.render_time_dimension_projection_reference('grouping_table') }}
                FROM (
                    SELECT COUNT(*) AS duplicated_count
                    {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
                    {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
                    FROM (
                        SELECT
                            {{ extract_in_list(parameters.columns) -}}
                            {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                            {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                        FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                        {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
                )
                GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
            ) grouping_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            
            
            SELECT
                CASE
                    WHEN SUM(duplicated_count) IS NULL THEN 0
                    ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
                    END AS actual_value,
            
                            grouping_table.grouping_level_1,
            
                            grouping_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                FROM (
                    SELECT COUNT(*) AS duplicated_count,
            
                                analyzed_table_nested.grouping_level_1,
            
                                analyzed_table_nested.grouping_level_2
            ,
                    time_period,
                    time_period_utc
                    FROM (
                        SELECT
                            "id", "created_at",
                        analyzed_table_nested."country" AS grouping_level_1,
                        analyzed_table_nested."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(analyzed_table_nested."date_column" AS date)) AS time_period,
                        CAST(DATE_TRUNC('MONTH', CAST(analyzed_table_nested."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                        FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table_nested
                    WHERE (COALESCE(CAST("id" AS VARCHAR), CAST("created_at" AS VARCHAR)) IS NOT NULL)
                )
                GROUP BY "id", "created_at", grouping_level_1, grouping_level_2, time_period, time_period_utc
            ) grouping_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
