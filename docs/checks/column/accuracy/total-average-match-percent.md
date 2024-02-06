# total average match percent data quality checks

A column-level check that ensures that the difference between the average value in the tested column and the average value of another column in the referenced table is below the maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.


___
The **total average match percent** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile total average match percent


**Check description**

Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_total_average_match_percent`</span>|[accuracy](../../../categories-of-data-quality-checks/how-to-detect-accuracy-data-quality-issues.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |Accuracy|[*total_average_match_percent*](../../../reference/sensors/column/accuracy-column-sensors.md#total-average-match-percent)|[*diff_percent*](../../../reference/rules/Comparison.md#diff-percent)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile total average match percent data quality check.

??? example "Managing profile total average match percent check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_total_average_match_percent --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_total_average_match_percent --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_total_average_match_percent --enable-warning
                            -Wmax_diff_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_total_average_match_percent --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_total_average_match_percent --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_total_average_match_percent --enable-error
                            -Emax_diff_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_total_average_match_percent* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_total_average_match_percent
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_total_average_match_percent
        ```

        You can also run this check on all tables (and columns)  on which the *profile_total_average_match_percent* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=profile_total_average_match_percent
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-18"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      profiling_checks:
        accuracy:
          profile_total_average_match_percent:
            parameters:
              referenced_table: landing_zone.customer_raw
              referenced_column: customer_id
            warning:
              max_diff_percent: 0.0
            error:
              max_diff_percent: 1.0
            fatal:
              max_diff_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [total_average_match_percent](../../../reference/sensors/column/accuracy-column-sensors.md#total-average-match-percent)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.`customer_id`)
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.`target_column`) AS actual_value
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
               {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.`customer_id`)
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.`target_column`) AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            SELECT
                (SELECT
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ lib.render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "MySQL"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.`customer_id`)
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.`target_column`) AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
              {{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                (SELECT
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} referenced_table
                ) AS expected_value,
                analyzed_table.actual_value
            FROM (SELECT
                    AVG({{ lib.render_target_column('original_table')}}) AS actual_value
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause() -}} ) analyzed_table
            GROUP BY actual_value
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw referenced_table
                ) AS expected_value,
                analyzed_table.actual_value
            FROM (SELECT
                    AVG(original_table."target_column") AS actual_value
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY actual_value
            ```
    ??? example "PostgreSQL"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
               {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Redshift"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Snowflake"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
               {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.`customer_id`)
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.`target_column`) AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "SQL Server"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.[customer_id])
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.[target_column]) AS actual_value
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
               {{ lib.quote_identifier(lib.macro_catalog_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    
___


## daily total average match percent


**Check description**

Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_total_average_match_percent`</span>|[accuracy](../../../categories-of-data-quality-checks/how-to-detect-accuracy-data-quality-issues.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|Accuracy|[*total_average_match_percent*](../../../reference/sensors/column/accuracy-column-sensors.md#total-average-match-percent)|[*diff_percent*](../../../reference/rules/Comparison.md#diff-percent)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily total average match percent data quality check.

??? example "Managing daily total average match percent check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_total_average_match_percent --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_total_average_match_percent --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_total_average_match_percent --enable-warning
                            -Wmax_diff_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_total_average_match_percent --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_total_average_match_percent --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_total_average_match_percent --enable-error
                            -Emax_diff_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_total_average_match_percent* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_total_average_match_percent
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_total_average_match_percent
        ```

        You can also run this check on all tables (and columns)  on which the *daily_total_average_match_percent* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=daily_total_average_match_percent
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-19"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        daily:
          accuracy:
            daily_total_average_match_percent:
              parameters:
                referenced_table: landing_zone.customer_raw
                referenced_column: customer_id
              warning:
                max_diff_percent: 0.0
              error:
                max_diff_percent: 1.0
              fatal:
                max_diff_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [total_average_match_percent](../../../reference/sensors/column/accuracy-column-sensors.md#total-average-match-percent)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.`customer_id`)
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.`target_column`) AS actual_value
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
               {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.`customer_id`)
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.`target_column`) AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            SELECT
                (SELECT
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ lib.render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "MySQL"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.`customer_id`)
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.`target_column`) AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
              {{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                (SELECT
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} referenced_table
                ) AS expected_value,
                analyzed_table.actual_value
            FROM (SELECT
                    AVG({{ lib.render_target_column('original_table')}}) AS actual_value
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause() -}} ) analyzed_table
            GROUP BY actual_value
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw referenced_table
                ) AS expected_value,
                analyzed_table.actual_value
            FROM (SELECT
                    AVG(original_table."target_column") AS actual_value
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY actual_value
            ```
    ??? example "PostgreSQL"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
               {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Redshift"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Snowflake"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
               {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.`customer_id`)
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.`target_column`) AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "SQL Server"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.[customer_id])
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.[target_column]) AS actual_value
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
               {{ lib.quote_identifier(lib.macro_catalog_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    
___


## monthly total average match percent


**Check description**

Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_total_average_match_percent`</span>|[accuracy](../../../categories-of-data-quality-checks/how-to-detect-accuracy-data-quality-issues.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|monthly|Accuracy|[*total_average_match_percent*](../../../reference/sensors/column/accuracy-column-sensors.md#total-average-match-percent)|[*diff_percent*](../../../reference/rules/Comparison.md#diff-percent)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly total average match percent data quality check.

??? example "Managing monthly total average match percent check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_total_average_match_percent --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_total_average_match_percent --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_total_average_match_percent --enable-warning
                            -Wmax_diff_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_total_average_match_percent --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_total_average_match_percent --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_total_average_match_percent --enable-error
                            -Emax_diff_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_total_average_match_percent* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_total_average_match_percent
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_total_average_match_percent
        ```

        You can also run this check on all tables (and columns)  on which the *monthly_total_average_match_percent* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=monthly_total_average_match_percent
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-19"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        monthly:
          accuracy:
            monthly_total_average_match_percent:
              parameters:
                referenced_table: landing_zone.customer_raw
                referenced_column: customer_id
              warning:
                max_diff_percent: 0.0
              error:
                max_diff_percent: 1.0
              fatal:
                max_diff_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [total_average_match_percent](../../../reference/sensors/column/accuracy-column-sensors.md#total-average-match-percent)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.`customer_id`)
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.`target_column`) AS actual_value
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
               {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.`customer_id`)
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.`target_column`) AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            SELECT
                (SELECT
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ lib.render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "MySQL"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.`customer_id`)
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.`target_column`) AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
              {{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                (SELECT
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} referenced_table
                ) AS expected_value,
                analyzed_table.actual_value
            FROM (SELECT
                    AVG({{ lib.render_target_column('original_table')}}) AS actual_value
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause() -}} ) analyzed_table
            GROUP BY actual_value
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw referenced_table
                ) AS expected_value,
                analyzed_table.actual_value
            FROM (SELECT
                    AVG(original_table."target_column") AS actual_value
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY actual_value
            ```
    ??? example "PostgreSQL"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
               {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Redshift"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Snowflake"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
               {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.`customer_id`)
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.`target_column`) AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "SQL Server"

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
                    AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table.[customer_id])
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table.[target_column]) AS actual_value
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_referenced_table(referenced_table) -%}
            {%- if referenced_table.find(".") < 0 -%}
               {{ lib.quote_identifier(lib.macro_catalog_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
            {%- else -%}
               {{ referenced_table }}
            {%- endif -%}
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                (SELECT
                    AVG(referenced_table."customer_id")
                FROM landing_zone.customer_raw AS referenced_table
                ) AS expected_value,
                AVG(analyzed_table."target_column") AS actual_value
            FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    
___



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
