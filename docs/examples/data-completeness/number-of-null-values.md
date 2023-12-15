# Number of null values

Verifies that the number of null values in a column does not exceed the maximum accepted count.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data. 
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

We want to verify the number of null values on `source ` column.

**SOLUTION**

We will verify the data of `bigquery-public-data.america_health_rankings.ahr` using monitoring
[nulls_count](../../checks/column/nulls/nulls-count.md) column check.
Our goal is to verify that the number of null values in the `source ` column does not exceed the set thresholds.

In this example, we will set three maximum number thresholds levels for the check:

- warning: 5
- error: 10
- fatal: 15

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the number of not nulls values exceed 5, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.america_health_rankings.ahr` dataset. Some columns were omitted for clarity.  
The `source ` column of interest contains NULL values.

| report_type             | measure_name                   | state_name    | subpopulation                 | source                                              |
|:------------------------|:-------------------------------|:--------------|:------------------------------|:----------------------------------------------------|
| 2021 Health Disparities | Maternal Mortality             | United States | Non-Metropolitan Area         |                                                     |
| 2021 Health Disparities | Dedicated Health Care Provider | Indiana       | Other Race                    | **CDC, Behavioral Risk Factor Surveillance System** |
| 2021 Health Disparities | Dedicated Health Care Provider | Hawaii        | Black/African American        | **CDC, Behavioral Risk Factor Surveillance System** |
| 2021 Health Disparities | Dedicated Health Care Provider | Kansas        | Other Race                    | **CDC, Behavioral Risk Factor Surveillance System** |
| 2021 Health Disparities | Dedicated Health Care Provider | Idaho         |                               | **CDC, Behavioral Risk Factor Surveillance System** |
| 2021 Health Disparities | Dedicated Health Care Provider | New York      | American Indian/Alaska Native | **CDC, Behavioral Risk Factor Surveillance System** |
| 2021 Health Disparities | Dedicated Health Care Provider | Indiana       | Black/African American        | **CDC, Behavioral Risk Factor Surveillance System** |
| 2021 Health Disparities | Dedicated Health Care Provider | Montana       | High School Grad              | **CDC, Behavioral Risk Factor Surveillance System** |
| 2021 Health Disparities | Dedicated Health Care Provider | Alabama       | Male                          | **CDC, Behavioral Risk Factor Surveillance System** |
| 2021 Health Disparities | Dedicated Health Care Provider | Alaska        | Male                          | **CDC, Behavioral Risk Factor Surveillance System** |

## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-null-count-checks2.png)

1. Go to the **Monitoring** section.
    
    The Monitoring Checks section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md). 


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../../dqo-concepts/user-interface-overview/user-interface-overview/#check-editor).


4. Run the enabled check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/daily-null-count-run-checks2.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
    The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
    The Sensor readouts category displays the values obtained by the sensors from the data source.
    The Execution errors category displays any error that occurred during the check's execution.

    Review the results which should be similar to the one below.

    ![Null-count check results](https://dqops.com/docs/images/examples/daily-null-count-check-results1.png)

    The actual value of null values in this example is 8, which is above the maximum threshold level set in the warning (5).
    The check gives a warning result (notice the yellow square to the left of the check name).


6. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

7. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 
 
    Below you can see the results displayed on the **Current completeness issues on columns** dashboard located in Data Quality Dimensions/Completeness group.
    This dashboard displays results from most recently executed null checks on columns ([null_count](../../checks/column/nulls/nulls-count.md), [null_percent](../../checks/column/nulls/nulls-percent.md),
    [not_nulls_count](../../checks/column/nulls/not-nulls-count.md) and [not_nulls_percent](../../checks/column/nulls/not-nulls-percent.md)).

    This dashboard allows filtering data by:
    
    * time window (from last 7 days to last 3 months)
    * connection,
    * schema,
    * data group,
    * data quality dimension,
    * check category,
    * check name,
    * stages,
    * priorities,
    * table,
    * column,
    * issue severity

    ![Null-count check results on current completeness issues on columns dashboard](https://dqops.com/docs/images/examples/current-completeness-issues-on-columns-dashboard.png)

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

In this example, we have set three maximum number thresholds levels for the check:

- warning: 5
- error: 10
- fatal: 15

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_nulls_count` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="20-33"
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
    source:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          nulls:
            daily_nulls_count:
              warning:
                max_count: 5
              error:
                max_count: 10
              fatal:
                max_count: 15
```

## Running the checks in the example and evaluating the results using DQOps Shell

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.
The number of null values in the `source` column is above 5 and the check raised warning.

```
Check evaluation summary per table:
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection             |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|america_health_rankings|america_health_rankings.ahr|1     |1             |1            |1       |0     |0           |0               |
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
    SUM(
        CASE
            WHEN analyzed_table.`source` IS NULL THEN 1
            ELSE 0
        END
    ) AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 8, which is above the maximum 
threshold level set in the warning (5).

```
**************************************************
Finished executing a sensor for a check nulls_count on the table america_health_rankings.ahr using a sensor definition column/nulls/null_count, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|8           |2023-05-08T12:05:28.996Z|2023-05-08T12:05:28.996Z|
+------------+------------------------+------------------------+
**************************************************
```

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQO as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [nulls_cont check used in this example, go to the check details section](../../checks/column/nulls/nulls-count.md).
- You might be interested in another completeness check that [evaluates that the number of rows in a table does not exceed the minimum accepted count](../data-completeness/number-of-rows-in-the-table.md).  
- Would you like to add your own connection? Here you can find [information about supported databases and how to add new connection](../../data-sources/index.md).
- DQOps provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md). 
 