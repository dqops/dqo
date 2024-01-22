# What is DQOps?

DQOps is an **open-source** DataOps friendly data quality monitoring tool with customizable data quality checks and data quality dashboards.

DQOps comes with more than 150 predefined data quality checks which helps you monitor the quality of your data.

!!! note "DQOps is open-source"

    You can start using DQOps right now, installing it as a [pip Python package](dqops-installation/install-dqops-using-pip.md),
    or run it in [docker](dqops-installation/run-dqops-as-docker-container.md);


## DQOps features
- Intuitive [user interface](dqo-concepts/dqops-user-interface-overview.md) that uses tabs
  to work on multiple tables at the same time
- [Command-line](dqo-concepts/command-line-interface.md) interface
- Support of a number of different data sources: BigQuery, Snowflake, PostgreSQL, Redshift, SQL Server, MySQL, Spark, Databricks, Oracle and others
- More than 150 built-in table and column checks with easy customization
- Table and column-level [data quality checks](dqo-concepts/definition-of-data-quality-checks/index.md) which allows writing your own SQL queries
- Daily and monthly date [partition testing](dqo-concepts/definition-of-data-quality-checks/partition-checks.md)
- [Data grouping](dqo-concepts/measuring-data-quality-with-data-grouping.md) by up to 9 different columns
- Built-in [job scheduler](working-with-dqo/configure-scheduling-of-data-quality-checks/index.md) to run data quality checks
- Calculation of [data quality KPI scores](dqo-concepts/definition-of-data-quality-kpis.md)
- More than 50 built-in [data quality dashboards](dqo-concepts/types-of-data-quality-dashboards.md) to answer any question related to data quality
- [Incremental data quality monitoring](dqo-concepts/incremental-data-quality-monitoring.md) to detect issues early
- [Compare tables between data sources](working-with-dqo/compare-tables-between-data-sources.md) to measure data accuracy
- Cloud hosted [Data Quality Data Warehouse](dqo-concepts/architecture/dqops-architecture.md#data-quality-data-warehouse)
- [Incident](working-with-dqo/managing-data-quality-incidents-with-dqops.md) management
- Sending incident notifications to [webhooks](./integrations/webhooks/index.md) and [Slack](./integrations/slack/configuring-slack-notifications.md)

## Getting started

Try our [tutorial](./getting-started/index.md) to learn how to start monitoring your data with DQOps.

## Supported data sources

You can use DQOps with the most popular databases. [Learn here how to connect a data source to DQOps](./data-sources/index.md).

![Athena](https://dqops.com/docs/images/connections/athena2.png)
&nbsp; &nbsp; &nbsp; ![Google BigQuery](https://dqops.com/docs/images/connections/google-bigquery.png)
&nbsp; &nbsp; &nbsp; ![Databricks](https://dqops.com/docs/images/connections/databricks.png)
&nbsp; &nbsp; &nbsp; ![MySQL](https://dqops.com/docs/images/connections/mysql.png)
&nbsp; &nbsp; &nbsp; ![Oracle](https://dqops.com/docs/images/connections/oracle2.png)
&nbsp; &nbsp; &nbsp; ![PostgreSQL](https://dqops.com/docs/images/connections/postgresql.png)
&nbsp; &nbsp; &nbsp; ![Presto](https://dqops.com/docs/images/connections/presto1.png)
&nbsp; &nbsp; &nbsp; ![Amazon Redshift](https://dqops.com/docs/images/connections/amazon-redshift1.png)
&nbsp; &nbsp; &nbsp; ![Snowflake](https://dqops.com/docs/images/connections/snowflake.png)
&nbsp; &nbsp; &nbsp; ![Spark](https://dqops.com/docs/images/connections/spark.png)
&nbsp; &nbsp; &nbsp; ![Microsoft SQL Server](https://dqops.com/docs/images/connections/microsoft-sql-server.png)
&nbsp; &nbsp; &nbsp; ![Trino](https://dqops.com/docs/images/connections/trino1.png)
&nbsp; &nbsp; &nbsp; ![Single Store](https://dqops.com/docs/images/connections/single-store.png)


## DQOps user interface

DQOps has a dynamic user interface for configuration of all data quality checks that is similar to popular database management tools.
Tabs allow managing data quality checks at the same time on multiple tables. Just imagine that it is a web based
Visual Studio Code for data quality. 

![DQOps supports the most popular data sources](https://dqops.com/docs/images/dqops-data-sources2.png "DQOps supports the most popular data sources"){ loading=lazy } &nbsp; &nbsp; &nbsp; ![DQOps enables quick data profiling](https://dqops.com/docs/images/dqops-profiling.png "DQOps enables quick data profiling"){ loading=lazy }


![Checks in DQOps can be quickly edited with intuitive user interface](https://dqops.com/docs/images/dqops-checks-editor.png "Checks in DQOps can be quickly edited with intuitive user interface"){ loading=lazy } &nbsp; &nbsp; &nbsp; ![With DQOps, you can conveniently keep track of the issues that arise during data quality monitoring](https://dqops.com/docs/images/dqops-incidents-management.png "With DQOps, you can conveniently keep track of the issues that arise during data quality monitoring"){ loading=lazy } 

## DQOps dashboards

DQOps has multiple built-in data quality dashboards for displaying [data quality KPI](./dqo-concepts/definition-of-data-quality-kpis.md)
and enabling quick identification of tables with data quality issues.

![DQOps dashboards simplify monitoring of data quality KPIs](https://dqops.com/docs/images/dqops-kpis-scorecard-dashboard.png "DQOps dashboards simplify monitoring of data quality KPIs"){ loading=lazy } &nbsp; &nbsp; &nbsp; ![DQOps dashboards enable quick identification of tables with data quality issues](https://dqops.com/docs/images/dqops-current-completeness-issues-dashboard.png "DQOps dashboards enable quick identification of tables with data quality issues"){ loading=lazy } 

## DQOps is DevOps and DataOps friendly

Technical users can manage data quality check configuration at scale by changing YAML files in their editor of choice 
and version the configuration in Git. An example YAML configuration with the `profile_nulls_count` check configured is shown below.

```yaml hl_lines="7-15"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      profiling_checks:
        nulls:
          profile_nulls_count:
            warning:
              max_count: 0
            error:
              max_count: 10
            fatal:
              max_count: 100
      labels:
      - This is the column that is analyzed for data quality issues
```

## Additional resources

Want to learn more about data quality? 

Check out eBook ["A step-by-step guide to improve data quality"](https://dqops.com/dqo_ebook_a_step-by-step_guide_to_improve_data_quality-2/)
created by the DQOps team based on their experience in data cleansing and data quality monitoring.

![A step-by-step guide to improve data quality](./images/dqops-ebook-open-with-process.png "A step-by-step guide to improve data quality"){ loading=lazy }