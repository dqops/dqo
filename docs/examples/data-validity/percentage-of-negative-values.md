---
title: How to Measure Percentage of Negative Values with Data Quality Checks
---
# How to Measure Percentage of Negative Values with Data Quality Checks
This sample shows how to use data quality checks to measure the percentage of negative values in a column and view the results on data quality dashboards.

## Overview

The following example shows how to verify that the percentage of negative values in a column does not exceed the maximum accepted percentage.

**PROBLEM**

[Countries in the world by population](https://www.worldometers.info/world-population/population-by-country/) provides an analysis of national health on a state-by-state basis by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

Population rise is currently a major subject of discussion nowadays. Every day, there is higher birth rate recorded as compare to death rate which is quite alarming for the world.
Below is the complete data about the world population , country by country (235 countries).

Worldometer was voted as one of the best free reference websites by the American Library Association (ALA), the oldest and largest library association in the world. 
Worldometer is a provider of global COVID-19 statistics for many caring people around the world. 
Worldometr's data is also trusted and used by the UK Government, Johns Hopkins CSSE, the Government of Thailand, the Government of Pakistan, the Government of Sri Lanka, Government of Vietnam and many others.

We want to verify the percentage of negative values on `Migrants__net_` column.

**SOLUTION**

We will verify the data using monitoring [negative_values_percent](../../checks/column/numeric/negative-values-percent.md) column check.
Our goal is to verify that the percent of negative values in the `Migrants__net_` column does not exceed the set thresholds.

In this example, we will set the maximum percentage threshold level for the check:

- warning: 50.0

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

**VALUE**

If the percentage of negative values exceed 50.0, a warning alert will be triggered.

## Data structure

The following is a fragment of the World population dataset. Some columns were omitted for clarity.  
The `Migrants__net` column of interest contains negative values.

| Country__or_dependency_ | Population__2022_ | Yearly_change | Net_change | Density__P_Km___ | Land_Area__Km___ | Migrants__net_ |
|:------------------------|:------------------|:--------------|:-----------|:-----------------|:-----------------|:---------------|
| Mali                    | 20250833          | 0.0302        | 592802     | 17               | 1220190          | **-40000**     |
| DR Congo                | 89561403          | 0.0319        | 2770836    | 40               | 2267050          | **23861**      |
| Uganda                  | 45741007          | 0.0332        | 1471413    | 229              | 199810           | **168694**     |
| Angola                  | 32866272          | 0.0327        | 1040977    | 26               | 1246700          | **6413**       |
| Chad                    | 16425864          | 0.03          | 478988     | 13               | 1259200          | **2000**       |
| Somalia                 | 15893222          | 0.0292        | 450317     | 25               | 627340           | **-40000**     |
| Burundi                 | 11890784          | 0.0312        | 360204     | 463              | 25680            | **2001**       |
| Nigeria                 | 206139589         | 0.0258        | 5175990    | 226              | 910770           | **-60000**     |
| Tanzania                | 59734218          | 0.0298        | 1728755    | 67               | 885800           | **-40076**     |


## Run the example using the user interface

A detailed explanation of [how to start DQOps platform and run the example is described here](../index.md#running-the-use-cases).

### **Navigate to a list of checks**

To navigate to a list of checks prepared in the example using the [user interface](../../dqo-concepts/dqops-user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-negative-percent-checks2.png){ loading=lazy; width="1200px" }

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checkpoints** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/dqops-user-interface-overview.md#check-editor).


### **Run checks**

Run the activated check using the **Run check** button.

You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

![Run check](https://dqops.com/docs/images/examples/daily-negative-percent-run-checks2.png){ loading=lazy; width="1200px" }


### **View detailed check results**

Access the detailed results by clicking the **Results** button. The results should be similar to the one below.

![Negative-percent check results](https://dqops.com/docs/images/examples/daily-negative-percent-checks-results2.png){ loading=lazy; width="1200px" }

Within the Results window, you will see four categories: **Check results**, **Sensor readouts**, **Execution errors**, and **Error sampling**.
The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
The Sensor readouts category displays the values obtained by the sensors from the data source.
The Execution errors category displays any error that occurred during the check's execution.
The Error sampling category displays examples of invalid values in the column.

The actual value in this example is 56%, which is above the maximum threshold level set in the warning (50.0%).
The check gives a warning result (notice the yellow square to the left of the check name).


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

4. Select the **Monitoring** tab

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

- warning: 50.0

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_negative_values_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

```yaml hl_lines="36-41"
apiVersion: dqo/v1
kind: table
spec:
   incremental_time_window:
      daily_partitioning_recent_days: 7
      monthly_partitioning_recent_months: 1
   columns:
      Country__or_dependency_:
         type_snapshot:
            column_type: STRING
            nullable: true
      Population__2022_:
         type_snapshot:
            column_type: INT64
            nullable: true
      Yearly_change:
         type_snapshot:
            column_type: FLOAT64
            nullable: true
      Net_change:
         type_snapshot:
            column_type: INT64
            nullable: true
      Density__P_Km___:
         type_snapshot:
            column_type: INT64
            nullable: true
      Land_Area__Km___:
         type_snapshot:
            column_type: INT64
            nullable: true
      Migrants__net_:
         type_snapshot:
            column_type: INT64
            nullable: true
         monitoring_checks:
            daily:
               numeric:
                  daily_negative_values_percent:
                     warning:
                        max_percent: 50.0
      Fert__Rate:
         type_snapshot:
            column_type: STRING
            nullable: true
```

In this example, we have demonstrated how to use DQOps to verify the validity of data in a column.
By using the [negative_values_percent](../../checks/column/numeric/negative-values-percent.md) column check, we can monitor that
the percentage of negative values in a column does not exceed the maximum accepted percentage. If it does, you will get a warning result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [negative_values_percent check used in this example, go to the check details section](../../checks/column/numeric/negative-values-percent.md).
- You might be interested in another validity check that [evaluates that a minimum percentage of rows passed a custom SQL condition (expression)](./percentage-of-rows-passing-sql-condition.md).
- With DQOps, you can easily customize when the checks are run at the level of the entire connection, table, or individual check. [Learn more about how to set schedules here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.