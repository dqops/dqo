# Percentage of duplicates

Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.

**PROBLEM**

[Austin-311-Public-Data](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he) provides the residents of Austin with a simple single point of contact for every city department.

What started as police non-emergency line for the City of Austin has become a robust Citywide Information Center
where ambassadors are available to answer residents’ concerns 24 hours a day, 7 days a week, and 365 days a year.

The `unique_key` column contains unique key data. We want to verify the percent of duplicated values on `unique_key` column.

**SOLUTION**

We will verify the data of `bigquery-public-data.austin_311.311_service_requests` using profiling
[duplicate_percent](../checks/column/uniqueness/duplicate-percent.md) column check.
Our goal is to verify if the percentage of duplicated values in `unique_key` column does not exceed set thresholds.

In this example, we will set three maximum percentage thresholds levels for the check:

- warning: 1.0%
- error: 2.0%
- fatal: 5.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of duplicated values on `unqiue_key` column exceed 1.0%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.austin_311.311_service_requests` dataset. Some columns were omitted for clarity.  
The `unique_key` column of interest contains unique values.

| unique_key      | complaint_description                               | source | source | status_change_date  | created_date        |
|:----------------|:----------------------------------------------------|:-------|:-------|:--------------------|:--------------------|
| **19-00454912** | Parking Machine Issue                               | Phone  | Closed | 12/3/2019 6:54:59   | 11/30/2019 11:33:22 |
| **20-00288726** | Community Connections - Coronavirus                 | Phone  | Closed | 7/16/2020 11:26:40  | 7/16/2020 10:21:17  |
| **19-00458482** | Parking Machine Issue                               | Phone  | Closed | 12/5/2019 6:41:42   | 12/3/2019 12:57:47  |
| **17-00207653** | Street Light Issue- Address                         | Web    | Closed | 7/20/2017 12:33:20  | 7/20/2017 11:19:51  |
| **18-00118937** | Parking Machine Issue                               | Phone  | Closed | 4/25/2018 13:30:43  | 4/24/2018 8:30:23   |
| **20-00525858** | Community Connections - Coronavirus                 | Phone  | Closed | 12/29/2020 14:13:49 | 12/28/2020 17:26:17 |
| **14-00150037** | Street Light Issue- Multiple poles/multiple streets | Phone  | Closed | 7/21/2014 14:52:20  | 7/21/2014 14:36:47  |
| **14-00181676** | Parking Machine Issue                               | Phone  | Closed | 8/28/2014 10:40:32  | 8/27/2014 11:32:21  |

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum percentage thresholds levels for the check:

- warning: 1.0%
- error: 2.0%
- fatal: 5.0%

The highlighted fragments in the YAML file below represent the segment where the profiling `duplicate_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

```yaml hl_lines="9-27"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    unique_key:
      type_snapshot:
        column_type: STRING
        nullable: true
      profiling_checks:
        uniqueness:
          duplicate_percent:
            comments:
              - date: 2023-04-14T09:13:20.243+00:00
                comment_by: user
                comment: In this example, values in "unique_key" column are verified  whether
                  the percentage of duplicated values does not exceed the indicated
                  thresholds.
            warning:
              max_percent: 1.0
            error:
              max_percent: 2.0
            fatal:
              max_percent: 5.0
    complaint_description:
      type_snapshot:
        column_type: STRING
        nullable: true
    source:
      type_snapshot:
        column_type: STRING
        nullable: true
    status:
      type_snapshot:
        column_type: STRING
        nullable: true
    status_change_date:
      type_snapshot:
        column_type: TIMESTAMP
        nullable: true
    created_date:
      type_snapshot:
        column_type: TIMESTAMP
        nullable: true
```
## Running the checks in the example and evaluating the results

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
The percent of the duplicate values in the `unique_key` column is below 5.0% and the check gives valid result.
```
Check evaluation summary per table:
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection|Table                          |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|austin_311|austin_311.311_service_requests|1     |1             |1            |0       |0     |0           |0               |
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
    CASE
        WHEN COUNT(analyzed_table.`unique_key`) = 0 THEN 100.0
        ELSE 100.0 * (
            COUNT(analyzed_table.`unique_key`) - COUNT(DISTINCT analyzed_table.`unique_key`)
        ) / COUNT(analyzed_table.`unique_key`)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value in this example is 0.0%, which is below the maximum
threshold level set in the warning (5.0%).
```
**************************************************
Finished executing a sensor for a check duplicate_percent on the table austin_311.311_service_requests using a sensor definition column/uniqueness/duplicate_percent, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|0.0         |2023-04-25T14:37:23.670Z|2023-04-25T14:37:23.670Z|
+------------+------------------------+------------------------+
**************************************************
```