---
title: How to measure the percentage of rows with a text found in a set using a data quality check
---
# How to measure the percentage of rows with a text found in a set using a data quality check
This sample shows how to use data quality checks to detect the percentage of rows with a text found in a set and view the results on data quality dashboards.

## Overview

Verifies that the percentage of text values from a set in a column does not fall below the minimum accepted percentage.

**PROBLEM**

We will be testing [Student Performance](https://www.kaggle.com/datasets/whenamancodes/student-performance) dataset. 
This data approach student achievement in secondary education of two Portuguese schools. 
The data attributes include student grades, demographic, social and school related features, and it was collected using school reports and questionnaires. 
Two datasets are provided regarding the student's performance in mathematics. 

In the `Fjob` column, which contains information about the student's father's job, we want to track the number of rows 
with values outside specified set.

**SOLUTION**

We will verify the data using monitoring [text_found_in_set_percent](../../checks/column/accepted_values/text-found-in-set-percent.md) column check.
Our data quality check will compare the values in the tested column to a set of accepted values. We accept only
`services`, `at_home`,and  `teacher` values.

The SQL query that will be executed use will use the following IN SQL clause:

```sql
SELECT
      100.0 * SUM(
            CASE
                WHEN analyzed_table.`Fjob` IN ('services', 'at_home', 'teacher')
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
FROM `dqo-ai-testing`.`kaggle_student_performance`.`maths` AS analyzed_table
```

In this example, we will set the minimum percent thresholds levels for the check (a minimum accepted percentage of valid rows):

- error: 98%

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

**VALUE**

If the percent of text values from a set fall below 98%, an error alert will be triggered.

## Data structure

The following is a fragment of the [Student Performance](https://www.kaggle.com/datasets/whenamancodes/student-performance).
Some columns were omitted for clarity.  
The `Fjob` column of interest contains information about student's father job.

| Medu | Fedu | Mjob     | Fjob         | reason     | guardian | traveltime | studytime |
|:-----|:-----|:---------|:-------------|:-----------|:---------|:-----------|:----------|
| 4    | 4    | services | **at_home**  | course     | mother   | 1          | 3         |
| 3    | 3    | other    | **other**    | course     | other    | 2          | 1         |
| 4    | 3    | teacher  | **services** | course     | father   | 2          | 4         |
| 3    | 2    | health   | **services** | home       | father   | 1          | 2         |
| 4    | 4    | teacher  | **teacher**  | course     | mother   | 1          | 1         |
| 3    | 2    | services | **at_home**  | home       | mother   | 1          | 1         |
| 2    | 2    | other    | **other**    | home       | mother   | 1          | 2         |
| 1    | 3    | at_home  | **services** | home       | mother   | 1          | 2         |
| 1    | 1    | at_home  | **other**    | reputation | mother   | 1          | 3         |
| 4    | 3    | teacher  | **other**    | course     | mother   | 1          | 1         |
| 2    | 1    | other    | **other**    | course     | other    | 2          | 3         |
| 2    | 2    | services | **services** | course     | father   | 1          | 4         |
| 2    | 2    | at_home  | **services** | home       | mother   | 1          | 3         |
| 3    | 3    | services | **services** | home       | mother   | 1          | 2         |
| 2    | 2    | other    | **other**    | home       | other    | 1          | 2         |


## Run the example using the user interface

A detailed explanation of [how to start DQOps platform and run the example is described here](../index.md#running-the-use-cases).

### **Navigate to a list of checks**

To navigate to a list of checks prepared in the example using the [user interface](../../dqo-concepts/dqops-user-interface-overview.md):


![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-string-in-set-percent-checks2.png){ loading=lazy; width="1200px" }

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checkpoints** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/dqops-user-interface-overview.md#check-editor).

    The **daily_text_found_in_set_percent** check has an additional parameter to select the **expected_values** that must
    be present in a string column. In our example, these values are `services`, `at_home` and `teacher`.


### **Run checks**

Run the activated check using the **Run check** button.

You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

![Run check](https://dqops.com/docs/images/examples/daily-string-in-set-percent-run-check2.png){ loading=lazy; width="1200px" }


### **View detailed check results**

Access the detailed results by clicking the **Results** button. The results should be similar to the one below.

![String-in-set-percent check results](https://dqops.com/docs/images/examples/daily-string-in-set-percent-check-results2.png){ loading=lazy; width="1200px" }

Within the Results window, you will see four categories: **Check results**, **Sensor readouts**, **Execution errors**, and **Error sampling**.
The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
The Sensor readouts category displays the values obtained by the sensors from the data source.
The Execution errors category displays any error that occurred during the check's execution.
The Error sampling category displays examples of invalid values in the column.

The actual value in this example is 40%, which is below the minimum threshold level set in the error (98%).
The check gives an error result (notice the orange square to the left of the check name).


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

    ![Turn off job scheduler](https://dqops.com/docs/images/examples/turning-off-scheduler.png)

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
all tables associated with that connection.

You can [read more about scheduling here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set the minimum percent threshold level for the check:

- error: 98%

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_text_found_in_set_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

```yaml hl_lines="12-26"
apiVersion: dqo/v1
kind: table
spec:
   incremental_time_window:
      daily_partitioning_recent_days: 7
      monthly_partitioning_recent_months: 1
   columns:
      school:
         type_snapshot:
            column_type: STRING
            nullable: true
      Fjob:
         type_snapshot:
            column_type: STRING
            nullable: true
         monitoring_checks:
            daily:
               accepted_values:
                  daily_text_found_in_set_percent:
                     parameters:
                        expected_values:
                           - services
                           - at_home
                           - teacher
                     error:
                        min_percent: 98.0
```

In this example, we have demonstrated how to use DQOps to verify the consistency of data in a column.
By using the [text_found_in_set_percent](../../checks/column/accepted_values/text-found-in-set-percent.md) column check,
we can monitor that the percentage of string values from a set in a column does not fall below the minimum accepted percentage.
If it does, you will get an error result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [text_found_in_set_percent check used in this example, go to the check details section](../../checks/column/accepted_values/text-found-in-set-percent.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).
- Would you like to add your own connection? Here you can find [information about supported databases and how to add new connection](../../data-sources/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.
