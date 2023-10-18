
## **string datatype detect**
**Full sensor name**
```
column/datatype/string_datatype_detect
```
**Description**  
Column level sensor that analyzes all values in a text column and detects the data type of the values.
 The sensor returns a value that identifies the detected data type of column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.




**SQL Template (Jinja2)**  
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
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE
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
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[-+]?\d+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[+-]?([0-9]*[.])[0-9]+$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),r"^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(\b(am|pm|AM|PM)\b)?)$") IS TRUE OR
                             REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(\b(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)\b)$") IS TRUE
                            THEN 0
                        WHEN TRIM(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
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
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\\d+$') IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[-+]?\d+$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[+-]?([0-9]*[.])[0-9]+$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])\\:([0-5][0-9])\\:([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') IS TRUE OR
                             REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') IS TRUE
                            THEN 0
                        WHEN TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
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
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
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
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
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
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
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
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) ~ '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
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
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[-+]?\d+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^[+-]?([0-9]*[.])[0-9]+$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$' IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT) REGEXP '^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$' IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS TEXT)) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
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
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         {{ make_text_constant(regex) }}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 1
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 2
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 3
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 4
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                            THEN 1
                        ELSE 0
                        END
                )
                THEN 5
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) =
                SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[-+]?\d+$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^[+-]?([0-9]*[.])[0-9]+$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4}))$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4}))$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01]))$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[/](0[1-9]|1[0-2])[/](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$|^((\d{4})[.](0[1-9]|1[0-2])[.](0[1-9]|[1][0-9]|[2][0-9]|3[01])[\s]([0]|[00]|2[0-3]|[01][0-9])[:]([0-5][0-9])[:]([0-5][0-9])[\s]?(am|pm|AM|PM)?)$') }} IS TRUE OR
                             CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX)) LIKE {{ render_regex('^(true|false|TRUE|FALSE|yes|no|YES|NO|y|n|Y|N|t|f|T|F)$') }} IS TRUE
                            THEN 0
                        WHEN TRIM(CAST({{ lib.render_target_column('analyzed_table') }} AS NVARCHAR(MAX))) <> ''
                            THEN 1
                        ELSE 0
                    END
                )
                THEN 6
            ELSE 7
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___
