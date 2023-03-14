# Partition checks



Describe what are time series. 
How to set timestamp and ingestion columns
Use fragments from the description below



# Time series
Time series is a collection of data points indexed in a time order. Here we describe how to use `time_series` in DQO.

## Mode
There are two time series modes:

- `current_time` - the time series is formed from the time of the sensor execution,
  the current result is assigned to the current time, all time series rules (average, standard deviations) need a
  database to load previous results.

- `timestamp_column` - the data in the table is grouped by the timestamp column
  that is truncated to the beginning of the period (day),
  this mode is useful to analyze fact tables that are assigned to an event time, and we can measure each period of
  data separately.

## Time gradient
If a time series is a collection of points in time, then a time gradient defines a distance between those points.
There are six `time_gradient` options: year, quarter, month, week, day, hour.
The current time (for the `current_time` mode) or the value of
the `timestamp_column` is truncated to the beginning of the time gradient period (day, etc.). The default time gradient
is 'daily'.

## Timestamp column
A `timestamp_column` is a reference to the name of the timestamp column when the mode is 'timestamp_column'.
It must be a column name
that stores a date or datetime. It could be a name of the date column for date partitioned data or a modification or
insert timestamp column (modified_at, inserted_at, etc.). Completeness sensors need the timestamp column to detect
missing time periods.

## Time window periods
The time window for the earliest captured data. When the 'mode' is 'timestamp_column', a WHERE
condition is added to the
query to retrieve only the given number of time periods. When the time_gradient is daily, the WHERE clause would be
equivalent to: WHERE {timestamp_column} >= now() - time_window_periods * '1 day'. As a result, only the last
time_window_periods days (or any other periods of selected time gradients) will be analyzed. This parameter has no
meaning when the mode is current_time.

## Incremental time window periods
Similar to the time_window_periods, but used when the --incremental parameter is used to capture sensor values.
Usually when the database is monitored frequently, it could be 2-3 time periods.

## Excluded recent periods
Number of most recent time gradient periods that should be excluded. This parameter is used when the mode is
timestamp_column, and we cannot analyze the data for the current time period (that contains 'now', which is today
for a daily time gradient) because not all data have been received and the data quality score like the row count
may still change. Set the value of this parameter to '1' to exclude today's result for a daily gradient. Use '2'
to wait one full day until the data is analyzed.

## How to configure time series
