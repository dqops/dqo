# Percentage of rows containing USA zipcodes

Calculates the  percentage of rows that contains USA zipcodes values in a column.

**PROBLEM**

[Austin-311-Public-Data](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he) provides the residents of Austin with a simple single point of contact for every city department.

What started as police non-emergency line for the City of Austin has become a robust Citywide Information Center
where ambassadors are available to answer residents’ concerns 24 hours a day, 7 days a week, and 365 days a year.

The `incident_zip` column contains USA zipcode data. We want to verify the percentage of rows that contains USA zipcode values on `incident_zip` column.

**SOLUTION**

We will verify the data of `bigquery-public-data.austin_311.311_service_requests` using profiling
[contains_usa_zipcode_percent](../../checks/column/pii/contains-usa-zipcode-percent.md) column check.
Our goal is to verify if the percentage of rows containing USA zipcode values in the `incident_zip` column does not exceed the setup thresholds.

In this example, we will set three maximum percentage thresholds levels for the check:

- warning: 10.0%
- error: 25.0%
- fatal: 35.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of zipcode values exceed 10.0%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.austin_311.311_service_requests` dataset. Some columns were omitted for clarity.  
The `incident_zip` column of interest contains valid USA zipcode values.

| city   | incident_zip | country | state_plane_x_coordinate | state_plane_y_coordinate | latitude    |
|:-------|:-------------|:--------|:-------------------------|:-------------------------|:------------|
|        | **78613**    |         |                          |                          |             |
|        | **78664**    |         |                          |                          |             |
|        | **78729**    |         |                          |                          |             |
| austin | **78746**    |         | 0.0                      | 0                        | 3.442386682 |
|        | **78701**    |         |                          |                          |             |
|        | **78739**    |         |                          |                          |             |
|        | **78751**    |         |                          |                          |             |
|        | **78733**    |         |                          |                          |             |
|        | **78786**    |         |                          |                          |             |

## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-contains-usa-zipcode-percent.png)

1. Go to the **Profiling** section.

    The Profiling section enables the configuration of advanced profiling data quality checks that are designed for the initial evaluation of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../working-with-dqo/adding-data-source-connection/index.md).


3. Select the **Profiling Checks** tab.

    In this tab you can find a list of data quality checks. On **Profiling** section, there is also a second tab [Basic data statistics](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md) that allows you to collect summary information about your tables and columns.


4. Run the enabled check using the **Run check** button.

    You can also run all checks for the check category using the **Run check** button located at the end of the row with the name of the check group.

    ![Run check](https://dqops.com/docs/images/examples/contains-usa-zipcode-percent-run-check.png)

5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
    displays the values obtained by the sensors from the data source. The Check results category shows the severity level
    that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
    that occurred during the check's execution.
 
    ![Check details](https://dqops.com/docs/images/examples/contains-usa-zipcode-percent-check-details.png)

6. Review the results which should be similar to the one below.
   
    The actual value in this example is 98, which is above the maximum threshold level set in the warning (10.0%).
    The check gives a fatal error (notice the red square on the left of the name of the check).

    ![Contains-usa-zipcode-percent check results](https://dqops.com/docs/images/examples/contains-usa-zipcode-percent-check-results.png)

7. Synchronize the results with your DQO cloud account using the **Synchronize** button located in the upper right corner of the graphical interface.

    Synchronization ensures that the locally stored results are synced with your DQO Cloud account, allowing you to view them on the dashboards.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left.

    Below you can see the results displayed on the Daily tests per column dashboard showing results by connections, schemas, tables, data groups and checks executed per column and day of month.

    ![Contains-usa-zipcode-percent check results on Daily tests per column dashboard](https://dqops.com/docs/images/examples/contains-usa-zipcode-percent-check-results-on-daily-tests-per-column-dashboard.png)

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum percentage thresholds levels for the check:

- warning: 10.0%
- error: 25.0%
- fatal: 35.0%

The highlighted fragments in the YAML file below represent the segment where the profiling `contains_usa_zipcode_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="14-26"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    incident_zip:
      type_snapshot:
        column_type: INT64
        nullable: true
      profiling_checks:
        pii:
          profile_contains_usa_zipcode_percent:
            comments:
            - date: 2023-08-30T11:25:25.292
              comment_by: user
              comment: "In this example, values in \"incident_zip\" column are verified\
                \ whether the percentage of USA zipcode values does not exceed the\
                \ indicated thresholds."
            warning:
              max_percent: 10.0
            error:
              max_percent: 25.0
            fatal:
              max_percent: 35.0
    county:
      type_snapshot:
        column_type: STRING
        nullable: true
    state_plane_x_coordinate:
      type_snapshot:
        column_type: STRING
        nullable: true
    state_plane_y_coordinate:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
    latitude:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
```

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
Access the results which should be similar as the one below.
The percentage of the USA zipcode values in the `incident_zip` column is above 10.0% and the check raised a fatal error.
```
Check evaluation summary per table:
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection|Table                          |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|austin_311|austin_311.311_service_requests|1     |1             |0            |0       |0     |1           |0               |
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection austin_311 (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 0.0
        ELSE 100.0 * SUM(
            CASE
                WHEN REGEXP_CONTAINS(
                    CAST(analyzed_table.`incident_zip` AS STRING),
                    r"[0-9]{5}(?:-[0-9]{4})?"
                ) THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value in this example is 98%, which is above the maximum
threshold level set in the warning (10.0%).

```
**************************************************
Finished executing a sensor for a check profile_contains_usa_zipcode_percent on the table austin_311.311_service_requests using a sensor definition column/pii/contains_usa_zipcode_percent, sensor result count: 1

Results returned by the sensor:
+-----------------+-----------+--------------------+
|actual_value     |time_period|time_period_utc     |
+-----------------+-----------+--------------------+
|98.94633469908392|2023-08-01 |2023-08-01T00:00:00Z|
+-----------------+-----------+--------------------+
**************************************************
```