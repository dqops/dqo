---
title: How to measure the percentage of integer values in range using a data quality check
---
# How to measure the percentage of integer values in range using a data quality check
This sample shows how to use data quality checks to detect the percentage of integer values in range and view the results on data quality dashboards.

## Overview

This example verifies that the percentage of integer values from a range in a column does not exceed the minimum accepted percentage.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis 
by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

We want to verify the percent of values between 0 ad 1,000,000 in `values` column.

**SOLUTION**

We will verify the data of `bigquery-public-data.america_health_rankings.ahr` using monitoring
[number_in_range_percent](../../checks/column/numeric/number-in-range-percent.md) column check.
Our goal is to verify if the percentage of values in a range in the `values` column does not fall below the set thresholds.

In this example, we will set the minimum percentage threshold level for the check:

- warning: 99.0%

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

**VALUE**

If the percentage of valid values falls below 1.0%, a warning alert will be triggered. 

## Data structure

The following is a fragment of the `bigquery-public-data.america_health_rankings.ahr` dataset. Some columns were omitted for clarity.  
The `value` column of interest contains values in range between 0 and 1,000,000.

| edition | report_type             | measure_name | state_name    | subpopulation | value  |
|:--------|:------------------------|:-------------|:--------------|:--------------|:-------|
| 2021    | 2021 Health Disparities | Able-Bodied  | California    |               | **87** |
| 2021    | 2021 Health Disparities | Able-Bodied  | Colorado      |               | **87** |
| 2021    | 2021 Health Disparities | Able-Bodied  | Hawaii        |               | **87** |
| 2021    | 2021 Health Disparities | Able-Bodied  | Kentucky      |               | **79** |
| 2021    | 2021 Health Disparities | Able-Bodied  | Maryland      |               | **87** |
| 2021    | 2021 Health Disparities | Able-Bodied  | New Jersey    |               | **87** |
| 2021    | 2021 Health Disparities | Able-Bodied  | Utah          |               | **88** |
| 2021    | 2021 Health Disparities | Able-Bodied  | West Virginia |               | **77** |
| 2021    | 2021 Health Disparities | Able-Bodied  | Arkansas      | Female        | **78** |


## Run the example using the user interface

A detailed explanation of [how to start DQOps platform and run the example is described here](../index.md#running-the-use-cases).

### **Navigate to a list of checks**

To navigate to a list of checks prepared in the example using the [user interface](../../dqo-concepts/dqops-user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-values-in-range-numeric-percent-checks2.png){ loading=lazy; width="1200px" }

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Data quality check editor** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/dqops-user-interface-overview.md#check-editor).

    The **[number_in_range_percent](../../checks/column/numeric/number-in-range-percent.md)** column check
    has an additional parameters to select the **min_values** and **max_values** range.


### **Run checks**

Run the activated check using the **Run check** button.

You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

![Run check](https://dqops.com/docs/images/examples/daily-values-in-range-numeric-percent-run-checks2.png){ loading=lazy; width="1200px" }


### **View detailed check results**

Access the detailed results by clicking the **Results** button. The results should be similar to the one below.

![Values-in-range-numeric-percent check results](https://dqops.com/docs/images/examples/daily-values-in-range-numeric-percent-checks-results2.png){ loading=lazy; width="1200px" }

Within the Results window, you will see four categories: **Check results**, **Sensor readouts**, **Execution errors**, and **Error sampling**.
The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
The Sensor readouts category displays the values obtained by the sensors from the data source.
The Execution errors category displays any error that occurred during the check's execution.
The Error sampling category displays examples of invalid values in the column.

The actual value in this example is 100%, which is above the minimum threshold level set in the warning (99%).
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

    ![Turn off job scheduler](https://dqops.com/docs/images/examples/turning-off-scheduler.png){ loading=lazy }

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
all tables associated with that connection.

You can [read more about scheduling here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set the minimum percentage threshold level for the check:

- warning: 99.0%

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_number_in_range_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

```yaml hl_lines="12-24"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    edition:
      type_snapshot:
        column_type: INT64
        nullable: true
    value:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
      monitoring_checks:
        daily:
          numeric:
            daily_number_in_range_percent:
              parameters:
                min_value: 0.0
                max_value: 1000000.0
              warning:
                min_percent: 99.0
```

In this example, we have demonstrated how to use DQOps to verify the reasonability of data in a column.
By using the [number_in_range_percent](../../checks/column/numeric/number-in-range-percent.md) column check, we can monitor that the percentage
of integer values from a range in a column does not exceed the minimum accepted value. If it does, you will get a warning result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [profile_number_in_range_percent check used in this example, go to the check details section](../../checks/column/numeric/number-in-range-percent.md).
- You might be interested in another reasonability check that [evaluates that the length of the string does not exceed the indicated value](../data-reasonability/text-not-exceeding-a-maximum-length.md).
- Would you like to add your own connection? Here you can find [information about supported databases and how to add new connection](../../data-sources/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.
