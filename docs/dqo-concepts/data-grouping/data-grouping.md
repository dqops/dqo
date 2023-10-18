# Data grouping

When a new data source's metadata is imported into DQO and the first data quality checks are enabled, the checks will
analyze the whole table. The data quality check will capture one metric (using a data quality sensor, that is an SQL query).
Then, that single data quality sensor readout (the captured metric) is evaluated through the rule engine, which will
result in creating a data quality rule result that is valid (no data quality issue detected) or is a data quality issue, 
ranked at three severity levels: warning, error or fatal.

A single data quality rule result for a whole table is not always the best solution. The data in the table is often coming
from different data sources, different vendors or loaded by different data pipelines. When the data in the table comes from
different sources, the source is usually identified by a value in dedicated discriminator columns. The typical discriminator
columns are: country, business unit, cost center, vendor, data provider, supplier or subsidiary.

In order to analyze each group of rows, DQO runs SQL queries on the monitored data sources using a GROUP BY clause in the query.
A data quality check that counts the percentage of null values in a column would return multiple values. 
Each row returned from the query is evaluated through the rule engine separately.

Here is an example of an SQL query that DQO will generate to count the percentage of null values in an analyzed column
for each country. The following example assumes that there is a _country_ column in the monitored table and we want to
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
    analyzed_table."country" AS grouping_level_1,
    LOCALTIMESTAMP AS time_period,
    CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
FROM "your_postgresql_database"."public"."analyzed_table" AS analyzed_table
GROUP BY grouping_level_1, time_period, time_period_utc
ORDER BY grouping_level_1, time_period, time_period_utc
```

The grouping could be multi dimensional. For example, an international company could identify the data from different
affiliates (countries), subdivided into business units in each affiliate.  
DQO supports setting of up to 9 different data grouping dimensions (levels).

## Grouping similar tables
DQO supports another scenario, when the data warehouse has similar tables (even with the same schema), that contain
data from different data sources. The data quality sensor readouts (measures) and the data quality check results (measures
evaluated by the rule engine) could be grouped, allowing to measure the data quality KPIs for all tables coming from the same
source, for example from the same country.

## Operating on multiple groups of data
DQO treats all data quality sensor readouts (metrics) and all data quality check results (rule evaluation results)
as time series. Suppose that a table contains data from two countries and each group of data is identified by
the value of the _country_ column value. The user enabled a **daily_row_count** check on the table.
DQO will track the row count time series for both countries, allowing to detect anomalies or missing data
for only one country. Detecting data quality issues that are related to a single data source is crucial for the root
cause analysis and allows to contact the right person, 
responsible for providing the data from that data stream (the country in this example).

When a data quality issue is identified (a data quality check's rule raised an alert), DQO could be configured
to create either one data quality incident for the whole table (without identification of the data group) or for each
group of data (the country in this example) as a separate data quality incident.

The data quality dashboards in DQO have a configuration parameter to select the data stream for which we want to
find the most recent data quality issues or calculate the data quality KPI.
The [data quality KPI](../data-quality-kpis/data-quality-kpis.md) scores that is calculated for each data source (data grouping)
simplify the root cause analysis by linking the data quality incident to a data source, a data group,
an external data supplier, a data provider, or simply a separate data pipeline that has loaded invalid data.

## Identifying data sources

There are two ways to identify the data source in DQO:

- **Separate tables for each data source**. This is a simple case that can be solved by adding a tag with the name 
of the data source. A [data quality KPI](../data-quality-kpis/data-quality-kpis.md) can be calculated from multiple 
tables at once.

Here is an example of data stream configuration in the YAML file using a tag named 'UK':

``` yaml hl_lines="7-11"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  groupings:
    by_country:
      level_1:
        source: tag
        tag: UK
```

- **Multiple data sources aggregated into a single table**. Data from multiple sources can be aggregated in a single 
table. If there is a column that identifies the data source, it can be used to assign the generated alerts and sensor 
readouts to the correct data group. 

Here is another example of the YAML file that uses a 'country' column to identify separate data groups by country.

``` yaml  hl_lines="7-11"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  groupings:
    by_country:
      level_1:
        source: column_value
        column: country
```

## Configuring data grouping 

In DQO you can set up data grouping globally for all tables in the connection or individually for each table.
You can set up data grouping in the **Data Sources** section by clicking at the connection or individual table in the tree and 
selecting **Data Grouping** tab.
It is also important to mention that the configuration of data grouping on the connection level
is only a template of the default grouping configuration that is copied to tables that are imported.
When a table was already imported into DQO, changing the configuration on the connection level has no effect.

For more detailed information on setting up a data grouping, go to [Working with DQO section](../../working-with-dqo/set-up-data-grouping/set-up-data-grouping.md).

## How DQO stores data grouping results
DQO stores the data in Parquet files. The format of parquet files for the sensor readout ([.data/sensor_readouts/](../../reference/parquetfiles/sensor_readouts.md))
and the check results ([.data/check_results/](../../reference/parquetfiles/check_results.md)) are documented in the reference.

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
  The data group hash identifies each grouping. DQO will calculate the same data group hash for the data quality sensor
  readouts and the data quality check results for every table, as long as all the *grouping_level_X* columns
  contain the same data. When the data groupings from a source table (a table in the staging zone) shares the same
  data group hash as the downstream table, it is possible to perform data comparison across different tables and different
  data sources.


- **time_series_id** - a unique identifier that identifies every time series of check results and sensor readouts
  for each table, column, check and the data group.  