---
title: How to Monitor MySQL? Data Observability Configuration
---
# How to Monitor MySQL? Data Observability Configuration
Learn how to monitor MySQL and enable data observability to detect schema changes, data anomalies, volume fluctuations, and data quality issues.

## Overview

MySQL is an open source, easy-to-use and flexible SQL database management system developed, distributed, and supported by Oracle Corporation.
MySQL is generally faster and more efficient than other relational database management systems (RDBMS), so it is often the preferred
choice for applications that require high performance.

## Prerequisite credentials

To add MySQL data source connection to DQOps you need a MySQL account. 

Use the TCP/IP Properties (IP Addresses Tab) dialog box to configure the TCP/IP protocol options for a specific IP address. 

In case of restrictions, you need to add the IP address used by DQOps to [NDB Cluster TCP/IP Connections Using Direct Connections](https://dev.mysql.com/doc/refman/8.0/en/mysql-cluster-tcp-definition-direct.html).

## Add a MySQL connection using the user interface

### **Navigate to the connection settings**

To navigate to the MySQL connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection2.png){ loading=lazy; width="1200px" }

2. Select MySQL database type.

    ![Selecting MySQL database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-mysql.png){ loading=lazy; width="1200px" }

### **Fill in the connection settings**

After navigating to the MySQL connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-mysql.png){ loading=lazy; width="1200px" }

| MySQL connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                             | 
|---------------------------|------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name           |                                          | The name of the connection that will be created in DQO. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters. |
| Parallel jobs limit       |                                          | A limit on the number of jobs that can run simultaneously. Leave empty to disable the limit.                                                                                                                                            |
| Engine Type               | `mysql_engine_type`                        | MySQL engine type. Supports also a ${MYSQL_ENGINE} configuration with a custom environment variable.                                                                                                                                    |
| Host                      | `host`                                     | MySQL host name. Supports also a ${MYSQL_HOST} configuration with a custom environment variable.                                                                                                                                        |
| Port                      | `port`                                     | MySQL port name. The default port is 3306 Supports also a ${MYSQL_PORT} configuration with a custom environment variable.                                                                                                               |
| Database                  | `database`                                 | MySQL database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                           |
| User name                 | `user`                                     | MySQL user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                               |
| Password                  | `password`                                 | MySQL database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                       |
| sslmode                   | `ssl_mode`                                 | MySQL connection sslmode parameter. [See the MySQL documentation for more information about using sslMode parameter.](https://dev.mysql.com/doc/connector-j/8.1/en/connector-j-connp-props-security.html#cj-conn-prop_sslMode)          |
| JDBC connection property  |                                          | Optional setting. DQOps supports using JDBC driver to access MySQL. [JDBC Concepts.](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-usagenotes-basic.html).                                                                   |
    
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

When you add a new connection, it will appear in the tree view on the left, and you will be redirected to the Import Metadata screen.
Now we can import schemas and tables.

1. Import the selected data resources (source schemas and tables) by clicking on the **Import Tables** button next to
   the name of the source schema from which you want to import tables.

    ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-schemas.png){ loading=lazy; width="1200px" }

2. Select the tables you want to import or import all tables using the buttons in the upper right corner.

    ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables.png){ loading=lazy; width="1200px" }


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

## Add a MySQL connection using DQOps Shell

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
Please enter one of the [] values: 1
MySQL host (--mysql-host)[${MYSQL_HOST}]: localhost
MySQL port (--mysql-port) [${MYSQL_PORT}]: 3306
MySQL database(--mysql-database) [${MYSQL_DATABASE}]: testing
MySQL user name (--mysql-user) [${MYSQL_USER}]: test
MySQL password (--mysql-password) [${MYSQL_PASSWORD}]: xxx
Connection connecton1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=mysql
--mysql-engine=mysql
--mysql-host=localhost
--mysql-port=3306
--mysql-database=testing
--mysql-username=test
--mysql-password=xxx
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
    host: localhost
    port: 3306
    user: testing
    database: testing
    password: xxx
    properties:
      cacheDefaultTimeZone: "false"
    mysql_engine_type: mysql
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