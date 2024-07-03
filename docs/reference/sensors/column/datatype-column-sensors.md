---
title: DQOps data quality datatype sensors
---
# DQOps data quality datatype sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **datatype** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## string datatype detect
Column level sensor that analyzes all values in a text column and detects the data type of the values.
 The sensor returns a value that identifies the detected data type of column: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types.

**Sensor summary**

The string datatype detect sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | datatype | <span class="no-wrap-code">`column/datatype/string_datatype_detect`</span> | [*sensors/column/datatype*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/datatype/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?[0-9]*[.,]?[0-9]+$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]?[T]?[\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?([.]\d{0,12})?[\s]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 6
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]?[T]?[\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?([.]\d{0,12})?[\s]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 7
            ELSE 8
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^[-+]?\\d+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^[+-]?[0-9]*[.,]?[0-9]+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4}))$|^((\\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4})[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4})[\\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4})[\\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((\\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((\\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]?[T]?[\\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?([.]\\d{0,12})?[\\s]?((GMT)|(UTC))?(([-+]\\d{2}[:]?(\\d{2})?)|[zZ])?)$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^(\\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^[-+]?\\d+$") IS TRUE OR
                             REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                             REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4}))$|^((\\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                             REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4})[\\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4})[\\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4})[\\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((\\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((\\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$") IS TRUE OR
                             REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]?[T]?[\\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?([.]\\d{0,12})?[\\s]?((GMT)|(UTC))?(([-+]\\d{2}[:]?(\\d{2})?)|[zZ])?)$") IS TRUE OR
                             REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^(\\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 7
            ELSE 8
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?[0-9]*[.,]?[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]?[T]?[\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?([.]\d{0,12})?[\s]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 6
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]?[T]?[\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?([.]\d{0,12})?[\s]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 7
            ELSE 8
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^[-+]?[0-9]+$') }}
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE WHEN {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^[+-]?[0-9]*[.,]?[0-9]+$') }}
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([0-9]{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([0-9]{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([0-9]{4}))$|^(([0-9]{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^(([0-9]{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^(([0-9]{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }}
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([0-9]{4})[ \t\n\r\f\v]([0]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[ \t\n\r\f\v]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([0-9]{4})[ \t\n\r\f\v]([0]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[ \t\n\r\f\v]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([0-9]{4})[ \t\n\r\f\v]([0]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[ \t\n\r\f\v]?(am|pm|AM|PM)?)$|^(([0-9]{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[ \t\n\r\f\v]([0]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[ \t\n\r\f\v]?(am|pm|AM|PM)?)$|^(([0-9]{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[ \t\n\r\f\v]([0]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[ \t\n\r\f\v]?(am|pm|AM|PM)?)$|^(([0-9]{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[ \t\n\r\f\v]([0]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[ \t\n\r\f\v]?(am|pm|AM|PM)?)$') }}
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^(([0-9]{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[ \t\n\r\f\v]?[T]?[ \t\n\r\f\v]?([0]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[ \t\n\r\f\v]?([.][0-9]{0,12})?[ \t\n\r\f\v]?((GMT)|(UTC))?(([-+][0-9]{2}[:]?([0-9]{2})?)|[zZ])?)$') }}
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }}
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL
                            OR {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^[-+]?[0-9]+$') }}
                            OR {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^[+-]?([0-9]*[.])[0-9]+$') }}
                            OR {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([0-9]{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([0-9]{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([0-9]{4}))$|^(([0-9]{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^(([0-9]{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^(([0-9]{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }}
                            OR {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([0-9]{4})[ \t\n\r\f\v]([0]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[ \t\n\r\f\v]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([0-9]{4})[ \t\n\r\f\v]([0]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[ \t\n\r\f\v]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([0-9]{4})[ \t\n\r\f\v]([0]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[ \t\n\r\f\v]?(am|pm|AM|PM)?)$|^(([0-9]{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[ \t\n\r\f\v]([0]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[ \t\n\r\f\v]?(am|pm|AM|PM)?)$|^(([0-9]{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[ \t\n\r\f\v]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[ \t\n\r\f\v]?(am|pm|AM|PM)?)$|^(([0-9]{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[ \t\n\r\f\v]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[ \t\n\r\f\v]?(am|pm|AM|PM)?)$') }}
                            OR {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^(([0-9]{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]?[T]?[\s]?([0]|2[0-3]|[01][0-9]):([0-5][0-9]):([0-5][0-9])[\s]?([.][0-9]{0,12})?[\s]?((GMT)|(UTC))?(([-+][0-9]{2}[:]?([0-9]{2})?)|[zZ])?)$') }}
                            OR {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }}
                                THEN 0
                        WHEN TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 7
            ELSE 8
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\d+$')
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[+-]?([0-9]*[.,])[0-9]+E[+-][0-9]*$')
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?[0-9]*[.,]?[0-9]+$')
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$')
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$')
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$')
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})\s([0]|2[0-3]|[01][0-9])\:([0-5][0-9])[:]([0-5][0-9])\s?(am|pm|AM|PM)?)$')
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})\s([0]|2[0-3]|[01][0-9])\:([0-5][0-9])\:([0-5][0-9])\s?(am|pm|AM|PM)?)$')
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})\s([0]|2[0-3]|[01][0-9])\:([0-5][0-9])\:([0-5][0-9])\s?(am|pm|AM|PM)?)$')
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])\s([0]|2[0-3]|[01][0-9])\:([0-5][0-9])\:([0-5][0-9])\s?(am|pm|AM|PM)?)$')
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])\s([0]|2[0-3]|[01][0-9])\:([0-5][0-9])\:([0-5][0-9])\s?(am|pm|AM|PM)?)$')
                            OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])\s([0]|2[0-3]|[01][0-9])\:([0-5][0-9])\:([0-5][0-9])\s?(am|pm|AM|PM)?)$')
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[[:space:]]?[T]?[[:space:]]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[[:space:]]?([.]\d{0,12})?[[:space:]]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$')
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$')
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\d+$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.,])[0-9]+E[+-][0-9]*$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.,])[0-9]+$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})\s([0]|2[0-3]|[01][0-9])\:([0-5][0-9])[:]([0-5][0-9])\s?(am|pm|AM|PM)?)$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})\s([0]|2[0-3]|[01][0-9])\:([0-5][0-9])\:([0-5][0-9])\s?(am|pm|AM|PM)?)$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})\s([0]|2[0-3]|[01][0-9])\:([0-5][0-9])\:([0-5][0-9])\s?(am|pm|AM|PM)?)$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])\s([0]|2[0-3]|[01][0-9])\:([0-5][0-9])\:([0-5][0-9])\s?(am|pm|AM|PM)?)$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])\s([0]|2[0-3]|[01][0-9])\:([0-5][0-9])\:([0-5][0-9])\s?(am|pm|AM|PM)?)$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])\s([0]|2[0-3]|[01][0-9])\:([0-5][0-9])\:([0-5][0-9])\s?(am|pm|AM|PM)?)$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[[:space:]]?[T]?[[:space:]]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[[:space:]]?([.]\d{0,12})?[[:space:]]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$')
                             OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$')
                            THEN 0
                        WHEN TRIM({{ lib.render_target_column('analyzed_table') }}) IS NOT NULL
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 7
            ELSE 8
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?[0-9]*[.,]?[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]?[T]?[\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?([.]\d{0,12})?[\s]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$'
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]?[T]?[\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?([.]\d{0,12})?[\s]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$' OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 7
            ELSE 8
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[-+]?\d+$')
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[+-]?[0-9]*[.,]?[0-9]+$')
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]?[T]?[\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?([.]\d{0,12})?[\s]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$')
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 6
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[-+]?\d+$')  OR
                             REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[+-]?([0-9]*[.])[0-9]+$')  OR
                             REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')  OR
                             REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$') OR
                             REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]?[T]?[\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?([.]\d{0,12})?[\s]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$') OR
                             REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                            THEN 0
                        WHEN TRIM(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 7
            ELSE 8
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?[0-9]*[.,]?[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]?[T]?[\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?([.]\d{0,12})?[\s]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$'
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]?[T]?[\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?([.]\d{0,12})?[\s]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$' OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 7
            ELSE 8
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\\d+$'
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?[0-9]*[.,]?[0-9]+$'
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4}))$|^((\\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$'
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4})[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4})[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4})[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(am|pm|AM|PM)?)$|^((\\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(am|pm|AM|PM)?)$|^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(am|pm|AM|PM)?)$|^((\\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(am|pm|AM|PM)?)$'
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]?[T]?[\\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?([.]\\d{0,12})?[\\s]?((GMT)|(UTC))?(([-+]\\d{2}[:]?(\\d{2})?)|[zZ])?)$'
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$'
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\\d+$' OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4}))$|^((\\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4})[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4})[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4})[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(am|pm|AM|PM)?)$|^((\\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(am|pm|AM|PM)?)$|^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(am|pm|AM|PM)?)$|^((\\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(am|pm|AM|PM)?)$' OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$'
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 7
            ELSE 8
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^[-+]?\\d+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^[+-]?[0-9]*[.,]?[0-9]+$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4}))$|^((\\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4})[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4})[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4})[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((\\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((\\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]?[T]?[\\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?([.]\\d{0,12})?[\\s]?((GMT)|(UTC))?(([-+]\\d{2}[:]?(\\d{2})?)|[zZ])?)$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^(\\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^[-+]?\\d+$") IS TRUE OR
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4}))$|^((\\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4})[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4})[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4})[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((\\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$|^((\\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?(\\b(am|pm|AM|PM)\\b)?)$") IS TRUE OR
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^((\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\\s]?[T]?[\\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\\s]?([.]\\d{0,12})?[\\s]?((GMT)|(UTC))?(([-+]\\d{2}[:]?(\\d{2})?)|[zZ])?)$") IS TRUE OR
                                REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "^(\\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 7
            ELSE 8
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) NOT LIKE '%[^-+0-9]%'
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) NOT LIKE '%[^-+0123456789.,]%'
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN LEN(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR)) <= 10
                            AND(
                                TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 110) IS NOT NULL     --  mm-dd-yyyy
                                OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 101) IS NOT NULL  --  mm/dd/yyyy
                                OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 105) IS NOT NULL  --  dd-mm-yyyy
                                OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 103) IS NOT NULL  --  dd/mm/yyyy
                                OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 104) IS NOT NULL  --  dd.mm.yyyy
                                OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 107) IS NOT NULL  --  Mon dd, yyyy
                                OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 102) IS NOT NULL  --  yyyy.mm.dd
                                OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 111) IS NOT NULL  --  yyyy/mm/dd
                                OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 23) IS NOT NULL   --  yyyy-mm-dd
                            ) THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) NOT LIKE '%[^-0-9./:APMapm ]%'
                               AND (
                                   CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '%[-/.]0[1-9][-/.]%'
                                       OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '%[-/.]1[0-2][-/.]%'
                               ) AND (
                                   (
                                       CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '0[1-9][-/.]__[-/.][0-9][0-9][0-9][0-9] %'
                                       OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '[12][0-9][-/.]__[-/.][0-9][0-9][0-9][0-9] %'
                                       OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '3[01][-/.]__[-/.][0-9][0-9][0-9][0-9] %'
                                   ) OR (
                                       CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '[0-9][0-9][0-9][0-9][-/.]__[-/.]0[1-9] %'
                                       OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '[0-9][0-9][0-9][0-9][-/.]__[-/.][12][0-9] %'
                                       OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '[0-9][0-9][0-9][0-9][-/.]__[-/.]3[01] %'
                                   )
                               ) AND (
                                    CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '%[0][0-9][:][0-5][0-9][:][0-5][0-9] [AaPp][Mm]'
                                    OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '%[1][0-2][:][0-5][0-9][:][0-5][0-9] [AaPp][Mm]'
                                    OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '% [0-9][:][0-5][0-9][:][0-5][0-9] [AaPp][Mm]'
                                    OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '%[01][0-9][:][0-5][0-9][:][0-5][0-9]'
                                    OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '%[2][0-3][:][0-5][0-9][:][0-5][0-9]'
                                    OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '% [0-9][:][0-5][0-9][:][0-5][0-9]'
                               )
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 120) IS NOT NULL     --  yyyy-mm-dd hh:mm:ss
                            OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 121) IS NOT NULL   --  yyyy-mm-dd hh:mm:ss:nnn
                            OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 126) IS NOT NULL   --  yyyy-mm-dd T hh:mm:ss:nnn
                            OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 127) IS NOT NULL   --  yyyy-mm-dd T hh:mm:ss:nnn
                            OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 20) IS NOT NULL    --  yyyy-mm-dd hh:mm:ss
                            OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 21) IS NOT NULL    --  yyyy-mm-dd hh:mm:ss:nnn
                            OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 25) IS NOT NULL    --  yyyyy-mm-dd hh:mm:ss:nnn
                            OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 26) IS NOT NULL    --  yyyyy-dd-mm hh:mm:ss:nnn
                            OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 27) IS NOT NULL    --  ymm-dd-yyyy hh:mm:ss:nnn
                            OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 28) IS NOT NULL    --  ymm-yyyy-dd hh:mm:ss:nnn
                            OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 29) IS NOT NULL    --  ydd-mm-yyyy hh:mm:ss:nnn
                            OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 30) IS NOT NULL    --  ydd-yyyy-mm hh:mm:ss:nnn
                            OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 109) IS NOT NULL   --  ydd-yyyy-mm hh:mm:ss:nnn
                            OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 113) IS NOT NULL   --  ydd-yyyy-mm hh:mm:ss:nnn
                        THEN 1
                        ELSE 0
                    END
                )
                THEN 5
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN LOWER(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) IN ('TRUE', 'FALSE', 'YES', 'NO', 'Y', 'N', 'T', 'F')
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 6
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) NOT LIKE '%[^0-9]%' OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) NOT LIKE '%[^-+0123456789.,]%' OR
                             LEN(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR)) <= 10
                                 AND(
                                     TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 110) IS NOT NULL     --  mm-dd-yyyy
                                     OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 101) IS NOT NULL  --  mm/dd/yyyy
                                     OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 105) IS NOT NULL  --  dd-mm-yyyy
                                     OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 103) IS NOT NULL  --  dd/mm/yyyy
                                     OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 104) IS NOT NULL  --  dd.mm.yyyy
                                     OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 107) IS NOT NULL  --  Mon dd, yyyy
                                     OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 102) IS NOT NULL  --  yyyy.mm.dd
                                     OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 111) IS NOT NULL  --  yyyy/mm/dd
                                     OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 23) IS NOT NULL   --  yyyy-mm-dd
                                 )
                             OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) NOT LIKE '%[^-0-9./:APMapm ]%'
                                   AND (
                                       CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '%[-/.]0[1-9][-/.]%'
                                           OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '%[-/.]1[0-2][-/.]%'
                                   ) AND (
                                       (
                                           CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '0[1-9][-/.]__[-/.][0-9][0-9][0-9][0-9] %'
                                           OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '[12][0-9][-/.]__[-/.][0-9][0-9][0-9][0-9] %'
                                           OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '3[01][-/.]__[-/.][0-9][0-9][0-9][0-9] %'
                                       ) OR (
                                           CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '[0-9][0-9][0-9][0-9][-/.]__[-/.]0[1-9] %'
                                           OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '[0-9][0-9][0-9][0-9][-/.]__[-/.][12][0-9] %'
                                           OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '[0-9][0-9][0-9][0-9][-/.]__[-/.]3[01] %'
                                       )
                                   ) AND (
                                        CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '%[0][0-9][:][0-5][0-9][:][0-5][0-9] [AaPp][Mm]'
                                        OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '%[1][0-2][:][0-5][0-9][:][0-5][0-9] [AaPp][Mm]'
                                        OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '% [0-9][:][0-5][0-9][:][0-5][0-9] [AaPp][Mm]'
                                        OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '%[01][0-9][:][0-5][0-9][:][0-5][0-9]'
                                        OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '%[2][0-3][:][0-5][0-9][:][0-5][0-9]'
                                        OR CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE '% [0-9][:][0-5][0-9][:][0-5][0-9]'
                                   )
                             OR
                                TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 120) IS NOT NULL               --  yyyy-mm-dd hh:mm:ss
                                    OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 121) IS NOT NULL        --  yyyy-mm-dd hh:mm:ss:nnn
                                    OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 126) IS NOT NULL        --  yyyy-mm-dd T hh:mm:ss:nnn
                                    OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 127) IS NOT NULL        --  yyyy-mm-dd T hh:mm:ss:nnn
                                    OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 20) IS NOT NULL         --  yyyy-mm-dd hh:mm:ss
                                    OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 21) IS NOT NULL         --  yyyy-mm-dd hh:mm:ss:nnn
                                    OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 25) IS NOT NULL         --  yyyyy-mm-dd hh:mm:ss:nnn
                                    OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 26) IS NOT NULL         --  yyyyy-dd-mm hh:mm:ss:nnn
                                    OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 27) IS NOT NULL         --  ymm-dd-yyyy hh:mm:ss:nnn
                                    OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 28) IS NOT NULL         --  ymm-yyyy-dd hh:mm:ss:nnn
                                    OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 29) IS NOT NULL         --  ydd-mm-yyyy hh:mm:ss:nnn
                                    OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 30) IS NOT NULL         --  ydd-yyyy-mm hh:mm:ss:nnn
                                    OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 109) IS NOT NULL        --  ydd-yyyy-mm hh:mm:ss:nnn
                                    OR TRY_CONVERT(date, CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), 113) IS NOT NULL        --  ydd-yyyy-mm hh:mm:ss:nnn
                             OR LOWER(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) IN ('TRUE', 'FALSE', 'YES', 'NO', 'Y', 'N', 'T', 'F')
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 7
            ELSE 8
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[-+]?\d+$')
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[+-]?[0-9]{1}[.][0-9]*E[-]?[0-9]+$') OR {# Casting double to varchar in trino results in a scientific notation #}
                             REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[+-]?[0-9]*[.,]?[0-9]+$')
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                        SUM(
                            CASE
                                WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]?[T]?[\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?([.]\d{0,12})?[\s]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$')
                                    THEN 1
                                ELSE 0
                                END
                        )
                        THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 6
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[-+]?\d+$')  OR
                             REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^[+-]?([0-9]*[.])[0-9]+$')  OR
                             REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$')  OR
                             REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$')  OR
                             REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]?[T]?[\s]?([0]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?([.]\d{0,12})?[\s]?((GMT)|(UTC))?(([-+]\d{2}[:]?(\d{2})?)|[zZ])?)$') OR
                             REGEXP_LIKE(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$')
                            THEN 0
                        WHEN TRIM(TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 7
            ELSE 8
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
