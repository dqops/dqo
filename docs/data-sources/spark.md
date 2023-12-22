# Spark

Apache Spark is an open-source unified analytics engine for large-scale data processing.

## Prerequisite credentials

You need a Spark Thrift Server to be running that provides a connection through JDBC to data in Spark.

## Adding Spark connection using the user interface

1. Go to Data Sources section and click **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png)

2. Select Spark database type.

    ![Selecting Spark database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-spark.png)

3. Add connection settings.

    ![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-spark1.png)

    | Spark connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                                             | 
    |---------------------------|------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | Connection name           |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters.               |
    | Parallel jobs limit       |                                          | New limit. Null value will disable limit.                                                                                                                                                                                                               |
    | Host                      | host                                     | Spark host name. Supports also a ${SPARK_HOST} configuration with a custom environment variable.                                                                                                                                                        |
    | Port                      | port                                     | Spark port name. The default port is 10000. Supports also a ${SPARK_PORT} configuration with a custom environment variable.                                                                                                                             |
    | User name                 | user                                     | Spark user name. The value can be in the ${SPARK_USER} format to use dynamic substitution.                                                                                                                                                              |
    | Password                  | password                                 | Spark database password. The value can be in the ${SPARK_PASSWORD} format to use dynamic substitution.                                                                                                                                                  |
    | Options                   | options                                  | Spark connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes.                                                                     |
    | Initialization SQL        | initialization_sql                       | Custom SQL that is executed after connecting to Spark. This SQL script can configure the default language, for example: alter session set NLS_DATE_FORMAT='YYYY-DD-MM HH24:MI:SS                                                                        |
    | JDBC connection property  |                                          | Optional setting. DQOps supports using JDBC driver to access Spark. [JDBC Concepts.](https://docs.oracle.com/en/database/oracle/oracle-database/23/jjdbc/introducing-JDBC.html).                                                                        |
    
    DQOps allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
    change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.

    For example:

    ![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-envvar.jpg)

    To add optional JDBC connection properties just type the **JDBC connection property** and the **Value**. The value
    can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.

    For example:

    ![Adding connection JDBC settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-JDBC-properties.jpg)

    To remove the property click on the trash icon at the end of the input field.

4. After filling in the connection settings, click the **Test Connection** button to test the connection.
5. Click the **Save** connection button when the test is successful otherwise you can check the details of what went wrong.
6. Import the selected data resources (source schemas and tables) by clicking on the **Import Tables** button next to
   the name of the source schema from which you want to import tables.

    ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-schemas.png)

7. Select the tables you want to import or import all tables using the buttons in the upper right corner.

    ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables.png)

8. When new tables are imported, DQOps automatically enables profiling and monitoring checks, such as row count, table availability and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m. By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks or modify the schedule for newly imported tables.

    ![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables-advisor.png)

## Adding Spark connection using DQOps Shell

To add a connection run the following command in DQOps Shell.

```
dqo> connection add
```

Fill in the data you will be asked for.

```
Connection name (--name): connection1
Database provider type (--provider):
 [ 1] bigquery
 [ 2] snowflake
 [ 3] postgresql
 [ 4] redshift
 [ 5] sqlserver
 [ 6] presto
 [ 7] trino
 [ 8] mysql
 [ 9] oracle
 [10] spark
Please enter one of the [] values: 10
Spark host name (--spark-host) [${SPARK_HOST}]: localhost
Spark port number (--spark-port) [${SPARK_PORT}]: 10000
Spark user name (--spark-user) [${SPARK_USER}]: 
Spark user password (--spark-password) [${SPARK_PASSWORD}]: 
Connection connection1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=spark
--spark-host=localhost
--spark-port=10000
--spark-user=test
--spark-password=xxx
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

Below is a sample YAML file showing an example configuration of the Spark data source connection.

``` yaml
apiVersion: dqo/v1
kind: source
spec:
  provider_type: spark
  spark:
    host: localhost
    port: 10000
    user: testing
    password: xxx
  incident_grouping:
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60
```