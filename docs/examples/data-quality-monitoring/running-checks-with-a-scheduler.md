# Running checks with a scheduler

## Overview

In this example, you will learn how to set different schedules on multiple checks.

**PROBLEM**

[TheLook](https://console.cloud.google.com/marketplace/product/bigquery-public-data/thelook-ecommerce) is a fictitious 
eCommerce clothing dataset developed by Looker team. The dataset contains information about customers, products, orders,
logistics, web events and digital marketing campaigns.

To make better use of that data, and accurately predict operational issues so that we can resolve them before they 
negatively impact our business, we want to monitor various data quality aspects of the dataset: 

- the availability of the `users` table every 30 minutes,
- the uniqueness of customer IDs in the `id` column every 15 minutes past the hour,
- the uniqueness and validity of customer email addresses in the `email` column daily at 8:00 AM
- the gender (F or M) in the `gender` column daily at 8:00 AM
- the age to be within the 10-80 range in the `age` column daily at 8:00 AM (set but temporarily disabled).

**SOLUTION**

We will set six data quality monitoring checks on `bigquery-public-data.thelook_ecommerce.users` dataset:

1. [daily_table_availability](../../checks/table/availability/table-availability.md) check on `users` table with max failures thresholds levels:
    - warning: 1
    - error: 5
    - fatal: 10

2. [daily_distinct_percent](../../checks/column/uniqueness/distinct-percent.md) check on `id` column with minimum percent thresholds levels
    - warning: 99.0%
    - error: 98.0%
    - fatal: 95.0%

3. [daily_distinct_percent](../../checks/column/uniqueness/distinct-percent.md) check on `email` column with minimum percent thresholds levels
    - warning: 99.0%
    - error: 98.0%
    - fatal: 95.0%

4. [daily_contains_email_percent](../../checks/column/pii/contains-email-percent.md) check on `email` column with minimum percent thresholds levels
    - warning: 99.0%
    - error: 98.0%
    - fatal: 95.0%

5.  [daily_text_found_in_set_percent](../../checks/column/accepted_values/text-found-in-set-percent.md) check on `gender` column with values parameters "F" and "M" and minimum percent thresholds levels
    - warning: 99.0%
    - error: 98.0%
    - fatal: 95.0%

6. [daily_number_in_range_percent](../../checks/column/numeric/number-in-range-percent.md) check on `age` column with values parameters "min_value=10" and "max_value=80" and minimum percent thresholds levels
    - warning: 99.0%
    - error: 98.0%
    - fatal: 95.0%

We will modify the default connection level schedule that apply to all activated checks from 12:00 AM to 8:00 AM. For two 
checks that require more frequent runs, we will set individual check-level schedules that will override the connection level settings.
The schedule for the [daily_number_in_range_percent](../../checks/column/numeric/number-in-range-percent.md) check will be temporarily disabled.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).
You can read more about scheduling [here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md). 

**VALUE**

By setting up a schedule, data can be constantly monitored, and errors can be triggered to alert about potential 
issues with the data quality or operational processes.


## Change the schedule settings using the user interface

### **Evaluate and change the default schedule settings**

To evaluate schedule settings open the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md)

To view the connection-level schedule:

1. Go to the **Data Source** section and select the "thelook_ecommerce" connection from the tree view on the left. 

2. In the main workspace select the **Schedule** tab and the **Monitoring Daily** tab. 
    Here, you can see that a schedule has been set to **Run every day at 12:00**.

    ![Navigating to a connection-level schedule](https://dqops.com/docs/images/examples/running-check-with-a-scheduler-navigating-to-connection-level-schedule1.png)


In the example, we want to change the default schedule configration for Monitoring checks to 8:00 AM. 
To do this simply change the value in the **Run every day at** and click the **Save** button.

![Changing the default schedule for daily monitoring](https://dqops.com/docs/images/examples/running-check-with-a-scheduler-changing-the-default-schedule.png)


### **Set the check-level schedule**

We also wanted to verify the availability of the `users` table every 30 minutes using the table-level
[daily_table_availability](../../checks/table/availability/table-availability.md) check. DQOps allows to set check-level
schedules and override the connection level settings.

To view and modify individual check-level schedules:

![Navigating to a check-level schedule](https://dqops.com/docs/images/examples/running-check-with-a-scheduler-navigating-to-check-level-schedule1.png)

1. Go to the section with a list of checks. In our example, we have set monitoring checks, so go to the **Monitoring Checks** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.

2. Select the table `users` from the tree view on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).

3. Select the **Daily checks** tab on the check editor.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/user-interface-overview/user-interface-overview.md#check-editor).

4. Find the [daily_table_availability](../../checks/table/availability/table-availability.md) check in the Availability group, and click on the **Settings** icon.

    ![Access the check-level schedule settings](https://dqops.com/docs/images/examples/running-check-with-a-scheduler-access-check-level-settings.png)

5. Select the **Schedule Override** tab and change the option from
   **Use scheduling configuration from the connection levels** to **Run every 30 minutes**. Click the **Save** button to save the changes.

    ![Check-level schedule override](https://dqops.com/docs/images/examples/running-check-with-a-scheduler-check-level-schedule-override1.png)


Similarly, we change the schedule of the uniqueness check [daily_distinct_percent](../../checks/column/uniqueness/distinct-percent.md)
activated on the `id` column for **Run 15 minutes past every hour**.

![Check-level schedule override on daily-distinct-percent check](https://dqops.com/docs/images/examples/running-check-with-a-scheduler-check-level-schedule-override2.png)

### **Disable check-level schedule**

We wanted to disable the scheduling of the [daily_number_in_range_percent](../../checks/column/numeric/number-in-range-percent.md)
check activated on the `age` column.

To disable the check scheduling:

1. Select the `age` column from the tree view

2. Access the settings of the [daily_number_in_range_percent](../../checks/column/numeric/number-in-range-percent.md)
   check and select the **Schedule override** tab.

3. Check the **Disable schedule** checkbutton and click the **Save** button.

    ![Disable check-level schedule](https://dqops.com/docs/images/examples/running-check-with-a-scheduler-disable-check-level-schedule.png)

## Start and stop a scheduler

In DQOps, the scheduler is started as a default. Data synchronization take place every 10 minutes.

To start or stop the scheduler use the user interface. Simply switch on or off the **Jobs scheduler** using the button in the Notifications window
on the [right upper right corner of the navigation bar](../../dqo-concepts/user-interface-overview/user-interface-overview.md).

![Start and stop a scheduler](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/notification-panel.png)


## YAML configuration file

The YAML connection configuration file stores data source configurations and allows setting connection-level schedule.

In the YAML data source configuration file below, the highlighted sections indicate the area where the cron expression
for the **Daily monitoring** schedule is set to run every day at 8:00 AM (0 8 * * *).

If you want to learn more about cron formatting, please refer to the [Working with DQOps section](../../working-with-dqo/configure-scheduling-of-data-quality-checks/cron-formatting.md).

```yaml hl_lines="13-14"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  provider_type: bigquery
  bigquery:
    source_project_id: bigquery-public-data
    jobs_create_project: create_jobs_in_default_project_from_credentials
    authentication_mode: google_application_credentials
  schedules:
    profiling:
      cron_expression: 0 12 * * *
    monitoring_daily:
      cron_expression: 0 8 * * *
    monitoring_monthly:
      cron_expression: 0 12 * * *
    partitioned_daily:
      cron_expression: 0 12 * * *
    partitioned_monthly:
      cron_expression: 0 12 * * *
  incident_grouping:
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60
```

In the YAML table configuration file below, the first highlighted section indicate that the table-level [daily_table_availability](../../checks/table/availability/table-availability.md)
check is set with schedule_override section. This means that this check will be run every 30 min (*/30 * * * *).

The `id` column also includes an updated schedule on a [daily_distinct_percent](../../checks/column/uniqueness/distinct-percent.md) check. This check will run 15 min past every hour.

The remaining monitoring checks will be run every day at 8:00 AM (0 8 * * *) as indicated in the YAML connection configuration mentioned earlier.

The [daily_number_in_range_percent](../../checks/column/numeric/number-in-range-percent.md) check activated
on the `age` column will not be run since the "disabled:" parameter has been set to "true" in the "schedule_override" section.

```yaml hl_lines="10-13 27-30 73-76"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  monitoring_checks:
    daily:
      availability:
        daily_table_availability:
          schedule_override:
            cron_expression: '*/30 * * * *'
          warning:
            max_failures: 1
          error:
            max_failures: 5
          fatal:
            max_failures: 10
  columns:
    id:
      type_snapshot:
        column_type: INT64
        nullable: true
      monitoring_checks:
        daily:
          uniqueness:
            daily_distinct_percent:
              schedule_override:
                cron_expression: 15 * * * *
              warning:
                min_percent: 99.0
              error:
                min_percent: 98.0
              fatal:
                min_percent: 95.0
    first_name:
      type_snapshot:
        column_type: STRING
        nullable: true
    last_name:
      type_snapshot:
        column_type: STRING
        nullable: true
    email:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          uniqueness:
            daily_distinct_percent:
              warning:
                min_percent: 99.0
              error:
                min_percent: 98.0
              fatal:
                min_percent: 95.0
          pii:
            daily_contains_email_percent:
              warning:
                max_percent: 0.0
              error:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
    age:
      type_snapshot:
        column_type: INT64
        nullable: true
      monitoring_checks:
        daily:
          numeric:
            daily_number_in_range_percent:
              schedule_override:
                disabled: true
              parameters:
                min_value: 10.0
                max_value: 80.0
              warning:
                min_percent: 99.0
              error:
                min_percent: 98.0
              fatal:
                min_percent: 95.0
    gender:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          accepted_values:
            daily_text_found_in_set_percent:
              warning:
                min_percent: 99.0
              error:
                min_percent: 98.0
              fatal:
                min_percent: 95.0
    state:
      type_snapshot:
        column_type: STRING
        nullable: true
```


## Starting a scheduler using the user interface

To start a scheduler using the user interface, simply select the **Notification** icon in the upper right corner and
toggle the button next to the **Jobs scheduler**.

![Starting scheduler](https://dqops.com/docs/images/examples/running-check-with-a-scheduler-starting-scheduler.png)

The scheduler has been initiated and will run checks based on the set schedules. Data synchronization will
take place every 10 minutes.


## Next step
- Now that you have set up a schedule and get first results, you can evaluate them on dashboards. 
  You can find instructions on how to do this [here](../../getting-started/review-results-on-dashboards.md).
- With DQOps, you can easily customize when the checks are run at the level of the entire connection, table, or individual check. [Learn more about how to set schedules here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md). 
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you to calculate separate data quality KPI scores for different groups of rows.