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

## Documentation

For full documentation with guides and use cases, visit https://dqops.com/docs/

The [getting started](https://dqops.com/docs/getting-started/) guide shows how to start using DQOps.

Also, read the [DQOps concept](https://dqops.com/docs/dqo-concepts/) guide to know how DQOps operates,
and how to configure data quality checks.

## Contact and issues

If you find any issues with the tool, just post it here:

https://github.com/dqops/dqo/issues

or contact us via https://dqops.com/
