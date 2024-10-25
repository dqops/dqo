---
title: How to set up data quality monitoring and data observability for Timescale
---
# How to set up data quality monitoring and data observability for Timescale
Data observability and data monitoring for Timescale. Detect schema changes, data anomalies, volume fluctuations, and other data quality issues.

## Overview

Timescale is an open-source database designed to make SQL scalable for time-series data. 
It is engineered up from PostgreSQL and packaged as a PostgreSQL extension, providing automatic partitioning across time and space (partitioning key), as well as full SQL support.

## Add a Timescale connection using the user interface

### **Navigate to the connection settings**

To navigate to the Timescale connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection2.png){ loading=lazy; width="1200px" }

2. Select Timescale database type.

    ![Selecting Timescale database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-timescale.png){ loading=lazy; width="1200px" }


### **Fill in the connection settings**

After navigating to the Timescale connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-timescale.png){ loading=lazy; width="1200px" }

| Timescale connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                               |
|---------------------------------|------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name                 |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters. |
| Engine Type                     | `postgresql_engine_type`                 | PostgreSQL engine type. Supports also a ${POSTGRESQL_ENGINE} configuration with a custom environment variable.                                                                                                                            |
| Host                            | `host`                                   | PostgreSQL host name. Supports also a ${POSTGRESQL_HOST} configuration with a custom environment variable.                                                                                                                                |
| Port                            | `port`                                   | PostgreSQL port name. The default port is 5432. Supports also a ${POSTGRESQL_PORT} configuration with a custom environment variable.                                                                                                      |
| Password                        | `password`                               | PostgreSQL database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                    |
| SSL mode                        | `sslmode`                                | PostgreSQL sslmode parameter. The default value is disabled. [See the PostgreSQL documentation for more information about using SSL.](https://jdbc.postgresql.org/documentation/ssl/)                                                     |  
| JDBC connection property        |                                          | Optional setting. DQOps supports using JDBC driver to access Timescale. [See the PostgreSQL documentation for JDBC connection parameter references.](https://jdbc.postgresql.org/documentation/use/)                                    |
    
DQOps allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.
    
For example:

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-envvar.jpg)
    
To add optional JDBC connection properties, just type the **JDBC connection property** and the **Value**. The value
can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
    
For example:

![Adding connection JDBC settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-JDBC-properties2.png){ loading=lazy; width="1200px" }
    
To remove the property, click the trash icon at the end of the input field.

After filling in the connection settings, click the **Test Connection** button to test the connection.

Click the **Save** connection button when the test is successful otherwise, you can check the details of what went wrong.

### **Import metadata using the user interface**

1. Import the selected data resources (source schemas and tables) by clicking on the **Import Tables** button next to
   the name of the source schema from which you want to import tables. 

    ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-schemas.png){ loading=lazy; width="1200px" }

2. Select the tables you want to import or import all tables using the buttons in the upper right corner.

    ![AImporting tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables.jpg)


Upon import, you will receive information that a new tables have been imported. You can then begin collecting basic statistics
and profiling data by running default data profiling checks. Simply click on the **Start profiling** button to initiate this process.

![Collect basic statistics and profile data with default profiling checks](https://dqops.com/docs/images/getting-started/collect-basic-statistics-and-profile-data.png)

!!! info "Automatically activated checks"

    Once new tables are imported, DQOps automatically activates [profiling and monitoring checks](../dqo-concepts/definition-of-data-quality-checks/index.md).
    These checks include row count, table availability, and checks detecting schema changes. The profiling checks are scheduled 
    to run at 1:00 a.m. on the 1st day of every month, and the monitoring checks are scheduled to run daily at 12:00 p.m.
    
    [**Profiling checks**](../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md) are designed to assess
    the initial data quality score of a data source. Profiling checks are also useful for exploring and experimenting with 
    various types of checks and determining the most suitable ones for regular data quality monitoring.
    
    [**Monitoring checks**](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md) are 
    standard checks that monitor the data quality of a table or column. They can also be referred to as **Data Observability** checks.
    These checks capture a single data quality result for the entire table or column.

## Add a Timescale connection using DQOps Shell

To add a connection run the following command in DQOps Shell.

```
dqo> connection add
```

Fill in the data you will be asked for.

```
Connection name (--name): connection1
Database provider type (--provider):
 [ 1] bigquery
 [ 2] clickhouse
 [ 3] databricks
 [ 4] db2
 [ 5] duckdb
 [ 6] hana
 [ 7] mariadb
 [ 8] mysql
 [ 9] oracle
 [10] postgresql
 [11] presto
 [12] questdb
 [13] redshift
 [14] snowflake
 [15] spark
 [16] sqlserver
 [17] teradata
 [18] trino
Please enter one of the [] values: 10
PostgreSQL engine type (--postgresql-engine):
 [ 1] postgresql
 [ 2] timescale
Please enter one of the [] values: 2
PostgreSQL host (--postgresql-host)[${POSTGRESQL_HOST}]: localhost
PostgreSQL port (--postgresql-port) [${POSTGRESQL_PORT}]: 65234
PostgreSQL user (--postgresql-user) [${POSTGRESQL_USER}]: testing
PostgreSQL password (--postgresql-password) [${POSTGRESQL_PASSWORD}]: xxx
Connection connecton1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=postgresql
--postgresql-engine=timescale
--postgresql-host=localhost
--postgresql-port=65234
--postgresql-user=testing
--postgresql-password=xxx
```

After adding connection run `table import -c=connection1` to select schemas and import tables.

DQOps will ask you to select the schema from which the tables will be imported.

You can also add the schema and table name as parameters to import tables in just a single step.

```
dqo> table import --connection={connection name}
--schema={schema name}
--table={table name}
```

DQOps supports the use of the asterisk character * as a wildcard when selecting schemas and tables, which can substitute
any number of characters. For example, use  pub* to find all schema a name with a name starting with "pub". The *
character can be used at the beginning, middle, or end of the name.

## Connections configuration files

Connection configurations are stored in the YAML files in the `./sources` folder. The name of the connection is also
the name of the folder where the configuration file is stored.

Below is a sample YAML file showing an example configuration of the Timescale data source connection.

``` yaml
apiVersion: dqo/v1
kind: source
spec:
  provider_type: postgresql
  postgresql:
    postgresql_engine_type: postgresql
    host: localhost
    port: 65234
    user: testing
    password: xxx
    properties:
      'connectTimeout ': 12
  incident_grouping:
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60
```

### **Reference of all connection parameters**
Complete documentation of all connection parameters used in the `spec.postgresql` node is
described in the reference section of the [PostgresqlParametersSpec](../reference/yaml/ConnectionYaml.md#postgresqlparametersspec)
YAML file format.

## Next steps

- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [complete list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.