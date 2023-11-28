# Percentage of valid latitude and longitude

Verifies that the percentage of valid latitude and longitude values are above the set threshold.

**PROBLEM**

[Austin-311-Public-Data](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he) 
provides the residents of Austin with a simple single point of contact for every city department.

What started as police non-emergency line for the City of Austin has become a robust Citywide Information Center
where ambassadors are available to answer residents’ concerns 24 hours a day, 7 days a week, and 365 days a year.

The dataset contain the list of service requests with unique key, descriptions, status, address as well as its longitude and latitude coordinates. 

We want to verify the percentage of valid latitude and longitude values in `latitude` and `longitude` columns, respectively.

**SOLUTION**

We will verify the data of `bigquery-public-data.austin_311.311_service_requests` using monitoring
[valid_latitude_percent](../../checks/column/numeric/valid-latitude-percent.md) and 
[valid_longitude_percent](../../checks/column/numeric/valid-longitude-percent.md) column checks.
Our goal is to verify if the percentage of valid latitude values in the `latitude` and `longitude` columns are above the set threshold.

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of valid latitude or longitude values is below 99%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.austin_311.311_service_requests` dataset. Some columns were 
omitted for clarity.  
The `latitude` and `longitude` columns of interest contains valid values.

| unique_key  | complaint_description               | incident_address                    | latitude        | longitude        |
|:------------|:------------------------------------|:------------------------------------|:----------------|:-----------------|
| 14-00000261 | Sign - Traffic Sign Maintenance     | 1411 MEARNS MEADOW BLVD, AUSTIN, TX | **30.37525546** | **-97.70466813** |
| 14-00000296 | Sign - Traffic Sign Maintenance     | DEER LN & BRODIE LN, AUSTIN, TX     | **30.19429024** | **-97.84298292** |
| 14-00001646 | Animal Control - Assistance Request | WHITESTONE                          | **30.53892395** | **-97.77471976** |
| 14-00001647 | Sign - Traffic Sign Maintenance     | METRIC                              | **30.38409576** | **-97.71474292** |
| 14-00001648 | Code Compliance                     | LA NARANJA                          | **30.21519414** | **-97.8738822**  |
| 14-00001649 | Code Compliance                     | DUBUQUE                             | **30.31488504** | **-97.6673545**  |
| 14-00001650 | Loose Dog                           | LOS INDIOS TRL & MC NEIL DR         | **30.43736609** | **-97.76050242** |
| 14-00001651 | Traffic Signal - Maintenance        | PLEASANT VALLEY                     | **30.17838134** | **-97.74744183** |
| 14-00001652 | ARR Missed Yard Trimmings /Organics | PROVENCIAL                          | **30.28755755** | **-97.66188875** |
| 14-00001654 | Parking Ticket Complaint            | 9TH                                 | **30.27091532** | **-97.7417791**  |

## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-valid-latitude-and-longitude-percent-checks.png)

1. Go to the **Monitoring** section.

    The Monitoring Checks section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../working-with-dqo/adding-data-source-connection/index.md).


3. Select the **Daily checks** tab.

    In this tab you can find a list of data quality checks.


4. Run the enabled check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/daily-valid-latitude-and-longitude-percent-run-checks.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
    displays the values obtained by the sensors from the data source. The Check results category shows the severity level
    that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
    that occurred during the check's execution.

    ![Check details](https://dqops.com/docs/images/examples/daily-valid-latitude-and-longitude-percent-checks-details.png)


6. Review the results which should be similar to the one below.
   
    The actual value in this example is 99, which is above the minimum threshold level set in the warning (99.0%).
    The check gives a valid error (notice the green square on the left of the name of the check).

    ![Valid-latitude-and-longitude-percent check results](https://dqops.com/docs/images/examples/daily-valid-latitude-and-longitude-percent-checks-results.png)

7. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left.
 
    Below you can see the results displayed on the Issues count per table dashboard showing results by severity level, table stage, table priority, issues per connection and issues per schema.

    ![Valid-latitude-and-longitude-percent check results on Issues count per table dashboard](https://dqops.com/docs/images/examples/daily-valid-latitude-and-longitude-percent-checks-results-on-issues-count-per-table-dashboard.png)

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

7. Enable the scheduler by clicking the toggle button.

![Enable job scheduler](https://dqops.com/docs/images/examples/enable-job-scheduler.png)

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
all tables associated with that connection.

You can [read more about scheduling here](../../working-with-dqo/schedules/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum percentage thresholds levels for the checks:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_valid_latitude_percent` and
`daily_valid_longitude_percent`checks are configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="12-39"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    unique_key:
      type_snapshot:
        column_type: STRING
        nullable: true
    latitude:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
      monitoring_checks:
        daily:
          numeric:
            daily_valid_latitude_percent:
              warning:
                min_percent: 99.0
              error:
                min_percent: 98.0
              fatal:
                min_percent: 95.0
    longitude:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
      monitoring_checks:
        daily:
          numeric:
            daily_valid_longitude_percent:
              warning:
                min_percent: 99.0
              error:
                min_percent: 98.0
              fatal:
                min_percent: 95.0
```

## Running the checks in the example and evaluating the results using DQOps Shell

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.
The percent of valid latitude and longitude values is above the 99% and the checks results are valid.

```
Check evaluation summary per table:
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection|Table                          |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|austin_311|austin_311.311_service_requests|2     |2             |2            |0       |0     |0           |0               |
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```

For a more detailed insight of how the checks are run, you can initiate the checks in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL queries (sensors) executed in the checks.

```
**************************************************
Executing SQL on connection austin_311 (bigquery)
SQL to be executed on the connection:
SELECT
    100.0 * SUM(
        CASE
            WHEN analyzed_table.`latitude` >= -90.0 AND analyzed_table.`latitude` <= 90.0 THEN 1
            ELSE 0
        END
    )/COUNT(*) AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

```
**************************************************
Executing SQL on connection austin_311 (bigquery)
SQL to be executed on the connection:
SELECT
    100.0 * SUM(
        CASE
            WHEN analyzed_table.`longitude` >= -180.0 AND analyzed_table.`longitude` <= 180.0 THEN 1
            ELSE 0
        END
    )/COUNT(*) AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************

```

You can also see the results returned by the sensors. The actual values in this example for both sensors is 99.19%, 
which is above the maximum threshold level set for the warning (99%).

```
**************************************************
Finished executing a sensor for a check valid_latitude_percent on the table austin_311.311_service_requests 
using a sensor definition column/numeric/valid_latitude_percent, sensor result count: 1

Results returned by the sensor:
+----------------+------------------------+------------------------+
|actual_value    |time_period             |time_period_utc         |
+----------------+------------------------+------------------------+
|99.1905543612962|2023-05-08T06:08:26.679Z|2023-05-08T06:08:26.679Z|
+----------------+------------------------+------------------------+
**************************************************
```

```
Finished executing a sensor for a check valid_longitude_percent on the table austin_311.311_service_requests
using a sensor definition column/numeric/valid_longitude_percent, sensor result count: 1

Results returned by the sensor:
+-----------------+------------------------+------------------------+
|actual_value     |time_period             |time_period_utc         |
+-----------------+------------------------+------------------------+
|99.19006528255773|2023-05-08T13:34:23.984Z|2023-05-08T13:34:23.984Z|
+-----------------+------------------------+------------------------+
```

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQOps as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [valid_latitude_percent check used in this example, go to the check details section](../../checks/column/numeric/valid-latitude-percent.md).
- For details on the [valid_longitude_percent check used in this example, go to the check details section](../../checks/column/numeric/valid-longitude-percent.md).
- You might be interested in another validity check that [evaluates that the percentage of valid UUID values in a column does not fall below the minimum accepted percentage](../data-validity/percentage-of-valid-uuid.md).
- Would you like to add your own connection? Here you can find [information about supported databases and how to add new connection](../../working-with-dqo/adding-data-source-connection/index.md).
