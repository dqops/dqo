# Redshift

Amazon Redshift is a fully managed, petabyte-scale data warehouse service in the cloud from Amazon Web Services.  
Redshift can handle analytic workloads on big data sets stored by a column-oriented DBMS principle

## Prerequisite credentials

You need a Redshift account. Amazon Redshift uses an elastic IP address for the external IP address. An elastic IP 
address is a static IP address. In case of restrictions, you need to add the IP address used by DQO 
to [Allowed IP Addresses in Redshift Network Policies](https://docs.aws.amazon.com/redshift/latest/mgmt/managing-clusters-vpc.html).

## Adding Redshift connection using graphic interface

1. Go to Data Sources section and click **+ Add connection** button in the upper left corner.

    ![Adding connection](https://docs.dqo.ai/docs/images/working-with-dqo/adding-connection.jpg)

2. Select Redshift database type.

    ![Selecting Redshift database type](https://docs.dqo.ai/docs/images/working-with-dqo/adding-connection-redshift.jpg)

 Add connection settings.

    ![Adding connection settings](https://docs.dqo.ai/docs/images/working-with-dqo/connection-settings-redshift.jpg)

   | Redshift connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                             |
   |------------------------------|------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
   | Connection name              |                                          | The name of the connection that will be created in DQO. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters. |
   | Host                         | host                                     | Redshift host name. Supports also a ${REDSHIFT_HOST} configuration with a custom environment variable.                                                                                                                                  |
   | Port                         | port                                     | Redshift port name. The default port is 5439. Supports also a ${REDSHIFT_PORT} configuration with a custom environment variable.                                                                                                        |
   | Database                     | database                                 | Redshift database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                        |
   | User name                    | user                                     | Redshift user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                            |
   | Password                     | password                                 | Redshift database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                    |
   | Use SSL                      | ssl                                      | Connect to Redshift using SSL. The default value is false.                                                                                                                                                                              |
   | JDBC connection property     |                                          | Optional setting. DQO supports using JDBC driver to access Redshift. [See the Redshift documentation for JDBC connection parameter references.](https://docs.aws.amazon.com/redshift/latest/mgmt/jdbc20-install-driver.html)            |   
   
    DQO allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
    change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.
    
    For example:
    ![Adding connection settings - environmental variables](https://docs.dqo.ai/docs/images/working-with-dqo/connection-settings-redshift-envvar.jpg)
    
    To add optional JDBC connection properties just type the **JDBC connection property** and the **Value**. The value
    can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
    
    For example:
    ![Adding connection JDBC settings](https://docs.dqo.ai/docs/images/working-with-dqo/connection-settings-JDBC-properties.jpg)
    
    To remove the property click on the trash icon add the end of the input field.
    
    
4. After filling in the connection settings, click the **Test Connection** button to test the connection.
5. Click the **Save** connection button when the test is successful otherwise you can check the details of what went wrong.
6. Import the selected data resources (source schemas and tables) by clicking on the **Import Tables** button next to
    the name of the source schema from which you want to import tables.

    ![Adding connection](https://docs.dqo.ai/docs/images/working-with-dqo/importing-schemas.jpg)

7. Select the tables you want to import or import all tables using the buttons in the upper right corner.

    ![Adding connection](https://docs.dqo.ai/docs/images/working-with-dqo/importing-tables.jpg)

## Adding Redshift connection using DQO Shell

To add a connection run the following command in DQO Shell.
```
dqo.ai> connection add
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
Please enter one of the [] values: 4
Redshift host (--redshift-host)[${REDSHIFT_HOST}]: localhost
Redshift port (--redshift-port) [${REDSHIFT_PORT}]: 5439
Redshift database (--redshift-database) [${REDSHIFT_DATABASE}]: TESTING
Redshift user name (--redshift-username) [${REDSHIFT_USER}]: TESTING
Redshift password (--redshift-password) [${REDSHIFT_PASSWORD}]: test
Connection connecton1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo.ai> connection add --name=connection1
--provider=redshift
--redshift-host=localhost
--redshift-port=5439
--redshift-database=TESTING
--redshift-user=testing
--redshift-password=test
```

After adding connection run `table import -c=connection1` to select schemas and import tables.

DQO will ask you to select the schema from which the tables will be imported.

You can also add the schema and table name as a parameter to import tables in just a single step.

```
dqo.ai> table import --connection={connection name}
--schema={schema name}
--table={table name}
```
DQO supports the use of the asterisk character * as a wildcard when selecting schemas and tables, which can substitute
any number of characters. For example, use  pub* to find all schema a name with a name starting with "pub". The *
character can be used at the beginning, in the middle or at the end of the name.

## Connections configuration files

Connection configurations are stored in the YAML files in the `./sources` folder. The name of the connection is also
the name of the folder where the configuration file is stored.

Below is a sample YAML file showing an example configuration of the Redshift data source connection.

``` yaml
apiVersion: dqo/v1
kind: source
spec:
  provider_type: redshift
  redshift:
    host: redshift-cluster-2.cds5vq1bzgx5.us-east-1.redshift.amazonaws.com
    port: 5439
    database: testing
    user: testing
    password: test
    ssl: false
    properties:
      'connectTimeout': 15
  incident_grouping:
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60
```
