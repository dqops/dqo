# column

## **pii** column sensors
___

### **contains usa zipcode percent**
**Full sensor name**
```
column/pii/contains_usa_zipcode_percent
```
**Description**  
Column level sensor that calculates the percent of values that contain a USA zip code number in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"[0-9]{5}(?:-[0-9]{4})?") THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"[0-9]{5}(?:-[0-9]{4})?") THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **contains usa phone percent**
**Full sensor name**
```
column/pii/contains_usa_phone_percent
```
**Description**  
Column level sensor that calculates the percent of values that contains a USA phone number in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))") THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))") THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___


## **datetime** column sensors
___

### **date values in future percent**
**Full sensor name**
```
column/datetime/date_values_in_future_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE) > CURRENT_DATE() THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                  WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP() THEN 1
                ELSE
                0
              END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                  WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE() THEN 1
                ELSE
                0
              END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                  WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME() THEN 1
                ELSE
                0
              END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'STRING' -%}
                CASE
                  WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP() THEN 1
                ELSE
                0
              END
        {%- else -%}
        <INVALID DATA TYPE: table.columns[column_name].type_snapshot.column_type/>
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        {{ render_value_in_future() }}
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___


## **bool** column sensors
___

### **true percent**
**Full sensor name**
```
column/bool/true_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a true value in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(CASE
          WHEN UPPER(TRY_CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) = 'TRUE' THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(CASE
          WHEN SAFE_CAST(SAFE_CAST({{ lib.render_target_column('analyzed_table')}} AS STRING) AS BOOL) IS TRUE THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **false percent**
**Full sensor name**
```
column/bool/false_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a false value in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(CASE
          WHEN UPPER(TRY_CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) = 'FALSE' THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(CASE
          WHEN SAFE_CAST(SAFE_CAST({{ lib.render_target_column('analyzed_table')}} AS STRING) AS BOOL) IS FALSE THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___


## **strings** column sensors
___

### **string parsable to integer percent**
**Full sensor name**
```
column/strings/string_parsable_to_integer_percent
```
**Description**  
Column level sensor that calculates the number of rows with parsable to integer string column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * COUNT( TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS INT64) ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * COUNT( SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS INT64) ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string valid email percent**
**Full sensor name**
```
column/strings/string_valid_email_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid email value in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$") IS TRUE THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$") IS TRUE THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string length above max length percent**
**Full sensor name**
```
column/strings/string_length_above_max_length_percent
```
**Description**  
Column level sensor that calculates the percentage of values that are longer than a given length in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|maximum_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN LENGTH(SAFE_CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) >= {{(parameters.maximum_length)}} THEN 1
        ELSE
        0
      END
        )/ COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN LENGTH(SAFE_CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) >= {{(parameters.maximum_length)}} THEN 1
        ELSE
        0
      END
        )/ COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string valid usa phone percent**
**Full sensor name**
```
column/strings/string_valid_usa_phone_percent
```
**Description**  
Column level sensor that calculates the percent of values that fit to a USA phone regex in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$") THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1\)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))$") THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string length below min length count**
**Full sensor name**
```
column/strings/string_length_below_min_length_count
```
**Description**  
Column level sensor that calculates the count of values that are shorter than a given length in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|minimum_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
      SUM(
        CASE
          WHEN LENGTH(SAFE_CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) <= {{(parameters.minimum_length)}} THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
      SUM(
        CASE
          WHEN LENGTH(SAFE_CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) <= {{(parameters.minimum_length)}} THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string whitespace count**
**Full sensor name**
```
column/strings/string_whitespace_count
```
**Description**  
Column level sensor that calculates the number of rows with an whitespace string column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> '' AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = '' THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> '' AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = '' THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string valid usa zipcode percent**
**Full sensor name**
```
column/strings/string_valid_usa_zipcode_percent
```
**Description**  
Column level sensor that calculates the percent of values that fit to a USA ZIP code regex in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
      ELSE
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[0-9]{5}(?:-[0-9]{4})?$") IS TRUE THEN 1
        ELSE
        0
      END
        ) / COUNT({{ lib.render_target_column('analyzed_table') }})
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[0-9]{5}(?:-[0-9]{4})?$") THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string boolean placeholder percent**
**Full sensor name**
```
column/strings/string_boolean_placeholder_percent
```
**Description**  
Column level sensor that calculates the number of rows with a boolean placeholder string column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0') THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0') THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string valid country code percent**
**Full sensor name**
```
column/strings/string_valid_country_code_percent
```
**Description**  
Column level sensor that calculates the number of rows with a valid country code string column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN UPPER({{ lib.render_column_cast_to_string('analyzed_table') }}) IN ('AF', 'AL', 'DZ', 'AS', 'AD', 'AO', 'AI', 'AQ', 'AG', 'AR', 'AM', 'AW', 'AU', 'AT', 'AZ', 'BS', 'BH', 'BD', 'BB', 'BY', 'BE', 'BZ', 'BJ', 'BM', 'BT', 'BO', 'BA', 'BW', 'BR', 'IO', 'VG', 'BN', 'BG', 'BF', 'BI', 'KH', 'CM', 'CA', 'CV', 'KY', 'CF', 'TD', 'CL', 'CN', 'CX', 'CC', 'CO', 'KM', 'CK', 'CR', 'HR', 'CU', 'CW', 'CY', 'CZ', 'CD', 'DK', 'DJ', 'DM', 'DO', 'TL', 'EC', 'EG', 'SV', 'GQ', 'ER', 'EE', 'ET', 'FK', 'FO', 'FJ', 'FI', 'FR', 'PF', 'GA', 'GM', 'GE', 'DE', 'GH', 'GI', 'GR', 'GL', 'GD', 'GU', 'GT', 'GG', 'GN', 'GW', 'GY', 'HT', 'HN', 'HK', 'HU', 'IS', 'IN', 'ID', 'IR', 'IQ', 'IE', 'IM', 'IL', 'IT', 'CI', 'JM', 'JP', 'JE', 'JO', 'KZ', 'KE', 'KI', 'XK', 'KW', 'KG', 'LA', 'LV', 'LB', 'LS', 'LR', 'LY', 'LI', 'LT', 'LU', 'MO', 'MK', 'MG', 'MW', 'MY', 'MV', 'ML', 'MT', 'MH', 'MR', 'MU', 'YT', 'MX', 'FM', 'MD', 'MC', 'MN', 'ME', 'MS', 'MA', 'MZ', 'MM', 'NA', 'NR', 'NP', 'NL', 'AN', 'NC', 'NZ', 'NI', 'NE', 'NG', 'NU', 'KP', 'MP', 'NO', 'OM', 'PK', 'PW', 'PS', 'PA', 'PG', 'PY', 'PE', 'PH', 'PN', 'PL', 'PT', 'PR', 'QA', 'CG', 'RE', 'RO', 'RU', 'RW', 'BL', 'SH', 'KN', 'LC', 'MF', 'PM', 'VC', 'WS', 'SM', 'ST', 'SA', 'SN', 'RS', 'SC', 'SL', 'SG', 'SX', 'SK', 'SI', 'SB', 'SO', 'ZA', 'KR', 'SS', 'ES', 'LK', 'SD', 'SR', 'SJ', 'SZ', 'SE', 'CH', 'SY', 'TW', 'TJ', 'TZ', 'TH', 'TG', 'TK', 'TO', 'TT', 'TN', 'TR', 'TM', 'TC', 'TV', 'VI', 'UG', 'UA', 'AE', 'GB', 'US', 'UY', 'UZ', 'VU', 'VA', 'VE', 'VN', 'WF', 'EH', 'YE', 'ZM', 'ZW') THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN UPPER({{ lib.render_column_cast_to_string('analyzed_table') }}) IN ('AF', 'AL', 'DZ', 'AS', 'AD', 'AO', 'AI', 'AQ', 'AG', 'AR', 'AM', 'AW', 'AU', 'AT', 'AZ', 'BS', 'BH', 'BD', 'BB', 'BY', 'BE', 'BZ', 'BJ', 'BM', 'BT', 'BO', 'BA', 'BW', 'BR', 'IO', 'VG', 'BN', 'BG', 'BF', 'BI', 'KH', 'CM', 'CA', 'CV', 'KY', 'CF', 'TD', 'CL', 'CN', 'CX', 'CC', 'CO', 'KM', 'CK', 'CR', 'HR', 'CU', 'CW', 'CY', 'CZ', 'CD', 'DK', 'DJ', 'DM', 'DO', 'TL', 'EC', 'EG', 'SV', 'GQ', 'ER', 'EE', 'ET', 'FK', 'FO', 'FJ', 'FI', 'FR', 'PF', 'GA', 'GM', 'GE', 'DE', 'GH', 'GI', 'GR', 'GL', 'GD', 'GU', 'GT', 'GG', 'GN', 'GW', 'GY', 'HT', 'HN', 'HK', 'HU', 'IS', 'IN', 'ID', 'IR', 'IQ', 'IE', 'IM', 'IL', 'IT', 'CI', 'JM', 'JP', 'JE', 'JO', 'KZ', 'KE', 'KI', 'XK', 'KW', 'KG', 'LA', 'LV', 'LB', 'LS', 'LR', 'LY', 'LI', 'LT', 'LU', 'MO', 'MK', 'MG', 'MW', 'MY', 'MV', 'ML', 'MT', 'MH', 'MR', 'MU', 'YT', 'MX', 'FM', 'MD', 'MC', 'MN', 'ME', 'MS', 'MA', 'MZ', 'MM', 'NA', 'NR', 'NP', 'NL', 'AN', 'NC', 'NZ', 'NI', 'NE', 'NG', 'NU', 'KP', 'MP', 'NO', 'OM', 'PK', 'PW', 'PS', 'PA', 'PG', 'PY', 'PE', 'PH', 'PN', 'PL', 'PT', 'PR', 'QA', 'CG', 'RE', 'RO', 'RU', 'RW', 'BL', 'SH', 'KN', 'LC', 'MF', 'PM', 'VC', 'WS', 'SM', 'ST', 'SA', 'SN', 'RS', 'SC', 'SL', 'SG', 'SX', 'SK', 'SI', 'SB', 'SO', 'ZA', 'KR', 'SS', 'ES', 'LK', 'SD', 'SR', 'SJ', 'SZ', 'SE', 'CH', 'SY', 'TW', 'TJ', 'TZ', 'TH', 'TG', 'TK', 'TO', 'TT', 'TN', 'TR', 'TM', 'TC', 'TV', 'VI', 'UG', 'UA', 'AE', 'GB', 'US', 'UY', 'UZ', 'VU', 'VA', 'VE', 'VN', 'WF', 'EH', 'YE', 'ZM', 'ZW') THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string min length**
**Full sensor name**
```
column/strings/string_min_length
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      MIN(LENGTH({{lib.render_column_cast_to_string('analyzed_table')}})) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      MIN( LENGTH({{lib.render_column_cast_to_string('analyzed_table')}}) ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string max length**
**Full sensor name**
```
column/strings/string_max_length
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      MAX(LENGTH({{lib.render_column_cast_to_string('analyzed_table')}})) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      MAX( LENGTH({{lib.render_column_cast_to_string('analyzed_table')}}) ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string invalid ip6 address count**
**Full sensor name**
```
column/strings/string_invalid_ip6_address_count
```
**Description**  
Column level sensor that calculates the number of rows with an invalid IP6 address value in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$") IS TRUE THEN 0
        ELSE
        1
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$") IS TRUE THEN 0
        ELSE
        1
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string invalid ip4 address count**
**Full sensor name**
```
column/strings/string_invalid_ip4_address_count
```
**Description**  
Column level sensor that calculates the number of rows with an invalid IP4 address value in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$") IS TRUE THEN 0
        ELSE
        1
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$") IS TRUE THEN 0
        ELSE
        1
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string length below min length percent**
**Full sensor name**
```
column/strings/string_length_below_min_length_percent
```
**Description**  
Column level sensor that calculates the percentage of values that are shorter than a given length in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|minimum_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN LENGTH(SAFE_CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) <= {{(parameters.minimum_length)}} THEN 1
        ELSE
        0
      END
        )/ COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN LENGTH(SAFE_CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) <= {{(parameters.minimum_length)}} THEN 1
        ELSE
        0
      END
        )/ COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string in set count**
**Full sensor name**
```
column/strings/string_in_set_count
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|values|Provided list of values to match the data.|string_list|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
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
        {%- if parameters['values']|length == 0 -%}
            NULL
        {%- else -%}
          SUM(
            CASE
              WHEN ({{lib.render_target_column('analyzed_table')}} IN ({{extract_in_list(parameters['values'])}})) IS TRUE THEN 1
            ELSE
            0
          END
            )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN NULL
      ELSE
      {{render_else()}}
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
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
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        NULL
        {%- else -%}
          SUM(
            CASE
              WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }}) THEN 1
            ELSE
            0
          END
            )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
      {{ actual_value() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string regex match percent**
**Full sensor name**
```
column/strings/string_regex_match_percent
```
**Description**  
Column level sensor that calculates the percent of values that fit to a regex in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|regex|This field can be used to define custom regex. In order to define custom regex, user should write correct regex as a string. If regex is not defined by user then default regex is null|string|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         r{{ make_text_constant(regex) }}
    {% endmacro %}
    
    SELECT
      CASE
        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
      ELSE
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS( {{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }}) IS TRUE THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         r{{ make_text_constant(regex) }}
    {% endmacro %}
    
    SELECT
      CASE
        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
      ELSE
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS( {{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }}) IS TRUE THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string valid currency code percent**
**Full sensor name**
```
column/strings/string_valid_currency_code_percent
```
**Description**  
Column level sensor that calculates the number of rows with a valid currency code string column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN UPPER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('ALL', 'AFN', 'ARS', 'AWG', 'AUD', 'AZN', 'BSD', 'BBD', 'BYN', 'BZD', 'BMD', 'BOB', 'BAM', 'BWP', 'BGN', 'BRL', 'BND', 'KHR', 'CAD', 'KYD', 'CLP', 'CNY', 'COP', 'CRC', 'HRK', 'CUP', 'CZK', 'DKK', 'DOP', 'XCD', 'EGP', 'SVC', 'EUR', 'FKP', 'FJD', 'GHS', 'GIP', 'GTQ', 'GGP', 'GYD', 'HNL', 'HKD', 'HUF', 'ISK', 'INR', 'IDR', 'IRR', 'IMP', 'ILS', 'JMD', 'JPY', 'JEP', 'KZT', 'KPW', 'KRW', 'KGS', 'LAK', 'LBP', 'LRD', 'MKD', 'MYR', 'MUR', 'MXN', 'MNT', 'MZN', 'NAD', 'NPR', 'ANG', 'NZD', 'NIO', 'NGN', 'NOK', 'OMR', 'PKR', 'PAB', 'PYG', 'PEN', 'PHP', 'PLN', 'QAR', 'RON', 'RUB', 'SHP', 'SAR', 'RSD', 'SCR', 'SGD', 'SBD', 'SOS', 'ZAR', 'LKR', 'SEK', 'CHF', 'SRD', 'SYP', 'TWD', 'THB', 'TTD', 'TRY', 'TVD', 'UAH', 'AED', 'GBP', 'USD', 'UYU', 'UZS', 'VEF', 'VND', 'YER', 'ZWD', 'LEK', '؋', '$', 'Ƒ', '₼', 'BR', 'BZ$', '$B', 'KM', 'P', 'ЛВ', 'R$', '៛', '¥', '₡', 'KN', '₱', 'KČ', 'KR', 'RD$', '£', '€', '¢', 'Q', 'L', 'FT', '₹', 'RP', '﷼', '₪', 'J$', '₩', '₭', 'ДЕН', 'RM', '₨', '₮', 'د.إ', 'MT', 'C$', '₦', 'B/.', 'GS', 'S/.', 'ZŁ', 'LEI', 'ДИН.', 'S', 'R', 'NT$', '฿', 'TT$', '₺', '₴', '$U', 'BS', '₫', 'Z$') THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN UPPER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('ALL', 'AFN', 'ARS', 'AWG', 'AUD', 'AZN', 'BSD', 'BBD', 'BYN', 'BZD', 'BMD', 'BOB', 'BAM', 'BWP', 'BGN', 'BRL', 'BND', 'KHR', 'CAD', 'KYD', 'CLP', 'CNY', 'COP', 'CRC', 'HRK', 'CUP', 'CZK', 'DKK', 'DOP', 'XCD', 'EGP', 'SVC', 'EUR', 'FKP', 'FJD', 'GHS', 'GIP', 'GTQ', 'GGP', 'GYD', 'HNL', 'HKD', 'HUF', 'ISK', 'INR', 'IDR', 'IRR', 'IMP', 'ILS', 'JMD', 'JPY', 'JEP', 'KZT', 'KPW', 'KRW', 'KGS', 'LAK', 'LBP', 'LRD', 'MKD', 'MYR', 'MUR', 'MXN', 'MNT', 'MZN', 'NAD', 'NPR', 'ANG', 'NZD', 'NIO', 'NGN', 'NOK', 'OMR', 'PKR', 'PAB', 'PYG', 'PEN', 'PHP', 'PLN', 'QAR', 'RON', 'RUB', 'SHP', 'SAR', 'RSD', 'SCR', 'SGD', 'SBD', 'SOS', 'ZAR', 'LKR', 'SEK', 'CHF', 'SRD', 'SYP', 'TWD', 'THB', 'TTD', 'TRY', 'TVD', 'UAH', 'AED', 'GBP', 'USD', 'UYU', 'UZS', 'VEF', 'VND', 'YER', 'ZWD', 'LEK', '؋', '$', 'Ƒ', '₼', 'BR', 'BZ$', '$B', 'KM', 'P', 'ЛВ', 'R$', '៛', '¥', '₡', 'KN', '₱', 'KČ', 'KR', 'RD$', '£', '€', '¢', 'Q', 'L', 'FT', '₹', 'RP', '﷼', '₪', 'J$', '₩', '₭', 'ДЕН', 'RM', '₨', '₮', 'د.إ', 'MT', 'C$', '₦', 'B/.', 'GS', 'S/.', 'ZŁ', 'LEI', 'ДИН.', 'S', 'R', 'NT$', '฿', 'TT$', '₺', '₴', '$U', 'BS', '₫', 'Z$') THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string not match regex count**
**Full sensor name**
```
column/strings/string_not_match_regex_count
```
**Description**  
Column level sensor that calculates the number of values that does not fit to a regex in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|regex|This field can be used to define custom regex. In order to define custom regex, user should write correct regex as a string. If regex is not defined by user then default regex is null|string|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         r{{ make_text_constant(regex) }}
    {% endmacro %}
    
    SELECT
      CASE
        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
      ELSE
      SUM(
        CASE
          WHEN REGEXP_CONTAINS({{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }}) IS TRUE THEN 0
        ELSE
        1
      END
        )
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro make_text_constant(string) -%}
        {{ '\'' }}{{ string | replace('\'', '\'\'') }}{{ '\'' }}
    {%- endmacro %}
    
    {%- macro render_regex(regex) -%}
         r{{ make_text_constant(regex) }}
    {% endmacro %}
    
    SELECT
      CASE
        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
      ELSE
      SUM(
        CASE
          WHEN REGEXP_CONTAINS({{ lib.render_target_column('analyzed_table') }}, {{ render_regex(parameters.regex) }}) IS TRUE THEN 0
        ELSE
        1
      END
        )
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string whitespace percent**
**Full sensor name**
```
column/strings/string_whitespace_percent
```
**Description**  
Column level sensor that calculates the number of rows with a whitespace string column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> '' AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = '' THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> '' AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = '' THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string valid ip6 address percent**
**Full sensor name**
```
column/strings/string_valid_ip6_address_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid IP6 address value in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$") IS TRUE THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$") IS TRUE THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string mean length**
**Full sensor name**
```
column/strings/string_mean_length
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      AVG(LENGTH({{lib.render_column_cast_to_string('analyzed_table')}})) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      AVG( LENGTH({{lib.render_column_cast_to_string('analyzed_table')}}) ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string valid uuid percent**
**Full sensor name**
```
column/strings/string_valid_uuid_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid UUID value in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$") IS TRUE THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$") IS TRUE THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string surrounded by whitespace percent**
**Full sensor name**
```
column/strings/string_surrounded_by_whitespace_percent
```
**Description**  
Column level sensor that calculates the number of rows with string surrounded by whitespace column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) <> '' AND ({{ lib.render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) <> '' AND ({{ lib.render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string length above max length count**
**Full sensor name**
```
column/strings/string_length_above_max_length_count
```
**Description**  
Column level sensor that calculates the count of values that are longer than a given length in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|maximum_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
      SUM(
        CASE
          WHEN LENGTH(SAFE_CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) >= {{(parameters.maximum_length)}} THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
      SUM(
        CASE
          WHEN LENGTH(SAFE_CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) >= {{(parameters.maximum_length)}} THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string in set percent**
**Full sensor name**
```
column/strings/string_in_set_percent
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|values|Provided list of values to match the data.|string_list|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
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
        {%- if parameters['values']|length == 0 -%}
            NULL
        {%- else -%}
          100.0 * SUM(
            CASE
              WHEN ({{lib.render_target_column('analyzed_table')}} IN ({{extract_in_list(parameters['values'])}})) IS TRUE THEN 1
            ELSE
            0
          END
            )/COUNT(*)
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN NULL
      ELSE
      {{render_else()}}
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
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
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
          CASE
            WHEN COUNT(*) = 0 THEN 100.0
          ELSE
          100.0 * SUM(
            CASE
              WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }}) THEN 1
            ELSE
            0
          END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
      {{ actual_value() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string null placeholder count**
**Full sensor name**
```
column/strings/string_null_placeholder_count
```
**Description**  
Column level sensor that calculates the number of rows with a null placeholder string column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '') THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '') THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string match date regex percent**
**Full sensor name**
```
column/strings/string_match_date_regex_percent
```
**Description**  
Column level sensor that calculates the percentage of values that does fit a given date regex in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|date_formats|Desired date format. Sensor will try to parse the column records and cast the data using this format.|enum||YYYY-MM-DD<br/>DD/MM/YYYY<br/>Month D, YYYY<br/>YYYY/MM/DD<br/>MM/DD/YYYY<br/>|


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$") IS TRUE THEN 0
        ELSE
        1
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
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
      ELSE
      100.0 * SUM(
        CASE
          WHEN SAFE.PARSE_DATE({{render_date_formats(parameters.date_formats)}}, SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) IS NOT NULL THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string valid ip4 address percent**
**Full sensor name**
```
column/strings/string_valid_ip4_address_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid IP4 address value in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$") IS TRUE THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$") IS TRUE THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string valid date percent**
**Full sensor name**
```
column/strings/string_valid_date_percent
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN SAFE_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE) IS NOT NULL THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN SAFE_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE) IS NOT NULL THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string most popular values**
**Full sensor name**
```
column/strings/string_most_popular_values
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|expected_values|Provided list of values to match the data.|string_list|||
|top_values|Provided limit of top popular values.|long|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
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
             ) top_col_values
        )
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
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
             ) top_col_values
        )
    WHERE top_values_rank <= {{ parameters.top_values }}
    {%- endmacro -%}
    
    SELECT
        {{ top_value() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string invalid email count**
**Full sensor name**
```
column/strings/string_invalid_email_count
```
**Description**  
Column level sensor that calculates the number of rows with an invalid emails value in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$") IS TRUE THEN 0
        ELSE
        1
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$") IS TRUE THEN 0
        ELSE
        1
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string match name regex percent**
**Full sensor name**
```
column/strings/string_match_name_regex_percent
```
**Description**  
Column level sensor that calculates the percentage of values that does fit a given name regex in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS( SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([\s-'])|([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{1})([.])(\s?))([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([\s-'.]?([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([\s-'.]?)(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([.])?))?$") THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN REGEXP_CONTAINS( SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([\s-'])|([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{1})([.])(\s?))([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})([\s-'.]?([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([\s-'.]?)(([a-zA-ZżźćńółęąśäöüåáéěíôúůýčďťĺňŕřšžçâêîôûàèìòùëïãõŻŹĆŃÓŁĘĄŚÄÖÜÅÁÉĚÍÔÚŮÝČĎŤĹŇŔŘŠŽÇÂÊÎÔÛÀÈÌÒÙËÏÃÕ]{2,})?([.])?))?$") THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string empty count**
**Full sensor name**
```
column/strings/string_empty_count
```
**Description**  
Column level sensor that calculates the number of rows with an empty string column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND {{ lib.render_column_cast_to_string('analyzed_table')}} = '' THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND {{ lib.render_column_cast_to_string('analyzed_table')}} = '' THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string invalid uuid count**
**Full sensor name**
```
column/strings/string_invalid_uuid_count
```
**Description**  
Column level sensor that calculates the number of rows with an invalid uuid value in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$") IS TRUE THEN 0
        ELSE
        1
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN REGEXP_CONTAINS(SAFE_CAST( {{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$") IS TRUE THEN 0
        ELSE
        1
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string not match date regex count**
**Full sensor name**
```
column/strings/string_not_match_date_regex_count
```
**Description**  
Column level sensor that calculates the number of values that does not fit to a date regex in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|date_formats|Desired date format. Sensor will try to parse the column records and cast the data using this format.|enum||YYYY-MM-DD<br/>DD/MM/YYYY<br/>Month D, YYYY<br/>YYYY/MM/DD<br/>MM/DD/YYYY<br/>|


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
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
      ELSE
      SUM(
        CASE
          WHEN SAFE.PARSE_DATE({{render_date_formats(parameters.date_formats)}}, SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) IS NULL THEN 1
        ELSE
        0
      END
        )
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
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
      ELSE
      SUM(
        CASE
          WHEN SAFE.PARSE_DATE({{render_date_formats(parameters.date_formats)}}, SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS STRING)) IS NULL THEN 1
        ELSE
        0
      END
        )
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string parsable to float percent**
**Full sensor name**
```
column/strings/string_parsable_to_float_percent
```
**Description**  
Column level sensor that calculates the number of rows with parsable to float string column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * COUNT( TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS FLOAT64) ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * COUNT( SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS FLOAT64) ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string surrounded by whitespace count**
**Full sensor name**
```
column/strings/string_surrounded_by_whitespace_count
```
**Description**  
Column level sensor that calculates the number of rows with string surrounded by whitespace column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) <> '' AND ({{ lib.render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) <> '' AND ({{ lib.render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string empty percent**
**Full sensor name**
```
column/strings/string_empty_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with an empty string column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND {{ lib.render_column_cast_to_string('analyzed_table')}} = '' THEN 1
        ELSE
        0
      END
        )/COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND {{ lib.render_column_cast_to_string('analyzed_table')}} = '' THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **string null placeholder percent**
**Full sensor name**
```
column/strings/string_null_placeholder_percent
```
**Description**  
Column level sensor that calculates the number of rows with a null placeholder string column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '') THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '') THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___


## **uniqueness** column sensors
___

### **duplicate count**
**Full sensor name**
```
column/uniqueness/duplicate_count
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}})) = 1 THEN COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
      ELSE
      (COUNT({{ lib.render_target_column('analyzed_table')}}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}}))) AS actual_value
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }})) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **duplicate percent**
**Full sensor name**
```
column/uniqueness/duplicate_percent
```
**Description**  
Column level sensor that calculates the percentage of rows that are duplicates.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0 THEN 100.0
      ELSE
      100.0 * (COUNT({{ lib.render_target_column('analyzed_table')}}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table')}})) / COUNT({{ lib.render_target_column('analyzed_table')}})
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
      ELSE
      100.0 * ( COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) ) / COUNT({{ lib.render_target_column('analyzed_table') }})
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **unique count**
**Full sensor name**
```
column/uniqueness/unique_count
```
**Description**  
Column level sensor that calculates the number of unique non-null values.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      COUNT( DISTINCT({{ lib.render_target_column('analyzed_table') }}) ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      COUNT( DISTINCT({{ lib.render_target_column('analyzed_table')}}) ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___


## **nulls** column sensors
___

### **null percent**
**Full sensor name**
```
column/nulls/null_percent
```
**Description**  
Column-level sensor that calculates the percentage of rows with null values.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NULL THEN 1
                    ELSE 0
                END
                )/COUNT(*)
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NULL THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **not null count**
**Full sensor name**
```
column/nulls/not_null_count
```
**Description**  
Column-level sensor that calculates the number of rows with not null values.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      COUNT({{ lib.render_target_column('analyzed_table') }}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      COUNT({{ lib.render_target_column('analyzed_table') }}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **null count**
**Full sensor name**
```
column/nulls/null_count
```
**Description**  
Column-level sensor that calculates the number of rows with null values.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NULL THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NULL THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___


## **numeric** column sensors
___

### **values in range numeric percent**
**Full sensor name**
```
column/numeric/values_in_range_numeric_percent
```
**Description**  
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|min_value|Minimal value range variable.|double|||
|max_value|Maximal value range variable.|double|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **sample variance**
**Full sensor name**
```
column/numeric/sample_variance
```
**Description**  
Column level sensor that counts negative values in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        VAR_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        VAR_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **mean**
**Full sensor name**
```
column/numeric/mean
```
**Description**  
Column level sensor that counts the average (mean) of values in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **numbers in set percent**
**Full sensor name**
```
column/numeric/numbers_in_set_percent
```
**Description**  
Column level sensor that calculates the percentage of values that are members of a given set.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|values|Provided list of values to match the data.|integer_list|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters['values']|length == 0 -%}
            NULL
        {%- else -%}
              100.0 * SUM(
                CASE
                  WHEN ({{lib.render_target_column('analyzed_table')}} IN ({{extract_in_list(parameters['values'])}})) THEN 1
                ELSE
                0
              END
                )/COUNT(*)
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN NULL
      ELSE
      {{render_else()}}
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        {#- Two approaches could be taken here. What if COUNT(*) = 0 AND value set is empty? This solution is the most convenient. -#}
        NULL
        {%- else -%}
          CASE
            WHEN COUNT(*) = 0 THEN 100.0
          ELSE
          100.0 * SUM(
            CASE
              WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }}) THEN 1
            ELSE
            0
          END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
      {{ actual_value() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **negative count**
**Full sensor name**
```
column/numeric/negative_count
```
**Description**  
Column level sensor that counts negative values in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **negative percent**
**Full sensor name**
```
column/numeric/negative_percent
```
**Description**  
Column level sensor that counts percentage of negative values in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
        ELSE
        0
      END
        ) / COUNT(*)
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **numbers in set count**
**Full sensor name**
```
column/numeric/numbers_in_set_count
```
**Description**  
Column level sensor that counts values that are members of a given set.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|values|Provided list of values to match the data.|integer_list|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters['values']|length == 0 -%}
            NULL
        {%- else -%}
              SUM(
                CASE
                  WHEN ({{lib.render_target_column('analyzed_table')}} IN ({{extract_in_list(parameters['values'])}})) THEN 1
                ELSE
                0
              END
                )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
      CASE
        WHEN COUNT(*) = 0 THEN NULL
      ELSE
      {{render_else()}}
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        NULL
        {%- else -%}
          SUM(
            CASE
              WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }}) THEN 1
            ELSE
            0
          END
            )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
      {{ actual_value() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **population variance**
**Full sensor name**
```
column/numeric/population_variance
```
**Description**  
Column level sensor that counts negative values in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        VAR_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        VAR_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **sum**
**Full sensor name**
```
column/numeric/sum
```
**Description**  
Column level sensor that counts the sum of values in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **sample stddev**
**Full sensor name**
```
column/numeric/sample_stddev
```
**Description**  
Column level sensor that counts negative values in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **values in range integers percent**
**Full sensor name**
```
column/numeric/values_in_range_integers_percent
```
**Description**  
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|min_value|Minimal value range variable.|long|||
|max_value|Maximal value range variable.|long|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      100.0 * SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
        ELSE
        0
      END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **population stddev**
**Full sensor name**
```
column/numeric/population_stddev
```
**Description**  
Column level sensor that counts negative values in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___


## **range** column sensors
___

### **min value**
**Full sensor name**
```
column/range/min_value
```
**Description**  
Column level sensor that counts minimum value in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **max value**
**Full sensor name**
```
column/range/max_value
```
**Description**  
Column level sensor that counts maximum value in a column.


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **min value**
**Full sensor name**
```
column/range/min_value
```
**Description**  
Column level sensor that finds the minimum value. It works on any data type that supports the MIN functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **max value**
**Full sensor name**
```
column/range/max_value
```
**Description**  
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___


## **sql** column sensors
___

### **sql condition passed count**
**Full sensor name**
```
column/sql/sql_condition_passed_count
```
**Description**  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **sql aggregated expression**
**Full sensor name**
```
column/sql/sql_aggregated_expression
```
**Description**  
Column level sensor that executes a given SQL expression on a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_expression|SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or withing a GROUP BY clause for daily partitions and/or data streams. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **sql condition failed percent**
**Full sensor name**
```
column/sql/sql_condition_failed_percent
```
**Description**  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT ({{ lib.render_target_column('analyzed_table')}}) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND NOT ({{ parameters.sql_condition | REPLACE('{column}', lib.render_target_column('analyzed_table')) | REPLACE('{table}', lib.render_target_table()) }}) THEN 1
        ELSE
        0
      END
        ) / COUNT({{ lib.render_target_column('analyzed_table')}})
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT ({{ lib.render_target_column('analyzed_table')}}) = 0 THEN 100.0
      ELSE
      100.0 * SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) THEN 1
        ELSE
        0
      END
        ) / COUNT({{ lib.render_target_column('analyzed_table')}})
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **sql condition failed count**
**Full sensor name**
```
column/sql/sql_condition_failed_count
```
**Description**  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      SUM(
        CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) THEN 1
        ELSE
        0
      END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

### **sql condition passed percent**
**Full sensor name**
```
column/sql/sql_condition_passed_percent
```
**Description**  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string|||


**SQL Template (Jinja2)**  
=== "snowflake"
      
    ```
    {% raw %}
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT ({{ lib.render_target_column('analyzed_table')}}) = 0 THEN 100.0
      ELSE
      100.0 * SUM(CASE
          WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) THEN 1
        ELSE
        0
      END
        ) / COUNT({{ lib.render_target_column('analyzed_table')}})
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
=== "bigquery"
      
    ```
    {% raw %}
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
      CASE
        WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
      ELSE
      100.0 * SUM(CASE
          WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) THEN 1
        ELSE
        0
      END
        ) / COUNT({{ lib.render_target_column('analyzed_table') }})
    END
      AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    {% endraw %}
    ```
___

