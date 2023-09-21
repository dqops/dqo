# Number of null values

Verifies that the number of null values in a column does not exceed the maximum accepted count.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data. 
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

We want to verify the number of null values on `source ` column.

**SOLUTION**

We will verify the data of `bigquery-public-data.america_health_rankings.ahr` using profiling
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

## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-null-count-checks.png)

1. Go to the **Profiling** section.
    
    The Profiling section enables the configuration of advanced profiling data quality checks that are designed for the initial evaluation of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../working-with-dqo/adding-data-source-connection/index.md). 


3. Select the **Profiling Checks** tab.

    In this tab you can find a list of data quality checks. On **Profiling** section, there is also a second tab [Basic data statistics](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md) that allows you to collect summary information about your tables and columns.


4. Run the enabled check using the **Run check** button.

    You can also run all checks for the check category using the **Run check** button located at the end of the row with the name of the check group.

    ![Run check](https://dqops.com/docs/images/examples/null-count-run-checks.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
    displays the values obtained by the sensors from the data source. The Check results category shows the severity level
    that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
    that occurred during the check's execution.
 
    ![Check details](https://dqops.com/docs/images/examples/null-count-check-details.png)


6. Review the results which should be similar to the one below.
   
    The actual value of null values in this example is 8, which is above the maximum threshold level set in the warning (5).
    The check gives a warning result (notice the yellow square on the left of the name of the check).

    ![Null-count check results](https://dqops.com/docs/images/examples/null-count-check-results.png)


7. Synchronize the results with your DQO cloud account using the **Synchronize** button located in the upper right corner of the graphical interface.

    Synchronization ensures that the locally stored results are synced with your DQO Cloud account, allowing you to view them on the dashboards.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 
 
    Below you can see the results displayed on the Affected tables dashboard showing results by issues per connection, issues per schema, issues per check category and severity level.

    ![Null-count check results on Affected tables dashboard](https://dqops.com/docs/images/examples/null-count-check-results-on-affected-tables-dashboard.png)


## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum number thresholds levels for the check:

- warning: 5
- error: 10
- fatal: 15

The highlighted fragments in the YAML file below represent the segment where the profiling `nulls_count` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="46-57"
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
    value:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
    lower_ci:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
    upper_ci:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
    source:
      type_snapshot:
        column_type: STRING
        nullable: true
      profiling_checks:
        nulls:
          profile_nulls_count:
            comments:
            - date: 2023-05-08T12:08:21.558+00:00
              comment_by: user
              comment: "In this exmple, values in the `source ` column are verified\
                \ whether the number of null values does not exceed the set thresholds."
            warning:
              max_count: 5
            error:
              max_count: 10
            fatal:
              max_count: 15
    source_date:
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
The number of null values in the `source ` column is above 5 and the check raised warning.

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

- You haven't installed DQO yet? Check the detailed guide on how to [install DQO using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQO as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For further information about the [nulls_cont check used in this example](../../checks/column/nulls/nulls-count.md), read more details.
- You might be interested in another completeness check that [evaluates that the number of rows in a table does not exceed the minimum accepted count](../data-completeness/number-of-rows-in-the-table.md).  
- Would you like to add your own connection? Here you can find [information about supported databases and how to add new connection](../../working-with-dqo/adding-data-source-connection/index.md).
- DQO provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md). 
 