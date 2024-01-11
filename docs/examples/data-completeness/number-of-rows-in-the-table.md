# Number of rows in the table

## Overview

This example verifies that the table is not empty and meets the size requirements.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis
by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

For any database analysis, it is important that the tables are not empty. In this example, we will detect empty or too small tables.

**SOLUTION**

You will verify the data using monitoring [row_count](../../checks/table/volume/row-count.md) table check.
Row_count check has a default configuration of warning threshold set to 1. You will use this check to validate if a table is not empty.

Next, after you are sure that your table is not empty, you can set higher thresholds to ensure that the table meets size requirements.
We aim to verify if the table meets size requirements and is not too small:

- warning: 692
- error: 381
- fatal: 150

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

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

## Running the checks in the example and evaluating the results using the user interface

### **Validation if the table is not empty**

1. Go to the **Monitoring** section. 
    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.
    Here you can see that the default check warning threshold is 1 which allows you to validate whether your table is empty or not.

    ![Navigating to a list of checks](https://dqops.com/docs/images/examples/row-count-navigating-to-the-list-of-checks-warning1.png)

2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).

3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/user-interface-overview/user-interface-overview.md#check-editor).


4. Run the activated check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/row-count-run-check-warning1.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
    The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
    The Sensor readouts category displays the values obtained by the sensors from the data source. 
    The Execution errors category displays any error
    that occurred during the check's execution.

    Review the results which should be similar to the one below.

    ![Check details](https://dqops.com/docs/images/examples/row-count-check-details-warning1.png)

    The actual value of rows in this example is 18155, which is above the minimum threshold level set in the warning (1).
    The check gives a valid result (notice the green square to the left of the check name).
    Now you can be sure that you table is not empty.

6. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.
    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

7. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left.

### **Validation that the table meets the size requirements**

Next, after you are sure that your table is not empty, you can set higher thresholds to ensure that the table meets size requirements.
We aim to verify if the table meets size requirements and is not too small:

    - warning: 692
    - error: 381
    - fatal: 150


1. Set the new threshold levels 

    - warning: 692
    - error: 381
    - fatal: 150

    ![Changing the threshold levels](https://dqops.com/docs/images/examples/row-count-changing-the-threshold-levels.png)

2. Run the activated check again using the **Run check** button.

3. Access the results by clicking the **Results** button.

    ![Check details](https://dqops.com/docs/images/examples/row-count-check-details-new-tresholds.png)

    The new results will replace the previous one. 

3. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

4. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left.

    Below you can see the results displayed on the **Largest tables by number of rows** dashboard located in the Volume group. 
    This dashboard displays tables monitored with [row_count](../../checks/table/volume/row-count.md) check and allows 
    review the number of rows in these tables.

    This dashboard allows filtering data by:
    
    * time window (from last 7 days to last 3 months)
    * row count
    * connection,
    * schema,
    * data group,
    * stages,
    * table.

    ![Row-count results on largest tables by number of rows dashboard](https://dqops.com/docs/images/examples/row-count-check-results-on-largest-tables-by-number-of-rows-dashboard.png)


## Change a schedule at the connection level

With DQOps, you can easily customize when checks are run by setting schedules. You can set schedules for an entire connection,
table, or individual check.

After importing new tables, DQOps sets the schedule for 12:00 P.M. (noon) every day. Follow the steps below to change the schedule.

![Change a schedule at the connection level](https://dqops.com/docs/images/examples/change-schedule-for-connection.png)

1. Navigate to the **Data Source** section.

2. Choose the connection from the tree view on the left.

3. Click on the **Schedule** tab.

4. Select the **Monitoring daily** tab

5. Select the **Run every day at** and change the time, for example, to 10:00. You can also select any other option. 

6. Once you have set the schedule, click on the **Save** button to save your changes.

    By default, scheduler is active. You can turn it off by clicking on notification icon in the top right corner of the screen, and clicking the toggle button.

    ![Turn off job scheduler](https://dqops.com/docs/images/examples/turning-off-scheduler.png)

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
all tables associated with that connection.

You can [read more about scheduling here](../../working-with-dqo/schedules/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set minimum count threshold level for the check:

- warning: 1

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_row_count` check is configured.

```yaml hl_lines="7-16"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  monitoring_checks:
    daily:
      volume:
        daily_row_count:
          warning:
            min_count: 1
  columns:
    edition:
      type_snapshot:
        column_type: INT64
        nullable: true
    report_type:
      type_snapshot:
        column_type: STRING
        nullable: true
```


In this example, we have set three minimum count thresholds levels for the check:

- warning: 692
- error: 381
- fatal: 150

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_row_count` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="7-16"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  monitoring_checks:
    daily:
      volume:
        daily_row_count:
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
```

## Running the checks in the example and evaluating the results using DQOps Shell

A detailed explanation of [how to run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.
The number of rows is above 1 and the check gives valid result.

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
threshold level set in the warning (1).

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

In this example, we have demonstrated how to use DQOps to verify that the table is not empty and meets the size requirements.
By using the [row_count](../../checks/table/volume/row-count.md) table check, we can monitor that the number of
rows in a table does not fall below the minimum accepted count. If it does, you will get a warning, error or fatal result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQO as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [row_count check used in this example, go to the check details section](../../checks/table/volume/row-count.md).
- You might be interested in another completeness check that [evaluates that the number of nulls in a column does not exceed the maximum accepted count](../data-completeness/number-of-null-values.md).
- Would you like to add your own connection? Here you can find [information about supported databases and how to add new connection](../../data-sources/index.md).
- With DQOps, you can easily customize when the checks are run at the level of the entire connection, table, or individual check. [Learn more about how to set schedules here](../../working-with-dqo/schedules/index.md).