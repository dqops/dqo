# Navigating the DQO graphical interface

The DQO graphical interface is divided into three main areas.

![The DQO graphical interface overview](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/graphical-interface-overview.png)

1. The [navigation bar](./#navigation-bar) has links to the main DQO data quality monitoring sections and provides access to the main settings and other functions.
2. The [tree view](./#tree-view) displays all the data sources available in your workspace or a list of dashboards depending on the section chosen in the navigation bar. 
3. The [main workspace](./#main-workspace) changes according to your selection in the navigation bar and the tree view.

## Navigation bar

The navigation bar allows you to select the specific sections that you want to focus on.

![Navigation bar sections](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/navigation-bar.png)

- The **Data Sources** section allows you to [add new connections](../adding-data-source-connection/index.md), import schemas and 
    tables, [set up schedules](../schedules/index.md), add comments and labels, [configure data streams](../set-up-data-grouping/set-up-data-grouping.md),
    [set the date and time columns for partition checks](../run-data-quality-checks/run-data-quality-checks.md/#configure-date-or-datetime-column-for-partition-checks), 
    and [modify the settings of the incidents and notifications](../incidents-and-notifications/incidents.md).
- The **Profiling** section allows you to [collect and review basic data statistics](../basic-data-statistics/basic-data-statistics.md),
    as well as [enable and run profiling data quality checks](../run-data-quality-checks/run-data-quality-checks.md)
    that should be used to profile data and run experiments to see which check would be most appropriate for monitoring the quality of data. 
- The **Monitoring Checks** section allows you to [enable and run daily or monthly monitoring data quality checks](../run-data-quality-checks/run-data-quality-checks.md)
    that are standard checks that monitor data quality.
- The **Partition Checks** section allows you to [enable and run daily or monthly partition data quality checks](../run-data-quality-checks/run-data-quality-checks.md)
    that measure data quality for each daily or monthly partition by creating a separate data quality score.
- The **Data Quality Dashboards** section lets you [review the summaries of data quality monitoring](../../getting-started/review-results-on-dashboards/review-results-on-dashboards.md) on multiple built-in data quality dashboards.
- The **Incidents** section helps you [review and manage the issues that arise during data quality monitoring](../incidents-and-notifications/incidents.md).
- The **Definitions** section lets you customize built-in data quality sensors and rules.  


The right side of the navigation bar provides access to the main settings and some other functions.

![Navigation bar sections](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/rigth-part-of-the-navigation-bar.png)

- **Help** - Links to the support page, documentation, DQO account manager and configuration parameters settings set at 
    the start of DQO. You can change the configuration settings by [setting command line arguments at the start of the application](../../command-line-interface/dqo.md).
- **Synchronize** - Synchronize locally stored results with your DQO account.
- **Notifications** - You can view DQO notifications and enable or disable the scheduler. A NEW tag will appear when there is a new notification.
- **User account** - Provide information about your account, current limits and access to the DQO account manager. 

## Tree view

![Tree view menu](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/tree-view.png)

The tree view at the **Data Source**, **Profiling**, **Monitoring Checks** and **Partition Checks** displays all the added sources and allows
expanding its schemas, tables, and columns. Additionally, on the Profiling, Monitoring Checks and Partition Checks sections the three view 
contains the list of checks.

Clicking on an object in the tree view displays settings, statistics or a list of table or column data quality checks on
the main workspace. Clicking on an object always opens a new tab in the main workspace.

In the **Profiling**, **Monitoring Checks** and **Partition Checks** sections, when you configure checks on any table or column, this
table or column is displayed in bold on the tree view. In the **Data Source** section the bolded column or table name indicates
that there is a check configured on any category. 

### Tree view menu

![Tree view menu](https://dqops.com/docs/images/working-with-dqo/navigating-the-graphical-interface/tree-view-menu.png)

Clicking on the tree vertical dots at the end of each element in the tree view opens a menu that allows to run functions
specific to the elements such as:

- Run all enabled checks on connection, schema, table or column
- Collect statistics
- Import metadata
- Add schema, table or column
- Refresh connection, schema, table or column
- Delete connection, schema, table or column
- Delete data

The tree view in the **Data Quality Dashboards** section displays the list of built-in dashboards divided into categories.

The tree view in the **Incidents** section displays the list of all added sources and a number of open incident from 15 days including today in brackets. 

The tree view in the **Definitions** section shows the list of all built-in data quality sensors and rules that can be customized.

## Main workspace

Once you select a section from the navigation bar and tree view, the main workspace will show you various data quality 
components. These include data source options, information about schemas, tables, and columns, a list of data quality checks,
and data quality dashboards.

Clicking on an object in the tree view always opens a new tab in the main workspace that helps in navigating between open elements. 



