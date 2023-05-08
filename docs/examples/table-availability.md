# Table availability

Verifies availability on table in database using simple row count.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis
by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

Here is a table with some sample customer data. In this example, we will verify table availability in database using simple row count.

We want to verify that a query can be executed on a table and that the server does not return errors, that the table exists, and that there are accesses to it.

**SOLUTION**

We will verify the data using profiling [table_availability](../checks/table/availability/table-availability.md) table check.
Our goal is to verify if the failures of table availability check does not exceed the setup thresholds.

In this example, we will set three maximum failures thresholds levels for the check:

- warning: 2
- error: 5
- fatal: 10

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of data that is available for transcription exceed 1, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.america_health_rankings.ahr` dataset. Some columns were omitted for clarity.

| edition | report_type             | measure_name | state_name    | subpopulation | value |
|:--------|:------------------------|:-------------|:--------------|:--------------|:------|
| 2021    | 2021 Health Disparities | Able-Bodied  | Hawaii        |               | 87    |
| 2021    | 2021 Health Disparities | Able-Bodied  | Kentucky      |               | 79    |
| 2021    | 2021 Health Disparities | Able-Bodied  | Maryland      |               | 87    |
| 2021    | 2021 Health Disparities | Able-Bodied  | New Jersey    |               | 87    |
| 2021    | 2021 Health Disparities | Able-Bodied  | Utah          |               | 88    |
| 2021    | 2021 Health Disparities | Able-Bodied  | West Virginia |               | 77    |
| 2021    | 2021 Health Disparities | Able-Bodied  | Arkansas      | Female        | 78    |
| 2021    | 2021 Health Disparities | Able-Bodied  | California    | Female        | 87    |
| 2021    | 2021 Health Disparities | Able-Bodied  | Colorado      | Female        | 87    |

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum failures thresholds levels for the check:

- warning: 2
- error: 5
- fatal: 10

The highlighted fragments in the YAML file below represent the segment where the profiling `table_availability` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

```yaml hl_lines="7-20"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    availability:
      table_availability:
        comments:
        - date: 2023-05-05T10:53:02.960+00:00
          comment_by: user
          comment: "\"In this example, we verify availability on table in database\
            \ using simple row count.\""
        warning:
          max_failures: 2
        error:
          max_failures: 5
        fatal:
          max_failures: 10
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
```
## Running the checks in the example and evaluating the results

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
The number of failures is below 2 and the check gives valid result.
```
Check evaluation summary per table:
+------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection        |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|table_availability|america_health_rankings.ahr|1     |1             |1            |0       |0     |0           |0               |
+------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection table_availability (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
       WHEN COUNT(*) > 0 THEN COUNT(*)
       ELSE 1.0
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM
    (
        SELECT
            *,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table

        LIMIT 1
    ) AS tab_scan
GROUP BY time_period
ORDER BY time_period
**************************************************
```
You can also see the results returned by the sensor. The actual value of failures in this example is 1.0, which is below the maximum
threshold level set in the warning (2).
```
**************************************************
Finished executing a sensor for a check table_availability on the table america_health_rankings.ahr using a sensor definition table/availability/table_availability, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|1.0         |2023-05-05T10:53:41.233Z|2023-05-05T10:53:41.233Z|
+------------+------------------------+------------------------+
**************************************************
```