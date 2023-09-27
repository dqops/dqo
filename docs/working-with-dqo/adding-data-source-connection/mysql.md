# MySQL

MySQL is an open source, easy-to-use and flexible SQL database management system developed, distributed, and supported by Oracle Corporation.
MySQL is generally faster and more efficient than other relational database management systems (RDBMS), so it is often the preferred
choice for applications that require high performance.

## Prerequisite credentials

You need a MySQL account. Use the TCP/IP Properties (IP Addresses Tab) dialog box to configure the TCP/IP protocol options
for a specific IP address. In case of restrictions, you need to add the IP address used by DQO to [NDB Cluster TCP/IP Connections Using Direct Connections](https://dev.mysql.com/doc/refman/8.0/en/mysql-cluster-tcp-definition-direct.html).

## Adding MySQL connection using the graphical interface

1. Go to Data Sources section and click **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png)

2. Select SQL Server database type.

    ![Selecting SQL Server database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-mysql.png)

3. Add connection settings.

    ![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-mysql.png)

    | MySQL connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                                                             | 
    |---------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | Connection name           |                                          | The name of the connection that will be created in DQO. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters.                                 |
    | Host                      | host                                     | MySQL host name. Supports also a ${MYSQL_HOST} configuration with a custom environment variable.                                                                                                                                                                        |
    | Port                      | port                                     | MySQL port name. The default port is 3306 Supports also a ${MYSQL_PORT} configuration with a custom environment variable.                                                                                                                                               |
    | Database                  | database                                 | MySQL database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                                           |
    | User name                 | user                                     | MySQL user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                                               |
    | Password                  | password                                 | MySQL database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                                       |
    | Options                   | options                                  | MySQL connection 'options' initialization parameter. For example, setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${MYSQL_OPTIONS} configuration with a custom environment variable. |
    | sslmode                   | ssl_mode                                 | MySQL connection sslmode parameter. [See the MySQL documentation for more information about using sslMode parameter.](https://dev.mysql.com/doc/connector-j/8.1/en/connector-j-connp-props-security.html#cj-conn-prop_sslMode)                                          |
    | JDBC connection property  |                                          | Optional setting. DQO supports using JDBC driver to access MySQL. [JDBC Concepts.](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-usagenotes-basic.html).                                                                                                     |
    
    DQO allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
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

8. When new tables are imported, DQO automatically enables profiling and monitoring checks, such as row count, table availability and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m. By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks or modify the schedule for newly imported tables.

    ![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables-advisor.png)

## Adding MySQL connection using DQO Shell

To add a connection run the following command in DQO Shell.

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
[ 6] mysql
[ 7] oracle
Please enter one of the [] values: 6
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
--mysql-host=localhost
--mysql-port=3306
--mysql-database=testing
--mysql-username=test
--mysql-password=xxx
```

After adding connection run `table import -c=connection1` to select schemas and import tables.

DQO will ask you to select the schema from which the tables will be imported.

You can also add the schema and table name as a parameter to import tables in just a single step.

```
dqo> table import --connection={connection name}
--schema={schema name}
--table={table name}
```
DQO supports the use of the asterisk character * as a wildcard when selecting schemas and tables, which can substitute
any number of characters. For example, use  pub* to find all schema a name with a name starting with "pub". The *
character can be used at the beginning, in the middle or at the end of the name.

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
  incident_grouping:
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60
```
