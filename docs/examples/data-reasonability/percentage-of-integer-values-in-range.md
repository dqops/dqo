# Percentage of integer values in range

Verifies that the percentage of integer values from a range in a column does not exceed the minimum accepted percentage.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis 
by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

We want to verify the percent of values between 0 ad 100,000 in `values` column.

**SOLUTION**

We will verify the data of `bigquery-public-data.america_health_rankings.ahr` using monitoring
[values_in_range_numeric_percent](../../checks/column/numeric/values-in-range-numeric-percent.md) column check.
Our goal is to verify if the percentage of values in a range in the `values` column does not fall below the set thresholds.

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 95.0%
- fatal: 90.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of valid values falls below 5.0%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.america_health_rankings.ahr` dataset. Some columns were omitted for clarity.  
The `value` column of interest contains values in range between 0 and 100,000.

| edition | report_type             | measure_name | state_name    | subpopulation | value  |
|:--------|:------------------------|:-------------|:--------------|:--------------|:-------|
| 2021    | 2021 Health Disparities | Able-Bodied  | California    |               | **87** |
| 2021    | 2021 Health Disparities | Able-Bodied  | Colorado      |               | **87** |
| 2021    | 2021 Health Disparities | Able-Bodied  | Hawaii        |               | **87** |
| 2021    | 2021 Health Disparities | Able-Bodied  | Kentucky      |               | **79** |
| 2021    | 2021 Health Disparities | Able-Bodied  | Maryland      |               | **87** |
| 2021    | 2021 Health Disparities | Able-Bodied  | New Jersey    |               | **87** |
| 2021    | 2021 Health Disparities | Able-Bodied  | Utah          |               | **88** |
| 2021    | 2021 Health Disparities | Able-Bodied  | West Virginia |               | **77** |
| 2021    | 2021 Health Disparities | Able-Bodied  | Arkansas      | Female        | **78** |


## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-values-in-range-numeric-percent-checks.png)

1. Go to the **Monitoring** section.

   The Monitoring Checks section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

   On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../working-with-dqo/adding-data-source-connection/index.md).


3. Select the **Monitoring Checks** tab.

   In this tab you can find a list of data quality checks.


4. Run the enabled check using the **Run check** button.

   You can also run all checks for the check category using the **Run check** button located at the end of the row with the name of the check group.

   ![Run check](https://dqops.com/docs/images/examples/daily-values-in-range-numeric-percent-run-checks.png)


5. Access the results by clicking the **Results** button.

   Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
   displays the values obtained by the sensors from the data source. The Check results category shows the severity level
   that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
   that occurred during the check's execution.

   ![Check details](https://dqops.com/docs/images/examples/daily-values-in-range-numeric-percent-checks-details.png)


6. Review the results which should be similar to the one below.
   
    The actual value in this example is 92, which is below the minimum threshold level set in the warning (99.0%).
    The check gives a warning (notice the orange square on the left of the name of the check).

    ![Values-in-range-numeric-percent check results](https://dqops.com/docs/images/examples/daily-values-in-range-numeric-percent-checks-results.png)

7. Synchronize the results with your DQO cloud account using the **Synchronize** button located in the upper right corner of the graphical interface.

    Synchronization ensures that the locally stored results are synced with your DQO Cloud account, allowing you to view them on the dashboards.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left.

    Below you can see the results displayed on the Current column status per check category dashboard showing results by connection, schema, check category and data group.

    ![Values-in-range-numeric-percent check results on Current column status per check category dashboard](https://dqops.com/docs/images/examples/daily-values-in-range-numeric-percent-checks-results-current-column-status-per-check-category-dashboard.png)

## Configuring a schedule at connection level

With DQO, you can easily customize when checks are run by setting schedules. You can set schedules for an entire connection,
table, or individual check.

After running the daily monitoring checks, let's set up a schedule for the entire connection to execute the checks every day at 12:00.

![Configure scheduler for the connection](https://dqops.com/docs/images/examples/configure-scheduler-for-connection.png)

1. Navigate to the **Data Source** section.

2. Choose the connection from the tree view on the left.

3. Click on the **Schedule** tab.

4. Select the Monitoring Daily tab

5. Select the **Run every day at** option and specify the time as 12:00.

6. Once you have set the schedule, click on the **Save** button to save your changes.

7. Enable the scheduler by clicking the toggle button.

![Enable job scheduler](https://dqops.com/docs/images/examples/enable-job-scheduler.png)

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
all tables associated with that connection.

You can [read more about scheduling here](../../working-with-dqo/schedules/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 95.0%
- fatal: 90.0%

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_values_in_range_numeric_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="12-28"
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
    value:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
      monitoring_checks:
        daily:
          numeric:
            daily_values_in_range_numeric_percent:
              parameters:
                min_value: 0.0
                max_value: 1000000.0
              warning:
                min_percent: 99.0
              error:
                min_percent: 95.0
              fatal:
                min_percent: 90.0
```

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
Review the results which should be similar to the one below.
The percentage of values between 1 and 100,000 in the `value` column is less than 95% and more than 90% and the check raised an error.

```
Check evaluation summary per table:
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection             |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|america_health_rankings|america_health_rankings.ahr|1     |1             |0            |0       |1     |0           |0               |
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
    100.0 * SUM(
        CASE
            WHEN analyzed_table.`value` >= 0.0 AND analyzed_table.`value` <= 100000.0 THEN 1
            ELSE 0
        END
    ) / COUNT(*) AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 92.9%, which is below the minimal
threshold level set in the warning alert(95.0%).

```
**************************************************
Finished executing a sensor for a check values_in_range_numeric_percent on the table america_health_rankings.ahr
using a sensor definition column/numeric/values_in_range_numeric_percent, sensor result count: 1

Results returned by the sensor:
+-----------------+------------------------+------------------------+
|actual_value     |time_period             |time_period_utc         |
+-----------------+------------------------+------------------------+
|92.87799504268797|2023-05-09T07:20:03.160Z|2023-05-09T07:20:03.160Z|
+-----------------+------------------------+------------------------+
**************************************************
```
## Next steps

- You haven't installed DQO yet? Check the detailed guide on how to [install DQO using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQO as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [profile_values_in_range_numeric_percent check used in this example, go to the check details section](../../checks/column/numeric/values-in-range-numeric-percent.md).
- You might be interested in another reasonability check that [evaluates that the length of the string does not exceed the indicated value](../data-reasonability/string-not-exceeding-a-set-length.md).
- Would you like to add your own connection? Here you can find [information about supported databases and how to add new connection](../../working-with-dqo/adding-data-source-connection/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQO](../../working-with-dqo/set-up-data-grouping/set-up-data-grouping.md) can help you to calculate separate data quality KPI scores for different groups of rows.
