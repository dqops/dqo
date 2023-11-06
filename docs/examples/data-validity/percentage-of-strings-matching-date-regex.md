# Percentage of strings matching date regex

Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis
by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

The `source_date` column contains non-standard date format. We want to verify the percent of values matches the indicated by the user date format on `source_date` column.

**SOLUTION**

We will verify the data of `bigquery-public-data.america_health_rankings.ahr` using monitoring
[string_match_date_regex_percent](../../checks/column/strings/string-match-date-regex-percent.md) column check.
Our goal is to verify if the percentage of values matches the indicated by the user date format on `source_date` column does not fall below the setup thresholds.

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of data falls below 99.0%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.america_health_rankings.ahr` dataset. Some columns were omitted for clarity.  
The `source_date` column of interest contains non-standard date format, in this case this is `YYYY-MM-DD`.

| value | lower_ci | upper_ci | source                                             | source_date   |
|:------|:---------|:---------|:---------------------------------------------------|:--------------|
| 87    | 87       | 87       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 87    | 87       | 87       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 87    | 86       | 87       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 79    | 79       | 79       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 87    | 86       | 87       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 87    | 87       | 88       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 88    | 88       | 88       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 77    | 76       | 77       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 78    | 78       | 79       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |


## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-string-match-date-regex-percent-checks.png)

1. Go to the **Monitoring** section.

   The Monitoring Checks section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

   On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../working-with-dqo/adding-data-source-connection/index.md).


3. Select the **Monitoring Checks** tab.

   In this tab you can find a list of data quality checks.


4. Run the enabled check using the **Run check** button.

   You can also run all checks for the check category using the **Run check** button located at the end of the row with the name of the check group.

   ![Run check](https://dqops.com/docs/images/examples/daily-string-match-date-regex-percent-run-checks.png)


5. Access the results by clicking the **Results** button.

   Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
   displays the values obtained by the sensors from the data source. The Check results category shows the severity level
   that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
   that occurred during the check's execution.

   ![Check details](https://dqops.com/docs/images/examples/daily-string-match-date-regex-percent-checks-results.png)


6. Review the results which should be similar to the one below.
   
    The actual value in this example is 0, which is below the minimum threshold level set in the warning (99.0%).
    The check gives a fatal error (notice the red square on the left of the name of the check).

    ![String-match-date-regex-percent check results](https://dqops.com/docs/images/examples/daily-string-match-date-regex-percent-checks-results.png)

7. Synchronize the results with your DQO cloud account using the **Synchronize** button located in the upper right corner of the graphical interface.

    Synchronization ensures that the locally stored results are synced with your DQO Cloud account, allowing you to view them on the dashboards.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 
 
    Below you can see the results displayed on the Current column status per data quality dimension dashboard showing results by connection, schema, dimension and data group.

    ![String-match-date-regex-percent results on Current column status per data quality dimension dashboard](https://dqops.com/docs/images/examples/daily-string-match-date-regex-percent-checks-results-on-current-column-status-per-data-quality-dimension-dashboard.png)

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
- error: 98.0%
- fatal: 95.0%

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_string_match_date_regex_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="16-31"
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
    source_date:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          strings:
            daily_string_match_date_regex_percent:
              parameters:
                date_formats: YYYY-MM-DD
              warning:
                min_percent: 99.0
              error:
                min_percent: 98.0
              fatal:
                min_percent: 95.0

```
## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
Review the results which should be similar to the one below.
The percent of valid date formats in the `source_date` column is below the 95% and the check raised the Fatal error.

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
    CASE
        WHEN COUNT(analyzed_table.`source_date`) = 0 THEN NULL
        ELSE 100.0 * SUM(
            CASE
                WHEN SAFE.PARSE_DATE('%Y-%m-%d', analyzed_table.`source_date`) IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value in this example is 0.0%, which is below the minimum
threshold level set in the Fatal error (95.0%).
```
**************************************************
Finished executing a sensor for a check string_match_date_regex_percent on the table america_health_rankings.ahr using a sensor definition column/strings/string_match_date_regex_percent, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|0.0         |2023-04-26T11:01:24.538Z|2023-04-26T11:01:24.538Z|
+------------+------------------------+------------------------+
**************************************************
```
## Next steps

- You haven't installed DQO yet? Check the detailed guide on how to [install DQO using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQO as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [string_match_date_regex_percent check used in this example, go to the check details section](../../checks/column/strings/string-match-date-regex-percent.md).
- You might be interested in another validity check that [evaluates that the percentage of valid currency code strings in the monitored column does not fall below set thresholds.](../data-validity/percentage-of-valid-currency-codes.md).
- With DQO, you can easily customize when the checks are run at the level of the entire connection, table, or individual check. [Learn more about how to set schedules here](../../working-with-dqo/schedules/index.md). 
- DQO allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/incidents-and-notifications/incidents.md) and [notifications](../../integrations/webhooks/index.md).
