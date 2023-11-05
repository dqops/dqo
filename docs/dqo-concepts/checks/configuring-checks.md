# Configuring data quality checks
Data quality checks are configured on monitored tables and columns in
[&lt;schema_name&gt;.&lt;table_name&gt;.dqotable.yaml](../../reference/yaml/TableYaml.md) YAML files.
These files are placed in the *[$DQO_USER_HOME/sources/&lt;connection_name&gt;/](../home-folders/dqops-user-home.md#monitored-tables)* folders
in the `DQOps user home`.
The role and layout of the `DQOps user home` folder is described on the [DQOps user home](../home-folders/dqops-user-home.md) page.


## DQOps table YAML file
The [\*.dqotable.yaml](../../reference/yaml/TableYaml.md) files are similar to Kubernetes specification files.
Additionally, the first line references a YAML schema file that is used by Visual Studio Code for code completion,
validation, and showing the documentation of checks. The concept of working with [YAML files](../yaml-files/yaml-files.md)
shows the editing experience in Visual Studio Code.


### **Table YAML file structure**
The following [.dqotable.yaml](../../reference/yaml/TableYaml.md) file below shows the location of the most important elements.

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
2.  The `spec` [table specification](../../reference/yaml/TableYaml.md#tablespec) object that describes the table, its columns
    and enabled data quality checks.
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
9.  The node where [profiling checks](./profiling-checks/profiling-checks.md) are configured at a table level.
10. The node where [monitoring checks](./monitoring-checks/monitoring-checks.md) are configured at a table level.
11. The node where daily [monitoring checks](./monitoring-checks/monitoring-checks.md) are configured at a table level.
12. The node where monthly [monitoring checks](./monitoring-checks/monitoring-checks.md) are configured at a table level.
13. The node where [partitioned checks](./partition-checks/partition-checks.md) are configured at a table level.
14. The node where daily [partitioned checks](./partition-checks/partition-checks.md) are configured at a table level.
15. The node where monthly [partitioned checks](./partition-checks/partition-checks.md) are configured at a table level.
16. The node with a list of columns.
17. One example column name. The column level checks for this column are defined inside the node.


### **Table YAML core elements**
The core elements found on the *.dqotable.yaml* file are described in the table below.

| Line | Element&nbsp;path&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  | Description                                                                                                                                                                                                                         | Default value |
|------|--------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------|
| 2    | `apiVersion`                                                                                           | The version of the DQOps file format                                                                                                                                                                                                | dqo/v1        |
| 3    | `kind`                                                                                                 | The file type                                                                                                                                                                                                                       | table         |
| 4    | `spec`                                                                                                 | The main content of the file, contains the [table specification](../../reference/yaml/TableYaml.md#tablespec).                                                                                                                      |               |
| 5    | `spec.timestamp_columns`                                                                               | The configuration of timestamp columns that are used for timeliness checks such as freshness, and for running partitioned checks.                                                                                                   |               |
| 6    | `spec.timestamp_columns.` `event_timestamp_column`                                                     | The column name (*date* in this example) that contains an event timestamp that is used to measure timeliness. It should be a column that identifies the event or transaction timestamp.                                             |               |
| 7    | `spec.timestamp_columns.` `partition_by_column`                                                        | The column name (*date* in this example) that will be used in partitioned checks to detect data quality issues at a date partition or for each daily or monthly time period. It can be a date, datetime or a timestamp column name. |               |
| 8    | `spec.incremental_time_window`                                                                         | The configuration for the recent time window used to run partitioned data quality checks incrementally.                                                                                                                             |               |
| 9    | `spec.incremental_time_window.` `daily_partitioning_recent_days`                                       | The number of recent days used to analyze the data incrementally in daily partitioned checks.                                                                                                                                       | 7             |
| 10   | `spec.incremental_time_window.` `monthly_partitioning_recent_months`                                   | The number of recent months used to analyze the data incrementally in monthly partitioned checks. *1* means that the previous month is analyzed only.                                                                               | 1             |
| 11   | `spec.profiling_checks`                                                                                | The node where [profiling checks](./profiling-checks/profiling-checks.md) are configured on a table level.                                                                                                                          |               |
| 13   | `spec.monitoring_checks`                                                                               | The node where [monitoring checks](./monitoring-checks/monitoring-checks.md) are configured at a table level.                                                                                                                       |               |
| 14   | `spec.monitoring_checks.daily`                                                                         | The node daily [monitoring checks](./monitoring-checks/monitoring-checks.md) are configured at a table level.                                                                                                                       |               |
| 16   | `spec.monitoring_checks.monthly`                                                                       | The node monthly [monitoring checks](./monitoring-checks/monitoring-checks.md) are configured at a table level.                                                                                                                     |               |
| 18   | `spec.partitioned_checks`                                                                              | The node where [partitioned checks](./partition-checks/partition-checks.md) are configured at a table level.                                                                                                                        |               |
| 19   | `spec.partitioned_checks.daily`                                                                        | The node where daily [partitioned checks](./partition-checks/partition-checks.md) are configured at a table level.                                                                                                                  |               |
| 21   | `spec.partitioned_checks.monthly`                                                                      | The node where monthly [partitioned checks](./partition-checks/partition-checks.md) are configured at a table level.                                                                                                                |               |
| 23   | `spec.columns`                                                                                         | A node that contains an array of columns for which the data quality checks are configured.                                                                                                                                          |               |
| 24   | `spec.columns.<first_column_name>`                                                                     | An example column named *first_column_name*. The column level data quality checks for this column are configured inside this node.                                                                                                  |               |


## Configuring table level checks
The data quality checks can be configured both at a table level and at a column level, depending on the type of the check.

The configuration of data quality checks will be shown on the example of a profiling check.


### **Table level profiling checks**
The table [profiling checks](./profiling-checks/profiling-checks.md) are meant to capture advanced data quality
statistics and store the most current value for each month. Their role is to track the overall quality of data,
without affecting the [data quality KPIs](../data-quality-kpis/data-quality-kpis.md).

The example below shows a configuration of the [profile_row_count](../../checks/table/volume/row-count.md#profile-row-count)
with only a **warning** severity rule that verifies if the table's row count is at least one row.
A warning severity issue will be raised when a result of a query similar to `SELECT COUNT(*) FROM <monitored_table>`
will be 0, which means that the table is empty.

``` { .yaml .annotate linenums="1" hl_lines="8-10" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:

  profiling_checks: # (1)!
    volume: # (2)!
      profile_row_count: # (3)!
        warning: # (4)!
          min_count: 1 # (5)!
    schema: # (6)!
      ...
  ...
```

1.  The table [profiling checks specification](../../reference/yaml/profiling/table-profiling-checks.md#tableprofilingcheckcategoriesspec)
    where the profiling checks are configured.
2.  A *volume* category node. Other categories are sibling nodes of the *volume*.
3.  The configuration of the [profile_row_count](../../checks/table/volume/row-count.md#profile-row-count) data quality check.
4.  The configuration of a [data quality rule](../rules/rules.md) at a **warning** severity level. This rule will raise
    a **warning** severity level data quality issue if the *sensor readout* does not meet the rule parameter. 
5.  The rule parameter for the [min_count](../../reference/rules/Comparison.md#min-count) rule. It is the smallest
    accepted row count (the *sensor readout* captured by the data quality check's sensor) that will make the rule pass.
6.  Another category node for the table schema checks.

The elements of the profiling checks configuration are listed in the table below.

| Line  | Element&nbsp;path (within the `spec` node)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | Description                                                                                                                                                                                                                             |
|-------|--------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 6     | `profiling_checks`                                                                                                             | The table level [profiling checks containerprofiling checks specification](../../reference/yaml/profiling/table-profiling-checks.md#tableprofilingcheckcategoriesspec) where the profiling checks are configured.                       |
| 7     | `profiling_checks.volume`                                                                                                      | A *volume* category node. Similar data quality checks are grouped in caregories. Other categories are sibling nodes of this node.                                                                                                       |
| 8     | `profiling_checks.volume.` `profile_row_count`                                                                                 | The configuration of the [profile_row_count](../../checks/table/volume/row-count.md#profile-row-count) data quality check. When a node with the name of the data quality check is added to the category node, it check becomes enabled. |
| 9     | `profiling_checks.volume.` `profile_row_count.warning`                                                                         | The configuration of a [data quality rule](../rules/rules.md) at a **warning** severity level. This rule will raise  a **warning** severity level data quality issue if the *sensor readout* does not meet the rule parameter.          |
| 10    | `profiling_checks.volume.` `profile_row_count.warning.min_count`                                                               | The rule parameter for the [min_count](../../reference/rules/Comparison.md#min-count) rule. It is the smallest accepted row count (the *sensor readout* captured by the data quality check's sensor) that will make the rule pass.      |
| 11    | `profiling_checks.schema`                                                                                                      | Yet another check category node.                                                                                                                                                                                                        |   


### **Table level monitoring checks**
The [monitoring checks](./monitoring-checks/monitoring-checks.md) are the primary type of data quality checks
used in DQOps for continuous monitoring of the data quality of the data source. 
The data quality issues raised by these checks are decreasing the [data quality KPI](../data-quality-kpis/data-quality-kpis.md). 

The monitoring checks are configured in the `spec.monitoring_checks` node and are divided into *daily* and *monthly* monitoring checks.
The daily monitoring checks keep only the most current check result for the day when the check was executed. Running the same check
again on the same day overrides the previously stored value, but does not delete the results from past days. The same principle is
followed by the monthly monitoring check, that store only one value for each calendar month.

The following example shows a table YAML file with the row count check configured both at a daily and monthly periods.
Please notice that the names of the checks are different, because all data quality check names are unique in DQOps.

The daily monitoring check variant is [daily_row_count](../../checks/table/volume/row-count.md#daily-row-count) and
the monthly monitoring check variant is [monthly_row_count](../../checks/table/volume/row-count.md#monthly-row-count).

``` { .yaml .annotate linenums="1" hl_lines="7 12" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:

  monitoring_checks: # (1)!
    daily: # (2)!
      volume:
        daily_row_count: # (3)!
          warning:
            min_count: 1
    monthly: # (4)!
      volume:
        monthly_row_count: # (5)!
          warning:
            min_count: 1
  ...
```

1.  The table [monitoring checks specification](../../reference/yaml/TableYaml.md#tablemonitoringchecksspec)
    where the monitoring checks are configured.
2.  The **daily** monitoring checks container.
3.  The configuration of the [daily_row_count](../../checks/table/volume/row-count.md#daily-row-count) data quality check.
4.  The **monthly** monitoring checks container.
5.  The configuration of the [monthly_row_count](../../checks/table/volume/row-count.md#monthly-row-count) data quality check.


### **Table level partitioned checks**
Table [partitioned checks](./partition-checks/partition-checks.md) allow analyzing even petabyte scale
tables that are constantly growing, because new data are appended to new partitions. 
Running data quality checks as queries on such big tables is time consuming, and can generate high charges
when the data is hosted in the cloud.

The partitioned checks require that the `spec.timestamp_columns.partition_by_column` field is set. 
The value of this field is a column name that is a partitioning key. DQOps is not limited to analyzing tables
that are physically date partitioned. It is possible to select any *date/datetime/timestamp* column that
divides the data by date ranges. The partitioned checks will use a **GROUP BY** clause, grouping the data
by the selected `partition_by_column` column, allowing to detect data quality issues in time ranges.

This column should be a *date*, *datetime* or a *timestamp* type of a column.
The actual column data types depend on the monitored database type.
However, when the column is not a date type because it is a text column, DQOps supports configuring
calculated columns in the table YAML metadata. The calculated columns are virtual columns that are defined as an SQL
expression using the target data source specific SQL grammar.

The configuration of partitioned checks is similar to the configuration of monitoring checks, they are also
divided into **daily** and **monthly** partitioned checks.

``` { .yaml .annotate linenums="1" hl_lines="13 18" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    partition_by_column: date # (1)!
  incremental_time_window:
    daily_partitioning_recent_days: 7 # (2)!
    daily_partitioning_include_today: false
    monthly_partitioning_recent_months: 1 # (3)!
    monthly_partitioning_include_current_month: false
  partitioned_checks: # (4)!
    daily: # (5)!
      volume:
        daily_partition_row_count: # (6)!
          warning:
            min_count: 1
    monthly: # (7)!
      volume:
        monthly_partition_row_count: # (8)!
          warning:
            min_count: 1
  ...
```

1.  The column name in the table that is used as the date partitioning key. It will be used by the partitioned
    checks to calculate the data quality results for **daily** and **monthly** time periods. The partitioning column in this example
    is the column named `date` from the tested table.
2.  The number of recent days queried when running the **daily** partitioned checks.
3.  The number of recent months queried when running the **monthly** partitioned checks.
4.  The table [partitioned checks specification](../../reference/yaml/TableYaml.md#tablepartitionedchecksrootspec)
    where the partition(ed) checks are configured.
5.  The **daily** partitioned checks container.
6.  The configuration of the [daily_partition_row_count](../../checks/table/volume/row-count.md#daily-partition-row-count) data quality check.
7.  The **monthly** partitioned checks container.
8.  The configuration of the [monthly_partition_row_count](../../checks/table/volume/row-count.md#monthly-partition-row-count) data quality check.

The `incremental_time_window` section configures how the incremental data quality check execution work on partitioned data.
DQOps generates SQL queries from the Jinja2 sensor templates by adding a **WHERE** clause that applies a query filter
on the table, scanning only the last 7 days for daily checks or the data since the 1st day of the previous month for monthly checks.
The additional filter predicate is similar to the following SQL fragment.

``` sql
WHERE analyzed_table."date" >= DATE_ADD(CURRENT_DATE(), INTERVAL -{{daily_partitioning_recent_days}} DAY)
```

Because the data may change during the day, the default configuration of incremental partitioned checks in DQOps
excludes today for daily checks and the current month from monthly checks.
The default values for the `daily_partitioning_include_today` and `monthly_partitioning_include_current_month` is *false*.


## Configuring issue severity levels
The data quality checks in DQOps allow setting different rule thresholds for different data quality issue severity levels.
The severity levels are **warning** to receive a simple warning when the issue is identified,
**error** when the issue should be resolved, and should decrease the data quality KPI score,
and **fatal** when the issue is so serious that the data pipeline should be stopped.

The following example shows how to set three different threshold levels for a row count check. 

``` { .yaml .annotate linenums="1" hl_lines="9 11 13" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    daily:
      volume:
        daily_row_count:
          warning: # (1)!
            min_count: 1000
          error: # (2)!
            min_count: 100
          fatal: # (3)!
            min_count: 1           
```

1.  The **warning** severity level rule.
2.  The **error** severity level rule.
3.  The **fatal** severity level rule.

It is worth mentioning that the thresholds for the **warning**, **error**, and **fatal** rules should be inclusive.
In this example, the **warning** rule raises a **warning** severity data quality issue when the row count
drops below 1000 rows. When the table has 999 rows, a **warning** will be raised, but the thresholds
for the **error** and **fatal** severity issues are lower, so these rules will pass successfully.

Setting up a threshold for the **warning** severity rule below the threshold for the **error** severity rule by mistake
will make the **warning** rule ineffective, raising only **error** severity issues.


## Checks without rules
Data quality checks without any rules are a special case in DQOps.
These checks will only capture the *sensor result* from the [sensor](../sensors/sensors.md) and store
it in both the [sensor_readouts](../../reference/parquetfiles/sensor_readouts.md), and the
check results [check_results](../../reference/parquetfiles/check_results.md) parquet tables.

The issue severity level for these checks will be always *valid*, storing the value 0 in the `severity` column
in the [check_results](../../reference/parquetfiles/check_results.md) parquet table.
Checks without any rules enabled will also not be included in the data quality KPI, because their `include_in_kpi`
flag will be *false*. The flag `include_in_sla` will be also *false*.

Due to a limitation in the YAML format, a node without a value makes the YAML file invalid.
However, every YAML supports JSON inclusions. 

The following example shows how to enable a check without setting any rules by setting its value
to a JSON `{}` empty object.

``` { .yaml .annotate linenums="1" hl_lines="8" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    daily:
      volume:
        daily_row_count: {}
```

## Rules without parameters
Some data quality [rules](../rules/rules.md) do not have any parameters (thresholds).
Configuring these rules uses the same YAML/JSON trick to set the value of the rule to a JSON `{}` object,
enabling the rule at the given severity level.

The following example shows the [daily_column_count_changed](../../checks/table/schema/column-count-changed.md#daily-column-count-changed)
check that compares the column count captured today to the yesterday's (or any earlier, known most recent row count),
detecting if the column count on the table has recently changed. A **warning** severity issue is raised
on the day when the column count change was detected.

``` { .yaml .annotate linenums="1" hl_lines="9" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    daily:
      schema:
        daily_column_count_changed:
          warning: {}
```

## Configuring columns

### **Column profiling checks**

### **Column monitoring checks**

### **Column partitioned checks**

### **Calculated columns**


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
    volume: # (10)!
      profile_row_count: # (11)!
        warning: # (12)!
          min_count: 1 # (13)!
  monitoring_checks: # (14)!
    daily: # (15)!
      volume:
        daily_row_count: # (16)!
          warning: # (17)!
            min_count: 1000
          error: # (18)!
            min_count: 100
          fatal: # (19)!
            min_count: 1           
  partitioned_checks: # (20)!
    daily: # (21)!
      volume:
        daily_partition_row_count:
          error:
            min_count: 1
  columns: # (22)!
    date: # (23)!
      type_snapshot: # (24)!
        column_type: DATE
        nullable: true
      profiling_checks: # (25)!
        nulls:
          profile_nulls_count: {} # (26)!
          profile_nulls_percent: {}
        schema:
          profile_column_exists:
            warning:
              expected_value: 1
          profile_column_type_changed:
            warning: {}
      monitoring_checks: # (27)!
        daily:
          nulls:
            daily_nulls_count: {}
      partitioned_checks: # (28)!
        daily:
          nulls:
            daily_partition_not_nulls_percent:
              warning:
                min_percent: 100.0    
```

1.  The type of the file is identified in the `kind` element.
2.  The `spec` [table specification](../../reference/yaml/TableYaml.md#tablespec) object that describes the table, its columns
    and enabled data quality checks.
3.  The configuration of timestamp columns that are used for timeliness checks such as freshness, and for running partitioned checks.
4.  The column name (*date* in this example) that contains an event timestamp that is used to measure timeliness.
5.  The column name (*date* in this example) that will be used in **GROUP BY** queries to measure the data quality at a partition level.
    The table does not need to be partitioned by this column physically. DQOps uses this column to detect data quality issues
    within time ranges.
6.  in t
7.  daily days
8.  monthly days
9.   9
10. 10
11. 11
12. 12
13. 13
14. 14
15. 15
16. 16
17. 17
18. 18
19. 19
20. 20
21. 21
22. 22
23. 23
24. 24
25. 25
26. 26
27. 27
28. 28


The elements are described in the table below.

| Line | Element&nbsp;path | Description                                                                                                    | Default value |
|------|-------------------|----------------------------------------------------------------------------------------------------------------|--------------|
| 2    | `apiVersion`      | The version of the DQOps file format                                                                           | dqo/v1       |
| 3    | `kind`            | The file type                                                                                                  | table        |
| 4    | `spec`            | The main content of the file, contains the [table specification](../../reference/yaml/TableYaml.md#tablespec). ||



``` { .yaml .annotate }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table(1)
spec:(2)
  timestamp_columns:(3)
    event_timestamp_column: date(4)
    partition_by_column: date(5)
  incremental_time_window:(6)
    daily_partitioning_recent_days: 7(7)
    monthly_partitioning_recent_months: 1(8)
  profiling_checks:(9)
    volume:(10)
      profile_row_count:(11)
        warning:(12)
          min_count: 1(13)
  monitoring_checks:(14)
    daily:(15)
      volume:
        daily_row_count:
          warning:
            min_count: 1000
          error:
            min_count: 100
          fatal:
            min_count: 1           
    monthly:(16)
      volume:
        monthly_row_count:
          warning:
            min_count: 1
  partitioned_checks:(17)
    daily:(18)
      volume:
        daily_partition_row_count:
          error:
            min_count: 1
  columns:
    date:
      type_snapshot:
        column_type: DATE
        nullable: true
      profiling_checks:
        nulls:
          profile_nulls_count: {}
          profile_nulls_percent: {}
      monitoring_checks:
        daily:
          nulls:
            daily_nulls_count: {}
            daily_nulls_percent: {}
            daily_nulls_percent_anomaly_stationary:
              warning:
                anomaly_percent: 1.0
            daily_nulls_percent_change_yesterday:
              warning:
                max_percent: 10.0
                exact_day: false
            daily_not_nulls_percent: {}
          schema:
            daily_column_exists:
              warning:
                expected_value: 1
            daily_column_type_changed:
              warning: {}
      partitioned_checks:
        daily:
          nulls:
            daily_partition_not_nulls_percent:
              warning:
                min_percent: 100.0
```

1.  The type of the file is identified in the `kind` element.
2.  The `spec` object 



``` { .yaml .annotate }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: date
    partition_by_column: date
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    volume:
      profile_row_count:
        warning:
          min_count: 1
    availability:
      profile_table_availability:
        warning:
          max_failures: 0
    schema:
      profile_column_count: {}
  monitoring_checks:
    daily:
      volume:
        daily_row_count:
          warning:
            min_count: 1
        daily_row_count_change:
          warning:
            max_percent: 10.0
        daily_row_count_anomaly_differencing:
          warning:
            anomaly_percent: 1.0
      availability:
        daily_table_availability:
          warning:
            max_failures: 0
      schema:
        daily_column_count_changed:
          warning: {}
        daily_column_list_changed:
          warning: {}
        daily_column_list_or_order_changed:
          warning: {}
        daily_column_types_changed:
          warning: {}
    monthly:
      volume:
        monthly_row_count:
          warning:
            min_count: 1
      availability:
        monthly_table_availability:
          warning:
            max_failures: 0
      schema:
        monthly_column_count: {}
        monthly_column_count_changed:
          warning: {}
        monthly_column_list_changed:
          warning: {}
        monthly_column_list_or_order_changed:
          warning: {}
        monthly_column_types_changed:
          warning: {}
  partitioned_checks:
    daily:
      volume:
        daily_partition_row_count:
          error:
            min_count: 1
  columns:
    date:
      type_snapshot:
        column_type: DATE
        nullable: true
      profiling_checks:
        nulls:
          profile_nulls_count: {}
          profile_nulls_percent: {}
        schema:
          profile_column_exists:
            warning:
              expected_value: 1
          profile_column_type_changed:
            warning: {}
      monitoring_checks:
        daily:
          nulls:
            daily_nulls_count: {}
            daily_nulls_percent: {}
            daily_nulls_percent_anomaly_stationary:
              warning:
                anomaly_percent: 1.0
            daily_nulls_percent_change_yesterday:
              warning:
                max_percent: 10.0
                exact_day: false
            daily_not_nulls_percent: {}
          schema:
            daily_column_exists:
              warning:
                expected_value: 1
            daily_column_type_changed:
              warning: {}
        monthly:
          nulls:
            monthly_nulls_count: {}
            monthly_nulls_percent: {}
          schema:
            monthly_column_exists:
              warning:
                expected_value: 1
            monthly_column_type_changed:
              warning: {}
```

## Default data quality checks
DQOps maintains a configuration of the [default data quality checks](../home-folders/dqops-user-home.md#shared-settings)
that are applied on the tables and columns when the tables are imported into DQOps.

We are using a table from the freely available Google BigQuery public datasets.
The table below is *bigquery-public-data.covid19_open_data.covid19_open_data*, but the example shows only one column, the *cumulative_confirmed*.
This column is numeric, which allowed to activate some numeric anomaly checks, 
such as the [daily_sum_anomaly_differencing](../../checks/column/anomaly/sum-anomaly-differencing.md#daily-sum-anomaly-differencing)
check that will compare the sum of values per day and raise a warning if the change since yesterday is greater than 10%.


??? info "Click to see a full *.dqotable.yaml* file with all default observability checks enabled"

    ``` yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      profiling_checks:
        volume:
          profile_row_count:
            warning:
              min_count: 1
        schema:
          profile_column_count: {}
      monitoring_checks:
        daily:
          volume:
            daily_row_count:
              warning:
                min_count: 1
            daily_row_count_change:
              warning:
                max_percent: 10.0
            daily_row_count_anomaly_differencing:
              warning:
                anomaly_percent: 1.0
          availability:
            daily_table_availability:
              warning:
                max_failures: 0
          schema:
            daily_column_count_changed:
              warning: {}
            daily_column_list_changed:
              warning: {}
            daily_column_list_or_order_changed:
              warning: {}
            daily_column_types_changed:
              warning: {}
      columns:
        cumulative_confirmed:
          type_snapshot:
            column_type: INT64
            nullable: true
          profiling_checks:
            nulls:
              profile_nulls_count: {}
              profile_nulls_percent: {}
          monitoring_checks:
            daily:
              nulls:
                daily_nulls_count: {}
                daily_nulls_percent: {}
                daily_nulls_percent_anomaly_stationary:
                  warning:
                    anomaly_percent: 1.0
                daily_nulls_percent_change_yesterday:
                  warning:
                    max_percent: 10.0
                    exact_day: false
                daily_not_nulls_percent: {}
              anomaly:
                daily_mean_anomaly_stationary:
                  warning:
                    anomaly_percent: 1.0
                daily_sum_anomaly_differencing:
                  warning:
                    anomaly_percent: 1.0
              schema:
                daily_column_exists:
                  warning:
                    expected_value: 1
                daily_column_type_changed:
                  warning: {}
    ```
