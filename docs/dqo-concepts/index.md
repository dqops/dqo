# Key concepts overview

These topics introduce the basic concepts of DQOps.

 - **[Sensors](./sensors/sensors.md)**

    The data quality `sensors` are SQL queries defined as Jinja2 templates. A sensor is called by a data quality check
    to capture a data quality measure such as the row count from the monitored source. The sensor's measure is called
    a `sensor readout` in DQOps.


 - **[Rules](./rules/rules.md)**

    Data quality rules in DQOps are Python functions that receive the `sensor readout`
    that was captured by sensor (a result of an SQL query).
    The rule verifies if the `sensor readout` is valid or a data quality issue should be raised.
    For example, the [max_percent](../reference/rules/Comparison.md#max-percent) rule will verify if the result
    of the [null_percent](../reference/sensors/column/nulls-column-sensors.md#null-percent) sensor is valid.

 - **[Checks](./checks/index.md)**

    A data quality check detects data quality issues. The check in DQOps is defined as a pair
    of a [sensor](./sensors/sensors.md) and a [rule](./rules/rules.md) that verifies the sensor's readout.
    For example, the [nulls_percent](../checks/column/nulls/nulls-percent.md) check uses both the
    [null_percent](../reference/sensors/column/nulls-column-sensors.md#null-percent) sensor
    and the [max_percent](../reference/rules/Comparison.md#max-percent) rule to detect if the maximum percent
    of null values in a tested column is satisfied.

    If the percent of null values in a column raises above the threshold (maximum allowed percent),
    a data quality issue is raised.


 - **[DQOps user home](./home-folders/dqops-user-home.md)**

    `DQOps user home` is the most important folder. When DQOps is started by running `python -m dqops`, the current working
    folder becomes the `DQOps user home`.

    DQOps will set up a folder tree to store the list of monitored data sources and the configuration of data quality
    checks for all imported tables. The configuration is stored in YAML files for simplicity of editing in Visual Studio Code.


 - **[Running checks](./running-checks/running-checks.md)**

    Data quality checks configured for each table and column are executed by targeting the data source, table, column,
    check name, check type, check category or even labels assigned to tables or columns. 


 - **[Check execution flow](./architecture/check-execution-flow.md)**

    Detailed data quality execution flows that show how DQOps uses [Sensors](./sensors/sensors.md), [Rules](./rules/rules.md),
    [Checks](./checks/index.md) and how the data is [stored](./data-storage/data-storage.md).


 - **[Deployment architecture](./architecture/dqops-architecture.md)**

    DQOps can be hosted locally, in the cloud or as a hybrid deployment, running a semi-offline DQOps instances
    on-premise or in the customer's cloud environment.


 - **[Data quality KPIs](./data-quality-kpis/data-quality-kpis.md)**

    The data quality is measured by data quality KPIs (Key Performance Indicators).
    The definition of a data quality KPI in DQOps is a percentage of passed data quality checks out of all executed data quality checks.
    
    The data quality KPIs are calculated on multiple levels:
    
    - per table

    - per data source

    - per data quality dimension

    - or a combination of any other dimensions


 - **[Data quality dashboards](./data-quality-dashboards/data-quality-dashboards.md)**

    DQOps stores the data quality check results locally, but also the data is synchronized to a `Data Quality Data Warehouse`
    hosted in the cloud by DQOps for each user.

    The data quality dashboards are accessing the `Data Quality Data Warehouse` and enable reviewing the `Data Quality KPIs`
    or drilling down to the data quality issues. DQOps uses a custom Google Looker Studio Community Connector to access
    the user's `Data Quality Data Warehouse`.


 - **[Data quality dimensions](./data-quality-dimensions/data-quality-dimensions.md)**

    The data quality dimensions are the fundamental way to group data quality checks into groups of checks that detect similar issue.
    The most important data quality dimensions supported by DQOps are:

    - `Validity` detects common field format issues, such as an *email* field does not meet the email format
   
    - `Completeness` detects missing data, for example columns with too many null values 
   
    - `Timeliness` tracks freshness of data, measuring the maximum allowed age of data
   
    - `Availability` watches the tables in the data source, raising a data quality issue when the table is missing or returns errors
   
    - `Consistency` monitors the data over a period of time, looking for anomalies such as the usual percent of null values
      per day was within the regular range, but an unusual increase of the percent of null values in a column was observed for one day 
   
    - `Uniqueness` finds issues related to duplicate values

    - `Reasonableness` identifies values that are not making sense, falling out of expected range


- **[Data grouping](./data-grouping/data-grouping.md)**

    DQOps unique feature is the ability to use a **GROUP BY** clause in the data quality sensors, allowing to run data quality checks
    for multiple ranges of rows in the same table.

    For example, a table *fact_global_sales* that aggregates sales fact rows from multiple countries can be tested for each country.
    A column that identifies a country must be present in the table and data grouping must be configured.

    Data grouping allows detecting data quality issues for groups of rows loaded by different data streams or received
    from different sources.


- **[Data storage](./data-storage/data-storage.md)**

    DQOps stores both the configuration of data sources, the configuration of data quality checks enabled on tables
    and the data quality check execution results locally in a `DQOps user home` folder.

    The data quality results are stored in a *$DQO_USER_HOME/.data* folder that is a Hive-compliant local data lake.
    Please read the [data storage](./data-storage/data-storage.md) concept guide to understand the data lake structure.
 

- **[Working with the YAML files](./working-with-yaml-files/working-with-yaml-files.md)**

    DQOps uses YAML files to keep the configuration of data sources and the enabled data quality checks on monitored tables.
    The DQOps YAML file format is fully documented and the YAML schema files are published.

    The DQOps YAML schema files allow unprecedented coding experience with Visual Studio Code when managing the
    configuring the data quality checks directly in the editor. Code completion, syntax validation and help hints
    are shown by Visual Studio Code and many other editors when editing DQOps YAML files.


- **[Working with CLI](./working-with-cli/working-with-cli.md)**

    Command-line access to DQOps is supported by a shell interface. The DQOps shell supports command and table name completion.

 