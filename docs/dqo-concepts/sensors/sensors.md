# Sensors

## What are sensors in DQOPs?

In DQOps, the data quality sensor and [data quality rule](../rules/rules.md) form the [data quality check](../checks/index.md).

Data quality sensor reads the value from the data source at a given point in time. Examples of these reads includes the
number of rows, the percentage of null values in a column, or the current delay between the timestamp of the latest row
and the current system time.

## Sensor templating

To implement sensors DQOps uses the Jinja2 templating engine which is rendered into a SQL query.

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
        {{- lib.render_data_grouping_projections('analyzed_table') }}
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
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
The file starts with an import of reusable dialects specific to the database.


| Macro name                         | Description                                                                                                                                                                                                                                                                                                                                                                                                                  |
|------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `render_target_table`              | Adds target table name.                                                                                                                                                                                                                                                                                                                                                                                                      |
| `render_data_grouping_projections` | Adds data [data grouping](../data-grouping/data-grouping.md) columns to the list of columns returned to support the **GROUP BY** clause. Read the [data grouping configuration](../../working-with-dqo/set-up-data-grouping/set-up-data-grouping.md) manual to learn how to configure data grouping. Data grouping configuration allows tracking data quality results for different groups of rows stored in the same table. |
| `render_time_dimension_projection` | Optional time dimension projection that allows measuring individual data quality results for each time period (hour, day, week, etc.) separately.                                                                                                                                                                                                                                                                            |
| `render_where_clause`              | WHERE clause is used to filter records.                                                                                                                                                                                                                                                                                                                                                                                      |
| `render_group_by`                  | GROUP BY statement group rows by dates, partitions or additional columns. The columns selected for data grouping are also returned.                                                                                                                                                                                                                                                                                          |
| `render_order_by`                  | ORDER BY sort the results from oldest to the newest daily partitions.                                                                                                                                                                                                                                                                                                                                                        |


## Sensor types and categories

Sensors are divided into two types: table and column. Each type has several categories and subcategories.
A full list of sensors within each category is available at the link.
 
| Sensor category                                                                           | Description                                                                                                                                                                                                |
|-------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Table**                                                                                 |                                                                                                                                                                                                            |
| [accuracy table sensors](../../reference/sensors/table/accuracy-table-sensors.md)         | Verifies the correct number of rows between tested and reference table.                                                                                                                                    |
| [availability table sensors](../../reference/sensors/table/availability-table-sensors.md) | Detects problems with datasource stability.                                                                                                                                                                |
| [schema table sensors](../../reference/sensors/table/schema-table-sensors.md)             | Detects the schema changes at the table level.                                                                                                                                                             |
| [custom sql table sensors](../../reference/sensors/table/custom_sql-table-sensors.md)     | Verifies custom SQL queries at the table level.                                                                                                                                                            |
| [timeliness table sensors](../../reference/sensors/table/timeliness-table-sensors.md)     | Verifies freshness, staleness and ingestion delay.                                                                                                                                                         |
| [volume table sensors](../../reference/sensors/table/volume-table-sensors.md)             | Verifies data volume.                                                                                                                                                                                      |
| **Column**                                                                                |                                                                                                                                                                                                            |
| [accuracy column sensors](../../reference/sensors/column/accuracy-column-sensors.md)      | Verifies the correctness of data between tested and reference column.                                                                                                                                      |
| [bool column sensors](../../reference/sensors/column/bool-column-sensors.md)              | Detects issues in columns with boolean-type data.                                                                                                                                                          |
| [datatype column sensors](../../reference/sensors/column/datatype-column-sensors.md)      | Detects issues in columns with datetime-type data.                                                                                                                                                         |
| [datetime column sensors](../../reference/sensors/column/datetime-column-sensors.md)      | Detects issues in columns with datatype data.                                                                                                                                                              |
| [integrity column sensors](../../reference/sensors/column/integrity-column-sensors.md)    | Detects integrity issues between different columns.                                                                                                                                                        |
| [nulls column sensors](../../reference/sensors/column/nulls-column-sensors.md)            | Detects nulls and not nulls on columns.                                                                                                                                                                    |
| [numeric column sensors](../../reference/sensors/column/numeric-column-sensors.md)        | Detects issues in columns with numeric data such as negative values and values in sets or in range. Detects whether basic statistic calculations such as max, min, mean, median, sum, stddev are in range. |
| [pii column sensors](../../reference/sensors/column/pii-column-sensors.md)                | Detects presence of sensitive data such as phone number, zip code, e-mail, and IP4 and IP6 addresses.                                                                                                      |
| [range column sensors](../../reference/sensors/column/range-column-sensors.md)            | Detects min or max values.                                                                                                                                                                                 |
| [sampling column sensors](../../reference/sensors/column/sampling-column-sensors.md)      | Retrieves column value samples.                                                                                                                                                                            |
| [schema column sensors](../../reference/sensors/column/schema-column-sensors.md)          | Detects the schema changes at the column level.                                                                                                                                                            |
| [custom sql column sensors](../../reference/sensors/column/custom_sql-column-sensors.md)  | Verifies custom SQL queries at the column level.                                                                                                                                                           |
| [text column sensors](../../reference/sensors/column/text-column-sensors.md)              | Detects issues in columns with string-type data.                                                                                                                                                           |
| [uniqueness column sensors](../../reference/sensors/column/uniqueness-column-sensors.md)  | Detect uniqueness and duplications.                                                                                                                                                                        |


## Sensor data storage

DQOps stores a copy of the sensor data locally on the monitoring agent. The data files are stored as Apache Parquet files
in an Apache Hive compatible folder tree, partitioned by the data source, monitored table name, and the month.
The sensor's query results are called **sensor readouts** in DQOps. The results are stored
in a [sensor_readouts](../../reference/parquetfiles/sensor_readouts.md) parquet table as described in
the [data storage](../data-storage/data-storage.md) concept.

