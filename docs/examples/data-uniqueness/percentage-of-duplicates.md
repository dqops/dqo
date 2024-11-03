---
title: How to measure the percentage of duplicate values using a data quality check
---
# How to measure the percentage of duplicate values using a data quality check
This sample shows how to use data quality checks in the DQOps platform to detect the percentage of duplicates and view the results on data quality dashboards.

## Overview

This example verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.

**PROBLEM**

[Austin-311-Public-Data](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he) provides the residents of Austin with a simple single point of contact for every city department.

What started as police non-emergency line for the City of Austin has become a robust Citywide Information Center
where ambassadors are available to answer residents’ concerns 24 hours a day, 7 days a week, and 365 days a year.

The `unique_key` column contains unique key data. We want to verify the percent of duplicated values on `unique_key` column.

**SOLUTION**

We will verify the data of `bigquery-public-data.austin_311.311_service_requests` using monitoring
[duplicate_percent](../../checks/column/uniqueness/duplicate-percent.md) column check.
Our goal is to verify if the percentage of duplicated values in `unique_key` column does not exceed set thresholds.

In this example, we will set the maximum percentage threshold level for the check:

- error: 1.0%

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

**VALUE**

If the percentage of duplicated values on `unqiue_key` column exceed 1.0%, an error alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.austin_311.311_service_requests` dataset. Some columns were omitted for clarity.  
The `unique_key` column of interest contains unique values.

| unique_key      | complaint_description                               | source | source | status_change_date  | created_date        |
|:----------------|:----------------------------------------------------|:-------|:-------|:--------------------|:--------------------|
| **19-00454912** | Parking Machine Issue                               | Phone  | Closed | 12/3/2019 6:54:59   | 11/30/2019 11:33:22 |
| **20-00288726** | Community Connections - Coronavirus                 | Phone  | Closed | 7/16/2020 11:26:40  | 7/16/2020 10:21:17  |
| **19-00458482** | Parking Machine Issue                               | Phone  | Closed | 12/5/2019 6:41:42   | 12/3/2019 12:57:47  |
| **17-00207653** | Street Light Issue- Address                         | Web    | Closed | 7/20/2017 12:33:20  | 7/20/2017 11:19:51  |
| **18-00118937** | Parking Machine Issue                               | Phone  | Closed | 4/25/2018 13:30:43  | 4/24/2018 8:30:23   |
| **20-00525858** | Community Connections - Coronavirus                 | Phone  | Closed | 12/29/2020 14:13:49 | 12/28/2020 17:26:17 |
| **14-00150037** | Street Light Issue- Multiple poles/multiple streets | Phone  | Closed | 7/21/2014 14:52:20  | 7/21/2014 14:36:47  |
| **14-00181676** | Parking Machine Issue                               | Phone  | Closed | 8/28/2014 10:40:32  | 8/27/2014 11:32:21  |

## Run the example using the user interface

A detailed explanation of [how to start DQOps platform and run the example is described here](../index.md#running-the-use-cases).

### **Navigate to a list of checks**

To navigate to a list of checks prepared in the example using the [user interface](../../dqo-concepts/dqops-user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-duplicate-percent-checks2.png){ loading=lazy; width="1200px" }

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Data quality check editor** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/dqops-user-interface-overview.md#check-editor).


### **Run checks**

Run the activated check using the **Run check** button.

You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

![Run check](https://dqops.com/docs/images/examples/daily-duplicate-percent-run-checks2.png){ loading=lazy; width="1200px" }


### **View detailed check results**

Access the detailed results by clicking the **Results** button. The results should be similar to the one below.

![Duplicate-percent check results](https://dqops.com/docs/images/examples/daily-duplicate-percent-checks-results2.png){ loading=lazy; width="1200px" }

Within the Results window, you will see four categories: **Check results**, **Sensor readouts**, **Execution errors**, and **Error sampling**.
The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
The Sensor readouts category displays the values obtained by the sensors from the data source.
The Execution errors category displays any error that occurred during the check's execution.
The Error sampling category displays examples of invalid values in the column.

The actual value in this example is 0%, which is below the maximum threshold level set in the error (1.0%).
The check gives a correct result (notice the green square to the left of the check name).

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

    ![Turn off scheduler](https://dqops.com/docs/images/examples/turning-off-scheduler.png){ loading=lazy }

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
all tables associated with that connection.

You can [read more about scheduling here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set the maximum percentage threshold level for the check:

- error: 1.0%

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_duplicate_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

```yaml hl_lines="8-17"
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
      monitoring_checks:
        daily:
          uniqueness:
            daily_duplicate_percent:
              error:
                max_percent: 1.0
    source:
      type_snapshot:
        column_type: STRING
        nullable: true
    created_date:
      type_snapshot:
        column_type: TIMESTAMP
        nullable: true
```

In this example, we have demonstrated how to use DQOps to verify the uniqueness of data in a column.
By using the [duplicate_percent](../../checks/column/uniqueness/duplicate-percent.md) column check, we can monitor that
the percentage of duplicate values in a column does not exceed the maximum accepted percentage. If it does, you will get an error result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [profile_duplicate_percent check used in this example, go to the check details section](../../checks/column/uniqueness/duplicate-percent.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md). 
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.
- DQOps provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../../working-with-dqo/collecting-basic-data-statistics.md). 

