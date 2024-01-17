# Configuring data sources
Read this guide to understand how DQOps stores the connection parameters to monitored data sources, and how the metadata about tables and columns is managed.

## Overview
DQOps stores the configuration of data sources in YAML files. The files support auto-completion in Visual Studio Code, and can be versioned in Git.    

!!! note "[`DQOps user home` folder](dqops-user-home-folder.md)"

    For the purpose of this guide, we will assume that DQOps was started in the current folder using the `python -m dqops` command.
    All files mentioned in this guide will be relative to the current folder, referred as a [`$DQO_USER_HOME`](dqops-user-home-folder.md) in the examples below.


## DQOps YAML files structure
The structure of DQOps YAML configuration files is similar to the structure of Kubernetes specification files.
Additionally, the first line references a YAML schema file that is used by Visual Studio Code for code completion,
validation, and showing the documentation of checks. The concept of working with [YAML files](../integrations/visual-studio-code/index.md)
shows the editing experience in Visual Studio Code.

### **DQOps YAML file example**
The following code example is a fragment of a [DQOps data source configuration file](../reference/yaml/ConnectionYaml.md#connectionyaml),
showing all regular elements of all DQOps YAML files.

``` { .yaml .annotate linenums="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json (1)
apiVersion: dqo/v1
kind: source # (2)!
spec: # (3)!
  provider_type: postgresql
  postgresql:
    host: localhost
    ...
```

1.  The YAML file schema identifier. Each type of DQOps configuration file points to its own YAML file schema. The YAML schema is used
    by text editors such as Visual Studio Code for the code completion and schema validation.
2.  The type of the file is identified in the `kind` element.
3.  The `spec` [connection specification](../reference/yaml/ConnectionYaml.md#connectionspec) object
    that describes the data source, and its connection parameters.

The first line of DQOps configuration files has an optional link to a YAML schema file. The YAML schema files
are used by text editors such as [Visual Studio Code for the code completion and schema validation](../integrations/visual-studio-code/index.md).

The `kind` node identifies the type of DQOps file, and the `spec` node contains the specification, which is the real configuration.
For detailed file reference, consult the reference of the *DQOps YAML files schema* in the [DQOps reference](../reference/index.md) section.

### **YAML file extensions**
DQOps identifies the type of its YAML files by the file extension. The file extensions of files storing the metadata of data sources
are listed below.

| File name pattern                                                                     | File purpose                                                                              |
|---------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------|
| *[connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md#connectionyaml)* | Data source connection configuration file with the connection details and credentials.    |
| *[\*.dqotable.yaml](../reference/yaml/TableYaml.md#tableyaml)*                        | Monitored table configuration file with the configuration of enabled data quality checks. |


## Data sources
The data sources can be registered in DQOps using the user interface, 
or creating DQOps YAML *[.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md#connectionyaml)* files directly in the data source folder.

### **Data sources folder**
The configuration of data sources and the metadata of all tables are defined in the **sources** folder,
inside the [`DQOps user home`](dqops-user-home-folder.md) folder, referred as `$DQO_USER_HOME`. 
The example below shows two data sources named *prod-landing-zone* and *prod-data-lake*.

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

The name of each child folder inside the **sources** folder is a connection name to a data source.
Each data source's folder contains one file named [connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md),
that specifies the connection parameters to that data source.

### **Configuring data sources in YAML files**
The data source folder should contain exactly one file, that must be named [connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md).
An example data source connection file for [PostgreSQL](../data-sources/postgresql.md) is shown below.

``` { .yaml .annotate linenums="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source # (1)!
spec:
  provider_type: postgresql # (2)!
  postgresql: # (3)!
    host: localhost
    port: "52289"
    user: test
    password: test
    database: test
```

1.  The type of the file is identified in the `kind` element. Data sources use a `source` kind.
2.  The type of data sources. Use the documentation of the [ConnectionSpec](../reference/yaml/ConnectionYaml.md#connectionspec) object
    to find the names of other supported data sources, beyond the `postgresql` connection type shown in this example.
3.  The configuration node for [PostgreSQL](../data-sources/postgresql.md). Other supported data sources are configured in similar elements,
    named as the type of data source.

Each [connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md) must have two nodes filled:

- `provider_type` enumeration stores the type of the data source.
- *data_source_type* node (`postgresql` in this example) stores type-safe configuration for that data source, supporting
  code completion in [Visual Studio Code](../integrations/visual-studio-code/index.md).

The full documentation of the `spec` element is provided in the
[ConnectionSpec](../reference/yaml/ConnectionYaml.md#connectionspec) object reference.
Examples of data source specific configurations are located in the [data sources](../data-sources/index.md) section of the documentation.

### **Configuring data sources from the user interface**
Each type of data source has its own connection configuration screen in the DQOps user interface.
Check out the [data sources](../data-sources/index.md) section to find the data source of interest,
and learn the details of configuring your connections from the DQOps UI.


## Configuring table metadata
The configuration of every monitored table is stored in [\*.dqotable.yaml](../reference/yaml/TableYaml.md) files. The table metadata configuration files
are stored in the *$DQO_USER_HOME/sources/{data_source_name}/* folder of the data source.
The [\*.dqotable.yaml](../reference/yaml/TableYaml.md) files are placed directly in the folder, not organized into folders by the database schema name.

### **.dqotable.yaml file names**
The schema name and the table name is not stored in the [.dqotable.yaml](../reference/yaml/TableYaml.md) file. 
Instead, DQOps depends on file naming conventions. 

The file name pattern for [\*.dqotable.yaml](../reference/yaml/TableYaml.md) files is `<schema_name>.<table_name>.dqotable.yaml`.

 - `<schema_name>` part is the database schema name. For databases that group tables by a different object, it is:
  
     - *database* in [MySQL](../data-sources/mysql.md)
   
     - *schema* or *user* in [Oracle](../data-sources/oracle.md)
   
     - *dataset* in [BigQuery](../data-sources/bigquery.md)

 - `<table_name>` part stores the table name

An example of a [`DQOps user home`](dqops-user-home-folder.md) folder with two tables is shown below.

``` { .asc .annotate hl_lines="6-7" }
$DQO_USER_HOME
├───...
└───sources                                                                
    ├───prod-landing-zone
    │   ├───connection.dqoconnection.yaml
    │   ├───public.fact_sales.dqotable.yaml
    │   ├───public.dim_date.dqotable.yaml
    │   └───...
    └─...   
```

### **Table YAML file structure**
The following [.dqotable.yaml](../reference/yaml/TableYaml.md) file below shows the structure of a table metadata configuration file.

``` { .yaml .annotate linenums="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table # (1)!
spec: # (2)!
  timestamp_columns: # (3)!
    event_timestamp_column: date # (4)!
    partition_by_column: date # (5)!
  incremental_time_window: # (6)!
    daily_partitioning_recent_days: 7 # (7)!
    monthly_partitioning_recent_months: 1 # (8)!
  columns: # (9)!
    first_column_name: # (10)!
      ...
    other_column_name:
      ...
```

1.  The type of the file is identified in the `kind` element.
2.  The `spec` [table specification](../reference/yaml/TableYaml.md#tablespec) object that describes the table, its columns
    and activated data quality checks.
3.  The configuration of timestamp columns that are used for timeliness checks such as freshness, and for running partitioned checks.
4.  The column name (*date* in this example) that contains an event timestamp that is used to measure timeliness.
5.  The column name (*date* in this example) that will be used in **GROUP BY** queries to measure the data quality at a partition level.
    The table does not need to be partitioned by this column physically. DQOps uses this column to detect data quality issues
    within time ranges.
6.  The configuration of the date, datetime or timestamp columns that are used in timeliness checks and in the **GROUP BY** clauses
    in date partitioned checks.
7.  The number of recent days used to analyze the data incrementally in daily partitioned checks.
8.  The number of recent months used to analyze the data incrementally in monthly partitioned checks. *1* means that the previous
    month is analyzed only.
9.  The node with a list of columns.
10. One example column name. The column-level checks for this column are defined inside the node.

The main nodes found created by default in the [.dqotable.yaml](../reference/yaml/TableYaml.md) file when a table is imported into DQOps are listed below.

- `timestamp_columns` node stores the names of *date*, *datetime* or *timestamp* columns that are used for 
   [timeliness and freshness](types-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md) checks,
   and to configure the date column used for [partition checks](definition-of-data-quality-checks/partition-checks.md).

- `incremental_time_window` node configures the time window for running [partition checks](definition-of-data-quality-checks/partition-checks.md)
  for the most recent data, to enable [incremental data quality monitoring](incremental-data-quality-monitoring.md)

- `columns` is a dictionary of columns, storing the columns' metadata, indexed by the column name.


### **Table YAML core elements**
The core elements found on the *.dqotable.yaml* file are described in the table below.

??? note "Please expand this section to see the description of all *.dqotable.yaml* file nodes"

    | Line | Element&nbsp;path&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  | Description                                                                                                                                                                                                                         | Default value |
    |------|--------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------|
    | 2    | `apiVersion`                                                                                           | The version of the DQOps file format                                                                                                                                                                                                | dqo/v1        |
    | 3    | `kind`                                                                                                 | The file type                                                                                                                                                                                                                       | table         |
    | 4    | `spec`                                                                                                 | The main content of the file, contains the [table specification](../reference/yaml/TableYaml.md#tablespec).                                                                                                                      |               |
    | 5    | `spec.timestamp_columns`                                                                               | The configuration of timestamp columns that are used for timeliness checks such as freshness, and for running partitioned checks.                                                                                                   |               |
    | 6    | `spec.timestamp_columns.` `event_timestamp_column`                                                     | The column name (*date* in this example) that contains an event timestamp that is used to measure timeliness. It should be a column that identifies the event or transaction timestamp.                                             |               |
    | 7    | `spec.timestamp_columns.` `partition_by_column`                                                        | The column name (*date* in this example) that will be used in partitioned checks to detect data quality issues at a date partition or for each daily or monthly time period. It can be a date, datetime or a timestamp column name. |               |
    | 8    | `spec.incremental_time_window`                                                                         | The configuration for the recent time window used to run partitioned data quality checks incrementally.                                                                                                                             |               |
    | 9    | `spec.incremental_time_window.` `daily_partitioning_recent_days`                                       | The number of recent days used to analyze the data incrementally in daily partitioned checks.                                                                                                                                       | 7             |
    | 10   | `spec.incremental_time_window.` `monthly_partitioning_recent_months`                                   | The number of recent months used to analyze the data incrementally in monthly partitioned checks. *1* means that the previous month is analyzed only.                                                                               | 1             |
    | 11   | `spec.columns`                                                                                         | A node that contains an array of columns for which the data quality checks are configured.                                                                                                                                          |               |
    | 12   | `spec.columns.<first_column_name>`                                                                     | An example column named *first_column_name*. The column level data quality checks for this column are configured inside this node.                                                                                                  |               |


## Configuring columns
The list of columns is stored in the `spec.columns` node in the *.dqotable.yaml* file.
The `spec.columns` node is a dictionary of [column specifications](../reference/yaml/TableYaml.md#columnspec),
the keys are the column names.

Columns are added to the *.dqotable.yaml* when the table's metadata is imported into DQOps.

### **Column schema**
The following example shows a *.dqotable.yaml* file with two columns named *cumulative_confirmed* and *date*.

``` { .yaml .annotate linenums="1" hl_lines="6 10" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns: # (1)!
    cumulative_confirmed: # (2)!
      type_snapshot: # (3)!
        column_type: INT64 # (4)!
        nullable: true
    date:
      type_snapshot:
        column_type: DATE
        nullable: true
```

1.  The column dictionary node. The nodes below it are the column names.
2.  The configuration and captured metadata of the first column *cumulative_confirmed*.
3.  Data type snapshot contains the last imported physical data type of the column. DQOps uses these data types
    to decide if some data type specific data quality checks could be activated on the column.
4.  The data type of the column, it is a physical data type introspected from the monitored table.

The node for each column contains a [type snapshot](../reference/yaml/TableYaml.md#columntypesnapshotspec) object
that is used by DQOps in the following cases:

- The default [data quality checks](#default-data-quality-checks) are activated depending on the column's data type.
  Numeric anomaly checks are activated only on numeric columns such as the *cumulative_confirmed* column in the example above.

- The data quality [sensors](definition-of-data-quality-sensors.md) may use the column's data type to decide if an additional type casting
  must be generated in the SQL query that will capture the metrics for the data quality check.

DQOps does not require that each column has the `type_snapshot` node defined. All the data quality checks will work
without knowing the column's data type. The `type_snapshot.column_type` field can be used to find columns by
the data type when activating multiple data quality checks 
using the `[dqo check activate](../command-line-interface/check.md#dqo-check-activate) command-line command, or
using the [bulk check editor screen](../working-with-dqo/activate-and-deactivate-multiple-checks.md).

### **Calculated columns**
DQOps goes beyond monitoring columns that are already present in the table.
Quite often, the values that will be monitored must be first extracted from complex text columns.

Let's take an example of a table that contains just one column, named *message*.
Each *message* column stores a single line of a HL7 message.

``` asc
EVN|A01|198808181123
```

We want to analyze a table that contains HL7 messages, verifying that the trigger event type is
one of accepted values, 'A01' and 'A02' in this example.

Calculated columns are defined in the *.dqotable.yaml* file also in the `spec.columns` section.
The column name becomes a virtual column name. Data quality checks may be applied to this virtual column.
The following example shows a *event_type_code* virtual column that extracts the second element from
the message lines that are the 'EVN' event messages.

``` { .yaml .annotate linenums="1" hl_lines="10 14" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    message: # (1)!
      type_snapshot:
        column_type: STRING
        nullable: true
    event_type_code: # (2)!
      type_snapshot:
        column_type: STRING # (3)!
        nullable: true
      sql_expression: "CASE WHEN SPLIT({alias}.message, '|')[0] = 'EVN' THEN SPLIT({alias}.message, '|')[1] ELSE NULL END" # (4)!
      monitoring_checks:
        daily:
          strings:
            daily_text_found_in_set_percent:
              parameters:
                expected_values:
                  - "A01" # (5)!
                  - "A02"
              error:
                min_percent: 100
```

1.  The *message* column that contains one line of a HL7 message.
2.  The name of the virtual (calculated) column that was created.
3.  The data type of the calculated column. It is not required to configure it, but the data quality checks may use it as a hint
    to avoid additional type casting.
4.  The SQL expression using the SQL grammar of the monitored data source that extracts the value of a calculated column.
    The `{alias}.message` expression references the *message* column from the monitored source. DQOps will replace the `{alias}`
    token with the table alias used in the SQL query that is generated from the sensor's template.
5.  We are validating that the column contains a 'A01' value.

The calculated column is defined using the SQL grammar of the monitored data source
The `sql_expression` field must contain an SQL extracts the value of a calculated column.

The `{alias}.message` expression references the *message* column from the monitored source. DQOps will replace the `{alias}`
token with the table alias used in the SQL query that is generated from the sensor's template.

This example also shows that the `type_snapshot.column_type` value is set to a result data type of the expression.
Setting the data type is not required to run checks, but DQOps may use it as a hint to avoid additional type casting.


### **Transforming column values**
The tables found in the data landing zones are often CSV files with all columns defined as a character data type.
These columns must be cast to a correct data type before they could be used to perform some kind of data transformations.

Let's assume that the monitored table is an external table, backed by the following CSV file.

```
date,message
2023-11-06,Hello world
```

The *date* column contains a text value that is a valid ISO 8601 date.
We want to replace all usages of the column reference with an SQL expression that will cast the column's value to a DATE.

When the *date* column is cast to a *DATE* type, we can use it as a partitioning column for partitioned checks
or run date specific data quality checks such as
the [daily-date-values-in-future-percent](../checks/column/datetime/date-values-in-future-percent.md#daily-date-values-in-future-percent)
check that detects if any dates are in the future.

The next example shows how to apply additional transformations such as type casting on a column that is present in the table.

``` { .yaml .annotate linenums="1" hl_lines="6 8 10" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    date: # (1)!
      type_snapshot:
        column_type: DATE # (2)!
        nullable: true
      sql_expression: "CAST({alias}.{column} AS DATE)" # (3)!
```

1.  A column that is already present in the table, but we want to apply a transformation.
2.  The new data type that is the result of the SQL expression.
3.  An SQL expression that is applied on the column. DQOps will use this expression to access the column
    instead of using the raw column value. The SQL expression uses a token `{alias}.{column}` to reference the
    raw value of the overwritten column.

The `sql_expression` parameter is an SQL expression that uses a token `{alias}.{column}` to reference the
raw value of the overwritten column.

## Using credentials and secrets
DQOps supports providing credentials from a separate location to avoid storing the *connection.dqoconnection.yaml* files
in the source repository.

### **Referencing environment variables**
Credentials provided to a DQOps instance using environment variables.
Environment variables are referenced using as `${ENVIRONMENT_VARIABLE_NAME}` values. 

``` { .yaml .annotate linenums="1" hl_lines="9-10" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  provider_type: postgresql
  postgresql:
    host: localhost
    port: "52289"
    user: ${MY_POSTGRESS_USER}
    password: ${MY_POSTGRESS_DATABASE}
    database: test
```

If you are [running DQOps as a docker container](../dqops-installation/run-dqops-as-docker-container.md), use the `-e` docker
parameter to pass additional environment variables.

### **Using shared credentials**
Credentials and secrets that are shared with the DQOps Cloud (and DQOps SaaS instances) are stored in the *.credentials* folder.
The name of the *secret* is the file name inside the *.credentials* folder. The following example shows two such secrets
named *my_postgress_user.txt* and *my_postgress_pass*.

``` { .asc .annotate hl_lines="4-5" }
$DQO_USER_HOME
├───...
├───.gitignore(1)
└───.credentials(2)                                                               
    ├───my_postgress_user.txt(3)
    ├───my_postgress_pass
    └─...   
```

1.  The default *.gitignore* file has a rule to ignore the *.credentials* folder and all files inside that folder.
2.  The hidden folder for shared credentials. This folder is added to the *.gitignore*, but is synchronized with DQOps Cloud
    if you want to use DQOps Cloud and synchronize the metadata between the SaaS environment and your local environment.
3.  The shared credentials are defined as files stored directly in the *.credentials* folder. The file name extension
    for credentials does not matter. This example uses a *.txt* file extension only for clarity.

The whole *.credentials* folder is added to the *.gitignore*, ensuring that the credentials are not pushed to the Git source code repository
by mistake.

The following example shows how to reference the shared credentials using a `${credential://shared_secret_name}` value.

``` { .yaml .annotate linenums="1" hl_lines="9-10" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  provider_type: postgresql
  postgresql:
    host: localhost
    port: "52289"
    user: ${credential://my_postgress_user.txt}
    password: ${credential://my_postgress_pass}
    database: test
```

The shared credentials can be also edited in the **Configuration** section of the DQOps user interface
by users holding an [Admin](../working-with-dqo/access-management.md#roles) or
[Editor](../working-with-dqo/access-management.md#roles) role. Access management is enabled only in the
[*TEAM* and *ENTERPRISE* editions of DQOps](https://dqops.com/pricing/).


## Additional connection configuration
This section describes the remaining configuration parameters defined
in the *[connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md)* file.

### **Job parallelism level**
DQOps runs data quality checks in parallel, running checks for each table in a separate thread.
The limit of parallel jobs per data source is configured in the `parallel_jobs_limit` parameter.

``` { .yaml .annotate linenums="1" hl_lines="6" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  provider_type: postgresql
  parallel_jobs_limit: 10  # (1)!
  postgresql:
    host: localhost
    port: "52289"
  ...
```

1.  The configuration parameter for configuring the maximum number of tables that are analyzed in parallel.

!!! note "DQOps license limits"

    The limit of parallel jobs supported by DQOps depends on the license level. Consult the [DQOps pricing](https://dqops.com/pricing/)
    for details.


### **Data quality check scheduling**
The DQOps CRON schedules for running each [type of data quality checks](definition-of-data-quality-checks/index.md#types-of-checks)
is configured in the `schedules` section.

``` { .yaml .annotate linenums="1" hl_lines="8 10 12 14 16" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  ...
  schedules:  # (1)!
    profiling:
      cron_expression: 0 12 1 * *  # (2)!
    monitoring_daily:
      cron_expression: 0 12 * * *  # (3)!
    monitoring_monthly:
      cron_expression: 0 12 * * *  # (4)!
    partitioned_daily:
      cron_expression: 0 12 * * *  # (5)!
    partitioned_monthly:
      cron_expression: 0 12 * * *  # (6)!
```

1.  The data source scheduling configuration of running data quality checks.
2.  The CRON schedule for running [*profiling checks*](definition-of-data-quality-checks/data-profiling-checks.md).
3.  The CRON schedule for running [*daily monitoring checks*](definition-of-data-quality-checks/data-observability-monitoring-checks.md).
4.  The CRON schedule for running [*monthly monitoring checks*](definition-of-data-quality-checks/data-observability-monitoring-checks.md).
5.  The CRON schedule for running [*daily partition checks*](definition-of-data-quality-checks/partition-checks.md).
6.  The CRON schedule for running [*monthly partition checks*](definition-of-data-quality-checks/partition-checks.md).


When a new data source is added to DQOps, the configuration of CRON schedules is copied from the default schedules
that are stored in the [*settings/defaultschedules.dqoschedules.yaml*](../reference/yaml/DefaultSchedulesYaml.md) file.
The schedules shown in the example above are used as the initial configuration.

- [*profiling checks*](definition-of-data-quality-checks/data-profiling-checks.md) are run once a month, on the 1st day of the month
  at 12:00 PM.

- all other types of data quality checks ([*monitoring checks*](definition-of-data-quality-checks/data-observability-monitoring-checks.md),
  [*partition checks*](definition-of-data-quality-checks/partition-checks.md)) are run every day at 12:00 PM.

Consult the [data quality check scheduling manual](../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md)
to see how to configure data quality scheduling from the [DQOps user interface](dqops-user-interface-overview.md).

The full documentation of the `schedules` element is provided in the
[DefaultSchedulesSpec](../reference/yaml/ConnectionYaml.md#defaultschedulesspec) object reference. 


### **Data quality issue to incident grouping**
DQOps [groups similar data quality issues into data quality incidents](grouping-data-quality-issues-to-incidents.md), the matching
method is described in the [data quality incidents](grouping-data-quality-issues-to-incidents.md) article.

The following example shows where the configuration is stored in the
*[connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md)* file.

``` { .yaml .annotate linenums="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  ...
  incident_grouping: # (1)!
    grouping_level: table_dimension_category # (2)!
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60 
```

1.  The `incident_grouping` node with the configuration of grouping data quality issues to incidents.
2.  The data quality issue mapping method. 

The full documentation of the `incident_grouping` element is provided in the
[ConnectionIncidentGroupingSpec](../reference/yaml/ConnectionYaml.md#connectionincidentgroupingspec) object reference.


### **Incident notification webhooks**
The urls of [webhooks](../integrations/webhooks/index.md) that DQOps should call
when new [data quality incidents](grouping-data-quality-issues-to-incidents.md) are identified, or the status
of an incident was changed.

This configuration overrides the default settings stored in the [*settings/defaultnotifications.dqonotifications.yaml*](../reference/yaml/DefaultNotificationsYaml.md) file
with data source specific configuration, allowing to send the notification to the data source owner or the right data engineering team.

``` { .yaml .annotate linenums="1" hl_lines="6 11-15"  }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  ...
  incident_grouping:
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60 
    webhooks: # (1)!
      incident_opened_webhook_url: https://my.ticketingsystem.com/on_new_incident_detected # (2)!
      incident_acknowledged_webhook_url: https://my.ticketingsystem.com/on_incident_confirmed_by_ops_team # (3)!
      incident_resolved_webhook_url: https://my.ticketingsystem.com/on_incident_resolved # (4)!
      incident_muted_webhook_url: https://my.ticketingsystem.com/on_incident_muted # (5)!
```

1.  The webhook configuration node.
2.  The webhook url where DQOps POSTs [notifications](../reference/yaml/IncidentNotificationMessage.md) of new data quality incidents that were just detected.
3.  The webhook url where DQOps POSTs [notifications](../reference/yaml/IncidentNotificationMessage.md) of data quality incidents that were reviewed and assigned for resolution.
4.  The webhook url where DQOps POSTs [notifications](../reference/yaml/IncidentNotificationMessage.md) of data quality incidents that were resolved and the data quality checks can be run again to validate the fix.
5.  The webhook url where DQOps POSTs [notifications](../reference/yaml/IncidentNotificationMessage.md) of data quality incidents that were muted, because the incident was identified as a low priority or out-of-scope.


The full documentation of the `incident_grouping.webhooks` element is provided in the
[IncidentWebhookNotificationsSpec](../reference/yaml/ConnectionYaml.md#incidentwebhooknotificationsspec) object reference.


## Additional table configuration
The following examples show how to apply additional configuration on a table level.

### **Table filter predicate**
DQOps analyzes all rows in a table. This behavior may not be always desired.
In order to analyze only a subset of rows, a filter predicate that is added to the **WHERE** SQL clause
should be defined. The filter predicate may use a `{alias}.` token to reference the analyzed table.
The `{alias}` is replaced with the table alias that is used in the query.

Almost all data quality sensors in DQOps use `analyzed_table` as the name of the alias, but some more complex
sensors that need to apply joins and other SQL clauses may use a different alias.

The following example shows a table alias that will analyze the data only after 2023-11-06.

``` { .yaml .annotate linenums="1" hl_lines="5" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  filter: "{alias}.date >= '2023-11-06'" # (1)!
  columns:
    date:
      type_snapshot:
        column_type: DATE
        nullable: true
    cumulative_confirmed:
      monitoring_checks:
        daily:
          nulls:
            daily_nulls_percent:
              warning:
                max_percent: 0
```

1. The table filter using a token `{alias}` that is replaced with the real table alias that is used in the SQL query.


### **Applying table filters at a data quality check level**

DQOps also supports setting the filter at a data quality check level. The filter will affect only a single check, while all other
checks defined on the table will analyze the whole table or use the table-level filter. The filter predicate
is specified in the `parameters.filter` node inside the check's configuration as shown on the following example.

For further guidance of configuring checks, read the [configuring data quality checks](configuring-data-quality-checks-and-rules.md) article.

``` { .yaml .annotate linenums="1" hl_lines="15-16" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    date:
      type_snapshot:
        column_type: DATE
        nullable: true
    cumulative_confirmed:
      monitoring_checks:
        daily:
          nulls:
            daily_nulls_percent:
              parameters:
                filter: "{alias}.date >= '2023-11-06'"
              warning:
                max_percent: 0
```


### **Table stage**
The tables can be grouped into stages, allowing to detect data quality issues for different stages, such as
landing zones, staging, cleansing, data marts, or other stage names specific to the environment.
Dividing tables by stage allows to calculate the [data quality KPIs](definition-of-data-quality-kpis.md)
for each stage.

DQOps does not enforce any naming convention for stages. The stages are free-form string values assigned to
a table in the *.dqotable.yaml* file.

The value of the `stage` field that was configured on the table at the time of running the check is saved in the
[sensor_readouts](../reference/parquetfiles/sensor_readouts.md) and the
[check_results](../reference/parquetfiles/check_results.md) parquet tables.
The [data quality dashboards](types-of-data-quality-dashboards.md) in DQOps are designed
to allow filtering by the stage, using the `stage` value from the tables mentioned above.

The following example shows how the `stage` field is configured.

``` { .yaml .annotate linenums="1" hl_lines="5" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  stage: landing
  columns:
    cumulative_confirmed:
      monitoring_checks:
        daily:
          nulls:
            daily_nulls_percent:
              warning:
                max_percent: 0
```


### **Table priority**
The table priority is a concept practiced by the DQOps data quality engineers when running data quality projects in an agile way.

The principle of agile software development is dividing the workload into iterations. The agile principles are realized
in DQOps by assigning numerical priorities to all tables that are initially imported at the beginning of a data quality project.

When the tables are assigned to priorities (1, 2, 3, ...), an agile data quality project should focus on improving the
data quality of the priority `1` tables first. When a satisfactory level of data quality, measured using
the [data quality KPIs](definition-of-data-quality-kpis.md) is achieved, the tables from the next priority
level are assigned to be improved in the next iteration.

The value of the `priority` field that was configured on the table at the time of running the check is saved in the
[sensor_readouts](../reference/parquetfiles/sensor_readouts.md) and the
[check_results](../reference/parquetfiles/check_results.md) parquet tables.
The [data quality dashboards](types-of-data-quality-dashboards.md) in DQOps use a filter for the table priorities,
allowing to separate data quality issues between high priority tables that should be already cleansed and lower priority tables
that are still in the data cleansing process.

The following example shows how the `priority` field is configured.

``` { .yaml .annotate linenums="1" hl_lines="5" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  priority: 1 # (1)!
  columns:
    cumulative_confirmed:
      monitoring_checks:
        daily:
          nulls:
            daily_nulls_percent:
              warning:
                max_percent: 0
```

1.  The priority **1** is the highest priority.

DQOps team has written a free eBook [Best practices for effective data quality improvement](https://dqops.com/best-practices-for-effective-data-quality-improvement/)
that describes the iterative data quality process based on table priorities in details.
Please download the eBook to learn more about the concept.

!!! info "Iterative data quality cleansing projects"

    The application of table prioritization for improving the data quality in iterations is described in the
    [data quality improvement process](definition-of-data-quality-kpis.md#data-quality-improvement-process) section
    of the data quality KPIs article. Using priorities enables a quick return of investment for data quality projects.


### **Labels**
Tables and columns can be tagged with labels. The labels are used by DQOps for targeting data quality checks
when the [checks are run](running-data-quality-checks.md).

Labels are defined in a `labels` section below the `spec` node (for a table-level label)
or below the column's node for column-level labels. The labels are defined as a list of strings values.

The following example shows how to assign one label on a table level and two labels on a column level.

``` { .yaml .annotate linenums="1" hl_lines="5-6 9-11" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  labels:
    - "fact"
  columns:
    cumulative_confirmed:
      labels:
        - "measure"
        - "not null"
      monitoring_checks:
        daily:
          nulls:
            daily_nulls_percent:
              warning:
                max_percent: 0
```


## What's next

- If you want to see how to import the metadata of data sources using DQOps user interface, 
  go back to the *getting started* section, and read the [adding data source connection](../getting-started/add-data-source-connection.md) again.
- Learn how to [configure data quality checks and rules](configuring-data-quality-checks-and-rules.md) in the *.dqotable.yaml* files.
- Learn more about managing configuration in the [`DQOps user home` folder](dqops-user-home-folder.md).
- Review the list of [data sources supported by DQOps](../data-sources/index.md) to find a step-by-step configuration manual for each data source.
- Learn what extensions are needed to activate editing DQOps configuration files in
  [Visual Studio Code with code completion and validation](../integrations/visual-studio-code/index.md).
