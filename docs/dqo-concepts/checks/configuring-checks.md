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
17. One example column name. The column-level checks for this column are defined inside the node.


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


## Configuring table-level checks
The data quality checks can be configured both at a table level and at a column level, depending on the type of the check.

The configuration of data quality checks will be shown on the example of a profiling check.


### **Table-level profiling checks**
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
| 6     | `profiling_checks`                                                                                                             | The table-level [profiling checks containerprofiling checks specification](../../reference/yaml/profiling/table-profiling-checks.md#tableprofilingcheckcategoriesspec) where the profiling checks are configured.                       |
| 7     | `profiling_checks.volume`                                                                                                      | A *volume* category node. Similar data quality checks are grouped in caregories. Other categories are sibling nodes of this node.                                                                                                       |
| 8     | `profiling_checks.volume.` `profile_row_count`                                                                                 | The configuration of the [profile_row_count](../../checks/table/volume/row-count.md#profile-row-count) data quality check. When a node with the name of the data quality check is added to the category node, it check becomes enabled. |
| 9     | `profiling_checks.volume.` `profile_row_count.warning`                                                                         | The configuration of a [data quality rule](../rules/rules.md) at a **warning** severity level. This rule will raise  a **warning** severity level data quality issue if the *sensor readout* does not meet the rule parameter.          |
| 10    | `profiling_checks.volume.` `profile_row_count.warning.min_count`                                                               | The rule parameter for the [min_count](../../reference/rules/Comparison.md#min-count) rule. It is the smallest accepted row count (the *sensor readout* captured by the data quality check's sensor) that will make the rule pass.      |
| 11    | `profiling_checks.schema`                                                                                                      | Yet another check category node.                                                                                                                                                                                                        |   


### **Table-level monitoring checks**
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


### **Table-level partitioned checks**
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

## Checks with sensor's parameters
Some data quality [sensors](../sensors/sensors.md) are parametrized. The parameters are used
in the SQL template and will be rendered in the SQL query that is generated from the sensor.

The following example shows how to use the
[daily_string_value_in_set_percent](../../checks/column/strings/string-value-in-set-percent.md#daily-string-value-in-set-percent)
check that uses a `expected_values` parameter that is an array of strings which are the valid values expected in the column.
This check counts the percentage of rows that have one of the expected values stored in the *country* column.

``` { .yaml .annotate linenums="1" hl_lines="14-17" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    country:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          strings:
            daily_string_value_in_set_percent:
              parameters:
                expected_values:
                  - "US" # (1)!
                  - "UK"
              error:
                min_percent: 100
```

1.  The value that is expected in the column.

The `parameters` node is present in every data quality check, but it is not saved to the *.dqotable.yaml* file
when no parameters are specified.


## Configuring columns
The list of columns is stored in the `spec.columns` node in the *.dqotable.yaml* file.
The `spec.columns` node is a dictionary of [column specifications](../../reference/yaml/TableYaml.md#columnspec),
the keys are the column names.

Columns are added to the *.dqotable.yaml* when a table's metadata is imported into DQOps.

### **Column schema** 
The following example shows a *.dqotable.yaml* file with two columns.

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
    to decide if some data type specific data quality checks could be enabled on the column.
4.  The data type of the column, it is a physical data type introspected from the monitored table.

The node for each column contains a [type snapshot](../../reference/yaml/TableYaml.md#columntypesnapshotspec) object
that is used by DQOps in the following cases:

- The default [data quality checks](#default-data-quality-checks) are enabled depending on the column's data type.
  Numeric anomaly checks are enabled only on numeric columns such as the *cumulative_confirmed* column in the example above.

- The data quality [sensors](../sensors/sensors.md) may use the column's data type to decide if an additional type casting
  must be generated in the SQL query that will capture the metrics for the data quality check.

DQOps does not require that each column has the `type_snapshot` node defined. All the data quality checks will work
without knowing the column's data type.


### **Column profiling checks**
[Profiling checks](./profiling-checks/profiling-checks.md) are configured on columns the same way as on tables.
Only a different set of data quality checks is available, because column-level checks must be executed on a column.
The column name is included in the generated SQL query rendered from a sensor's template.

The following example shows column profiling checks configured on a column.

``` { .yaml .annotate linenums="1" hl_lines="10-21" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    cumulative_confirmed:
      type_snapshot:
        column_type: INT64
        nullable: true
      profiling_checks: # (1)!
        nulls:
          profile_nulls_count: {} # (2)!
          profile_nulls_percent:  # (3)!
            warning:
              max_percent: 0
            error:
              max_percent: 1
        schema:
          profile_column_exists: # (4)!
            warning:
              expected_value: 1 # (5)!
    date:
      type_snapshot:
        column_type: DATE
        nullable: true
```

1.  The container of the column-level [profiling checks](./profiling-checks/profiling-checks.md).
2.  [profile_nulls_count](../../checks/column/nulls/nulls-count.md#profile-nulls-count) check without any rules,
    the check will only capture the number of rows with a null value in the *cumulative_confirmed* column.
3.  [profile_nulls_percent](../../checks/column/nulls/nulls-percent.md#profile-nulls-percent) check that measures a percentage
    of rows with null values, instead of returning the number of rows. The check is configured to raise a **warning** severity
    issue when any null rows were detected (the percentage of null values is above 0%). An **error** severity issue is raised
    when the percentage of rows with null values exceeds 1%.
4.  [profile_column_exists](../../checks/column/schema/column-exists.md#profile-column-exists) check that verifies that the
    column is present in the table by reading the metadata of the table. 
5.  The *expected_value* rule parameter's value is 1, which means that DQOps requires that the column was found.

### **Column monitoring checks**
Column-level [monitoring checks](./monitoring-checks/monitoring-checks.md) are also configured in a very similar way.

The following example shows using the daily monitoring variants of the profiling checks shown in the previous example.

``` { .yaml .annotate linenums="1" hl_lines="10-22" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    cumulative_confirmed:
      type_snapshot:
        column_type: INT64
        nullable: true
      monitoring_checks: # (1)!
        daily: # (2)!
          nulls:
            daily_nulls_count: {}
            daily_nulls_percent:
              warning:
                max_percent: 0
              error:
                max_percent: 1
          schema:
            daily_column_exists:
              warning:
                expected_value: 1
    date:
      type_snapshot:
        column_type: DATE
        nullable: true
```

1.  The container of the column-level [monitoring checks](./monitoring-checks/monitoring-checks.md).
2.  The container of the daily monitoring checks.

When the [scheduling](../../working-with-dqo/schedules/index.md) is enabled, these checks will be executed daily,
detecting if any rows with null values were identified (measuring the completeness of the *cumulative_confirmed* column).
Also, DQOps will retrieve the table schema from the data source and verify if the column is still found in the table's metadata. 


### **Column partitioned checks**
Configuring column-level [partitioned checks](./partition-checks/partition-checks.md) is also
not different from configuring them on a table level.

The following example shows using the completeness checks on a partition level. Please also notice that
there is no *daily_partition_column_exists* check,
because schema checks can be measured only on a whole table level by the monitoring type of checks.
Table schema drift checks cannot operate on partitions.

``` { .yaml .annotate linenums="1" hl_lines="12-20" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    partition_by_column: date # (1)!  
  columns:
    cumulative_confirmed:
      type_snapshot:
        column_type: INT64
        nullable: true
      partitioned_checks: # (2)!
        daily:
          nulls:
            daily_partition_nulls_count: {}
            daily_partition_nulls_percent:
              warning:
                max_percent: 0
              error:
                max_percent: 1
```

1.  The selection of the column that will be used for date partitioning in the **GROUP BY** SQL clause.
2.  The container of the column-level [partitioned checks](./partition-checks/partition-checks.md).


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
            daily_string_value_in_set_percent:
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
These columns must be casted to a correct data type before they could be used to perform some kind of data transformations.

Let's assume that the monitored table is an external table, backed by the following CSV file.

```
date,message
2023-11-06,Hello world
```

The *date* column contains a text value that is a valid ISO 8601 date.
We want to replace all usages of the column reference with an SQL expression that will cast the column's value to a DATE.

When the *date* column is casted to a *DATE* type, we can use it as a partitioning column for partitioned checks
or run date specific data quality checks such as 
the [daily-date-values-in-future-percent](../../checks/column/datetime/date-values-in-future-percent.md#daily-date-values-in-future-percent)
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

DQOps also supports setting the filter at a check level. The filter will affect only a single check, while all other
checks defined on the table will analyze the whole table or use the table-level filter. The filter predicate
is specified in the `parameters.filter` node inside the check's configuration as shown on the following example.

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
Dividing tables by stage allows to calculate the [data quality KPIs](../data-quality-kpis/data-quality-kpis.md)
for each stage.

DQOps does not enforce any naming convention for stages. The stages are free-form string values assigned to
a table in the *.dqotable.yaml* file.

The value of the `stage` field that was configured on the table at the time of running the check is saved in the
[sensor_readouts](../../reference/parquetfiles/sensor_readouts.md) and the
[check_results](../../reference/parquetfiles/check_results.md) parquet tables. 
The [data quality dashboards](../data-quality-dashboards/data-quality-dashboards.md) in DQOps are designed
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
The table priority is a concept practices by the DQOps data quality engineers when running data quality projects in an agile way.

The principle of agile software development is dividing the work load into iterations. The agile principles are realized
in DQOps by assigning numerical priorities to all tables that are initially imported at the beginning of a data quality project.

When the tables are assigned to priorities (1, 2, 3, ...), an agile data quality project should focus on improving the
data quality of the priority `1` tables first. When a satisfactory level of data quality, measured using
the [data quality KPIs](../data-quality-kpis/data-quality-kpis.md) is achieved, the tables from the next priority
level are assigned to improve in the next interation.

The value of the `priority` field that was configured on the table at the time of running the check is saved in the
[sensor_readouts](../../reference/parquetfiles/sensor_readouts.md) and the
[check_results](../../reference/parquetfiles/check_results.md) parquet tables.
The [data quality dashboards](../data-quality-dashboards/data-quality-dashboards.md) in DQOps use a filter for the table priorities,
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

DQOps team has wrote a free eBook [Best practices for effective data quality improvement](https://dqops.com/best-practices-for-effective-data-quality-improvement/)
that describes the iterative data quality process based on table priorities in details.
Please download the eBook to learn more about the concept.


### **Labels**
Tables and columns can be tagged with labels. The labels are used by DQOps for targeting data quality checks
when the [checks are run](../running-checks/running-checks.md).

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


## Default data quality checks
DQOps maintains a configuration of the [default data quality checks](../home-folders/dqops-user-home.md#shared-settings)
that are applied on the tables and columns when the tables are imported into DQOps.

We are using a table from the freely available Google BigQuery public datasets.
The table below is *bigquery-public-data.covid19_open_data.covid19_open_data*, but the example shows only one column, the *cumulative_confirmed*.
This column is numeric, which allowed to activate some numeric anomaly checks, 
such as the [daily_sum_anomaly_differencing](../../checks/column/anomaly/sum-anomaly-differencing.md#daily-sum-anomaly-differencing)
check that will compare the sum of values per day and raise a warning if the change since yesterday is greater than 10%.

??? info "Click to see a full *.dqotable.yaml* file with all default data observability checks enabled"

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

