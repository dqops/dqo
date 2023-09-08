# Running checks with a scheduler

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

5.  [daily_string_value_in_set_percent](../../checks/column/strings/string-value-in-set-percent.md) check on `gender` column with values parameters "F" and "M" and minimum percent thresholds levels
    - warning: 99.0%
    - error: 98.0%
    - fatal: 95.0%

6. [daily_values_in_range_numeric_percent](../../checks/column/numeric/values-in-range-numeric-percent.md) check on `age` column with values parameters "min_value=10" and "max_value=80" and minimum percent thresholds levels
    - warning: 99.0%
    - error: 98.0%
    - fatal: 95.0%

To set a schedule, we will create a connection level schedule that will apply to all enabled checks at 8:00 AM. For two 
checks that require more frequent runs, we will set individual check-level schedules that will override the connection level settings.
The schedule for the daily_values_in_range_numeric_percent check will be temporarily disabled.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).
You can read more about scheduling [here](../../working-with-dqo/schedules/index.md). 

**VALUE**

By setting up a schedule, data can be constantly monitored, and errors can be triggered to alert about potential 
issues with the data quality or operational processes.

## Data structure

The following is a fragment of the `bigquery-public-data.thelook_ecommerce.users` dataset. Some columns were omitted for clarity.  


| id    | first_name | last_name | email                        | age | gender | created_at         |
|:------|:-----------|:----------|:-----------------------------|:----|:-------|:-------------------|
| 1543  | Bob        | Caldwell  | bobcaldwell@example.net      | 61  | M      | 3/9/2021 15:49:00  |
| 2586  | Brittany   | Flores    | brittanyflores@example.org   | 40  | F      | 1/24/2021 1:25:00  |
| 38699 | Christina  | Johnson   | christinajohnson@example.com | 63  | F      | 6/7/2021 6:48:00   |
| 49224 | Latoya     | Bird      | latoyabird@example.com       | 56  | F      | 6/22/2019 5:54:00  |
| 57933 | Charles    | Martin    | charlesmartin@example.net    | 28  | M      | 7/25/2022 8:11:00  |
| 58471 | Amanda     | Collins   | amandacollins@example.com    | 45  | F      | 8/24/2019 4:56:00  |
| 73890 | Jose       | Marsh     | josemarsh@example.net        | 39  | M      | 11/21/2022 6:21:00 |
| 82328 | Diane      | Leonard   | dianeleonard@example.net     | 62  | F      | 12/12/2021 6:47:00 |
| 84680 | Mitchell   | Williams  | mitchellwilliams@example.net | 13  | M      | 3/1/2019 12:36:00  |


## YAML configuration file

The YAML connection configuration file stores data source configurations and allows setting connection-level schedule. 

In the YAML data source configuration file below, the highlighted sections indicate the area where the cron expression 
for the schedule is set to run every day at 8:00 AM (0 8 * * *).

If you want to learn more about cron formatting, please refer to the [Working with DQO section](../../working-with-dqo/schedules/cron-formatting.md).

```yaml hl_lines="9-11"

# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  provider_type: bigquery
  bigquery:
    source_project_id: bigquery-public-data
    authentication_mode: google_application_credentials
  schedules:
    monitoring_daily:
      cron_expression: 0 8 * * *
  incident_grouping:
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60
```

In the YAML table configuration file below, the highlighted section indicate that the table-level `daily_table_availability` 
check is set with schedule_override section. This means that this check will be run every 30 min (*/30 * * * *). 

The `id` column also includes an updated schedule on a daily_distinct_percent check. This check will run 15 min past every hour.
The remaining checks will be run every day at 8:00 AM (0 8 * * *) as indicated in the YAML connection configuration mentioned earlier.
It is important to keep in mind that the daily_values_in_range_numeric_percent check will not be run since the "disabled:"
parameter has been set to "true" in the "schedule_override" section.

```yaml hl_lines="8-19"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
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
                min_percent: 99.0
              error:
                min_percent: 98.0
              fatal:
                min_percent: 95.0
    age:
      type_snapshot:
        column_type: INT64
        nullable: true
      monitoring_checks:
        daily:
          numeric:
            daily_values_in_range_numeric_percent:
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
          strings:
            daily_string_value_in_set_percent:
              parameters:
                values:
                - F
                - M
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
    street_address:
      type_snapshot:
        column_type: STRING
        nullable: true
    postal_code:
      type_snapshot:
        column_type: STRING
        nullable: true
    city:
      type_snapshot:
        column_type: STRING
        nullable: true
    country:
      type_snapshot:
        column_type: STRING
        nullable: true
    latitude:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
    longitude:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
    traffic_source:
      type_snapshot:
        column_type: STRING
        nullable: true
    created_at:
      type_snapshot:
        column_type: TIMESTAMP
        nullable: true
```


## Evaluating the schedule settings using the graphical interface

To evaluate schedule settings that have been configured in YAML files open the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md)

To view the connection-level schedule:

1. Go to the Data Source section and select the "thelook_ecommerce" connection from the tree view on the left. 

2. In the main workspace select the **Schedule** tab and the **Monitoring Daily** tab. 
    Here, you can see that a schedule has been set to "Run every day at 8:00".

    ![Navigating to a connection-level schedule](https://dqops.com/docs/images/examples/running-check-with-a-scheduler-navigating-to-connection-level-schedule.png)

In the example, we want to verify the availability of the `users` table every 30 minutes using the **daily_table_availability** check.
DQO allows to set check-level schedules and override the connection level settings.

To view and modify individual check-level schedules:

1. Go to the section with a list of checks. In our example, we have set monitoring checks, so go to the **Monitoring Checks** section.

2. In the main workspace select the table "users" from the tree view on the left. 

3. Click on the **Settings** icon to the left of the daily_table_availability check name. 

    ![Navigating to a check-level schedule](https://dqops.com/docs/images/examples/running-check-with-a-scheduler-navigating-to-check-level-schedule.png)

4. Select the **Schedule Override** tab to see that the check-level schedule is set to run every 30 minutes.

    ![Check-level schedule override](https://dqops.com/docs/images/examples/running-check-with-a-scheduler-check-level-schedule-override.png)


## Starting a scheduler using the graphical interface

To start a scheduler using the graphical interface, simply select the **Notification** icon in the upper right corner and
toggle the button next to the **Jobs scheduler**.

![Starting scheduler](https://dqops.com/docs/images/examples/running-check-with-a-scheduler-starting-scheduler.png)

The scheduler has been initiated and will run checks based on the set schedules. Data synchronization will
take place every 10 minutes.

## Starting a scheduler using DQO Shell

To initiate a scheduler in the DQO Shell, simply enter the command `scheduler start`. To stop the scheduler, use the
command `scheduler stop`.
For further information on the `scheduler` commands, please refer to the [Command-line interface section](../../command-line-interface/scheduler.md).

Scheduler can also be started in a server mode that continuously run a job scheduler and synchronize the data every 10 minutes.
To do this, simply enter the command below in your terminal:
```
$ dqo run
```
To terminate dqo running in the background, simply use the Ctrl+C.

For more information on the `run` command, please refer to the [Command-line interface section](../../command-line-interface/run.md).



## Running the checks in the example and evaluating the results

The detailed explanation of how to run the example is described [here](../#running-the-examples). 

Even if we have started scheduler we can run all the checks to verify the results. 
To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```

Review the results which should be similar to the one below.
Results from all checks appear to be valid except for one that indicates a fatal error.


```
Check evaluation summary per table:
+-----------------+-----------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection       |Table                  |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------+-----------------------+------+--------------+-------------+--------+------+------------+----------------+
|thelook_ecommerce|thelook_ecommerce.users|6     |6             |5            |0       |0     |1           |0               |
+-----------------+-----------------------+------+--------------+-------------+--------+------+------------+----------------+
```

For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the 
following command, as in the other examples:

```
check run --mode=debug
```

## Next step
Now that you have set up a schedule and get first results, you can evaluate them on dashboards. 
You can find instructions on how to do this [here](../../getting-started/review-results-on-dashboards/review-results-on-dashboards.md).