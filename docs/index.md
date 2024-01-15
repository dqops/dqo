# What is DQOps?

DQOps is an DataOps friendly data quality monitoring tool with customizable data quality checks and data quality dashboards.
DQOps comes with more than 140 predefined data quality checks which helps you monitor the quality of your data.

## DQOps features
- Intuitive [user interface](dqo-concepts/user-interface-overview.md) that uses tabs
  to work on multiple tables at the same time
- [Command-line](dqo-concepts/command-line-interface.md) interface
- Support of a number of different data sources: BigQuery, Snowflake, PostgreSQL, Redshift, SQL Server, MySQL, Spark, Databricks, Oracle and others
- More than 140 built-in table and column checks with easy customization
- Table and column-level [data quality checks](dqo-concepts/definition-of-data-quality-checks/index.md) which allows writing your own SQL queries
- Daily and monthly date [partition testing](dqo-concepts/definition-of-data-quality-checks/partition-checks.md)
- [Data grouping](dqo-concepts/measuring-data-quality-with-data-grouping.md) by up to 9 different columns
- Built-in [job scheduler](working-with-dqo/configure-scheduling-of-data-quality-checks/index.md)
- Calculation of [data quality KPIs](dqo-concepts/definition-of-data-quality-kpis.md)
- Built-in [data quality dashboards](dqo-concepts/types-of-data-quality-dashboards.md)
- [Incremental data quality monitoring](dqo-concepts/incremental-data-quality-monitoring.md) to detect issues early
- [Compare tables between data sources](working-with-dqo/compare-tables-between-data-sources.md) to measure data accuracy
- Cloud hosted [Data Quality Data Warehouse](dqo-concepts/architecture/dqops-architecture.md#data-quality-data-warehouse)
- [Incident](working-with-dqo/managing-data-quality-incidents-with-dqops.md) management
- Sending incident notifications to [webhooks](./integrations/webhooks/index.md) and [Slack](./integrations/slack/configuring-slack-notifications.md)

## Getting started

Try our [tutorial](./getting-started/index.md) to learn how to start monitoring your data with DQOps.

## Supported data sources

You can use DQOps with the most popular databases. [Learn here how to connect a data source to DQOps](./data-sources/index.md).

![Google BigQuery](https://dqops.com/docs/images/connections/google-bigquery.png)
&nbsp; &nbsp; &nbsp; ![Snowflake](https://dqops.com/docs/images/connections/snowflake.png)
&nbsp; &nbsp; &nbsp; ![Amazon Redshift](https://dqops.com/docs/images/connections/amazon-redshift1.png)
&nbsp; &nbsp; &nbsp; ![PostgreSQL](https://dqops.com/docs/images/connections/postgresql.png)
&nbsp; &nbsp; &nbsp; ![Microsoft SQL Server](https://dqops.com/docs/images/connections/microsoft-sql-server.png)
&nbsp; &nbsp; &nbsp; ![MySQL](https://dqops.com/docs/images/connections/mysql.png)
&nbsp; &nbsp; &nbsp; ![Oracle](https://dqops.com/docs/images/connections/oracle2.png)
&nbsp; &nbsp; &nbsp; ![Spark](https://dqops.com/docs/images/connections/spark.png)
&nbsp; &nbsp; &nbsp; ![Databricks](https://dqops.com/docs/images/connections/databricks.png)
&nbsp; &nbsp; &nbsp; ![Presto](https://dqops.com/docs/images/connections/presto1.png)
&nbsp; &nbsp; &nbsp; ![Trino](https://dqops.com/docs/images/connections/trino1.png)
&nbsp; &nbsp; &nbsp; ![Athena](https://dqops.com/docs/images/connections/athena2.png)


## DQOps user interface

DQOps has a dynamic user interface for configuration of all data quality checks that is similar to popular database management tools.
Tabs allow managing data quality checks at the same time on multiple tables. Just imagine that it is a web based
Visual Studio Code for data quality. 

![](https://dqops.com/docs/images/dqo-screen1.png){ loading=lazy } &nbsp; &nbsp; &nbsp; ![](https://dqops.com/docs/images/dqo-screen2.png){ loading=lazy }


![](https://dqops.com/docs/images/dqo-screen3.png){ loading=lazy } &nbsp; &nbsp; &nbsp; ![](https://dqops.com/docs/images/dqo-screen4.png){ loading=lazy } 

## Additional resources

Want to learn more about data quality? 

Check out eBook ["A step-by-step guide to improve data quality"](https://dqops.com/dqo_ebook_a_step-by-step_guide_to_improve_data_quality-2/)
created by the DQOps team based on their experience in data cleansing and data quality monitoring.

![A step-by-step guide to improve data quality](./images/dqops-ebook-open-with-process.png "A step-by-step guide to improve data quality"){ loading=lazy }

