# Definition of a data quality sensor
Read this guide to understand how data quality sensors in DQOps capture metrics about monitored data sources for detecting data quality issues with rules.

## What are sensors in DQOPs?

In DQOps, the data quality sensor and [data quality rule](definition-of-data-quality-rules.md) form the [data quality check](definition-of-data-quality-checks/index.md).

The data quality sensor reads the value from the data source at a given point in time. Examples of these reads include the
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


| Macro name                         | Description                                                                                                                                                                                                                                                                                                                                                                                                                        |
|------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `render_target_table`              | Adds target table name.                                                                                                                                                                                                                                                                                                                                                                                                            |
| `render_data_grouping_projections` | Adds [data grouping](measuring-data-quality-with-data-grouping.md) columns to the list of columns returned to support the **GROUP BY** clause. Read the [data grouping configuration](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) manual to learn how to configure data grouping. Data grouping configuration allows tracking data quality results for different groups of rows stored in the same table. |
| `render_time_dimension_projection` | Optional time dimension projection that allows measuring individual data quality results for each time period (hour, day, week, etc.) separately.                                                                                                                                                                                                                                                                                  |
| `render_where_clause`              | The **WHERE** clause is used to filter records.                                                                                                                                                                                                                                                                                                                                                                                    |
| `render_group_by`                  | The **GROUP BY** statement groups rows by dates, partitions or additional columns. The columns selected for data grouping are also returned.                                                                                                                                                                                                                                                                                       |
| `render_order_by`                  | The **ORDER BY** statement sorts the results from the oldest to the newest daily partitions.                                                                                                                                                                                                                                                                                                                                       |


## Sensor types and categories

Sensors are divided into two types: table and column. Each type has several categories and subcategories.
A full list of sensors within each category is available at the link.
 
| Sensor category                                                                                 | Description                                                                                                                                                                                                    |
|-------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Table**                                                                                       |                                                                                                                                                                                                                |
| [accuracy table sensors](../reference/sensors/table/accuracy-table-sensors.md)                  | Verifies the correct number of rows between the tested and reference table.                                                                                                                                    |
| [availability table sensors](../reference/sensors/table/availability-table-sensors.md)          | Detects problems with data source stability.                                                                                                                                                                   |
| [schema table sensors](../reference/sensors/table/schema-table-sensors.md)                      | Detects the schema changes at the table level.                                                                                                                                                                 |
| [custom sql table sensors](../reference/sensors/table/custom_sql-table-sensors.md)              | Verifies custom SQL queries at the table level.                                                                                                                                                                |
| [timeliness table sensors](../reference/sensors/table/timeliness-table-sensors.md)              | Verifies freshness, staleness, and ingestion delay.                                                                                                                                                            |
| [volume table sensors](../reference/sensors/table/volume-table-sensors.md)                      | Verifies data volume.                                                                                                                                                                                          |
| **Column**                                                                                      |                                                                                                                                                                                                                |
| [accepted values column sensors](../reference/sensors/column/accepted_values-column-sensors.md) | Verifies that values in a column are only from an accepted list of values.                                                                                                                                     |
| [accuracy column sensors](../reference/sensors/column/accuracy-column-sensors.md)               | Verifies the correctness of data between the tested and reference column.                                                                                                                                      |
| [blanks column sensors](../reference/sensors/column/blanks-column-sensors.md)                   | Detects columns storing blank values, such as 'None', 'n/a', ''.                                                                                                                                               |
| [bool column sensors](../reference/sensors/column/bool-column-sensors.md)                       | Detects issues in columns with boolean-type data.                                                                                                                                                              |
| [custom sql column sensors](../reference/sensors/column/custom_sql-column-sensors.md)           | Verifies custom SQL queries at the column level.                                                                                                                                                               |
| [datatype column sensors](../reference/sensors/column/datatype-column-sensors.md)               | Detects issues in columns with datetime-type data.                                                                                                                                                             |
| [datetime column sensors](../reference/sensors/column/datetime-column-sensors.md)               | Detects issues in columns with datatype data.                                                                                                                                                                  |
| [integrity column sensors](../reference/sensors/column/integrity-column-sensors.md)             | Detects integrity issues between different columns.                                                                                                                                                            |
| [nulls column sensors](../reference/sensors/column/nulls-column-sensors.md)                     | Detects nulls and not nulls on columns.                                                                                                                                                                        |
| [numeric column sensors](../reference/sensors/column/numeric-column-sensors.md)                 | Detects issues in columns with numeric data such as negative values and values in sets or in range. Detects whether basic statistic calculations such as max, min, mean, median, sum, and stddev are in range. |
| [patterns column sensors](../reference/sensors/column/patterns-column-sensors.md)               | Verifies text columns by matching the values to custom or predefined patterns (regular expressions)                                                                                                            |
| [pii column sensors](../reference/sensors/column/pii-column-sensors.md)                         | Detects the presence of sensitive data such as phone number, zip code, e-mail, and IP4 and IP6 addresses.                                                                                                      |
| [range column sensors](../reference/sensors/column/range-column-sensors.md)                     | Detects min or max values.                                                                                                                                                                                     |
| [sampling column sensors](../reference/sensors/column/sampling-column-sensors.md)               | Retrieves column value samples.                                                                                                                                                                                |
| [schema column sensors](../reference/sensors/column/schema-column-sensors.md)                   | Detects the schema changes at the column level.                                                                                                                                                                |
| [text column sensors](../reference/sensors/column/text-column-sensors.md)                       | Detects issues in columns with string-type data.                                                                                                                                                               |
| [uniqueness column sensors](../reference/sensors/column/uniqueness-column-sensors.md)           | Detect uniqueness and duplications.                                                                                                                                                                            |


## Sensor readout data storage

DQOps stores a copy of the sensor data locally on the DQOps instance. The data files are stored as Apache Parquet files
in an Apache Hive-compatible folder tree, partitioned by the data source, monitored table name, and the month.
The sensor's query results are called **sensor readouts** in DQOps. The results are stored
in a [sensor_readouts](../reference/parquetfiles/sensor_readouts.md) parquet table as described in
the [data quality results storage](data-storage-of-data-quality-results.md) concept.


## Configure sensors in UI
You can easily configure sensors in DQOps using the **Configuration** section of the user interface.

### **Sensor definition screen**
Below is an example of screens with the definition for the [text_length_in_range_percent](../reference/sensors/column/text-column-sensors.md) sensor
and the Jinja template for the PostgreSQL database which can be modified.

The first tab named the *Sensor definition* is responsible for editing the specification files for
a custom data quality sensor
stored in the [*$DQO_USER_HOME/sensors/\*\*/\*.dqosensor.yaml*](../reference/yaml/SensorDefinitionYaml.md) files.

![Sensor definition configuration](https://dqops.com/docs/images/concepts/sensor-definition-configuration.png)

### **Database specific SQL query template**
The Jinja2 query templates are edited on the tabs named as the [data sources](../data-sources/index.md).
The configuration for each data source is stored in two files:

- [*sensors/\*\*/&lt;data_source_type&gt;.dqoprovidersensor.yaml*](../reference/yaml/ProviderSensorYaml.md)
  specification file with additional configuration for that data source type.

- Jinja2 file stored in the *sensors/\*\*/&lt;data_source_type&gt;.sql.jinja2* file.

![PostgreSQL template](https://dqops.com/docs/images/concepts/sensor-postgresql-jinja-template.png)


## What's next
- Learn how DQOps [executes data quality sensors and rules](architecture/data-quality-check-execution-flow.md).
- Learn how the [data quality results are stored](data-storage-of-data-quality-results.md).
- Review the full reference of all [supported data quality sensors](../reference/sensors/index.md) with examples
  of templated SQL queries for each [data source](../data-sources/index.md).