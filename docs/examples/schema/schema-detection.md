# Schema detection

## Overview

The following example shows how to detect changes in the schema using DQOps schema change monitoring. 

**PROBLEM**

Even the small schema changes can have a big impact on your pipelines and data. 

Typical table schema change issues:

- adding or removing columns,
- changing the order of columns,
- renaming column,
- changing the column type.

In this example, we will use DQOps checks that can automatically detect typical schema change issues described above.


**SOLUTION**

We will verify the data using the following monitoring checks design for detecting schema changes. 

| Check name                                                                                      | Description                                                                                                                                                                                                                                                                                                                                                                   |
|:------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [daily_column_count](../../checks/table/schema/column-count.md)                                 | Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).                                                                                                                                                                 |
| [daily_column_count_changed](../../checks/table/schema/column-count-changed.md)                 | Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.                                                                                                     |
| [daily_column_list_changed](../../checks/table/schema/column-list-changed.md)                   | Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.                                                                                                  |
| [daily_column_list_or_order_changed](../../checks/table/schema/column-list-or-order-changed.md) | Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.                                                         |
| [daily_column_types_changed](../../checks/table/schema/column-types-changed.md)                 | Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types. |

If you want to learn more about checks, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the number or order of columns in a table changes, an error alert will be triggered. Also, renaming a column or changing its type will trigger an error alert.

## Data structure

The `bigquery-public-data.example_dataset` dataset which we use in the example contains four tables with the following five columns:

| Column name  | Column type |
|:-------------|:------------|
| product_id   | STRING      |
| name         | STRING      |
| price        | FLOAT       |
| date_created | DATETIME    |
| date_updated | DATETIME    |

## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-schema-detection-checks.png)

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. We can see a schema-detection connection which
    contains four tables `product1`, `product2`, `product3`, and `product4`. Select the `product1` table. 

    Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/user-interface-overview/user-interface-overview.md#check-editor).


4. Run the activated check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.
    You can run all checks for the connection by clicking on the three dots icon next to the connection name in the tree view, and selecting the **Run checks** option.

    ![Run check](https://dqops.com/docs/images/examples/schema-detection-run-checks.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
    The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
    The Sensor readouts category displays the values obtained by the sensors from the data source.
    The Execution errors category displays any error that occurred during the check's execution.

    Review the results which should be similar to the one below.

    ![Schema detection check results](https://dqops.com/docs/images/examples/schema-detection-checks-results.png)
  
    All the checks in the example gives valid results (notice the green square to the left of the checks name).

6. Now we will modify the columns, so when we run the checks again we can the error alerts.

    We are going to run the SQL queries to make the following modifications in the columns:

    - remove the `date_updated` column from the `product1` table
        
        ```sql
        ALTER TABLE `dqo-ai-testing.example_dataset.products1`
        DROP COLUMN date_updated;
        ```

    - rename column `product_id` to `id` in the `product2` table
    
        ```sql
        ALTER TABLE `dqo-ai-testing.example_dataset.products2`
        RENAME COLUMN product_id TO id;
        ```
    
    - change the `date_updated` column type from DATETIME to DATE in the `product3` table
    
        ```sql
        ALTER TABLE `dqo-ai-testing.example_dataset.products3`
        DROP COLUMN date_updated;
        
        ALTER TABLE `dqo-ai-testing.example_dataset.products3`
        ADD COLUMN date_updated DATE;
        ```
    
    - change the order of columns `date_created` and `date_updated` in the `product4` table
    
        ```sql
        ALTER TABLE `dqo-ai-testing.example_dataset.products4`
        DROP COLUMN date_created,
        DROP COLUMN date_updated;
        
        ALTER TABLE `dqo-ai-testing.example_dataset.products4`
        ADD COLUMN date_updated DATETIME,
        ADD COLUMN date_created DATETIME;
        ```

7. Evaluate the results after the changes made in the columns. Because the daily checks stores the most recent result for
    each day when the data quality check was evaluated, the checks were run next day.
    
    - for the `product1` table where whe have remove the `date_updated` column we can see errors alerts in all run checks because
        with the removal of the column both the number of columns and the list of columns has changed (notice the orange squares to the left of the checks names).

        ![Schema detection check results after removing columns](https://dqops.com/docs/images/examples/schema-detection-checks-results-after-removing-columns.png)

    - for the `product2` table where whe have renamed the column `product_id` to `id` we can see errors in checks that detects changes in the 
        list of columns i.e. [daily_column_list_changed](../../checks/table/schema/column-list-changed.md), [daily_column_list_or_order_changed](../../checks/table/schema/column-list-or-order-changed.md),
        and [daily_column_types_changed](../../checks/table/schema/column-types-changed.md).

        ![Schema detection check results after renaming columns](https://dqops.com/docs/images/examples/schema-detection-checks-results-after-renaming-columns.png)

    - for the `product3` table where whe have changed the type of the column `date_updated` from DATETIME to DATE we can see error only in the check 
        [daily_column_types_changed](../../checks/table/schema/column-types-changed.md).
 
        ![Schema detection check results after changing datatype](https://dqops.com/docs/images/examples/schema-detection-checks-results-after-changing-datatype.png)

    - for the `product4` table where whe have changed the order of columns `date_created` and `date_updated` we can see error only in the check
        [daily_column_list_or_order_changed](../../checks/table/schema/column-list-or-order-changed.md)

        ![Schema detection check results after changing column order](https://dqops.com/docs/images/examples/schema-detection-checks-results-after-changing-column-order.png)


8. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

9. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 

    Below you can see the results displayed on the **Schema changes - summary of changes in columns** dashboard located in the Schema changes/Table-level issues group. 
    This dashboard summarizes results from schema changes checks.
    
    This dashboard allows filtering data by:

    * connection,
    * schema,
    * data group,
    * stages,
    * priorities,
    * check name,
    * issue severity level,
    * table.
   
    At the bottom of the dashboard there is a table summarizing the results from all executed schema change checks. You can check other dashboards in this group for 
    details per every schema change check type. 

    ![Schema change checks results on the Schema changes - summary of changes in columns dashboard](https://dqops.com/docs/images/examples/schema-checks-result-on-schema-summary-dashboard.png)


## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

The highlighted fragments in the YAML file below represent the segment where the schema change check are configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="7-20"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  monitoring_checks:
    daily:
      schema:
        daily_expected_column_count:
          error:
            expected_value: 5
        daily_column_count_changed:
          error: {}
        daily_column_list_changed:
          error: {}
        daily_column_list_or_order_changed:
          error: {}
        daily_column_types_changed:
          error: {}
  columns:
    product_id:
      type_snapshot:
        column_type: STRING
        nullable: true
```

In this example, we have demonstrated how to use DQOps to detect changes in the schema.
By using the [column_count](../../checks/table/schema/column-count.md), [column_count_changed](../../checks/table/schema/column-count-changed.md),
[column_list_changed](../../checks/table/schema/column-list-changed.md),
[column_list_or_order_changed](../../checks/table/schema/column-list-or-order-changed.md), and
[column_types_changed](../../checks/table/schema/column-types-changed.md) check, we can monitor typical schema changes issues.  
If the change occurs, you will get a warning, error or fatal results, depending on your settings.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQOps as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [table schema checks used in this example, go to the check details section](../../checks/table/schema/column-count.md).
- DQOps provides you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md). 
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/incidents-and-notifications/incidents.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping/set-up-data-grouping.md) can help you to calculate separate data quality KPI scores for different groups of rows.