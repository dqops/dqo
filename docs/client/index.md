# REST API and Python client

Running DQOps instances can be interacted with through the use of RESTful API.
What's more, you can integrate the DQOps REST API into Python code using `dqops` package.

**Check out the guide underneath. It will walk you through the process of installing and interacting with DQOps through the Python client.**

??? Operations
    | Operation module | Description |
    |------------------|-------------|
    |[CheckResults](operations/check_results.md)|Returns the complete results of executed checks on tables and columns.|
    |[CheckResultsOverview](operations/check_results_overview.md)|Returns the overview of the recently executed checks on tables and columns.|
    |[Checks](operations/checks.md)|Data quality check definition management|
    |[Columns](operations/columns.md)|Manages columns inside a table|
    |[Connections](operations/connections.md)|Manages connections to monitored data sources|
    |[Dashboards](operations/dashboards.md)|Provides access to data quality dashboards|
    |[DataGroupingConfigurations](operations/data_grouping_configurations.md)|Manages data grouping configurations on a table|
    |[DataSources](operations/data_sources.md)|Rest API controller that operates on data sources that are not yet imported, testing connections or retrieving the metadata (schemas and tables).|
    |[Defaults](operations/defaults.md)|Default settings management for configuring the default data quality checks that are configured for all imported tables and columns.|
    |[Environment](operations/environment.md)|DQOps environment and configuration controller, provides access to the DQOps configuration, current user&#x27;s information and issue local API Keys for the calling user.|
    |[Errors](operations/errors.md)|Returns the errors related to check executions on tables and columns.|
    |[Healthcheck](operations/healthcheck.md)|Health check service for checking if the DQOps service is up and operational.|
    |[Incidents](operations/incidents.md)|Data quality incidents controller that supports loading incidents and changing the status of an incident.|
    |[Jobs](operations/jobs.md)|Jobs management controller that supports starting new jobs, such as running selected data quality checks|
    |[LogShipping](operations/log_shipping.md)|Log shipping controller that accepts logs sent from a web application or external tools and aggregates them in the local DQOps instance logs.|
    |[Rules](operations/rules.md)|Rule management|
    |[Schemas](operations/schemas.md)|Schema management|
    |[SensorReadouts](operations/sensor_readouts.md)|Returns the complete sensor readouts of executed checks on tables and columns.|
    |[Sensors](operations/sensors.md)|Sensors definition management|
    |[SharedCredentials](operations/shared_credentials.md)|Shared credentials management for managing credentials that are stored in the shared .credentials folder in the DQOps user&#x27;s home folder.|
    |[TableComparisonResults](operations/table_comparison_results.md)|Controller that returns the results of the most recent table comparison that was performed between the compared table and the reference table (the source of truth).|
    |[TableComparisons](operations/table_comparisons.md)|Manages the configuration of table comparisons between tables on the same or different data sources|
    |[Tables](operations/tables.md)|Manages tables inside a connection/schema|
    |[Timezones](operations/timezones.md)|Timezone management|
    |[Users](operations/users.md)|User management service|
    

??? Models
    | Model group |
    |-------------|
    |[common](models/common.md)|
    |[check_results](models/check_results.md)|
    |[check_results_overview](models/check_results_overview.md)|
    |[checks](models/checks.md)|
    |[columns](models/columns.md)|
    |[connections](models/connections.md)|
    |[dashboards](models/dashboards.md)|
    |[data_grouping_configurations](models/data_grouping_configurations.md)|
    |[data_sources](models/data_sources.md)|
    |[environment](models/environment.md)|
    |[errors](models/errors.md)|
    |[incidents](models/incidents.md)|
    |[jobs](models/jobs.md)|
    |[log_shipping](models/log_shipping.md)|
    |[rules](models/rules.md)|
    |[schemas](models/schemas.md)|
    |[sensor_readouts](models/sensor_readouts.md)|
    |[sensors](models/sensors.md)|
    |[shared_credentials](models/shared_credentials.md)|
    |[table_comparison_results](models/table_comparison_results.md)|
    |[table_comparisons](models/table_comparisons.md)|
    |[tables](models/tables.md)|
    |[users](models/users.md)|
    

## Python client guide

Guide :)
