# Percentage of negative values

Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.

**PROBLEM**

[Countries in the world by population](https://www.worldometers.info/world-population/population-by-country/) provides an analysis of national health on a state-by-state basis by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

Population rise is currently a major subject of discussion nowadays. Every day, there is higher birth rate recorded as compare to death rate which is quite alarming for the world.
Below is the complete data about the world population , country by country (235 countries).

Worldometer was voted as one of the best free reference websites by the American Library Association (ALA), the oldest and largest library association in the world. 
Worldometer is a provider of global COVID-19 statistics for many caring people around the world. 
Worldometr's data is also trusted and used by the UK Government, Johns Hopkins CSSE, the Government of Thailand, the Government of Pakistan, the Government of Sri Lanka, Government of Vietnam and many others.

We want to verify the percentage of negative values on `Migrants__net_` column.

**SOLUTION**

We will verify the data using monitoring [negative_percent](../../checks/column/numeric/negative-percent.md) column check.
Our goal is to verify that the percent of negative values in the `Migrants__net_` column does not exceed the set thresholds.

In this example, we will set three maximum percentage thresholds levels for the check:

- warning: 45.0
- error: 55.0
- fatal: 60.0

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of negative values exceed 45.0, a warning alert will be triggered.

## Data structure

The following is a fragment of the World population dataset. Some columns were omitted for clarity.  
The `Migrants__net` column of interest contains negative values.

| Country__or_dependency_ | Population__2022_ | Yearly_change | Net_change | Density__P_Km___ | Land_Area__Km___ | Migrants__net_ |
|:------------------------|:------------------|:--------------|:-----------|:-----------------|:-----------------|:---------------|
| Mali                    | 20250833          | 0.0302        | 592802     | 17               | 1220190          | **-40000**     |
| DR Congo                | 89561403          | 0.0319        | 2770836    | 40               | 2267050          | **23861**      |
| Uganda                  | 45741007          | 0.0332        | 1471413    | 229              | 199810           | **168694**     |
| Angola                  | 32866272          | 0.0327        | 1040977    | 26               | 1246700          | **6413**       |
| Chad                    | 16425864          | 0.03          | 478988     | 13               | 1259200          | **2000**       |
| Somalia                 | 15893222          | 0.0292        | 450317     | 25               | 627340           | **-40000**     |
| Burundi                 | 11890784          | 0.0312        | 360204     | 463              | 25680            | **2001**       |
| Nigeria                 | 206139589         | 0.0258        | 5175990    | 226              | 910770           | **-60000**     |
| Tanzania                | 59734218          | 0.0298        | 1728755    | 67               | 885800           | **-40076**     |


## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-negative-percent-checks.png)

1. Go to the **Monitoring** section.

    The Monitoring Checks section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../working-with-dqo/adding-data-source-connection/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../../dqo-concepts/user-interface-overview/user-interface-overview/#check-editor).


4. Run the enabled check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/daily-negative-percent-run-checks.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
    displays the values obtained by the sensors from the data source. The Check results category shows the severity level
    that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
    that occurred during the check's execution.

    ![Check details](https://dqops.com/docs/images/examples/daily-negative-percent-checks-details.png)

    Review the results which should be similar to the one below.
   
    The actual value in this example is 48, which is above the maximum threshold level set in the warning (45.0%).
    The check gives a warning result (notice the yellow square on the left of the name of the check).

    ![Negative-percent check results](https://dqops.com/docs/images/examples/daily-negative-percent-checks-results.png)

6. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

7. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left.

    Below you can see the results displayed on the KPIs scoreboard - summary dashboard showing results by percentage of passed checks, KPIs history by month, passed data quality checks, percentage of executed checks and failed data quality checks.

    ![Negative-percent check results on KPIs scoreboard - summary dashboard](https://dqops.com/docs/images/examples/daily-negative-percent-checks-results-on-KPIs-scoreboard-summary-dashboard.png)

## Configuring a schedule at connection level

With DQOps, you can easily customize when checks are run by setting schedules. You can set schedules for an entire connection,
table, or individual check.

After running the daily monitoring checks, let's set up a schedule for the entire connection to execute the checks every day at 12:00.

![Configure scheduler for the connection](https://dqops.com/docs/images/examples/configure-scheduler-for-connection.png)

1. Navigate to the **Data Source** section.

2. Choose the connection from the tree view on the left.

3. Click on the **Schedule** tab.

4. Select the Monitoring Daily tab

5. Select the **Run every day at** option and specify the time as 12:00.

6. Once you have set the schedule, click on the **Save** button to save your changes.

    By default, scheduler is active. You can turn it off by clicking on notification icon in the top right corner of the screen, and clicking the toggle button.

    ![Turn off scheduler](https://dqops.com/docs/images/examples/turning-off-scheduler.png)

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
all tables associated with that connection.

You can [read more about scheduling here](../../working-with-dqo/schedules/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum percentage thresholds levels for the check:

- warning: 45.0
- error: 55.0
- fatal: 60.0

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_negative_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="16-29"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    Country__or_dependency_:
      type_snapshot:
        column_type: STRING
        nullable: true
    Land_Area__Km___:
      type_snapshot:
        column_type: INT64
        nullable: true
    Migrants__net_:
      type_snapshot:
        column_type: INT64
        nullable: true
      monitoring_checks:
        daily:
          numeric:
            daily_negative_percent:
              warning:
                max_percent: 45.0
              error:
                max_percent: 55.0
              fatal:
                max_percent: 60.0
```

## Running the checks in the example and evaluating the results using DQOps Shell

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.
The percentage of negative values in the `Migrants__net_` column is above 45.0 and the check raised warning.

```
Check evaluation summary per table:
+----------------+-----------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection      |Table                                          |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+----------------+-----------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|negative_percent|kaggle_worldpopulation.world_population_dataset|1     |1             |1            |1       |0     |0           |0               |
+----------------+-----------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```

For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection negative_percent (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN analyzed_table.`Migrants__net_` < 0 THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `dqo-ai-testing`.`kaggle_worldpopulation`.`world_population_dataset` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 48.08510638297872, which is above the maximum 
threshold level set in the warning (45.0).

```
**************************************************
Finished executing a sensor for a check negative_percent on the table kaggle_worldpopulation.world_population_dataset using a sensor definition column/numeric/negative_percent, sensor result count: 1

Results returned by the sensor:
+-----------------+------------------------+------------------------+
|actual_value     |time_period             |time_period_utc         |
+-----------------+------------------------+------------------------+
|48.08510638297872|2023-05-16T08:45:11.722Z|2023-05-16T08:45:11.722Z|
+-----------------+------------------------+------------------------+
**************************************************
```

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQOps as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [negative_percent check used in this example, go to the check details section](../../checks/column/numeric/negative-percent.md).
- You might be interested in another validity check that [evaluates that a minimum percentage of rows passed a custom SQL condition (expression)](../data-validity/percentage-of-rows-passing-sql-condition.md).
- With DQOps, you can easily customize when the checks are run at the level of the entire connection, table, or individual check. [Learn more about how to set schedules here](../../working-with-dqo/schedules/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping/set-up-data-grouping.md) can help you to calculate separate data quality KPI scores for different groups of rows.