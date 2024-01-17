# Configuring data quality checks
Read this guide to learn how to configure data quality checks in DQOps in YAML files, and how to set up the validation rules.

## Where are the data quality checks configured?
Data quality checks are configured on monitored tables and columns in
the [&lt;schema_name&gt;.&lt;table_name&gt;.dqotable.yaml](../reference/yaml/TableYaml.md) YAML files.
These files are placed in the *[$DQO_USER_HOME/sources/&lt;connection_name&gt;/](dqops-user-home-folder.md#monitored-tables)* folders
in the `DQOps user home`.
The role and layout of the `DQOps user home` folder is described on the [DQOps user home](dqops-user-home-folder.md) page.
The configuration of data sources and adding table metadata is described in the previous [configuring data sources](configuring-data-sources.md) article.

``` { .yaml .annotate linenums="1" }
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
  profiling_checks: # (1)!
    ...
  monitoring_checks: # (2)!
    daily: # (3)!
      ...
    monthly: # (4)!
      ...
  partitioned_checks: # (5)!
    daily: # (6)!
      ...
    monthly: # (7)!
      ...
```

1.  The node where [profiling checks](definition-of-data-quality-checks/data-profiling-checks.md) are configured at a table level.
2.  The node where [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are configured at a table level.
3.  The node where daily [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are configured at a table level.
4.  The node where monthly [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are configured at a table level.
5.  The node where [partitioned checks](definition-of-data-quality-checks/partition-checks.md) are configured at a table level.
6.  The node where daily [partitioned checks](definition-of-data-quality-checks/partition-checks.md) are configured at a table level.
7.  The node where monthly [partitioned checks](definition-of-data-quality-checks/partition-checks.md) are configured at a table level.


### **Table-level configuration elements**
The nodes where the table-level data quality checks are configured in the *.dqotable.yaml* file are described in the table below.

??? note "Please expand this section to see the description of all *.dqotable.yaml* file nodes"

    | Line | Element&nbsp;path&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  | Description                                                                                                                                                                                                                         | Default value |
    |------|--------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------|
    | 11   | `spec.profiling_checks`                                                                                | The node where [profiling checks](definition-of-data-quality-checks/data-profiling-checks.md) are configured on a table level.                                                                                                                          |               |
    | 13   | `spec.monitoring_checks`                                                                               | The node where [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are configured at a table level.                                                                                                                       |               |
    | 14   | `spec.monitoring_checks.daily`                                                                         | The node daily [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are configured at a table level.                                                                                                                       |               |
    | 16   | `spec.monitoring_checks.monthly`                                                                       | The node monthly [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are configured at a table level.                                                                                                                     |               |
    | 18   | `spec.partitioned_checks`                                                                              | The node where [partitioned checks](definition-of-data-quality-checks/partition-checks.md) are configured at a table level.                                                                                                                        |               |
    | 19   | `spec.partitioned_checks.daily`                                                                        | The node where daily [partitioned checks](definition-of-data-quality-checks/partition-checks.md) are configured at a table level.                                                                                                                  |               |
    | 21   | `spec.partitioned_checks.monthly`                                                                      | The node where monthly [partitioned checks](definition-of-data-quality-checks/partition-checks.md) are configured at a table level.                                                                                                                |               |


## Configuring table-level checks
The data quality checks can be configured both at a table level and at a column level, depending on the type of the check.

The configuration of data quality checks will be shown on the example of a profiling check.


### **Table-level profiling checks**
The table [profiling checks](definition-of-data-quality-checks/data-profiling-checks.md) are meant to capture advanced data quality
statistics and store the most current value for each month. Their role is to track the overall quality of data,
without affecting the [data quality KPIs](definition-of-data-quality-kpis.md).

The example below shows a configuration of the [profile_row_count](../checks/table/volume/row-count.md#profile-row-count)
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

1.  The table [profiling checks specification](../reference/yaml/profiling/table-profiling-checks.md#tableprofilingcheckcategoriesspec)
    where the profiling checks are configured.
2.  A *volume* category node. Other categories are sibling nodes of the *volume*.
3.  The configuration of the [profile_row_count](../checks/table/volume/row-count.md#profile-row-count) data quality check.
4.  The configuration of a [data quality rule](definition-of-data-quality-rules.md) at a **warning** severity level. This rule will raise
    a **warning** severity level data quality issue if the *sensor readout* does not meet the rule parameter. 
5.  The rule parameter for the [min_count](../reference/rules/Comparison.md#min-count) rule. It is the smallest
    accepted row count (the *sensor readout* captured by the data quality check's sensor) that will make the rule pass.
6.  Another category node for the table schema checks.

The elements of the profiling checks configuration are listed in the table below.

| Line | Element&nbsp;path (within the `spec` node)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | Description                                                                                                                                                                                                                                      |
|------|--------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 6    | `profiling_checks`                                                                                                             | The table-level [profiling checks container profiling checks specification](../reference/yaml/profiling/table-profiling-checks.md#tableprofilingcheckcategoriesspec) where the profiling checks are configured.                                  |
| 7    | `profiling_checks.volume`                                                                                                      | A *volume* category node. Similar data quality checks are grouped in caregories. Other categories are sibling nodes of this node.                                                                                                                |
| 8    | `profiling_checks.volume.` `profile_row_count`                                                                                 | The configuration of the [profile_row_count](../checks/table/volume/row-count.md#profile-row-count) data quality check. When a node with the name of the data quality check is added to the category node,check becomes activated.               |
| 9    | `profiling_checks.volume.` `profile_row_count.warning`                                                                         | The configuration of a [data quality rule](definition-of-data-quality-rules.md) at a **warning** severity level. This rule will raise  a **warning** severity level data quality issue if the *sensor readout* does not meet the rule parameter. |
| 10   | `profiling_checks.volume.` `profile_row_count.warning.min_count`                                                               | The rule parameter for the [min_count](../reference/rules/Comparison.md#min-count) rule. It is the smallest accepted row count (the *sensor readout* captured by the data quality check's sensor) that will make the rule pass.                  |
| 11   | `profiling_checks.schema`                                                                                                      | Yet another check category node.                                                                                                                                                                                                                 |


### **Table-level monitoring checks**
The [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are the primary type of data quality checks
used in DQOps for continuous monitoring of the data quality of the data source. 
The data quality issues raised by these checks are decreasing the [data quality KPI](definition-of-data-quality-kpis.md). 

The monitoring checks are configured in the `spec.monitoring_checks` node and are divided into *daily* and *monthly* monitoring checks.
The daily monitoring checks keep only the most current check result for the day when the check was executed. Running the same check
again on the same day overrides the previously stored value, but does not delete the results from past days. The same principle is
followed by the monthly monitoring check, that store only one value for each calendar month.

The following example shows a table YAML file with the row count check configured both at a daily and monthly periods.
Please notice that the names of the checks are different, because all data quality check names are unique in DQOps.

The daily monitoring check variant is [daily_row_count](../checks/table/volume/row-count.md#daily-row-count) and
the monthly monitoring check variant is [monthly_row_count](../checks/table/volume/row-count.md#monthly-row-count).

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

1.  The table [monitoring checks specification](../reference/yaml/TableYaml.md#tablemonitoringchecksspec)
    where the monitoring checks are configured.
2.  The **daily** monitoring checks container.
3.  The configuration of the [daily_row_count](../checks/table/volume/row-count.md#daily-row-count) data quality check.
4.  The **monthly** monitoring checks container.
5.  The configuration of the [monthly_row_count](../checks/table/volume/row-count.md#monthly-row-count) data quality check.


### **Table-level partitioned checks**
Table [partitioned checks](definition-of-data-quality-checks/partition-checks.md) allow analyzing even petabyte scale
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
4.  The table [partitioned checks specification](../reference/yaml/TableYaml.md#tablepartitionedchecksrootspec)
    where the partition(ed) checks are configured.
5.  The **daily** partitioned checks container.
6.  The configuration of the [daily_partition_row_count](../checks/table/volume/row-count.md#daily-partition-row-count) data quality check.
7.  The **monthly** partitioned checks container.
8.  The configuration of the [monthly_partition_row_count](../checks/table/volume/row-count.md#monthly-partition-row-count) data quality check.

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


## Configure issue severity levels
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
These checks will only capture the *sensor result* from the [sensor](definition-of-data-quality-sensors.md) and store
it in both the [sensor_readouts](../reference/parquetfiles/sensor_readouts.md), and the
check results [check_results](../reference/parquetfiles/check_results.md) parquet tables.

The issue severity level for these checks will be always *valid*, storing the value 0 in the `severity` column
in the [check_results](../reference/parquetfiles/check_results.md) parquet table.
Checks without any rules enabled will also not be included in the data quality KPI, because their `include_in_kpi`
flag will be *false*. The flag `include_in_sla` will be also *false*.

Due to a limitation in the YAML format, a node without a value makes the YAML file invalid.
However, every YAML supports JSON inclusions. 

The following example shows how to activate a check without setting any rules by setting its value
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
Some data quality [rules](definition-of-data-quality-rules.md) do not have any parameters (thresholds).
Configuring these rules uses the same YAML/JSON trick to set the value of the rule to a JSON `{}` object,
enabling the rule at the given severity level.

The following example shows the [daily_column_count_changed](../checks/table/schema/column-count-changed.md#daily-column-count-changed)
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
Some data quality [sensors](definition-of-data-quality-sensors.md) are parametrized. The parameters are used
in the SQL template and will be rendered in the SQL query that is generated from the sensor.

The following example shows how to use the
[daily_text_found_in_set_percent](../checks/column/accepted_values/text-found-in-set-percent.md#daily-text-found-in-set-percent)
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
            daily_text_found_in_set_percent:
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


## Configure columns
The list of columns is stored in the `spec.columns` node in the *.dqotable.yaml* file.
The [configuration of the column metadata](configuring-data-sources.md#configuring-columns) is described 
in the configuration of the data sources.

### **Column profiling checks**
[Profiling checks](definition-of-data-quality-checks/data-profiling-checks.md) are configured on columns the same way as on tables.
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

1.  The container of the column-level [profiling checks](definition-of-data-quality-checks/data-profiling-checks.md).
2.  [profile_nulls_count](../checks/column/nulls/nulls-count.md#profile-nulls-count) check without any rules,
    the check will only capture the number of rows with a null value in the *cumulative_confirmed* column.
3.  [profile_nulls_percent](../checks/column/nulls/nulls-percent.md#profile-nulls-percent) check that measures a percentage
    of rows with null values, instead of returning the number of rows. The check is configured to raise a **warning** severity
    issue when any null rows were detected (the percentage of null values is above 0%). An **error** severity issue is raised
    when the percentage of rows with null values exceeds 1%.
4.  [profile_column_exists](../checks/column/schema/column-exists.md#profile-column-exists) check that verifies that the
    column is present in the table by reading the metadata of the table. 
5.  The *expected_value* rule parameter's value is 1, which means that DQOps requires that the column was found.

### **Column monitoring checks**
Column-level [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are also configured in a very similar way.

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

1.  The container of the column-level [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md).
2.  The container of the daily monitoring checks.

When the [scheduling](../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md) is enabled, these checks will be executed daily,
detecting if any rows with null values were identified (measuring the completeness of the *cumulative_confirmed* column).
Also, DQOps will retrieve the table schema from the data source and verify if the column is still found in the table's metadata. 


### **Column partitioned checks**
Configuring column-level [partitioned checks](definition-of-data-quality-checks/partition-checks.md) is also
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
2.  The container of the column-level [partitioned checks](definition-of-data-quality-checks/partition-checks.md).

## Activate multiple checks at once
DQOps supports also alternative methods of activating data quality checks, designed to configure thousands of checks at once,
by turning on data quality checks using filters for target tables, columns, and the column's data type.

### **Activate multiple checks with DQOps shell**
The [`dqo check activate`](../command-line-interface/check.md#dqo-check-activate) command supports activating multiple checks
from the [DQOps shell](command-line-interface.md). 
Ready-to-use command examples for activating data quality checks are provided in the [documentation of data quality checks](../checks/index.md).

The following example activates the [daily_row_count](../checks/table/volume/row-count.md#daily-row-count) check on
all fact table, raising a *warning* severity issue when the table has less than 10000 rows.
The `-Wrule_parameter_name=` parameters support passing parameters to the [data quality rules](definition-of-data-quality-rules.md).

``` { .asc }
dqo> check activate -c=connection_name -t=public.fact_* -ch=daily_row_count --enable-warning -Wmin_count=10000
```

The next example activates the [daily_nulls_percent](../checks/column/nulls/nulls-percent.md#daily-nulls-percent) check
to measure that the columns that match the *\*_id* name pattern contain only non-null values, counting the percentage of null values,
and raising a data quality issue when the percentage is above 0%.

``` { .asc }
dqo> check activate -c=connection_name -t=public.fact_* -ch=daily_nulls_percent -col=*_id --enable-warning -Wmax_percent=0
```

Configuring data quality checks to raise *error* severity issues requires slightly different parameters, shown in the example below.

``` { .asc }
dqo> check activate -c=connection_name -t=public.fact_* -ch=daily_nulls_percent -col=*_id --enable-error -Emax_percent=0
```

### **Configure multiple checks using the UI**
DQOps provides the option to configure multiple checks from the user interface. You can search for target tables and 
columns to activate data quality checks or review the configuration of rules.

![Search for checks on the multiple check editor](https://dqops.com/docs/images/working-with-dqo/activate-and-deactivate-multiple-checks/search-for-checks.png)

To learn more about [managing multiple data quality checks](../working-with-dqo/activate-and-deactivate-multiple-checks.md), refer to the manual.


## What's next

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../dqops-installation/run-dqops-as-docker-container.md).
- DQOps has multiple built-in data quality dashboards for displaying data quality KPI. [Learn more about different types of dashboards](types-of-data-quality-dashboards.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- DQOps provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../working-with-dqo/collecting-basic-data-statistics.md).