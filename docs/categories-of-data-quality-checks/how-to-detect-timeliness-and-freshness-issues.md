---
title: How to measure data timeliness, freshness and staleness metrics
---
# How to measure data timeliness, freshness and staleness metrics
Read this guide to learn how to measure data timeliness metrics, such as freshness (the most recent data) or staleness (when the data was loaded).

The timeliness data quality checks are configured in the `timeliness` category in DQOps.

## Data timeliness
Timeliness is a measure that tells us the lag between something happened and when it was recorded. 
When we apply timeliness to data, we come up with the concept of data timeliness. 
Data timeliness uses the most recent timestamps in the dataset to calculate the time lag.

The formula that calculates data timeliness compares two timestamps and measures the time difference between them: the data lag.

- The first value is the most recent timestamp in the dataset.
  It is the point in time when **SOMETHING happened**. It can be a business action, 
  such as the timestamp of the most recent transaction
  in an eCommerce platform, the most recent impression of an advertisement,
  the timestamp of a log entry, or the timestamp of the last shipment.

- The second value is the system's current time. It is the timestamp when we recorded the state of the data in the dataset.

The only challenge here is to identify the right timestamp column in the dataset that can tell us about the currency of the data.

### Data freshness
The most relevant timeliness metric is **data freshness**. 
It is the delay since the last business action recorded in the database.

The most common business actions are:

- The transaction timestamp in eCommerce.

- The timestamp of registering the last user in a user's database.

- The impression timestamp of digital ads.

- The invoice issue timestamp.

- The timestamp of the log entry if we are aggregating logs.

The data freshness measures the time lag since one of these business actions
and the timestamp when a data observability platform such as DQOps analyzed
the table and found the most recent business action.

The following table shows a list of potential orders in an eCommerce platform. 

| order_id | customer_id | created_at                |                                |
|----------|-------------|---------------------------|--------------------------------|
| 100      | 5           | _2023-11-12 09:15:00_     |                                |
| 101      | 7           | _2023-11-13 08:40:00_     |                                |
| 102      | 4           | **_2023-11-14 10:25:00_** | <- the most recent transaction |

Let's assume the current system time when the data freshness calculation was performed was
*2023-11-16 11:45:00* (*November 16th, 2023 11:45 AM*).
We want to calculate the lag (time difference) between the most recent business action and
the timestamp when we tested data freshness.

| order_id | customer_id | created_at                | Data freshness formula: `NOW() - created_at`      | Data lag          |
|----------|-------------|---------------------------|---------------------------------------------------|-------------------|
| 100      | 5           | _2023-11-12 09:15:00_     | _"2023-11-16 11:45:00"_ - _"2023-11-12 09:15:00"_ | 4 days 2h 30m     |
| 101      | 7           | _2023-11-13 08:40:00_     | _"2023-11-16 11:45:00"_ - _"2023-11-13 08:40:00"_ | 3 days 3h 5m      |
| 102      | 4           | **_2023-11-14 10:25:00_** | _"2023-11-16 11:45:00"_ - _"2023-11-14 10:25:00"_ | **2 days 1h 20m** |

We don't need to calculate the data lag for every record in the table. 
We only need to know the timestamp of the most recent business transaction.
DQOps finds the most recent timestamp using a MAX function, which is an aggregate function in SQL.

| order_id | customer_id | created_at                | Data freshness formula: `NOW() - MAX(created_at)` | Data lag          |
|----------|-------------|---------------------------|---------------------------------------------------|-------------------|
| 102      | 4           | **_2023-11-14 10:25:00_** | _"2023-11-16 11:45:00"_ - _"2023-11-14 10:25:00"_ | **2 days 1h 20m** |


The data freshness of this table is **2 days 1 hour 20 min**. If we measure the lag numerically, that would be *2.05555* days.

### Data staleness
Because data timeliness measures the currency of a copy of data stored in a database,
we must distinguish the timestamps of business actions and the time when the data pipeline (ETL process)
loaded the data into the data warehouse or data lake.

A well-designed data pipeline augments the target table by adding additional columns 
that identify the data source and the time of inserting the data. 
If the data pipeline adds additional metadata columns such as *inserted_at* or *loaded_at*, 
they will be populated by the data pipeline with the timestamp when the data was stored in the database.
These columns become our next reference point in time. They store the time when the data was most recently loaded into the database.
We use the name **data staleness** to refer to the time lag since the last refresh of the data warehouse.

Let's extend the previous example of the orders table. The table will have an additional column named *loaded_at*, 
which holds the timestamp when the data was loaded into the database.
If the data pipeline loaded the data incrementally, the *loaded_at* timestamps would differ for each row.
But if our data pipeline performs a full table refresh every time, the timestamp of loading rows would change.

| order_id | customer_id | created_at                | **loaded_at**             |
|----------|-------------|---------------------------|---------------------------|
| 100      | 5           | _2023-11-12 09:15:00_     | _2023-11-12 11:00:00_     |
| 101      | 7           | _2023-11-13 08:40:00_     | _2023-11-13 11:00:00_     |
| 102      | 4           | **_2023-11-14 10:25:00_** | **_2023-11-14 11:00:00_** |

The formula for calculating the data staleness compares the time between the most recent *loaded_at* timestamp 
and the point in time of checking the data staleness.

| order_id | customer_id | created_at                | **loaded_at**               | Data staleness formula: `NOW() - loaded_at`       | Data lag          |
|----------|-------------|---------------------------|-----------------------------|---------------------------------------------------|-------------------|
| 100      | 5           | _2023-11-12 09:15:00_     | _2023-11-12 11:00:00_       | _"2023-11-16 11:45:00"_ - _"2023-11-12 11:00:00"_ | 4 days 0h 45m     |
| 101      | 7           | _2023-11-13 08:40:00_     | _2023-11-13 11:00:00_       | _"2023-11-16 11:45:00"_ - _"2023-11-13 11:00:00"_ | 3 days 0h 45m     |
| 102      | 4           | **_2023-11-14 10:25:00_** | **_2023-11-14 11:00:00_**   | _"2023-11-16 11:45:00"_ - _"2023-11-14 11:00:00"_ | **2 days 0h 45m** |

We also only need one record, the most recently loaded one, to calculate the data staleness. 
It is enough that a data observability platform 
such as DQOps finds the most recent value using a MAX aggregate function.

| order_id | customer_id | created_at                | **loaded_at**               | Data staleness formula: `NOW() - MAX(loaded_at)`  | Data lag          |
|----------|-------------|---------------------------|-----------------------------|---------------------------------------------------|-------------------|
| 102      | 4           | **_2023-11-14 10:25:00_** | **_2023-11-14 11:00:00_**   | _"2023-11-16 11:45:00"_ - _"2023-11-14 11:00:00"_ | **2 days 0h 45m** |

The data freshness of this table is **2 days 0 hours 45 min**. If we measure the lag numerically, that would be *2.03125* days.

### Ingestion delay
The time between when the business action happened and the time the record was loaded is called the **ingestion delay**. 
It is the time duration when the record was being processed by the data pipeline or the delay between each run of the pipeline.

DQOps finds the two most recent timestamps and compares them.

- The most recent timestamp of a business transaction.

- The most recent timestamp of loading the record.

| order_id | customer_id | created_at                | **loaded_at**               | Data staleness formula: `MAX(loaded_at) - MAX(created_at)` | Data lag          |
|----------|-------------|---------------------------|-----------------------------|------------------------------------------------------------|-------------------|
| 102      | 4           | **_2023-11-14 10:25:00_** | **_2023-11-14 11:00:00_**   | _"2023-11-14 11:00:00"_ - _"2023-11-14 10:25:00"_          | **0 days 1h 20m** |

The ingestion delay for the dataset shown above is 1 hour 20 minutes.

When applied to date-partitioned data, the ingestion delay becomes a more critical timeliness measure. 
Tables that are physically partitioned by date can benefit from more efficient incremental data load by refreshing whole partitions.

The [partition checks in DQOps](../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)
calculate the ingestion delay for each daily or monthly partition, allowing for tracking when partitions were loaded or reloaded.

### Freshness, staleness and ingestion delay compared
All timeliness metrics supported by DQOps are compared on the following time diagram.

![Data timeliness, freshness, staleness and ingestion delay metrics explained](https://dqops.com/docs/images/concepts/DQOps_timeliness_checks_min.png){ loading=lazy }

## Configure DQOps for timeliness checks
Before activating any timeliness data quality checks in DQOps, you must enter additional configuration about the monitored table.
DQOps must know which columns in the table store relevant timestamp values. DQOps stores the configuration
of these columns for each table in the table's YAML file. The section is named `timestamp_columns`.

You have to configure one or both of the column names:

- The `event_timestamp_column` parameter stores the name of the column containing the most recent business action.

- The `ingestion_timestamp_column` parameter stores the name of the column containing 
  the timestamp when the row was loaded into the target table.

### Configuring timestamp columns from UI
To configure the event and/or ingestion timestamp columns:

1. Go to the **Data Sources** section.

2. Select the table of interest from the tree view.

3. Select the **Data and Time Columns** tab and select a column from the drop-down list in the "Event timestamp column name
   for timeliness checks" and/or "Ingestion timestamp column name for timeliness checks" input fields.

4. Click the Save button in the upper right corner.

![Configure event and ingestion timestamp columns](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/event-and-ingestion-columns-configuration-for-timeliness-checks.png){ loading=lazy }


### Configuring timestamp columns in YAML
The event and ingestion timestamps for timeliness checks can be also configured by adding
the appropriate parameters to the table's YAML configuration file.

Below is an example of the YAML file showing a sample configuration with set event timestamps (the business action) column `event_timestamp_column`,
ingestion timestamps (the *loaded_at* or similar) column `ingestion_timestamp_column`.

``` { .yaml linenums="1" hl_lines="5-6" }
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: created_at
    ingestion_timestamp_column: loaded_at
```


## Enabling table freshness monitoring
The [*data_freshness*](../checks/table/timeliness/data-freshness.md) check monitors the freshness of the table.
The best way to track data freshness is by using a [daily monitoring](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)
variant of this check, named [*daily_data_freshness*](../checks/table/timeliness/data-freshness.md#daily-data-freshness).

The [*data_freshness*](../checks/table/timeliness/data-freshness.md) check 
captures the delay since the timestamp of the most recent business action that we call the *event timestamp* in DQOps.

The data quality rules for this check take one parameter **max_days**, which configures the maximum accepted lag in days. 
DQOps supports setting this parameter at multiple severity levels.
We advise configuring the check for warning and error severity levels.

- When the lag exceeds the maximum number of days for the warning threshold, 
  DQOps will raise a data quality issue with a [warning severity level](../dqo-concepts/definition-of-data-quality-checks/index.md#issue-severity-levels).
  The current lag should be reviewed, and the configuration of the check should be adapted to avoid too many false-positive alerts.

- When the lag exceeds the maximum number of days for the error threshold,
  DQOps will raise a data quality issue with an [error severity level](../dqo-concepts/definition-of-data-quality-checks/index.md#issue-severity-levels).
  These issues should be reviewed and reported to the data engineering team or the data owner.

### Configuring table freshness in UI
The data timeliness checks are configured on the table level, as shown in the following screenshot.

![Data freshness data quality check configured in DQOps](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/table-freshness-data-quality-check-in-dqops-min.png){ loading=lazy }

### Configuring table freshness in YAML
The configuration of [*data_freshness*](../checks/table/timeliness/data-freshness.md) is straightforward in YAML.

A warning is raised when the table freshness lag exceeds 2 days,
or an error is raised when the table freshness lag exceeds 3 days.

``` { .yaml linenums="1" hl_lines="12 14" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: created_date
  monitoring_checks:
    daily:
      timeliness:
        daily_data_freshness:
          warning:
            max_days: 2.0
          error:
            max_days: 3.0
```

## Tracking data timeliness on dashboards
DQOps has several built-in data quality dashboards dedicated to data timeliness.
They allow to track and review all types of timeliness issues from multiple angles.

- The **Current timeliness issues** shows the most recent data timeliness issues. 
  If the missing data is received and the current data timeliness is below the allowed lag, 
  DQOps does not show the older issues on the dashboard.

- The **History of timeliness issues** dashboard also shows older timeliness issues, 
  allowing a review of the reliability of the timeliness dimension for tables.

- The **Table freshness - tables with most current data** shows the tables containing the most recent business actions.

- The **Table freshness - tables with the oldest data** shows tables that contain outdated data. 
  These tables do not receive new records.

- The **Table staleness - tables most recently loaded** shows the tables most recently loaded, 
  identified by the data staleness check.

- The **Table staleness - tables no longer loaded** shows tables not refreshed for a long time, sorted from the oldest. 
  These tables should probably be retired, or a data pipeline stopped working and is not feeding new data.

- The **History of table freshness** dashboard shows the data freshness lags for each day in a pivot table. 
  This dashboard helps to understand when the tables most likely receive new data.

- The **Minimum, maximum, average delay** dashboard shows the statistics about the data processing lag.

### Data freshness dashboard
The **Table freshness - tables with most current data** dashboard shows the tables with the most recent data,
but also shows data freshness issues for outdated tables.

![Data freshness data quality dashboard in DQOps showing tables with most recent data](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/data-freshness-dashboard-most-recent-tables-in-dqops-min.png){ loading=lazy }

### History of timeliness issues
The **History of timeliness issues** dashboard shows an aggregated list of recent data timeliness issues.

![Data quality dashboard showing a history of recent data timelines issues in DQOps](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/history-of-recent-data-timeliness-issues-dashboard-in-dqops-min.png){ loading=lazy }


## List of timeliness checks at a table level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*data_freshness*](../checks/table/timeliness/data-freshness.md)|Timeliness|A table-level check that calculates the time difference between the most recent row in the table and the current time. The timestamp column that is used for comparison is defined as the timestamp_columns.event_timestamp_column on the table configuration. This check is also known as &quot;Data Freshness&quot;.|:material-check-bold:|
|[*data_staleness*](../checks/table/timeliness/data-staleness.md)|Timeliness|A table-level check that calculates the time difference between the last timestamp when any data was loaded into a table and the current time. This check can only be use when a data pipeline, ETL process, or trigger in the data warehouse is filling an extra column with the timestamp when the data loading job was loaded. The ingestion column used for comparison is defined as the timestamp_columns.ingestion_timestamp_column on the table configuration. This check is also known as &quot;Data Staleness&quot;.| |
|[*data_ingestion_delay*](../checks/table/timeliness/data-ingestion-delay.md)|Timeliness|A table-level check that calculates the time difference between the most recent row in the table and the most recent timestamp when the last row was loaded into the data warehouse or data lake. To identify the most recent row, the check finds the maximum value of the timestamp column that should contain the last modification timestamp from the source. The timestamp when the row was loaded is identified by the most recent (maximum) value a timestamp column that was filled by the data pipeline, for example: &quot;loaded_at&quot;, &quot;updated_at&quot;, etc. This check requires that the data pipeline is filling an extra column with the timestamp when the data loading job has been executed. The names of both columns used for comparison should be specified in the &quot;timestamp_columns&quot; configuration entry on the table.| |
|[*reload_lag*](../checks/table/timeliness/reload-lag.md)|Timeliness|A table-level check that calculates the maximum difference in days between ingestion timestamp and event timestamp values on any row. This check should be executed only as a partitioned check because this check finds the longest delay between the time that the row was created in the data source and the timestamp when the row was loaded into its daily or monthly partition. This check detects that a daily or monthly partition was reloaded, setting also the most recent timestamps in the created_at, loaded_at, inserted_at or other similar columns filled by the data pipeline or an ETL process during data loading.|:material-check-bold:|


**Reference and samples**

The full list of all data quality checks in this category is located in the [table/timeliness](../checks/table/timeliness/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
