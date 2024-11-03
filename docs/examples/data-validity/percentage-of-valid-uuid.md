---
title: How to detect invalid UUID values using a data quality check
---
# How to detect invalid UUID values using a data quality check
This sample shows how to use data quality checks to measure the percentage of valid UUID values in a column and view the results on data quality dashboards.

## Overview

The following example shows how to verify that the percentage of valid UUID values in a column does not fall below the minimum accepted percentage.

**PROBLEM**

Here is a table with some sample customer data. The `uuid` column contains UUID data. 

We want to verify the percent of valid UUID on `uuid` column does not fall below the set threshold.

**SOLUTION**

We will verify the data using monitoring [invalid_uuid_format_percent](../../checks/column/patterns/invalid-uuid-format-percent.md) column check.
Our goal is to verify that the percent of invalid UUID values in a `uuid` column does not exceed the set threshold.

In this example, we will set the minimum percent thresholds levels for the check:

- error: 5%

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

**VALUE**

If the percentage of invalid UUID values exceeds 5%, an error alert will be triggered.

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

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-string-valid-uuid-percent-checks2.png){ loading=lazy; width="1200px" }

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checkpoints** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/dqops-user-interface-overview.md#check-editor).


### **Run checks**

Run the activated check using the **Run check** button.

You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

![Run check](https://dqops.com/docs/images/examples/daily-string-valid-uuid-percent-run-checks2.png){ loading=lazy; width="1200px" }


### **View detailed check results**

Access the detailed results by clicking the **Results** button. The results should be similar to the one below.

![valid_uuid_format_percent check results](https://dqops.com/docs/images/examples/daily-string-valid-uuid-percent-checks-result2.png){ loading=lazy; width="1200px" }

Within the Results window, you will see four categories: **Check results**, **Sensor readouts**, **Execution errors**, and **Error sampling**.
The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
The Sensor readouts category displays the values obtained by the sensors from the data source.
The Execution errors category displays any error that occurred during the check's execution.
The Error sampling category displays examples of invalid values in the column.

The actual value in this example is 25%, which is above the minimum threshold level set in the error (5.0%).
The check gives an error (notice the orange square to the left of the check name).


### **Synchronize the results with the cloud account**

Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner
of the user interface.

Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set the maximum percent threshold level for the check:

- error: 5

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_invalid_uuid_format_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

```yaml hl_lines="8-17"
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
                  daily_invalid_uuid_format_percent:
                     error:
                        max_percent: 5.0
      result:
         type_snapshot:
            column_type: INT64
            nullable: true
      date:
         type_snapshot:
            column_type: DATE
            nullable: true
```


In this example, we have demonstrated how to use DQOps to verify the validity of data in a column.
By using the [invalid_uuid_format_percent](../../checks/column/patterns/invalid-uuid-format-percent.md) column check, we can monitor that
the percentage of valid UUID values in a column does exceed the maximum accepted percentage. If it does, you will get an error result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [valid_uuid_format_percent check used in this example, go to the check details section](../../checks/column/patterns/invalid-uuid-format-percent.md).
- You might be interested in another validity check that [evaluates that ensures that the percentage of rows containing valid currency codes does not exceed set thresholds](./percentage-of-values-that-contains-usa-zipcode.md).
- With DQOps, you can easily customize when the checks are run at the level of the entire connection, table, or individual check. [Learn more about how to set schedules here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md). 
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).