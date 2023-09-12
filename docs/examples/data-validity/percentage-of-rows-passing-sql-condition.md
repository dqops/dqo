# Percentage of passed SQL condition on table

Table level check that ensures that a minimum percentage of rows passed a custom SQL condition (expression).

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis
by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

### Data structure

The following is a fragment of the `bigquery-public-data.america_health_rankings.ahr` dataset. Some columns were omitted for clarity.

| value | lower_ci | upper_ci | source                                             | source_date |
|:------|:---------|:---------|:---------------------------------------------------|:------------|
| 87    | **87**   | **87**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 87    | **87**   | **87**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 87    | **86**   | **87**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 79    | **79**   | **79**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 87    | **86**   | **87**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 87    | **87**   | **88**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 88    | **88**   | **88**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 77    | **76**   | **77**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 78    | **78**   | **79**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |

We want to verify that the upper_ci column is always greater or equal to the lower_ci column.
The check result should tell us if we have any rows not matching an SQL expression `upper_ci >=lower_ci` evaluated on each row.

The SQL query that can calculate the percentage of rows that passed the check should be like:
```sql hl_lines="5-5"
SELECT
     SUM(
          CASE
              WHEN 
                   upper_ci >= lower_ci
              THEN 1
              ELSE 0
          END) * 100.0 / COUNT(*)  AS actual_value
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
```


We want to verify that the percent of rows passed a custom SQL condition (expression) matches the expected threshold,
for example at least 95% passed the data quality check or a data quality issue is raised.


**SOLUTION**

We will verify the data of `bigquery-public-data.america_health_rankings.ahr` using profiling
[sql_condition_passed_percent](../../checks/table/sql/sql-condition-passed-percent-on-table.md) table check.
Our goal is to verify if the percentage of rows passed a custom SQL condition does not fall below the setup thresholds.

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 100.0%
- error: 99.0%
- fatal: 95.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of data falls below 100.0%, a warning alert will be triggered.

## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-sql-condition-passed-percent-check.png)

1. Go to the **Profiling** section.

    The Profiling section enables the configuration of advanced profiling data quality checks that are designed for the initial evaluation of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../working-with-dqo/adding-data-source-connection/index.md).


3. Select the **Profiling Checks** tab.

    In this tab you can find a list of data quality checks. On **Profiling** section, there is also a second tab [Basic data statistics](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md) that allows you to collect summary information about your tables and columns.


4. Run the enabled check using the **Run check** button.

    You can also run all checks for the check category using the **Run check** button located at the end of the row with the name of the check group.

    ![Run check](https://dqops.com/docs/images/examples/sql-condition-passed-percent-run-check.png)

5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
    displays the values obtained by the sensors from the data source. The Check results category shows the severity level
    that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
    that occurred during the check's execution.
 
    ![Check details](https://dqops.com/docs/images/examples/sql-condition-passed-percent-check-details.png)

6. Review the results which should be similar to the one below.
   
    The actual value in this example is 92, which is below the minimum threshold level set in the warning (100.0%).
    The check gives a fatal error (notice the red square on the left of the name of the check).

    ![SQL-condition-passed-percent check results](https://dqops.com/docs/images/examples/sql-condition-passed-percent-check-results.png)

7. Synchronize the results with your DQO cloud account using the **Synchronize** button located in the upper right corner of the graphical interface.

    Synchronization ensures that the locally stored results are synced with your DQO Cloud account, allowing you to view them on the dashboards.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left.

    Below you can see the results displayed on the Affected tables per KPI dashboard showing results by issues per connection, issues per schema, issues per data quality dimension and issues per check category.

    ![SQL-condition-passed-percent results on Affected tables per KPI dashboard](https://dqops.com/docs/images/examples/sql-condition-passed-percent-results-on-affected-tables-per-KPI-dashboard.png)

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 100.0%
- error: 99.0%
- fatal: 95.0%

The highlighted fragments in the YAML file below represent the segment where the profiling `sql_condition_passed_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="9-17"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    sql:
      profile_sql_condition_passed_percent_on_table:
        parameters:
          sql_condition: upper_ci >= lower_ci
        warning:
          min_percent: 100.0
        error:
          min_percent: 99.0
        fatal:
          min_percent: 95.0
  columns:
    edition:
      type_snapshot:
        column_type: INT64
        nullable: true
```

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
Review the results which should be similar to the one below.

```
Check evaluation summary per table:
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection             |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|america_health_rankings|america_health_rankings.ahr|1     |1             |0            |0       |0     |**1**       |0               |
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
As you can see, the table failed the data quality check raising a fatal severity error.
The percent of passed SQL expressions is below the 95.0% and the check raised the Fatal error.

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
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
                         CASE
                             WHEN (upper_ci >= lower_ci)
                                  THEN 1
                             ELSE 0
                         END) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. Now the actual value in this example is 92.6025888185073%, which is below the minimum
threshold level set in the Fatal error (95.0%).
```
**************************************************
Finished executing a sensor for a check sql_condition_passed_percent_on_table on the table america_health_rankings.ahr using a sensor definition table/sql/sql_condition_passed_percent, sensor result count: 1

Results returned by the sensor:
+----------------+------------------------+------------------------+
|actual_value    |time_period             |time_period_utc         |
+----------------+------------------------+------------------------+
|92.6025888185073|2023-05-18T08:29:25.667Z|2023-05-18T08:29:25.667Z|
+----------------+------------------------+------------------------+
**************************************************
```

The expression `upper_ci >=lower_ci` was false for almost 7.4% rows probably because the column upper_ci or lower_ci is NULL so the expression was false. 

Let's update the SQL expression and count rows with NULL values as valid.

```yaml hl_lines="9-17"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    sql:
      profile_sql_condition_passed_percent_on_table:
        parameters:
          sql_condition: upper_ci >= lower_ci or upper_ci is NULL or lower_ci is NULL
        warning:
          min_percent: 100.0
        error:
          min_percent: 99.0
        fatal:
          min_percent: 95.0
  columns:
    edition:
      type_snapshot:
        column_type: INT64
        nullable: true
```
## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-sql-condition-passed-percent-check-valid.png)

1. Go to the **Profiling** section.

    The Profiling section enables the configuration of advanced profiling data quality checks that are designed for the initial evaluation of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../working-with-dqo/adding-data-source-connection/index.md).


3. Select the **Profiling Checks** tab.

    In this tab you can find a list of data quality checks. On **Profiling** section, there is also a second tab [Basic data statistics](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md) that allows you to collect summary information about your tables and columns.


4. Run the enabled check using the **Run check** button.

    You can also run all checks for the check category using the **Run check** button located at the end of the row with the name of the check group.

    ![Run check](https://dqops.com/docs/images/examples/sql-condition-passed-percent-run-check-valid.png)

5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
    displays the values obtained by the sensors from the data source. The Check results category shows the severity level
    that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
    that occurred during the check's execution.
 
    ![Check details](https://dqops.com/docs/images/examples/sql-condition-passed-percent-check-details-valid.png)

6. Review the results which should be similar to the one below.
   
    The actual value in this example is 100.
    The check gives a valid result (notice the green square on the left of the name of the check).

    ![SQL-condition-passed-percent check results](https://dqops.com/docs/images/examples/sql-condition-passed-percent-check-results-valid.png)

7. Synchronize the results with your DQO cloud account using the **Synchronize** button located in the upper right corner of the graphical interface.

    Synchronization ensures that the locally stored results are synced with your DQO Cloud account, allowing you to view them on the dashboards.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 

    Below you can see the results displayed on the Total DQ KPIs per category dashboard showing results by KPI, KPI per check category, Datetime category, Pii KPI and Standard KPI.

    ![SQL-condition-passed-percent results on Total DQ KPIs per category dashboard](https://dqops.com/docs/images/examples/sql-condition-passed-percent-results-on-total-DQ-KPIs-per-category-dashboard-valid.png)

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
Review the results which should be similar to the one below.
The percent of passed SQL expressions is 100.0% so the result is valid.

```
Check evaluation summary per table:
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection             |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|america_health_rankings|america_health_rankings.ahr|1     |1             |1            |0       |0     |0           |0               |
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
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
                         CASE
                             WHEN (upper_ci >= lower_ci or upper_ci is NULL or lower_ci is NULL)
                                  THEN 1
                             ELSE 0
                         END) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value in this example is 100.0%.
```
**************************************************
Finished executing a sensor for a check sql_condition_passed_percent_on_table on the table america_health_rankings.ahr using a sensor definition table/sql/sql_condition_passed_percent, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|100.0       |2023-05-18T08:38:22.311Z|2023-05-18T08:38:22.311Z|
+------------+------------------------+------------------------+
**************************************************
```