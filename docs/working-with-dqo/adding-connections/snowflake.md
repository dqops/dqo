# Snowflake

Snowflake is a cloud-based data warehouse that separates storage from compute resources and requires little maintenance.

## Prerequisite credentials

You need a Snowflake account. By default, Snowflake instances are open to any IP address unless you configure network 
policies that restrict this communication. In case of restrictions you need to add the IP address used by DQO to 
[Allowed IP Addresses in Snowflake Network Policies](https://docs.snowflake.com/en/user-guide/network-policies#modifying-network-policies).

## Adding Snowflake connection using graphic interface

1. Go to Data Sources section and click **+ Add connection** button in the upper left corner.

    ![Adding connection](https://docs.dqo.ai/docs/images/working-with-dqo/adding-connection.jpg)

2. Select Snowflake database type.

     ![Selecting Snowflake database type](https://docs.dqo.ai/docs/images/working-with-dqo/adding-connection-snowflake.jpg)

3. Add connection settings.

    ![Adding connection settings](https://docs.dqo.ai/docs/images/working-with-dqo/connection-settings-snowflake.jpg)

    | Snowflake connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                                              | 
    |-------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | Connection name               |                                          | The name of the connection that will be created in DQO. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters.                  |
    | Account                       | account                                  | Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;. Supports also a ${SNOWFLAKE_ACCOUNT} configuration with a custom environment variable. |
    | Warehouse                     | warehouse                                | Optional setting. Snowflake warehouse name.  Supports also a ${SNOWFLAKE_WAREHOUSE} configuration with a custom environment variable.                                                                                                                    |
    | Database                      | database                                 | Snowflake database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                        |
    | User name                     | user                                     | Snowflake user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                            |
    | Password                      | password                                 | Snowflake database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                    |
    | Role                          | role                                     | Optional setting. Snowflake role name.  Supports also ${SNOWFLAKE_ROLE} configuration with a custom environment variable.                                                                                                                                |
    | JDBC connection property      |                                          | Optional setting. DQO supports using JDBC driver to access Snowflake. [See the Snowflake documentation for JDBC connection parameter references.](https://docs.snowflake.com/en/developer-guide/jdbc/jdbc-parameters)                                    |

    DQO allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
    change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.

    For example:
    ![Adding connection settings - environmnetal variables](https://docs.dqo.ai/docs/images/working-with-dqo/connection-settings-snowflake-envvar.jpg)

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

## Adding Snowflake connection using DQO Shell

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
Please enter one of the [] values: 2
Snowflake account name (--snowflake-account) [${SNOWFLAKE_ACCOUNT}]: xx-kh8138
Snowflake warehouse name (--snowflake-warehouse) [${SNOWFLAKE_WAREHOUSE}]: OMPUTE_WH
Snowflake database name (--snowflake-database) [${SNOWFLAKE_DATABASE}]: TESTING
Snowflake user name (--snowflake-user) [${SNOWFLAKE_USER}]: john
Snowflake user password (--snowflake-password) [${SNOWFLAKE_PASSWORD}]: xxx
Connection connecton1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo.ai> connection add --name=connection1 
--provider=snowflake 
--snowflake-account=xx-kh8138 
--snowflake-warehouse=COMPUTE_WH
--snowflake-database=TESTING
--snowflake-user=john
--snowflake-password=xxx
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

Connection configurations are stored in YAML files in the `./userhome/sources` folder. The name of the connection is also
the name of the folder where the configuration file is stored.

Below is a sample YAML file showing an example configuration of the Snowflake data source connection.


``` yaml
apiVersion: dqo/v1
kind: source
spec:
  provider_type: snowflake
  snowflake:
    account: xx-kh8138
    warehouse: COMPUTE_WH
    database: TESTING
    user: john
    password: xxx
    properties:
      loginTimeout: 55
      queryTimeout: 20
```