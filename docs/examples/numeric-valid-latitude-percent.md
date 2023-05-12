# Valid latitude percent

Verifies that the percentage of valid latitude values in a column does not exceed the minimum accepted percentage.

**PROBLEM**

[Austin-311-Public-Data](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he) provides the residents of Austin with a simple single point of contact for every city department.

What started as police non-emergency line for the City of Austin has become a robust Citywide Information Center
where ambassadors are available to answer residents’ concerns 24 hours a day, 7 days a week, and 365 days a year.

The `latitude` column indicates if the latitude values are valid. If the percentage of latitude values exceeds the set thresholds then the file is not ready to be transcribed.

We want to verify the percent of valid latitude values on `latitude` column, which will tell us what percentage of data are
ready to be transcribed.

**SOLUTION**

We will verify the data of `bigquery-public-data.austin_311.311_service_requests` using profiling
[valid_latitude_percent](../checks/column/numeric/valid-latitude-percent.md) column check.
Our goal is to verify if the percentage of valid latitude values on `latitude` column does not exceed 99.0%.

In this example, we will set three maximum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of data that is available for transcription exceed 99.0%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.austin_311.311_service_requests` dataset. Some columns were omitted for clarity.  
The `latitude` column of interest contains valid latitude values.

| latitude        | longitude    | location                    | council_district_code | map_page | map_tile |
|:----------------|:-------------|:----------------------------|:----------------------|:---------|:---------|
|                 |              |                             | 0                     |          |          |
|                 |              |                             | 0                     |          |          |
|                 |              |                             | 0                     |          |          |
| **3.442386682** | -105.9831947 | (3.442386682, -105.9831947) | 0                     |          |          |
|                 |              |                             | 0                     |          |          |
|                 |              |                             | 0                     |          |          |

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

The highlighted fragments in the YAML file below represent the segment where the profiling `valid_latitude_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

```yaml hl_lines="9-27"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    latitude:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
      profiling_checks:
        numeric:
          valid_latitude_percent:
            comments:
              - date: 2023-04-14T09:03:21.495+00:00
                comment_by: user
                comment: In this example, values in "latitude" column are verified whether
                  the percentage of values within the latitude range does not exceed
                  the indicated thresholds.
            warning:
              max_percent: 99.0
            error:
              max_percent: 98.0
            fatal:
              max_percent: 95.0
    longitude:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
    location:
      type_snapshot:
        column_type: STRING
        nullable: true
    council_district_code:
      type_snapshot:
        column_type: INT64
        nullable: true
    map_page:
      type_snapshot:
        column_type: STRING
        nullable: true
    map_tile:
      type_snapshot:
        column_type: STRING
        nullable: true
```

## Running the checks in the example and evaluating the results

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
The percent of valid latitude values in the `latitude` column is above the 95% and the check raised the Fatal error.

```
Check evaluation summary per table:
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection|Table                          |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|austin_311|austin_311.311_service_requests|1     |1             |0            |0       |0     |1           |0               |
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection austin_311 (bigquery)
SQL to be executed on the connection:
SELECT
    100.0 * SUM(
        CASE
            WHEN analyzed_table.`latitude` >= -90.0 AND analyzed_table.`latitude` <= 90.0 THEN 1
            ELSE 0
        END
    )/COUNT(*) AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 99.1864%, what is above maximum
threshold level set in the fatal error (95%).

```
**************************************************
Finished executing a sensor for a check valid_latitude_percent on the table austin_311.311_service_requests using a sensor definition column/numeric/valid_latitude_percent, sensor result count: 1

Results returned by the sensor:
+-----------------+------------------------+------------------------+
|actual_value     |time_period             |time_period_utc         |
+-----------------+------------------------+------------------------+
|99.18649108177759|2023-04-25T12:32:31.147Z|2023-04-25T12:32:31.147Z|
+-----------------+------------------------+------------------------+
**************************************************
```