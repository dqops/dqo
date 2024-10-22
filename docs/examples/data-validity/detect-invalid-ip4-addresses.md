---
title: How to detect invalid IP4 addresses using a data quality check
---
# How to detect invalid IP4 addresses using a data quality check
This sample shows how to use data quality checks in the DQOps platform to detect invalid IP4 addresses and view the results on data quality dashboards.

## Overview

The following example shows how to verify that the number of invalid IP4 address in a column does not exceed the maximum accepted count.

**PROBLEM**

Here is a table with some sample data. In this example, we will monitor the `ip4` column.

The `ip4` column contains IP4 address values. We want to verify the number of invalid IP4 address values on `ip4` column.

**SOLUTION**

We will verify the data using monitoring [invalid_ip4_address_format_found](../../checks/column/patterns/invalid-ip4-address-format-found.md) column check.
Our goal is to verify if the number of invalid IP4 address values in `ip4` column does not exceed set thresholds.

In this example, we will set the maximum count threshold level for the check:

- warning: 0

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

**VALUE**

If the number of IP4 address values exceed 0, a warning alert will be triggered.

## Data structure

The following is a fragment of the `DQOps` dataset. Some columns were omitted for clarity.  
The `ip4` column of interest contains both valid and invalid IP4 address values.

| ip4                 | result | date      |
|:--------------------|:-------|:----------|
| 256.212.62.31       | 0      | 2/12/2023 |
| 206212177195        | 0      | 3/13/2022 |
| 206-212-177-195     | 0      | 5/15/2022 |
| 138.181.31.220      | 1      | 1/11/2023 |
| 225.129.88.137000   | 1      | 1/11/2023 |
| 116.229.97.20 text  | 1      | 1/11/2023 |
| 111218.203.183.163  | 1      | 1/11/2023 |
| 239.62.26.116       | 1      | 1/11/2023 |
| 189.133.75.23       | 1      | 1/11/2023 |
| 63.219.239.5        | 1      | 1/11/2023 |
| (67.170.154.241)    | 1      | 1/11/2023 |
| 206.212.177.195     | 1      | 1/11/2023 |
| 217.22.25.65        | 1      | 1/11/2023 |
| 198.235.37.157      | 1      | 1/11/2023 |
| text 127.186.60.232 | 1      | 1/11/2023 |
| 55.190.92.1         | 1      | 1/11/2023 |
| 150.238.182.105     | 1      | 1/11/2023 |
| 233.227.62.33       | 1      | 1/11/2023 |

## Run the example using the user interface

A detailed explanation of [how to start DQOps platform and run the example is described here](../index.md#running-the-use-cases).

### **Navigate to a list of checks**

To navigate to a list of checks prepared in the example using the [user interface](../../dqo-concepts/dqops-user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-string-invalid-ip4-address-count-checks2.png){ loading=lazy; width="1200px" }

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checkpoints** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/dqops-user-interface-overview.md#check-editor).


### **Run checks**

Run the activated check using the **Run check** button.

You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

![Run check](https://dqops.com/docs/images/examples/daily-string-invalid-ip4-address-count-run-checks2.png){ loading=lazy; width="1200px" }


### **View detailed check results**

Access the detailed results by clicking the **Results** button. The results should be similar to the one below.

![String-invalid-ip4-address-count check results](https://dqops.com/docs/images/examples/daily-string-invalid-ip4-address-count-checks-results2.png){ loading=lazy; width="1200px" }

Within the Results window, you will see four categories: **Check results**, **Sensor readouts**, **Execution errors**, and **Error sampling**.
The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
The Sensor readouts category displays the values obtained by the sensors from the data source.
The Execution errors category displays any error that occurred during the check's execution.
The Error sampling category displays examples of invalid values in the column.

The actual value in this example is 5, which is above the maximum threshold level set in the warning (0).
The check gives a warning (notice the yellow square to the left of the check name).


### **Synchronize the results with the cloud account**

Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner
of the user interface.

Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

## Change a schedule at the connection level

With DQOps, you can easily customize when checks are run by setting schedules. You can set schedules for an entire connection,
table, or individual check.

After importing new tables, DQOps sets the schedule for 12:00 P.M. (noon) every day. Follow the steps below to change the schedule.

![Change a schedule at the connection level](https://dqops.com/docs/images/examples/change-schedule-for-connection2.png){ loading=lazy; width="1200px" }

1. Navigate to the **Data Source** section.

2. Choose the connection from the tree view on the left.

3. Click on the **Schedule** tab.

4. Select the **Monitoring daily** tab

5. Select the **Run every day at** and change the time, for example, to 10:00. You can also select any other option. 

6. Once you have set the schedule, click on the **Save** button to save your changes. 

    By default, scheduler is active. You can turn it off by clicking on notification icon in the top right corner of the screen, and clicking the toggle button.

    ![Turn off job scheduler](https://dqops.com/docs/images/examples/turning-off-scheduler.png){ loading=lazy }

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
all tables associated with that connection.

You can [read more about scheduling here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set the maximum count threshold level for the check:

- warning: 0

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_invalid_ip4_address_format_found` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

```yaml hl_lines="8-17"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    ip4:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          patterns:
            daily_invalid_ip4_address_format_found:
              warning:
                max_count: 0
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
By using the [invalid_ip4_address_format_found](../../checks/column/patterns/invalid-ip4-address-format-found.md) column check, we can monitor that
the number of invalid IP4 address in a column does not exceed the maximum accepted count. If it does, you will get a warning result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [invalid_ip4_address_format_found check used in this example, go to the check details section](../../checks/column/patterns/invalid-ip4-address-format-found.md).
- You might be interested in another validity check that [evaluates that the percentage of negative values in a column does not exceed the maximum accepted percentage](../data-validity/percentage-of-negative-values.md).
- DQOps provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../../working-with-dqo/collecting-basic-data-statistics.md). 
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../../integrations/webhooks/index.md).