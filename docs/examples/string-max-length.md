# String max length

The check counts percentage of those strings with length above the one provided by the user in a column.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

The `string_max_length` column indicates if the length of values does not exceed the indicated by the user thresholds. If length of values exceeds the set thresholds then the file is not ready to be transcribed.

We want to verify the number of valid length values on `string_max_length` column, which will tell us what number of data are
ready to be transcribed.

**SOLUTION**

We will verify the data of `bigquery-public-data.america_health_rankings.ahr` using profiling
[string_max_length](../checks/column/strings/string-length-above-max-length-percent.md) column check.
Our goal is to verify if the number of valid length values on `string_max_length` column does not exceed 5.0.

In this example, we will set three maximum number thresholds levels for the check:

- warning: 5.0
- error: 6.0
- fatal: 8.0

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

**VALUE**

If the number of data that is available for transcription exceed 5.0, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.america_health_rankings.ahr` dataset. Some columns were omitted for clarity.  
The `measure_name` column of interest contains values that shouldn't exceed the length indicated thresholds.

| edition | report_type             | measure_name    | state_name    | subpopulation |
|:--------|:------------------------|:----------------|:--------------|:--------------|
| 2021    | 2021 Health Disparities | **Able-Bodied** | California    |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Colorado      |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Hawaii        |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Kentucky      |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Maryland      |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | New Jersey    |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Utah          |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | West Virginia |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Arkansas      | Female        |

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum number thresholds levels for the check:

- warning: 5.0
- error: 6.0
- fatal: 8.0

The highlighted fragments in the YAML file below represent the segment where the profiling `string_max_length` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

```yaml hl_lines="17-34"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    edition:
      type_snapshot:
        column_type: INT64
        nullable: true
    report_type:
      type_snapshot:
        column_type: STRING
        nullable: true
    measure_name:
      type_snapshot:
        column_type: STRING
        nullable: true
      profiling_checks:
        strings:
          string_max_length:
            comments:
              - date: 2023-04-14T09:11:51.993+00:00
                comment_by: user
                comment: In this example, values in "measure_name" column are verified
                  whether the length of values does not exceed the indicated thresholds.
            warning:
              max_value: 5.0
            error:
              max_value: 6.0
            fatal:
              max_value: 8.0
    state_name:
      type_snapshot:
        column_type: STRING
        nullable: true
    subpopulation:
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
The number of the valid string length in the `measure_name` column is above 8.0 and the check raised the Fatal error.
```
Check evaluation summary per table:
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection             |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|america_health_rankings|america_health_rankings.ahr|1     |1             |0            |0       |0     |1           |0               |
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection america_health_rankings (bigquery)
SQL to be executed on the connection:
SELECT
    MAX(
        LENGTH(analyzed_table.`measure_name`)
    ) AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value in this example is 31, what is above maximum
threshold level set in the Fatal error (8.0).

```
**************************************************
Finished executing a sensor for a check string_max_length on the table america_health_rankings.ahr using a sensor definition column/strings/string_max_length, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|31          |2023-04-25T14:23:22.355Z|2023-04-25T14:23:22.355Z|
+------------+------------------------+------------------------+
**************************************************
```