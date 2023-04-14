# Sensors

In DQO, the data quality sensor and [data quality rule](../rules/rules.md) form the [data quality check](../checks/index.md).

Data quality sensor reads the value from the data source at a given point in time. Examples of these reads includes the
number of rows, the percentage of null values in a column, or the current delay between the timestamp of the latest row
and the current system time.

## Sensor templating

To implement sensors DQO uses the Jinja2 templating engine which is rendered into a SQL query.

The following examples show a data quality sensor template for various database types that calculates the number of rows
in a table.

**SQL Template (Jinja2)**  
=== "bigquery"

    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"

    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"

    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"

    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
The file starts with an import of reusable dialects specific to the database.

| Maro name                        | Description                                                                                                                                       |
|----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| render_target_table              | Adds target table name.                                                                                                                           |
| render_data_stream_projections   | Optional data stream projection that allows tracking data quality results for different data streams aggregated in the same table.                |
| render_time_dimension_projection | Optional time dimension projection that allows measuring individual data quality results for each time period (hour, day, week, etc.) separately. |
| render_where_clause              | WHERE clause is used to filter records.                                                                                                           |
| render_group_by                  | GROUP BY statement group rows by dates, partitions or additional columns.                                                                         |
| render_order_by                  | ORDER BY sort the results from oldest to the newest daily partitions.                                                                             |

## Sensor types and categories

Sensors are divided into two types: table and column. Each type has several categories and subcategories.
A full list of sensors within each category is available at the link.

| Sensor category                                                                            | Description                                                                                                                                                                                                |
|--------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Table**                                                                                  |                                                                                                                                                                                                            |
| [availability table sensors](../../../reference/sensors/table/availability-table-sensors/) | Detects problems with datasource stability.                                                                                                                                                                |
| [sql table sensors](../../../reference/sensors/table/sql-table-sensors/)                   | Verifies custom SQL queries at the table level.                                                                                                                                                            |
| [standard table sensors](../../../reference/sensors/table/standard-table-sensors/)         | Verifies data volume.                                                                                                                                                                                      |
| [timeliness table sensors](../../../reference/sensors/table/timeliness-table-sensors/)     | Verifies freshness, staleness and ingestion delay.                                                                                                                                                         |
| **Column**                                                                                 |                                                                                                                                                                                                            |
| [bool column sensors](../../../reference/sensors/column/bool-column-sensors/)              | Detects issues in columns with boolean-type data.                                                                                                                                                          |
| [datetime column sensors](../../../reference/sensors/column/datetime-column-sensors/)      | Detects issues in columns with datetime-type data.                                                                                                                                                         |
| [integrity column sensors](../../../reference/sensors/column/integrity-column-sensors/)    | Detects integrity issues between different columns.                                                                                                                                                        |
| [nulls column sensors](../../../reference/sensors/column/nulls-column-sensors/)            | Detects nulls and not nulls on columns.                                                                                                                                                                    |
| [numeric column sensors](../../../reference/sensors/column/numeric-column-sensors/)        | Detects issues in columns with numeric data such as negative values and values in sets or in range. Detects whether basic statistic calculations such as max, min, mean, median, sum, stddev are in range. |
| [pii column sensors](../../../reference/sensors/column/pii-column-sensors/)                | Detects presence of sensitive data such as phone number, zip code, e-mail, and IP4 and IP6 addresses.                                                                                                      |
| [range column sensors](../../../reference/sensors/column/range-column-sensors/)            |                                                                                                                                                                                                            |
| [sql column sensors](../../../reference/sensors/column/sql-column-sensors/)                | Verifies custom SQL queries at the column level.                                                                                                                                                           |
| [strings column sensors](../../../reference/sensors/column/strings-column-sensors/)        | Detects issues in columns with string-type data.                                                                                                                                                           |
| [uniqueness column sensors](../../../reference/sensors/column/uniqueness-column-sensors/)  | Detect uniqueness and duplications.                                                                                                                                                                        |

## Storing sensor data

DQO stores a copy of the sensor data locally on the monitoring agent. The data files are stored as Apache Parquet files
in an Apache Hive compatible folder tree, partitioned by the data source, monitored table name, and the month. A local 
copy of the sensor data enables a true multi-cloud data collection, without accessing any sensitive data by an external 
cloud or SaaS solution.