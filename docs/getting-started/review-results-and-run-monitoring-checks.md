---
title: Review results from data quality assessment and automatically configure data quality checks
---
# Review results from data quality assessment and automatically configure data quality checks
This guide will show you how to review results from data quality assessment and automatically configure data quality checks to detect the most common data quality issues.
We will run those check and review the first results and data quality issues. 

## Overview

After [adding your first connection, and starting data assessment](add-data-source-connection.md),
we describe how to review the initial results from the basic statistics and profiling checks. Then, by using the DQOps [rule mining engine](../dqo-concepts/data-quality-rule-mining.md), we will automatically
configure (mine) data quality checks to find the most common data quality issues. 

## Review basic statistics results

Basic statistics provides you with summary information about your tables and columns. This information is
valuable in deciding which data quality checks and threshold levels should be set to monitor data quality.

The information from basic statistics is also used by [rule miner](../dqo-concepts/data-quality-rule-mining.md) 
to propose a reasonable configuration of data quality checks.

In the previous step we have collected basic statistics for imported tables and columns. 

To review the results: 

1. Go to the **Profiling** section. 
2. Select the **orders.csv** table on the tree view on the left. 
3. The results are displayed under the **Basic data statistics** tab. 

    You can filter the columns by simply clicking on the sorting icon next to any column header.

    For detailed description of each column go to the [Basics statistics section](../working-with-dqo/collecting-basic-data-statistics.md).

    ![Basic statistics results for orders](https://dqops.com/docs/images/getting-started/orders-statistics1.png){ loading=lazy; width="1200px" }

To view detailed statistics for column, click on the name of the column or navigate to the single column on the tree view.
 
![Basic statistics results for orders - details](https://dqops.com/docs/images/getting-started/orders-column-statistics1.png){ loading=lazy; width="1200px" }

## Propose a configuration of data quality checks using rule mining

Setting up data quality rules and thresholds manually can be time-consuming, especially since most checks will show that everything is okay.
DQOps automates this process with its data quality [rule mining engine](../dqo-concepts/data-quality-rule-mining.md). The engine
analyzes data collected from basic statistics, sample values and results from profiling checks to propose check configurations for detecting common data quality issues.
It also suggests checks that will pass for the current data and serve as a baseline for detecting changes in data distribution over time.

You can find more information about [the data quality rule mining in the concept section](../dqo-concepts/data-quality-rule-mining.md).

### Navigating to the rule miner

To navigate to the data quality rule miner.

1. Select the **Profiling** section
2. Select the table of interest on the tree view on the left.
3. Click on the **Data quality rule mining** tab

![Navigating to rule miner](https://dqops.com/docs/images/getting-started/navigating-to-rule-miner2.png){ loading=lazy; width="1200px" }

The rule mining screen allows you to view and select the automatically proposed configuration of data quality checks.
DQOps proposes a list of data quality checks instantly upon entering the rule mining screen.
If any parameter configuration is changed, clicking the **Propose** button will generate a new proposal.
Use the **Apply** button to save (apply) the proposed check configuration.

The rule miner shows proposed configurations as a list of checks divided by table, columns and check types. 
The column names can be expanded to view the proposed checks. You can change the proposed data quality check
parameters and data quality rule thresholds before applying the proposed configuration. 

You can also customize the rule miner proposals with the following options:

- **Filtering by check category, check name and column name:** You can use these filtering options no narrow down the
list of proposals by specifying check category, check name, or column name.
- **Error rate (% of rows):** The most critical configuration parameter of the rule miner to control its sensitivity.
It represents the percentage of errors that can contain invalid values. By default, it is set to 2%, but you can change this value.
DQOps will attempt to configure data quality checks to detect data quality issues in 2% of records containing the most outstanding values.
- **Default severity level:** This is used to set the initial severity level for proposed checks, which can be later modified individually.
- **Advanced parameters:** These parameters contains checkboxes that allows control how the rule mining engine handles 
    already configured data quality checks and select data quality check categories that you want to include
    in the proposal. More information about [Rule mining parameters](../dqo-concepts/data-quality-rule-mining.md#Rule-mining-parameters). 

### Automated data quality check configuration using rule miner
As an example, let's filter the initial proposition with the column name **gender**.

Click on the **Propose** button to get the filtered proposition.

![Rule miner gender column](https://dqops.com/docs/images/getting-started/rule-mining-proposition-gender-column1.png){ loading=lazy; width="1200px" }

From the list of propositions, we will make the following modifications: 

- In the **Nulls** category, keep only the **profile_null_percent** check with suggested max_percent parameter to value of 0.
- In the **Uniqueness** category, adjust the **profile_distinct_count** check to have a range value of 2 to ensure 2 distinct values (M and F) are present.
- In the **Accepted Values** category, keep the **profile_text_found_in_set_percent** check with the suggested values M and F.
- In the **Text** and **PII** categories, deactivate all checks using the green toggle button.

Click on the **Apply** button to apply the proposed check configuration.

![Rule miner gender column proposition modification](https://dqops.com/docs/images/getting-started/rule-mining-proposition-modifications1.png){ loading=lazy; width="1200px" }

A popup window will appear, notifying you that the checks have been activated, and that you can run activated check by clicking on the **Confirm** button.

![Rule miner proposition - run activated checks](https://dqops.com/docs/images/getting-started/rule-mining-proposition-run-activated-checks1.png){ loading=lazy }

A notification will appear indicating that the job has begun, and the **Table quality status** tab will start flashing.

If desired, you can use the rule miner to activate more checks for table or other columns.

## Review profiling checks results

[**Profiling checks**](../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md) enable you to run more advanced data analyses than
[**Basic data statistics**](../working-with-dqo/collecting-basic-data-statistics.md). Profiling checks are also useful for
exploring and experimenting with various types of checks and determining the most suitable ones for regular data quality monitoring.

In the previous step we have run several profiling checks suggested by the [rule mining](../dqo-concepts/data-quality-rule-mining.md). Now, let's review the results.

### Review Profiling Table quality status

**Table quality status** allows you to quickly evaluate the data quality status of your table.

To navigate to the Table quality status:

1. Click on **Profiling** section
2. Select the **orders.csv** table on the tree view on the left.
3. Click on the **Table quality status** tab

![Profiling Table quality status](https://dqops.com/docs/images/getting-started/profiling-table-quality-status5.png){ loading=lazy; width="1200px" }

This tab provides an overview of the current data quality status of your table. It includes the number of
executed checks, and detailed results per table and columns grouped by check categories.

At the bottom of the screen, you will find a table that displays the check results per category, table, and columns.

The colored boxes indicate the current or the highest severity status: green for a correct result, yellow for a warning,
orange for an error, red for a fatal error, and grey stripes for execution error.

You can click on the colored box to view a list of checks that contribute to the result. Hovering over the check name will provide more details.

In our example, the "gender" column currently has an error severity status, which resulted from the failed checks in the **Accepted values**, **Nulls**, and **Uniqueness** categories.

![Profiling Table quality status - details](https://dqops.com/docs/images/getting-started/profiling-table-quality-status-details2.png){ loading=lazy; width="1200px" }

You can group the results by data quality dimension using the radio button and filter the results by time frame
and severity status.

### Review check results on the Profiling check editor

We can also review more detailed check results and error samples on the **Profiling check editor**.

To navigate to the Profiling checks editor, click on the **gender** column on the tree view on the left
and select the **Profiling checks editor** tab. 

![Profiling checks list](https://dqops.com/docs/images/getting-started/profiling-checks-list6.png){ loading=lazy; width="1200px" }

On the **Profiling checks editor** you can view the list of all table profiling checks. Notice that the toggle button next to the name of the 
default checks activated upon importing new tables has a light green color. If the check has
been activated manually or through the rule miner, the toggle button will have a darker green color.

The icons located before the name of each check allow you to:

- Activate and deactivate it using toggle button. 
- Enable or disable check.
- Configure check settings.
- Get information if the check has activated scheduling.
- [Run a check](../working-with-dqo/run-data-quality-checks.md),
- Review results
- View the check description.

For activated checks, notice a square next to the name indicating the results of the check:

- Green for a correct result.
- Yellow for a warning.
- Orange for an error.
- Red for a fatal error.
- Black for execution error.

You can find more information about the check editor in the [User interface overview section of the documentation](../dqo-concepts/dqops-user-interface-overview.md#Check-editor).

To review the checks results, click the **Results** icon. Let's review the results for the **profile_text_found_in_set_percent**
check in the **Accepted values** category.

![Checking results](https://dqops.com/docs/images/getting-started/checking-results5.png){ loading=lazy; width="1200px" }

A table will appear with more details about the run check. The check displayed an Error result with the actual value of 99.599%, indicating that not
all values in the table are in the expected_values set defined as a check parameter.

### Review the profiling checks results error samples

To assist with identifying the root cause of errors and cleaning up the data, DQOps offers error sampling.
You can view representative examples of data that do not meet the specified data quality criteria by clicking on the
**Error sampling** tab in the results section.

![Checking error samples](https://dqops.com/docs/images/getting-started/checking-error-samples2.png){ loading=lazy; width="1200px" }

We can see that there are four values that do not match the set defined as a check parameter.

For additional information about error sampling, please refer to [the Data Quality Error Sampling documentation](../dqo-concepts/data-quality-error-sampling.md).
You can check there how to configure ID column that will help in identifying error sample or download error samples as a CSV file. 

## Copy the verified profiling check to monitoring checks 
To start monitoring your data using checks configured in the Profiling sections we can copy the configuration to monitoring checks.
[**Monitoring checks**](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md) are standard checks that monitor the data quality of a
table or column. These checks create a single data quality result for the entire table or column. When run multiple times per day, the **daily checks** store only
the most recent result for each day.

To copy the configuration of checks activated in the Profiling section to Monitoring:

1. Navigate to the **Monitoring checks** section.
2. Select the **orders.csv** table from the tree view on the left.
3. Click on the **Copy verified profiling checks** tab

     Here you can see a proposition of checks based on checks that have been activated in the Profiling section.
     To increase the number of propositions, you can either activate more profiling checks manually or use the data quality rule miner in the Profiling section.

     ![Copy verified profiling checks to monitoring](https://dqops.com/docs/images/getting-started/copy-verified-profiling-checks-to-monitoring1.png){ loading=lazy; width="1200px" }

4. Click on the **Apply** button to activate the proposed monitoring checks. 
5. A popup window will appear, notifying you that the checks have been activated, and that you can run activated check by clicking on the **Confirm** button.
6. A notification will appear indicating that the job has begun, and the **Table quality status** tab will start flashing.

### Review Monitoring Table quality status

**Table quality status** allows you to quickly evaluate the data quality status of your table.

To navigate to the monitoring checks Table quality status:

1. Click on **Monitoring** section
2. Select the **orders.csv** table on the tree view on the left.
3. Click on the **Table quality status** tab

![Monitoring Table quality status](https://dqops.com/docs/images/getting-started/monitoring-table-quality-status1.png){ loading=lazy; width="1200px" }

We can see, that the "gender" column currently has an error severity status, which resulted from the failed checks in the **Accepted values**, **Nulls**, and **Uniqueness** categories.

## Run monitoring checks

Now let's run some more monitoring checks, which have been automatically activated upon importing the tables. 

In the **Monitoring checks** section, select the gender column to open the **Data quality check editor**.

![Monitoring check editor](https://dqops.com/docs/images/getting-started/monitoring-check-editor.png){ loading=lazy; width="1200px" }

Notice that the toggle button next to the name of the default checks activated upon importing new tables has a light green color. If the check has
been activated manually or through the rule miner, the toggle button will have a darker green color.

To run a single check, click the **Run check** icon next to its name. To run all activated checks in a category, click the icon on the category row.
To run all activated checks in a table or column, click the icon in the top right corner of the Check Editor table.

![Run monitoring checks](https://dqops.com/docs/images/getting-started/run-monitoring-checks1.png){ loading=lazy; width="1200px" }

You can also run checks for your entire connection, schema, table, or column from the tree view on the left-hand side of
the screen. To do so, click on the three-dot icon and select the Run check option. Check more information how to
[Run checks from a tree view](../working-with-dqo/run-data-quality-checks.md#Run-checks-from-a-tree-view).

Let's execute all the monitoring checks activated in the **demo_connection** by clicking on the three-dot icon and selecting the **Run check** option. 
Click on **Run checks** in the popup.

![Run monitoring checks from three view](https://dqops.com/docs/images/getting-started/run-monitoring-checks-from-three-view1.png){ loading=lazy }

Now you can check the Table status again or check details results as before. 

## View Tables summary

To review the overall health summary of your data, navigate to the Home screen and use the Tables and Columns summaries.
These summaries are based on previously executed monitoring and partition data quality checks, excluding the results from profiling checks.

To access the **Tables summary**, click on the DQOps logo in the top left corner and select the **Data quality summary** tab and Tables subtab.

![Navigating to the Tables summary](https://dqops.com/docs/images/getting-started/table-summary2.png){ loading=lazy; width="1200px" }

You can view a list of tables with measured total data quality KPI and a breakdown of KPIs calculated from executed checks
categorized by specific dimensions (e.g., completeness, validity, consistency). Hovering over the KPIs value will display
a tooltip containing more details.

The calculated KPIs in our example are at 100% because we ran one monitoring check that gave a correct result.

For a deeper dive into [Tables and Columns summaries, see the DQOps concepts section](../dqo-concepts/dqops-user-interface-overview.md#home-screen)

## View Incidents summary

![Navigating to the Incidents summary](https://dqops.com/docs/images/getting-started/incidents-summary2.png){ loading=lazy; width="1200px" }


## Create incident notification

![Open the Accepted values incidents](https://dqops.com/docs/images/getting-started/open-the-accepted-values-incidents1.png){ loading=lazy; width="1200px" }

![Open the incident details](https://dqops.com/docs/images/getting-started/open-the-incident-details1.png){ loading=lazy; width="1200px" }

![Incident details](https://dqops.com/docs/images/getting-started/incident-details1.png){ loading=lazy; width="1200px" }

![Creating notification for the incident](https://dqops.com/docs/images/getting-started/creating-notification-for-the-incident1.png){ loading=lazy }

![Configuring notification for the incident](https://dqops.com/docs/images/getting-started/configure-new-notification-for-the-incident1.png){ loading=lazy; width="1200px" }

## Next step

Now that you have reviewed the initial results from basic statistics and profiling checks and run monitoring checks, 
you can [review the results on the dashboards](review-results-on-dashboards.md).
