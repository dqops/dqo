# Not nulls count

Verifies that the number of not null values in a column does not exceed the maximum accepted count.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data. 
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

The `subpopulation` column indicates if the gender data contains not null values. If the number of not null values exceeds the set thresholds then the file is not ready to be transcribed.

We want to verify the number of not null values on `subpopulation` column, which will tell us what number of data are
ready to be transcribed.

**SOLUTION**

We will verify the data of `bigquery-public-data.america_health_rankings.ahr` using profiling
[not_nulls_count](../checks/column/nulls/not-nulls-count.md) column check.
Our goal is to verify if the number of not null values on `subpopulation` column does not exceed 15.

In this example, we will set three maximum number thresholds levels for the check:

- warning: 0
- error: 10
- fatal: 15

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

**VALUE**

If the number of data that is available for transcription exceed 15, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.america_health_rankings.ahr` dataset. Some columns were omitted for clarity.  
The `subpopulation` column of interest contains NULL values.

| edition | report_type             | measure_name | state_name    | subpopulation | value |
|:--------|:------------------------|:-------------|:--------------|:--------------|:------|
| 2021    | 2021 Health Disparities | Able-Bodied  | Hawaii        |               | 87    |
| 2021    | 2021 Health Disparities | Able-Bodied  | Kentucky      |               | 79    |
| 2021    | 2021 Health Disparities | Able-Bodied  | Maryland      |               | 87    |
| 2021    | 2021 Health Disparities | Able-Bodied  | New Jersey    |               | 87    |
| 2021    | 2021 Health Disparities | Able-Bodied  | Utah          |               | 88    |
| 2021    | 2021 Health Disparities | Able-Bodied  | West Virginia |               | 77    |
| 2021    | 2021 Health Disparities | Able-Bodied  | Arkansas      | **Female**    | 78    |
| 2021    | 2021 Health Disparities | Able-Bodied  | California    | **Female**    | 87    |
| 2021    | 2021 Health Disparities | Able-Bodied  | Colorado      | **Female**    | 87    |

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum number thresholds levels for the check:

- warning: 0
- error: 10
- fatal: 15

The highlighted fragments in the YAML file below represent the segment where the profiling `not_nulls_count` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

```yaml hl_lines="24-42"
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
    state_name:
      type_snapshot:
        column_type: STRING
        nullable: true
    subpopulation:
      type_snapshot:
        column_type: STRING
        nullable: true
      profiling_checks:
        nulls:
          not_nulls_count:
            comments:
            - date: 2023-04-25T12:16:36.057+00:00
              comment_by: user
              comment: "\"In this example, values in \"subpopulation\" column are\
                \ verified whether the number of not null values reaches the indicated\
                \ thresholds.\""
            warning:
              max_count: 0
            error:
              max_count: 10
            fatal:
              max_count: 15
```

## Running the checks in the example and evaluating the results

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
The number of not null values in the `subpopulation` column is above 15 and the check raised the Fatal error.

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
    COUNT(analyzed_table.`subpopulation`)
    AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 16647, what is above maximum 
threshold level set in the fatal error (15).

```
**************************************************
Finished executing a sensor for a check not_nulls_count on the table america_health_rankings.ahr using a sensor definition column/nulls/not_null_count, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|16647       |2023-04-25T12:10:34.061Z|2023-04-25T12:10:34.061Z|
+------------+------------------------+------------------------+
**************************************************
```