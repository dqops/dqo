# Creating custom data quality checks
Read this guide to learn how to create a custom data quality check in DQOps using the user interface.

## Overview

DQOps is a powerful tool that offers more than 150 built-in data quality checks. These checks consist of a sensor and rule.
A sensor is a [templated Jinja2 SQL query](../dqo-concepts/definition-of-data-quality-sensors.md) and is further validated by a
[Python data quality rule](../dqo-concepts/definition-of-data-quality-rules.md).

One of the best things about DQOps is that you can design custom data quality checks that will be supervised by the data quality team. 
This means that you have complete control over the data quality standards that are implemented in your organization. 
Furthermore, any custom checks that you create will be visible in the user interface, making it easy for everyone on 
your team to access and use them.

## Creating a custom data quality check using UI

In this guide, we will show you how to create a custom check.

As an example, we will create a check that will verify that there is a valid DUNS number in a column. 
A data universal numbering system (DUNS) number is the most widely used method for identifying companies in the United States.
DUNS number is a unique, nine-digit series of numerals.

The following is a fragment of the exemplary table with a column `duns_number` containing valid and invalid DUNS numbers.

| id  | company_name                 | duns_number   |
|:----|:-----------------------------|:--------------|
| 1   | 23andme Holding Co.          | **117684369** |
| 2   | Dupont De Nemours, Inc.      | **080386615** |
| 3   | Johnson & Johnson            | **307081**    |
| 4   | Pepsico, Inc.                | **001287762** |
| 5   | The Procter & Gamble Company | **PG**        |

Specifically, we will verify that the number of rows with invalid DUNS numbers in a column does not exceed the maximum
accepted count. As a template for the custom check, we will use the [daily_invalid_email_format_found](../checks/column/patterns/invalid-email-format-found.md) column check


### **Navigate to custom checks, sensors, and rule editors**

To navigate to custom check, sensor, and rule editors using the [user interface](../dqo-concepts/dqops-user-interface-overview.md):

1. Go to the **Configuration** section.
2. From the tree view on the left, select the check, sensor or rule that you want to modify


!!! tip "Navigating tabs"

    When you select an item from the tree view, it will open in a new tab within the main workspace. You can easily 
    switch between open tabs by clicking on them, and close tabs that are no longer needed. Please note that there is
    a limit of seven tabs that can be opened at once. If you try to open an eighth tab, the first one you open will
    be automatically closed to maintain the limit.


The below example shows a check editor of the `daily_invalid_email_format_found` check.

![Navigate to a custom checks, sensors and rule editors](https://dqops.com/docs/images/working-with-dqo/creating-custom-data-quality-checks/navigating-to-custom-checks-rules-sensors.png)

### **Create a custom sensor**

To create a custom sensor, copy a sensor that you will use as a template, following the steps below:

1. On the **Configuration** section, select a sensor from the tree view on the left, that you will use as a template.
2. Make a copy of the sensor, by clicking on the **Copy** button.
3. Enter a new name and modify the sensor configuration.
4. Click the **Save** button to save the changes.

In our example, we want to modify the `invalid-email-count` sensor, which is located in the *sensors/column/patterns* folder.
On the sensor configuration screen, you can see a tab with the **Sensor definition** when you can choose to set that a check requires 
event and ingestion timestamps columns, set the default value when the table is empty and modify the sensor fields and parameters.

![Custom sensor editor](https://dqops.com/docs/images/working-with-dqo/creating-custom-data-quality-checks/custom-sensor-editor.png)

There are also tabs for different data sources which allows you to see and modify the source-specific [templated Jinja2 SQL queries](../dqo-concepts/definition-of-data-quality-sensors.md)

![Sensor SQL template](https://dqops.com/docs/images/working-with-dqo/creating-custom-data-quality-checks/sensor-sql-template.png)

As an example, we can modify the query for the BigQuery data source to verify the number the DUNS number. We will name it `invalid_duns_format_count`.
The sensor will calculate the number of rows with an invalid DUNS number. To do this, we need to change the fragment of templated
Jinja2 SQL query with a regular expression that recognizes email to a regex that recognizes nine-digit number `^[0-9]{9}$`.

Below is an example of Jinja2 SQL query before and after modification.

=== "Sensor template with a regex recognizing email"
    ```sql+jinja hl_lines="5"
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$")
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```

=== "Sensor template with a regex recognizing DUNS number"
    ```sql+jinja hl_lines="5"
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"^[0-9]{9}$")
                    THEN 0
                ELSE 1
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```


### **Create a custom rule**
To create a custom rule, copy a rule that you will use as a template, following the steps below:

1. On the **Configuration** section, select a rule from the tree view on the left, that you will use as a template.
2. Make a copy of the rule, by clicking on the **Copy** button.
3. Enter a new name and modify the rule configuration.
4. Click the **Save** button to save the changes.

The custom rule screen contains two tabs **Rule definition** and **Python code** which can be modified and allows customization of the rule.

![Custom rule editor](https://dqops.com/docs/images/working-with-dqo/creating-custom-data-quality-checks/custom-rule-editor.png)


### **Create a custom check**

To create a custom check, copy a check, which you will use as a template, following the steps below:

1. Click on the **Copy** button.
2. Enter a new check name.
3. Modify the check configuration (sensors and/or rules).
4. Click on the **Save** button, to save the changes.

You cannot modify the configuration of the built-in checks, but you can modify the check that has been added.

As an example, we will show how to modify the [daily_invalid_email_format_found](../checks/column/patterns/invalid-email-format-found.md) check,
which is located in the *column/monitoring/daily/patterns/* folder.

![Custom check editor](https://dqops.com/docs/images/working-with-dqo/creating-custom-data-quality-checks/custom-checks-editor.png)

On the main workspace of the check editor, you can see the names of the [sensor](../dqo-concepts/definition-of-data-quality-sensors.md)
and [rule](../dqo-concepts/definition-of-data-quality-rules.md) associated with this check. Clicking on the **Show definition** button
will link you to the details of the sensor or rule used in the checks.

There is also a checkbox indicating whether the check is categorized to the standard data quality checks and is always shown in the
check editor.

In our example, we copied the [daily_invalid_email_format_found](../checks/column/patterns/invalid-email-format-found.md) check,
and named it `daily_invalid_duns_format_found`). 

The copied check will appear in the tree view in the same folder as the check used as a template. 
The checks which have been added are shown on the tree view in bold.

![Create a copy of data quality check](https://dqops.com/docs/images/working-with-dqo/creating-custom-data-quality-checks/create-a-copy-of-check.png)

To change the sensor that is assigned to a check, simply select the sensor of choice from the dropdown menu and Save the changes by clicking on the **Save** button.
We will assign to this check a newly created sensor `invalid_duns_format_count` that calculates the number of rows with invalid DUNS number.

![Assign new sensor to a check](https://dqops.com/docs/images/working-with-dqo/creating-custom-data-quality-checks/assign-new-sensor-to-a-check.png)

If you want to change the rule, simply, choose a new rule from the dropdown menu. 

After saving changes, the new check is now visible on the Check editor and can be run.

![Assign new sensor to a check](https://dqops.com/docs/images/working-with-dqo/creating-custom-data-quality-checks/daily-invalid-duns-format-found-check-in-check-editor.png)


### **Storing definitions of custom checks, sensors, and rules**
All the added custom checks, sensors, and rules are stored in the [DQOps user home folder](../dqo-concepts/dqops-user-home-folder.md)

* The *checks* folder stores the definition of custom data quality [checks](../dqo-concepts/definition-of-data-quality-checks/index.md) as YAML files (`*.dqocheck.yaml`). 
* The *sensors* folder stores the definition of custom and overwritten data quality [sensors](../dqo-concepts/definition-of-data-quality-sensors.md).
* The *rules* folder stores the definition of custom and overwritten data quality [rules](../dqo-concepts/definition-of-data-quality-rules.md).


## What's more
- Look at the architecture diagrams showing [how DQOps runs data quality checks](../dqo-concepts/architecture/data-quality-check-execution-flow.md) to understand how DQOps uses all its configuration files.
- Learn [how the data quality results are stored](../dqo-concepts/data-storage-of-data-quality-results.md) as Parquet files in the *.data* folder.
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../integrations/slack/configuring-slack-notifications.md). 

