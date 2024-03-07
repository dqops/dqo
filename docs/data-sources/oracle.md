# Oracle
Read this guide to learn how to connect DQOps to Oracle from the UI, command-line interface, or directly in YAML files. All parameters are documented.

## Overview

Oracle Database is a robust object relational database that provides efficient and effective solutions for database 
users such as delivering high performance, protecting users from unauthorized access, and enabling fast failure recovery.

## Add Oracle connection using the user interface

### **Navigate to the connection settings**

To navigate to the Oracle connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png)

2. Select Oracle database type.

    ![Selecting Oracle database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-oracle.png)


### **Fill in the connection settings**

After navigating to the Oracle connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-oracle2.png)

| Oracle connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                               | 
|----------------------------|------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name            |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters. |
| Host                       | host                                     | Oracle host name. Supports also a ${ORACLE_HOST} configuration with a custom environment variable.                                                                                                                                        |
| Port                       | port                                     | Oracle port name. The default port is 1521. Supports also a ${ORACLE_PORT} configuration with a custom environment variable.                                                                                                              |
| Database                   | database                                 | Oracle database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                            |
| User name                  | user                                     | Oracle user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                |
| Password                   | password                                 | Oracle database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                        |
| Initialization SQL         | initialization_sql                       | Custom SQL that is executed after connecting to Oracle. This SQL script can configure the default language, for example: alter session set NLS_DATE_FORMAT='YYYY-DD-MM HH24:MI:SS                                                         |
| JDBC connection property   |                                          | Optional setting. DQOps supports using JDBC driver to access Oracle. [JDBC Concepts.](https://docs.oracle.com/en/database/oracle/oracle-database/23/jjdbc/introducing-JDBC.html).                                                         |
    
DQOps allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.

For example:

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-envvar.jpg)

To add optional JDBC connection properties, just type the **JDBC connection property** and the **Value**. The value
can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.

For example:

![Adding connection JDBC settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-JDBC-properties.jpg)

To remove the property click on the trash icon at the end of the input field.

After filling in the connection settings, click the **Test Connection** button to test the connection.

Click the **Save** connection button when the test is successful otherwise, you can check the details of what went wrong.

### **Import metadata using the user interface**

1. Import the selected data resources (source schemas and tables) by clicking on the **Import Tables** button next to
   the name of the source schema from which you want to import tables.

    ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-schemas.png)

2. Select the tables you want to import or import all tables using the buttons in the upper right corner.

    ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables.png)


When new tables are imported, DQOps automatically activates profiling and monitoring checks, such as row count,
table availability, and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m.
By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks,
or modify the schedule for newly imported tables.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables-advisor.png)


## Add Oracle connection using DQOps Shell

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
Please enter one of the [] values: 4
Oracle host name (--oracle-host)[${ORACLE_HOST}]: localhost
Oracle port number (--oracle-port)[${ORACLE_port}]: 1521
Oracle database name (--oracle-database) [${ORACLE_DATABASE}]: testing
Oracle user name (--oracle-user) [${ORACLE_USER}]: test
Oracle user password (--oracle-password) [${ORACLE_PASSWORD}]: xx
Connection connecton1 was successfully added.
Run 'table import -c=connection1' to import tables.


```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=oracle
--oracle-host=localhost
--oracle-port=1521
--oracle-database=testing
--oracle-user=test
--oracle-password=xxx
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

Below is a sample YAML file showing an example configuration of the Oracle data source connection.

``` yaml
apiVersion: dqo/v1
kind: source
spec:
  provider_type: oracle
  oracle:
    host: localhost
    port: 1521
    user: testing
    database: testing
    password: xxx
    properties:
      cacheDefaultTimeZone: "false"
  incident_grouping:
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60
```

### **Reference of all connection parameters**
Complete documentation of all connection parameters used in the `spec.oracle` node is
described in the reference section of the [OracleParametersSpec](../reference/yaml/ConnectionYaml.md#oracleparametersspec)
YAML file format.


## Next steps

- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [full list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.