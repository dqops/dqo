---
title: How to activate data observability for Databricks
---
# How to activate data observability for Databricks
Read this guide to learn how to connect DQOps to Databricks from the UI, command-line interface, or directly in YAML files, and activate monitoring.

## Overview

Databricks is a unified, open analytics platform for building, deploying, 
sharing, and maintaining enterprise-grade data, analytics, and AI solutions at scale.

## Prerequisite credentials

To add Databricks data source connection to DQOps you need a Databricks SQL Warehouse instance.
It is also recommended to use an access token to connect an instance, so a permission to generate access token or a 
possession of a previously generated token is necessary.

## Add Databricks connection using the user interface

### **Navigate to the connection settings**

To navigate to the Databricks connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png){ loading=lazy; width="1200px" }

2. Select Databricks database type.

    ![Selecting Databricks database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-databricks2.png){ loading=lazy; width="1200px" }


### **Fill in the connection settings**

After navigating to the Databricks connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-databricks1.png){ loading=lazy; width="1200px" }

| Databricks connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                               | 
|--------------------------------|------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name                |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters. |
| Parallel jobs limit            |                                          | A limit on the number of jobs that can run simultaneously. Leave empty to disable the limit.                                                                                                                                              |
| Host                           | `host`                                   | Databricks host name. Supports also a ${DATABRICKS_HOST} configuration with a custom environment variable.                                                                                                                                |
| Port                           | `port`                                   | Databricks port number. The default port is 443. Supports also a ${DATABRICKS_PORT} configuration with a custom environment variable.                                                                                                     |
| Catalog                        | `catalog`                                | Databricks catalog name. Supports also a ${DATABRICKS_CATALOG} configuration with a custom environment variable.                                                                                                                          |                                                                                                                           
| User name                      | `user`                                   | Databricks user name. Supports also a ${DATABRICKS_USER} configuration with a custom environment variable.                                                                                                                                |
| Password                       | `password`                               | Databricks database password. Supports also a ${DATABRICKS_PASSWORD} configuration with a custom environment variable.                                                                                                                    |
| Http Path                      | `http_path`                              | Databricks http path to the warehouse. For example: /sql/1.0/warehouses/<warehouse instance id>. Supports also a ${DATABRICKS_HTTP_PATH} configuration with a custom environment variable.                                                |
| Access token (optional)        | `access_token`                           | Databricks access token (short time). Supports also a ${DATABRICKS_ACCESS_TOKEN} configuration with a custom environment variable.                                                                                                        |
| JDBC connection property       |                                          | Optional setting. DQOps supports using JDBC driver to access Databricks. [See the Databricks documentation for JDBC connection parameter references](https://docs.databricks.com/en/integrations/jdbc/index.html).                        |
    
DQOps allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.

For example:

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-envvar.jpg)

To add optional JDBC connection properties, just type the **JDBC connection property** and the **Value**. The value
can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.

For example:

![Adding connection JDBC settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-JDBC-properties2.png){ loading=lazy; width="1200px" }

To remove the property click on the trash icon at the end of the input field.

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

When new tables are imported, DQOps automatically activates profiling and monitoring checks, such as row count, 
table availability, and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m. 
By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks 
or modify the schedule for newly imported tables.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables-advisor.png){ loading=lazy; width="1200px" }


## Add Databricks connection using DQOps Shell

To add a connection run the following command in DQOps Shell.

```
dqo> connection add
```

Fill in the data you will be asked for.

```
Connection name (--name): connection1
Database provider type (--provider):
 [ 1] bigquery
 [ 2] databricks
 [ 3] mysql
 [ 4] oracle
 [ 5] postgresql
 [ 6] duckdb
 [ 7] presto
 [ 8] redshift
 [ 9] snowflake
 [10] spark
 [11] sqlserver
 [12] trino
Please enter one of the [] values: 2
Databricks host name (--databricks-host) [${DATABRICKS_HOST}]: <databricks_host>.azuredatabricks.net
Databricks port number (--databricks-port) [${DATABRICKS_PORT}]: 443
Databricks catalog name (--databricks-catalog) [${DATABRICKS_CATALOG}]: samples
Databricks user name (--databricks-user) [${DATABRICKS_USER}]: 
Databricks user password (--databricks-password) [${DATABRICKS_PASSWORD}]: 
Databricks http path (--databricks-http-path) [${DATABRICKS_HTTP_PATH}]: /sql/1.0/warehouses/<warehouse_id>
Connection connection1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=databricks
--databricks-host=<databricks_host>.azuredatabricks.net
--databricks-port=443
--databricks-catalog=samples
--databricks-http-path=/sql/1.0/warehouses/<warehouse_id>
--databricks-access-token=<access_token>
```

After adding connection run `table import -c=connection1` to select schemas and import tables.

DQOps will ask you to select the schema from which the tables will be imported.

You can also add the schema and table name as a parameter to import tables in just a single step.

```
dqo> table import --connection={connection name}
--schema={schema name}
--table={table name}
```

DQOps supports the use of the asterisk character * as a wildcard when selecting schemas and tables, which can substitute
any number of characters. For example, use  pub* to find all schema a name with a name starting with "pub". The *
character can be used at the beginning, in the middle or at the end of the name.

## Connections configuration files

Connection configurations are stored in the YAML files in the `./sources` folder. The name of the connection is also
the name of the folder where the configuration file is stored.

Below is a sample YAML file showing an example configuration of the Databricks data source connection.

``` yaml
apiVersion: dqo/v1
kind: source
spec:
  provider_type: databricks
  databricks:
    host: <databricks_host>.azuredatabricks.net
    catalog: samples
    http_path: /sql/1.0/warehouses/<warehouse_id>
    access_token: <access_token>
    database: samples
  incident_grouping:
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60
```

### **Reference of all connection parameters**
Complete documentation of all connection parameters used in the `spec.databricks` node is
described in the reference section of the [DatabricksParametersSpec](../reference/yaml/ConnectionYaml.md#databricksparametersspec)
YAML file format.

## Next steps

- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [full list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.