# DQOps user home
Read this guide to understand how DQOps stores the configuration of data sources and activated data quality checks locally, allowing to version files in Git.

## What is the DQOps user home?

DQOps depends on the local file system to store the configuration files and the data files.
The folder on the disk where the files are stored is called a `DQOps user home`.

Five most important kinds of files are stored in the `DQOps user home`.

- **YAML** files with the configuration of activated data quality checks on tables, data source connection settings and several
  other configuration files used for defining custom [sensors](definition-of-data-quality-sensors.md), [rules](definition-of-data-quality-rules.md)
  and [checks](definition-of-data-quality-checks/index.md).

- **Shared credentials** are just regular files, both text and binary. Shared credentials can be referenced in the YAML files.
  They are synchronized to the DQOps Cloud Data Lake, but they are ignored in the *.gitignore* file, preventing from committing
  secrets and password to Git.

- **Parquet** files with a local copy of the data quality data lake. Storing all historical *sensor readouts* 
  (metrics captured by the data quality checks) enables running anomaly detection rules locally,
  without using any SaaS cloud resources.

- **Jinja2 SQL templates** are templates of SQL queries that are executed on the data sources to capture metrics.
  The **Jinja2 SQL templates** are either custom sensor definitions or overrides of built-in sensors,
  shadowing the original sensor template by using the same file name as the sensor definition
  in the [DQOps home sensors](https://github.com/dqops/dqo/tree/develop/home/sensors) folder.

- **Python rules** are custom Python functions that are called to evaluate the *sensor readouts* to decide if the value
  is valid or not. The **Python rules** in the `DQOps user home` folder are either custom rule definitions or overrides
  of built-in rules, shadowing the original rules by using the same file name as the rule definition
  in the [DQOps home rules](https://github.com/dqops/dqo/tree/develop/home/rules) folder. 


## DQOps user home location
When DQOps is started as a Python module, the current working folder becomes the `DQOps user home`.
Alternatively, an environment variable *$DQO_USER_HOME* should be set that points to the `DQOps user home` location.
The *$DQO_USER_HOME* environment variable is used along this documentation to point to the `DQOps user home`,
whenever this folder is referenced.

It is advised to create a new, empty folder that will serve as the `DQOps user home` before starting DQOps.

If DQOps is started as a docker image for production use, the `DQOps user home` folder should be
mounted to the */dqo/userhome* folder inside a DQOps docker image as described in
the [DQOps docker installation manual](../dqops-installation/run-dqops-as-docker-container.md).


## DQOps user home structure
DQOps initializes the `DQOps user home` folder on startup, creating empty folders and adding default files.

The following folders and root level files are created.

``` { .asc .annotate }
$DQO_USER_HOME
├───.DQO_USER_HOME(1)
├───.gitignore(2)
├───.localsettings.dqosettings.yaml(3)
├───.credentials(4)
├───.data(5)                                                                   
├───.index(6) 
├───.logs(7)
├───checks(8)
├───dictionaries(9)
├───rules(10)                                                                 
├───sensors(11)
├───settings(12)
└───sources(13)                                                                
```

1.   A marker file that is created only to identify the `DQOps user home` root and confirm that the folder was fully initialized.
2.   Git ignore file that lists files and folders that should not be stored in Git.
     - The *[.localsettings.dqosettings.yaml](../reference/yaml/LocalSettingsYaml.md)* file is ignored because it contains
       the DQOps Cloud Pairing key
     - The *.data* folder is ignored because it contains Parquet data files that change frequently.
     - The *.credentials* folder is ignored because it contains secrets and passwords.
     - The *.index* and *.logs* folders are ignored because they are only required by a local DQOps instance.
3.   [.localsettings.dqosettings.yaml](../reference/yaml/LocalSettingsYaml.md) file contains settings that are private
     for the current DQOps instance and should not be stored in the Git repository or shared with other DQOps instances.
     The most important parameters in the local settings file are a *DQOps Cloud Pairing Key* and a local instance key
     used to sign DQOps API keys.
4.   The *.credentials* folder stores secrets and passwords as regular text or binary files. This folder should not be committed to Git.
5.   The *.data* folder is a local copy of the data quality data lake, storing all current and historical data quality results,
     statistics, execution errors and incidents. The content of this folder is replicated to the *DQOps Cloud Data Lake* as
     documented in the [DQOps architecture](architecture/dqops-architecture.md). The content of the folder is
     described in the [data storage](data-storage-of-data-quality-results.md) concept manual.
6.   The *.index* folder is used internally by DQOps to track the file synchronization status between the local `DQOps user home`
     folder and the DQOps Cloud Data Lake. The files in this folder should not be modified manually.
7.   The *.logs* folder stores error logs locally. The files in the folder are rotated to save space. 
     In case that an error is reported when running DQOps, the content of the folder should be sent to the DQOps support.
     Please review all the *--logging.\** and *--dqo.logging.\** parameters passed to DQOps as 
     the [entry point parameters](../command-line-interface/dqo.md) to learn how to configure logging. 
8.   The *checks* folder stores the definition of custom data quality [checks](definition-of-data-quality-checks/index.md).
9.   The *dictionaries* folder stores custom data dictionaries (CSV files) that can be referenced in [accepted_values](../checks/column/accepted_values/index.md).
10.  The *rules* folder stores the definition of custom and overwritten data quality [rules](definition-of-data-quality-rules.md).
11.  The *sensors* folder stores the definition of custom and overwritten data quality [sensors](definition-of-data-quality-sensors.md).
12.  The *settings* folder stores shared settings that can be committed to Git. The shared settings include the list
     of custom data quality dashboards or the default configuration of data observability checks that are applied on
     all imported data sources.
13.  The *.sources* folder is the most important folder in the `DQOps user home`. It is the folder where DQOps stores
     the connection parameters to the data sources and the data quality checks configuration for all monitored tables.

The files stored directly in the `DQOps user home` folder and all folders are described below.

| File&nbsp;or&nbsp;folder&nbsp;name | Description                                                                                                                                                                                                                                                                                                                                                                                                                                         | Stored&nbsp;in&nbsp;Git  |
|------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:------------------------:|
| *.DQO_USER_HOME*                   | A marker file that is created only to identify the `DQOps user home` root and confirm that the folder was fully initialized.                                                                                                                                                                                                                                                                                                                        |     :material-check:     |
| *.gitignore*                       | Git ignore file that lists files and folders that should not be stored in Git.                                                                                                                                                                                                                                                                                                                                                                      |     :material-check:     |
| *.localsettings.dqosettings.yaml*  | [.localsettings.dqosettings.yaml](../reference/yaml/LocalSettingsYaml.md) file contains settings that are private for the current DQOps instance and should not be stored in the Git repository or shared with other DQOps instances. The most important parameters in the local settings file are a *DQOps Cloud Pairing Key* and a local instance key used to sign DQOps API keys.                                                                |                          |
| *.credentials*                     | The *.credentials* folder stores secrets and passwords as regular text or binary files.                                                                                                                                                                                                                                                                                                                                                             |                          |
| *.data*                            | The *.data* folder is a local copy of the data quality data lake, storing all current and historical data quality results, statistics, execution errors and incidents. The content of this folder is replicated to the *DQOps Cloud Data Lake* as documented in the [DQOps architecture](architecture/dqops-architecture.md). The content of the folder is described in the [data storage](data-storage-of-data-quality-results.md) concept manual. |                          |
| *.index*                           | The *.index* folder is used internally by DQOps to track the file synchronization status between the local `DQOps user home` folder and the DQOps Cloud Data Lake. The files in this folder should not be modified manually.                                                                                                                                                                                                                        |                          |
| *.logs*                            | The *.logs* folder stores error logs locally. The files in the folder are rotated to save space. In case that an error is reported when running DQOps, the content of the folder should be sent to the DQOps support. Please review all the *--logging.\** and *--dqo.logging.\** parameters passed to DQOps as the [entry point parameters](../command-line-interface/dqo.md) to learn how to configure logging.                                   |                          |
| *checks*                           | The *checks* folder stores the definition of custom data quality [checks](definition-of-data-quality-checks/index.md).                                                                                                                                                                                                                                                                                                                              |     :material-check:     |
| *dictionaries*                     | The *dictionaries* folder stores custom data dictionaries that are CSV files with values. The dictionaries can be referenced in data quality checks in the [accepted_values](../checks/column/accepted_values/index.md) category using a `${dictionary://filename.csv}` token.                                                                                                                                                                      |     :material-check:     |
| *rules*                            | The *rules* folder stores the definition of custom and overwritten data quality [rules](definition-of-data-quality-rules.md).                                                                                                                                                                                                                                                                                                                       |     :material-check:     |
| *sensors*                          | The *sensors* folder stores the definition of custom and overwritten data quality [sensors](definition-of-data-quality-sensors.md).                                                                                                                                                                                                                                                                                                                 |     :material-check:     |
| *settings*                         | The *settings* folder stores shared settings that can be committed to Git. The shared settings include the list of custom data quality dashboards or the default configuration of data observability checks that are applied on all imported data sources.                                                                                                                                                                                          |     :material-check:     |
| **sources**                        | The *sources* folder is the most important folder in the `DQOps user home`. It is the folder where DQOps stores the connection parameters to the data sources and the data quality checks configuration for all monitored tables.                                                                                                                                                                                                                   |     :material-check:     |


## Data sources
The data sources are defined in the **sources** folder as shown below.

``` { .asc .annotate hl_lines="4 7" }
$DQO_USER_HOME
├───...
└───sources(1)                                                                
    ├───prod-landing-zone(2)
    │   ├───connection.dqoconnection.yaml(3)
    │   └───...
    ├───prod-data-lake
    │   ├───connection.dqoconnection.yaml
    │   └───...
    └─...   
```

1.  The **sources** folder stores data sources as nested folders.
2.  Each folder inside the **sources** folder is a connection name to a data source.
3.  Each data source's folder contains a file [connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md)
    that specifies the connection parameters to the data source.
    
Each folder inside the **sources** folder is a connection name to a data source.
Each data source's folder contains a file [connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md)
that specifies the connection parameters to the data source.


## Monitored tables
DQOps uses one file per monitored table. The file names use the [.dqotable.yaml](../reference/yaml/TableYaml.md) file extension.

The file name format is *&lt;schema_name&gt;.&lt;table_name&gt;.dqotable.yaml*.
DQOps also encodes selected characters (including `\`, `/`, `%`) as %AB, where *AB* is the ASCII code of the character.

The following example shows a folder structure with several tables.

``` { .asc .annotate hl_lines="6-10" }
$DQO_USER_HOME
├───...
└───sources                                                                
    ├───prod-data-lake
    │   ├───connection.dqoconnection.yaml
    │   ├───country_codes.country_codes.dqotable.yaml
    │   ├───crypto_dogecoin.blocks.dqotable.yaml
    │   ├───crypto_dogecoin.inputs.dqotable.yaml
    │   ├───crypto_dogecoin.outputs.dqotable.yaml
    │   └───<schema_name>.<table_name>.dqotable.yaml (1)!
    └─...   
```

1. A table configuration file. The [.dqotable.yaml](../reference/yaml/TableYaml.md) files are named as the *&lt;schema_name&gt;.&lt;table_name&gt;.dqotable.yaml*.


The [.dqotable.yaml](../reference/yaml/TableYaml.md) files are named as the *&lt;schema_name&gt;.&lt;table_name&gt;.dqotable.yaml*.

Storing the configuration of the data quality checks in a file named after the table name simplifies migration
of the table versions or between environments. When a similar table is present in another data source
or within the current data source, the whole *.dqotable.yaml* file can be easily copied and renamed.

The following scenarios are supported by copying the *.dqotable.yaml* file manually:

- Table definitions are moved between environments (production, test, development). The file with a configuration
  of the data quality checks on the development environment is copied to another data source folder that is connected
  to the production environment. 

- A similar table is created in the same data source, but in a different schema. The *.dqotable.yaml* file
  is copied and renamed to replace the *schema_name* part of the file name.

- Another similar table is created in the same schema. The *.dqotable.yaml* file
  is copied and renamed to replace the *table_name* part of the file name.


## Shared credentials
Shared credentials are secrets and passwords that should not be stored in Git.

Shared credentials are referenced in the [connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md) files
by using a special field value format *${credential://**file_name**}* where the **file_name** is a file name inside
the *.credentials* folder.

``` { .asc .annotate }
$DQO_USER_HOME
├───...
├───.credentials
│   ├───db_password.txt(1)
│   └───GCP_application_default_credentials.json(2)
├───...                                                                   
```

1.  A sample shared credential named *db_password.txt*. It can be referenced in the
    [connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md) file as `${credential://db_password.txt}`.
2.  The name of the default GCP application credentials that is used by the BigQuery connector if the GCP default
    credentials are not available otherwise. It should be a key generated for a GCP service account. The service account
    whose key was generated must have correct permissions to run queries on the BigQuery data set that is monitored.
    This file must be created manually, it is not created during the DQOps user home initialization.

The example *db_password.txt* credential can be referenced as a `${credential://db_password.txt}` expression
used in the [connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md) file.


## Data dictionaries
Data dictionaries are CSV files containing text values that can be referenced by data quality checks that
[compare column values to a set of accepted values](../categories-of-data-quality-checks/how-to-validate-accepted-values-in-columns.md).
Instead of entering the same values for multiple data quality checks, a shared dictionary can be referenced.

The data quality checks that can use *data dictionaries* are:

- [text_found_in_set_percent](../checks/column/accepted_values/text-found-in-set-percent.md)

- [expected_text_values_in_use_count](../checks/column/accepted_values/expected-text-values-in-use-count.md)

- [expected_texts_in_top_values_count](../checks/column/accepted_values/expected-texts-in-top-values-count.md)

A sample *currencies.csv* dictionary file is shown in the folder structure below.

``` { .asc .annotate hl_lines="4" }
$DQO_USER_HOME
├───...
├───dictionaries(1)
│   └───currencies.csv(2)
├───...                                                                   
```

1.  The *dictionaries* folder for storing custom dictionaries.
2.  A sample dictionary file.

The data dictionary file is referenced in the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files as
`${dictionary://<file_name>}`. For example, the *currencies.csv* file shown above is referenced as `${dictionary://currencies.csv}`.

DQOps does not support headers in the CSV files. All lines in the uploaded file must contain values. Additionally, 
when multiple values separated by a comma (`,` character) are present in a single line, DQOps aggregates all values.

The following three CSV files compared in the tabs below are equivalent. DQOps will concatenate the values to be used in the
[text_found_in_set_percent](../checks/column/accepted_values/text-found-in-set-percent.md) data quality check 
using a combined condition `<column> IN ('USD', 'EUR', 'GBP')`.

=== "One value per line"

    ``` { .asc }
    USD
    EUR
    GBP
    ```

=== "All value in a single line, separated by a comma"

    ``` { .asc }
    USD,EUR,GBP
    ```

=== "Multiple lines, some lines contain multiple values separated by a comma"

    ``` { .asc }
    USD,EUR
    GBP
    ```

An example of referencing a data dictionary in a data quality check is shown in the 
[referencing data dictionary](configuring-data-quality-checks-and-rules.md#referencing-data-dictionaries) section of
the configuration of data quality checks article.

## Custom sensors
Custom data quality [sensors](definition-of-data-quality-sensors.md) are defined in the *sensors* folder.
The folder structure is not strict, but it is advised to follow the order:

- the target object: *table* or *column*

- the category of sensor

- the short name of the sensor

DQOps uses the folder structure inside the *sensors* folder to identify data quality sensors.
The sensor in the following example is named *column/nulls/null_count* according to the sensor's naming convention.
The highlighted lines show the minimum set of sensor's configuration files.

``` { .asc .annotate hl_lines="7 8 17" }
$DQO_USER_HOME
├───...
├───sensors
│   └───column(1)
│       └───nulls(2)
│           └───null_count(3)
│               ├───bigquery.dqoprovidersensor.yaml(4)
│               ├───bigquery.sql.jinja2(5)
│               ├───mysql.dqoprovidersensor.yaml
│               ├───mysql.sql.jinja2
│               ├───oracle.dqoprovidersensor.yaml
│               ├───oracle.sql.jinja2
│               ├───postgresql.dqoprovidersensor.yaml
│               ├───postgresql.sql.jinja2
│               ├───redshift.dqoprovidersensor.yaml
│               ├───redshift.sql.jinja2
│               ├───sensordefinition.dqosensor.yaml(6)
│               ├───snowflake.dqoprovidersensor.yaml
│               ├───snowflake.sql.jinja2
│               ├───sqlserver.dqoprovidersensor.yaml
│               └───sqlserver.sql.jinja2 
└───...                                                                
```

1.  The sensor target, should be *table* or *column*.
2.  The sensor category that is a logical grouping of similar sensors.
3.  The short sensor name within the category. This folder contains the sensor configuration files.
4.  The [database specific](../reference/yaml/ProviderSensorYaml.md) configuration of the sensor.
5.  Jinja2 SQL template of the sensor.
6.  The main [sensor definition](../reference/yaml/SensorDefinitionYaml.md) file that configures the list
    of sensor's parameters that are shown in the DQOps check editor screen.

DQOps supports both creating a custom sensors or changing the Jinja2 templates for built-in sensors.
Updating built-in sensors has one limitation. The list of sensor's parameters stored in
the [sensordefinition.dqosensor.yaml](../reference/yaml/SensorDefinitionYaml.md) cannot be modified by
adding or changing sensor's parameters.

Sensors that are overwritten from the built-in sensors are a copy of the sensor definition file
from the [DQOps home](https://github.com/dqops/dqo/tree/develop/home/sensors) sensors folder. The easiest way
to customize a built-in sensor is to edit the Jinja2 file from the *Configuration -> Sensors* screen in the DQOps user interface.
DQOps will copy the default definition from its distribution to the DQOps user home's sensors folder.

A custom sensor must have at least three files:

- [sensordefinition.dqosensor.yaml](../reference/yaml/SensorDefinitionYaml.md) file that provides the list of parameters

- [.dqoprovidersensor.yaml](../reference/yaml/ProviderSensorYaml.md) file named as *&lt;database_type&gt;.dqoprovidersensor.yaml*.
  which confirms that there is a sensor definition (and a query template) for the *database_type*.

- Jinja2 SQL template of the sensor named as *&lt;database_type&gt;.sql.jinja2*.


## Custom rules
Custom data quality [rules](definition-of-data-quality-rules.md) are defined as two files. 
The [.dqorule.yaml](../reference/yaml/RuleDefinitionYaml.md) file with the rule parameters and configuration.
The second file is a Python module that must have a `evaluate_rule` function.

The rule names also follow a naming convention, but in contrary to the sensors, multiple rules can be defined in
a single rule category folder.

The full rule name is defined as *comparison/max_count* in the following example.

``` { .asc .annotate hl_lines="6-7" }
$DQO_USER_HOME
├───...
├───rules                                                                 
│   ├───requirements.txt(1)
│   ├───comparison(2)
│   │   ├───max_count.dqorule.yaml(3)
│   │   └───max_count.py(4)
│   └─...   
└───...                                                                
```

1.  The requirements.txt file with a list of custom Python packages that should be installed
    when DQOps is started as a docker container.
2.  Rule category name where a custom or an overwritten rule is defined. Custom rules can be defined in any category.
3.  The [.dqorule.yaml](../reference/yaml/RuleDefinitionYaml.md) rule definition file that specifies a list of
    rule parameters shown on the check's editor screen and the time window requirements of historical *sensor readouts*
    required by rules that use historical values for change or anomaly detection.
4.  Python module with a `evaluate_rule` function that is called by DQOps to evaluate the *sensor readout*.

The custom rules also support both overriding built-in rules that are copied from the
[DQOps home](https://github.com/dqops/dqo/tree/develop/home/rules) rules folder.
Also, the easiest way to alter a built-in rule is to edit the Python rule file
on the *Configuration -> Rules* screen in the DQOps user interface.

The same limitation is in effect that the list of parameters of built-in rules cannot be changed.
If a new version of a rule is required that has a different set of parameters, the built-in rule should be copied
and altered as a custom rule. The *Configuration -> Rules* screen has a *copy* button that supports this use case.

Custom rules can also use additional Python packages from PyPi. The list of dependencies must be configured
in the *rules/requirements.txt* file. It is important to understand where and when the additional packages are installed.

DQOps instance that is started for development as a Python module by running `python -m dqops` will not use the
*rules/requirements.txt* file at all. Instead, DQOps will require that all necessary packages were already installed
in the Python's system or virtual environment that is used to start the `python -m dqops` command.

A production DQOps instance that was started from [docker](../dqops-installation/run-dqops-as-docker-container.md),
DQOps will detect changes to the *rules/requirements.txt* file on startup and will reinstall required packages.


## Custom checks
Custom data quality checks in DQOps are simply a pair of a sensor and a rule.
A check can use any combination of custom and built-in sensors and rules.

The folder structure for custom data quality checks is strictly limited because the folder name
affects the location on the check editor screen in the DQOps user interface.
The custom checks must be defined in a three-level deep folder structure.
The folder names on the folder tree are:

- The target object, must be *table* or *column*.

- The target type of data quality check, must be one of *profiling*, *monitoring*, or *partitioned*.

- The name of an existing check category within the built-in check structure. The folder structure is shown within
  the reference of the data quality [checks](../checks/index.md) in this documentation.
  A custom check can be appended to an existing category of checks or added to the category named *custom*.

The following example shows two custom data quality checks. One in the *custom* category and another appended
to an existing *volume* category.

``` { .asc .annotate hl_lines="7 9" }
$DQO_USER_HOME
├───...
├───checks
│   └───table(1)
│       └───profiling(2)
│           ├───custom(3)
│           │   └───custom_profile_row_count.dqocheck.yaml(4)
│           └───volume(5)
│               └───profile_max_row_count.dqocheck.yaml
└───...                                                                
```

1.  The check target, *table* or *column*.
2.  The check type, must be one of *profiling*, *monitoring*, or *partitioned*.
3.  The *custom* check category for adding new custom checks.
4.  The [check definition](../reference/yaml/CheckDefinitionYaml.md) file that contains the configuration of the check.
5.  An existing category of checks where a custom check is appended.

The custom checks are defined in [**&lt;check_name&gt;**.dqocheck.yaml](../reference/yaml/CheckDefinitionYaml.md) files.
The check names must be unique even between categories. Otherwise, the results shown on the
[data quality dashboards](types-of-data-quality-dashboards.md) will not identify the correct check name.
The **check_name** used in the *.dqocheck.yaml* is the name of the check that is used to run it.

Differently from the customization of sensors and rules, it is not possible to overwrite a built-in check by creating
a check with the same name in the *checks* folder.


## Shared settings
Some configuration files are safe to store to Git and should be shared between on-premise and cloud DQOps instances
in a hybrid deployment model.

The default settings files are created when the DQOps user home is initialized for the first time.

The following example shows the list of the files in the *settings* folder.

``` { .asc .annotate }
$DQO_USER_HOME
├───...
├───settings
│   ├───dashboardslist.dqodashboards.yaml(1)
│   ├───default.dqodefaultchecks.yaml(2)
│   ├───defaultnotifications.dqonotifications.yaml(3)
│   └───defaultschedules.dqoschedules.yaml(4)
└───...   
```

1.  A [list](../reference/yaml/DashboardYaml.md) (tree) of custom or overwritten data quality dashboards.
2.  The configuration of the [default data quality checks](../reference/yaml/DefaultObservabilityChecksYaml.md)
    that are activated on imported tables and columns to detect common issues and observe the data source.
3.  The configuration of the default [incident notification](../integrations/webhooks/index.md)
    [webhooks](../reference/yaml/DefaultNotificationsYaml.md).
4.  The configuration of the [default schedules](../reference/yaml/DefaultSchedulesYaml.md)
    for running data quality checks daily or monthly.

The default configuration files are listed below.

| File&nbsp;name                                                                                | Description                                                                                                                                                                                                                                                                                                                                                          |
|-----------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| *[dashboardslist.dqodashboards.yaml](../reference/yaml/DashboardYaml.md)*                     | The configuration of custom data quality dashboards. Adding custom dashboards is documented in the [creating custom dashboards](../integrations/looker-studio/creating-custom-data-quality-dashboards.md) manual.                                                                                                                                                    |
| *[default.dqodefaultchecks.yaml](../reference/yaml/DefaultObservabilityChecksYaml.md)*         | The configuration of the default checks that are activated on imported tables and columns to detect common issues and observe the data source.                                                                                                                                                                                                                       |
| *[defaultnotifications.dqonotifications.yaml](../reference/yaml/DefaultNotificationsYaml.md)* | The configuration of the webhooks where the [notification of incidents](../integrations/webhooks/index.md) are POST'ed when data quality incidents are created or reassigned.                                                                                                                                                                                        |                                   
| *[defaultschedules.dqoschedules.yaml](../reference/yaml/DefaultSchedulesYaml.md)*             | The default configuration of CRON schedules for running data quality checks in regular intervals. <br/> **NOTE: The CRON schedules defined in this file are copied to the *connection.dqoconnection.yaml* file when a new connection is imported in DQOps. Changes to this file will not change the schedules of running checks for already imported data sources.** |


## What's next
- Learn [how the data quality results are stored](data-storage-of-data-quality-results.md) as Parquet files in the *.data* folder.
- Look at the architecture diagrams showing [how DQOps runs data quality checks](architecture/data-quality-check-execution-flow.md)
  to understand how DQOps uses all its configuration files.
- Review the [DQOps architecture diagrams](architecture/dqops-architecture.md) to understand also deployment options,
  and how a local DQOps instance synchronizes the files with the DQOps Cloud.
