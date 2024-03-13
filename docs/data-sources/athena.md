# Athena
Read this guide to learn how to connect DQOps to Amazon Athena from the UI, command-line interface, or directly in YAML files. All parameters are documented.

## Overview

Amazon Athena is a serverless, interactive analytics service built on open-source
frameworks, that makes it easy to analyze data in Amazon S3 using standard SQL.

## Prerequisite credentials

To add Athena data source connection to DQOps you need the following:

- An AWS account
- An S3 bucket where the data will be written
- An AWS Lake Formation database where tables will be created (one per stream)
- AWS credentials in the form of either the pair Access key ID / Secret key ID or a role with the following permissions:
    - Writing objects in the S3 bucket
    - Updating of the Lake Formation database

## Add Athena connection using the user interface

### **Navigate to the connection settings**

To navigate to the Athena connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png)

2. Select Athena database type.

    ![Selecting Athena database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-athena.png)


### **Fill in the connection settings**

After navigating to the Athena connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-athena.png)

| Athena connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                               | 
|----------------------------|------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name            |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters. |
| Parallel jobs limit        |                                          | New limit. A null value will disable the limit.                                                                                                                                                                                                 |
| Trino engine type          | trino_engine_type                        | Trino engine type. Supports also a ${TRINO_ENGINE} configuration with a custom environment variable.                                                                                                                                      |
| Athena authentication mode | athena_authentication_mode               | The authentication mode for AWS Athena. Supports also a ${ATHENA_AUTHENTICATION_MODE} configuration with a custom environment variable.                                                                                                   |
| Athena AccessKeyId         | athena_access_key_id                     | The AWS access key id for the IAM that can access the Athena.                                                                                                                                                                             |
| Athena SecretAccessKey     | athena_secret_access_key                 | The AWS secret access key for the IAM that can access the Athena.                                                                                                                                                                         |
| Athena region              | athena_region                            | The AWS Region where queries will be run. Supports also a ${ATHENA_REGION} configuration with a custom environment variable.                                                                                                              |
| Athena WorkGroup           | athena_work_group                        | The workgroup in which queries will run. Supports also a ${ATHENA_WORK_GROUP} configuration with a custom environment variable.                                                                                                           |
| Athena OutputLocation      | athena_output_location                   | The location in Amazon S3 where query results will be stored. Supports also a ${ATHENA_OUTPUT_LOCATION} configuration with a custom environment variable.                                                                                 |
| Catalog                    | catalog                                  | The catalog that contains the databases and the tables that will be accessed with the driver. Supports also a ${TRINO_CATALOG} configuration with a custom environment variable.                                                          |
| JDBC connection property    |                                          | Optional setting. DQOps supports using JDBC driver to access Athena. [JDBC Concepts.](https://docs.oracle.com/en/database/oracle/oracle-database/23/jjdbc/introducing-JDBC.html).                                                         |
    
DQOps allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.

For example:

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-envvar.jpg)

To add optional JDBC connection properties, just type the **JDBC connection property** and the **Value**. The value
can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.

For example:

![Adding connection JDBC settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-JDBC-properties2.png)

To remove the property click on the trash icon at the end of the input field.

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

When new tables are imported, DQOps automatically activates profiling and monitoring checks, such as row count,
table availability, and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m.
By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks,
or modify the schedule for newly imported tables.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables-advisor.png)


## Add Athena connection using DQOps Shell

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
Please enter one of the [] values: 11
Trino engine type (--trino-engine):
 [ 1] trino
 [ 2] athena
Athena authentication mode (--athena-authentication-mode):
 [ 1] iam
 [ 2] default_credentials
Please enter one of the [] values: 1
 AWS AccessKeyId (--trino-user) [${TRINO_USER}]: <aws access key id>
 AWS SecretAccessKey (--trino-password) [${TRINO_PASSWORD}]: <aws secret acces key>
 (--athena-region) [${ATHENA_REGION}]: us-east-1
 (--trino-catalog) [${TRINO_CATALOG}]: awsdatacatalog
 (--athena-work-group) [${ATHENA_WORK_GROUP}]: primary
 (--athena-output-location) [${ATHENA_OUTPUT_LOCATION}]: s3://you_bucket_name/a_path
Connection connection1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=trino
--trino-engine=athena
--athena-authentication-mode=iam
--trino-user=<aws access key id>
--trino-password=<aws secret acces key>
--athena-region=us-east-1
--trino-catalog=awsdatacatalog
--athena-work-group=primary
--athena-output-location=s3://you_bucket_name/a_path
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

Below is a sample YAML file showing an example configuration of the Athena data source connection.

``` yaml
apiVersion: dqo/v1
kind: source
spec:
  provider_type: trino
  trino:
    trino_engine_type: athena
    user: <aws access key id>
    password: <aws secret acces key>
    athena_authentication_mode: iam
    athena_region: us-east-1
    catalog: awsdatacatalog
    athena_work_group: primary
    athena_output_location: s3://you_bucket_name/a_path
  incident_grouping:
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60
```

### **Reference of all connection parameters**
Complete documentation of all connection parameters used in the `spec.trino` node is
described in the reference section of the [TrinoParametersSpec](../reference/yaml/ConnectionYaml.md#trinoparametersspec)
YAML file format.


## Next steps

- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [full list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.