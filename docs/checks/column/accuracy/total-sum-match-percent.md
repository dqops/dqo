# total sum match percent data quality checks

Column-level check that ensures that there are no more than a maximum percentage of difference of sum of a table column and of a sum of another table column.


___
The **total sum match percent** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/checks/index.md#types-of-checks) checks supported by DQOps.


## profile total sum match percent


**Check description**

Verifies that percentage of the difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_total_sum_match_percent|profiling| |Accuracy|[total_sum_match_percent](../../../reference/sensors/column/accuracy-column-sensors.md#total-sum-match-percent)|[diff_percent](../../../reference/rules/Comparison.md#diff-percent)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the profile total sum match percent data quality check.

??? example "Managing profile total sum match percent check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=profile_total_sum_match_percent
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=profile_total_sum_match_percent
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_total_sum_match_percent
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
          profile_total_sum_match_percent:
            parameters:
              referenced_table: dim_customer
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
    [total_sum_match_percent](../../../reference/sensors/column/accuracy-column-sensors.md#total-sum-match-percent)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.`customer_id`)
                FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.`target_column`) AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.`customer_id`)
                FROM `<target_schema>`.`dim_customer` AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.`target_column`) AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.`customer_id`)
                FROM `dim_customer` AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.`target_column`) AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} referenced_table
                ) AS expected_value,
                analyzed_table.actual_value
            FROM (SELECT
                    SUM({{ lib.render_target_column('original_table')}}) AS actual_value
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause() -}} ) analyzed_table
            GROUP BY actual_value
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM "dim_customer" referenced_table
                ) AS expected_value,
                analyzed_table.actual_value
            FROM (SELECT
                    SUM(original_table."target_column") AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM ""."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
            FROM ""."<target_schema>"."<target_table>" AS analyzed_table
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.`customer_id`)
                FROM `<target_schema>`.`dim_customer` AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.`target_column`) AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.[customer_id])
                FROM [your_sql_server_database].[<target_schema>].[dim_customer] AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.[target_column]) AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM ""."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
            FROM ""."<target_schema>"."<target_table>" AS analyzed_table
            ```
    






___


## daily total sum match percent


**Check description**

Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_total_sum_match_percent|monitoring|daily|Accuracy|[total_sum_match_percent](../../../reference/sensors/column/accuracy-column-sensors.md#total-sum-match-percent)|[diff_percent](../../../reference/rules/Comparison.md#diff-percent)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the daily total sum match percent data quality check.

??? example "Managing daily total sum match percent check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=daily_total_sum_match_percent
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=daily_total_sum_match_percent
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_total_sum_match_percent
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
            daily_total_sum_match_percent:
              parameters:
                referenced_table: dim_customer
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
    [total_sum_match_percent](../../../reference/sensors/column/accuracy-column-sensors.md#total-sum-match-percent)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.`customer_id`)
                FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.`target_column`) AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.`customer_id`)
                FROM `<target_schema>`.`dim_customer` AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.`target_column`) AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.`customer_id`)
                FROM `dim_customer` AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.`target_column`) AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} referenced_table
                ) AS expected_value,
                analyzed_table.actual_value
            FROM (SELECT
                    SUM({{ lib.render_target_column('original_table')}}) AS actual_value
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause() -}} ) analyzed_table
            GROUP BY actual_value
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM "dim_customer" referenced_table
                ) AS expected_value,
                analyzed_table.actual_value
            FROM (SELECT
                    SUM(original_table."target_column") AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM ""."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
            FROM ""."<target_schema>"."<target_table>" AS analyzed_table
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.`customer_id`)
                FROM `<target_schema>`.`dim_customer` AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.`target_column`) AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.[customer_id])
                FROM [your_sql_server_database].[<target_schema>].[dim_customer] AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.[target_column]) AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM ""."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
            FROM ""."<target_schema>"."<target_table>" AS analyzed_table
            ```
    






___


## monthly total sum match percent


**Check description**

Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_total_sum_match_percent|monitoring|monthly|Accuracy|[total_sum_match_percent](../../../reference/sensors/column/accuracy-column-sensors.md#total-sum-match-percent)|[diff_percent](../../../reference/rules/Comparison.md#diff-percent)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the monthly total sum match percent data quality check.

??? example "Managing monthly total sum match percent check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=monthly_total_sum_match_percent
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=monthly_total_sum_match_percent
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_total_sum_match_percent
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
            monthly_total_sum_match_percent:
              parameters:
                referenced_table: dim_customer
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
    [total_sum_match_percent](../../../reference/sensors/column/accuracy-column-sensors.md#total-sum-match-percent)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.`customer_id`)
                FROM `your-google-project-id`.`<target_schema>`.`dim_customer` AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.`target_column`) AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.`customer_id`)
                FROM `<target_schema>`.`dim_customer` AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.`target_column`) AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.`customer_id`)
                FROM `dim_customer` AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.`target_column`) AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} referenced_table
                ) AS expected_value,
                analyzed_table.actual_value
            FROM (SELECT
                    SUM({{ lib.render_target_column('original_table')}}) AS actual_value
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause() -}} ) analyzed_table
            GROUP BY actual_value
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM "dim_customer" referenced_table
                ) AS expected_value,
                analyzed_table.actual_value
            FROM (SELECT
                    SUM(original_table."target_column") AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM "your_postgresql_database"."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM ""."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
            FROM ""."<target_schema>"."<target_table>" AS analyzed_table
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM "your_redshift_database"."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM "your_snowflake_database"."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.`customer_id`)
                FROM `<target_schema>`.`dim_customer` AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.`target_column`) AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table.[customer_id])
                FROM [your_sql_server_database].[<target_schema>].[dim_customer] AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table.[target_column]) AS actual_value
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
                    SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
                FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
                ) AS expected_value,
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                (SELECT
                    SUM(referenced_table."customer_id")
                FROM ""."<target_schema>"."dim_customer" AS referenced_table
                ) AS expected_value,
                SUM(analyzed_table."target_column") AS actual_value
            FROM ""."<target_schema>"."<target_table>" AS analyzed_table
            ```
    






___


