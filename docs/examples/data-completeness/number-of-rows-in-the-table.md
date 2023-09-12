# Number of rows in the table

Verifies that the number of rows in a table does not exceed the minimum accepted count.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis
by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

In this example, we will verify if the number of rows in a table does not exceed the minimum accepted count.

We want to verify that the number of rows in a table does not exceed the minimum accepted count.

**SOLUTION**

We will verify the data using profiling [row_count](../../checks/table/volume/row-count.md) table check.
Our goal is to verify if the number of rows does not fall below setup thresholds.

In this example, we will set three minimum count thresholds levels for the check:

- warning: 692
- error: 381
- fatal: 150

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the number of rows falls below 692, a warning alert will be triggered.

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

## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/row-count-navigating-to-the-list-of-checks.png)

1. Go to the **Profiling** section. 

2. Select the table or column mentioned in the example description from the tree view on the left.

3. Select the **Profiling Checks** tab.

4. Run the enabled check using the **Run check** button.
    ![Run check](https://dqops.com/docs/images/examples/row-count-run-check.png)

5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
    displays the values obtained by the sensors from the data source. The Check results category shows the severity level
    that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
    that occurred during the check's execution.
 
    ![Check details](https://dqops.com/docs/images/examples/row-count-check-details.png)

6. Review the results which should be similar to the one below.
   
    The actual value of rows in this example is 18155, which is above the minimum threshold level set in the warning (692).
    The check gives a valid result (notice the green square on the left of the name of the check).
    
    ![Row-count check results](https://dqops.com/docs/images/examples/row-count-check-results.png)

7. Synchronize the results with your DQO cloud account using the **Synchronize** button located in the upper right corner of the graphical interface.

    Synchronization ensures that the locally stored results are synced with your DQO Cloud account, allowing you to view them on the dashboards.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 
 
    Below you can see the results displayed on the Issues dashboard showing results by check, number of issues per connection, and number 
    of issues per table.

    ![Row-count results on Issues dashboard](https://dqops.com/docs/images/examples/row-count-results-on-issues-dashboard.png)

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum count thresholds levels for the check:

- warning: 692
- error: 381
- fatal: 150

The highlighted fragments in the YAML file below represent the segment where the profiling `row_count` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="9-20"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    volume:
      profile_row_count:
        comments:
        - date: 2023-05-05T12:19:34.814+00:00
          comment_by: user
          comment: "\"In this example, we verify if the number of rows in a table\
            \ does not exceed the minimum accepted count.\""
        warning:
          min_count: 692
        error:
          min_count: 381
        fatal:
          min_count: 150
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

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
Review the results which should be similar to the one below.
The number of rows is above 692 and the check gives valid result.
```
Check evaluation summary per table:
+---------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|table_row_count|america_health_rankings.ahr|1     |1             |1            |0       |0     |0           |0               |
+---------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection table_row_count (bigquery)
SQL to be executed on the connection:
SELECT
    COUNT(*) AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value of rows in this example is 18155, which is above the minimum
threshold level set in the warning (692).
```
**************************************************
Finished executing a sensor for a check row_count on the table america_health_rankings.ahr using a sensor definition table/volume/row_count, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|18155       |2023-05-05T12:29:22.192Z|2023-05-05T12:29:22.192Z|
+------------+------------------------+------------------------+
**************************************************
```