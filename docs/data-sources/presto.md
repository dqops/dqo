# Presto
Read this guide to learn how to connect DQOps to Presto from the UI, command-line, or directly in YAML files. All parameters are documented.

## Overview

Presto is an open source SQL query engine that’s fast, reliable, and efficient at scale.

## Prerequisite credentials

You need a Presto.

## Add Presto connection using the user interface

### **Navigate to the connection settings**

To navigate to the Presto connection settings:

1. Go to Data Sources section and click **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png)

2. Select Presto database type.

    ![Selecting Presto database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-presto.png)


### **Fill in the connection settings**

After navigating to the Presto connection settings, you will need to fill in the connection details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-presto.png)

| Presto connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                               | 
|----------------------------|------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name            |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters. |
| Parallel jobs limit        |                                          | New limit. Null value will disable limit.                                                                                                                                                                                                 |
| Host                       | host                                     | Presto host name. Supports also a ${PRESTO_HOST} configuration with a custom environment variable.                                                                                                                                        |
| Port                       | port                                     | Presto port number. The default port is 8080. Supports also a ${PRESTO_PORT} configuration with a custom environment variable.                                                                                                            |
| Database                   | database                                 | Presto database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                            |
| User name                  | user                                     | Presto user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                |
| Password                   | password                                 | Presto database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                        |
| JDBC connection property   |                                          | Optional setting. DQOps supports using JDBC driver to access Presto. [JDBC Concepts.](https://docs.oracle.com/en/database/oracle/oracle-database/23/jjdbc/introducing-JDBC.html).                                                         |
    
DQOps allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.

For example:

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-envvar.jpg)

To add optional JDBC connection properties just type the **JDBC connection property** and the **Value**. The value
can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.

For example:

![Adding connection JDBC settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-JDBC-properties.jpg)

To remove the property click on the trash icon at the end of the input field.

After filling in the connection settings, click the **Test Connection** button to test the connection.

Click the **Save** connection button when the test is successful otherwise you can check the details of what went wrong.


### **Import metadata using the user interface**

When you add a new connection, it will appear in the tree view on the left, and you will be redirected to the Import Metadata screen.
Now we can import schemas and tables.

1. Import the selected data resources (source schemas and tables) by clicking on the **Import Tables** button next to
   the name of the source schema from which you want to import tables.

    ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-schemas.png)

2. Select the tables you want to import or import all tables using the buttons in the upper right corner.

    ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables.png)

When new tables are imported, DQOps automatically activates profiling and monitoring checks, such as row count,
table availability and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m.
By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks
or modify the schedule for newly imported tables.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables-advisor.png)


## Add Presto connection using DQOps Shell

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
 [ 6] presto
 [ 7] redshift
 [ 8] snowflake
 [ 9] spark
 [10] sqlserver
 [11] trino
Please enter one of the [] values: 6
Presto host name (--presto-host) [${PRESTO_HOST}]: localhost
Presto port number (--presto-port) [${PRESTO_PORT}]: 8080
Presto database name (--presto-database) [${PRESTO_DATABASE}]: default
Presto user name (--presto-user) [${PRESTO_USER}]: test
Presto user password (--presto-password) [${PRESTO_PASSWORD}]: xxx
Connection connection1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=presto
--presto-host=localhost
--presto-port=8080
--presto-database=default
--presto-user=test
--presto-password=xxx
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

Below is a sample YAML file showing an example configuration of the Presto data source connection.

``` yaml
apiVersion: dqo/v1
kind: source
spec:
  provider_type: presto
  presto:
    host: localhost
    port: 8080
    database: default
    user: testing
    password: xxx
  incident_grouping:
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60
```

## Next steps

- We have provided a variety of use cases which use openly available datasets [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [full list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you to calculate separate data quality KPI scores for different groups of rows.