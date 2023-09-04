# Table availability

Verifies the availability of a table in the database using a simple row count.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis
by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

Here is a table with some sample customer data. When working with a large dataset, it's common to have multiple tables within it. 

However, it is important to ensure that these tables are available and actually exist. This can be achieved using a table availability check, which helps ensure that the necessary data is readily available for analysis.

Typical table availability issues are:

- the table does not exist because it has been deleted,
- the table is corrupted and cannot be queried,
- the database is down or is unreachable,
- database credentials are incorrect,
- access rights to the table have changed.

In this example, we will verify table availability in database using a simple row count.

We want to verify that a query can be executed on a table and that the server does not return errors, that the table exists, and that there are accesses to it.

**SOLUTION**

We will verify the data using profiling [table_availability](../../checks/table/availability/table-availability.md) check.
Our goal is to verify that table availability check failures do not exceed the set thresholds.
In this check, you can only get two values in the result 1 or 0. If you get a value of 1, it means the table is fully available and exists, so the result is valid. 

However, if you receive a value of 0, then there is a problem, and you need to run this check again after fixing the issue with the table. 
The number of failed attempts are failures, which we set in thresholds.

In this example, we will set three maximum failure threshold levels for the check:

- warning: 1
- error: 5
- fatal: 10

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the number of failures will exceed 1, a warning alert will be triggered.

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

- warning: 1
- error: 5
- fatal: 10

The highlighted fragments in the YAML file below represent the segment where the profiling `table_availability` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="9-20"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    availability:
      profile_table_availability:
        comments:
        - date: 2023-05-09T11:05:06.960+00:00
          comment_by: user
          comment: "\"In this example, we verify availability on table in database\
            \ using simple row count.\""
        warning:
          max_failures: 1
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
## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-table-availability-check.png)

1. Go to **Profiling** section.

2. Select the table or column mentioned in the example description from the tree view on the left.

3. Select **Profiling Checks** tab.

4. Run the enabled check using the **Run check** button.
   ![Run check](https://dqops.com/docs/images/examples/table-availability-run-check.png)

5. Review the results by opening the **Check details** button.
   ![Check details](https://dqops.com/docs/images/examples/table-availability-check-details.png)

6. You should see the results as the one below.
   The actual value in this example is 1.
   The check gives a valid result (notice the green square on the left of the name of the check).

   ![Table-availability check results](https://dqops.com/docs/images/examples/table-availability-check-results.png)

7. After executing the checks, synchronize the results with your DQO cloud account sing the **Synchronize** button
   located in the upper right corner of the graphical interface.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. Below you can see
   the results displayed on the Current table status by dimension dashboard showing results by connection, schema, dimension and data group.

   ![Table-availability check results on Current table status by dimension dashboard](https://dqops.com/docs/images/examples/table-availability-check-results-on-current-table-status-by-dimension-dashboard.png)

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
The number of failures is 0 and the check gives valid result.
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
You can also see the results returned by the sensor. The actual value of the check is 1.0, which means that the table exists and is accessible so the result is valid.
```
**************************************************
Finished executing a sensor for a check table_availability on the table america_health_rankings.ahr using a sensor definition table/availability/table_availability, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|1.0         |2023-05-09T11:05:06.211Z|2023-05-09T11:05:06.211Z|
+------------+------------------------+------------------------+
**************************************************
```