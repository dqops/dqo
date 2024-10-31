
# How to detect data type changes in the landing zone tables using a data quality check

This sample shows how to use data quality checks to detect data type changes in the landing zone tables using a data quality check.

## Overview

This example shows how to verify that the data type in a column in the landing zone tables has not changed.

**PROBLEM**

We will be testing [House Price Prediction Treated](https://www.kaggle.com/datasets/aravinii/house-price-prediction-treated-dataset) dataset.
This dataset contains house sale prices for King County, which includes Seattle. It includes homes sold between May 2014 and May 2015.

In the **date** column, which contains the date of sale of the house, we want to check whether the data type does not change, e.g. by the appearance of a row in the datetime format.

**SOLUTION**

We will verify the data using monitoring [detected_datatype_in_text_changed](../../checks/column/datatype/detected-datatype-in-text-changed.md) column check.
Our goal is to verify whether the data type in the `date` column has changed.

In this example, we will set one threshold level for the check:

- error

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

**VALUE**

f the data type in the `date` column changes, an error alert will be triggered.

## Data structure

The following is a fragment of the `house-price-prediction-treated-dataset` dataset

| date     |price   | bedrooms |grade|has_basement|living_in_m2|renovated|nice_view|perfect_condition|real_bathrooms|has_lavatory|single_floor|month|quartile_zone|
|----------|--------|---------|-----|------------|------------|---------|---------|-----------------|--------------|------------|------------|-----|-------------|
| 2014-09-26|305000.0| 2       |1    |False       |76.18046    |False    |False    |True             |1             |False       |True        |9    |2            |
| 2014-05-14|498000.0| 3       |2    |True        |210.88981   |False    |False    |False            |2             |True        |True        |5    |2            |
| 2015-03-23|590000.0| 2       |4    |False       |262.91549   |False    |False    |False            |2             |True        |False       |3    |2            |
| 2014-07-15|775000.0| 3       |3    |False       |159.79316   |False    |False    |False            |1             |True        |False       |7    |3            |
| 2015-04-14|350000.0| 2       |1    |False       |92.903      |False    |False    |False            |1             |True        |True        |4    |3            |
| 2015-05-08|399000.0| 2       |2    |False       |201.59951   |False    |False    |False            |1             |True        |True        |5    |1            |
| 2014-10-15|325000.0| 1       |2    |False       |94.76106    |False    |False    |False            |1             |True        |False       |10   |3            |
| 2014-07-15|250000.0| 3       |2    |True        |177.44473   |False    |False    |False            |2             |True        |True        |7    |1            |
| 2015-02-25|445000.0| 3       |2    |False       |360.46364   |False    |False    |False            |3             |False       |False       |2    |2            |

In the landing table, the data in all 14 columns is defined as **STRING**, which we can check by selecting the selected column from the **tree view** on the left or by navigating to **Basic data statistic** in the **Profiling tab**.

To navigate to a **Basic data statistic** in the example using the [user interface](../../dqo-concepts/dqops-user-interface-overview.md):

![Navigating to the Basic data statistics](https://dqops.com/docs/images/examples/navigating-to-the-basic-data-statistic-detect-datatype-in-text-changed2.png){ loading=lazy; width="1200px" }

1. Go to the **Profiling** section.
  

2. Select the table or column mentioned in the example description from the **tree view** on the left.

   On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Basic data statistic** tab.

   This tab allows you to collect basic statistics about data sources during the data profiling stage. To do this, click the **Collect statistics** button in the upper right corner.

## Run the example using the user interface

A detailed explanation of [how to start DQOps platform and run the example is described here](../index.md#running-the-use-cases).

### **Navigate to a list of checks**

To navigate to a list of checks prepared in the example using the [user interface](../../dqo-concepts/dqops-user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-checks-detect-datatype-in-text-changed2.png){ loading=lazy; width="1200px" }

1. Go to the **Monitoring** section.

   The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Data quality checks editor** tab.

   This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/dqops-user-interface-overview.md#check-editor).

### **Run checks**

Run the activated check using the **Run check** button.

You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

![Run check](https://dqops.com/docs/images/examples/detect-datatype-in-text-changed-run-check2.png){ loading=lazy; width="1200px" }


### **View detailed check results**

Access the detailed results by clicking the **Results** button. The results should be similar to the one below.

![Schema detection check results](https://dqops.com/docs/images/examples/detect-datatype-in-text-changed-check-results2.png){ loading=lazy; width="1200px" }

Within the Results window, you will see four categories: **Check results**, **Sensor readouts**, **Execution errors**, and **Error sampling**.
The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
The Sensor readouts category displays the values obtained by the sensors from the data source.
The Execution errors category displays any error that occurred during the check's execution.
The Error sampling category displays examples of invalid values in the column.

The check in the example gives the correct result (notice the green square to the left of the check's name).

### **Add a row to the table containing a different data type**

Now we will add the new row with a different datatype in the `date` column, so when we run the checks again we can see the error alerts.

| date                    |price   |bedrooms|grade|has_basement|living_in_m2|renovated|nice_view|perfect_condition|real_bathrooms|has_lavatory|single_floor|month|quartile_zone|
|-------------------------|--------|--------|-----|------------|------------|---------|---------|-----------------|--------------|------------|------------|-----|-------------|
| **2014-09-26 00:00:00** |305000.0|1       |1    |False       |76.18046    |False    |False    |True             |1             |False       |True        |9    |2            |


### **Evaluate results after changing the data type**

Because the daily checks stores the most recent result for
each day when the data quality check was evaluated, the checks were run on the next day.

![Schema detection check results day after](https://dqops.com/docs/images/examples/detect-datatype-in-text-changed-check-results-after-evaluation2.png){ loading=lazy; width="1200px" }

- for the `date` column where we have added the row that changed the data type, we can see an error alert in the `daily_detected_datatype_in_text_changed` check.

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

The highlighted fragments in the YAML file below represent the segment where the detect data type changes check are configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

```yaml hl_lines="12-16"
apiVersion: dqo/apiVersion: dqo/v1
kind: table
spec:
   incremental_time_window:
      daily_partitioning_recent_days: 7
      monthly_partitioning_recent_months: 1
   columns:
      date:
         type_snapshot:
            column_type: STRING
            nullable: true
         monitoring_checks:
            daily:
               datatype:
                  daily_detected_datatype_in_text_changed:
                     error: {}
      price:
         type_snapshot:
            column_type: STRING
            nullable: true
```
In this example, we have demonstrated how to use DQOps to verify the consistency of data in a column.
By using the [detected_datatype_in_text_changed](../../checks/column/datatype/detected-datatype-in-text-changed.md) column check,
we can monitor whether the data type in the column has not changed.
If it does, you will get an error result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [table schema checks used in this example, go to the check details section](../../checks/table/schema/column-count.md).
- DQOps provides you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../../working-with-dqo/collecting-basic-data-statistics.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.
