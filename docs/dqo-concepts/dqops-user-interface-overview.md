---
title: DQOps user interface overview
---
# DQOps user interface overview
This guide introduces the concept of DQOps user interface, how the screen is organized, how to work with multiple tables, and navigate across the platform.

## Overview

The DQOps web interface is divided into three main areas.

![The DQOps user interface overview](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/user-interface-overview3.png){ loading=lazy; width="1200px" }

1. The [navigation bar](#navigation-bar) has links to the main DQOps data quality monitoring sections and provides access to the main settings and other functions.
2. The [tree view](#tree-view) displays all the data sources available in your workspace or a list of dashboards depending on the section chosen in the navigation bar. 
3. The [main workspace](#main-workspace) changes according to your selection in the navigation bar and the tree view.

## Home screen

The DQOps home screen provides a quick guide on how to start data quality monitoring. Here, you can also access summaries
that provide a centralized view of the data quality or all your monitored tables and columns, as well as summary of the incidents.
These summaries are created based on the results of previously executed monitoring and partition data quality checks. 
The results form profiling data quality are not included in the calculation.

### **Tables summary**
The Tables summary screen provides a centralized view of the data quality of all your tables within DQOps.

To access **Tables summary** click on the DQOps logo in the top left corner and select the **Tables** tab.

![Table summary](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/tables-summary.png){ loading=lazy; width="1200px" }

Searching and filtering the tables:

- **Search**: Use the search bar at the top of the screen to find specific tables based on connection name, schema name, or table name. You can also use wildcards (*) to easily locate tables that meet specific criteria without needing to enter exact names.
- **Filter by Label**: Refine your view by applying filters to the table labels on the left-hand side. This helps you locate tables based on your user-defined table labels.

The Tables summary screen displays the following information for each table:

- **Connection** - The database connection the table belongs to.
- **Schema** - The schema within the connection where the table is located.
- **Table** - The name of the specific table.
- **Stage** - The current stage of the table (e.g., development, production).
- **Labels** - User-defined labels associated with the table for easier organization.
- **Data Quality KPIs** - A summary of [data quality KPIs](definition-of-data-quality-kpis.md) calculated from partial data values, providing a quick overview of data quality of the table.
- **Data Quality KPIs by dimensions** - This column displays a breakdown of [data quality KPIs](definition-of-data-quality-kpis.md) calculated from executed checks categorized by specific dimensions (e.g., completeness, validity, consistency). Hovering over the KPIs value will display a tooltip containing more details.
  
    ![Table summary - deta quality dimension results details](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/table-summary-tooltip.png){ loading=lazy; width="500px" }

- **Action buttons** - Click on these buttons ![Table summary - action buttons](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/table-and-column-summary-action-buttons.png) to link to Data Source, Profiling, Monitoring Checks, or Partition Checks section screens.


### **Columns summary**
The Column summary screen provides a granular view of the data quality for each column within your tables.

To access Columns summary click on the DQOps logo in the top left corner and select the **Columns** tab.

![Table summary](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/columns-summary.png){ loading=lazy; width="1200px" }

Searching and filtering the columns:

- **Search**: Use the search bar at the top of the screen to find specific columns based on connection name, schema name, table name, column name, or column type (e.g., INTEGER, STRING, DATE). You can also use wildcards (*) to easily locate columns that meet specific criteria without needing to enter exact names.
- **Filter by Label**: Refine your view by applying filters to the table labels on the left-hand side. This helps you locate tables based on your user-defined table labels.

The table summary screen displays the following information for each table:

- **Connection** - The database connection the table belongs to.
- **Schema** - The schema within the connection where the table is located.
- **Table** - The table where the column is located.
- **Column** - The name of the specific column.
- **Column type** - The data type of the column (e.g., INTEGER, STRING, DATE).
- **Data Quality KPIs** - A summary of [data quality KPIs](definition-of-data-quality-kpis.md) from partial data values, providing a quick overview of data quality of your columns.
- **Results from data quality dimensions** - This column displays a breakdown of [data quality KPIs](definition-of-data-quality-kpis.md) calculated from executed checks categorized by specific dimensions (e.g., completeness, validity, consistency). Hovering over the KPIs value will display a tooltip containing more details.
  
    ![Table summary - deta quality dimension results details](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/table-summary-tooltip.png){ loading=lazy; width="500px" }

- **Action buttons** - Click on these buttons ![Table summary - action buttons](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/table-and-column-summary-action-buttons.png) to link to Data Source, Profiling, Monitoring Checks, or Partition Checks section screens.

### **Incidents summary**

The **Incidents summary** provides an overview of all incidents created in DQOps, which are groupings of similar data quality issues. 
To learn more about Incidents refer to the [Data quality incident management, grouping and notifications section](grouping-data-quality-issues-to-incidents.md)

To access **Incidents summary** click on the DQOps logo in the top left corner and select the **Incidents summary** tab.

![Incidents summary](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/incidents-summary2.png){ loading=lazy; width="1200px" }

The **Incidents summary** screen provides an overview of data quality incidents categorized by either check category or data quality dimension.
You can easily switch between these groupings using the provided radio buttons.
The **Refresh** button located at the top right of the screen, allows you to refresh the displayed incident data.

At the top of The Incidents summary screen, there are two sections displaying the summaries of **Open** and **Acknowledged** incidents.
Each section is divided into three blocks based on severity level: Warnings, Errors and Fatal errors. In each block,
there is a summary of new incidents from the last 2 months along with detailed data showing the number of incidents detected in the last 24h, last 7 days,
current month and previous month.

Below the **Open** and **Acknowledged** incidents summaries, there is a table that list incidents grouped by the selected check category or the quality dimension, depending on your grouping selection.
The table includes the following columns:

- **Severity** - Indicates the severity of the incident with colored squares (yellow for warning, orange for error and red for fatal error)
- **Total issues** - Displays the total number of times the data quality issue has occurred in the incident.
- **Connection** - The name of the connection where the incident was detected.
- **Schema** - The schema name within the connection where the incident occurred.
- **Table** - The name of the table where the incident was detected.
- **Quality dimension** or **Check category** (depending on chosen grouping) - This column will display either the data quality dimension associated with the incident (e.g., Completeness, Validity, Consistency) or the check category that triggered the incident (e.g., anomaly, datetime, nulls, uniqueness).
- **First seen** - Shows the date the incident was first detected.
- **Last seen** - Displays the date the incident was last detected.

Each table has a **Show more** button. Clicking this button will navigate you to the dedicated Incidents screen for a more detailed view.

## Navigation bar

The navigation bar allows you to select the specific sections that you want to focus on.

![Navigation bar sections](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/navigation-bar2.png)

- The **Home screen** section, which you can access by clicking on the DQOps logo. It allows you to view the Welcome page, as well as summaries of Tables and Columns.
- The **Data Sources** section allows you to [add new connections](../data-sources/index.md), import schemas and 
    tables, [set up schedules](../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md), add comments and labels, [configure data grouping](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md),
    [set the date and time columns for partition checks](../working-with-dqo/run-data-quality-checks.md#configure-date-or-datetime-column-for-partition-checks), 
    and [modify the settings of the incidents and notifications](../working-with-dqo/managing-data-quality-incidents-with-dqops.md).
- The **Profiling** section allows you to [collect and review basic data statistics](../working-with-dqo/collecting-basic-data-statistics.md),
    as well as [enable and run profiling data quality checks](../working-with-dqo/run-data-quality-checks.md)
    that should be used to profile data and run experiments to see which check would be most appropriate for monitoring the quality of data. 
- The **Monitoring Checks** section allows you to [enable and run daily or monthly monitoring data quality checks](../working-with-dqo/run-data-quality-checks.md)
    that are standard checks that monitor data quality.
- The **Partition Checks** section allows you to [enable and run daily or monthly partition data quality checks](../working-with-dqo/run-data-quality-checks.md)
    that measure data quality for each daily or monthly partition by creating a separate data quality score.
- The **Data Quality Dashboards** section lets you [review the summaries of data quality monitoring](../getting-started/review-results-on-dashboards.md) on multiple built-in data quality dashboards.
- The **Incidents** section helps you [review and manage the issues that arise during data quality monitoring](../working-with-dqo/managing-data-quality-incidents-with-dqops.md).
- The **Configuration** section lets you customize built-in data quality sensors and rules.  


The right side of the navigation bar provides access to the main settings and some other functions.

![DQOps Navigation bar sections](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/rigth-part-of-the-navigation-bar.png)

- **Help** - Links to the support page, documentation, DQOps account manager and configuration parameters settings set at 
    the start of DQOps. You can change the configuration settings by [setting command line arguments at the start of the application](../command-line-interface/dqo.md).
- **Synchronize** - Synchronize locally stored results with your DQOps Cloud account.
- **[Notifications](#notifications)** - View DQOps notifications and enable/disable the scheduler. The Notifications icon displays **NEW** tag, when there is a new notification.
- **User account** - Provide information about your account, current limits and access to the DQOps Cloud account. 


## Tree view

The tree view at the **Data Source**, **Profiling**, **Monitoring Checks** and **Partition Checks** displays all the added sources and allows
expanding its schemas, tables, and columns. Additionally, on the Profiling, Monitoring Checks and Partition Checks sections the three view
contains the list of checks.

![DQOps tree view - monitoring](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/tree-view-monitoring.png)

Clicking on an object in the tree view displays settings, statistics or a list of table or column data quality checks on
the main workspace. Clicking on an object always opens a new tab in the main workspace.

### **Profiling, Monitoring Checks and Partition Checks tree view**

In the **Profiling**, **Monitoring Checks** and **Partition Checks** sections, when you configure checks on any table or column, this
table or column is displayed in bold on the tree view. In the **Data Source** section the bolded column or table name indicates
that there is a check configured on any category.

![DQOps tree view menu](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/tree-view-menu.png)

Clicking on the tree vertical dots at the end of each element in the tree view in the **Profiling**, **Monitoring Checks**
and **Partition Checks** sections opens a menu that allows to run the following functions:

- Run all activated checks on connection, schema, table or column
- Collect statistics
- Import metadata
- Add schema, table or column
- Refresh connection, schema, table or column
- Delete connection, schema, table or column
- Delete data

### **Data Quality Dashboards tree view**

The tree view in the **Data Quality Dashboards** section displays the list of built-in dashboards divided into categories.
When you hover your cursor over a dashboard name, thumbnails will appear.

![DQOps tree view - dashboards](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/tree-view-dashboards.png)


### **Incidents tree view**

The tree view in the **Incidents** section displays a list of all added sources and the number of open incidents for the last 15 days (including today) in brackets.

![DQOps tree view  - Incidents](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/tree-view-incidents.png)


### **Configuration tree view**

In the **Configuration** section, the tree view displays a list of all built-in data quality sensors, rules and data 
quality checks that can be customized. The tree view in this section also provides access to the configuration of the following:

- **Default checks configuration:** This allows you to configure checks that are automatically activated after importing new tables.
- **Manage users:** This allows you to add new users and manage their roles.
- **Default schedules:** This allows you to [configure schedules](../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md) that are automatically set after importing new tables.
- **Defaults webhooks:** This allows you to [configure webhooks](../integrations/webhooks/index.md).
- **Shared credentials:** This allows you to add and manage shared credentials.

![DQOps tree view - Configuration](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/tree-view-configuration.png)

## Main workspace

Once you select a section from the navigation bar and tree view, the main workspace will show you various data quality 
components. These include home screen summaries, data source options, information about schemas, tables, and columns, a list of data quality checks,
and data quality dashboards.

Clicking on an object in the tree view always opens a new tab in the main workspace that helps in navigating between open elements.

### **Tabbed interface**

When you select an item from the tree view, it opens a new tab in the main workspace. You can open and close tabs within each section 
such as **Data Source**, **Profiling**, **Monitoring Checks**, **Partition Checks**, **Data Quality Dashboards**, **Incidents** and **Configuration**.
Each section can have a maximum of seven tabs open at a time.  If you try to add an eighth tab, the first one will be automatically removed to maintain the limit. 
These tabs are stored locally, which means that you can always return to them even after closing the application.

![Tabs](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/tabs.png){ loading=lazy; width="1200px" }

If the tab's content no longer exists, a pop-up notification will appear and the corresponding tab will close automatically.

![Closing tab](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/tab-will-closed.png){ loading=lazy; width="1200px" }

### **Main workspace tabs**

Below we explain the main tabs of the workspace in the **Profiling**, **Monitoring Checks** and **Partition Checks** sections.

In the **Profiling** section you can access:

![Main workspace tabs - Profiling](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/main-workspace-tabs-profiling.png){ loading=lazy; width="1200px" }

- **Basic data statistics** that allows you to [collect basic statistics](../working-with-dqo/collecting-basic-data-statistics.md) about data sources during the data profiling stage.
- **Table preview** that provides a summary preview of the table. 
- **Table quality status** provides a summary of the results of the executed profiling checks.
- **Profiling checks** that gives access to the Check editor.
- **Table comparison** that enables you to [identify differences between two tables](../working-with-dqo/compare-tables-between-data-sources.md).

In the **Monitoring Checks** section you can access

![Main workspace tabs - Monitoring](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/main-workspace-tabs-monitoring.png){ loading=lazy; width="1200px" }


- **Table quality status (daily checks)** provides a summary of the results of the executed daily monitoring checks.
- **Daily checks** that gives access to the Check editor for daily checks.
- **Table quality status (monthly checks)** provides a summary of the results of the executed monthly monitoring checks.
- **Monthly checks** that gives access to the Check editor for monthly checks.
- **Daily comparisons** that enables you to [identify differences between two tables using daily checks](../working-with-dqo/compare-tables-between-data-sources.md).
- **Monthly comparisons** that enables you to [identify differences between two tables using monthly checks](../working-with-dqo/compare-tables-between-data-sources.md).
  
In the **Partition Checks** section you can access

![Main workspace tabs - Partition](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/main-workspace-tabs-partition.png){ loading=lazy; width="1200px" }

- **Table quality status (daily checks)** provides a summary of the results of the executed daily partition checks.
- **Daily checks** that gives access to the Check editor for daily checks.
- **Table quality status (monthly checks)** provides a summary of the results of the executed monthly partition checks.
- **Monthly checks** that gives access to the Check editor for monthly checks.
- **Daily comparisons** that enables you to [identify differences between two tables using daily partition checks](../working-with-dqo/compare-tables-between-data-sources.md).
- **Monthly comparisons** that enables you to [identify differences between two tables using monthly partition checks](../working-with-dqo/compare-tables-between-data-sources.md).


### **Check editor**

The Check editor screen allows you to work with checks.

![Main workspace tabs](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/main-workspace-check-editor3.png){ loading=lazy; width="1200px" }

Under tabs selection, there is information about the set schedule configuration. Also, in the **Partition Checks** 
section, there is additional information about the date partitioning column.

The table with data quality checks contains a list of checks divided into different data quality subcategories that you
can expand and collapse by clicking on an arrow. [Learn more about the different check subcategories](../checks/index.md).
By clicking on the **Show advanced checks** checkbox you can view all checks available in DQOps. 

The right side of the table allows setting different threshold levels (severity levels). [Learn more about threshold levels ](definition-of-data-quality-checks/index.md#issue-severity-levels)

On the left of the name of each check, there are several buttons and icons. And under the name of the check, there is a data quality
dimension category to which this check was categorized.

![Check buttons](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/check-buttons.png)

The buttons and icons allow you to:

- Activate and deactivate checks by using the toggle button. The toggle button next to the name of the activated default checks will have a light green color. If the check has
    been activated manually, the toggle button will have a darker green color. Learn more about [monitoring data quality with default check here](../working-with-dqo/daily-monitoring-of-data-quality.md). 
- Disable checks by using the Disable button
- Access check **Settings** where you can set Custom data groupings, modify inclusion/exclusion of check in KPI and SLA, modify the name of the Data Quality Dimension, add SQL WHERE condition, modify scheduling settings, or add Labels.

    ![Settings buttons](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/check-settings.png){ loading=lazy; width="1200px" }

- Check schedule status. 
- Run data quality checks
- View detailed results for checks, Sensor readouts, and Execution errors

    ![Results buttons](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/check-results.png){ loading=lazy; width="1200px" }

- View detailed information about check. 
- Check the results of the run check shown as a color square
    - Green for a valid result
    - Yellow for a warning
    - Orange for an error
    - Red for a fatal error
    - Black for execution error.

### **Table quality status**

In the **Profiling**, **Monitoring Checks**, and **Partition Checks**, there is a tab called **Table quality status**.
This tab provides a summary of the results of the executed checks, grouped by check category or data quality dimension.

![Table quality status](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/table-quality-status.png){ loading=lazy; width="1200px" }


By using the radio buttons in the upper right corner of the screen, you can view the results for the **Current month**,
**Last 3 months**, or select a specific starting date. You can also switch between viewing the **Current severity status**
and the **Highest severity status**.

There are two tables on the left-hand side of the screen, which show the **Current table status** and the summary of the
**Total checks executed**. 

Below these tables, there is a table with the Current severity statuses divided by the check category or data quality 
dimension, depending on the selected option. The first row displays the results from the table-level checks executed 
on the table, while the following rows show the column names and the results from the executed column-level checks. 
The color indicate the current or the highest severity status:

- green for a valid result
- yellow for a warning
- orange for an error
- red for a fatal error

You can view the list of executed checks by clicking on the arrow. Hovering over a check name will display more details.

![Table quality status - detailed checks view](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/table-quality-status-detailed-checks-view.png){ loading=lazy; width="1200px" }

## Notifications

Notifications provide a log of all completed tasks, such as running checks, gathering statistics, importing metadata,
synchronizing folders, etc.

![DQOps Notifications](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/notification-panel.png)

You can use the notifications to access a complete log of all jobs that have been executed. This log allows you to check
the status of each action, which can be "finished," "failed," "running," "waiting," "queued," or "canceled."
Additionally, you can expand each job entry to view sub-jobs and access more detailed information about specific tasks.

### **Enable and disable scheduler**

In the top part of notifications window you can start and stop the scheduler. You can [learn more about scheduling here](../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).

![DQOps job scheduler](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/job-scheduler.png)

### **Cancel a queued or running job**

If the job is queued or running you can cancel or stop it by clicking the X button.

![DQOps canceling job](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/cancel-job.png)

### **Run checks summary**

You can view the result of executed checks by simply hovering on run checks job. Color of the square represents the highest
severity status of the results (green for a valid result, yellow for a warning, orange for an error and red for a fatal error).

![DQOps job results](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/job-results.png)

## What's next

- Check the detailed process of running data quality checks and all DQOps engine internal components involved in the process in the [Data quality check execution flow](architecture/data-quality-check-execution-flow.md) section.
- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../dqops-installation/run-dqops-as-docker-container.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../integrations/slack/configuring-slack-notifications.md).


