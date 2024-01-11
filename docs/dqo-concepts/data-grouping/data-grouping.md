# Data grouping

## How does data grouping work?

When a new data source's metadata is imported into DQOps and the first data quality checks are activated, the checks will
analyze the whole table. The data quality check will capture one metric (using a data quality sensor, that is an SQL query).
Then, that single data quality sensor readout (the captured metric) is evaluated through the rule engine, which will
result in creating a data quality rule result that is valid (no data quality issue detected) or is a data quality issue, 
ranked at three severity levels: warning, error or fatal.

A single data quality rule result for a whole table is not always the best solution. The data in the table is often coming
from different data sources, different vendors or is loaded by different data pipelines. When the data in the table comes from
different sources, the source is usually identified by a value in dedicated discriminator columns. The typical discriminator
columns are: country, business unit, cost center, vendor, data provider, supplier or subsidiary.

In order to analyze each group of rows, DQOps runs SQL queries on the monitored data sources using a GROUP BY clause in the query.
A data quality check that counts the percentage of null values in a column would return multiple values. 
Each row returned from the query is evaluated through the rule engine separately.

Here is an example of an SQL query that DQOps will generate to count the percentage of null values in an analyzed column
for each country. The following example assumes that there is a _country_ column in the monitored table, and we want to
analyze the data for each country separately.

```sql hl_lines="11-11"
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN analyzed_table."target_column" IS NULL THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    analyzed_table."country" AS grouping_level_1
FROM "your_postgresql_database"."public"."analyzed_table" AS analyzed_table
GROUP BY grouping_level_1
ORDER BY grouping_level_1
```

The result set returned by the SQL query above is shown below. 

| actual_value | grouping_level_1 |
|--------------|------------------|
| 31.4%        | US               |
| 58.2%        | UK               |
| 55.1%        | DE               |
| 56.7%        | IT               |

DQOps will store all results in the [sensor_readout](../../reference/parquetfiles/sensor_readouts.md) parquet table.
Each sensor readout will be verified by a data quality rule, generating a data quality check result
for each data group returned by the query.

In order to avoid storing too many data quality results in the DQOps Cloud Data Warehouse,
DQOps captures only the first 1000 results. When the query returns more than 1000 rows, DQOps will
cancel the query and discard the remaining results. The behavior can be changed using the
*--dqo.sensor.limit.fail-on-sensor-readout-limit-exceeded=true* configuration parameter.

The columns used for data grouping should be selected with care, knowing that using columns with
too many distinct values (more than 1000) will cause result set truncation.
At best, the column used for grouping should identify different data sources or different business areas.


## Grouping data at multiple levels

DQOps can also analyze data at multiple dimensions, for example, when the data is divided both by country and state.
Additional columns used for data grouping are added to the GROUP BY clause as shown in the following query.

```sql hl_lines="11-12"
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN analyzed_table."target_column" IS NULL THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    analyzed_table."country" AS grouping_level_1,
    analyzed_table."state" AS grouping_level_2
FROM "your_postgresql_database"."public"."analyzed_table" AS analyzed_table
GROUP BY grouping_level_1, grouping_level_2
ORDER BY grouping_level_1, grouping_level_2
```

The result set returned by the SQL query above is shown below.

| actual_value | grouping_level_1 | grouping_level_2 |
|--------------|------------------|------------------|
| 37.4%        | US               | CA               |
| 52.1%        | US               | NY               |
| 44.2%        | US               | WA               |


DQOps supports setting of up to 9 different data grouping dimensions (levels).


## Configuring data grouping

Data grouping is configured for each table in the [.dqotable.yaml](../../reference/yaml/TableYaml.md) files
as shown in the example below.


``` { .yaml .annotate linenums="1" hl_lines="7-15" }
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  default_grouping_name: group_by_country_and_state # (1)!
  groupings: # (2)!
    group_by_country_and_state: # (3)!
      level_1: # (4)!
        source: column_value # (5)!
        column: country # (6)!
      level_2: # (7)!
        source: column_value
        column: state
  columns:
    unique_key:
      type_snapshot:
        column_type: INT64
        nullable: true
    country:
      type_snapshot:
        column_type: STRING
        nullable: true
    state:
      type_snapshot:
        column_type: STRING
        nullable: true
```

1.  The **default_grouping_name** field stores the name of the active data grouping configuration that is applied to data quality checks.

2.  The **grouping** node is a dictionary of named data grouping configurations. One of the named configuration is selected
    in the **default_grouping_name** field.

3.  The configuration of one example data grouping, named *group_by_country_and_state* in this example.

4.  The configuration of the first column used for grouping.

5.  The source field identifies the data source for data grouping. Supported values are: **column_value** when the grouping
    value should be captured from the data in the monitored table, by adding a GROUP BY condition. Or **tag**, when a hardcoded
    value should be applied on all results.

6.  The name of the column that is added to the GROUP BY clause for dynamic grouping by row values.

7.  The configuration for the second grouping level. Additional grouping levels are: *level_2*, ..., *level_9*.


Each table can have multiple named configuration of data groupings that are defined in the **groupings** dictionary node.
The active data grouping that is applied on data quality checks is selected in the **default_grouping_name** field.

Each named data grouping configuration contains the configuration of up to 9 data grouping levels, but not all levels
must be configured. The configuration of each data grouping level is defined in the *level_2*, ..., *level_9* nodes.

For each data grouping level, the source of values used for data grouping must be selected.
The supported values for the **source** field are:

- **column_value** - the data grouping level is dynamic, the data grouping value is captured from the monitored table
  by adding the column to the GROUP BY clause. The name of the column is provided in the *column* field.

- **tag** - the data grouping level is static, assigning the same hardcoded data grouping value to all data quality results
  (sensor readouts, data quality check results). Tagging is used for grouping the results of similar tables, especially
  for calculating [data quality KPIs](../definition-of-data-quality-kpis.md) for different data areas, data pipelines,
  or data sources.


### **Grouping by calculated columns**
Virtual columns that are defined as SQL expressions ([calculated columns](../checks/configuring-checks.md#calculated-columns))
can be also used for dynamic grouping. This complex scenario could be used to run data quality checks on unstructured data
in the landing zone of the data warehouse.

The calculated column must be defined under the *columns* node. Once the calculated column is defined, it can be referenced
in the **column** field inside the data grouping level configuration (*level_1*, ...).

``` { .yaml .annotate linenums="1" hl_lines="12 18-22" }
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  default_grouping_name: by_type_code
  groupings:
    by_type_code:
      level_1:
        source: column_value
        column: country # (1)!
  columns:
    unique_key:
      type_snapshot:
        column_type: INT64
        nullable: true
    event_type_code: # (2)!
      type_snapshot:
        column_type: STRING
        nullable: true
      sql_expression: "CASE WHEN SPLIT({alias}.message, '|')[0] = 'EVN' THEN SPLIT({alias}.message, '|')[1] ELSE NULL END" # (3)!
```

1.  The name of the calculated column.

2.  The configuration of the calculated column. 

3.  The formula of the calculated column, an SQL expression.


## Grouping similar tables
DQOps supports another scenario, when the data warehouse has similar tables (even with the same schema), that contain
data from different data sources. 
The data quality results captured from those tables could be tagged by the name of the data source, external vendor, or department.

When the results are tagged, the values are stored in the sensor readout and rule result tables, keeping the data group names.
The [data quality dashboards](../data-quality-dashboards/data-quality-dashboards.md) in DQOps always have a filter
for **data group**, allowing to deep-dive into data quality issues related to that source, or the data suppliers.

Tagging data quality results becomes even more important when combined with [data quality KPIs](../definition-of-data-quality-kpis.md),
because a separate data quality KPI score can be calculated for each data supplier, vendor, department, or any other data area.

The tags are defined under the *level_1*, ..., *level_9* nodes as show below. 

``` { .yaml .annotate linenums="1" hl_lines="11-12" }
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  default_grouping_name: by_supplier
  groupings:
    by_supplier:
      level_1:
        source: tag # (1)!
        tag: US # (2)!
      level_2:
        source: tag
        tag: "Supplier, Inc."
  columns:
    unique_key:
      type_snapshot:
        column_type: INT64
        nullable: true
```

1.  The **source** value must be "tag".

2.  The **tag** node contains a constant value.


DQOps also supports mixing dynamic dimensions (using a column value added to the GROUP BY clause), and static tags.

``` { .yaml .annotate linenums="1" hl_lines="11 14" }
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  default_grouping_name: by_supplier
  groupings:
    by_supplier:
      level_1:
        source: tag
        tag: US
      level_2:
        source: column_value
        column: state
  columns:
    state:
      type_snapshot:
        column_type: STRING
        nullable: false
```


## Data grouping as time series

DQOps treats all data quality sensor readouts (metrics) and all data quality check results (rule evaluation results)
as time series. Suppose that a table contains data from two countries and each group of data is identified by
the value of the _country_ column value. The user activated a **daily_row_count** check on the table.
DQOps will track the row count time series for both countries, allowing to detect anomalies or missing data
for only one country. Detecting data quality issues that are related to a single data source is crucial for the root
cause analysis and allows to contact the right person, 
responsible for providing the data from that data grouping (the country in this example).

When a data quality issue is identified (a data quality check's rule raised an alert), DQOps could be configured
to create either one data quality incident for the whole table (without identification of the data group) or for each
group of data (the country in this example) as a separate data quality incident.

The data quality dashboards in DQOps have a configuration parameter to select the data grouping for which we want to
find the most recent data quality issues or calculate the data quality KPI.
The [data quality KPI](../definition-of-data-quality-kpis.md) scores that is calculated for each data source (data grouping)
simplify the root cause analysis by linking the data quality issue to a data source, a data group,
an external data supplier, a data provider, or simply a separate data pipeline that has loaded invalid data.


## Configuring data grouping in UI

In DQOps you can set up data grouping globally for all tables in the connection or individually for each table.
You can set up data grouping in the **Data Sources** section by clicking at the connection or individual table in the tree and 
selecting **Data Grouping** tab.
It is also important to mention that the configuration of data grouping on the connection level
is only a template of the default grouping configuration that is copied to tables that are imported.
When a table was already imported into DQOps, changing the configuration on the connection level has no effect.

For more detailed information on setting up a data grouping, go to [Working with DQOps section](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md).


## How DQOps stores data grouping results
DQOps stores the data in Parquet files. The format of parquet files for the sensor readout ([.data/sensor_readouts/](../../reference/parquetfiles/sensor_readouts.md))
and the check results ([.data/check_results/](../../reference/parquetfiles/check_results.md)) are documented in the reference section.

The columns that identify the data groups are:


- **data_grouping_configuration** - the name of the data grouping configuration that is configured on the table level
  and was used to run the data quality checks. When no grouping configuration was configured, the default grouping configuration
  is named *default*.


- **grouping_level_X** - 9 columns *grouping_level_1*, *grouping_level_2*, ..., *grouping_level_9* that contain the 
  values for each data group dimension.


- **data_group_name** - generated data group name that is a concatenation of the values from the *grouping_level_X* columns.
  The format of the group name when one or more grouping dimensions are defined is: [grouping_level_1] / [grouping_level_2] / (...). 
  When the data grouping is not configured (or the grouping has no dimensions),
  the default data group name for a whole table is **all data**. 


- **data_group_hash** - unique hash code (64 bit long integer) calculated by hashing the values of all grouping
  dimension columns *grouping_level_1*, *grouping_level_2*, ..., *grouping_level_9*. 
  The data group hash identifies each grouping. DQOps will calculate the same data group hash for the data quality sensor
  readouts and the data quality check results for every table, as long as all the *grouping_level_X* columns
  contain the same data. When the data groupings from a source table (a table in the staging zone) shares the same
  data group hash as the downstream table, it is possible to perform data comparison across different tables and different
  data sources.


- **time_series_id** - a unique identifier that identifies every time series of check results and sensor readouts
  for each table, column, check and the data group. 


## Integration of partitioned checks with data grouping
[Partition checks](../checks/partition-checks/partition-checks.md) can also analyze partitions with grouping rows
by additional dimensions within each date partition.
Because both partition checks and data grouping by columns depends on adding all columns to the **GROUP BY** clause,
DQOps groups rows both by the date partitioning column and the columns used for data grouping.

The following SQL query shows how DQOps applies grouping by the grouping column and the partition date.


``` { .sql .annotate linenums="12-14 16" }
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN analyzed_table."target_column"
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    analyzed_table."country" AS grouping_level_1,
    CAST(analyzed_table."date_column" AS date) AS time_period,
    CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
GROUP BY grouping_level_1, time_period, time_period_utc
ORDER BY grouping_level_1, time_period, time_period_utc
```

The results of this query collect data quality scores for each day and country separately, and allows accurate
identification of the source of the data quality issue.

| time_period    |     US |     UK |     DE |
|----------------|-------:|-------:|-------:|
| **2023-10-04** |  96.4% |  94.2% |  95.2% |
| **2023-10-05** |  95.3% |  94.7% |  95.6% |
| **2023-10-05** |  93.9% |  96.4% |  96.2% |
| **2023-10-07** |  94.8% |  94.9% |  95.4% |
| **2023-10-08** |  94.7% | **0%** |  95.2% |


## What's next
- Learn how DQOps calculated [data quality KPIs](../definition-of-data-quality-kpis.md)
- Read how the data quality results are [stored as a Hive-compliant local data warehouse](../data-storage-of-data-quality-results.md)
- Check how the **data groups** are aggregated on the [data quality dashboards](../data-quality-dashboards/data-quality-dashboards.md) 
