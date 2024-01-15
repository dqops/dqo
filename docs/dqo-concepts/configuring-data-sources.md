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
    port: 52289
    user: test
    password: test
    database: test
  schedules: # (4)!
    profiling:
      cron_expression: 0 12 1 * *
    monitoring_daily:
      cron_expression: 0 12 * * *
    monitoring_monthly:
      cron_expression: 0 12 * * *
    partitioned_daily:
      cron_expression: 0 12 * * *
    partitioned_monthly:
      cron_expression: 0 12 * * *   
  incident_grouping: # (5)!
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60 
```

1.  The type of the file is identified in the `kind` element. Data sources use a `source` kind.
2.  The type of data sources. Use the documentation of the [ConnectionSpec](../reference/yaml/ConnectionYaml.md#connectionspec) object
    to find the names of other supported data sources, beyond the `postgresql` connection type shown in this example.
3.  The configuration node for [PostgreSQL](../data-sources/postgresql.md). Other supported data sources are configured in similar elements,
    named as the type of data source.
4.  The CRON schedules for running data quality checks, divided by the [type of data quality checks](definition-of-data-quality-checks/index.md#types-of-checks).
5.  The configuration of [grouping data quality issues into incident](grouping-data-quality-issues-to-incidents.md).

TODO: describe more..., show a link to [data sources](../data-sources/index.md)


### **Configuring data sources from the user interface**
Each type of data source has its own connection configuration screen in the DQOps user interface.
Check out the [data sources](../data-sources/index.md) section to find the data source of interest,
and learn the details of configuring your connections.


## Monitored tables
The configuration of every monitored table is stored in [\*.dqotable.yaml](../reference/yaml/TableYaml.md) files,
located in the *$DQO_USER_HOME/sources/{data_source_name}* folder.
The [\*.dqotable.yaml](../reference/yaml/TableYaml.md) files are placed directly in the folder, not organized into folders by the database schema name.

### **.dqotable.yaml file names**

TODO: show also how to put table files in subfolders to manage more tables.

### **Table YAML file structure**
The following [.dqotable.yaml](../reference/yaml/TableYaml.md) file below shows the location of the most important elements.



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



TODO: move this section back to the "configuring data quality checks"

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
  profiling_checks: # (9)!
    ...
  monitoring_checks: # (10)!
    daily: # (11)!
      ...
    monthly: # (12)!
      ...
  partitioned_checks: # (13)!
    daily: # (14)!
      ...
    monthly: # (15)!
      ...
  columns: # (16)!
    first_column_name: # (17)!
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
9.  The node where [profiling checks](definition-of-data-quality-checks/data-profiling-checks.md) are configured at a table level.
10. The node where [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are configured at a table level.
11. The node where daily [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are configured at a table level.
12. The node where monthly [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are configured at a table level.
13. The node where [partitioned checks](definition-of-data-quality-checks/partition-checks.md) are configured at a table level.
14. The node where daily [partitioned checks](definition-of-data-quality-checks/partition-checks.md) are configured at a table level.
15. The node where monthly [partitioned checks](definition-of-data-quality-checks/partition-checks.md) are configured at a table level.
16. The node with a list of columns.
17. One example column name. The column-level checks for this column are defined inside the node.


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
    | 11   | `spec.profiling_checks`                                                                                | The node where [profiling checks](definition-of-data-quality-checks/data-profiling-checks.md) are configured on a table level.                                                                                                                          |               |
    | 13   | `spec.monitoring_checks`                                                                               | The node where [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are configured at a table level.                                                                                                                       |               |
    | 14   | `spec.monitoring_checks.daily`                                                                         | The node daily [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are configured at a table level.                                                                                                                       |               |
    | 16   | `spec.monitoring_checks.monthly`                                                                       | The node monthly [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are configured at a table level.                                                                                                                     |               |
    | 18   | `spec.partitioned_checks`                                                                              | The node where [partitioned checks](definition-of-data-quality-checks/partition-checks.md) are configured at a table level.                                                                                                                        |               |
    | 19   | `spec.partitioned_checks.daily`                                                                        | The node where daily [partitioned checks](definition-of-data-quality-checks/partition-checks.md) are configured at a table level.                                                                                                                  |               |
    | 21   | `spec.partitioned_checks.monthly`                                                                      | The node where monthly [partitioned checks](definition-of-data-quality-checks/partition-checks.md) are configured at a table level.                                                                                                                |               |
    | 23   | `spec.columns`                                                                                         | A node that contains an array of columns for which the data quality checks are configured.                                                                                                                                          |               |
    | 24   | `spec.columns.<first_column_name>`                                                                     | An example column named *first_column_name*. The column level data quality checks for this column are configured inside this node.                                                                                                  |               |


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
