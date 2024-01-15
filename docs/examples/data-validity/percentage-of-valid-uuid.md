# Percentage of valid UUID
This sample shows how to use data quality checks to detect the percentage of valid UUID values in a column and view the results on data quality dashboards.

## Overview

The following example shows how to verify that the percentage of valid UUID values in a column does not fall below the minimum accepted percentage.

**PROBLEM**

Here is a table with some sample customer data. The `uuid` column contains UUID data. 

We want to verify the percent of valid UUID on `uuid` column does not fall below the set threshold.

**SOLUTION**

We will verify the data using monitoring [valid_uuid_format_percent](../../checks/column/patterns/valid-uuid-format-percent.md) column check.
Our goal is to verify that the percent of valid UUID values in a `uuid` column does not fall below the set threshold.

In this example, we will set three minimum percent thresholds levels for the check:

- warning: 99
- error: 98
- fatal: 95

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

**VALUE**

If the percentage of valid UUID values fall below 99%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `DQOps` dataset.
The `uuid ` column of interest contains both valid and invalid UUID values.

| uuid                                      | result | date      |
|:------------------------------------------|:-------|:----------|
| **26x5e2be-925b-11ed-a1eb-0242ac120002**  | 0      | 2/12/2023 |
| **wrong UUID**                            | 0      | 5/15/2022 |
| **26b5e2be-925b-112ed-a1eb-0242ac120002** | 0      | 3/13/2022 |
| **2137**                                  | 0      | 6/16/2022 |
| **26b5d9a4-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5c586-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5dd64-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5dc24-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5cdc4-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5e2be-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5cc84-925b 11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5d5ee-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5ca7c 925b 11ed a1eb 0242ac120002**  | 1      | 1/11/2023 |
| **26b5d486-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5df9e-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5d21a-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5e124-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5daf8-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5c8b0925b11eda1eb0242ac120002**      | 1      | 1/11/2023 |

## Run the example using the user interface

A detailed explanation of [how to start DQOps platform and run the example is described here](../index.md#running-the-use-cases).

### **Navigate to a list of checks**

To navigate to a list of checks prepared in the example using the [user interface](../../dqo-concepts/dqops-user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-string-valid-uuid-percent-checks1.png)

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/dqops-user-interface-overview.md#check-editor).


### **Run checks**

Run the activated check using the **Run check** button.

You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

![Run check](https://dqops.com/docs/images/examples/daily-string-valid-uuid-percent-run-checks1.png)


### **View detailed check results**

Access the detailed results by clicking the **Results** button. The results should be similar to the one below.

![String-valid-uuid-percent check results](https://dqops.com/docs/images/examples/daily-string-valid-uuid-percent-checks-result1.png)

Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
The Sensor readouts category displays the values obtained by the sensors from the data source.
The Execution errors category displays any error that occurred during the check's execution.

The actual value in this example is 75%, which is below the minimum threshold level set in the warning (100.0%).
The check gives a fatal error (notice the red square to the left of the check name).


### **Synchronize the results with the cloud account**

Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner
of the user interface.

Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

### **Review the results on the data quality dashboards**

To review the results on the [data quality dashboards](../../working-with-dqo/review-the-data-quality-results-on-dashboards.md)
go to the Data Quality Dashboards section and select the dashboard from the tree view on the left.  

Below you can see the results displayed on the **Current data quality checks results** dashboard located in the Check results group. This dashboard 
displays all executed checks run on tables and columns and allows reviewing their set parameters, as well as actual and expected values.

This dashboard allows filtering data by:
    
* time window (from last 7 days to last 6 months)
* connection,
* schema,
* data group,
* data quality dimension,
* check category,
* stages,
* priorities,
* table,
* column,
* check name,
* issue severity.

![String-valid-uuid-percent check results on Current data quality checks results dashboard](https://dqops.com/docs/images/examples/daily-string-valid-uuid-percent-checks-result-on-current-results-dashboard.png)

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percent thresholds levels for the check:

- warning: 99
- error: 98
- fatal: 95

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_valid_uuid_format_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

```yaml hl_lines="8-21"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    uuid:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          patterns:
            daily_valid_uuid_format_percent:
              warning:
                min_percent: 99.0
              error:
                min_percent: 98.0
              fatal:
                min_percent: 95.0
    result:
      type_snapshot:
        column_type: INT64
        nullable: true
```

## Run the checks in the example using the DQOps Shell

A detailed explanation of [how to start DQOps platform and run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.
The percent of valid UUID values in the `uuid` column is below 95 and the check raised fatal error.

```
Check evaluation summary per table:
+------------------+----------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection        |Table                                         |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+------------------+----------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|valid_uuid_percent|dqo_ai_test_data.uuid_test_7014625119307825231|1     |1             |0            |0       |0     |1           |0               |
+------------------+----------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```

For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection valid_uuid_percent (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`uuid` AS STRING), r"^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$")
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `dqo-ai-testing`.`dqo_ai_test_data`.`uuid_test_7014625119307825231` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 75%, which is below the minimum 
threshold level set in the warning (99%).

```
**************************************************
Finished executing a sensor for a check valid_uuid_format_percent on the table dqo_ai_test_data.uuid_test_7014625119307825231 using a sensor definition column/patterns/valid_uuid_format_percent, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|75.0        |2023-05-22T12:08:24.199Z|2023-05-22T12:08:24.199Z|
+------------+------------------------+------------------------+
**************************************************
```

In this example, we have demonstrated how to use DQOps to verify the validity of data in a column.
By using the [valid_uuid_format_percent](../../checks/column/patterns/valid-uuid-format-percent.md) column check, we can monitor that
the percentage of valid UUID values in a column does not fall below the minimum accepted percentage. If it does, you will get a warning, error or fatal results.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [valid_uuid_format_percent check used in this example, go to the check details section](../../checks/column/patterns/valid-uuid-format-percent.md).
- You might be interested in another validity check that [evaluates that ensures that the percentage of rows containing valid currency codes does not exceed set thresholds](./percentage-of-values-that-contains-usa-zipcode.md).
- With DQOps, you can easily customize when the checks are run at the level of the entire connection, table, or individual check. [Learn more about how to set schedules here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md). 
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).