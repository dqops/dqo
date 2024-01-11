# Percentage of false boolean values

## Overview

This example shows how to detect that the percentage of false boolean values remains above a set threshold.

**PROBLEM**

[The Federal Communication Commission (FCC) political ads public inspection files dataset](https://console.cloud.google.com/marketplace/product/federal-communications-commission/fcc-political-ads)
contains political ad file information that broadcast stations have uploaded to their public inspection files, which are
housed on the FCC website.

This information is uploaded to the FCC’s website in PDF form and not machine-readable. However, this dataset includes 
a content_info table that contains manual annotations of some data fields like advertiser, gross spend, ad air dates and
a link to a copy of the PDF, which can be found on Google Cloud Storage.

The `invalidOcr` column indicates if the OCR does match the raw file text (false value) or does not (true value). In case
of the true value, the OCR process needs more work and the file is not ready to be transcribed.

We want to verify the percentage of false boolean values in the `invalidOcr` column, which will tell us what percentage of data is 
ready to be transcribed.

**SOLUTION**

We will verify the data of `bigquery-public-data.fcc_political_ads.content_info` using monitoring 
[false_percent](../../checks/column/bool/false-percent.md) column check.
Our goal is to verify that the percentage of false boolean values on `invalidOcr` column does not fall below 99%. 

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of data that is available for transcription drops below 99%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.fcc_political_ads.content_info` dataset. Some columns were omitted for clarity.  
The `invalidOcr` column of interest contains both TRUE and FALSE values.

| contentInfoId    | advertiser                     | grossSpend | infoSource | invalidOcr |
|:-----------------|:-------------------------------|:-----------|:-----------|:-----------|
| 5116276091387904 | J Hawley for Missouri Senate-R | 2847500    | MANUAL     | **TRUE**   |
| 5101779805011968 | Friends of Michael Guest       | 307500     | MANUAL     | **FALSE**  |
| 5948640182927360 | ALISON HARTSON 2018            | 360000     | MANUAL     | **FALSE**  |
| 5724690924437504 | Friedman for Congress          | 2247500    | MANUAL     | **TRUE**   |
| 5660449924186112 | WALDEN, GREG                   | 85000      | MANUAL     | **FALSE**  |
| 4831078887981056 | ASHFORD FOR CONGRESS           | 1557500    | MANUAL     | **FALSE**  |
| 6030938634977280 | Dr. Jim Maxwell                | 2735000    | MANUAL     | **TRUE**   |
| 5199588961026048 | CROWLEY FOR CONGRESS           | 1170000    | MANUAL     | **FALSE**  |
| 4925540251205632 | Alaskans For Don Young         | 1500800    | MANUAL     | **FALSE**  |
| 5155591651590144 | POL/ Ted Cruz/R/US SEN / TX    | 1610000    | MANUAL     | **FALSE**  |

## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-false-percent-checks1.png)

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/user-interface-overview/user-interface-overview.md#check-editor).


4. Run the activated check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/daily-false-percent-run-checks1.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
    The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
    The Sensor readouts category displays the values obtained by the sensors from the data source.
    The Execution errors category displays any error that occurred during the check's execution.

    Review the results which should be similar to the one below.

    ![False-percent check results](https://dqops.com/docs/images/examples/daily-false-percent-check-results1.png)

    The actual value in this example is 99%, which is above the minimum threshold level set in the warning (99%).
    The check gives a valid result (notice the green square to the left of the check name).


6. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

7. To review the results on the [data quality dashboards](../../working-with-dqo/reviewing-results-on-data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left.

    Below you can see the results displayed on the **Current column status** dashboard located in the Current status group. 

    This dashboard allows data engineers and data owners to quickly evaluate the data quality of monitored
    columns. The dashboards display a color-coded status that indicates the severity level detected by run
    checks. When the status is green, it means that the monitored column has no data quality issues. However, if the status
    is yellow, orange, or red, it indicates that there were some issues detected. The dashboard also displays the number
    of detected issues per severity threshold, making it easier to identify and address tables and columns with issues.
    
    This dashboard allow filtering data by:
    
    * time frame,
    * connection,
    * schema,
    * data quality dimension,
    * check category,
    * data group,
    * table,
    * column.

    ![False-percent check results on the Current column status dashboard](https://dqops.com/docs/images/examples/daily-false-percent-check-results-on-current-column-status-dashboard.png)

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

You can [read more about scheduling here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations. 

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_false_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="16-29"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    grossSpend:
      type_snapshot:
        column_type: INT64
        nullable: true
    infoSource:
      type_snapshot:
        column_type: STRING
        nullable: true
    invalidOcr:
      type_snapshot:
        column_type: BOOL
        nullable: true
      monitoring_checks:
        daily:
          bool:
            daily_false_percent:
              warning:
                min_percent: 99.0
              error:
                min_percent: 98.0
              fatal:
                min_percent: 95.0
```

## Running the checks in the example and evaluating the results using DQOps Shell

A detailed explanation of [how to run the example is described here](../index.md#running-the-use-cases). 

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.
The check shows a valid results what means that the percentage of false values in the `invalidOcr` column exceeds 99%.

```
Check evaluation summary per table:
+-----------------+------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection       |Table                         |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------+------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|fcc_political_ads|fcc_political_ads.content_info|1     |1             |1            |0       |0     |0           |0               |
+-----------------+------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```

For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the 
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection fcc_political_ads (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN NOT analyzed_table.`invalidOcr`
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`fcc_political_ads`.`content_info` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also view the results returned by the sensor. The actual value in this example is 99.95%, which exceeds the 
minimal threshold level set in the warning (99%).

```
Finished executing a sensor for a check false_percent on the table fcc_political_ads.content_info using a sensor 
definition column/bool/false_percent, sensor result count: 1

Results returned by the sensor:
+----------------+------------------------+------------------------+
|actual_value    |time_period             |time_period_utc         |
+----------------+------------------------+------------------------+
|99.9540159411404|2023-04-26T09:06:53.386Z|2023-04-26T09:06:53.386Z|
+----------------+------------------------+------------------------+
**************************************************
```

In this example, we have demonstrated how to use DQOps to verify the reasonability of data in a column.
By using the [false_percent](../../checks/column/bool/false-percent.md) column check, we can monitor that the percentage
of false boolean values does not fall below a set threshold. If it does, you will get a warning, error or fatal result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [profile_false_percent check used in this example, go to the check details section](../../checks/column/bool/false-percent.md).
- You might be interested in another reasonability check that [evaluates that percentage of integer values from a range in a column does not exceed the minimum accepted percentage](../data-reasonability/percentage-of-integer-values-in-range.md).
- With DQOps, you can easily customize when the checks are run at the level of the entire connection, table, or individual check. [Learn more about how to set schedules here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).
- DQOps provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../../working-with-dqo/collecting-basic-data-statistics.md).