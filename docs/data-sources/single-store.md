---
title: How to Monitor SingleStoreDB? Data Observability Configuration
---
# How to Monitor SingleStoreDB? Data Observability Configuration
Learn how to monitor SingleStoreDB and enable data observability to detect schema changes, data anomalies, volume fluctuations, and data quality issues.

## Overview

SingleStoreDB is a distributed SQL database that offers high-throughput transactions (inserts and upserts), low-latency analytics and context from real-time vector data.

## Prerequisite credentials

To add SingleStoreDB data source connection to DQOps you need a SingleStore account. 

## Add a SingleStoreDB connection using the user interface

### **Navigate to the connection settings**

To navigate to the SingleStoreDB connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

   ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection2.png){ loading=lazy; width="1200px" }

2. Select SingleStoreDB database type.

   ![Selecting SingleStoreDB database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-singlestore1.png){ loading=lazy; width="1200px" }

### **Fill in the connection settings**

After navigating to the SingleStoreDB connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-singlestore1.png)

| SingleStoreDB connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                                                                                             | 
|-----------------------------------|------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name                   |                                          | The name of the connection that will be created in DQO. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters.                                                                 |
| Parallel jobs limit               |                                          | A limit on the number of jobs that can run simultaneously. Leave empty to disable the limit.                                                                                                                                                                                                            |
| Engine Type                       | `mysql_engine_type`                      | MySQL engine type. Supports also a ${MYSQL_ENGINE} configuration with a custom environment variable.                                                                                                                                                                                                    |
| Loadbalancing Mode                | `load_balancing_mode`                    | SingleStoreDB Failover and Load-Balancing Modes for Single Store DB. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                           |
| Host Descriptions                 | `host_descriptions`                      | SingleStoreDB Host descriptions. Supports also a ${SINGLE_STORE_HOST_DESCRIPTIONS} configuration with a custom environment variable.                                                                                                                                                                    |
| Schema                            | `schema`                                 | SingleStoreDB database/schema name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                                                            |
| User name                         | `user`                                   | MySQL (SingleStoreDB) user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                                                               |
| Password                          | `password`                               | MySQL (SingleStoreDB) database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                                                       |
| Use SSL                           | `use_ssl`                                | Force enables SSL/TLS on the connection. Supports also a ${SINGLE_STORE_USE_SSL} configuration with a custom environment variable.                                                                                                                                                                      |
| JDBC connection property          |                                          | Optional setting. DQOps supports using JDBC driver to access MySQL. [See the SingleStore documentation for JDBC connection parameter references](https://docs.singlestore.com/cloud/developer-resources/connect-with-application-development-tools/connect-with-java-jdbc/the-singlestore-jdbc-driver/) |

DQOps allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.

For example:

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-envvar.jpg)

To add optional JDBC connection properties, just type the **JDBC connection property** and the **Value**. The value
can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.

For example:

![Adding connection JDBC settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-JDBC-properties2.png)

To remove the property, click the trash icon at the end of the input field.

After filling in the connection settings, click the **Test Connection** button to test the connection.

Click the **Save** connection button when the test is successful otherwise, you can check the details of what went wrong.

### **Import metadata using the user interface**

When you add a new connection, it will appear in the tree view on the left, and you will be redirected to the Import Metadata screen.
Now we can import schemas and tables.

1. Import the selected data resources (source schemas and tables) by clicking on the **Import Tables** button next to
   the name of the source schema from which you want to import tables.

   ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-schemas.png)

2. Select the tables you want to import or import all tables using the buttons in the upper right corner.

   ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables.png)

Upon import, you will receive information that a new tables have been imported. You can then begin collecting basic statistics
and profiling data by running default data profiling checks. Simply click on the **Start profiling** button to initiate this process.

![Collect basic statistics and profile data with default profiling checks](https://dqops.com/docs/images/getting-started/collect-basic-statistics-and-profile-data.png)

!!! info "Automatically activated checks"

    Once new tables are imported, DQOps automatically activates [profiling and monitoring checks](../dqo-concepts/definition-of-data-quality-checks/index.md) which are which are pre-enabled by [data quality policies](../dqo-concepts/data-observability.md#automatic-activation-of-checks).
    These checks detect volume anomalies, data freshness anomalies, empty tables, table availability, schema changes, anomalies in the count of distinct values, and null percent anomalies. The profiling checks are scheduled 
    to run at 12:00 p.m. on the 1st day of every month, and the monitoring checks are scheduled to run daily at 12:00 p.m.
    
    [**Profiling checks**](../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md) are designed to assess
    the initial data quality score of a data source. Profiling checks are also useful for exploring and experimenting with 
    various types of checks and determining the most suitable ones for regular data quality monitoring.
    
    [**Monitoring checks**](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md) are 
    standard checks that monitor the data quality of a table or column. They can also be referred to as **Data Observability** checks.
    These checks capture a single data quality result for the entire table or column.

## Add a SingleStoreDB connection using DQOps Shell

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
Please enter one of the [] values: 8
MySQL engine type (--mysql-engine):
 [ 1] mysql
 [ 2] singlestoredb
Please enter one of the [] values: 2
Single Store DB balancing mode (--single-store-load-balancing-mode):
 [ 1] none
 [ 2] sequential
 [ 3] loadbalance
Please enter one of the [] values: 1
Single Store DB host descriptions (--single-store-host-descriptions) [${SINGLE_STORE_HOST_DESCRIPTIONS}]: <host>:<port>
Single Store DB schema name (--single-store-schema) [${SINGLE_STORE_SCHEMA}]: database_0c0ab
MySQL user name (--mysql-user) [${MYSQL_USER}]: user-12345
MySQL user password (--mysql-password) [${MYSQL_PASSWORD}]: <single-store-password>
Connection connecton1 was successfully added.
Run 'table import -c=connection1' to import tables.
dqo> 

```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=mysql
--mysql-engine=singlestoredb
--single-store-load-balancing-mode=none
--single-store-host-descriptions=<host>:<port>
--single-store-schema=database_0c0ab
--mysql-username=user-12345
--mysql-password=<single-store-password>
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

Below is a sample YAML file showing an example configuration of the MySQL data source connection.

``` yaml
apiVersion: dqo/v1
kind: source
spec:
  provider_type: mysql
  mysql:
    database: def
    user: user-12345
    password: <single-store-password>
    single_store_db_parameters_spec:
      load_balancing_mode: none
      host_descriptions:
        - "<host>:<port>"
      schema: database_0c0ab
      use_ssl: true
    mysql_engine_type: singlestoredb
  incident_grouping:
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60
```

### **Reference of all connection parameters**
Complete documentation of all connection parameters used in the `spec.mysql` node is
described in the reference section of the [MysqlParametersSpec](../reference/yaml/ConnectionYaml.md#mysqlparametersspec)
YAML file format.

## Next steps

- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [complete list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.