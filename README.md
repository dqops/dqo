# DQO Data Quality Operations Center

DQO is an DataOps friendly data quality monitoring tool with customizable data quality checks and data quality dashboards.
DQO comes with around 150 predefined data quality checks which helps you monitor the quality of your data.

![DQO screens](https://dqops.com/docs/images/dqo-screens.gif)

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

To use DQO you need:

- Python version 3.8 or greater (for details see [Python's documentation](https://www.python.org/doc/) and [download sites](https://www.python.org/downloads/)).
- Ability to install Python packages with pip.
- Installed JDK software (version 17) and set the JAVA_HOME environment variable.


DQO is available on [PyPi repository](https://pypi.org/project/dqops/).

1. To install DQO via pip manager just run

    ```
    python -m pip install --user dqops
    ```
   
    If you prefer to work with the source code, just clone our GitHub repository [https://github.com/dqops/dqo](https://github.com/dqops/dqo)
    and run

2. Run dqo app to finalize the installation.

    ```
    python -m dqops
    ```

3. Create DQO userhome folder.

   After installation, you will be asked whether to initialize the DQO user's home folder in the default location. Type Y to create the folder.  
   The user's home folder locally stores data such as sensor readouts and the data quality check results, as well as data source configurations. [You can learn more about data storage here](https://dqops.com/docs/dqo-concepts/data-storage/data-storage/).

4. Login to DQO Cloud.

   To use DQO features, such as storing data quality definitions and results in the cloud or data quality dashboards, you
   must create a DQO cloud account.

   After creating a user's home folder, you will be asked whether to log in to the DQO cloud. After typing Y, you will be
   redirected to [https://cloud.dqo.ai/registration](https://cloud.dqo.ai/registration), where you can create a new account, use Google single sign-on (SSO) or log in if you already have an account.

   During the first registration, a unique identification code (API Key) will be generated and automatically retrieved by DQO application.
   The API Key is now stored in the configuration file.

5. Open the DQO User Interface Console in your browser by CTRL-clicking on the link displayed on the command line (for example http://localhost:8888)
   or by copying the link.

## Documentation

For full documentation with guides and use cases, visit https://dqops.com/docs

## Contact and issues

If you find any issues with the tool, just post it here:

https://github.com/dqops/dqo/issues

or contact us via https://dqops.com/
