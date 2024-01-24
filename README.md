# DQOps Data Quality Operations Center

DQOps is an DataOps friendly data quality monitoring tool with customizable data quality checks and data quality dashboards.
DQOps comes with around 150 predefined data quality checks which helps you monitor the quality of your data.

![DQOps screens](https://dqops.com/docs/images/dqo-screens.gif)

## Key features
- Intuitive graphical interface and access via CLI
- Support of a number of different data sources: BigQuery, Snowflake, PostgreSQL, Redshift, SQL Server and MySQL
- ~600 build-in table and column checks with easy customization
- Table and column-level checks which allows writing your own SQL queries
- Daily and monthly date partition testing
- Data grouping by up to 9 different data grouping levels
- Build-in scheduling
- Calculation of data quality KPIs which can be displayed on multiple built-in data quality dashboards
- Incident analysis

## Installation

To use DQOps you need:

- Python version 3.8 or greater (for details see [Python's documentation](https://www.python.org/doc/) and [download sites](https://www.python.org/downloads/)).
- Ability to install Python packages with pip.
- If you want to compile DQOps locally, also Java JDK (version 17 or higher), and a configured JAVA_HOME environment variable.


DQOps is available on [PyPi repository](https://pypi.org/project/dqops/).

1. To install DQOps via pip manager just run

    ```
    python -m pip install --user dqops
    ```
   
    If you prefer to work with the source code, just clone our GitHub repository [https://github.com/dqops/dqo](https://github.com/dqops/dqo)
    and run

2. Run dqops app to finalize the installation.

    ```
    python -m dqops
    ```

3. Create DQOps userhome folder.

   After installation, you will be asked whether to initialize the DQOps user's home folder in the default location. Type Y to create the folder.  
   The user's home folder locally stores data such as sensor readouts and the data quality check results, as well as data source configurations. [You can learn more about data storage here](https://dqops.com/docs/dqo-concepts/data-storage/data-storage/).

4. Login to DQOps Cloud.

   To use DQOps features, such as storing data quality definitions and results in the cloud or data quality dashboards, you
   must create a DQOps cloud account.

   After creating a user's home folder, you will be asked whether to log in to the DQO cloud. After typing Y, you will be
   redirected to [https://cloud.dqops.com/registration](https://cloud.dqops.com/registration), where you can create a new account, use Google single sign-on (SSO) or log in if you already have an account.

   During the first registration, a unique identification code (API Key) will be generated and automatically retrieved by DQOps application.
   The API Key is now stored in the configuration file.

5. Open the DQOps User Interface Console in your browser by CTRL-clicking on the link displayed on the command line (for example http://localhost:8888)
   or by copying the link.


## What you can do with DQOps
DQOps is designed as the primary platform for data quality teams, and for all data engineering or data science
teams who want to apply data quality for their data platforms.

The following list shows selected use cases, with examples and best practices.

- The [definition of data quality KPIs](https://dqops.com/docs/dqo-concepts/definition-of-data-quality-kpis/)
  describes the formulas used by DQOps to calculate a data quality score, used to measure the quality of data sources

- You will learn how to [create custom data quality dashboards](https://dqops.com/docs/integrations/looker-studio/creating-custom-data-quality-dashboards/)
  using custom connector for Looker Studio provided by DQOps. Your data quality dashboards will show the data quality
  results organized in a format that is easy to understand by your business sponsors.

- Look at the [categories of data quality checks](https://dqops.com/docs/dqo-concepts/categories-of-data-quality-checks/)
  that are supported by DQOps.

- Learn how to [detect timeliness and freshness](https://dqops.com/docs/dqo-concepts/categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues/)
  with DQOps.

- Learn how to [measure data quality incrementally](https://dqops.com/docs/dqo-concepts/incremental-data-quality-monitoring/)
  using time partitioned checks that are a unique feature of DQOps, allowing to analyze financial data, append-only data,
  or very big tables at a terabyte or petabyte scales.

- Similar data quality issues are grouped into data quality incidents,
  learn how [grouping data quality issues to incidents](https://dqops.com/docs/dqo-concepts/grouping-data-quality-issues-to-incidents/),
  and how to receive notifications using Slack or passed to a webhook.


The following examples also show the whole process of configuring data quality checks, both using YAML files,
or using the [DQOps user interface](https://dqops.com/docs/dqo-concepts/dqops-user-interface-overview/).

- Learn how to [detect database and table availability issues](https://dqops.com/docs/examples/data-availability/detect-table-availability-issues/).

- Learn how configure data volume checks to [detect empty or incomplete tables](https://dqops.com/docs/examples/data-completeness/detect-empty-or-incomplete-tables/).

- Detect if [columns contain only accepted values](https://dqops.com/docs/examples/data-consistency/percentage-of-rows-with-a-text-found-in-set/).

- Detect duplicate values in columns by [measuring percentage of duplicates](https://dqops.com/docs/examples/data-uniqueness/percentage-of-duplicates/).

- Validate values in text columns using a regular expression to [detect values that are invalid emails](https://dqops.com/docs/examples/data-validity/detect-invalid-emails/).

- Use schema drift data quality checks to [detect table schema changes](https://dqops.com/docs/examples/schema/detect-table-schema-changes/),
  such as: missing columns, column order changed, column data type changed, or just that new columns were added or removed from a table.


## Documentation

For full documentation with guides and use cases, visit https://dqops.com/docs/

The [getting started](https://dqops.com/docs/getting-started/) guide shows how to start using DQOps.

Also, read the [DQOps concept](https://dqops.com/docs/dqo-concepts/) guide to know how DQOps operates,
and how to configure data quality checks.

The documentation of the [DQOps REST API Python client](https://dqops.com/docs/client/) shows the complete
documentation of the Python client, including code samples that are ready to copy-paste into your data pipeline code.

## Contact and issues

If you find any issues with the tool, just post it here:

https://github.com/dqops/dqo/issues

or contact us via https://dqops.com/
